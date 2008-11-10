package edu.umd.ks.poc.lum.lu.service;

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
import edu.umd.ks.poc.lum.lu.dto.CluCreateInfo;
import edu.umd.ks.poc.lum.lu.dto.CluCriteria;
import edu.umd.ks.poc.lum.lu.dto.CluDisplay;
import edu.umd.ks.poc.lum.lu.dto.CluInfo;
import edu.umd.ks.poc.lum.lu.dto.CluRelationAssignInfo;
import edu.umd.ks.poc.lum.lu.dto.CluRelationCriteria;
import edu.umd.ks.poc.lum.lu.dto.CluRelationDisplay;
import edu.umd.ks.poc.lum.lu.dto.CluRelationInfo;
import edu.umd.ks.poc.lum.lu.dto.CluRelationUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.CluSetCreateInfo;
import edu.umd.ks.poc.lum.lu.dto.CluSetInfo;
import edu.umd.ks.poc.lum.lu.dto.CluSetUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.CluUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.LuAttributeTypeInfo;
import edu.umd.ks.poc.lum.lu.dto.LuTypeInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiCreateInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiCriteria;
import edu.umd.ks.poc.lum.lu.dto.LuiDisplay;
import edu.umd.ks.poc.lum.lu.dto.LuiInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiRelationAssignInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiRelationCriteria;
import edu.umd.ks.poc.lum.lu.dto.LuiRelationDisplay;
import edu.umd.ks.poc.lum.lu.dto.LuiRelationInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiRelationUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.Status;

