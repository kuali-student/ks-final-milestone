package org.kuali.student.enrollment.class1.lpr.dao;

import java.util.List;

import org.kuali.student.enrollment.class1.lpr.model.LprTransactionEntity;
import org.kuali.student.enrollment.class1.lpr.model.LprTransactionItemEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

public class LprTransactionItemDao extends GenericEntityDao<LprTransactionItemEntity> {
    public List<LprTransactionItemEntity> getLprTransactionItemsByLpr(String lprId) {
        return em.createQuery("from LprTransactionItemEntity a  where a.resultingLprId=:lprId").setParameter("lprId", lprId)
                .getResultList();
    }

    
    public List<LprTransactionItemEntity> getLprTransactionItemByPerson(String personId) {
        return em.createQuery("from LprTransactionItemEntity a where  a.personId = :personId")
                .setParameter("personId", personId).getResultList();
    }

    public List<LprTransactionItemEntity> getLprTransactionItemByLprTrans(String lprTransId) {
        return em.createQuery("select a.lprTransItems from LprTransactionEntity a where  a.id = :lprTransId")
                .setParameter("lprTransId", lprTransId).getResultList();
    }

}
