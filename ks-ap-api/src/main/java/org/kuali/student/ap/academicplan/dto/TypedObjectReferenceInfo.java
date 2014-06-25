package org.kuali.student.ap.academicplan.dto;

import java.io.Serializable;
import java.util.List;

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
