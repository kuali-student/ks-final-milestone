package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.service.assembler.ActivityOfferingAssembler;
import org.kuali.student.enrollment.class2.courseoffering.service.assembler.CourseOfferingAssembler;
import org.kuali.student.enrollment.class2.courseoffering.service.assembler.RegistrationGroupAssembler;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.state.service.StateService;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.service.TypeService;

@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class CourseOfferingServiceImpl implements CourseOfferingService{
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
	public List<CourseOfferingInfo> getCourseOfferingsForCourseAndTerm(
			String courseId, String termId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCourseOfferingIdsForTerm(String termId,
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
    public List<String> getCourseOfferingIdsByTermAndInstructorId(String termId, String instructorId, ContextInfo context) 
                    throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                           OperationFailedException, PermissionDeniedException {

        List<LuiPersonRelationInfo> lprInfos = lprService.getLprsByPersonAndTypeForAtp(instructorId,termId,"kuali.lpr.type.instructor.main",context);
        List<String> coIds = new ArrayList<String>();
        for(LuiPersonRelationInfo lprInfo : lprInfos){
            coIds.add(lprInfo.getLuiId());
        }

        return coIds;
    }
        
        
	@Override
	public List<String> getCourseOfferingIdsByTermAndUnitContentOwner(String termId,
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
			String courseId, String termId, List<String> formatIdList,
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
        else {
        	throw new DoesNotExistException("The course does not exist. course: " + courseId);
        }

        if(acalService.getTerm(termId, context) != null) {
        	courseOfferingInfo.setTermId(termId);
        }
        else {
        	throw new DoesNotExistException("The term does not exist. term: " + termId);
        }

        if (checkExistenceForFormats(formatIdList, context)) {
        	courseOfferingInfo.setFormatIds(formatIdList);
        }

        courseOfferingInfo.setStateKey(getStateKey(LuiServiceConstants.COURSE_OFFERING_PROCESS_KEY, LuiServiceConstants.LUI_DRAFT_STATE_KEY, context));
        courseOfferingInfo.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        
        LuiInfo luiInfo = coAssembler.disassemble(courseOfferingInfo, context);
        LuiInfo created = luiService.createLui(courseId, termId, luiInfo, context);
        
        if (created != null) {
        	courseOfferingInfo.setId(created.getId());

            // Create an LprRoster for this CourseOffering
            LprRosterInfo lprrInfo = new LprRosterInfo();
            lprrInfo.setAssociatedLuiIds(Arrays.asList(new String[] { created.getId()} ));
            lprrInfo.setTypeKey(LuiPersonRelationServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_TYPE_KEY);
            lprrInfo.setStateKey(LuiPersonRelationServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_READY_STATE_KEY);
            // TODO - does LprRoster.maximumCapacity equate to CourseOffering.maximumEnrollment?
            // TODO - remove constant when KSENROLL-247 is resolved
            lprrInfo.setMaximumCapacity(null != courseOfferingInfo.getMaximumEnrollment() ? courseOfferingInfo.getMaximumEnrollment() : TEMP_MAX_ENROLLMENT_DEFAULT);
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
        Boolean hasFinalExam = co.getHasFinalExam();
		if(hasFinalExam != null && hasFinalExam) {
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
		
		List<String> currrentInstructors = lprService.getPersonIdsByLui(courseOfferingId, LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY, LuiPersonRelationServiceConstants.ASSIGNED_STATE_KEY, context);
		
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
        List<LprRosterInfo> rosters = lprService.getLprRostersByLuiAndRosterType(courseOfferingId, LuiPersonRelationServiceConstants.LPRROSTER_COURSE_FINAL_GRADEROSTER_TYPE_KEY, context);
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
			List<LuiPersonRelationInfo> lprs = lprService.getLprsByLuiAndPerson(instructor, courseOfferingId, context);
			
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
        try {
            return luiService.deleteLui(courseOfferingId,context);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException("Error deleting course offering",e);
        }
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
	public List<ActivityOfferingInfo> getActivitiesForCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getActivitiesForRelation(courseOfferingId, LuiServiceConstants.LUI_LUI_RELATION_DELIVEREDVIA_TYPE_KEY, "kuali.lui.type.course.finalExam", context);
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

        if (null == courseOfferingIdList || courseOfferingIdList.isEmpty()) {
            throw new MissingParameterException("Course offering ID list parameter is required");
        }

        LuiInfo lui = null;
        try {
            lui = aoAssembler.disassemble(activityOfferingInfo, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        try {
            LuiInfo created = luiService.createLui(
                    activityOfferingInfo.getActivityId(), activityOfferingInfo.getTermId(), lui, context);
            if (null == created) {
                throw new OperationFailedException("LUI service did not create LUI");
            }
            activityOfferingInfo.setId(created.getId());
            processRelationsForActivityOffering(courseOfferingIdList, activityOfferingInfo, context);
            return activityOfferingInfo;
        }
        catch (DoesNotExistException e) {
            throw new OperationFailedException();
        }
	}
	
	private void processRelationsForActivityOffering(List<String> courseOfferingIdList, ActivityOfferingInfo activityOfferingInfo, ContextInfo context)  
			throws AlreadyExistsException,DataValidationErrorException, InvalidParameterException, MissingParameterException, 
			OperationFailedException, PermissionDeniedException{
		
		processLuiluiRelationsForActivityOffering(courseOfferingIdList, activityOfferingInfo, context);
		
		try {
			processInstructors(activityOfferingInfo.getId(), activityOfferingInfo.getInstructors(), activityOfferingInfo.getTermId(), context);
	
		} catch (DoesNotExistException e) {
			throw new OperationFailedException();
		} catch (VersionMismatchException e) {
			throw new OperationFailedException();
		}
		
		//TODO: ao.setGradingOptionKeys -- ignore for core slice
}

	
	private void processLuiluiRelationsForActivityOffering(List<String> courseOfferingIdList,
			ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{

		for (String courseOfferingId : courseOfferingIdList) {
			createLuiLuiRelation(activityOfferingInfo.getId(), courseOfferingId, LuiServiceConstants.LUI_LUI_RELATION_DELIVEREDVIA_TYPE_KEY, context);
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
        RegistrationGroupInfo rg;
        try {
            rg = rgAssembler.assemble(lui, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        return rg;
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
					LuiInfo created = luiService.createLui(registrationGroupInfo.getFormatId(), termId, lui, context);
					
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
        List<LuiInfo> luiInfoList = luiService.getLuisByIdList(courseOfferingIds,context);
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
        // TODO - DavidE needs this implemented
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
