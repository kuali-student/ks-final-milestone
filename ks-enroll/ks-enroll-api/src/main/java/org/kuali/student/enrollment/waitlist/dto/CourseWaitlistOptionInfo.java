package org.kuali.student.enrollment.waitlist.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.enrollment.waitlist.course.infc.CourseWaitlistOption;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.w3c.dom.Element;

public class CourseWaitlistOptionInfo extends IdEntityInfo implements CourseWaitlistOption, Serializable {
    
    private static final long serialVersionUID = 1L;

    @XmlElement
    private String regGroupId;
    
    @XmlElement
    private String clearingStrategy;
     
    @XmlElement
    private Boolean checkInRequired;
    
    @XmlElement
    private TimeAmount checkInFrequency;
    
    @XmlAnyElement
    private List<Element> _futureElements;
    
    @Override
    public String getRegGroupId() {
        return regGroupId;
    }

    @Override
    public String getClearingStrategy() {
        return clearingStrategy;
    }

    @Override
    public Boolean getCheckInRequired() {
        return checkInRequired;
    }

    @Override
    public TimeAmount getCheckInFrequency() {
        return checkInFrequency;
    }

}
