package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

public class ActivityOfferingAssembler implements DTOAssembler<ActivityOfferingInfo, LuiInfo>{
	private LuiService luiService;
	
	public LuiService getLuiService() {
		return luiService;
	}

	public void setLuiService(LuiService luiService) {
		this.luiService = luiService;
	}
	
	@Override
	public ActivityOfferingInfo assemble(LuiInfo lui, ContextInfo context) {
		if(lui != null){
			ActivityOfferingInfo ao = new ActivityOfferingInfo();
			ao.setId(lui.getId());
			ao.setMeta(lui.getMeta());
			ao.setStateKey(lui.getStateKey());
			ao.setTypeKey(lui.getTypeKey());
			ao.setDescr(lui.getDescr());
			ao.setAttributes(lui.getAttributes());
			ao.setActivityId(lui.getCluId());
			ao.setTermKey(lui.getAtpKey());
						
			//TODO: ao.setGradingOptionIds --- lui.getResultOptionIds() call LRCService.getResultValuesByIdList
			//TODO: ao.setInstructors(instructors) -- call LPRService.findPersonIdsRelatedToLui?
			
			//rest fields no mapping doc
			
			return ao;
		}
		else
			return null;
	}
	
	@Override
	public LuiInfo disassemble(ActivityOfferingInfo ao, ContextInfo context) {
		if(ao != null){
			LuiInfo lui = new LuiInfo();
			lui.setId(ao.getId());
			lui.setTypeKey(ao.getTypeKey());
			lui.setStateKey(ao.getStateKey());
			lui.setDescr(ao.getDescr());
			lui.setMeta(ao.getMeta());
			lui.setAttributes(ao.getAttributes());
			lui.setCluId(ao.getActivityId());
			lui.setAtpKey(ao.getTermKey());
			
			//rest fields no mapping doc
			return lui;
		}
		else
			return null;
	}

}
