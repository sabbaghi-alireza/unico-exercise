package com.unico.exercise.rest.security.interceptor;

import com.unico.exercise.security.SecurityContext;
import org.apache.shiro.SecurityUtils;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@CheckRequest
public class CheckRequestInterceptor {

    @Inject
    SecurityContext context;

    @AroundInvoke
    public Object checkAccess(InvocationContext ctx) throws Exception {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            context.setPrincipal(SecurityUtils.getSubject().getPrincipal());
        }
        return ctx.proceed();
    }
}