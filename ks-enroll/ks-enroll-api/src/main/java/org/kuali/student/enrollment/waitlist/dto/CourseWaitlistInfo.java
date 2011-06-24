package org.kuali.student.enrollment.waitlist.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.waitlist.course.infc.CourseWaitlist;
import org.kuali.student.enrollment.waitlist.course.infc.CourseWaitlistOption;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseWaitlistInfo", propOrder = {"id", "typeKey", "stateKey", "courseOfferingId",
        "courseWailtistEntryIds", "waitlistOptions", "meta", "attributes", "_futureElements"})
        
public class CourseWaitlistInfo extends IdEntityInfo implements CourseWaitlist, Serializable {
    
    private static final long serialVersionUID = 1L;

    private String courseOfferingId;

    private List<String> courseWailtistEntryIds;

    private List<CourseWaitlistOption> waitlistOptions;
    
    private List<Element> _futureElements;


    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public void setCourseWailtistEntryIds(List<String> courseWailtistEntryIds) {
        this.courseWailtistEntryIds = courseWailtistEntryIds;
    }

    public void setWaitlistOptions(List<CourseWaitlistOption> waitlistOptions) {
        this.waitlistOptions = waitlistOptions;
    }

    @Override
    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    @Override
    public List<String> getCourseWailtistEntryIds() {
        return courseWailtistEntryIds;
    }

    @Override
    public List<CourseWaitlistOption> getWaitlistOptions() {

        return waitlistOptions;
    }

}
