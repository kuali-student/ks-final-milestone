package org.kuali.student.r2.core.process.model;

import org.kuali.student.enrollment.class1.hold.model.IssueEntity;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.infc.Check;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "KSEN_CHECK")
public class CheckEntity extends MetaEntity implements AttributeOwner<CheckAttributeEntity>{

    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private CheckRichTextEntity descr;

	@ManyToOne(optional=false)
	@JoinColumn(name = "STATE_ID")
	private StateEntity checkState;

	@ManyToOne(optional=false)
	@JoinColumn(name = "TYPE_ID")
	private CheckTypeEntity checkType;

    private String agendaId;

    @ManyToOne(optional=false)
	@JoinColumn(name = "PROCESS_ID")
    private ProcessEntity process;

    private String milestoneTypeId;

    @ManyToOne(optional=false)
	@JoinColumn(name = "ISSUE_ID")
    private IssueEntity issue;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner",orphanRemoval = true)
    private List<CheckAttributeEntity> attributes;

    public CheckEntity(){

    }

	public CheckEntity(Check check){
	    super(check);

        this.setId(check.getKey());
        this.setName(check.getName());

        if(check.getDescr() != null) {
            this.setDescr(new CheckRichTextEntity(check.getDescr()));
        }

        this.setAgendaId(check.getAgendaId());
        this.setMilestoneTypeId(check.getMilestoneTypeKey());

        this.setAttributes(new ArrayList<CheckAttributeEntity>());
        if (check.getAttributes() != null) {
            for (Attribute att : check.getAttributes()) {
                CheckAttributeEntity checkEntity = new CheckAttributeEntity(att);
                this.getAttributes().add(checkEntity);
            }
        }

	}

    public CheckInfo toDto(){

	    CheckInfo obj = new CheckInfo();

        obj.setKey(getId());
    	obj.setName(name);
        obj.setAgendaId(getAgendaId());

        if (getProcess() != null){
            obj.setProcessKey(getProcess().getId());
        }

        obj.setMilestoneTypeKey(getMilestoneTypeId());

        if (getIssue() != null){
            obj.setIssueKey(getIssue().getId());
        }

        if (getCheckState() != null){
            obj.setStateKey(getCheckState().getId());
        }

        if (getCheckType() != null){
            obj.setTypeKey(getCheckType().getId());
        }

        obj.setMeta(super.toDTO());

        if (descr != null){
            obj.setDescr(descr.toDto());
        }

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (CheckAttributeEntity att : getAttributes()) {
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

	public CheckRichTextEntity getDescr() {
        return descr;
    }

	public void setDescr(CheckRichTextEntity descr) {
        this.descr = descr;
    }

	public StateEntity getCheckState() {
        return checkState;
    }
	public void setCheckState(StateEntity checkState) {
        this.checkState = checkState;
    }

    public IssueEntity getIssue() {
        return issue;
    }

    public void setIssue(IssueEntity issue) {
        this.issue = issue;
    }

    public CheckTypeEntity getCheckType() {
        return checkType;
    }

    public void setCheckType(CheckTypeEntity checkType) {
        this.checkType = checkType;
    }

    public String getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(String agendaId) {
        this.agendaId = agendaId;
    }

    public ProcessEntity getProcess() {
        return process;
    }

    public void setProcess(ProcessEntity process) {
        this.process = process;
    }

    public String getMilestoneTypeId() {
        return milestoneTypeId;
    }

    public void setMilestoneTypeId(String milestoneTypeId) {
        this.milestoneTypeId = milestoneTypeId;
    }

	@Override
	public List<CheckAttributeEntity> getAttributes() {
		 return attributes;
	}

    @Override
	public void setAttributes(List<CheckAttributeEntity> attributes) {
		this.attributes = attributes;
	}
}