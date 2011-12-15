package org.kuali.student.process.poc.evaluator;

import java.util.List;

import org.kuali.student.process.poc.context.CheckContext;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.Context;
import org.kuali.student.r2.common.infc.ValidationResult;

public interface CheckEvaluator<P extends CheckContext, C extends Context> {

    public List<? extends ValidationResult> evaluate(P checkContext, C context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;;

}
