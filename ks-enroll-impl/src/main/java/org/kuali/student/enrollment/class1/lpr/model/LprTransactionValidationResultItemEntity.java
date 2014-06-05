package org.kuali.student.enrollment.class1.lpr.model;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_LPR_TRANS_ITEM_VR")
public class LprTransactionValidationResultItemEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name="LPR_TRANS_ITEM_ID")
    private LprTransactionItemEntity owner;

    @Column(name = "ELEMENT")
    private String element;

    @Enumerated(EnumType.STRING)
    @Column(name="ERROR_LEVEL_CD")
    private ValidationResult.ErrorLevel level;

    @Column(name = "VR_MESSAGE")
    private String message;

    public LprTransactionValidationResultItemEntity() {
        super();
    }

    public LprTransactionValidationResultItemEntity(ValidationResultInfo validationResultInfo, LprTransactionItemEntity owner) {
        this();
        fromDto(validationResultInfo);
        this.owner = owner;
    }

    @PrePersist
    public void prePersist() {
        //Auto generate the object id, and auto generate the ID if it's not set
        if (this.id == null) {
            this.id = UUIDHelper.genStringUUID(this.id);
        }
    }

    public void fromDto(ValidationResultInfo validationResultInfo){
        this.element = validationResultInfo.getElement();
        this.level = validationResultInfo.getLevel();
        this.message = validationResultInfo.getMessage();
    }

    public ValidationResultInfo toDto(){
        ValidationResultInfo validationResultInfo = new ValidationResultInfo();
        validationResultInfo.setElement(this.element);
        validationResultInfo.setLevel(this.level);
        validationResultInfo.setMessage(this.message);
        return validationResultInfo;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public ValidationResult.ErrorLevel getLevel() {
        return level;
    }

    public void setLevel(ValidationResult.ErrorLevel level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LprTransactionItemEntity getOwner() {return owner;}

    public void setOwner(LprTransactionItemEntity owner) {this.owner = owner;}
}
