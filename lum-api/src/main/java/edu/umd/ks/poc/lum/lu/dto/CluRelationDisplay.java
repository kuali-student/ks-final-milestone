package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluRelationDisplay implements Serializable {

	private static final long serialVersionUID = -8698450718245233463L;

	@XmlElement
	private CluDisplay cluDisplay;
	@XmlElement
	private CluDisplay relatedCluDisplay;
	@XmlElement
	private String luRelationTypeId;

	/**
	 * @return the cluDisplay
	 */
	public CluDisplay getCluDisplay() {
		return cluDisplay;
	}

	/**
	 * @param cluDisplay
	 *            the cluDisplay to set
	 */
	public void setCluDisplay(CluDisplay cluDisplay) {
		this.cluDisplay = cluDisplay;
	}

	/**
	 * @return the relatedCluDisplay
	 */
	public CluDisplay getRelatedCluDisplay() {
		return relatedCluDisplay;
	}

	/**
	 * @param relatedCluDisplay
	 *            the relatedCluDisplay to set
	 */
	public void setRelatedCluDisplay(CluDisplay relatedCluDisplay) {
		this.relatedCluDisplay = relatedCluDisplay;
	}

	/**
	 * @return the luRelationTypeId
	 */
	public String getLuRelationTypeId() {
		return luRelationTypeId;
	}

	/**
	 * @param luRelationTypeId
	 *            the luRelationTypeId to set
	 */
	public void setLuRelationTypeId(String luRelationTypeId) {
		this.luRelationTypeId = luRelationTypeId;
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
				+ ((cluDisplay == null) ? 0 : cluDisplay.hashCode());
		result = prime
				* result
				+ ((luRelationTypeId == null) ? 0 : luRelationTypeId.hashCode());
		result = prime
				* result
				+ ((relatedCluDisplay == null) ? 0 : relatedCluDisplay
						.hashCode());
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
		final CluRelationDisplay other = (CluRelationDisplay) obj;
		if (cluDisplay == null) {
			if (other.cluDisplay != null)
				return false;
		} else if (!cluDisplay.equals(other.cluDisplay))
			return false;
		if (luRelationTypeId == null) {
			if (other.luRelationTypeId != null)
				return false;
		} else if (!luRelationTypeId.equals(other.luRelationTypeId))
			return false;
		if (relatedCluDisplay == null) {
			if (other.relatedCluDisplay != null)
				return false;
		} else if (!relatedCluDisplay.equals(other.relatedCluDisplay))
			return false;
		return true;
	}
}
