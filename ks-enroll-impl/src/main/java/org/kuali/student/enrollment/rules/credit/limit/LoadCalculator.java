package org.kuali.student.enrollment.rules.credit.limit;

import java.util.List;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * This defines the interface that needs to be implemented to execute a load calculation
 */
public interface LoadCalculator {


    public LoadInfo calculateLoad(List<CourseRegistrationInfo> courseRegistrations,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException;
}
