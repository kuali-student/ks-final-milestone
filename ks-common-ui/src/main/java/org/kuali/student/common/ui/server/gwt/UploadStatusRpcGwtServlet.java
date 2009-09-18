package org.kuali.student.common.ui.server.gwt;

import java.util.UUID;

import org.kuali.student.common.ui.client.dto.UploadStatus;
import org.kuali.student.common.ui.client.service.UploadStatusRpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UploadStatusRpcGwtServlet extends RemoteServiceServlet implements UploadStatusRpcService{
	private static final long serialVersionUID = 1L;

	@Override
	public UploadStatus getUploadStatus(String uploadId) {
		UploadStatus status = (UploadStatus) (getThreadLocalRequest().getSession().getAttribute(uploadId));
		return status;
	}

	@Override
	public String getUploadId() {
		return UUID.randomUUID().toString();
	}
}
