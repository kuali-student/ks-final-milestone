package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.jws.WebParam;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CourseOfferingServiceValidationDecorator
        extends CourseOfferingServiceDecorator
        implements HoldsValidator {
    // validator property w/getter & setter
    private DataDictionaryValidator validator;
    private TypeService typeService;
    private CluService cluService;

    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public CourseOfferingInfo createCourseOffering(String courseId, String termId, String courseOfferingTypeKey, CourseOfferingInfo courseOfferingInfo, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create 
        try {
            List<ValidationResultInfo> errors =
                    this.validateCourseOffering(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            courseOfferingInfo, context);
            boolean errorCheck = checkForErrors(errors);
            if (errorCheck) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }

            // Check for uniqueness in the course offering code
            List<CourseOfferingInfo> existingCos;
            try {
                existingCos = getCourseOfferingsByCourseAndTerm(courseOfferingInfo.getCourseId(), courseOfferingInfo.getTermId(), context);
            } catch (PermissionDeniedException e) {
                throw new OperationFailedException("Error validating", e);
            }
            for (CourseOfferingInfo existingCo : existingCos) {

                if (StringUtils.equals(existingCo.getCourseOfferingCode(), courseOfferingInfo.getCourseOfferingCode())) {
                    ValidationResultInfo validationResult = new ValidationResultInfo();
                    validationResult.setError(CourseOfferingServiceConstants.COURSE_OFFERING_CODE_UNIQUENESS_VALIDATION_MESSAGE);
                    validationResult.setElement(CourseOfferingServiceConstants.COURSE_OFFERING_CODE_VALIDATION_ELEMENT);
                    errors.add(validationResult);
                    break;
                }
            }

        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createCourseOffering(courseId, termId, courseOfferingTypeKey, courseOfferingInfo, optionKeys,
                context);
    }

    @Override
    public CourseOfferingInfo updateCourseOffering(String courseOfferingId, CourseOfferingInfo courseOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateCourseOffering(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            courseOfferingInfo, context);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateCourseOffering(courseOfferingId, courseOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(String validationType, CourseOfferingInfo courseOfferingInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationType, courseOfferingInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateCourseOffering(validationType,
                    courseOfferingInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }

        return errors;
    }

    @Override
    public FormatOfferingInfo createFormatOffering(String courseOfferingId, String formatId, String formatOfferingType, FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        _initServices();
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateFormatOffering(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            formatOfferingInfo, context);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
            _checkFormatOfferingForValidAOTypes(formatOfferingInfo, context);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createFormatOffering(courseOfferingId, formatId, formatOfferingType, formatOfferingInfo, context);
    }

    private void _checkFormatOfferingForValidAOTypes(FormatOfferingInfo fo, ContextInfo context)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, DoesNotExistException {
        SearchRequestInfo request = new SearchRequestInfo("lu.search.relatedTypes");
        request.addParam("lu.queryParam.cluId", fo.getFormatId());
        request.addParam("lu.queryParam.luOptionalRelationType", "luLuRelationType.contains");
        SearchResultInfo result = cluService.search(request, context);
        List<SearchResultRowInfo> rows = result.getRows();
        List<String> activityKeys = new ArrayList<String>();
        for (SearchResultRowInfo row: rows) {
            List<SearchResultCellInfo> cells = row.getCells();
            for (SearchResultCellInfo cell: cells) {
                if (cell.getKey().equals("lu.resultColumn.cluType")) {
                    activityKeys.add(cell.getValue());
                    break; // Found the key, so break
                }
            }
        }
        if (activityKeys.isEmpty()) {
            // Must have some activity keys
            throw new DataValidationErrorException("Missing Activity keys for FO(" + fo.getId() + ")");
        }
        List<String> aoTypes = fo.getActivityOfferingTypeKeys();
        if (aoTypes.isEmpty()) {
            throw new DataValidationErrorException("Missing Activity Offering type keys for FO(" + fo.getId() + ")");
        }
        if (aoTypes.size() != activityKeys.size()) {
            throw new DataValidationErrorException("Number of type keys do not match: Activity types (" + activityKeys.size() +
                                ") vs AO types (" + aoTypes.size() + ")");
        }
        Set<String> usedActivtyTypeKeys = new HashSet<String>(); // To avoid duplication of Activity keys
        for (String aoTypeKey: aoTypes) {
            List<TypeTypeRelationInfo> typeTypeRelationInfos =
                typeService.getTypeTypeRelationsByRelatedTypeAndType(aoTypeKey, TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, context);
            if (typeTypeRelationInfos == null || typeTypeRelationInfos.size() != 1) {
                int size = 0;
                if (typeTypeRelationInfos != null) {
                    size = typeTypeRelationInfos.size();
                }
                throw new DataValidationErrorException("There should be exactly one Activity type for AO type (" + aoTypeKey
                                    + ").  There are " + size + " Activity types instead");
            }
            String foundActivityType = typeTypeRelationInfos.get(0).getOwnerTypeKey();
            if (usedActivtyTypeKeys.contains(foundActivityType)) {
                throw new DataValidationErrorException("There should be 1-1 relation between Activity types and AO types. " +
                        "The activity type, " + foundActivityType + " has been seen before.");
            }

            if (!activityKeys.contains(foundActivityType)) {
                throw new DataValidationErrorException("The activity type found (" + foundActivityType + ") for AO type (" +
                        aoTypeKey + ") is not found in the CLU activity types");
            }
            usedActivtyTypeKeys.add(foundActivityType);
        }
        // If it gets to this point, then the types should match
    }

    @Override
    public FormatOfferingInfo updateFormatOffering(String formatOfferingId, FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateFormatOffering(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            formatOfferingInfo, context);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateFormatOffering(formatOfferingId, formatOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateFormatOffering(String validationType, FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationType, formatOfferingInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateFormatOffering(validationType,
                    formatOfferingInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public ActivityOfferingInfo createActivityOffering(String formatOfferingId, String activityId, String activityOfferingTypeKey, ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateActivityOffering(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            activityOfferingInfo, context);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createActivityOffering(formatOfferingId, activityId, activityOfferingTypeKey,
                activityOfferingInfo, context);
    }

    @Override
    public ActivityOfferingInfo updateActivityOffering(String activityOfferingId, ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateActivityOffering(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            activityOfferingInfo, context);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateActivityOffering(activityOfferingId, activityOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(String validationType, ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationType, activityOfferingInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateActivityOffering(validationType,
                    activityOfferingInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public RegistrationGroupInfo createRegistrationGroup(String formatOfferingId, String activityOfferingClusterId, String registrationGroupType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create 
        try {
            List<ValidationResultInfo> errors =
                    this.validateRegistrationGroup(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            activityOfferingClusterId,
                            registrationGroupType,
                            registrationGroupInfo, context);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createRegistrationGroup(formatOfferingId, activityOfferingClusterId, registrationGroupType, registrationGroupInfo, context);
    }

    @Override
    public RegistrationGroupInfo updateRegistrationGroup(String registrationGroupId, RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateRegistrationGroup(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            null,
                            null,
                            registrationGroupInfo, context);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateRegistrationGroup(registrationGroupId, registrationGroupInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(String validationType, String activityOfferingClusterId, String registrationGroupType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationType, registrationGroupInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateRegistrationGroup(validationType,
                    activityOfferingClusterId, registrationGroupType,
                    registrationGroupInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    // RegGroupTemplates are out of scope for M4
//	@Override
//	public ActivityOfferingClusterInfo updateRegistrationGroupTemplate(String registrationGroupTemplateId, ActivityOfferingClusterInfo registrationGroupTemplateInfo, ContextInfo context)
//		throws DataValidationErrorException
//		      ,DoesNotExistException
//		      ,InvalidParameterException
//		      ,MissingParameterException
//		      ,OperationFailedException
//		      ,PermissionDeniedException
//		      ,ReadOnlyException
//		      ,VersionMismatchException
//	{
//		// update
//		try {
//		    List<ValidationResultInfo> errors = 
//		      this.validateRegistrationGroupTemplate(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), registrationGroupTemplateInfo, context);
//		    if (checkForErrors(errors)) {
//		       throw new DataValidationErrorException("Error(s) occurred validating", errors);
//		    }
//		} catch (DoesNotExistException ex) {
//		    throw new OperationFailedException("Error validating", ex);
//		}
//		return getNextDecorator().updateRegistrationGroupTemplate(registrationGroupTemplateId, registrationGroupTemplateInfo, context);
//	}
    @Override
    public SeatPoolDefinitionInfo createSeatPoolDefinition(SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        // create 
        try {
            List<ValidationResultInfo> errors =
                    this.validateSeatPoolDefinition(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            seatPoolDefinitionInfo, context);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createSeatPoolDefinition(seatPoolDefinitionInfo, context);
    }

    @Override
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(String seatPoolDefinitionId, SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateSeatPoolDefinition(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                            seatPoolDefinitionInfo, context);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateSeatPoolDefinition(seatPoolDefinitionId, seatPoolDefinitionInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateSeatPoolDefinition(String validationTypeKey, SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateTypeKey(seatPoolDefinitionInfo.getTypeKey(), CourseOfferingServiceConstants.REF_OBJECT_URI_SEAT_POOL_DEFINITION,getTypeService(),context);
            errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, seatPoolDefinitionInfo, context));
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateSeatPoolDefinition(validationTypeKey,
                    seatPoolDefinitionInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public StatusInfo addSeatPoolDefinitionToActivityOffering(String seatPoolDefinitionId, String activityOfferingId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // gets a list of existing seatpools for this AO
        List<SeatPoolDefinitionInfo> existingSeatPools = getNextDecorator().getSeatPoolDefinitionsForActivityOffering(activityOfferingId,contextInfo);

        // pull the new seat pool from the db. (note, a seat pool has to exist prior to this call )
        SeatPoolDefinitionInfo newSeatPool = getNextDecorator().getSeatPoolDefinition(seatPoolDefinitionId,contextInfo);  // this must already be in the database.


        // Check for duplicate populations
        for(SeatPoolDefinitionInfo seatPool : existingSeatPools){
             String newPopId = newSeatPool.getPopulationId();
             if(newPopId != null && newPopId.equals(seatPool.getPopulationId())){
                 throw new AlreadyExistsException("Unable to addSeatPoolDefinitionToActivityOffering. Cannot have duplicate populations. ActitiyOffreingId["+ activityOfferingId +"] : SeatPoolId["+ seatPool.getId() +"] : PopulationId["+ seatPool.getPopulationId() +"]");
             }
        }

        return getNextDecorator().addSeatPoolDefinitionToActivityOffering(seatPoolDefinitionId, activityOfferingId, contextInfo);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteFormatOffering(String formatOfferingId,
                                           ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            DependentObjectsExistException {

        // check for activities
        List<ActivityOfferingInfo> activities = getActivityOfferingsByFormatOffering(formatOfferingId, context);

        if (activities.size() > 0)
            throw new DependentObjectsExistException("Activity Offerings Exist that refer to FormatOfferingId = " + formatOfferingId);

        // check for reg groups
        List<RegistrationGroupInfo> rgs = getRegistrationGroupsByFormatOffering(formatOfferingId, context);

        if (rgs.size() > 0)
            throw new DependentObjectsExistException("Registration Groups Exist that refer to Format Offering Id = " + formatOfferingId);

        // check for seat pools
        for (ActivityOfferingInfo activityOfferingInfo : activities) {

            List<SeatPoolDefinitionInfo> spls = getSeatPoolDefinitionsForActivityOffering(activityOfferingInfo.getId(), context);

            if (spls.size() > 0) {
                throw new DependentObjectsExistException("SeatPoolDefinitions Exist that refer to Format Offering Id = " + formatOfferingId);
            }


        }

        return super.deleteFormatOffering(formatOfferingId, context);
    }

    @Override
    public StatusInfo deleteCourseOffering(String courseOfferingId,
                                           ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            DependentObjectsExistException {

        List<FormatOfferingInfo> formats = getFormatOfferingsByCourseOffering(courseOfferingId, context);

        if (formats.size() > 0)
            throw new DependentObjectsExistException("Formats exist for course offering with id = " + courseOfferingId);

        List<ActivityOfferingInfo> activities = getActivityOfferingsByCourseOffering(courseOfferingId, context);

        if (activities.size() > 0)
            throw new DependentObjectsExistException("Activities exist for course offering with id = " + courseOfferingId);

        List<RegistrationGroupInfo> registrationGroups = getRegistrationGroupsForCourseOffering(courseOfferingId, context);

        if (registrationGroups.size() > 0)
            throw new DependentObjectsExistException("RegistrationGroups exist for course offering with id = " + courseOfferingId);

        return getNextDecorator().deleteCourseOffering(courseOfferingId, context);
    }

    @Override
    public StatusInfo deleteActivityOffering(String activityOfferingId,
                                             ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            DependentObjectsExistException {

        ActivityOfferingInfo offering = getActivityOffering(activityOfferingId, context);

        // check for reg groups
        List<RegistrationGroupInfo> regGroups = getRegistrationGroupsByFormatOffering(offering.getFormatOfferingId(), context);

        for (RegistrationGroupInfo rg : regGroups) {

            if (rg.getActivityOfferingIds().contains(activityOfferingId))
                throw new DependentObjectsExistException("Registration Groups Exist that refer to ActivityOfferingId = " + activityOfferingId);

        }

        // check for seat pools
        List<SeatPoolDefinitionInfo> seatPools = getSeatPoolDefinitionsForActivityOffering(activityOfferingId, context);

        if (seatPools.size() > 0)
            throw new DependentObjectsExistException("Seat Pools Exist that refer to ActivityOfferingId = " + activityOfferingId);

        return getNextDecorator().deleteActivityOffering(activityOfferingId, context);
    }

    @Override
    public List<ValidationResultInfo> validateActivityOfferingCluster(
            String validationTypeKey, String formatOfferingId,
            ActivityOfferingClusterInfo activityOfferingClusterInfo,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateTypeKey(activityOfferingClusterInfo.getTypeKey(),CourseOfferingServiceConstants.REF_OBJECT_URI_AO_CLUSTER_DEFINITION, getTypeService(), contextInfo);
            errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, activityOfferingClusterInfo, contextInfo));

            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateActivityOfferingCluster(validationTypeKey, formatOfferingId, activityOfferingClusterInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;

    }

    @Override
    public ActivityOfferingClusterInfo createActivityOfferingCluster(
            String formatOfferingId, String activityOfferingClusterTypeKey,
            ActivityOfferingClusterInfo activityOfferingClusterInfo,
            ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {

        try {
            List<ValidationResultInfo> errors =
                    this.validateActivityOfferingCluster(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), formatOfferingId, activityOfferingClusterInfo, contextInfo);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }

        return getNextDecorator().createActivityOfferingCluster(formatOfferingId,
                activityOfferingClusterTypeKey, activityOfferingClusterInfo,
                contextInfo);
    }

    @Override
    public ActivityOfferingClusterInfo updateActivityOfferingCluster(
            String formatOfferingId, String activityOfferingClusterId,
            ActivityOfferingClusterInfo activityOfferingClusterInfo,
            ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException,
            VersionMismatchException {

        try {
            List<ValidationResultInfo> errors =
                    this.validateActivityOfferingCluster(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), formatOfferingId, activityOfferingClusterInfo, contextInfo);
            if (checkForErrors(errors)) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }

        return getNextDecorator().updateActivityOfferingCluster(formatOfferingId,
                activityOfferingClusterId, activityOfferingClusterInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteActivityOfferingCluster(
            String activityOfferingClusterId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, DependentObjectsExistException {


        List<RegistrationGroupInfo> existingRegGroups = getRegistrationGroupsByActivityOfferingCluster(activityOfferingClusterId, context);

        if (existingRegGroups.size() > 0)
            throw new DependentObjectsExistException("Registration Groups Exist that refer to this ActivityOfferingClusterId = " + activityOfferingClusterId);


        return getNextDecorator().deleteActivityOfferingCluster(activityOfferingClusterId, context);
    }

    /* TODOSSR
    @Override
    public List<ValidationResultInfo> validateColocatedOfferingSet(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "colocatedOfferingSetTypeKey") String colocatedOfferingSetTypeKey, @WebParam(name = "colocatedOfferingSetInfo") ColocatedOfferingSetInfo colocatedOfferingSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, colocatedOfferingSetInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateColocatedOfferingSet(validationTypeKey,colocatedOfferingSetTypeKey,colocatedOfferingSetInfo,contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }

        return errors;
    }

    @Override
    public ColocatedOfferingSetInfo createColocatedOfferingSet(@WebParam(name = "colocatedOfferingSetTypeKey") String colocatedOfferingSetTypeKey, @WebParam(name = "colocatedOfferingSetInfo") ColocatedOfferingSetInfo colocatedOfferingSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        List<ValidationResultInfo> errors = this.validateColocatedOfferingSet(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), colocatedOfferingSetTypeKey, colocatedOfferingSetInfo, contextInfo);
        boolean errorCheck = checkForErrors(errors);
        if (errorCheck) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }
        return getNextDecorator().createColocatedOfferingSet(colocatedOfferingSetTypeKey, colocatedOfferingSetInfo, contextInfo);
    }

    @Override
    public ColocatedOfferingSetInfo updateColocatedOfferingSet(@WebParam(name = "colocatedOfferingSetId") String colocatedOfferingSetId, @WebParam(name = "colocatedOfferingSetInfo") ColocatedOfferingSetInfo colocatedOfferingSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        List<ValidationResultInfo> errors = this.validateColocatedOfferingSet(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), "", colocatedOfferingSetInfo, contextInfo);
        boolean errorCheck = checkForErrors(errors);
        if (errorCheck) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }
        return getNextDecorator().updateColocatedOfferingSet(colocatedOfferingSetId,colocatedOfferingSetInfo,contextInfo);
    }*/

    private static boolean checkForErrors(List<ValidationResultInfo> errors) {

        if (errors != null && !errors.isEmpty()) {
            for (ValidationResultInfo error : errors) {
                if (error.isError()) {
                    return true;
                }
            }
        }

        return false;
    }

    public void _initServices() {
        getCluService();
        getTypeService();
    }

    public CluService getCluService() {
        if (cluService == null){
            cluService = (CluService) GlobalResourceLoader.getService(new QName(CluServiceConstants.NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public TypeService getTypeService() {
        if(typeService == null){
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }
}
