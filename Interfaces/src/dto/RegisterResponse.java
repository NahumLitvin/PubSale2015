package dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class RegisterResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6897118877453133977L;
    private RegisterStatus Status;

    public RegisterResponse() {
        // TODO Auto-generated constructor stub
    }

    public RegisterResponse(RegisterStatus ok) {
        Status = ok;
    }

    @XmlElement
    public RegisterStatus getStatus() {
        return Status;
    }

    public void setStatus(RegisterStatus status) {
        Status = status;
    }
}
