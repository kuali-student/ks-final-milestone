package org.kuali.student.enrollment.class1.lpr.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionOfElements;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.infc.LPRTransactionItem;
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "TYPE_ID")
    private LuiPersonRelationTypeEntity lprTransactionType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "STATE_ID")
    private StateEntity lprTransactionState;

    @CollectionOfElements
    private Set<String> resutOptionIds = new HashSet<String>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LprTransItemAttributeEntity> attributes;

    public LprTransactionItemEntity() {}

    public LprTransactionItemEntity(LPRTransactionItem lprTransactionItem) {

        super(lprTransactionItem);
        if (lprTransactionItem != null) {
            this.setId(lprTransactionItem.getId());
            this.newLuiId = lprTransactionItem.getNewLuiId();
            this.existingLuiId = lprTransactionItem.getExistingLuiId();

            if (null != lprTransactionItem.getResultOptionIds()) {
                resutOptionIds.addAll(lprTransactionItem.getResultOptionIds());
            }

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
        return new LprTransactionItemInfo();
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

    public StateEntity getLprTransactionState() {
        return lprTransactionState;
    }

    public void setLprTransactionState(StateEntity lprTransactionState) {
        this.lprTransactionState = lprTransactionState;
    }

    public Set<String> getResutOptionIds() {
        return resutOptionIds;
    }

    public void setResutOptionIds(Set<String> resutOptionIds) {
        this.resutOptionIds = resutOptionIds;
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
