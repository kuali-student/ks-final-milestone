package org.kuali.student.common.ui.server.gwt;

import java.util.List;

import org.kuali.student.common.ui.client.service.CommentRpcService;
import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.comment.service.CommentService;

public class CommentRpcGwtServlet extends BaseRpcGwtServletAbstract<CommentService> implements CommentRpcService {

	private static final long serialVersionUID = 1L;

	@Override
	public CommentInfo addComment(String referenceId, String referenceTypeKey,
			CommentInfo commentInfo) throws Exception {
		return service.addComment(referenceId, referenceTypeKey, commentInfo);
	}

	@Override
	public List<CommentInfo> getComments(String referenceId,
			String referenceTypeKey) throws Exception {
		return service.getComments(referenceId, referenceTypeKey);
	}

	@Override
	public List<CommentInfo> getCommentsByType(String referenceId,
			String referenceTypeKey, String commentTypeKey) throws Exception {
		return service.getCommentsByType(referenceId, referenceTypeKey, commentTypeKey);
	}

	@Override
	public CommentInfo updateComment(String referenceId,
			String referenceTypeKey, CommentInfo commentInfo) throws Exception {
		return service.updateComment(referenceId, referenceTypeKey, commentInfo);
	}

}
