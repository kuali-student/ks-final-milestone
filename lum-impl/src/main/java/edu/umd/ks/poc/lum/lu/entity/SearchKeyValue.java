package edu.umd.ks.poc.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class SearchKeyValue {
	@Id
	private String id;
	private String keyName;
	private String value;
	@ManyToOne
	private CluCrit cluCrit;
	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the cluCrit
	 */
	public CluCrit getCluCrit() {
		return cluCrit;
	}

	/**
	 * @param cluCrit the cluCrit to set
	 */
	public void setCluCrit(CluCrit cluCrit) {
		this.cluCrit = cluCrit;
	}

	/**
	 * @return the keyName
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * @param keyName the keyName to set
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

}
