/**
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
 *
 * Created by venkat on 3/3/14
 */
package org.kuali.student.cm.course.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.common.uif.service.impl.KSLookupableImpl;
import org.kuali.student.lum.lu.ui.krms.dto.CluInformation;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */
public class ProposalLookupableImpl extends KSLookupableImpl {

    private static final long serialVersionUID = 1L;

    private transient CluService cluService;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();

        String fieldValue = fieldValues.get("title");

        if (StringUtils.isBlank(fieldValue)){
            return new ArrayList<CluInformation>();
        }

        SearchParamInfo qpv = new SearchParamInfo();
        qpv.setKey("proposal.queryParam.proposalOptionalName");
        qpv.getValues().add(fieldValue);
        searchParams.add(qpv);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("proposal.search.generic");
        searchRequest.setSortColumn("proposal.queryParam.proposalOptionalName");

        try {
            SearchResultInfo searchResult = getCluService().search(searchRequest, ContextUtils.getContextInfo());
            return resolveProposalSearchResultSet(searchResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<CluInformation> resolveProposalSearchResultSet(SearchResultInfo searchResult) {
        List<CluInformation> clus = new ArrayList<CluInformation>();
        List<SearchResultRowInfo> rows = searchResult.getRows();
        for (SearchResultRowInfo row : rows) {
            List<SearchResultCellInfo> cells = row.getCells();
            CluInformation cluInformation = new CluInformation();
            for (SearchResultCellInfo cell : cells) {
                if (cell.getKey().equals("proposal.resultColumn.proposalId")) {
                    cluInformation.setCluId(cell.getValue());
                } else if (cell.getKey().equals("proposal.resultColumn.proposalOptionalName")) {
                    cluInformation.setTitle(cell.getValue());
                } else if (cell.getKey().equals("proposal.resultColumn.proposalOptionalTypeName")) {

                    cluInformation.setType(cell.getValue());
                } else if (cell.getKey().equals("proposal.resultColumn.proposalOptionalStatus")) {
                    cluInformation.setState(cell.getValue());
                }
            }
            clus.add(cluInformation);
        }
        return clus;
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }

}
