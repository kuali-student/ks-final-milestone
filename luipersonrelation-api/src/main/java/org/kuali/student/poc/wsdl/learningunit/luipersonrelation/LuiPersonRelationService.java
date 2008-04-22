package org.kuali.student.poc.wsdl.learningunit.luipersonrelation;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.poc.common.ws.exceptions.ReadOnlyException;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.Status;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationCreateInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationCriteria;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationDisplay;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationType;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationState;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.ValidationResult;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDisplay;

@WebService(name = "LuiPersonRelationService", targetNamespace = "http://student.kuali.org/poc/wsdl/learningunit/luipersonrelation")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LuiPersonRelationService {
    // Setup
    /**
     * Retrieves the list of LUIPersonRelation types.
     * @return list of luiPersonRelationTypes
     * @throws OperationFailedException unable to complete request
     */
    @WebMethod
    public List<LuiPersonRelationType> findLuiPersonRelationTypes() throws OperationFailedException;

    /**
     * Retrieves the list of RelationStates.
     * @return list of relation states
     * @throws OperationFailedException unable to complete request
     */
    @WebMethod
    public List<RelationState> findRelationStates() throws OperationFailedException;

    /**
     * Retrieves the list of Allowed Relation States.
     * 
     * @param luiPersonRelationType Type of LUI Person Relation
     * @return list of relationState
     * @throws OperationFailedException unable to complete request
     * @throws DoesNotExistException luiPersonRelationType not found
     * @throws InvalidParameterException invalid luiPersonRelationType
     * @throws MissingParameterException missing luiPersonRelationType
     */
    @WebMethod
    public List<RelationState> findAllowedRelationStates(@WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType) throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException;

    // Read
    /**
     * Retrieves the Relation for the specified LUI Person Relation.
     * 
     * @param luiPersonRelationId Identifier for the LUI Person Relation
     * @return LUI Person Relation information
     * @throws DoesNotExistException luiPersonRelationId not found
     * @throws InvalidParameterException invalid luiPersonRelationId
     * @throws MissingParameterException missing luiPersonRelationId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public LuiPersonRelationInfo fetchLUIPersonRelation(@WebParam(name = "luiPersonRelationId")
    String luiPersonRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the LUI Ids for Person related to LUI.
     * 
     * @param personId Identifier for the LUI Person Relation
     * @param luiPersonRelationType Type of LUI Person Relation
     * @param relationState Relation State
     * @return Simple list of LUI Ids
     * @throws DoesNotExistException personId, luiPersonRelationType, relationState, ? person to LUI relationship not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId, luiPersonRelationType, relationState
     * @throws MissingParameterException missing personId, luiPersonRelationType, relationState
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<String> findLuiIdsRelatedToPerson(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Ids related to the specified LUI.
     * 
     * @param luiId Identifier for the LUI
     * @param luiPersonRelationType Type of LUI Person Relation
     * @param relationState Relation State
     * @return Simple list of Person Ids
     * @throws DoesNotExistException luiId, luiPersonRelationType, relationState, ? LUI to person relationship not found
     * @throws InvalidParameterException invalid luiId, luiPersonRelationType, relationState
     * @throws MissingParameterException missing luiId, luiPersonRelationType, relationState
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<String> findPersonIdsRelatedToLui(@WebParam(name = "luiId")
    String luiId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Relation Types for LUI.
     * 
     * @param personId Identifier for person
     * @param luiId Identifier for LUI
     * @param relationState Relation State
     * @return Simple list of LUI Person Relation Types
     * @throws DoesNotExistException personId, luiId, relationState not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId, luiId, relationState
     * @throws MissingParameterException missing personId, luiId, relationState
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<LuiPersonRelationType> findLuiPersonRelationTypesForLuiPersonRelation(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Relation for LUI.
     * 
     * @param personId Identifier for person
     * @param luiId Identifier for LUI
     * @return List of LUI Person Relation display info
     * @throws DoesNotExistException personId, luiId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId, luiId
     * @throws MissingParameterException missing personId, luiId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<LuiPersonRelationDisplay> findLuiPersonRelations(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation Ids.
     * 
     * @param personId Identifier for person
     * @param luiId Identifier for LUI
     * @return List of LUI Person Relation display info
     * @throws DoesNotExistException personId, luiId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId, luiId
     * @throws MissingParameterException missing personId, luiId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<String> findLuiPersonRelationIds(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation for Person.
     * 
     * @param personId Identifier for person
     * @return List of LUI Person Relation display info
     * @throws DoesNotExistException personId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId
     * @throws MissingParameterException missing personId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<LuiPersonRelationDisplay> findLuiPersonRelationsForPerson(@WebParam(name = "personId")
    String personId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation Ids for Person.
     * 
     * @param personId Identifier for person
     * @return Simple list of person relation identifiers
     * @throws DoesNotExistException personId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId
     * @throws MissingParameterException missing personId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<String> findLuiPersonRelationIdsForPerson(@WebParam(name = "personId")
    String personId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation for a specified LUI.
     * 
     * @param luiId Identifier for LUI
     * @return List of LUI Person Relation display info
     * @throws DoesNotExistException luiId not found
     * @throws InvalidParameterException luiId not found
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<LuiPersonRelationDisplay> findLuiPersonRelationsForLui(@WebParam(name = "luiId")
    String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUIPersonRelation for LUI.
     * 
     * @param luiId Identifier for LUI
     * @return Simple list of LUI Person Relation identifiers
     * @throws DoesNotExistException luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<String> findLuiPersonRelationIdsForLui(@WebParam(name = "luiId")
    String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves valid Relation States for LUI Person relation.
     * 
     * @param personId Identifier for person
     * @param luiId Identifier for LUI
     * @param luiPersonRelationType Type of LUI Person Relation
     * @return list of valid relation states
     * @throws DoesNotExistException personId, luiId, luiPersonRelationType not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId, luiId, luiPersonRelationType
     * @throws MissingParameterException missing personId, luiId, luiPersonRelationType
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<RelationState> findValidRelationStatesForLuiPersonRelation(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Checks to see if it's ok to create a particular type and state of a relation between Person and LUI.
     * 
     * @param personId Identifier for person
     * @param luiId Identifier for LUI
     * @param luiPersonRelationType Type of LUI Person relation
     * @param relationState Relation state
     * @return true if relation of specified type and state is ok to create between person and lui
     * @throws DoesNotExistException personId, luiId, luiPersonRelationType, relationState not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId, luiId, luiPersonRelationType, relationState
     * @throws MissingParameterException missing personId, luiId, luiPersonRelationType, relationState
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public boolean isValidLuiPersonRelation(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Checks if a Person and LUI have a particular relation in a particular state.
     * 
     * @param personId Identifier for person
     * @param luiId Identifier for LUI
     * @param luiPersonRelationType Type of LUI Person Relation
     * @param relationState Relation state
     * @return true if relation of specified type and state exists between person and lui
     * @throws DoesNotExistException personId, luiId, luiPersonRelationType, relationState not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId, luiId, luiPersonRelationType, relationState
     * @throws MissingParameterException missing personId, luiId, luiPersonRelationType, relationState
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public boolean isRelated(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates the particular relation in a state for a Person and LUI.
     * 
     * @param personId Identifier for person
     * @param luiId Identifier for LUI
     * @param luiPersonRelationType Type of luiPersonRelationI
     * @param relationState Relation State
     * @return result from validation operation ? not sure of specifics
     * @throws DoesNotExistException personId, luiId, luiPersonRelationType, relationState not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId, luiId, luiPersonRelationType, relationState
     * @throws MissingParameterException missing personId, luiId, luiPersonRelationType, relationState
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public ValidationResult validateLuiPersonRelation(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUIs for an academic time period where the specified relation type and state would be valid for the specified person.
     * 
     * @param personId Identifier for person
     * @param luiPersonRelationType Type of luiPersonRelationI
     * @param relationState Relation State
     * @param atpId Identifier for academic time period
     * @return List of LUIs
     * @throws DoesNotExistException personId, luiPersonRelationType, relationState, atpId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId, luiPersonRelationType, relationState, atpId
     * @throws MissingParameterException missing personId, luiPersonRelationType, relationState, atpId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<LuiDisplay> findAllValidLuisForPerson(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState, @WebParam(name = "atpId")
    String atpId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Ids for an academic time period where the specified relation type and state would be valid for the specified person.
     * 
     * @param personId Identifier for person
     * @param luiPersonRelationType Type of LUI Person Relation
     * @param relationState Relation State
     * @param atpId identifier for the academic time period
     * @return List of LUI Ids for person, of a given relation type for a given academic time period
     * @throws DoesNotExistException personId, luiPersonRelationType, relationState, atpId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId, luiPersonRelationType, relationState, atpId
     * @throws MissingParameterException missing personId, luiPersonRelationType, relationState, atpId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<String> findAllValidLuiIdsForPerson(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState, @WebParam(name = "atpId")
    String atpId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of people where the specified relation type and state would be valid for the specified LUI.
     * 
     * @param luiId Identifier for LUI
     * @param luiPersonRelationType Type of LUI Person Relation
     * @param relationState Relation state
     * @return List of people that could have a particular relation with a LUI
     * @throws DoesNotExistException personId, luiPersonRelationType, relationState, atpId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid luiId, luiPersonRelationType, relationState, atpId
     * @throws MissingParameterException missing luiId, luiPersonRelationType, relationState, atpId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<PersonDisplay> findAllValidPeopleForLui(@WebParam(name = "luiId")
    String luiId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of person ids where the specified relation type and state would be valid for the specified LUI.
     * 
     * @param luiId Identifier for LUI
     * @param luiPersonRelationType Type of LUI Person Relation
     * @param relationState Relation state
     * @return List of Ids for people that have a particular relation with a LUI for an academic time period
     * @throws DoesNotExistException personId, luiPersonRelationType, relationState, atpId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId, luiPersonRelationType, relationState, atpId
     * @throws MissingParameterException missing personId, luiPersonRelationType, relationState, atpId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<String> findAllValidPersonIdsForLui(@WebParam(name = "luiId")
    String luiId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves an ordered list of the "history" of a particular LUI Person Relation, including state and date of change.
     * 
     * @param luiPersonRelationId Identifier for LUI Person Relation
     * @return List of LUI Person Relations ? Andy Bucior This might need to be slightly different from the normal luiPersonRelation display listing, since it is dealing with a relationship history type concept.
     * @throws DoesNotExistException luiPersonRelationId not found
     * @throws InvalidParameterException invalid luiPersonRelationId
     * @throws MissingParameterException missing luiPersonRelationId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<LuiPersonRelationDisplay> findOrderedRelationStatesForLuiPersonRelation(@WebParam(name = "luiPersonRelationId")
    String luiPersonRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    // Search
    /**
     * Retrieves details of LUI Person Relations.
     * 
     * @param luiPersonRelationCriteria criteria to be used for retrieval of multiple LUI Person relations
     * @return Simple list of LUI Person Relation info for display purposes
     * @throws InvalidParameterException invalid relation criteria
     * @throws MissingParameterException missing relation criteria
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<LuiPersonRelationDisplay> searchForLuiPersonRelations(@WebParam(name = "luiPersonRelationCriteria")
    LuiPersonRelationCriteria luiPersonRelationCriteria) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves detail of LUI Person Relation Ids.
     * 
     * @param luiPersonRelationCriteria Criteria to be used for retrieval of multiple LUI Person Relation identifiers
     * @return Simple list of LUI Person Relation identifiers
     * @throws InvalidParameterException invalid relation criteria
     * @throws MissingParameterException missing relation criteria
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<String> searchForLuiPersonRelationIds(@WebParam(name = "luiPersonRelationCriteria")
    LuiPersonRelationCriteria luiPersonRelationCriteria) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    // Maintenance
    /**
     * Creates relation between the specified Person and LUI.
     * 
     * @param personId Identifier for Person
     * @param luiId Identifier for LUI
     * @param relationState Relation State
     * @param luiPersonRelationType Type of LUI Person Relation
     * @param luiPersonRelationCreateInfo Information required to create the LUI Person relation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException relation already exists
     * @throws DoesNotExistException personId, luiId, relationState, luiPersonRelationType does not exist
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId, luiId, relationState, luiPersonRelationType, luiPersonRelation create info
     * @throws MissingParameterException missing personId, luiId, relationState, luiPersonRelationType, luiPersonRelation create info
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public String createLuiPersonRelation(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId, @WebParam(name = "relationState")
    RelationState relationState, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "luiPersonRelationCreateInfo")
    LuiPersonRelationCreateInfo luiPersonRelationCreateInfo) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates bulk relationships for one specified LUI. This is an all or nothing transaction - any error will invalidate the entire transaction.
     * 
     * @param luiId Identifier for LUI
     * @param personIdList Simple list of Person identifiers
     * @param relationState Relation state
     * @param luiPersonRelationType Type of LUI Person relation
     * @param luiPersonRelationCreateInfo Information required to create the LUI Person relation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException relation already exists
     * @throws DoesNotExistException luiId, personId, relationState, luiPersonRelationType does not exist
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid luiId, personId, relationState, luiPersonRelationType, luiPersonRelation create info
     * @throws MissingParameterException missing luiId, personId, relationState, luiPersonRelationType, luiPersonRelation create info
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<String> createBulkRelationshipsForLui(@WebParam(name = "luiId")
    String luiId, @WebParam(name = "personIdList")
    List<String> personIdList, @WebParam(name = "relationState")
    RelationState relationState, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "luiPersonRelationCreateInfo")
    LuiPersonRelationCreateInfo luiPersonRelationCreateInfo) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates bulk relationships for one specified person. This is an all or nothing transaction - any error will invalidate the entire transaction.
     * 
     * @param personId Identifier for Person
     * @param luiIdList Simple list of LUI identifiers
     * @param relationState Relation state
     * @param luiPersonRelationType Type of LUI Person relation
     * @param luiPersonRelationCreateInfo Information required to create the LUI Person relation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException relation already exists
     * @throws DoesNotExistException personId, luiId, relationState, luiPersonRelationType does not exist
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId, luiId, relationState, luiPersonRelationType, luiPersonRelation create info
     * @throws MissingParameterException missing personId, luiId, relationState, luiPersonRelationType, luiPersonRelation create info
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public List<String> createBulkRelationshipsForPerson(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiIdList")
    List<String> luiIdList, @WebParam(name = "relationState")
    RelationState relationState, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "luiPersonRelationCreateInfo")
    LuiPersonRelationCreateInfo luiPersonRelationCreateInfo) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Update relation between Person and LUI.
     * 
     * @param luiPersonRelationId Identifier for the LUI Person Relation
     * @param luiPersonRelationUpdateInfo Changed information about the LUI Person Relation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException luiPersonRelationId does not exist
     * @throws InvalidParameterException invalid luiPersonRelationId, luiPersonRelation update info
     * @throws MissingParameterException missing luiPersonRelationId, luiPersonRelation update info
     * @throws ReadOnlyException attempt to update a read only attribute
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public Status updateLuiPersonRelation(@WebParam(name = "luiPersonRelationId")
    String luiPersonRelationId, @WebParam(name = "luiPersonRelationUpdateInfo")
    LuiPersonRelationUpdateInfo luiPersonRelationUpdateInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes relation between the specified Person and LUI.
     * 
     * @param luiPersonRelationId Identifier for the LUI Person Relation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException luiPersonRelationId does not exist
     * @throws InvalidParameterException invalid luiPersonRelationId
     * @throws MissingParameterException missing luiPersonRelationId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public Status deleteLuiPersonRelation(@WebParam(name = "luiPersonRelationId")
    String luiPersonRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Update relation state. ? Yu-Tin Kuo Would we ever need a READ_ONLY error on the relationState?
     * 
     * @param luiPersonRelationId Identifier for the LUI Person Relation
     * @param relationState Relation state
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException luiPersonRelationId, relationState does not exist
     * @throws InvalidParameterException invalid luiPersonRelationId, relationState
     * @throws MissingParameterException missing luiPersonRelationId, relationState
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @WebMethod
    public Status updateRelationState(@WebParam(name = "luiPersonRelationId")
    String luiPersonRelationId, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}
