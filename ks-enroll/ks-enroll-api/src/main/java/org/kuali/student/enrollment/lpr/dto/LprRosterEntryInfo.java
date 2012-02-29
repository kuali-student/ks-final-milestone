package org.kuali.student.enrollment.lpr.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.LprRosterEntry;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LprRosterEntryInfo", propOrder = {"id", "typeKey", "stateKey", "effectiveDate", "expirationDate",
        "lprRosterId", "lprId", "position", "meta", "attributes", "_futureElements"})
public class LprRosterEntryInfo extends RelationshipInfo implements LprRosterEntry, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String lprRosterId;
    @XmlElement
    private String lprId;
    @XmlElement
    private Integer position;

    @XmlAnyElement
    private List<Element> _futureElements;

    public LprRosterEntryInfo() {
        super();
        this.lprRosterId = null;
        this.lprId = null;
        this.position = null;
        this._futureElements = null;
    }

    public LprRosterEntryInfo(LprRosterEntry lprRosterEntry) {
        super();
        this.lprRosterId = lprRosterEntry.getLprRosterId();
        this.lprId = lprRosterEntry.getLprId();
        this.position = lprRosterEntry.getPosition();
    }

    public void setLprRosterId(String lprRosterId) {
        this.lprRosterId = lprRosterId;
    }

    public void setLprId(String lprId) {
        this.lprId = lprId;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String getLprRosterId() {
        return lprRosterId;
    }

    @Override
    public String getLprId() {
        return lprId;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

}
