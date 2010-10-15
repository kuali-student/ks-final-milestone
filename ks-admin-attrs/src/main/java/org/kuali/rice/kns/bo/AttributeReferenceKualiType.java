package org.kuali.rice.kns.bo;

import java.sql.Date;
import java.util.LinkedHashMap;

public class AttributeReferenceKualiType extends PersistableBusinessObjectBase {
	private static final long serialVersionUID = 7252003662003263295L;

	private String id;
	private String name;
	private String description;
	private String state;
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private String typeId;

	public AttributeReferenceKualiType() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap m = new LinkedHashMap();

		m.put("hashCode", Integer.toHexString(hashCode()));

		return m;
	}

}
