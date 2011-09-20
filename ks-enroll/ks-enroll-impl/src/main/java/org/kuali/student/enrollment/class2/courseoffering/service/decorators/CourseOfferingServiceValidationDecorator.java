package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import java.util.List;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingServiceDecorator;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsDataDictionaryService;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.service.util.ValidationUtils;

public class CourseOfferingServiceValidationDecorator extends CourseOfferingServiceDecorator  implements HoldsValidator, HoldsDataDictionaryService{
	private DataDictionaryValidator validator;
	private DataDictionaryService dataDictionaryService;
	
    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;        
    }

	@Override
	public DataDictionaryService getDataDictionaryService() {
		return dataDictionaryService;
	}

	@Override
	public void setDataDictionaryService(
			DataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;		
	}
	
    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        return dataDictionaryService.getDataDictionaryEntry(entryKey, context);
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        return this.dataDictionaryService.getDataDictionaryEntryKeys(context);
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(
    		String validationType, CourseOfferingInfo courseOfferingInfo,
    		ContextInfo context) throws DoesNotExistException,
    		InvalidParameterException, MissingParameterException,
    		OperationFailedException {
    	List<ValidationResultInfo> errors;
    	try {
    		errors = ValidationUtils.validateInfo(validator, validationType, courseOfferingInfo, context);
    		List<ValidationResultInfo> nextDecoratorErrors =
    				getNextDecorator().validateCourseOffering(validationType, courseOfferingInfo, context);
    		if (null != nextDecoratorErrors) {
    			errors.addAll(nextDecoratorErrors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error trying to validate course offering", ex);
    	}
    	return errors;
    }
    
    private void _courseOfferingFullValidation(CourseOfferingInfo courseOfferingInfo, ContextInfo context)
	 		throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
		try {
		    List<ValidationResultInfo> errors =
                    this.validateCourseOffering(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), courseOfferingInfo, context);
		    if (!errors.isEmpty()) {
		        throw new DataValidationErrorException("Error(s) validating course offering", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating course offering", ex);
		}
    }
 
	
	@Override
	public CourseOfferingInfo updateCourseOffering(String courseOfferingId,
			CourseOfferingInfo courseOfferingInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		_courseOfferingFullValidation(courseOfferingInfo, context);
		return getNextDecorator().updateCourseOffering(courseOfferingId, courseOfferingInfo, context);
	}

}
