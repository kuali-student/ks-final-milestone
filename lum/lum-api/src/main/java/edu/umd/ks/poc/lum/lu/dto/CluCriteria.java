package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.poc.common.ws.binding.JaxbAttributeMapListAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluCriteria implements Serializable {

	private static final long serialVersionUID = -2109861706103417878L;
	@XmlElement
	private String luTypeKey;
	@XmlElement
	@XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
	private Map<String, String> searchKeyValue;

	/**
	 * @return the luTypeKey
	 */
	public String getLuTypeKey() {
		return luTypeKey;
	}

	/**
	 * @param luTypeKey
	 *            the luTypeKey to set
	 */
	public void setLuTypeKey(String luTypeKey) {
		this.luTypeKey = luTypeKey;
	}

	/**
	 * @return the searchKeyValue
	 */
	public Map<String, String> getSearchKeyValue() {
		return searchKeyValue;
	}

	/**
	 * @param searchKeyValue
	 *            the searchKeyValue to set
	 */
	public void setSearchKeyValue(Map<String, String> searchKeyValue) {
		this.searchKeyValue = searchKeyValue;
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
				+ ((luTypeKey == null) ? 0 : luTypeKey.hashCode());
		result = prime * result
				+ ((searchKeyValue == null) ? 0 : searchKeyValue.hashCode());
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
		final CluCriteria other = (CluCriteria) obj;
		if (luTypeKey == null) {
			if (other.luTypeKey != null)
				return false;
		} else if (!luTypeKey.equals(other.luTypeKey))
			return false;
		if (searchKeyValue == null) {
			if (other.searchKeyValue != null)
				return false;
		} else if (!searchKeyValue.equals(other.searchKeyValue))
			return false;
		return true;
	}
}
