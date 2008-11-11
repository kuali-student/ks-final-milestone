package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Search criteria for Learning Unit Instances.  Description comes from CLU
 * and can have % as wildcard matching.  LuTypeKey is the actual LuType and
 * does not support wildcards.  If both parameters have values then
 * the fields are used as an AND.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LuiCriteria implements Serializable {

	private static final long serialVersionUID = -2109862706103417878L;
	
	@XmlElement
	private String luTypeKey;
	
	@XmlElement
	private String description;

	public String getLuTypeKey() {
		return luTypeKey;
	}

	public void setLuTypeKey(String luTypeKey) {
		this.luTypeKey = luTypeKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

}
