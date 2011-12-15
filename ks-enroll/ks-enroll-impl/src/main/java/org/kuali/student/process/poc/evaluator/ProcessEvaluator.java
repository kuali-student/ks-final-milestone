package org.kuali.student.process.poc.evaluator;

import java.util.List;

import org.kuali.student.process.poc.context.ProcessContext;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.Context;
import org.kuali.student.r2.common.infc.ValidationResult;

public interface ProcessEvaluator<P extends ProcessContext, C extends Context> {

    public List<? extends ValidationResult> evaluate( P processContext, C context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


}
