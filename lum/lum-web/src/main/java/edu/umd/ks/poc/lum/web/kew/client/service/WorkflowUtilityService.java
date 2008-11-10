package edu.umd.ks.poc.lum.web.kew.client.service;

import java.util.List;

import edu.umd.ks.poc.lum.lu.dto.CluInfo;


public interface WorkflowUtilityService {
	public List<CluInfo> getClusForUser(String user);
//    public ActionItemDTO[] getActionItemsForUser(UuIdDTO userId) throws SerializableException;
//    public DocumentContentDTO getDocumentContent(Long routeHeaderId) throws SerializableException;


	public String getUser();
	public String getCluIdForDocument(String docId);
    public String switchUser(String userId);
}
