package org.kuali.student.enrollment.classII.acal.service.decorators;

import java.util.List;

import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarServiceDecorator;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.HoldsValidator;


public class AcademicCalendarServiceValidationDecorator extends AcademicCalendarServiceDecorator  implements HoldsValidator{
    private DataDictionaryValidator validator;
    
    @Override
    public DataDictionaryValidator getValidator() {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        
    }

    @Override
    public List<ValidationResultInfo> validateAcademicCalendar(String validationType,
            AcademicCalendarInfo acalInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        try{
            this.validator.validate(DataDictionaryValidator.ValidationType.fromString(validationType), acalInfo, context);
        }catch(PermissionDeniedException ex){
            throw new OperationFailedException();
        }
        return super.validateAcademicCalendar(validationType, acalInfo, context);
    }
    
}
