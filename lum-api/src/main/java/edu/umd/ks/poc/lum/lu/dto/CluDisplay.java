package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluDisplay implements Serializable {

	private static final long serialVersionUID = -261520681330575290L;
	@XmlAttribute
	private String cluId;
	@XmlElement
	private String luTypeId;
	@XmlElement
	private String cluShortName;
	//Removed because there is no corresponding cluCode on info or create
	//@XmlElement
	//private String cluCode;
	@XmlElement
	private AtpDisplay atpDisplayStart;
	@XmlElement
	private AtpDisplay atpDisplayEnd;

	/**
	 * @return the cluId
	 */
	public String getCluId() {
		return cluId;
	}

	/**
	 * @param cluId
	 *            the cluId to set
	 */
	public void setCluId(String cluId) {
		this.cluId = cluId;
	}

	/**
	 * @return the luTypeId
	 */
	public String getLuTypeId() {
		return luTypeId;
	}

	/**
	 * @param luTypeId
	 *            the luTypeId to set
	 */
	public void setLuTypeId(String luTypeId) {
		this.luTypeId = luTypeId;
	}

	/**
	 * @return the cluShortName
	 */
	public String getCluShortName() {
		return cluShortName;
	}

	/**
	 * @param cluShortName
	 *            the cluShortName to set
	 */
	public void setCluShortName(String cluShortName) {
		this.cluShortName = cluShortName;
	}

//	/**
//	 * @return the cluCode
//	 */
//	public String getCluCode() {
//		return cluCode;
//	}
//
//	/**
//	 * @param cluCode
//	 *            the cluCode to set
//	 */
//	public void setCluCode(String cluCode) {
//		this.cluCode = cluCode;
//	}

	/**
	 * @return the atpDisplayStart
	 */
	public AtpDisplay getAtpDisplayStart() {
		return atpDisplayStart;
	}

	/**
	 * @param atpDisplayStart
	 *            the atpDisplayStart to set
	 */
	public void setAtpDisplayStart(AtpDisplay atpDisplayStart) {
		this.atpDisplayStart = atpDisplayStart;
	}

	/**
	 * @return the atpDisplayEnd
	 */
	public AtpDisplay getAtpDisplayEnd() {
		return atpDisplayEnd;
	}

	/**
	 * @param atpDisplayEnd
	 *            the atpDisplayEnd to set
	 */
	public void setAtpDisplayEnd(AtpDisplay atpDisplayEnd) {
		this.atpDisplayEnd = atpDisplayEnd;
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
				+ ((atpDisplayEnd == null) ? 0 : atpDisplayEnd.hashCode());
		result = prime * result
				+ ((atpDisplayStart == null) ? 0 : atpDisplayStart.hashCode());
		//result = prime * result + ((cluCode == null) ? 0 : cluCode.hashCode());
		result = prime * result + ((cluId == null) ? 0 : cluId.hashCode());
		result = prime * result
				+ ((cluShortName == null) ? 0 : cluShortName.hashCode());
		result = prime * result
				+ ((luTypeId == null) ? 0 : luTypeId.hashCode());
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
		final CluDisplay other = (CluDisplay) obj;
		if (atpDisplayEnd == null) {
			if (other.atpDisplayEnd != null)
				return false;
		} else if (!atpDisplayEnd.equals(other.atpDisplayEnd))
			return false;
		if (atpDisplayStart == null) {
			if (other.atpDisplayStart != null)
				return false;
		} else if (!atpDisplayStart.equals(other.atpDisplayStart))
			return false;
//		if (cluCode == null) {
//			if (other.cluCode != null)
//				return false;
//		} else if (!cluCode.equals(other.cluCode))
//			return false;
		if (cluId == null) {
			if (other.cluId != null)
				return false;
		} else if (!cluId.equals(other.cluId))
			return false;
		if (cluShortName == null) {
			if (other.cluShortName != null)
				return false;
		} else if (!cluShortName.equals(other.cluShortName))
			return false;
		if (luTypeId == null) {
			if (other.luTypeId != null)
				return false;
		} else if (!luTypeId.equals(other.luTypeId))
			return false;
		return true;
	}

}
