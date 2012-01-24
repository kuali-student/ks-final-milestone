package org.kuali.student.enrollment.courseregistration.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseregistration.infc.RegRequest;
import org.kuali.student.enrollment.courseregistration.infc.RegRequestItem;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegRequestInfo", propOrder = {"id", "name", "descr", "typeKey", "stateKey", "requestorId", "termId",
        "regRequestItems", "meta", "attributes", "_futureElements"})
public class RegRequestInfo extends IdEntityInfo implements RegRequest, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String requestorId;

    @XmlElement
    private String termId;
    @XmlElement
    private List<RegRequestItemInfo> regRequestItems;
    @XmlAnyElement
    private List<Element> _futureElements;

    public RegRequestInfo() {
        super();
        this.requestorId = null;
        this.termId = null;
        this.regRequestItems = new ArrayList<RegRequestItemInfo>();
        this._futureElements = null;

    }

    public RegRequestInfo(RegRequest regRequest) {
        super(regRequest);
        if (null != regRequest) {
            this.requestorId = regRequest.getRequestorId();
            this.termId = regRequest.getTermId();
            this.regRequestItems = new ArrayList<RegRequestItemInfo>();
            if (regRequest.getRegRequestItems() != null) {
                for (RegRequestItem regRequestItem : regRequest.getRegRequestItems()) {
                    this.regRequestItems.add(new RegRequestItemInfo(regRequestItem));
                }
            }

            this._futureElements = null;
        }

    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public void setRegRequestItems(List<RegRequestItemInfo> regRequestItems) {
        this.regRequestItems = regRequestItems;
    }

    @Override
    public String getRequestorId() {
        return requestorId;
    }

    @Override
    public String getTermId() {

        return termId;
    }

    @Override
    public List<RegRequestItemInfo> getRegRequestItems() {
        return regRequestItems;
    }

}
