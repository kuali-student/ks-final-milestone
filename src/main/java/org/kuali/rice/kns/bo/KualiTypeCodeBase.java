package org.kuali.rice.kns.bo;

import java.sql.Date;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public abstract class KualiTypeCodeBase extends PersistableBusinessObjectBase implements KualiTypeCode {
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESC")
	private String description;

	@Column(name = "STATE")
	private String state;

	private String typeId;

	private String createId;

	private Date createDate;

	private String updateId;

	private Date updateDate;

	@ManyToOne
	@JoinColumn(name = "TYPE")
	private KualiType type;

	public KualiTypeCodeBase() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
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

	public KualiType getType() {
		return type;
	}

	public void setType(KualiType type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap m = new LinkedHashMap();

		m.put("id", this.id);

		return m;
	}

}
