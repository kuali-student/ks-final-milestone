package org.kuali.student.common.ui.client.service;

import org.kuali.student.common.ui.client.dto.UploadStatus;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UploadStatusRpcServiceAsync {
	public void getUploadStatus(String uploadId, AsyncCallback<UploadStatus> callback);
	
	public void getUploadId(AsyncCallback<String> callback);
}
