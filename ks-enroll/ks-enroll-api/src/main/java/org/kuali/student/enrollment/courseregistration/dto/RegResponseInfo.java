package org.kuali.student.enrollment.courseregistration.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseregistration.infc.RegResponse;
import org.kuali.student.enrollment.courseregistration.infc.RegResponseItem;
import org.kuali.student.r2.common.dto.OperationStatusInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegResponseInfo", propOrder = {"regRequestId", "regResponseItems", "operationStatus", "_futureElements"})
public class RegResponseInfo implements RegResponse, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String regRequestId;
    @XmlElement
    private List<RegResponseItemInfo> regResponseItems;
    @XmlElement
    private OperationStatusInfo operationStatus;
    @XmlAnyElement
    private List<Element> _futureElements;

    public RegResponseInfo() {
        super();
        this.regRequestId = null;
        this.regResponseItems = null;
        this.operationStatus = null;
        this._futureElements = null;

    }

    public RegResponseInfo(RegResponse regResponse) {
        
        if (null != regResponse) {
            this.regRequestId = regResponse.getRegRequestId();
            this.regResponseItems = new ArrayList<RegResponseItemInfo>();

            for (RegResponseItem regResponseItem : regResponse.getRegResponseItems()) {
                this.regResponseItems.add(new RegResponseItemInfo(regResponseItem));
            }
            if (regResponse.getOperationStatus() != null) {
                this.operationStatus = new OperationStatusInfo(regResponse.getOperationStatus());
            }
            this._futureElements = null;
        }
    }

    @Override
    public List<RegResponseItemInfo> getRegResponseItems() {
        return regResponseItems;
    }

    public void setRegResponseItems(List<RegResponseItemInfo> regResponseItems) {
        this.regResponseItems = regResponseItems;
    }

    @Override
    public OperationStatusInfo getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatusInfo operationStatus) {
        this.operationStatus = operationStatus;
    }

    @Override
    public String getRegRequestId() {
        return regRequestId;
    }

    public void setRegRequestId(String regRequestId) {
        this.regRequestId = regRequestId;
    }
}
