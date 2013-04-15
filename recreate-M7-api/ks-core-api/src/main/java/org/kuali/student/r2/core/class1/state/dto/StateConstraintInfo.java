package org.kuali.student.r2.core.class1.state.dto;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.core.class1.state.infc.StateConstraint;
import org.kuali.student.r2.core.class1.state.infc.StateConstraintOperator;

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
        "stateConstraintOperator", "relatedObjectStateKeys", "agendaId", "meta", "attributes", "_futureElements"})
public class StateConstraintInfo extends IdNamelessEntityInfo implements StateConstraint {

    @XmlElement
    private StateConstraintOperator stateConstraintOperator;
    @XmlElement
    private List<String> relatedObjectStateKeys;
    @XmlElement
    private String agendaId;
    @XmlAnyElement
    private List<Element> _futureElements;

    public StateConstraintInfo() {

    }

    public StateConstraintInfo(StateConstraint stateConstraint) {
        super(stateConstraint);
        if (stateConstraint != null) {
            this.stateConstraintOperator = stateConstraint.getStateConstraintOperator();
            this.relatedObjectStateKeys = new ArrayList<String>(stateConstraint.getRelatedObjectStateKeys());
            this.agendaId = stateConstraint.getAgendaId();
        }
    }

    @Override
    public StateConstraintOperator getStateConstraintOperator() {
        return this.stateConstraintOperator;
    }

    public void setStateConstraintOperator(StateConstraintOperator stateConstraintOperator) {
        this.stateConstraintOperator = stateConstraintOperator;
    }

    @Override
    public List<String> getRelatedObjectStateKeys() {
        if (this.relatedObjectStateKeys == null) {
            relatedObjectStateKeys = new ArrayList<String>();
        }
        return this.relatedObjectStateKeys;
    }

    public void setRelatedObjectStateKeys(List<String> relatedObjectStateKeys) {
        this.relatedObjectStateKeys = relatedObjectStateKeys;
    }

    @Override
    public String getAgendaId() {
        return this.agendaId;
    }

    public void setAgendaId(String agendaId) {
        this.agendaId = agendaId;
    }
}
