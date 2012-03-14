package org.kuali.student.r2.core.class1.process.dao;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.process.model.InstructionTypeEntity;

import java.util.List;

public class InstructionTypeDao extends GenericEntityDao<InstructionTypeEntity>{

    @SuppressWarnings("unchecked")
    public List<InstructionTypeEntity> findAll(String refObjectURI) {
        return em.createQuery("from InstructionTypeEntity ins where ins.refObjectURI =:refObjectURI").setParameter("refObjectURI", refObjectURI).getResultList();
    }

}
