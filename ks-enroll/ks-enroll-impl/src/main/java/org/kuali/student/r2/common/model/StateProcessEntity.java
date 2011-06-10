package org.kuali.student.r2.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.StateProcess;

@Entity
@Table(name = "KSEN_STATE_PROCESS")
public class StateProcessEntity extends MetaEntity{
	@Column(name="NAME")
    private String name;

    @Column(name="DESCR")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<StateAttributeEntity> attributes;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<StateAttributeEntity> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<StateAttributeEntity> attributes) {
		this.attributes = attributes;
	}
	
	public StateProcessEntity(){}

	public StateProcessEntity(StateProcess process){
		super();
		try{
			this.setId(process.getKey());
			this.setName(process.getName());
			this.setDescription(process.getDescr());
			this.setVersionNumber((long) 0);
			this.setEffectiveDate(process.getEffectiveDate());
	        this.setExpirationDate(process.getExpirationDate());
			this.setAttributes(new ArrayList<StateAttributeEntity>());
			if(null != process.getAttributes()){
				for (Attribute att : process.getAttributes()) {
					StateAttributeEntity attEntity = new StateAttributeEntity(att);
		        	attEntity.setVersionNumber((long) 0);
		            this.getAttributes().add(attEntity);
		        }				
			}
		} catch (Exception e){
            e.printStackTrace();
        }		
	}
	
	public StateProcessInfo toDto(){
		StateProcessInfo process = StateProcessInfo.newInstance();
		process.setKey(getId());
		process.setName(name);
		process.setDescr(description);
		process.setEffectiveDate(effectiveDate);
		process.setExpirationDate(expirationDate);
		
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (StateAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        process.setAttributes(atts);
        
        return process;
	}
}
