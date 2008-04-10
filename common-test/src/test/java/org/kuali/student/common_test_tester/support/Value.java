package org.kuali.student.common_test_tester.support;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name = "idGen")
public class Value {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")private Long id;
	private String value;
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
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
