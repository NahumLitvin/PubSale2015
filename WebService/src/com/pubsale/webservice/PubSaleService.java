package com.pubsale.webservice;

import com.pubsale.dal.HibernatePubSaleService;
import com.pubsale.dto.*;
import com.pubsale.interfaces.IPubSaleService;
import retrofit.http.Body;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/pubsale")
public class PubSaleService implements IPubSaleService {
    static HibernatePubSaleService service = new HibernatePubSaleService();

    @GET
    @Path("/hi")
    public String Hi() {
        return "hi";
    }


    @Path("/IsLoggedInRequestDTO")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public IsLoggedInRequestDTO IsLoggedInRequestDTO() {
        return new IsLoggedInRequestDTO() {{
            setEmail("lnahum@gmail.com");
            setSession("123456789");
        }};
    }


    @Path("/IsLoggedIn")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public IsLoggedInResponseDTO IsLoggedIn(IsLoggedInRequestDTO request) {
        return service.IsLoggedIn(request);
    }

    @Path("/Login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponseDTO Login(LoginRequestDTO request) {
        return service.Login(request);
    }

    @Path("/Register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RegisterResponseDTO Register(RegisterRequestDTO request) {
        return service.Register(request);
    }

    @Path("/CreateAuction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public IsActionSuccededDTO CreateAuction(CreateAuctionRequestDTO request) {
        return service.CreateAuction(request);
    }

    @Path("/GetAuctions")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<AuctionDTO> GetAuctions(GetAuctionsRequestDTO request) {
        return service.GetAuctions(request);
    }

    @Path("/GetCategories")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<CategoryDTO> GetCategories() {
        return service.GetCategories();
    }

    @Path("/BidInAuction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public IsActionSuccededDTO BidInAuction(@Body BidRequestDTO request) {
        return service.BidInAuction(request);
    }

    @Override
    public void Close() {

    }
}

/*}
*/