package org.kuali.student.enrollment.class1.hold.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.enrollment.hold.dto.HoldInfo;
import org.kuali.student.enrollment.hold.infc.Hold;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.model.AttributeEntity;
import org.kuali.student.r2.common.model.StateEntity;

@Entity
@Table(name = "KSEN_HOLD")
public class HoldEntity extends MetaEntity implements AttributeOwner<AttributeEntity>{
    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private HoldRichTextEntity descr;   
   
    @ManyToOne(optional=false)
    @JoinColumn(name = "TYPE_ID")
    private HoldTypeEntity holdType;

    @ManyToOne(optional=false)
    @JoinColumn(name = "STATE_ID")
    private StateEntity holdState;
 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RELEASED_DT")
    private Date releasedDate;
    
    @Column(name="IS_WARNING")
    private boolean isWarning;
 
    @Column(name="IS_OVERRIDABLE")
    private boolean isOverridable;

    @ManyToOne(optional=false)
    @JoinColumn(name = "ISSUE_ID")
    private IssueEntity issue;
    
    @Column(name = "PERS_ID")
    private String personId;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<AttributeEntity> attributes;
    
	@Override
	public void setAttributes(List<AttributeEntity> attributes) {
		this.attributes = attributes;		
	}

	public HoldEntity(){}
	
    public HoldEntity(Hold hold){
        super(hold); 
        try {
	        this.setId(hold.getId());
	        this.setName(hold.getName());
	        if (hold.getEffectiveDate() != null)
	        	this.setEffectiveDate(hold.getEffectiveDate());
	        if (hold.getReleasedDate() != null)
	        	this.setReleasedDate(hold.getReleasedDate());
	        this.setWarning(hold.getIsWarning());
	        this.setOverridable(hold.getIsOverridable());
	        this.setPersonId(hold.getPersonId());
	        if(hold.getDescr() != null)
	            this.setDescr(new HoldRichTextEntity(hold.getDescr()));

	        this.setAttributes(new ArrayList<AttributeEntity>());
	        if (null != hold.getAttributes()) {
	            for (Attribute att : hold.getAttributes()) {
	            	AttributeEntity attEntity = new AttributeEntity(att);
	                this.getAttributes().add(attEntity);
	            }
	        }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public HoldInfo toDto() {
    	HoldInfo obj = new HoldInfo();
    	obj.setId(getId());
    	obj.setName(name);
        obj.setEffectiveDate(effectiveDate);
        obj.setReleasedDate(releasedDate);
        obj.setWarning(isWarning);
        obj.setOverridable(isOverridable);
        obj.setPersonId(personId);
        if(holdType != null)
            obj.setTypeKey(holdType.getId());
        if(holdState != null)
            obj.setStateKey(holdState.getId());
        if(issue != null)
        	obj.setIssueId(issue.getId());
        obj.setMeta(super.toDTO());
        if(descr != null)
            obj.setDescr(descr.toDto());

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (AttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);
        
        return obj;
    }


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HoldRichTextEntity getDescr() {
		return descr;
	}

	public void setDescr(HoldRichTextEntity descr) {
		this.descr = descr;
	}

	public HoldTypeEntity getHoldType() {
		return holdType;
	}

	public void setHoldType(HoldTypeEntity holdType) {
		this.holdType = holdType;
	}

	public StateEntity getHoldState() {
		return holdState;
	}

	public void setHoldState(StateEntity holdState) {
		this.holdState = holdState;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(Date releasedDate) {
		this.releasedDate = releasedDate;
	}

	public boolean isWarning() {
		return isWarning;
	}

	public void setWarning(boolean isWarning) {
		this.isWarning = isWarning;
	}

	public boolean isOverridable() {
		return isOverridable;
	}

	public void setOverridable(boolean isOverridable) {
		this.isOverridable = isOverridable;
	}

	public IssueEntity getIssue() {
		return issue;
	}

	public void setIssue(IssueEntity issue) {
		this.issue = issue;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Override
	public List<AttributeEntity> getAttributes() {
		 return attributes;
	}
}
