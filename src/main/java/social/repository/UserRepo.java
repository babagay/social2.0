package social.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import social.model.User;
import social.service.SecurityService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserRepo
{
    @PersistenceContext
    EntityManager entityManager;

    public User findSingleUser(User user){
        return entityManager.createQuery("from users u where u.user = :user", User.class).setParameter("user",user).getSingleResult();
    }

    public List<User> getAll()
    {
        return entityManager.createQuery("from users u order by c.id desc", User.class).getResultList();
    }

    public User getById(Long id)
    {
        return entityManager.find(User.class, id);
    }

    public User create(User user)
    {
        entityManager.persist(user);
        return user;
    }
}
