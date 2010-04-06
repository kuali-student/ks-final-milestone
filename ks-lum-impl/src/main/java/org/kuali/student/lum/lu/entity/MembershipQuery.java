package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name = "KSLU_MEMSHIP")
public class MembershipQuery {

	@Id
	@Column(name = "ID")
    private String id;

	@Column(name = "SEARCH_TYPE_KEY")
	private String searchTypeKey;

	@OneToMany(cascade = CascadeType.ALL)
	private List<SearchParameter> searchParameters = new ArrayList<SearchParameter>();

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

	public String getSearchTypeKey() {
		return searchTypeKey;
	}

	public void setSearchTypeKey(String searchTypeKey) {
		this.searchTypeKey = searchTypeKey;
	}

	public List<SearchParameter> getSearchParameters() {
		return searchParameters;
	}

	public void setSearchParameters(List<SearchParameter> searchParameters) {
		this.searchParameters = searchParameters;
	}

	@Override
	public String toString() {
		return "MembershipQuery[searchTypeKey=" + searchTypeKey + "]";
	}

}
