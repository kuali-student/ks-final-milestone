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
 * Created by Li Pan on 11/14/12
 */
package org.kuali.student.r2.core.class1.state.model;

import org.kuali.student.r2.common.assembler.TransformUtility;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.core.class1.state.dto.StateChangeInfo;
import org.kuali.student.r2.core.class1.state.infc.StateChange;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "KSEN_STATE_CHG")
public class StateChangeEntity extends MetaEntity implements AttributeOwner<StateChangeAttributeEntity> {
    @Column(name = "FROM_STATE_ID")
    private String fromStateKey;
    @Column(name = "TO_STATE_ID")
    private String toStateKey;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT", nullable = false)
    private Date effectiveDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT", nullable = false)
    private Date expirationDate;
    @Column(name = "STATE_CHG_TYPE", nullable = false)
    private String typeKey;
    @Column(name = "STATE_CHG_STATE", nullable = false)
    private String stateKey;

    @ElementCollection
    @CollectionTable(name ="KSEN_STATE_CHG_CNSTRNT",joinColumns = @JoinColumn(name = "STATE_CHG_ID"))
    @Column(name="STATE_CNSTRNT_ID")
    private List<String> stateConstraintIds;

    @ElementCollection
    @CollectionTable(name ="KSEN_STATE_CHG_PROPAGT",joinColumns = @JoinColumn(name = "STATE_CHG_ID"))
    @Column(name="STATE_PROPAGT_ID")
    private List<String> statePropagationIds;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner",orphanRemoval=true)
    private Set<StateChangeAttributeEntity> attributes = new HashSet<StateChangeAttributeEntity>();

    public StateChangeEntity(){

    }

    public StateChangeEntity(StateChange stateChange){
        super(stateChange);
        setId(stateChange.getId());
        setTypeKey(stateChange.getTypeKey());
        setStateKey(stateChange.getStateKey());
        fromDTO(stateChange);
    }

    public void fromDTO(StateChange stateChange){
        List<Object> orphansToDelete = new ArrayList<Object>();
        setEffectiveDate(stateChange.getEffectiveDate());
        setExpirationDate(stateChange.getExpirationDate());
        setFromStateKey(stateChange.getFromStateKey());
        setToStateKey(stateChange.getToStateKey());

        if (stateChange.getStateConstraintIds() != null) {
            stateConstraintIds = new ArrayList<String>(stateChange.getStateConstraintIds());
        } else {
            stateConstraintIds = null;
        }

        if (stateChange.getStatePropagationIds() != null){
            statePropagationIds = new ArrayList<String>(stateChange.getStatePropagationIds());
        } else {
            statePropagationIds = null;
        }

        // Merge attributes into entity and add leftovers to be deleted
        orphansToDelete.addAll(TransformUtility.mergeToEntityAttributes(StateChangeAttributeEntity.class, stateChange, this));
    }

    public StateChangeInfo toDto(){
        StateChangeInfo info = new StateChangeInfo();
        info.setId(getId());
        info.setFromStateKey(getFromStateKey());
        info.setToStateKey(getToStateKey());
        info.setEffectiveDate(getEffectiveDate());
        info.setExpirationDate(getExpirationDate());
        info.setStateKey(getStateKey());
        info.setTypeKey(getTypeKey());
        info.setMeta(super.toDTO());
        info.setAttributes(TransformUtility.toAttributeInfoList(this));

        info.getStateConstraintIds().clear();
        if (stateConstraintIds != null){
            info.getStateConstraintIds().addAll(stateConstraintIds);
        }

        info.getStatePropagationIds().clear();
        if (statePropagationIds != null) {
            info.getStatePropagationIds().addAll(statePropagationIds);
        }

        return info;
    }

    public String getFromStateKey() {
        return fromStateKey;
    }

    public void setFromStateKey(String fromStateKey) {
        this.fromStateKey = fromStateKey;
    }

    public String getToStateKey() {
        return toStateKey;
    }

    public void setToStateKey(String toStateKey) {
        this.toStateKey = toStateKey;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
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

    public List<String> getStateConstraintIds() {
        return stateConstraintIds;
    }

    public void setStateConstraintIds(List<String> stateConstraintIds) {
        this.stateConstraintIds = stateConstraintIds;
    }

    public List<String> getStatePropagationIds() {
        return statePropagationIds;
    }

    public void setStatePropagationIds(List<String> statePropagationIds) {
        this.statePropagationIds = statePropagationIds;
    }

    @Override
    public void setAttributes(Set<StateChangeAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<StateChangeAttributeEntity> getAttributes() {
        return attributes;
    }
}
