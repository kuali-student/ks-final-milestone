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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.infc.LprTransaction;
import org.kuali.student.enrollment.lpr.infc.LprTransactionItem;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;

@Entity
@Table(name = "KSEN_LPR_TRANS")
public class LprTransactionEntity extends MetaEntity implements AttributeOwner<LprTransactionAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "REQUESTING_PERS_ID")
    private String requestingPersonId;

    @Column(name = "ATP_ID")
    private String atpId;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;
    
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;

    @Column(name = "LPR_TRANS_TYPE", nullable=false)
    private String lprTransType;

    @Column(name = "LPR_TRANS_STATE", nullable=false)
    private String lprTransState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval=true)
    private final Set<LprTransactionAttributeEntity> attributes = new HashSet<LprTransactionAttributeEntity>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval=true)
    private final Set<LprTransactionItemEntity> lprTransactionItems = new HashSet<LprTransactionItemEntity>();
   

    public LprTransactionEntity() {}

    public LprTransactionEntity(LprTransaction lprTransaction) {
        super(lprTransaction);
        
        // TODO: determine if these are the static fields on the Entity.
        this.setId(lprTransaction.getId());

        this.setLprTransType(lprTransaction.getTypeKey());
        
       this.fromDto(lprTransaction);
    }

    @SuppressWarnings("unchecked")
	public void fromDto (LprTransaction lprTransaction) {
    	
		
    	 this.setName(lprTransaction.getName());
    	 
         this.setRequestingPersonId(lprTransaction.getRequestingPersonId());
         this.setAtpId(lprTransaction.getAtpId());
         
         this.setLprTransState(lprTransaction.getStateKey());
         
         
         if (lprTransaction.getDescr() != null) {
             this.setDescrFormatted(lprTransaction.getDescr().getFormatted());
             this.setDescrPlain(lprTransaction.getDescr().getPlain());
         } else {
             this.setDescrFormatted(null);
             this.setDescrPlain(null);
         }
         
         this.attributes.clear();
         
         for (Attribute attr : lprTransaction.getAttributes()) {
			
			this.attributes.add(new LprTransactionAttributeEntity(attr, this));
         }

         this.lprTransactionItems.clear();
         
         for (LprTransactionItem lprTransactionItem : lprTransaction.getLprTransactionItems()) {
			
        	 LprTransactionItemEntity item;
			 this.lprTransactionItems.add(item = new LprTransactionItemEntity(lprTransactionItem));
        	 item.setOwner(this);
		}
         
    }
    
    
    public LprTransactionInfo toDto() {

        LprTransactionInfo lpr = new LprTransactionInfo();
        lpr.setId(getId());

        if (this.getLprTransType() != null)
            lpr.setTypeKey(this.getLprTransType());
        if (this.getLprTransState() != null)
            lpr.setStateKey(this.getLprTransState());
        
        lpr.setMeta(super.toDTO());
       
        if (getAttributes() != null) {
            List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
            for (LprTransactionAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                atts.add(attInfo);
            }
            lpr.setAttributes(atts);
        }
        
        lpr.setDescr(new RichTextHelper().toRichTextInfo(getDescrPlain(), getDescrFormatted()));

        lpr.setName(getName());
        lpr.setRequestingPersonId(getRequestingPersonId());
        lpr.setAtpId(getAtpId());        
        List<LprTransactionItemInfo> lprItemsInfo = new ArrayList<LprTransactionItemInfo>();
        if (lprTransactionItems != null) {
            for (LprTransactionItemEntity lprItemEntity : lprTransactionItems) {
                lprItemsInfo.add(lprItemEntity.toDto());
            }
        }
        lpr.setLprTransactionItems(lprItemsInfo);
        return lpr;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLprTransType() {
        return lprTransType;
    }

    public void setLprTransType(String lprTransType) {
        this.lprTransType = lprTransType;
    }

    public String getLprTransState() {
        return lprTransState;
    }

    public void setLprTransState(String lprTransState) {
        this.lprTransState = lprTransState;
    }

    public String getRequestingPersonId() {
        return requestingPersonId;
    }

    public void setRequestingPersonId(String requestingPersonId) {
        this.requestingPersonId = requestingPersonId;
    }

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }
    
    

    public Set<LprTransactionItemEntity> getLprTransactionItems() {
        return lprTransactionItems;
    }

    public void setLprTransactionItems(Set<LprTransactionItemEntity> lprTransactionItems) {
    	
    	this.lprTransactionItems.clear();
    	
    	if (lprTransactionItems != null)
    		this.lprTransactionItems.addAll(lprTransactionItems);
    }


    @Override
    public void setAttributes(Set<LprTransactionAttributeEntity> attributes) {
    	this.attributes.clear();
    	
    	if (attributes != null)
    		this.attributes.addAll(attributes);
    }

    @Override
    public Set<LprTransactionAttributeEntity> getAttributes() {
        return this.attributes;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LprTransactionEntity [version=");
		builder.append(getVersionNumber());
		builder.append(", id=");
		builder.append(getId());
		builder.append(", name=");
		builder.append(name);
		builder.append(", requestingPersonId=");
		builder.append(requestingPersonId);
		builder.append(", atpId=");
		builder.append(atpId);
		builder.append(", lprTransType=");
		builder.append(lprTransType);
		builder.append(", lprTransState=");
		builder.append(lprTransState);
		builder.append("]");
		return builder.toString();
	}

	
}
