package org.kuali.student.enrollment.class2.academicrecord.service.assembler;

import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

/**
 * Mock StudentCourseRecordAssembler...to  use until better registration & student record data is setup & useable
 */
public class StudentCourseRecordAssemblerMockAllGradeA extends StudentCourseRecordAssembler
    implements DTOAssembler<StudentCourseRecordInfo, CourseRegistrationInfo> {

	@Override
	public StudentCourseRecordInfo assemble(CourseRegistrationInfo courseReg, ContextInfo context) throws AssemblyException {
            StudentCourseRecordInfo courseRecord = new StudentCourseRecordInfo();
            
            courseRecord.setCourseRegistrationId(courseReg.getId());
            courseRecord.setPersonId(courseReg.getPersonId());

            try {
            
                LuiInfo lui = this.getLuiService().getLui(courseReg.getCourseOfferingId(), context);
                LuiIdentifierInfo identifier = lui.getOfficialIdentifier();
                courseRecord.setCourseTitle(identifier != null ? identifier.getLongName() : null);
                courseRecord.setCourseCode(identifier != null ? identifier.getCode() : null);

                //TODO:The code or number of the primary activity. how to determine which activity is primary?
                /*            RegGroupRegistrationInfo regGroup = courseReg.getRegGroupRegistration();
                              if (regGroup.getActivityRegistrations()!= null && !regGroup.getActivityRegistrations().isEmpty()) {
                              courseRecord.setActivityCode(regGroup.getActivityRegistrations().get(0).getActivityOffering().getActivityCode());
                              }
                */

                if (lui.getAtpId() != null) {
                    AtpInfo atp = getAtpService().getAtp(lui.getAtpId(), context);
                    courseRecord.setTermId(atp.getId());
                    courseRecord.setTermName(atp.getName());
                    courseRecord.setCourseBeginDate(atp.getStartDate());
                    courseRecord.setCourseEndDate(atp.getEndDate());
                }

//                GradeRosterEntryInfo finalRosterEntry = null;
//                finalRosterEntry = gradingService.getFinalGradeForStudentInCourseOffering(courseReg.getStudentId(), lui.getId(), context);
//                courseRecord.setAssignedGradeValue(getValue(finalRosterEntry.getAssignedGradeKey(), context));
//                courseRecord.setAssignedGradeScaleKey(getScaleKey(finalRosterEntry.getAssignedGradeKey(), context));
//                courseRecord.setAdministrativeGradeValue(getValue(finalRosterEntry.getAdministrativeGradeKey(), context));
//                courseRecord.setAdministrativeGradeScaleKey(getScaleKey(finalRosterEntry.getAdministrativeGradeKey(), context));
//                courseRecord.setCalculatedGradeValue(getValue(finalRosterEntry.getCalculatedGradeKey(), context));
//                courseRecord.setCalculatedGradeScaleKey(getScaleKey(finalRosterEntry.getCalculatedGradeKey(), context));
                courseRecord.setAssignedGradeValue("A");
                courseRecord.setAssignedGradeScaleKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_A);
                courseRecord.setAdministrativeGradeValue("A");
                courseRecord.setAdministrativeGradeScaleKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_A);
                courseRecord.setCalculatedGradeValue("A");
                courseRecord.setCalculatedGradeScaleKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_A);
                courseRecord.setCreditsAttempted("3.0");
                courseRecord.setCreditsEarned("3.0");
                courseRecord.setCreditsForGPA("3.0");

//            } catch (DisabledIdentifierException e) {
//                throw new AssemblyException("DisabledIdentifierException: " + e.getMessage());
            } catch (DoesNotExistException e) {
                throw new AssemblyException("DoesNotExistException: " + e.getMessage());
            } catch (InvalidParameterException e) {
                throw new AssemblyException("InvalidParameterException: " + e.getMessage());
            } catch (MissingParameterException e) {
                throw new AssemblyException("MissingParameterException: " + e.getMessage());
            } catch (OperationFailedException e) {
                throw new AssemblyException("OperationFailedException: " + e.getMessage());
            } catch (PermissionDeniedException e) {
                throw new AssemblyException("PermissionDeniedException: "  + e.getMessage());
            }	
            
            return courseRecord;
	}

}
