package com.pubsale.dal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Nahum on 06/03/2016.
 */
@Entity(name = "categories")
public class CategoryDAO implements Serializable {
    private static final long serialVersionUID = 2121393950784417824L;
    private int id;
    private String name;

    public CategoryDAO() {
        // TODO Auto-generated constructor stub
    }

    @Id
    @GeneratedValue
    @Column
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
