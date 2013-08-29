package org.kuali.student.r2.lum.course.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebParam;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssembler;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.r1.common.assembly.BusinessServiceMethodInvoker;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r1.common.validator.ValidatorUtils;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.validator.Validator;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.kuali.student.r1.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.springframework.transaction.annotation.Transactional;

/**
 * CourseServiceImpl implements CourseService Interface by mapping DTOs in CourseInfo to underlying entity DTOs like CluInfo
 * and CluCluRelationInfo. For Credits, there are three credit types that are set with a combination of type and dynamic
 * attributes To set a variable(range) credit option, set the ResultComponentInfo type to
 * CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE and add the dynamic attributes
 * CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_MIN_CREDIT_VALUE and
 * CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_MAX_CREDIT_VALUE with respective credit min and max values. To set a
 * fixed credit option, set the ResultComponentInfo type to CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED and
 * add the dynamic attribute CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_FIXED_CREDIT_VALUE with the fixed credit value
 * To Set multiple credit options, set the ResultComponentInfo type to
 * CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE and add each credit as a numeric ResultValue on the
 * ResultComponentInfo for each credit you desire
 * 
 * @author Kuali Student Team
 */
public class CourseServiceImpl implements CourseService {

    final static Logger LOG = Logger.getLogger(CourseServiceImpl.class);

