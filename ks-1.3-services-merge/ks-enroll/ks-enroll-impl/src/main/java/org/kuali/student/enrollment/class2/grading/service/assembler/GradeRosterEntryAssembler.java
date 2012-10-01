package org.kuali.student.enrollment.class2.grading.service.assembler;

import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.List;
import org.kuali.student.enrollment.roster.dto.LprRosterEntryInfo;

public class GradeRosterEntryAssembler implements DTOAssembler<GradeRosterEntryInfo, LprRosterEntryInfo> {


    @Override
    public GradeRosterEntryInfo assemble(LprRosterEntryInfo lprRosterEntryInfo, ContextInfo context) {
        GradeRosterEntryInfo gradeRosterEntryInfo = new GradeRosterEntryInfo();
        gradeRosterEntryInfo.setId(lprRosterEntryInfo.getId());

        return gradeRosterEntryInfo;
    }

    public GradeRosterEntryInfo assemble(LprRosterEntryInfo lprRosterEntryInfo, String studentId, String activityOfferingId, String assignedGradeKey, String calculatedGradeKey, String administrativeGradeKey, String creditsEarnedKey, List<String> gradingOptionKeys, ContextInfo context) {
        GradeRosterEntryInfo gradeRosterEntryInfo = assemble(lprRosterEntryInfo, context);
        gradeRosterEntryInfo.setActivityOfferingId(activityOfferingId);
        gradeRosterEntryInfo.setAdministrativeGradeKey(administrativeGradeKey);
        gradeRosterEntryInfo.setAssignedGradeKey(assignedGradeKey);
        gradeRosterEntryInfo.setCalculatedGradeKey(calculatedGradeKey);
        gradeRosterEntryInfo.setCreditsEarnedKey(creditsEarnedKey);
        gradeRosterEntryInfo.setValidGradeGroupKeys(gradingOptionKeys);
        gradeRosterEntryInfo.setStudentId(studentId);
        return gradeRosterEntryInfo;
    }

    @Override
    public LprRosterEntryInfo disassemble(GradeRosterEntryInfo gradeRosterEntryInfo, ContextInfo context) {
        // TODO implement method
        throw new UnsupportedOperationException("Method not implemented.");
    }
}
