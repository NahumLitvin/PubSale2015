package dto;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 5958508548368347717L;
    private String email;
    private String password;

    /**
     *
     */
    public LoginRequest() {
        // TODO Auto-generated constructor stub
    }

    public LoginRequest(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
