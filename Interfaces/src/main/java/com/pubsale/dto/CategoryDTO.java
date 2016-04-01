package com.pubsale.dto;

import java.io.Serializable;

/**
 * Created by Nahum on 19/03/2016.
 */

public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 2121393950784419024L;
    private int id;
    private String name;

    public CategoryDTO() {
        // TODO Auto-generated constructor stub
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
