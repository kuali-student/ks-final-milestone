package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class LuiRelationDisplay implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private LuiDisplay luiDisplay;
	@XmlElement
	private LuiDisplay relatedLuiDisplay;
	@XmlElement
	private String luRelationTypeId;

	/**
	 * @return the luiDisplay
	 */
	public LuiDisplay getLuiDisplay() {
		return luiDisplay;
	}

	/**
	 * @param luiDisplay
	 *            the luiDisplay to set
	 */
	public void setLuiDisplay(LuiDisplay luiDisplay) {
		this.luiDisplay = luiDisplay;
	}

	/**
	 * @return the relatedLuiDisplay
	 */
	public LuiDisplay getRelatedLuiDisplay() {
		return relatedLuiDisplay;
	}

	/**
	 * @param relatedLuiDisplay
	 *            the relatedLuiDisplay to set
	 */
	public void setRelatedLuiDisplay(LuiDisplay relatedLuiDisplay) {
		this.relatedLuiDisplay = relatedLuiDisplay;
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
		result = prime
				* result
				+ ((luRelationTypeId == null) ? 0 : luRelationTypeId.hashCode());
		result = prime * result
				+ ((luiDisplay == null) ? 0 : luiDisplay.hashCode());
		result = prime
				* result
				+ ((relatedLuiDisplay == null) ? 0 : relatedLuiDisplay
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
		final LuiRelationDisplay other = (LuiRelationDisplay) obj;
		if (luRelationTypeId == null) {
			if (other.luRelationTypeId != null)
				return false;
		} else if (!luRelationTypeId.equals(other.luRelationTypeId))
			return false;
		if (luiDisplay == null) {
			if (other.luiDisplay != null)
				return false;
		} else if (!luiDisplay.equals(other.luiDisplay))
			return false;
		if (relatedLuiDisplay == null) {
			if (other.relatedLuiDisplay != null)
				return false;
		} else if (!relatedLuiDisplay.equals(other.relatedLuiDisplay))
			return false;
		return true;
	}
}
