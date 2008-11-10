package edu.umd.ks.poc.lum.lu.dao.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.umd.ks.poc.lum.lu.dao.LuDao;
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

public class LuDaoImpl implements LuDao {
	@PersistenceContext(unitName="Lu")
	private EntityManager em;


	public Atp createAtp(Atp atp) {
		em.persist(atp);
		return atp;
	}


	public Clu createClu(Clu clu) {
		em.persist(clu);
		return clu;
	}


	public CluRelation createCluRelation(CluRelation cluRelation) {
		em.persist(cluRelation);
		return cluRelation;
	}


	public CluSet createCluSet(CluSet cluSet) {
		em.persist(cluSet);
		return cluSet;
	}


	public Lui createLui(Lui lui) {
		em.persist(lui);
		return lui;
	}


	public LuiRelation createLuiRelation(LuiRelation luiRelation) {
		em.persist(luiRelation);
		return luiRelation;
	}


	public LuRelationType createLuRelationType(LuRelationType luRelationType) {
		em.persist(luRelationType);
		return luRelationType;
	}


	public LuType createLuType(LuType luType) {
		em.persist(luType);
		return luType;
	}


	public boolean deleteClu(Clu clu) {
		em.remove(clu);
		return true;
	}


	public boolean deleteCluRelation(CluRelation cluRelation) {
		em.remove(cluRelation);
		return true;
	}


	public boolean deleteCluSet(CluSet cluSet) {
		em.remove(cluSet);
		return true;
	}


	public boolean deleteLui(Lui lui) {
		em.remove(lui);
		return true;
	}


	public boolean deleteLuiRelation(LuiRelation luiRelation) {
		em.remove(luiRelation);
		return true;
	}


	public Clu updateClu(Clu clu) {
		return em.merge(clu);
	}


	public CluRelation updateCluRelation(CluRelation cluRelation) {
		return em.merge(cluRelation);
	}


	public CluSet updateCluSet(CluSet cluSet) {
		return em.merge(cluSet);
	}


	public Lui updateLui(Lui lui) {
		return em.merge(lui);
	}


	public LuiRelation updateLuiRelation(LuiRelation luiRelation) {
		return em.merge(luiRelation);
	}

	/**
	 * @return the em
	 */
	public EntityManager getEm() {
		return em;
	}

	/**
	 * @param em
	 *            the em to set
	 */
	public void setEm(EntityManager em) {
		this.em = em;
	}


	public List<LuType> findLuTypes() {
		Query q = em.createQuery("SELECT lut FROM LuType lut");
		return q.getResultList();
	}


	public List<LuRelationType> findLuRelationTypes() {
		Query q = em.createQuery("SELECT lurt FROM LuRelationType lurt");
		return q.getResultList();
	}


	public LuType fetchLuType(String luTypeId) {
		return em.find(LuType.class, luTypeId);
	}


	public Set<LuRelationType> findAllowedLuLuRelationTypesForLuType(
			String luTypeId, String relatedLuTypeId) {
		Set<LuRelationType> set1 = em.find(LuType.class, luTypeId)
				.getAllowedLuRelationTypes();
		Set<LuRelationType> set2 = em.find(LuType.class, relatedLuTypeId)
				.getAllowedLuRelationTypes();
		set1.retainAll(set2);
		return set1;
	}


	public Atp fetchAtp(String atpId) {
		return em.find(Atp.class, atpId);
	}


	public Clu fetchClu(String cluId) {
		return em.find(Clu.class, cluId);
	}


	public List<Lui> findLuisForClu(String cluId, String atpId) {
		Query q = em
				.createQuery("SELECT l FROM Lui l WHERE l.clu.cluId=:cluId AND l.atp.atpId=:atpId");
		q.setParameter("cluId", cluId);
		q.setParameter("atpId", atpId);
		return q.getResultList();
	}


	public CluSet fetchCluSet(String cluSetId) {
		return em.find(CluSet.class, cluSetId);
	}


	public Lui fetchLui(String luiId) {
		return em.find(Lui.class, luiId);
	}


