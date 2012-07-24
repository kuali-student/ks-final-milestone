/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Mezba Mahtab on 7/9/12
 */
package org.kuali.student.r2.core.population.model;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.infc.PopulationRule;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents the Population Rule table.
 *
 * @author Kuali Student Team
 */

@Entity
@Table(name = "KSEN_POPULATION_RULE")
public class PopulationRuleEntity extends MetaEntity implements AttributeOwner<PopulationRuleAttributeEntity> {

    ////////////////////
    // DATA FIELDS
    ////////////////////

    @Column(name = "POPULATION_RULE_TYPE", nullable = false)
    private String populationRuleType;

    @Column(name = "POPULATION_RULE_STATE", nullable = false)
    private String populationRuleState;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;

    // NOTE: in the database this is an @ManyToOne but we want to sidestep JPA
    // loading a lot of nested data so we just store the id and manage the fetch
    // of the rule data separately.
    @Column(name = "REF_POPULATION_ID")
    private String refPopulationId;

    @Column(name = "VARIES_BY_TIME_IND")
    private boolean variesByTimeIndicator;

    @Column(name = "SUPPORTS_GET_MBR_IND")
    private boolean supportsGetMembersIndicator;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private final Set<PopulationRuleAttributeEntity> attributes = new HashSet<PopulationRuleAttributeEntity>();

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////

    public PopulationRuleEntity(PopulationRule infc) {
        super(infc);
        this.setId(infc.getId());
        this.setPopulationRuleType(infc.getTypeKey());
        this.fromDTO(infc);
    }

    public PopulationRuleEntity() {
        super();
    }

    public void fromDTO(PopulationRule infc) {
        this.setPopulationRuleState(infc.getStateKey());
        this.setName(infc.getName());
        if (infc.getDescr() != null) {
            this.setDescrFormatted(infc.getDescr().getFormatted());
            this.setDescrPlain(infc.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }
        this.setRefPopulationId(infc.getReferencePopulationId());
        this.setVariesByTimeIndicator(infc.getVariesByTime());
        this.setSupportsGetMembersIndicator(infc.getSupportsGetMembers());
        this.attributes.clear();
        for (Attribute att : infc.getAttributes()) {
            this.attributes.add(new PopulationRuleAttributeEntity(att, this));
        }
    }

    /**
     * @return PopulationRule DTO
     */
    public PopulationRuleInfo toDto() {
        PopulationRuleInfo populationRuleInfo = new PopulationRuleInfo();
        populationRuleInfo.setMeta(super.toDTO());
        populationRuleInfo.setId(getId());
        populationRuleInfo.setTypeKey(populationRuleType);
        populationRuleInfo.setStateKey(populationRuleState);
        populationRuleInfo.setDescr(new RichTextHelper().toRichTextInfo(descrPlain, descrFormatted));
        populationRuleInfo.setReferencePopulationId(refPopulationId);
        populationRuleInfo.setVariesByTime(variesByTimeIndicator);
        populationRuleInfo.setSupportsGetMembers(supportsGetMembersIndicator);
        List<AttributeInfo> dtoAttributes = populationRuleInfo.getAttributes();
        dtoAttributes.clear();
        List<AttributeInfo> attributes = populationRuleInfo.getAttributes();
        if (getAttributes() != null) {
            for (PopulationRuleAttributeEntity att : getAttributes()) {
                attributes.add(att.toDto());
                /*AttributeInfo attInfo = att.toDto();
                attributes.add(attInfo);*/
            }
        }
        populationRuleInfo.setAttributes(attributes);

        return populationRuleInfo;
    }

    ///////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////

    public String getPopulationRuleType() {
        return populationRuleType;
    }

    public void setPopulationRuleType(String populationRuleType) {
        this.populationRuleType = populationRuleType;
    }

    public String getPopulationRuleState() {
        return populationRuleState;
    }

    public void setPopulationRuleState(String populationRuleState) {
        this.populationRuleState = populationRuleState;
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

    public String getRefPopulationId() {
        return refPopulationId;
    }

    public void setRefPopulationId(String refPopulationId) {
        this.refPopulationId = refPopulationId;
    }

    public boolean getVariesByTimeIndicator() {
        return variesByTimeIndicator;
    }

    public void setVariesByTimeIndicator(boolean variesByTimeIndicator) {
        this.variesByTimeIndicator = variesByTimeIndicator;
    }

    public boolean getSupportsGetMembersIndicator() {
        return supportsGetMembersIndicator;
    }

    public void setSupportsGetMembersIndicator(boolean supportsGetMembersIndicator) {
        this.supportsGetMembersIndicator = supportsGetMembersIndicator;
    }

    @Override
    public Set<PopulationRuleAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<PopulationRuleAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null)
            this.attributes.addAll(attributes);
    }

    /////////////////////
    // FUNCTIONALS
    //////////////////////

    @Override
    public String toString() {
        return "PopulationRuleEntity{" +
                "populationRuleType='" + populationRuleType + '\'' +
                ", populationRuleState='" + populationRuleState + '\'' +
                ", name='" + name + '\'' +
                ", descrPlain='" + descrPlain + '\'' +
                ", descrFormatted='" + descrFormatted + '\'' +
                ", refPopulationId='" + refPopulationId + '\'' +
                ", variesByTimeIndicator='" + variesByTimeIndicator + '\'' +
                ", supportsGetMembersIndicator='" + supportsGetMembersIndicator + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
