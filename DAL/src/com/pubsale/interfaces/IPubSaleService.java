package com.pubsale.interfaces;

import com.pubsale.dto.*;

public interface IPubSaleService {
    /**
     * gets user name and session guid.
     * if user is logged in and session is valid returns true
     **/
    IsLoggedInResponse IsLoggedIn(IsLoggedInRequest request);

    LoginResponse Login(LoginRequest request);

    RegisterResponse Register(RegisterRequest request);

    CreateAuctionResponse CreateAuction(CreateAuctionRequest request);

    void Close();

}
