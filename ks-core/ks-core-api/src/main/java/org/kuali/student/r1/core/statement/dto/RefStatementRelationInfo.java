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
import org.kuali.student.r1.common.dto.HasTypeState;
import org.kuali.student.r1.common.dto.Idable;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

@Deprecated
@XmlAccessorType(XmlAccessType.FIELD)
public class RefStatementRelationInfo implements Serializable, Idable, HasTypeState, HasAttributes {

	private static final long serialVersionUID = 1L;

    @XmlElement
	private String refObjectTypeKey;

    @XmlElement
	private String refObjectId;

    @XmlElement
	private String statementId;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes = new HashMap<String, String>();

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;

    /**
     * Gets the unique identifier for an object type.
     * Used to identify the type of object being referred to,
     * so that the id can be resolved.
     *
     * @return Reference object type key
     */
	public String getRefObjectTypeKey() {
		return refObjectTypeKey;
	}

	/**
     * Sets the unique identifier for an object type.
     * Used to identify the type of object being referred to,
     * so that the id can be resolved.
	 *
	 * @param refObjectTypeKey Reference object type key
	 */
	public void setRefObjectTypeKey(String refObjectTypeKey) {
		this.refObjectTypeKey = refObjectTypeKey;
	}

	/**
	 * Sets the identifier for an object.
	 * This will likely require some additional context in order to be resolved,
	 * such as the type of object. An objectId could be a cluId, a luiId,
	 * an orgId, a documentId, etc.
	 *
	 * @return Reference object id
	 */
	public String getRefObjectId() {
		return refObjectId;
	}

	/**
	 * Gets the identifier for an object.
	 * This will likely require some additional context in order to be resolved,
	 * such as the type of object. An objectId could be a cluId, a luiId,
	 * an orgId, a documentId, etc.
	 *
	 * @param refObjectId Reference object id
	 */
	public void setRefObjectId(String refObjectId) {
		this.refObjectId = refObjectId;
	}

	/**
	 * Gets the unique identifier for a single statement record.
	 *
	 * @return
	 */
	public String getStatementId() {
		return statementId;
	}

	/**
	 * Sets the unique identifier for a single statement record.
	 *
	 * @param statementId Statement id
	 */
	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

	/**
	 * Gets the date and time that this object to statement relationship
	 * became effective. This is a similar concept to the effective date on
	 * enumerated values. When an expiration date has been specified,
	 * this field must be less than or equal to the expiration date.
	 *
	 * @return Object to statement relationship effective date
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * Sets the date and time that this object to statement relationship
	 * became effective. This is a similar concept to the effective date on
	 * enumerated values. When an expiration date has been specified,
	 * this field must be less than or equal to the expiration date.
	 *
	 * @param effectiveDate Object to statement relationship effective date
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * Gets the date and time that this object to statement relationship expires.
	 * This is a similar concept to the expiration date on enumerated values.
	 * If specified, this should be greater than or equal to the effective date.
	 * If this field is not specified, then no expiration date has been
	 * currently defined and should automatically be considered greater
	 * than the effective date.
	 *
	 * @return Object to statement relationship expiration date
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * Sets the date and time that this object to statement relationship expires.
	 * This is a similar concept to the expiration date on enumerated values.
	 * If specified, this should be greater than or equal to the effective date.
	 * If this field is not specified, then no expiration date has been
	 * currently defined and should automatically be considered greater
	 * than the effective date.
	 *
	 * @param expirationDate Object to statement relationship expiration date
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
	 * Gets the create and last update info for the structure.
	 * This is optional and treated as read only since the data is set by
	 * the internals of the service during maintenance operations.
	 *
	 * @return Meta data information
	 */
	public MetaInfo getMetaInfo() {
		return metaInfo;
	}

	/**
	 * Sets the create and last update info for the structure.
	 * This is optional and treated as read only since the data is set by
	 * the internals of the service during maintenance operations.
	 *
	 * @param metaInfo Meta data information
	 */
	public void setMetaInfo(MetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}

	/**
	 * Gets the object to statement relation type.
	 *
	 * @return Object to statement relation type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the object to statement relation type.
	 *
	 * @param type Object to statement relation type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the identifier for the current status of the object to statement
	 * relationship. The values for this field are constrained to those in
	 * the refStatementRelationState enumeration. A separate setup operation
	 * does not exist for retrieval of the meta data around this value.
	 *
	 * @return Object to statement relation state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the identifier for the current status of the object to statement
	 * relationship. The values for this field are constrained to those in
	 * the refStatementRelationState enumeration. A separate setup operation
	 * does not exist for retrieval of the meta data around this value.
	 *
	 * @param state Object to statement relation state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the unique identifier for a single Object Statement Relationship record.
	 *
	 * @return Object to Statement Relation Identifier
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique identifier for a single Object Statement Relationship record.
	 *
	 * @param id Object to statement relation identifier
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "RefStatementRelationInfo[id=" + id + ", type=" + type
				+ ", statementId=" + statementId + ", refObjectId="
				+ refObjectId + ", refObjectTypeKey=" + refObjectTypeKey + "]";
	}
}
