package org.kuali.student.lum.lu.bo;

import javax.persistence.Column;

import org.kuali.student.core.bo.KsBusinessObjectBase;

public class CluIdentifier extends KsBusinessObjectBase {

	private static final long serialVersionUID = -3231108861178451995L;

	@Column(name = "CD")
    private String code;

    @Column(name = "SHRT_NAME")
    private String shortName;

    @Column(name = "LNG_NAME")
    private String longName;

    @Column(name = "LVL")
    private String level;

    @Column(name = "DIV")
    private String division;

    @Column(name = "VARTN")
    private String variation;

    @Column(name = "SUFX_CD")
    private String suffixCode;

    @Column(name = "ORG_ID")
    private String orgId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "ST")
    private String state;

    
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getVariation() {
		return variation;
	}

	public void setVariation(String variation) {
		this.variation = variation;
	}

	public String getSuffixCode() {
		return suffixCode;
	}

	public void setSuffixCode(String suffixCode) {
		this.suffixCode = suffixCode;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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

}
