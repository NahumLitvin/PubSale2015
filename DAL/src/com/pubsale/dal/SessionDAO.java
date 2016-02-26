package com.pubsale.dal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity(name = "sessions")
class SessionDAO {
    private String session;
    private UserDAO user;
    private Date expirationDate;

    public SessionDAO(String session, UserDAO user, Date expirationDate) {
        super();
        this.session = session;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    public SessionDAO() {
        // TODO Auto-generated constructor stub
    }

    @OneToOne
    public UserDAO getUser() {
        return user;
    }

    public void setUser(UserDAO user) {
        this.user = user;
    }

    @Column(name = "expirationDate")
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Id
    @Column(name = "session")
    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
