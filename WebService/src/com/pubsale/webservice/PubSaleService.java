package com.pubsale.webservice;

import com.pubsale.dal.HibernatePubSaleService;
import com.pubsale.interfaces.IPubSaleService;
import dto.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.List;

/**
 * Created by Nahum on 26/02/2016.
 */
@WebService()
public class PubSaleService implements IPubSaleService {
    IPubSaleService service = new HibernatePubSaleService();

    public static void main(String[] argv) {
        Object implementor = new PubSaleService();
        String address = "http://localhost:9000/HelloWorld";
        Endpoint.publish(address, implementor);
    }

    @WebMethod
    @Override
    public IsLoggedInResponse IsLoggedIn(IsLoggedInRequest request) {
        return service.IsLoggedIn(request);
    }

    @WebMethod
    @Override
    public LoginResponse Login(LoginRequest request) {
        return service.Login(request);
    }

    @WebMethod
    @Override
    public RegisterResponse Register(RegisterRequest request) {
        return service.Register(request);
    }

    @WebMethod
    @Override
    public CreateAuctionResponse CreateAuction(CreateAuctionRequest request) {
        return service.CreateAuction(request);
    }

    @Override
    public List<AuctionDTO> GetAuctions(GetAuctionsRequest request) {
        return service.GetAuctions(request);
    }

    @Override
    public void Close() {

    }
}
