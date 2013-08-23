package org.kuali.student.enrollment.class2.courseofferingset.model;

import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.infc.Soc;
import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "KSEN_SOC")
@NamedQueries({
        @NamedQuery(name="Soc.getSocsBySocTypeId", query="Select a from SocEntity a where a.socType =:socType"),
        @NamedQuery(name="Soc.getSocIdsByType", query="Select a.id from SocEntity a where a.socType =:socType"),
        @NamedQuery(name="Soc.getSocsByTerm", query="Select a from SocEntity a where a.termId =:termId"),
        @NamedQuery(name="Soc.getSocIdsByTerm", query="SELECT soc.id FROM SocEntity soc WHERE soc.termId = :termId"),
        @NamedQuery(name="Soc.getSocsByTermAndSubjectArea", query="Select a from SocEntity a where a.termId=:termId and a.subjectArea = :subjectArea"),
        @NamedQuery(name="Soc.getSocIdsByTermAndSubjectArea", query="Select soc.id from SocEntity soc where soc.termId=:termId and soc.subjectArea = :subjectArea"),
        @NamedQuery(name="Soc.getSocsByTermAndUnitsContentOwner", query="Select a from SocEntity a where a.termId=:termId and a.unitsContentOwnerId = :unitsContentOwnerId"),
        @NamedQuery(name="Soc.getSocIdsByTermAndUnitsContentOwner", query="Select a.id from SocEntity a where a.termId=:termId and a.unitsContentOwnerId = :unitsContentOwnerId")

})
public class SocEntity extends MetaEntity implements AttributeOwner<SocAttributeEntity> {

    @Column(name = "SOC_TYPE", nullable = false)
    private String socType;
    @Column(name = "SOC_STATE", nullable = false)
    private String socState;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String plain;
    @Column(name = "TERM_ID")
    private String termId;
    @Column(name = "SUBJECT_AREA")
    private String subjectArea;
    @Column(name = "UNITS_CONTENT_OWNER_ID")
    private String unitsContentOwnerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<SocAttributeEntity> attributes = new HashSet<SocAttributeEntity>();

    public SocEntity() {
    }

    public SocEntity(Soc soc) {
        super(soc);
        this.setId(soc.getId());
        this.setSocType(soc.getTypeKey());
        this.setSocState(soc.getStateKey());
        this.setTermId(soc.getTermId());
        this.fromDTO(soc);
    }

    public void fromDTO(Soc soc) {
        this.setName(soc.getName());
        if (soc.getDescr() != null) {
            this.setDescrFormatted(soc.getDescr().getFormatted());
            this.setDescrPlain(soc.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }
        this.setSubjectArea(soc.getSubjectArea());
        this.setUnitsContentOwnerId(soc.getUnitsContentOwnerId()); // dynamic attributes
        this.attributes.clear();
        for (Attribute att : soc.getAttributes()) {
            SocAttributeEntity attEntity = new SocAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   
    public String getSocType() {
        return socType;
    }

    public void setSocType(String socType) {
        this.socType = socType;
    }

    public String getSocState() {
        return socState;
    }

    public void setSocState(String socState) {
        this.socState = socState;
    }

    public void setAttributes(Set<SocAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    public Set<SocAttributeEntity> getAttributes() {
        return attributes;
    }

    public SocInfo toDto() {
        SocInfo soc = new SocInfo();
        soc.setId(getId());
        soc.setTypeKey(socType);
        soc.setStateKey(socState);
        soc.setName(name);
        soc.setDescr(new RichTextHelper().toRichTextInfo(getDescrPlain(), getDescrFormatted()));
        soc.setTermId(termId);
        soc.setSubjectArea(subjectArea);
        soc.setUnitsContentOwnerId(unitsContentOwnerId);
        soc.setMeta(super.toDTO());
        if (getAttributes() != null) {
            for (SocAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                soc.getAttributes().add(attInfo);
            }
        }

        Date schedulingStarted = parseStateChangeDateString(soc, CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS);
        Date schedulingCompleted = parseStateChangeDateString(soc, CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED);
        Date publishingStarted = parseStateChangeDateString(soc, CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY);
        Date publishingCompleted = parseStateChangeDateString(soc, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY);
        String schedulingState;

        //
        if(schedulingStarted == null) {
            schedulingState = CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_NOT_STARTED;
        }
        else {
            soc.setLastSchedulingRunStarted(schedulingStarted);
            if(schedulingCompleted == null) {
                schedulingState = CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS;
            }
            else {
                schedulingState = CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED;
                soc.setLastSchedulingRunCompleted(schedulingCompleted);
            }
        }
        soc.setSchedulingStateKey(schedulingState);

        if (publishingStarted != null) {
            soc.setPublishingStarted(publishingStarted);
        }

        if (publishingCompleted != null) {
            soc.setPublishingCompleted(publishingCompleted);
        }

        return soc;
    }

    /**
     *  Reads a date string from the given state key from a dynamic attribute and attempts to create a java.util.Date object
     *  from it.
     *  @return A java.util.Date if the date string is parsable. If the state key doesn't exist returns null.
     *  Throws a RuntimeException if the key exists but the value is not parsable.
     */
    private Date parseStateChangeDateString(SocInfo soc, String stateKey) {
        Date dateOut = null;
        String value = soc.getAttributeValue(stateKey);
        if (value != null) {
            try {
                dateOut = DateFormatters.STATE_CHANGE_DATE_FORMATTER.parse(value);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(String.format("Could not parse date string [%s] stored in SOC %s attribute %s.",
                        value, soc.getId(), stateKey));
            }
        }
        return dateOut;
    }

    public String getDescrFormatted() {
        return formatted;
    }

    public void setDescrFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getDescrPlain() {
        return plain;
    }

    public void setDescrPlain(String plain) {
        this.plain = plain;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getUnitsContentOwnerId() {
        return unitsContentOwnerId;
    }

    public void setUnitsContentOwnerId(String unitsContentOwnerId) {
        this.unitsContentOwnerId = unitsContentOwnerId;
    }
}
