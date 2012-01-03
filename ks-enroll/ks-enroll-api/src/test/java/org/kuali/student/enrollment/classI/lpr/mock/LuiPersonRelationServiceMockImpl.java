/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.classI.lpr.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.lpr.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.datadictionary.infc.DictionaryEntry;
import org.kuali.student.r2.common.datadictionary.util.CriteriaValidatorParser;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsLprService;
import org.kuali.student.r2.common.infc.HoldsLuiService;
import org.kuali.student.r2.common.infc.State;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.test.utilities.MockHelper;

/**
 * @author nwright
 */
public class LuiPersonRelationServiceMockImpl implements LuiPersonRelationService, HoldsLprService, HoldsLuiService {

    private LuiService luiService;

    @Override
    public LuiService getLuiService() {
        return luiService;
    }

    @Override
    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    private final Map<String, LuiPersonRelationInfo> lprCache = new HashMap<String, LuiPersonRelationInfo>();

    @Override
    public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList, String relationState,
            String luiPersonRelationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
            throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> lprIds = new ArrayList<String>(luiIdList.size());
        for (String luiId : luiIdList) {
            LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo(luiPersonRelationInfo);
            lprInfo.setLuiId(luiId);

            String lprId = this.createLpr(personId, luiId, luiPersonRelationType, lprInfo, context);
            lprIds.add(lprId);
        }
        return lprIds;
    }

    @Override
    public String createLpr(String personId, String luiId, String luiPersonRelationType,
            LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        MockHelper helper = new MockHelper();
        LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo(luiPersonRelationInfo);
        lprInfo.setId(UUID.randomUUID().toString());
        lprInfo.setPersonId(personId);
        lprInfo.setLuiId(luiId);
        lprInfo.setTypeKey(luiPersonRelationType);
        lprInfo.setMeta(helper.createMeta(context));
        this.lprCache.put(lprInfo.getId(), lprInfo);
        return lprInfo.getId();
    }

    @Override
    public StatusInfo deleteLpr(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.lprCache.remove(luiPersonRelationId) == null) {
            throw new DoesNotExistException(luiPersonRelationId);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public LuiPersonRelationInfo getLpr(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiPersonRelationInfo bean = this.lprCache.get(luiPersonRelationId);
        if (bean == null) {
            throw new DoesNotExistException(luiPersonRelationId);
        }
        return bean;
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // check type is valid
        this.getLuiPersonRelationTypeEnum(processKey);
        if (isInstructorType(processKey)) {
            List<StateInfo> states = new ArrayList<StateInfo>(
                    LuiPersonRelationStateEnum.COURSE_INSTRUCTOR_STATES.length);
            for (State state : LuiPersonRelationStateEnum.COURSE_INSTRUCTOR_STATES) {
                states.add(StateInfo.getInstance(state));
            }
            return states;
        }
        if (processKey.equals(LuiPersonRelationServiceConstants.ADVISOR_TYPE_KEY)) {
            List<StateInfo> states = new ArrayList<StateInfo>(
                    LuiPersonRelationStateEnum.COURSE_INSTRUCTOR_STATES.length);
            for (State state : LuiPersonRelationStateEnum.PROGRAM_ADVISOR_STATES) {
                states.add(StateInfo.getInstance(state));
            }
            return states;
        }
        if (isStudentCourseType(processKey)) {
            List<StateInfo> states = new ArrayList<StateInfo>(LuiPersonRelationStateEnum.COURSE_STUDENT_STATES.length);
            for (State state : LuiPersonRelationStateEnum.COURSE_STUDENT_STATES) {
                states.add(StateInfo.getInstance(state));
            }
            return states;
        }
        if (isStudentProgramType(processKey)) {
            List<StateInfo> states = new ArrayList<StateInfo>(LuiPersonRelationStateEnum.PROGRAM_STUDENT_STATES.length);
            for (State state : LuiPersonRelationStateEnum.PROGRAM_STUDENT_STATES) {
                states.add(StateInfo.getInstance(state));
            }
            return states;
        }
        throw new IllegalArgumentException(processKey);
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
        if (LuiPersonRelationTypeEnum.REGISTRANT.getKey().equals(typeKey)) {
            return true;
        }
        return false;
    }

    @Override
    public List<String> getLuiIdsByPerson(String personId, String luiPersonRelationType, String relationState,
            ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> luiIds = new ArrayList();
        for (LuiPersonRelationInfo bean : this.lprCache.values()) {
            if (!personId.equals(bean.getPersonId())) {
                continue;
            }
            if (!luiPersonRelationType.equals(bean.getTypeKey())) {
                continue;
            }
            if (!relationState.equals(bean.getStateKey())) {
                continue;
            }

            luiIds.add(bean.getLuiId());
        }
        return luiIds;
    }

    @Override
    public List<String> getLprIdsByLuiAndPerson(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> lprIds = new ArrayList();
        for (LuiPersonRelationInfo bean : this.lprCache.values()) {
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
    public List<String> getLprIdsByLui(String luiId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> lprIds = new ArrayList();
        for (LuiPersonRelationInfo bean : this.lprCache.values()) {
            if (!luiId.equals(bean.getLuiId())) {
                continue;
            }
            lprIds.add(bean.getId());
        }
        return lprIds;
    }

    @Override
    public List<String> getLprIdsByPerson(String personId, ContextInfo context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> lprIds = new ArrayList();
        for (LuiPersonRelationInfo bean : this.lprCache.values()) {
            if (!personId.equals(bean.getPersonId())) {
                continue;
            }
            lprIds.add(bean.getId());
        }
        return lprIds;
    }

    // TODO: Add this method to the service interface
    private LuiPersonRelationTypeEnum getLuiPersonRelationTypeEnum(String typeKey) throws DoesNotExistException {
        for (LuiPersonRelationTypeEnum type : LuiPersonRelationTypeEnum.values()) {
            if (type.getKey().equals(typeKey)) {
                return type;
            }
        }
        throw new DoesNotExistException(typeKey);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByLuiAndPerson(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationInfo> lprs = new ArrayList();
        for (LuiPersonRelationInfo bean : this.lprCache.values()) {
            if (!personId.equals(bean.getPersonId())) {
                continue;
            }
            if (!luiId.equals(bean.getLuiId())) {
                continue;
            }
            lprs.add(bean);
        }
        return lprs;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByIdList(List<String> luiPersonRelationIdList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationInfo> lprs = new ArrayList();
        for (String id : luiPersonRelationIdList) {
            LuiPersonRelationInfo bean = this.getLpr(id, context);
            lprs.add(bean);
        }
        return lprs;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByLui(String luiId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> ids = this.getLprIdsByLui(luiId, context);
        return this.getLprsByIdList(ids, context);
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPerson(String personId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> ids = this.getLprIdsByPerson(personId, context);
        return this.getLprsByIdList(ids, context);
    }

    @Override
    public LuiPersonRelationInfo updateLpr(String luiPersonRelationId, LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            ReadOnlyException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        LuiPersonRelationInfo existing = this.lprCache.get(luiPersonRelationId);
        if (existing == null) {
            throw new DoesNotExistException(luiPersonRelationId);
        }
        if (!luiPersonRelationInfo.getMeta().getVersionInd().equals(existing.getMeta().getVersionInd())) {
            throw new VersionMismatchException("Updated by " + existing.getMeta().getUpdateId() + " on "
                    + existing.getMeta().getUpdateId() + " with version of " + existing.getMeta().getVersionInd());
        }
        LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo(luiPersonRelationInfo);
        lprInfo.setMeta(new MockHelper().updateMeta(existing.getMeta(), context));
        // update attributes in order to be different than that in
        // luiPersonRelationInfo
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (AttributeInfo att : luiPersonRelationInfo.getAttributes()) {
            atts.add(new AttributeInfo(att));
        }
        lprInfo.setAttributes(atts);
        this.lprCache.put(luiPersonRelationId, lprInfo);
        return lprInfo;
    }

    @Override
    /**
     * this is just mock
     */
    public List<String> searchForLprIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

//        // get the dictionary entry for the LPR object
        String dictionaryEntryKey = LuiPersonRelationServiceConstants.REF_OBJECT_URI_LUI_PERSON_RELATION;
//        DictionaryEntry dictionaryEntry;
//        try {
//            dictionaryEntry = this.getDataDictionaryEntry(dictionaryEntryKey, context);
//        } catch (DoesNotExistException ex) {
//            throw new OperationFailedException(dictionaryEntryKey + " is not in the dictionary", ex);
//        }

        // validate the criteria
        CriteriaValidatorParser validator = new CriteriaValidatorParser();

        // now do the in memory matching
        CriteriaMatcherInMemory<LuiPersonRelationInfo> matcher = new CriteriaMatcherInMemory<LuiPersonRelationInfo>();
//        matcher.setDictionaryEntry(dictionaryEntry);
        matcher.setParsedOperators(validator.getParsedOperators());
        matcher.setParsedValues(validator.getParsedValues());
        Collection<LuiPersonRelationInfo> allValues = this.lprCache.values();
        List<LuiPersonRelationInfo> selected = matcher.findMatching(allValues);
        List<String> selectedIds = new ArrayList<String>();
        for (LuiPersonRelationInfo info : selected) {
            selectedIds.add(info.getId());
        }
        return selectedIds;
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {

        if (isInstructorType(processKey)) {
            for (State state : LuiPersonRelationStateEnum.COURSE_INSTRUCTOR_STATES) {
                if (state.getKey().equals(stateKey))
                    return (StateInfo.getInstance(state));
            }
        }
        if (processKey.equals(LuiPersonRelationServiceConstants.ADVISOR_TYPE_KEY)) {
            for (State state : LuiPersonRelationStateEnum.PROGRAM_ADVISOR_STATES) {
                if (state.getKey().equals(stateKey))
                    return (StateInfo.getInstance(state));
            }
        }
        if (isStudentCourseType(processKey)) {
            for (State state : LuiPersonRelationStateEnum.COURSE_STUDENT_STATES) {
                if (state.getKey().equals(stateKey))
                    return (StateInfo.getInstance(state));
            }
        }
        if (isStudentProgramType(processKey)) {
            for (State state : LuiPersonRelationStateEnum.PROGRAM_STUDENT_STATES) {
                if (state.getKey().equals(stateKey))
                    return (StateInfo.getInstance(state));
            }
        }

        throw new DoesNotExistException("Requested state does not exist!");
    }

    @Override
    public LuiPersonRelationService getLprService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLprService(LuiPersonRelationService lprService) {
        // TODO Auto-generated method stub

    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getProcessByObjectType(String objectTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getPersonIdsByLui(String luiId, String luiPersonRelationTypeKey, String relationState,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateLpr(String validationType, LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> createBulkRelationshipsForLui(String luiId, List<String> personIdList, String relationState,
            String luiPersonRelationTypeKey, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> searchForLprs(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLprTransaction(java.lang.String,
     *      org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LprTransactionInfo getLprTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#deleteLprTransaction(java.lang.String,
     *      org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public StatusInfo deleteLprTransaction(String lprTransactionId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLprsByPersonForAtp(java.lang.String,
     *      java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtp(String personId, String atpId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LprRosterInfo updateLprRoster(String lprRosterId, LprRosterInfo lprRosterInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String createLprRoster(LprRosterInfo lprRosterInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteLprRoster(String lprRosterId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprRosterEntryInfo> getEntriesForLprRoster(String lprRosterId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprRosterEntryInfo> getLprRosterEntriesByIdList(List<String> lprRosterEntryIdList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLuiAndRosterType(String luiId, String lprRosterTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLui(String luiId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LprRosterInfo getLprRoster(String lprRosterId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String createLprRosterEntry(LprRosterEntryInfo lprRosterEntryInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteLprRosterEntry(String lprRosterEntryId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo insertLprRosterEntryInPosition(String lprRosterEntryId, Integer position, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo reorderLprRosterEntries(List<String> lprRosterEntryIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateLprTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LprTransactionInfo createLprTransaction(LprTransactionInfo lprTransactionInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LprTransactionInfo createLprTransactionFromExisting(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForPersonByLui(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LprTransactionInfo processLprTransaction(String lprTransactionId, ContextInfo context)
            throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<String> getLprTransactionIdsForPerson(String personId, List<String> lprTypes, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsByIdList(List<String> lprIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForPersonByAtp(String atpId, String personId,
            List<String> lprTypes, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForLpr(String lprId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForLui(String luiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LprTransactionInfo updateLprTransaction(String lprTransactionId, LprTransactionInfo lprTransactionInfo,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprTransactionInfo> searchForLprTransactions(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForLprTransactionIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprRosterInfo> searchForLprRosters(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForLprRosterIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonAndTypeForAtp(String personId, String atpId, String typeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtpAndLuiType(String personId, String atpId,
            String luiTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtpAndPersonType(String personId, String atpId,
            String personTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByTypeAndLui(String typeKey, String luiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonAndLuiType(String personId, String luiTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
