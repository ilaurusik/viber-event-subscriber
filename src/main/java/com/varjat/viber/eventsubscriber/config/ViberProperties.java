package com.varjat.viber.eventsubscriber.config;

import java.util.Set;

public class ViberProperties {
    private String authKey;
    private Set<String> adminUsers;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public Set<String> getAdminUsers() {
        return adminUsers;
    }

    public void setAdminUsers(Set<String> adminUsers) {
        this.adminUsers = adminUsers;
    }
}
