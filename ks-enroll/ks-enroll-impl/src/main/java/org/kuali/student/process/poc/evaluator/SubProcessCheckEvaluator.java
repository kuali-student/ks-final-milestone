package org.kuali.student.process.poc.evaluator;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.process.poc.context.SubProcessCheckContext;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.process.dto.InstructionInfo;

public class SubProcessCheckEvaluator implements CheckEvaluator<SubProcessCheckContext> {

    @Override
    public List<ValidationResultInfo> evaluate(SubProcessCheckContext checkContext, ContextInfo context) throws OperationFailedException {
        List<ValidationResultInfo> vrs = new ArrayList<ValidationResultInfo>();
        ValidationResultInfo vr = null;

        // rerun the process evaluator with the new process key
        String oldProcessKey = null;
        try {
            oldProcessKey = checkContext.getProcessContext().getProcessKey();
            checkContext.getProcessContext().setProcessKey(checkContext.getCheck().getProcessKey());
            vrs = checkContext.getProcessEvaluator().evaluate(checkContext.getProcessContext(), context);
        } finally {
            // set it back so we don't mess stuff up
            checkContext.getProcessContext().setProcessKey(oldProcessKey);
        }

        if (hasErrors(vrs)) {
            vr = initInfo(checkContext.getInstruction());
            vrs.add(vr);
            vr.setMessage("you have errors in the subprocess");
            return vrs;
        }
        vr = initInfo(checkContext.getInstruction());
        vrs.add(vr);
        vr.setMessage("no errors were found in the subprocess");
        return vrs;
    }

    public static boolean hasErrors(List<ValidationResultInfo> vrs) {
        for (ValidationResultInfo vr : vrs) {
            if (vr.getIsError()) {
                return true;
            }
            if (vr.getIsWarn()) {
                return true;
            }
        }
        return false;
    }

    private static ValidationResultInfo initError(InstructionInfo instruction) {
        return MilestoneCheckEvaluator.initError(instruction);
    }

    private static ValidationResultInfo initInfo(InstructionInfo instruction) {
        return MilestoneCheckEvaluator.initInfo(instruction);
    }
}
