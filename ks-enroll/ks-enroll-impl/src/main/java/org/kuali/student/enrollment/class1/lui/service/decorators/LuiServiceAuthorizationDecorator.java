/**
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.enrollment.class1.lui.service.decorators;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.api.permission.PermissionService;

import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiServiceDecorator;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import org.kuali.student.r2.common.infc.HoldsPermissionService;

import java.util.List;

public class LuiServiceAuthorizationDecorator 
    extends LuiServiceDecorator 
    implements HoldsPermissionService {

    public static final String ENRLLMENT_NAMESPACE = "KS-ENROLL";
    public static final String SERVICE_NAME = "LuiService.";
    
    private PermissionService permissionService;
    
    @Override
    public PermissionService getPermissionService() {
        return permissionService;
    }
    
    @Override
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    
    @Override
    public LuiInfo getLui(String luiId, ContextInfo context)
        throws DoesNotExistException, InvalidParameterException,
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {
        
        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLui", null)) {
            return getNextDecorator().getLui(luiId, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public List<LuiInfo> getLuisByIds(List<String> luiIds, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuisByIds", null)) {
            return getNextDecorator().getLuisByIds(luiIds, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public List<String> getLuiIdsByType(String luiTypeKey, ContextInfo context)
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiIdsByType", null)) {
            return getNextDecorator().getLuiIdsByType(luiTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public List<String> getLuiIdsByClu(String cluId, ContextInfo context)
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiIdsByClu", null)) {
            return getNextDecorator().getLuiIdsByClu(cluId, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public List<String> getLuiIdsByAtpAndClu(String cluId, String atpId, ContextInfo context) 
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiIdsByAtpAndClu", null)) {
            return getNextDecorator().getLuiIdsByAtpAndClu(cluId, atpId, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public List<LuiInfo> getLuisByAtpAndClu(String cluId, String atpId, ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuisByAtpAndClu", null)) {
            return getNextDecorator().getLuisByAtpAndClu(cluId, atpId, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public List<String> getLuiIdsByRelation(String relatedLuiId, String luiLuiRelationTypeKey, 
                                            ContextInfo context)
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiIdsByRelation", null)) {
            return getNextDecorator().getLuiIdsByRelation(relatedLuiId, luiLuiRelationTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

	@Override
    public List<LuiInfo> getLuisByRelation(String relatedLuiId,
                                           String luiLuiRelationTypeKey, 
                                           ContextInfo context)
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuisByRelation", null)) {
            return getNextDecorator().getLuisByRelation(relatedLuiId, luiLuiRelationTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public List<String> getRelatedLuiIdsByLui(String luiId,
                                              String luiLuiRelationTypeKey, 
                                              ContextInfo context)
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRelatedLuiIdsByLui", null)) {
            return getNextDecorator().getRelatedLuiIdsByLui(luiId, luiLuiRelationTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public List<LuiInfo> getRelatedLuisByLui(String luiId,
                                             String luiLuiRelationTypeKey, 
                                             ContextInfo context)
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRelatedLuisByLui", null)) {
            return getNextDecorator().getRelatedLuisByLui(luiId, luiLuiRelationTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public List<String> searchForLuiIds(QueryByCriteria criteria,
                                        ContextInfo context) 
        throws InvalidParameterException,
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForLuiIds", null)) {
            return getNextDecorator().searchForLuiIds(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }
    
    @Override
    public List<LuiInfo> searchForLuis(QueryByCriteria criteria,
                                       ContextInfo context)
        throws InvalidParameterException,
        MissingParameterException, OperationFailedException,
        PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForLuis", null)) {
            return getNextDecorator().searchForLuis(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }
    
    @Override
    public List<ValidationResultInfo> validateLui(String validationTypeKey, String cluId, 
                                                  String atpId, String luiTypeKey,
                                                  LuiInfo luiInfo, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateLui", null)) {
            return getNextDecorator().validateLui(validationTypeKey, cluId, atpId, luiTypeKey, luiInfo, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public LuiInfo createLui(String cluId, String atpId, String luiTypeKey, LuiInfo luiInfo,
                             ContextInfo context) 
        throws DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException,
               ReadOnlyException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createLui", null)) {
            return getNextDecorator().createLui(cluId, atpId, luiTypeKey, luiInfo, context);
        } else {
           throw new PermissionDeniedException();
        }
    }

    @Override
	public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
                   InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException,
                   ReadOnlyException, VersionMismatchException {
            
            if (null == context) {
                throw new MissingParameterException();
            }
            
            if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateLui", null)) {
	        return getNextDecorator().updateLui(luiId, luiInfo, context);
            } else {
                throw new PermissionDeniedException();
            }
	}
    
    @Override
    public StatusInfo deleteLui(String luiId, ContextInfo context)
        throws DependentObjectsExistException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteLui", null)) {
            return getNextDecorator().deleteLui(luiId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }
    
    @Override
    public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId, ContextInfo context) 
        throws DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiLuiRelation", null)) {
            return getNextDecorator().getLuiLuiRelation(luiLuiRelationId, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByIds(List<String> luiLuiRelationIds, 
                                                            ContextInfo context)
        throws DoesNotExistException, InvalidParameterException,
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiLuiRelationsByIds", null)) {
            return getNextDecorator().getLuiLuiRelationsByIds(luiLuiRelationIds, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public List<String> getLuiLuiRelationIdsByType(String luiLuiRelationTypeKey, 
                                                   ContextInfo context)
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiLuiRelationIdsByType", null)) {
            return getNextDecorator().getLuiLuiRelationIdsByType(luiLuiRelationTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId, ContextInfo context) 
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiLuiRelationsByLui", null)) {
            return getNextDecorator().getLuiLuiRelationsByLui(luiId, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public List<String> searchForLuiLuiRelationIds(QueryByCriteria criteria,
                                                   ContextInfo context) 
        throws InvalidParameterException,
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForLuiLuiRelationIds", null)) {
            return getNextDecorator().searchForLuiLuiRelationIds(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }
    
    @Override
    public List<LuiLuiRelationInfo> searchForLuiLuiRelations(QueryByCriteria criteria, 
                                                             ContextInfo context)
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForLuiLuiRelations", null)) {
            return getNextDecorator().searchForLuiLuiRelations(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }
    
    @Override
    public List<ValidationResultInfo> validateLuiLuiRelation(String validationTypeKey, String luiId,
                                                             String relatedLuiId, String luiRelationTypeKey,
                                                             LuiLuiRelationInfo luiLuiRelationInfo,
                                                             ContextInfo context) 
        throws DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateLuiLuiRelation", null)) {
            return getNextDecorator().validateLuiLuiRelation(validationTypeKey, luiId, relatedLuiId, luiRelationTypeKey, luiLuiRelationInfo, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId, 
                                                   String luiLuiRelationTypeKey,
                                                   LuiLuiRelationInfo luiLuiRelationInfo, 
                                                   ContextInfo context)
        throws CircularRelationshipException,
               DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException,
               ReadOnlyException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createLuiLuiRelation", null)) {
            return getNextDecorator().createLuiLuiRelation(luiId, relatedLuiId, luiLuiRelationTypeKey, luiLuiRelationInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }
    
    @Override
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId,
                                                   LuiLuiRelationInfo luiLuiRelationInfo, 
                                                   ContextInfo context)
        throws DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException,
               ReadOnlyException, VersionMismatchException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateLuiLuiRelation", null)) {
            return getNextDecorator().updateLuiLuiRelation(luiLuiRelationId, luiLuiRelationInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }
    
    @Override
    public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId, ContextInfo context) 
        throws DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteLuiLuiRelation", null)) {
            return getNextDecorator().deleteLuiLuiRelation(luiLuiRelationId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }
    
    @Override
    public LuiCapacityInfo getLuiCapacity(String luiCapacityId, ContextInfo context) 
        throws DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiCapacity", null)) {
            return getNextDecorator().getLuiCapacity(luiCapacityId, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByIds(List<String> luiCapacityIds, 
                                                       ContextInfo context)
        throws DoesNotExistException, InvalidParameterException,
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiCapacitiesByIds", null)) {
            return getNextDecorator().getLuiCapacitiesByIds(luiCapacityIds, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public List<String> getLuiCapacityIdsByType(String luiCapacityTypeKey,
                                                ContextInfo context) 
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiCapacityIdsByType", null)) {
            return getNextDecorator().getLuiCapacityIdsByType(luiCapacityTypeKey, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public List<String> searchForLuiCapacityIds(QueryByCriteria criteria,
                                                ContextInfo context) 
        throws InvalidParameterException,
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForLuiCapacityIds", null)) {
            return getNextDecorator().searchForLuiCapacityIds(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }
    
    @Override
    public List<LuiCapacityInfo> searchForLuiCapacities(QueryByCriteria criteria, 
                                                        ContextInfo context)
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForLuiCapacities", null)) {
            return getNextDecorator().searchForLuiCapacities(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }
    
    @Override
    public List<ValidationResultInfo> validateLuiCapacity(String validationTypeKey, 
                                                          String luiCapacityTypeKey,
                                                          LuiCapacityInfo luiCapacityInfo,
                                                          ContextInfo context) 
        throws DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateLuiCapacity", null)) {
            return getNextDecorator().validateLuiCapacity(validationTypeKey, luiCapacityTypeKey, luiCapacityInfo, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }
    
    @Override
    public LuiCapacityInfo createLuiCapacity(String luiCapacityTypeKey, LuiCapacityInfo luiCapacityInfo, 
                                             ContextInfo context) 
        throws DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException,
               ReadOnlyException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createLuiCapacity", null)) {
            return getNextDecorator().createLuiCapacity(luiCapacityTypeKey, luiCapacityInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }
    
    @Override
    public LuiCapacityInfo updateLuiCapacity(String luiCapacityId,
                                             LuiCapacityInfo luiCapacityInfo, 
                                             ContextInfo context)
        throws DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException,
               ReadOnlyException, VersionMismatchException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateLuiCapacity", null)) {
            return getNextDecorator().updateLuiCapacity(luiCapacityId, luiCapacityInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }
    
    @Override
    public StatusInfo deleteLuiCapacity(String luiCapacityId, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException,
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        if (null == context) {
            throw new MissingParameterException();
        }
        
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteLuiCapacity", null)) {
            return getNextDecorator().deleteLuiCapacity(luiCapacityId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }
}
