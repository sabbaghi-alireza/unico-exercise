package com.unico.exercise.soap.security.config;

import com.unico.exercise.security.UserService;
import com.unico.exercise.security.entity.User;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.mindrot.jbcrypt.BCrypt;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by Alireza on 3/22/2017.
 */
public class CustomUTValidator implements Validator {

    private UserService userService;

    public CustomUTValidator() {
        try {
            InitialContext ctx = new InitialContext();
            String moduleName = (String) ctx.lookup("java:module/ModuleName");
            String appName = (String) ctx.lookup("java:app/AppName");
            this.userService = (UserService) ctx.lookup("java:global/secureapp-ear/secureapp-ejb/UserService");
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public Credential validate(Credential credential, RequestData data) throws WSSecurityException {
        String user = credential.getUsernametoken().getName();
        String password = credential.getUsernametoken().getPassword();

        try {
            validateUser(user, password);
        } catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }

//        try {
//            NamePasswordCallbackHandler handler = new NamePasswordCallbackHandler(user, password);
//            LoginContext ctx = new LoginContext("cname", handler);
//            ctx.login();
//            Subject subject = ctx.getSubject();
//            credential.setSubject(subject);
//
//        } catch (LoginException ex) {
//            throw new WSSecurityException(
//                    WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, ex
//            );
//        }

        return credential;
    }

    private void validateUser(String user, String password) throws WSSecurityException {

        User dbUser = userService.findByUsername(user);

        if (dbUser == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }

        if (!BCrypt.checkpw(password, dbUser.getPassword())) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
    }
}
