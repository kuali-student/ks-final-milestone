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
package org.kuali.student.common.uif.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MethodAccessible;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.form.KSCommentForm;
import org.kuali.student.common.uif.form.KSCommentWrapper;
import org.kuali.student.common.uif.json.KSCommentJSONResponseData;
import org.kuali.student.common.uif.util.CommentUtil;
import org.kuali.student.common.uif.util.KSCommentsConstants;
import org.kuali.student.common.util.security.ContextUtils;
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
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * This class handles the comment functionality for a proposal.
 *
 * @author Kuali Student Team
 */

public abstract class KSCommentController extends UifControllerBase {

    private static final Logger LOG = LoggerFactory.getLogger(KSCommentController.class);

    public static final int OPERATION_VIEW = 1;
    public static final int OPERATION_EDIT = 2;
    public static final int OPERATION_DELETE = 3;
    public static final int OPERATION_ADD = 4;

    public static final String VALID_TEXT = "VALID_TEXT";

    private Map<String, String[]> originalParametersMap;
    protected PersonService personService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new KSCommentForm();
    }

    protected String getRequestParamValue(HttpServletRequest request, String paramName) {
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

        originalParametersMap = new HashMap<String, String[]>(request.getParameterMap());
        KSCommentForm commentForm = (KSCommentForm) form;
        String refId = getRequestParamValue(request, "refId");
        String refType = getRequestParamValue(request, "refType");
        String refName = getRequestParamValue(request, "refName");

        String url = request.getRequestURI();
        String controllerUrl = url.substring(url.lastIndexOf("/"), url.length());

        commentForm.setReferenceId(refId);
        commentForm.setReferenceType(refType);
        commentForm.setReferenceName(refName);
        commentForm.setControllerUrl(controllerUrl);

        commentForm.setCanAddComment(isOperationPermitted(OPERATION_ADD, commentForm, originalParametersMap, request));
        KSCommentWrapper wrapper = new KSCommentWrapper();
        commentForm.setCanDeleteComment(isOperationPermitted(OPERATION_DELETE, commentForm, originalParametersMap, request));
        commentForm.setCanEditComment(isOperationPermitted(OPERATION_EDIT, commentForm, originalParametersMap, request));
        retrieveComments(commentForm);

        return super.start(form, request, response);
    }


    @MethodAccessible
    @RequestMapping(params = "methodToCall=addComment")
    public ModelAndView addComment(@ModelAttribute("KualiForm") KSCommentForm form, HttpServletRequest request) throws Exception {

        String validationMessage = CommentUtil.isValidText(form.getCommentText(), 1, 500, 4000);
        if(!validationMessage.equals(VALID_TEXT)){
//            GlobalVariables.getMessageMap().putErrorForSectionId("commentText",  validationMessage);
            GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath("commentText", validationMessage);
            return getUIFModelAndView(form);
        }

        if(!isOperationPermitted(OPERATION_ADD, form, originalParametersMap, request)){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, KSCommentsConstants.KSCOMMENT_MSG_ERROR_NO_ADD_PERMISSION);
        }
        KSCommentWrapper wrapper = new KSCommentWrapper();
        wrapper.getCommentInfo().getCommentText().setPlain(form.getCommentText());
        saveComment(form, wrapper);
        form.setCommentText("");
        retrieveComments(form);

        return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=updateComment")
    public ModelAndView updateComment(@ModelAttribute("KualiForm") KSCommentForm form, HttpServletRequest request) throws Exception {
            int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
            KSCommentWrapper commentWrapper = form.getComments().get(index);
            CommentInfo comment = commentWrapper.getCommentInfo();
            try {
                comment = CommentUtil.getCommentService().updateComment(comment.getId(), comment, ContextUtils.createDefaultContextInfo());
            } catch (Exception e) {
                String message = String.format("Error updating comment for Ref Type %s with Ref Id %s ", form.getReferenceType(), form.getReferenceId());
                LOG.error(message);
                throw new RuntimeException(message, e);
            }
            setupCommentWrapper(form, commentWrapper, comment);
            return getUIFModelAndView(form);
    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=ajaxUpdateComment")
    public @ResponseBody
    KSCommentJSONResponseData ajaxUpdateComment(@ModelAttribute("KualiForm") KSCommentForm form, HttpServletRequest request) throws Exception {
        KSCommentJSONResponseData responseData = new KSCommentJSONResponseData();
        responseData.setHasErrors(false);

        if (!isOperationPermitted(OPERATION_EDIT, form, originalParametersMap, request)) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, KSCommentsConstants.KSCOMMENT_MSG_ERROR_NO_EDIT_PERMISSION);
            responseData.setHasErrors(true);
        } else {
            int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
            KSCommentWrapper commentWrapper = form.getComments().get(index);
            CommentInfo comment = commentWrapper.getCommentInfo();
            String validationMessage = CommentUtil.isValidText(commentWrapper.getCommentInfo().getCommentText().getPlain(), 1, 500, 4000);
            if (!validationMessage.equals(VALID_TEXT)) {
                GlobalVariables.getMessageMap().putErrorForSectionId("commentInfo.commentText.plain", validationMessage);
                responseData.setHasErrors(true);
            } else {
                try {
                    comment = CommentUtil.getCommentService().updateComment(comment.getId(), comment, ContextUtils.createDefaultContextInfo());
                } catch (Exception e) {
                    String message = String.format("Error updating comment for Ref Type %s with Ref Id %s ", form.getReferenceType(), form.getReferenceId());
                    LOG.error(message);
                    throw new RuntimeException(message, e);
                }     }
            setupCommentWrapper(form, commentWrapper, comment);
            responseData.setCommentWrapper(commentWrapper);
        }
        responseData.setMessageMap(GlobalVariables.getMessageMap());
        return responseData;
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

        form.getComments().remove(commentWrapper);
        CommentUtil.getCommentService().deleteComment(commentWrapper.getCommentInfo().getId(), ContextUtils.createDefaultContextInfo());

        commentWrapper.getCommentInfo().setId(null); //Clear out the ID so that we can add that to DB if user decided to undo later

        return getUIFModelAndView(form);

    }

    @MethodAccessible
    @RequestMapping(params = "methodToCall=ajaxDeleteComment")
    public @ResponseBody KSCommentJSONResponseData ajaxDeleteComment(@ModelAttribute("KualiForm") KSCommentForm form, HttpServletRequest request) throws Exception {

        KSCommentJSONResponseData responseData = new KSCommentJSONResponseData();
        responseData.setHasErrors(false);
        if (!isOperationPermitted(OPERATION_DELETE, form, originalParametersMap, request)) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, KSCommentsConstants.KSCOMMENT_MSG_ERROR_NO_DELETE_PERMISSION);
            responseData.setHasErrors(true);
        } else {
            int index = Integer.parseInt(form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX));
            KSCommentWrapper commentWrapper = form.getComments().get(index);

            form.getComments().remove(commentWrapper);
            CommentUtil.getCommentService().deleteComment(commentWrapper.getCommentInfo().getId(), ContextUtils.createDefaultContextInfo());
            responseData.setCount(CommentUtil.getCommentsCount(form.getReferenceId(), form.getReferenceType(), ContextUtils.createDefaultContextInfo()));
        }

        responseData.setMessageMap(GlobalVariables.getMessageMap());
        return responseData;
    }

    private void saveComment(KSCommentForm form, KSCommentWrapper commentWrapper) {

        CommentInfo comment = commentWrapper.getCommentInfo();

        comment.setRefObjectId(form.getReferenceId());
        comment.setRefObjectUri(form.getReferenceType());
        comment.setTypeKey(CommentServiceConstants.COMMENT_GENERAL_REMARKS_TYPE_KEY);
        comment.setCommenterId(GlobalVariables.getUserSession().getPrincipalId());
        comment.setStateKey(CommentServiceConstants.COMMENT_ACTIVE_STATE_KEY);

        try {
            comment = CommentUtil.getCommentService().createComment(form.getReferenceId(), form.getReferenceType(), CommentServiceConstants.COMMENT_GENERAL_REMARKS_TYPE_KEY, comment, ContextUtils.createDefaultContextInfo());
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
            comments = CommentUtil.getCommentService().getCommentsByRefObject(form.getReferenceId(), form.getReferenceType(), ContextUtils.createDefaultContextInfo());
            LOG.debug(String.format("Retrieved %d comments for Ref Type %s with Ref Id %s ", comments.size(), form.getReferenceType(), form.getReferenceId()));
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error retrieving comment(s) for Ref Type %s with Ref Id %s", form.getReferenceType(), form.getReferenceId()), e);
        }

        form.getComments().clear();
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
    }

    private PersonService getPersonService() {
        if (personService == null) {
            personService = KimApiServiceLocator.getPersonService();
        }
        return personService;
    }


    /*
    * The subclasses of this controller implement this method to return a boolean indicating whether the user is authorized
    * to perform the operation specified by the operation parameter.
    *
    * params:
    *   operation int, one of the following constants:
    *           OPERATION_VIEW
    *           OPERATION_EDIT
    *           OPERATION_DELETE
    *           OPERATION_ADD
    *   form KSCommentForm
    *   originalParametersMap an unmodifiable Map, Contains the original parameters passed to the url to open the lightbox
    *   request  HttpServletRequest
    */
    protected abstract boolean isOperationPermitted(final int operation, KSCommentForm form, Map<String, String[]> originalParametersMap, HttpServletRequest request);
}
