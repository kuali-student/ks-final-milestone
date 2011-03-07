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
package org.kuali.student.enrollment.lpr.service.impl;

import java.security.InvalidParameterException;
import java.util.List;

import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DisabledIdentifierException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.ReadOnlyException;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.enrollment.lpr.dao.LuiPersonRelationDao;
import org.kuali.student.enrollment.lpr.dto.ContextInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationCriteria;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;



/**
 *
 * @Author sambit
 * @since
  *
 */
public class LuiPersonRelationServiceImpl implements LuiPersonRelationService  {

    private LuiPersonRelationDao personRelationDao;

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForLui(
            String luiId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public List<String> createBulkRelationshipsForPerson(String personId,
			List<String> luiIdList, String relationState,
			String luiPersonRelationType,
			LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
			throws AlreadyExistsException, DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createLuiPersonRelation(String personId, String luiId,
			String luiPersonRelationType,
			LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
			throws AlreadyExistsException, DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteLuiPersonRelation(String luiPersonRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuiPersonRelationInfo fetchLUIPersonRelation(
			String luiPersonRelationId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findAllValidLuisForPerson(String personId,
			String luiPersonRelationType, String relationState, String atpId,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findAllValidPeopleForLui(String luiId,
			String luiPersonRelationType, String relationState,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationStateInfo> findAllowedRelationStates(
			String luiPersonRelationType, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findLuiIdsRelatedToPerson(String personId,
			String luiPersonRelationType, String relationState,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findLuiPersonRelationIds(String personId, String luiId,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findLuiPersonRelationIdsForLui(String luiId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findLuiPersonRelationIdsForPerson(String personId,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationStateInfo> findLuiPersonRelationStates(
			ContextInfo context) throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationTypeInfo> findLuiPersonRelationTypes(
			ContextInfo context) throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationTypeInfo> findLuiPersonRelationTypesForLuiPersonRelation(
			String personId, String luiId, String relationState,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationInfo> findLuiPersonRelations(String personId,
			String luiId, ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationInfo> findLuiPersonRelationsByIdList(
			List<String> luiPersonRelationIdList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationInfo> findLuiPersonRelationsForPerson(
			String personId, ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationInfo> findOrderedRelationStatesForLuiPersonRelation(
			String luiPersonRelationId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findPersonIdsRelatedToLui(String luiId,
			String luiPersonRelationType, String relationState,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationStateInfo> findValidRelationStatesForLuiPersonRelation(
			String personId, String luiId, String luiPersonRelationType,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isRelated(String personId, String luiId,
			String luiPersonRelationType, String relationState,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isValidLuiPersonRelation(String personId, String luiId,
			String luiPersonRelationType, String relationState,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForLuiPersonRelationIds(
			LuiPersonRelationCriteria luiPersonRelationCriteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuiPersonRelationInfo updateLuiPersonRelation(
			String luiPersonRelationId,
			LuiPersonRelationInfo luiPersonRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, ReadOnlyException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo updateRelationState(String luiPersonRelationId,
			LuiPersonRelationStateInfo relationState)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValidationResultInfo validateLuiPersonRelation(String personId,
			String luiId, String luiPersonRelationType, String relationState,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

    public void setPersonRelationDao(LuiPersonRelationDao personRelationDao) {
        this.personRelationDao = personRelationDao;
    }
}