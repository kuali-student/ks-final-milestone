package org.kuali.student.enrollment.class1.lpr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.enrollment.class1.lrc.model.ResultValuesGroupEntity;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.infc.Lpr;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;

/**
 * @author Igor
 */
@Entity
@Table(name = "KSEN_LPR")
public class LprEntity extends MetaEntity  {

    @Column(name = "PERS_ID")
    private String personId;

    @Column(name = "LUI_ID")
    private String luiId;

    @Column(name = "COMMIT_PERCT")
    private Float commitmentPercent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXPIR_DT")
    private Date expirationDate;

    @Column(name = "LPR_TYPE")
    private String personRelationTypeId;

    @Column(name = "LPR_STATE")
    private String personRelationStateId;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "owner" )
    private List<LprAttributeEntity> attributes;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy="lpr")
    private List<LprResultValueGroupEntity>resultValueGroups;
    
    public LprEntity() {}

    public LprEntity(Lpr dto) {
        super(dto);
        // These are the read-only fields
        this.setId(dto.getId());
        this.setLuiId(dto.getLuiId());
        this.setPersonId(dto.getPersonId());
        this.setPersonRelationTypeId(dto.getTypeKey());
        fromDto(dto);
    }

    public void fromDto(Lpr dto){
        this.setCommitmentPercent(Float.parseFloat(dto.getCommitmentPercent()));
        this.setExpirationDate(dto.getExpirationDate());
        this.setEffectiveDate(dto.getEffectiveDate());
        this.setPersonRelationStateId(dto.getStateKey());
        this.setAttributes(new ArrayList<LprAttributeEntity>());
        if (null != dto.getAttributes()) {
            for (Attribute att : dto.getAttributes()) {
                this.getAttributes().add(new LprAttributeEntity(att));
            }
        }
        
        this.setResultValueGroups(new ArrayList<LprResultValueGroupEntity>());
        
        if (null != dto.getResultValuesGroupKeys()) {
        	for (String key : dto.getResultValuesGroupKeys()) {
				LprResultValueGroupEntity rvg = new LprResultValueGroupEntity();
				
				rvg.setResultValueGroupId(key);
				
				rvg.setLpr(this);
				
				this.getResultValueGroups().add(rvg);
			}
        }
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

    public Float getCommitmentPercent() {
        return commitmentPercent;
    }

    public void setCommitmentPercent(Float commitmentPercent) {
        this.commitmentPercent = commitmentPercent;
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

        // instead need to create a new JPA entity to hold the lpr to rvg mapping
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
    
    
}
