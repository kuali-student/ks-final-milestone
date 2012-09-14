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
 * Created by David Yin on 9/10/12
 */
package org.kuali.student.enrollment.class2.courseoffering.model;

import org.kuali.student.enrollment.class2.courseofferingset.model.SocRolloverResultEntity;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOfferingCluster;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOfferingSet;
import org.kuali.student.r2.common.entity.BaseEntity;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

@Entity
@Table(name = "KSEN_CO_AO_CLUSTER_SET")
public class ActivityOfferingSetEntity extends BaseEntity {

    @Column(name = "ACTIVITY_OFFERING_TYPE")
    private String aoType;

    @ManyToOne
    @JoinColumn(name = "AO_CLUSTER_ID")
    private ActivityOfferingClusterEntity aoCluster;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "KSEN_CO_AO_CLUSTER_SET_AO", joinColumns = @JoinColumn(name = "AOC_SET_ID"))
    @Column(name = "ACTIVITY_OFFERING_ID")
    private final Set<String> aoIds = new HashSet<String>();

    public ActivityOfferingSetEntity() { // no-arg constructor expected in entity
    }

    public ActivityOfferingSetEntity(ActivityOfferingSet aoSet) {
        this.setId(aoSet.getId()); // read-only field
        this.fromDto(aoSet);
    }

    public void fromDto(ActivityOfferingSet aoSet) {
        this.setAoType(aoSet.getActivityOfferingType());
        this.aoIds.clear();
        this.aoIds.addAll(aoSet.getActivityOfferingIds());
    }

    public ActivityOfferingSetInfo toDto() {
        ActivityOfferingSetInfo aoSetInfo = new ActivityOfferingSetInfo();
        // Set the instance variables that are common to most entities
        aoSetInfo.setActivityOfferingType(getAoType());

        List<String> aoIds = new ArrayList<String>();
        for (String aoId : getAoIds()) {
                aoIds.add(aoId);
        }

        aoSetInfo.setActivityOfferingIds(aoIds);
        return aoSetInfo;
    }

    public String getAoType() {
        return aoType;
    }

    public void setAoType(String aoType) {
        this.aoType = aoType;
    }

    public ActivityOfferingClusterEntity getAoCluster() {
        return aoCluster;
    }

    public void setAoCluster(ActivityOfferingClusterEntity aoCluster) {
        this.aoCluster = aoCluster;
    }

    public Set<String> getAoIds() {
        return aoIds;
    }

    public void setAoIds(Set<String> aoIds) {
        this.aoIds.clear();

        if (aoIds != null)
            this.aoIds.addAll(aoIds);
    }
}
