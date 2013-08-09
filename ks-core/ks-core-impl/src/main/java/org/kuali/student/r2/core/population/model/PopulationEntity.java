package org.kuali.student.r2.core.population.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.infc.Population;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "KSEN_POPULATION")
public class PopulationEntity extends MetaEntity implements AttributeOwner<PopulationAttributeEntity> {

    ////////////////////
    // DATA FIELDS
    ////////////////////

    @Column(name = "POPULATION_TYPE", nullable = false)
    private String populationType;

    @Column(name = "POPULATION_STATE", nullable = false)
    private String populationState;

    @Column(name = "NAME")
    private String name;

    // NOTE: in the database this is an @ManyToOne but we want to sidestep JPA
    // loading a lot of nested data so we just store the id and manage the fetch
    // of the rule data separately.
    @Column(name = "POPULATION_RULE_ID")
    private String populationRuleId;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private final Set<PopulationAttributeEntity> attributes = new HashSet<PopulationAttributeEntity>();

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////

    public PopulationEntity(Population infc) {
        super(infc);
        this.setId(infc.getId());
        this.setPopulationType(infc.getTypeKey());
        this.fromDTO(infc);
    }

    public PopulationEntity() {
        super();
    }

    public void fromDTO(Population infc) {
        this.setPopulationState(infc.getStateKey());
        this.setName(infc.getName());
        if (infc.getDescr() != null) {
            this.setDescrFormatted(infc.getDescr().getFormatted());
            this.setDescrPlain(infc.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }
        // the rule id is thrown away when creating the dto

        this.attributes.clear();
        for (Attribute att : infc.getAttributes()) {
            this.attributes.add(new PopulationAttributeEntity(att, this));
        }
    }

    /**
     * @return Population DTO
     */
    public PopulationInfo toDto() {
        PopulationInfo populationInfo = new PopulationInfo();
        populationInfo.setMeta(super.toDTO());
        populationInfo.setId(getId());
        populationInfo.setTypeKey(populationType);
        populationInfo.setStateKey(populationState);
        populationInfo.setDescr(new RichTextHelper().toRichTextInfo(descrPlain, descrFormatted));
        populationInfo.setName(getName());

        // this rule id is thrown away when creating the dto

        List<AttributeInfo> dtoAttributes = populationInfo.getAttributes();
        dtoAttributes.clear();
        List<AttributeInfo> attributes = populationInfo.getAttributes();
        if (getAttributes() != null) {
            for (PopulationAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                attributes.add(attInfo);
            }
        }
        populationInfo.setAttributes(attributes);

        return populationInfo;
    }

    ///////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////

    public String getPopulationType() {
        return populationType;
    }

    public void setPopulationType(String populationType) {
        this.populationType = populationType;
    }

    public String getPopulationState() {
        return populationState;
    }

    public void setPopulationState(String populationState) {
        this.populationState = populationState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopulationRuleId() {
        return populationRuleId;
    }

    public void setPopulationRuleId(String populationRuleId) {
        this.populationRuleId = populationRuleId;
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

    @Override
    public Set<PopulationAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<PopulationAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null)
            this.attributes.addAll(attributes);
    }

    ///////////////////////
    // FUNCTIONALS
    ///////////////////////

    @Override
    public String toString() {
        return "PopulationEntity{" +
                "populationType='" + populationType + '\'' +
                ", populationState='" + populationState + '\'' +
                ", name='" + name + '\'' +
                ", populationRuleId='" + populationRuleId + '\'' +
                ", descrPlain='" + descrPlain + '\'' +
                ", descrFormatted='" + descrFormatted + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
