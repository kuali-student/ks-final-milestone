package org.kuali.student.r2.core.class1.state.dto;

import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.core.class1.state.infc.StateChange;

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
@XmlType(name = "StateChangeInfo", propOrder = {"id", "typeKey", "stateKey",
        "fromStateKey", "toStateKey", "stateConstraintIds", "statePropagationIds", "effectiveDate", "expirationDate",
        "meta", "attributes", "_futureElements"})
public class StateChangeInfo extends RelationshipInfo implements StateChange {

    @XmlElement
    private String fromStateKey;
    @XmlElement
    private String toStateKey;
    @XmlElement
    private List<String> stateConstraintIds;
    @XmlElement
    private List<String> statePropagationIds;
    @XmlAnyElement
    private List<Element> _futureElements;

    public StateChangeInfo() {

    }

    public StateChangeInfo(StateChange stateChange) {
        super(stateChange);
        if (stateChange != null) {
            this.fromStateKey = stateChange.getFromStateKey();
            this.toStateKey = stateChange.getToStateKey();
            this.stateConstraintIds = new ArrayList<String>(stateChange.getStateConstraintIds());
            this.statePropagationIds = new ArrayList<String>(stateChange.getStatePropagationIds());
        }
    }

    @Override
    public String getFromStateKey() {
        return fromStateKey;
    }

    public void setFromStateKey(String fromStateKey) {
        this.fromStateKey = fromStateKey;
    }

    @Override
    public String getToStateKey() {
        return toStateKey;
    }

    public void setToStateKey(String toStateKey) {
        this.toStateKey = toStateKey;
    }

    @Override
    public List<String> getStateConstraintIds() {
        if(this.stateConstraintIds == null) {
            stateConstraintIds = new ArrayList<String>();
        }
        return  this.stateConstraintIds;
    }

    public void setStateConstraintIds(List<String> stateConstraintIds) {
        this.stateConstraintIds = stateConstraintIds;
    }

    @Override
    public List<String> getStatePropagationIds() {
        if (this.statePropagationIds == null) {
            statePropagationIds = new ArrayList<String>();
        }
        return this.statePropagationIds;
    }

    public void setStatePropagationIds(List<String> statePropagationIds) {
        this.statePropagationIds = statePropagationIds;
    }

}
