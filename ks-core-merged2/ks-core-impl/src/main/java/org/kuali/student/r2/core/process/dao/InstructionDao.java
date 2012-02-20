package org.kuali.student.r2.core.process.dao;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.process.model.InstructionEntity;

import java.util.List;

public class InstructionDao extends GenericEntityDao<InstructionEntity> {

    public List<InstructionEntity> getByInstructionTypeId(String instructionTypeId) {
        return em.createQuery("from InstructionEntity a where a.instructionType.id=:instructionTypeId").setParameter("instructionTypeId", instructionTypeId).getResultList();
    }

    public List<InstructionEntity> getByProcess(String processId) {
        return em.createQuery("from InstructionEntity a where a.process.id=:processId").setParameter("processId", processId).getResultList();
    }

    public List<InstructionEntity> getByCheck(String checkId) {
        return em.createQuery("from InstructionEntity a where a.check.id=:checkId").setParameter("checkId", checkId).getResultList();
    }

    public List<InstructionEntity> getByProcessAndCheck(String processId, String checkId) {
        return em.createQuery("from InstructionEntity a where a.process.id=:processId and a.check.id=:checkId").setParameter("processId", processId).setParameter("checkId", checkId).getResultList();
    }
}
