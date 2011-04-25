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
package org.kuali.student.enrollment.classI.lpr.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.kuali.student.common.infc.HoldsLprService;
import org.kuali.student.common.infc.HoldsLuiService;
import org.kuali.student.common.infc.State;
import org.kuali.student.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.datadictionary.infc.DictionaryEntry;
import org.kuali.student.enrollment.classI.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.classI.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.classI.lui.dto.LuiInfo;
import org.kuali.student.enrollment.classI.lui.service.LuiService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.CriteriaInfo;
import org.kuali.student.r2.common.dto.StateInfo;
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
import org.kuali.student.test.utilities.MockHelper;
import org.kuali.student.datadictionary.util.CriteriaValidatorParser;

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
    
    private Map<String, LuiPersonRelationInfo> lprCache = new HashMap<String, LuiPersonRelationInfo>();

    @Override
    public List<String> createBulkRelationshipsForPerson(String personId,
            List<String> luiIdList,
            String relationState,
            String luiPersonRelationType,
            LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context)
            throws AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> lprIds = new ArrayList<String>(luiIdList.size());
        for (String luiId : luiIdList) {
            LuiPersonRelationInfo.Builder bldr = new LuiPersonRelationInfo.Builder(luiPersonRelationInfo);
            bldr.setLuiId(luiId);
            LuiPersonRelationInfo lprInfo = bldr.build();

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
            LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context) throws
            AlreadyExistsException,
            DoesNotExistException,
            DisabledIdentifierException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        MockHelper helper = new MockHelper();
        LuiPersonRelationInfo.Builder builder = new LuiPersonRelationInfo.Builder(luiPersonRelationInfo);
        builder.setId(UUID.randomUUID().toString());
        builder.setPersonId(personId);
        builder.setLuiId(luiId);
        builder.setTypeKey(luiPersonRelationType);
        builder.setMetaInfo(helper.createMeta(context));
        LuiPersonRelationInfo copy = builder.build();
        this.lprCache.put(copy.getId(), copy);
        return copy.getId();
    }

    @Override
    public StatusInfo deleteLuiPersonRelation(String luiPersonRelationId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (this.lprCache.remove(luiPersonRelationId) == null) {
            throw new DoesNotExistException(luiPersonRelationId);
        }
        StatusInfo.Builder bldr = new StatusInfo.Builder();
        bldr.setSuccess(Boolean.TRUE);
        return bldr.build();
    }

    @Override
    public LuiPersonRelationInfo fetchLuiPersonRelation(String luiPersonRelationId,
            ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        LuiPersonRelationInfo bean = this.lprCache.get(
                luiPersonRelationId);
        if (bean == null) {
            throw new DoesNotExistException(luiPersonRelationId);
        }
        return bean;
    }

    @Override
    public List<String> findAllValidLuisForPerson(String personId,
            String luiPersonRelationType,
            String relationState,
            String atpId,
            ContextInfo context) throws
            DoesNotExistException,
            DisabledIdentifierException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> luiIds = new ArrayList();
        for (LuiPersonRelationInfo lpr : this.lprCache.values()) {
            if (!personId.equals(lpr.getPersonId())) {
                continue;
            }
            if (!luiPersonRelationType.equals(lpr.getTypeKey())) {
                continue;
            }
            if (!relationState.equals(lpr.getStateKey())) {
                continue;
            }
            LuiInfo lui = luiService.getLui(lpr.getLuiId(), context);
            if (!atpId.equals(lui.getAtpKey())) {
                continue;
            }
            luiIds.add(lpr.getLuiId());
        }
        return luiIds;
    }

