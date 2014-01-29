/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by cmuller on 1/25/13
 */
package org.kuali.student.r2.core.room.service.decorators;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.constants.RoomServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomResponsibleOrgInfo;
import org.kuali.student.r2.core.room.service.RoomServiceDecorator;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * This class provides validation for Room Service methods
 *
 * @author Kuali Student Team
 */
public class RoomServiceValidationDecorator extends RoomServiceDecorator {

    private DataDictionaryValidator validator;
    private TypeService typeService;

    @Override
    public RoomInfo createRoom(String buildingId, String roomTypeKey, RoomInfo roomInfo, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateRoom(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), roomInfo.getBuildingId(), roomTypeKey, roomInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createRoom(buildingId, roomTypeKey, roomInfo, context);
    }

    @Override
    public RoomInfo updateRoom(String roomId, RoomInfo roomInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateRoom(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), roomInfo.getBuildingId(), roomInfo.getTypeKey(), roomInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateRoom(roomId, roomInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateRoom(String validationTypeKey, String buildingId, String roomTypeKey, RoomInfo roomInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateTypeKey(roomTypeKey, RoomServiceConstants.REF_OBJECT_URI_ROOM, getTypeService(), context);
            errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, roomInfo, context));
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateRoom(validationTypeKey, roomInfo.getBuildingId(), roomInfo.getTypeKey(), roomInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public BuildingInfo createBuilding(String buildingTypeKey, BuildingInfo buildingInfo, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateBuilding(buildingInfo.getTypeKey(), DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), buildingInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createBuilding(buildingTypeKey, buildingInfo, context);
    }

    @Override
    public BuildingInfo updateBuilding(String buildingId, BuildingInfo buildingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateBuilding(buildingInfo.getTypeKey(), DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), buildingInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateBuilding(buildingId, buildingInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateBuilding(String buildingTypeKey, String validationTypeKey, BuildingInfo buildingInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateTypeKey(buildingTypeKey, RoomServiceConstants.REF_OBJECT_URI_BUILDING, getTypeService(), context);
            errors.addAll(ValidationUtils.validateInfo(validator, validationTypeKey, buildingInfo, context));
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateBuilding(buildingTypeKey, validationTypeKey, buildingInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public RoomResponsibleOrgInfo createRoomResponsibleOrg(String roomId,  String orgId,  String roomResponsibleOrgTypeKey, RoomResponsibleOrgInfo roomResponsibleOrgInfo,  ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateRoomResponsibleOrg(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), roomResponsibleOrgInfo.getRoomId(), roomResponsibleOrgInfo.getOrgId(), roomResponsibleOrgInfo.getTypeKey(), roomResponsibleOrgInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createRoomResponsibleOrg(roomId, orgId, roomResponsibleOrgTypeKey, roomResponsibleOrgInfo, context);
    }

    @Override
    public RoomResponsibleOrgInfo updateRoomResponsibleOrg(String roomResponsibleOrgId, RoomResponsibleOrgInfo roomResponsibleOrgInfo,  ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateRoomResponsibleOrg(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), roomResponsibleOrgInfo.getRoomId(), roomResponsibleOrgInfo.getOrgId(), roomResponsibleOrgInfo.getTypeKey(), roomResponsibleOrgInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateRoomResponsibleOrg(roomResponsibleOrgId, roomResponsibleOrgInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateRoomResponsibleOrg(String validationTypeKey, String roomId, String orgId, String roomResponsibleTypeKey, RoomResponsibleOrgInfo roomResponsibleOrgInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, roomResponsibleOrgInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateRoomResponsibleOrg(validationTypeKey, roomId, orgId, roomResponsibleTypeKey, roomResponsibleOrgInfo, context);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    public DataDictionaryValidator getValidator() {
        return validator;
    }

    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
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
