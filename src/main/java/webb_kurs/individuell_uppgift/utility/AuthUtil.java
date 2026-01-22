package webb_kurs.individuell_uppgift.utility;

import webb_kurs.individuell_uppgift.user.User;

public class AuthUtil {

    public static boolean validatePassword(User user, String password) {
        return user.getPassword().equals(password);
    }
}
