package org.kuali.student.brms.statement.config.context.lu;

public class CluInfo {
	private String id;
	private String code;
	private String shortName;
	private String longName;
	
	public CluInfo(String id, String code, String shortName, String longName) {
		this.id = id;
		this.code = code;
		this.shortName = shortName;
		this.longName = longName;
	}

	public String getId() {
		return id;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getShortName() {
		return this.shortName;
	}
	
	public String getLongName() {
		return this.longName;
	}

	@Override
	public String toString() {
		return "CluInfo[id=" + id + ", code=" + code + "]";
	}
}
