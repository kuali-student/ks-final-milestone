package org.kuali.student.r2.core.class1.state.dto;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.core.class1.state.infc.StatePropagation;

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
@XmlType(name = "StatePropagationInfo", propOrder = {"id", "typeKey", "stateKey",
        "targetStateChangeId", "stateConstraintIds", "meta", "attributes", "_futureElements"})
public class StatePropagationInfo extends IdNamelessEntityInfo implements StatePropagation
{
    @XmlElement
    private String targetStateChangeId;
    @XmlElement
    private List<String> stateConstraintIds;
    @XmlAnyElement
    private List<Element> _futureElements;

    public StatePropagationInfo() {

    }

    public StatePropagationInfo(StatePropagation statePropagation) {
        super(statePropagation);
        if (statePropagation != null) {
            this.targetStateChangeId = statePropagation.getTargetStateChangeId();
            this.stateConstraintIds = new ArrayList<String>(statePropagation.getStateConstraintIds());
        }
    }

    @Override
    public String getTargetStateChangeId() {
        return targetStateChangeId;
    }

    public void setTargetStateChangeId(String targetStateChangeId) {
        this.targetStateChangeId = targetStateChangeId;
    }

    @Override
    public List<String> getStateConstraintIds() {
        if(this.stateConstraintIds == null) {
            stateConstraintIds = new ArrayList<String>();
        }
        return this.stateConstraintIds;
    }

    public void setStateConstraintIds(List<String> stateConstraintIds) {
        this.stateConstraintIds = stateConstraintIds;
    }
}
