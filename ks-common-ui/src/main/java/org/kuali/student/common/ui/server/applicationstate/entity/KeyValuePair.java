package org.kuali.student.common.ui.server.applicationstate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name = "KSAP_KEY_VALUE_T") 
public class KeyValuePair {
	@Id
    @Column(name = "ID")
	private String id;

	@Column(name = "APP_STATE_KEY", nullable=false)
	private String key;

	@Column(name = "VALUE", length=2000, nullable=false)
	private String value;

	public KeyValuePair() {
	}

	public KeyValuePair(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}

	public String getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "KeyValuePair[id=" + id + ", key=" + key + ", value=" + value + "]";
	}
}
