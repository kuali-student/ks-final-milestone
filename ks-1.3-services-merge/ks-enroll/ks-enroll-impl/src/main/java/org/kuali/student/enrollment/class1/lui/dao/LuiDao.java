package org.kuali.student.enrollment.class1.lui.dao;

import java.util.List;

import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class    LuiDao extends GenericEntityDao<LuiEntity> {
	public List<LuiEntity> getLuisByType(String typeId) {
    	return (List<LuiEntity>) em.createQuery("from LuiEntity lui where lui.luiType =:typeId").setParameter("typeId", typeId).getResultList();
    }

    public List<LuiEntity> getLuisByClu(String cluId) {
        return (List<LuiEntity>) em.createQuery("from LuiEntity lui where lui.cluId=:cluId").setParameter("cluId", cluId).getResultList();

    }

    public  List<LuiEntity>  getLuisByAtpAndType(String atpId, String typeKey)   {
        return (List<LuiEntity>) em.createQuery("from LuiEntity lui where lui.atpId=:atpId and lui.luiType = :typeKey").setParameter("typeKey", typeKey).setParameter("atpId", atpId).getResultList();

    }

    public  List<LuiEntity>  getLuisByAtpAndClu(String atpId, String cluId)   {
        return (List<LuiEntity>) em.createQuery("from LuiEntity lui where lui.atpId=:atpId and lui.cluId = :cluId").setParameter("cluId", cluId).setParameter("atpId", atpId).getResultList();

    }
}
