package com.pubsale.dto;

import java.io.Serializable;

/**
 * Created by Nahum on 20/04/2016.
 */
public class BidRequestDTO implements Serializable {
    private static final long serialVersionUID = 3760393943723412324L;
    int auctionId;
    IsLoggedInRequestDTO request;
    int bidValue;

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auction) {
        this.auctionId = auction;
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
