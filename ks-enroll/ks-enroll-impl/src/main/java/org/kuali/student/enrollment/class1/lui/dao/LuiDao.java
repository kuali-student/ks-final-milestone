package org.kuali.student.enrollment.class1.lui.dao;

import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

import java.util.ArrayList;
import java.util.List;

public class LuiDao extends GenericEntityDao<LuiEntity> {
	public List<LuiEntity> getLuisByType(String typeId) {
    	return (List<LuiEntity>) em.createNamedQuery("Lui.getLuisByType").setParameter("typeId", typeId).getResultList();
    }

    public List<LuiEntity> getLuisByClu(String cluId) {
        return (List<LuiEntity>) em.createNamedQuery("Lui.getLuisByClu").setParameter("cluId", cluId).getResultList();

    }

    public  List<LuiEntity> getLuisByAtpAndType(String atpId, String typeKey)   {
        return (List<LuiEntity>) em.createNamedQuery("Lui.getLuisByAtpAndType").setParameter("typeKey", typeKey).setParameter("atpId", atpId).getResultList();

    }

    public  List<LuiEntity> getLuisByAtpAndClu(String atpId, String cluId)   {
        return (List<LuiEntity>) em.createNamedQuery("Lui.getLuisByAtpAndClu").setParameter("cluId", cluId).setParameter("atpId", atpId).getResultList();

    }

    public  List<String> getLuisIdsByAtpAndType(String atpId, String typeKey)   {
        return  (List<String>) em.createNamedQuery("Lui.getLuiIdsByAtpAndType").setParameter("typeKey", typeKey).setParameter("atpId", atpId).getResultList();
    }

    public  List<String> getScheduleIdsByLuiId(String aoId)   {
        List<LuiEntity> luis =  (List<LuiEntity>) em.createNamedQuery("Lui.getLuisByLuiId").setParameter("aoId", aoId).getResultList();
        List<String> scheduleIds = new ArrayList<String>();
        if(luis != null && !luis.isEmpty()) {
            LuiEntity lui = luis.get(0);
            scheduleIds.addAll(lui.getScheduleIds());
        }
        return scheduleIds;
    }

}
