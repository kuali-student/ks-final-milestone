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
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.mock.LuiPersonRelationServiceAdapter;

/**
 * Checks if updating read only fields.
 *
 * @Author Norm
 */
public class LuiPersonRelationReadOnlyFieldUpdatesCheckerAdapter
		extends LuiPersonRelationServiceAdapter {

	 
	@Override
	public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList, String relationState, String luiPersonRelationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
			throws DataValidationErrorException,
			AlreadyExistsException,
			DoesNotExistException,
			DisabledIdentifierException,
			ReadOnlyException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		if (luiPersonRelationInfo.getId() != null) {
			throw new ReadOnlyException("Id is not allowed to be supplied on a create");
		}
		if (luiPersonRelationInfo.getMetaInfo() != null) {
			throw new ReadOnlyException("MetaInfo is not allowed to be supplied on a create");
		}
		return this.getLprService().createBulkRelationshipsForPerson(personId, luiIdList, relationState, luiPersonRelationType, luiPersonRelationInfo, context);
	}

	@Override
	public String createLuiPersonRelation(String personId, String luiId,
	  String luiPersonRelationType,
	  LuiPersonRelationInfo luiPersonRelationInfo,
	  ContextInfo context)
	  throws DataValidationErrorException,
	  AlreadyExistsException,
	  DoesNotExistException,
	  DisabledIdentifierException,
	  ReadOnlyException,
	  InvalidParameterException,
	  MissingParameterException,
	  OperationFailedException,
	  PermissionDeniedException {
		if (luiPersonRelationInfo.getId() != null) {
			throw new ReadOnlyException("Id is not allowed to be supplied on a create");
		}
		if (luiPersonRelationInfo.getMetaInfo() != null) {
			throw new ReadOnlyException("MetaInfo is not allowed to be supplied on a create");
		}
		return this.getLprService().createLuiPersonRelation(personId, luiId, luiPersonRelationType, luiPersonRelationInfo, context);
	}

	@Override
	public LuiPersonRelationInfo updateLuiPersonRelation(String luiPersonRelationId, 
	  LuiPersonRelationInfo luiPersonRelationInfo,
	  ContextInfo context)
	  throws DataValidationErrorException,
	  DoesNotExistException,
	  InvalidParameterException,
	  MissingParameterException,
	  ReadOnlyException,
	  OperationFailedException,
	  PermissionDeniedException,
	  VersionMismatchException {
		LuiPersonRelationInfo orig = this.fetchLuiPersonRelation(luiPersonRelationId, context);
		// once created these fields are never updatable directly by the application
		checkReadOnly("id", orig.getId(), luiPersonRelationInfo.getId());
		checkReadOnly("type", orig.getType(), luiPersonRelationInfo.getType());
		checkReadOnly("createId", orig.getMetaInfo().getCreateId(), luiPersonRelationInfo.getMetaInfo().getCreateId());
		checkReadOnly("createTime", orig.getMetaInfo().getCreateTime(), luiPersonRelationInfo.getMetaInfo().getCreateTime());
		// if nothing has changed since fetching then cannot update update info either
		// TODO: consider throwing the optimistic lock exception (VersionMismatchException) if version ids do not match
		if (orig.getMetaInfo().getVersionInd().equals(luiPersonRelationInfo.getMetaInfo().getVersionInd())) {
			checkReadOnly("updateId", orig.getMetaInfo().getUpdateId(), luiPersonRelationInfo.getMetaInfo().getUpdateId());
			checkReadOnly("updateTime", orig.getMetaInfo().getUpdateTime(), luiPersonRelationInfo.getMetaInfo().getUpdateTime());
		}
		return this.getLprService().updateLuiPersonRelation(luiPersonRelationId, luiPersonRelationInfo, context);
	}

	private void checkReadOnly(String field, Object orig, Object supplied)
			throws ReadOnlyException {
		checkReadOnly(field, orig, supplied, "" + orig, "" + supplied);
	}

	private void checkReadOnly(String field, Object orig, Object supplied, String origStr, String suppliedStr)
			throws ReadOnlyException {
		if (orig != null) {
			if (orig.equals(supplied)) {
				return;
			}
		}
		throw new ReadOnlyException(field + " is read only but the original value " + origStr + " and the supplied new=" + suppliedStr);
	}
}
