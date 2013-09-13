package org.kuali.student.myplan.comment.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.myplan.comment.dataobject.MessageDataObject;
import org.kuali.student.myplan.main.service.MyPlanLookupableImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.core.constants.CommentServiceConstants;

public class MessagesLookupableHelperImpl extends MyPlanLookupableImpl {

	private static final long serialVersionUID = 0xa00b01c00l;

	private transient CommentService commentService;
	private transient CommentQueryHelper commentQueryHelper;

	@Override
	protected List<MessageDataObject> getSearchResults(LookupForm lookupForm,
			Map<String, String> fieldValues, boolean unbounded) {
		String studentId = KsapFrameworkServiceLocator.getUserSessionHelper().getStudentId();
		List<MessageDataObject> messages = CommentQueryHelper.getMessages(studentId);

		Collections.sort(messages, new Comparator<MessageDataObject>() {
			@Override
			public int compare(MessageDataObject message1,
					MessageDataObject message2) {
				Date d1 = message1.getLastCommentDate();
				Date d2 = message2.getLastCommentDate();
				if (message1.getLastCommentDate() == null) {
					d1 = message1.getCreateDate();
				}
				if (message2.getLastCommentDate() == null) {
					d2 = message2.getCreateDate();
				}
				return d2.compareTo(d1);
			}
		});
		return messages;
	}

	public CommentService getCommentService() {
		if (commentService == null) {
			commentService = (CommentService) GlobalResourceLoader
					.getService(new QName(CommentServiceConstants.NAMESPACE,
							"CommentService"));
		}
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public CommentQueryHelper getCommentQueryHelper() {
		if (commentQueryHelper == null) {
			commentQueryHelper = new CommentQueryHelper();
		}
		return commentQueryHelper;
	}
}
