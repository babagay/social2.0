package social.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import social.model.UserInfo;

// TODO: можно переименовать весь пакет в repositories
// @Repository
public interface UserInfoDao extends JpaRepository<UserInfo, Long>
{
    UserInfo findUserInfoByUserId(Long userId);
}
