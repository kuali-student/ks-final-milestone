package org.kuali.student.enrollment.class1.lui.dao;

import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiLuiRelationEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

import java.util.List;

public class LuiLuiRelationDao extends GenericEntityDao<LuiLuiRelationEntity> {
    @SuppressWarnings({"unchecked"})
    public List<LuiLuiRelationEntity> getLuiLuiRelationsByLui(String luiId) {
    	return (List<LuiLuiRelationEntity>) em.createNamedQuery("LuiLuiRelationENR.getLuiLuiRelationsByLui")
                .setParameter("luiId", luiId)
                .getResultList();
    }
    
    @SuppressWarnings({"unchecked"})
	public List<String> getLuiIdsByRelation(String relatedLuiId, String luLuRelationTypeKey){
		return (List<String>) em.createNamedQuery("LuiLuiRelationENR.getLuiIdsByRelation")
        .setParameter("relatedLuiId", relatedLuiId)
		.setParameter("luLuRelationTypeKey", luLuRelationTypeKey)
		.getResultList();
	}
	
    @SuppressWarnings({"unchecked"})
	public List<LuiEntity> getLuisByRelation(String relatedLuiId, String luLuRelationTypeKey){
        return (List<LuiEntity>) em.createNamedQuery("LuiLuiRelationENR.getLuisByRelation")
		.setParameter("relatedLuiId", relatedLuiId)
		.setParameter("luLuRelationTypeKey", luLuRelationTypeKey)
		.getResultList();
	}

    @SuppressWarnings({"unchecked"})
	public List<String> getRelatedLuisByLuiId(String luiId, String luLuRelationTypeKey){
		return (List<String>) em.createNamedQuery("LuiLuiRelationENR.getRelatedLuisByLuiId")
		.setParameter("luiId", luiId)
		.setParameter("luLuRelationTypeKey", luLuRelationTypeKey)
		.getResultList();
	}


    public List<LuiLuiRelationEntity> getLuiLuiRelationsByRelatedLuiAndLuiId(String luiId, String relatedLuiId){
        return (List<LuiLuiRelationEntity>) em.createNamedQuery("LuiLuiRelationENR.getLuiLuiRelationsByRelatedLuiAndLuiId")
                .setParameter("luiId", luiId)
                .setParameter("relatedLuiId",relatedLuiId)
                .getResultList();

    }

    public  List<LuiEntity> getRelatedLuisByLuiIdAndRelationType(String luiId, String luiLuiRelationTypeKey) {
        return (List<LuiEntity>) em.createNamedQuery("LuiLuiRelationENR.getRelatedLuisByLuiIdAndRelationType")
                .setParameter("luiId", luiId)
                .setParameter("luiLuiRelationTypeKey",luiLuiRelationTypeKey)
                .getResultList();

    }
}
