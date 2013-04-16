/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.r2.common.datadictionary.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.kuali.rice.core.api.uif.DataType;
import org.kuali.student.r2.common.datadictionary.infc.AttributeDefinitionInfc;
import org.kuali.student.r2.common.datadictionary.infc.ValidCharactersConstraintInfc;

@XmlAccessorType(XmlAccessType.FIELD)
public class AttributeDefinitionInfo 
    implements AttributeDefinitionInfc, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String name;

    @XmlElement
    private String childEntryName;

    @XmlElement
    private DataType dataType;

    @XmlElement
    private Boolean isRequired;

    @XmlElement
    private Integer minOccurs;

    @XmlElement
    private Integer maxOccurs;

    @XmlElement
    private Integer minLength;

    @XmlElement
    private Integer maxLength;

    @XmlElement
    private Boolean isForceUppercase;

    @XmlElement
    private String shortLabel;

    @XmlElement
    private String summary;

    @XmlElement
    private String label;

    @XmlElement
    private String description;

    @XmlElement
    private String exclusiveMin;

    @XmlElement
    private String inclusiveMax;

    @XmlElement
    private String displayLabelAttribute;

    @XmlElement
    private Boolean isUnique;

    @XmlElement
    private String customValidatorClass;

    @XmlElement
    private String formatterClass;

    @XmlElement
    private ValidCharactersConstraintInfo validCharactersConstraint;

    
    /** 
     * Constructs a new AttributeDefinitionInfo.
     */
    public AttributeDefinitionInfo() {
    }

    /** 
     * Constructs a new AttributeDefinitionInfo from an
     * AttributeDefinition.
     *
     * @param attrDef the AttributeDefinition to copy
     */
    public AttributeDefinitionInfo(AttributeDefinitionInfc attrDef) {
        
        if (attrDef != null) {
            this.name = attrDef.getName();
            this.childEntryName = attrDef.getChildEntryName();
            this.dataType = attrDef.getDataType();
            this.isRequired = attrDef.getIsRequired();
            this.minOccurs = attrDef.getMinOccurs();
            this.maxOccurs = attrDef.getMaxOccurs();
            this.minLength = attrDef.getMinLength();
            this.maxLength = attrDef.getMaxLength();
            this.isForceUppercase = attrDef.getIsForceUppercase();
            this.shortLabel = attrDef.getShortLabel();
            this.summary = attrDef.getSummary();
            this.label = attrDef.getLabel();
            this.description = attrDef.getDescription();
            this.exclusiveMin = attrDef.getExclusiveMin();
            this.inclusiveMax = attrDef.getInclusiveMax();
            this.displayLabelAttribute = attrDef.getDisplayLabelAttribute();
            this.isUnique = attrDef.getIsUnique();
            this.customValidatorClass = attrDef.getCustomValidatorClass();
            this.formatterClass = attrDef.getFormatterClass();
            if (attrDef.getValidCharactersConstraint() != null) {
                this.validCharactersConstraint = new ValidCharactersConstraintInfo.Builder(attrDef.getValidCharactersConstraint()).build();
            }
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getChildEntryName() {
        return this.childEntryName;
    }

    @Override
    public DataType getDataType() {
        return this.dataType;
    }

    @Override
    public Boolean getIsRequired() {
        return this.isRequired;
    }

    @Override
    public Integer getMinOccurs() {
        return this.minOccurs;
    }

    @Override
    public Integer getMaxOccurs() {
        return this.maxOccurs;
    }

    @Override
    public Integer getMinLength() {
        return this.minLength;
    }

    @Override
    public Integer getMaxLength() {
        return this.maxLength;
    }

    @Override
    public Boolean getIsForceUppercase() {
        return this.isForceUppercase;
    }

    @Override
    public String getShortLabel() {
        return this.shortLabel;
    }

    @Override
    public String getSummary() {
        return this.summary;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getExclusiveMin() {
        return this.exclusiveMin;
    }

    @Override
    public String getInclusiveMax() {
        return this.inclusiveMax;
    }

    @Override
    public String getDisplayLabelAttribute() {
        return this.displayLabelAttribute;
    }

    @Override
    public Boolean getIsUnique() {
        return this.isUnique;
    }

    @Override
    public String getCustomValidatorClass() {
        return this.customValidatorClass;
    }

    @Override
    public String getFormatterClass() {
        return this.formatterClass;
    }

    @Override
    public ValidCharactersConstraintInfo getValidCharactersConstraint() {
        return this.validCharactersConstraint;
    }


    public static class Builder implements AttributeDefinitionInfc {

        private String name;
        private String childEntryName;
        private DataType dataType;
        private Boolean required;
        private Integer minOccurs;
        private Integer maxOccurs;
        private Integer minLength;
        private Integer maxLength;
        private Boolean forceUppercase;
        private String shortLabel;
        private String summary;
        private String label;
        private String description;
        private String exclusiveMin;
        private String inclusiveMax;
        private String displayLabelAttribute;
        private Boolean unique;
        private String customValidatorClass;
        private String formatterClass;
        private ValidCharactersConstraintInfc validCharactersConstraint;

        public Builder() {
        }

        public Builder(AttributeDefinitionInfc attrDef) {
            this.name = attrDef.getName();
            this.childEntryName = attrDef.getChildEntryName();
            this.dataType = attrDef.getDataType();
            this.required = attrDef.getIsRequired();
            this.minOccurs = attrDef.getMinOccurs();
            this.maxOccurs = attrDef.getMaxOccurs();
            this.minLength = attrDef.getMinLength();
            this.maxLength = attrDef.getMaxLength();
            this.forceUppercase = attrDef.getIsForceUppercase();
            this.shortLabel = attrDef.getShortLabel();
            this.summary = attrDef.getSummary();
            this.label = attrDef.getLabel();
            this.description = attrDef.getDescription();
            this.exclusiveMin = attrDef.getExclusiveMin();
            this.inclusiveMax = attrDef.getInclusiveMax();
            this.displayLabelAttribute = attrDef.getDisplayLabelAttribute();
            this.unique = attrDef.getIsUnique();
            this.customValidatorClass = attrDef.getCustomValidatorClass();
            this.formatterClass = attrDef.getFormatterClass();
            this.validCharactersConstraint = attrDef.getValidCharactersConstraint();
        }

        public AttributeDefinitionInfo build() {
            return new AttributeDefinitionInfo(this);
        }

        @Override
        public String getChildEntryName() {
            return this.childEntryName;
        }

        @Override
        public String getCustomValidatorClass() {
            return this.customValidatorClass;
        }

        @Override
        public DataType getDataType() {
            return this.dataType;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public String getDisplayLabelAttribute() {
            return this.displayLabelAttribute;
        }

        @Override
        public String getExclusiveMin() {
            return this.exclusiveMin;
        }

        @Override
        public Boolean getIsForceUppercase() {
            return this.forceUppercase;
        }

        @Override
        public String getFormatterClass() {
            return this.formatterClass;
        }

        @Override
        public String getInclusiveMax() {
            return this.inclusiveMax;
        }

        @Override
        public String getLabel() {
            return this.label;
        }

        @Override
        public Integer getMaxLength() {
            return this.maxLength;
        }

        @Override
        public Integer getMaxOccurs() {
            return this.maxOccurs;
        }

        @Override
        public Integer getMinLength() {
            return this.minLength;
        }

        @Override
        public Integer getMinOccurs() {
            return this.minOccurs;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getShortLabel() {
            return this.shortLabel;
        }

        @Override
        public String getSummary() {
            return this.summary;
        }

        @Override
        public Boolean getIsUnique() {
            return this.unique;
        }

        @Override
        public ValidCharactersConstraintInfc getValidCharactersConstraint() {
            return this.validCharactersConstraint;
        }

        @Override
        public Boolean getIsRequired() {
            return this.required;
        }

        public Builder setChildEntryName(String childEntryName) {
            this.childEntryName = childEntryName;
            return this;
        }

        public Builder setCustomValidatorClass(String customValidatorClass) {
            this.customValidatorClass = customValidatorClass;
            return this;
        }

        public Builder setDataType(DataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDisplayLabelAttribute(String displayLabelAttribute) {
            this.displayLabelAttribute = displayLabelAttribute;
            return this;
        }

        public Builder setExclusiveMin(String exclusiveMin) {
            this.exclusiveMin = exclusiveMin;
            return this;
        }

        public Builder setForceUppercase(Boolean forceUppercase) {
            this.forceUppercase = forceUppercase;
            return this;
        }

        public Builder setFormatterClass(String formatterClass) {
            this.formatterClass = formatterClass;
            return this;
        }

        public Builder setInclusiveMax(String inclusiveMax) {
            this.inclusiveMax = inclusiveMax;
            return this;
        }

        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        public Builder setMaxLength(Integer maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        public Builder setMaxOccurs(Integer maxOccurs) {
            this.maxOccurs = maxOccurs;
            return this;
        }

        public Builder setMinLength(Integer minLength) {
            this.minLength = minLength;
            return this;
        }

        public Builder setMinOccurs(Integer minOccurs) {
            this.minOccurs = minOccurs;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setRequired(Boolean required) {
            this.required = required;
            return this;
        }

        public Builder setShortLabel(String shortLabel) {
            this.shortLabel = shortLabel;
            return this;
        }

        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder setUnique(Boolean unique) {
            this.unique = unique;
            return this;
        }

        public Builder setValidCharactersConstraint(ValidCharactersConstraintInfc validCharactersConstraint) {
            this.validCharactersConstraint = validCharactersConstraint;
            return this;
        }
    }
}
