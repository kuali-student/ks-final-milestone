package org.kuali.student.enrollment.class1.lui.dao;

import java.util.List;

import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class LuiDao extends GenericEntityDao<LuiEntity> {
	public List<LuiEntity> getLuisByType(String typeId) {
    	return (List<LuiEntity>) em.createQuery("from LuiEntity lui where lui.luiType.id=:typeId").setParameter("typeId", typeId).getResultList();
    }
}
