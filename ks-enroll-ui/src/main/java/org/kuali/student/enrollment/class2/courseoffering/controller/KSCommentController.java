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
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.KsUifControllerBase;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.appointment.util.AppointmentConstants;
import org.kuali.student.enrollment.class2.courseoffering.form.KSCommentForm;
import org.kuali.student.enrollment.class2.courseoffering.form.KSCommentWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.KSCommentsConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.core.constants.CommentServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

//import org.kuali.student.cm.course.form.CMCommentForm;
//import org.kuali.student.cm.course.form.KSCommentWrapper;

/**
 * This class handles the comment functionality for a proposal.
 *
 * @author Kuali Student Team
 */

public abstract class KSCommentController extends KsUifControllerBase {

    public final int OPERATION_VIEW = 1;
    public final int OPERATION_EDIT = 2;
    public final int OPERATION_DELETE = 3;
    public final int OPERATION_ADD = 4;

    private static final Logger LOG = LoggerFactory.getLogger(KSCommentController.class);
    protected CommentService commentService;
    //    protected ProposalService proposalService;
    protected PersonService personService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new KSCommentForm();
    }

    private String getRequestParamValue(HttpServletRequest request, String paramName) {
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

        String socState = getRequestParamValue(request, "socState");
        String subjectArea = getRequestParamValue(request, "subjectArea");
        String offeringAdminOrgId = getRequestParamValue(request, "offeringAdminOrgId");

        // Note that the referer is only used to redirect after the lightbox is closed. No risk is involved.
        ((KSCommentForm) form).setParentUrl(request.getHeader("referer"));
        commentForm.setReferenceId(refId);
        commentForm.setReferenceType(refType);
        commentForm.setReferenceName(refName);
        commentForm.setSocState(socState);
        commentForm.setSubjectArea(subjectArea);
        commentForm.setOfferingAdminOrgId(offeringAdminOrgId);

        commentForm.setCanAddComment(isOperationPermitted(OPERATION_ADD, commentForm));
        KSCommentWrapper wrapper = new KSCommentWrapper();
        commentForm.setCanDeleteComment(isOperationPermitted(OPERATION_DELETE, commentForm));
        commentForm.setCanEditComment(isOperationPermitted(OPERATION_EDIT, commentForm));
        retrieveComments(commentForm);

        return super.start(form, request, response);
    }


    @MethodAccessible
    @RequestMapping(params = "methodToCall=addComment")
    public ModelAndView addComment(@ModelAttribute("KualiForm") KSCommentForm form, HttpServletRequest request) throws Exception {

        if (StringUtils.isEmpty(form.getCommentText())) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, KSCommentsConstants.KSCOMMENT_MSG_ERROR_EMPTY_TEXT_FIELD);
            return getUIFModelAndView(form);
        }
        KSCommentWrapper wrapper = new KSCommentWrapper();
        wrapper.getCommentInfo().getCommentText().setPlain(form.getCommentText());
        saveComment(form, wrapper);
        form.setCommentText("");
        form.getComments().add(0, wrapper);

        return getUIFModelAndView(form);
    }

        @MethodAccessible
    @RequestMapping(params = "methodToCall=updateComment")
    public ModelAndView updateComment(@ModelAttribute("KualiForm") KSCommentForm form, HttpServletRequest request) throws Exception {
            int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
            KSCommentWrapper commentWrapper = form.getComments().get(index);
            CommentInfo comment = commentWrapper.getCommentInfo();
            try {
                comment = getCommentService().updateComment(comment.getId(), comment, ContextUtils.createDefaultContextInfo());
            } catch (Exception e) {
                String message = String.format("Error updating comment for Ref Type %s with Ref Id %s ", form.getReferenceType(), form.getReferenceId());
                LOG.error(message);
                throw new RuntimeException(message);
            }
            setupCommentWrapper(form, commentWrapper, comment);
            return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=undeleteComment")
    public ModelAndView undeleteComment(@ModelAttribute("KualiForm") KSCommentForm form) throws Exception {

        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));

        KSCommentWrapper wrapper = new KSCommentWrapper();
        wrapper.setCommentInfo(form.getComments().get(index).getCommentInfo());
        wrapper.getCommentInfo().setId(null);

        saveComment(form, wrapper);
        form.getComments().add(0, wrapper);

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=ajaxUndeleteComment")
    public @ResponseBody List<String> ajaxUndeleteComment(@ModelAttribute("KualiForm") KSCommentForm form, HttpServletRequest request) throws Exception {

//        String collectionPath = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_PATH);
        int index = Integer.parseInt(request.getParameter(UifParameters.SELECTED_LINE_INDEX));

        KSCommentWrapper wrapper = new KSCommentWrapper();
        wrapper.setCommentInfo(form.getComments().get(index).getCommentInfo());
        wrapper.getCommentInfo().setId(null);
        saveComment(form, wrapper);
        form.getComments().add(0, wrapper);
        return new ArrayList<String>();
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=deleteComment")
    public ModelAndView deleteComment(@ModelAttribute("KualiForm") KSCommentForm form) throws Exception {

        int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
        KSCommentWrapper commentWrapper = form.getComments().get(index);

        // verify that current user is able to delete this comment
//        if (!canDeleteComment(form.getProposal(),commentWrapper)) {
//            throw new RuntimeException("Current user '" + GlobalVariables.getUserSession().getPrincipalId() + "' is not authorized to delete comment id '" + commentWrapper.getCommentInfo().getId() + "'");
//        }
        form.getComments().remove(commentWrapper);
        getCommentService().deleteComment(commentWrapper.getCommentInfo().getId(), ContextUtils.createDefaultContextInfo());

        commentWrapper.getCommentInfo().setId(null); //Clear out the ID so that we can add that to DB if user decided to undo later
//        form.setDeletedComment(commentWrapper);

        return getUIFModelAndView(form);

    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=ajaxDeleteComment")
    public @ResponseBody List<String> ajaxDeleteComment(@ModelAttribute("KualiForm") KSCommentForm form, HttpServletRequest request) throws Exception {

        int index = Integer.parseInt(request.getParameter(UifParameters.SELECTED_LINE_INDEX));
        KSCommentWrapper commentWrapper = form.getComments().get(index);

        form.getComments().remove(commentWrapper);
        getCommentService().deleteComment(commentWrapper.getCommentInfo().getId(), ContextUtils.createDefaultContextInfo());

        commentWrapper.getCommentInfo().setId(null); //Clear out the ID so that we can add that to DB if user decided to undo later
        return new ArrayList<String>();
    }

    private void saveComment(KSCommentForm form, KSCommentWrapper commentWrapper) {

//        LOG.trace("Saving comment - " + commentWrapper.getCommentInfo().getCommentText());

        CommentInfo comment = commentWrapper.getCommentInfo();

//        if (commentWrapper.isNewDto()) {

        comment.setRefObjectId(form.getReferenceId());
        comment.setRefObjectUri(form.getReferenceType());
        comment.setTypeKey(CommentServiceConstants.COMMENT_GENERAL_REMARKS_TYPE_KEY);
        comment.setCommenterId(GlobalVariables.getUserSession().getPrincipalId());
        comment.setStateKey(CommentServiceConstants.COMMENT_ACTIVE_STATE_KEY);

        try {
            comment = getCommentService().createComment(form.getReferenceId(), form.getReferenceType(), CommentServiceConstants.COMMENT_GENERAL_REMARKS_TYPE_KEY, comment, ContextUtils.createDefaultContextInfo());
        } catch (Exception e) {
            LOG.error(String.format("Error adding comment for Ref Type %s with Ref Id %s ", form.getReferenceType(), form.getReferenceId()));
            throw new RuntimeException(String.format("Error adding comment for Ref Type %s with Ref Id %s ", form.getReferenceType(), form.getReferenceId()), e);
        }

        setupCommentWrapper(form, commentWrapper, comment);
        LOG.debug("Comment successfully added/updated. [id=" + comment.getId() + "]");

    }

    private void retrieveComments(KSCommentForm form) {
        List<CommentInfo> comments;
        try {
            comments = getCommentService().getCommentsByRefObject(form.getReferenceId(), form.getReferenceType(), ContextUtils.createDefaultContextInfo());
            LOG.debug(String.format("Retrieved %d comments for Ref Type %s with Ref Id %s ", comments.size(), form.getReferenceType(), form.getReferenceId()));
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error retrieving comment(s) for Ref Type %s with Ref Id %s", form.getReferenceType(), form.getReferenceId()), e);
        }

        if (comments != null) {
            for (CommentInfo comment : comments) {
                KSCommentWrapper wrapper = new KSCommentWrapper();
                setupCommentWrapper(form, wrapper, comment);
                form.getComments().add(wrapper);
            }
        }
    }

    private void setupCommentWrapper(KSCommentForm form, KSCommentWrapper wrapper, CommentInfo comment) {
        wrapper.setCommentInfo(comment);
        if (comment.getCommentText() != null) {
            wrapper.setCommentTextUI(comment.getCommentText().getPlain());
        }
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
//        wrapper.setCanDeleteComment(canEditComment(wrapper.getCommentInfo().getId(),comment.getRefObjectUri()));
//        wrapper.setCanEditComment(canEditComment(wrapper.getCommentInfo().getId(),comment.getRefObjectUri()));
//        form.setCanDeleteComment(isOperationPermitted(OPERATION_DELETE, form));
//        form.setCanEditComment(isOperationPermitted(OPERATION_EDIT, form));
    }

    private PersonService getPersonService() {
        if (personService == null) {
            personService = KimApiServiceLocator.getPersonService();
        }
        return personService;
    }

    private CommentService getCommentService() {
        if (commentService == null) {
            commentService = (CommentService) GlobalResourceLoader.getService(new QName(CommentServiceConstants.NAMESPACE, CommentService.class.getSimpleName()));
        }
        return commentService;
    }

     protected abstract boolean isOperationPermitted(final int operation, KSCommentForm form);
}
