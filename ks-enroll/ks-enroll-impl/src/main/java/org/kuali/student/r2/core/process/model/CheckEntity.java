package org.kuali.student.r2.core.process.model;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.infc.Check;

import javax.persistence.*;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "KSEN_PROCESS_CHECK")
public class CheckEntity extends MetaEntity /*implements AttributeOwner<CheckAttributeEntity>*/ {

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

    @Column(name = "MILESTONE_TYPE")
    private String milestoneType;

    @Column(name = "AGENDA_ID")
    private String agendaId;

    @Column(name = "RIGHT_AGENDA_ID")
    private String rightAgendaId;

    @Column(name = "LEFT_AGENDA_ID")
    private String leftAgendaId;
    /*
    @ManyToOne (optional = true)
    @JoinColumn(name = "CHILD_PROCESS_ID")
    */
    @Column(name = "CHILD_PROCESS_ID")
    private String childProcessId;

    @ElementCollection
    @MapKeyColumn(name = "ATTR_KEY")
    @Column(name = "ATTR_VALUE")
    @CollectionTable(name="KSEN_PROCESS_CHECK_ATTR", joinColumns = @JoinColumn(name = "OWNER_ID"))
    private Map<String, String> attributes;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<CheckAttributeEntity> attributes;*/

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////

    public CheckEntity() { attributes = new Hashtable<String, String>(); }

    public CheckEntity(Check check) {
        super(check);
        this.setId(check.getId());
        this.setCheckType(check.getTypeKey());
        this.fromDTO(check);
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
        this.setIssueId(check.getIssueId());
        this.setMilestoneType(check.getMilestoneTypeKey());
        this.setAgendaId(check.getAgendaId());
        this.setRightAgendaId(check.getRightComparisonValue());
        this.setLeftAgendaId(check.getLeftComparisonAgendaId());
        this.setChildProcessId(check.getProcessKey());
        this.attributes = new Hashtable<String, String>();
        for (Attribute att : check.getAttributes()) {
            this.attributes.put(att.getKey(), att.getValue());
        }

        /*
        this.setAttributes(new HashSet<CheckAttributeEntity>());
        for (Attribute att : check.getAttributes()) {
            this.getAttributes().add(new CheckAttributeEntity(att, this));
        }
        */
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
        checkInfo.setIssueId(issueId);
        checkInfo.setMilestoneTypeKey(milestoneType);
        checkInfo.setAgendaId(agendaId);
        checkInfo.setRightComparisonAgendaId(rightAgendaId);
        checkInfo.setLeftComparisonAgendaId(leftAgendaId);
        checkInfo.setChildProcessKey(childProcessId);
        List<AttributeInfo> dtoAttributes = checkInfo.getAttributes();
        dtoAttributes.clear();
        for (String key : this.attributes.keySet()) {
            AttributeInfo inf = new AttributeInfo();
            inf.setKey(key);
            inf.setValue(attributes.get(key));
            dtoAttributes.add(inf);
        }
        checkInfo.setAttributes(dtoAttributes);
/*
        List<AttributeInfo> attributes = checkInfo.getAttributes();
        if (getAttributes() != null) {
            for (CheckAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                attributes.add(attInfo);
            }
        }
        checkInfo.setAttributes(attributes);
*/
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

    public void addAttribute (String key, String value) {
        attributes.put(key, value);
    }
 /*
    @Override
    public Set<CheckAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<CheckAttributeEntity> attributes) {
        this.attributes = attributes;
    }
 */

    ///////////////////////
    // FUNCTIONALS
    ///////////////////////

    @Override
    public String toString() {
        return "CheckEntity{" +
                "id='" + getId() + '\'' +
                ", checkType='" + checkType + '\'' +
                ", checkState='" + checkState + '\'' +
                ", name='" + name + '\'' +
                ", descrPlain='" + descrPlain + '\'' +
                ", descrFormatted='" + descrFormatted + '\'' +
                ", issueId='" + issueId + '\'' +
                ", milestoneType='" + milestoneType + '\'' +
                ", agendaId='" + agendaId + '\'' +
                ", rightAgendaId='" + rightAgendaId + '\'' +
                ", leftAgendaId='" + leftAgendaId + '\'' +
                ", childProcessId='" + childProcessId + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}