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
	private String routeNodeName;
	
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

	/**
	 * @return the routeNodeName
	 */
	public String getRouteNodeName() {
		return routeNodeName;
	}

	/**
	 * @param routeNodeName the routeNodeName to set
	 */
	public void setRouteNodeName(String routeNodeName) {
		this.routeNodeName = routeNodeName;
	}
}
