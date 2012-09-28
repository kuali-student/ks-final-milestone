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
import org.kuali.student.r2.common.assembler.TransformUtility;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    //@org.hibernate.annotations.ForeignKey(name="FK_AOCATTR")
    private Set<ActivityOfferingClusterAttributeEntity> attributes = new HashSet<ActivityOfferingClusterAttributeEntity>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="AO_CLUSTER_ID", nullable=false)
    //@org.hibernate.annotations.ForeignKey(name="FK_AOCSET")
    private Set<ActivityOfferingSetEntity> aoSets = new HashSet<ActivityOfferingSetEntity>();

    public ActivityOfferingClusterEntity() { // no-arg constructor expected in entity
    }

    public ActivityOfferingClusterEntity(ActivityOfferingCluster aoCluster) {
        super(aoCluster);
        this.setId(aoCluster.getId()); // read-only field
        this.setActivityOfferingClusterType(aoCluster.getTypeKey()); // read-only field
        this.fromDto(aoCluster);
    }

    public List<Object> fromDto(ActivityOfferingCluster aoCluster) {
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

        List<Object> orphans = new ArrayList<Object>();

        //Merge in attributes
        orphans.addAll(TransformUtility.mergeToEntityAttributes(ActivityOfferingClusterAttributeEntity.class, aoCluster, this));

        //Merge in AOSets
        Map<String,ActivityOfferingSetEntity> existingAOSets = new HashMap<String,ActivityOfferingSetEntity>();
        if (aoSets != null) {
            for (ActivityOfferingSetEntity aoSetEntity : aoSets) {
                existingAOSets.put(aoSetEntity.getId(), aoSetEntity);
            }
        }

        // Clear out the current list
        aoSets = new HashSet<ActivityOfferingSetEntity>();

        for (ActivityOfferingSet aoSet : aoCluster.getActivityOfferingSets()){
            ActivityOfferingSetEntity aoSetEntity;
            if (existingAOSets.containsKey(aoSet.getId())){
                aoSetEntity = existingAOSets.remove(aoSet.getId());
                aoSetEntity.getAoIds().clear();
                aoSetEntity.getAoIds().addAll(aoSet.getActivityOfferingIds());
            } else {
                aoSetEntity = new ActivityOfferingSetEntity(aoSet);
            }
            aoSets.add(aoSetEntity);
        }

        orphans.addAll(existingAOSets.values());

        return orphans;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityOfferingClusterEntity)) return false;

        ActivityOfferingClusterEntity that = (ActivityOfferingClusterEntity) o;

        if (activityOfferingClusterState != null ? !activityOfferingClusterState.equals(that.activityOfferingClusterState) : that.activityOfferingClusterState != null)
            return false;
        if (activityOfferingClusterType != null ? !activityOfferingClusterType.equals(that.activityOfferingClusterType) : that.activityOfferingClusterType != null)
            return false;
        if (aoSets != null ? !aoSets.equals(that.aoSets) : that.aoSets != null) return false;
        if (attributes != null ? !attributes.equals(that.attributes) : that.attributes != null) return false;
        if (descrFormatted != null ? !descrFormatted.equals(that.descrFormatted) : that.descrFormatted != null)
            return false;
        if (descrPlain != null ? !descrPlain.equals(that.descrPlain) : that.descrPlain != null) return false;
        if (formatOfferingId != null ? !formatOfferingId.equals(that.formatOfferingId) : that.formatOfferingId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (privateName != null ? !privateName.equals(that.privateName) : that.privateName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (privateName != null ? privateName.hashCode() : 0);
        result = 31 * result + (descrPlain != null ? descrPlain.hashCode() : 0);
        result = 31 * result + (descrFormatted != null ? descrFormatted.hashCode() : 0);
        result = 31 * result + (formatOfferingId != null ? formatOfferingId.hashCode() : 0);
        result = 31 * result + (activityOfferingClusterType != null ? activityOfferingClusterType.hashCode() : 0);
        result = 31 * result + (activityOfferingClusterState != null ? activityOfferingClusterState.hashCode() : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        result = 31 * result + (aoSets != null ? aoSets.hashCode() : 0);
        return result;
    }
}
