package org.kuali.student.core.mock.service;

import java.util.List;

import javax.jws.WebService;

import org.kuali.student.core.dto.RefDocRelationInfoMock;
import org.kuali.student.core.dto.StatusInfo;

@WebService(name = "DocumentRelationService", targetNamespace = "http://student.kuali.org/wsdl/documentRelation")
public interface DocumentRelationService {
	public List<RefDocRelationInfoMock> getRefDocIdsForRef(String id) throws Exception;
	public void createRefDocRelation(String refId, String docId, RefDocRelationInfoMock info) throws Exception;
	public StatusInfo deleteRefDocRelation(String id) throws Exception;
}
