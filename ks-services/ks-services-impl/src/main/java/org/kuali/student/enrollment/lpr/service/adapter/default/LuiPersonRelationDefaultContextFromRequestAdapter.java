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

package org.kuali.student.enrollment.lpr.service.adapter.default;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.kuali.student.common.infc.ContextBean;

import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DisabledIdentifierException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;


import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.StatusInfc;
import org.kuali.student.core.exceptions.ReadOnlyException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationServiceInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationStateInfc;

import org.kuali.student.enrollment.lpr.service.adapter.LuiPersonRelationAdapter;

/**
 * A example of an adaptor that will default the context if not supplied based
 * on the requset.
 *
 * This could be generated from the contract definitions as well.
 *
 * @Author Norm
 */
public class LuiPersonRelationDefaultContextFromRequestAdapter
        extends LuiPersonRelationAdapter
        implements LuiPersonRelationServiceInfc {

 private HttpServletRequest request;

 // TODO: add logic to the servlet to actually set this variable because it cannot be configured
 public HttpServletRequest getRequest() {
  return request;
 }

 public void setRequest(HttpServletRequest request) {
  this.request = request;
 }

 private ContextInfc defaultContext(ContextInfc context) {
  if (context == null) {
   context = new ContextBean();
  }
  if (context.getPrincipalId() == null) {
   context.setPrincipalId(request.getRemoteUser());
  }
  if (context.getLocaleLanguage() == null) {
   context.setLocaleLanguage(request.getLocale().getLanguage());
  }
  // TODO: check if Region and country are supposed to the same thing
  if (context.getLocaleRegion() == null) {
   context.setLocaleLanguage(request.getLocale().getCountry());
  }
  if (context.getLocaleVariant() == null) {
   context.setLocaleLanguage(request.getLocale().getVariant());
  }
  // TODO: default script from the character set
  return context;
 }

 @Override
 public String createLuiPersonRelation(String personId, String luiId,
         String luiPersonRelationType,
         LuiPersonRelationInfc luiPersonRelationInfo,
         ContextInfc context)
         throws AlreadyExistsException, DoesNotExistException,
         DisabledIdentifierException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException {
  context = defaultContext(context);
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
         DisabledIdentifierException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException {
  context = defaultContext(context);
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
  context = defaultContext(context);
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
  context = defaultContext(context);
  return (getProvider().deleteLuiPersonRelation(luiPersonRelationId, context));
 }

 @Override
 public StatusInfc updateRelationState(String luiPersonRelationId,
         LuiPersonRelationStateInfc relationState,
         ContextInfc context)
         throws DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException, ReadOnlyException {
  context = defaultContext(context);
  return (getProvider().updateRelationState(luiPersonRelationId,
          relationState, context));
 }
}
