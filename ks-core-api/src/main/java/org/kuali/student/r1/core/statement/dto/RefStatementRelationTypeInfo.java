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

package org.kuali.student.r1.core.statement.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.r1.common.dto.HasAttributes;
import org.kuali.student.r1.common.dto.Idable;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

@Deprecated
@XmlAccessorType(XmlAccessType.FIELD)
public class RefStatementRelationTypeInfo implements Serializable, Idable, HasAttributes {

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
     * Gets the friendly name of the Object Statement Relation type.
     * 
     * @return Statement relation type name
     */
    public String getName() {
		return name;
	}

    /**
     * Sets the friendly name of the Object Statement Relation type.
     * 
     * @param name Statement relation type name
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the narrative description of the Object Statement Relation.
	 * 
	 * @return Statement relation description
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Sets the narrative description of the Object Statement Relation.
	 * 
	 * @param desc Object statement relation description
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * Gets the Date and time that this object statement relation type 
	 * became effective. This is a similar concept to the effective date on 
	 * enumerated values. When an expiration date has been specified, 
	 * this field must be less than or equal to the expiration date.
	 * 
	 * @return Statement relation type effective date
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * Sets the Date and time that this object statement relation type 
	 * became effective. This is a similar concept to the effective date on 
	 * enumerated values. When an expiration date has been specified, 
	 * this field must be less than or equal to the expiration date.
	 * 
	 * @param effectiveDate Statement relation type effective date
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * Sets the date and time that this object statement relation type expires. 
	 * This is a similar concept to the expiration date on enumerated values. 
	 * If specified, this should be greater than or equal to the effective date. 
	 * If this field is not specified, then no expiration date has been 
	 * currently defined and should automatically be considered greater than 
	 * the effective date.
	 * 
	 * @return Statement relation type expiration date
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * Gets the date and time that this object statement relation type expires. 
	 * This is a similar concept to the expiration date on enumerated values. 
	 * If specified, this should be greater than or equal to the effective date. 
	 * If this field is not specified, then no expiration date has been 
	 * currently defined and should automatically be considered greater than 
	 * the effective date.
	 * 
	 * @param expirationDate Statement relation type expiration date
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
	 * Gets the unique identifier for an object statement relation type.
	 * 
	 * @return Unique identifier 
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique identifier for an object statement relation type.
	 * 
	 * @param id Unique identifier 
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "RefStatementRelationTypeInfo[id=" + id + "]";
	}
}
