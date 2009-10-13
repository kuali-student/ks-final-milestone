package org.kuali.student.common.ui.server.gwt;

import java.util.List;

import org.kuali.student.common.ui.client.service.DocumentRelationMockRpcService;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.dto.RefDocRelationInfoMock;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.mock.service.DocumentRelationService;

public class DocumentRelationMockRpcGwtServlet extends BaseRpcGwtServletAbstract<DocumentRelationService> implements DocumentRelationMockRpcService{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void createRefDocRelation(String refId, String docId, RefDocRelationInfoMock relInfo) throws Exception{
		service.createRefDocRelation(refId, docId, relInfo);
	}

	@Override
	public List<RefDocRelationInfoMock> getRefDocIdsForRef(String id) throws Exception{
		return service.getRefDocIdsForRef(id);
	}

	@Override
	public StatusInfo deleteRefDocRelation(String id) throws Exception {
		// TODO Auto-generated method stub
		return service.deleteRefDocRelation(id);
	}

}
