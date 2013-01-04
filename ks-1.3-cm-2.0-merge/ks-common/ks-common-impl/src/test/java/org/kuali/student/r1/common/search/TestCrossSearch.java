package org.kuali.student.r1.common.search;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;

public class TestCrossSearch {

	@Test
	public void testCrossSearchUnion() throws MissingParameterException{
		MockSearch search = new MockSearch();
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setSearchKey("test.crossSearch");
		SearchResult result = search.search(searchRequest);
		assertNotNull(result);
	}
}
