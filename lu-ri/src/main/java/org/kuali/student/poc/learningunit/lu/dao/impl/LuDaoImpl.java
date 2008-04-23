package org.kuali.student.poc.learningunit.lu.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kuali.student.poc.learningunit.lu.dao.LuDao;
import org.kuali.student.poc.learningunit.lu.entity.Atp;
import org.kuali.student.poc.learningunit.lu.entity.Clu;
import org.kuali.student.poc.learningunit.lu.entity.CluRelation;
import org.kuali.student.poc.learningunit.lu.entity.LuRelationType;
import org.kuali.student.poc.learningunit.lu.entity.LuType;
import org.kuali.student.poc.learningunit.lu.entity.Lui;
import org.kuali.student.poc.learningunit.lu.entity.LuiRelation;

public class LuDaoImpl implements LuDao {
	@PersistenceContext
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
	public Lui createLui(Lui lui) {
		em.persist(lui);
		return lui;
	}

	@Override
	public LuiRelation createLuiRelation(LuiRelation luiRelation) {
		em.persist(luiRelation);
		return luiRelation;
	}

	/**
	 * @return the em
	 */
	public EntityManager getEm() {
		return em;
	}

	/**
	 * @param em the em to set
	 */
	public void setEm(EntityManager em) {
		this.em = em;
	}

}
