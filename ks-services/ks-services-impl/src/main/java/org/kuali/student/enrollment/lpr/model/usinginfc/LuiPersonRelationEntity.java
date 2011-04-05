package org.kuali.student.enrollment.lpr.model.usinginfc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.dto.AttributeInfo;
import org.kuali.student.common.infc.AttributeInfc;
import org.kuali.student.common.infc.MetaInfc;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;

/**
 * @author Igor
 */
@Entity
public class LuiPersonRelationEntity implements LuiPersonRelationInfc, Serializable {

    private static final long serialVersionUID = -8711908979901134064L;
    @Id
    @GeneratedValue
    private Long id;
    private String personId;
    private String luiId;
    @Temporal(TemporalType.DATE)
    private Date effectiveDate;
    @Temporal(TemporalType.DATE)
    private Date expirationDate;
    @OneToMany
    @JoinColumn(name = "lui_person_relation_id")
    private List<AttributeInfc> attributes;
    private String type;
    private String state;

    public LuiPersonRelationEntity(LuiPersonRelationInfc lprInfo) {
    	this.id = Long.valueOf(lprInfo.getId());
    	this.personId = lprInfo.getPersonId();
    	this.luiId = lprInfo.getLuiId();
    	this.effectiveDate = new Date(lprInfo.getEffectiveDate().getTime());
    	this.expirationDate = new Date(lprInfo.getExpirationDate().getTime());
    	this.attributes = new ArrayList<AttributeInfc>(lprInfo.getAttributes());
    	this.type = lprInfo.getType();
    	this.state = lprInfo.getState();
	}

    public LuiPersonRelationEntity() {
	}

	public LuiPersonRelationInfc toDto() {
    	return new LuiPersonRelationInfo.Builder(this).build();
    }
    
    public static LuiPersonRelationEntity fromDto(LuiPersonRelationInfc lprInfo) {
    	return new LuiPersonRelationEntity(lprInfo);
    }
    
	@Override
    public String getId() {
        return "" + id;
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    @Override
    public String getLuiId() {
        return luiId;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getState() {
        return this.state;
    }

    @Override
    public List<AttributeInfc> getAttributes() {
        if (this.attributes == null) {
            return null;
        }
        List<AttributeInfc> list = new ArrayList<AttributeInfc>(attributes.size());
        for (AttributeInfc dae : this.attributes) {
            list.add(new AttributeInfo.Builder().id(dae.getId()).key(dae.getKey()).value(dae.getValue()).build());
        }
        return list;
    }

    @Override
    public MetaInfc getMetaInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setId(Long id) {
		this.id = id;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public void setLuiId(String luiId) {
		this.luiId = luiId;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setAttributes(List<AttributeInfc> attributes) {
		this.attributes = attributes;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setState(String state) {
		this.state = state;
	}
}
