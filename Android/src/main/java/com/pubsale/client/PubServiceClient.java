package com.pubsale.client;

import android.util.Log;
import com.pubsale.dto.*;
import com.pubsale.interfaces.IPubSaleService;
import com.squareup.okhttp.OkHttpClient;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;
import retrofit.http.Body;

import java.util.List;

/**
 * Created by Nahum on 03/03/2016.
 */
public class PubServiceClient implements IPubSaleService {
    private static PubServiceClient instance = new PubServiceClient();
    IPubSaleService service;

    private PubServiceClient() {
        super();
        //Retrofit section start from here...
        RestAdapter restAdapter = new RestAdapter.Builder().setConverter(new JacksonConverter())
                .setEndpoint("http://127.0.0.1:8080/PubSaleApi/api/pubsale/").setClient(new OkClient(new OkHttpClient())).build();
        //create an adapter for retrofit with base url

        service = restAdapter.
                create(IPubSaleService.class);
    }

    public static PubServiceClient getInstance() {
        return instance;
    }

    public IsLoggedInResponseDTO IsLoggedIn(IsLoggedInRequestDTO request) {
        try {
            return service.IsLoggedIn(request);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("IsLoggedIn", e.getMessage());
        }
        return null;
    }


    public LoginResponseDTO Login(LoginRequestDTO request) {
        try {
            return service.Login(request);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Login", e.getMessage());
        }
        return null;
    }


    public RegisterResponseDTO Register(RegisterRequestDTO request) {
        try {
            return service.Register(request);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Register", "" + e.getMessage());
        }
        return null;
    }


    public IsActionSuccededDTO CreateAuction(CreateAuctionRequestDTO request) {
        try {
            return service.CreateAuction(request);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CreateAuction", "" + e.getMessage());
        }
        return null;
    }


    public List<AuctionDTO> GetAuctions(GetAuctionsRequestDTO request) {
        try {
            return service.GetAuctions(request);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("GetAuctions", e.getMessage());
        }
        return null;
    }

    public List<CategoryDTO> GetCategories() {
        try {
            return service.GetCategories();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("GetCategories", e.getMessage());
        }
        return null;
    }

    public IsActionSuccededDTO BidInAuction(@Body BidRequestDTO request) {
        return null;
    }

    public void Close() {


    }


}
