package org.kuali.student.common.ui.client.widgets.field.layout.element;

public class MessageKeyInfo {
	private String group;
	private String type;
	private String state;
	private String id;
	
	public MessageKeyInfo(String id){
		this.id = id;
	}
	
	public MessageKeyInfo(String group, String type, String state, String id) {
		this.group = group;
		this.type = type;
		this.state = state;
		this.id = id;
	}
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
