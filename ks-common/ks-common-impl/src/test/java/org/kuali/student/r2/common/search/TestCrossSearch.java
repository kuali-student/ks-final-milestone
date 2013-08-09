package org.kuali.student.r2.common.search;

import org.junit.Test;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;

import static org.junit.Assert.assertNotNull;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;

public class TestCrossSearch {

	@Test
	public void testCrossSearchUnion() throws MissingParameterException, PermissionDeniedException, OperationFailedException, InvalidParameterException {
		MockSearch search = new MockSearch();
		SearchRequestInfo searchRequest = new SearchRequestInfo();
		searchRequest.setSearchKey("test.crossSearch");
		SearchResultInfo result = search.search(searchRequest, new ContextInfo());
		assertNotNull(result);
	}
}
