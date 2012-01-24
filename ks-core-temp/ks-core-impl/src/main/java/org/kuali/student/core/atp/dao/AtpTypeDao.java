package org.kuali.student.core.atp.dao;

import java.util.List;

import org.kuali.student.core.class1.type.model.AtpTypeEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class AtpTypeDao extends GenericEntityDao<AtpTypeEntity> {

    @SuppressWarnings("unchecked")
    public List<AtpTypeEntity> findAll(String refObjectURI) {
        return em.createQuery("from AtpTypeEntity ate where ate.refObjectURI =:refObjectURI").setParameter("refObjectURI", refObjectURI).getResultList();
    }

}
