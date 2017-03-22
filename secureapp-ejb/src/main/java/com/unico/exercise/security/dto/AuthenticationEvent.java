package com.unico.exercise.security.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthenticationEvent {

    public enum Type {
        LOGIN,
        LOGOUT
    }

    private String username;
    private Date creation;
    private Type type;

    public AuthenticationEvent(String username, Type type) {
        this.username = username;
        this.type = type;
        this.creation = new Date();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AuthenticationEvent{" +
                "username='" + username + '\'' +
                ", creation=" + creation +
                ", type=" + type +
                '}';
    }
}