package org.kuali.student.common.assembly;

import java.util.Map;

public interface EnumerationMetadata {
	// totally a strawman, no design work done yet
	Map<String, String> getCriteria(); // obviously wrong

	String getSearchName();
}
