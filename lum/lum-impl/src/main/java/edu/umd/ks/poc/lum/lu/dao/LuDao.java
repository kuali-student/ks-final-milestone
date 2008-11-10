package edu.umd.ks.poc.lum.lu.dao;

import java.util.List;
import java.util.Set;

import edu.umd.ks.poc.lum.lu.entity.Atp;
import edu.umd.ks.poc.lum.lu.entity.Clu;
import edu.umd.ks.poc.lum.lu.entity.CluRelation;
import edu.umd.ks.poc.lum.lu.entity.CluSet;
import edu.umd.ks.poc.lum.lu.entity.LuAttributeType;
import edu.umd.ks.poc.lum.lu.entity.LuRelationType;
import edu.umd.ks.poc.lum.lu.entity.LuType;
import edu.umd.ks.poc.lum.lu.entity.Lui;
import edu.umd.ks.poc.lum.lu.entity.LuiRelation;
import edu.umd.ks.poc.lum.lu.dto.LuiCriteria;

public interface LuDao {
	public Clu createClu(Clu clu);

	public Atp createAtp(Atp atp);

	public LuType createLuType(LuType luType);

	public Lui createLui(Lui lui);

	public LuiRelation createLuiRelation(LuiRelation luiRelation);

	public LuRelationType createLuRelationType(LuRelationType luRelationType);

	public CluRelation createCluRelation(CluRelation cluRelation);

	public CluSet createCluSet(CluSet cluSet);

	public boolean deleteClu(Clu clu);

	public boolean deleteCluRelation(CluRelation cluRelation);

	public boolean deleteCluSet(CluSet cluSet);

	public boolean deleteLui(Lui lui);

	public boolean deleteLuiRelation(LuiRelation luiRelation);

	public Clu updateClu(Clu clu);

	public CluRelation updateCluRelation(CluRelation cluRelation);

	public CluSet updateCluSet(CluSet cluSet);

	public Lui updateLui(Lui lui);

	public LuiRelation updateLuiRelation(LuiRelation luiRelation);

	public List<LuType> findLuTypes();

	public List<LuRelationType> findLuRelationTypes();

	public LuType fetchLuType(String luTypeId);

	public Set<LuRelationType> findAllowedLuLuRelationTypesForLuType(
			String luTypeId, String relatedLuTypeId);

	public Set<LuRelationType> findAllowedLuRelationTypesForClu(String cluId,
			String relatedCluId);

	public Set<LuRelationType> findAllowedLuRelationTypesForLui(String luiId,
			String relatedLuiId);
	
	public Atp fetchAtp(String atpId);

	public Clu fetchClu(String cluId);

	public CluSet fetchCluSet(String cluSetId);

	public Lui fetchLui(String luiId);

	public List<Lui> findLuisForClu(String cluId, String atpId);

	public LuiRelation fetchLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId);

	public CluRelation fetchCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId);

	public List<Clu> findClusForLuType(String luTypeId);

	public List<Lui> searchForLuis(LuiCriteria luiCriteria);

	public LuAttributeType createLuAttributeType(LuAttributeType createLuAttributeType);

	public LuAttributeType fetchLuAttributeType(String luAttrTypeId);

	public List<LuAttributeType> findLuAttributeTypes();

	public List<LuAttributeType> findLuAttributeTypesForLuTypeId(String luTypeId);

	public boolean deleteLuAttributeType(LuAttributeType fetchLuAttributeType);

	public boolean deleteLuType(LuType fetchLuType);

	public LuType updateLuType(LuType luType);

	public LuAttributeType updateLuAttributeType(LuAttributeType luAttrType);

	public List<LuType> searchForLuTypesByDescription(String descriptionSearchString);

}
