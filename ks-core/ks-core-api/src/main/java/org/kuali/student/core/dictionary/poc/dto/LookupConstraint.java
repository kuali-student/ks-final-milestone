package org.kuali.student.core.dictionary.poc.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class LookupConstraint extends BaseConstraint {
	private String searchTypeId; // id of search type defined in search xml
	private String resultReturnKey; // key of searchResultColumn to map back to
									// this field
	protected List<LookupConstraintParamMapping> lookupParams; // maps fields to
																// search
																// params?

	public String getSearchTypeId() {
		return searchTypeId;
	}

	public void setSearchTypeId(String searchTypeId) {
		this.searchTypeId = searchTypeId;
	}

	public String getResultReturnKey() {
		return resultReturnKey;
	}

	public void setResultReturnKey(String resultReturnKey) {
		this.resultReturnKey = resultReturnKey;
	}

	public List<LookupConstraintParamMapping> getLookupParams() {
		return lookupParams;
	}

	public void setLookupParams(List<LookupConstraintParamMapping> lookupParams) {
		this.lookupParams = lookupParams;
	}
}