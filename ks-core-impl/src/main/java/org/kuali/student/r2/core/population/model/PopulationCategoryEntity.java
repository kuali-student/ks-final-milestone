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
 * Created by Mezba Mahtab on 7/10/12
 */
package org.kuali.student.r2.core.population.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.population.dto.PopulationCategoryInfo;
import org.kuali.student.r2.core.population.infc.PopulationCategory;

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
 * This class represents the main Population Category database tuple.
 *
 * @author Mezba Mahtab
 */

@Entity
@Table(name = "KSEN_POPULATION_CAT")
public class PopulationCategoryEntity extends MetaEntity implements AttributeOwner<PopulationCategoryAttributeEntity> {

    ////////////////////
    // DATA FIELDS
    ////////////////////

    @Column(name = "POPULATION_CAT_TYPE", nullable = false)
    private String populationCategoryType;

    @Column(name = "POPULATION_CAT_STATE", nullable = false)
    private String populationCategoryState;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private final Set<PopulationCategoryAttributeEntity> attributes = new HashSet<PopulationCategoryAttributeEntity>();

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////

    public PopulationCategoryEntity(PopulationCategory infc) {
        super(infc);
        this.setId(infc.getId());
        this.setPopulationCategoryType(infc.getTypeKey());
        this.fromDTO(infc);
    }

    public PopulationCategoryEntity() {
        super();
    }

    public void fromDTO(PopulationCategory infc) {
        this.setPopulationCategoryState(infc.getStateKey());
        this.setName(infc.getName());
        if (infc.getDescr() != null) {
            this.setDescrFormatted(infc.getDescr().getFormatted());
            this.setDescrPlain(infc.getDescr().getPlain());
        } else {
            this.setDescrFormatted(null);
            this.setDescrPlain(null);
        }
        this.attributes.clear();
        for (Attribute att : infc.getAttributes()) {
            this.attributes.add(new PopulationCategoryAttributeEntity(att, this));
        }
    }

    /**
     * @return PopulationCategory DTO
     */
    public PopulationCategoryInfo toDto() {
        PopulationCategoryInfo populationCategoryInfo = new PopulationCategoryInfo();
        populationCategoryInfo.setMeta(super.toDTO());
        populationCategoryInfo.setId(getId());
        populationCategoryInfo.setTypeKey(populationCategoryType);
        populationCategoryInfo.setStateKey(populationCategoryState);
        populationCategoryInfo.setDescr(new RichTextHelper().toRichTextInfo(descrPlain, descrFormatted));

        List<AttributeInfo> dtoAttributes = populationCategoryInfo.getAttributes();
        dtoAttributes.clear();
        List<AttributeInfo> attributes = populationCategoryInfo.getAttributes();
        if (getAttributes() != null) {
            for (PopulationCategoryAttributeEntity att : getAttributes()) {
                AttributeInfo attInfo = att.toDto();
                attributes.add(attInfo);
            }
        }
        populationCategoryInfo.setAttributes(attributes);
        return populationCategoryInfo;
    }

    ///////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////

    public String getPopulationCategoryType() {
        return populationCategoryType;
    }

    public void setPopulationCategoryType(String populationCategoryType) {
        this.populationCategoryType = populationCategoryType;
    }

    public String getPopulationCategoryState() {
        return populationCategoryState;
    }

    public void setPopulationCategoryState(String populationCategoryState) {
        this.populationCategoryState = populationCategoryState;
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

    @Override
    public Set<PopulationCategoryAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<PopulationCategoryAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null)
            this.attributes.addAll(attributes);
    }

    /////////////////////
    // FUNCTIONALS
    //////////////////////

    @Override
    public String toString() {
        return "PopulationCategoryEntity{" +
                "populationCategoryType='" + populationCategoryType + '\'' +
                ", populationCategoryState='" + populationCategoryState + '\'' +
                ", name='" + name + '\'' +
                ", descrPlain='" + descrPlain + '\'' +
                ", descrFormatted='" + descrFormatted + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
