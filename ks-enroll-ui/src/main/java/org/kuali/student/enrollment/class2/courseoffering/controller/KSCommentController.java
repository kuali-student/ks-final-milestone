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
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.KsUifControllerBase;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.student.StudentWorkflowConstants;
//import org.kuali.student.cm.course.form.CMCommentForm;
//import org.kuali.student.cm.course.form.KSCommentWrapper;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.courseoffering.form.KSCommentForm;
import org.kuali.student.enrollment.class2.courseoffering.form.KSCommentWrapper;
import org.kuali.student.lum.kim.KimIdentityServiceConstants;
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
 * This class handles the comment functionality for a proposal.
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/ksComment")
public class KSCommentController extends KsUifControllerBase {

    private static final Logger LOG = LoggerFactory.getLogger(KSCommentController.class);

    protected CommentService commentService;
//    protected ProposalService proposalService;
    protected PersonService personService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new KSCommentForm();
    }

    private String getRequestParamValue(HttpServletRequest request, String paramName){
        String paramValue = request.getParameter(paramName);
        if (StringUtils.isBlank(paramValue)) {
            String message = String.format("Missing parameter: %s", paramName);
            LOG.error(message);
            throw new RuntimeException(message);
        }
        return paramValue;
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                              HttpServletResponse response) {

        KSCommentForm commentForm = (KSCommentForm) form;
        String refId = getRequestParamValue(request, "refId");
        String refType = getRequestParamValue(request, "refType");
        String refName = getRequestParamValue(request, "refName");
        commentForm.setReferenceId(refId);
        commentForm.setReferenceType(refType);
        commentForm.setReferenceName(refName);

//        try {
//            ProposalInfo proposalInfo = getProposalService().getProposal(proposalId, ContextUtils.createDefaultContextInfo());
//            commentForm.setProposal(proposalInfo);
//            commentForm.setCanAddComment(canAddComment(proposalInfo));
            retrieveComments(commentForm);
//        } catch (Exception e) {
//            throw new RuntimeException("Invalid Proposal [id=" + proposalId + "]");
//        }

        return super.start(form, request, response);
    }

    /**
     * This method submits a new comment or the comment editing by the user
     *
     * @param form {@link org.kuali.rice.krad.web.form.MaintenanceDocumentForm} instance used for this action
     * @throws Exception
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=addComment")
    public ModelAndView addComment(@ModelAttribute("KualiForm") KSCommentForm form, HttpServletRequest request) throws Exception {

//        String refId = getRequestParamValue(request, "refId");
//        String refType = getRequestParamValue(request, "refType");
//        String refName = getRequestParamValue(request, "refName");
//        form.setReferenceName(refName);


        String collectionPath = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_PATH);
//        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));

        KSCommentWrapper wrapper = new KSCommentWrapper();
            wrapper.getCommentInfo().getCommentText().setPlain(form.getCommentText());
        saveComment(form, wrapper);
        form.getComments().add(0,wrapper);
        /**
         * If it's new comment, just create a new wrapper and call save, which should initialize all the details at the DTO
         */
//        if (StringUtils.isBlank(collectionPath)) {
//
//            KSCommentWrapper wrapper = new KSCommentWrapper();
//            wrapper.getCommentInfo().getCommentText().setPlain(form.getNewComment());
//            saveComment(form.getProposal(), wrapper);
//            form.getComments().add(0,wrapper);
//
//            form.setNewComment(StringUtils.EMPTY);
//
//        } else {
//
//            int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
//            saveComment(form.getProposal(), form.getComments().get(index));
//        }

        //Reset edit in progress flag from all the comments.
//        for (CommentWrapper commentWrapper : form.getComments()) {
//            commentWrapper.getRenderHelper().setEditInProgress(false);
//        }

        form.setDeletedComment(null);

        return getUIFModelAndView(form);
    }

