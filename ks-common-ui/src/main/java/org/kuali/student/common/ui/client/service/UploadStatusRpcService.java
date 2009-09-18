package org.kuali.student.common.ui.client.service;

import org.kuali.student.common.ui.client.dto.UploadStatus;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/UploadStatusRpcService")
public interface UploadStatusRpcService extends RemoteService{
	public UploadStatus getUploadStatus(String uploadId);
	public String getUploadId();
}
