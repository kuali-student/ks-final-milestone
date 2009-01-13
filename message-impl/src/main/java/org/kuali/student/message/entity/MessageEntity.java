package org.kuali.student.message.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
public class MessageEntity {
	 @Id
	 private String id; 
	 private String locale;
	 private String groupName;
	 private String value;
	 
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
}
