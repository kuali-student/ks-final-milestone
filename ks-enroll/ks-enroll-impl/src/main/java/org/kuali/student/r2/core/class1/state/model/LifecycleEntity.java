package org.kuali.student.r2.core.class1.state.model;

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
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.state.infc.Lifecycle;

@Entity
@Table(name = "KSEN_STATE_PROCESS")
public class LifecycleEntity extends MetaEntity implements AttributeOwner<StateAttributeEntity> {
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
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
	
	public LifecycleEntity(){}

	public LifecycleEntity(Lifecycle lifecycle){
		super();
		try{
			this.setId(lifecycle.getKey());
			this.setName(lifecycle.getName());
                        // TODO: change this entity to handle a rich text description
//			this.setDescription(lifecycle.getDescr());
			this.setVersionNumber((long) 0);
//			this.setEffectiveDate(lifecycle.getEffectiveDate());
//	                this.setExpirationDate(lifecycle.getExpirationDate());
			this.setAttributes(new ArrayList<StateAttributeEntity>());
			if(null != lifecycle.getAttributes()){
				for (Attribute att : lifecycle.getAttributes()) {
					StateAttributeEntity attEntity = new StateAttributeEntity(att);
		            this.getAttributes().add(attEntity);
		        }				
			}
		} catch (Exception e){
            e.printStackTrace();
        }		
	}
	
	public LifecycleInfo toDto(){
		LifecycleInfo lifecycle = new LifecycleInfo ();
		lifecycle.setKey(getId());
		lifecycle.setName(name);
                // TODO: make this entity handle rich text descriptions
		lifecycle.setDescr(new RichTextHelper ().fromPlain(description));
//		lifecycle.setEffectiveDate(effectiveDate);
//		lifecycle.setExpirationDate(expirationDate);
		
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (StateAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        lifecycle.setAttributes(atts);
        
        return lifecycle;
	}
}
