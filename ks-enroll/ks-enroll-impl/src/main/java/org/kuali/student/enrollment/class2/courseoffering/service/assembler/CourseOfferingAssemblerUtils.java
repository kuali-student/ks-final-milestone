package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import org.kuali.student.enrollment.courseoffering.infc.COBasicAssembly;
import org.kuali.student.enrollment.courseoffering.infc.COCommonAssembly;
import org.kuali.student.enrollment.lui.dto.LuiInfo;


public class CourseOfferingAssemblerUtils {
	public COBasicAssembly assembleBasics(LuiInfo lui, COBasicAssembly co){
		co.setId(lui.getId());
		co.setMeta(lui.getMeta());
		co.setStateKey(lui.getStateKey());
		co.setTypeKey(lui.getTypeKey());
		co.setAttributes(lui.getAttributes());
		return co;
	}
	
	public LuiInfo disassembleBasics(LuiInfo lui, COBasicAssembly co){
		lui.setId(co.getId());
		lui.setTypeKey(co.getTypeKey());
		lui.setStateKey(co.getStateKey());
		lui.setDescr(co.getDescr());
		lui.setMeta(co.getMeta());
		lui.setAttributes(co.getAttributes());

		return lui;
	}
	
	public COCommonAssembly assembleCommons(LuiInfo lui, COCommonAssembly co){
		co.setMaximumEnrollment(lui.getMaximumEnrollment());
		co.setMinimumEnrollment(lui.getMinimumEnrollment());

		//TODO: co.setIsHonorsOffering(isHonorsOffering) -- lui.getLuiCodes() ?
		
		//below undecided
		//co.setHasWaitlist(lui.getHasWaitlist());
		//co.setWaitlistTypeKey(lui.getWaitlistTypeKey());
		//co.setWaitlistMaximum(lui.getWaitlistMaximum());
		//co.setIsWaitlistCheckinRequired(lui.getIsWaitlistCheckinRequired());
		//co.setWaitlistCheckinFrequency(lui.getWaitlistCheckinFrequency());
		return co;
	}
	
	public LuiInfo disassembleCommons(LuiInfo lui, COCommonAssembly co){
		lui.setMaximumEnrollment(co.getMaximumEnrollment());
		lui.setMinimumEnrollment(co.getMinimumEnrollment());
		
		//TODO: co.getIsHonorsOffering() --store in a generic lui luCodes type of field?
		
		//below undecided
		//lui.setHasWaitlist(co.getHasWaitlist());
		//lui.setIsWaitlistCheckinRequired(co.getIsWaitlistCheckinRequired());
		//lui.setWaitlistCheckinFrequency(co.getWaitlistCheckinFrequency());
		//lui.setWaitlistMaximum(co.getWaitlistMaximum());
		//lui.setWaitlistTypeKey(co.getWaitlistTypeKey());
		
		return lui;
	}
	
}
