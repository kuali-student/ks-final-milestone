package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.registration.course.infc.RegRequest;
import org.kuali.student.enrollment.registration.course.infc.RegRequestItem;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegRequestInfo", propOrder = {"id", "typeKey", "stateKey", "requestorId", "studentId", "termKey",
        "regRequestItems", "meta", "attributes", "_futureElements"})
public class RegRequestInfo extends IdEntityInfo implements RegRequest, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String requestorId;
    @XmlElement
    private String studentId;
    @XmlElement
    private String termKey;
    @XmlElement
    private List<RegRequestItem> regRequestItems;
    @XmlAnyElement
    private List<Element> _futureElements;

    public RegRequestInfo() {
        super();
        this.requestorId = null;
        this.studentId = null;
        this.termKey = null;
        this.regRequestItems = null;
        this._futureElements = null;

    }

    public RegRequestInfo(RegRequest regRequest) {
        super(regRequest);
        if (null != regRequest) {
            this.requestorId = regRequest.getRequestorId();
            this.studentId = regRequest.getStudentId();
            this.termKey = regRequest.getTermKey();
            this.regRequestItems = regRequest.getRegRequestItems();
            this._futureElements = null;
        }

    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setTermKey(String termKey) {
        this.termKey = termKey;
    }

    public void setRegRequestItems(List<RegRequestItem> regRequestItems) {
        this.regRequestItems = regRequestItems;
    }

    @Override
    public String getRequestorId() {
        return requestorId;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    @Override
    public String getTermKey() {

        return termKey;
    }

    @Override
    public List<RegRequestItem> getRegRequestItems() {
        return regRequestItems;
    }

}
