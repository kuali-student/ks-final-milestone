package org.kuali.student.cm.course.service;

import org.kuali.student.cm.proposal.service.ProposalMaintainable;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

/**
 * An interface class for common course maintainable methods
 */
public interface CommonCourseMaintainable extends ProposalMaintainable {

    public void setRetirementAttributesOnCourse(CourseInfo courseInfo, ProposalInfo proposalInfo);

}
