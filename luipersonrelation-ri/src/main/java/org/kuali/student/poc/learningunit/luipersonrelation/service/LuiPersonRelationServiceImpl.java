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
import org.kuali.student.poc.wsdl.learningunit.lu.LuService;
import org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService;
import org.kuali.student.poc.wsdl.personidentity.person.PersonService;
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
    private PersonService personClient;
    private LuService luClient;

    public LuiPersonRelationDAO getDao() {
        return dao;
    }

    public void setDao(LuiPersonRelationDAO dao) {
        this.dao = dao;
    }

    public PersonService getPersonClient() {
        return personClient;
    }

    public void setPersonClient(PersonService personClient) {
        this.personClient = personClient;
    }
    
    public LuService getLuClient() {
        return luClient;
    }

    public void setLuClient(LuService luClient) {
        this.luClient = luClient;
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
        // I wonder if error handling should be somewhat different somehow, but I doubt it
        List<String> ids = new ArrayList<String>();
        for (String luiId : luiIdList) {
            ids.add(createLuiPersonRelation(personId, luiId, relationStateInfo, luiPersonRelationTypeInfo, luiPersonRelationCreateInfo));
        }
        return ids;
    }

    @Override
    public String createLuiPersonRelation(String personId, String luiId, RelationStateInfo relationStateInfo, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, LuiPersonRelationCreateInfo luiPersonRelationCreateInfo) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        String id = null;
        
        if (validateLuiPersonRelation(personId, luiId, luiPersonRelationTypeInfo, relationStateInfo).isSuccess()
                && isCreatableRelation(luiId, luiPersonRelationTypeInfo, relationStateInfo)) {
            LuiPersonRelation luiPersonRelation = new LuiPersonRelation();
            luiPersonRelation.setPersonId(personId);
            luiPersonRelation.setLuiId(luiId);
            luiPersonRelation.setRelationState(relationStateInfo.getState());
            luiPersonRelation.setLuiPersonRelationType(luiPersonRelationTypeInfo.getName());
            luiPersonRelation.setEffectiveEndDate(luiPersonRelationCreateInfo.getEffectiveEndDate());
            luiPersonRelation.setEffectiveStartDate(luiPersonRelationCreateInfo.getEffectiveStartDate());
            id = dao.createLuiPersonRelation(luiPersonRelation).getId();
        }
       
        return id;
    }

    @Override
    public Status deleteLuiPersonRelation(String luiPersonRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        //TODO
        boolean success = dao.deleteLuiPersonRelation(luiPersonRelationId);

        // this is of course stupid since if there is no success, an error gets thrown
        Status status = new Status();
        status.setSuccess(success);
        return status;
    }

    @Override
    public LuiPersonRelationInfo fetchLUIPersonRelation(String luiPersonRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        LuiPersonRelation luiPersonRelation = dao.lookupLuiPersonRelation(luiPersonRelationId);
        return toLuiPersonRelationInfo(luiPersonRelation);
    }

    @Override
    public List<String> findAllValidLuiIdsForPerson(String personId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo, String atpId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Do we lookup lui to filter on atpId
        return null;
    }

    @Override
    public List<LuiDisplay> findAllValidLuisForPerson(String personId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo, String atpId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Do we lookup lui to filter on atpId
        return null;
    }

    @Override
    public List<PersonDisplay> findAllValidPeopleForLui(String luiId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        // TODO: Does this belong in this service? PersonDisplay could be obtained from Person Service, but we
        //can just make a call to person service
        return null;
    }

    @Override
    public List<String> findAllValidPersonIdsForLui(String luiId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //TODO: Incorporate relation state info
        
        List<LuiPersonRelation> luiPersonRelations = dao.findLuiPersonRelationsByLui(luiId, luiPersonRelationTypeInfo.getName(), relationStateInfo.getState());
        
        List<String> personIdList = new ArrayList<String>();        
        for (LuiPersonRelation lpr : luiPersonRelations) {
            personIdList.add(lpr.getPersonId());
        }        

        return personIdList;
    }

    @Override
    public List<RelationStateInfo> findAllowedRelationStates(LuiPersonRelationTypeInfo luiPersonRelationTypeInfo) throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> findLuiIdsRelatedToPerson(String personId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //TODO: Incorporate relation state info
        
        List<LuiPersonRelation> luiPersonRelations = dao.findLuiPersonRelationsByPerson(personId, luiPersonRelationTypeInfo.getName(), relationStateInfo.getState());
        List<String> personIdList = new ArrayList<String>();

        List<String> luiIdList = new ArrayList<String>();
        
        for (LuiPersonRelation lpr : luiPersonRelations) {
            personIdList.add(lpr.getLuiId());
        }        
     
        return luiIdList;
    }

    @Override
    public List<String> findLuiPersonRelationIds(String personId, String luiId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        
        return null;
    }

    @Override
    public List<String> findLuiPersonRelationIdsForLui(String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelation> luiPersonRelations = dao.findLuiPersonRelationsByLui(luiId, "%", "%");
        
        List<String> luiPersonRelationIdList = new ArrayList<String>();        
        for (LuiPersonRelation lpr : luiPersonRelations) {
            luiPersonRelationIdList.add(lpr.getId());
        }        
        
        return luiPersonRelationIdList;
    }

    @Override
    public List<String> findLuiPersonRelationIdsForPerson(String personId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelation> luiPersonRelations = dao.findLuiPersonRelationsByPerson(personId, "%", "%");

        List<String> luiPersonRelationIdList = new ArrayList<String>();
        
        for (LuiPersonRelation lpr : luiPersonRelations) {
            luiPersonRelationIdList.add(lpr.getId());
        }
        
        return luiPersonRelationIdList;
    }

    @Override
    public List<LuiPersonRelationTypeInfo> findLuiPersonRelationTypes() throws OperationFailedException {
        // TODO Make up relation types to send back
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
        List<LuiPersonRelation> luiPersonRelations = dao.findLuiPersonRelationsByLui(luiId, "%", "%");
        
        List<LuiPersonRelationDisplay> lprDisplayList = new ArrayList<LuiPersonRelationDisplay>();        
        for (LuiPersonRelation lpr : luiPersonRelations) {                                   
            lprDisplayList.add(toLuiPersonRelationDisplay(lpr));
        }        
        
        return lprDisplayList;
    }

    @Override
    public List<LuiPersonRelationDisplay> findLuiPersonRelationsForPerson(String personId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelation> luiPersonRelations = dao.findLuiPersonRelationsByPerson(personId, "%", "%");
      
        List<LuiPersonRelationDisplay> lprDisplayList = new ArrayList<LuiPersonRelationDisplay>();        
        for (LuiPersonRelation lpr : luiPersonRelations) {                                   
            lprDisplayList.add(toLuiPersonRelationDisplay(lpr));
        }        
        
        return lprDisplayList;          
    }

    @Override
    public List<LuiPersonRelationDisplay> findOrderedRelationStatesForLuiPersonRelation(String luiPersonRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findPersonIdsRelatedToLui(String luiId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelation> luiPersonRelations = dao.findLuiPersonRelationsByLui(luiId, luiPersonRelationTypeInfo.getName(), relationStateInfo.getState());
        
        List<String> personIdList = new ArrayList<String>();        
        for (LuiPersonRelation lpr : luiPersonRelations) {
            personIdList.add(lpr.getPersonId());
        }        
        
        return personIdList;
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
        LuiPersonRelation luiPersonRelation = dao.findLuiPersonRelation(luiPersonRelationId);
        if(luiPersonRelation == null)
            throw new DoesNotExistException("No LUI Person Relation with id "+luiPersonRelationId);
        luiPersonRelation.setEffectiveEndDate(luiPersonRelationUpdateInfo.getEffectiveEndDate());
        luiPersonRelation.setEffectiveStartDate(luiPersonRelationUpdateInfo.getEffectiveStartDate());
        luiPersonRelation.setLuiPersonRelationType(luiPersonRelationUpdateInfo.getLuiPersonRelationType().getName());
        luiPersonRelation.setRelationState(luiPersonRelationUpdateInfo.getRelationState().getState());
        dao.updateLuiPersonRelation(luiPersonRelation);
        
        Status status = new Status();
        status.setSuccess(true); //if no exceptions, then it must've worked
        return status;
    }

    @Override
    public Status updateRelationState(String luiPersonRelationId, RelationStateInfo relationStateInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiPersonRelation luiPersonRelation = dao.findLuiPersonRelation(luiPersonRelationId);
        if(luiPersonRelation == null)
            throw new DoesNotExistException("No LUI Person Relation with id "+luiPersonRelationId);
        luiPersonRelation.setRelationState(relationStateInfo.getState());
        dao.updateLuiPersonRelation(luiPersonRelation);
        
        Status status = new Status();
        status.setSuccess(true); //if no exceptions, then it must've worked
        return status;
    }

    @Override
    public ValidationResult validateLuiPersonRelation(String personId, String luiId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Use "rules" for validation (eg. prereq checks).
        
        ValidationResult validationResult = new ValidationResult();        
        validationResult.setSuccess(true);
                
        return validationResult;
    }
    
    private boolean isCreatableRelation(String luiId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, RelationStateInfo relationStateInfo){
        //TODO: Relation should not be creatable if seats full
        return true;
    }

    private LuiPersonRelationInfo toLuiPersonRelationInfo(LuiPersonRelation luiPersonRelation) {
        LuiPersonRelationInfo luiPersonRelationInfo = new LuiPersonRelationInfo();

        luiPersonRelationInfo.setEffectiveEndDate(luiPersonRelation.getEffectiveEndDate());
        luiPersonRelationInfo.setEffectiveStartDate(luiPersonRelation.getEffectiveStartDate());
        // TODO copy rest of fields

        return luiPersonRelationInfo;
    }
    
    private LuiPersonRelationDisplay toLuiPersonRelationDisplay(LuiPersonRelation lpr){
        LuiPersonRelationDisplay lprDisplay = new LuiPersonRelationDisplay();
        
        //TODO: Call person service to get person display
        PersonDisplay personDisplay = null;
        lprDisplay.setPersonDisplay(personDisplay);
        
        //TODO: Call lui service to get LUI display
        LuiDisplay luiDisplay = null;
        lprDisplay.setLuiDisplay(luiDisplay);
        
        LuiPersonRelationTypeInfo lprTypeInfo = new LuiPersonRelationTypeInfo();
        lprTypeInfo.setName(lpr.getLuiPersonRelationType());            
        lprDisplay.setLuiPersonRelationType(lprTypeInfo);
        
        lprDisplay.setLuiPersonRelationId(lpr.getId());
        
        RelationStateInfo relationStateInfo = new RelationStateInfo();
        relationStateInfo.setState(lpr.getRelationState());           
        lprDisplay.setRelationState(relationStateInfo);
        
        return lprDisplay;
    }

}
