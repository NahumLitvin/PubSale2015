package com.pubsale.interfaces;

import dto.*;

import java.util.List;

public interface IPubSaleService {
    /**
     * gets user name and session guid.
     * if user is logged in and session is valid returns true
     **/
    IsLoggedInResponse IsLoggedIn(IsLoggedInRequest request);

    LoginResponse Login(LoginRequest request);

    RegisterResponse Register(RegisterRequest request);

    CreateAuctionResponse CreateAuction(CreateAuctionRequest request);

    List<AuctionDTO> GetAuctions(GetAuctionsRequest request);


    void Close();

}
