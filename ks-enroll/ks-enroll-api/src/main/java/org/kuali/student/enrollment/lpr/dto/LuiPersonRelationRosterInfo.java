package org.kuali.student.enrollment.lpr.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationRoster;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

/**
 * A representation of a group of LPRs which link back to theit
 * 
 * @author Kuali Student Team (sambit)
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "lprIds", "associatedLuiIds",
        "meta", "attributes", "_futureElements"})
public class LuiPersonRelationRosterInfo extends IdEntityInfo implements LuiPersonRelationRoster {

    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private List<String> associatedLuiIds;

    @XmlElement
    private List<String> lprIds;
    
    @XmlAnyElement
    private List<Element> _futureElements;

    public LuiPersonRelationRosterInfo() {
        this.associatedLuiIds = null;
        this.lprIds = null;
    }

    public LuiPersonRelationRosterInfo(LuiPersonRelationRoster lprRoster) {
        this.associatedLuiIds = new ArrayList<String>(lprRoster.getAssociatedLuiIds());
        this.lprIds = new ArrayList<String>(lprRoster.getLprIds());
    }

    @Override
    public List<String> getAssociatedLuiIds() {
        return this.associatedLuiIds;
    }

    @Override
    public List<String> getLprIds() {
        return this.lprIds;
    }

}
