/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.core.enumerationmanagement.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/**
 *Describes a "field" or simple type within a larger object.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class FieldDescriptorInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String name;

    @XmlElement
    private String desc;

    @XmlElement
    private String dataType;

    @XmlElement
    private Integer minLength;

    @XmlElement
    private String maxLength;

    @XmlElement
    private String validChars;

    @XmlElement
    private String invalidChars;

    @XmlElement
    private String minValue;

    @XmlElement
    private String maxValue;

    @XmlElement
    private EnumFieldViewInfo enumFieldView;

    @XmlElement
    private Boolean readOnly;

    /**
     * Friendly name for the field.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Narrative description for the field.
     */
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * The Data Type value indicates the type for a given field. This is currently assumed to be a primitive type (string, int, date, etc.).
     */
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * Primarily used for string data types, the Minimum Length value establishes a lower bound on the length of the string. A 0 length indicates an empty string is allowed. Must be less than or equal to the maxLength value, if specified.
     */
    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    /**
     * Primarily used for string data types, the Maximum Length value establishes an upper bound on the length of the string. The values of this field are restricted to integer values and the string "unbounded". Must be greater than or equal to the minLength value, if specified. "Unbounded" is automatically considered to meet this condition.
     */
    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * Primarily used for string data types, Valid Characters acts as a white list - only the characters which are specified should be used in the value. In general, this field should not be specified if invalidChars is also specified.
     */
    public String getValidChars() {
        return validChars;
    }

    public void setValidChars(String validChars) {
        this.validChars = validChars;
    }

    /**
     * Primarily used for string data types, Invalid Characters acts as a black list - the specified characters should not be used in the value. In general, this field should not be specified if invalidChars is also specified.
     */
    public String getInvalidChars() {
        return invalidChars;
    }

    public void setInvalidChars(String invalidChars) {
        this.invalidChars = invalidChars;
    }

    /**
     * Primarily used for numeric and time-related data types, this value establishes a lower bound on the value of the field. Must be less than or equal to the maxValue value, if specified.
     */
    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    /**
     * Primarily used for numeric and time-related data types, this value establishes an upper bound on the value of the field. Must be greater than or equal to the minValue value, if specified.
     */
    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Description of an enumeration as it relates to a particular field.
     */
    public EnumFieldViewInfo getEnumFieldView() {
        return enumFieldView;
    }

    public void setEnumFieldView(EnumFieldViewInfo enumFieldView) {
        this.enumFieldView = enumFieldView;
    }

    /**
     * Indicates if the field is read only. This field should not be used when describing parameters, such as in named searches, etc.
     */
    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }
    
    public Map<String, Object> toMap(){
    	Map<String, Object> fieldDescMap = new HashMap<String, Object>();
    	fieldDescMap.put("maxValue", maxValue);
    	fieldDescMap.put("maxLength", maxLength);
    	fieldDescMap.put("minValue", minValue);
    	fieldDescMap.put("minLength", minLength);
    	fieldDescMap.put("validChars", validChars);
    	fieldDescMap.put("invalidChars", invalidChars);
    	fieldDescMap.put("dataType", dataType);
    	return fieldDescMap;
    }
}