//    @MethodAccessible
//    @RequestMapping(params = "methodToCall=updateComment")
//    public ModelAndView updateComment(@ModelAttribute("KualiForm") KSCommentForm form, HttpServletRequest request) throws Exception {
//    }
//
//    @MethodAccessible
//    @RequestMapping(params = "methodToCall=cancelEditComment")
//    public ModelAndView cancelEditComment(@ModelAttribute("KualiForm") CMCommentForm form) throws Exception {
//
//        for (KSCommentWrapper commentWrapper : form.getComments()) {
//            commentWrapper.getRenderHelper().setEditInProgress(false);
//        }
//
//        return getUIFModelAndView(form);
//    }
//
//    @MethodAccessible
//    @RequestMapping(params = "methodToCall=editComment")
//    public ModelAndView editComment(@ModelAttribute("KualiForm") CMCommentForm form) throws Exception {
//
//        if (form.getProposal() == null) {
//            throw new RuntimeException("Proposal not found.");
//        }
//
//        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
//        KSCommentWrapper commentWrapper = form.getComments().get(index);
//
//        // verify that current user is able to delete this comment
//        if (!canEditComment(form.getProposal(),commentWrapper)) {
//            throw new RuntimeException("Current user '" + GlobalVariables.getUserSession().getPrincipalId() + "' is not authorized to edit comment id '" + commentWrapper.getCommentInfo().getId() + "'");
//        }
//
//        form.getComments().get(index).getRenderHelper().setEditInProgress(true);
//
//        return getUIFModelAndView(form);
//    }
//
//    @MethodAccessible
//    @RequestMapping(params = "methodToCall=deleteComment")
//    public ModelAndView deleteComment(@ModelAttribute("KualiForm") CMCommentForm form) throws Exception {
//
//        if (form.getProposal() == null) {
//            throw new RuntimeException("Proposal not found.");
//        }
//
//        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
//        KSCommentWrapper commentWrapper = form.getComments().get(index);
//
//        // verify that current user is able to delete this comment
//        if (!canDeleteComment(form.getProposal(),commentWrapper)) {
//            throw new RuntimeException("Current user '" + GlobalVariables.getUserSession().getPrincipalId() + "' is not authorized to delete comment id '" + commentWrapper.getCommentInfo().getId() + "'");
//        }
//        form.getComments().remove(commentWrapper);
//        getCommentService().deleteComment(commentWrapper.getCommentInfo().getId(), ContextUtils.createDefaultContextInfo());
//
//        commentWrapper.getCommentInfo().setId(null); //Clear out the ID so that we can add that to DB if user decided to undo later
//        form.setDeletedComment(commentWrapper);
//
//        return getUIFModelAndView(form);
//
//    }
//
//    @MethodAccessible
//    @RequestMapping(params = "methodToCall=undoDeleteComment")
//    public ModelAndView undoDeleteComment(@ModelAttribute("KualiForm") CMCommentForm form) throws Exception {
//
//        KSCommentWrapper deletedComment = form.getDeletedComment();
//        saveComment(form.getProposal(), deletedComment);
//        form.getComments().add(deletedComment);
//        form.setDeletedComment(null);
//
//        Collections.sort(form.getComments());
//
//        return getUIFModelAndView(form);
//    }
//
    /**
     * Create or update a comment.
     *
     * @param form
     * @param commentWrapper
     */
    protected void saveComment(KSCommentForm form, KSCommentWrapper commentWrapper) {

//        LOG.trace("Saving comment - " + commentWrapper.getCommentInfo().getCommentText());

        CommentInfo comment = commentWrapper.getCommentInfo();

//        if (commentWrapper.isNewDto()) {

            comment.setReferenceId(form.getReferenceId());
            comment.setReferenceTypeKey(form.getReferenceType());
            comment.setTypeKey(CommentServiceConstants.COMMENT_GENERAL_REMARKS_TYPE_KEY);
            comment.setCommenterId(GlobalVariables.getUserSession().getPrincipalId());
            comment.setStateKey(CommentServiceConstants.COMMENT_ACTIVE_STATE_KEY);

            try {
                comment = getCommentService().createComment(form.getReferenceId(), form.getReferenceType(), CommentServiceConstants.COMMENT_GENERAL_REMARKS_TYPE_KEY, comment, ContextUtils.createDefaultContextInfo());
            } catch (Exception e) {
                LOG.error(String.format("Error adding comment for Ref Type %s with Ref Id %s ", form.getReferenceType(), form.getReferenceId()));
                throw new RuntimeException(String.format("Error adding comment for Ref Type %s with Ref Id %s ", form.getReferenceType(), form.getReferenceId()), e);
            }

//        } else {
//            try {
//                comment = getCommentService().updateComment(comment.getId(), comment, ContextUtils.createDefaultContextInfo());
//            } catch (Exception e) {
//                LOG.error("Error updating comment " + comment.getId() + " for the proposal " + proposalInfo.getName());
//                throw new RuntimeException("Error updating comment " + comment.getId() + " for the proposal " + proposalInfo.getName(), e);
//            }
//        }

        setupCommentWrapper(commentWrapper,comment);
        LOG.debug("Comment successfully added/updated. [id=" + comment.getId() + "]");

    }
