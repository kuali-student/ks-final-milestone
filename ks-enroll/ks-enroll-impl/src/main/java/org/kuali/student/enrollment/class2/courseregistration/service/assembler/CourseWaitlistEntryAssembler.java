package org.kuali.student.enrollment.class2.courseregistration.service.assembler;

import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitlistEntryInfo;
import org.kuali.student.enrollment.lpr.dto.LprRosterEntryInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;

public class CourseWaitlistEntryAssembler implements DTOAssembler<CourseWaitlistEntryInfo, LprRosterEntryInfo> {

    @Override
    public CourseWaitlistEntryInfo assemble(LprRosterEntryInfo baseDTO, ContextInfo context) throws AssemblyException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LprRosterEntryInfo disassemble(CourseWaitlistEntryInfo businessDTO, ContextInfo context) throws AssemblyException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
