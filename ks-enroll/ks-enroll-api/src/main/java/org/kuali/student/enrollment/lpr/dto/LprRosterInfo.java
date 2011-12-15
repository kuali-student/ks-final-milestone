package org.kuali.student.enrollment.lpr.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.LprRoster;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.w3c.dom.Element;

/**
 *
 * 
 * @author Kuali Student Team (sambit)
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LprRosterInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "associatedLuiIds",
        "maximumCapacity", "checkInRequired", "checkInFrequency", "meta", "attributes", "_futureElements"})
        
public class LprRosterInfo extends IdEntityInfo implements LprRoster, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> associatedLuiIds;

    @XmlElement
    private Integer maximumCapacity;

    @XmlElement
    private Boolean checkInRequired;

    @XmlElement
    private TimeAmountInfo checkInFrequency;

    @XmlAnyElement
    private List<Element> _futureElements;

    public LprRosterInfo() {
        this.associatedLuiIds = null;
    }

    public LprRosterInfo(LprRoster lprRoster) {
        if (lprRoster != null) {
            this.associatedLuiIds = new ArrayList<String>(lprRoster.getAssociatedLuiIds());
        }
    }

    @Override
    public Boolean getCheckInRequired() {
        return checkInRequired;
    }

    public void setCheckInRequired(Boolean checkInRequired) {
        this.checkInRequired = checkInRequired;
    }

    @Override
    public TimeAmountInfo getCheckInFrequency() {
        return checkInFrequency;
    }

    public void setCheckInFrequency(TimeAmountInfo checkInFrequency) {
        this.checkInFrequency = checkInFrequency;
    }

    @Override
    public List<String> getAssociatedLuiIds() {
        return this.associatedLuiIds;
    }

    public void setAssociatedLuiIds(List<String> associatedLuiIds) {
        this.associatedLuiIds = associatedLuiIds;
    }

    public void setMaximumCapacity(Integer maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    @Override
    public Integer getMaximumCapacity() {
        return maximumCapacity;
    }

}
