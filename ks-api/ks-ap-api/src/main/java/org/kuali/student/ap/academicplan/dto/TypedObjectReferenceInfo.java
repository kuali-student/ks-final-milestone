/*
 * Copyright 2014 The Kuali Foundation Licensed under the
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
package org.kuali.student.ap.academicplan.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.ap.academicplan.infc.TypedObjectReference;
import org.w3c.dom.Element;

/**
 * Reference Object List message Structure
 * 
 * @Author mguilla Date: 1/21/14
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypedObjectReferenceInfo", propOrder = { "id", "refObjectId",
		"refObjectType", "_futureElements" })
public class TypedObjectReferenceInfo implements TypedObjectReference,
		Serializable {

	private static final long serialVersionUID = 6019908398241577527L;

	@XmlAttribute
	private String id;

	@XmlElement
	private String refObjectId;

	@XmlElement
	private String refObjectType;

	@XmlAnyElement
	private List<Element> _futureElements;

	public TypedObjectReferenceInfo() {
	}

	public TypedObjectReferenceInfo(String refObjectType, String refObjectId) {
		this.id = UUID.randomUUID().toString();
		this.refObjectId = refObjectId;
		this.refObjectType = refObjectType;
	}

	public TypedObjectReferenceInfo(TypedObjectReference copy) {
		id = copy.getId();
		refObjectId = copy.getRefObjectId();
		refObjectType = copy.getRefObjectType();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefObjectId() {
		return refObjectId;
	}

	public void setRefObjectId(String refObjectId) {
		this.refObjectId = refObjectId;
	}

	public String getRefObjectType() {
		return refObjectType;
	}

	public void setRefObjectType(String refObjectType) {
		this.refObjectType = refObjectType;
	}

}
