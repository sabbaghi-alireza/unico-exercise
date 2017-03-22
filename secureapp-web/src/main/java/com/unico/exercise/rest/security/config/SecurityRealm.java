package com.unico.exercise.rest.security.config;

import com.unico.exercise.security.UserService;
import com.unico.exercise.security.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by Alireza on 3/19/2017.
 */
public class SecurityRealm extends AuthorizingRealm {

    private UserService userService;
    private Logger logger;

    public SecurityRealm() {
        super();
        this.logger = Logger.getLogger(SecurityRealm.class.getName());

        try {
            InitialContext ctx = new InitialContext();
            String moduleName = (String) ctx.lookup("java:module/ModuleName");
            String appName = (String) ctx.lookup("java:app/AppName");
            this.userService = (UserService) ctx.lookup("java:global/secureapp-ear/secureapp-ejb/UserService");
        } catch (NamingException ex) {
            logger
                    .warning("Cannot do the JNDI Lookup to instantiate the User service : " + ex);
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        String username = (String) getAvailablePrincipal(principals);
        Set<String> roleNames = new HashSet<>();
        roleNames.add(this.userService.findByUsername(username).getRole().getName());
        AuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        /**
         * If you want to do Permission Based authorization, you can grab the Permissions List
         associated to your user:
         * Set<String> permissions = new HashSet<>();
         *
         permissions.add(this.userService.findByUsername(username).getRole().getPermissions());
         * ((SimpleAuthorizationInfo)info).setStringPermissions(permissions);
         */
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // Identify account to log to
        UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
        String username = userPassToken.getUsername();
        if (username == null) {
            logger.warning("Username is null.");
            return null;
        }
        // Read the user from DB
        User user = this.userService.findByUsername(username);
        if (user == null) {
            logger.warning("No account found for user [" + username + "]");
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
    }
}
