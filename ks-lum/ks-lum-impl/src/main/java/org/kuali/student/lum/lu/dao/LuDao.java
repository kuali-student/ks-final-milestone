/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.lum.lu.dao;

import java.util.Date;
import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluCluRelation;
import org.kuali.student.lum.lu.entity.CluLoRelation;
import org.kuali.student.lum.lu.entity.CluResult;
import org.kuali.student.lum.lu.entity.CluResultType;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.LuDocumentRelation;
import org.kuali.student.lum.lu.entity.Lui;
import org.kuali.student.lum.lu.entity.LuiLuiRelation;

public interface LuDao extends CrudDao, SearchableDao  {
	public List<Clu> getClusByIdList(List<String> cluIdList);
	public List<Clu> getClusByLuType(String luTypeKey, String luState);
	public List<Lui> getLuisByIdList(List<String> luiIds);
	public List<Lui> getLuisByRelationType(String luiId, String luLuRelationTypeId);
	public List<String> getLuiIdsByCluId(String cluId);
	public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey);
    public List<CluSet> getCluSetInfoByIdList(List<String> cluSetIdList);
    public Boolean isCluInCluSet(String cluId, String cluSetId);
	public List<LuiLuiRelation> getLuiLuiRelations(String luiId);
	public List<CluCluRelation> getCluCluRelationsByClu(String cluId);
	public List<Clu> getClusByRelation(String relatedCluId, String luLuRelationTypeKey);
	public List<LuDocumentRelation> getLuDocRelationsByClu(String cluId);
	public List<LuDocumentRelation> getLuDocRelationsByDocument(
			String documentId);
	public List<LuDocumentRelation> getLuDocRelationsByIdList(
			List<String> luDocRelationIdList);
	public List<LuDocumentRelation> getLuDocRelationsByType(
			String luDocRelationTypeKey);
    public List<String> getCluIdsByLoId(String loId);
	public List<String> getRelatedCluIdsByCluId(String cluId,
			String luLuRelationTypeId);
	public List<String> getCluIdsByRelatedCluId(String cluId,
			String luLuRelationTypeId);
	public List<Clu> getRelatedClusByCluId(String cluId,
			String luLuRelationTypeId);
	public List<Lui> getRelatedLuisByLuiId(String luiId, String id);
	public List<String> getRelatedLuiIdsByLuiId(String luiId, String id);
	public List<String> getLuiIdsByRelationType(String relatedLuiId, String luLuRelationTypeId);
	public List<CluLoRelation> getCluLoRelationsByClu(String cluId);    
	public List<CluLoRelation> getCluLoRelationsByCludIdAndLoId(String cluId,
			String loId);
	public List<CluLoRelation> getCluLoRelationsByLo(String loId);
	public List<String> getAllowedCluLoRelationTypesForLuType(String luTypeId);
	public List<CluResultType> getAllowedCluResultTypesForLuType(String luTypeId);
	public List<String> getCluIdsByResultUsageType(String resultUsageTypeKey);
	public List<String> getCluIdsByResultComponentId(String resultComponentId);
	public List<String> getAllowedResultUsageTypesForLuType(String luTypeId);
	public List<String> getAllowedResultComponentTypesForResultUsageType(
			String resultUsageType);
	public List<String> getAllowedLuLuRelationTypesForLuType(
			String luTypeId, String relatedLuTypeId);
	public List<String> getAllowedLuLuRelationTypesByLuiId(String luiId,
			String relatedLuiId);
	public List<String> getAllowedLuLuRelationTypesByCluId(String cluId,
			String relatedCluId);
	public List<CluResult> getCluResultByClu(String cluId);
	
	public Clu getLatestCluVersion(String cluVersionIndId);
	public Clu getCurrentCluVersion(String cluVersionIndId);
	public VersionDisplayInfo getCurrentCluVersionInfo(String cluVersionIndId, String objectTypeURI);
	public List<VersionDisplayInfo> getVersionsInDateRange(String refObjectId,
			String refObjectTypeURI, Date from, Date to);
	public List<VersionDisplayInfo> getVersions(String refObjectId,
			String refObjectTypeURI);
	public VersionDisplayInfo getVersionBySequenceNumber(String refObjectId,
			String refObjectTypeURI, Long sequence);
	public VersionDisplayInfo getFirstVersion(String refObjectId,
			String refObjectTypeURI);
	public VersionDisplayInfo getCurrentVersionOnDate(String refObjectId,
			String refObjectTypeURI, Date date);

}
