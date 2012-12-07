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
package org.kuali.student.enrollment.class2.courseoffering.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.enrollment.courseoffering.infc.SeatPoolDefinition;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSEN_CO_SEAT_POOL_DEFN")
public class SeatPoolDefinitionEntity extends MetaEntity implements AttributeOwner<SeatPoolDefinitionAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "ACTIVITY_OFFERING_ID")
    private String activityOfferingId;

    @Column(name = "EXPIR_MSTONE_TYPE")
    private String expirationMilestoneTypeKey;

    @Column(name = "PERCENTAGE_IND")
    private boolean isPercentage;

    @Column(name = "SEAT_LIMIT")
    private Integer seatLimit;

    @Column(name = "PROCESSING_PRIORITY")
    private Integer processingPriority;

    @Column(name = "POPULATION_ID")
    private String populationId;

    // =====================================================================
    // The fields below are inherited from MetaEntity (and everything MetaEntity inherits from)
    // MetaEntity is what EnrollmentFee extends (Meta fields are included by inheritance from MetaIdentity)
    @Column(name = "SEAT_POOL_DEFN_TYPE")
    private String seatPoolDefnType;

    @Column(name = "SEAT_POOL_DEFN_STATE")
    private String seatPoolDefnState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<SeatPoolDefinitionAttributeEntity> attributes = new HashSet<SeatPoolDefinitionAttributeEntity>();

    public SeatPoolDefinitionEntity() { // no-arg constructor expected in entity
    }

    public SeatPoolDefinitionEntity(SeatPoolDefinition pool) {
        super(pool);
        this.setId(pool.getId()); // read-only field
        this.setSeatPoolDefnType(pool.getTypeKey()); // read-only field
        this.fromDto(pool);
    }

    public void fromDto(SeatPoolDefinition pool) {
        this.setSeatPoolDefnState(pool.getStateKey());
        this.setSeatPoolDefnType(pool.getTypeKey());
        this.setName(pool.getName());
        this.setExpirationMilestoneTypeKey(pool.getExpirationMilestoneTypeKey());
        this.setIsPercentage(defaultFalse(pool.getIsPercentage()));
        this.setSeatLimit(pool.getSeatLimit());
        this.setProcessingPriority(pool.getProcessingPriority());
        this.setPopulationId(pool.getPopulationId());
        this.setAttributes(new HashSet<SeatPoolDefinitionAttributeEntity>());
        for (Attribute att : pool.getAttributes()) {
            SeatPoolDefinitionAttributeEntity attEntity = new SeatPoolDefinitionAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
    }

    public SeatPoolDefinitionInfo toDto() {
        SeatPoolDefinitionInfo poolInfo = new SeatPoolDefinitionInfo();
        // Set the instance variables that are common to most entities
        poolInfo.setId(getId());
        poolInfo.setStateKey(getSeatPoolDefnState());
        poolInfo.setTypeKey(getSeatPoolDefnType());
        // Then, all the instance variables that are specific to SeatPoolDefinitionEntity
        poolInfo.setName(getName());
        poolInfo.setExpirationMilestoneTypeKey(getExpirationMilestoneTypeKey());
        poolInfo.setIsPercentage(getIsPercentage());
        poolInfo.setSeatLimit(getSeatLimit());
        poolInfo.setProcessingPriority(getProcessingPriority());
        poolInfo.setPopulationId(getPopulationId());
        // Then, the meta fields
        poolInfo.setMeta(super.toDTO());
        // Finally, attributes
        for (SeatPoolDefinitionAttributeEntity att: getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            poolInfo.getAttributes().add(attInfo);
        }
        return poolInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityOfferingId() {
        return activityOfferingId;
    }

    public void setActivityOfferingId(String activityOfferingId) {
        this.activityOfferingId = activityOfferingId;
    }

    public String getExpirationMilestoneTypeKey() {
        return expirationMilestoneTypeKey;
    }

    public void setExpirationMilestoneTypeKey(String expirationMilestoneTypeKey) {
        this.expirationMilestoneTypeKey = expirationMilestoneTypeKey;
    }

    public Boolean getIsPercentage() {
        return isPercentage;
    }

    public void setIsPercentage(Boolean percentage) {
        isPercentage = percentage;
    }

    public void setIsPercentage(boolean percentage) {
        isPercentage = percentage;
    }

    public Integer getSeatLimit() {
        return seatLimit;
    }

    public void setSeatLimit(Integer seatLimit) {
        this.seatLimit = seatLimit;
    }

    public Integer getProcessingPriority() {
        return processingPriority;
    }

    public void setProcessingPriority(Integer processingPriority) {
        this.processingPriority = processingPriority;
    }

    public String getPopulationId() {
        return populationId;
    }

    public void setPopulationId(String populationId) {
        this.populationId = populationId;
    }

    public String getSeatPoolDefnType() {
        return seatPoolDefnType;
    }

    public void setSeatPoolDefnType(String seatPoolDefnType) {
        this.seatPoolDefnType = seatPoolDefnType;
    }

    public String getSeatPoolDefnState() {
        return seatPoolDefnState;
    }

    public void setSeatPoolDefnState(String seatPoolDefnState) {
        this.seatPoolDefnState = seatPoolDefnState;
    }

    public Set<SeatPoolDefinitionAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<SeatPoolDefinitionAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    private boolean defaultFalse(Boolean b) {
        if (b == null) {
            return false;
        }
        return b.booleanValue();
    }

}
