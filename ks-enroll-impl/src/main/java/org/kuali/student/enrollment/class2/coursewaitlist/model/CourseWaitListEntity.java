/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.enrollment.class2.coursewaitlist.model;

import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.infc.CourseWaitList;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.List;


/**
 * JPA Entity for the CourseWaitList table
 */

@Entity
@Table(name = "KSEN_CWL")
public class CourseWaitListEntity extends MetaEntity implements AttributeOwner<CourseWaitListAttributeEntity> {

    @Column(name = "REG_IN_FIRST_AAO_IND")
    private Boolean registerInFirstAvailableActivityOffering;

    @Column(name = "AUTO_PROCESSED_IND")
    private Boolean automaticallyProcessed;

    @Column(name = "CONF_REQ_IND")
    private Boolean confirmationRequired;

    @Column(name = "MAX_SIZE")
    private Integer maxSize;

    @Column(name = "CHECK_IN_REQ_IND")
    private Boolean checkInRequired;

    @Column(name = "STD_DUR_TYPE")
    private String standardDurationType;

    @Column(name = "STD_DUR_TIME_QTY")
    private Integer standardDurationTime;

    @Column(name = "ALLOW_HOLD_UTIL_ENTRIES_IND")
    private Boolean allowHoldUntilEntries;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @ElementCollection
    @CollectionTable(name ="KSEN_CWL_ACTIV_OFFER",joinColumns = @JoinColumn(name = "CWL_ID"))
    @Column(name="ACTIV_OFFER_ID")
    List<String> activityOfferingIds;

    @ElementCollection
    @CollectionTable(name ="KSEN_CWL_FORMAT_OFFER",joinColumns = @JoinColumn(name = "CWL_ID"))
    @Column(name="FORMAT_OFFER_ID")
    List<String> formatOfferingIds;

    // =====================================================================
    // The fields below are inherited from MetaEntity (and everything MetaEntity inherits from)
    // MetaEntity is what CourseWaitList extends (Meta fields are included by inheritance from MetaIdentity)

    @Column(name = "CWL_TYPE")
    private String type;

