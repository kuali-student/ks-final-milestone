package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.service.assembler.ActivityOfferingAssembler;
import org.kuali.student.enrollment.class2.courseoffering.service.assembler.CourseOfferingAssembler;
import org.kuali.student.enrollment.class2.courseoffering.service.assembler.RegistrationGroupAssembler;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class CourseOfferingServiceImpl implements CourseOfferingService{
	private LuiService luiService;
	private CourseService courseService;
	private AcademicCalendarService acalService;
	private CourseOfferingAssembler coAssembler;
	private ActivityOfferingAssembler aoAssembler;
	private RegistrationGroupAssembler rgAssembler;
	private StateService stateService;
	
	public LuiService getLuiService() {
		return luiService;
	}

	public void setLuiService(LuiService luiService) {
		this.luiService = luiService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public AcademicCalendarService getAcalService() {
		return acalService;
	}

	public void setAcalService(AcademicCalendarService acalService) {
		this.acalService = acalService;
	}

	public CourseOfferingAssembler getCoAssembler() {
		return coAssembler;
	}

	public void setCoAssembler(CourseOfferingAssembler coAssembler) {
		this.coAssembler = coAssembler;
	}

	public ActivityOfferingAssembler getAoAssembler() {
		return aoAssembler;
	}

	public void setAoAssembler(ActivityOfferingAssembler aoAssembler) {
		this.aoAssembler = aoAssembler;
	}

	public RegistrationGroupAssembler getRgAssembler() {
		return rgAssembler;
	}

	public void setRgAssembler(RegistrationGroupAssembler rgAssembler) {
		this.rgAssembler = rgAssembler;
	}

	public StateService getStateService() {
		return stateService;
	}

	public void setStateService(StateService stateService) {
		this.stateService = stateService;
	}

	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
			throws OperationFailedException, MissingParameterException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
			ContextInfo context) throws OperationFailedException,
			MissingParameterException, PermissionDeniedException,
			DoesNotExistException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CourseOfferingInfo getCourseOffering(String courseOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		LuiInfo lui = luiService.getLui(courseOfferingId, context);		
		return coAssembler.assemble(lui, context);
	}

	@Override
	public List<CourseOfferingInfo> getCourseOfferingsForCourseAndTerm(
			String courseId, String termKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCourseOfferingIdsForTerm(String termKey,
			Boolean useIncludedTerm, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    /**
     * HACK HACK HACK
     *
     * This implementation is a HACK!!!  Please think of the poor wasted CPU cycles and use a more specific lui service method
     * when one is made.
     *
     * This implementation is terrible inefficient, since it looks up ALL courseOffering and then filters the matching ones out.
     *
     * HACK HACK HACK
     */
	public List<String> getCourseOfferingIdsByTermAndSubjectArea(String termKey,
			String subjectArea, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO UNHACK THIS HACK!!

        TermInfo term = acalService.getTerm(termKey, context);

        List<String> luiIds = luiService.getLuiIdsByType(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, context);

        List<String> results = new ArrayList<String>();

        for(String luiId : luiIds) {
            CourseOfferingInfo co = getCourseOffering(luiId, context);

            if(StringUtils.equals(co.getSubjectArea(), subjectArea) && StringUtils.equals(co.getTermKey(), term.getKey())) {
                results.add(co.getId());
            }
        }

		return results;
	}

	@Override
	public List<String> getCourseOfferingIdsByTermAndUnitContentOwner(String termKey,
			String unitOwnerId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public CourseOfferingInfo createCourseOfferingFromCanonical(
			String courseId, String termKey, List<String> formatIdList,
			ContextInfo context) throws AlreadyExistsException,
			DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        CourseOfferingInfo courseOfferingInfo = null;

        CourseInfo courseInfo = getCourse(courseId);
        if (courseInfo != null){
        	courseOfferingInfo = coAssembler.assemble(courseInfo);
        	courseOfferingInfo.setCourseId(courseId);
        }
        else
        	throw new DoesNotExistException("The course does not exist. course: " + courseId);
        
        //TODO: uncomment when make the following insert in ks-lui.sql working 
        //INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('testTermId1', 'testTerm1', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'kuali.atp.type.Fall', 'kuali.atp.state.Draft', 'RICHTEXT-201', 0)
//        if(acalService.getTerm(termKey, context) != null)
//        	courseOfferingInfo.setTermKey(termKey);
//        else
//        	throw new DoesNotExistException("The term does not exist. term: " + termKey);
        courseOfferingInfo.setTermKey(termKey);
        
        if (checkExistenceForFormats(formatIdList, context))
        	courseOfferingInfo.setFormatIds(formatIdList);
        
        courseOfferingInfo.setStateKey(getStateKey(LuiServiceConstants.COURSE_OFFERING_PROCESS_KEY, LuiServiceConstants.LUI_DRAFT_STATE_KEY, context));
        courseOfferingInfo.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        
        LuiInfo luiInfo = coAssembler.disassemble(courseOfferingInfo, context);
        LuiInfo created = luiService.createLui(courseId, termKey, luiInfo, context);
        
        if (created != null)
        	courseOfferingInfo.setId(created.getId());
		return courseOfferingInfo;
	}

	private CourseInfo getCourse(String courseId)throws DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException{
		CourseInfo course = null;
		try {
			course = courseService.getCourse(courseId);
		} catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
			throw new DoesNotExistException("The course does not exist. course: " + courseId);
		} catch (org.kuali.student.common.exceptions.InvalidParameterException e) {
			throw new InvalidParameterException("The course has invalid parameter. course: " + courseId);
		} catch (org.kuali.student.common.exceptions.MissingParameterException e) {
			throw new MissingParameterException("The course is missing parameter. course: " + courseId);
		} catch (org.kuali.student.common.exceptions.OperationFailedException e) {
			throw new OperationFailedException("Operation failed when getting course: " + courseId);
		} catch (org.kuali.student.common.exceptions.PermissionDeniedException e) {
			throw new PermissionDeniedException("Permission denied when getting course: " + courseId);
		}
		
		return course;
	}
	
	//TODO:call LuService 
	private boolean checkExistenceForFormats(List<String> formatIds, ContextInfo context){
		if(formatIds != null && !formatIds.isEmpty()){
			for(String formatId : formatIds){
				//luService.getClu(formatId, context);
			}
		}

    	return true;
    }
	
	private String getStateKey(String processKey, String defaultState, ContextInfo context) throws DoesNotExistException, InvalidParameterException, 
			MissingParameterException, OperationFailedException{
        String stateKey = null;
        List<StateInfo> ivStates = stateService.getInitialValidStates(processKey, context);
        if(ivStates != null && ivStates.size() > 0)
        	stateKey = ivStates.get(0).getKey();
        else
        	stateKey = defaultState;	
        
        return stateKey;
	}
	
	@Override
	@Transactional
	public CourseOfferingInfo updateCourseOffering(String courseOfferingId,
			CourseOfferingInfo courseOfferingInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
        try{
        	LuiInfo existing = luiService.getLui(courseOfferingId, context);
        	if(existing != null){
        		LuiInfo lui = coAssembler.disassemble(courseOfferingInfo, context);
	
	            if(lui != null){
	            	LuiInfo updated = luiService.updateLui(courseOfferingId, lui, context);
	            	if (updated != null)
	            		processRelationsForCourseOffering(courseOfferingInfo, context);
	            }
        	}
        	else
        		throw new DoesNotExistException("The CourseOffering does not exist: " + courseOfferingId);
        } catch (DoesNotExistException e1) {
            throw new DoesNotExistException("The CourseOffering does not exist: " + courseOfferingId);
        }
        
        return courseOfferingInfo;
	}
	
	private void processRelationsForCourseOffering(CourseOfferingInfo co, ContextInfo context) throws DataValidationErrorException, 
			DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		//TODO: co.getInstructors() -- wire up with LuiPersonRelationService
		
		//TODO: hasFinalExam -- ignore for core slice
		//how to determine that the lui already exist?
		if(co.getHasFinalExam()) processFinalExam(co, context);
			
		//TODO:jointOfferingIds -- ignore for core slice

		//TODO: creditOptions -- ignore for core slice
		
		//TODO: gradingOptionIds -- ignore for core slice
	}

	private void processFinalExam(CourseOfferingInfo co, ContextInfo context) throws DataValidationErrorException, 
	DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		String cluId = co.getCourseId();
		String atpKey = co.getTermKey();
		LuiInfo finalExam = new LuiInfo();
		finalExam.setCluId(cluId);
		finalExam.setAtpKey(atpKey);
		finalExam.setStateKey(co.getStateKey());
		//TODO: not sure what type
		finalExam.setTypeKey("kuali.lui.type.course.finalExam");
		//TODO: what else inherit or fill into finalExam?
		LuiInfo created;
		try {
			created = luiService.createLui(cluId, atpKey, finalExam, context);
		} catch (AlreadyExistsException e1) {
			throw new OperationFailedException("AlreadyExistsException when createLui. cluId: " + cluId + ", atpKey: " + atpKey);
		}
		
		if(created != null){
			try {
				createLuiLuiRelation(co.getId(), created.getId(), "kuali.lui.lui.relation.IsDeliveredVia", context);
			} catch (AlreadyExistsException e1) {
				throw new OperationFailedException();
			}
		}
	}
	
	@Override
	@Transactional
	public CourseOfferingInfo updateCourseOfferingFromCanonical(
			String courseOfferingId, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public StatusInfo deleteCourseOffering(String courseOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatementTreeViewInfo> getCourseOfferingRestrictions(
			String courseOfferingId, String nlUsageTypeKey, String language,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatementTreeViewInfo createCourseOfferingRestriction(
			String courseOfferingId, StatementTreeViewInfo restrictionInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DataValidationErrorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatementTreeViewInfo updateCourseOfferingRestriction(
			String courseOfferingId, StatementTreeViewInfo restrictionInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DataValidationErrorException, CircularReferenceException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteCourseOfferingRestriction(String courseOfferingId,
			String restrictionId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeInfo getActivityOfferingType(String activityOfferingTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getAllActivityOfferingTypes(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getActivityOfferingTypesForActivityType(
			String activityTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
//        TypeInfo activityType = luiService.getType(activityTypeKey, context);    
        
    	return luiService.getAllowedTypesForType(activityTypeKey, LuiServiceConstants.REF_OBJECT_URI_LUI, context);
	}

	@Override
	public ActivityOfferingInfo getActivityOffering(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		LuiInfo lui = luiService.getLui(activityOfferingId, context);		
		return aoAssembler.assemble(lui, context);
	}

	@Override
	public List<ActivityOfferingInfo> getActivitiesForCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getActivitiesForRelation(courseOfferingId, "kuali.lui.lui.relation.IsDeliveredVia", "kuali.lui.type.course.finalExam", context);
	}


	private List<ActivityOfferingInfo> getActivitiesForRelation(String relatedLuiId, String relType, String exludedLuiType, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException{
		//TODO: implement LuiService.getLuiIdsByRelation and call it instead
		List<ActivityOfferingInfo> aos = new ArrayList<ActivityOfferingInfo>();
		List<String> aoIds = new ArrayList<String>();
		List<LuiLuiRelationInfo> rels = luiService.getLuiLuiRelationsByLui(relatedLuiId, context);
		if(rels != null && !rels.isEmpty()){                  
            for(LuiLuiRelationInfo rel : rels){
            	if(rel.getRelatedLuiId().equals(relatedLuiId)){
            		if(rel.getTypeKey().equals(relType)){
            			String luiId = rel.getLuiId();
            			LuiInfo lui = luiService.getLui(luiId, context);
            			if(lui != null && !lui.getTypeKey().equals(exludedLuiType) && !aoIds.contains(luiId)){
            				aoIds.add(luiId);
            				aos.add(getActivityOffering(luiId, context));
            			}
            		}
            	}
            }
		}
		
		return aos;		
	}
	
	@Override
	@Transactional
	public ActivityOfferingInfo createActivityOffering(
			List<String> courseOfferingIdList,
			ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
       
		if(courseOfferingIdList != null && !courseOfferingIdList.isEmpty()){
			LuiInfo lui = aoAssembler.disassemble(activityOfferingInfo, context);
			try {
				LuiInfo created = luiService.createLui(activityOfferingInfo.getActivityId(), activityOfferingInfo.getTermKey(), lui, context);
				
				if(created != null){
					activityOfferingInfo.setId(created.getId());
					
					processRelationsForActivityOffering(courseOfferingIdList, activityOfferingInfo, context);
				}
				
				return activityOfferingInfo;
			} catch (DoesNotExistException e) {
				throw new OperationFailedException();
			}
		}
		else
			return null;
	}
	
	private void processRelationsForActivityOffering(List<String> courseOfferingIdList, ActivityOfferingInfo activityOfferingInfo, ContextInfo context)  
			throws AlreadyExistsException,DataValidationErrorException, InvalidParameterException, MissingParameterException, 
			OperationFailedException, PermissionDeniedException{
		
		processLuiluiRelationsForActivityOffering(courseOfferingIdList, activityOfferingInfo, context);
		
		//TODO: ao.getInstructors() -- wire up with LuiPersonRelationService
		
		//TODO: ao.setGradingOptionIds -- ignore for core slice
}

	
	private void processLuiluiRelationsForActivityOffering(List<String> courseOfferingIdList,
			ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{

		for (String courseOfferingId : courseOfferingIdList) {
			createLuiLuiRelation(activityOfferingInfo.getId(), courseOfferingId, "kuali.lui.lui.relation.type.deliveredvia", context);
		}
			
	}
	
	private void createLuiLuiRelation(String luiId, String relatedLuiId, String luLuRelationTypeKey, ContextInfo context) throws AlreadyExistsException, 
			DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		try {
			luiService.createLuiLuiRelation(luiId, relatedLuiId, luLuRelationTypeKey, 
					initLuiLuiRelationInfo(luiId, relatedLuiId, luLuRelationTypeKey, context), context);
		} catch (CircularRelationshipException e) {
			throw new OperationFailedException();
		} catch (DoesNotExistException e) {
			throw new OperationFailedException();
		}
	}
	private LuiLuiRelationInfo initLuiLuiRelationInfo(String luiId, String relatedLuiId, String typeKey, ContextInfo context) throws InvalidParameterException, 
			MissingParameterException, OperationFailedException{
		LuiLuiRelationInfo luiRel = new LuiLuiRelationInfo();
		luiRel.setLuiId(luiId);
		luiRel.setRelatedLuiId(relatedLuiId);
		luiRel.setTypeKey(typeKey);
		try {
			luiRel.setStateKey(getStateKey(LuiServiceConstants.LUI_LUI_RELATION_PROCESS_KEY, LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, context));
		} catch (DoesNotExistException e) {
			throw new OperationFailedException();
		}
		return luiRel;
	}

	@Override
	@Transactional
	public ActivityOfferingInfo updateActivityOffering(
			String activityOfferingId,
			ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public StatusInfo deleteActivityOffering(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatementTreeViewInfo> getActivityOfferingRestrictions(
			String activityOfferingId, String nlUsageTypeKey, String language,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatementTreeViewInfo createActivityOfferingRestriction(
			String activityOfferingId, StatementTreeViewInfo restrictionInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DataValidationErrorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatementTreeViewInfo updateActivityOfferingRestriction(
			String activityOfferingId, StatementTreeViewInfo restrictionInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DataValidationErrorException, CircularReferenceException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteActivityOfferingRestriction(
			String activityOfferingId, String restrictionId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float calculateInClassContactHoursForTerm(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float calculateOutofClassContactHoursForTerm(
			String activityOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float calculateTotalContactHoursForTerm(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityOfferingInfo> copyActivityOffering(
			String activityOfferingId, Integer numberOfCopies,
			String copyContextTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegistrationGroupInfo getRegistrationGroup(
			String registrationGroupId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		LuiInfo lui = luiService.getLui(registrationGroupId, context);		
		return rgAssembler.assemble(lui, context);
	}

	@Override
	public List<RegistrationGroupInfo> getRegGroupsForCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		//TODO: implement LuiService.getLuiIdsByRelation and call it instead
		List<RegistrationGroupInfo> rgs = new ArrayList<RegistrationGroupInfo>();
		List<String> rgIds = new ArrayList<String>();
		List<LuiLuiRelationInfo> rels = luiService.getLuiLuiRelationsByLui(courseOfferingId, context);
		if(rels != null && !rels.isEmpty()){                  
            for(LuiLuiRelationInfo rel : rels){
            	if(rel.getRelatedLuiId().equals(courseOfferingId)){
            		if(rel.getTypeKey().equals("kuali.lui.lui.relation.RegisteredForVia")){
            			String luiId = rel.getLuiId();
            			LuiInfo lui = luiService.getLui(luiId, context);
            			if(lui != null && lui.getTypeKey().equals(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY) && !rgIds.contains(luiId)){
            				rgIds.add(luiId);
            				rgs.add(getRegistrationGroup(luiId, context));
            			}
            		}
            	}
            }
		}
		
		return rgs;		
	}

	@Override
	public List<RegistrationGroupInfo> getRegGroupsByFormatForCourse(
			String courseOfferingId, String formatTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public RegistrationGroupInfo createRegistrationGroup(
			String courseOfferingId,
			RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
			throws AlreadyExistsException, DoesNotExistException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		if(courseOfferingId != null){
			LuiInfo lui = rgAssembler.disassemble(registrationGroupInfo, context);
			try {
				LuiInfo created = luiService.createLui(registrationGroupInfo.getFormatId(), null, lui, context);
				
				if(created != null){
					registrationGroupInfo.setId(created.getId());
					
					processRelationsForRegGroup(courseOfferingId, registrationGroupInfo, context);
				}
				
				return registrationGroupInfo;
			} catch (DoesNotExistException e) {
				throw new OperationFailedException();
			}
		}
		else
			return null;
	}

	private void processRelationsForRegGroup(String courseOfferingId, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) 
			throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, 
			MissingParameterException, OperationFailedException, PermissionDeniedException{

			createLuiLuiRelation(registrationGroupInfo.getId(), courseOfferingId, "kuali.lui.lui.relation.type.registeredforvia", context);
			
			if(registrationGroupInfo.getActivityOfferingIds() != null && !registrationGroupInfo.getActivityOfferingIds().isEmpty()){
				for (String activityOfferingId : registrationGroupInfo.getActivityOfferingIds()){
					createLuiLuiRelation(registrationGroupInfo.getId(), activityOfferingId, "kuali.lui.lui.relation.type.registeredforvia", context);
				}
			}	
	}
	
	@Override
	@Transactional
	public RegistrationGroupInfo updateRegistrationGroup(
			String registrationGroupId,
			RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public StatusInfo deleteRegistrationGroup(String registrationGroupId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeatPoolDefinitionInfo getSeatPoolDefinition(
			String seatPoolDefinitionId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SeatPoolDefinitionInfo> getSeatPoolsForCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SeatPoolDefinitionInfo> getSeatPoolsForRegGroup(
			String registrationGroupId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeatPoolDefinitionInfo createSeatPoolDefinition(
			SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeatPoolDefinitionInfo updateSeatPoolDefinition(
			String seatPoolDefinitionId,
			SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<ValidationResultInfo> validateCourseOffering(String validationType, CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateCourseOfferingRestriction(String validationType, StatementTreeViewInfo restrictionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(String validationType, ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateActivityOfferingRestriction(String validationType, StatementTreeViewInfo restrictionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(String validationType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIdList(List<String> courseOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getCourseOfferingsByIdList not supported");
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIdList(List<String> activityOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getActivityOfferingsByIdList not supported");
    }

    @Override
    public StatusInfo assignActivityToCourseOffering(String activityOfferingId, List<String> courseOfferingIdList, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("assignActivityToCourseOffering not supported");    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByIdList(List<String> registrationGroupIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getRegistrationGroupsByIdList not supported");
    }

    @Override
    public List<CourseOfferingInfo> searchForCourseOfferings(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForCourseOfferingIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ActivityOfferingInfo> searchForActivityOfferings(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForActivityOfferingIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CourseRegistrationInfo> searchForRegistrationGroups(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForRegistrationGroupIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefintions(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForSeatpoolDefintionIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }    
}
