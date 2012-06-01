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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.infc.LprTransaction;
import org.kuali.student.enrollment.lpr.infc.LprTransactionItem;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.helper.EntityMergeHelper;
import org.kuali.student.r2.common.helper.EntityMergeHelper.EntityMergeResult;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;

@Entity
@Table(name = "KSEN_LPR_TRANS")
public class LprTransactionEntity extends MetaEntity {

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

    @Column(name = "LRP_TRANS_STATE", nullable=false)
    private String lprTransState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LprTransactionAttributeEntity> attributes;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
    private List<LprTransactionItemEntity> lprTransactionItems;


    public LprTransactionEntity() {}

    public LprTransactionEntity(LprTransaction lprTransaction) {
        super(lprTransaction);
        
        // TODO: determine if these are the static fields on the Entity.
        this.setId(lprTransaction.getId());
        this.setLprTransType(lprTransaction.getTypeKey());
        
       this.fromDto(lprTransaction);
    }

    @SuppressWarnings("unchecked")
	public List<Object>fromDto (LprTransaction lprTransaction) {
    	
    	List<Object>orphanList = new ArrayList<Object>();
		
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
         
         
         EntityMergeHelper<LprTransactionAttributeEntity, Attribute>attributeMergeHelper = new EntityMergeHelper<LprTransactionAttributeEntity, Attribute>();
         
         EntityMergeResult<LprTransactionAttributeEntity> attributeMergeResults = attributeMergeHelper.merge(this.attributes, (List<Attribute>) lprTransaction.getAttributes(), new EntityMergeHelper.EntityMergeOptions<LprTransactionAttributeEntity, Attribute>() {

			@Override
			public String getEntityId(LprTransactionAttributeEntity entity) {
				return entity.getId();
			}

			@Override
			public String getInfoId(Attribute info) {
				return info.getId();
			}

			@Override
			public List<Object> merge(LprTransactionAttributeEntity entity,
					Attribute info) {
				
				entity.fromDto(info);
				
				entity.setOwner(LprTransactionEntity.this);
				
				return new ArrayList<Object>();
			}

			@Override
			public LprTransactionAttributeEntity create(Attribute att) {
				LprTransactionAttributeEntity attr = new LprTransactionAttributeEntity(att);
				
				attr.setOwner(LprTransactionEntity.this);
				
				return attr;
				
			}
        	 
		});
         
         this.setAttributes(attributeMergeResults.getMergedList());
         
         orphanList.addAll(attributeMergeResults.getOrphanList());
         
         
         EntityMergeHelper<LprTransactionItemEntity, LprTransactionItem>transactionItemMergeHelper = new EntityMergeHelper<LprTransactionItemEntity, LprTransactionItem>();
         
         EntityMergeResult<LprTransactionItemEntity> transactionItemMergeResult = transactionItemMergeHelper.merge(this.lprTransactionItems, (List<LprTransactionItem>) lprTransaction.getLprTransactionItems(), new EntityMergeHelper.EntityMergeOptions<LprTransactionItemEntity, LprTransactionItem>() {

			@Override
			public String getEntityId(LprTransactionItemEntity entity) {
				return entity.getId();
			}

			@Override
			public String getInfoId(LprTransactionItem info) {
				return info.getId();
			}

			@Override
			public List<Object> merge(LprTransactionItemEntity entity,
					LprTransactionItem info) {
				
				List<Object>orphanList = entity.fromDto(info);
				
				entity.setOwner(LprTransactionEntity.this);
				
				return orphanList;
			}

			@Override
			public LprTransactionItemEntity create(LprTransactionItem info) {
				LprTransactionItemEntity lprTransactionItemEntity = new LprTransactionItemEntity(info);
			
				lprTransactionItemEntity.setOwner(LprTransactionEntity.this);
				
				return lprTransactionItemEntity;
			}
        	 
		});
         
         this.setLprTransactionItems(transactionItemMergeResult.getMergedList());
         
         orphanList.addAll(transactionItemMergeResult.getOrphanList());
		
		return orphanList;
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
    
    

    public List<LprTransactionItemEntity> getLprTransactionItems() {
        return lprTransactionItems;
    }

    public void setLprTransactionItems(List<LprTransactionItemEntity> lprTransactionItems) {
        this.lprTransactionItems = lprTransactionItems;
    }

    public void setAttributes(List<LprTransactionAttributeEntity> attributes) {
       this.attributes = attributes;
    }

    public List<LprTransactionAttributeEntity> getAttributes() {
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
