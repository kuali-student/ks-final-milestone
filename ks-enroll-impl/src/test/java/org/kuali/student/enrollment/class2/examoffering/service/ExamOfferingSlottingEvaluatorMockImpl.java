package org.kuali.student.enrollment.class2.examoffering.service;

import org.kuali.student.enrollment.class2.examoffering.krms.evaluator.ExamOfferingSlottingEvaluator;
import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingResult;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.List;

/**
 * Created by SW Genis on 2014/04/11.
 */
public class ExamOfferingSlottingEvaluatorMockImpl implements ExamOfferingSlottingEvaluator {

    @Override
    public ExamOfferingResult executeRuleForAOSlotting(ActivityOffering activityOffering, String examOfferingId, String termType,
                                         List<String> optionKeys, ContextInfo context) throws OperationFailedException {
        return null;
    }

    @Override
    public ExamOfferingResult executeRuleForCOSlotting(CourseOffering courseOffering, String examOfferingId, String termType,
                                         List<String> optionKeys, ContextInfo context) throws OperationFailedException {
        return null;
    }
}