//
//    protected void setupAuthorizations(ProposalInfo proposalInfo, KSCommentWrapper commentWrapper) {
//        commentWrapper.getRenderHelper().setCanEdit(canEditComment(proposalInfo,commentWrapper));
//        commentWrapper.getRenderHelper().setCanDelete(canDeleteComment(proposalInfo, commentWrapper));
//    }
//
//    protected boolean canEditComment(ProposalInfo proposalInfo, KSCommentWrapper commentWrapper) {
//        Map<String,String> permDetails = new HashMap<String, String>();
//        permDetails.put(StudentIdentityConstants.KS_REFERENCE_TYPE_KEY, StudentIdentityConstants.QUALIFICATION_PROPOSAL_REF_TYPE);
//        Map<String,String> roleQualifications = new HashMap<String, String>();
//        roleQualifications.put(KimIdentityServiceConstants.COMMENT_ID_QUALIFICATION, commentWrapper.getCommentInfo().getId());
//        return KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(GlobalVariables.getUserSession().getPrincipalId(), StudentIdentityConstants.PERMISSION_TEMPLATE_NAMESPACE_COMMENTS, StudentIdentityConstants.PERMISSION_TEMPLATE_NAME_COMMENTS_EDIT, permDetails, roleQualifications);
//    }
//
//    protected boolean canDeleteComment(ProposalInfo proposalInfo, KSCommentWrapper commentWrapper) {
//        Map<String,String> permDetails = new HashMap<String, String>();
//        permDetails.put(StudentIdentityConstants.KS_REFERENCE_TYPE_KEY, StudentIdentityConstants.QUALIFICATION_PROPOSAL_REF_TYPE);
//        Map<String,String> roleQualifications = new HashMap<String, String>();
//        roleQualifications.put(KimIdentityServiceConstants.COMMENT_ID_QUALIFICATION, commentWrapper.getCommentInfo().getId());
//        return KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(GlobalVariables.getUserSession().getPrincipalId(), StudentIdentityConstants.PERMISSION_TEMPLATE_NAMESPACE_COMMENTS, StudentIdentityConstants.PERMISSION_TEMPLATE_NAME_COMMENTS_DELETE, permDetails, roleQualifications);
//    }
//
//    protected boolean canAddComment(ProposalInfo proposalInfo) throws WorkflowException {
//        Map<String,String> permDetails = new HashMap<String, String>();
//        permDetails.put(StudentIdentityConstants.KS_REFERENCE_TYPE_KEY, StudentIdentityConstants.QUALIFICATION_PROPOSAL_REF_TYPE);
//        return KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(GlobalVariables.getUserSession().getPrincipalId(), StudentIdentityConstants.PERMISSION_TEMPLATE_NAMESPACE_COMMENTS, StudentIdentityConstants.PERMISSION_TEMPLATE_NAME_COMMENTS_ADD, permDetails, buildAddCommentAuthorizationRoleQualification(proposalInfo));
//    }
//
//    protected Map<String,String> buildAddCommentAuthorizationRoleQualification(ProposalInfo proposalInfo) throws WorkflowException {
//        Map<String,String> roleQualifications = new HashMap<String, String>();
//        roleQualifications.put(KimConstants.AttributeConstants.DOCUMENT_NUMBER, proposalInfo.getWorkflowId());
//        WorkflowDocument workflowDocument = KRADServiceLocatorWeb.getDocumentService().
//                getByDocumentHeaderId(proposalInfo.getWorkflowId()).getDocumentHeader().getWorkflowDocument();
//        roleQualifications.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, workflowDocument.getDocumentTypeName());
//
//        if (workflowDocument.isInitiated() || workflowDocument.isSaved()) {
//            roleQualifications.put(KimConstants.AttributeConstants.ROUTE_NODE_NAME, StudentWorkflowConstants.DEFAULT_WORKFLOW_DOCUMENT_START_NODE_NAME);
//        } else {
//            roleQualifications.put(KimConstants.AttributeConstants.ROUTE_NODE_NAME,
//                    KRADServiceLocatorWeb.getWorkflowDocumentService().getCurrentRouteNodeNames(workflowDocument));
//        }
//
//        roleQualifications.put(KimConstants.AttributeConstants.ROUTE_STATUS_CODE, workflowDocument.getStatus().getCode());
//        return roleQualifications;
//    }

    /**
     * This method retrieves all the comments for the proposal
     */
    protected void retrieveComments(KSCommentForm form) {

//        ProposalInfo proposal = form.getProposal();
//
//        LOG.debug("Retrieving comments for  - " + proposal.getId());

        List<CommentInfo> comments;

//        form.getComments().clear();

//        StudentIdentityConstants.QUALIFICATION_PROPOSAL_REF_TYPE
        try {
//            comments = getCommentService().getCommentsByReferenceAndType(form.getReferenceId(), form.getReferenceType(), ContextUtils.createDefaultContextInfo());
            comments = getCommentService().getCommentsByReferenceAndType(form.getReferenceId(), form.getReferenceType(), ContextUtils.createDefaultContextInfo());
            LOG.debug(String.format("Retrieved %d comments for Ref Type %s with Ref Id %s ", comments.size(), form.getReferenceType(), form.getReferenceId()));
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error retrieving comment(s) comments for Ref Type %s with Ref Id %s", form.getReferenceType(), form.getReferenceId()), e);
        }

        if (comments != null) {
            for (CommentInfo comment : comments) {
                KSCommentWrapper wrapper = new KSCommentWrapper();
//                setupCommentWrapper(wrapper,comment,proposal);
                setupCommentWrapper(wrapper, comment);
                form.getComments().add(wrapper);
            }
        }
    }

    protected void setupCommentWrapper(KSCommentWrapper wrapper, CommentInfo comment) {
        wrapper.setCommentInfo(comment);
        wrapper.setCreatedDate(DateFormatters.COURSE_OFFERING_VIEW_HELPER_DATE_TIME_FORMATTER.format(comment.getMeta().getCreateTime()));
        Person creator = getPersonService().getPerson(comment.getCommenterId());
        wrapper.setCreatorName(creator.getFirstName() + " " + creator.getLastName());
        wrapper.setLastEditedDate(DateFormatters.COURSE_OFFERING_VIEW_HELPER_DATE_TIME_FORMATTER.format(comment.getMeta().getUpdateTime()));
        Person lastEditor = getPersonService().getPerson(comment.getMeta().getUpdateId());
        wrapper.setLastEditorName(lastEditor.getFirstName() + " " + lastEditor.getLastName());
        if (lastEditor.getPrincipalId().equals(creator.getPrincipalId()) && comment.getMeta().getUpdateTime().equals(comment.getMeta().getCreateTime())) {
            wrapper.setEdited(false);
        } else {
            wrapper.setEdited(true);
        }
//        setupAuthorizations(proposalInfo, commentWrapper);
    }

    public PersonService getPersonService() {
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

}
