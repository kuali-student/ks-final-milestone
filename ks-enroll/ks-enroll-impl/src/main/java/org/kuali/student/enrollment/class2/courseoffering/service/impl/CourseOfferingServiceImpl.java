package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.service.assembler.ActivityOfferingAssembler;
import org.kuali.student.enrollment.class2.courseoffering.service.assembler.CourseOfferingAssembler;
import org.kuali.student.enrollment.class2.courseoffering.service.assembler.RegistrationGroupAssembler;
import org.kuali.student.enrollment.courseoffering.dto.*;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.state.service.StateService;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.service.TypeService;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import java.util.*;

public  class CourseOfferingServiceImpl implements CourseOfferingService{
	private LuiService luiService;
    private TypeService typeService;
	private CourseService courseService;
	private AcademicCalendarService acalService;
	private CourseOfferingAssembler coAssembler;
	private ActivityOfferingAssembler aoAssembler;
	private RegistrationGroupAssembler rgAssembler;
	private StateService stateService;
	private LuiPersonRelationService lprService;

    // TODO - remove when KSENROLL-247 is resolved
    private static final Integer TEMP_MAX_ENROLLMENT_DEFAULT = 50;

    public LuiService getLuiService() {
		return luiService;
	}

	public void setLuiService(LuiService luiService) {
		this.luiService = luiService;
	}

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
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

	public LuiPersonRelationService getLprService() {
		return lprService;
	}

	public void setLprService(LuiPersonRelationService lprService) {
		this.lprService = lprService;
	}

	@Override
	public CourseOfferingInfo getCourseOffering(String courseOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		LuiInfo lui = luiService.getLui(courseOfferingId, context);
        CourseOfferingInfo co;
        try {
            co = coAssembler.assemble(lui, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        return co;
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
	public List<String> getCourseOfferingIdsByTermAndSubjectArea(String termId,
			String subjectArea, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO UNHACK THIS HACK!!

        TermInfo term = acalService.getTerm(termId, context);

        List<String> luiIds = luiService.getLuiIdsByType(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, context);

        List<String> results = new ArrayList<String>();

        for(String luiId : luiIds) {
            CourseOfferingInfo co = getCourseOffering(luiId, context);

            if(StringUtils.equals(co.getSubjectArea(), subjectArea) && StringUtils.equals(co.getTermId(), term.getId())) {
                results.add(co.getId());
            }
        }

		return results;
	}

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(String termId, String instructorId, ContextInfo context)
                    throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                           OperationFailedException, PermissionDeniedException {

        List<LuiPersonRelationInfo> lprInfos = lprService.getLprsByPersonAndTypeForAtp(instructorId,termId,"kuali.lpr.type.instructor.main",context);
        List<CourseOfferingInfo> cos = new ArrayList<CourseOfferingInfo>();
        for(LuiPersonRelationInfo lprInfo : lprInfos){
            cos.add(getCourseOffering(lprInfo.getLuiId(), context));
        }

        return cos;
    }
        
        
    @Override
	@Transactional
	public CourseOfferingInfo createCourseOffering(
            String courseId, String termId, String courseOfferingTypeKey,
            CourseOfferingInfo coInfo,
            ContextInfo context) throws AlreadyExistsException,
			DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

        CourseInfo courseInfo = getCourse(courseId);
        if (courseInfo != null){
        	coInfo = coAssembler.assemble(courseInfo);
        	coInfo.setCourseId(courseId);
        }
        else {
        	throw new DoesNotExistException("The course does not exist. course: " + courseId);
        }

        if(acalService.getTerm(termId, context) != null) {
        	coInfo.setTermId(termId);
        }
        else {
        	throw new DoesNotExistException("The term does not exist. term: " + termId);
        }


        coInfo.setStateKey(getStateKey(LuiServiceConstants.COURSE_OFFERING_PROCESS_KEY, LuiServiceConstants.LUI_DRAFT_STATE_KEY, context));
        coInfo.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        
        LuiInfo luiInfo = coAssembler.disassemble(coInfo, context);
        LuiInfo created = luiService.createLui(courseId, termId, luiInfo, context);
        
        if (created != null) {
        	coInfo.setId(created.getId());

            // Create an LprRoster for this CourseOffering
            LprRosterInfo lprrInfo = new LprRosterInfo();
            lprrInfo.setAssociatedLuiIds(Arrays.asList(new String[] { created.getId()} ));
            lprrInfo.setTypeKey(LuiPersonRelationServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_TYPE_KEY);
            lprrInfo.setStateKey(LuiPersonRelationServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_READY_STATE_KEY);
            // TODO - does LprRoster.maximumCapacity equate to CourseOffering.maximumEnrollment?
            // TODO - remove constant when KSENROLL-247 is resolved
            lprrInfo.setMaximumCapacity(null != coInfo.getMaximumEnrollment() ? coInfo.getMaximumEnrollment() : TEMP_MAX_ENROLLMENT_DEFAULT);
            // TODO - where does this come from?
            lprrInfo.setCheckInRequired(false);
            // lprrInfo.setCheckInFrequency(???);
            // ...
            try {
                lprService.createLprRoster(lprrInfo, context);
            } catch (DisabledIdentifierException die) {
                throw new OperationFailedException(die.getClass().getCanonicalName() + ": " + die.getMessage());
            } catch (ReadOnlyException roe) {
                throw new OperationFailedException(roe.getClass() + ": " + roe.getMessage());
            }
        }
		return coInfo;
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
	
	private String getStateKey(String lifecycleKey, String defaultState, ContextInfo context) throws DoesNotExistException, InvalidParameterException, 
			MissingParameterException, OperationFailedException, PermissionDeniedException{
        String stateKey = null;
//        List<StateInfo> ivStates = stateService.getInitialValidStates(lifecycleKey, context);
//        if(ivStates != null && ivStates.size() > 0) {
//        	stateKey = ivStates.get(0).getKey();
//        }
//        else {
        	stateKey = defaultState;
//        }
//        
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
	            	if (updated != null) {
	            		processRelationsForCourseOffering(courseOfferingInfo, context);
                    }
	            }
        	}
        	else {
        		throw new DoesNotExistException("The CourseOffering does not exist: " + courseOfferingId);
            }
        } catch (DoesNotExistException e1) {
            throw new DoesNotExistException("The CourseOffering does not exist: " + courseOfferingId);
        }
        
        return courseOfferingInfo;
	}
	
