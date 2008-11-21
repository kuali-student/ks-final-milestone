package org.kuali.student.lum.atp.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractCrudDaoImpl;
import org.kuali.student.lum.atp.dao.AtpDao;
import org.kuali.student.lum.atp.entity.Atp;
import org.kuali.student.lum.atp.entity.DateRange;
import org.kuali.student.lum.atp.entity.DateRangeType;
import org.kuali.student.lum.atp.entity.Milestone;
import org.kuali.student.lum.atp.entity.MilestoneType;

public class AtpDaoImpl extends AbstractCrudDaoImpl implements AtpDao {
	@PersistenceContext(unitName = "Atp")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Atp> findAtpsByAtpType(String atpTypeKey) {
		
		Query q = em.createQuery("SELECT atp FROM Atp atp WHERE atp.type.key = :atpTypeKey");
		q.setParameter("atpTypeKey", atpTypeKey);
		
		return q.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Atp> findAtpsByDate(Date searchDate) {
		
		Query q = em.createQuery("SELECT atp " +
				                 "  FROM Atp atp " +
				                 " WHERE atp.effectiveDate <= :searchDate " +
				                 "   AND atp.expirationDate > :searchDate");
		q.setParameter("searchDate", searchDate);
		
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Atp> findAtpsByDates(Date startDate, Date endDate) {
		Query q = em.createQuery("SELECT atp " +
				                 "  FROM Atp atp " +
				                 " WHERE atp.effectiveDate >= :startDate " +
 				                 "   AND atp.expirationDate <= :endDate");
		q.setParameter("startDate", startDate);
		q.setParameter("endDate", endDate);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DateRange> findDateRangesByAtp(String atpKey) {
		
		Query q = em.createQuery("SELECT dateRange FROM DateRange dateRange WHERE dateRange.atp.key = :atpKey");
		q.setParameter("atpKey", atpKey);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DateRange> findDateRangesByDate(Date searchDate) {
		
		Query q = em.createQuery("SELECT dateRange " +
				                 "  FROM DateRange dateRange " +
				                 " WHERE dateRange.startDate <= :searchDate " +
				                 "   AND dateRange.endDate >= :searchDate");
		
		q.setParameter("searchDate", searchDate);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MilestoneType> findMilestoneTypesForAtpType(String atpTypeKey) {
		
		Query q = em.createQuery("SELECT milestone.type FROM Milestone milestone WHERE milestone.atp.type.key = :atpTypeKey");
		q.setParameter("atpTypeKey", atpTypeKey);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DateRangeType> findDateRangeTypesForAtpType(String atpTypeKey) {
		
		Query q = em.createQuery("SELECT dateRange.type FROM DateRange dateRange WHERE dateRange.atp.type.key = :atpTypeKey");
		q.setParameter("atpTypeKey", atpTypeKey);
		
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Milestone> findMilestonesByAtp(String atpKey) {
		
		Query q = em.createQuery("SELECT milestone FROM Milestone milestone WHERE milestone.atp.key = :atpKey");
		q.setParameter("atpKey", atpKey);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Milestone> findMilestonesByDates(Date startDate, Date endDate) {
		Query q = em.createQuery("SELECT milestone " +
				                 "  FROM Milestone milestone " +
				                 " WHERE milestone.milestoneDate >= :startDate " +
				                 "   AND milestone.milestoneDate <= :endDate");

		q.setParameter("startDate", startDate);
		q.setParameter("endDate", endDate);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Milestone> findMilestonesByDatesAndType(
			String milestoneTypeKey, Date startDate, Date endDate) {
		Query q = em.createQuery("SELECT milestone " +
				                 "  FROM Milestone milestone " +
				                 " WHERE milestone.type.key = :milestoneTypeKey " +
				                 "   AND milestone.milestoneDate >= :startDate " +
				                 "   AND milestone.milestoneDate <= :endDate");
		
		q.setParameter("milestoneTypeKey", milestoneTypeKey);
		q.setParameter("startDate", startDate);
		q.setParameter("endDate", endDate);
		
		return q.getResultList();
	}

}
