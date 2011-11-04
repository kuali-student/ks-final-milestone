package org.kuali.student.enrollment.class1.lui.dao;

import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiLuiRelationEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

import java.util.List;

public class LuiDao extends GenericEntityDao<LuiEntity> {
	public List<LuiEntity> getLuisByType(String typeId) {
    	return (List<LuiEntity>) em.createQuery("from LuiEntity lui where lui.luiType.id=:typeId").setParameter("typeId", typeId).getResultList();
    }
}
