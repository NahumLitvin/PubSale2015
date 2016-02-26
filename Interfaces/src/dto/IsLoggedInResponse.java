package dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IsLoggedInResponse {
    private boolean isLoggedIn;

    public IsLoggedInResponse(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public IsLoggedInResponse() {
        // TODO Auto-generated constructor stub
    }

    @XmlElement
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
}
