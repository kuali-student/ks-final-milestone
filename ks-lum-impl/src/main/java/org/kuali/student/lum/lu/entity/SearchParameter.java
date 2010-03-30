package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name = "KSLU_SPARAM")
public class SearchParameter {

	@Id
	@Column(name = "ID")
    private String id;

	@Column(name = "SEARCH_PARAM_KEY")
	private String key;

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<SearchParameterValue> values = new ArrayList<SearchParameterValue>();

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<SearchParameterValue> getValues() {
		return values;
	}

	public void setValues(List<SearchParameterValue> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "SearchParam[key=" + key + ", values=" + values + "]";
	}
}
