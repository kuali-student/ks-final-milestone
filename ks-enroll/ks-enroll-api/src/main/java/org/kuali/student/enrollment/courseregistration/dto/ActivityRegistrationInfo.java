package org.kuali.student.enrollment.courseregistration.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseregistration.infc.ActivityRegistration;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;
 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityRegistrationInfo", propOrder = {"id", "typeKey", "stateKey", "activityOffering", "studentId",
        "effectiveDate", "expirationDate", "meta", "attributes", "_futureElements"})
public class ActivityRegistrationInfo extends RelationshipInfo implements ActivityRegistration, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private ActivityOfferingInfo activityOffering;

    @XmlElement
    private String studentId;

    @XmlAnyElement
    private List<Element> _futureElements;

    public ActivityRegistrationInfo() {
        super();
        this.activityOffering= null;
        this.studentId = null;
        this._futureElements = null;

    }

    public ActivityRegistrationInfo(ActivityRegistration activityRegistration) {
        super(activityRegistration);

        if (null != activityRegistration) {
            this.activityOffering = new ActivityOfferingInfo( activityRegistration.getActivityOffering());
            this.studentId = activityRegistration.getStudentId();
            this._futureElements = null;
        }
    }

  
    @Override
    public ActivityOfferingInfo getActivityOffering() {
        return activityOffering;
    }

    public void setActivityOffering(ActivityOfferingInfo activityOffering) {
        this.activityOffering = activityOffering;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}
