
package org.kuali.student.enrollment.rules.credit.limit;

import java.util.List;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * This defines the methods that need to be implemented to merge a reg request with existing registrations *
 */
public interface RegistrationRequestMerger {

    /**
     * Make registration actions merging the request and existing registrations
     *
     * @param request
     * @param registrations
     * @param skipActivities if true then skip the merger of activities.  Load Level calc does not require activity merger.
     * @param contextInfo
     * @return
     * @throws OperationFailedException
     */
    public List<CourseRegistrationTransaction> merge(RegistrationRequestInfo request,
            List<CourseRegistrationInfo> registrations,
            boolean skipActivities,
            ContextInfo contextInfo)
            throws OperationFailedException;

    /**
     * Simulate what the course registrations would look like if the request were to be successfully applied to the current 
     * registrations
     *
     * @param request
     * @param registrations
     * @param skipActivities if true then skip the merger of activities.  Load Level calc does not require activity merger.
     * @param contextInfo
     * @return
     * @throws OperationFailedException
     */
    public List<CourseRegistrationInfo> simulate(RegistrationRequestInfo request,
            List<CourseRegistrationInfo> registrations,
            boolean skipActivities,
            ContextInfo contextInfo)
            throws OperationFailedException;
}
