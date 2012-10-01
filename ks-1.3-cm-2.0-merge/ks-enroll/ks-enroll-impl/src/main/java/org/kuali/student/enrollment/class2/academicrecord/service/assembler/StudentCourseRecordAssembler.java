package org.kuali.student.enrollment.class2.academicrecord.service.assembler;

import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.service.GradingService;

import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;

import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

public class StudentCourseRecordAssembler 
    implements DTOAssembler<StudentCourseRecordInfo, CourseRegistrationInfo> {

	private AtpService atpService;
	private GradingService gradingService;
	private LRCService lrcService;
	private LuiService luiService;
	
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

	public LRCService getLrcService() {
		return lrcService;
	}

	public void setLrcService(LRCService lrcService) {
		this.lrcService = lrcService;
	}

	public LuiService getLuiService() {
		return luiService;
	}

	public void setLuiService(LuiService luiService) {
		this.luiService = luiService;
	}

	@Override
	public StudentCourseRecordInfo assemble(CourseRegistrationInfo courseReg, ContextInfo context) throws AssemblyException {
            StudentCourseRecordInfo courseRecord = new StudentCourseRecordInfo();
            
            courseRecord.setCourseRegistrationId(courseReg.getId());
            courseRecord.setPersonId(courseReg.getStudentId());

            try {
            
                LuiInfo lui = this.luiService.getLui(courseReg.getCourseOfferingId(), context);
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
                    courseRecord.setTermName(atp.getName());
                    courseRecord.setCourseBeginDate(atp.getStartDate());
                    courseRecord.setCourseEndDate(atp.getEndDate());
                }

                GradeRosterEntryInfo finalRosterEntry = null;
                finalRosterEntry = gradingService.getFinalGradeForStudentInCourseOffering(courseReg.getStudentId(), lui.getId(), context);
                courseRecord.setAssignedGradeValue(getValue(finalRosterEntry.getAssignedGradeKey(), context));
                courseRecord.setAssignedGradeScaleKey(getScaleKey(finalRosterEntry.getAssignedGradeKey(), context));
                courseRecord.setAdministrativeGradeValue(getValue(finalRosterEntry.getAdministrativeGradeKey(), context));
                courseRecord.setAdministrativeGradeScaleKey(getScaleKey(finalRosterEntry.getAdministrativeGradeKey(), context));
                courseRecord.setCalculatedGradeValue(getValue(finalRosterEntry.getCalculatedGradeKey(), context));
                courseRecord.setCalculatedGradeScaleKey(getScaleKey(finalRosterEntry.getCalculatedGradeKey(), context));
                
            } catch (DisabledIdentifierException e) {
                throw new AssemblyException("DisabledIdentifierException: " + e.getMessage());
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

	@Override
	public CourseRegistrationInfo disassemble(
			StudentCourseRecordInfo businessDTO, ContextInfo context) {
		return null;
	}
	
	private String getValue(String key, ContextInfo context) throws AssemblyException {
		String value = null;
		if(key != null){
			try {
				if(lrcService != null){
					ResultValueInfo resultValue = lrcService.getResultValue(key, context);
					value = resultValue.getValue();
				}
			} catch (DoesNotExistException e) {
				throw new AssemblyException("DoesNotExistException: " + e.getMessage());
			} catch (InvalidParameterException e) {
				throw new AssemblyException("InvalidParameterException: "  + e.getMessage());
			} catch (MissingParameterException e) {
				throw new AssemblyException("MissingParameterException: "  + e.getMessage());
			} catch (OperationFailedException e) {
				throw new AssemblyException("OperationFailedException: "  + e.getMessage());
			} catch (PermissionDeniedException e) {
				throw new AssemblyException("PermissionDeniedException: "  + e.getMessage());
			}
		}
		
		return value;
	}
	
	private String getScaleKey(String key, ContextInfo context) throws AssemblyException {
		String scaleKey = null;
		if(key != null){
			try {
				if(lrcService != null){
					ResultValueInfo resultValue = lrcService.getResultValue(key, context);
					scaleKey = resultValue.getResultScaleKey();
				}
			} catch (DoesNotExistException e) {
				throw new AssemblyException("DoesNotExistException: " + e.getMessage());
			} catch (InvalidParameterException e) {
				throw new AssemblyException("InvalidParameterException: " + e.getMessage());
			} catch (MissingParameterException e) {
				throw new AssemblyException("MissingParameterException: "  + e.getMessage());
			} catch (OperationFailedException e) {
				throw new AssemblyException("OperationFailedException: "  + e.getMessage());
			} catch (PermissionDeniedException e) {
				throw new AssemblyException("PermissionDeniedException: "  + e.getMessage());
			}
		}
		
		return scaleKey;
	}
}
