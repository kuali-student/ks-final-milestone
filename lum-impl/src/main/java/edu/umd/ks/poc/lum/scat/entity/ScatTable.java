package edu.umd.ks.poc.lum.scat.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class ScatTable implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private String tableId;
	private String tableDescription;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="table")
	private List<Scat> scats;
	
	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.tableId = UUIDHelper.genStringUUID(this.tableId);
	}
	
	/**
	 * @return the tableId
	 */
	public String getTableId() {
		return tableId;
	}
	/**
	 * @param tableId the tableId to set
	 */
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	/**
	 * @return the tableDescription
	 */
	public String getTableDescription() {
		return tableDescription;
	}
	/**
	 * @param tableDescription the tableDescription to set
	 */
	public void setTableDescription(String tableDescription) {
		this.tableDescription = tableDescription;
	}
	/**
	 * @return the scats
	 */
	public List<Scat> getScats() {
		if(scats==null){
			scats = new ArrayList<Scat>();
		}
		return scats;
	}
	/**
	 * @param scats the scats to set
	 */
	public void setScats(List<Scat> scats) {
		this.scats = scats;
	}
}
