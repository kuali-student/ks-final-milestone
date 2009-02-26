package org.kuali.student.lum.lu.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name="KS_REQ_COMP_T")
public class ReqComponent extends MetaEntity {
	@Id
	@Column(name = "ID")
	private String id;
	
    @Column(name="DESCRIPTION")
    private String desc;

    @Column(name="STATE")
    private String state;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFFECTIVE_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRATION_DT")
    private Date expirationDate;    

    @ManyToOne
    @JoinColumn(name="REQ_COMP_TYPE_ID")
    private ReqComponentType requiredComponentType;
    
    @OneToMany
    @JoinTable(name = "KS_REQ_COMP_REQ_COMP_FIELD_T", joinColumns = @JoinColumn(name = "REQ_COMP_FIELD_ID"), inverseJoinColumns = @JoinColumn(name = "REQ_COMP_ID"))
    private List<ReqComponentField> reqCompField;
    
    /**
     * AutoGenerate the Id
     */
    @Override
    public void onPrePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ReqComponentType getRequiredComponentType() {
		return requiredComponentType;
	}

	public void setRequiredComponentType(ReqComponentType requiredComponentType) {
		this.requiredComponentType = requiredComponentType;
	}

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public List<ReqComponentField> getReqCompField() {
        return reqCompField;
    }

    public void setReqCompField(List<ReqComponentField> reqCompField) {
        this.reqCompField = reqCompField;
    }

}
