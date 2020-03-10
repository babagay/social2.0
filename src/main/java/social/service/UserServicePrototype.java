package social.service;

import social.model.User;

public interface UserServicePrototype
{
    void save(User user);

    User findByName(String userName);
}
