package org.kuali.student.enrollment.class2.courseregistration.service.assembler;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegGroupRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegGroupRegistration;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.r2.common.assembler.CommonRelationshipDTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.infc.DTOAssembler;

public class CourseRegistrationAssembler implements DTOAssembler<CourseRegistrationInfo, LuiPersonRelationInfo> {

    @Override
    public CourseRegistrationInfo assemble(LuiPersonRelationInfo baseDTO, ContextInfo context) {
        CourseRegistrationInfo courseRegInfo = new CourseRegistrationInfo();
        CommonRelationshipDTOAssembler<LuiPersonRelationInfo, CourseRegistrationInfo> commonAssembler = new CommonRelationshipDTOAssembler<LuiPersonRelationInfo, CourseRegistrationInfo>();
        courseRegInfo = commonAssembler.assemble(baseDTO, courseRegInfo, context);

        courseRegInfo.setStudentId(baseDTO.getPersonId());
        // courseRegInfo.setCreditCount(creditCount);
        // courseRegInfo.setGradingOption(gradingOption);
        // courseRegInfo.setCourseOffering(baseDTO.getLuiId());

        return courseRegInfo;
    }

    public CourseRegistrationInfo assemble(LuiPersonRelationInfo courseRegLPR,
            List<LuiPersonRelationInfo> activityRegLPRs, LuiPersonRelationInfo regGroupRegLPR, ContextInfo context) {

        CourseRegistrationInfo courseRegInfo = assemble(courseRegLPR, context);
        courseRegInfo.setRegGroupRegistration(assembleRegGroup(regGroupRegLPR, activityRegLPRs, context));
        return courseRegInfo;
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

    private RegGroupRegistrationInfo assembleRegGroup(LuiPersonRelationInfo regGroupRegLPR,
            List<LuiPersonRelationInfo> activityRegLPRs, ContextInfo context) {

        RegGroupRegistrationInfo regGroupRegistrationInfo = new RegGroupRegistrationInfo();
        CommonRelationshipDTOAssembler<LuiPersonRelationInfo, RegGroupRegistrationInfo> commonAssembler = new CommonRelationshipDTOAssembler<LuiPersonRelationInfo, RegGroupRegistrationInfo>();
        regGroupRegistrationInfo = commonAssembler.assemble(regGroupRegLPR, regGroupRegistrationInfo, context);
        regGroupRegistrationInfo.setStudentId(regGroupRegLPR.getPersonId());
        regGroupRegistrationInfo.setActivityRegistrations(assembleActivityRegistrations(activityRegLPRs, context));

        return null;
    }

    private List<ActivityRegistrationInfo> assembleActivityRegistrations(List<LuiPersonRelationInfo> activtyRegLprs,
            ContextInfo context) {
        List<ActivityRegistrationInfo> activityRegistrations = new ArrayList<ActivityRegistrationInfo>();
        for (LuiPersonRelationInfo activtyRegLpr : activtyRegLprs) {
            ActivityRegistrationInfo activityRegistrationInfo = new ActivityRegistrationInfo();
            CommonRelationshipDTOAssembler<LuiPersonRelationInfo, ActivityRegistrationInfo> commonAssembler = new CommonRelationshipDTOAssembler<LuiPersonRelationInfo, ActivityRegistrationInfo>();
            activityRegistrationInfo = commonAssembler.assemble(activtyRegLpr, activityRegistrationInfo, context);
            activityRegistrations.add(activityRegistrationInfo);

        }
        return null;
    }
}
