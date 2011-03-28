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
package org.kuali.student.datadictionary.infc;

import org.kuali.rice.kns.datadictionary.validation.DataType;

/**
 * Dictionary data for Attributes (or fields) that exist on an object.
 *
 * @author nwright
 */
public interface AttributeDefinitionInfc {

    /**
     * The name of the attribute (or field).
     *
     * Should match the field name on the corresponding business object.
     *
     * TODO: figure out how to handle sub-objects.  I.e. using dot notation?
     *
     * name = name of attribute
     */
    public String getName();

    /**
     * @return the childEntryName
     */
    public String getChildEntryName();

    /**
     * @return the dataType
     */
    public DataType getDataType();

    /**
     * The required element allows values of "true" or "false". A value of
     * "true" indicates that a value must be entered for this business object
     * when creating or editing a new business object.
     */
    public Boolean isRequired();

    /**
     * MinOccurs
     *
     * TODO: get Rice to rename this to getMinOccurs to it matches the setter.
     *
     * @return the minOccurs
     */
    public Integer getMinOccurs();

    /**
     * MaxOccurs
     *
     * TODO: Get RICE to rename this getMaxOccurs so it matches the setter
     * TODO: Get RICE to set a value that means UNBOUNDED, perhaps 999999999
     *
     * @return the maxOccurs
     */
    public Integer getMaxOccurs();

    /**
     * @return the minLength
     */
    public Integer getMinLength();

    /**
     * The maxLength element determines the maximum size of the field for data
     * entry edit purposes and for display purposes.
     */
    public Integer getMaxLength();

    /**
     * forceUppercase = convert user entry to uppercase and always display
     * database value as uppercase.
     */
    public Boolean getForceUppercase();

    /**
     * The shortLabel element is the field or collection name that will be used
     * in applications when a shorter name (than the label element) is required.
     * This will be overridden by presence of displayLabelAttribute element.
     */
    public String getShortLabel();

    /**
     * The summary element is used to provide a short description of the
     * attribute or collection. This is designed to be used for help purposes.
     */
    public String getSummary();

    /**
     * The label element is the field or collection name that will be shown on
     * inquiry and maintenance screens. This will be overridden by presence of
     * displayLabelAttribute element.
     */
    public String getLabel();

    /**
     * The description element is used to provide a long description of the
     * attribute or collection. This is designed to be used for help purposes.
     */
    public String getDescription();

    /**
     * The exclusiveMin element determines the minimum allowable value for data
     * entry editing purposes. Value can be an integer or decimal value such as
     * -.001 or 99.
     */
    public String getExclusiveMin();

    /**
     * The inclusiveMax element determines the maximum allowable value for data
     * entry editing purposes. Value can be an integer or decimal value such as
     * -.001 or 99.
     *
     * JSTL: This field is mapped into the field named "exclusiveMax".
     */
    public String getInclusiveMax();


    /**
     * The displayLabelAttribute element is used to indicate that the label and
     * short label should be obtained from another attribute.
     *
     * The label element and short label element defined for this attribute will
     * be overridden. Instead, the label and short label values will be obtained
     * by referencing the corresponding values from the attribute indicated by
     * this element.
     */
    public String getDisplayLabelAttribute();

    /**
     * Similar to a db column that is flagged as unique, the value is unique
     * across all data objects of that type
     *
     * TODO: ask if this should be isUnique instead of getUnique?
     *
     * @return the unique
     */
    public Boolean isUnique();


    /**
     * Class to do custom validation
     *
     * @return the customValidatorClass
     */
    public String getCustomValidatorClass();

        /**
     * The formatterClass element is used when custom formatting is required for
     * display of the field value. This field specifies the name of the java
     * class to be used for the formatting. About 15 different classes are
     * available including BooleanFormatter, CurrencyFormatter, DateFormatter,
     * etc.
     */
    public String getFormatterClass();

        /**
     * The constraint that applies regular expressions to to the value
     *
     * TODO: Ask RICE to create an interface for ValidCharactersConstraint so we can return that instead of the generic constraint
     *
     * @return the validChars
     */
    public ValidCharactersConstraintInfc getValidCharactersConstraint();
}
