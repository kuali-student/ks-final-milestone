package org.kuali.student.ap.comment;

import org.kuali.student.r2.core.constants.CommentServiceConstants;

public class CommentConstants extends CommentServiceConstants {
    public static final String SERVICE_NAME = "CommentService";

    public static final String MESSAGE_TYPE = "kuali.uw.comment.type.academicPlanning.message";
    public static final String COMMENT_TYPE = "kuali.uw.comment.type.academicPlanning.comment";
    public static final String MESSAGE_REF_TYPE = "kuali.uw.comment.reference.type.person";
    public static final String COMMENT_REF_TYPE = "kuali.uw.comment.reference.type.academicPlanning.message";

    public static final String SUBJECT_ATTRIBUTE_NAME = "subject";
    public static final String CREATED_BY_USER_ATTRIBUTE_NAME = "createdBy";

    public static final String SUCCESS_KEY_MESSAGE_ADDED = "ksap.text.success.comment.messageAdded";
    public static final String SUCCESS_KEY_COMMENT_ADDED = "ksap.text.success.comment.commentAdded";

    public static final String ERROR_KEY_NOTIFICATION_FAILED = "ksap.text.error.comment.notificationFailed";

    public static final String MESSAGE_RESPONSE_PAGE = "message_dialog_response_page";
    public static final String MESSAGE_SUBJECT_BOX = "message_dialog_subject";
    public static final String MESSAGE_MESSAGE_BOX = "message_textbox";

    public static final String COMMENT_MESSAGE_BOX = "commenting_textbox";
    public static final String COMMENT_RESPONSE_PAGE = "comment_dialog_response_page";
    public static final String ADVISER_ACCESS_ERROR = "ksap.text.error.message.adviserAccessError";

    public static final String EMPTY_MESSAGE = "ksap.text.error.message.emptyMessage";
    public static final String EMPTY_COMMENT = "ksap.text.error.message.emptyComment";
    public static final String EMPTY_TO_ADDRESS = "ksap.text.error.message.emptyToAddress";
    public static final String SPECIAL_CHARACTERS_ERROR = "ksap.text.error.message.specialCharacterError";

    public static final String EMAIL_COMMENT_SUBJECT = "ksap.commment.subject";
    public static final String EMAIL_MESSAGE_SUBJECT = "ksap.message.subject";
    public static final String EMAIL_BODY = "ksap.email.body";
    public static final String EMAIL_FROM = "ksap.comment.fromAddress";

    public static final String MESSAGE_LINK = "ksap.message.env.link";
    public static final String ADVISER_MESSAGE_LINK = "ksap.adviser.message.env.link";
    
    public static final String SWS_URL_PARAM="uw.studentservice.url";


}
