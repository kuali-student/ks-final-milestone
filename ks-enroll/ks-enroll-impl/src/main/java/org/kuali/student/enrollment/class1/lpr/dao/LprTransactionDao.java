package org.kuali.student.enrollment.class1.lpr.dao;

import static javax.persistence.TemporalType.DATE;

import java.util.Date;
import java.util.List;

import org.kuali.student.enrollment.class1.lpr.model.LprTransactionEntity;
import org.kuali.student.enrollment.class1.lpr.model.LprTransactionItemEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class LprTransactionDao extends GenericEntityDao<LprTransactionEntity> {
    @SuppressWarnings("unchecked")
    public List<LprTransactionEntity> getByAtpTypeId(String atpTypeId) {
        return em.createQuery("from LprTransactionEntity a where a.atpType.id=:atpTypeId")
                .setParameter("atpTypeId", atpTypeId).getResultList();
    }

    public List<LprTransactionEntity> getByDate(Date searchDate) {
        return em.createQuery("from AtpEntity a where :searchDate between a.startDate and a.endDate")
                .setParameter("searchDate", searchDate, DATE).getResultList();
    }

    public List<LprTransactionEntity> getByDates(Date startDate, Date endDate) {
        return em.createQuery("from AtpEntity a where a.startDate >= :startDate and a.endDate <= :endDate")
                .setParameter("startDate", startDate, DATE).setParameter("endDate", endDate).getResultList();
    }

    public List<LprTransactionItemEntity> getLprTransactionItemByLpr(String lprId) {
        return em.createQuery("from LprTransactionItemEntity a  where a.lprId=:lprId").setParameter("lprId", lprId)
                .getResultList();
    }

    public List<LprTransactionEntity> getByLprTransactionItemId(String lprTransactionItemId) {
        return em.createQuery("from LprTransactionEntity a  where :lprTransactionItemId  IN a.lprTransactionItems")
                .setParameter("lprId", lprTransactionItemId).getResultList();
    }

    public List<LprTransactionItemEntity> getLprTransactionItemByPerson(String personId) {
        return em.createQuery("from LprTransactionItemEntity a where  a.personId = :personId")
                .setParameter("personId", personId).getResultList();
    }
}
