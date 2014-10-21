package org.kuali.student.ap.academicplan.model;

import com.sun.istack.NotNull;
import org.kuali.student.ap.academicplan.dao.LearningPlanDao;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
@SuppressWarnings({"JpaDataSourceORMInspection"})
@Entity
@Table(name = "KSPL_LRNG_PLAN_ITEM",
    uniqueConstraints = @UniqueConstraint(columnNames={"PLAN_ID", "TYPE_ID", "REF_OBJ_ID"}))
@NamedQueries( {

    @NamedQuery(name = "LearningPlanItem.getPlanItems",
            query = "SELECT pi FROM PlanItemEntity pi, LearningPlanEntity p WHERE " +
                    "pi.learningPlan = p " +
                    "and p.id =:learningPlanId"),

    @NamedQuery(name = "LearningPlanItem.getPlanItemsByType",
            query = "SELECT pi FROM PlanItemEntity pi, LearningPlanEntity p WHERE " +
                    "pi.learningPlan = p " +
                    "and p.id =:learningPlanId " +
                    "and pi.typeId =:learningPlanItemType"),

    @NamedQuery(name = "LearningPlanItem.getPlanItemsByRefObjectId",
            query = "SELECT pi FROM PlanItemEntity pi, LearningPlanEntity p  WHERE " +
                    "pi.learningPlan = p " +
                    "and p.id =:learningPlanId " +
                    "and pi.refObjectTypeKey = :refObjectTypeKey " +
                    "and pi.refObjectId = :refObjectId"),

    @NamedQuery(name = "LearningPlanItem.getPlanItemsByCategory",
            query = "SELECT pi FROM PlanItemEntity pi, LearningPlanEntity p WHERE " +
                    "pi.learningPlan = p " +
                    "and p.id =:learningPlanId " +
                    "and pi.category =:category")
})
public class PlanItemEntity extends MetaEntity implements AttributeOwner<PlanItemAttributeEntity> {

    @NotNull
    @Column(name="REF_OBJ_TYPE_KEY")
	private String refObjectTypeKey;

    @Column(name="REF_OBJ_ID")
    private String refObjectId;

    @Column(name = "TYPE_ID")
    private String typeId;

    @ManyToOne()
    @JoinColumn(name = "PLAN_ID")
    private LearningPlanEntity learningPlan;

    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String plain;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval = true)
    private Set<PlanItemAttributeEntity> attributes;

    @ElementCollection (fetch=FetchType.EAGER)
    @CollectionTable(name="KSPL_LRNG_PLAN_ITEM_ATP_ID",
        joinColumns=@JoinColumn(name="PLAN_ITEM_ID"),
            uniqueConstraints = @UniqueConstraint(columnNames={"PLAN_ITEM_ID", "ATP_ID"}))
    @Column(name="ATP_ID")
    private Set<String> planTermIds;

    @Column(name = "CREDIT")
    private BigDecimal credit;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name="STATE")
    private String stateKey;

    public PlanItemEntity() {
        super();
    }

	@Override
	public Set<PlanItemAttributeEntity> getAttributes() {
		return this.attributes;
	}

	@Override
	public void setAttributes(Set<PlanItemAttributeEntity> attributes) {
		this.attributes = attributes;
	}

	public String getRefObjectTypeKey() {
		return refObjectTypeKey;
	}

	public void setRefObjectTypeKey(String refObjectTypeKey) {
		this.refObjectTypeKey = refObjectTypeKey;
	}

	public String getRefObjectId() {
		return refObjectId;
	}

	public void setRefObjectId(String refObjectId) {
		this.refObjectId = refObjectId;
	}

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LearningPlanEntity getLearningPlan() {
        return learningPlan;
    }

    public void setLearningPlan(LearningPlanEntity learningPlan) {
        this.learningPlan = learningPlan;
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

    public Set<String> getPlanTermIds() {
        return planTermIds;
    }

    public void setPlanTermIds(Set<String> planTermIds) {
        this.planTermIds = planTermIds;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStateKey() {
        return stateKey;
    }

    public void setStateKey(String stateKey) {
        this.stateKey = stateKey;
    }


    /**
     * Add an ATP id to the set. No nulls or empty strings.
     *
     * @return
     */
    public boolean addPlanTermId(String atpId) {
        if (atpId == null || atpId.trim().equals("")) {
            return false;
        }
        return this.planTermIds.add(atpId);
    }

    /**
     * Remove an ATP id from the Set.
     *
     * @param atpId
     * @return
     */
    public boolean removePlanTermId(String atpId) {
        return this.planTermIds.remove(atpId);
    }

    /**
     * Provides and data transfer object representation of the plan item.
     * @return LearningPlanItemInfo
     */
    public PlanItemInfo toDto() {
        PlanItemInfo dto = new PlanItemInfo();

        dto.setId(getId());
        dto.setName(getName());
        dto.setLearningPlanId(this.getLearningPlan().getId());
        dto.setRefObjectId(this.getRefObjectId());
        dto.setRefObjectType(this.getRefObjectTypeKey());
        dto.setTypeKey(this.getTypeId());
        dto.setStateKey(this.getStateKey());
        dto.setCredit(this.getCredit());
        dto.setCategory(AcademicPlanServiceConstants.ItemCategory.fromString(this.getCategory()));

        dto.setMeta(super.toDTO());
        dto.setDescr(new RichTextHelper().toRichTextInfo(plain, formatted));

        //  Convert the Set to a List.
        dto.setPlanTermIds(new ArrayList<String>(this.getPlanTermIds()));

        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        if (getAttributes() != null) {
            for (PlanItemAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                attributes.add(attInfo);
            }
        }
        dto.setAttributes(attributes);

        return dto;
    }

    @Override
    public String toString() {
        return "PlanItemEntity [" + getId() + "]";
    }

    /**
     * Populate PlanItemEntity (this) from the passed in Data Transfer Object
     * @param planItem -  plan item data transfer object
     * @param learningPlanDao - learning plan data-access-object...used to populate the learningPlan of the PlanItemEntity
     * @throws InvalidParameterException
     */
    public void fromDto(PlanItemInfo planItem, LearningPlanDao learningPlanDao) throws InvalidParameterException {
        super.fromDTO(planItem);
        this.setName(planItem.getName());
        setId(planItem.getId());
        setRefObjectId(planItem.getRefObjectId());
        setRefObjectTypeKey(planItem.getRefObjectType());
        setTypeId(planItem.getTypeKey());
        setStateKey(planItem.getStateKey());
        setCredit(planItem.getCredits());
        setCategory(planItem.getCategory().toString());

        if (planItem.getDescr() == null) {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        } else {
            this.setDescrFormatted(planItem.getDescr().getFormatted());
            this.setDescrPlain(planItem.getDescr().getPlain());
        }

        //  Update plan term ids.
        if (planItem.getPlanTermIds() != null) {
            //  Convert from List to Set.
            setPlanTermIds(new HashSet<String>(planItem.getPlanTermIds()));
        }

        //  Load entity attributes
        if(this.getAttributes() == null) {
            this.setAttributes(new HashSet<PlanItemAttributeEntity>());
        }
        else {
            this.getAttributes().clear();
        }
        for (Attribute att : planItem.getAttributes()) {
            this.getAttributes().add(new PlanItemAttributeEntity(att, this));
        }

        LearningPlanEntity planEntity = learningPlanDao.find(planItem.getLearningPlanId());
        if (planEntity == null) {
            throw new InvalidParameterException(String.format("Unknown learning plan id [%s]",
                    planItem.getLearningPlanId()));
        }
        setLearningPlan(planEntity);
    }
}
