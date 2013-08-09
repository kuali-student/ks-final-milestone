package org.kuali.student.r2.core.process.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.infc.Process;

@Entity
@Table(name = "KSEN_PROCESS")
@NamedQueries({
        @NamedQuery(name = "ProcessEntity.getByProcessTypeId",
                query = "select a from ProcessEntity a where a.processType=:processTypeId")
})

public class ProcessEntity extends MetaEntity implements AttributeOwner<ProcessAttributeEntity> {

    ////////////////////
    // DATA FIELDS
    ////////////////////

    @Column(name = "PROCESS_TYPE", nullable = false)
    private String processType;

    @Column(name = "PROCESS_STATE", nullable = false)
    private String processState;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;

	@Column(name = "OWNER_ORG_ID")
	private String ownerOrgID;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner",orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ProcessAttributeEntity> attributes;

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////

    public ProcessEntity() {}

	public ProcessEntity(Process process){
	    super(process);
        this.setId(process.getKey());
        this.setProcessType(process.getTypeKey());
        fromDTO(process);
	}

    public void fromDTO(Process process) {
        this.setProcessState(process.getStateKey());
        this.setName(process.getName());
        if (process.getDescr() != null) {
            this.setDescrFormatted(process.getDescr().getFormatted());
            this.setDescrPlain(process.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }
        this.setOwnerOrgID(process.getOwnerOrgId());
        this.setAttributes(new HashSet<ProcessAttributeEntity>());
            for (Attribute att : process.getAttributes()) {
            this.getAttributes().add(new ProcessAttributeEntity(att, this));
            }
        }

    /**
     * @return Process Information DTO
     */
    public ProcessInfo toDto(){
	    ProcessInfo processInfo = new ProcessInfo();
        processInfo.setMeta(super.toDTO());
        processInfo.setKey(getId());
        processInfo.setTypeKey(processType);
        processInfo.setStateKey(processState);
        processInfo.setName(name);
        processInfo.setDescr(new RichTextHelper().toRichTextInfo(descrPlain, descrFormatted));
        processInfo.setOwnerOrgId(getOwnerOrgID());
        List<AttributeInfo> attributes = processInfo.getAttributes();
        if (getAttributes() != null) {
            for (ProcessAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                attributes.add(attInfo);
            }
        }
        processInfo.setAttributes(attributes);
        return processInfo;
	}

    ///////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
        }

    public String getProcessState() {
        return processState;
        }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getName() {
        return name;
        }

    public void setName(String name) {
        this.name = name;
        }

    public String getDescrPlain() {
        return descrPlain;
	}

    public void setDescrPlain(String descrPlain) {
        this.descrPlain = descrPlain;
    }

    public String getDescrFormatted() {
        return descrFormatted;
    }

    public void setDescrFormatted(String descrFormatted) {
        this.descrFormatted = descrFormatted;
    }

    public String getOwnerOrgID() {
        return ownerOrgID;
    }

    public void setOwnerOrgID(String ownerOrgID) {
        this.ownerOrgID = ownerOrgID;
    }

	@Override
	public Set<ProcessAttributeEntity> getAttributes() {
		 return attributes;
	}

    @Override
	public void setAttributes(Set<ProcessAttributeEntity> attributes) {
		this.attributes = attributes;
	}
}