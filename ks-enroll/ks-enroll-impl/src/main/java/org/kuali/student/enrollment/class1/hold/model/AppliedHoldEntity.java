package org.kuali.student.enrollment.class1.hold.model;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.infc.AppliedHold;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.kuali.student.r2.common.util.RichTextHelper;

@Entity
@Table(name = "KSEN_HOLD")
@NamedQueries({
    @NamedQuery(name = "AppliedHoldEntity.getIdsByType",
    query = "select id from AppliedHoldEntity where holdType = :type"),
    @NamedQuery(name = "AppliedHoldEntity.getIdsByIssue",
    query = "select AH.id from AppliedHoldEntity AH where AH.holdIssue.id = :holdIssueId"),
    @NamedQuery(name = "AppliedHoldEntity.getByPerson",
    query = "select AH from AppliedHoldEntity AH where AH.personId = :personId"),
    @NamedQuery(name = "AppliedHoldEntity.getByPersonAndState",
    query = "select AH from AppliedHoldEntity AH where AH.personId = :personId and AH.holdState = :stateKey"),
    @NamedQuery(name = "AppliedHoldEntity.getByIssueAndPerson",
    query = "select AH from AppliedHoldEntity AH where AH.holdIssue.id = :holdIssueId and AH.personId = :personId"),
    @NamedQuery(name = "AppliedHoldEntity.getByIssuePersonAndState",
    query = "select AH from AppliedHoldEntity AH where AH.holdIssue.id = :holdIssueId and AH.personId = :personId and AH.holdState = :stateKey")
})
public class AppliedHoldEntity
        extends MetaEntity
        implements AttributeOwner<AppliedHoldAttributeEntity> {

    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = false)
    private String descrPlain;
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;
    @Column(name = "HOLD_STATE")
    private String holdState;
    @Column(name = "HOLD_TYPE")
    private String holdType;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RELEASED_DT")
    private Date releasedDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "ISSUE_ID")
    private HoldIssueEntity holdIssue;
    @Column(name = "PERS_ID")
    private String personId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<AppliedHoldAttributeEntity> attributes = new HashSet<AppliedHoldAttributeEntity>();

    @Override
    public void setAttributes(Set<AppliedHoldAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    public AppliedHoldEntity() {
    }

    public AppliedHoldEntity(AppliedHold hold) {
        super(hold);
        this.setId(hold.getId());
        this.personId = hold.getPersonId();
        // issue an an entity so it gets set in the service
//        this.holdIssue = hold.getIssueId();
        this.holdType = hold.getTypeKey();
        this.fromDto(hold);
    }

    public void fromDto(AppliedHold dto) {
        this.setName(dto.getName());
        this.setHoldState(dto.getStateKey());
        if (dto.getDescr() != null) {
            this.setDescrFormatted(dto.getDescr().getFormatted());
            this.setDescrPlain(dto.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }
        this.setEffectiveDate(dto.getEffectiveDate());
        this.setReleasedDate(dto.getReleasedDate());

        // dynamic attributes
        this.attributes.clear();
        for (Attribute att : dto.getAttributes()) {
            AppliedHoldAttributeEntity attEntity = new AppliedHoldAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
    }

    public AppliedHoldInfo toDto() {
        AppliedHoldInfo info = new AppliedHoldInfo();
        info.setId(getId());
        info.setName(name);
        info.setEffectiveDate(effectiveDate);
        info.setReleasedDate(releasedDate);
        info.setPersonId(personId);
        info.setTypeKey(holdType);
        info.setStateKey(holdState);
        if (holdIssue != null) {
            info.setHoldIssueId(holdIssue.getId());
        }
        info.setDescr(new RichTextHelper().toRichTextInfo(getDescrPlain(), getDescrFormatted()));

        info.setMeta(super.toDTO());
        for (AppliedHoldAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            info.getAttributes().add(attInfo);
        }
        return info;
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

    public String getDescrFormatted() {
        return descrFormatted;
    }

    public void setDescrPlain(String plain) {
        this.descrPlain = plain;
    }

    public void setDescrFormatted(String formatted) {
        this.descrFormatted = formatted;
    }

    public String getHoldType() {
        return holdType;
    }

    public void setHoldType(String holdType) {
        this.holdType = holdType;
    }

    public String getHoldState() {
        return holdState;
    }

    public void setHoldState(String holdState) {
        this.holdState = holdState;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
    }

    public HoldIssueEntity getHoldIssue() {
        return holdIssue;
    }

    public void setHoldIssue(HoldIssueEntity issue) {
        this.holdIssue = issue;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public Set<AppliedHoldAttributeEntity> getAttributes() {
        return attributes;
    }
}
