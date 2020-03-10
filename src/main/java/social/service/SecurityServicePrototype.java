package social.service;

public interface SecurityServicePrototype
{
    String findLoggedInUserName();

    void autoLogin(String userName, String pass);
}
