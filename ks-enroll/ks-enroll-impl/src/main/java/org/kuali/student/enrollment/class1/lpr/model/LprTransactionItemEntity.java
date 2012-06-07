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
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemRequestOptionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemResultInfo;
import org.kuali.student.enrollment.lpr.infc.LprTransactionItem;
import org.kuali.student.enrollment.lpr.infc.LprTransactionItemRequestOption;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.helper.EntityMergeHelper;
import org.kuali.student.r2.common.helper.EntityMergeHelper.EntityMergeOptions;
import org.kuali.student.r2.common.helper.EntityMergeHelper.EntityMergeResult;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;

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

	@Column(name = "LTI_RESULT_MESSAGE")
	private String resultMessage;

	@Column(name = "LTI_RESULTING_STATUS")
	private String status;

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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private Set<LprTransactionItemAttributeEntity> attributes;
	
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "lprTransactionItem")
	private Set<LprTransactionItemRequestOptionEntity>requestOptions;
	
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "lprTransactionItem")
	private Set<LprTransactionItemResultValueGroupEntity>resultValueGroups;
	
	@ManyToOne
	@JoinColumn(name="LPR_TRANS_ID")
	private LprTransactionEntity owner;

	public LprTransactionItemEntity() {
	}

	public LprTransactionItemEntity(LprTransactionItem lprTransactionItem) {

		super(lprTransactionItem);
		if (lprTransactionItem != null) {
			this.setId(lprTransactionItem.getId());
			
			this.fromDto(lprTransactionItem);
		}
	}
	

	public List<Object> fromDto(LprTransactionItem lprTransactionItem) {
		
		List<Object>orphanList = new ArrayList<Object>();

		this.setNewLuiId(lprTransactionItem.getNewLuiId());
		this.setExistingLuiId(lprTransactionItem.getExistingLuiId());
		this.setPersonId(lprTransactionItem.getPersonId());
		
		this.setLprTransactionItemState(lprTransactionItem.getStateKey());
		this.setLprTransactionItemType(lprTransactionItem.getTypeKey());
		
		if (lprTransactionItem.getDescr() != null) {
			this.setDescrFormatted(lprTransactionItem.getDescr()
					.getFormatted());
			this.setDescrPlain(lprTransactionItem.getDescr().getPlain());
		} else {
			this.setDescrFormatted(null);
			this.setDescrPlain(null);
		}
		
		if (lprTransactionItem.getLprTransactionItemResult() != null) {
			this.setResultingLprId(lprTransactionItem
					.getLprTransactionItemResult().getResultingLprId());
			this.setStatus(lprTransactionItem.getLprTransactionItemResult()
					.getStatus() ? "Y" : "N");
		}

		
		EntityMergeHelper<LprTransactionItemAttributeEntity, Attribute>attributeMergeHelper = new EntityMergeHelper<LprTransactionItemAttributeEntity, Attribute>();
		
		EntityMergeResult<LprTransactionItemAttributeEntity> attributesMergeResult = attributeMergeHelper.merge(attributes, (Set<Attribute>) lprTransactionItem.getAttributes(), new EntityMergeHelper.EntityMergeOptions<LprTransactionItemAttributeEntity, Attribute>() {

			@Override
			public String getEntityId(LprTransactionItemAttributeEntity entity) {
				return entity.getId();
			}

			@Override
			public String getInfoId(Attribute info) {
				return info.getId();
			}

			@Override
			public List<Object> merge(LprTransactionItemAttributeEntity entity,
					Attribute info) {
				
				entity.fromDto(info);
				
				entity.setOwner(LprTransactionItemEntity.this);
				
				return new ArrayList<Object>();
			}

			@Override
			public LprTransactionItemAttributeEntity create(Attribute info) {
				
				LprTransactionItemAttributeEntity entity = new LprTransactionItemAttributeEntity(info);
				
				entity.setOwner(LprTransactionItemEntity.this);
				
				return entity;
			}
		
			
		});

		this.attributes = attributesMergeResult.getMergedList();
		
		orphanList.addAll(attributesMergeResult.getOrphanList());
		
		EntityMergeHelper<LprTransactionItemRequestOptionEntity, LprTransactionItemRequestOption>requestOptionMergeHelper = new EntityMergeHelper<LprTransactionItemRequestOptionEntity, LprTransactionItemRequestOption>();
		
		EntityMergeResult<LprTransactionItemRequestOptionEntity> requestOptionMergeResults = requestOptionMergeHelper.merge(this.requestOptions, (Set<LprTransactionItemRequestOption>) lprTransactionItem.getRequestOptions(), new EntityMergeOptions<LprTransactionItemRequestOptionEntity, LprTransactionItemRequestOption>() {

			@Override
			public String getEntityId(
					LprTransactionItemRequestOptionEntity entity) {
				return entity.getId();
			}

			@Override
			public String getInfoId(LprTransactionItemRequestOption info) {
				return info.getId();
			}

			@Override
			public List<Object> merge(
					LprTransactionItemRequestOptionEntity entity,
					LprTransactionItemRequestOption info) {
				
				entity.fromDto(info);
				
				entity.setLprTransactionItem(LprTransactionItemEntity.this);
				
				return new ArrayList<Object>();
			}

			@Override
			public LprTransactionItemRequestOptionEntity create(
					LprTransactionItemRequestOption info) {
				
				LprTransactionItemRequestOptionEntity entity = new LprTransactionItemRequestOptionEntity(info);
				
				entity.setLprTransactionItem(LprTransactionItemEntity.this);
				
				return entity;
			}
		
		
		});
		
		
		this.requestOptions = requestOptionMergeResults.getMergedList();
		
		orphanList.addAll(requestOptionMergeResults.getOrphanList());
		
		EntityMergeHelper<LprTransactionItemResultValueGroupEntity, String> resultValueGroupMergeHelper = new EntityMergeHelper<LprTransactionItemResultValueGroupEntity, String>();
		
		EntityMergeResult<LprTransactionItemResultValueGroupEntity> resultValueGroupMergeResult = resultValueGroupMergeHelper.mergeStringList(this.resultValueGroups, lprTransactionItem.getResultValuesGroupKeys(), new EntityMergeHelper.StringMergeOptions<LprTransactionItemResultValueGroupEntity>() {

			@Override
			public String getKey(LprTransactionItemResultValueGroupEntity entity) {
				return entity.getResultValueGroupId();
			}

			@Override
			public LprTransactionItemResultValueGroupEntity create(String value) {
				
				LprTransactionItemResultValueGroupEntity entity = new LprTransactionItemResultValueGroupEntity(value);
				
				entity.setLprTransactionItem(LprTransactionItemEntity.this);
				
				return entity;
			}
			
		});
		
		this.resultValueGroups = resultValueGroupMergeResult.getMergedList();
		
		orphanList.addAll(resultValueGroupMergeResult.getOrphanList());
		
		return orphanList;
	}

	public LprTransactionItemInfo toDto() {

		LprTransactionItemInfo lprTransItemInfo = new LprTransactionItemInfo();
		lprTransItemInfo.setId(getId());

		lprTransItemInfo.setTypeKey(this.getLprTransactionItemType());
		lprTransItemInfo.setStateKey(this.getLprTransactionItemState());
		lprTransItemInfo.setExistingLuiId(this.getExistingLuiId());
		lprTransItemInfo.setNewLuiId(this.getNewLuiId());
		lprTransItemInfo.setPersonId(this.getPersonId());
		
		if (this.owner != null)
			lprTransItemInfo.setTransactionId(this.owner.getId());
		else
			lprTransItemInfo.setTransactionId(null);
		
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
		}
		else {
			lprTransItemInfo.setRequestOptions(new ArrayList<LprTransactionItemRequestOptionInfo>());
		}
		
		Set<LprTransactionItemResultValueGroupEntity> resultValueGroupsList = getResultValueGroups();
		
		if (resultValueGroupsList != null) {
			
			for (LprTransactionItemResultValueGroupEntity lprTransactionItemResultValueGroup : resultValueGroupsList) {
				lprTransItemInfo.getResultValuesGroupKeys().add(lprTransactionItemResultValueGroup.getResultValueGroupId());
			}
			
		}

		if (this.getResultingLprId() != null && this.getStatus() != null) {
			// only record the details if the values are not null
			LprTransactionItemResultInfo lprItemResult = new LprTransactionItemResultInfo();

			lprItemResult.setResultingLprId(this.getResultingLprId());
			lprItemResult.setStatus(new Boolean(
					"Y".equals(this.getStatus()) ? true : false));
			lprTransItemInfo.setLprTransactionItemResult(lprItemResult);
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Set<LprTransactionItemAttributeEntity> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<LprTransactionItemAttributeEntity> attributes) {
		this.attributes = attributes;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LprTransactionEntity getOwner() {
		return owner;
	}

	public void setOwner(LprTransactionEntity owner) {
		this.owner = owner;
	}

	public Set<LprTransactionItemRequestOptionEntity> getRequestOptions() {
		return requestOptions;
	}

	public void setRequestOptions(
			Set<LprTransactionItemRequestOptionEntity> requestOptions) {
		this.requestOptions = requestOptions;
	}

	public Set<LprTransactionItemResultValueGroupEntity> getResultValueGroups() {
		return resultValueGroups;
	}

	public void setResultValueGroups(
			Set<LprTransactionItemResultValueGroupEntity> resultValueGroups) {
		this.resultValueGroups = resultValueGroups;
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
		builder.append("]");
		return builder.toString();
	}
	
	

}
