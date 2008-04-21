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
    @WebMethod
    public List<LuiPersonRelationType> findLuiPersonRelationTypes() throws OperationFailedException;

    @WebMethod
    public List<RelationState> findRelationStates() throws OperationFailedException;

    @WebMethod
    public List<RelationState> findAllowedRelationStates(@WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType) throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException;

    // Read
    @WebMethod
    public LuiPersonRelationInfo fetchLUIPersonRelation(@WebParam(name = "luiPersonRelationId")
    String luiPersonRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<String> findLuiIdsRelatedToPerson(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<String> findPersonIdsRelatedToLui(@WebParam(name = "luiId")
    String luiId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<LuiPersonRelationType> findLuiPersonRelationTypesForLuiPersonRelation(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<LuiPersonRelationDisplay> findLuiPersonRelations(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<String> findLuiPersonRelationIds(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<LuiPersonRelationDisplay> findLuiPersonRelationsForPerson(@WebParam(name = "personId")
    String personId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<String> findLuiPersonRelationIdsForPerson(@WebParam(name = "personId")
    String personId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<LuiPersonRelationDisplay> findLuiPersonRelationsForLui(@WebParam(name = "luiId")
    String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<String> findLuiPersonRelationIdsForLui(@WebParam(name = "luiId")
    String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<RelationState> findValidRelationStatesForLuiPersonRelation(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public boolean isValidLuiPersonRelation(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public boolean isRelated(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public ValidationResult validateLuiPersonRelation(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<LuiDisplay> findAllValidLuisForPerson(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState, @WebParam(name = "atpId")
    String atpId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<String> findAllValidLuiIdsForPerson(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState, @WebParam(name = "atpId")
    String atpId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<PersonDisplay> findAllValidPeopleForLui(@WebParam(name = "luiId")
    String luiId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<String> findAllValidPersonIdsForLui(@WebParam(name = "luiId")
    String luiId, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<LuiPersonRelationDisplay> findOrderedRelationStatesForLuiPersonRelation(@WebParam(name = "luiPersonRelationId")
    String luiPersonRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    // Search
    @WebMethod
    public List<LuiPersonRelationDisplay> searchForLuiPersonRelations(@WebParam(name = "luiPersonRelationCriteria")
    LuiPersonRelationCriteria luiPersonRelationCriteria) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<String> searchForLuiPersonRelationIds(@WebParam(name = "luiPersonRelationCriteria")
    LuiPersonRelationCriteria luiPersonRelationCriteria) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    // Maintenance
    @WebMethod
    public String createLuiPersonRelation(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiId")
    String luiId, @WebParam(name = "relationState")
    RelationState relationState, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "luiPersonRelationCreateInfo")
    LuiPersonRelationCreateInfo luiPersonRelationCreateInfo) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<String> createBulkRelationshipsForLui(@WebParam(name = "luiId")
    String luiId, @WebParam(name = "personIdList")
    List<String> personIdList, @WebParam(name = "relationState")
    RelationState relationState, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "luiPersonRelationCreateInfo")
    LuiPersonRelationCreateInfo luiPersonRelationCreateInfo) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public List<String> createBulkRelationshipsForPerson(@WebParam(name = "personId")
    String personId, @WebParam(name = "luiIdList")
    List<String> luiIdList, @WebParam(name = "relationState")
    RelationState relationState, @WebParam(name = "luiPersonRelationType")
    LuiPersonRelationType luiPersonRelationType, @WebParam(name = "luiPersonRelationCreateInfo")
    LuiPersonRelationCreateInfo luiPersonRelationCreateInfo) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public Status updateLuiPersonRelation(@WebParam(name = "luiPersonRelationId")
    String luiPersonRelationId, @WebParam(name = "luiPersonRelationUpdateInfo")
    LuiPersonRelationUpdateInfo luiPersonRelationUpdateInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public Status deleteLuiPersonRelation(@WebParam(name = "luiPersonRelationId")
    String luiPersonRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @WebMethod
    public Status updateRelationState(@WebParam(name = "luiPersonRelationId")
    String luiPersonRelationId, @WebParam(name = "relationState")
    RelationState relationState) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}
