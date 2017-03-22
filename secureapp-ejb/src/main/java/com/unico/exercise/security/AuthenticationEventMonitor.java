package com.unico.exercise.security;

import com.unico.exercise.security.dto.AuthenticationEvent;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

@Singleton
public class AuthenticationEventMonitor {
    @Inject
    private Logger logger;

    private CopyOnWriteArrayList<String> connectedUsers;

    @PostConstruct
    public void init() {
        this.connectedUsers = new CopyOnWriteArrayList<>();
    }

    public void onAuthenticationEvent(@Observes AuthenticationEvent event) {
        if (event != null &&
                ((event.getType() == AuthenticationEvent.Type.LOGIN) ||
                        (event.getType() == AuthenticationEvent.Type.LOGOUT))) {
            if (event.getType() == AuthenticationEvent.Type.LOGIN) {
                if (!connectedUsers.contains(event.getUsername())) {
                    this.connectedUsers.add(event.getUsername());
                }
            } else {
                if (connectedUsers.contains(event.getUsername())) {
                    this.connectedUsers.remove(event.getUsername());
                }
            }
        } else {
            logger.warning("Unrecognized AuthenticationEvent : [" + event + "]");
        }

    }

    public boolean isUserAlreadyConnected(String username) {
        return this.connectedUsers.contains(username);
    }

    public List<String> getConnectedUsers() {
        return connectedUsers;
    }
}