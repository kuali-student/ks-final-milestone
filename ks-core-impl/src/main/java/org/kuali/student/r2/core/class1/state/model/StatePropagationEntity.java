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
 */
package org.kuali.student.r2.core.class1.state.model;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.class1.state.dto.StatePropagationInfo;

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
 * This is the entity for State Propagation.
 *
 * @author Kuali Student Team
 */

@Entity
@Table(name = "KSEN_STATE_PROPAGT")
public class StatePropagationEntity extends MetaEntity implements AttributeOwner<StatePropagationAttributeEntity> {

    @Column(name = "STATE_PROPAGT_TYPE", nullable = false)
    private String typeKey;

    @Column(name = "STATE_PROPAGT_STATE", nullable = false)
    private String stateKey;

    @Column(name="TARGET_STATE_CHG_ID")
    private String targetStateChangeId;

    @ElementCollection
    @CollectionTable(name ="KSEN_STATE_PROPAGT_CNSTRNT",joinColumns = @JoinColumn(name = "STATE_PROPAGT_ID"))
    @Column(name="STATE_CNSTRNT_ID")
    private List<String> stateConstraintIds = new ArrayList<String>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner",orphanRemoval=true)
    private Set<StatePropagationAttributeEntity> attributes = new HashSet<StatePropagationAttributeEntity>();

    public StatePropagationEntity(){
    }

    public StatePropagationEntity(StatePropagationInfo dto){
        super(dto);
        setId(dto.getId());
        fromDto(dto);
    }

    public void fromDto(StatePropagationInfo dto){
        if (dto != null){
            this.setTypeKey(dto.getTypeKey());
            this.setStateKey(dto.getStateKey());
            this.setTargetStateChangeId(dto.getTargetStateChangeId());

            getStateConstraintIds().clear();
            getStateConstraintIds().addAll(dto.getStateConstraintIds());

            if (getAttributes() == null){
                this.setAttributes(new HashSet<StatePropagationAttributeEntity>());
            } else {
                this.getAttributes().clear();
            }

            for (Attribute att : dto.getAttributes()) {
                this.getAttributes().add(new StatePropagationAttributeEntity(att, this));
            }
        }
    }

    public StatePropagationInfo toDto(){
        StatePropagationInfo info = new StatePropagationInfo();
        info.setId(getId());
        info.setStateKey(getStateKey());
        info.setTypeKey(getTypeKey());
        info.setTargetStateChangeId(getTargetStateChangeId());
        info.getStateConstraintIds().clear();
        if (!getStateConstraintIds().isEmpty()){
            info.getStateConstraintIds().addAll(getStateConstraintIds());
        }
        for (StatePropagationAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            info.getAttributes().add(attInfo);
        };
        info.setMeta(super.toDTO());
        return info;
    }

    public String getTargetStateChangeId() {
        return targetStateChangeId;
    }

    public void setTargetStateChangeId(String targetStateChangeId) {
        this.targetStateChangeId = targetStateChangeId;
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

    @Override
    public void setAttributes(Set<StatePropagationAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<StatePropagationAttributeEntity> getAttributes() {
        return attributes;
    }
}
