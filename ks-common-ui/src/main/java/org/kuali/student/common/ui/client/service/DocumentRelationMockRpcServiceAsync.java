package org.kuali.student.common.ui.client.service;

import java.util.List;

import org.kuali.student.core.dto.RefDocRelationInfoMock;
import org.kuali.student.core.dto.StatusInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DocumentRelationMockRpcServiceAsync extends BaseRpcServiceAsync{
	public void getRefDocIdsForRef(String id, AsyncCallback<List<RefDocRelationInfoMock>> callback);
	public void createRefDocRelation(String refId, String docId, RefDocRelationInfoMock info, AsyncCallback callback);
	public void deleteRefDocRelation(String id, AsyncCallback<StatusInfo> callback) throws Exception;
}
