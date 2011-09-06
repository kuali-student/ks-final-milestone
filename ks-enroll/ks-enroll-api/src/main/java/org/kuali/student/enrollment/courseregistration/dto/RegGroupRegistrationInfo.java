package org.kuali.student.enrollment.courseregistration.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.enrollment.courseregistration.infc.RegGroupRegistration;
import org.kuali.student.r2.common.dto.RelationshipInfo;

import com.google.gwt.dom.client.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegGroupRegistrationInfo", propOrder = {"id", "typeKey", "stateKey", "studentId", "registrationGroup",
        "activityRegistrations", "effectiveDate", "expirationDate", "meta", "attributes", "_futureElements"})
public class RegGroupRegistrationInfo extends RelationshipInfo implements RegGroupRegistration, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private RegistrationGroupInfo registrationGroup;

    @XmlElement
    private List<ActivityRegistrationInfo> activityRegistrations;

    @XmlElement
    private String studentId;
    @XmlAnyElement
    private List<Element> _futureElements;

    public RegGroupRegistrationInfo() {
        super();
        this.studentId = null;
        this._futureElements = null;
        this.registrationGroup = null;
        this.activityRegistrations = null;
    }

    public RegGroupRegistrationInfo(RegGroupRegistration regGroupRegistration) {

        if (null != regGroupRegistration) {
            this._futureElements = null;
            this.studentId = null;
            this.registrationGroup = new RegistrationGroupInfo(regGroupRegistration.getRegistrationGroup());
            this.activityRegistrations = (null != regGroupRegistration.getActivityRegistrations()) ? new ArrayList<ActivityRegistrationInfo>(
                    (List<ActivityRegistrationInfo>) regGroupRegistration.getActivityRegistrations()) : null;

        }
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setRegistrationGroup(RegistrationGroupInfo registrationGroup) {
        this.registrationGroup = registrationGroup;
    }

    public void setActivityRegistrations(List<ActivityRegistrationInfo> activityRegistrations) {
        this.activityRegistrations = activityRegistrations;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    @Override
    public RegistrationGroupInfo getRegistrationGroup() {
        return registrationGroup;
    }

    @Override
    public List<ActivityRegistrationInfo> getActivityRegistrations() {
        return activityRegistrations;
    }

}
