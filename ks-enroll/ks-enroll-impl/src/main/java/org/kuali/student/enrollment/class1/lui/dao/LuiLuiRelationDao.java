package org.kuali.student.enrollment.class1.lui.dao;

import java.util.List;

import org.kuali.student.enrollment.class1.lui.model.LuiLuiRelationEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class LuiLuiRelationDao extends GenericEntityDao<LuiLuiRelationEntity>{
    @SuppressWarnings({"unchecked"})
    public List<LuiLuiRelationEntity> getLuiLuiRelationsByLui(String luiId) {
    	return (List<LuiLuiRelationEntity>) em.createQuery("from LuiLuiRelationEntity rel where rel.lui.id=:luiId OR rel.relatedLui.id=:luiId").setParameter("luiId", luiId).getResultList();
    }
}
