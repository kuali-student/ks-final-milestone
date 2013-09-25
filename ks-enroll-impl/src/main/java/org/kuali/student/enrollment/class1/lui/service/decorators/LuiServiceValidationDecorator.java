package org.kuali.student.enrollment.class1.lui.service.decorators;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiSetInfo;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.jws.WebParam;
import javax.xml.namespace.QName;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 9/19/12
 * Time: 2:58 PM
 * Right now this just validates that the types used to create and update Lui's are in the DB. When updating luis the types cannot change. There need to be more checks in the future to ensure the the correct classes are being
 * assigned to the types.
 */
public class LuiServiceValidationDecorator extends LuiServiceDecorator {

    protected TypeService typeService = null;

    private DataDictionaryValidator validator;

    @Override
    public LuiInfo createLui(String cluId, String atpId, String luiTypeKey, LuiInfo luiInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        List<ValidationResultInfo> errors = this.validateLui(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),cluId,atpId,luiTypeKey,luiInfo,contextInfo);

        if(errors != null && !errors.isEmpty()){
            throw new DataValidationErrorException("Could not create lui because the type errors", errors);
        }

        return getNextDecorator().createLui(cluId,atpId,luiTypeKey,luiInfo,contextInfo);
    }

    @Override
    public LuiCapacityInfo createLuiCapacity(String luiCapacityTypeKey, LuiCapacityInfo luiCapacityInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        List<ValidationResultInfo> errors = this.validateLuiCapacity(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),luiCapacityTypeKey,luiCapacityInfo,contextInfo);

        if(errors != null && !errors.isEmpty()){
            throw new DataValidationErrorException("Could not create lui because the type errors", errors);
        }

        return getNextDecorator().createLuiCapacity(luiCapacityTypeKey,luiCapacityInfo,contextInfo);
    }

    @Override
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId, String luiLuiRelationTypeKey, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        List<ValidationResultInfo> errors = this.validateLuiLuiRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),luiId,relatedLuiId,luiLuiRelationTypeKey,luiLuiRelationInfo,contextInfo);

        if(errors != null && !errors.isEmpty()){
            throw new DataValidationErrorException("Could not create lui because the type errors", errors);
        }

        return getNextDecorator().createLuiLuiRelation(luiId,relatedLuiId,luiLuiRelationTypeKey,luiLuiRelationInfo,contextInfo);
    }

    @Override
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        LuiInfo oldLui = getNextDecorator().getLui(luiId,contextInfo);

        // types can never change on update.
        List<ValidationResultInfo> errors = ValidationUtils.validateTypesAreEqual(luiInfo, oldLui);

        if(errors != null && !errors.isEmpty()){
            throw new DataValidationErrorException("Could not create lui because the type errors", errors);
        }

        errors = this.validateLui(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), luiInfo.getCluId(), luiInfo.getAtpId(), luiInfo.getTypeKey(), luiInfo, contextInfo);
        if (ValidationUtils.checkForErrors(errors)) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }

        return getNextDecorator().updateLui(luiId,luiInfo,contextInfo);
    }


    @Override
    public LuiCapacityInfo updateLuiCapacity(String luiCapacityId, LuiCapacityInfo luiCapacityInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        LuiCapacityInfo oldLui = getNextDecorator().getLuiCapacity(luiCapacityId,contextInfo);

        // types can never change on update.
        List<ValidationResultInfo> errors = ValidationUtils.validateTypesAreEqual(luiCapacityInfo, oldLui);

        if(errors != null && !errors.isEmpty()){
            throw new DataValidationErrorException("Could not create lui because the type errors", errors);
        }

        errors = this.validateLuiCapacity(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), luiCapacityInfo.getTypeKey(), luiCapacityInfo, contextInfo);
        if (ValidationUtils.checkForErrors(errors)) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }

        return getNextDecorator().updateLuiCapacity(luiCapacityId,luiCapacityInfo,contextInfo);
    }

    @Override
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        LuiLuiRelationInfo oldLui = getNextDecorator().getLuiLuiRelation(luiLuiRelationId,contextInfo);

        // types can never change on update.
        List<ValidationResultInfo> errors = ValidationUtils.validateTypesAreEqual(luiLuiRelationInfo, oldLui);

        if(errors != null && !errors.isEmpty()){
            throw new DataValidationErrorException("Could not create lui because the type errors", errors);
        }

        errors = this.validateLuiLuiRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), luiLuiRelationInfo.getLuiId(), luiLuiRelationInfo.getRelatedLuiId(), luiLuiRelationInfo.getTypeKey(), luiLuiRelationInfo, contextInfo);
        if (ValidationUtils.checkForErrors(errors)) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }

        return getNextDecorator().updateLuiLuiRelation(luiLuiRelationId,luiLuiRelationInfo,contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLui(String validationTypeKey, String cluId, String atpId, String luiTypeKey, LuiInfo luiInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ValidationResultInfo> errors = ValidationUtils.validateTypeKey(luiTypeKey, LuiServiceConstants.REF_OBJECT_URI_LUI, getTypeService(), contextInfo);
        errors.addAll(getNextDecorator().validateLui(validationTypeKey,cluId,atpId,luiTypeKey,luiInfo, contextInfo));
        errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, luiInfo, contextInfo));

        return errors;
    }

    @Override
    public List<ValidationResultInfo> validateLuiSet(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "luiSetTypeKey") String luiSetTypeKey,
                                                     @WebParam(name = "LuiSetInfo") LuiSetInfo LuiSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> errors = ValidationUtils.validateTypeKey(luiSetTypeKey, LuiServiceConstants.REF_OBJECT_URI_LUI, getTypeService(), contextInfo);
        errors.addAll(getNextDecorator().validateLuiSet(validationTypeKey, luiSetTypeKey, LuiSetInfo, contextInfo));
        errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, LuiSetInfo, contextInfo));
        
        return errors;
    }

    @Override
    public List<ValidationResultInfo> validateLuiCapacity(String validationTypeKey, String luiCapacityTypeKey, LuiCapacityInfo luiCapacityInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> errors = ValidationUtils.validateTypeKey(luiCapacityTypeKey, LuiServiceConstants.REF_OBJECT_URI_LUI, getTypeService(), contextInfo);
        errors.addAll(getNextDecorator().validateLuiCapacity(validationTypeKey,luiCapacityTypeKey,luiCapacityInfo, contextInfo));
        errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, luiCapacityInfo, contextInfo));
        return errors;
    }

    @Override
    public List<ValidationResultInfo> validateLuiLuiRelation(String validationTypeKey, String luiId, String relatedLuiId, String luiLuiRelationTypeKey, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> errors = ValidationUtils.validateTypeKey(luiLuiRelationTypeKey, LuiServiceConstants.REF_OBJECT_URI_LUI_LUI_RELATION, getTypeService(), contextInfo);
        errors.addAll(getNextDecorator().validateLuiLuiRelation(validationTypeKey,luiId, relatedLuiId,luiLuiRelationTypeKey, luiLuiRelationInfo, contextInfo));
        errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, luiLuiRelationInfo, contextInfo));
        return errors;
    }

    @Override
    public LuiSetInfo createLuiSet(@WebParam(name = "luiSetTypeKey") String luiSetTypeKey, @WebParam(name = "luiSetInfo") LuiSetInfo luiSetInfo,
                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, UnsupportedActionException {
        List<ValidationResultInfo> errors = this.validateLuiSet(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),luiSetTypeKey,luiSetInfo,contextInfo);

        if(errors != null && !errors.isEmpty()){
            throw new DataValidationErrorException("Could not create lui set because the type errors", errors);
        }

        return getNextDecorator().createLuiSet(luiSetTypeKey,luiSetInfo,contextInfo);
    }

    @Override
    public LuiSetInfo updateLuiSet(@WebParam(name = "luiSetId") String luiSetId, @WebParam(name = "luiSetInfo") LuiSetInfo luiSetInfo,
                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException, VersionMismatchException {
        LuiSetInfo oldLuiSet = getNextDecorator().getLuiSet(luiSetId,contextInfo);

        // types can never change on update.
        List<ValidationResultInfo> errors = ValidationUtils.validateTypesAreEqual(luiSetInfo, oldLuiSet);

        if(errors != null && !errors.isEmpty()){
            throw new DataValidationErrorException("Could not update lui set because the type errors", errors);
        }

        return getNextDecorator().updateLuiSet(luiSetId,luiSetInfo,contextInfo);
    }

    public TypeService getTypeService() {
        if(typeService == null){
            typeService = (TypeService)GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public DataDictionaryValidator getValidator() {
        return validator;
    }

    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }
}
