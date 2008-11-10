package edu.umd.ks.poc.lum.lu.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class LuAttributeTypeInfo  implements Serializable{

	private static final long serialVersionUID = 8840495545480676172L;
	@XmlElement
	private String id;
	@XmlElement
	private String name;
	@XmlElement
	private boolean list;
	@XmlElement
	private String dataType;
	@XmlElement
	private String displayType;
	@XmlElement
	private String scatId;
	@XmlElement
	private String groupingCd;
	
	@XmlElement
	private String status;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the list
	 */
	public boolean isList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(boolean list) {
		this.list = list;
	}
	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	/**
	 * @return the displayType
	 */
	public String getDisplayType() {
		return displayType;
	}
	/**
	 * @param displayType the displayType to set
	 */
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	/**
	 * @return the scatId
	 */
	public String getScatId() {
		return scatId;
	}
	/**
	 * @param scatId the scatId to set
	 */
	public void setScatId(String scatId) {
		this.scatId = scatId;
	}
	/**
	 * @return the groupingCd
	 */
	public String getGroupingCd() {
		return groupingCd;
	}
	/**
	 * @param groupingCd the groupingCd to set
	 */
	public void setGroupingCd(String groupingCd) {
		this.groupingCd = groupingCd;
	}
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
	
}
