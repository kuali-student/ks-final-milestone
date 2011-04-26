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
package org.kuali.student.r2.common.datadictionary.infc;

import org.kuali.rice.kns.datadictionary.validation.DataType;

/**
 * Dictionary data for Attributes (or fields) that exist on an object.
 *
 * @author nwright
 */
public interface AttributeDefinitionInfc {

    /**
     * Name: Name
     *
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
     * Name: child entry name
     *
     * deprecated
     */
    public String getChildEntryName();

    /**
     * Name: Data Type
     *
     * The type of data this attribite (field) holds.
     * I.e. STRING, INTEGER, DATE, LONG, DATETIME, COMPLEX, etc.
     */
    public DataType getDataType();

    /**
     * Name: Required
     *
     * The required element allows values of "true" or "false". A value of
     * "true" indicates that a value must be entered for this business object
     * when creating or editing a new business object.
     */
    public Boolean isRequired();

    /**
     * Name: Minimum Occurences
     *
     * The minimum number of allowed occurences
     *
     * TODO: get Rice to rename this to getMinOccurs to it matches the setter.
     */
    public Integer getMinOccurs();

    /**
     * Name: Maximum Occurrences
     *
     * The maximum number of allowed occurences of this field
     *
     * TODO: Get RICE to rename this getMaxOccurs so it matches the setter
     * TODO: Get RICE to set a value that means UNBOUNDED, perhaps 999999999
     */
    public Integer getMaxOccurs();

    /**
     * Name: Minimum Length
     *
     * The miniumum length allowed for the field
     */
    public Integer getMinLength();

    /**
     * Name: Maximum Length
     *
     * The maxLength element determines the maximum size of the field for data
     * entry edit purposes and for display purposes.
     */
    public Integer getMaxLength();

    /**
     * Name: Force Uppercase
     *
     * forceUppercase = convert user entry to uppercase and always display
     * database value as uppercase.
     */
    public Boolean getForceUppercase();

    /**
     * Name: Short Label
     *
     * The shortLabel element is the field or collection name that will be used
     * in applications when a shorter name (than the label element) is required.
     * This will be overridden by presence of displayLabelAttribute element.
     */
    public String getShortLabel();

    /**
     * Name: Summary
     *
     * The summary element is used to provide a short description of the
     * attribute or collection. This is designed to be used for help purposes.
     */
    public String getSummary();

    /**
     * Name: Label
     *
     * The label element is the field or collection name that will be shown on
     * inquiry and maintenance screens. This will be overridden by presence of
     * displayLabelAttribute element.
     */
    public String getLabel();

    /**
     * Name: Description
     *
     * The description element is used to provide a long description of the
     * attribute or collection. This is designed to be used for help purposes.
     */
    public String getDescription();

    /**
     * Name: Exclusive Minimum
     *
     * The exclusiveMin element determines the minimum allowable value for data
     * entry editing purposes. Value can be an integer or decimal value such as
     * -.001 or 99.
     */
    public String getExclusiveMin();

    /**
     * Name: Exclusive Maximum
     *
     * The inclusiveMax element determines the maximum allowable value for data
     * entry editing purposes. Value can be an integer or decimal value such as
     * -.001 or 99.
     *
     * JSTL: This field is mapped into the field named "exclusiveMax".
     */
    public String getInclusiveMax();

    /**
     * Name: Display Label Attribute
     *
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
     * Name: Unique
     *
     * Similar to a db column that is flagged as unique, the value is unique
     * across all data objects of that type
     *
     * TODO: ask if this should be isUnique instead of getUnique?
     */
    public Boolean isUnique();

    /**
     * Name: Custom Validator Class
     *
     * Class to do custom validation
     */
    public String getCustomValidatorClass();

    /**
     * Name: Formatter Class
     *
     * The formatterClass element is used when custom formatting is required for
     * display of the field value. This field specifies the name of the java
     * class to be used for the formatting. About 15 different classes are
     * available including BooleanFormatter, CurrencyFormatter, DateFormatter,
     * etc.
     */
    public String getFormatterClass();

    /**
     * Name: Valid Characters Constraint
     * 
     * The constraint that applies regular expressions to to the value
     *
     * TODO: Ask RICE to create an interface for ValidCharactersConstraint so we can return that instead of the generic constraint
     */
    public ValidCharactersConstraintInfc getValidCharactersConstraint();
}
