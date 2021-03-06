package com.unico.exercise.security;

import com.unico.exercise.security.entity.Role;
import com.unico.exercise.security.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Created by Alireza on 3/23/2017.
 */
@Singleton
@Startup
public class DataGenerator {

    @Inject
    UserService userService;

    @Inject
    RoleService roleService;

    @PostConstruct
    public void startup() {
        Role adminRole = getOrSaveRole("ADMIN");
        for (int i = 1; i <= 20; i++) {
            String username = "user" + i;
            String password = "pass" + i;
            if (userService.findByUsername(username) == null) {
                User newUser = new User();
                newUser.setUsername(username);
                newUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(10)));
                newUser.setEnabled(true);
                newUser.setRole(adminRole);
                userService.save(newUser);
            }
        }
    }

    public Role getOrSaveRole(String name) {
        Role dbRole = roleService.findByName(name);
        if (dbRole == null) {
            dbRole = new Role();
            dbRole.setName(name);
            roleService.save(dbRole);
        }
        return dbRole;
    }

    @PreDestroy
    void atShutdown() {
        System.out.println("shutdown");
    }
}
