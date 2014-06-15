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
package org.kuali.student.cm.course.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.KsUifControllerBase;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.CMCommentForm;
import org.kuali.student.cm.course.form.CommentWrapper;
import org.kuali.student.common.util.security.ContextUtils;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * This class handles the comment functionality for a proposal.
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = CurriculumManagementConstants.ControllerRequestMappings.CM_COMMENT)
public class CMCommentController extends KsUifControllerBase {

    private static final Logger LOG = LoggerFactory.getLogger(CMCommentController.class);

    protected CommentService commentService;
    protected ProposalService proposalService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CMCommentForm();
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
            HttpServletResponse response) {

        CMCommentForm commentForm = (CMCommentForm)form;

        String proposalId = request.getParameter("proposalId");

        if (StringUtils.isBlank(proposalId)){
            throw new RuntimeException("Missing proposal Id");
        }

        try {
            ProposalInfo proposalInfo = getProposalService().getProposal(proposalId,ContextUtils.createDefaultContextInfo());
            commentForm.setProposal(proposalInfo);
            retrieveComments(commentForm);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Proposal [id=" + proposalId + "]");
        }

        return super.start(form,request,response);
    }

    /**
     * This method submits a new comment or the comment editing by the user
     *
     * @param form     {@link org.kuali.rice.krad.web.form.MaintenanceDocumentForm} instance used for this action
     * @throws Exception
     */
    @MethodAccessible
    @RequestMapping(params = "methodToCall=saveComment")
    public ModelAndView saveComment(@ModelAttribute("KualiForm") CMCommentForm form) throws Exception {

        if (form.getProposal() == null){
            throw new RuntimeException("Proposal not found.");
        }

        String collectionPath = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_PATH);

        /**
         * If it's new comment, just create a new wrapper and call save, which should initialize all the details at the DTO
         */
        if (StringUtils.isBlank(collectionPath)){

            CommentWrapper wrapper = new CommentWrapper();
            wrapper.getCommentInfo().getCommentText().setPlain(form.getNewComment());
            saveComment(form.getProposal(),wrapper);
            form.getComments().add(wrapper);

            form.setNewComment(StringUtils.EMPTY);

        } else {

            int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
            saveComment(form.getProposal(), form.getComments().get(index));
        }

        //Reset edit in progress flag from all the comments.
        for (CommentWrapper commentWrapper : form.getComments()){
            commentWrapper.getRenderHelper().setEditInProgress(false);
        }

        form.setDeletedComment(null);

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=cancelEditComment")
    public ModelAndView cancelEditComment(@ModelAttribute("KualiForm") CMCommentForm form) throws Exception {

        for (CommentWrapper commentWrapper : form.getComments()){
            commentWrapper.getRenderHelper().setEditInProgress(false);
        }

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=editComment")
    public ModelAndView editComment(@ModelAttribute("KualiForm") CMCommentForm form) throws Exception {

        if (form.getProposal() == null){
            throw new RuntimeException("Proposal not found.");
        }

        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        form.getComments().get(index).getRenderHelper().setEditInProgress(true);

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=deleteComment")
    public ModelAndView deleteComment(@ModelAttribute("KualiForm") CMCommentForm form) throws Exception {

        if (form.getProposal() == null){
            throw new RuntimeException("Proposal not found.");
        }

        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));

        CommentWrapper commentWrapper = form.getComments().remove(index);
        getCommentService().deleteComment(commentWrapper.getCommentInfo().getId(),ContextUtils.createDefaultContextInfo());

        commentWrapper.getCommentInfo().setId(null); //Clear out the ID sothat we can add that to DB if user decided to undo later
        form.setDeletedComment(commentWrapper);

        return getUIFModelAndView(form);

    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=undoDeleteComment")
    public ModelAndView undoDeleteComment(@ModelAttribute("KualiForm") CMCommentForm form) throws Exception {

        CommentWrapper deletedComment = form.getDeletedComment();
        saveComment(form.getProposal(),deletedComment);
        form.getComments().add(deletedComment);
        form.setDeletedComment(null);

        return getUIFModelAndView(form);
    }

    /**
     * Create or update a comment.
     *
     * @param proposalInfo
     * @param commentWrapper
     */
    protected void saveComment(ProposalInfo proposalInfo,CommentWrapper commentWrapper) {

        LOG.trace("Saving comment - " + commentWrapper.getCommentInfo().getCommentText());

        CommentInfo comment = commentWrapper.getCommentInfo();

        if (commentWrapper.isNewDto()) {

            comment.setReferenceId(proposalInfo.getId());
            comment.setReferenceTypeKey(proposalInfo.getTypeKey());
            comment.setTypeKey(CommentServiceConstants.COMMENT_GENERAL_REMARKS_TYPE_KEY);
            DateFormat dateFormat = new SimpleDateFormat("MMMMM dd, yyyy, HH:mm a");
            Date date = new Date();
            comment.setCommenterId(GlobalVariables.getUserSession().getPrincipalId() + " on " + dateFormat.format(date));
            comment.setStateKey(CommentServiceConstants.COMMENT_ACTIVE_STATE_KEY);
            try {
                comment = getCommentService().createComment(proposalInfo.getId(), proposalInfo.getTypeKey(), CommentServiceConstants.COMMENT_GENERAL_REMARKS_TYPE_KEY, comment, ContextUtils.createDefaultContextInfo());
            } catch (Exception e) {
                LOG.error("Error adding comment " + comment.getId() + " for the proposal " + proposalInfo.getName());
                throw new RuntimeException("Error adding comment " + comment.getId() + " for the proposal " + proposalInfo.getName(), e);
            }

        } else {
            try {
                comment = getCommentService().updateComment(comment.getId(), comment, ContextUtils.createDefaultContextInfo());
            } catch (Exception e) {
                LOG.error("Error updating comment " + comment.getId() + " for the proposal " + proposalInfo.getName());
                throw new RuntimeException("Error updating comment " + comment.getId() + " for the proposal " + proposalInfo.getName(), e);
            }
        }

        commentWrapper.setCommentInfo(comment);

        LOG.debug("Comment successfully added/updated. [id=" + comment.getId() + "]");

    }

    /**
     * This method retrieves all the comments for the proposal
     */
    protected void retrieveComments(CMCommentForm form) {

        ProposalInfo proposal = form.getProposal();

        LOG.debug("Retrieving comments for  - " + proposal.getId());

        List<CommentInfo> comments;

        form.getComments().clear();

        try {
            comments = getCommentService().getCommentsByReferenceAndType(proposal.getId(), proposal.getTypeKey(), ContextUtils.createDefaultContextInfo());
            LOG.debug("Retrieved " + comments.size() + " comments for proposal " + proposal.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving comment(s) for the proposal [id=" + proposal.getId() + "]", e);
        }

        if (comments != null) {
            for (CommentInfo comment : comments) {
                CommentWrapper wrapper = new CommentWrapper();
                wrapper.setCommentInfo(comment);
//                Person person = getPersonService().getPerson(comment.getMeta().getCreateId());
                //              wrapper.getRenderHelper().setUser(person.getNameUnmasked());
                wrapper.getRenderHelper().setDateTime(DateFormatters.MONTH_DATE_YEAR_TIME_COMMA_FORMATTER.format(comment.getMeta().getCreateTime()));
                form.getComments().add(wrapper);
            }
        }
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
