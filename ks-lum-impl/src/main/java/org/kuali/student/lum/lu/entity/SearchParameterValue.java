package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name = "KSLU_SEARCH_PARAM_VALUE")
public class SearchParameterValue {
	@Id
	@Column(name = "ID")
    private String id;

	@Column(name = "VALUE")
	private String value;

    /**
     * AutoGenerate the Id
     */
	@PrePersist
    public void onPrePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SearchParamValue [value=" + value + "]";
	}
}
