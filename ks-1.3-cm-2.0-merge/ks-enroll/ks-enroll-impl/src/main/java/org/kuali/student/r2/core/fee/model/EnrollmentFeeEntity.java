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
 * Created by Charles on 4/25/12
 */
package org.kuali.student.r2.core.fee.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.fee.dto.EnrollmentFeeAmountInfo;
import org.kuali.student.r2.core.fee.dto.EnrollmentFeeInfo;
import org.kuali.student.r2.core.fee.infc.EnrollmentFee;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSEN_ENROLLMENT_FEE")
public class EnrollmentFeeEntity extends MetaEntity implements AttributeOwner<EnrollmentFeeAttributeEntity> {

    @Column(name = "CURRENCY_TYPE")
    private String currencyType;

    @Column(name = "CURRENCY_QUANTITY")
    private Integer currencyQuantity;

    @Column(name = "ORG_ID")
    private String orgId;

    @Column(name = "REF_OBJECT_URI")
    private String refObjectURI;

    @Column(name = "REF_OBJECT_ID")
    private String refObjectId;

    // =====================================================================
    // The fields below are inherited from MetaEntity (and everything MetaEntity inherits from)
    // MetaEntity is what EnrollmentFee extends (Meta fields are included by inheritance from MetaIdentity)
    @Column(name = "ENRL_FEE_TYPE")
    private String enrollFeeType;

    @Column(name = "ENRL_FEE_STATE")
    private String enrollFeeState;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String plain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<EnrollmentFeeAttributeEntity> attributes = new HashSet<EnrollmentFeeAttributeEntity>();

    public EnrollmentFeeEntity() { // no-arg constructor expected in entity
    }

    public EnrollmentFeeEntity(EnrollmentFee fee) {
        super(fee);
        this.setId(fee.getId()); // read-only field
        this.setEnrollFeeType(fee.getTypeKey()); // read-only field
        this.fromDto(fee);
    }

    public void fromDto(EnrollmentFee fee) {
        this.setEnrollFeeState(fee.getStateKey());
        this.setCurrencyType(fee.getAmount().getCurrencyTypeKey());
        this.setCurrencyQuantity(fee.getAmount().getCurrencyQuantity());
        this.setOrgId(fee.getOrgId());
        this.setRefObjectURI(fee.getRefObjectURI());
        this.setRefObjectId(fee.getRefObjectId());
        // This part usually boiler-plate since many entities have these fields
        if (fee.getDescr() != null) {
            this.setPlain(fee.getDescr().getPlain());
            this.setFormatted(fee.getDescr().getFormatted());
        }
        this.setAttributes(new HashSet<EnrollmentFeeAttributeEntity>());
        for (Attribute att : fee.getAttributes()) {
            EnrollmentFeeAttributeEntity attEntity = new EnrollmentFeeAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
    }

    public EnrollmentFeeInfo toDto() {
        EnrollmentFeeInfo feeInfo = new EnrollmentFeeInfo();
        // Set the instance variables that are common to most entities
        feeInfo.setId(getId());
        feeInfo.setStateKey(getEnrollFeeState());
        feeInfo.setTypeKey(getEnrollFeeType());

        String plain = getPlain();
        String formatted = getFormatted();

        if((plain != null && !"".equals(plain)) || (formatted != null && !"".equals(formatted))  ){
            RichTextInfo descr = new RichTextInfo(plain, formatted);
            feeInfo.setDescr(descr);
        }
        // Then, all the instance variables that are specific to EnrollmentFeeEntity
        if (getCurrencyType() != null) { // strange if it's null, but make the check anyway
            EnrollmentFeeAmountInfo amtInfo = new EnrollmentFeeAmountInfo();
            amtInfo.setCurrencyQuantity(getCurrencyQuantity());
            amtInfo.setCurrencyTypeKey(getCurrencyType());
            feeInfo.setAmount(amtInfo);
        }
        feeInfo.setOrgId(getOrgId());
        feeInfo.setRefObjectURI(getRefObjectURI());
        feeInfo.setRefObjectId(getRefObjectId());
        // Then, the meta fields
        feeInfo.setMeta(super.toDTO());
        // Finally, attributes
        for (EnrollmentFeeAttributeEntity att: getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            feeInfo.getAttributes().add(attInfo);
        }
        return feeInfo;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public Integer getCurrencyQuantity() {
        return currencyQuantity;
    }

    public void setCurrencyQuantity(Integer currencyQuantity) {
        this.currencyQuantity = currencyQuantity;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRefObjectURI() {
        return refObjectURI;
    }

    public void setRefObjectURI(String refObjectURI) {
        this.refObjectURI = refObjectURI;
    }

    public String getRefObjectId() {
        return refObjectId;
    }

    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
    }

    public String getEnrollFeeType() {
        return enrollFeeType;
    }

    public void setEnrollFeeType(String enrollFeeType) {
        this.enrollFeeType = enrollFeeType;
    }

    public String getEnrollFeeState() {
        return enrollFeeState;
    }

    public void setEnrollFeeState(String enrollFeeState) {
        this.enrollFeeState = enrollFeeState;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public Set<EnrollmentFeeAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<EnrollmentFeeAttributeEntity> attributes) {
        this.attributes = attributes;
    }



}
