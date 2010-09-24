package org.kuali.rice.kns.bo;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Id;

public abstract class KualiTypeBusinessObjectBase extends PersistableBusinessObjectBase implements KualiType {
	@Id
	@Column(name = "TYPE_KEY")
	private String id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "TYPE_DESC")
	private String description;

	public KualiTypeBusinessObjectBase() {
		super();
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

	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap m = new LinkedHashMap();

		m.put("id", this.id);

		return m;
	}

}
