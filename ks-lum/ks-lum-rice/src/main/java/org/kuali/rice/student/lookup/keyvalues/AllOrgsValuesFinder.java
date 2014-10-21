/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.rice.student.lookup.keyvalues;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllOrgsValuesFinder extends StudentKeyValuesBase {
	private static final Logger LOG = LoggerFactory.getLogger(AllOrgsValuesFinder.class);

	public List<KeyValue> getKeyValues() {
        List<KeyValue> departments = new ArrayList<KeyValue>();

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("org.search.generic");

        try {
        	for (SearchResultRowInfo result : getOrganizationService().search(searchRequest, ContextUtils.getContextInfo()).getRows()) {
                String orgId = "";
                String orgShortName = "";
                String orgOptionalLongName = "";
                String orgType = "";
                for (SearchResultCellInfo resultCell : result.getCells()) {
                    if ("org.resultColumn.orgId".equals(resultCell.getKey())) {
                        orgId = resultCell.getValue();
                    } else if ("org.resultColumn.orgShortName".equals(resultCell.getKey())) {
                        orgShortName = resultCell.getValue();
                    } else if ("org.resultColumn.orgOptionalLongName".equals(resultCell.getKey())) {
                    	orgOptionalLongName = resultCell.getValue();
                    } else if ("org.resultColumn.orgType".equals(resultCell.getKey())) {
                    	orgType = resultCell.getValue();
                    }
                }
                departments.add(buildKeyLabelPair(orgId, orgShortName, orgOptionalLongName, orgType));
            }
        	return departments;
        } catch (Exception e) {
        	LOG.error("Error building KeyValues List", e);
            throw new RuntimeException(e);
        }
    }

}
