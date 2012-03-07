package org.kuali.student.r2.core.process.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.process.model.ProcessTypeEntity;

import java.util.List;

public class ProcessTypeDao extends GenericEntityDao<ProcessTypeEntity>{

    @SuppressWarnings("unchecked")
    public List<ProcessTypeEntity> findAll(String refObjectURI) {
        return em.createQuery("from ProcessTypeEntity process where process.refObjectURI =:refObjectURI").setParameter("refObjectURI", refObjectURI).getResultList();
    }

}
