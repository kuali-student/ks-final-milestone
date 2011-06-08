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
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.State;

@Entity
@Table(name = "KSEN_COMM_STATE")
public class StateEntity extends MetaEntity {
	@Column(name="NAME")
    private String name;

    @Column(name="DESCR")
    private String description;
    
    @Column(name="PROCESS_KEY")
    private String processKey;

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

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
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

	public StateEntity(){}

	public StateEntity(State state){
		super();
		try{
			this.setId(state.getKey());
			this.setName(state.getName());
			this.setDescription(state.getDescr());
			this.setVersionNumber((long) 0);
			this.setAttributes(new ArrayList<StateAttributeEntity>());
			if(null != state.getAttributes()){
				for (Attribute att : state.getAttributes()) {
					StateAttributeEntity attEntity = new StateAttributeEntity(att);
		        	attEntity.setVersionNumber((long) 0);
		            this.getAttributes().add(attEntity);
		        }				
			}
		} catch (Exception e){
            e.printStackTrace();
        }		
	}
	
	public StateInfo toDto(){
		StateInfo state = StateInfo.newInstance();
		state.setKey(getId());
		state.setName(name);
		state.setDescr(description);
		state.setEffectiveDate(effectiveDate);
		state.setExpirationDate(expirationDate);
		
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (StateAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        state.setAttributes(atts);
        
        return state;
	}
}
