package org.kuali.student.common.search;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.oldsearch.dto.SearchRequest;
import org.kuali.student.common.oldsearch.dto.SearchResult;

public class TestCrossSearch {

	@Test
	public void testCrossSearchUnion() throws MissingParameterException{
		MockSearch search = new MockSearch();
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setSearchKey("test.crossSearch");
		// TODO KSCM
		SearchResult result = search.search(searchRequest,new ContextInfo());
		assertNotNull(result);
	}
}
