package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;

import java.util.ArrayList;
import java.util.List;

public class ActivityOfferingAssembler implements DTOAssembler<ActivityOfferingInfo, LuiInfo>{
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
            ao.setMeetingSchedules(lui.getMeetingSchedules());
			
			//instructors
			assembleInstructors(ao, lui.getId(), context);
			
			//TODO: ao.setGradingOptionIds --- lui.getResultOptionIds() call LRCService.getResultValuesByIdList
			
			//rest fields no mapping doc
			
			return ao;
		}
		else
			return null;
	}
	
	private void assembleInstructors(ActivityOfferingInfo ao, String luiId, ContextInfo context){
		List<LuiPersonRelationInfo> lprs = null;;
		try {
			lprs = lprService.getLprsByLui(luiId, context);
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
			ao.setInstructors(instructors);
		}		
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
            lui.setMeetingSchedules(ao.getMeetingSchedules());
			
			//rest fields no mapping doc
			return lui;
		}
		else
			return null;
	}

}
