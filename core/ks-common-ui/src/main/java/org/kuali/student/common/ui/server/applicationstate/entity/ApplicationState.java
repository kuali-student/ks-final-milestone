/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.server.applicationstate.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name = "KSAP_APP_STATE_T", 
       uniqueConstraints={@UniqueConstraint(columnNames={"APPLICATION_ID", "REFERENCE_KEY", "REFERENCE_TYPE", "USER_ID"})})
@NamedQueries( {
        @NamedQuery(name = "ApplicationState.getApplicationStateByAppRefUserId", query = "SELECT appState FROM ApplicationState appState WHERE appState.applicationId =:applicationId AND appState.referenceKey =:referenceKey AND appState.referenceType =:referenceType AND appState.userId =:userId")
} )
public class ApplicationState {

	@Id
    @Column(name = "ID")
	private String id;
	
	@Column(name = "APPLICATION_ID", nullable=false)
	private String applicationId;

	@Column(name = "REFERENCE_KEY", nullable=false)
	private String referenceKey;

	@Column(name = "REFERENCE_TYPE", nullable=false)
	private String referenceType;

	@Column(name = "USER_ID", nullable=false)
	private String userId;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="KS_APP_ST_JN_KEY_VALUE")	
	private List<KeyValuePair> keyValueList;

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
	public String getReferenceKey() {
		return referenceKey;
	}
	
	public void setReferenceKey(String referenceKey) {
		this.referenceKey = referenceKey;
	}
	
	public String getReferenceType() {
		return referenceType;
	}
	
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<KeyValuePair> getKeyValueList() {
		return keyValueList;
	}

	public void setKeyValueList(List<KeyValuePair> keyValueList) {
		this.keyValueList = keyValueList;
	}

	@Override
	public String toString() {
		return "ApplicationState[id=" + id + ", applicationId=" + applicationId 
				+ ", referenceKey=" + referenceKey + ", referenceType="
				+ referenceType + ", userId=" + userId + "]";
	}
}
