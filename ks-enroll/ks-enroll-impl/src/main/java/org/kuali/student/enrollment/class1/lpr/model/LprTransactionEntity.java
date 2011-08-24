package org.kuali.student.enrollment.class1.lpr.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.infc.LPRTransactionItem;
import org.kuali.student.enrollment.lpr.infc.LprTransaction;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;

@Entity
@Table(name = "KSEN_LPR_TRANS")
public class LprTransactionEntity extends MetaEntity implements AttributeOwner<LprTransAttributeEntity> {

    @Column(name = "REQ_PERSON_ID")
    private String requestingPersonId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LprRichTextEntity descr;

    @ElementCollection
    @CollectionTable(name = "KSEN_LPR_TRANS_ITEMS", joinColumns = @JoinColumn(name = "Id"))
    @Column(name = "LPR_TRANS_ITEMS")
    private Set<LprTransactionItemEntity> lprTransactionItems;

    @ManyToOne(optional = false)
    @JoinColumn(name = "LPR_TYPE_ID")
    private LuiPersonRelationTypeEntity lprTransType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "LPR_STATE_ID")
    private LuiPersonRelationStateEntity lprTransState;

    public LprTransactionEntity() {}

    public LprTransactionEntity(LprTransaction lprTransaction) {
        super(lprTransaction);
        this.requestingPersonId = lprTransaction.getRequestingPersonId();
        this.lprTransactionItems = new HashSet<LprTransactionItemEntity>();
        for (LPRTransactionItem lprTransItem : lprTransaction.getLprTransactionItems()) {
            this.lprTransactionItems.add(new LprTransactionItemEntity(lprTransItem));
        }

    }

    public LprTransactionInfo toDto() {

        LprTransactionInfo lpr = new LprTransactionInfo();
        lpr.setId(getId());

        if (lprTransType != null)
            lpr.setTypeKey(lprTransType.getId());
        if (lprTransState != null)
            lpr.setStateKey(lprTransState.getId());
        lpr.setMeta(super.toDTO());
        if (descr != null)
            lpr.setDescr(descr.toDto());

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LprTransAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        lpr.setAttributes(atts);

        return lpr;

    }

    public LprRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(LprRichTextEntity descr) {
        this.descr = descr;
    }

    public LuiPersonRelationTypeEntity getLprTransType() {
        return lprTransType;
    }

    public void setLprTransType(LuiPersonRelationTypeEntity lprTransType) {
        this.lprTransType = lprTransType;
    }

    public LuiPersonRelationStateEntity getLprTransState() {
        return lprTransState;
    }

    public void setLprTransState(LuiPersonRelationStateEntity lprTransState) {
        this.lprTransState = lprTransState;
    }

    public String getRequestingPersonId() {
        return requestingPersonId;
    }

    public void setRequestingPersonId(String requestingPersonId) {
        this.requestingPersonId = requestingPersonId;
    }

    public Set<LprTransactionItemEntity> getLprTransactionItems() {
        return lprTransactionItems;
    }

    public void setLprTransactionItems(Set<LprTransactionItemEntity> lprTransactionItems) {
        this.lprTransactionItems = lprTransactionItems;
    }

    @Override
    public void setAttributes(List<LprTransAttributeEntity> attributes) {

    }

    @Override
    public List<LprTransAttributeEntity> getAttributes() {
        return null;
    }

}
