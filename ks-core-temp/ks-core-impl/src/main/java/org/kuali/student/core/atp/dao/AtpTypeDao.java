package org.kuali.student.core.atp.dao;

import java.util.List;

import org.kuali.student.core.generic.dao.GenericEntityDao;
import org.kuali.student.core.type.entity.AtpTypeEntity;

public class AtpTypeDao extends GenericEntityDao<AtpTypeEntity> {

    @SuppressWarnings("unchecked")
    public List<AtpTypeEntity> findAll(String refObjectURI) {
        return em.createQuery("from AtpTypeEntity ate where ate.refObjectURI =:refObjectURI").setParameter("refObjectURI", refObjectURI).getResultList();
    }

}
