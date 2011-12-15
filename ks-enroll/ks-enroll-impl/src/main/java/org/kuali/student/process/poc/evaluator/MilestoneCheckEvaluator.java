package org.kuali.student.process.poc.evaluator;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.process.poc.context.MilestoneCheckContext;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.service.AtpService;

public class MilestoneCheckEvaluator implements CheckEvaluator<MilestoneCheckContext, ContextInfo> {

    AtpService atpService;

    @Override
    public List<ValidationResultInfo> evaluate(MilestoneCheckContext checkContext, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        return new ArrayList<ValidationResultInfo>();
    }

}
