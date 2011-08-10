package org.kuali.student.enrollment.class2.courseregistration.service.assembler;

import java.util.List;

import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.infc.DTOAssembler;

public class CourseRegistrationAssembler implements DTOAssembler<CourseRegistrationInfo, LuiPersonRelationInfo> {

    @Override
    public CourseRegistrationInfo assemble(LuiPersonRelationInfo baseDTO, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LuiPersonRelationInfo disassemble(CourseRegistrationInfo businessDTO, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    public List<CourseRegistrationInfo> assembleList(List<LuiPersonRelationInfo> baseDTOs, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public List<CourseRegistrationInfo> assembleList(List<String> luiPersonRelationIdsForLui) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }


}