	private void processRelationsForCourseOffering(CourseOfferingInfo co, ContextInfo context) throws DataValidationErrorException, 
			DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, 
			PermissionDeniedException, VersionMismatchException{

		processInstructors(co.getId(), co.getInstructors(), co.getTermId(), context);

		//how to determine that the lui already exist?
		if(co.getHasFinalExam()) {
            processFinalExam(co, context);
        }
			
		//TODO:jointOfferingIds -- ignore for core slice

        processFinalRoster(co.getId(), co.getMaximumEnrollment(), context);
	}

	private void processFinalExam(CourseOfferingInfo co, ContextInfo context) throws DataValidationErrorException, 
	DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		String cluId = co.getCourseId();
		String atpId = co.getTermId();
		LuiInfo finalExam = new LuiInfo();
		finalExam.setCluId(cluId);
		finalExam.setAtpId(atpId);
		finalExam.setStateKey(co.getStateKey());
		//TODO: not sure what type
		finalExam.setTypeKey("kuali.lui.type.course.finalExam");
		//TODO: what else inherit or fill into finalExam?
		LuiInfo created;
		try {
			created = luiService.createLui(cluId, atpId, finalExam, context);
		} catch (AlreadyExistsException e1) {
			throw new OperationFailedException("AlreadyExistsException when createLui. cluId: " + cluId + ", atpId: " + atpId);
		}
		
