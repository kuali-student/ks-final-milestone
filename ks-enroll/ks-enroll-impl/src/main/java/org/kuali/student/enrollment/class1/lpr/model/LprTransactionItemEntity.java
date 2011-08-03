package org.kuali.student.enrollment.class1.lpr.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.enrollment.lpr.dto.RequestOptionInfo;
import org.kuali.student.lum.lu.dto.ResultOptionInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;

@Entity
@Table(name = "KSLP_LPR_TRANS")
public class LprTransactionItemEntity extends MetaEntity implements AttributeOwner<LprTransactionAttributeEntity> {

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
    private LuiPersonRelationStateEntity lprTransactionState;
    
    // TODO - In LRR impl ?
    // @ManyToOne(optional = false)
    //private List<ResultOptionEntity> resultOptions;
    
    @OneToMany
    @JoinColumn(name = "ID")
    private List<RequestOptionEntity> requestOptions;

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
    public void setAttributes(List<LprTransactionAttributeEntity> attributes) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS

    }

    @Override
    public List<LprTransactionAttributeEntity> getAttributes() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
