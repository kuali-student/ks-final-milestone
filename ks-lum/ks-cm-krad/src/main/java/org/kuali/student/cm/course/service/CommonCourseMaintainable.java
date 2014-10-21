package org.kuali.student.cm.course.service;

import org.kuali.student.cm.proposal.service.ProposalMaintainable;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

/**
 * An interface class for common course maintainable methods
 */
public interface CommonCourseMaintainable extends ProposalMaintainable {

    public void setRetirementAttributesOnCourse(CourseInfo courseInfo, ProposalInfo proposalInfo);

    /**
     * A method to identify if post processing methods should be run when called by workflow
     *
     * This is used to ignore the post processing for the "Modify This Version" proposal (also known as the "Modify No Version" proposal)
     * because that document type does not have a new version of the course and does no state changing of the course that it's modifying. The
     * save of the Course data will happen from the Controller rather than in the post processing.
     */
    public abstract boolean shouldIgnorePostProcessing(String documentId);

}
