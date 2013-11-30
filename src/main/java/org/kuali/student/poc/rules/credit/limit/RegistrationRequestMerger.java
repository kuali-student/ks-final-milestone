package org.kuali.student.poc.rules.credit.limit;

import java.util.List;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * This code merges a registration request with a student's already persisted data to create a composite so we can apply rules
 * against it such as credit limit checks and time conflict checks and co-requisite checks.
 *
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
