package org.kuali.student.r2.core.process.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.kuali.rice.kim.impl.identity.PersonImpl;
import org.kuali.student.enrollment.class1.hold.model.IssueEntity;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpTypeEntity;
import org.kuali.student.r2.core.class1.atp.model.MilestoneEntity;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.infc.Check;

@Entity
@Table(name = "KSEN_CHECK")
public class CheckEntity extends MetaEntity implements AttributeOwner<CheckAttributeEntity>{

    /*
X    ID VARCHAR2(255),
X    OBJ_ID VARCHAR2(36),
X    VER_NBR NUMBER(19,0),
X    CREATEID VARCHAR2(255),
X    CREATETIME TIMESTAMP (6),
X    UPDATEID VARCHAR2(255),
X    UPDATETIME TIMESTAMP (6),
>    NAME VARCHAR2(255),
>    RT_DESCR_ID VARCHAR2(255),
>    STATE_ID VARCHAR2(255),
>    TYPE_ID VARCHAR2(255),
>    ISSUE_ID VARCHAR2(255),
>    MILESTONE_ID  VARCHAR2(255),
>    AGENDA_ID   VARCHAR2(255),
>    PROCESS_ID  VARCHAR2(255)
    */

    //NAME
    @Column(name = "NAME")
    private String name;

    //RT_DESCR_ID
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private CheckRichTextEntity descr;

	//STATE_ID
	@ManyToOne(optional=false)
	@JoinColumn(name = "STATE_ID")
	private StateEntity checkState;

    //TYPE_ID
	@ManyToOne(optional=false)
	@JoinColumn(name = "TYPE_ID")
	private CheckTypeEntity checkType;

	@ManyToOne(optional=false)
	@JoinColumn(name = "ISSUE_ID")
	private IssueEntity IssueType;

	@ManyToOne(optional=false)
	@JoinColumn(name = "MILESTONE_TYPE_ID")
	private MilestoneEntity milestoneType;

    /* TODO
     * How should Agenda be filled out here?  What type?
     * Couldn't find what I thought would be appropriate.
     */
	//@ManyToOne(optional=false)
	//@JoinColumn(name = "AGENDA_ID")
	//private Agenda...? agendaType;

	@ManyToOne(optional=false)
	@JoinColumn(name = "PROCESS_ID")
    private ProcessEntity process;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CheckAttributeEntity> attributes;

	@Override
	public void setAttributes(List<CheckAttributeEntity> attributes) {
		this.attributes = attributes;
	}

    public CheckEntity() {}
	public CheckEntity(Check check){
	    super(check);
        try {
	        this.setId(check.getKey());
	        this.setName(check.getName());
	        if(check.getDescr() != null) {
	            this.setDescr(new CheckRichTextEntity(check.getDescr()));
			}

	        this.setAttributes(new ArrayList<CheckAttributeEntity>());
	        if (null != check.getAttributes()) {
	            for (Attribute att : check.getAttributes()) {
	            	CheckAttributeEntity attEntity = new CheckAttributeEntity(att);
	                this.getAttributes().add(attEntity);
	            }
	        }
        } catch (Exception e){
            e.printStackTrace();
        }
	}

    /**
     * @return Process Information DTO
     */
    public CheckInfo toDto(){
        CheckInfo obj = new CheckInfo();
    	obj.setKey(getId());
    	obj.setName(name);
        if (checkType != null)
            obj.setTypeKey(checkType.getId());
        if (checkState != null)
            obj.setStateKey(checkState.getId());
        obj.setMeta(super.toDTO());
        if (descr != null)
            obj.setDescr(descr.toDto());

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (CheckAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);

        return obj;
	}

	// NAME
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	// RT_DESCR_ID
	public CheckRichTextEntity getDescr() { return descr; }
	public void setDescr(CheckRichTextEntity descr) { this.descr = descr; }

	//PROCESS_ID
	public StateEntity getCheckStateID() {
        return checkState;
    }
	public void setCheckStateID(StateEntity checkState) {
        this.checkState = checkState;
    }

	//PROCESS_TYPE_ID
	public CheckTypeEntity getCheckTypeID() {
        return checkType;
    }
	public void setCheckTypeID(CheckTypeEntity checkType) {
        this.checkType = checkType;
    }

    public StateEntity getCheckState() {
        return checkState;
    }

    public void setCheckState(StateEntity checkState) {
        this.checkState = checkState;
    }

    public CheckTypeEntity getCheckType() {
        return checkType;
    }

    public void setCheckType(CheckTypeEntity checkType) {
        this.checkType = checkType;
    }

    public IssueEntity getIssueType() {
        return IssueType;
    }

    public void setIssueType(IssueEntity issueType) {
        IssueType = issueType;
    }

    public MilestoneEntity getMilestoneType() {
        return milestoneType;
    }

    public void setMilestoneType(MilestoneEntity milestoneType) {
        this.milestoneType = milestoneType;
    }

    public ProcessEntity getProcess() {
        return process;
    }

    public void setProcess(ProcessEntity process) {
        this.process = process;
    }

    @Override
	public List<CheckAttributeEntity> getAttributes() {
		 return attributes;
	}
}