package org.kuali.student.process.poc.evaluator;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.student.process.poc.context.DirectRuleCheckContext;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.process.dto.InstructionInfo;

public class DirectRuleCheckEvaluator implements CheckEvaluator<DirectRuleCheckContext> {

    public static final String AGENDA_IS_ALIVE = "kuali.agenda.is.alive";
    public static final String AGENDA_IS_NOT_SUMMER_TERM = "kuali.agenda.is.not.summer.term";
    private IdentityService identityService;
    private AtpService atpService;

    public IdentityService getIdentityService() {
        return identityService;
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    @Override
    public List<ValidationResultInfo> evaluate(DirectRuleCheckContext checkContext, ContextInfo context) throws OperationFailedException {
        List<ValidationResultInfo> vrs = new ArrayList<ValidationResultInfo>();
        ValidationResultInfo vr = null;
        // is alive check
        if (checkContext.getCheck().getAgendaId().equals(AGENDA_IS_ALIVE)) {
            Entity entity = identityService.getEntity(checkContext.getStudentId());
            if (entity == null) {
                throw new OperationFailedException("studentId not found" + checkContext.getStudentId());
            }
            if (entity.getBioDemographics().getDeceasedDate() == null) {
                vr = initInfo(checkContext.getInstruction());
                vrs.add(vr);
                return vrs;
            }
            vr = initError(checkContext.getInstruction());
            vrs.add(vr);
            return vrs;
        }
        // is not summer term
        if (checkContext.getCheck().getAgendaId().equals(AGENDA_IS_NOT_SUMMER_TERM)) {
            AtpInfo atp = null;
            try {
                atp = atpService.getAtp(checkContext.getAtpKey(), context);
            } catch (OperationFailedException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new OperationFailedException("unexpected", ex);
            }


            if (atp.getTypeKey().equals(AtpServiceConstants.ATP_SUMMER_TYPE_KEY)) {
                vr = initInfo(checkContext.getInstruction());
                vrs.add(vr);
                return vrs;
            }
            vr = initError(checkContext.getInstruction());
            vrs.add(vr);
            return vrs;
        }
        throw new OperationFailedException ("unknown/supported Agenda Id=" + checkContext.getCheck().getAgendaId());
    }

    private static ValidationResultInfo initError(InstructionInfo instruction) {
        // TODO: set the error level based on hold.isWarning () but not sure it should even be a property of the hold
        return MilestoneCheckEvaluator.initError(instruction);
    }

    private static ValidationResultInfo initInfo(InstructionInfo instruction) {
        return MilestoneCheckEvaluator.initInfo(instruction);
    }
}
