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
package org.kuali.student.cm.proposal.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.entity.EntityDefaultQueryResults;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.cm.maintenance.CMMaintenanceDocument;
import org.kuali.student.common.uif.service.impl.KSLookupableImpl;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.dto.AttributeInfo;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lookupable class for ProposalInfo objects
 *
 * @author Kuali Student Team
 */
public abstract class ProposalLookupableImpl extends KSLookupableImpl {

    private transient ProposalService proposalService;

    protected final String PROPOSAL_TITLE_LEY = "proposal.queryParam.proposalOptionalName";
    protected final String SEARCH_KEY = "proposal.search.generic";
    protected final String PROPOSAL_ID_KEY = "proposal.resultColumn.proposalId";
    protected final String CAN_EDIT_PROPOSAL_KEY = "canEditProposal";
    protected final String CAN_OPEN_PROPOSAL_KEY = "canOpenProposal";

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();

        String fieldValue = fieldValues.get("title");

        SearchParamInfo qpv = new SearchParamInfo();
        qpv.setKey(PROPOSAL_TITLE_LEY);
        qpv.getValues().add(fieldValue);
        searchParams.add(qpv);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey(SEARCH_KEY);
        searchRequest.setSortColumn(PROPOSAL_TITLE_LEY);

        List<ProposalInfo> proposalInfos;
        try {
            SearchResultInfo searchResult = getProposalService().search(searchRequest, ContextUtils.createDefaultContextInfo());
            proposalInfos = resolveProposalSearchResultSet(searchResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return proposalInfos;
    }

    protected List<ProposalInfo> resolveProposalSearchResultSet(SearchResultInfo searchResult) throws Exception {
        List<ProposalInfo> proposals = new ArrayList<ProposalInfo>();
        List<SearchResultRowInfo> rows = searchResult.getRows();
        List<String> proposalIds = new ArrayList<String>();

        for (SearchResultRowInfo row : rows) {
            List<SearchResultCellInfo> cells = row.getCells();
            for (SearchResultCellInfo cell : cells) {
                if (cell.getKey().equals(PROPOSAL_ID_KEY)) {
                    proposalIds.add(cell.getValue());
                }
            }
        }

        if (!proposalIds.isEmpty()){
            proposals = getProposalService().getProposalsByIds(proposalIds,ContextUtils.createDefaultContextInfo());
            populateProposalCreatorName(proposals);
            populateProposalAllowedActions(proposals);
        }

        return Collections.unmodifiableList(proposals);
    }

    protected void populateProposalCreatorName(List<ProposalInfo> proposals) {

        if (proposals.isEmpty()){
            return;
        }

        List<String> principalIds = new ArrayList<String>();
        for (ProposalInfo proposal : proposals){
            principalIds.add(proposal.getMeta().getCreateId());
        }

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.in("principals.principalId", principalIds.toArray()));

        QueryByCriteria criteria = qbcBuilder.build();

        EntityDefaultQueryResults entityResults = KimApiServiceLocator.getIdentityService().findEntityDefaults(criteria);

        Map<String,String> principalId2Name = new HashMap<String, String>();

        for (EntityDefault entity : entityResults.getResults()) {
            for (Principal principal : entity.getPrincipals()) {
                principalId2Name.put(principal.getPrincipalId(), entity.getName().getCompositeName());
            }
        }

        for (ProposalInfo proposal : proposals) {
            String principalName = principalId2Name.get(proposal.getMeta().getCreateId());
            /** A hacky way to display the user full name at the UI to avoid creating a new wrapper class.
             * And, we're returning an unmodifiable list from the search method to make sure it's not
             * possible to change this object.
             */
            proposal.getMeta().setCreateId(principalName);
        }

    }

