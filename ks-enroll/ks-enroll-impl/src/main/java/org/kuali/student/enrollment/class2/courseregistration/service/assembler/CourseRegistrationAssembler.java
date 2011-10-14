package org.kuali.student.enrollment.class2.courseregistration.service.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegGroupRegistrationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.r2.common.assembler.RelationshipDTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultValuesGroup;

public class CourseRegistrationAssembler {

    public CourseRegistrationInfo assemble(LuiPersonRelationInfo baseDTO, CourseOfferingInfo courseOfferingInfo, ContextInfo context) {
        CourseRegistrationInfo courseRegInfo = new CourseRegistrationInfo();
        RelationshipDTOAssembler<LuiPersonRelationInfo, CourseRegistrationInfo> commonAssembler = new RelationshipDTOAssembler<LuiPersonRelationInfo, CourseRegistrationInfo>();
        courseRegInfo = commonAssembler.assemble(baseDTO, courseRegInfo, context);

        courseRegInfo.setStudentId(baseDTO.getPersonId());
        // courseRegInfo.setCreditCount(creditCount);
        // courseRegInfo.setGradingOptionKey(gradingOptionKey);
        courseRegInfo.setCourseOffering(courseOfferingInfo);
        return courseRegInfo;
    }

    public CourseRegistrationInfo assemble(LuiPersonRelationInfo courseRegLPR, CourseOfferingInfo courseOfferingInfo, Map<LuiPersonRelationInfo, ActivityOfferingInfo> activityRegLPRs,
            LuiPersonRelationInfo regGroupRegLPR, RegistrationGroupInfo regGroupInfo, ContextInfo context) {

        CourseRegistrationInfo courseRegInfo = assemble(courseRegLPR, courseOfferingInfo, context);
        courseRegInfo.setRegGroupRegistration(assembleRegGroup(regGroupRegLPR, activityRegLPRs, regGroupInfo, context));
        return courseRegInfo;
    }

    public LuiPersonRelationInfo disassemble(CourseRegistrationInfo businessDTO, ContextInfo context) {
        return null;
    }

    //FIXME: Needs sure whether this ResultValuesGroup loading should be a part of the above assemble methods
    public CourseRegistrationInfo assemble(LuiPersonRelationInfo baseDTO, ResultValuesGroup resultValuesGroup, ContextInfo context) {
        CourseRegistrationInfo courseRegInfo = new CourseRegistrationInfo();

        courseRegInfo.setId(baseDTO.getId());
        courseRegInfo.setStudentId(baseDTO.getPersonId());
        if (resultValuesGroup != null){
            courseRegInfo.setGradingOptionKey(resultValuesGroup.getKey());
        }
        return courseRegInfo;
    }

    public List<CourseRegistrationInfo> assembleList(List<LuiPersonRelationInfo> baseDTOs, ContextInfo context) {
        return new ArrayList<CourseRegistrationInfo>();
    }

    public List<CourseRegistrationInfo> assembleList(List<String> luiPersonRelationIdsForLui) {
        return new ArrayList<CourseRegistrationInfo>();
    }

    private RegGroupRegistrationInfo assembleRegGroup(LuiPersonRelationInfo regGroupRegLPR, Map<LuiPersonRelationInfo, ActivityOfferingInfo> activityLprInfoMap, RegistrationGroupInfo regGroupInfo,
            ContextInfo context) {

        RegGroupRegistrationInfo regGroupRegistrationInfo = new RegGroupRegistrationInfo();
        RelationshipDTOAssembler<LuiPersonRelationInfo, RegGroupRegistrationInfo> commonAssembler = new RelationshipDTOAssembler<LuiPersonRelationInfo, RegGroupRegistrationInfo>();
        regGroupRegistrationInfo = commonAssembler.assemble(regGroupRegLPR, regGroupRegistrationInfo, context);
        regGroupRegistrationInfo.setStudentId(regGroupRegLPR.getPersonId());
        regGroupRegistrationInfo.setActivityRegistrations(assembleActivityRegistrations(activityLprInfoMap, context));
        regGroupRegistrationInfo.setRegistrationGroup(regGroupInfo);

        return regGroupRegistrationInfo;
    }

    private List<ActivityRegistrationInfo> assembleActivityRegistrations( Map<LuiPersonRelationInfo, ActivityOfferingInfo> activityLprInfoMap , ContextInfo context) {
        List<ActivityRegistrationInfo> activityRegistrations = new ArrayList<ActivityRegistrationInfo>();
        for ( Map.Entry<LuiPersonRelationInfo, ActivityOfferingInfo> entry : activityLprInfoMap.entrySet() ) {
            ActivityRegistrationInfo activityRegistrationInfo = new ActivityRegistrationInfo();
            RelationshipDTOAssembler<LuiPersonRelationInfo, ActivityRegistrationInfo> commonAssembler = new RelationshipDTOAssembler<LuiPersonRelationInfo, ActivityRegistrationInfo>();
            activityRegistrationInfo = commonAssembler.assemble(entry.getKey() , activityRegistrationInfo, context);
            activityRegistrationInfo.setActivityOffering(entry.getValue() );

            activityRegistrationInfo.setStudentId(entry.getKey().getPersonId());
            activityRegistrations.add(activityRegistrationInfo);

        }
        return activityRegistrations;
    }
}
