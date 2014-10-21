package org.kuali.student.r2.core.process.evaluator;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.process.context.ProcessContext;

import java.util.List;

public interface ProcessEvaluator<C extends ProcessContext> {

    public List<ValidationResultInfo> evaluate(C processContext, ContextInfo context) 
            throws OperationFailedException;


}
