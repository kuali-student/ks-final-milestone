package org.kuali.student.enrollment.class2.courseregistration.service.assembler;

import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitlistEntryInfo;
import org.kuali.student.enrollment.lpr.dto.LprRosterEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.infc.DTOAssembler;

public class CourseWaitlistEntryAssembler implements DTOAssembler<CourseWaitlistEntryInfo, LprRosterEntryInfo> {

    @Override
    public CourseWaitlistEntryInfo assemble(LprRosterEntryInfo baseDTO, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LprRosterEntryInfo disassemble(CourseWaitlistEntryInfo businessDTO, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
