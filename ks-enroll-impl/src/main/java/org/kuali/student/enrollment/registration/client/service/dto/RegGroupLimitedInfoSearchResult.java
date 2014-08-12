package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegGroupLimitedInfoSearchResult", propOrder = {
        "regGroupId", "regGroupCode", "waitListOffered", "maxWaitListSize", "waitListSize"})
public class RegGroupLimitedInfoSearchResult implements Serializable {

    private String regGroupId;
    private String regGroupCode;
    private boolean waitListOffered;
    private Integer maxWaitListSize;
    private Integer waitListSize;

    public String getRegGroupId() {
        return regGroupId;
    }

    public void setRegGroupId(String regGroupId) {
        this.regGroupId = regGroupId;
    }

    public String getRegGroupCode() {
        return regGroupCode;
    }

    public void setRegGroupCode(String regGroupCode) {
        this.regGroupCode = regGroupCode;
    }

    public Integer getWaitListSize() { return waitListSize; }

    public void setWaitListSize(Integer waitListSize) { this.waitListSize = waitListSize; }

    public boolean isWaitListOffered() {
        return waitListOffered;
    }

    public void setWaitListOffered(boolean waitListOffered) {
        this.waitListOffered = waitListOffered;
    }

    public Integer getMaxWaitListSize() {
        return maxWaitListSize;
    }

    public void setMaxWaitListSize(Integer maxWaitListSize) {
        this.maxWaitListSize = maxWaitListSize;
    }
}
