/**
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.model;

import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemRequestOptionInfo;
import org.kuali.student.enrollment.lpr.infc.LprTransactionItem;
import org.kuali.student.enrollment.lpr.infc.LprTransactionItemRequestOption;
import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "KSEN_LPR_TRANS_ITEM")
public class LprTransactionItemEntity extends MetaEntity implements AttributeOwner<LprTransactionItemAttributeEntity> {

    @Column(name = "PERS_ID")
    private String personId;

    @Column(name = "NEW_LUI_ID")
    private String newLuiId;

    @Column(name = "EXISTING_LUI_ID")
    private String existingLuiId;

    @Column(name = "LTI_RESULTING_LPR_ID")
    private String resultingLprId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval = true, fetch = FetchType.EAGER)
    private final Set<LprTransactionValidationResultItemEntity> validationResults = new HashSet<LprTransactionValidationResultItemEntity>();

    @Column(name = "GROUP_ID")
    private String groupId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;

    @Column(name = "LPR_TRANS_ITEM_TYPE")
    private String lprTransactionItemType;

    @Column(name = "LPR_TRANS_ITEM_STATE")
    private String lprTransactionItemState;

    @Column(name = "CROSSLIST")
    private String crossListedCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval = true, fetch = FetchType.EAGER)
    private final Set<LprTransactionItemAttributeEntity> attributes = new HashSet<LprTransactionItemAttributeEntity>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lprTransactionItem", orphanRemoval = true, fetch = FetchType.EAGER)
    private final Set<LprTransactionItemRequestOptionEntity> requestOptions = new HashSet<LprTransactionItemRequestOptionEntity>();

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "KSEN_LPR_TRANS_ITEM_RVG", joinColumns = @JoinColumn(name = "LPR_TRANS_ITEM_ID"))
    @Column(name = "RESULT_VAL_GRP_ID")
    private final Set<String> resultValueGroupIds = new HashSet<String>();

    @Column(name = "LPR_TRANS_ID", updatable = false)
    private String owner;

    public LprTransactionItemEntity() {
    }

    public LprTransactionItemEntity(LprTransactionItem lprTransactionItem) {

        super(lprTransactionItem);
        if (lprTransactionItem != null) {
            this.setId(lprTransactionItem.getId());

            this.fromDto(lprTransactionItem);
        }
    }


    public void fromDto(LprTransactionItem lprTransactionItem) {

        super.fromDTO(lprTransactionItem);

        this.setNewLuiId(lprTransactionItem.getNewLuiId());
        this.setExistingLuiId(lprTransactionItem.getExistingLprId());
        this.setPersonId(lprTransactionItem.getPersonId());

        this.setLprTransactionItemState(lprTransactionItem.getStateKey());
        this.setLprTransactionItemType(lprTransactionItem.getTypeKey());

        this.setCrossListedCode(lprTransactionItem.getCrossListedCode());
        this.setResultingLprId(lprTransactionItem.getResultingLprId());

        if (lprTransactionItem.getDescr() != null) {
            this.setDescrFormatted(lprTransactionItem.getDescr()
                    .getFormatted());
            this.setDescrPlain(lprTransactionItem.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }


        this.validationResults.clear();

        for(ValidationResultInfo validationResultInfo:lprTransactionItem.getValidationResults()){
            this.validationResults.add(new LprTransactionValidationResultItemEntity(validationResultInfo, this));
        }

        this.attributes.clear();

        for (Attribute attr : lprTransactionItem.getAttributes()) {
            this.attributes.add(new LprTransactionItemAttributeEntity(attr, this));
        }

        this.requestOptions.clear();
        for (LprTransactionItemRequestOption requestOption : lprTransactionItem.getRequestOptions()) {
            //Add in options
            LprTransactionItemRequestOptionEntity requestOptionEntity = new LprTransactionItemRequestOptionEntity(requestOption);
            requestOptionEntity.setLprTransactionItem(this);
            this.requestOptions.add(requestOptionEntity);
        }

        this.resultValueGroupIds.clear();

        this.resultValueGroupIds.addAll(lprTransactionItem.getResultValuesGroupKeys());


    }

    public LprTransactionItemInfo toDto() {

        LprTransactionItemInfo lprTransItemInfo = new LprTransactionItemInfo();
        lprTransItemInfo.setId(getId());

        lprTransItemInfo.setTypeKey(this.getLprTransactionItemType());
        lprTransItemInfo.setStateKey(this.getLprTransactionItemState());
        lprTransItemInfo.setExistingLprId(this.getExistingLuiId());
        lprTransItemInfo.setNewLuiId(this.getNewLuiId());
        lprTransItemInfo.setPersonId(this.getPersonId());
        lprTransItemInfo.setCrossListedCode(this.getCrossList());
        lprTransItemInfo.setResultingLprId(this.getResultingLprId());

        lprTransItemInfo.setTransactionId(this.owner);

        lprTransItemInfo.setMeta(super.toDTO());

        lprTransItemInfo.setDescr(new RichTextHelper().toRichTextInfo(
                getDescrPlain(), getDescrFormatted()));

        if (getAttributes() != null) {
            List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
            for (LprTransactionItemAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                atts.add(attInfo);
            }
            lprTransItemInfo.setAttributes(atts);
        }

        Set<LprTransactionItemRequestOptionEntity> requestOptionsList = getRequestOptions();

        if (requestOptionsList != null) {

            List<LprTransactionItemRequestOptionInfo> rOptions = new ArrayList<LprTransactionItemRequestOptionInfo>();

            for (LprTransactionItemRequestOptionEntity lprTransactionItemRequestOptionEntity : requestOptionsList) {

                rOptions.add(lprTransactionItemRequestOptionEntity.toDto());
            }

            lprTransItemInfo.setRequestOptions(rOptions);
        } else {
            lprTransItemInfo.setRequestOptions(new ArrayList<LprTransactionItemRequestOptionInfo>());
        }

        lprTransItemInfo.getResultValuesGroupKeys().addAll(getResultValueGroupIds());

        for(LprTransactionValidationResultItemEntity validationResultEntity : validationResults){
            lprTransItemInfo.getValidationResults().add(validationResultEntity.toDto());
        }

        return lprTransItemInfo;

    }

    public String getDescrFormatted() {
        return descrFormatted;
    }

    public void setDescrFormatted(String descrFormatted) {
        this.descrFormatted = descrFormatted;
    }

    public String getDescrPlain() {
        return descrPlain;
    }

    public void setDescrPlain(String descrPlain) {
        this.descrPlain = descrPlain;
    }

    public String getResultingLprId() {
        return resultingLprId;
    }

    public void setResultingLprId(String resultingLprId) {
        this.resultingLprId = resultingLprId;
    }

    public String getPersonId() {
        return personId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getNewLuiId() {
        return newLuiId;
    }

    public void setNewLuiId(String newLuiId) {
        this.newLuiId = newLuiId;
    }

    public String getExistingLuiId() {
        return existingLuiId;
    }

    public void setExistingLuiId(String existingLuiId) {
        this.existingLuiId = existingLuiId;
    }

    public String getLprTransactionItemType() {
        return lprTransactionItemType;
    }

    public void setLprTransactionItemType(String lprTransactionType) {
        this.lprTransactionItemType = lprTransactionType;
    }

    public String getLprTransactionItemState() {
        return lprTransactionItemState;
    }

    public void setLprTransactionItemState(String lprTransactionState) {
        this.lprTransactionItemState = lprTransactionState;
    }

    public String getCrossList() {
        return crossListedCode;
    }

    public void setCrossListedCode(String crossListedCode) {
        this.crossListedCode = crossListedCode;
    }

    public Set<LprTransactionItemAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<LprTransactionItemAttributeEntity> attributes) {

        this.attributes.clear();

        if (attributes != null)
            this.attributes.addAll(attributes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Set<LprTransactionItemRequestOptionEntity> getRequestOptions() {
        return requestOptions;
    }

    public void setRequestOptions(
            Set<LprTransactionItemRequestOptionEntity> requestOptions) {
        this.requestOptions.clear();

        if (requestOptions != null)
            this.requestOptions.addAll(requestOptions);
    }


    public Set<String> getResultValueGroupIds() {
        return resultValueGroupIds;
    }

    public void setResultValueGroupIds(Set<String> resultValueGroupIds) {
        this.resultValueGroupIds.clear();

        if (resultValueGroupIds != null) {
            this.resultValueGroupIds.addAll(resultValueGroupIds);
        }
    }

    public Set<LprTransactionValidationResultItemEntity> getValidationResults() {
        return validationResults;
    }

    public void setValidationResults(List<LprTransactionValidationResultItemEntity> validationResults) {
        this.validationResults.clear();

        if (validationResults != null) {
            this.validationResults.addAll(validationResults);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LprTransactionItemEntity [id=");
        builder.append(getId());
        builder.append(", personId=");
        builder.append(personId);
        builder.append(", newLuiId=");
        builder.append(newLuiId);
        builder.append(", existingLuiId=");
        builder.append(existingLuiId);
        builder.append(", resultingLprId=");
        builder.append(resultingLprId);
        builder.append(", groupId=");
        builder.append(groupId);
        builder.append(", lprTransactionItemType=");
        builder.append(lprTransactionItemType);
        builder.append(", lprTransactionItemState=");
        builder.append(lprTransactionItemState);
        builder.append(", validationResults=");
        builder.append(validationResults);
        builder.append("]");
        return builder.toString();
    }


}
