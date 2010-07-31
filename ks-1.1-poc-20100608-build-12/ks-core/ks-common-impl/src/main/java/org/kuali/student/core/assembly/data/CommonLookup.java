package org.kuali.student.core.assembly.data;

import java.io.Serializable;

public class CommonLookup implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private String searchTypeId;
	private String resultReturnKey;
	
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
	

}
