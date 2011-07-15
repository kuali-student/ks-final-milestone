package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.registration.course.infc.RegGroupRegistration;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegGroupRegistrationInfo", propOrder = {"id", "typeKey", "stateKey", "regGroupId", "studentId",
        "meta", "attributes", "_futureElements"})
public class RegGroupRegistrationInfo extends RelationshipInfo implements RegGroupRegistration, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String regGroupId;
    @XmlElement
    private String studentId;
    @XmlAnyElement
    private List<Element> _futureElements;
    
    
    public RegGroupRegistrationInfo() {
        super();
        this.regGroupId = null;
        this.studentId = null;
        this._futureElements = null;
    }

    public RegGroupRegistrationInfo(RegGroupRegistration regGroupRegistration) {
        super(regGroupRegistration);

        if (null != regGroupRegistration) {
            this.regGroupId = regGroupRegistration.getRegGroupId();
            this.studentId = regGroupRegistration.getRegGroupId();
            this._futureElements = null;
        }
    }

    public void setRegGroupId(String regGroupId) {
        this.regGroupId = regGroupId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getRegGroupId() {
        return regGroupId;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

}
