package org.kuali.student.process.poc.evaluator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.student.process.poc.context.HoldCheckContext;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.kuali.student.r2.core.process.dto.InstructionInfo;

public class HoldCheckEvaluator implements CheckEvaluator<HoldCheckContext> {

    private HoldService holdService;
    private AtpService atpService;

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public HoldService getHoldService() {
        return holdService;
    }

    public void setHoldService(HoldService holdService) {
        this.holdService = holdService;
    }

    @Override
    public List<ValidationResultInfo> evaluate(HoldCheckContext checkContext, ContextInfo context) throws OperationFailedException {
        List<HoldInfo> holds;
        try {
            holds = holdService.getActiveHoldsByIssueAndPerson(checkContext.getCheck().getIssueKey(), checkContext.getStudentId(), context);
        } catch (OperationFailedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        AtpInfo atp = null;
        try {
            atp = atpService.getAtp(checkContext.getAtpKey(), context);
        } catch (OperationFailedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        // TODO: ask business what date we should use to check for holds? Start date or some specific milestone?
        Date holdAsOfDate = atp.getStartDate();
        List<ValidationResultInfo> vrs = new ArrayList<ValidationResultInfo>();
        ValidationResultInfo vr = null;
        for (HoldInfo hold : holds) {
            if (holdAsOfDate.before(hold.getEffectiveDate())) {
                continue;
            }
            if (hold.getReleasedDate() != null) {
                if (holdAsOfDate.after(hold.getReleasedDate())) {
                    continue;
                }
            }
            // TODO: Check for an Exemption to this hold and the !hold.getIsOverridable()
            vr = initError (checkContext.getInstruction());
            vrs.add(vr);
        }
        if (vrs.isEmpty()) {
            vr = initInfo(checkContext.getInstruction());
            vrs.add(vr);
        }

        return vrs;
    }

    private static ValidationResultInfo initError(InstructionInfo instruction) {
       // TODO: set the error level based on hold.isWarning () but not sure it should even be a property of the hold
        return MilestoneCheckEvaluator.initError(instruction);
    }

    private static ValidationResultInfo initInfo(InstructionInfo instruction) {
        return MilestoneCheckEvaluator.initInfo(instruction);
    }
}
