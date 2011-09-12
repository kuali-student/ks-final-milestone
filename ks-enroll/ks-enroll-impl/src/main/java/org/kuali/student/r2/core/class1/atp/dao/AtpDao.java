package org.kuali.student.r2.core.class1.atp.dao;

import java.util.Date;
import java.util.List;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.atp.model.AtpEntity;

import javax.persistence.TemporalType;

import static javax.persistence.TemporalType.DATE;

public class AtpDao extends GenericEntityDao<AtpEntity>{

    @SuppressWarnings("unchecked")
    public List<AtpEntity> getByAtpTypeId(String atpTypeId) {
        return em.createQuery("from AtpEntity a where a.atpType.id=:atpTypeId").setParameter("atpTypeId", atpTypeId).getResultList();
    }

    public List<AtpEntity> getByDate(Date searchDate) {
        return em.createQuery("from AtpEntity a where :searchDate between a.startDate and a.endDate").setParameter("searchDate", searchDate, DATE).getResultList();
    }

    public List<AtpEntity> getByDates(Date startDate, Date endDate) {
        return em.createQuery("from AtpEntity a where a.startDate >= :startDate and a.endDate <= :endDate")
                            .setParameter("startDate", startDate, DATE).setParameter("endDate", endDate).getResultList();
    }

    public List<AtpEntity> getByStartDateRange(Date searchDateRangeStart, Date searchDateRangeEnd) {
        return em.createQuery("from AtpEntity a where (a.startDate between :searchDateRangeStart and :searchDateRangeEnd)")
                .setParameter("searchDateRangeStart", searchDateRangeStart, DATE)
                .setParameter("searchDateRangeEnd", searchDateRangeEnd, DATE)
                .getResultList();
    }

    public List<AtpEntity> getByStartDateRangeAndType(Date searchDateRangeStart, Date searchDateRangeEnd, String searchTypeKey) {
        return em.createQuery("from AtpEntity a where (a.startDate between :searchDateRangeStart and :searchDateRangeEnd)  and (a.atpType.id = :searchTypeKey)")
                .setParameter("searchDateRangeStart", searchDateRangeStart, DATE)
                .setParameter("searchDateRangeEnd", searchDateRangeEnd, DATE)
                .setParameter("searchTypeKey", searchTypeKey)
                .getResultList();
    }
}
