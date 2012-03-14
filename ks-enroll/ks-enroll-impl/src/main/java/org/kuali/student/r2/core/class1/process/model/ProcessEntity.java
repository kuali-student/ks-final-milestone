package org.kuali.student.r2.core.class1.process.model;

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
import org.kuali.student.r2.core.class1.state.model.StateEntity;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.infc.Process;

@Entity
@Table(name = "KSEN_PROCESS")
public class ProcessEntity extends MetaEntity implements AttributeOwner<ProcessAttributeEntity>{

    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private ProcessRichTextEntity descr;

	@Column(name = "PROCESS_STATE")
	private String processState;

	@Column(name = "PROCESS_TYPE")
	private String processType;

	@Column(name = "OWNER_ORG_ID")
	private String ownerOrgID;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner",orphanRemoval = true)
    private List<ProcessAttributeEntity> attributes;

    public ProcessEntity(){

    }

	public ProcessEntity(Process process){
	    super(process);

        this.setId(process.getKey());
        this.setName(process.getName());

        if(process.getDescr() != null) {
            this.setDescr(new ProcessRichTextEntity(process.getDescr()));
        }
        this.setProcessType (process.getTypeKey());
        this.setProcessState(process.getStateKey());
        this.setOwnerOrgID(process.getOwnerOrgId());

        this.setAttributes(new ArrayList<ProcessAttributeEntity>());
        if (null != process.getAttributes()) {
            for (Attribute att : process.getAttributes()) {
                ProcessAttributeEntity attEntity = new ProcessAttributeEntity(att);
                this.getAttributes().add(attEntity);
            }
        }

	}

    /**
     * @return Process Information DTO
     */
    public ProcessInfo toDto(){

	    ProcessInfo obj = new ProcessInfo();

        obj.setKey(getId());
    	obj.setName(name);
        obj.setOwnerOrgId(getOwnerOrgID());

        if (processType != null){
            obj.setTypeKey(processType);
        }

        if (processState != null){
            obj.setStateKey(processState);
        }

        obj.setMeta(super.toDTO());

        if (descr != null){
            obj.setDescr(descr.toDto());
        }

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (ProcessAttributeEntity att : getAttributes()) {
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
	public ProcessRichTextEntity getDescr() { return descr; }
	public void setDescr(ProcessRichTextEntity descr) { this.descr = descr; }

	//PROCESS_STATE_ID
	public String getProcessState() {
        return processState;
    }
	public void setProcessState(String processState) {
        this.processState = processState;
    }

	//PROCESS_TYPE_ID
	public String getProcessType() {
        return processType;
    }
	public void setProcessType(String processType) {
        this.processType = processType;
    }

	//OWNER_ORG_ID
	public String getOwnerOrgID() { return ownerOrgID; }
	public void setOwnerOrgID(String ownerOrgID) { this.ownerOrgID = ownerOrgID; }

	@Override
	public List<ProcessAttributeEntity> getAttributes() {
		 return attributes;
	}

    @Override
	public void setAttributes(List<ProcessAttributeEntity> attributes) {
		this.attributes = attributes;
	}
}