package com.pubsale.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class IsLoggedInRequest implements Serializable {
    private static final long serialVersionUID = 9051597762007925256L;
    private String session;
    private String email;

    /**
     *
     */
    public IsLoggedInRequest() {
        // TODO Auto-generated constructor stub
    }

    public IsLoggedInRequest(String session, String email) {
        super();
        this.session = session;
        this.email = email;
    }

    @XmlElement
    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
