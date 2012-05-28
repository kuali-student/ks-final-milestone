package org.kuali.student.enrollment.class1.lpr.dao;

import org.kuali.student.enrollment.class1.roster.model.LprRosterEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

import java.util.List;

public class LprRosterDao extends GenericEntityDao<LprRosterEntity> {

    public List<LprRosterEntity> getLprRostersByLuiAndRosterType(String luiId, String lprRosterTypeKey){
        return em.createQuery("from LprRosterEntity m where m.lprRosterType.id=:lprRosterTypeKey" /*and  :luiId MEMBER OF m.associatedLuis */).setParameter("lprRosterTypeKey", lprRosterTypeKey)/*.setParameter("luiId",luiId)*/.getResultList();
    }

     public List<LprRosterEntity> getLprRostersByLui(String luiId){
        return em.createQuery("from LprRosterEntity m" /*where :luiId MEMBER OF m.associatedLuis */)/*.setParameter("luiId",luiId)*/.getResultList();
    }

}
