package org.kuali.student.process.poc.evaluator;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.kuali.student.process.poc.context.MilestoneCheckContext;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.ExemptionServiceConstants;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.service.ExemptionService;
import org.kuali.student.r2.core.process.dto.InstructionInfo;

public class MilestoneCheckEvaluator implements CheckEvaluator<MilestoneCheckContext> {

    private AtpService atpService;
    private ExemptionService exemptionService;

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public ExemptionService getExemptionService() {
        return exemptionService;
    }

    public void setExemptionService(ExemptionService exemptionService) {
        this.exemptionService = exemptionService;
    }

    public MilestoneCheckEvaluator() {
    }

    @Override
    public List<ValidationResultInfo> evaluate(MilestoneCheckContext checkContext, ContextInfo context) throws OperationFailedException {

        // TODO: look for a DateOverride Exemption for the student that might extend the milestone deadline
        List<MilestoneInfo> milestones;
        try {
            milestones = atpService.getMilestonesByTypeForAtp(checkContext.getAtpKey(), checkContext.getCheck().getMilestoneTypeKey(), context);
        } catch (OperationFailedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        if (milestones.isEmpty()) {
            throw new OperationFailedException("no miilestone found for atp " + checkContext.getAtpKey() + " " + checkContext.getCheck().getMilestoneTypeKey());
        }
        // TODO: consider if it should be OK if it matches ANY if more than one found
        if (milestones.size() > 1) {
            throw new OperationFailedException("More than one miilestone found for atp & type " + checkContext.getAtpKey() + " " + checkContext.getCheck().getMilestoneTypeKey());
        }

        MilestoneInfo milestone = milestones.get(0);
        ValidationResultInfo vr = null;
        switch (checkContext.getComparison()) {
            case MilestoneCheckContext.PERIOD:
                if (checkContext.getDateToTest().before(milestone.getStartDate())) {
                    vr = initError(checkContext.getInstruction());
                    return Arrays.asList(vr);
                }
                if (checkContext.getDateToTest().after(milestone.getEndDate())) {
                    vr = initError(checkContext.getInstruction());
                    return Arrays.asList(vr);
                }
                vr = initInfo(checkContext.getInstruction());
                return Arrays.asList(vr);
            case MilestoneCheckContext.START_DATE:
                if (checkContext.getDateToTest().before(milestone.getStartDate())) {
                    vr = initError(checkContext.getInstruction());
                    return Arrays.asList(vr);
                }
                vr = initInfo(checkContext.getInstruction());
                return Arrays.asList(vr);
            case MilestoneCheckContext.END_DATE:
                Date endDate = milestone.getEndDate();
                // if not a date range then compare to the start date
                if (endDate == null) {
                    endDate = milestone.getStartDate();
                }
                if (checkContext.getDateToTest().after(endDate)) {
                    List<ExemptionInfo> extensions = null;
                    try {
                        extensions = exemptionService.getActiveExemptionsByTypeForPerson(ExemptionServiceConstants.MILESTONE_DATE_EXEMPTION_TYPE_KEY,
                                checkContext.getStudentId(), context);
                    } catch (OperationFailedException ex) {
                        throw ex;
                    } catch (Exception ex) {
                        throw new OperationFailedException("unexpected", ex);
                    }
                    if (extensions.isEmpty()) {
                        vr = initError(checkContext.getInstruction());
                        return Arrays.asList(vr);
                    }
                    // TODO: worry about having more than one extension 
                    endDate = extensions.get(0).getDateOverride().getEffectiveEndDate();
                    if (endDate != null) {
                         if (checkContext.getDateToTest().after(endDate)) {
                           // TODO: insert the info object that the exemption was used
                           vr = initError(checkContext.getInstruction());
                           return Arrays.asList(vr);         
                         }
                    }
                    // TODO: insert the info object that the excemption failed despite the exemption
                }
                vr = initInfo(checkContext.getInstruction());
                return Arrays.asList(vr);
        }
        throw new OperationFailedException("bad logic in java code");
    }

    public static ValidationResultInfo initError(InstructionInfo instruction) {
        ValidationResultInfo vr = new ValidationResultInfo();
        vr.setElement(instruction.getProcessKey() + " - " + instruction.getCheckKey());
        if (instruction.getIsWarning()) {
            vr.setLevel(ValidationResultInfo.ErrorLevel.WARN);
        } else {
            vr.setLevel(ValidationResultInfo.ErrorLevel.ERROR);
        }
        vr.setMessage(instruction.getMessage().getPlain());
        return vr;
    }

    public static ValidationResultInfo initInfo(InstructionInfo instruction) {
        ValidationResultInfo vr = new ValidationResultInfo();
        vr.setElement(instruction.getProcessKey() + " - " + instruction.getCheckKey());
        vr.setLevel(ValidationResultInfo.ErrorLevel.OK);
        vr.setMessage(instruction.getMessage().getPlain());
        return vr;
    }
}
