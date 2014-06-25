package org.kuali.student.ap.academicplan.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.ap.academicplan.infc.PlaceholderInstance;
import org.w3c.dom.Element;

/**
 * Placeholder instance message structure
 * 
 * @Author mguilla Date: 1/21/14
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlaceholderInstanceInfo", propOrder = { "id", "placeholderId",
		"refObjectId", "refObjectType", "advisorId", "advisorOK", "studentOK",
		"_futureElements" })
public class PlaceholderInstanceInfo implements PlaceholderInstance {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 6019908398241577527L;

	@XmlAttribute
	private String id;

	@XmlAttribute
	private String placeholderId;

	@XmlElement
	private String refObjectId;

	@XmlElement
	private String refObjectType;

	@XmlElement
	private String advisorId;

	@XmlElement
	private boolean advisorOK;

	@XmlElement
	private boolean studentOK;

	@XmlAnyElement
	private List<Element> _futureElements;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlaceholderId() {
		return placeholderId;
	}

	public void setPlaceholderId(String placeholderId) {
		this.placeholderId = placeholderId;
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

	public String getAdvisorId() {
		return advisorId;
	}

	public void setAdvisorId(String advisorId) {
		this.advisorId = advisorId;
	}

	public boolean isAdvisorOK() {
		return advisorOK;
	}

	public void setAdvisorOK(boolean advisorOK) {
		this.advisorOK = advisorOK;
	}

	public boolean isStudentOK() {
		return studentOK;
	}

	public void setStudentOK(boolean studentOK) {
		this.studentOK = studentOK;
	}

	public List<Element> get_futureElements() {
		return _futureElements;
	}

	public void set_futureElements(List<Element> _futureElements) {
		this._futureElements = _futureElements;
	}

}
