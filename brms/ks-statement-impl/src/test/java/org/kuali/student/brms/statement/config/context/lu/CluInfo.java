package org.kuali.student.brms.statement.config.context.lu;

public class CluInfo {
	private String id;
	private String code;
	private String shortName;
	private String longName;
	
	public CluInfo() {
	}

	public CluInfo(String id, String code, String shortName, String longName) {
		this.id = id;
		this.code = code;
		this.shortName = shortName;
		this.longName = longName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	@Override
	public String toString() {
		return "CluInfo[id=" + id + ", code=" + code + "]";
	}
}
