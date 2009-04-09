package org.kuali.student.common.ui.client.dto;

public class HelpInfo {
	
	private String id;
	private String url;
	private String shortVersion;
	private String title;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getShortVersion() {
		return shortVersion;
	}
	public void setShortVersion(String shortVersion) {
		this.shortVersion = shortVersion;
	}
	
	
}
