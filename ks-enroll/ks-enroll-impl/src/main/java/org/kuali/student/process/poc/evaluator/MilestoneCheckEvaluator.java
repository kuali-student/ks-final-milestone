package org.kuali.student.process.poc.evaluator;


import java.util.Date;
import java.util.List;

import org.kuali.student.process.poc.context.MilestoneCheckContext;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

public class MilestoneCheckEvaluator implements CheckEvaluator<MilestoneCheckContext, ContextInfo> {

    AtpService atpService;

    @Override
    public ValidationResultInfo evaluate(MilestoneCheckContext checkContext, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        
        List<MilestoneInfo> milestones = atpService.getMilestonesByTypeForAtp(checkContext.getAtpKey(), checkContext.getMilestoneTypeKey(), context);

        if (checkContext.getMilestoneTypeKey().equals("DATE_RANGE_MILESTONE_CHECK")) {
            for (MilestoneInfo milestone : milestones) {
                ValidationResultInfo validationError = new ValidationResultInfo();
                if (milestone.getTypeKey().equals("IN_DATE_RANGE")) {
                    if (new Date().after(milestone.getStartDate()) && new Date().before(milestone.getEndDate())) {

                        validationError.setLevel(0);

                    } else {

                        validationError.setError("Milestone date range check failed");
                        return validationError;

                    }
                   
                }
            }
        } else if (checkContext.getMilestoneTypeKey().equals("DEADLINE_MILESTONE_CHECK")) {
            for (MilestoneInfo milestone : milestones) {
                ValidationResultInfo validationError = new ValidationResultInfo();
                if (milestone.getTypeKey().equals("START_DATE_MILESTONE_CHECK")) {
                    if (new Date().after(milestone.getStartDate())) {
                        validationError.setLevel(0);
                    } else {

                        validationError.setError("Milestone deadline check failed");
                        return validationError;
                    }
                   
                }

            }

        }
        return new ValidationResultInfo();
    }

}
