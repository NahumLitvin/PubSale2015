package com.pubsale.dal;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "Dali", date = "2016-02-20T10:51:31.536+0200")
@StaticMetamodel(SessionDAO.class)
public class SessionDAO_ {
    public static volatile SingularAttribute<SessionDAO, Date> expirationDate;
    public static volatile SingularAttribute<SessionDAO, UserDAO> user;
    public static volatile SingularAttribute<SessionDAO, String> session;
}
