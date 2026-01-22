package webb_kurs.individuell_uppgift.user;

import webb_kurs.individuell_uppgift.exeptions.CreateUserException;

public interface IUserService {
    User createUser(String username, String password) throws CreateUserException;
}
