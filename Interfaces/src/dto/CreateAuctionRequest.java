package dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class CreateAuctionRequest implements Serializable {
    private static final long serialVersionUID = -2849429158861402846L;

    AuctionDTO auction;

    IsLoggedInRequest isLoggedIn;

    /**
     *
     *
     */
    public CreateAuctionRequest() {
        // TODO Auto-generated constructor stub
    }

    public CreateAuctionRequest(AuctionDTO auction, IsLoggedInRequest isLoggedIn) {
        super();
        this.auction = auction;
        this.isLoggedIn = isLoggedIn;
    }

    @XmlElement
    public AuctionDTO getAuction() {
        return auction;
    }

    public void setAuction(AuctionDTO auction) {
        this.auction = auction;
    }

    @XmlElement
    public IsLoggedInRequest getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(IsLoggedInRequest isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
}
