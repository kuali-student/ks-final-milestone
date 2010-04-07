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

import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RefDocRelationInfoMock;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.entity.Meta;
import org.kuali.student.core.mock.service.DocumentRelationService;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.dto.LuDocRelationInfo;
import org.kuali.student.lum.lu.entity.LuDocumentRelation;
import org.kuali.student.lum.lu.entity.LuRichText;
import org.kuali.student.lum.lu.service.LuService;

@WebService(endpointInterface = "org.kuali.student.core.mock.service.DocumentRelationService", serviceName = "DocumentRelationService", portName = "DocumentRelationService", targetNamespace = "http://student.kuali.org/wsdl/documentRelation")
public class DocumentRelationServiceImpl implements DocumentRelationService{

	LuService service;
	private LuDao luDao;
	
	
	public DocumentRelationServiceImpl() {

	}
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

	    List<RefDocRelationInfoMock> relationInfos = new ArrayList<RefDocRelationInfoMock>();
		List<LuDocumentRelation> documentRelations = luDao.getLuDocRelationsByClu(id);
		
		//TODO: Fix with LuService RC1.4 changes		
		for(LuDocumentRelation relation : documentRelations){
			RefDocRelationInfoMock relationInfo = new RefDocRelationInfoMock();
			relationInfo.setRefId(relation.getClu().getId());
			relationInfo.setDocumentId(relation.getDocumentId());
			relationInfo.setId(relation.getId());
			MetaInfo metaInfo = new MetaInfo();
			Meta meta = relation.getMeta();
			metaInfo.setCreateId(meta.getCreateId());
			metaInfo.setCreateTime(meta.getCreateTime());
			metaInfo.setUpdateId(meta.getUpdateId());
			metaInfo.setUpdateTime(meta.getUpdateTime());
			//FIXME: version indicator
			metaInfo.setVersionInd("1");
			relationInfo.setMetaInfo(metaInfo);
			relationInfo.setTitle(relation.getTitle());
			RichTextInfo richTextInfo = null;
			LuRichText richText = relation.getDescr();
			if(richText != null) {
			    richTextInfo = new RichTextInfo();
			    richTextInfo.setFormatted(richText.getFormatted());
			    richTextInfo.setPlain(richText.getPlain());
			}
			
			relationInfo.setDesc(richTextInfo);
			relationInfos.add(relationInfo);
		}
		return relationInfos;
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

    public void setLuDao(LuDao luDao) {
        this.luDao = luDao;
    }


	
	

}
