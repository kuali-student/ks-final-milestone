package org.kuali.student.enrollment.class1.lui.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.lum.lu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.lu.infc.LuCode;

@Entity
@Table(name = "KSEN_LUI_LUCD")
public class LuCodeEntity extends MetaEntity implements AttributeOwner<LuCodeAttributeEntity>{
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RT_DESCR_ID")
	private LuiRichTextEntity descr;   

	@Column(name = "VALUE")
	private String value;

	@Column(name = "TYPE")
	private String type;

	@ManyToOne
	@JoinColumn(name="LUI_ID")
	private LuiEntity lui;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LuCodeAttributeEntity> attributes;

    public LuCodeEntity(){}
    
    public LuCodeEntity(LuCode luCode){
    	super(luCode);
    	try{
    		this.setId(luCode.getId());
    		this.setValue(luCode.getValue());
    		this.setType(luCode.getTypeKey());
    		if(luCode.getDescr() != null)
    			this.setDescr(new LuiRichTextEntity(luCode.getDescr()));
    		
	        this.setAttributes(new ArrayList<LuCodeAttributeEntity>());
	        if (null != luCode.getAttributes()) {
	            for (Attribute att : luCode.getAttributes()) {
	            	LuCodeAttributeEntity attEntity = new LuCodeAttributeEntity(att);
	                this.getAttributes().add(attEntity);
	            }
	        }
    	} catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public LuCodeInfo toDto() {
    	LuCodeInfo obj = new LuCodeInfo();
    	obj.setId(getId());
    	obj.setTypeKey(type);
    	obj.setValue(value);
        if(descr != null)
            obj.setDescr(descr.toDto());
        
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LuCodeAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);
        
        return obj;
    }

	public LuiRichTextEntity getDescr() {
		return descr;
	}

	public void setDescr(LuiRichTextEntity descr) {
		this.descr = descr;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LuiEntity getLui() {
		return lui;
	}

	public void setLui(LuiEntity lui) {
		this.lui = lui;
	}

	@Override
	public void setAttributes(List<LuCodeAttributeEntity> attributes) {
		this.attributes = attributes;
		
	}

	@Override
	public List<LuCodeAttributeEntity> getAttributes() {
		return attributes;
	}

}
