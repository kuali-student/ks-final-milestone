package org.kuali.student.r2.core.class1.atp.dao;

import java.util.List;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.atp.model.AtpTypeEntity;

public class AtpTypeDao extends GenericEntityDao<AtpTypeEntity>{

    @SuppressWarnings("unchecked")
    public List<AtpTypeEntity> findAll(String refObjectURI) {
        return em.createQuery("from AtpTypeEntity ate where ate.refObjectURI =:refObjectURI").setParameter("refObjectURI", refObjectURI).getResultList();
    }

}
