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
}
