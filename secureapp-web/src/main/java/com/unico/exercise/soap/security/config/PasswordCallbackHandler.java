package com.unico.exercise.soap.security.config;

import org.apache.wss4j.common.ext.WSPasswordCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

public class PasswordCallbackHandler implements CallbackHandler {
    private Map<String, String> passwords = new HashMap();

    public PasswordCallbackHandler(Map<String, String> initMap) {
        this.passwords.putAll(initMap);
    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for(int i = 0; i < callbacks.length; ++i) {
            Callback c = callbacks[i];
            if(c != null && c instanceof WSPasswordCallback) {
                WSPasswordCallback pc = (WSPasswordCallback)c;
                String pass = (String)this.passwords.get(pc.getIdentifier());
                if(pass != null) {
                    pc.setPassword(pass);
                    return;
                }
            }
        }

    }

    public void setAliasPassword(String alias, String password) {
        this.passwords.put(alias, password);
    }
}