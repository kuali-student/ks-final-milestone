package org.kuali.student.ap.academicplan.model;

import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "KSPL_LRNG_PLAN")
public class LearningPlanEntity extends MetaEntity implements AttributeOwner<LearningPlanAttributeEntity>, Comparable<LearningPlanEntity> {

    @Column(name="STUDENT_ID")
	private String studentId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LearningPlanRichTextEntity descr;

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

    public LearningPlanRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(LearningPlanRichTextEntity descr) {
        this.descr = descr;
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
        return String.format("LearningPlan [%s, %s]: %s", this.getId(), this.getObjectId(), this.getDescr().getPlain());
    }

    /**
     * Provides and data transfer object representation of the plan.
     * @return LearningPlanInfo
     */
    public LearningPlanInfo toDto() {
        LearningPlanInfo dto = new LearningPlanInfo();

        dto.setId(getId());
        dto.setStudentId(this.studentId);
        dto.setTypeKey(this.getTypeId());
        dto.setStateKey(this.getStateKey());
        dto.setShared(this.shared);

        dto.setMeta(super.toDTO());

        if (this.getDescr() != null) {
            dto.setDescr(this.getDescr().toDto());
        }

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
        if (! this.getDescr().getPlain().equals(other.getDescr().getPlain())) {
            return this.getDescr().getPlain().compareTo(other.getDescr().getPlain());
        }

        return 0;
    }
}