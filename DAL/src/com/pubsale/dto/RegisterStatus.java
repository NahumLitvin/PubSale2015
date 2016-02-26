package com.pubsale.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum RegisterStatus {
    @XmlElement
    OK,
    @XmlElement
    UserExists,
    @XmlElement
    Exception
}
