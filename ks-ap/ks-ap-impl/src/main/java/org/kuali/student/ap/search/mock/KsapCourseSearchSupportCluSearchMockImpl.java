/*
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.ap.search.mock;

import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.search.KsapCourseSearchSupportCluSearchImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.util.SearchRequestHelper;

public class KsapCourseSearchSupportCluSearchMockImpl extends KsapCourseSearchSupportCluSearchImpl {

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_SEARCH_FIND_CURRENT_VERSION_ID.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo findCurrentVersionId(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String versionId = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters.VERSION_IND_ID);

        SearchResultRowInfo row = new SearchResultRowInfo();
        row.addCell(CourseSearchConstants.SearchResultColumns.CLU_ID, versionId.replace("ind",""));
        resultInfo.getRows().add(row);
        return resultInfo;
    }
}
