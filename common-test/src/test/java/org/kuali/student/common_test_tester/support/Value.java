package org.kuali.student.common_test_tester.support;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.test.spring.Idable;
import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class Value implements Idable{
	@Id
	private String id;

	private String value;

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}

	/**
	 * 
	 */
	public Value() {
		super();
	}

	/**
	 * @param value
	 */
	public Value(String value) {
		super();
		this.value = value;
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
}
