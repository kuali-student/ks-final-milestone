package org.kuali.student.enrollment.class1.lui.service.decorators;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

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

    @Override
    public LuiInfo createLui(String cluId, String atpId, String luiTypeKey, LuiInfo luiInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        List<ValidationResultInfo> errors = this.validateLui(ValidationUtils.TYPE_VALIDATION_TYPE_KEY,cluId,atpId,luiTypeKey,luiInfo,contextInfo);

        if(errors != null && !errors.isEmpty()){
            throw new DataValidationErrorException("Could not create lui because the type errors", errors);
        }

        return getNextDecorator().createLui(cluId,atpId,luiTypeKey,luiInfo,contextInfo);
    }

    @Override
    public LuiCapacityInfo createLuiCapacity(String luiCapacityTypeKey, LuiCapacityInfo luiCapacityInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        List<ValidationResultInfo> errors = this.validateLuiCapacity(ValidationUtils.TYPE_VALIDATION_TYPE_KEY,luiCapacityTypeKey,luiCapacityInfo,contextInfo);

        if(errors != null && !errors.isEmpty()){
            throw new DataValidationErrorException("Could not create lui because the type errors", errors);
        }

        return getNextDecorator().createLuiCapacity(luiCapacityTypeKey,luiCapacityInfo,contextInfo);
    }

    @Override
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId, String luiLuiRelationTypeKey, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        List<ValidationResultInfo> errors = this.validateLuiLuiRelation(ValidationUtils.TYPE_VALIDATION_TYPE_KEY,luiId,relatedLuiId,luiLuiRelationTypeKey,luiLuiRelationInfo,contextInfo);

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

        return getNextDecorator().updateLuiLuiRelation(luiLuiRelationId,luiLuiRelationInfo,contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLui(String validationTypeKey, String cluId, String atpId, String luiTypeKey, LuiInfo luiInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ValidationResultInfo> errors = ValidationUtils.validateTypeKey(luiTypeKey, getTypeService(), contextInfo);
        errors.addAll(getNextDecorator().validateLui(validationTypeKey,cluId,atpId,luiTypeKey,luiInfo, contextInfo));
        return errors;
    }

    @Override
    public List<ValidationResultInfo> validateLuiCapacity(String validationTypeKey, String luiCapacityTypeKey, LuiCapacityInfo luiCapacityInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> errors = ValidationUtils.validateTypeKey(luiCapacityTypeKey, getTypeService(), contextInfo);
        errors.addAll(getNextDecorator().validateLuiCapacity(validationTypeKey,luiCapacityTypeKey,luiCapacityInfo, contextInfo));
        return errors;
    }

    @Override
    public List<ValidationResultInfo> validateLuiLuiRelation(String validationTypeKey, String luiId, String relatedLuiId, String luiLuiRelationTypeKey, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> errors = ValidationUtils.validateTypeKey(luiLuiRelationTypeKey, getTypeService(), contextInfo);
        errors.addAll(getNextDecorator().validateLuiLuiRelation(validationTypeKey,luiId, relatedLuiId,luiLuiRelationTypeKey, luiLuiRelationInfo, contextInfo));
        return errors;
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
}
