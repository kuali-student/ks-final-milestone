package org.kuali.student.enrollment.dao;


import java.util.List;

import org.kuali.student.enrollment.lpr.model.LuiPersonRelationEntity;



/**
 * @author Igor
 */
public class LprDao extends GenericEntityDao<LuiPersonRelationEntity> {

    @SuppressWarnings({"unchecked"})
    public List<LuiPersonRelationEntity> getByLuiId(String luiId) {
        return (List<LuiPersonRelationEntity>) em.createQuery("from LuiPersonRelationEntity pr where pr.luiId=:luiId").setParameter("luiId", luiId).getResultList();
    }
}
