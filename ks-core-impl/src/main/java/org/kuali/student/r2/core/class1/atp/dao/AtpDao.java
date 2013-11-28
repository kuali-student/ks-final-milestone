package org.kuali.student.r2.core.class1.atp.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.atp.model.AtpEntity;

import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.DATE;

public class AtpDao extends GenericEntityDao<AtpEntity> {

    @SuppressWarnings("unchecked")
    public List<AtpEntity> getByAtpTypeId(String atpType) {
        return em.createQuery("from AtpEntity a where a.atpType=:atpType").setParameter("atpType", atpType)
                .getResultList();
    }

    public List<AtpEntity> getByCode(String code) {
        return em.createNamedQuery("Atp.findByCode").setParameter("code", code).getResultList();
    }

    public List<AtpEntity> getByDate(Date searchDate) {
        return em.createQuery("from AtpEntity a where :searchDate between a.startDate and a.endDate")
                .setParameter("searchDate", searchDate, DATE).getResultList();
    }

    public List<AtpEntity> getByDates(Date startDate, Date endDate) {
        return em.createQuery("from AtpEntity a where a.startDate >= :startDate and a.endDate <= :endDate")
                .setParameter("startDate", startDate, DATE).setParameter("endDate", endDate).getResultList();
    }

    public List<AtpEntity> getByStartDateRange(Date searchDateRangeStart, Date searchDateRangeEnd) {
        return em
                .createQuery(
                        "from AtpEntity a where (a.startDate between :searchDateRangeStart and :searchDateRangeEnd)")
                .setParameter("searchDateRangeStart", searchDateRangeStart, DATE)
                .setParameter("searchDateRangeEnd", searchDateRangeEnd, DATE)
                .getResultList();
    }

    public List<AtpEntity> getByStartDateRangeAndType(Date searchDateRangeStart, Date searchDateRangeEnd,
            String searchType) {
        return em
                .createQuery(
                        "from AtpEntity a where (a.startDate between :searchDateRangeStart and :searchDateRangeEnd)  and (a.atpType = :searchType)")
                .setParameter("searchDateRangeStart", searchDateRangeStart, DATE)
                .setParameter("searchDateRangeEnd", searchDateRangeEnd, DATE)
                .setParameter("searchType", searchType)
                .getResultList();
    }

    public List<AtpEntity> getByDateAndType(Date searchDate, String searchTypeKey) {
        return em.createQuery("from AtpEntity a where :searchDate between a.startDate and a.endDate and atpType = :searchTypeKey")
                .setParameter("searchDate", searchDate, DATE)
                .setParameter("searchTypeKey", searchTypeKey)
                .getResultList();
    }
}
