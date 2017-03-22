package com.unico.exercise.soap.security.interceptor;

import com.unico.exercise.security.UserService;
import com.unico.exercise.security.entity.User;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.xml.ws.WebServiceContext;

@Interceptor
@CheckUserRole
public class CheckUserRoleInterceptor {

    @Inject
    UserService userService;

    @Resource
    WebServiceContext context;

    @AroundInvoke
    public Object checkAccess(InvocationContext ctx) throws Exception {
        CheckUserRole checkUserRole = ctx.getMethod().getAnnotation(CheckUserRole.class);
        String userName = context.getUserPrincipal().getName();
        User dbUser = userService.findByUsername(userName);
        if (dbUser.getRole().getName().equals(checkUserRole.value())) {
            return ctx.proceed();
        }
        throw new AccessDeniedException("Access is denied for user " + userName);
    }
}