    protected void populateProposalAllowedActions(List<ProposalInfo> proposals){

        if (proposals.isEmpty()){
            return;
        }

        List<String> workflowIds = new ArrayList<String>();
        for (ProposalInfo proposal : proposals){
            workflowIds.add(proposal.getWorkflowId());
        }

        List<Document> documents = null;
        try {
            documents = KRADServiceLocatorWeb.getDocumentService()
                    .getDocumentsByListOfDocumentHeaderIds(CMMaintenanceDocument.class, workflowIds);
        } catch (WorkflowException e) {
            throw new RuntimeException("Error loading maintenance document",e);
        }

        Map<String,Document> workflowId2Doc = new HashMap<String, Document>();

        for (Document document : documents){
            workflowId2Doc.put(document.getDocumentNumber(),document);
        }

        for (ProposalInfo proposal : proposals){

            boolean canEdit;
            boolean canOpen;

            Document document = workflowId2Doc.get(proposal.getWorkflowId());
            String docTypeName = document.getDocumentHeader().getWorkflowDocument().getDocumentTypeName();

            canEdit = KRADServiceLocatorWeb.getDocumentDictionaryService().getDocumentAuthorizer(docTypeName).canEdit(document,
                    GlobalVariables.getUserSession().getPerson());

            canOpen = KRADServiceLocatorWeb.getDocumentDictionaryService().getDocumentAuthorizer(docTypeName).canOpen(document,
                    GlobalVariables.getUserSession().getPerson());

            AttributeInfo editAttribute = new AttributeInfo(CAN_EDIT_PROPOSAL_KEY, BooleanUtils.toStringTrueFalse(canEdit));
            AttributeInfo openAttribute = new AttributeInfo(CAN_OPEN_PROPOSAL_KEY, BooleanUtils.toStringTrueFalse(canOpen));
            proposal.getAttributes().add(editAttribute);
            proposal.getAttributes().add(openAttribute);
        }
    }

    /**
     * Determines if given data object has associated maintenance document that allows edit maintenance
     * actions
     *
     * @return boolean true if the maintenance edit action is allowed for the data object instance, false otherwise
     */
    public boolean allowsProposalEditAction(Object dataObject) {

        ProposalInfo proposalInfo = (ProposalInfo)dataObject;

        return BooleanUtils.toBoolean(proposalInfo.getAttributeValue(CAN_EDIT_PROPOSAL_KEY));
    }

    /**
     * Determines if given data object has associated maintenance document that allows edit maintenance
     * actions
     *
     * @return boolean true if the maintenance edit action is allowed for the data object instance, false otherwise
     */
    public boolean allowsProposalOpenAction(Object dataObject) {

        ProposalInfo proposalInfo = (ProposalInfo)dataObject;

        return BooleanUtils.toBoolean(proposalInfo.getAttributeValue(CAN_OPEN_PROPOSAL_KEY));
    }

    /**
     * This is the finalizeMethodToCall which builds the action links.
     */
    public void buildProposalActionLink(Action actionLink, Object model, String maintenanceMethodToCall, String pageId) {

        Object dataObject = actionLink.getContext().get(UifConstants.ContextVariableNames.LINE);

        ProposalInfo proposalInfo = (ProposalInfo) dataObject;

        String href = buildHrefForActionLink(maintenanceMethodToCall, pageId, proposalInfo.getWorkflowId(), proposalInfo.getTypeKey());

        if (StringUtils.isBlank(href)) {
            actionLink.setRender(false);
            return;
        }

        actionLink.setActionScript("window.open('" + href + "', '_self');");

        // rice 2.4 upgrade - commented out
//        lookupForm.setAtLeastOneRowHasActions(true);
    }

    public abstract String buildHrefForActionLink(String maintenanceMethodToCall, String pageId, String workflowDocId, String proposalType);

    protected ProposalService getProposalService() {
        if (proposalService == null) {
            proposalService = GlobalResourceLoader.getService(new QName(ProposalServiceConstants.NAMESPACE, ProposalServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return proposalService;
    }

}
