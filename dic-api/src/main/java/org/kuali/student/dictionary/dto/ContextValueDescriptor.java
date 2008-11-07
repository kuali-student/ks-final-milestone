package org.kuali.student.dictionary.dto;

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
public class ContextValueDescriptor {
    @XmlElement(required = true)
    protected String desc;
    @XmlElement(required = true)
    protected String dataType;
    protected String minValue;
    protected String maxValue;
    protected int minLength = -1;
    protected int maxLength = -1;
    protected String validChars;
    protected String invalidChars;
    protected int minOccurs = -1;
    protected int maxOccurs = -1;

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
     *     {@link int }
     *     
     */
    public int getMinLength() {
        return minLength;
    }

    /**
     * Sets the value of the minLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link int }
     *     
     */
    public void setMinLength(int value) {
        this.minLength = value;
    }

    /**
     * Gets the value of the maxLength property.
     * 
     * @return
     *     possible object is
     *     {@link int }
     *     
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the value of the maxLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link int }
     *     
     */
    public void setMaxLength(int value) {
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
     *     {@link int }
     *     
     */
    public int getMinOccurs() {
        return minOccurs;
    }

    /**
     * Sets the value of the minOccurs property.
     * 
     * @param value
     *     allowed object is
     *     {@link int }
     *     
     */
    public void setMinOccurs(int value) {
        this.minOccurs = value;
    }

    /**
     * Gets the value of the maxOccurs property.
     * 
     * @return
     *     possible object is
     *     {@link int }
     *     
     */
    public int getMaxOccurs() {
        return maxOccurs;
    }

    /**
     * Sets the value of the maxOccurs property.
     * 
     * @param value
     *     allowed object is
     *     {@link int }
     *     
     */
    public void setMaxOccurs(int value) {
        this.maxOccurs = value;
    }

}
