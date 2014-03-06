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
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.view.LookupView;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.cm.course.form.CourseInfoWrapper;
import org.kuali.student.common.uif.service.impl.KSLookupableImpl;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.constants.ProposalServiceConstants;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.proposal.service.ProposalService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Kuali Student Team
 */
public class ProposalLookupableImpl extends KSLookupableImpl {

    private static final long serialVersionUID = 1L;

    private transient ProposalService proposalService;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();

        String fieldValue = fieldValues.get("title");

        if (StringUtils.isBlank(fieldValue)){
            return new ArrayList<ProposalInfo>();
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
            SearchResultInfo searchResult = getProposalService().search(searchRequest, ContextUtils.getContextInfo());
            return resolveProposalSearchResultSet(searchResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ProposalInfo> resolveProposalSearchResultSet(SearchResultInfo searchResult) {
        List<ProposalInfo> clus = new ArrayList<ProposalInfo>();
        List<SearchResultRowInfo> rows = searchResult.getRows();
        for (SearchResultRowInfo row : rows) {
            List<SearchResultCellInfo> cells = row.getCells();
            ProposalInfo proposalInfo = new ProposalInfo();
            for (SearchResultCellInfo cell : cells) {
                if (cell.getKey().equals("proposal.resultColumn.proposalId")) {
                    proposalInfo.setId(cell.getValue());
                } else if (cell.getKey().equals("proposal.resultColumn.proposalOptionalName")) {
                    proposalInfo.setName(cell.getValue());
                } else if (cell.getKey().equals("proposal.resultColumn.proposalOptionalTypeName")) {

                    proposalInfo.setType(cell.getValue());
                } else if (cell.getKey().equals("proposal.resultColumn.proposalOptionalStatus")) {
                    proposalInfo.setState(cell.getValue());
                }
            }
            clus.add(proposalInfo);
        }
        return clus;
    }

    protected String getActionUrlHref(LookupForm lookupForm, Object dataObject, String methodToCall, List<String> pkNames) {

        if (dataObject == null){
            return super.getActionUrlHref(lookupForm,dataObject,methodToCall,pkNames);
        }

        LookupView lookupView = (LookupView) lookupForm.getView();

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put(KRADConstants.PARAMETER_COMMAND, KRADConstants.METHOD_DISPLAY_DOC_SEARCH_VIEW);

        if (StringUtils.isNotBlank(lookupForm.getReturnLocation())) {
            props.put(KRADConstants.RETURN_LOCATION_PARAMETER, lookupForm.getReturnLocation());
        }

        props.put(UifParameters.DATA_OBJECT_CLASS_NAME, CourseInfoWrapper.class.toString());
        props.put(UifParameters.PAGE_ID, "KS-CourseView-ReviewProposalPage");

        try {
            ProposalInfo proposalInfo = getProposalService().getProposal(((ProposalInfo) dataObject).getId(), ContextUtils.getContextInfo());
            props.put(KRADConstants.PARAMETER_DOC_ID, proposalInfo.getWorkflowId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return UrlFactory.parameterizeUrl("courses", props);
    }

    protected ProposalService getProposalService() {
        if (proposalService == null) {
            proposalService = GlobalResourceLoader.getService(new QName(ProposalServiceConstants.NAMESPACE, ProposalServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return proposalService;
    }

}
