package org.kuali.student.enrollment.class1.lpr.dao;

import org.kuali.student.enrollment.class1.roster.model.LprRosterEntryEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

import java.util.List;

public class LprRosterEntryDao  extends GenericEntityDao<LprRosterEntryEntity> {

     public List<LprRosterEntryEntity> getLprRosterEntriesForLprRoster(String lprRosterId){
        return em.createQuery("from LprRosterEntryEntity m where m.lprRosterId=:lprRosterId").setParameter("lprRosterId", lprRosterId).getResultList();
     }
}
