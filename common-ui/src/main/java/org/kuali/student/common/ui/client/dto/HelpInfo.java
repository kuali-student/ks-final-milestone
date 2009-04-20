package org.kuali.student.common.ui.client.dto;

public class HelpInfo {
	
	private String id;
	private String url;
	private String shortVersion;
	private String title;
	
	
	public String getId() {
		return id;
	}
	public HelpInfo setId(String id) {
		this.id = id;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public HelpInfo setTitle(String title) {
		this.title = title;
		return this;
	}
	public String getUrl() {
		return url;
	}
	public HelpInfo setUrl(String url) {
		this.url = url;
		return this;
	}
	public String getShortVersion() {
		return shortVersion;
	}
	public HelpInfo setShortVersion(String shortVersion) {
		this.shortVersion = shortVersion;
		return this;
	}
	
	
}
