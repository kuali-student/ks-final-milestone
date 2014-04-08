package org.kuali.student.ap.academicplan.model;

import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"JpaDataSourceORMInspection"})
@Entity
@Table(name = "KSPL_LRNG_PLAN")
public class LearningPlanEntity extends MetaEntity implements AttributeOwner<LearningPlanAttributeEntity>, Comparable<LearningPlanEntity> {

    @Column(name="STUDENT_ID")
	private String studentId;

    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String plain;


    @Column(name = "TYPE_ID")
    private String typeId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval = true)
    private  Set<LearningPlanAttributeEntity> attributes;

    @Column(name="SHARED")
    private Boolean shared;


    @Column(name="STATE")
    private String stateKey;

    public LearningPlanEntity() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes(Set<LearningPlanAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public Set<LearningPlanAttributeEntity> getAttributes() {
        return attributes;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getStateKey() {
		return stateKey;
	}

	public void setStateKey(String stateKey) {
		this.stateKey = stateKey;
	}

    @Override
    public String toString() {
        return String.format("LearningPlan [%s, %s]: %s", this.getId(), this.getObjectId(), this.getDescrPlain());
    }

    /**
     * Provides and data transfer object representation of the plan.
     * @return LearningPlanInfo
     */
    public LearningPlanInfo toDto() {
        LearningPlanInfo dto = new LearningPlanInfo();

        dto.setId(getId());
        dto.setName(getName());
        dto.setStudentId(this.studentId);
        dto.setTypeKey(this.getTypeId());
        dto.setStateKey(this.getStateKey());
        dto.setShared(this.shared);

        dto.setMeta(super.toDTO());
        dto.setDescr(new RichTextHelper().toRichTextInfo(plain, formatted));

        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        if (null != getAttributes()) {
            for (LearningPlanAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                attributes.add(attInfo);
            }
        }
        dto.setAttributes(attributes);

        return dto;
    }


    @Override
    public int compareTo(LearningPlanEntity other) {

        if (other == null) {
            return -1;
        }

        //  First check student id.
        if (! other.getStudentId().equals(this.getStudentId())) {
            return this.getStudentId().compareTo(other.getStudentId());
        }

        //  Could check type here.

        //  Check description text
        if (! this.getDescrPlain().equals(other.getDescrPlain())) {
            return this.getDescrPlain().compareTo(other.getDescrPlain());
        }

        return 0;
    }

    /**
     * Populate LearningPlanEntity (this) from the passed in Data Transfer Object
     * @param learningPlan -  plan item data transfer object
     * @throws InvalidParameterException
     */

    public void fromDto(LearningPlanInfo learningPlan) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        super.fromDTO(learningPlan);

        setId(learningPlan.getId());
        setName(learningPlan.getName());
        setTypeId(learningPlan.getTypeKey());
        setStateKey(learningPlan.getStateKey());
        setStudentId(learningPlan.getStudentId());
        setShared(learningPlan.getShared());

        if (learningPlan.getDescr() == null) {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        } else {
            this.setDescrFormatted(learningPlan.getDescr().getFormatted());
            this.setDescrPlain(learningPlan.getDescr().getPlain());
        }

        //  Load entity attributes
        if(this.getAttributes() == null) {
            this.setAttributes(new HashSet<LearningPlanAttributeEntity>());
        }
        else {
            this.getAttributes().clear();
        }
        for (Attribute att : learningPlan.getAttributes()) {
            this.getAttributes().add(new LearningPlanAttributeEntity(att, this));
        }
    }
}