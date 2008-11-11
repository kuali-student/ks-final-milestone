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
public class CluAttribute {
	@Id
	private String id;
	@ManyToOne
	@JoinColumn(name = "Clu_ID", nullable = false)
	private Clu clu;

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
	public List<String> getValues() {
		if(values==null){
			values = new ArrayList<String>();
		}
		return values;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValues(List<String> values) {
		this.values = values;
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

}
