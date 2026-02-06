package webb_kurs.individuell_uppgift.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import webb_kurs.individuell_uppgift.user.IUserRepository;
import webb_kurs.individuell_uppgift.user.UserModel;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final IUserRepository userRepository;

    //Because login and register should be available to anybody
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/user/register") || path.equals("/user/login");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getServletPath();

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring("Bearer ".length());

        UUID userId;
        try {
            userId = jwtService.validateToken(token);
        } catch (JWTVerificationException exception) {
            response.setStatus(401);
            return;
        }

        Optional<UserModel> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            response.setStatus(401);
            return;
        }

        UserModel user = userOptional.get();

        SecurityContextHolder
                .getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(
                        user,
                        user.getPassword(),
                        user.getAuthorities()
                ));
        filterChain.doFilter(request, response);
    }
}