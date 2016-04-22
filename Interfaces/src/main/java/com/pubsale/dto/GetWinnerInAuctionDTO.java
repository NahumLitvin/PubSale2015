package com.pubsale.dto;

/**
 * Created by Nahum on 22/04/2016.
 */
public class GetWinnerInAuctionDTO {
    IsLoggedInRequestDTO isLoggedIn;
    int auctionId;

    public IsLoggedInRequestDTO getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(IsLoggedInRequestDTO isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }
}
