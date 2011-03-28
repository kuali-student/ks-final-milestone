/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.datadictionary.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.kuali.rice.kns.datadictionary.validation.DataType;
import org.kuali.student.datadictionary.infc.AttributeDefinitionInfc;
import org.kuali.student.datadictionary.infc.ValidCharactersConstraintInfc;

/**
 * This is an info ojbect that holds an attribute definition
 *
 * @author nwright
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AttributeDefinitionInfo implements AttributeDefinitionInfc, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String name;
    @XmlElement
    private String childEntryName;
    @XmlElement
    private DataType dataType;
    @XmlElement
    private Boolean required;
    @XmlElement
    private Integer minOccurs;
    @XmlElement
    private Integer maxOccurs;
    @XmlElement
    private Integer minLength;
    @XmlElement
    private Integer maxLength;
    @XmlElement
    private Boolean forceUppercase;
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
    private Boolean unique;
    @XmlElement
    private String customValidatorClass;
    @XmlElement
    private String formatterClass;
    @XmlElement
    private ValidCharactersConstraintInfo validCharactersConstraint;

    public AttributeDefinitionInfo() {
        this.name = null;
        this.childEntryName = null;
        this.dataType = null;
        this.required = null;
        this.minOccurs = null;
        this.maxOccurs = null;
        this.minLength = null;
        this.maxLength = null;
        this.forceUppercase = null;
        this.shortLabel = null;
        this.summary = null;
        this.label = null;
        this.description = null;
        this.exclusiveMin = null;
        this.inclusiveMax = null;
        this.displayLabelAttribute = null;
        this.unique = null;
        this.customValidatorClass = null;
        this.formatterClass = null;
        this.validCharactersConstraint = null;

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
    public Boolean getForceUppercase() {
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
    public Boolean isUnique() {
        return this.unique;
    }

    @Override
    public ValidCharactersConstraintInfc getValidCharactersConstraint() {
        return this.validCharactersConstraint;
    }

    @Override
    public Boolean isRequired() {
        return this.required;
    }

    private AttributeDefinitionInfo(AttributeDefinitionInfc infc) {
        this.name = infc.getName();
        this.childEntryName = infc.getChildEntryName();
        this.dataType = infc.getDataType();
        this.required = infc.isRequired();
        this.minOccurs = infc.getMinOccurs();
        this.maxOccurs = infc.getMaxOccurs();
        this.minLength = infc.getMinLength();
        this.maxLength = infc.getMaxLength();
        this.forceUppercase = infc.getForceUppercase();
        this.shortLabel = infc.getShortLabel();
        this.summary = infc.getSummary();
        this.label = infc.getLabel();
        this.description = infc.getDescription();
        this.exclusiveMin = infc.getExclusiveMin();
        this.inclusiveMax = infc.getInclusiveMax();
        this.displayLabelAttribute = infc.getDisplayLabelAttribute();
        this.unique = infc.isUnique();
        this.customValidatorClass = infc.getCustomValidatorClass();
        this.formatterClass = infc.getFormatterClass();
        if (infc.getValidCharactersConstraint() != null) {
            this.validCharactersConstraint = new ValidCharactersConstraintInfo.Builder(infc.getValidCharactersConstraint()).build();
        }
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

        public Builder(AttributeDefinitionInfc infc) {
            this.name = infc.getName();
            this.childEntryName = infc.getChildEntryName();
            this.dataType = infc.getDataType();
            this.required = infc.isRequired();
            this.minOccurs = infc.getMinOccurs();
            this.maxOccurs = infc.getMaxOccurs();
            this.minLength = infc.getMinLength();
            this.maxLength = infc.getMaxLength();
            this.forceUppercase = infc.getForceUppercase();
            this.shortLabel = infc.getShortLabel();
            this.summary = infc.getSummary();
            this.label = infc.getLabel();
            this.description = infc.getDescription();
            this.exclusiveMin = infc.getExclusiveMin();
            this.inclusiveMax = infc.getInclusiveMax();
            this.displayLabelAttribute = infc.getDisplayLabelAttribute();
            this.unique = infc.isUnique();
            this.customValidatorClass = infc.getCustomValidatorClass();
            this.formatterClass = infc.getFormatterClass();
            this.validCharactersConstraint = infc.getValidCharactersConstraint();
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
        public Boolean getForceUppercase() {
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
        public Boolean isUnique() {
            return this.unique;
        }

        @Override
        public ValidCharactersConstraintInfc getValidCharactersConstraint() {
            return this.validCharactersConstraint;
        }

        @Override
        public Boolean isRequired() {
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
