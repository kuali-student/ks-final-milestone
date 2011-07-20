package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.registration.course.infc.RegResponse;
import org.kuali.student.enrollment.registration.course.infc.RegResponseItem;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.OperationStatusInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegResponseInfo", propOrder = {"id", "name", "descr", "typeKey", "stateKey", "regRequestId",
        "regResponseItemInfos", "operationStatusInfo", "meta", "attributes", "_futureElements"})
public class RegResponseInfo extends IdEntityInfo implements RegResponse, Serializable {

    
    private static final long serialVersionUID = 1L;

    @XmlElement
    private String regRequestId;

    @XmlElement
    private List<RegResponseItemInfo> regResponseItemInfos;

    @XmlElement
    private OperationStatusInfo operationStatusInfo;

    @XmlAnyElement
    private List<Element> _futureElements;

    public RegResponseInfo() {
        super();
        this.regRequestId = null;
        this.regResponseItemInfos = null;
        this.operationStatusInfo = null;
        this._futureElements = null;

    }

    public RegResponseInfo(RegResponse regResponse) {
        super(regResponse);
        if (null != regResponse) {
            this.regRequestId = regResponse.getRegRequestId();
            this.regResponseItemInfos = new ArrayList<RegResponseItemInfo>();

            for (RegResponseItem regResponseItemInfo : regResponse.getRegResponseItemInfos()) {
                this.regResponseItemInfos.add(new RegResponseItemInfo(regResponseItemInfo));
            }

            this.operationStatusInfo = regResponse.getOperationStatusInfo();
            this._futureElements = null;
        }
    }

    @Override
    public List<RegResponseItemInfo> getRegResponseItemInfos() {
        return regResponseItemInfos;
    }

    public void setRegResponseItemInfos(List<RegResponseItemInfo> regResponseItemInfos) {
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
