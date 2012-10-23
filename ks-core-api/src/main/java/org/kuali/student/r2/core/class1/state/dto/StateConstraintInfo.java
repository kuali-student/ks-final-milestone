package org.kuali.student.r2.core.class1.state.dto;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.core.class1.state.infc.StateConstraint;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StateConstraintInfo", propOrder = {"id", "typeKey", "stateKey",
        "cardinality", "relatedStateKeys", "agendaId", "meta", "attributes", "_futureElements"})
public class StateConstraintInfo extends IdNamelessEntityInfo implements StateConstraint {

    @XmlElement
    private Cardinality cardinality;
    @XmlElement
    private List<String> relatedStateKeys;
    @XmlElement
    private String agendaId;
    @XmlAnyElement
    private List<Element> _futureElements;

    public StateConstraintInfo() {

    }

    public StateConstraintInfo(StateConstraint stateConstraint) {
        super(stateConstraint);
        if (stateConstraint != null) {
            this.cardinality = stateConstraint.getCardinality();
            this.relatedStateKeys = new ArrayList<String>(stateConstraint.getRelatedStateKeys());
            this.agendaId = stateConstraint.getAgendaId();
        }
    }

    @Override
    public Cardinality getCardinality() {
        return this.cardinality;
    }

    public void setCardinality(Cardinality cardinality) {
        this.cardinality = cardinality;
    }

    @Override
    public List<String> getRelatedStateKeys() {
        if (this.relatedStateKeys == null) {
            return new ArrayList<String>();
        }
        return this.relatedStateKeys;
    }

    public void setRelatedStateKeys(List<String> relatedStateKeys) {
        this.relatedStateKeys = relatedStateKeys;
    }

    @Override
    public String getAgendaId() {
        return this.agendaId;
    }

    public void setAgendaId(String agendaId) {
        this.agendaId = agendaId;
    }
}
