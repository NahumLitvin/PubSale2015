package com.pubsale.dal;

import com.pubsale.dto.RegisterRequestDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Nahum on 16/01/2016.
 */
@Entity(name = "users")
class UserDAO {
    private int id;
    private String name;
    private String email;
    private String saltedHashedPassword;
    private String phone;

    public UserDAO() {
        // TODO Auto-generated constructor stub
    }

    public UserDAO(RegisterRequestDTO request, String saltedHashedPassword) {
        this.email = request.getEmail();
        this.name = request.getName();
        this.phone = request.getPhone();
        this.saltedHashedPassword = saltedHashedPassword;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "saltedHashedPassword", nullable = false)
    public String getSaltedHashedPassword() {
        return saltedHashedPassword;
    }

    public void setSaltedHashedPassword(String saltedHashedPassword) {
        this.saltedHashedPassword = saltedHashedPassword;
    }

    @Column(name = "phone", nullable = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
