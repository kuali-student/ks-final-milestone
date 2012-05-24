package org.kuali.student.enrollment.class1.lpr.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemResultInfo;
import org.kuali.student.enrollment.lpr.infc.LprTransactionItem;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;

@Entity
@Table(name = "KSEN_LPR_TRANS_ITEM")
public class LprTransactionItemEntity extends MetaEntity {

    @Column(name = "PERS_ID")
    private String personId;

    @Column(name = "NEW_LUI_ID")
    private String newLuiId;

    @Column(name = "EXISTING_LUI_ID")
    private String existingLuiId;

    @Column(name = "LTI_RESULTING_LPR_ID")
    private String resultingLprId;

    @Column(name="LTI_RESULT_MESSAGE")
    private String resultMessage;
    
    @Column(name = "LTI_RESULTING_STATUS")
    private String status;

    @Column(name = "GROUP_ID")
    private String groupId;

    @Column (name="NAME")
    private String name;
    
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;
    
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = false)
    private String descrPlain;
    
    @Column(name = "LPR_TRANS_ITEM_TYPE")
    private String lprTransactionItemType;

    @Column(name = "LPR_TRANS_ITEM_STATE")
    private String lprTransactionItemState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LprTransItemAttributeEntity> attributes;
    
    

    public LprTransactionItemEntity() {}

    public LprTransactionItemEntity(LprTransactionItem lprTransactionItem) {

        super(lprTransactionItem);
        if (lprTransactionItem != null) {
            this.setId(lprTransactionItem.getId());
            this.setNewLuiId(lprTransactionItem.getNewLuiId());
            this.setExistingLuiId(lprTransactionItem.getExistingLuiId());
            this.setPersonId(lprTransactionItem.getPersonId());
            this.setLprTransactionItemState(lprTransactionItem.getStateKey());
            this.setAttributes(new ArrayList<LprTransItemAttributeEntity>());
            if (null != lprTransactionItem.getAttributes()) {
                for (Attribute att : lprTransactionItem.getAttributes()) {
                    LprTransItemAttributeEntity attEntity = new LprTransItemAttributeEntity(att);
                    this.getAttributes().add(attEntity);
                }
            }
            if (lprTransactionItem.getDescr() != null) {
                this.setDescrFormatted(lprTransactionItem.getDescr().getFormatted());
                this.setDescrPlain(lprTransactionItem.getDescr().getPlain());
            } else {
                this.setDescrFormatted(null);
                this.setDescrPlain(null);
            }
            if (lprTransactionItem.getLprTransactionItemResult() != null) {
                this.setResultingLprId(lprTransactionItem.getLprTransactionItemResult().getResultingLprId());
                this.setStatus(lprTransactionItem.getLprTransactionItemResult().getStatus()?"Y":"N");
            }
        }

    }

    public LprTransactionItemInfo toDto() {

        LprTransactionItemInfo lprTransItemInfo = new LprTransactionItemInfo();
        lprTransItemInfo.setId(getId());

        lprTransItemInfo.setTypeKey(this.getLprTransactionItemType());
        lprTransItemInfo.setStateKey(this.getLprTransactionItemState());
        lprTransItemInfo.setExistingLuiId(this.getExistingLuiId());
        lprTransItemInfo.setNewLuiId(this.getNewLuiId());
        lprTransItemInfo.setPersonId(this.getPersonId());
        lprTransItemInfo.setMeta(super.toDTO());
        lprTransItemInfo.setDescr(new RichTextHelper().toRichTextInfo(getDescrPlain(), getDescrFormatted()));

        if (getAttributes() != null) {
            List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
            for (LprTransItemAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                atts.add(attInfo);
            }
            lprTransItemInfo.setAttributes(atts);
        }

        LprTransactionItemResultInfo lprItemResult = new LprTransactionItemResultInfo();
        
        
        lprItemResult.setResultingLprId(this.getResultingLprId());
        lprItemResult.setStatus(new Boolean("Y".equals(this.getStatus())?true:false));
        lprTransItemInfo.setLprTransactionItemResult(lprItemResult);
        return lprTransItemInfo;

    }

   

    public String getDescrFormatted() {
		return descrFormatted;
	}

	public void setDescrFormatted(String descrFormatted) {
		this.descrFormatted = descrFormatted;
	}

	public String getDescrPlain() {
		return descrPlain;
	}

	public void setDescrPlain(String descrPlain) {
		this.descrPlain = descrPlain;
	}

	public String getResultingLprId() {
        return resultingLprId;
    }

    public void setResultingLprId(String resultingLprId) {
        this.resultingLprId = resultingLprId;
    }

    public String getPersonId() {
        return personId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getNewLuiId() {
        return newLuiId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNewLuiId(String newLuiId) {
        this.newLuiId = newLuiId;
    }

    public String getExistingLuiId() {
        return existingLuiId;
    }

    public void setExistingLuiId(String existingLuiId) {
        this.existingLuiId = existingLuiId;
    }

    public String getLprTransactionItemType() {
        return lprTransactionItemType;
    }

    public void setLprTransactionItemType(String lprTransactionType) {
        this.lprTransactionItemType = lprTransactionType;
    }

    public String getLprTransactionItemState() {
        return lprTransactionItemState;
    }

    public void setLprTransactionItemState(String lprTransactionState) {
        this.lprTransactionItemState = lprTransactionState;
    }

    public List<LprTransItemAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<LprTransItemAttributeEntity> attributes) {
        this.attributes = attributes;
    }

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    
}