    private CluService cluService;
    private CourseAssembler courseAssembler;
    private BusinessServiceMethodInvoker courseServiceMethodInvoker;
    private DictionaryService dictionaryServiceDelegate;
    private ValidatorFactory validatorFactory;
    private StatementService statementService;

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public CourseInfo createCourse(CourseInfo courseInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        checkForMissingParameter(courseInfo, "CourseInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateCourse("OBJECT", courseInfo, contextInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
            return processCourseInfo(courseInfo, NodeOperation.CREATE, contextInfo);
        } catch (AssemblyException e) {
            LOG.error("Error disassembling course", e);
            throw new OperationFailedException("Error disassembling course");
        } catch (Exception e) {
            LOG.error("Error disassembling course", e);
            throw new OperationFailedException("Error disassembling course");
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public CourseInfo updateCourse(String courseId, CourseInfo courseInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException, UnsupportedActionException, DependentObjectsExistException, AlreadyExistsException, CircularRelationshipException, CircularReferenceException, ReadOnlyException {

        checkForMissingParameter(courseInfo, "CourseInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateCourse("OBJECT", courseInfo, contextInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {

            return processCourseInfo(courseInfo, NodeOperation.UPDATE, contextInfo);

        } catch (VersionMismatchException vme) {
            // Re-instantiate this exception with more descriptive error.
            throw new VersionMismatchException("Course to be updated is not the current version.");

        } catch (AssemblyException e) {
            LOG.error("Error disassembling course", e);
            throw new OperationFailedException("Error disassembling course");
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, AlreadyExistsException, UnsupportedActionException, DependentObjectsExistException, CircularRelationshipException, CircularReferenceException, ReadOnlyException {

        try {
            CourseInfo course = getCourse(courseId, contextInfo);

            processCourseInfo(course, NodeOperation.DELETE, contextInfo);

            StatusInfo status = new StatusInfo();
            status.setSuccess(true);
            return status;

        } catch (AssemblyException e) {
            LOG.error("Error disassembling course", e);
            throw new OperationFailedException("Error disassembling course");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CourseInfo getCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        CluInfo clu = cluService.getClu(courseId, contextInfo);

        CourseInfo course;

        try {
            course = courseAssembler.assemble(clu, null, false, contextInfo);

        } catch (AssemblyException e) {
            LOG.error("Error assembling course", e);
            throw new OperationFailedException("Error assembling course");
        }

        return course;

    }

    @Transactional(readOnly = true)
    public List<ActivityInfo> getCourseActivities(String formatId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("GetCourseActivities");
    }

    @Transactional(readOnly = true)
    public List<FormatInfo> getCourseFormats(String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("GetCourseFormats");
    }

    @Transactional(readOnly = true)
    public List<LoDisplayInfo> getCourseLos(String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("GetCourseLos");
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementTreeViewInfo> getCourseStatements(String courseId, String nlUsageTypeKey, String language, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(courseId, "courseId");

        CluInfo clu = cluService.getClu(courseId, contextInfo);
        if (!CourseAssemblerConstants.COURSE_TYPE.equals(clu.getTypeKey())) {
            throw new DoesNotExistException("Specified CLU is not a Course");
        }
        List<RefStatementRelationInfo> relations = R1R2ConverterUtil.convertLists(statementService.getRefStatementRelationsByRef(CourseAssemblerConstants.COURSE_TYPE, clu.getId()), RefStatementRelationInfo.class);
        if (relations == null) {
            return new ArrayList<StatementTreeViewInfo>(0);
        }

        List<StatementTreeViewInfo> tree = new ArrayList<StatementTreeViewInfo>(relations.size());
        for (RefStatementRelationInfo relation : relations) {
            tree.add(statementService.getStatementTreeView(relation.getStatementId()));
        }
        return tree;
    }

    @Transactional(readOnly = true)
    public List<ValidationResultInfo> validateCourse(String validationType, CourseInfo courseInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {

        ObjectStructureDefinition objStructure = this.getObjectStructure(CourseInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(courseInfo, objStructure, contextInfo);
        return validationResults;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatementTreeViewInfo createCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        checkForMissingParameter(courseId, "courseId");
        checkForMissingParameter(statementTreeViewInfo, "statementTreeViewInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateCourseStatement(courseId, statementTreeViewInfo, contextInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        if (findStatementReference(courseId, statementTreeViewInfo, contextInfo) != null) {
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
            statementService.createRefStatementRelation(R1R2ConverterUtil.convert(relation, org.kuali.student.r1.core.statement.dto.RefStatementRelationInfo.class));
        } catch (Exception e) {
            throw new OperationFailedException("Unable to create clu/tree relation", e);
        }
        return statementTreeViewInfo;
    }

	@Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(courseId, "courseId");
        checkForMissingParameter(statementTreeViewInfo, "statementTreeViewInfo");

        RefStatementRelationInfo relation = findStatementReference(courseId, statementTreeViewInfo, contextInfo);
        if (relation != null) {
            statementService.deleteRefStatementRelation(relation.getId());
            statementService.deleteStatementTreeView(statementTreeViewInfo.getId());
            StatusInfo result = new StatusInfo();
            return result;
        }

        throw new DoesNotExistException("Course does not have this StatemenTree");
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatementTreeViewInfo updateCourseStatement(String courseId, String statementId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        checkForMissingParameter(courseId, "courseId");
        checkForMissingParameter(statementTreeViewInfo, "statementTreeViewInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateCourseStatement(courseId, statementTreeViewInfo, contextInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        if (findStatementReference(courseId, statementTreeViewInfo, contextInfo) == null) {
            throw new InvalidParameterException("Statement is not part of this course");
        }

        try {
            return statementService.updateStatementTreeView(statementTreeViewInfo.getId(), statementTreeViewInfo);
        } catch (CircularReferenceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<ValidationResultInfo> validateCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(courseId, "courseId");
        checkForMissingParameter(statementTreeViewInfo, "statementTreeViewInfo");

        try {
            CluInfo clu = cluService.getClu(courseId, contextInfo);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException("course does not exist");
        }

        ObjectStructureDefinition objStructure = this.getObjectStructure(StatementTreeViewInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(statementTreeViewInfo, objStructure, contextInfo);
        return validationResults;
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey){
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    @Override
    public List<String> getObjectTypes(){
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

    private CourseInfo processCourseInfo(CourseInfo courseInfo, NodeOperation operation, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException, AssemblyException, UnsupportedActionException, DependentObjectsExistException, AlreadyExistsException, CircularRelationshipException, CircularReferenceException, ReadOnlyException {

        BaseDTOAssemblyNode<CourseInfo, CluInfo> results = courseAssembler.disassemble(courseInfo, operation, contextInfo);

        // Use the results to make the appropriate service calls here
        courseServiceMethodInvoker.invokeServiceCalls(results, contextInfo);

        return results.getBusinessDTORef();
    }

    public ValidatorFactory getValidatorFactory() {
        return validatorFactory;
    }

    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    public CluService getCluService() {
        return cluService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public StatementService getStatementService() {
        return statementService;
    }

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }

	@Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public CourseInfo createNewCourseVersion(String versionIndCourseId, String versionComment, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {

        // step one, get the original course
        VersionDisplayInfo currentVersion = cluService.getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, versionIndCourseId, contextInfo);
        CourseInfo originalCourse = getCourse((String) currentVersion.getId(), contextInfo);

        // Version the Clu
        CluInfo newVersionClu = cluService.createNewCluVersion(versionIndCourseId, versionComment, contextInfo);

        try {
            BaseDTOAssemblyNode<CourseInfo, CluInfo> results;

            // Clear Ids from the original course
            CourseServiceUtils.resetIds(originalCourse);

            // Integrate changes into the original course. (should this just be just the id?)
            courseAssembler.assemble(newVersionClu, originalCourse, true, contextInfo);

            // Clear dates since they need to be set anyway
            originalCourse.setStartTerm(null);
            originalCourse.setEndTerm(null);

            // Disassemble the new course
            results = courseAssembler.disassemble(originalCourse, NodeOperation.UPDATE, contextInfo);

            // Use the results to make the appropriate service calls here
            courseServiceMethodInvoker.invokeServiceCalls(results, contextInfo);

            // copy statements
            CourseServiceUtils.copyStatements((String) currentVersion.getId(), results.getBusinessDTORef().getId(), results.getBusinessDTORef().getStateKey(), statementService, cluService, this, contextInfo);

            return results.getBusinessDTORef();

        } catch (AlreadyExistsException e) {
            throw new OperationFailedException("Error creating new course version", e);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException("Error creating new course version", e);
        } catch (CircularRelationshipException e) {
            throw new OperationFailedException("Error creating new course version", e);
        } catch (UnsupportedActionException e) {
            throw new OperationFailedException("Error creating new course version", e);
        } catch (AssemblyException e) {
            throw new OperationFailedException("Error creating new course version", e);
        } catch (UnsupportedOperationException e) {
            throw new OperationFailedException("Error creating new course version", e);
        } catch (CircularReferenceException e) {
            throw new OperationFailedException("Error creating new course version", e);
        }

    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo setCurrentCourseVersion(String courseVersionId, Date currentVersionStart, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return cluService.setCurrentCluVersion(courseVersionId, currentVersionStart, contextInfo);
    }

    @Transactional(readOnly = true)
    public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (CourseServiceConstants.COURSE_NAMESPACE_URI.equals(refObjectTypeURI)) {
            return cluService.getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, refObjectId, contextInfo);
        }
        throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
    }

    @Transactional(readOnly = true)
    public VersionDisplayInfo getCurrentVersionOnDate(String refObjectTypeURI, String refObjectId, Date date, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (CourseServiceConstants.COURSE_NAMESPACE_URI.equals(refObjectTypeURI)) {
            return cluService.getCurrentVersionOnDate(CluServiceConstants.CLU_NAMESPACE_URI, refObjectId, date, contextInfo);
        }
        throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
    }

    @Transactional(readOnly = true)
    public VersionDisplayInfo getFirstVersion(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (CourseServiceConstants.COURSE_NAMESPACE_URI.equals(refObjectTypeURI)) {
            return cluService.getFirstVersion(CluServiceConstants.CLU_NAMESPACE_URI, refObjectId, contextInfo);
        }
        throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");

    }

    @Transactional(readOnly = true)
    public VersionDisplayInfo getLatestVersion(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (CourseServiceConstants.COURSE_NAMESPACE_URI.equals(refObjectTypeURI)) {
            return cluService.getLatestVersion(CluServiceConstants.CLU_NAMESPACE_URI, refObjectId, contextInfo);
        }
        throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");

    }

    @Transactional(readOnly = true)
    public VersionDisplayInfo getVersionBySequenceNumber(String refObjectTypeURI, String refObjectId, Long sequence, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (CourseServiceConstants.COURSE_NAMESPACE_URI.equals(refObjectTypeURI)) {
            return cluService.getVersionBySequenceNumber(CluServiceConstants.CLU_NAMESPACE_URI, refObjectId, sequence, contextInfo);
        }
        throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
    }

    @Transactional(readOnly = true)
    public List<VersionDisplayInfo> getVersions(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (CourseServiceConstants.COURSE_NAMESPACE_URI.equals(refObjectTypeURI)) {
            return cluService.getVersions(CluServiceConstants.CLU_NAMESPACE_URI, refObjectId, contextInfo);
        }
        throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
    }

    @Transactional(readOnly = true)
    public List<VersionDisplayInfo> getVersionsInDateRange(String refObjectTypeURI, String refObjectId, Date from, Date to, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (CourseServiceConstants.COURSE_NAMESPACE_URI.equals(refObjectTypeURI)) {
            return cluService.getVersionsInDateRange(CluServiceConstants.CLU_NAMESPACE_URI, refObjectId, from, to, contextInfo);
        }
        throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
    }

    /**
     * Check for missing parameter and throw localized exception if missing
     * 
     * @param param
     * @param paramName
     * @throws MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName) throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

    /**
     * @param courseId
     * @param statementTreeViewInfo
     * @return reference exists
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    private RefStatementRelationInfo findStatementReference(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, DoesNotExistException {
        List<RefStatementRelationInfo> course = R1R2ConverterUtil.convertLists(statementService.getRefStatementRelationsByRef(CourseAssemblerConstants.COURSE_TYPE, courseId), RefStatementRelationInfo.class);
        if (course != null) {
            for (RefStatementRelationInfo refRelation : course) {
                if (refRelation.getStatementId().equals(statementTreeViewInfo.getId())) {
                    return refRelation;
                }
            }
        }
        return null;
    }

    @Override
    public List<CourseInfo> getCoursesByIds(List<String> courseIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO KSCM-429
        throw new UnsupportedOperationException("getCoursesByIds");
        // To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForCourseIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO KSCM-429
        throw new UnsupportedOperationException("searchForCourseIds");
        // To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<CourseInfo> searchForCourses(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO KSCM-429
        throw new UnsupportedOperationException("searchForCourses");
        // To change body of implemented methods use File | Settings | File Templates.
    }

    // TODO KSCM-429 Service Method Comparison Implementation
    @Override
    public List<FormatInfo> getCourseFormatsByCourse(String courseId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getCourseFormats(courseId);
    }

    // TODO KSCM-429 Service Method Comparison Implementation
    @Override
    public List<ActivityInfo> getCourseActivitiesByCourseFormat(String formatId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getCourseActivities(formatId);
    }

    // TODO KSCM-429 replaced implementation with ENR
    @Override
    public List<LoDisplayInfo> getCourseLearningObjectivesByCourse(String courseId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getCourseLos(courseId);
    }

}
