package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.registration.course.infc.ActivityRegistration;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;
 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityRegistrationInfo", propOrder = {"id", "typeKey", "stateKey", "activityOfferingId", "studentId",
        "meta", "attributes", "_futureElements"})
public class ActivityRegistrationInfo extends RelationshipInfo implements ActivityRegistration, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String activityOfferingId;

    @XmlElement
    private String studentId;

    @XmlAnyElement
    private List<Element> _futureElements;

    public ActivityRegistrationInfo() {
        super();
        this.activityOfferingId = null;
        this.studentId = null;
        this._futureElements = null;

    }

    public ActivityRegistrationInfo(ActivityRegistration activityRegistration) {
        super(activityRegistration);

        if (null != activityRegistration) {
            this.activityOfferingId = activityRegistration.getActivityOfferingId();
            this.studentId = activityRegistration.getStudentId();
            this._futureElements = null;
        }
    }

    public void setActivityOfferingId(String activityOfferingId) {
        this.activityOfferingId = activityOfferingId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getActivityOfferingId() {
        return activityOfferingId;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

}
