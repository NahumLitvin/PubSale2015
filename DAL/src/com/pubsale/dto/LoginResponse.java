package com.pubsale.dto;

public class LoginResponse {
    private String session;

    public LoginResponse(String session) {
        this.session = session;

    }

    public LoginResponse() {
        // TODO Auto-generated constructor stub
    }

    /*if not null user is logged in*/
    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
