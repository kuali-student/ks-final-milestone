package org.kuali.student.enrollment.class1.lpr.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.enrollment.lpr.infc.LPRTransactionItem;
import org.kuali.student.lum.lu.dto.ResultOptionInfo;
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "LPR_TRANS_ID")
    private LprTransactionEntity lprTransactionEntity;

    @JoinColumn(name = "TYPE_ID")
    private LuiPersonRelationTypeEntity lprTransactionType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "STATE_ID")
    private LuiPersonRelationStateEntity lprTransactionState;

    @Embedded
    private List<ResultOptionEntity> resultOptions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private final List<LprTransItemAttributeEntity> attributes = new ArrayList<LprTransItemAttributeEntity>();

    public LprTransactionItemEntity() {}

    public LprTransactionItemEntity(LPRTransactionItem lprTransactionItem) {

        super(lprTransactionItem);
        this.newLuiId = lprTransactionItem.getNewLuiId();
        this.existingLuiId = lprTransactionItem.getExistingLuiId();

        if (null != lprTransactionItem.getResultOptions()) {
            for (ResultOptionInfo resultOption : lprTransactionItem.getResultOptions()) {
                ResultOptionEntity resultOptionEntity = new ResultOptionEntity(resultOption);
                this.getResultOptions().add(resultOptionEntity);
            }
        }


        this.setAttributes(new ArrayList<LprTransItemAttributeEntity>());
        if (null != lprTransactionItem.getAttributes()) {
            for (Attribute att : lprTransactionItem.getAttributes()) {
                LprTransItemAttributeEntity attEntity = new LprTransItemAttributeEntity(att);
                this.getAttributes().add(attEntity);
            }
        }

    }

    public List<ResultOptionEntity> getResultOptions() {
        return resultOptions;
    }

    public void setResultOptions(List<ResultOptionEntity> resultOptions) {
        this.resultOptions = resultOptions;
    }

    public LprTransactionEntity getLprTransactionEntity() {
        return lprTransactionEntity;
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

    public LuiPersonRelationTypeEntity getLprTransactionType() {
        return lprTransactionType;
    }

    public void setLprTransactionType(LuiPersonRelationTypeEntity lprTransactionType) {
        this.lprTransactionType = lprTransactionType;
    }

    public LuiPersonRelationStateEntity getLprTransactionState() {
        return lprTransactionState;
    }

    public void setLprTransactionState(LuiPersonRelationStateEntity lprTransactionState) {
        this.lprTransactionState = lprTransactionState;
    }

    @Override
    public void setAttributes(List<LprTransItemAttributeEntity> attributes) {

    }

    public void setLprTransactionEntity(LprTransactionEntity lprTransactionEntity) {
        this.lprTransactionEntity = lprTransactionEntity;
    }

    @Override
    public List<LprTransItemAttributeEntity> getAttributes() {
        return null;
    }

}
