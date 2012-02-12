package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.R1ToR2CopyHelper;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.assembler.EntityDTOAssembler;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.lu.dto.LuCodeInfo;

import java.util.ArrayList;
import java.util.List;

public class CourseOfferingAssembler implements DTOAssembler<CourseOfferingInfo, LuiInfo>{
	private LuiService luiService;
	private LuiPersonRelationService lprService;
	
	public LuiService getLuiService() {
		return luiService;
	}

	public void setLuiService(LuiService luiService) {
		this.luiService = luiService;
	}

	public LuiPersonRelationService getLprService() {
		return lprService;
	}

	public void setLprService(LuiPersonRelationService lprService) {
		this.lprService = lprService;
	}

	@Override
	public CourseOfferingInfo assemble(LuiInfo lui, ContextInfo context) throws AssemblyException {
		if(lui != null){
			CourseOfferingInfo co = new CourseOfferingInfo();
            EntityDTOAssembler<LuiInfo, CourseOfferingInfo> commonAssembler = new EntityDTOAssembler<LuiInfo, CourseOfferingInfo>();
            co = commonAssembler.assemble(lui, co, context);
			co.setId(lui.getId());
			co.setMaximumEnrollment(lui.getMaximumEnrollment());
			co.setMinimumEnrollment(lui.getMinimumEnrollment());

			assembleLuiCodes(lui, co);
			
			//below undecided
			//co.setHasWaitlist(lui.getHasWaitlist());
			//co.setWaitlistTypeKey(lui.getWaitlistTypeKey());
			//co.setWaitlistMaximum(lui.getWaitlistMaximum());
			//co.setIsWaitlistCheckinRequired(lui.getIsWaitlistCheckinRequired());
			//co.setWaitlistCheckinFrequency(lui.getWaitlistCheckinFrequency());
			
			co.setCourseId(lui.getCluId());
			co.setTermId(lui.getAtpId());
			co.setUnitsDeployment(lui.getUnitsDeployment());
			co.setUnitsContentOwner(lui.getUnitsContentOwner());

			co.setFees(lui.getFees());
			co.setRevenues(lui.getRevenues());
            co.setGradingOptionKeys(lui.getResultValuesGroupKeys());

            /*
             * From Bonnie: Comment out setting for Expenditure since we got  DataValidationErrorException:
             * Error(s) validating course offering Validation Results:
             *             [2] Path: [expenditure.id] - error.outOfRange data=[null]
             * the value of expenditure.id looks like this org.kuali.student.lum.course.dto.CourseExpenditureInfo@16a3516.
             * Norm's input:
             *  the problem is in the code I wrote a long time ago...
             *  // TODO: worry about using the toString method for the id
             *  r2.setId(r1.toString());
             *  well I should have worried about it more... :(
             *  I was using that as part of a mock impl where I needed a fake id but we should remove it
             * it is in the R1ToR2CopyHelper.java
             *
             * After the above issue is fixed, we can revisit about   co.setExpenditure(lui.getExpenditure());
             */
//			co.setExpenditure(lui.getExpenditure());
			co.setFormatIds(lui.getCluCluRelationIds());
			
			assembleIdentifier(lui, co);
			
			//TODO: lui.getResultOptionIds() -- co.setCreditOptions & co.setGradingOptionKeys --- call LRCService.getResultValuesByIdList
			
			//instructors
			assembleInstructors(co, lui.getId(), context);
			
			//lui.getAlternateIdentifiers() -- where to map?
			//lui.getName() -- where to map?
			//lui.getReferenceURL() -- where to map?
		
		
			//LuiLuiRelation (to set jointOfferingIds, hasFinalExam)
			 assembleLuiLuiRelations(co, lui.getId(), context);
			
			return co;
		}
		else
			return null;
	}

	private void assembleLuiCodes(LuiInfo lui, CourseOfferingInfo co){
		co.setIsHonorsOffering(false);
		List<LuCodeInfo> luiCodes = lui.getLuiCodes();
		if(luiCodes!= null && !luiCodes.isEmpty()){
			for(LuCodeInfo luiCode : luiCodes){
				if(luiCode.getTypeKey().equals("kuali.lu.code.honorsOffering"))
					co.setIsHonorsOffering(Boolean.parseBoolean(luiCode.getValue()));
					break;
			}
		}
	}
	