@WebService(name = "LuService", targetNamespace = "http://edu.umd.ks/poc/lum/lu")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LuService {
	/* Setup */
	/**
	 * Retrieves the list of LU types
	 * 
	 * @return list of LU types
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<LuTypeInfo> findLuTypes() throws OperationFailedException;

	/**
	 * Retrieves the list of LU to LU relation types
	 * 
	 * @return list of LU to LU relation types
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<String> findLuRelationTypes() throws OperationFailedException;

	/**
	 * Retrieves information about a LU Type
	 * 
	 * @param luTypeKey
	 * @return information about a LU Type
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public LuTypeInfo fetchLuType(@WebParam(name = "luTypeKey")
	String luTypeKey) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves the list of allowed relation types between the two specified LU
	 * Types
	 * 
	 * @param luTypeKey
	 * @param relatedLuTypeKey
	 * @return list of LU to LU relation types
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<String> findAllowedLuLuRelationTypesForLuType(
			@WebParam(name = "luTypeKey")
			String luTypeKey, @WebParam(name = "relatedLuTypeKey")
			String relatedLuTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/* Read */
	/**
	 * Retrieves information about a CLU
	 * 
	 * @param cluId
	 * @return information about a CLU
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public CluInfo fetchClu(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves information about a LUI
	 * 
	 * @param luiId
	 * @return information about a LUI
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public LuiInfo fetchLui(@WebParam(name = "luiId")
	String luiId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;
	/**
	 * Retrieves Display information about a LUI
	 * 
	 * @param luiId
	 * @return Display information about a LUI
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public LuiDisplay fetchLuiDisplay(@WebParam(name = "luiId")
	String luiId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;
	/**
	 * Retrieves the list of CLUs for the specified LU Type
	 * 
	 * @param luTypeKey
	 * @return list of CLU information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<CluDisplay> findClusForLuType(@WebParam(name = "luTypeKey")
	String luTypeKey) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves the list of CLU ids for the specified LU Type
	 * 
	 * @param luTypeKey
	 * @return list of CLU identifiers
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<String> findCluIdsForLuType(@WebParam(name = "luTypeKey")
	String luTypeKey) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves the list of LUIs for the specified CLU
	 * 
	 * @param cluId
	 * @param atpId
	 * @return list of LUI information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<LuiDisplay> findLuisForClu(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "atpId")
	String atpId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves the list of LUI ids for the specified CLU
	 * 
	 * @param cluId
	 * @param atpId
	 * @return list of LUI identifiers
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<String> findLuiIdsForClu(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "atpId")
	String atpId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves the list of allowed relation types between the two specified
	 * CLUs
	 * 
	 * @param cluId
	 * @param relatedCluId
	 * @return list of LU to LU relation types
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<String> findAllowedLuRelationTypesForClu(
			@WebParam(name = "cluId")
			String cluId, @WebParam(name = "relatedCluId")
			String relatedCluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the list of allowed relation types between the two specified
	 * LUIs
	 * 
	 * @param luiId
	 * @param relatedLuiId
	 * @return list of LU to LU relation types
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<String> findAllowedLuRelationTypesForLui(
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
	String cluId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the list of CLU Ids for the specified related CLU Id and LU to
	 * LU relation type (findRelatedCluIdsForCluId from the other direction)
	 * 
	 * @param cluId
	 * @param luRelationTypeId
	 * @return list of CLU identifiers
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<String> findCluIdsByRelation(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the list of LUI information for the specified related LUI Id
	 * and LU to LU relation type (findRelatedLuisForLuiId from the other
	 * direction)
	 * 
	 * @param luiId
	 * @param luRelationTypeId
	 * @return list of LUI information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<LuiDisplay> findLuisByRelation(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the list of LUI Ids for the specified related LUI Id and LU to
	 * LU relation type (findRelatedLuiIdsForLuiId from the other direction)
	 * 
	 * @param luiId
	 * @param luRelationTypeId
	 * @return list of LUI identifiers
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<String> findLuiIdsByRelation(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the list of related CLU information for the specified LUI Id
	 * and LU to LU relation type (findClusByRelation from the other direction)
	 * 
	 * @param cluId
	 * @param luRelationTypeId
	 * @return list of CLU information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<CluDisplay> findRelatedClusForCluId(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the list of related CLU Ids for the specified LUI Id and LU to
	 * LU relation type (findCluIdsByRelation from the other direction)
	 * 
	 * @param cluId
	 * @param luRelationTypeId
	 * @return list of CLU identifiers
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<String> findRelatedCluIdsForCluId(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the list of related LUI information for the specified LUI Id
	 * and LU to LU relation type (findLuisByRelation from the other direction)
	 * 
	 * @param luiId
	 * @param luRelationTypeId
	 * @return list of LUI information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<CluDisplay> findRelatedLuisForLuiId(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the list of related LUI Ids for the specified LUI Id and LU to
	 * LU relation type. (findLuiIdsByRelation from the other direction)
	 * 
	 * @param luiId
	 * @param luRelationTypeId
	 * @return list of LUI identifiers
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<String> findRelatedLuiIdsForLuiId(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the relationship information between the specified CLUs
	 * 
	 * @param cluId
	 * @param relatedCluId
	 * @param luRelationTypeId
	 * @return information on the relation between two CLUs
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public CluRelationInfo fetchCluRelation(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "relatedCluId")
	String relatedCluId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the relationship information between the specified LUIs
	 * 
	 * @param luiId
	 * @param relatedLuiId
	 * @param luRelationTypeId
	 * @return information on the relation between two LUIs
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public LuiRelationInfo fetchLuiRelation(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "relatedLuiId")
	String relatedLuiId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the list of relationship information for the specified CLU
	 * 
	 * @param cluId
	 * @param luRelationType
	 * @return list of relation information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<CluRelationDisplay> findCluRelations(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "luRelationType")
	String luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the list of relationship information for the specified LUI
	 * 
	 * @param luiId
	 * @param luRelationTypeId
	 * @return list of relation information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<LuiRelationDisplay> findLuiRelations(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves an "English" translation of the prerequisites for the target
	 * CLU.
	 * 
	 * @param cluId
	 * @return "English" description of prerequisites
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public String findPrerequisitesForDisplay(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves list of simple prerequisite(s) for the target CLU.
	 * 
	 * @param cluId
	 * @return list of simple prerequisite(s), separated by commas with and
	 *         before last one
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<CluDisplay> findSimplePrerequisites(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves an "English" translation of the corequisites for the target
	 * CLU.
	 * 
	 * @param cluId
	 * @return "English" description of corequisites
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public String findCorequisitesForDisplay(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves list of simple corequisites for the target CLU.
	 * 
	 * @param cluId
	 * @return list of simple corequisites
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<CluDisplay> findSimpleCorequisites(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves an "English" translation of the antirequisites for the target
	 * CLU.
	 * 
	 * @param cluId
	 * @return "English" description of antirequisites
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public String findAntirequisitesForDisplay(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves list of simple antirequisites for the target CLU.
	 * 
	 * @param cluId
	 * @return list of simple antirequisites
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<CluDisplay> findSimpleAntirequisites(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves an "English" translation of the equivalencies for the target
	 * CLU.
	 * 
	 * @param cluId
	 * @return "English" description of equivalencies
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public String findEquivalenciesForDisplay(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves list of simple equivalencies for the target CLU.
	 * 
	 * @param cluId
	 * @return list of simple equivalencies
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<CluDisplay> findSimpleEquivalencies(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * 
	 * Retrieve information on a CLU set. This information should be about the
	 * set itself, and in the case of a dynamic CLU set, should include the
	 * criteria used to generate the set.
	 * 
	 * @param cluSetId
	 * @return The retrieved CLU set information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public CluSetInfo fetchCluSetInfo(@WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves the list of CLUs in a CLU set. This list will be flattened and
	 * de-duplicated in the case of the CLU set containing additional CLU sets.
	 * 
	 * @param cluSetId
	 * @return The retrieved list of information on the CLUs within the CLU set
	 *         (flattened and de-duped)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<CluDisplay> findClusFromCluSet(@WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves the list of CLU Identifiers within a CLU Set. This list will be
	 * flattened and de-duplicated in the case of the CLU set containing
	 * additional CLU sets.
	 * 
	 * @param cluSetId
	 * @return The retrieved list of CLU Ids within the specified CLU set
	 *         (flattened and de-duped)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<String> findCluIdsFromCluSet(@WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieve the list of CLU Set Ids within a CLU Set
	 * 
	 * @param cluSetId
	 * @return The retrieved list of CLU Set Ids within the specified CLU set
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<String> findCluSetIdsFromCluSet(@WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves the full list of CLUs in this CLU set. Duplicate CLUs resulting
	 * from additional CLU set members will be included in the results.
	 * 
	 * @param cluSetId
	 * @return The retrieved list of information on the CLUs within the CLU set
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<CluDisplay> findEnumeratedClusInCluSet(
			@WebParam(name = "cluSetId")
			String cluSetId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves the list of CLU Identifiers within a CLU Set. Duplicate CLU Ids
	 * resulting from additional CLU set members will be included in the
	 * results.
	 * 
	 * @param cluSetId
	 * @return The retrieved list of CLU Ids within the specified CLU set
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<String> findEnumeratedCluIdsInCluSet(
			@WebParam(name = "cluSetId")
			String cluSetId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Checks if a CLU is a member of a CLU set or any contained CLU set
	 * 
	 * @param cluId
	 * @param cluSetId
	 * @return True if the CLU is a member of the CLU Set
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public boolean isCluInCluSet(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/* Search */
	/**
	 * Retrieves CLU information by criteria
	 * 
	 * @param cluCriteria
	 * @return criteria to be used for retrieval of multiple CLUs
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<CluDisplay> searchForClus(@WebParam(name = "cluCriteria")
	CluCriteria cluCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves LUI information by criteria
	 * 
	 * @param luiCriteria
	 * @return list of information about LUIs that match the supplied criteria
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<LuiDisplay> searchForLuis(@WebParam(name = "luiCriteria")
	LuiCriteria luiCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves CLU ids by criteria
	 * 
	 * @param cluCriteria
	 * @return list of CLU identifiers that match the supplied criteria
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<String> searchForCluIds(@WebParam(name = "cluCriteria")
	CluCriteria cluCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves LUI ids by criteria
	 * 
	 * @param luiCriteria
	 * @return list of LUI identifiers that match the supplied criteria
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<String> searchForLuiIds(@WebParam(name = "luiCriteria")
	LuiCriteria luiCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves CLU to CLU relations by criteria
	 * 
	 * @param cluRelationCriteria
	 * @return list of CLU to CLU relations that match the supplied criteria
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<CluRelationDisplay> searchForCluRelations(
			@WebParam(name = "cluRelationCriteria")
			CluRelationCriteria cluRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves Lui to Lui relations by criteria
	 * 
	 * @param luiRelationCriteria
	 * @return list of LUI to LUI relations that match the supplied criteria
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<LuiRelationDisplay> searchForLuiRelations(
			@WebParam(name = "luiRelationCriteria")
			LuiRelationCriteria luiRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/* Maintenance */
	/**
	 * Creates a CLU record
	 * 
	 * @param luTypeKey
	 * @param cluCreateInfo
	 * @return identifier for the newly created CLU
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public String createClu(@WebParam(name = "luTypeKey")
	String luTypeKey, @WebParam(name = "cluCreateInfo")
	CluCreateInfo cluCreateInfo) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Creates a LUI record
	 * 
	 * @param cluId
	 * @param atpId
	 * @param luiCreateInfo
	 * @return identifier for the newly created LUI
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public String createLui(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "atpId")
	String atpId, @WebParam(name = "luiCreateInfo")
	LuiCreateInfo luiCreateInfo) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Updates a CLU record
	 * 
	 * @param cluId
	 * @param cluUpdateInfo
	 * @return status of the operation
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status updateClu(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "cluUpdateInfo")
	CluUpdateInfo cluUpdateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Updates a LUI record
	 * 
	 * @param luiId
	 * @param cluUpdateInfo
	 * @return status of the operation
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status updateLui(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "luiUpdateInfo")
	LuiUpdateInfo cluUpdateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Deletes a CLU record
	 * 
	 * @param cluId
	 * @return status of the operation
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws DependentObjectsExistException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status deleteClu(@WebParam(name = "cluId")
	String cluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, DependentObjectsExistException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Deletes a LUI record
	 * 
	 * @param luiId
	 * @return status of the operation
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws DependentObjectsExistException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status deleteLui(@WebParam(name = "luiId")
	String luiId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, DependentObjectsExistException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Assigns a relationship between two CLUs
	 * 
	 * @param cluId
	 * @param relatedCluId
	 * @param luRelationType
	 * @param cluRelationAssignInfo
	 * @return status of the operation (success or failure)
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status assignCluRelation(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "relatedCluId")
	String relatedCluId, @WebParam(name = "luRelationType")
	String luRelationType, @WebParam(name = "cluRelationAssignInfo")
	CluRelationAssignInfo cluRelationAssignInfo) throws AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Assigns a relationship between two LUIs
	 * 
	 * @param luiId
	 * @param relatedLuiId
	 * @param luRelationTypeId
	 * @param luiRelationAssignInfo
	 * @return status of the operation (success or failure)
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status assignLuiRelation(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "relatedLuiId")
	String relatedLuiId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId, @WebParam(name = "luiRelationAssignInfo")
	LuiRelationAssignInfo luiRelationAssignInfo) throws AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Updates a relationship between two CLUs
	 * 
	 * @param cluId
	 * @param relatedCluId
	 * @param luRelationTypeId
	 * @param cluRelationUpdateInfo
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status updateCluRelation(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "relatedCluId")
	String relatedCluId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId, @WebParam(name = "cluRelationUpdateInfo")
	CluRelationUpdateInfo cluRelationUpdateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Updates a relationship between two LUIs
	 * 
	 * @param luiId
	 * @param relatedLuiId
	 * @param luRelationType
	 * @param luiRelationUpdateInfo
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status updateLuiRelation(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "relatedLuiId")
	String relatedLuiId, @WebParam(name = "luRelationType")
	String luRelationType, @WebParam(name = "luiRelationUpdateInfo")
	LuiRelationUpdateInfo luiRelationUpdateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Deletes a relationship between two CLUs
	 * 
	 * @param cluId
	 * @param relatedCluId
	 * @param luRelationTypeId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status deleteCluRelation(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "relatedCluId")
	String relatedCluId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Deletes a relationship between two LUIs
	 * 
	 * @param luiId
	 * @param relatedLuiId
	 * @param luRelationTypeId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status deleteLuiRelation(@WebParam(name = "luiId")
	String luiId, @WebParam(name = "relatedLuiId")
	String relatedLuiId, @WebParam(name = "luRelationTypeId")
	String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Adds a CLU as the prerequisite of another CLU. If there are two CLUs that
	 * must be completed as part of prereq, in no particular order, then this
	 * method would be used twice, once to add first prereq and then again to
	 * add the second. Logic is limited to "and"s between multiple prereq CLUs.
	 * 
	 * @param cluId
	 * @param prereqCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status addSimplePrerequisite(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "prereqCluId")
	String prereqCluId) throws DoesNotExistException, AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Remove CLU as the prerequisite of another CLU
	 * 
	 * @param cluId
	 * @param prereqCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status removeSimplePrerequisite(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "prereqCluId")
	String prereqCluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Adds a CLU as the corequisite of another CLU. If there are two CLUs that
	 * must be completed as part of the coreq, in no particular order, then this
	 * method would be used twice, once to add first coreq and then again to add
	 * the second. Logic is limited to "and"s between multiple coreq CLUs.
	 * 
	 * @param cluId
	 * @param coreqCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status addSimpleCorequisite(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "coreqCluId")
	String coreqCluId) throws DoesNotExistException, AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Remove CLU as the corequisite of another CLU
	 * 
	 * @param cluId
	 * @param coreqCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status removeSimpleCorequisite(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "coreqCluId")
	String coreqCluId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Adds a CLU as the antirequisite of another CLU. If there are multiple
	 * CLUs that are antireqs, then this method would be used once for each
	 * antireq CLU. Logic is limited to "and"s between multiple antireq CLUs.
	 * 
	 * @param cluId
	 * @param antireqCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status addSimpleAntirequisite(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "antireqCluId")
	String antireqCluId) throws DoesNotExistException, AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Remove CLU as the antirequisite of another CLU
	 * 
	 * @param cluId
	 * @param antireqCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status removeSimpleAntirequisite(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "antireqCluId")
	String antireqCluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Adds a CLU as an equivalent of another CLU. If there are multiple CLUs
	 * that are equivalent, then this method would be used once for each
	 * equivalent CLU. Logic is limited to "and"s between multiple equivalent
	 * CLUs.
	 * 
	 * @param cluId
	 * @param equivalentCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status addSimpleEquivalency(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "equivalentCluId")
	String equivalentCluId) throws DoesNotExistException,
			AlreadyExistsException, CircularReferenceException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Remove CLU as the equivalent of another CLU
	 * 
	 * @param cluId
	 * @param equivalentCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status removeSimpleEquivalency(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "equivalentCluId")
	String equivalentCluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Creates a CLU set with manually maintained membership. Sets created in
	 * this manner can contain other sets.
	 * 
	 * @param cluSetName
	 * @param cluSetCreateInfo
	 * @return the identifier of the created CLU set
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public String createEnumeratedCluSet(@WebParam(name = "cluSetName")
	String cluSetName, @WebParam(name = "cluSetCreateInfo")
	CluSetCreateInfo cluSetCreateInfo) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Creates a CLU set with membership determined via a search criteria based
	 * query. Sets created in this manner cannot have their membership managed
	 * manually and cannot contain other sets.
	 * 
	 * @param cluSetName
	 * @param cluSetCreateInfo
	 * @param cluCriteria
	 * @return the identifier of the created CLU set
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public String createDynamicCluSet(@WebParam(name = "cluSetName")
	String cluSetName, @WebParam(name = "cluSetCreateInfo")
	CluSetCreateInfo cluSetCreateInfo, @WebParam(name = "cluCriteria")
	CluCriteria cluCriteria) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Update the information for a CLU set
	 * 
	 * @param cluSetId
	 * @param cluSetUpdateInfo
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status updateCluSet(@WebParam(name = "cluSetId")
	String cluSetId, @WebParam(name = "cluSetUpdateInfo")
	CluSetUpdateInfo cluSetUpdateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Delete a CLU set
	 * 
	 * @param cluSetId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status deleteCluSet(@WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Adds one CLU set to another
	 * 
	 * @param cluSetId
	 * @param addedCluSetId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status addCluSetToCluSet(@WebParam(name = "cluSetId")
	String cluSetId, @WebParam(name = "addedCluSetId")
	String addedCluSetId) throws DoesNotExistException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Removes one CLU set from another
	 * 
	 * @param cluSetId
	 * @param removedCluSetId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status removeCluSetFromCluSet(@WebParam(name = "cluSetId")
	String cluSetId, @WebParam(name = "removedCluSetId")
	String removedCluSetId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Add a CLU to a CLU set
	 * 
	 * @param cluId
	 * @param cluSetId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws UnsupportedActionException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status addCluToCluSet(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, CircularReferenceException,
			InvalidParameterException, MissingParameterException,
			UnsupportedActionException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Remove a CLU from a CLU set
	 * 
	 * @param cluId
	 * @param cluSetId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws UnsupportedActionException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Status removeCluFromCluSet(@WebParam(name = "cluId")
	String cluId, @WebParam(name = "cluSetId")
	String cluSetId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, UnsupportedActionException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public String createLuTypeInfo(@WebParam(name = "luTypeInfo")LuTypeInfo luTypeInfo);
	@WebMethod
	public String updateLuTypeInfo(@WebParam(name = "luTypeInfo")LuTypeInfo luTypeInfo);
	@WebMethod
	public Status removeLuTypeInfo(@WebParam(name = "luTypeInfoId")String luTypeInfoId);
	
	@WebMethod
	public String createLuAttributeTypeInfo(@WebParam(name = "luAttributeTypeInfo")LuAttributeTypeInfo luAttributeTypeInfo);
	@WebMethod
	public String updateLuAttributeTypeInfo(@WebParam(name = "luAttributeTypeInfo")LuAttributeTypeInfo luAttributeTypeInfo);
	@WebMethod
	public Status removeLuAttributeTypeInfo(@WebParam(name = "luAttributeTypeInfoId")String luAttributeTypeInfoId);
	
	@WebMethod
	public List<LuAttributeTypeInfo> findLuAttributeTypes();

	@WebMethod
	public List<LuAttributeTypeInfo> findLuAttributeTypesForLuTypeId(@WebParam(name = "luTypeId")String luTypeId);
	
	@WebMethod
	public LuAttributeTypeInfo fetchLuAttributeType(@WebParam(name = "luAttributeTypeId")String luAttributeTypeId);
	
	@WebMethod
	public List<LuTypeInfo> searchForLuTypesByDescription(@WebParam(name = "descriptionSearchString")String descriptionSearchString);
}
