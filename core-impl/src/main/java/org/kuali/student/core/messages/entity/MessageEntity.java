package org.kuali.student.core.messages.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.kuali.student.common.util.UUIDHelper;

@Entity
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
