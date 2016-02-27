package com.pubsale.webservice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Nahum on 26/02/2016.
 */
@WebServlet(name = "hello", urlPatterns = "/")
public class Test extends HttpServlet {
    //IPubSaleService service = new HibernatePubSaleService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Test");
    }
/*

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public IsLoggedInResponseDTO IsLoggedIn(IsLoggedInRequestDTO request) {
        return service.IsLoggedIn(request);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponseDTO Login(LoginRequestDTO request) {
        return service.Login(request);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RegisterResponseDTO Register(RegisterRequestDTO request) {
        return service.Register(request);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CreateAuctionResponseDTO CreateAuction(CreateAuctionRequestDTO request) {
        return service.CreateAuction(request);
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<AuctionDTO> GetAuctions(GetAuctionsRequestDTO request) {
        return service.GetAuctions(request);
    }


    public void Close() {

    }*/
}
