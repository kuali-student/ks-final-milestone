package org.kuali.student.poc.learningunit.lu.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name = "idGen")
public class LuAttribute {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
	private String id;
	@ManyToOne
	@JoinColumn(name = "Clu_ID", nullable = false)
	private Clu clu;

	@ManyToOne
	@JoinColumn(name = "Lu_Attribute_Type_ID", nullable = false)
	private LuAttributeType luAttributeType;

	private String value;

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
	 * @return the clu
	 */
	public Clu getClu() {
		return clu;
	}

	/**
	 * @param clu
	 *            the clu to set
	 */
	public void setClu(Clu clu) {
		this.clu = clu;
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
	 * @return the luAttributeType
	 */
	public LuAttributeType getLuAttributeType() {
		return luAttributeType;
	}

	/**
	 * @param luAttributeType the luAttributeType to set
	 */
	public void setLuAttributeType(LuAttributeType luAttributeType) {
		this.luAttributeType = luAttributeType;
	}

}
