package org.kuali.student.enrollment.class1.lpr.dao;

import org.kuali.student.enrollment.class1.lpr.model.LprRosterEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

public class LprRosterDao extends GenericEntityDao<LprRosterEntity> {

    public List<LprRosterEntity> getLprRostersByLuiAndRosterType(String luiId, String lprRosterTypeKey){
        List<LprRosterInfo> info = null;
        return em.createQuery("from LprRosterEntity m where m.lprRosterType.id=:lprRosterTypeKey and  :luiId MEMBER OF m.associatedLuis").setParameter("lprRosterTypeKey", lprRosterTypeKey).setParameter("luiId",luiId).getResultList();
    }

     public List<LprRosterEntity> getLprRostersByLui(String luiId){
        List<LprRosterInfo> info = null;
        return em.createQuery("from LprRosterEntity m where :luiId MEMBER OF m.associatedLuis").setParameter("luiId",luiId).getResultList();
    }

}
