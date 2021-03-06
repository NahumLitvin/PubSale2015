package com.pubsale.dal;

import javax.persistence.*;

@Entity(name = "auctions")
class AuctionDAO {
    private int id;
    private String name;
    private String description;
    private byte[] photo;
    private int currentPrice;
    private int endPrice;
    private long startUnixTime;
    private long endUnixTime;
    private UserDAO seller;

    private UserDAO topBidder;
    private CategoryDAO category;

    public AuctionDAO() {
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

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column
    @Lob
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Column
    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Column
    public int getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }

    @Column
    public long getStartUnixTime() {
        return startUnixTime;
    }

    public void setStartUnixTime(long startUnixTime) {
        this.startUnixTime = startUnixTime;
    }

    @Column
    public long getEndUnixTime() {
        return endUnixTime;
    }

    public void setEndUnixTime(long endUnixTime) {
        this.endUnixTime = endUnixTime;
    }

    @OneToOne
    public UserDAO getSeller() {
        return seller;
    }

    public void setSeller(UserDAO seller) {
        this.seller = seller;
    }

    @OneToOne
    public UserDAO getTopBidder() {
        return topBidder;
    }

    public void setTopBidder(UserDAO topBidder) {
        this.topBidder = topBidder;
    }


    @OneToOne
    public CategoryDAO getCategory() {
        return category;
    }

    public void setCategory(CategoryDAO category) {
        this.category = category;
    }
}
