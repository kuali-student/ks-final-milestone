package org.kuali.student.enrollment.class2.acal.service.assembler;

import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.List;

public class GradeRosterAssembler implements DTOAssembler<GradeRosterInfo, LprRosterInfo> {

    @Override
    public GradeRosterInfo assemble(LprRosterInfo lprRosterInfo, ContextInfo context) {

        GradeRosterInfo gradeRosterInfo = new GradeRosterInfo();
        gradeRosterInfo.setId(lprRosterInfo.getId());
        gradeRosterInfo.setActivityOfferingIds(lprRosterInfo.getAssociatedLuiIds());

        return gradeRosterInfo;
    }
    public GradeRosterInfo assemble(LprRosterInfo lprRosterInfo, List<String> lprRosterEntryIds, List<String> graderIds, String courseOfferingId, ContextInfo context) {
        GradeRosterInfo gradeRosterInfo = assemble(lprRosterInfo, context);
        gradeRosterInfo.setGradeRosterEntryIds(lprRosterEntryIds);
        gradeRosterInfo.setGraderIds(graderIds);
        gradeRosterInfo.setCourseOfferingId(courseOfferingId);
        return gradeRosterInfo;
    }

    @Override
    public LprRosterInfo disassemble(GradeRosterInfo gradeRosterInfo, ContextInfo context) {
        // TODO implement method
        throw new UnsupportedOperationException("Method not implemented.");
    }
}
