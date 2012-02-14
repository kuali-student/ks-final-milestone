package org.kuali.student.process.poc.evaluator;

import java.util.List;

import org.kuali.student.process.poc.context.ProcessContext;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

public interface ProcessEvaluator<C extends ProcessContext> {

    public List<ValidationResultInfo> evaluate(C processContext, ContextInfo context) 
            throws OperationFailedException;


}
