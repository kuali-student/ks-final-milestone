package org.kuali.student.ap.framework.course;

import java.util.List;
import java.util.Map;

public interface CourseSearchForm {

	static final String SEARCH_TERM_ANY_ITEM = "any";

	List<String> getCampusSelect();

	void setCampusSelect(List<String> campusSelect);

	String getSearchQuery();

	void setSearchQuery(String searchQuery);

	String getSearchTerm();

	void setSearchTerm(String searchTerm);

	Map<String, String> getAdditionalParams();
	
}
