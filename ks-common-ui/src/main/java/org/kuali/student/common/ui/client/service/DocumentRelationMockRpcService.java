package org.kuali.student.common.ui.client.service;

import java.util.List;
import org.kuali.student.core.dto.RefDocRelationInfoMock;
import org.kuali.student.core.dto.StatusInfo;


import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/DocumentRelationMockRpcService")
public interface DocumentRelationMockRpcService extends BaseRpcService{
	public List<RefDocRelationInfoMock> getRefDocIdsForRef(String id) throws Exception;
	public void createRefDocRelation(String refId, String docId, RefDocRelationInfoMock info) throws Exception;
	public StatusInfo deleteRefDocRelation(String id) throws Exception;
}
