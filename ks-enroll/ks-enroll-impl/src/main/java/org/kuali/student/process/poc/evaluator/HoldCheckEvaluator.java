package org.kuali.student.process.poc.evaluator;

import java.util.List;

import org.kuali.student.process.poc.context.HoldCheckContext;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

public class HoldCheckEvaluator implements CheckEvaluator<HoldCheckContext, ContextInfo> {

    @Override
    public List<ValidationResultInfo> evaluate(HoldCheckContext checkContext, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            PermissionDeniedException, OperationFailedException {
        return null;

    }

}
