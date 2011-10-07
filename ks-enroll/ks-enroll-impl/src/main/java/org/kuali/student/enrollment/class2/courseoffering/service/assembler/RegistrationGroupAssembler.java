package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

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
			rg.setDescr(lui.getDescr());
			rg.setAttributes(lui.getAttributes());					
			rg.setMaximumEnrollment(lui.getMaximumEnrollment());
			rg.setMinimumEnrollment(lui.getMinimumEnrollment());
			rg.setName(lui.getName());
			rg.setFormatId(lui.getCluId());
			rg.setTermKey(lui.getAtpKey());
			//TODO: co.setIsHonorsOffering(isHonorsOffering) -- lui.getLuiCodes() ?
			
			//below undecided
			//co.setHasWaitlist(lui.getHasWaitlist());
			//co.setWaitlistTypeKey(lui.getWaitlistTypeKey());
			//co.setWaitlistMaximum(lui.getWaitlistMaximum());
			//co.setIsWaitlistCheckinRequired(lui.getIsWaitlistCheckinRequired());
			//co.setWaitlistCheckinFrequency(lui.getWaitlistCheckinFrequency());
			
			//LuiLuiRelation (to set courseOfferingId, activityOfferingIds)
			 assembleLuiLuiRelations(rg, lui.getId(), context);

			return rg;
		}
		else
			return null;
	}

	private void assembleLuiLuiRelations(RegistrationGroupInfo rg, String luiId, ContextInfo context){
		try {
			String courseOfferingId = null;;
			List<String> activityIds= new ArrayList<String>();
			List<LuiLuiRelationInfo> rels = luiService.getLuiLuiRelationsByLui(luiId, context);
			if(rels != null && !rels.isEmpty()){                  
                for(LuiLuiRelationInfo rel : rels){
                	if(rel.getLuiId().equals(luiId)){
                		if(rel.getTypeKey().equals(LuiServiceConstants.LUI_LUI_RELATION_REGISTEREDFORVIA_TYPE_KEY)){
                			LuiInfo lui = luiService.getLui(rel.getRelatedLuiId(), context);
                			if(lui != null){
	                			if( lui.getTypeKey().equals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY)){
	                				courseOfferingId = rel.getRelatedLuiId();
	                			}
	                			else
	                			{
		                			if( !activityIds.contains(rel.getRelatedLuiId())){
		                				activityIds.add(rel.getRelatedLuiId());
		                			}
	                			}
                			}
                		}
                	}
                }
			}
			
			if ( null != courseOfferingId ) rg.setCourseOfferingId(courseOfferingId);
			if (!activityIds.isEmpty()) rg.setActivityOfferingIds(activityIds);
			
		} catch (DoesNotExistException e) {
			return;
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		} catch (MissingParameterException e) {
			e.printStackTrace();
		} catch (OperationFailedException e) {
			e.printStackTrace();
		}
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
			lui.setName(rg.getName());
			lui.setCluId(rg.getFormatId());
			lui.setMaximumEnrollment(rg.getMaximumEnrollment());
			lui.setMinimumEnrollment(rg.getMinimumEnrollment());
			lui.setAtpKey(rg.getTermKey());
			
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
