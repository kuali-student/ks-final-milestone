package edu.umd.ks.poc.lum.lu.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class CluCrit {
	@Id
	private String id;
	private String luTypeKey;
	@OneToOne
	private CluSet cluSet;
	@OneToMany(cascade=CascadeType.ALL)
	private Set<SearchKeyValue> searchKeyValues;

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}

	/**
	 * @return the searchKeyValues
	 */
	public Set<SearchKeyValue> getSearchKeyValues() {
		if (searchKeyValues == null) {
			searchKeyValues = new HashSet<SearchKeyValue>();
		}
		return searchKeyValues;
	}

	/**
	 * @param searchKeyValues
	 *            the searchKeyValues to set
	 */
	public void setSearchKeyValues(Set<SearchKeyValue> searchKeyValues) {
		this.searchKeyValues = searchKeyValues;
	}

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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the cluSet
	 */
	public CluSet getCluSet() {
		return cluSet;
	}

	/**
	 * @param cluSet the cluSet to set
	 */
	public void setCluSet(CluSet cluSet) {
		this.cluSet = cluSet;
	}
}
