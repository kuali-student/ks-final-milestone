package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.enrollment.registration.course.infc.RegResponse;
import org.kuali.student.enrollment.registration.course.infc.RegResponseItem;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.OperationStatusInfo;
import org.w3c.dom.Element;

public class RegResponseInfo extends IdEntityInfo implements RegResponse, Serializable {

    @XmlElement
    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private String regRequestId;

    @XmlElement
    private List<RegResponseItem> regResponseItemInfos;

    @XmlElement
    private OperationStatusInfo operationStatusInfo;

    @XmlAnyElement
    private List<Element> _futureElements;
    
    @Override
    public List<RegResponseItem> getRegResponseItemInfos() {
        return regResponseItemInfos;
    }

    public void setRegResponseItemInfos(List<RegResponseItem> regResponseItemInfos) {
        this.regResponseItemInfos = regResponseItemInfos;
    }

    @Override
    public OperationStatusInfo getOperationStatusInfo() {
        return operationStatusInfo;
    }

    public void setOperationStatusInfo(OperationStatusInfo operationStatusInfo) {
        this.operationStatusInfo = operationStatusInfo;
    }

    

    @Override
    public String getRegRequestId() {
        return regRequestId;
    }

    public void setRegRequestId(String regRequestId) {
        this.regRequestId = regRequestId;
    }

}
