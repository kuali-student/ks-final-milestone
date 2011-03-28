/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service;

import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.*;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.enrollment.lpr.conversion.LprConverter;
import org.kuali.student.enrollment.lpr.conversion.LprStateConverter;
import org.kuali.student.enrollment.lpr.conversion.LprTypeConverter;
import org.kuali.student.enrollment.lpr.dao.LprDao;
import org.kuali.student.enrollment.lpr.dao.LprStateDao;
import org.kuali.student.enrollment.lpr.dao.LprTypeDao;
import org.kuali.student.enrollment.lpr.dto.*;
import org.kuali.student.enrollment.lpr.model.LuiPersonRelation;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import java.security.InvalidParameterException;
import java.util.List;


/**
 * @author igor
 */
public class LuiPersonRelationServiceImpl implements LuiPersonRelationService {

    private LprDao lprDao;

    private LprTypeDao lprTypeDao;

    private LprStateDao lprStateDao;

    private LprConverter lprConverter;

    private LprTypeConverter lprTypeConverter;

    private LprStateConverter lprStateConverter;

    @Override
    @Transactional
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForLui(
            String luiId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelation> luiPersonRelations = lprDao.getByLuiId(luiId);
        return lprConverter.fromEntities(luiPersonRelations);
    }

    @Override
    public List<LuiPersonRelationTypeInfo> findLuiPersonRelationTypes(@WebParam(name = "context") ContextInfo context) throws OperationFailedException {
        return lprTypeConverter.fromEntities(lprTypeDao.findAll());
    }

    @Override
    public List<LuiPersonRelationStateInfo> findLuiPersonRelationStates(@WebParam(name = "context") ContextInfo context) throws OperationFailedException {
        return lprStateConverter.fromEntities(lprStateDao.findAll());
    }

    @Override
    public LuiPersonRelationInfo fetchLUIPersonRelation(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprConverter.fromEntity(lprDao.find(luiPersonRelationId));
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsByIdList(@WebParam(name = "luiPersonRelationIdList") List<String> luiPersonRelationIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return lprConverter.fromEntities(lprDao.findByIds(luiPersonRelationIdList));
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelations(@WebParam(name = "personId") String personId, @WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<String> findLuiPersonRelationIds(@WebParam(name = "personId") String personId, @WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<String> findLuiPersonRelationIdsForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }


    @Override
    public List<String> findLuiPersonRelationIdsForLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public ValidationResultInfo validateLuiPersonRelation(@WebParam(name = "validationType") String validationType, @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<String> findAllValidLuisForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "luiPersonRelationType") String luiPersonRelationType, @WebParam(name = "relationState") String relationState, @WebParam(name = "atpId") String atpId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<String> searchForLuiPersonRelationIds(@WebParam(name = "criteria") List<CriteriaInfo> criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public String createLuiPersonRelation(@WebParam(name = "personId") String personId, @WebParam(name = "luiId") String luiId, @WebParam(name = "luiPersonRelationType") String luiPersonRelationType, @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<String> createBulkRelationshipsForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "luiIdList") List<String> luiIdList, @WebParam(name = "relationState") String relationState, @WebParam(name = "luiPersonRelationType") String luiPersonRelationType, @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<String> createBulkRelationshipsForLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "personIdList") List<String> personIdList, @WebParam(name = "relationState") String relationState, @WebParam(name = "luiPersonRelationType") String luiPersonRelationType, @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public LuiPersonRelationInfo updateLuiPersonRelation(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId, @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public StatusInfo deleteLuiPersonRelation(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public StatusInfo updateRelationState(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId, @WebParam(name = "relationState") LuiPersonRelationStateInfo relationState, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    //TODO: not sure how to implement
    @Override
    public List<LuiPersonRelationStateInfo> findAllowedRelationStates(@WebParam(name = "luiPersonRelationType") String luiPersonRelationType, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    //TODO: Why would I do that?
    @Override
    public List<String> findLuiIdsRelatedToPerson(@WebParam(name = "personId") String personId, @WebParam(name = "luiPersonRelationType") String luiPersonRelationType, @WebParam(name = "relationState") String relationState, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    //TODO: why would I do that?
    @Override
    public List<String> findPersonIdsRelatedToLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "luiPersonRelationType") String luiPersonRelationType, @WebParam(name = "relationState") String relationState, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }


    //======Dao Setters===============
    public void setLprDao(LprDao lprDao) {
        this.lprDao = lprDao;
    }

    public void setLprTypeDao(LprTypeDao lprTypeDao) {
        this.lprTypeDao = lprTypeDao;
    }

    public void setLprStateDao(LprStateDao lprStateDao) {
        this.lprStateDao = lprStateDao;
    }

    //============Converters Setters================
    public void setLprConverter(LprConverter lprConverter) {
        this.lprConverter = lprConverter;
    }

    public void setLprTypeConverter(LprTypeConverter lprTypeConverter) {
        this.lprTypeConverter = lprTypeConverter;
    }

    public void setLprStateConverter(LprStateConverter lprStateConverter) {
        this.lprStateConverter = lprStateConverter;
    }
}