	private void assembleInstructors(CourseOfferingInfo co, String luiId, ContextInfo context) throws AssemblyException{
		List<LuiPersonRelationInfo> lprs = null;;
		try {
			lprs = lprService.getLprsByLui(luiId, context);
		} catch (DoesNotExistException e) {
			throw new AssemblyException("DoesNotExistException: " + e.getMessage());
		} catch (InvalidParameterException e) {
			throw new AssemblyException("InvalidParameterException:" + e.getMessage());
		} catch (MissingParameterException e) {
			throw new AssemblyException("MissingParameterException"  + e.getMessage());
		} catch (OperationFailedException e) {
			throw new AssemblyException("OperationFailedException"  + e.getMessage());
		} catch (PermissionDeniedException e) {
			throw new AssemblyException("PermissionDeniedException"  + e.getMessage());
		}
		
		if(lprs != null && !lprs.isEmpty()){
			List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
			for (LuiPersonRelationInfo lpr : lprs){
				if(lpr != null && lpr.getTypeKey() != null && lpr.getTypeKey().equals(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY)){
					OfferingInstructorInfo instructor = new OfferingInstructorInfo();
					instructor.setPersonId(lpr.getPersonId());
					instructor.setPercentageEffort(lpr.getCommitmentPercent());
					instructor.setId(lpr.getId());
					instructor.setTypeKey(lpr.getTypeKey());
					instructor.setStateKey(lpr.getStateKey());
					instructors.add(instructor);
				}
			}
			co.setInstructors(instructors);
		}		
	}
	
