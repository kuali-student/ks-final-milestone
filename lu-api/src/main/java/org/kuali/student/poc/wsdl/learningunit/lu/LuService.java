package org.kuali.student.poc.wsdl.learningunit.lu;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.CircularReferenceException;
import org.kuali.student.poc.common.ws.exceptions.DependentObjectsExistException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.poc.common.ws.exceptions.UnsupportedActionException;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluCreateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluCriteria;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluRelationAssignInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluRelationCriteria;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluRelationDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluRelationInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluRelationUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluSetCreateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluSetInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluSetUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuRelationType;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuTypeInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiCreateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiCriteria;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiRelationAssignInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiRelationCriteria;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiRelationDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiRelationInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiRelationUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.Status;

@WebService(name = "LuService", targetNamespace = "http://student.kuali.org/poc/wsdl/learningunit/lu")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LuService {
	/* Setup */
	@WebMethod
	public List<LuTypeInfo> findLuTypes() throws OperationFailedException;

	@WebMethod
	public List<LuRelationType> findLuRelationTypes()
			throws OperationFailedException;

	@WebMethod
	public LuTypeInfo fetchLuType(@WebParam(name = "luTypeId")
	String luTypeId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<LuRelationType> findAllowedLuLuRelationTypesForLuType(
			@WebParam(name = "luTypeId")
			String luTypeId, @WebParam(name = "relatedLuTypeId")
			String relatedLuTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/* Read */
	@WebMethod
	public CluInfo fetchClu(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public LuiInfo fetchLui(@WebParam(name = "luiId")
	String luiId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<CluDisplay> findClusForLuType(@WebParam(name = "luTypeId")
	String luTypeId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<String> findCluIdsForLuType(@WebParam(name = "luTypeId")
	String luTypeId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<LuiDisplay> findLuisForClu(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "atpId")
	String atpId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<String> findLuiIdsForClu(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "atpId")
	String atpId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<LuRelationType> findAllowedLuRelationTypesForClu(
			@WebParam(name = "cluId")
			String cluId, @WebParam(name = "relatedCluId")
			String relatedCluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public List<LuRelationType> findAllowedLuRelationTypesForLui(
			@WebParam(name = "luiId")
			String luiId, @WebParam(name = "relatedLuiId")
			String relatedLuiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the list of CLU information for the specified related CLU Id
	 * and LU to LU relation type (findRelatedClusForCluId from the other
	 * direction)
	 * 
	 * @param cluId
	 * @param luRelationType
	 * @return list of CLU information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<CluDisplay> findClusByRelation(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public List<String> findCluIdsByRelation(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public List<LuiDisplay> findLuisByRelation(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public List<String> findLuiIdsByRelation(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public List<CluDisplay> findRelatedClusForCluId(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public List<String> findRelatedCluIdsForCluId(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public List<CluDisplay> findRelatedLuisForLuiId(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public List<String> findRelatedLuiIdsForLuiId(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public CluRelationInfo fetchCluRelation(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "relatedCluId")
	String relatedCluId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public LuiRelationInfo fetchLuiRelation(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "relatedLuiId")
	String relatedLuiId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public List<CluRelationDisplay> findCluRelations(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public List<LuiRelationDisplay> findLuiRelations(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public String findPrerequisitesForDisplay(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<CluDisplay> findSimplePrerequisites(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public String findCorequisitesForDisplay(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<CluDisplay> findSimpleCorequisites(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public String findAntirequisitesForDisplay(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<CluDisplay> findSimpleAntirequisites(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public String findEquivalenciesForDisplay(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<CluDisplay> findSimpleEquivalencies(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public CluSetInfo fetchCluSetInfo(@WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	@WebMethod
	public List<CluDisplay> findClusFromCluSet(@WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<String> findCluIdsFromCluSet(@WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	@WebMethod
	public List<String> findCluSetIdsFromCluSet(@WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	@WebMethod
	public List<CluDisplay> findEnumeratedClusInCluSet(
			@WebParam(name = "cluSetId")
			String cluSetId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public List<String> findEnumeratedCluIdsInCluSet(
			@WebParam(name = "cluSetId")
			String cluSetId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public boolean isCluInCluSet(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/* Search */
	@WebMethod
	public List<CluDisplay> searchForClus(@WebParam(name = "cluCriteria")
	CluCriteria cluCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<LuiDisplay> searchForLuis(@WebParam(name = "luiCriteria")
	LuiCriteria luiCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<String> searchForCluIds(@WebParam(name = "cluCriteria")
	CluCriteria cluCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<String> searchForLuiIds(@WebParam(name = "luiCriteria")
	LuiCriteria luiCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException;

	@WebMethod
	public List<CluRelationDisplay> searchForCluRelations(
			@WebParam(name = "cluRelationCriteria")
			CluRelationCriteria cluRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public List<LuiRelationDisplay> searchForLuiRelations(
			@WebParam(name = "luiRelationCriteria")
			LuiRelationCriteria luiRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/* Maintenance */
	@WebMethod
	public String createClu(@WebParam(name = "luTypeId")
	String luTypeId, @WebParam(name = "cluCreateInfo")
	CluCreateInfo cluCreateInfo) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public String createLui(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "atpId")
	String atpId, @WebParam(name = "luiCreateInfo")
	LuiCreateInfo luiCreateInfo) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status updateClu(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "cluUpdateInfo")
	CluUpdateInfo cluUpdateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status updateLui(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "luiUpdateInfo")
	LuiUpdateInfo cluUpdateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status deleteClu(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, DependentObjectsExistException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status deleteLui(@WebParam(name = "luiId")
	String luiId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, DependentObjectsExistException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status assignCluRelation(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "relatedCluId")
	String relatedCluId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType, @WebParam(name = "cluRelationAssignInfo")
	CluRelationAssignInfo cluRelationAssignInfo) throws AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	@WebMethod
	public Status assignLuiRelation(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "relatedLuiId")
	String relatedLuiId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType, @WebParam(name = "luiRelationAssignInfo")
	LuiRelationAssignInfo luiRelationAssignInfo) throws AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	@WebMethod
	public Status updateCluRelation(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "relatedCluId")
	String relatedCluId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType, @WebParam(name = "cluRelationUpdateInfo")
	CluRelationUpdateInfo cluRelationUpdateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status updateLuiRelation(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "relatedLuiId")
	String relatedLuiId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType, @WebParam(name = "luiRelationUpdateInfo")
	LuiRelationUpdateInfo luiRelationUpdateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status deleteCluRelation(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "relatedCluId")
	String relatedCluId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status deleteLuiRelation(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "relatedLuiId")
	String relatedLuiId, @WebParam(name = "luRelationType")
	LuRelationType luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status addSimplePrerequisite(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "prereqCluId")
	String prereqCluId) throws DoesNotExistException, AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	@WebMethod
	public Status removeSimplePrerequisite(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "prereqCluId")
	String prereqCluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status addSimpleCorequisite(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "coreqCluId")
	String coreqCluId) throws DoesNotExistException, AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	@WebMethod
	public Status removeSimpleCorequisite(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "coreqCluId")
	String coreqCluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	@WebMethod
	public Status addSimpleAntirequisite(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "antireqCluId")
	String antireqCluId) throws DoesNotExistException, AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	@WebMethod
	public Status removeSimpleAntirequisite(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "antireqCluId")
	String antireqCluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status addSimpleEquivalency(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "equivalentCluId")
	String equivalentCluId) throws DoesNotExistException,
			AlreadyExistsException, CircularReferenceException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status removeSimpleEquivalency(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "equivalentCluId")
	String equivalentCluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public String createEnumeratedCluSet(@WebParam(name = "cluSetName")
	String cluSetName, @WebParam(name = "cluSetCreateInfo")
	CluSetCreateInfo cluSetCreateInfo) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public String createDynamicCluSet(@WebParam(name = "cluSetName")
	String cluSetName, @WebParam(name = "cluSetCreateInfo")
	CluSetCreateInfo cluSetCreateInfo, @WebParam(name = "cluCriteria")
	CluCriteria cluCriteria) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status updateCluSet(@WebParam(name = "cluSetId")
	String cluSetId, @WebParam(name = "cluSetUpdateInfo")
	CluSetUpdateInfo cluSetUpdateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status deleteCluSet(@WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	@WebMethod
	public Status addCluSetToCluSet(@WebParam(name = "cluSetId")
	String cluSetId, @WebParam(name = "addedCluSetId")
	String addedCluSetId) throws DoesNotExistException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	@WebMethod
	public Status removeCluSetFromCluSet(@WebParam(name = "cluSetId")
	String cluSetId, @WebParam(name = "removedCluSetId")
	String removedCluSetId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public Status addCluToCluSet(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, CircularReferenceException,
			InvalidParameterException, MissingParameterException,
			UnsupportedActionException, OperationFailedException,
			PermissionDeniedException;

	@WebMethod
	public Status removeCluFromCluSet(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, UnsupportedActionException,
			OperationFailedException, PermissionDeniedException;

}
