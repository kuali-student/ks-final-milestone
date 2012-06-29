package org.kuali.student.enrollment.class1.hold.model;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.infc.Hold;

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
    @NamedQuery(name = "HoldEntity.getIdsByType",
    query = "select id from HoldEntity where holdType = :type"),
    @NamedQuery(name = "HoldEntity.getIdsByIssue",
    query = "select H.id from HoldEntity H where H.holdIssue.id = :issueId"),
    @NamedQuery(name = "HoldEntity.getByPerson",
    query = "select H from HoldEntity H where H.personId = :personId"),
    @NamedQuery(name = "HoldEntity.getByPersonAndState",
    query = "select H from HoldEntity H where H.personId = :personId and h.holdState = :stateKey"),
    @NamedQuery(name = "HoldEntity.getByIssueAndPerson",
    query = "select H from HoldEntity H where H.holdIssue.id = :issueId and H.personId = :personId"),
    @NamedQuery(name = "HoldEntity.getByIssuePersonAndState",
    query = "select H from HoldEntity H where H.holdIssue.id = :issueId and H.personId = :personId and h.holdState = :stateKey")
})
public class HoldEntity
        extends MetaEntity
        implements AttributeOwner<HoldAttributeEntity> {

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
    private final Set<HoldAttributeEntity> attributes = new HashSet<HoldAttributeEntity>();

    @Override
    public void setAttributes(Set<HoldAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    public HoldEntity() {
    }

    public HoldEntity(Hold hold) {
        super(hold);
        this.setId(hold.getId());
        this.personId = hold.getPersonId();
        // issue an an entity so it gets set in the service
//        this.holdIssue = hold.getIssueId();
        this.holdType = hold.getTypeKey();
        this.fromDto(hold);
    }

    public void fromDto(Hold dto) {
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
            HoldAttributeEntity attEntity = new HoldAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
    }

    public HoldInfo toDto() {
        HoldInfo info = new HoldInfo();
        info.setId(getId());
        info.setName(name);
        info.setEffectiveDate(effectiveDate);
        info.setReleasedDate(releasedDate);
        info.setPersonId(personId);
        info.setTypeKey(holdType);
        info.setStateKey(holdState);
        if (holdIssue != null) {
            info.setIssueId(holdIssue.getId());
        }
        info.setDescr(new RichTextHelper().toRichTextInfo(getDescrPlain(), getDescrFormatted()));

        info.setMeta(super.toDTO());
        for (HoldAttributeEntity att : getAttributes()) {
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
    public Set<HoldAttributeEntity> getAttributes() {
        return attributes;
    }
}
