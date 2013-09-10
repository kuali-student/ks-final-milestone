package org.kuali.student.enrollment.class2.coursewaitlist.service.facade;

import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.r2.common.dto.ContextInfo;

/**
 * Business Logic for activating and deactivating Waitlists for AO level by a specified CO
 *
 * @author Kuali Student Team
 */
public interface CourseWaitListServiceFacade {
    public void activateActivityOfferingWaitlistsByCourseOffering(String coId, ContextInfo context) throws Exception;
    public void deactivateActivityOfferingWaitlistsByCourseOffering(String coId, ContextInfo context) throws Exception;

}
