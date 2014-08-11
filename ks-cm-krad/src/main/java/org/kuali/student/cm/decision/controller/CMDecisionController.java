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
 * Created by venkat on 7/28/14
 */
package org.kuali.student.cm.decision.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.web.controller.KsUifControllerBase;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.decision.form.CMDecisionForm;
import org.kuali.student.cm.decision.form.wrapper.CMDecisionWrapper;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.core.constants.CommentServiceConstants;
import org.kuali.student.r2.core.constants.ProposalServiceConstants;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.proposal.service.ProposalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = CurriculumManagementConstants.ControllerRequestMappings.CM_DECISION)
public class CMDecisionController extends KsUifControllerBase {

    private static final Logger LOG = LoggerFactory.getLogger(CMDecisionController.class);

    protected PersonService personService;
    protected CommentService commentService;
    protected ProposalService proposalService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CMDecisionForm();
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                              HttpServletResponse response) {

        CMDecisionForm decisionForm = (CMDecisionForm) form;

        String proposalId = request.getParameter("proposalId");

        if (StringUtils.isBlank(proposalId)) {
            throw new RuntimeException("Missing proposal Id");
        }

        try {
            ProposalInfo proposalInfo = getProposalService().getProposal(proposalId, ContextUtils.createDefaultContextInfo());
            decisionForm.setProposal(proposalInfo);
            retrieveDecisions(decisionForm);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Proposal [id=" + proposalId + "]",e);
        }

        retrieveDecisions(decisionForm);

        return super.start(form, request, response);
    }

    protected void retrieveDecisions(CMDecisionForm form) {

        ProposalInfo proposal = form.getProposal();

        LOG.debug("Retrieving decisions for  - " + proposal.getId());

        List<CommentInfo> decisions;

        form.getDecisions().clear();

        try {
            decisions = getCommentService().getCommentsByRefObject(proposal.getId(), StudentIdentityConstants.QUALIFICATION_PROPOSAL_REF_TYPE, ContextUtils.createDefaultContextInfo());
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving decision(s) for the proposal [id=" + proposal.getId() + "]", e);
        }

        Map<String,String> personId2DisplayName = new HashMap<>();

        for (CommentInfo comment : decisions) {

            if (isWorkflowDecision(comment.getTypeKey())){
                CMDecisionWrapper wrapper = new CMDecisionWrapper(comment);
                if (!personId2DisplayName.containsKey(comment.getCommenterId())) {
                    personId2DisplayName.put(comment.getCommenterId(), getDisplayNameForPrincipalId(comment.getCommenterId()));
                }
                wrapper.setActor(personId2DisplayName.get(comment.getCommenterId()));
                wrapper.setDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(comment.getMeta().getCreateTime()));
                wrapper.setDecision(CommentServiceConstants.WORKFLOW_DECISIONS.getByType(comment.getTypeKey()).getLabel());

                wrapper.setRationale(comment.getCommentText().getPlain());
                form.getDecisions().add(wrapper);
            }
        }

        if (!form.getDecisions().isEmpty()){
            Collections.sort(form.getDecisions());
        }

        LOG.debug("There are " + form.getDecisions().size() + " decisions for proposal " + proposal.getId());


    }

    protected String getDisplayNameForPrincipalId(String principalId) {
        Person person = getPersonService().getPerson(principalId);
        if (person == null) {
            throw new RuntimeException("Error fetching person for principal id '" + principalId + "'");
        }
        return (new StringBuilder(person.getFirstName())).append(" ").append(person.getLastName()).toString();
    }

    protected boolean isWorkflowDecision(String typeKey){
        for(CommentServiceConstants.WORKFLOW_DECISIONS workflowDecision : CommentServiceConstants.WORKFLOW_DECISIONS.values()){
           if(StringUtils.equalsIgnoreCase(workflowDecision.getType(),typeKey)){
                return true;
            }
        }
        return false;
    }

    protected PersonService getPersonService() {
        if (personService == null) {
            personService = KimApiServiceLocator.getPersonService();
        }
        return personService;
    }

    protected CommentService getCommentService() {
        if (commentService == null) {
            commentService = (CommentService) GlobalResourceLoader.getService(new QName(CommentServiceConstants.NAMESPACE, CommentService.class.getSimpleName()));
        }
        return commentService;
    }

    protected ProposalService getProposalService() {
        if (proposalService == null) {
            proposalService = (ProposalService) GlobalResourceLoader.getService(new QName(ProposalServiceConstants.NAMESPACE, ProposalServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return proposalService;
    }
}