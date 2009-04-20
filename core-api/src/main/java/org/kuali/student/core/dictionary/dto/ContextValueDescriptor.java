package org.kuali.student.core.dictionary.dto;

	import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
	import javax.xml.bind.annotation.XmlAccessorType;
	import javax.xml.bind.annotation.XmlElement;
	import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "desc",
	"dataType",
	"minValue",
	"maxValue",
    "minLength",
    "maxLength",
    "validChars",
    "invalidChars",
    "minOccurs",
    "maxOccurs"
})
@XmlRootElement(name = "contextValueDescriptor")
public class ContextValueDescriptor implements Serializable{
          
    private static final long serialVersionUID = 1L;
    
    @XmlElement(required = true)
    protected String desc;
    @XmlElement(required = true)
    protected String dataType;
    protected String minValue;
    protected String maxValue;
    protected Integer minLength;
    protected Integer maxLength;
    protected String validChars;
    protected String invalidChars;
    protected Integer minOccurs;
    protected Integer maxOccurs;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
    
    /**
     * Gets the value of the dataType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataType(String value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the minLength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinLength() {
        return minLength;
    }

    /**
     * Sets the value of the minLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinLength(Integer value) {
        this.minLength = value;
    }

    /**
     * Gets the value of the maxLength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the value of the maxLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxLength(Integer value) {
        this.maxLength = value;
    }

    /**
     * Gets the value of the validChars property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidChars() {
        return validChars;
    }

    /**
     * Sets the value of the validChars property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidChars(String value) {
        this.validChars = value;
    }

    /**
     * Gets the value of the invalidChars property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvalidChars() {
        return invalidChars;
    }

    /**
     * Sets the value of the invalidChars property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvalidChars(String value) {
        this.invalidChars = value;
    }

    /**
     * Gets the value of the minOccurs property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinOccurs() {
        return minOccurs;
    }

    /**
     * Sets the value of the minOccurs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinOccurs(Integer value) {
        this.minOccurs = value;
    }

    /**
     * Gets the value of the maxOccurs property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxOccurs() {
        return maxOccurs;
    }

    /**
     * Sets the value of the maxOccurs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxOccurs(Integer value) {
        this.maxOccurs = value;
    }
    
    public Map<String, Object> toMap(){
    	Map<String, Object> fieldDescMap = new HashMap<String, Object>();
    	fieldDescMap.put("maxOccurs", maxOccurs);
    	fieldDescMap.put("maxValue", maxValue);
    	fieldDescMap.put("maxLength", maxLength);
    	fieldDescMap.put("minOccurs", minOccurs);
    	fieldDescMap.put("minValue", minValue);
    	fieldDescMap.put("minLength", minLength);
    	fieldDescMap.put("validChars", validChars);
    	fieldDescMap.put("invalidChars", invalidChars);
    	fieldDescMap.put("dataType", dataType);
    	return fieldDescMap;
    }

}
