package org.kuali.student.enrollment.class1.lui.dao;

import java.util.List;

import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiLuiRelationEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

public class LuiLuiRelationDao extends GenericEntityDao<LuiLuiRelationEntity>{
    @SuppressWarnings({"unchecked"})
    public List<LuiLuiRelationEntity> getLuiLuiRelationsByLui(String luiId) {
    	return (List<LuiLuiRelationEntity>) em.createQuery("from LuiLuiRelationEntity rel where rel.lui.id=:luiId OR rel.relatedLui.id=:luiId").setParameter("luiId", luiId).getResultList();
    }
    
    @SuppressWarnings({"unchecked"})
	public List<String> getLuiIdsByRelation(String relatedLuiId, String luLuRelationTypeKey){
		return (List<String>) em.createQuery("select rel.lui.id from LuiLuiRelationEntity rel where rel.relatedLui.id=:relatedLuiId and rel.luiLuiRelationType=:luLuRelationTypeKey")
        .setParameter("relatedLuiId", relatedLuiId)
		.setParameter("luLuRelationTypeKey", luLuRelationTypeKey)
		.getResultList();
	}
	
    @SuppressWarnings({"unchecked"})
	public List<LuiEntity> getLuisByRelation(String relatedLuiId, String luLuRelationTypeKey){
        return (List<LuiEntity>) em.createQuery("select rel.lui from LuiLuiRelationEntity rel where rel.relatedLui.id=:relatedLuiId and rel.luiLuiRelationType=:luLuRelationTypeKey")
		.setParameter("relatedLuiId", relatedLuiId)
		.setParameter("luLuRelationTypeKey", luLuRelationTypeKey)
		.getResultList();
	}

    @SuppressWarnings({"unchecked"})
	public List<String> getRelatedLuisByLuiId(String luiId, String luLuRelationTypeKey){
		return (List<String>) em.createQuery("select rel.relatedLui.id from LuiLuiRelationEntity rel where rel.lui.id=:luiId and rel.luiLuiRelationType=:luLuRelationTypeKey")
		.setParameter("luiId", luiId)
		.setParameter("luLuRelationTypeKey", luLuRelationTypeKey)
		.getResultList();
	}


    public List<LuiLuiRelationEntity> getLuiLuiRelationsByRelatedLuiAndLuiId(String luiId, String relatedLuiId){
        return (List<LuiLuiRelationEntity>) em.createQuery("from LuiLuiRelationEntity rel where rel.lui.id=:luiId AND rel.relatedLui.id=:relatedLuiId").setParameter("luiId", luiId)
                .setParameter("relatedLuiId",relatedLuiId).getResultList();

    }

    public  List<LuiEntity> getRelatedLuisByLuiIdAndRelationType(String luiId, String luiLuiRelationTypeKey) {
        return (List<LuiEntity>) em.createQuery("Select  rel.relatedLui from LuiLuiRelationEntity rel where rel.lui.id=:luiId AND rel.luiLuiRelationType=:luiLuiRelationTypeKey").setParameter("luiId", luiId)
                .setParameter("luiLuiRelationTypeKey",luiLuiRelationTypeKey).getResultList();

    }
}
