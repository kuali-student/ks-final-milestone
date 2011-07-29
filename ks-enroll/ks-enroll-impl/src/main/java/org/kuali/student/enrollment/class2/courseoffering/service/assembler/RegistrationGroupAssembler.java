package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.infc.DTOAssembler;

public class RegistrationGroupAssembler implements DTOAssembler<RegistrationGroupInfo, LuiInfo>{
	private LuiService luiService;

	public LuiService getLuiService() {
		return luiService;
	}

	public void setLuiService(LuiService luiService) {
		this.luiService = luiService;
	}
	
	@Override
	public RegistrationGroupInfo assemble(LuiInfo lui, ContextInfo context) {
		if(lui != null){
			RegistrationGroupInfo rg = new RegistrationGroupInfo();
			rg.setId(lui.getId());
			rg.setMeta(lui.getMeta());
			rg.setStateKey(lui.getStateKey());
			rg.setTypeKey(lui.getTypeKey());
			rg.setAttributes(lui.getAttributes());					
			rg.setMaximumEnrollment(lui.getMaximumEnrollment());
			rg.setMinimumEnrollment(lui.getMinimumEnrollment());

			//TODO: co.setIsHonorsOffering(isHonorsOffering) -- lui.getLuiCodes() ?
			
			//below undecided
			//co.setHasWaitlist(lui.getHasWaitlist());
			//co.setWaitlistTypeKey(lui.getWaitlistTypeKey());
			//co.setWaitlistMaximum(lui.getWaitlistMaximum());
			//co.setIsWaitlistCheckinRequired(lui.getIsWaitlistCheckinRequired());
			//co.setWaitlistCheckinFrequency(lui.getWaitlistCheckinFrequency());
			
			//LuiLuiRelation (to set courseOfferingId, activityOfferingIds)
			// assembleLuiLuiRelations(rg, lui.getId(), context);

			return rg;
		}
		else
			return null;
	}

	@Override
	public LuiInfo disassemble(RegistrationGroupInfo rg, ContextInfo context) {
		if(rg != null){			
			LuiInfo lui = new LuiInfo();
			lui.setId(rg.getId());
			lui.setTypeKey(rg.getTypeKey());
			lui.setStateKey(rg.getStateKey());
			lui.setDescr(rg.getDescr());
			lui.setMeta(rg.getMeta());
			lui.setAttributes(rg.getAttributes());
			
			//TODO: co.getIsHonorsOffering() --store in a generic lui luCodes type of field?
			
			//below undecided
			//lui.setHasWaitlist(rg.getHasWaitlist());
			//lui.setIsWaitlistCheckinRequired(rg.getIsWaitlistCheckinRequired());
			//lui.setWaitlistCheckinFrequency(rg.getWaitlistCheckinFrequency());
			//lui.setWaitlistMaximum(rg.getWaitlistMaximum());
			//lui.setWaitlistTypeKey(rg.getWaitlistTypeKey());
			return lui;
		}
		return null;
	}

}