	public LuiRelation fetchLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId) {
		Query q = em.createQuery("SELECT lr FROM LuiRelation lr "
				+ "WHERE lr.lui.luiId=:luiId "
				+ "AND lr.relatedLui.luiId=:relatedLuiId "
				+ "AND lr.luRelationType.id=:luRelationTypeId");
		q.setParameter("luiId", luiId);
		q.setParameter("relatedLuiId", relatedLuiId);
		q.setParameter("luRelationTypeId", luRelationTypeId);
		return (LuiRelation) q.getSingleResult();
	}


	public CluRelation fetchCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId) {
		Query q = em.createQuery("SELECT cr FROM CluRelation cr "
				+ "WHERE cr.clu.cluId=:cluId "
				+ "AND cr.relatedClu.cluId=:relatedCluId "
				+ "AND cr.luRelationType.id=:luRelationTypeId");
		q.setParameter("cluId", cluId);
		q.setParameter("relatedCluId", relatedCluId);
		q.setParameter("luRelationTypeId", luRelationTypeId);
		return (CluRelation) q.getSingleResult();
	}


	public Set<LuRelationType> findAllowedLuRelationTypesForClu(String cluId,
			String relatedCluId) {
		String luTypeId = em.find(Clu.class, cluId).getLuType().getLuTypeId();
		String relatedLuTypeId = em.find(Clu.class, relatedCluId).getLuType()
				.getLuTypeId();
		Set<LuRelationType> set1 = em.find(LuType.class, luTypeId)
				.getAllowedLuRelationTypes();
		Set<LuRelationType> set2 = em.find(LuType.class, relatedLuTypeId)
				.getAllowedLuRelationTypes();
		set1.retainAll(set2);
		return set1;
	}


	public Set<LuRelationType> findAllowedLuRelationTypesForLui(String luiId,
			String relatedLuiId) {
		String luTypeId = em.find(Lui.class, luiId).getClu().getLuType()
				.getLuTypeId();
		String relatedLuTypeId = em.find(Lui.class, relatedLuiId).getClu()
				.getLuType().getLuTypeId();
		Set<LuRelationType> set1 = em.find(LuType.class, luTypeId)
				.getAllowedLuRelationTypes();
		Set<LuRelationType> set2 = em.find(LuType.class, relatedLuTypeId)
				.getAllowedLuRelationTypes();
		set1.retainAll(set2);
		return set1;
	}


	public List<Clu> findClusForLuType(String luTypeId) {
		Query q = em.createQuery("SELECT c FROM Clu c "
				+ "WHERE c.luType.luTypeId=:luTypeId ");
		q.setParameter("luTypeId", luTypeId);
		return q.getResultList();
	}


	public List<Lui> searchForLuis(LuiCriteria luiCriteria) {
		String query="SELECT l FROM Lui l WHERE ";
		if(luiCriteria.getDescription()!=null){
			query+="l.clu.description LIKE :description ";
		}
		if(luiCriteria.getDescription()!=null &&luiCriteria.getLuTypeKey()!=null){
			query+="AND ";
		}
		if(luiCriteria.getLuTypeKey()!=null){
			query+="l.clu.luType.luTypeId = :luTypeId ";
			
		}
		Query q = em.createQuery(query);
		if(luiCriteria.getDescription()!=null){
			q.setParameter("description",luiCriteria.getDescription());
		}
		if(luiCriteria.getLuTypeKey()!=null){
			q.setParameter("luTypeId",luiCriteria.getLuTypeKey());			
		}
		return q.getResultList();
	}


	@Override
	public LuAttributeType createLuAttributeType(
			LuAttributeType createLuAttributeType) {
		em.persist(createLuAttributeType);
		return createLuAttributeType;
	}


	@Override
	public LuAttributeType fetchLuAttributeType(String luAttrTypeId) {
		return em.find(LuAttributeType.class, luAttrTypeId);
	}


	@Override
	public List<LuAttributeType> findLuAttributeTypes() {
		Query q = em.createQuery("SELECT luat FROM LuAttributeType luat");
		return q.getResultList();
	}


	@Override
	public List<LuAttributeType> findLuAttributeTypesForLuTypeId(String luTypeId) {
		Query q = em.createQuery("SELECT luat FROM LuAttributeType luat, IN(luat.luTypes) lut " +
				                 " WHERE lut.luTypeId = :luTypeId");
		q.setParameter("luTypeId", luTypeId);
		return q.getResultList();
	}


	@Override
	public boolean deleteLuAttributeType(LuAttributeType fetchLuAttributeType) {
		em.remove(fetchLuAttributeType);
		return true;
	}


	@Override
	public boolean deleteLuType(LuType fetchLuType) {
		em.remove(fetchLuType);
		return true;
	}


	@Override
	public LuAttributeType updateLuAttributeType(LuAttributeType luAttrType) {
		return em.merge(luAttrType);
	}


	@Override
	public LuType updateLuType(LuType luType) {
		return em.merge(luType);
	}


	@Override
	public List<LuType> searchForLuTypesByDescription(
			String descriptionSearchString) {
		Query q = em.createQuery("SELECT lut " +
				                 "  FROM LuType lut " +
								 " WHERE UPPER(lut.description) LIKE :description");
		q.setParameter("description", "%"+descriptionSearchString.toUpperCase()+"%");
		return q.getResultList();
	}

}
