package org.kuali.student.core.search.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
@XmlAccessorType(XmlAccessType.FIELD)
public class CrossSearchTypeInfo extends SearchTypeInfo {

	private static final long serialVersionUID = 1L;
	
	List<SubSearchInfo> subSearches;
	JoinCriteriaInfo joinCriteria;
	List<JoinResultMappingInfo> joinResultMappings;
	public List<SubSearchInfo> getSubSearches() {
		return subSearches;
	}
	public void setSubSearches(List<SubSearchInfo> subSearches) {
		if(subSearches == null){
			subSearches = new ArrayList<SubSearchInfo>();
		}
		this.subSearches = subSearches;
	}
	public JoinCriteriaInfo getJoinCriteria() {
		return joinCriteria;
	}
	public void setJoinCriteria(JoinCriteriaInfo joinCriteria) {
		this.joinCriteria = joinCriteria;
	}
	public List<JoinResultMappingInfo> getJoinResultMappings() {
		if(joinResultMappings == null){
			joinResultMappings = new ArrayList<JoinResultMappingInfo>();
		}
		return joinResultMappings;
	}
	public void setJoinResultMappings(List<JoinResultMappingInfo> joinResultMappings) {
		this.joinResultMappings = joinResultMappings;
	}
}