//    @Override
//    public List<String> findAllValidPeopleForLui(String luiId,
//                                                 String luiPersonRelationType,
//                                                 String relationState,
//                                                 ContextInfo context) throws
//            DoesNotExistException,
//            DisabledIdentifierException,
//            InvalidParameterException,
//            MissingParameterException,
//            OperationFailedException,
//            PermissionDeniedException {
//        List<String> personIds = new ArrayList();
//        for (LuiPersonRelationInfo bean : this.lprCache.values()) {
//            if (!luiId.equals(bean.getLuiId())) {
//                continue;
//            }
//            if (!luiPersonRelationType.equals(bean.getType())) {
//                continue;
//            }
//            if (!relationState.equals(bean.getState())) {
//                continue;
//            }
//            LuiInfo lui = luiService.getLui(bean.getLuiId(), context);
//            personIds.add(bean.getPersonId());
//        }
//        return personIds;
//    }

    
    @Override
    public List<StateInfo> getStatesByProcess(
            String processKey,
            ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // check type is valid
        this.getLuiPersonRelationTypeEnum(processKey);
        if (isInstructorType(processKey)) {
            List<StateInfo> states = new ArrayList<StateInfo>(LuiPersonRelationStateEnum.COURSE_INSTRUCTOR_STATES.length);
            for (State state : LuiPersonRelationStateEnum.COURSE_INSTRUCTOR_STATES) {
                states.add(new StateInfo.Builder(state).build());
            }
            return states;
        }
        if (processKey.equals(LuiPersonRelationServiceConstants.ADVISOR_TYPE_KEY)) {
            List<StateInfo> states = new ArrayList<StateInfo>(LuiPersonRelationStateEnum.COURSE_INSTRUCTOR_STATES.length);
            for (State state : LuiPersonRelationStateEnum.PROGRAM_ADVISOR_STATES) {
                states.add(new StateInfo.Builder(state).build());
            }
            return states;
        }
        if (isStudentCourseType(processKey)) {
            List<StateInfo> states = new ArrayList<StateInfo>(LuiPersonRelationStateEnum.COURSE_STUDENT_STATES.length);
            for (State state : LuiPersonRelationStateEnum.COURSE_STUDENT_STATES) {
                states.add(new StateInfo.Builder(state).build());
            }
            return states;
        }
        if (isStudentProgramType(processKey)) {
            List<StateInfo> states = new ArrayList<StateInfo>(LuiPersonRelationStateEnum.PROGRAM_STUDENT_STATES.length);
            for (State state : LuiPersonRelationStateEnum.PROGRAM_STUDENT_STATES) {
                states.add(new StateInfo.Builder(state).build());
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
    public List<String> findLuiIdsRelatedToPerson(String personId,
            String luiPersonRelationType,
            String relationState,
            ContextInfo context) throws
            DoesNotExistException,
            DisabledIdentifierException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
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
    public List<String> findLuiPersonRelationIds(String personId, String luiId,
            ContextInfo context) throws
            DoesNotExistException,
            DisabledIdentifierException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
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
    public List<String> findLuiPersonRelationIdsForLui(String luiId,
            ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
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
    public List<String> findLuiPersonRelationIdsForPerson(String personId,
            ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException,
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
    private LuiPersonRelationTypeEnum getLuiPersonRelationTypeEnum(String typeKey)
            throws DoesNotExistException {
        for (LuiPersonRelationTypeEnum type : LuiPersonRelationTypeEnum.values()) {
            if (type.getKey().equals(typeKey)) {
                return type;
            }
        }
        throw new DoesNotExistException(typeKey);
    }

//    @Override
//    public List<LuiPersonRelationTypeInfo> findLuiPersonRelationTypesForLuiPersonRelation(
//            String personId,
//            String luiId,
//            String relationState,
//            ContextInfo context)
//            throws DoesNotExistException, DisabledIdentifierException,
//            InvalidParameterException, MissingParameterException,
//            OperationFailedException, PermissionDeniedException {
//        // TODO: reevaluate if this method is needed -- I can see no use case for it
//        Map<String, LuiPersonRelationTypeInfo> types = new HashMap();
//        for (LuiPersonRelationInfo lpr : this.lprCache.values()) {
//            if (!lpr.getPersonId().equals(personId)) {
//                continue;
//            }
//            if (!lpr.getLuiId().equals(luiId)) {
//                continue;
//            }
//            if (!lpr.getState().equals(relationState)) {
//                continue;
//            }
//            LuiPersonRelationTypeInfo type = this.getLuiPersonRelationType(lpr.getType());
//            types.put(type.getKey(), type);
//        }
//        return new ArrayList(types.values());
//    }
    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelations(String personId,
            String luiId,
            ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
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
    public List<LuiPersonRelationInfo> findLuiPersonRelationsByIdList(
            List<String> luiPersonRelationIdList,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<LuiPersonRelationInfo> lprs = new ArrayList();
        for (String id : luiPersonRelationIdList) {
            LuiPersonRelationInfo bean = this.fetchLuiPersonRelation(id, context);
            lprs.add(bean);
        }
        return lprs;
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForLui(String luiId,
            ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> ids = this.findLuiPersonRelationIdsForLui(luiId, context);
        return this.findLuiPersonRelationsByIdList(ids, context);
    }

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForPerson(
            String personId,
            ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> ids = this.findLuiPersonRelationIdsForPerson(personId, context);
        return this.findLuiPersonRelationsByIdList(ids, context);
    }

    @Override
    public LuiPersonRelationInfo updateLuiPersonRelation(String luiPersonRelationId,
            LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            ReadOnlyException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        LuiPersonRelationInfo existing = this.lprCache.get(
                luiPersonRelationId);
        if (existing == null) {
            throw new DoesNotExistException(luiPersonRelationId);
        }
        if (!luiPersonRelationInfo.getMetaInfo().getVersionInd().equals(
                existing.getMetaInfo().getVersionInd())) {
            throw new VersionMismatchException(
                    "Updated by " + existing.getMetaInfo().getUpdateId() + " on "
                    + existing.getMetaInfo().getUpdateId() + " with version of "
                    + existing.getMetaInfo().getVersionInd());
        }
        LuiPersonRelationInfo.Builder builder = new LuiPersonRelationInfo.Builder(luiPersonRelationInfo);
        builder.setMetaInfo(new MockHelper().updateMeta(existing.getMetaInfo(), context));
        // update attributes in order to be different than that in luiPersonRelationInfo
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (AttributeInfo att : luiPersonRelationInfo.getAttributes()) {
            atts.add(new AttributeInfo.Builder(att).build());
        }
        builder.setAttributes(atts);
        LuiPersonRelationInfo copy = builder.build();
        this.lprCache.put(luiPersonRelationId, copy);
        // mirroring what was done before immutable DTO's; why returning copy of copy?
        return new LuiPersonRelationInfo.Builder(copy).build();
    }

    @Override
    public List<String> searchForLuiPersonRelationIds(CriteriaInfo criteria, ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        // get the dictionary entry for the LPR object
        String dictionaryEntryKey = LuiPersonRelationServiceConstants.REF_OBJECT_URI_LUI_PERSON_RELATION;
        DictionaryEntry dictionaryEntry;
        try {
            dictionaryEntry = this.getDataDictionaryEntry(dictionaryEntryKey, context);
        } catch (DoesNotExistException ex) {
           throw new OperationFailedException (dictionaryEntryKey + " is not in the dictionary", ex);
        }

        // validate the criteria
        CriteriaValidatorParser validator = new CriteriaValidatorParser();
        validator.setCriteria(criteria);
        validator.setDictionaryEntry(dictionaryEntry);
        validator.validate();

        // now do the in memory matching
        CriteriaMatcherInMemory<LuiPersonRelationInfo> matcher = new CriteriaMatcherInMemory<LuiPersonRelationInfo>();
        matcher.setDictionaryEntry(dictionaryEntry);
        matcher.setCriteria(criteria);
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
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        if (isInstructorType(processKey)) {
            for (State state : LuiPersonRelationStateEnum.COURSE_INSTRUCTOR_STATES) {
                if(state.getKey().equals(stateKey)) return (new StateInfo.Builder(state).build());
            }
        }
        if (processKey.equals(LuiPersonRelationServiceConstants.ADVISOR_TYPE_KEY)) {
            for (State state : LuiPersonRelationStateEnum.PROGRAM_ADVISOR_STATES) {
                if(state.getKey().equals(stateKey)) return (new StateInfo.Builder(state).build());
            }
        }
        if (isStudentCourseType(processKey)) {
            for (State state : LuiPersonRelationStateEnum.COURSE_STUDENT_STATES) {
                if(state.getKey().equals(stateKey)) return (new StateInfo.Builder(state).build());
            }
        }
        if (isStudentProgramType(processKey)) {
            for (State state : LuiPersonRelationStateEnum.PROGRAM_STUDENT_STATES) {
                if(state.getKey().equals(stateKey)) return (new StateInfo.Builder(state).build());
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
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
			throws OperationFailedException, MissingParameterException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
			ContextInfo context) throws OperationFailedException,
			MissingParameterException, PermissionDeniedException,
			DoesNotExistException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeInfo getType(String typeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey,
			String relatedRefObjectURI, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(
			String ownerTypeKey, String relationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getProcessKeys(String typeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StateInfo> getInitialValidStates(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StateInfo getNextHappyState(String processKey,
			String currentStateKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findPersonIdsRelatedToLui(String luiId,
			String luiPersonRelationTypeKey, String relationState,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResultInfo> validateLuiPersonRelation(
			String validationType, LuiPersonRelationInfo luiPersonRelationInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> createBulkRelationshipsForLui(String luiId,
			List<String> personIdList, String relationState,
			String luiPersonRelationTypeKey,
			LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, DisabledIdentifierException,
			ReadOnlyException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}
}

