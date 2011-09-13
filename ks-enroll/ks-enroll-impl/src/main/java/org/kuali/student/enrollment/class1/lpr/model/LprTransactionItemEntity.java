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

import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.infc.LPRTransactionItem;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.model.StateEntity;

@Entity
@Table(name = "KSEN_LPR_TRANS_ITEMS")
public class LprTransactionItemEntity extends MetaEntity implements AttributeOwner<LprTransItemAttributeEntity> {

    @Column(name = "PERSON_ID")
    private String personId;

    @Column(name = "NEW_LUI_ID")
    private String newLuiId;

    @Column(name = "EXIST_LUI_ID")
    private String existingLuiId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LprRichTextEntity descr;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TYPE_ID")
    private LuiPersonRelationTypeEntity lprTransactionItemType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "STATE_ID")
    private StateEntity lprTransactionItemState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LprTransItemAttributeEntity> attributes;

    public LprTransactionItemEntity() {}

    public LprTransactionItemEntity(LPRTransactionItem lprTransactionItem) {

        super(lprTransactionItem);
        if (lprTransactionItem != null) {
            this.setId(lprTransactionItem.getId());
            this.newLuiId = lprTransactionItem.getNewLuiId();
            this.existingLuiId = lprTransactionItem.getExistingLuiId();

            this.setAttributes(new ArrayList<LprTransItemAttributeEntity>());
            if (null != lprTransactionItem.getAttributes()) {
                for (Attribute att : lprTransactionItem.getAttributes()) {
                    LprTransItemAttributeEntity attEntity = new LprTransItemAttributeEntity(att);
                    this.getAttributes().add(attEntity);
                }
            }
        }

    }

    public LprTransactionItemInfo toDto() {

        LprTransactionItemInfo lprTransItemInfo = new LprTransactionItemInfo();
        lprTransItemInfo.setId(getId());

        if (lprTransactionItemType != null)
            lprTransItemInfo.setTypeKey(lprTransactionItemType.getId());
        if (lprTransactionItemState != null)
            lprTransItemInfo.setStateKey(lprTransactionItemState.getId());
        lprTransItemInfo.setExistingLuiId(existingLuiId);
        lprTransItemInfo.setNewLuiId(newLuiId);
        lprTransItemInfo.setPersonId(personId);
        lprTransItemInfo.setMeta(super.toDTO());
        if (descr != null)
            lprTransItemInfo.setDescr(descr.toDto());
        if (getAttributes() != null) {
            List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
            for (LprTransItemAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                atts.add(attInfo);
            }
            lprTransItemInfo.setAttributes(atts);
        }
        return lprTransItemInfo;

    }

    public LprRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(LprRichTextEntity descr) {
        this.descr = descr;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getNewLuiId() {
        return newLuiId;
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

    public LuiPersonRelationTypeEntity getLprTransactionItemType() {
        return lprTransactionItemType;
    }

    public void setLprTransactionItemType(LuiPersonRelationTypeEntity lprTransactionType) {
        this.lprTransactionItemType = lprTransactionType;
    }

    public StateEntity getLprTransactionItemState() {
        return lprTransactionItemState;
    }

    public void setLprTransactionItemState(StateEntity lprTransactionState) {
        this.lprTransactionItemState = lprTransactionState;
    }

    @Override
    public List<LprTransItemAttributeEntity> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<LprTransItemAttributeEntity> attributes) {
        this.attributes = attributes;
    }

}
