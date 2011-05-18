package org.kuali.student.r2.core.class1.atp.service.decorators;

import java.util.List;

import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpServiceDecorator;

public class AtpServiceValidationDecorator extends AtpServiceDecorator  implements HoldsValidator{
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
    public List<ValidationResultInfo> validateAtp(
            String validationType,
            AtpInfo atpInfo,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        
        try{
            this.validator.validate(DataDictionaryValidator.ValidationType.fromString(validationType), atpInfo, context);
        }catch(PermissionDeniedException ex){
            throw new OperationFailedException();
        }
        
        return this.nextDecorator.validateAtp(validationType, atpInfo, context);

    }

    @Override
    public List<ValidationResultInfo> validateMilestone(String validationType, MilestoneInfo milestoneInfo, ContextInfo context) 
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        
        try{
            this.validator.validate(DataDictionaryValidator.ValidationType.fromString(validationType), milestoneInfo, context);
        } catch(PermissionDeniedException ex){
            throw new OperationFailedException();
        }
        
        return this.nextDecorator.validateMilestone(validationType, milestoneInfo, context);
    }
    
    

}
