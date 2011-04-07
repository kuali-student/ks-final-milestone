package org.kuali.student.lum.course.service.impl;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.common.assembly.BusinessServiceMethodInvoker;
import org.kuali.student.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.common.assembly.data.AssemblyException;
import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.dictionary.service.DictionaryService;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.CircularReferenceException;
import org.kuali.student.common.exceptions.CircularRelationshipException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DependentObjectsExistException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.UnsupportedActionException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.common.validator.ValidatorFactory;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.CourseJointInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.CourseServiceConstants;
import org.kuali.student.lum.course.service.assembler.CourseAssembler;
import org.kuali.student.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.service.LuServiceConstants;
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;
import org.springframework.transaction.annotation.Transactional;
/**
 * CourseServiceImpl implements CourseService Interface by mapping DTOs in CourseInfo to underlying entity DTOs like CluInfo
 * and CluCluRelationInfo.
 *
 * For Credits, there are three credit types that are set with a combination of type and dynamic attributes
 * To set a variable(range) credit option,
 * set the ResultComponentInfo type to CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE
 * and add the dynamic attributes CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_MIN_CREDIT_VALUE and 
 * CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_MAX_CREDIT_VALUE with respective credit min and max values.
 * 
 * To set a fixed credit option,
 * set the ResultComponentInfo type to CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED
 * and add the dynamic attribute CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_FIXED_CREDIT_VALUE
 * with the fixed credit value
 * 
 * To Set multiple credit options, 
 * set the ResultComponentInfo type to CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE
 * and add each credit as a numeric ResultValue on the ResultComponentInfo for each credit you desire
 *
 * @author Kuali Student Team
 */
