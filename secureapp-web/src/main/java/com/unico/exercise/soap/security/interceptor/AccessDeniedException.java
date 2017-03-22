package com.unico.exercise.soap.security.interceptor;

/**
 * Created by Alireza on 3/22/2017.
 */
public class AccessDeniedException extends Exception {

    public AccessDeniedException(String message, Throwable cause){
        super(message, cause);
    }

    public AccessDeniedException(String message){
        super(message);
    }
}
