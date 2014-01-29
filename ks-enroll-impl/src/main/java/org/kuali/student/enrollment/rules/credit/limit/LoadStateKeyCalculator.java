package org.kuali.student.enrollment.rules.credit.limit;

import java.util.List;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * this calculates the state key based on the actions
 */
public class LoadStateKeyCalculator  {


    public String calcStateKey(List<CourseRegistrationTransaction> actions,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        for (CourseRegistrationTransaction action : actions) {
            switch (action.getAction()) {
                case NO_CHANGE:
                    continue;
                case CREATE:
                case UPDATE:
                case DELETE:
                    return AcademicRecordServiceTypeStateConstants.LOAD_STATE_PRELIMIARY;
                default:
                    throw new OperationFailedException(action.toString());

            }
        }
        return AcademicRecordServiceTypeStateConstants.LOAD_STATE_FINAL;
    }

}
