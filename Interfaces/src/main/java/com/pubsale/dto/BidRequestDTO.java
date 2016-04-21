package com.pubsale.dto;

import java.io.Serializable;

/**
 * Created by Nahum on 20/04/2016.
 */
public class BidRequestDTO implements Serializable {
    private static final long serialVersionUID = 3760393943723412324L;
    AuctionDTO auction;
    IsLoggedInRequestDTO request;
    int bidValue;

    public AuctionDTO getAuction() {
        return auction;
    }

    public void setAuction(AuctionDTO auction) {
        this.auction = auction;
    }

    public IsLoggedInRequestDTO getRequest() {
        return request;
    }

    public void setRequest(IsLoggedInRequestDTO request) {
        this.request = request;
    }

    public int getBidValue() {
        return bidValue;
    }

    public void setBidValue(int bidValue) {
        this.bidValue = bidValue;
    }
}
