/**
 * Copyright 2011 The Kuali Foundation
 *
 *  Licensed under the the Educational Community License, Version 1.0
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
import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiSetInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.jws.WebParam;
import java.util.List;


public class LuiServiceDecorator 
    implements LuiService {
    
    private LuiService nextDecorator;

    public LuiService getNextDecorator()
        throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }

        return nextDecorator;
    }

    public void setNextDecorator(LuiService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public LuiInfo getLui(String luiId, ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException,
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        return getNextDecorator().getLui(luiId, contextInfo);
    }

    @Override
    public List<LuiInfo> getLuisByIds(List<String> luiIds, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        return getNextDecorator().getLuisByIds(luiIds, contextInfo);
    }

    @Override
    public List<String> getLuiIdsByType(String luiTypeKey, ContextInfo contextInfo) 
        throws InvalidParameterException,  MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getLuiIdsByType(luiTypeKey, contextInfo);
    }

    @Override
    public List<String> getLuiIdsByClu(String cluId, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getLuiIdsByClu(cluId, contextInfo);
    }

    @Override
    public List<String> getLuiIdsByAtpAndType(String atpId, String typeKey, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

            return getNextDecorator().getLuiIdsByAtpAndType(atpId, typeKey, contextInfo);
        }

    @Override
    public List<String> getLuiIdsByAtpAndClu(String cluId, String atpId, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getLuiIdsByAtpAndClu(cluId, atpId, contextInfo);
    }

    @Override
    public List<LuiInfo> getLuisByAtpAndClu(String cluId, String atpId, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getLuisByAtpAndClu(cluId, atpId, contextInfo);
    }

    @Override
    public List<String> searchForLuiIds(QueryByCriteria criteria, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForLuiIds(criteria, contextInfo);
    }

    @Override
    public List<LuiInfo> searchForLuis(QueryByCriteria criteria, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForLuis(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLui(String validationTypeKey, String cluId,
                                                  String atpId, String luiTypeKey,
                                                  LuiInfo luiInfo, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        return getNextDecorator().validateLui(validationTypeKey, cluId, atpId, luiTypeKey, luiInfo, contextInfo);
    }

    @Override
    public LuiInfo createLui(String cluId, String atpId, String luiTypeKey, LuiInfo luiInfo, ContextInfo contextInfo) 
        throws DataValidationErrorException, 
               DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException, ReadOnlyException {

        return getNextDecorator().createLui(cluId, atpId, luiTypeKey, luiInfo, contextInfo);
    }

    @Override
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo contextInfo)
        throws DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException,
               ReadOnlyException, VersionMismatchException {

        return getNextDecorator().updateLui(luiId,luiInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteLui(String luiId, ContextInfo contextInfo)
        throws DependentObjectsExistException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().deleteLui(luiId, contextInfo);
    }

    @Override
    public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        return getNextDecorator().getLuiLuiRelation(luiLuiRelationId, contextInfo);
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByIds(List<String> luiLuiRelationIds, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        return getNextDecorator().getLuiLuiRelationsByIds(luiLuiRelationIds, contextInfo);
    }

    @Override
    public List<String> getLuiLuiRelationIdsByType(String luiLuiRelationTypeKey, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getLuiLuiRelationIdsByType(luiLuiRelationTypeKey, contextInfo);
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getLuiLuiRelationsByLui(luiId, contextInfo);
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLuiAndRelatedLui(String luiId, String relatedLuiId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getLuiLuiRelationsByLuiAndRelatedLui(luiId, relatedLuiId, contextInfo);
    }

    @Override
    public List<LuiInfo> getLuiLuiRelationsByLuiAndRelatedLuiType(String luiId, String relatedLuiTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getLuiLuiRelationsByLuiAndRelatedLuiType(luiId, relatedLuiTypeKey, contextInfo);
    }

    @Override
    public List<LuiInfo> getLuisByRelatedLuiAndRelationType(String relatedLuiId, String luiLuiRelationTypeKey, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getLuisByRelatedLuiAndRelationType(relatedLuiId, luiLuiRelationTypeKey, contextInfo);
    }

    @Override
    public List<String> getLuiIdsByRelatedLuiAndRelationType(String relatedLuiId, String luiLuiRelationTypeKey, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getLuiIdsByRelatedLuiAndRelationType(relatedLuiId, luiLuiRelationTypeKey, contextInfo);
    }

    @Override
    public List<LuiInfo> getRelatedLuisByLuiAndRelationType(String luiId, String luiLuiRelationTypeKey, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getRelatedLuisByLuiAndRelationType(luiId, luiLuiRelationTypeKey, contextInfo);
    }

    @Override
    public List<String> getLuiIdsByLuiAndRelationType(String luiId, String luiLuiRelationTypeKey, ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getLuiIdsByLuiAndRelationType(luiId, luiLuiRelationTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForLuiLuiRelationIds(QueryByCriteria criteria, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForLuiLuiRelationIds(criteria, contextInfo);
    }

    @Override
    public List<LuiLuiRelationInfo> searchForLuiLuiRelations(QueryByCriteria criteria, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForLuiLuiRelations(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLuiLuiRelation(String validationTypeKey, String luiId, String relatedLuiId, String luiLuiRelationTypeKey, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        return getNextDecorator().validateLuiLuiRelation(validationTypeKey, luiId, relatedLuiId, luiLuiRelationTypeKey, luiLuiRelationInfo, contextInfo);
    }

    @Override
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId, String luiLuiRelationTypeKey, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo)
        throws DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException,
               ReadOnlyException {

        return getNextDecorator().createLuiLuiRelation(luiId, relatedLuiId, luiLuiRelationTypeKey, luiLuiRelationInfo, contextInfo);
    }

    @Override
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo)
        throws DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException,
               ReadOnlyException, VersionMismatchException {

        return getNextDecorator().updateLuiLuiRelation(luiLuiRelationId, luiLuiRelationInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {

        return getNextDecorator().deleteLuiLuiRelation(luiLuiRelationId, contextInfo);
    }

    @Override
    public LuiCapacityInfo getLuiCapacity(String luiCapacityId, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        return getNextDecorator().getLuiCapacity(luiCapacityId, contextInfo);
    }

    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByIds(List<String> luiCapacityIds, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        return getNextDecorator().getLuiCapacitiesByIds(luiCapacityIds, contextInfo);
    }

    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByLui(String luiId, ContextInfo contextInfo) 
            throws InvalidParameterException, MissingParameterException, 
                   OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuiCapacitiesByLui(luiId, contextInfo);
    }

    
    
    @Override
    public List<String> getLuiCapacityIdsByType(String luiCapacityTypeKey, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getLuiCapacityIdsByType(luiCapacityTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForLuiCapacityIds(QueryByCriteria criteria, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForLuiCapacityIds(criteria, contextInfo);
    }

    @Override
    public List<LuiCapacityInfo> searchForLuiCapacities(QueryByCriteria criteria, ContextInfo contextInfo) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForLuiCapacities(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLuiCapacity(String validationTypeKey, String luiCapacityTypeKey, LuiCapacityInfo luiCapacityInfo, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException,
               PermissionDeniedException {

        return getNextDecorator().validateLuiCapacity(validationTypeKey, luiCapacityTypeKey,luiCapacityInfo, contextInfo);
    }

    @Override
    public LuiCapacityInfo createLuiCapacity(String luiCapacityTypeKey, LuiCapacityInfo luiCapacityInfo, ContextInfo contextInfo) 
        throws DataValidationErrorException, 
               DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException, ReadOnlyException {

        return getNextDecorator().createLuiCapacity(luiCapacityTypeKey, luiCapacityInfo, contextInfo);
    }

    @Override
    public LuiCapacityInfo updateLuiCapacity(String luiCapacityId, LuiCapacityInfo luiCapacityInfo, ContextInfo contextInfo) 
        throws DataValidationErrorException, DoesNotExistException, 
               InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException, 
               ReadOnlyException, VersionMismatchException {

        return getNextDecorator().updateLuiCapacity(luiCapacityId, luiCapacityInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteLuiCapacity(String luiCapacityId, ContextInfo contextInfo) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {

        return getNextDecorator().deleteLuiCapacity(luiCapacityId, contextInfo);
    }

    @Override
    public LuiSetInfo getLuiSet(@WebParam(name = "luiSetId") String luiSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuiSet(luiSetId, contextInfo);
    }

    @Override
    public List<LuiSetInfo> getLuiSetsByIds(@WebParam(name = "luiSetIds") List<String> luiSetIds, @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuiSetsByIds(luiSetIds, contextInfo);
    }

    @Override
    public List<String> getLuiIdsFromLuiSet(@WebParam(name = "luiSetId") String luiSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuiIdsFromLuiSet(luiSetId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLuiSet(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "luiSetTypeKey") String luiSetTypeKey,
                                                     @WebParam(name = "LuiSetInfo") LuiSetInfo LuiSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateLuiSet(validationTypeKey, luiSetTypeKey, LuiSetInfo, contextInfo);
    }

    @Override
    public LuiSetInfo createLuiSet(@WebParam(name = "luiSetTypeKey") String luiSetTypeKey, @WebParam(name = "luiSetInfo") LuiSetInfo luiSetInfo,
                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, UnsupportedActionException {
        return getNextDecorator().createLuiSet(luiSetTypeKey, luiSetInfo, contextInfo);
    }

    @Override
    public LuiSetInfo updateLuiSet(@WebParam(name = "luiSetId") String luiSetId, @WebParam(name = "luiSetInfo") LuiSetInfo luiSetInfo,
                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException, VersionMismatchException {
        return getNextDecorator().updateLuiSet(luiSetId, luiSetInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteLuiSet(@WebParam(name = "luiSetId") String luiSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLuiSet(luiSetId, contextInfo);
    }

    @Override
    public List<LuiSetInfo> getLuiSetsByLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuiSetsByLui(luiId, contextInfo);
    }

    @Override
    public List<String> getLuiSetIdsByType(@WebParam(name = "luiSetTypeKey") String luiSetTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuiSetIdsByType(luiSetTypeKey, contextInfo);
    }
}
