package social.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social.model.Role;

public interface RoleDao extends JpaRepository<Role, Long>
{
    Role findByTitle(String title);
}
