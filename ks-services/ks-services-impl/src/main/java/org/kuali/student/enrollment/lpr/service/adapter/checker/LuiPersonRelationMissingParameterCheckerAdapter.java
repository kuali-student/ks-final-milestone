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
package org.kuali.student.enrollment.lpr.service.adapter.checker;

import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.StatusInfc;
import org.kuali.student.core.exceptions.*;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationServiceInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationStateInfc;
import org.kuali.student.enrollment.lpr.service.adapter.LuiPersonRelationAdapter;

import java.util.List;

/**
 * A example of an adaptor that could be generated from the contract defintions
 * to do the drudge work of checking for missing parameters.
 *
 * @Author Norm
 */
public class LuiPersonRelationMissingParameterCheckerAdapter
        extends LuiPersonRelationAdapter
        implements LuiPersonRelationServiceInfc {


    @Override
    public String createLuiPersonRelation(String personId, String luiId,
                                          String luiPersonRelationType,
                                          LuiPersonRelationInfc luiPersonRelationInfo,
                                          ContextInfc context)
            throws AlreadyExistsException, DoesNotExistException, ReadOnlyException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkParameter("personId", personId);
        checkParameter("luiId", luiId);
        checkParameter("luiPersonRelationType", luiPersonRelationType);
        checkParameter("luiPersonRelationInfo", luiPersonRelationInfo);
        checkParameter("context", context);
        return (getProvider().createLuiPersonRelation(personId, luiId,
                luiPersonRelationType,
                luiPersonRelationInfo,
                context));
    }


    @Override
    public List<String> createBulkRelationshipsForPerson(String personId,
                                                         List<String> luiIdList,
                                                         String relationState,
                                                         String luiPersonRelationType,
                                                         LuiPersonRelationInfc luiPersonRelationInfo,
                                                         ContextInfc context)
            throws AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        checkParameter("personId", personId);
        checkParameter("luiIdList", luiIdList);
        checkParameter("luiIdList", luiIdList);
        checkParameter("relationState", relationState);
        checkParameter("luiPersonRelationInfo", luiPersonRelationInfo);
        checkParameter("context", context);
        return (getProvider().createBulkRelationshipsForPerson(personId, luiIdList,
                relationState,
                luiPersonRelationType,
                luiPersonRelationInfo,
                context));
    }

    @Override
    public LuiPersonRelationInfc updateLuiPersonRelation(
            String luiPersonRelationId, LuiPersonRelationInfc luiPersonRelationInfo,
            ContextInfc context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, ReadOnlyException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        checkParameter("luiPersonRelationId", luiPersonRelationId);
        checkParameter("luiPersonRelationInfo", luiPersonRelationInfo);
        checkParameter("context", context);
        return (getProvider().updateLuiPersonRelation(luiPersonRelationId,
                luiPersonRelationInfo,
                context));
    }


    @Override
    public StatusInfc deleteLuiPersonRelation(String luiPersonRelationId,
                                              ContextInfc context) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        checkParameter("luiPersonRelationId", luiPersonRelationId);
        checkParameter("context", context);
        return (getProvider().deleteLuiPersonRelation(luiPersonRelationId, context));
    }


    @Override
    public StatusInfc updateRelationState(String luiPersonRelationId,
                                          LuiPersonRelationStateInfc relationState,
                                          ContextInfc context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        checkParameter("luiPersonRelationId", luiPersonRelationId);
        checkParameter("relationState", relationState);
        checkParameter("context", context);
        return (getProvider().updateRelationState(luiPersonRelationId,
                relationState, context));
    }

	
    protected void checkParameter(String parameterName, Object parameter)
            throws MissingParameterException {

        if (parameter == null) {
            throw new MissingParameterException(parameterName);
        }
    }
}
