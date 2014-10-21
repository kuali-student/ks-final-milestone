package org.kuali.student.enrollment.class1.lpr.dao;

import static javax.persistence.TemporalType.DATE;

import java.util.Date;
import java.util.List;

import org.kuali.student.enrollment.class1.lpr.model.LprTransactionEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;

public class LprTransactionDao extends GenericEntityDao<LprTransactionEntity> {

    public List<LprTransactionEntity> getByDate(Date searchDate) {
        return em.createQuery("from AtpEntity a where :searchDate between a.startDate and a.endDate")
                .setParameter("searchDate", searchDate, DATE).getResultList();
    }

    public List<LprTransactionEntity> getByDates(Date startDate, Date endDate) {
        return em.createQuery("from AtpEntity a where a.startDate >= :startDate and a.endDate <= :endDate")
                .setParameter("startDate", startDate, DATE).setParameter("endDate", endDate).getResultList();
    }
    public LprTransactionEntity getByLprTransactionItemId(String lprTransactionItemId) {
        return (LprTransactionEntity) ( em.createQuery("select distinct a from LprTransactionEntity a, IN (a.lprTransactionItems) item where item.id=:lprTransactionItemId")
                .setParameter("lprTransactionItemId", lprTransactionItemId).getSingleResult()) ;
    }

}
