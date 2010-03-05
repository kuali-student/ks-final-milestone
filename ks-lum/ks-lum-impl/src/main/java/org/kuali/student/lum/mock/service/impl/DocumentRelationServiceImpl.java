/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.mock.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.kuali.student.core.dto.RefDocRelationInfoMock;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.mock.service.DocumentRelationService;
import org.kuali.student.lum.lu.dto.LuDocRelationInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.service.impl.LuServiceImpl;

@WebService(endpointInterface = "org.kuali.student.core.mock.service.DocumentRelationService", serviceName = "DocumentRelationService", portName = "DocumentRelationService", targetNamespace = "http://student.kuali.org/wsdl/documentRelation")
public class DocumentRelationServiceImpl implements DocumentRelationService{

	LuService service;
	@Override
	public void createRefDocRelation(String refId, String docId,RefDocRelationInfoMock relInfo) throws Exception {
		LuDocRelationInfo info = new LuDocRelationInfo();
		info.setCluId(refId);
		info.setDocumentId(docId);
		info.setTitle(relInfo.getTitle());
		RichTextInfo text = new RichTextInfo();
		info.setDesc(relInfo.getDesc());
		//TODO: Fix with LuService RC1.4 changes
		//service.createLuDocRelationForClu("luDocRelationType.doctype1", docId, refId, info);
		//service.deleteLuDocRelation(luDocRelationId)
	}

	@Override
	public List<RefDocRelationInfoMock> getRefDocIdsForRef(String id)
			throws Exception {
		List<RefDocRelationInfoMock> relations = new ArrayList<RefDocRelationInfoMock>();
		//TODO: Fix with LuService RC1.4 changes		
//		List<LuDocRelationInfo> infos = service.getLuDocRelationsByClu(id);
//		for(LuDocRelationInfo i : infos){
//			RefDocRelationInfoMock relInfo = new RefDocRelationInfoMock();
//			relInfo.setRefId(i.getCluId());
//			relInfo.setDocumentId(i.getDocumentId());
//			relInfo.setId(i.getId());
//			relInfo.setMetaInfo(i.getMetaInfo());
//			relInfo.setTitle(i.getTitle());
//			relInfo.setDesc(i.getDesc());
//			relations.add(relInfo);
//		}
		return relations;
	}
	
	@Override 
	public StatusInfo deleteRefDocRelation(String id) throws Exception {
		//TODO: Fix with LuService RC1.4 changes		
//		return service.deleteLuDocRelation(id);
		return null;
	}

	public LuService getService() {
		return service;
	}

	public void setService(LuService service) {
		this.service = service;
	}


	
	

}
