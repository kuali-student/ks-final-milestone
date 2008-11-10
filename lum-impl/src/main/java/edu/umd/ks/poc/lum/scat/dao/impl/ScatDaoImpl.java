package edu.umd.ks.poc.lum.scat.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.umd.ks.poc.lum.scat.dao.ScatDao;
import edu.umd.ks.poc.lum.scat.entity.Scat;
import edu.umd.ks.poc.lum.scat.entity.ScatTable;

public class ScatDaoImpl implements ScatDao {
	@PersistenceContext(unitName="Scat")
	private EntityManager em;

	@Override
	public List<ScatTable> searchScats(String searchString) {
		Query q = em.createQuery("SELECT st " +
								 "  FROM ScatTable st " +
								 " WHERE UPPER(st.tableDescription) LIKE :searchString");
		q.setParameter("searchString", "%"+searchString.toUpperCase()+"%");
		return q.getResultList();

	}

	@Override
	public ScatTable createScatTable(ScatTable scatTable) {
		em.persist(scatTable);
		return scatTable;
	}

	@Override
	public void deleteScatTable(String scatTableId) {
		em.remove(fetchScatTable(scatTableId));
		
	}

	@Override
	public ScatTable fetchScatTable(String scatTableId) {
		return em.find(ScatTable.class, scatTableId);
	}

	@Override
	public ScatTable updateScatTable(ScatTable scatTable) {
		return em.merge(scatTable);
	}

	@Override
	public void deleteScat(Scat scat) {
		em.remove(scat);
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

	@Override
	public Scat createScat(Scat scat) {
		em.persist(scat);
		return scat;
	}

	@Override
	public Scat fetchScat(String scatId) {
		return em.find(Scat.class, scatId);
	}



}
