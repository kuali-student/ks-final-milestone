package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import java.util.List;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.exceptions.*;

import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.class1.util.ValidationUtils;

public class CourseOfferingServiceValidationDecorator
        extends CourseOfferingServiceDecorator
        implements HoldsValidator {
    // validator property w/getter & setter
    private DataDictionaryValidator validator;

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
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
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
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateCourseOffering(courseOfferingId, courseOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(String validationType, CourseOfferingInfo courseOfferingInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
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
        // create 
        try {
            List<ValidationResultInfo> errors =
                    this.validateFormatOffering(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                    formatOfferingInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createFormatOffering(courseOfferingId, formatId, formatOfferingType, formatOfferingInfo, context);
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
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateFormatOffering(formatOfferingId, formatOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateFormatOffering(String validationType, FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
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
            if (!errors.isEmpty()) {
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
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateActivityOffering(activityOfferingId, activityOfferingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(String validationType, ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
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
    public RegistrationGroupInfo createRegistrationGroup(String formatOfferingId, String registrationGroupType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create 
        try {
            List<ValidationResultInfo> errors =
                    this.validateRegistrationGroup(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                    registrationGroupInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createRegistrationGroup(formatOfferingId, registrationGroupType, registrationGroupInfo, context);
    }

    @Override
    public RegistrationGroupInfo updateRegistrationGroup(String registrationGroupId, RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateRegistrationGroup(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                    registrationGroupInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateRegistrationGroup(registrationGroupId, registrationGroupInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(String validationType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationType, registrationGroupInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateRegistrationGroup(validationType,
                    registrationGroupInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    // RegGroupTemplates are out of scope for M4
//	@Override
//	public RegistrationGroupTemplateInfo updateRegistrationGroupTemplate(String registrationGroupTemplateId, RegistrationGroupTemplateInfo registrationGroupTemplateInfo, ContextInfo context)
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
//		    if (!errors.isEmpty()) {
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
            List<ValidationResultInfo> errors;
            try {
                errors = this.validateSeatPoolDefinition(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),
                        seatPoolDefinitionInfo, context);
            } catch (VersionMismatchException ex) {
                throw new OperationFailedException("Unexpected", ex);
            }
            if (!errors.isEmpty()) {
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
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateSeatPoolDefinition(seatPoolDefinitionId, seatPoolDefinitionInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateSeatPoolDefinition(String validationTypeKey, SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, seatPoolDefinitionInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateSeatPoolDefinition(validationTypeKey,
                    seatPoolDefinitionInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
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
			throw new DependentObjectsExistException("Formats exist for course offering with id = "+ courseOfferingId);
		
		List<ActivityOfferingInfo> activities = getActivityOfferingsByCourseOffering(courseOfferingId, context);
		
		if (activities.size() > 0)
			throw new DependentObjectsExistException("Activities exist for course offering with id = "+ courseOfferingId);
		
		List<RegistrationGroupInfo> registrationGroups = getRegistrationGroupsForCourseOffering(courseOfferingId, context);
		
		if (registrationGroups.size() > 0)
			throw new DependentObjectsExistException("RegistrationGroups exist for course offering with id = "+ courseOfferingId);
		
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
    
    
}
