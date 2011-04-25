package org.kuali.student.enrollment.classI.lpr.dao;


import java.util.List;

import org.kuali.student.enrollment.classI.lpr.model.LuiPersonRelationEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;



/**
 * @author Igor
 */
public class LprDao extends GenericEntityDao<LuiPersonRelationEntity> {

    @SuppressWarnings({"unchecked"})
    public List<LuiPersonRelationEntity> getByLuiId(String luiId) {
        return (List<LuiPersonRelationEntity>) em.createQuery("from LuiPersonRelationEntity pr where pr.luiId=:luiId").setParameter("luiId", luiId).getResultList();
    }
}
