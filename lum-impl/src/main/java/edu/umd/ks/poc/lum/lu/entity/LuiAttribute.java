package edu.umd.ks.poc.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class LuiAttribute {

	@Id
	private String id;
	@ManyToOne
	@JoinColumn(name = "Lui_ID", nullable = false)
	private Lui lui;

	@ManyToOne
	@JoinColumn(name = "Lu_Attribute_Type_ID", nullable = false)
	private LuAttributeType luAttributeType;

	@org.hibernate.annotations.CollectionOfElements
	@JoinTable
	private List<String> values;
	private String status;
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

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
	 * @return the luAttributeType
	 */
	public LuAttributeType getLuAttributeType() {
		return luAttributeType;
	}

	/**
	 * @param luAttributeType
	 *            the luAttributeType to set
	 */
	public void setLuAttributeType(LuAttributeType luAttributeType) {
		this.luAttributeType = luAttributeType;
	}

	/**
	 * @return the lui
	 */
	public Lui getLui() {
		return lui;
	}

	/**
	 * @param lui the lui to set
	 */
	public void setLui(Lui lui) {
		this.lui = lui;
	}

	/**
	 * @return the values
	 */
	public List<String> getValues() {
		if(values==null){
			values = new ArrayList<String>();
		}
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<String> values) {
		this.values = values;
	}

}
