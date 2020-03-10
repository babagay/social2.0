package social.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import social.model.User;


public interface UserDao extends JpaRepository<User, Long>
{
    // [!] Чтобы имплементация метода сгенерилась автоматом, видимо, нужно,
    // чтоб слово Username совпадало с названием столбца username
    User findByUsername(String userName);

    // TODO: выбрать все роли пользователя
//    select u.id, u.username,  r.title
//    from users u
//    join user_to_role ur on ur.user_id = u.id
//    join roles r on r.id = ur.role_id
//    where u.username = 'admin'
//    getRolesByUserId()
}
