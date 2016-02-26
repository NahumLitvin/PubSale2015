package dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class CreateAuctionResponse implements Serializable {
    private static final long serialVersionUID = 6598301046890548171L;
    boolean IsCreated;
    String failReason;

    /**
     *
     */
    public CreateAuctionResponse() {
        // TODO Auto-generated constructor stub
    }

    public CreateAuctionResponse(boolean isCreated, String failReason) {
        super();
        IsCreated = isCreated;
        this.failReason = failReason;
    }

    @XmlElement
    public boolean isIsCreated() {
        return IsCreated;
    }

    public void setIsCreated(boolean isCreated) {
        IsCreated = isCreated;
    }

    @XmlElement
    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
