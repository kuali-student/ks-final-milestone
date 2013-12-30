package org.kuali.student.r2.core.process.evaluator;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.List;
import org.kuali.student.r2.core.process.context.ProcessContextInfo;

public interface ProcessEvaluator {

    public List<ValidationResultInfo> evaluate(ProcessContextInfo processContext, ContextInfo contextInfo) 
            throws OperationFailedException;


}
