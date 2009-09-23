package org.kuali.student.lum.lu.dto.workflow;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CluProposalCollabRequestDocInfo implements Serializable {

	private static final long serialVersionUID = 4870488293006448501L;
	private String cluId;
	@XmlElement(name = "PrincipalIdRoleAttribute")
	private PrincipalIdRoleAttribute principalIdRoleAttribute;
	private String principalId;
	private String docId;
	private String collaboratorType;
	private boolean participationRequired;
	private String respondBy;

	public String getCluId() {
		return cluId;
	}

	public void setCluId(String cluId) {
		this.cluId = cluId;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getCollaboratorType() {
		return collaboratorType;
	}

	public void setCollaboratorType(String collaboratorType) {
		this.collaboratorType = collaboratorType;
	}

	public boolean getParticipationRequired() {
		return participationRequired;
	}

	public void setParticipationRequired(boolean participationRequired) {
		this.participationRequired = participationRequired;
	}

	public String getRespondBy() {
		return respondBy;
	}

	public void setRespondBy(String respondBy) {
		this.respondBy = respondBy;
	}

	public PrincipalIdRoleAttribute getPrincipalIdRoleAttribute() {
		return principalIdRoleAttribute;
	}

	public void setPrincipalIdRoleAttribute(
			PrincipalIdRoleAttribute principalIdRoleAttribute) {
		this.principalIdRoleAttribute = principalIdRoleAttribute;
	}
}
