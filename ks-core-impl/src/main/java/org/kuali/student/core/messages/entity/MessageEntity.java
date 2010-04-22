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

package org.kuali.student.core.messages.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name = "KSMG_MESSAGE")
public class MessageEntity {
	 
	 @Id
	 private String databaseId;
	 private String id; 
	 private String locale;
	 private String groupName;
	 private String value;
	 
    @PrePersist
    public void prePersist() {
        this.databaseId = UUIDHelper.genStringUUID();
    }
	 
	 public String getLocale() {
		 return locale;
	 }
	 public void setLocale(String locale) {
		 this.locale = locale;
	 }
	 public String getGroupName() {
		 return groupName;
	 }
	 public void setGroupName(String groupName) {
		 this.groupName = groupName;
	 }
	 public String getId() {
		 return id;
	 }
	 public void setId(String id) {
		 this.id = id;
	 }
	 public String getValue() {
		 return value;
	 }
	 public void setValue(String value) {
		 this.value = value;
	 }
	public String getDatabaseId() {
		return databaseId;
	}
	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}
	 
	 
	 
	 
}
