package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluSetDisplay implements Serializable {

	private static final long serialVersionUID = -3197747684258490939L;

	@XmlElement
	private String cluSetId;
	@XmlElement
	private String cluSetName;

	/**
	 * @return the cluSetId
	 */
	public String getCluSetId() {
		return cluSetId;
	}

	/**
	 * @param cluSetId
	 *            the cluSetId to set
	 */
	public void setCluSetId(String cluSetId) {
		this.cluSetId = cluSetId;
	}

	/**
	 * @return the cluSetName
	 */
	public String getCluSetName() {
		return cluSetName;
	}

	/**
	 * @param cluSetName
	 *            the cluSetName to set
	 */
	public void setCluSetName(String cluSetName) {
		this.cluSetName = cluSetName;
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
		result = prime * result
				+ ((cluSetId == null) ? 0 : cluSetId.hashCode());
		result = prime * result
				+ ((cluSetName == null) ? 0 : cluSetName.hashCode());
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
		final CluSetDisplay other = (CluSetDisplay) obj;
		if (cluSetId == null) {
			if (other.cluSetId != null)
				return false;
		} else if (!cluSetId.equals(other.cluSetId))
			return false;
		if (cluSetName == null) {
			if (other.cluSetName != null)
				return false;
		} else if (!cluSetName.equals(other.cluSetName))
			return false;
		return true;
	}
}
