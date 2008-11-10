package edu.umd.ks.poc.lum.scat.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class ScatTableInfo implements Serializable{

	private static final long serialVersionUID = 7222665074579198130L;

	@XmlElement
	private String tableId;
	
	@XmlElement
	private String tableDescription;
	
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
}