@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class CourseServiceImpl implements CourseService {
    final static Logger LOG = Logger.getLogger(CourseServiceImpl.class);

    private LuService luService;
    private CourseAssembler courseAssembler;
    private BusinessServiceMethodInvoker courseServiceMethodInvoker;
    private DictionaryService dictionaryServiceDelegate;
    private ValidatorFactory validatorFactory;
    private StatementService statementService;

    @Override
    @Transactional(readOnly=false)
	public CourseInfo createCourse(CourseInfo courseInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException {

        checkForMissingParameter(courseInfo, "CourseInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateCourse("OBJECT", courseInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
            return processCourseInfo(courseInfo, NodeOperation.CREATE);
        } catch (AssemblyException e) {
            LOG.error("Error disassembling course", e);
            throw new OperationFailedException("Error disassembling course");
        } catch (Exception e){
        	LOG.error("Error disassembling course", e);
        	throw new OperationFailedException("Error disassembling course");
        }
    }

    @Override
    @Transactional(readOnly=false)
	public CourseInfo updateCourse(CourseInfo courseInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, UnsupportedOperationException, CircularReferenceException {

        checkForMissingParameter(courseInfo, "CourseInfo");
        
        // Validate
        List<ValidationResultInfo> validationResults = validateCourse("OBJECT", courseInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {

            return processCourseInfo(courseInfo, NodeOperation.UPDATE);

        } catch (AssemblyException e) {
            LOG.error("Error disassembling course", e);
            throw new OperationFailedException("Error disassembling course");
        }
    }

    @Override
    @Transactional(readOnly=false)
	public StatusInfo deleteCourse(String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, AlreadyExistsException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, UnsupportedOperationException, CircularReferenceException {

        try {
            CourseInfo course = getCourse(courseId);

            processCourseInfo(course, NodeOperation.DELETE);

            StatusInfo status = new StatusInfo();
            status.setSuccess(true);
            return status;

        } catch (AssemblyException e) {
            LOG.error("Error disassembling course", e);
            throw new OperationFailedException("Error disassembling course");
        }
    }

    @Override
    public CourseInfo getCourse(String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        CluInfo clu = luService.getClu(courseId);

        CourseInfo course;
        try {
            course = courseAssembler.assemble(clu, null, false);
        } catch (AssemblyException e) {
            LOG.error("Error assembling course", e);
            throw new OperationFailedException("Error assembling course");
        }

        return course;

    }

    @Override
    public List<ActivityInfo> getCourseActivities(String formatId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("GetCourseActivities");
    }

    @Override
    public List<FormatInfo> getCourseFormats(String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("GetCourseFormats");
    }

    @Override
    public List<LoDisplayInfo> getCourseLos(String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("GetCourseLos");
    }

    @Override
    public List<StatementTreeViewInfo> getCourseStatements(String courseId, String nlUsageTypeKey, String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	checkForMissingParameter(courseId, "courseId");

    	CluInfo clu = luService.getClu(courseId);
		if (!CourseAssemblerConstants.COURSE_TYPE.equals(clu.getType())) {
			throw new DoesNotExistException("Specified CLU is not a Course");
		}
		List<RefStatementRelationInfo> relations = statementService.getRefStatementRelationsByRef(CourseAssemblerConstants.COURSE_TYPE, clu.getId());
		if (relations == null) {
			return new ArrayList<StatementTreeViewInfo>(0);
		}

		List<StatementTreeViewInfo> tree = new ArrayList<StatementTreeViewInfo>(relations.size());
		for (RefStatementRelationInfo relation : relations) {
			tree.add(statementService.getStatementTreeView(relation.getStatementId()));
		}
    	return tree;
    }

    @Override
    public List<ValidationResultInfo> validateCourse(String validationType, CourseInfo courseInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        ObjectStructureDefinition objStructure = this.getObjectStructure(CourseInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(courseInfo, objStructure);
        return validationResults;
    }

    @Override
    @Transactional(readOnly=false)
	public StatementTreeViewInfo createCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
    	checkForMissingParameter(courseId, "courseId");
    	checkForMissingParameter(statementTreeViewInfo, "statementTreeViewInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateCourseStatement(courseId, statementTreeViewInfo);
        if (!isEmpty(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        if (findStatementReference(courseId, statementTreeViewInfo) != null) {
        	throw new InvalidParameterException("Statement is already referenced by this course");
        }

		try {
			StatementTreeViewInfo tree = statementService.createStatementTreeView(statementTreeViewInfo);
			RefStatementRelationInfo relation = new RefStatementRelationInfo();
			relation.setRefObjectId(courseId);
			relation.setRefObjectTypeKey(CourseAssemblerConstants.COURSE_TYPE);
			relation.setStatementId(tree.getId());
	        relation.setType(CourseAssemblerConstants.COURSE_REFERENCE_TYPE);
	        relation.setState(CourseAssemblerConstants.ACTIVE);
			statementService.createRefStatementRelation(relation);
		} catch (Exception e) {
			throw new OperationFailedException("Unable to create clu/tree relation", e);
		}
    	return statementTreeViewInfo;
    }

	@Override
    @Transactional(readOnly=false)
	public StatusInfo deleteCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	checkForMissingParameter(courseId, "courseId");
    	checkForMissingParameter(statementTreeViewInfo, "statementTreeViewInfo");

    	RefStatementRelationInfo relation = findStatementReference(courseId, statementTreeViewInfo);
    	if (relation != null) {
    		statementService.deleteRefStatementRelation(relation.getId());
    		statementService.deleteStatementTreeView(statementTreeViewInfo.getId());
    		StatusInfo result = new StatusInfo();
    		return result;
    	}

    	throw new DoesNotExistException("Course does not have this StatemenTree");
	}

    @Override
    @Transactional(readOnly=false)
	public StatementTreeViewInfo updateCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException {
    	checkForMissingParameter(courseId, "courseId");
    	checkForMissingParameter(statementTreeViewInfo, "statementTreeViewInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateCourseStatement(courseId, statementTreeViewInfo);
        if (!isEmpty(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        if (findStatementReference(courseId, statementTreeViewInfo) == null) {
        	throw new InvalidParameterException("Statement is not part of this course");
        }

        return statementService.updateStatementTreeView(statementTreeViewInfo.getId(), statementTreeViewInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
    	checkForMissingParameter(courseId, "courseId");
    	checkForMissingParameter(statementTreeViewInfo, "statementTreeViewInfo");

    	try {
			CluInfo clu = luService.getClu(courseId);
		} catch (DoesNotExistException e) {
			throw new InvalidParameterException("course does not exist");
		}

    	ObjectStructureDefinition objStructure = this.getObjectStructure(StatementTreeViewInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(statementTreeViewInfo, objStructure);
        return validationResults;
    }   

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    @Override
    public List<String> getObjectTypes() {
        return dictionaryServiceDelegate.getObjectTypes();
    }

    public CourseAssembler getCourseAssembler() {
        return courseAssembler;
    }

    public void setCourseAssembler(CourseAssembler courseAssembler) {
        this.courseAssembler = courseAssembler;
    }

    public BusinessServiceMethodInvoker getCourseServiceMethodInvoker() {
        return courseServiceMethodInvoker;
    }

    public void setCourseServiceMethodInvoker(BusinessServiceMethodInvoker courseServiceMethodInvoker) {
        this.courseServiceMethodInvoker = courseServiceMethodInvoker;
    }

    public DictionaryService getDictionaryServiceDelegate() {
        return dictionaryServiceDelegate;
    }

    public void setDictionaryServiceDelegate(DictionaryService dictionaryServiceDelegate) {
        this.dictionaryServiceDelegate = dictionaryServiceDelegate;
    }

    private CourseInfo processCourseInfo(CourseInfo courseInfo, NodeOperation operation) throws AssemblyException, OperationFailedException, VersionMismatchException, PermissionDeniedException, MissingParameterException, InvalidParameterException, DoesNotExistException, DataValidationErrorException, AlreadyExistsException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, UnsupportedOperationException, CircularReferenceException {

        BaseDTOAssemblyNode<CourseInfo, CluInfo> results = courseAssembler.disassemble(courseInfo, operation);

        // Use the results to make the appropriate service calls here
		courseServiceMethodInvoker.invokeServiceCalls(results);

        return results.getBusinessDTORef();
    }

    public ValidatorFactory getValidatorFactory() {
		return validatorFactory;
	}

	public void setValidatorFactory(ValidatorFactory validatorFactory) {
		this.validatorFactory = validatorFactory;
	}

	public LuService getLuService() {
        return luService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    }

	public StatementService getStatementService() {
		return statementService;
	}

	public void setStatementService(StatementService statementService) {
		this.statementService = statementService;
	}

	@Override
	@Transactional(readOnly=false)
	public CourseInfo createNewCourseVersion(String versionIndCourseId,
			String versionComment) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {

		//step one, get the original course
		VersionDisplayInfo currentVersion = luService.getCurrentVersion(LuServiceConstants.CLU_NAMESPACE_URI, versionIndCourseId);
		CourseInfo originalCourse = getCourse(currentVersion.getId());

		//Version the Clu
		CluInfo newVersionClu = luService.createNewCluVersion(versionIndCourseId, versionComment);

		try {
	        BaseDTOAssemblyNode<CourseInfo, CluInfo> results;

	        //Integrate changes into the original course. (should this just be just the id?)
			courseAssembler.assemble(newVersionClu, originalCourse, true);

			//Clear Ids from the original course
			resetIds(originalCourse);

			//Disassemble the new course
			results = courseAssembler.disassemble(originalCourse, NodeOperation.UPDATE);

			// Use the results to make the appropriate service calls here
			courseServiceMethodInvoker.invokeServiceCalls(results);

			//copy statements
			List<StatementTreeViewInfo> statementTreeViews = getCourseStatements(currentVersion.getId(),null,null);
			
			clearStatementTreeViewIds(statementTreeViews);
			
			for(StatementTreeViewInfo statementTreeView:statementTreeViews){
				createCourseStatement(results.getBusinessDTORef().getId(), statementTreeView);
			}
			
			return results.getBusinessDTORef();
		} catch (AlreadyExistsException e) {
			throw new OperationFailedException("Error creating new course version",e);
		} catch (DependentObjectsExistException e) {
			throw new OperationFailedException("Error creating new course version",e);
		} catch (CircularRelationshipException e) {
			throw new OperationFailedException("Error creating new course version",e);
		} catch (UnsupportedActionException e) {
			throw new OperationFailedException("Error creating new course version",e);
		} catch (AssemblyException e) {
			throw new OperationFailedException("Error creating new course version",e);
		} catch (UnsupportedOperationException e) {
			throw new OperationFailedException("Error creating new course version",e);
		} catch (CircularReferenceException e) {
			throw new OperationFailedException("Error creating new course version",e);
		}

	}

	private void clearStatementTreeViewIds(
			List<StatementTreeViewInfo> statementTreeViews) throws OperationFailedException {
		for(StatementTreeViewInfo statementTreeView:statementTreeViews){
			clearStatementTreeViewIdsRecursively(statementTreeView);
		}
	}

	private void clearStatementTreeViewIdsRecursively(StatementTreeViewInfo statementTreeView) throws OperationFailedException{
		statementTreeView.setId(null);
		for(ReqComponentInfo reqComp:statementTreeView.getReqComponents()){
			reqComp.setId(null);
			for(ReqCompFieldInfo field:reqComp.getReqCompFields()){
				field.setId(null);
				//copy any clusets that are adhoc'd and set the field value to the new cluset
				if(ReqComponentFieldTypes.COURSE_CLUSET_KEY.getId().equals(field.getType())||
				   ReqComponentFieldTypes.PROGRAM_CLUSET_KEY.getId().equals(field.getType())||
				   ReqComponentFieldTypes.CLUSET_KEY.getId().equals(field.getType())){
					try {
						CluSetInfo cluSet = luService.getCluSetInfo(field.getValue());
						cluSet.setId(null);
						//Clear clu ids if membership info exists, they will be re-added based on membership info 
						if (cluSet.getMembershipQuery() != null){
							cluSet.getCluIds().clear();
							cluSet.getCluSetIds().clear();
						}
						cluSet = luService.createCluSet(cluSet.getType(), cluSet);
						field.setValue(cluSet.getId());
					} catch (Exception e) {
						throw new OperationFailedException("Error copying clusets.", e);
					}
				}
				
			}
		}
		for(StatementTreeViewInfo child: statementTreeView.getStatements()){
			clearStatementTreeViewIdsRecursively(child);
		}
	}

	private void resetIds(CourseInfo course) {
		//Clear/Reset Joint info ids
		for(CourseJointInfo joint:course.getJoints()){
			joint.setRelationId(null);
		}
		//Clear Los
		for(LoDisplayInfo lo:course.getCourseSpecificLOs()){
			resetLoRecursively(lo);
		}
		//Clear format/activity ids
		for(FormatInfo format:course.getFormats()){
			format.setId(null);
			for(ActivityInfo activity:format.getActivities()){
				activity.setId(null);
			}
		}
	}

	private void resetLoRecursively(LoDisplayInfo lo){
		lo.getLoInfo().setId(null);
		for(LoDisplayInfo nestedLo:lo.getLoDisplayInfoList()){
			resetLoRecursively(nestedLo);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo setCurrentCourseVersion(String courseVersionId,
			Date currentVersionStart) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			IllegalVersionSequencingException, OperationFailedException,
			PermissionDeniedException {
		return luService.setCurrentCluVersion(courseVersionId, currentVersionStart);
	}

	@Override
	public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI,
			String refObjectId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(CourseServiceConstants.COURSE_NAMESPACE_URI.equals(refObjectTypeURI)){
			return luService.getCurrentVersion(LuServiceConstants.CLU_NAMESPACE_URI, refObjectId);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	@Override
	public VersionDisplayInfo getCurrentVersionOnDate(String refObjectTypeURI,
			String refObjectId, Date date) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(CourseServiceConstants.COURSE_NAMESPACE_URI.equals(refObjectTypeURI)){
			return luService.getCurrentVersionOnDate(LuServiceConstants.CLU_NAMESPACE_URI, refObjectId, date);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	@Override
	public VersionDisplayInfo getFirstVersion(String refObjectTypeURI,
			String refObjectId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(CourseServiceConstants.COURSE_NAMESPACE_URI.equals(refObjectTypeURI)){
			return luService.getFirstVersion(LuServiceConstants.CLU_NAMESPACE_URI, refObjectId);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");

	}

	@Override
	public VersionDisplayInfo getLatestVersion(String refObjectTypeURI,
			String refObjectId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(CourseServiceConstants.COURSE_NAMESPACE_URI.equals(refObjectTypeURI)){
			return luService.getLatestVersion(LuServiceConstants.CLU_NAMESPACE_URI, refObjectId);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");

	}

	@Override
	public VersionDisplayInfo getVersionBySequenceNumber(
			String refObjectTypeURI, String refObjectId, Long sequence)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		if(CourseServiceConstants.COURSE_NAMESPACE_URI.equals(refObjectTypeURI)){
			return luService.getVersionBySequenceNumber(LuServiceConstants.CLU_NAMESPACE_URI, refObjectId, sequence);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	@Override
	public List<VersionDisplayInfo> getVersions(String refObjectTypeURI,
			String refObjectId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(CourseServiceConstants.COURSE_NAMESPACE_URI.equals(refObjectTypeURI)){
			return luService.getVersions(LuServiceConstants.CLU_NAMESPACE_URI, refObjectId);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	@Override
	public List<VersionDisplayInfo> getVersionsInDateRange(
			String refObjectTypeURI, String refObjectId, Date from, Date to)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		if(CourseServiceConstants.COURSE_NAMESPACE_URI.equals(refObjectTypeURI)){
			return luService.getVersionsInDateRange(LuServiceConstants.CLU_NAMESPACE_URI, refObjectId, from, to);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	/**
	 * Check for missing parameter and throw localized exception if missing
	 *
	 * @param param
	 * @param parameter name
	 * @throws MissingParameterException
	 */
	private void checkForMissingParameter(Object param, String paramName)
			throws MissingParameterException {
		if (param == null) {
			throw new MissingParameterException(paramName + " can not be null");
		}
	}

	/**
	 * @param courseId
	 * @param statementTreeViewInfo
	 * @return reference exists
	 *
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws DoesNotExistException
	 */
	private RefStatementRelationInfo findStatementReference(String courseId,
			StatementTreeViewInfo statementTreeViewInfo)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, DoesNotExistException {
		List<RefStatementRelationInfo> course = statementService.getRefStatementRelationsByRef(CourseAssemblerConstants.COURSE_TYPE, courseId);
		if (course != null) {
			for (RefStatementRelationInfo refRelation : course) {
				if (refRelation.getStatementId().equals(statementTreeViewInfo.getId())) {
					return refRelation;
				}
			}
		}
		return null;
	}
}
