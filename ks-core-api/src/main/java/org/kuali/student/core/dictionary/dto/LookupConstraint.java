package org.kuali.student.core.dictionary.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;



@XmlAccessorType(XmlAccessType.FIELD)
public class LookupConstraint implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    protected String search;

    @XmlAttribute
    protected String searchField;
        
    @XmlElement(name = "lookupKey")
    protected List<LookupKeyConstraint> lookupKey;

	/**
	 * @return the search
	 */
	public String getSearch() {
		return search;
	}

	/**
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}

	/**
	 * @return the lookupKey
	 */
	public List<LookupKeyConstraint> getLookupKey() {
		return lookupKey;
	}

	/**
	 * @param lookupKey the lookupKey to set
	 */
	public void setLookupKey(List<LookupKeyConstraint> lookupKey) {
		this.lookupKey = lookupKey;
	}

	/**
	 * @return the searchField
	 */
	public String getSearchField() {
		return searchField;
	}

	/**
	 * @param searchField the searchField to set
	 */
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}       
}
