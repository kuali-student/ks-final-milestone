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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service.adapter.validation;

import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.DisabledIdentifierException;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.ReadOnlyException;
import org.kuali.student.common.exceptions.*;

import java.util.List;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;
import org.kuali.student.enrollment.lpr.mock.LuiPersonRelationServiceAdapter;

/**
 * A example of an adaptor that could be generated from the contract defintions
 * to do the drudge work of checking for missing parameters.
 *
 * @Author Norm
 */
public class LuiPersonRelationMissingParameterCheckerAdapter
		extends LuiPersonRelationServiceAdapter {

	@Override
	public String createLuiPersonRelation(String personId, String luiId,
			String luiPersonRelationType,
			LuiPersonRelationInfo luiPersonRelationInfo,
			ContextInfo context)
			throws DataValidationErrorException,
			AlreadyExistsException,
			DoesNotExistException,
			ReadOnlyException,
			DisabledIdentifierException,
			InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkParameter("personId", personId);
		checkParameter("luiId", luiId);
		checkParameter("luiPersonRelationType", luiPersonRelationType);
		checkParameter("luiPersonRelationInfo", luiPersonRelationInfo);
		checkParameter("context", context);
		return (getLprService().createLuiPersonRelation(personId, luiId,
				luiPersonRelationType,
				luiPersonRelationInfo,
				context));
	}

	@Override
	public List<String> createBulkRelationshipsForPerson(String personId,
			List<String> luiIdList,
			String relationState,
			String luiPersonRelationType,
			LuiPersonRelationInfo luiPersonRelationInfo,
			ContextInfo context)
			throws DataValidationErrorException,
			AlreadyExistsException, DoesNotExistException,
			DisabledIdentifierException, ReadOnlyException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		checkParameter("personId", personId);
		checkParameter("luiIdList", luiIdList);
		checkParameter("relationState", relationState);
		checkParameter("luiPersonRelationType", luiPersonRelationType);
		checkParameter("luiPersonRelationInfo", luiPersonRelationInfo);
		checkParameter("context", context);
		return (getLprService().createBulkRelationshipsForPerson(personId, luiIdList,
				relationState,
				luiPersonRelationType,
				luiPersonRelationInfo,
				context));
	}

	@Override
	public LuiPersonRelationInfo updateLuiPersonRelation(
			String luiPersonRelationId, LuiPersonRelationInfo luiPersonRelationInfo,
			ContextInfo context)
			throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, ReadOnlyException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
		checkParameter("luiPersonRelationId", luiPersonRelationId);
		checkParameter("luiPersonRelationInfo", luiPersonRelationInfo);
		checkParameter("context", context);
		return (getLprService().updateLuiPersonRelation(luiPersonRelationId,
				luiPersonRelationInfo,
				context));
	}

	@Override
	public StatusInfo deleteLuiPersonRelation(String luiPersonRelationId,
			ContextInfo context) throws
			DoesNotExistException, InvalidParameterException, MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		checkParameter("luiPersonRelationId", luiPersonRelationId);
		checkParameter("context", context);
		return (getLprService().deleteLuiPersonRelation(luiPersonRelationId, context));
	}

	@Override
	public StatusInfo updateRelationState(String luiPersonRelationId,
			LuiPersonRelationStateInfo relationState,
			ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		checkParameter("luiPersonRelationId", luiPersonRelationId);
		checkParameter("relationState", relationState);
		checkParameter("context", context);
		return (getLprService().updateRelationState(luiPersonRelationId,
				relationState, context));
	}

	protected void checkParameter(String parameterName, Object parameter)
			throws MissingParameterException {

		if (parameter == null) {
			throw new MissingParameterException(parameterName);
		}
	}
}
