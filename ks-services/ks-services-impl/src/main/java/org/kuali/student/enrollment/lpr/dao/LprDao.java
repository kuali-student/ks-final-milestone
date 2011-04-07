package org.kuali.student.enrollment.lpr.dao;

import org.kuali.student.enrollment.lpr.model.LuiPersonRelationEntity;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Igor
 */
public class LprDao extends GenericEntityDao<LuiPersonRelationEntity> {

    @SuppressWarnings({"unchecked"})
    public List<LuiPersonRelationEntity> getByLuiId(String luiId) {
        return (List<LuiPersonRelationEntity>) em.createQuery("from LuiPersonRelation pr where pr.luiId=:luiId").setParameter("luiId", luiId).getResultList();
    }
}
