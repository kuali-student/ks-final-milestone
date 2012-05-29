package org.kuali.student.enrollment.class1.lpr.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.infc.Lpr;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.helper.EntityMergeHelper;
import org.kuali.student.r2.common.helper.EntityMergeHelper.EntityMergeOptions;
import org.kuali.student.r2.common.helper.EntityMergeHelper.EntityMergeResult;
import org.kuali.student.r2.common.helper.EntityMergeHelper.StringMergeOptions;
import org.kuali.student.r2.common.infc.Attribute;

/**
 * @author Igor
 */
@Entity
@Table(name = "KSEN_LPR")
public class LprEntity extends MetaEntity {

	@Column(name = "PERS_ID")
	private String personId;

	@Column(name = "LUI_ID")
	private String luiId;

	@Column(name = "COMMIT_PERCT")
	private BigDecimal commitmentPercent;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@Column(name = "LPR_TYPE")
	private String personRelationTypeId;

	@Column(name = "LPR_STATE")
	private String personRelationStateId;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LprAttributeEntity> attributes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "lpr")
	private List<LprResultValueGroupEntity> resultValueGroups;

	public LprEntity() {
	}

	public LprEntity(Lpr dto) {
		super(dto);
		// These are the read-only fields
		this.setId(dto.getId());
		this.setLuiId(dto.getLuiId());
		this.setPersonId(dto.getPersonId());
		this.setPersonRelationTypeId(dto.getTypeKey());
		fromDto(dto);
	}

	
	public List<Object> fromDto(Lpr dto) {

		List<Object> orphanData = new ArrayList<Object>();

		this.setCommitmentPercent(new BigDecimal(dto.getCommitmentPercent()));
		this.setExpirationDate(dto.getExpirationDate());
		this.setEffectiveDate(dto.getEffectiveDate());
		this.setPersonRelationStateId(dto.getStateKey());

		EntityMergeHelper<LprAttributeEntity, Attribute> attributeMergeHelper = new EntityMergeHelper<LprAttributeEntity, Attribute>();

		EntityMergeResult<LprAttributeEntity> attributeMergeResult = attributeMergeHelper
				.merge(this.attributes,
						(List<Attribute>) dto.getAttributes(),
						new EntityMergeOptions<LprAttributeEntity, Attribute>() {

							@Override
							public String getEntityId(LprAttributeEntity entity) {
								return entity.getId();
							}

							@Override
							public String getInfoId(Attribute info) {
								return info.getId();
							}

							@Override
							public List<Object> merge(
									LprAttributeEntity entity, Attribute info) {

								entity.fromDto(info); 
								
								entity.setOwner(LprEntity.this);

								return new ArrayList<Object>();
							}

							@Override
							public LprAttributeEntity create(Attribute info) {
								LprAttributeEntity entity = new LprAttributeEntity(info);
							
								entity.setOwner(LprEntity.this);
								
								return entity;
							}

						});

		this.attributes = attributeMergeResult.getMergedList();

		orphanData.addAll(attributeMergeResult.getOrphanList());

		EntityMergeHelper<LprResultValueGroupEntity, String> resultValueGroupMergeHelper = new EntityMergeHelper<LprResultValueGroupEntity, String>();

		EntityMergeResult<LprResultValueGroupEntity> resultValueGroupMergeResult = resultValueGroupMergeHelper
				.mergeStringList(this.resultValueGroups,
						dto.getResultValuesGroupKeys(),
						new StringMergeOptions<LprResultValueGroupEntity>() {

							@Override
							public String getKey(
									LprResultValueGroupEntity entity) {
								return entity.getResultValueGroupId();
							}

							@Override
							public LprResultValueGroupEntity create(String value) {

								LprResultValueGroupEntity entity = new LprResultValueGroupEntity();

								entity.setResultValueGroupId(value);

								entity.setLpr(LprEntity.this);

								return entity;
							}

						});

		this.resultValueGroups = resultValueGroupMergeResult.getMergedList();

		orphanData.addAll(resultValueGroupMergeResult.getOrphanList());

		return orphanData;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getLuiId() {
		return luiId;
	}

	public void setLuiId(String luiId) {
		this.luiId = luiId;
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

	public String getPersonRelationTypeId() {
		return personRelationTypeId;
	}

	public void setPersonRelationTypeId(String personRelationTypeId) {
		this.personRelationTypeId = personRelationTypeId;
	}

	public String getPersonRelationStateId() {
		return personRelationStateId;
	}

	public void setPersonRelationStateId(String personRelationStateId) {
		this.personRelationStateId = personRelationStateId;
	}

	public List<LprAttributeEntity> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<LprAttributeEntity> attributes) {
		this.attributes = attributes;
	}

	public LprInfo toDto() {
		LprInfo lprInfo = new LprInfo();
		lprInfo.setId(getId());
		lprInfo.setLuiId(luiId);
		lprInfo.setCommitmentPercent("" + commitmentPercent);
		lprInfo.setPersonId(personId);
		lprInfo.setEffectiveDate(effectiveDate);
		lprInfo.setExpirationDate(expirationDate);
		lprInfo.setTypeKey(personRelationTypeId);
		lprInfo.setStateKey(personRelationStateId);

		// instead need to create a new JPA entity to hold the lpr to rvg
		// mapping
		List<String> rvGroupIds = new ArrayList<String>();
		if (null != getResultValueGroups()) {
			for (LprResultValueGroupEntity rvGroup : getResultValueGroups()) {
				rvGroupIds.add(rvGroup.getResultValueGroupId());
			}
		}
		lprInfo.setResultValuesGroupKeys(rvGroupIds);

		lprInfo.setMeta(super.toDTO());
		List<AttributeInfo> atts = lprInfo.getAttributes();
		if (getAttributes() != null) {
			for (LprAttributeEntity att : getAttributes()) {
				AttributeInfo attInfo = att.toDto();
				atts.add(attInfo);
			}
		}

		return lprInfo;
	}

	public List<LprResultValueGroupEntity> getResultValueGroups() {
		return resultValueGroups;
	}

	public void setResultValueGroups(
			List<LprResultValueGroupEntity> resultValueGroups) {
		this.resultValueGroups = resultValueGroups;
	}

	public BigDecimal getCommitmentPercent() {
		return commitmentPercent;
	}

	public void setCommitmentPercent(BigDecimal commitmentPercent) {
		this.commitmentPercent = commitmentPercent;
	}

}
