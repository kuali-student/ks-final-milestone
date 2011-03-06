/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lui.infc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DisabledIdentifierException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.ReadOnlyException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.enrollment.common.infc.StatusBean;
import org.kuali.student.enrollment.common.infc.StatusInfc;
import org.kuali.student.enrollment.common.infc.ValidationResultInfc;

/**
 *
 * @author nwright
 */
public class LuiPersonRelationServiceMockImpl implements
        LuiPersonRelationServiceInfc {

 private LuiServiceInfc luiService;

 public LuiServiceInfc getLuiService() {
  return luiService;
 }

 public void setLuiService(LuiServiceInfc luiService) {
  this.luiService = luiService;
 }
 private Map<String, LuiPersonRelationInfc> luiPersonRelationInfcCache =
         new HashMap();

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
  List<String> lprIds = new ArrayList(luiIdList.size());
  for (String luiId : luiIdList) {
   luiPersonRelationInfo.setLuiId(luiId);
   String lprId = this.createLuiPersonRelation(personId,
           luiId,
           luiPersonRelationType,
           luiPersonRelationInfo,
           context);
   lprIds.add(lprId);
  }
  return lprIds;
 }

 @Override
 public String createLuiPersonRelation(String personId, String luiId,
         String luiPersonRelationType,
         LuiPersonRelationInfc luiPersonRelationInfo,
         ContextInfc context) throws
         AlreadyExistsException,
         DoesNotExistException,
         DisabledIdentifierException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException {
  MockImplHelper helper = new MockImplHelper();
  LuiPersonRelationInfc copy = helper.makeCopy(luiPersonRelationInfo);
  copy.setId(UUID.randomUUID().toString());
  copy.setMetaInfo(helper.createMeta(context));
  this.luiPersonRelationInfcCache.put(copy.getId(), copy);
  return copy.getId();
 }

 @Override
 public StatusInfc deleteLuiPersonRelation(String luiPersonRelationId, ContextInfc context) throws
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException {
  if (!this.luiPersonRelationInfcCache.containsKey(luiPersonRelationId)) {
   throw new DoesNotExistException(luiPersonRelationId);
  }
  this.luiPersonRelationInfcCache.remove(luiPersonRelationId);
  StatusInfc status = new StatusBean();
  status.setSuccess(Boolean.TRUE);
  return status;
 }

 @Override
 public LuiPersonRelationInfc fetchLUIPersonRelation(String luiPersonRelationId,
         ContextInfc context)
         throws DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException {
  LuiPersonRelationInfc bean = this.luiPersonRelationInfcCache.get(
          luiPersonRelationId);
  return bean;
 }

 @Override
 public List<String> findAllValidLuisForPerson(String personId,
         String luiPersonRelationType,
         String relationState,
         String atpId,
         ContextInfc context) throws
         DoesNotExistException,
         DisabledIdentifierException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException {
  List<String> luiIds = new ArrayList();
  for (LuiPersonRelationInfc bean : this.luiPersonRelationInfcCache.values()) {
   if (!personId.equals(bean.getPersonId())) {
    continue;
   }
   if (!luiPersonRelationType.equals(bean.getType())) {
    continue;
   }
   if (!relationState.equals(bean.getState())) {
    continue;
   }
   LuiInfc lui = luiService.getLui(bean.getLuiId());
   if (!atpId.equals(lui.getAtpId())) {
    continue;
   }
   luiIds.add(bean.getLuiId());
  }
  return luiIds;
 }

 @Override
 public List<String> findAllValidPeopleForLui(String luiId,
         String luiPersonRelationType,
         String relationState,
         ContextInfc context) throws
         DoesNotExistException,
         DisabledIdentifierException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException {
  List<String> personIds = new ArrayList();
  for (LuiPersonRelationInfc bean : this.luiPersonRelationInfcCache.values()) {
   if (!luiId.equals(bean.getLuiId())) {
    continue;
   }
   if (!luiPersonRelationType.equals(bean.getType())) {
    continue;
   }
   if (!relationState.equals(bean.getState())) {
    continue;
   }
   LuiInfc lui = luiService.getLui(bean.getLuiId());
   personIds.add(bean.getPersonId());
  }
  return personIds;
 }

 @Override
 public List<LuiPersonRelationStateInfc> findAllowedRelationStates(
         String luiPersonRelationType,
         ContextInfc context)
         throws DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException {
  throw new UnsupportedOperationException("Not supported yet.");
 }

 @Override
 public List<String> findLuiIdsRelatedToPerson(String personId,
         String luiPersonRelationType,
         String relationState,
         ContextInfc context) throws
         DoesNotExistException,
         DisabledIdentifierException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException {
  List<String> luiIds = new ArrayList();
  for (LuiPersonRelationInfc bean : this.luiPersonRelationInfcCache.values()) {
   if (!personId.equals(bean.getPersonId())) {
    continue;
   }
   if (!luiPersonRelationType.equals(bean.getType())) {
    continue;
   }
   if (!relationState.equals(bean.getState())) {
    continue;
   }

   luiIds.add(bean.getLuiId());
  }
  return luiIds;
 }

 @Override
 public List<String> findLuiPersonRelationIds(String personId, String luiId,
         ContextInfc context) throws
         DoesNotExistException,
         DisabledIdentifierException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException {
  List<String> lprIds = new ArrayList();
  for (LuiPersonRelationInfc bean : this.luiPersonRelationInfcCache.values()) {
   if (!personId.equals(bean.getPersonId())) {
    continue;
   }
   if (!luiId.equals(bean.getLuiId())) {
    continue;
   }
   lprIds.add(bean.getId());
  }
  return lprIds;
 }

 @Override
 public List<String> findLuiPersonRelationIdsForLui(String luiId,
         ContextInfc context) throws
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException {
  List<String> lprIds = new ArrayList();
  for (LuiPersonRelationInfc bean : this.luiPersonRelationInfcCache.values()) {
   if (!luiId.equals(bean.getLuiId())) {
    continue;
   }
   lprIds.add(bean.getId());
  }
  return lprIds;
 }

 @Override
 public List<String> findLuiPersonRelationIdsForPerson(String personId,
         ContextInfc context)
         throws DoesNotExistException, DisabledIdentifierException,
         InvalidParameterException, MissingParameterException,
         OperationFailedException, PermissionDeniedException {
  List<String> lprIds = new ArrayList();
  for (LuiPersonRelationInfc bean : this.luiPersonRelationInfcCache.values()) {
   if (!personId.equals(bean.getPersonId())) {
    continue;
   }
   lprIds.add(bean.getId());
  }
  return lprIds;
 }

 @Override
 public List<LuiPersonRelationStateInfc> findLuiPersonRelationStates(
         ContextInfc context)
         throws OperationFailedException {
  throw new UnsupportedOperationException("Not supported yet.");
 }

 @Override
 public List<LuiPersonRelationTypeInfc> findLuiPersonRelationTypes(
         ContextInfc context)
         throws OperationFailedException {
  throw new UnsupportedOperationException("Not supported yet.");
 }

 @Override
 public List<LuiPersonRelationTypeInfc> findLuiPersonRelationTypesForLuiPersonRelation(
         String personId,
         String luiId,
         String relationState,
         ContextInfc context)
         throws DoesNotExistException, DisabledIdentifierException,
         InvalidParameterException, MissingParameterException,
         OperationFailedException, PermissionDeniedException {
  throw new UnsupportedOperationException("Not supported yet.");
 }

 @Override
 public List<LuiPersonRelationInfc> findLuiPersonRelations(String personId,
         String luiId,
         ContextInfc context)
         throws DoesNotExistException, DisabledIdentifierException,
         InvalidParameterException, MissingParameterException,
         OperationFailedException, PermissionDeniedException {
  List<LuiPersonRelationInfc> lprs = new ArrayList();
  MockImplHelper helper = new MockImplHelper();
  for (LuiPersonRelationInfc bean : this.luiPersonRelationInfcCache.values()) {
   if (!personId.equals(bean.getPersonId())) {
    continue;
   }
   if (!luiId.equals(bean.getLuiId())) {
    continue;
   }
   lprs.add(helper.makeCopy(bean));
  }
  return lprs;
 }

 @Override
 public List<LuiPersonRelationInfc> findLuiPersonRelationsByIdList(
         List<String> luiPersonRelationIdList,
         ContextInfc context)
         throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException {
  List<LuiPersonRelationInfc> lprs = new ArrayList();
  MockImplHelper helper = new MockImplHelper();
  for (String id : luiPersonRelationIdList) {
   LuiPersonRelationInfc bean = this.fetchLUIPersonRelation(id, context);
   lprs.add(helper.makeCopy(bean));
  }
  return lprs;
 }

 @Override
 public List<LuiPersonRelationInfc> findLuiPersonRelationsForLui(String luiId,
         ContextInfc context)
         throws DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException {
  List<String> ids = this.findLuiPersonRelationIdsForLui(luiId, context);
  return this.findLuiPersonRelationsByIdList(ids, context);
 }

 @Override
 public List<LuiPersonRelationInfc> findLuiPersonRelationsForPerson(
         String personId,
         ContextInfc context)
         throws DoesNotExistException, DisabledIdentifierException,
         InvalidParameterException, MissingParameterException,
         OperationFailedException, PermissionDeniedException {
  List<String> ids = this.findLuiPersonRelationIdsForPerson(personId, context);
  return this.findLuiPersonRelationsByIdList(ids, context);
 }

 @Override
 public List<LuiPersonRelationInfc> findOrderedRelationStatesForLuiPersonRelation(
         String luiPersonRelationId,
         ContextInfc context)
         throws DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException {
  throw new UnsupportedOperationException("Not supported yet.");
 }

 @Override
 public List<String> findPersonIdsRelatedToLui(String luiId,
         String luiPersonRelationType,
         String relationState,
         ContextInfc context) throws
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException {
  throw new UnsupportedOperationException("Not supported yet.");
 }

 @Override
 public List<LuiPersonRelationStateInfc> findValidRelationStatesForLuiPersonRelation(
         String personId,
         String luiId,
         String luiPersonRelationType,
         ContextInfc context)
         throws DoesNotExistException, DisabledIdentifierException,
         InvalidParameterException, MissingParameterException,
         OperationFailedException, PermissionDeniedException {
  throw new UnsupportedOperationException("Not supported yet.");
 }

 @Override
 public Boolean isRelated(String personId, String luiId,
         String luiPersonRelationType, String relationState,
         ContextInfc context) throws DoesNotExistException,
         DisabledIdentifierException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException {
  throw new UnsupportedOperationException("Not supported yet.");
 }

 @Override
 public Boolean isValidLuiPersonRelation(String personId, String luiId,
         String luiPersonRelationType,
         String relationState,
         ContextInfc context) throws
         DoesNotExistException,
         DisabledIdentifierException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException {
  throw new UnsupportedOperationException("Not supported yet.");
 }

 @Override
 public List<String> searchForLuiPersonRelationIds(
         LuiPersonRelationCriteriaInfc luiPersonRelationCriteria,
         ContextInfc context) throws
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException {
  throw new UnsupportedOperationException("Not supported yet.");
 }

 @Override
 public LuiPersonRelationInfc updateLuiPersonRelation(
         String luiPersonRelationId,
         LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context)
         throws DoesNotExistException, InvalidParameterException,
         MissingParameterException, ReadOnlyException, OperationFailedException,
         PermissionDeniedException, VersionMismatchException {
  LuiPersonRelationInfc existing = this.luiPersonRelationInfcCache.get(
          luiPersonRelationId);
  if (existing == null) {
   throw new DoesNotExistException(luiPersonRelationId);
  }
  MockImplHelper helper = new MockImplHelper();
  LuiPersonRelationInfc copy = helper.makeCopy(luiPersonRelationInfo);
  if (!luiPersonRelationInfo.getMetaInfo().getVersionInd().equals(
          existing.getMetaInfo().getVersionInd())) {
   throw new VersionMismatchException(
           "Updated by " + existing.getMetaInfo().getUpdateId() + " on "
           + existing.getMetaInfo().getUpdateId() + " with version of "
           + existing.getMetaInfo().getVersionInd());
  }
  copy.setMetaInfo(helper.updateMeta(existing.getMetaInfo(), context));
  return copy;
 }

 @Override
 public StatusInfc updateRelationState(String luiPersonRelationId,
         LuiPersonRelationStateInfc relationState, ContextInfc context)
         throws DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException, ReadOnlyException {
  try {
   LuiPersonRelationInfc existing = this.luiPersonRelationInfcCache.get(luiPersonRelationId);
   if (existing == null) {
    throw new DoesNotExistException(luiPersonRelationId);
   }
   existing.setState(relationState.getKey());
   this.updateLuiPersonRelation(luiPersonRelationId, existing, context);
  } catch (VersionMismatchException ex) {
   throw new OperationFailedException("id changed between fetch and update", ex);
  }
  StatusInfc status = new StatusBean();
  status.setSuccess(Boolean.TRUE);
  return status;
 }

 @Override
 public ValidationResultInfc validateLuiPersonRelation(String personId,
         String luiId,
         String luiPersonRelationType,
         String relationState,
         ContextInfc context)
         throws DoesNotExistException, DisabledIdentifierException,
         InvalidParameterException, MissingParameterException,
         OperationFailedException, PermissionDeniedException {
  throw new UnsupportedOperationException("Not supported yet.");
 }
}
