/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by chongzhu on 11/15/12
 */
package org.kuali.student.r2.core.class1.state.model;

import org.kuali.student.r2.common.assembler.TransformUtility;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.core.class1.state.dto.StateConstraintInfo;
import org.kuali.student.r2.core.class1.state.infc.StateConstraint;
import org.kuali.student.r2.core.class1.state.infc.StateConstraintOperator;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is the model of KSEN_STATE_CNSTRNT table in the database.
 *
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSEN_STATE_CNSTRNT")
public class StateConstraintEntity extends MetaEntity implements AttributeOwner<StateConstraintAttributeEntity> {
    @Column(name = "AGENDA_ID:")
    private String agendaId;
    @Column(name = "STATE_CNSTRNT_OPERATOR", nullable = false)
    private StateConstraintOperator stateConstraintOperator;
    @Column(name = "STATE_CNSTRNT_TYPE", nullable = false)
    private String typeKey;
    @Column(name = "STATE_CNSTRNT_STATE", nullable = false)
    private String stateKey;

    @ElementCollection
    @CollectionTable(name ="KSEN_STATE_CHG_CNSTRNT",joinColumns = @JoinColumn(name = "STATE_CHG_ID"))
    @Column(name="STATE_CNSTRNT_ID")
    private List<String> relatedObjectStateKeys;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner",orphanRemoval=true)
    private Set<StateConstraintAttributeEntity> attributes = new HashSet<StateConstraintAttributeEntity>();

    public StateConstraintEntity() {

    }

    public StateConstraintEntity(StateConstraint stateConstraint) {
        super(stateConstraint);
        setId(stateConstraint.getId());
        fromDTO(stateConstraint);
    }

    public void fromDTO(StateConstraint stateConstraint) {
        List<Object> orphansToDelete = new ArrayList<Object>();

        this.agendaId = stateConstraint.getAgendaId();
        this.stateConstraintOperator = stateConstraint.getStateConstraintOperator();
        this.typeKey = stateConstraint.getTypeKey();
        this.stateKey = stateConstraint.getStateKey();
        this.relatedObjectStateKeys = stateConstraint.getRelatedObjectStateKeys();
        if (stateConstraint.getRelatedObjectStateKeys() != null) {
            relatedObjectStateKeys = new ArrayList<String>(stateConstraint.getRelatedObjectStateKeys());
        } else {
            relatedObjectStateKeys = null;
        }

        // Merge attributes into entity and add leftovers to be deleted
        orphansToDelete.addAll(TransformUtility.mergeToEntityAttributes(StateConstraintAttributeEntity.class, stateConstraint, this));
    }

    public StateConstraintInfo toDto() {
        StateConstraintInfo info = new StateConstraintInfo();
        info.setId(getId());
        info.setAgendaId(getAgendaId());
        info.setStateKey(getStateKey());
        info.setTypeKey(getTypeKey());
        info.setMeta(super.toDTO());
        info.setAttributes(TransformUtility.toAttributeInfoList(this));

        info.getRelatedObjectStateKeys().clear();
        if (relatedObjectStateKeys != null){
            info.getRelatedObjectStateKeys().addAll(relatedObjectStateKeys);
        }

        return info;
    }

    public String getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(String agendaId) {
        this.agendaId = agendaId;
    }

    public StateConstraintOperator getStateConstraintOperator() {
        return stateConstraintOperator;
    }

    public void setStateConstraintOperator(StateConstraintOperator stateConstraintOperator) {
        this.stateConstraintOperator = stateConstraintOperator;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public String getStateKey() {
        return stateKey;
    }

    public void setStateKey(String stateKey) {
        this.stateKey = stateKey;
    }

    public List<String> getRelatedObjectStateKeys() {
        return relatedObjectStateKeys;
    }

    public void setRelatedObjectStateKeys(List<String> relatedObjectStateKeys) {
        this.relatedObjectStateKeys = relatedObjectStateKeys;
    }

    @Override
    public Set<StateConstraintAttributeEntity> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(Set<StateConstraintAttributeEntity> attributes) {
        this.attributes = attributes;
    }
}
