package social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social.model.Role;
import social.model.User;

import java.util.HashSet;
import java.util.Set;

/**
 * service is responsible for providing authentication details to Authentication Manager
 */
@Service
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userService.findByName(username);

        if (user == null)
        {
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }

        return
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        getGrantedAuthorities(user)
                ); // new principal
    }

    private Set<GrantedAuthority> getGrantedAuthorities(User user)
    {
        Set<GrantedAuthority> authorities = new HashSet<>();

        if (user.getRoles() != null)
        {
            for (Role role : user.getRoles())
            {
                authorities.add(new SimpleGrantedAuthority(role.getTitle()));
            }
        }

        return authorities;
    }
}
