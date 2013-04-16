package org.kuali.student.r2.core.process.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.process.model.InstructionEntity;

import javax.persistence.Query;
import java.util.List;

public class InstructionDao extends GenericEntityDao<InstructionEntity> {

    public List<InstructionEntity> getByInstructionTypeId(String instructionTypeId) {
        Query query = em.createNamedQuery("InstructionEntity.getByInstructionTypeId");
        query.setParameter("instructionTypeId", instructionTypeId);
        return query.getResultList();
    }

    public List<InstructionEntity> getByProcess(String processId) {
        Query query = em.createNamedQuery("InstructionEntity.getByProcess");
        query.setParameter("processId", processId);
        return query.getResultList();
    }

    public List<InstructionEntity> getByCheck(String checkId) {
        Query query = em.createNamedQuery("InstructionEntity.getByCheck");
        query.setParameter("checkId", checkId);
        return query.getResultList();
    }

    public List<InstructionEntity> getByProcessAndCheck(String processId, String checkId) {
        Query query = em.createNamedQuery("InstructionEntity.getByProcessAndCheck");
        query.setParameter("checkId", checkId);
        query.setParameter("processId", processId);
        return query.getResultList();
    }
}
