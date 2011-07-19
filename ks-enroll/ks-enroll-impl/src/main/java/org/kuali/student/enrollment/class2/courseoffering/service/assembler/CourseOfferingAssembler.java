package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.infc.DTOAssembler;

public class CourseOfferingAssembler implements DTOAssembler<CourseOfferingInfo, LuiInfo>{

	@Override
	public CourseOfferingInfo assemble(LuiInfo lui, ContextInfo context) {
		if(lui != null){
			CourseOfferingInfo co = new CourseOfferingInfo();
			co.setId(lui.getId());
			co.setTypeKey(lui.getTypeKey());
			co.setStateKey(lui.getStateKey());
			co.setDescr(lui.getDescr());
			co.setCourseId(lui.getCluId());
			co.setTermKey(lui.getAtpKey());
			co.setCourseCode(lui.getLuiCode());
			//co.setCourseNumberSuffix(courseNumberSuffix) //TODO: where to map?
			co.setCourseTitle(lui.getStudyTitle());
			//co.setIsHonorsOffering(isHonorsOffering)     //TODO: where to map?
			co.setInstructors(lui.getInstructors());
			co.setSubjectArea(lui.getStudySubjectArea());
			co.setUnitsDeployment(lui.getUnitsDeployment());
			co.setUnitsContentOwner(lui.getUnitsContentOwner());
			//co.setFinalExamStatus(finalExamStatus);     //TODO: where to map?
			co.setMaximumEnrollment(lui.getMaximumEnrollment());
			co.setMinimumEnrollment(lui.getMinimumEnrollment());
			//co.setCreditOptions(creditOptions)         //TODO: where to map?
			co.setGradingOptions(lui.getGradingOptions());
			//co.setGradeRosterLevel(gradeRosterLevel);  //TODO: where to map?
			co.setHasWaitlist(lui.getHasWaitlist());
			co.setWaitlistTypeKey(lui.getWaitlistTypeKey());
			co.setWaitlistMaximum(lui.getWaitlistMaximum());
			co.setIsWaitlistCheckinRequired(lui.getIsWaitlistCheckinRequired());
			co.setWaitlistCheckinFrequency(lui.getWaitlistCheckinFrequency());
			//co.setFundingSource(fundingSource);		//TODO: where to map?
			co.setFees(lui.getFees());
			co.setRevenues(lui.getRevenues());
			co.setExpenditure(lui.getExpenditure());
			//co.setIsFinancialAidEligible(isFinancialAidEligible);		//TODO: where to map?
			//co.setRegistrationOrderTypeKey(registrationOrderTypeKey);		//TODO: where to map?
			co.setMeta(lui.getMeta());
			co.setAttributes(lui.getAttributes());
			
			//LuiLuiRelation (formatIds, activityOfferingIds, registrationGroupIds and jointOfferingIds)
			
			return co;
		}
		else
			return null;
	}

	@Override
	public LuiInfo disassemble(CourseOfferingInfo co, ContextInfo context) {
		if(co != null){
			LuiInfo lui = new LuiInfo();
			lui.setId(co.getId());
			lui.setTypeKey(co.getTypeKey());
			lui.setStateKey(co.getStateKey());
			//lui.setName(name);		//TODO: where to map?
			lui.setDescr(co.getDescr());
			lui.setLuiCode(co.getCourseCode());
			lui.setCluId(co.getCourseId());
			lui.setAtpKey(co.getTermKey());
			lui.setInstructors(co.getInstructors());
			lui.setStudySubjectArea(co.getSubjectArea());
			lui.setMaximumEnrollment(co.getMaximumEnrollment());
			lui.setMinimumEnrollment(co.getMinimumEnrollment());
			//lui.setEffectiveDate(effectiveDate)		//TODO: where to map?
			//lui.setExpirationDate(expirationDate)		//TODO: where to map?
			lui.setFees(co.getFees());
			lui.setGradingOptions(co.getGradingOptions());
			lui.setStudyTitle(co.getCourseTitle());
			lui.setUnitsContentOwner(co.getUnitsContentOwner());
			lui.setUnitsDeployment(co.getUnitsDeployment());
			lui.setExpenditure(co.getExpenditure());
			lui.setRevenues(co.getRevenues());
			lui.setHasWaitlist(co.getHasWaitlist());
			lui.setIsWaitlistCheckinRequired(co.getIsWaitlistCheckinRequired());
			lui.setWaitlistCheckinFrequency(co.getWaitlistCheckinFrequency());
			lui.setWaitlistMaximum(co.getWaitlistMaximum());
			lui.setWaitlistTypeKey(co.getWaitlistTypeKey());
			lui.setMeta(co.getMeta());
			lui.setAttributes(co.getAttributes());
			
			return lui;
		}
		else
			return null;
	}

}
