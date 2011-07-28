package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.infc.DTOAssembler;

public class ActivityOfferingAssembler implements DTOAssembler<ActivityOfferingInfo, LuiInfo>{
	private CourseOfferingAssemblerUtils coAssemblerUtils;
	
	public CourseOfferingAssemblerUtils getCoAssemblerUtils() {
		return coAssemblerUtils;
	}

	public void setCoAssemblerUtils(CourseOfferingAssemblerUtils coAssemblerUtils) {
		this.coAssemblerUtils = coAssemblerUtils;
	}
	
	@Override
	public ActivityOfferingInfo assemble(LuiInfo lui, ContextInfo context) {
		if(lui != null){
			ActivityOfferingInfo ao = new ActivityOfferingInfo();
			coAssemblerUtils.assembleBasics(lui, ao);
			
			return ao;
		}
		else
			return null;
	}

	@Override
	public LuiInfo disassemble(ActivityOfferingInfo ao, ContextInfo context) {
		if(ao != null){
			LuiInfo lui = new LuiInfo();
			coAssemblerUtils.disassembleBasics(lui, ao);
			return lui;
		}
		else
			return null;
	}

}
