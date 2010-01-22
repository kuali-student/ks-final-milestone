package org.kuali.student.core.statement.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class NlUsageTypeInfo implements Serializable, Idable, HasAttributes {

	private static final long serialVersionUID = 1L;
	
    @XmlElement
    private String name;

    @XmlElement
    private String desc;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes = new HashMap<String, String>();

    @XmlAttribute(name="key")
    private String id;

    /**
     * Gets the friendly name of the natural language usage type.
     * 
     * @return Name of the natural language usage type
     */
	public String getName() {
		return name;
	}

	/**
     * Sets the friendly name of the natural language usage type.
	 * 
	 * @param name Name of the natural language usage type
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets	the narrative description of the natural language usage type.
	 * 
	 * @return Description of the natural language usage type
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Sets	the narrative description of the natural language usage type.
	 * 
	 * @param desc Description of the natural language usage type
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * Gets the date and time that this natural language usage type 
	 * became effective. This is a similar concept to the effective date on 
	 * enumerated values. When an expiration date has been specified, 
	 * this field must be less than or equal to the expiration date.
	 * 
	 * @return Natural language usage type effective date
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * Sets the Date and time that this natural language usage type 
	 * became effective. This is a similar concept to the effective date on 
	 * enumerated values. When an expiration date has been specified, 
	 * this field must be less than or equal to the expiration date.
	 * 
	 * @param effectiveDate Natural language usage type effective date
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * Gets the date and time that this natural language usage type expires. 
	 * This is a similar concept to the expiration date on enumerated values. 
	 * If specified, this should be greater than or equal to the effective date. 
	 * If this field is not specified, then no expiration date has been 
	 * currently defined and should automatically be considered greater than 
	 * the effective date.
	 * 
	 * @return Natural language usage type expiration date
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * Sets the date and time that this natural language usage type expires. 
	 * This is a similar concept to the expiration date on enumerated values. 
	 * If specified, this should be greater than or equal to the effective date. 
	 * If this field is not specified, then no expiration date has been 
	 * currently defined and should automatically be considered greater than 
	 * the effective date.
	 * 
	 * @param expirationDate Natural language usage type expiration date
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * Gets the list of key/value pairs, typically used for dynamic attributes.
	 * 
	 * @return Map of attributes
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * Sets the list of key/value pairs, typically used for dynamic attributes.
	 * 
	 * @param attributes Map of attributes
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	/**
	 * Gets unique identifier for a natural language usage type.
	 * 
	 * @return Unique identifier 
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique identifier for a natural language usage type.
	 * 
	 * @param id Unique identifier 
	 */
	public void setId(String id) {
		this.id = id;
	}
}
