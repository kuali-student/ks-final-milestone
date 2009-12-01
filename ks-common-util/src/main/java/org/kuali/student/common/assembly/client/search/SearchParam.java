package org.kuali.student.common.assembly.client.search;

import java.util.List;

public class SearchParam {
	private List<String> value;//TODO or just a single. maybe 2 classes, one with list and one without
	private String key;
	public List<String> getValue() {
		return value;
	}
	public void setValue(List<String> value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
