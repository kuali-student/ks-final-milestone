package org.kuali.student.enrollment.waitlist.course.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.waitlist.course.infc.CourseWaitlist;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseWaitlistInfo", propOrder = {"id", "typeKey", "stateKey", "courseOfferingId",
        "courseWailtistEntryIds", "meta", "attributes", "_futureElements"})
public class CourseWaitlistInfo extends IdEntityInfo implements CourseWaitlist, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String courseOfferingId;
    @XmlElement
    private List<String> courseWailtistEntryIds;
    @XmlAnyElement
    private List<Element> _futureElements;

    public CourseWaitlistInfo() {
        super();
        this.courseOfferingId = null;
        this.courseWailtistEntryIds = null;
        this._futureElements = null;
    }

    public CourseWaitlistInfo(CourseWaitlist courseWaitlist) {
        super(courseWaitlist);
        if (null != courseWaitlist) {
            this.courseOfferingId = courseWaitlist.getCourseOfferingId();
            this.courseWailtistEntryIds = courseWaitlist.getCourseWailtistEntryIds();
            this._futureElements = null;
        }
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public void setCourseWailtistEntryIds(List<String> courseWailtistEntryIds) {
        this.courseWailtistEntryIds = courseWailtistEntryIds;
    }

    @Override
    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    @Override
    public List<String> getCourseWailtistEntryIds() {
        return courseWailtistEntryIds;
    }

}