    @Column(name = "CWL_STATE")
    private String state;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch=FetchType.EAGER, orphanRemoval=true)
    private Set<CourseWaitListAttributeEntity> attributes = new HashSet<CourseWaitListAttributeEntity>();

    public CourseWaitListEntity() {
        super();
    }

    public Boolean getRegisterInFirstAvailableActivityOffering() {
        return registerInFirstAvailableActivityOffering;
    }

    public void setRegisterInFirstAvailableActivityOffering(Boolean registerInFirstAvailableActivityOffering) {
        this.registerInFirstAvailableActivityOffering = registerInFirstAvailableActivityOffering;
    }

    public Boolean getAutomaticallyProcessed() {
        return automaticallyProcessed;
    }

    public void setAutomaticallyProcessed(Boolean automaticallyProcessed) {
        this.automaticallyProcessed = automaticallyProcessed;
    }

    public Boolean getConfirmationRequired() {
        return confirmationRequired;
    }

    public void setConfirmationRequired(Boolean confirmationRequired) {
        this.confirmationRequired = confirmationRequired;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Boolean getCheckInRequired() {

        return checkInRequired;
    }

    public void setCheckInRequired(Boolean checkInRequired) {
        this.checkInRequired = checkInRequired;
    }

    public String getStandardDurationType() {
        return standardDurationType;
    }

    public void setStandardDurationType(String standardDurationType) {
        this.standardDurationType = standardDurationType;
    }

    public Integer getStandardDurationTime() {
        return standardDurationTime;
    }

    public void setStandardDurationTime(Integer standardDurationTime) {
        this.standardDurationTime = standardDurationTime;
    }

    public Boolean getAllowHoldUntilEntries() {
        return allowHoldUntilEntries;
    }

    public void setAllowHoldUntilEntries(Boolean allowHoldUntilEntries) {
        this.allowHoldUntilEntries = allowHoldUntilEntries;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set<CourseWaitListAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<CourseWaitListAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public List<String> getActivityOfferingIds() {
        return activityOfferingIds;
    }

    public void setActivityOfferingIds(List<String> activityOfferingIds) {
        this.activityOfferingIds = activityOfferingIds;
    }

    public List<String> getFormatOfferingIds() {
        return formatOfferingIds;
    }

    public void setFormatOfferingIds(List<String> formatOfferingIds) {
        this.formatOfferingIds = formatOfferingIds;
    }

    public void fromDto(CourseWaitList courseWaitList) {
    	
    	super.fromDTO(courseWaitList);
    	
        this.setAllowHoldUntilEntries(courseWaitList.getAllowHoldUntilEntries());
        this.setAttributes(new HashSet<CourseWaitListAttributeEntity>());
        for (Attribute att : courseWaitList.getAttributes()) {
            CourseWaitListAttributeEntity attEntity = new CourseWaitListAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }

        if (courseWaitList.getActivityOfferingIds() != null) {
            activityOfferingIds = new ArrayList<String>(courseWaitList.getActivityOfferingIds());
        } else {
            activityOfferingIds = null;
        }

        if (courseWaitList.getFormatOfferingIds() != null) {
            formatOfferingIds = new ArrayList<String>(courseWaitList.getFormatOfferingIds());
        } else {
            formatOfferingIds = null;
        }
        this.setAutomaticallyProcessed(courseWaitList.getAutomaticallyProcessed());
        this.setAllowHoldUntilEntries(courseWaitList.getAllowHoldUntilEntries());
        this.setCheckInRequired(courseWaitList.getCheckInRequired());
        this.setConfirmationRequired(courseWaitList.getConfirmationRequired());
        this.setEffectiveDate(courseWaitList.getEffectiveDate());
        this.setExpirationDate(courseWaitList.getExpirationDate());
        this.setMaxSize(courseWaitList.getMaxSize());
        this.setRegisterInFirstAvailableActivityOffering(courseWaitList.getRegisterInFirstAvailableActivityOffering());
        this.setState(courseWaitList.getStateKey());
        if(courseWaitList.getCheckInFrequency() != null) {
            this.setStandardDurationTime(courseWaitList.getCheckInFrequency().getTimeQuantity());
            this.setStandardDurationType(courseWaitList.getCheckInFrequency().getAtpDurationTypeKey());
        }
    }

    public CourseWaitListEntity(CourseWaitList courseWaitList) {
        super(courseWaitList);
        this.setId(courseWaitList.getId());
        this.setType(courseWaitList.getTypeKey());
        this.fromDto(courseWaitList);
    }

    public CourseWaitListInfo toDto() {
        CourseWaitListInfo cwlInfo = new CourseWaitListInfo();
        cwlInfo.setId(getId());
        cwlInfo.setStateKey(getState());
        cwlInfo.setTypeKey(getType());
        cwlInfo.setRegisterInFirstAvailableActivityOffering(getRegisterInFirstAvailableActivityOffering());
        cwlInfo.setMaxSize(getMaxSize());
        cwlInfo.setAllowHoldUntilEntries(getAllowHoldUntilEntries());
        cwlInfo.setAutomaticallyProcessed(getAutomaticallyProcessed());
        cwlInfo.setCheckInRequired(getCheckInRequired());
        TimeAmountInfo timeAmountInfo = new TimeAmountInfo();
        timeAmountInfo.setTimeQuantity(getStandardDurationTime());
        timeAmountInfo.setAtpDurationTypeKey(getStandardDurationType());
        cwlInfo.setCheckInFrequency(timeAmountInfo);
        cwlInfo.setConfirmationRequired(getConfirmationRequired());
        cwlInfo.setEffectiveDate(getEffectiveDate());
        cwlInfo.setExpirationDate(getExpirationDate());
        cwlInfo.setMeta(super.toDTO());
        for(CourseWaitListAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            cwlInfo.getAttributes().add(attInfo);
        }
        if(activityOfferingIds != null) {
            cwlInfo.setActivityOfferingIds(new ArrayList());
            cwlInfo.getActivityOfferingIds().addAll(activityOfferingIds);
        }
        if(formatOfferingIds != null) {
            cwlInfo.setFormatOfferingIds(new ArrayList());
            cwlInfo.getFormatOfferingIds().addAll(formatOfferingIds);
        }

        return cwlInfo;
    }
}
