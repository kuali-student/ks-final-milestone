package org.kuali.student.enrollment.class1.lui.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.infc.LuiIdentifier;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LUI_IDENT")
public class LuiIdentifierEntity extends MetaEntity implements AttributeOwner<LuiIdentifierAttributeEntity>{
	    
   @Column(name = "CD")
    private String code;

    @Column(name = "SHRT_NAME")
    private String shortName;

    @Column(name = "LNG_NAME")
    private String longName;

    @Column(name = "DIVISION")
    private String division;

    @Column(name = "VARTN")
    private String variation;

    @Column(name = "SUFX_CD")
    private String suffixCode;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "ST")
    private String state;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LuiIdentifierAttributeEntity> attributes;
    
    public LuiIdentifierEntity(){}
    
    public LuiIdentifierEntity(LuiIdentifier luiIdentifier){
    	super(luiIdentifier);
    	try{
    		this.setId(luiIdentifier.getId());
    		this.setCode(luiIdentifier.getCode());
    		this.setDivision(luiIdentifier.getDivision());
    		this.setLongName(luiIdentifier.getLongName());
    		this.setShortName(luiIdentifier.getShortName());
    		this.setState(luiIdentifier.getStateKey());
    		this.setSuffixCode(luiIdentifier.getSuffixCode());
    		this.setType(luiIdentifier.getTypeKey());
    		this.setVariation(luiIdentifier.getVariation());
    		
	        this.setAttributes(new ArrayList<LuiIdentifierAttributeEntity>());
	        if (null != luiIdentifier.getAttributes()) {
	            for (Attribute att : luiIdentifier.getAttributes()) {
	            	LuiIdentifierAttributeEntity attEntity = new LuiIdentifierAttributeEntity(att);
	                this.getAttributes().add(attEntity);
	            }
	        }
    	} catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public LuiIdentifierInfo toDto() {
    	LuiIdentifierInfo obj = new LuiIdentifierInfo();
    	obj.setId(getId());
    	obj.setCode(code);
    	obj.setDivision(division);
    	obj.setLongName(longName);
		obj.setShortName(shortName);
		obj.setStateKey(state);
		obj.setSuffixCode(suffixCode);
		obj.setTypeKey(type);
		obj.setVariation(variation);
        obj.setMeta(super.toDTO());
        
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LuiIdentifierAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);
        
        return obj;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getVariation() {
		return variation;
	}

	public void setVariation(String variation) {
		this.variation = variation;
	}

	public String getSuffixCode() {
		return suffixCode;
	}

	public void setSuffixCode(String suffixCode) {
		this.suffixCode = suffixCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public void setAttributes(List<LuiIdentifierAttributeEntity> attributes) {
		this.attributes = attributes;
		
	}

	@Override
	public List<LuiIdentifierAttributeEntity> getAttributes() {
		return attributes;
	}

}
