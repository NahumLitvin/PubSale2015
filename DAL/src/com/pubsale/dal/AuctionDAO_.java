package com.pubsale.dal;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "Dali", date = "2016-02-20T10:54:13.086+0200")
@StaticMetamodel(AuctionDAO.class)
public class AuctionDAO_ {
    public static volatile SingularAttribute<AuctionDAO, Integer> id;
    public static volatile SingularAttribute<AuctionDAO, String> name;
    public static volatile SingularAttribute<AuctionDAO, String> description;
    public static volatile SingularAttribute<AuctionDAO, String> photo;
    public static volatile SingularAttribute<AuctionDAO, Integer> startPrice;
    public static volatile SingularAttribute<AuctionDAO, Integer> endPrice;
    public static volatile SingularAttribute<AuctionDAO, Date> startDate;
    public static volatile SingularAttribute<AuctionDAO, Date> endDate;
    public static volatile SingularAttribute<AuctionDAO, UserDAO> seller;
}
