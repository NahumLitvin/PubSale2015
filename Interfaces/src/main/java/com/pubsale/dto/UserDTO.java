package com.pubsale.dto;

import java.io.Serializable;

/**
 * Created by Nahum on 21/04/2016.
 */
public class UserDTO implements Serializable {
    String name;
    String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
