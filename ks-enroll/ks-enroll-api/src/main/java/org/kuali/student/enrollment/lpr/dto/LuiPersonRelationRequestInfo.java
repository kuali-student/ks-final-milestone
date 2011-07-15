package org.kuali.student.enrollment.lpr.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationRequest;
import org.kuali.student.enrollment.lpr.infc.RequestOption;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiPersonRelationRequestInfo", propOrder = {"id", "typeKey", "stateKey", "requestingPersonId",
        "personId", "newLuiId", "existingLuiId", "requestOptions", "meta",
        "attributes", "_futureElements"})
        
        
public class LuiPersonRelationRequestInfo extends IdEntityInfo implements LuiPersonRelationRequest, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String requestingPersonId;
    @XmlElement
    private String personId;
    @XmlElement
    private String newLuiId;
    @XmlElement
    private String existingLuiId;
    @XmlElement
    private List<RequestOption> requestOptions;
    @XmlAnyElement
    private List<Element> _futureElements;

    public LuiPersonRelationRequestInfo(LuiPersonRelationRequest luiPersonRelationRequest) {
        super(luiPersonRelationRequest);

        if (null != luiPersonRelationRequest) {
            this.requestingPersonId = luiPersonRelationRequest.getRequestingPersonId();
            this.personId = luiPersonRelationRequest.getPersonId();
            this.newLuiId = luiPersonRelationRequest.getNewLuiId();
            this.existingLuiId = luiPersonRelationRequest.getExistingLuiId();
            this.requestOptions = luiPersonRelationRequest.requestOptions();
            this._futureElements = null;
        }
    }

    public List<RequestOption> getRequestOptions() {
        return requestOptions;
    }

    public void setRequestOptions(List<RequestOption> requestOptions) {
        this.requestOptions = requestOptions;
    }

    public void setNewLuiId(String newLuiId) {
        this.newLuiId = newLuiId;
    }

    public void setExistingLuiId(String existingLuiId) {
        this.existingLuiId = existingLuiId;
    }

    @Override
    public String getNewLuiId() {
        return newLuiId;
    }

    @Override
    public String getExistingLuiId() {
        return existingLuiId;
    }

    @Override
    public List<RequestOption> requestOptions() {
        return requestOptions;
    }

    @Override
    public String getRequestingPersonId() {
        return requestingPersonId;
    }

    public void setRequestingPersonId(String requestingPersonId) {
        this.requestingPersonId = requestingPersonId;
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

}
