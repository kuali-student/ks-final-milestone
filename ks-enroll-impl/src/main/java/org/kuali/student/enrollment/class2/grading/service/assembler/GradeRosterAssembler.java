package org.kuali.student.enrollment.class2.grading.service.assembler;

import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.List;
import org.kuali.student.enrollment.roster.dto.LprRosterInfo;

public class GradeRosterAssembler implements DTOAssembler<GradeRosterInfo, LprRosterInfo> {

    @Override
    public GradeRosterInfo assemble(LprRosterInfo lprRosterInfo, ContextInfo context) {

        GradeRosterInfo gradeRosterInfo = new GradeRosterInfo();
        gradeRosterInfo.setId(lprRosterInfo.getId());
        gradeRosterInfo.setStateKey(lprRosterInfo.getStateKey());
        gradeRosterInfo.setTypeKey(lprRosterInfo.getTypeKey());
        gradeRosterInfo.setDescr(lprRosterInfo.getDescr());
        gradeRosterInfo.setAttributes(lprRosterInfo.getAttributes());
        gradeRosterInfo.setName(lprRosterInfo.getName());
        return gradeRosterInfo;
    }

    public GradeRosterInfo assemble(LprRosterInfo lprRosterInfo, List<String> lprRosterEntryIds, List<String> graderIds, String courseOfferingId, List<String> activityOfferingIds, ContextInfo context) {
        GradeRosterInfo gradeRosterInfo = assemble(lprRosterInfo, context);
        gradeRosterInfo.setGradeRosterEntryIds(lprRosterEntryIds);
        gradeRosterInfo.setGraderIds(graderIds);
        gradeRosterInfo.setCourseOfferingId(courseOfferingId);
        gradeRosterInfo.setActivityOfferingIds(activityOfferingIds);
        return gradeRosterInfo;
    }

    @Override
    public LprRosterInfo disassemble(GradeRosterInfo gradeRosterInfo, ContextInfo context) {
        // TODO implement method
        throw new UnsupportedOperationException("Method not implemented.");
    }
}
