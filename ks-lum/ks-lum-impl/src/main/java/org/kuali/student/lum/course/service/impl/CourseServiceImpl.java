package org.kuali.student.lum.course.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.common.validator.ValidatorFactory;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BusinessServiceMethodInvoker;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.assembler.CourseAssembler;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.springframework.transaction.annotation.Transactional;

/**
 * CourseServiceImpl implements CourseService Interface by mapping DTOs in CourseInfo to underlying entity DTOs like CluInfo
 * and CluCluRelationInfo.
 * 
 * @author Kuali Student Team
 */
@Transactional(noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class CourseServiceImpl implements CourseService {
    final static Logger LOG = Logger.getLogger(CourseServiceImpl.class);

    private LuService luService;
    private CourseAssembler courseAssembler;
    private BusinessServiceMethodInvoker courseServiceMethodInvoker;
    private DictionaryService dictionaryServiceDelegate;
    private Validator validator;
    private ValidatorFactory validatorFactory;

    @Override
    public CourseInfo createCourse(CourseInfo courseInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException {

        if (courseInfo == null) {
            throw new MissingParameterException("CourseInfo can not be null");
        }

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
    public CourseInfo updateCourse(CourseInfo courseInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException {

        if (courseInfo == null) {
            throw new MissingParameterException("CourseInfo can not be null");
        }

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
    public StatusInfo deleteCourse(String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, AlreadyExistsException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException {

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
        throw new UnsupportedOperationException("GetCourseStatements");
    }

    @Override
    public List<ValidationResultInfo> validateCourse(String validationType, CourseInfo courseInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        ObjectStructureDefinition objStructure = this.getObjectStructure(CourseInfo.class.getName());
        validatorFactory.setObjectStructureDefinition(objStructure);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(courseInfo, objStructure);
        return validationResults;
    }

    @Override
    public StatementTreeViewInfo createCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("createCourseStatement");
    }

    @Override
    public StatusInfo deleteCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("deleteCourseStatement");
    }

    @Override
    public StatementTreeViewInfo updateCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("updateCourseStatement");
    }

    @Override
    public List<ValidationResultInfo> validateCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("validateCourseStatement");
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

    private CourseInfo processCourseInfo(CourseInfo courseInfo, NodeOperation operation) throws AssemblyException, OperationFailedException, VersionMismatchException, PermissionDeniedException, MissingParameterException, InvalidParameterException, DoesNotExistException, DataValidationErrorException, AlreadyExistsException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException {

        BaseDTOAssemblyNode<CourseInfo, CluInfo> results = courseAssembler.disassemble(courseInfo, operation);

        // Use the results to make the appropriate service calls here
		courseServiceMethodInvoker.invokeServiceCalls(results);

        return results.getBusinessDTORef();
    }

    /**
     * @param validator
     *            the validator to set
     */
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * @return the validator
     */
    public Validator getValidator() {
        return validator;
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
}
