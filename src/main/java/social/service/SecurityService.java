package social.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import social.config.WebSecurityConfig;
import social.model.UserInfo;

@Service
public class SecurityService implements SecurityServicePrototype
{
    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private WebSecurityConfig securityConfig;

    public PasswordEncoder getPasswordEncoder()
    {
        return securityConfig.getPasswordEncoder();
    }

    @Override
    public String findLoggedInUserName()
    {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();

        if (userDetails instanceof UserInfo)
        {
            return ((UserInfo) userDetails).getFirstName();
        }

        return null;
    }

    @Override
    public void autoLogin(String userName, String pass)
    {
        UserDetails authUser = userDetailsService.loadUserByUsername(userName);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authUser, pass, authUser.getAuthorities());

        if (authToken.isAuthenticated())
        {
            SecurityContextHolder.getContext().setAuthentication(authToken);

            logger.debug(String.format("Successfully %s auto logged in", userName));
        }
    }

    public String getPrincipalName()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails)
        {
            return ((UserDetails) principal).getUsername();
        }

        return null;
    }

}
