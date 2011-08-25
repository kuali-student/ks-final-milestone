package org.kuali.student.enrollment.class1.lpr.dao;


import java.util.Collection;
import java.util.List;

import org.kuali.student.enrollment.class1.lpr.model.LuiPersonRelationEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.enrollment.lui.infc.LuiIdentifier;


/**
 * @author Igor
 */
public class LprDao extends GenericEntityDao<LuiPersonRelationEntity> {

    @SuppressWarnings({"unchecked"})
    public List<LuiPersonRelationEntity> getByLuiId(String luiId) {
        return (List<LuiPersonRelationEntity>) em.createQuery("from LuiPersonRelationEntity lpr where lpr.luiId=:luiId").setParameter("luiId", luiId).getResultList();
    }

    public List<String> getLprIdsByLuiAndPerson(String personId, String luiId) {
        return (List<String>) em.createQuery("select lpr.id from LuiPersonRelationEntity lpr where lpr.personId=:personId and lpr.luiId=:luiId").setParameter("personId", personId).setParameter("luiId", luiId).getResultList();
    }
}
