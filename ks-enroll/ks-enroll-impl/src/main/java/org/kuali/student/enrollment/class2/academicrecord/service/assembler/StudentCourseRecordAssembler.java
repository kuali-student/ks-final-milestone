package org.kuali.student.enrollment.class2.academicrecord.service.assembler;

import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegGroupRegistrationInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

public class StudentCourseRecordAssembler implements DTOAssembler<StudentCourseRecordInfo, CourseRegistrationInfo> {
	private AtpService atpService;
	private GradingService gradingService;
	
	public AtpService getAtpService() {
		return atpService;
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}

	public GradingService getGradingService() {
		return gradingService;
	}

	public void setGradingService(GradingService gradingService) {
		this.gradingService = gradingService;
	}

	@Override
	public StudentCourseRecordInfo assemble(CourseRegistrationInfo courseReg, ContextInfo context) {
		StudentCourseRecordInfo courseRecord = new StudentCourseRecordInfo();
		
		courseRecord.setCourseRegistrationId(courseReg.getId());
		courseRecord.setPersonId(courseReg.getStudentId());
		
		CourseOfferingInfo co = courseReg.getCourseOffering();
		courseRecord.setCourseTitle(co.getCourseTitle());
		
		try{
			if(co.getTermKey() != null){
				AtpInfo atp = atpService.getAtp(co.getTermKey(), context);
					courseRecord.setTermName(atp.getName());
					courseRecord.setCourseBeginDate(atp.getStartDate());
					courseRecord.setCourseEndDate(atp.getEndDate());
						
			}
			
			GradeRosterEntryInfo finalRosterEntry = gradingService.getFinalGradeForStudentInCourseOffering(courseReg.getStudentId(), co.getId(), context);
			courseRecord.setAssignedGradeValue(finalRosterEntry.getAssignedGradeKey());
		} catch (DoesNotExistException e) {
			e.printStackTrace();
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		} catch (MissingParameterException e) {
			e.printStackTrace();
		} catch (OperationFailedException e) {
			e.printStackTrace();
		} catch (PermissionDeniedException e) {
			e.printStackTrace();
		}	
		
		
		RegGroupRegistrationInfo regGroup = courseReg.getRegGroupRegistration();

		return courseRecord;
	}

	@Override
	public CourseRegistrationInfo disassemble(
			StudentCourseRecordInfo businessDTO, ContextInfo context) {
		return null;
	}
}
