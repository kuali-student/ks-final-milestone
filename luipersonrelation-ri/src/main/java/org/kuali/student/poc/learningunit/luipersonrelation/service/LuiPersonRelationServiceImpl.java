package org.kuali.student.poc.learningunit.luipersonrelation.service;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.poc.common.ws.exceptions.ReadOnlyException;
import org.kuali.student.poc.learningunit.luipersonrelation.dao.LuiPersonRelationDAO;
import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelation;
import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelationType;
import org.kuali.student.poc.learningunit.luipersonrelation.entity.RelationState;
import org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.Status;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationCreateInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationCriteria;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationDisplay;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.ValidationResult;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDisplay;

public class LuiPersonRelationServiceImpl implements LuiPersonRelationService {
    
    private LuiPersonRelationDAO dao;

    public LuiPersonRelationDAO getDao() {
        return dao;
    }

    public void setDao(LuiPersonRelationDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<String> createBulkRelationshipsForLui(String luiId, List<String> personIdList, RelationStateInfo relationStateInfo, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, LuiPersonRelationCreateInfo luiPersonRelationCreateInfo) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> ids = new ArrayList<String>();
        for (String personId : personIdList) {
            ids.add(createLuiPersonRelation(personId, luiId, relationStateInfo, luiPersonRelationTypeInfo, luiPersonRelationCreateInfo));
        }
        return ids;
    }

    @Override
    public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList, RelationStateInfo relationStateInfo, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, LuiPersonRelationCreateInfo luiPersonRelationCreateInfo) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //I wonder if error handling should be somewhat different somehow, but I doubt it
        List<String> ids = new ArrayList<String>();
        for (String luiId : luiIdList) {
            ids.add(createLuiPersonRelation(personId, luiId, relationStateInfo, luiPersonRelationTypeInfo, luiPersonRelationCreateInfo));
        }
        return ids;
    }

    @Override
    public String createLuiPersonRelation(String personId, String luiId, RelationStateInfo relationStateInfo, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, LuiPersonRelationCreateInfo luiPersonRelationCreateInfo) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //TODO should we validate personId and luiId?
        RelationState relationState = toRelationState(relationStateInfo);
        LuiPersonRelationType luiPersonRelationType = toLuiPersonRelationType(luiPersonRelationTypeInfo);
        
        LuiPersonRelation luiPersonRelation = new LuiPersonRelation();
        luiPersonRelation.setPersonId(personId);
        luiPersonRelation.setLuiId(luiId);
        luiPersonRelation.setRelationState(relationState);
        luiPersonRelation.setLuiPersonRelationType(luiPersonRelationType);
        luiPersonRelation.setEffectiveEndDate(luiPersonRelationCreateInfo.getEffectiveEndDate());
        luiPersonRelation.setEffectiveStartDate(luiPersonRelationCreateInfo.getEffectiveStartDate());
        String id = dao.createLuiPersonRelation(luiPersonRelation);
        
        return id;
    }

    private LuiPersonRelationType toLuiPersonRelationType(LuiPersonRelationTypeInfo luiPersonRelationTypeInfo) {
        LuiPersonRelationType lprtDb = new LuiPersonRelationType();
        lprtDb.setName(luiPersonRelationTypeInfo.getName());
        return lprtDb;
    }

    private RelationState toRelationState(RelationStateInfo relationStateInfo) {
        RelationState rsDb = new RelationState();
        rsDb.setState(relationStateInfo.getState());
        return rsDb;
    }

    @Override
    public Status deleteLuiPersonRelation(String luiPersonRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        boolean success = dao.deleteLuiPersonRelation(luiPersonRelationId);
        
        //this is of course stupid since if there is no success, an error gets thrown
        Status status = new Status();
        status.setSuccess(success);
        return status;
    }

    @Override
    public LuiPersonRelationInfo fetchLUIPersonRelation(String luiPersonRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findAllValidLuiIdsForPerson(String personId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo, String atpId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiDisplay> findAllValidLuisForPerson(String personId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo, String atpId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PersonDisplay> findAllValidPeopleForLui(String luiId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findAllValidPersonIdsForLui(String luiId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RelationStateInfo> findAllowedRelationStates(LuiPersonRelationTypeInfo luiPersonRelationTypeInfo) throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findLuiIdsRelatedToPerson(String personId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findLuiPersonRelationIds(String personId, String luiId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findLuiPersonRelationIdsForLui(String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findLuiPersonRelationIdsForPerson(String personId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationTypeInfo> findLuiPersonRelationTypes() throws OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationTypeInfo> findLuiPersonRelationTypesForLuiPersonRelation(String personId, String luiId, RelationStateInfo relationStateInfo) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationDisplay> findLuiPersonRelations(String personId, String luiId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationDisplay> findLuiPersonRelationsForLui(String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationDisplay> findLuiPersonRelationsForPerson(String personId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationDisplay> findOrderedRelationStatesForLuiPersonRelation(String luiPersonRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findPersonIdsRelatedToLui(String luiId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RelationStateInfo> findRelationStates() throws OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RelationStateInfo> findValidRelationStatesForLuiPersonRelation(String personId, String luiId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isRelated(String personId, String luiId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isValidLuiPersonRelation(String personId, String luiId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<String> searchForLuiPersonRelationIds(LuiPersonRelationCriteria luiPersonRelationCriteria) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationDisplay> searchForLuiPersonRelations(LuiPersonRelationCriteria luiPersonRelationCriteria) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Status updateLuiPersonRelation(String luiPersonRelationId, LuiPersonRelationUpdateInfo luiPersonRelationUpdateInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Status updateRelationState(String luiPersonRelationId, RelationStateInfo relationStateInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValidationResult validateLuiPersonRelation(String personId, String luiId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

}
