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
 * Created by David Yin on 9/7/12
 */
package org.kuali.student.enrollment.class2.courseoffering.model;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOfferingCluster;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOfferingSet;
import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

@Entity
@Table(name = "KSEN_CO_AO_CLUSTER")
public class ActivityOfferingClusterEntity extends MetaEntity implements AttributeOwner<ActivityOfferingClusterAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRIVATE_NAME")
    private String privateName;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;

    @Column(name = "FORMAT_OFFERING_ID")
    private String formatOfferingId;

    @Column(name = "AO_CLUSTER_TYPE")
    private String activityOfferingClusterType;

    @Column(name = "AO_CLUSTER_STATE")
    private String activityOfferingClusterState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval=true)
    private Set<ActivityOfferingClusterAttributeEntity> attributes = new HashSet<ActivityOfferingClusterAttributeEntity>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aoCluster", orphanRemoval=true)
    private Set<ActivityOfferingSetEntity> aoSets = new HashSet<ActivityOfferingSetEntity>();


    public ActivityOfferingClusterEntity() { // no-arg constructor expected in entity
    }

    public ActivityOfferingClusterEntity(ActivityOfferingCluster aoCluster) {
        super(aoCluster);
        this.setId(aoCluster.getId()); // read-only field
        this.setActivityOfferingClusterType(aoCluster.getTypeKey()); // read-only field
        this.fromDto(aoCluster);
    }

    public void fromDto(ActivityOfferingCluster aoCluster) {
        this.setActivityOfferingClusterState(aoCluster.getStateKey());
        this.setActivityOfferingClusterType(aoCluster.getTypeKey());
        this.setName(aoCluster.getName());
        this.setPrivateName(aoCluster.getPrivateName());
        this.setFormatOfferingId(aoCluster.getFormatOfferingId());
        if (aoCluster.getDescr() != null) {
            this.setDescrFormatted(aoCluster.getDescr().getFormatted());
            this.setDescrPlain(aoCluster.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }

        this.setAttributes(new HashSet<ActivityOfferingClusterAttributeEntity>());
        for (Attribute att : aoCluster.getAttributes()) {
            ActivityOfferingClusterAttributeEntity attEntity = new ActivityOfferingClusterAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }

        this.setAoSets(new HashSet<ActivityOfferingSetEntity>());
        for (ActivityOfferingSet aoSet : aoCluster.getActivityOfferingSets()) {
            ActivityOfferingSetEntity aoSetEntity = new ActivityOfferingSetEntity(aoSet);
            this.getAoSets().add(aoSetEntity);
        }
    }

    public ActivityOfferingClusterInfo toDto() {
        ActivityOfferingClusterInfo aoClusterInfo = new ActivityOfferingClusterInfo();
        // Set the instance variables that are common to most entities
        aoClusterInfo.setId(getId());
        aoClusterInfo.setStateKey(getActivityOfferingClusterState());
        aoClusterInfo.setTypeKey(getActivityOfferingClusterType());
        // Then, all the instance variables that are specific to SeatPoolDefinitionEntity
        aoClusterInfo.setName(getName());
        aoClusterInfo.setPrivateName(getPrivateName());
        aoClusterInfo.setFormatOfferingId(getFormatOfferingId());
        aoClusterInfo.setPrivateName(getPrivateName());

        // Then, the meta fields
        aoClusterInfo.setMeta(super.toDTO());
        // Finally, attributes
        for (ActivityOfferingClusterAttributeEntity att: getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            aoClusterInfo.getAttributes().add(attInfo);
        }

        for (ActivityOfferingSetEntity aoSetEntity: getAoSets()) {
            ActivityOfferingSetInfo aoSetInfo = aoSetEntity.toDto();
            aoClusterInfo.getActivityOfferingSets().add(aoSetInfo);
        }
        return aoClusterInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrPlain() {
        return descrPlain;
    }

    public void setDescrPlain(String descrPlain) {
        this.descrPlain = descrPlain;
    }

    public String getDescrFormatted() {
        return descrFormatted;
    }

    public void setDescrFormatted(String descrFormatted) {
        this.descrFormatted = descrFormatted;
    }

    public String getFormatOfferingId() {
        return formatOfferingId;
    }

    public void setFormatOfferingId(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
    }

    public String getActivityOfferingClusterType() {
        return activityOfferingClusterType;
    }

    public void setActivityOfferingClusterType(String activityOfferingClusterType) {
        this.activityOfferingClusterType = activityOfferingClusterType;
    }

    public String getActivityOfferingClusterState() {
        return activityOfferingClusterState;
    }

    public void setActivityOfferingClusterState(String activityOfferingClusterState) {
        this.activityOfferingClusterState = activityOfferingClusterState;
    }

    public Set<ActivityOfferingClusterAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<ActivityOfferingClusterAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public String getPrivateName() {
        return privateName;
    }

    public void setPrivateName(String privateName) {
        this.privateName = privateName;
    }

    public Set<ActivityOfferingSetEntity> getAoSets() {
        return aoSets;
    }

    public void setAoSets(Set<ActivityOfferingSetEntity> aoSets) {
        this.aoSets = aoSets;
    }
}
