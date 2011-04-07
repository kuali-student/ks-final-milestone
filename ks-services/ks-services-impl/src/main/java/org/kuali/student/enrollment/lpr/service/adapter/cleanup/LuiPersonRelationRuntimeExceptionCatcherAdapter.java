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
package org.kuali.student.enrollment.lpr.service.adapter.cleanup;

import java.util.List;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DisabledIdentifierException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.ReadOnlyException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.adapter.LuiPersonRelationServiceAdapter;

/**
 * A example of an adapter that might sit at the top of the stack and converts any
 * runtime exceptions into the formal OperationFailedException
 * <p/>
 * This could be genrated automatically from the contract definitions too.
 *
 * @Author Norm
 */
public class LuiPersonRelationRuntimeExceptionCatcherAdapter
        extends LuiPersonRelationServiceAdapter {

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
        try {
            return (getLprService().createLuiPersonRelation(personId, luiId,
                    luiPersonRelationType,
                    luiPersonRelationInfo,
                    context));
        } catch (RuntimeException ex) {
            throw new OperationFailedException("Got RuntimeException", ex);
        }
    }

    @Override
    public List<String> createBulkRelationshipsForPerson(String personId,
                                                         List<String> luiIdList,
                                                         String relationState,
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

        try {
            return (getLprService().createBulkRelationshipsForPerson(personId, luiIdList,
                    relationState,
                    luiPersonRelationType,
                    luiPersonRelationInfo,
                    context));
        } catch (RuntimeException ex) {
            throw new OperationFailedException("Got RuntimeException", ex);
        }
    }

    @Override
    public LuiPersonRelationInfo updateLuiPersonRelation(
            String luiPersonRelationId, LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context)
            throws DataValidationErrorException, 
			DoesNotExistException,
			InvalidParameterException,
            MissingParameterException, 
			ReadOnlyException,
			OperationFailedException,
            PermissionDeniedException,
			VersionMismatchException {
        try {
            return (getLprService().updateLuiPersonRelation(luiPersonRelationId,
                    luiPersonRelationInfo,
                    context));
        } catch (RuntimeException ex) {
            throw new OperationFailedException("Got RuntimeException", ex);
        }
    }

    @Override
    public StatusInfo deleteLuiPersonRelation(String luiPersonRelationId,
                                              ContextInfo context) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        try {
            return (getLprService().deleteLuiPersonRelation(luiPersonRelationId, context));
        } catch (RuntimeException ex) {
            throw new OperationFailedException("Got RuntimeException", ex);
        }
    }
}
