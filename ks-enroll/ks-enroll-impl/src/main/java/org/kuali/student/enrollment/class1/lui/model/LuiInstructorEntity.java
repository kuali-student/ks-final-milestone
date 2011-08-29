package org.kuali.student.enrollment.class1.lui.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.infc.OfferingInstructor;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
@Deprecated
@Entity
@Table(name = "KSEN_LUI_INSTR")
public class LuiInstructorEntity extends MetaEntity implements AttributeOwner<LuiAttributeEntity>{
    @Column(name = "ORG_ID")
    private String orgId;

    @Column(name = "PERS_ID")
    private String personId;
    
    @Column(name = "PERS_OVRID")
    private String personInfoOverride;
    
    @Column(name = "PERCT_EFFT")
    private Float percentageEffort;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LuiAttributeEntity> attributes;

    public LuiInstructorEntity(){}
    
    public LuiInstructorEntity(OfferingInstructor luiInstructor){
    	super(luiInstructor);
    	try{
    		this.setId(luiInstructor.getId());
    		this.setPersonId(luiInstructor.getPersonId());
    		this.setPercentageEffort(luiInstructor.getPercentageEffort());
    		
	        this.setAttributes(new ArrayList<LuiAttributeEntity>());
	        if (null != luiInstructor.getAttributes()) {
	            for (Attribute att : luiInstructor.getAttributes()) {
	            	LuiAttributeEntity attEntity = new LuiAttributeEntity(att);
	                this.getAttributes().add(attEntity);
	            }
	        }
    	 } catch (Exception e){
             e.printStackTrace();
         }
    }
    
    public OfferingInstructorInfo toDto(){
    	OfferingInstructorInfo obj = new OfferingInstructorInfo();
    	obj.setId(getId());
    	obj.setPersonId(personId);
    	obj.setPercentageEffort(percentageEffort);
    	obj.setMeta(super.toDTO());
    	
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LuiAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);
        
        return obj;
    }
    
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonInfoOverride() {
		return personInfoOverride;
	}

	public void setPersonInfoOverride(String personInfoOverride) {
		this.personInfoOverride = personInfoOverride;
	}

	public Float getPercentageEffort() {
		return percentageEffort;
	}

	public void setPercentageEffort(Float percentageEffort) {
		this.percentageEffort = percentageEffort;
	}

	@Override
	public void setAttributes(List<LuiAttributeEntity> attributes) {
		this.attributes = attributes;			
	}

	@Override
	public List<LuiAttributeEntity> getAttributes() {
		return attributes;
	}

}
