package org.kuali.student.enrollment.lpr.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationTransactionItem;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationTransactionItemResult;
import org.kuali.student.enrollment.lpr.infc.RequestOption;
import org.kuali.student.r2.common.dto.EntityInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiPersonRelationTransactionItemInfo", propOrder = {"typeKey", "stateKey", "personId",
        "newLuiId", "existingLuiId", "requestOptions", "lprTransactionItemResult", "meta", "attributes", "_futureElements"})
public class LuiPersonRelationTransactionItemInfo extends EntityInfo implements LuiPersonRelationTransactionItem,
        Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String personId;

    @XmlElement
    private String newLuiId;

    @XmlElement
    private String existingLuiId;

    @XmlElement
    private List<RequestOption> requestOptions;

    @XmlElement
    private LuiPersonRelationTransactionItemResult lprTransactionItemResult;

    @XmlAnyElement
    private List<Element> _futureElements;

    public LuiPersonRelationTransactionItemInfo() {
        super();
        this.personId = null;
        this.newLuiId = null;
        this.existingLuiId = null;
        this.requestOptions = null;
        this._futureElements = null;
    }

    public LuiPersonRelationTransactionItemInfo(LuiPersonRelationTransactionItem luiPersonRelationTransactionItem) {
        
        super(luiPersonRelationTransactionItem);
        if (null != luiPersonRelationTransactionItem) {
            this.personId = luiPersonRelationTransactionItem.getPersonId();
            this.newLuiId = luiPersonRelationTransactionItem.getNewLuiId();
            this.existingLuiId = luiPersonRelationTransactionItem.getExistingLuiId();
            this.requestOptions = luiPersonRelationTransactionItem.getRequestOptions();
            this._futureElements = null;
        }
    }

    @Override
    public LuiPersonRelationTransactionItemResult getLprTransactionItemResult() {
        return lprTransactionItemResult;
    }

    public void setLprTransactionResult(LuiPersonRelationTransactionItemResult lprTransactionResult) {
        this.lprTransactionItemResult = lprTransactionResult;
    }

    @Override
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
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

}
