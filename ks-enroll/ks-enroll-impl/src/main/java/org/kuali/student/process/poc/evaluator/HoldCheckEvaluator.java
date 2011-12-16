package org.kuali.student.process.poc.evaluator;

import java.util.Date;
import java.util.List;

import org.kuali.student.process.poc.context.HoldCheckContext;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.service.HoldService;

public class HoldCheckEvaluator implements CheckEvaluator<HoldCheckContext, ContextInfo> {

    HoldService holdService;

    AtpService atpService;

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public void setHoldService(HoldService holdService) {
        this.holdService = holdService;
    }

    
    
    @Override
    public ValidationResult evaluate(HoldCheckContext checkContext, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, PermissionDeniedException,
            OperationFailedException {

        List<HoldInfo> holdInfoList = holdService.getActiveHoldsByIssueAndPerson(checkContext.getIssueId(), checkContext.getStudentId(), context);

        for (HoldInfo activeHoldInfo : holdInfoList) {
            if (!activeHoldInfo.getIsOverridable() || !activeHoldInfo.getIsWarning() && activeHoldInfo.getEffectiveDate().after(new Date())) {
                ValidationResultInfo validationResult = new ValidationResultInfo();
                validationResult.setError("Hold is active on the person" + activeHoldInfo.getName());
                return validationResult;
            }

        }

        return new ValidationResultInfo();
    }
}
