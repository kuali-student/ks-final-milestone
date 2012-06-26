package org.kuali.student.r2.core.process.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.process.model.InstructionTypeEntity;

import java.util.List;

public class InstructionTypeDao extends GenericEntityDao<InstructionTypeEntity>{

    @SuppressWarnings("unchecked")
    public List<InstructionTypeEntity> findAll(String refObjectURI) {
        return em.createQuery("from InstructionTypeEntity ins where ins.refObjectURI =:refObjectURI").setParameter("refObjectURI", refObjectURI).getResultList();
    }

}
