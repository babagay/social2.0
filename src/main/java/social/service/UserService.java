package social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social.dao.RoleDao;
import social.dao.UserDao;
import social.model.Role;
import social.model.User;
import social.model.UserInfo;
import social.repository.UserInfoRepo;
import social.repository.UserRepo;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserServicePrototype
{
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_SUPERVISOR = "SUPERVISOR";

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private UserInfoRepo userInfoRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    // create user via Dao
    @Override
    public void save(User user)
    {
        String openPassword = user.getPassword();
        String encryptedPassword = encoder.encode(openPassword);
        user.setPassword(encryptedPassword);

        setDefaultRole(user);

        userDao.save(user);
    }

    // create user via repository
    public void add(User user){
        PasswordEncoder encoder = securityService.getPasswordEncoder();
        String pass = user.getPassword();
        String passEncoded = encoder.encode(pass);
        user.setPassword(passEncoded);
        setDefaultRole(user);
        userRepository.create(user);

        UserInfo userInfo = new UserInfo(user);
        userInfo.setFirstName(user.getUsername());
        userInfo.setLastName(user.getUsername());
        userInfoRepository.create(userInfo);
    }

    @Override
    public User findByName(String userName)
    {
        return userDao.findByUsername(userName);
    }

    public User findByObject(User user){
        return userRepository.findSingleUser(user);
    }

    private void setDefaultRole(User user){
        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleDao.findByTitle(UserService.ROLE_USER);
        roles.add(defaultRole); // Set role 'USER' for user just created

        user.setRoles(roles);
    }

    /*
    @Transactional(readOnly = true)
    public org.springframework.security.core.userdetails.User getUserByUsername(String name)
    {
        // Get user from users where firstName = name
        User user =  findByName(name);

        // Long userId = user.getId();

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (Role role: user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getTitle()));
        }

        org.springframework.security.core.userdetails.User principal =
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        authorities
                );

        return principal;
    }

    // TODO
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return null;
    }

    // TODO
    // Или это в RolesService?
    // getRolesByUserId
    */
}
