package org.kuali.student.r2.core.process.model;

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
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.model.StateEntity;
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

	//PROCESS_STATE_ID
	@ManyToOne(optional=false)
	@JoinColumn(name = "STATE_ID")
	private StateEntity processStateID;

	//PROCESS_TYPE_ID
	@ManyToOne(optional=false)
	@JoinColumn(name = "PROCESS_TYPE_ID")
	private ProcessTypeEntity processTypeID;

	//OWNER_ORG_ID
	private String ownerOrgID;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ProcessAttributeEntity> attributes;

	@Override
	public void setAttributes(List<ProcessAttributeEntity> attributes) {
		this.attributes = attributes;
	}

	public ProcessEntity(Process process){
	    super(process);
        try {
            /** TODO
             * getId , do we need to create this as a getter/setter since it doesn't
             * exist in this context?
             * @return
             */
	        //this.setId(process.getId());   <---- FIX THIS
	        this.setName(process.getName());
	        if(process.getDescr() != null) {
	            this.setDescr(new ProcessRichTextEntity(process.getDescr()));
			}

	        this.setAttributes(new ArrayList<ProcessAttributeEntity>());
	        if (null != process.getAttributes()) {
	            for (Attribute att : process.getAttributes()) {
	            	ProcessAttributeEntity attEntity = new ProcessAttributeEntity(att);
	                this.getAttributes().add(attEntity);
	            }
	        }
        } catch (Exception e){
            e.printStackTrace();
        }
	}

    /** TODO
     * Clean up the toDto function
     * Deal with the issues of
     * @return
     */
    public ProcessInfo toDto(){
	    ProcessInfo obj = new ProcessInfo();
    	//obj.setId(getId());   <---- FIX THIS: Only OwnerOrgID exists as getter/setters
    	obj.setName(name);
        if (processTypeID != null)
            obj.setTypeKey(processTypeID.getId());
        if (processStateID != null)
            obj.setStateKey(processStateID.getId());
        obj.setMeta(super.toDTO());
        if (descr != null)
            obj.setDescr(descr.toDto());

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (ProcessAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);

        return obj;
	}

	//ID
	//OBJ_ID

	//VER_NBR
	private int versionNumber;

	// NAME
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	// RT_DESCR_ID
	public ProcessRichTextEntity getDescr() { return descr; }
	public void setDescr(ProcessRichTextEntity descr) { this.descr = descr; }

	//PROCESS_STATE_ID
	public StateEntity getProcessStateID() {
        return processStateID;
    }
	public void setProcessStateID(StateEntity processStateID) {
        this.processStateID = processStateID;
    }

	//PROCESS_TYPE_ID
	public ProcessTypeEntity getProcessTypeID() {
        return processTypeID;
    }
	public void setProcessTypeID(ProcessTypeEntity processTypeID) {
        this.processTypeID = processTypeID;
    }

	//OWNER_ORG_ID
	public String getOwnerOrgID() { return ownerOrgID; }
	public void setOwnerOrgID(String ownerOrgID) { this.ownerOrgID = ownerOrgID; }

	@Override
	public List<ProcessAttributeEntity> getAttributes() {
		 return attributes;
	}
}