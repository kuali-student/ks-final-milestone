package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class AtpDisplay implements Serializable {

	private static final long serialVersionUID = 4353066782978375768L;
	@XmlAttribute
	private String atpId;
	@XmlAttribute
	private String atpName;

	/**
	 * @return the atpId
	 */
	public String getAtpId() {
		return atpId;
	}

	/**
	 * @param atpId
	 *            the atpId to set
	 */
	public void setAtpId(String atpId) {
		this.atpId = atpId;
	}

	/**
	 * @return the atpName
	 */
	public String getAtpName() {
		return atpName;
	}

	/**
	 * @param atpName
	 *            the atpName to set
	 */
	public void setAtpName(String atpName) {
		this.atpName = atpName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atpId == null) ? 0 : atpId.hashCode());
		result = prime * result + ((atpName == null) ? 0 : atpName.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AtpDisplay other = (AtpDisplay) obj;
		if (atpId == null) {
			if (other.atpId != null)
				return false;
		} else if (!atpId.equals(other.atpId))
			return false;
		if (atpName == null) {
			if (other.atpName != null)
				return false;
		} else if (!atpName.equals(other.atpName))
			return false;
		return true;
	}
}
