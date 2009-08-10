package org.kuali.student.core.dictionary.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;


@XmlAccessorType(XmlAccessType.FIELD)
public class LookupKeyConstraint implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    protected String field;
    
    @XmlAttribute
    protected String mapsTo;

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the mapsTo
	 */
	public String getMapsTo() {
		return mapsTo;
	}

	/**
	 * @param mapsTo the mapsTo to set
	 */
	public void setMapsTo(String mapsTo) {
		this.mapsTo = mapsTo;
	}   
}
