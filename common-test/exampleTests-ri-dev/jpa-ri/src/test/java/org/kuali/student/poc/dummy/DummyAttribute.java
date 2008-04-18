package org.kuali.student.poc.dummy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "Dummy_Attribute_T")
@TableGenerator(name = "idGen")
public class DummyAttribute {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "idGen")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "Dummy_ID", nullable = false)
	private Dummy dummy;

	private String value;

	public DummyAttribute() {
		id = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Dummy getDummy() {
		return dummy;
	}

	public void setDummy(Dummy dummy) {
		this.dummy = dummy;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
