package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.infc.DTOAssembler;

public class ActivityOfferingAssembler implements DTOAssembler<ActivityOfferingInfo, LuiInfo>{
	private LuiService luiService;
	private CourseOfferingAssemblerUtils coAssemblerUtils;
	
	public LuiService getLuiService() {
		return luiService;
	}

	public void setLuiService(LuiService luiService) {
		this.luiService = luiService;
	}
	
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
			ao.setActivityId(lui.getCluId());
			ao.setTermKey(lui.getAtpKey());
						
			//TODO: ao.setGradingOptionIds --- lui.getResultOptionIds() call LRCService.getResultValuesByIdList
			//TODO: ao.setInstructors(instructors) -- call LPRService.findPersonIdsRelatedToLui?
			
			//rest fields no mapping doc
			
			//LuiLuiRelation (to set courseOfferingIds, registrationGroupIds)
			 assembleLuiLuiRelations(ao, lui.getId(), context);
			return ao;
		}
		else
			return null;
	}

	private void assembleLuiLuiRelations(ActivityOfferingInfo ao, String luiId, ContextInfo context){
		try {
			List<String> courseOfferingIds = new ArrayList<String>();
			List<String> registrationGroupIds = new ArrayList<String>();
			List<LuiLuiRelationInfo> rels = luiService.getLuiLuiRelationsByLui(luiId, context);
			if(rels != null && !rels.isEmpty()){                  
                for(LuiLuiRelationInfo rel : rels){
                	if(rel.getLuiId().equals(luiId)){
                		if(rel.getTypeKey().equals("kuali.lui.lui.relation.RegisteredForVia")){
                			LuiInfo lui1 = luiService.getLui(rel.getRelatedLuiId(), context);
                			if(lui1 != null && lui1.getTypeKey().equals("kuali.lui.type.course.registrationGroup") && !courseOfferingIds.contains(rel.getRelatedLuiId())){
                				courseOfferingIds.add(rel.getRelatedLuiId());
                			}
                		}
                		
                   		if(rel.getTypeKey().equals("kuali.lui.lui.relation.IsDeliveredVia")){
                			LuiInfo lui2 = luiService.getLui(rel.getRelatedLuiId(), context);
                			if(lui2 != null && !lui2.getTypeKey().equals("kuali.lui.type.course.finalExam") && !registrationGroupIds.contains(rel.getRelatedLuiId())){
                				registrationGroupIds.add(rel.getRelatedLuiId());
                			}
                		}
                	}
                }
			}
			
			if (!courseOfferingIds.isEmpty()) ao.setCourseOfferingIds(courseOfferingIds);
			if (!registrationGroupIds.isEmpty()) ao.setRegistrationGroupIds(registrationGroupIds);
			
		} catch (DoesNotExistException e) {
			e.printStackTrace();
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		} catch (MissingParameterException e) {
			e.printStackTrace();
		} catch (OperationFailedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public LuiInfo disassemble(ActivityOfferingInfo ao, ContextInfo context) {
		if(ao != null){
			LuiInfo lui = new LuiInfo();
			coAssemblerUtils.disassembleBasics(lui, ao);
			lui.setCluId(ao.getActivityId());
			lui.setAtpKey(ao.getTermKey());
			
			//rest fields no mapping doc
			return lui;
		}
		else
			return null;
	}

}
