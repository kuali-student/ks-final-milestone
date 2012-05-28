package org.kuali.student.enrollment.class1.lpr.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.common.entity.RichText;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.infc.LprTransaction;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;

@Entity
@Table(name = "KSEN_LPR_TRANS")
public class LprTransactionEntity extends MetaEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "REQUESTING_PERS_ID")
    private String requestingPersonId;

    @Column(name = "ATP_ID")
    private String atpId;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;
    
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = false)
    private String descrPlain;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "LPR_TRANS_ID")
    private List<LprTransactionItemEntity> lprTransactionItems;

    @Column(name = "LPR_TRANS_TYPE")
    private String lprTransType;

    @Column(name = "LRP_TRANS_STATE")
    private String lprTransState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LprTransAttributeEntity> attributes;

    public LprTransactionEntity() {}

    public LprTransactionEntity(LprTransaction lprTransaction) {
        super(lprTransaction);
        this.setName(lprTransaction.getName());
        this.setRequestingPersonId(lprTransaction.getRequestingPersonId());
        this.requestingPersonId = lprTransaction.getAtpId();
        this.lprTransactionItems = new ArrayList<LprTransactionItemEntity>();
        this.setLprTransState(lprTransaction.getStateKey());
        this.setLprTransType(lprTransaction.getTypeKey());
        this.setId(lprTransaction.getId());
        if (lprTransaction.getDescr() != null) {
            this.setDescrFormatted(lprTransaction.getDescr().getFormatted());
            this.setDescrPlain(lprTransaction.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }
        
        this.setAttributes(new ArrayList<LprTransAttributeEntity>());
        
        if (null != lprTransaction.getAttributes()) {
            for (Attribute att : lprTransaction.getAttributes()) {
                this.getAttributes().add(new LprTransAttributeEntity(att));

            }
        }

    }

    public LprTransactionInfo toDto() {

        LprTransactionInfo lpr = new LprTransactionInfo();
        lpr.setId(getId());

        if (this.getLprTransType() != null)
            lpr.setTypeKey(this.getLprTransType());
        if (this.getLprTransState() != null)
            lpr.setStateKey(this.getLprTransState());
        lpr.setMeta(super.toDTO());
        
       
        if (getAttributes() != null) {
            List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
            for (LprTransAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                atts.add(attInfo);
            }
            lpr.setAttributes(atts);
        }
        
        lpr.setDescr(new RichTextHelper().toRichTextInfo(getDescrPlain(), getDescrFormatted()));

        lpr.setName(getName());
        lpr.setRequestingPersonId(getRequestingPersonId());
        lpr.setAtpId(getAtpId());        
        List<LprTransactionItemInfo> lprItemsInfo = new ArrayList<LprTransactionItemInfo>();
        if (lprTransactionItems != null) {
            for (LprTransactionItemEntity lprItemEntity : lprTransactionItems) {
                lprItemsInfo.add(lprItemEntity.toDto());
            }
        }
        lpr.setLprTransactionItems(lprItemsInfo);
        return lpr;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLprTransType() {
        return lprTransType;
    }

    public void setLprTransType(String lprTransType) {
        this.lprTransType = lprTransType;
    }

    public String getLprTransState() {
        return lprTransState;
    }

    public void setLprTransState(String lprTransState) {
        this.lprTransState = lprTransState;
    }

    public String getRequestingPersonId() {
        return requestingPersonId;
    }

    public void setRequestingPersonId(String requestingPersonId) {
        this.requestingPersonId = requestingPersonId;
    }

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }
    
    

    public List<LprTransactionItemEntity> getLprTransactionItems() {
        return lprTransactionItems;
    }

    public void setLprTransactionItems(List<LprTransactionItemEntity> lprTransactionItems) {
        this.lprTransactionItems = lprTransactionItems;
    }

    public void setAttributes(List<LprTransAttributeEntity> attributes) {
       this.attributes = attributes;
    }

    public List<LprTransAttributeEntity> getAttributes() {
        return this.attributes;
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

}
