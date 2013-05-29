package org.kuali.student.r2.core.process.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.infc.Check;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "KSEN_PROCESS_CHECK")
@NamedQueries({
        @NamedQuery(name = "CheckEntity.getByCheckType",
                query = "select a from CheckEntity a where a.checkType=:checkType"),
        @NamedQuery(name = "CheckEntity.getByName",
                query = "select a from CheckEntity a where a.name=:name")
})

public class CheckEntity extends MetaEntity implements AttributeOwner<CheckAttributeEntity> {

    ////////////////////
    // DATA FIELDS
    ////////////////////

    @Column(name = "PROCESS_CHECK_TYPE", nullable = false)
    private String checkType;

    @Column(name = "PROCESS_CHECK_STATE", nullable = false)
    private String checkState;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;

    @Column(name = "ISSUE_ID")
    private String issueId;

    @Column(name = "MSTONE_TYPE")
    private String milestoneType;

    @Column(name = "AGENDA_ID")
    private String agendaId;

    @Column(name = "RIGHT_AGENDA_ID")
    private String rightAgendaId;

    @Column(name = "LEFT_AGENDA_ID")
    private String leftAgendaId;

    // NOTE: in the database this is an @ManyToOne but we want to sidestep JPA
    // loading a lot of nested data so we just store the id and manage the fetch
    // of the process data separately.
    @Column(name = "CHILD_PROCESS_ID")
    private String childProcessId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private final Set<CheckAttributeEntity> attributes = new HashSet<CheckAttributeEntity>();

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////

    public CheckEntity(Check check) {
        super(check);
        this.setId(check.getId());
        this.setCheckType(check.getTypeKey());
        this.fromDTO(check);
    }

    public CheckEntity() {
        super();
    }

    public void fromDTO(Check check) {
        this.setCheckState(check.getStateKey());
        this.setName(check.getName());
        if (check.getDescr() != null) {
            this.setDescrFormatted(check.getDescr().getFormatted());
            this.setDescrPlain(check.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }
        this.setIssueId(check.getHoldIssueId());
        this.setMilestoneType(check.getMilestoneTypeKey());
        this.setAgendaId(check.getAgendaId());
        this.setRightAgendaId(check.getRightComparisonValue());
        this.setLeftAgendaId(check.getLeftComparisonAgendaId());
        this.setChildProcessId(check.getChildProcessKey());

        this.attributes.clear();

        for (Attribute att : check.getAttributes()) {
            this.attributes.add(new CheckAttributeEntity(att, this));
        }

    }

    /**
     * @return Process Check DTO
     */
    public CheckInfo toDto() {
        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setMeta(super.toDTO());
        checkInfo.setId(getId());
        checkInfo.setTypeKey(checkType);
        checkInfo.setStateKey(checkState);
        checkInfo.setDescr(new RichTextHelper().toRichTextInfo(descrPlain, descrFormatted));
        checkInfo.setHoldIssueId(issueId);
        checkInfo.setMilestoneTypeKey(milestoneType);
        checkInfo.setAgendaId(agendaId);
        checkInfo.setRightComparisonAgendaId(rightAgendaId);
        checkInfo.setLeftComparisonAgendaId(leftAgendaId);
        checkInfo.setChildProcessKey(childProcessId);
        checkInfo.setName(this.getName());
        List<AttributeInfo> dtoAttributes = checkInfo.getAttributes();
        dtoAttributes.clear();
        List<AttributeInfo> attributes = checkInfo.getAttributes();
        if (getAttributes() != null) {
            for (CheckAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                attributes.add(attInfo);
            }
        }
        checkInfo.setAttributes(attributes);

        return checkInfo;
    }

    ///////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
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

    public void setDescrPlain(String descrPlain) {
        this.descrPlain = descrPlain;
    }

    public String getDescrFormatted() {
        return descrFormatted;
    }

    public void setDescrFormatted(String descrFormatted) {
        this.descrFormatted = descrFormatted;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getMilestoneType() {
        return milestoneType;
    }

    public void setMilestoneType(String milestoneType) {
        this.milestoneType = milestoneType;
    }

    public String getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(String agendaId) {
        this.agendaId = agendaId;
    }

    public String getRightAgendaId() {
        return rightAgendaId;
    }

    public void setRightAgendaId(String rightAgendaId) {
        this.rightAgendaId = rightAgendaId;
    }

    public String getLeftAgendaId() {
        return leftAgendaId;
    }

    public void setLeftAgendaId(String leftAgendaId) {
        this.leftAgendaId = leftAgendaId;
    }

    public String getChildProcessId() {
        return childProcessId;
    }

    public void setChildProcessId(String childProcessId) {
        this.childProcessId = childProcessId;
    }

    @Override
    public Set<CheckAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<CheckAttributeEntity> attributes) {

        this.attributes.clear();

        if (attributes != null)
            this.attributes.addAll(attributes);
    }

    ///////////////////////
    // FUNCTIONALS
    ///////////////////////

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("CheckEntity {");
        builder.append("id='" + getId() + '\'');
        builder.append(", checkType='" + checkType + '\'');
        builder.append(", checkState='" + checkState + '\'');
        builder.append(", name='" + name + '\'');
        builder.append(", descrPlain='" + descrPlain + '\'');
        builder.append(", descrFormatted='" + descrFormatted + '\'');
        builder.append(", issueId='" + issueId + '\'');
        builder.append(", milestoneType='" + milestoneType + '\'');
        builder.append(", agendaId='" + agendaId + '\'');
        builder.append(", rightAgendaId='" + rightAgendaId + '\'');
        builder.append(", leftAgendaId='" + leftAgendaId + '\'');
        builder.append(", childProcessId='" + childProcessId + '\'');

        if (!attributes.isEmpty()) {
            builder.append(", attributes={");
            for (CheckAttributeEntity attr : attributes) {
                builder.append(" CheckAttributeEntity [id=");
                builder.append(attr.getId());
                builder.append(", key=");
                builder.append(attr.getKey());
                builder.append(", value=");
                builder.append(attr.getValue());
                builder.append("]");
            }
            builder.append(" }");
        }

        return builder.toString();
    }

    public void addAttribute(String key, String value) {

        this.attributes.add(new CheckAttributeEntity(new AttributeInfo(key, value), this));


    }
}