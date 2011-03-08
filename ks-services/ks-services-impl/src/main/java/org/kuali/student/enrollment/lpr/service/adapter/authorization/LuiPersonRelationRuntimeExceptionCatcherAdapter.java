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
package org.kuali.student.enrollment.lpr.service.adapter.authorization;

import java.util.List;

import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DisabledIdentifierException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.ReadOnlyException;
import org.kuali.student.core.exceptions.VersionMismatchException;


import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.StatusInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationServiceInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationStateInfc;

import org.kuali.student.enrollment.lpr.service.adapter.LuiPersonRelationAdapter;

/**
 * A example of an authorization adapter that extends the adapter
 * template and inserts some example authorization calls.
 *
 * @Author Tom
 */
public class LuiPersonRelationRuntimeExceptionCatcherAdapter
        extends LuiPersonRelationAdapter
        implements LuiPersonRelationServiceInfc {

 @Override
 public String createLuiPersonRelation(String personId, String luiId,
         String luiPersonRelationType,
         LuiPersonRelationInfc luiPersonRelationInfo,
         ContextInfc context)
         throws AlreadyExistsException, DoesNotExistException,
         DisabledIdentifierException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException {
  try {
   return (getProvider().createLuiPersonRelation(personId, luiId,
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
         LuiPersonRelationInfc luiPersonRelationInfo,
         ContextInfc context)
         throws AlreadyExistsException, DoesNotExistException,
         DisabledIdentifierException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException {

  try {
   return (getProvider().createBulkRelationshipsForPerson(personId, luiIdList,
           relationState,
           luiPersonRelationType,
           luiPersonRelationInfo,
           context));
  } catch (RuntimeException ex) {
   throw new OperationFailedException("Got RuntimeException", ex);
  }
 }

 @Override
 public LuiPersonRelationInfc updateLuiPersonRelation(
         String luiPersonRelationId, LuiPersonRelationInfc luiPersonRelationInfo,
         ContextInfc context)
         throws DoesNotExistException, InvalidParameterException,
         MissingParameterException, ReadOnlyException, OperationFailedException,
         PermissionDeniedException, VersionMismatchException {
  try {
   return (getProvider().updateLuiPersonRelation(luiPersonRelationId,
           luiPersonRelationInfo,
           context));
  } catch (RuntimeException ex) {
   throw new OperationFailedException("Got RuntimeException", ex);
  }
 }

 @Override
 public StatusInfc deleteLuiPersonRelation(String luiPersonRelationId,
         ContextInfc context) throws
         DoesNotExistException, InvalidParameterException, MissingParameterException,
         OperationFailedException,
         PermissionDeniedException {
  try {
   return (getProvider().deleteLuiPersonRelation(luiPersonRelationId, context));
  } catch (RuntimeException ex) {
   throw new OperationFailedException("Got RuntimeException", ex);
  }
 }

 @Override
 public StatusInfc updateRelationState(String luiPersonRelationId,
         LuiPersonRelationStateInfc relationState,
         ContextInfc context)
         throws DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException, ReadOnlyException {
  try {
   return (getProvider().updateRelationState(luiPersonRelationId,
           relationState, context));
  } catch (RuntimeException ex) {
   throw new OperationFailedException("Got RuntimeException", ex);
  }
 }
}
