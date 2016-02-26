package com.pubsale.dal;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "Dali", date = "2016-02-20T10:51:02.079+0200")
@StaticMetamodel(UserDAO.class)
public class UserDAO_ {
    public static volatile SingularAttribute<UserDAO, Integer> id;
    public static volatile SingularAttribute<UserDAO, String> name;
    public static volatile SingularAttribute<UserDAO, String> email;
    public static volatile SingularAttribute<UserDAO, String> saltedHashedPassword;
    public static volatile SingularAttribute<UserDAO, String> phone;
}
