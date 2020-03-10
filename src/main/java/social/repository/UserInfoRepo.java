package social.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import social.model.User;
import social.model.UserInfo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class UserInfoRepo
{
    @PersistenceContext
    EntityManager entityManager;

    public UserInfo create(UserInfo userInfo){
        entityManager.persist(userInfo);
        return userInfo;
    }
}
