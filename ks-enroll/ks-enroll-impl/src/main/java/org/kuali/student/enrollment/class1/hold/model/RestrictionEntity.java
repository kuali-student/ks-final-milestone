package org.kuali.student.enrollment.class1.hold.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.model.AttributeEntity;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpRichTextEntity;

@Entity
@Table(name = "KSEN_RESTRICTION")
public class RestrictionEntity extends MetaEntity implements AttributeOwner<AttributeEntity>{
    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private AtpRichTextEntity descr;   
   
    //TODO:use common TypeEntity
//    @ManyToOne(optional=false)
//    @JoinColumn(name = "TYPE_ID")
//    private TypeEntity restrictionType;
    @Column(name = "TYPE_ID")
    String restrictionType;

    @ManyToOne(optional=false)
    @JoinColumn(name = "STATE_ID")
    private StateEntity restrictionState;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AttributeEntity> attributes;

	public String getRestrictionType() {
		return restrictionType;
	}

	public void setRestrictionType(String restrictionType) {
		this.restrictionType = restrictionType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AtpRichTextEntity getDescr() {
		return descr;
	}

	public void setDescr(AtpRichTextEntity descr) {
		this.descr = descr;
	}

	public StateEntity getRestrictionState() {
		return restrictionState;
	}

	public void setRestrictionState(StateEntity restrictionState) {
		this.restrictionState = restrictionState;
	}

	@Override
	public void setAttributes(List<AttributeEntity> attributes) {
		this.attributes = attributes;		
	}

	@Override
	public List<AttributeEntity> getAttributes() {
		 return attributes;
	}

}
