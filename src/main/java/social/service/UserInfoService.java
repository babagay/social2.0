package social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.dao.UserInfoDao;
import social.model.UserInfo;

@Service
public class UserInfoService implements UserInfoServicePrototype
{
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo getUserByFirstName(Long userId)
    {
        UserInfo info = userInfoDao.findUserInfoByUserId(userId);

        return info;
    }

}
