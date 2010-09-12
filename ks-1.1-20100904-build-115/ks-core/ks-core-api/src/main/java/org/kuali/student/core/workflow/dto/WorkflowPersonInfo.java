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

package org.kuali.student.core.workflow.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WorkflowPersonInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String principalId;
	private String firstName;
	private String lastName;
	
	private List<String> permList;
	private List<String> actionList;
	
	private boolean canRevokeRequest = false;
	private String actionRequestStatus;
	
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<String> getPermList() {
		if(permList == null){
			permList = new ArrayList<String>();
		}
		return permList;
	}
	public void setPermList(List<String> permList) {
		this.permList = permList;
	}
	public List<String> getActionList() {
		if(actionList == null){
			actionList = new ArrayList<String>();
		}
		return actionList;
	}
	public void setActionList(List<String> actionList) {
		this.actionList = actionList;
	}
	public boolean isCanRevokeRequest() {
    	return canRevokeRequest;
    }
	public void setCanRevokeRequest(boolean canRevokeRequest) {
    	this.canRevokeRequest = canRevokeRequest;
    }
	public String getActionRequestStatus() {
    	return actionRequestStatus;
    }
	public void setActionRequestStatus(String actionRequestStatus) {
    	this.actionRequestStatus = actionRequestStatus;
    }
	

}
