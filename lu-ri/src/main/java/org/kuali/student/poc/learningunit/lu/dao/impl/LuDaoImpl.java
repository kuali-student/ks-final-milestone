package org.kuali.student.poc.learningunit.lu.dao.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.poc.learningunit.lu.dao.LuDao;
import org.kuali.student.poc.learningunit.lu.entity.Atp;
import org.kuali.student.poc.learningunit.lu.entity.Clu;
import org.kuali.student.poc.learningunit.lu.entity.CluRelation;
import org.kuali.student.poc.learningunit.lu.entity.CluSet;
import org.kuali.student.poc.learningunit.lu.entity.LuRelationType;
import org.kuali.student.poc.learningunit.lu.entity.LuType;
import org.kuali.student.poc.learningunit.lu.entity.Lui;
import org.kuali.student.poc.learningunit.lu.entity.LuiRelation;

public class LuDaoImpl implements LuDao {
	@PersistenceContext(unitName="Lu")
	private EntityManager em;

	@Override
	public Atp createAtp(Atp atp) {
		em.persist(atp);
		return atp;
	}

	@Override
	public Clu createClu(Clu clu) {
		em.persist(clu);
		return clu;
	}

	@Override
	public CluRelation createCluRelation(CluRelation cluRelation) {
		em.persist(cluRelation);
		return cluRelation;
	}

	@Override
	public CluSet createCluSet(CluSet cluSet) {
		em.persist(cluSet);
		return cluSet;
	}

	@Override
	public Lui createLui(Lui lui) {
		em.persist(lui);
		return lui;
	}

	@Override
	public LuiRelation createLuiRelation(LuiRelation luiRelation) {
		em.persist(luiRelation);
		return luiRelation;
	}

	@Override
	public LuRelationType createLuRelationType(LuRelationType luRelationType) {
		em.persist(luRelationType);
		return luRelationType;
	}

	@Override
	public LuType createLuType(LuType luType) {
		em.persist(luType);
		return luType;
	}

	@Override
	public boolean deleteClu(Clu clu) {
		em.remove(clu);
		return true;
	}

	@Override
	public boolean deleteCluRelation(CluRelation cluRelation) {
		em.remove(cluRelation);
		return true;
	}

	@Override
	public boolean deleteCluSet(CluSet cluSet) {
		em.remove(cluSet);
		return true;
	}

	@Override
	public boolean deleteLui(Lui lui) {
		em.remove(lui);
		return true;
	}

	@Override
	public boolean deleteLuiRelation(LuiRelation luiRelation) {
		em.remove(luiRelation);
		return true;
	}

	@Override
	public Clu updateClu(Clu clu) {
		return em.merge(clu);
	}

	@Override
	public CluRelation updateCluRelation(CluRelation cluRelation) {
		return em.merge(cluRelation);
	}

	@Override
	public CluSet updateCluSet(CluSet cluSet) {
		return em.merge(cluSet);
	}

	@Override
	public Lui updateLui(Lui lui) {
		return em.merge(lui);
	}

	@Override
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

	@Override
	public List<LuType> findLuTypes() {
		Query q = em.createQuery("SELECT lut FROM LuType lut");
		return q.getResultList();
	}

	@Override
	public List<LuRelationType> findLuRelationTypes() {
		Query q = em.createQuery("SELECT lurt FROM LuRelationType lurt");
		return q.getResultList();
	}

	@Override
	public LuType fetchLuType(String luTypeId) {
		return em.find(LuType.class, luTypeId);
	}

	@Override
	public Set<LuRelationType> findAllowedLuLuRelationTypesForLuType(
			String luTypeId, String relatedLuTypeId) {
		Set<LuRelationType> set1 = em.find(LuType.class, luTypeId)
				.getAllowedLuRelationTypes();
		Set<LuRelationType> set2 = em.find(LuType.class, relatedLuTypeId)
				.getAllowedLuRelationTypes();
		set1.retainAll(set2);
		return set1;
	}

	@Override
	public Atp fetchAtp(String atpId) {
		return em.find(Atp.class, atpId);
	}

	@Override
	public Clu fetchClu(String cluId) {
		return em.find(Clu.class, cluId);
	}

	@Override
	public List<Lui> findLuisForClu(String cluId, String atpId) {
		Query q = em
				.createQuery("SELECT l FROM Lui l WHERE l.clu.cluId=:cluId AND l.atp.atpId=:atpId");
		q.setParameter("cluId", cluId);
		q.setParameter("atpId", atpId);
		return q.getResultList();
	}

	@Override
	public CluSet fetchCluSet(String cluSetId) {
		return em.find(CluSet.class, cluSetId);
	}

	@Override
	public Lui fetchLui(String luiId) {
		return em.find(Lui.class, luiId);
	}

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
	public List<Clu> findClusForLuType(String luTypeId) {
		Query q = em.createQuery("SELECT c FROM Clu c "
				+ "WHERE c.luType.luTypeId=:luTypeId ");
		q.setParameter("luTypeId", luTypeId);
		return q.getResultList();
	}

}
