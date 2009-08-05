package org.kuali.student.lum.lu.dao;

import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluCluRelation;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.LuDocumentRelation;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.Lui;
import org.kuali.student.lum.lu.entity.LuiLuiRelation;
import org.kuali.student.lum.lu.entity.ReqComponent;

public interface LuDao extends CrudDao, SearchableDao  {
	public List<Clu> getClusByIdList(List<String> cluIdList);
	public List<Clu> getClusByLuType(String luTypeKey, String luState);
	public List<Lui> getLuisByIdList(List<String> luiIds);
	public List<Lui> getLuisByRelationType(String luiId, String luLuRelationTypeId);
	public List<String> getLuiIdsByCluId(String cluId);
	public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey);
    public List<LuStatement> getLuStatementsForLuStatementType(String luStatementTypeKey);
    public List<ReqComponent> getReqComponentsByType(String reqComponentTypeKey);
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
	public List<Clu> getRelatedClusByCluId(String cluId,
			String luLuRelationTypeId);
	public List<Lui> getRelatedLuisByLuiId(String luiId, String id);
	public List<String> getRelatedLuiIdsByLuiId(String luiId, String id);
    public List<LuStatement> getLuStatementsForClu(String cluId);
	public List<String> getLuiIdsByRelationType(String relatedLuiId, String luLuRelationTypeId);
}
