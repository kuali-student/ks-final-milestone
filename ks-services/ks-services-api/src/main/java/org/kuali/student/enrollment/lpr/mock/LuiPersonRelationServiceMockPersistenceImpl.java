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
package org.kuali.student.enrollment.lpr.mock;

import org.kuali.student.enrollment.lpr.service.LuiPersonRelationServiceInfc;
import org.kuali.student.common.infc.AttributeInfc;
import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.StatusInfc;
import org.kuali.student.common.infc.ValidationResultInfc;
import org.kuali.student.core.exceptions.*;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.infc.*;
import org.kuali.student.enrollment.lui.infc.LuiInfc;
import org.kuali.student.enrollment.lui.infc.LuiServiceInfc;

import java.util.*;

import org.kuali.student.common.dto.AttributeInfo;
import org.kuali.student.common.dto.MetaInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.infc.CriteriaInfc;
import org.kuali.student.common.infc.HoldsLuiServiceInfc;


/**
 * @author nwright
 */
public class LuiPersonRelationServiceMockPersistenceImpl implements
        LuiPersonRelationServiceInfc, HoldsLuiServiceInfc {

    private LuiServiceInfc luiService;

    @Override
    public LuiServiceInfc getLuiService() {
        return luiService;
    }

    @Override
    public void setLuiService(LuiServiceInfc luiService) {
        this.luiService = luiService;
    }

    private Map<String, LuiPersonRelationInfc> lprCache = new HashMap<String, LuiPersonRelationInfc>();

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
        List<String> lprIds = new ArrayList<String>(luiIdList.size());
        for (String luiId : luiIdList) {
        	LuiPersonRelationInfc lprInfo = new LuiPersonRelationInfo.Builder(luiPersonRelationInfo).setLuiId(luiId).build();
        			
            String lprId = this.createLuiPersonRelation(personId,
                    luiId,
                    luiPersonRelationType,
                    lprInfo,
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
    	CopierHelper helper = new CopierHelper();
        LuiPersonRelationInfo.Builder builder = new LuiPersonRelationInfo.Builder(luiPersonRelationInfo);
        builder.setId(UUID.randomUUID().toString()).setPersonId(personId).setLuiId(luiId).setType(luiPersonRelationType).setMetaInfo(helper.createMeta(context));
        LuiPersonRelationInfc copy = builder.build();
        this.lprCache.put(copy.getId(), copy);
        return copy.getId();
    }

    @Override
    public StatusInfc deleteLuiPersonRelation(String luiPersonRelationId, ContextInfc context) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (this.lprCache.remove(luiPersonRelationId) == null) {
            throw new DoesNotExistException(luiPersonRelationId);
        }
        return new StatusInfo.Builder().setSuccess(Boolean.TRUE).build();
    }

    @Override
    public LuiPersonRelationInfc fetchLUIPersonRelation(String luiPersonRelationId,
                                                        ContextInfc context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        LuiPersonRelationInfc bean = this.lprCache.get(
                luiPersonRelationId);
        if (bean == null) {
            throw new DoesNotExistException(luiPersonRelationId);
        }
        return new CopierHelper ().makeCopy(bean);
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
        for (LuiPersonRelationInfc lpr : this.lprCache.values()) {
            if (!personId.equals(lpr.getPersonId())) {
                continue;
            }
            if (!luiPersonRelationType.equals(lpr.getType())) {
                continue;
            }
            if (!relationState.equals(lpr.getState())) {
                continue;
            }
            LuiInfc lui = luiService.getLui(lpr.getLuiId(), context);
            if (!atpId.equals(lui.getAtpKey())) {
                continue;
            }
            luiIds.add(lpr.getLuiId());
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
        for (LuiPersonRelationInfc bean : this.lprCache.values()) {
            if (!luiId.equals(bean.getLuiId())) {
                continue;
            }
            if (!luiPersonRelationType.equals(bean.getType())) {
                continue;
            }
            if (!relationState.equals(bean.getState())) {
                continue;
            }
            LuiInfc lui = luiService.getLui(bean.getLuiId(), context);
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
        // check type is valid
        this.getLuiPersonRelationTypeEnum(luiPersonRelationType);
        if (isInstructorType(luiPersonRelationType)) {
            List<LuiPersonRelationStateInfc> states = new ArrayList(LuiPersonRelationStateEnum.COURSE_INSTRUCTOR_STATES.length);
            CopierHelper helper = new CopierHelper();
            for (LuiPersonRelationStateInfc state : LuiPersonRelationStateEnum.COURSE_INSTRUCTOR_STATES) {
                states.add(helper.makeCopy(state));
            }
            return states;
        }
        if (luiPersonRelationType.equals(LuiPersonRelationTypeEnum.ADVISOR.getKey())) {
            List<LuiPersonRelationStateInfc> states = new ArrayList(LuiPersonRelationStateEnum.COURSE_INSTRUCTOR_STATES.length);
            CopierHelper helper = new CopierHelper();
            for (LuiPersonRelationStateInfc state : LuiPersonRelationStateEnum.PROGRAM_ADVISOR_STATES) {
                states.add(helper.makeCopy(state));
            }
            return states;
        }
        if (isStudentCourseType(luiPersonRelationType)) {
            List<LuiPersonRelationStateInfc> states = new ArrayList(LuiPersonRelationStateEnum.COURSE_STUDENT_STATES.length);
            CopierHelper helper = new CopierHelper();
            for (LuiPersonRelationStateInfc state : LuiPersonRelationStateEnum.COURSE_STUDENT_STATES) {
                states.add(helper.makeCopy(state));
            }
            return states;
        }
        if (isStudentProgramType(luiPersonRelationType)) {
            List<LuiPersonRelationStateInfc> states = new ArrayList(LuiPersonRelationStateEnum.PROGRAM_STUDENT_STATES.length);
            CopierHelper helper = new CopierHelper();
            for (LuiPersonRelationStateInfc state : LuiPersonRelationStateEnum.PROGRAM_STUDENT_STATES) {
                states.add(helper.makeCopy(state));
            }
            return states;
        }
        throw new IllegalArgumentException(luiPersonRelationType);
    }

    private boolean isInstructorType(String typeKey) {
        for (LuiPersonRelationTypeEnum type : LuiPersonRelationTypeEnum.COURSE_INSTRUCTOR_TYPES) {
            if (type.getKey().equals(typeKey)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStudentCourseType(String typeKey) {
        for (LuiPersonRelationTypeEnum type : LuiPersonRelationTypeEnum.COURSE_STUDENT_TYPES) {
            if (type.getKey().equals(typeKey)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStudentProgramType(String typeKey) {
        if (LuiPersonRelationTypeEnum.STUDENT.getKey().equals(typeKey)) {
            return true;
        }
        return false;
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
        for (LuiPersonRelationInfc bean : this.lprCache.values()) {
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
        for (LuiPersonRelationInfc bean : this.lprCache.values()) {
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
        for (LuiPersonRelationInfc bean : this.lprCache.values()) {
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
        for (LuiPersonRelationInfc bean : this.lprCache.values()) {
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
        List<LuiPersonRelationStateInfc> states = new ArrayList();
        CopierHelper helper = new CopierHelper();
        for (LuiPersonRelationStateEnum state : LuiPersonRelationStateEnum.values()) {
            states.add(helper.makeCopy(state));
        }
        return states;
    }

    @Override
    public List<LuiPersonRelationTypeInfc> findLuiPersonRelationTypes(
            ContextInfc context)
            throws OperationFailedException {
        List<LuiPersonRelationTypeInfc> types = new ArrayList();
        CopierHelper helper = new CopierHelper();
        for (LuiPersonRelationTypeEnum type : LuiPersonRelationTypeEnum.values()) {
            types.add(helper.makeCopy(type));
        }
        return types;
    }

    // TODO: Add this method to the service interface
    private LuiPersonRelationTypeEnum getLuiPersonRelationTypeEnum(String typeKey)
            throws DoesNotExistException {
        for (LuiPersonRelationTypeEnum type : LuiPersonRelationTypeEnum.values()) {
            if (type.getKey().equals(typeKey)) {
                return type;
            }
        }
        throw new DoesNotExistException(typeKey);
    }

    // TODO: Add this method to the service interface
    private LuiPersonRelationTypeInfc getLuiPersonRelationType(String typeKey)
            throws DoesNotExistException {
        return new CopierHelper().makeCopy(this.getLuiPersonRelationTypeEnum(typeKey));
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
        // TODO: reevaluate if this method is needed -- I can see no use case for it
        Map<String, LuiPersonRelationTypeInfc> types = new HashMap();
        for (LuiPersonRelationInfc lpr : this.lprCache.values()) {
            if (!lpr.getPersonId().equals(personId)) {
                continue;
            }
            if (!lpr.getLuiId().equals(luiId)) {
                continue;
            }
            if (!lpr.getState().equals(relationState)) {
                continue;
            }
            LuiPersonRelationTypeInfc type = this.getLuiPersonRelationType(lpr.getType());
            types.put(type.getKey(), type);
        }
        return new ArrayList(types.values());
    }

    @Override
    public List<LuiPersonRelationInfc> findLuiPersonRelations(String personId,
                                                              String luiId,
                                                              ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationInfc> lprs = new ArrayList();
        CopierHelper helper = new CopierHelper();
        for (LuiPersonRelationInfc bean : this.lprCache.values()) {
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
        CopierHelper helper = new CopierHelper();
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
            List<CriteriaInfc> criteria,
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
        LuiPersonRelationInfc existing = this.lprCache.get(
                luiPersonRelationId);
        if (existing == null) {
            throw new DoesNotExistException(luiPersonRelationId);
        }
        CopierHelper helper = new CopierHelper();
        if (!luiPersonRelationInfo.getMetaInfo().getVersionInd().equals(
                existing.getMetaInfo().getVersionInd())) {
            throw new VersionMismatchException(
                    "Updated by " + existing.getMetaInfo().getUpdateId() + " on "
                            + existing.getMetaInfo().getUpdateId() + " with version of "
                            + existing.getMetaInfo().getVersionInd());
        }
        LuiPersonRelationInfo.Builder builder = new LuiPersonRelationInfo.Builder(luiPersonRelationInfo).setMetaInfo(helper.updateMeta(existing.getMetaInfo(), context));
        // update attributes in order to be different than that in luiPersonRelationInfo
        List<AttributeInfc> atts = new ArrayList<AttributeInfc>();
        for (AttributeInfc att : luiPersonRelationInfo.getAttributes()) {
        	atts.add(new AttributeInfo.Builder(att).build());
        }
        builder.setAttributes(atts);
        LuiPersonRelationInfc copy = builder.build();
        this.lprCache.put(luiPersonRelationId, copy);
        // mirroring what was done before immutable DTO's; why returning copy of copy?
        return new LuiPersonRelationInfo.Builder(copy).build(); 
    }

    @Override
    public StatusInfc updateRelationState(String luiPersonRelationId,
                                          LuiPersonRelationStateInfc relationState, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        try {
            LuiPersonRelationInfc existing = this.lprCache.get(luiPersonRelationId);
            if (existing == null) {
                throw new DoesNotExistException(luiPersonRelationId);
            }
            LuiPersonRelationInfc revised = new LuiPersonRelationInfo.Builder(existing).setState(relationState.getKey()).build();
            this.updateLuiPersonRelation(luiPersonRelationId, revised, context);
        } catch (VersionMismatchException ex) {
            throw new OperationFailedException("id changed between fetch and update", ex);
        }
        return new StatusInfo.Builder().setSuccess(Boolean.TRUE).build();
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

