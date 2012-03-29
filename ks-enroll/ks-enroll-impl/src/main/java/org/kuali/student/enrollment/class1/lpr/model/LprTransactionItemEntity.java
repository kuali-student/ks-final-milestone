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
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemResultInfo;
import org.kuali.student.enrollment.lpr.infc.LprTransactionItem;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LPR_TRANS_ITEMS")
public class LprTransactionItemEntity extends MetaEntity implements AttributeOwner<LprTransItemAttributeEntity> {

    @Column(name = "PERSON_ID")
    private String personId;

    @Column(name = "NEW_LUI_ID")
    private String newLuiId;

    @Column(name = "EXIST_LUI_ID")
    private String existingLuiId;

    @Column(name = "RESULTING_LPR_ID")
    private String resultingLprId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "GROUP_ID")
    private String groupId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LprRichTextEntity descr;

    @Column(name = "TYPE_ID")
    private String lprTransactionItemType;

    @Column(name = "STATE_ID")
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
            this.setGroupId(lprTransactionItem.getGroupId());
            this.setLprTransactionItemState(lprTransactionItem.getStateKey());
            this.setAttributes(new ArrayList<LprTransItemAttributeEntity>());
            if (null != lprTransactionItem.getAttributes()) {
                for (Attribute att : lprTransactionItem.getAttributes()) {
                    LprTransItemAttributeEntity attEntity = new LprTransItemAttributeEntity(att);
                    this.getAttributes().add(attEntity);
                }
            }
            if (lprTransactionItem.getLprTransactionItemResult() != null) {
                this.setResultingLprId(lprTransactionItem.getLprTransactionItemResult().getResultingLprId());
                this.setStatus(lprTransactionItem.getLprTransactionItemResult().getStatus());
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
        if (this.getDescr() != null)
            lprTransItemInfo.setDescr(this.getDescr().toDto());
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
        lprItemResult.setStatus(this.getStatus());
        lprTransItemInfo.setGroupId(this.getGroupId());
        lprTransItemInfo.setLprTransactionItemResult(lprItemResult);
        return lprTransItemInfo;

    }

    public LprRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(LprRichTextEntity descr) {
        this.descr = descr;
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

    @Override
    public List<LprTransItemAttributeEntity> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<LprTransItemAttributeEntity> attributes) {
        this.attributes = attributes;
    }

}
