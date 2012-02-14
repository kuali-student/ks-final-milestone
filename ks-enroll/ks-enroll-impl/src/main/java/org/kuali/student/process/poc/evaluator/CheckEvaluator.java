package org.kuali.student.process.poc.evaluator;


import java.util.List;
import org.kuali.student.process.poc.context.CheckContext;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

public interface CheckEvaluator<C extends CheckContext> {

    public List<ValidationResultInfo> evaluate(C checkContext, ContextInfo context) 
            throws OperationFailedException;

}