	private void assembleLuiLuiRelations(CourseOfferingInfo co, String luiId, ContextInfo context) throws AssemblyException {
		try {
			List<String> jointOfferingIds = new ArrayList<String>();
			List<String> finalExams = new ArrayList<String>();
			List<LuiLuiRelationInfo> rels = luiService.getLuiLuiRelationsByLui(luiId, context);
			if(rels != null && !rels.isEmpty()){                  
                for(LuiLuiRelationInfo rel : rels){
                	if(rel.getLuiId().equals(luiId)){
                		if(rel.getTypeKey().equals(LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY)){
                			LuiInfo lui1 = luiService.getLui(rel.getRelatedLuiId(), context);
                			if(lui1 != null && lui1.getTypeKey().equals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY) && !jointOfferingIds.contains(rel.getRelatedLuiId())){
                				jointOfferingIds.add(rel.getRelatedLuiId());
                			}
                		}
                		
                   		if(rel.getTypeKey().equals(LuiServiceConstants.LUI_LUI_RELATION_DELIVEREDVIA_TYPE_KEY)){
                			LuiInfo lui2 = luiService.getLui(rel.getRelatedLuiId(), context);
                			if(lui2 != null && lui2.getTypeKey().equals("kuali.lui.type.course.finalExam") && !finalExams.contains(rel.getRelatedLuiId())){
                				finalExams.add(rel.getRelatedLuiId());
                			}
                		}
                	}
                }
			}
			
			if (!jointOfferingIds.isEmpty()) co.setJointOfferingIds(jointOfferingIds);
				
			if (finalExams.size() > 0) co.setHasFinalExam(true);
			
		} catch (DoesNotExistException e) {
			return;
		} catch (InvalidParameterException e) {
			throw new AssemblyException("InvalidParameterException: " + e.getMessage());
		} catch (MissingParameterException e) {
			throw new AssemblyException("MissingParameterException: " + e.getMessage());
		} catch (OperationFailedException e) {
			throw new AssemblyException("OperationFailedException: " + e.getMessage());
		}
	}
	@Override
	public LuiInfo disassemble(CourseOfferingInfo co, ContextInfo context) {
		if(co != null){			
			LuiInfo lui = new LuiInfo();
			lui.setId(co.getId());
			lui.setTypeKey(co.getTypeKey());
			lui.setStateKey(co.getStateKey());
			lui.setDescr(co.getDescr());
			lui.setMeta(co.getMeta());
			lui.setAttributes(co.getAttributes());
			
			disassembleLuiCodes(co, lui);
			
			//below undecided
			//lui.setHasWaitlist(co.getHasWaitlist());
			//lui.setIsWaitlistCheckinRequired(co.getIsWaitlistCheckinRequired());
			//lui.setWaitlistCheckinFrequency(co.getWaitlistCheckinFrequency());
			//lui.setWaitlistMaximum(co.getWaitlistMaximum());
			//lui.setWaitlistTypeKey(co.getWaitlistTypeKey());
			
			lui.setCluId(co.getCourseId());
			lui.setCluCluRelationIds(co.getFormatIds());
			lui.setAtpId(co.getTermId());
			lui.setUnitsContentOwner(co.getUnitsContentOwner());
			lui.setUnitsDeployment(co.getUnitsDeployment());
			lui.setMaximumEnrollment(co.getMaximumEnrollment());
			lui.setMinimumEnrollment(co.getMinimumEnrollment());
		    lui.setResultValuesGroupKeys(co.getGradingOptionKeys());

			lui.setFees(co.getFees());
			lui.setExpenditure(co.getExpenditure());
			lui.setRevenues(co.getRevenues());
	        
			disassembleIdentifier(co, lui);
					
			//TODO: the following mapping undecided on wiki
			//gradeRosterLevelTypeKey
			//fundingSource
			//isFinancialAidEligible
			//registrationOrderTypeKey
						
			return lui;
		}
		else
			return null;
	}

	private void disassembleLuiCodes(CourseOfferingInfo co, LuiInfo lui){
		lui.setLuiCodes(new ArrayList<LuCodeInfo>());

        Boolean isHonorsOffering = co.getIsHonorsOffering();

        //TODO needs review: when creating a new CO from a CLU the honorsOffering is never set so we get
        //                  NPEs when getting value.  Should we be setting the value to false on null?
        if (isHonorsOffering != null) {
            LuCodeInfo code = new LuCodeInfo();
            code.setTypeKey("kuali.lu.code.honorsOffering");
            code.setValue(co.getIsHonorsOffering().toString());
            code.setAttributes(new ArrayList<AttributeInfo>());
            lui.getLuiCodes().add(code);
        }
	}
	
	private void disassembleIdentifier(CourseOfferingInfo co, LuiInfo lui){
		LuiIdentifierInfo identifier = new LuiIdentifierInfo();
		identifier.setCode(co.getCourseOfferingCode());
		identifier.setSuffixCode(co.getCourseNumberSuffix());
		identifier.setLongName(co.getCourseTitle());
		identifier.setDivision(co.getSubjectArea());
		lui.setOfficialIdentifier(identifier);		
	}
	
	private void assembleIdentifier(LuiInfo lui, CourseOfferingInfo co){
		LuiIdentifierInfo identifier = lui.getOfficialIdentifier();
		if(identifier != null){
			co.setCourseOfferingCode(identifier.getCode());
			co.setCourseNumberSuffix(identifier.getSuffixCode());
			co.setCourseTitle(identifier.getLongName());
			co.setSubjectArea(identifier.getDivision());
		}
	}
	
	public CourseOfferingInfo assemble(CourseInfo courseInfo){
		CourseOfferingInfo courseOfferingInfo = new CourseOfferingInfo();
		courseOfferingInfo.setCourseId(courseInfo.getId());
		courseOfferingInfo.setCourseNumberSuffix(courseInfo.getCourseNumberSuffix());
		courseOfferingInfo.setCourseTitle(courseInfo.getCourseTitle());
		courseOfferingInfo.setSubjectArea(courseInfo.getSubjectArea());
		courseOfferingInfo.setCourseOfferingCode(courseInfo.getCode());
		courseOfferingInfo.setUnitsContentOwner(courseInfo.getUnitsContentOwner());
		courseOfferingInfo.setUnitsDeployment(courseInfo.getUnitsDeployment());
		courseOfferingInfo.setGradingOptionKeys(courseInfo.getGradingOptions());
		if (courseInfo.getCreditOptions() == null) {
		    courseOfferingInfo.setCreditOptions(null);
		} else if (courseInfo.getCreditOptions().isEmpty()) {
		    courseOfferingInfo.setCreditOptions(null);
		} else {
		    courseOfferingInfo.setCreditOptions(new R1ToR2CopyHelper().copyResultValuesGroup(courseInfo.getCreditOptions().get(0)));
		}
		courseOfferingInfo.setDescr(new R1ToR2CopyHelper().copyRichText(courseInfo.getDescr()));
		courseOfferingInfo.setExpenditure(new R1ToR2CopyHelper().copyCourseExpenditure(courseInfo.getExpenditure()));
		courseOfferingInfo.setFees(new R1ToR2CopyHelper().copyCourseFeeList(courseInfo.getFees()));
		courseOfferingInfo.setInstructors(new R1ToR2CopyHelper().copyInstructors(courseInfo.getInstructors()));		
		
		return courseOfferingInfo;
	}

}
