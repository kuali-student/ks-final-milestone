package org.kuali.student.enrollment.class2.coursewaitlist.service.transformer;

import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitlistEntryInfo;
import org.kuali.student.enrollment.roster.dto.LprRosterEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

public class CourseWaitlistEntry2LprRosterEntryTransformer {

    public static CourseWaitlistEntryInfo transformRoster2Waitlist(LprRosterEntryInfo rosterEntry, ContextInfo context)
            throws OperationFailedException {
        throw new OperationFailedException("not implemented");
    }

    public LprRosterEntryInfo transformWaitlist2Roster(CourseWaitlistEntryInfo waitlistEntry, ContextInfo context)
            throws OperationFailedException {
        throw new OperationFailedException("not implemented");
    }
}