		if(created != null){
			try {
				createLuiLuiRelation(co.getId(), created.getId(), LuiServiceConstants.LUI_LUI_RELATION_DELIVEREDVIA_TYPE_KEY, context);
			} catch (AlreadyExistsException e1) {
				throw new OperationFailedException();
			}
		}
	}
	
	private void processInstructors(String courseOfferingId, List<OfferingInstructorInfo> instructors, String atpId, ContextInfo context) 
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, 
		PermissionDeniedException, DataValidationErrorException, VersionMismatchException{
		
		List<String> currrentInstructors = lprService.getPersonIdsByLuiAndTypeAndState(courseOfferingId, LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY, LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY, context);
		
		if(instructors != null && !instructors.isEmpty()){
			for(OfferingInstructorInfo instructor : instructors){
				try{
					if(currrentInstructors.contains(instructor.getPersonId())){
						LuiPersonRelationInfo existingLpr = getLpr(instructor.getPersonId(), courseOfferingId, context);
						if (existingLpr != null){
							existingLpr.setCommitmentPercent(instructor.getPercentageEffort());
							lprService.updateLpr(existingLpr.getId(), existingLpr, context);
							currrentInstructors.remove(instructor.getPersonId());
						}
					}	
					else{
						lprService.createLpr(instructor.getPersonId(), courseOfferingId, instructor.getTypeKey(), getNewLpr(instructor, courseOfferingId), context);
					}
				} catch (AlreadyExistsException e) {
					throw new OperationFailedException();
				} catch (DisabledIdentifierException e) {
					throw new OperationFailedException();
				} catch (ReadOnlyException e) {
					throw new OperationFailedException();
				}
			}
		}
		
		if (currrentInstructors != null && currrentInstructors.size() > 0){
			if(atpId != null){
				for(String instructorId: currrentInstructors){
					LuiPersonRelationInfo lpr = getLpr(instructorId, courseOfferingId, context);
					if(lpr != null ) {
						lprService.deleteLpr(lpr.getId(), context);
                    }
				}
			}
		}
	}

   private void processFinalRoster(String courseOfferingId, Integer maxEnrollment, ContextInfo context) throws DataValidationErrorException,
           InvalidParameterException, MissingParameterException,
           DoesNotExistException, PermissionDeniedException,
           OperationFailedException, VersionMismatchException {
        List<LprRosterInfo> rosters = lprService.getLprRostersByLuiAndType(courseOfferingId, LuiPersonRelationServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_TYPE_KEY, context);
        if(rosters != null && !rosters.isEmpty()){
            for(LprRosterInfo roster : rosters){
                roster.setMaximumCapacity(null != maxEnrollment ? maxEnrollment : TEMP_MAX_ENROLLMENT_DEFAULT );
                try {
                    lprService.updateLprRoster(roster.getId(), roster, context);
                } catch (ReadOnlyException e) {
                    throw new OperationFailedException();
                }
            }
        }
    }

	private LuiPersonRelationInfo getNewLpr(OfferingInstructorInfo instructor, String courseOfferingId){
		LuiPersonRelationInfo lpr = new LuiPersonRelationInfo();
		lpr.setPersonId(instructor.getPersonId());
		lpr.setCommitmentPercent(instructor.getPercentageEffort());
		lpr.setId(UUIDHelper.genStringUUID());
		lpr.setLuiId(courseOfferingId);
		lpr.setTypeKey(instructor.getTypeKey());
		lpr.setStateKey(instructor.getStateKey());		
		return lpr;
	}
	
	private LuiPersonRelationInfo getLpr(String instructor, String courseOfferingId, ContextInfo context) throws DoesNotExistException, 
			InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		LuiPersonRelationInfo lpr = null;
		try {
			List<LuiPersonRelationInfo> lprs = lprService.getLprsByPersonAndLui(instructor, courseOfferingId, context);
			
			if(lprs != null && !lprs.isEmpty()){
				for(LuiPersonRelationInfo lpri : lprs){
					if(lpri.getTypeKey().equals(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY)) {
						 lpr = lpri;
                    }
				}
			}			
		} catch (DisabledIdentifierException e) {
			throw new OperationFailedException();
		}
		
		return lpr;
	}

	@Override
	@Transactional
	public StatusInfo deleteCourseOffering(String courseOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        try {
            return luiService.deleteLui(courseOfferingId,context);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException("Error deleting course offering",e);
        }
    }


	@Override
	public List<TypeInfo> getActivityOfferingTypes(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        try {
            return typeService.getTypesByRefObjectUri(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,context);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Error getting Lui Types",e);
        }
    }

	@Override
	public List<TypeInfo> getActivityOfferingTypesForActivityType(
			String activityTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException, PermissionDeniedException {
//        TypeInfo activityType = luiService.getType(activityTypeKey, context);    
        
    	return typeService.getAllowedTypesForType(activityTypeKey, LuiServiceConstants.REF_OBJECT_URI_LUI, context);
	}

	@Override
	public ActivityOfferingInfo getActivityOffering(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		LuiInfo lui = luiService.getLui(activityOfferingId, context);
        ActivityOfferingInfo ao;
        try {
            ao = aoAssembler.assemble(lui, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        return ao;
    }

	@Override
	public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(
            String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getActivitiesForRelation(courseOfferingId, LuiServiceConstants.LUI_LUI_RELATION_DELIVEREDVIA_TYPE_KEY, "kuali.lui.type.course.finalExam", context);
	}


    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOfferingWithoutRegGroup(@WebParam(name = "activityOfferingTemplateId") String activityOfferingTemplateId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<ActivityOfferingInfo> getUnscheduledActivityOfferingsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<ActivityOfferingInfo> getUnpublishedActivityOfferingsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
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
			String  courseOfferingIds, String activityOfferingTypeKey,
			ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
			throws  DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

        if (null == courseOfferingIds || courseOfferingIds.isEmpty()) {
            throw new MissingParameterException("Course offering ID list parameter is required");
        }

        LuiInfo lui = null;
        try {
            lui = aoAssembler.disassemble(activityOfferingInfo, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        try {
            LuiInfo created = null;
                created = luiService.createLui(
                        activityOfferingInfo.getActivityId(), activityOfferingInfo.getTermId(), lui, context);
            if (null == created) {
                throw new OperationFailedException("LUI service did not create LUI");
            }
            activityOfferingInfo.setId(created.getId());
            processRelationsForActivityOffering(courseOfferingIds, activityOfferingInfo, context);
        }
        catch (DoesNotExistException e) {
            throw new OperationFailedException();
        }
        catch (AlreadyExistsException e) {
                e.printStackTrace();
        }
        return activityOfferingInfo;

	}

    private void processRelationsForActivityOffering(String courseOfferingId, ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
			throws AlreadyExistsException,DataValidationErrorException, InvalidParameterException, MissingParameterException, 
			OperationFailedException, PermissionDeniedException{
		
		processLuiluiRelationsForActivityOffering(courseOfferingId, activityOfferingInfo, context);
		
		try {
			processInstructors(activityOfferingInfo.getId(), activityOfferingInfo.getInstructors(), activityOfferingInfo.getTermId(), context);
	
		} catch (DoesNotExistException e) {
			throw new OperationFailedException();
		} catch (VersionMismatchException e) {
			throw new OperationFailedException();
		}
		
		//TODO: ao.setGradingOptionKeys -- ignore for core slice
       }

	
	private void processLuiluiRelationsForActivityOffering(String courseOfferingId,
			ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{

			createLuiLuiRelation(activityOfferingInfo.getId(), courseOfferingId, LuiServiceConstants.LUI_LUI_RELATION_DELIVEREDVIA_TYPE_KEY, context);

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
			MissingParameterException, OperationFailedException, PermissionDeniedException{
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

        LuiInfo lui = null;
        try {
            lui = aoAssembler.disassemble(activityOfferingInfo,context);
        }catch(AssemblyException e){
            throw new OperationFailedException("Error disassemble Activity Offering",e);
        }

        LuiInfo updatedLui = luiService.updateLui(activityOfferingId,lui,context);
        ActivityOfferingInfo activity = null;
        try {
            activity = aoAssembler.assemble(updatedLui,context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("Error assembling lui",e);
        }

        return activity;
	}

	@Override
	@Transactional
	public StatusInfo deleteActivityOffering(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        try {
            return luiService.deleteLui(activityOfferingId,context);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException("Error deleting dependent objects",e);
        }
    }



	@Override
	public RegistrationGroupInfo getRegistrationGroup(
			String registrationGroupId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		LuiInfo lui = luiService.getLui(registrationGroupId, context);
        RegistrationGroupInfo rg;
        try {
            rg = rgAssembler.assemble(lui, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        return rg;
    }

	@Override
	public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(
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
            		if(rel.getTypeKey().equals(LuiServiceConstants.LUI_LUI_RELATION_REGISTEREDFORVIA_TYPE_KEY)){
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
	@Transactional
	public RegistrationGroupInfo createRegistrationGroup(
			String courseOfferingId,
			RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
			throws  DoesNotExistException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		if(courseOfferingId != null){
            LuiInfo lui = null;
            try {
                lui = rgAssembler.disassemble(registrationGroupInfo, context);
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }

            try {
				String termId = null;
				
				if(registrationGroupInfo.getTermId()!= null) {
					termId = registrationGroupInfo.getTermId();
                }
				else {
					termId = getTermkeyByCourseOffering(courseOfferingId, context);
                }
				
				if(termId != null){
                    LuiInfo created = null;

                    created = luiService.createLui(registrationGroupInfo.getFormatId(), termId, lui, context);

                    if(created != null){
						registrationGroupInfo.setId(created.getId());
						registrationGroupInfo.setTermId(termId);
						processRelationsForRegGroup(courseOfferingId, registrationGroupInfo, context);
					}
					
					return registrationGroupInfo;
				}
				else {
					throw new OperationFailedException("termkey is missing.");
                }
			} catch (DoesNotExistException e) {
				throw new OperationFailedException();
			}  catch (AlreadyExistsException ex) {
                throw new OperationFailedException(ex.toString());
            }
		}
		else
			return null;
	}


    private String getTermkeyByCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException,
		InvalidParameterException, MissingParameterException, OperationFailedException{
		String termId = null;
		
		LuiInfo co = luiService.getLui(courseOfferingId, context);
		if(co != null){
			termId = co.getAtpId();
		}
		
		return termId;
	}

	private void processRelationsForRegGroup(String courseOfferingId, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) 
			throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, 
			MissingParameterException, OperationFailedException, PermissionDeniedException{

			createLuiLuiRelation(registrationGroupInfo.getId(), courseOfferingId, LuiServiceConstants.LUI_LUI_RELATION_REGISTEREDFORVIA_TYPE_KEY, context);
			
			if(registrationGroupInfo.getActivityOfferingIds() != null && !registrationGroupInfo.getActivityOfferingIds().isEmpty()){
				for (String activityOfferingId : registrationGroupInfo.getActivityOfferingIds()){
					createLuiLuiRelation(registrationGroupInfo.getId(), activityOfferingId, LuiServiceConstants.LUI_LUI_RELATION_REGISTEREDFORVIA_TYPE_KEY, context);
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

        Set<String> existingRelatedLuiIds = new HashSet<String>();
        Set<String> newRelatedLuiIds = new HashSet<String>(registrationGroupInfo.getActivityOfferingIds());
        newRelatedLuiIds.add(registrationGroupInfo.getCourseOfferingId());

        // Delete relations for removed Activity Offerings or Course Offering
        List<LuiLuiRelationInfo> llrs = luiService.getLuiLuiRelationsByLui(registrationGroupId, context);
        for (LuiLuiRelationInfo llr : llrs) {
            if (registrationGroupId.equals(llr.getLuiId()) && LuiServiceConstants.LUI_LUI_RELATION_REGISTEREDFORVIA_TYPE_KEY.equals(llr.getTypeKey())) {
                String relatedLuiId = llr.getRelatedLuiId();
                existingRelatedLuiIds.add(relatedLuiId);
                if (!newRelatedLuiIds.contains(relatedLuiId)) {
                    luiService.deleteLuiLuiRelation(llr.getId(), context);
                }
            }
        }

        // Create relations for added Activity Offerings or Course Offering
        for (String luiId : newRelatedLuiIds) {
            if (!existingRelatedLuiIds.contains(luiId)) {
                try {
                    createLuiLuiRelation(registrationGroupId, luiId, LuiServiceConstants.LUI_LUI_RELATION_REGISTEREDFORVIA_TYPE_KEY, context);
                } catch (AlreadyExistsException e) {
                    throw new OperationFailedException("Could not create Lui-Lui Relation '" + registrationGroupId + "'-'" + luiId + "'", e);
                }
            }
        }

        try {
            LuiInfo regGroupLui = rgAssembler.disassemble(registrationGroupInfo, context);
            LuiInfo updatedRegGroupLui = luiService.updateLui(regGroupLui.getId(), regGroupLui, context);
            return rgAssembler.assemble(updatedRegGroupLui, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("Could not disassemble RegistrationGroup " + registrationGroupInfo.getId(), e);
        }
	}

	@Override
	@Transactional
	public StatusInfo deleteRegistrationGroup(String registrationGroupId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        try {
            return luiService.deleteLui(registrationGroupId, context);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException("Could not delete LUI '" + registrationGroupId + "'", e);
        }
	}

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIds(List<String> courseOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiInfo> luiInfoList = luiService.getLuisByIds(courseOfferingIds,context);
        List<CourseOfferingInfo> coList = new ArrayList();
        for (LuiInfo lui : luiInfoList){
            CourseOfferingInfo coInfo = null;
            try {
                coInfo = coAssembler.assemble(lui,context);
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
            coList.add(coInfo);
        }
        return coList;
    }


    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo> getCourseOfferingsByCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(@WebParam(name = "courseId") String courseId, @WebParam(name = "termId") String termId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> getCourseOfferingIdsByTerm(@WebParam(name = "termId") String termId, @WebParam(name = "useIncludedTerm") Boolean useIncludedTerm, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(@WebParam(name = "termId") String termId, @WebParam(name = "unitsContentOwnerId") String unitsContentOwnerId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> getCourseOfferingIdsByType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> getCourseOfferingIdsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> getPublishedCourseOfferingIdsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo updateCourseOfferingFromCanonical(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> validateCourseOffering(@WebParam(name = "validationType") String validationType, @WebParam(name = "courseOfferingInfo") org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> validateCourseOfferingFromCanonical(@WebParam(name = "courseOfferingInfo") org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo getFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo> getFormatOfferingByCourseOfferingId(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo createFormatOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "formatOfferingType") String formatOfferingType, @WebParam(name = "formatOfferingInfo") org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo updateFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "formatOfferingInfo") org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo formatOfferingInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.r2.common.dto.StatusInfo deleteFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.r2.core.type.dto.TypeInfo getActivityOfferingType(@WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo> getActivityOfferingsByIds(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo> getActivityOfferingsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo> generateActivityOfferingsForFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> validateActivityOffering(@WebParam(name = "validationType") String validationType, @WebParam(name = "activityOfferingInfo") org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public Float calculateInClassContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public Float calculateOutofClassContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public Float calculateTotalContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo> getRegistrationGroupsByIds(@WebParam(name = "registrationGroupIds") List<String> registrationGroupIds, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo> getRegistrationGroupsWithActivityOfferings(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo> getRegistrationGroupsByFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo> generateRegistrationGroupsForFormatOffering(@WebParam(name = "formatOfferingId") String formatOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> validateRegistrationGroup(@WebParam(name = "validationType") String validationType, @WebParam(name = "registrationGroupInfo") org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupTemplateInfo getRegistrationGroupTemplate(@WebParam(name = "registrationGroupTemplateId") String registrationGroupTemplateId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupTemplateInfo updateRegistrationGroupTemplate(@WebParam(name = "registrationGroupTemplateId") String registrationGroupTemplateId, @WebParam(name = "registrationGroupTemplateInfo") org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupTemplateInfo registrationGroupTemplateInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.r2.common.dto.StatusInfo deleteRegistrationGroupTemplate(@WebParam(name = "registrationGroupTemplateId") String registrationGroupTemplateId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo getSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo> getSeatPoolDefinitionsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo> getSeatPoolDefinitionsForRegGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo createSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionInfo") org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo updateSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "seatPoolDefinitionInfo") org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.r2.common.dto.ValidationResultInfo> validateSeatPoolDefinition(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "seatPoolDefinitionInfo") org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public org.kuali.student.r2.common.dto.StatusInfo deleteSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo> searchForCourseOfferings(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> searchForCourseOfferingIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo> searchForActivityOfferings(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> searchForActivityOfferingIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo> searchForRegistrationGroups(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> searchForRegistrationGroupIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo> searchForSeatpoolDefintions(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

    @Override
    public List<String> searchForSeatpoolDefintionIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") org.kuali.student.r2.common.dto.ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
}
