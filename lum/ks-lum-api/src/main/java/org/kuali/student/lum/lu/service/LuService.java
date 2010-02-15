/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.lu.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.enumerable.service.EnumerableService;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.service.SearchService;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.LrTypeInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.LuStatementTypeInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;

/**
 * <b>IMPORTANT:</b> This service contract is currently under development. If you are planning to implement the Kuali Student System or parts thereof, <b>please do not consider this service to be final!</b> Consult this page for status before making any plans that rely on specific implementations of these services.</p>
 * 
 * <h3><a name="KSDOC-ServiceDescriptions-Description"></a>Description</h3>
 * 
 * <p>The Learning Unit (LU) Service provides management of Canonical Learning Units (CLUs) and Learning Unit Instances (LUIs). This includes the development and approval of new Learning Units and changes to existing Learning Units. A Learning Unit (LU) is any learning-related activity that needs to be tracked by the institution or the learner. Learning units include the traditional curriculum of courses and degrees, professional and extension programs, as well as non-academic activities such as leadership development and service learning. All credit and non-credit learning activities, whether traditional or non-traditional, are Learning Units.</p>
 * 
 * <p>The LU service is broad and covers many aspects of learning either directly or by reference from other services. The LU Service focuses on the creation of the inventory of CLUs that comprise an institution's offering, including:</p>
 * <ul>
 * 	<li>associated learning objectives</li>
 * 	<li>learning results</li>
 * 	<li>supporting documents</li>
 * 	<li>evaluations</li>
 * 
 * 	<li>statements (structured rules logic specific to LU)</li>
 * 	<li>resource types</li>
 * 	<li>academic time periods</li>
 * </ul>
 * 
 * 
 * <p>The service includes operations for LUIs, although these are expected to evolve with later KS releases for enrollment and scheduling.</p>
 * 
 * <h3><a name="KSDOC-ServiceDescriptions-Assumptions"></a>Assumptions</h3>
 * 
 * <p>The design of this service considers the following assumptions:</p>
 * 
 * <p><em>Types and Configuration</em><br>
 * The following type concepts are part of configuration and not managed through service operations ::</p>
 * <ul>
 * 	<li>LuType - overall type of the Learning Unit (for example, course, academic course, exam, program and so forth). Specifics will be determined as part of reference implementation</li>
 * 	<li>LuLuRelationType - relation type between CLUs and other CLUs and between LUIs and other LUIs (for example, contains, precedes, and so forth)</li>
 * 	<li>ResultOptionUsageType - expected usage type for the result option (for example, final course grade, program certification)</li>
 * 
 * 	<li>LuDocRelationType - relation type between an LU and document (for example, proposal rationale, syllabus, reading list, and so forth). This is distinct from what the document self-identifies as.</li>
 * 	<li>LuStatementType - type or use of statement (for example, academic readiness)</li>
 * 	<li>LuReqComponentType - type of component used in statements (for example, countLrd, avgGpaLrd)</li>
 * </ul>
 * 
 * 
 * <p>Many of these types are constrained by other types. Management of these constraints is also part of configuration and not managed through service operations:</p>
 * <ul>
 * 	<li>LuLuRelationType, LrScaleType and LuStatementType are constrained by LuType</li>
 * 
 * 	<li>ReqStatementType and ReqComponentType are constrained by LuStatementType</li>
 * </ul>
 * 
 * 
 * <p><em>Behavior and Interaction</em></p>
 * <ul>
 * 	<li>Retrieval operations do not require authorization. This needs to be confirmed with use cases to determine if this is the case.</li>
 * 	<li>Relationships are unidirectional. For example, one CLU precedes another.</li>
 * 	<li>Only learning objective identifiers are returned from the relevant LU operations. Information on learning objectives is retrieved from the Learning Objective service.</li>
 * 
 * 	<li>Only result component identifiers are referenced within the result options. Information on the result component and associated values are retrieved from the Learning Result Catalog service.</li>
 * </ul>
 * 
 * 
 * <h3><a name="KSDOC-ServiceDescriptions-KeyConcepts"></a>Key Concepts</h3>
 * 
 * <ul>
 * 	<li><b>Learning Unit Types (LUTs)</b> provide the basic structure or template for creating the CLU. Depending on the type, there may be different information associated with the CLU. Examples of LUT include Course, Program, Project, and Assessment. The granularity or LuType depends on the implementation.</li>
 * 	<li><b>Canonical Learning Units (CLUs)</b> are the inventory of LU for an institution, the collection of LU that have been defined. These have generally been reviewed and approved through an approval process. There is a status (for example, active, inactive, retired) associated with the canonical learning unit. Canonical LUs are often approved for a range of Academic Time Periods. There are several ways to compose CLUs into complex structures. LUIs inherit the composition of the CLU.
 * 	<ul>
 * 
 * 		<li>CluClu Relations can be created to connect CLUs. The relation has a LuLuRelationType which is constrained by the LUT. This construct supports composite CLUs. For example, a Chemistry class that is comprised of a lecture and a lab. CluClu Relations are used to define a series of CLUs in which all courses must be completed before credit is awarded.</li>
 * 		<li>CLU Sets are collections of CLUs. They can be dynamic (based on a query) or simple enumerated lists. CLU sets are unordered sets of CLUs.</li>
 * 	</ul>
 * 	</li>
 * 	<li><b>Learning Unit Instances (LUIs)</b> are the specific offerings (occurrences) of a Canonical LU, typically during a specific Academic Time Period (ATP), although in some instances there may not be associated calendar dates (for example, online courses).</li>
 * 	<li><b>Learning Objectives (LOs)</b> have a many-to-many relationship with CLUs. A single CLU has multiple learning objectives and the same learning objective may be related to several CLUs. Learning Objectives are the intended outcome(s) for the LU.
 * 	<ul>
 * 
 * 		<li>There is a separate Learning Objective (LO) service that acts as a catalog service to manage the various LOs that may be related to CLU.</li>
 * 		<li>LOs may also be related directly to a student, which is not part of this service. The current strategy is that Learning Objectives related directly to a student would be part of the Learning Plan (LP) which may be subsumed by the Student Service, which remains to be defined.</li>
 * 	</ul>
 * 	</li>
 * 	<li><b>Result Options</b> are wrappers for the result components defined within the Learning Result Catalog service. Each result option is associated with a specific result option usage type. CLUs and LUIs do not attach directly to result options.</li>
 * 	<li><b>Result Sets</b> are the collections of one or more result options. The options in a set represent one permutation of results that can be offered by a CLU/LUI and achieved by a student. Each Result Set is associated with an LU Type to constrain to which CLUs they may be attached.</li>
 * 
 * 	<li><b>CLU Results</b> are the association of CLUs to defined result sets. The CLU Results represent the possible sets of learning results available for an LUI instance. The CLU Results instantiated for an LUI in turn represent the available results for a Learning Unit Person Relation (LPR). LU Statements can be evaluated during creation of an LPR in order to determine which results sets are valid for a specific person.</li>
 * 	<li><b>Documents</b> are the associated various supporting materials that accompany the CLU from inception, through approval, catalog listing, offering, evaluation and finally retiring. The LU service manages the connection to documents but not the actual maintenance and storage of the documents themselves. They have been separated from CluInfo in support of different authorization access.</li>
 * 	<li><b>Requirement Components</b> are basic building blocks for describing rules logic associated with various levels of LU (LUT, CLU and LUI). Each ReqComponent has a type that is the handshake with the Business Rules Management Service (BRMS) which knows how to translate ket/value pairs sent along with the type into the appropriate rules. For example, a type of LrdTime could represent a pre-requisite condition of having "taken CHEM1A within the last 6 months and received a grade of B or better."</li>
 * 	<li><b>Learning Unit Statements</b> represent reusable, structured rule logic comprised of requirement components and/or other statements with a single logical operator (for example, AND, OR) to combine. Each statement is limited to one logical operator. The combination of nesting statements and/or requirement components supports the parentheses need for more complex logic.</li>
 * 
 * </ul>
 * 
 * @author Kuali Student Team
 *
 */
@WebService(name = "LuService", targetNamespace = "http://student.kuali.org/wsdl/lu")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LuService extends DictionaryService, EnumerableService, SearchService {

    /** 
     * Retrieves the list of LU types
     * @return list of LU type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuTypeInfo> getLuTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a LU Type
     * @param luTypeKey Key of the LU Type
     * @return information about a LU Type
     * @throws DoesNotExistException luType not found
     * @throws InvalidParameterException invalid luType
     * @throws MissingParameterException missing luType
     * @throws OperationFailedException unable to complete request
	 */
    public LuTypeInfo getLuType(@WebParam(name="luTypeKey")String luTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the complete list of LU to LU relation types
     * @return list of LU to LU relation type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuLuRelationTypeInfo> getLuLuRelationTypeInfos() throws OperationFailedException;

    /** 
     * Retrieves the LU to LU relation type
     * @param luLuRelationTypeKey Key of the LU to LU Relation Type
     * @return LU to LU relation type information
     * @throws OperationFailedException unable to complete request
     * @throws MissingParameterException missing luLuRelationTypeKey
     * @throws DoesNotExistException LuLuRelationType not found
	 */
    public LuLuRelationTypeInfo getLuLuRelationTypeInfo(@WebParam(name="luLuRelationTypeKey")String luLuRelationTypeKey) throws OperationFailedException, MissingParameterException, DoesNotExistException;

    /** 
     * Retrieves the list of allowed relation types between the two specified LU Types
     * @param luTypeKey Key of the first LU Type
     * @param relatedLuTypeKey Key of the second LU Type
     * @return list of LU to LU relation types
     * @throws DoesNotExistException luTypeKey, relatedLuTypeKey not found
     * @throws InvalidParameterException invalid luTypeKey, relatedLuTypeKey
     * @throws MissingParameterException missing luTypeKey, relatedLuTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getAllowedLuLuRelationTypeInfosForLuType(@WebParam(name="luTypeKey")String luTypeKey, @WebParam(name="relatedLuTypeKey")String relatedLuTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of learning result types known by this service
     * @return list of learning result types
     * @throws OperationFailedException unable to complete request
	 */
    public List<LrTypeInfo> getLrTypes() throws OperationFailedException;

    /** 
     * Retrieves information for a specified Learning Result Types
     * @param lrTypeKey Learning Result Type Key
     * @return learning result type information
     * @throws DoesNotExistException lrTypeKey not found
     * @throws InvalidParameterException invalid lrTypeKey
     * @throws MissingParameterException missing lrTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public LrTypeInfo getLrType(@WebParam(name="lrTypeKey")String lrTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of allowed learning result types for a specified LU Type
     * @param luTypeKey LU Type Key
     * @return list of learning result type keys
     * @throws DoesNotExistException luTypeKey not found
     * @throws InvalidParameterException invalid luTypeKey
     * @throws MissingParameterException missing luTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getAllowedLrTypesForLuType(@WebParam(name="luTypeKey")String luTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of allowed learning result scale types for a specified learning unit type and learning result type
     * @param luTypeKey LU Type Key
     * @param lrTypeKey Learning Result Type Key
     * @return list of learning result scale type keys
     * @throws DoesNotExistException luTypeKey, lrTypeKey not found
     * @throws InvalidParameterException invalid luTypeKey, lrTypeKey
     * @throws MissingParameterException missing luTypeKey, lrTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getAllowedLrScaleTypesForLuType(@WebParam(name="luTypeKey")String luTypeKey, @WebParam(name="lrTypeKey")String lrTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of all LU document relationship types
     * @return list of LU document relationship types
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuDocRelationTypeInfo> getLuDocRelationTypes() throws OperationFailedException;

    /** 
     * Retrieves information for a specified LU document relationship type
     * @param luDocRelationTypeKey LU document relationship type key
     * @return LU document relationship type information
     * @throws DoesNotExistException luDocRelationTypeKey not found
     * @throws InvalidParameterException invalid luDocRelationTypeKey
     * @throws MissingParameterException missing luDocRelationTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public LuDocRelationTypeInfo getLuDocRelationType(@WebParam(name="luDocRelationTypeKey")String luDocRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of all lu statement types.
     * @return list of LU statement types
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuStatementTypeInfo> getLuStatementTypes() throws OperationFailedException;

    /** 
     * Retrieves information for a specified LuStatement Types
     * @param luStatementTypeKey LU statement type identifier
     * @return LU statement type information
     * @throws DoesNotExistException luStatementTypeKey not found
     * @throws InvalidParameterException invalid luStatementTypeKey
     * @throws MissingParameterException missing luStatementTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public LuStatementTypeInfo getLuStatementType(@WebParam(name="luStatementTypeKey")String luStatementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of LU statement types which are allowed to be used in an LU statement type.
     * @param luStatementTypeKey luStatementTypeKey
     * @return list of LU statement type
     * @throws DoesNotExistException luStatementTypeKey not found
     * @throws InvalidParameterException invalid luStatementTypeKey
     * @throws MissingParameterException missing luStatementTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuStatementTypeInfo> getLuStatementTypesForLuStatementType(@WebParam(name="luStatementTypeKey")String luStatementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

	/** 
     * Retrieves the list of requirement component types known by this service.
     * @return list of requirement component types
     * @throws OperationFailedException unable to complete request
	 */
    public List<ReqComponentTypeInfo> getReqComponentTypes() throws OperationFailedException;

    /** 
     * Retrieves information for a specified fetchReqComponent Types
     * @param reqComponentTypeKey reqComponent Type Key
     * @return Requirement component type information
     * @throws DoesNotExistException reqComponentTypeKey not found
     * @throws InvalidParameterException invalid reqComponentTypeKey
     * @throws MissingParameterException missing reqComponentTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public ReqComponentTypeInfo getReqComponentType(@WebParam(name="reqComponentTypeKey")String reqComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of requirement component types which are allowed to be used in an LU statement type.
     * @param luStatementTypeKey luStatementTypeKey
     * @return list of requirement component types
     * @throws DoesNotExistException luStatementTypeKey not found
     * @throws InvalidParameterException invalid luStatementTypeKey
     * @throws MissingParameterException missing luStatementTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType(@WebParam(name="luStatementTypeKey")String luStatementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Validates a clu. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the organization (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the organization can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param cluInfo Clu information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultContainer> validateClu(@WebParam(name="validationType")String validationType, @WebParam(name="cluInfo")CluInfo cluInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Validates a cluCluRelation. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the organization (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the organization can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param cluCluRelationInfo cluCluRelation information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, cluCluRelationInfo
     * @throws MissingParameterException missing validationTypeKey, cluCluRelationInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultContainer> validateCluCluRelation(@WebParam(name="validationType")String validationType, @WebParam(name="cluCluRelationInfo")CluCluRelationInfo cluCluRelationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Validates a luDocRelation. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the organization (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the organization can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param luDocRelationInfo luDocRelation information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, luDocRelationInfo
     * @throws MissingParameterException missing validationTypeKey, luDocRelationInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultContainer> validateLuDocRelation(@WebParam(name="validationType")String validationType, @WebParam(name="luDocRelationInfo")LuDocRelationInfo luDocRelationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Validates a ReqComponent. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the organization (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the organization can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param reqComponentInfo reqComponent information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, reqComponentInfo
     * @throws MissingParameterException missing validationTypeKey, reqComponentInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultContainer> validateReqComponent(@WebParam(name="validationType")String validationType, @WebParam(name="reqComponentInfo")ReqComponentInfo reqComponentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Validates a LuStatement. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the organization (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the organization can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param luStatementInfo luStatement information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, luStatementInfo
     * @throws MissingParameterException missing validationTypeKey, luStatementInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultContainer> validateLuStatement(@WebParam(name="validationType")String validationType, @WebParam(name="luStatementInfo")LuStatementInfo luStatementInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information about a CLU
     * @param cluId identifier of the CLU
     * @return information about a CLU
     * @throws DoesNotExistException cluId not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException missing cluId
     * @throws OperationFailedException unable to complete request
	 */
    public CluInfo getClu(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information about CLUs from a list of ids
     * @param cluIdList List of CLU identifiers
     * @return information a list of CLUs
     * @throws DoesNotExistException One or more cluIds not found
     * @throws InvalidParameterException One or more invalid cluIds
     * @throws MissingParameterException missing cluIdList
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluInfo> getClusByIdList(@WebParam(name="cluIdList")List<String> cluIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of CLUs for the specified LU Type and state
     * @param luTypeKey Key of the LU Type
     * @param luState LU State. Value is expected to be constrained to those in the luState enumeration.
     * @return list of CLU information
     * @throws DoesNotExistException luType not found
     * @throws InvalidParameterException invalid luType or luState
     * @throws MissingParameterException missing luType or luState
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluInfo> getClusByLuType(@WebParam(name="luTypeKey")String luTypeKey, @WebParam(name="luState")String luState) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of CLU ids for the specified LU Type and state
     * @param luTypeKey Key of the LU Type
     * @param luState LU State. Value is expected to be constrained to those in the luState enumeration.
     * @return list of CLU identifiers
     * @throws DoesNotExistException luType or luState not found
     * @throws InvalidParameterException invalid luType or luState
     * @throws MissingParameterException missing luType or luState
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getCluIdsByLuType(@WebParam(name="luTypeKey")String luTypeKey, @WebParam(name="luState")String luState) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information about a LUI
     * @param luiId identifier of the LUI
     * @return information about a LUI
     * @throws DoesNotExistException luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException unable to complete request
	 */
    public LuiInfo getLui(@WebParam(name="luiId")String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information about LUIs from a list of Ids
     * @param luiIdList List of LUI identifiers
     * @return information about a list of LUIs
     * @throws DoesNotExistException One or more luiIds not found
     * @throws InvalidParameterException One or more invalid luiIds
     * @throws MissingParameterException missing luiIdList
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuiInfo> getLuisByIdList(@WebParam(name="luiIdList")List<String> luiIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of LUIs for the specified CLU and period
     * @param cluId identifier of the CLU
     * @param atpKey identifier for the academic time period
     * @return list of LUI information
     * @throws DoesNotExistException cluId, atpKey not found
     * @throws InvalidParameterException invalid cluId, atpKey
     * @throws MissingParameterException missing cluId, atpKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuiInfo> getLuisInAtpByCluId(@WebParam(name="cluId")String cluId, @WebParam(name="atpKey")String atpKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of LUI ids for the specified CLU
     * @param cluId identifier of the CLU
     * @return list of LUI identifiers
     * @throws DoesNotExistException cluId not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException missing cluId
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getLuiIdsByCluId(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of LUI ids for the specified CLU and Time period
     * @param cluId identifier of the CLU
     * @param atpKey identifier for the academic time period
     * @return list of LUI identifiers
     * @throws DoesNotExistException cluId, atpKey not found
     * @throws InvalidParameterException invalid cluId, atpKey
     * @throws MissingParameterException missing cluId, atpKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getLuiIdsInAtpByCluId(@WebParam(name="cluId")String cluId, @WebParam(name="atpKey")String atpKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of allowed relation types between the two specified CLUs
     * @param cluId identifier of the first CLU
     * @param relatedCluId identifier of the second CLU
     * @return list of LU to LU relation types
     * @throws DoesNotExistException cluId, relatedCluId not found
     * @throws InvalidParameterException invalid cluId, relatedCluId
     * @throws MissingParameterException missing cluId, relatedCluId
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getAllowedLuLuRelationTypeInfosByCluId(@WebParam(name="cluId")String cluId, @WebParam(name="relatedCluId")String relatedCluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of allowed relation types between the two specified LUIs
     * @param luiId identifier of the first LUI
     * @param relatedLuiId identifier of the second LUI
     * @return list of LU to LU relation types
     * @throws DoesNotExistException luiId, relatedLuiId not found
     * @throws InvalidParameterException invalid luiId, relatedLuiId
     * @throws MissingParameterException missing luiId, relatedLuiId
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getAllowedLuLuRelationTypeInfosByLuiId(@WebParam(name="luiId")String luiId, @WebParam(name="relatedLuiId")String relatedLuiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of CLU information for the CLUs related to a specified CLU Id with a certain LU to LU relation type (getRelatedClusByCluId from the other direction)
     * @param relatedCluId identifier of the CLU
     * @param luLuRelationType the LU to LU relation type
     * @return list of CLU information
     * @throws DoesNotExistException relatedCluId, luLuRelationType not found
     * @throws InvalidParameterException invalid relatedCluId, luLuRelationType
     * @throws MissingParameterException missing relatedCluId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluInfo> getClusByRelation(@WebParam(name="relatedCluId")String relatedCluId, @WebParam(name="luLuRelationTypeKey") String luLuRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of CLU Ids for the specified related CLU Id and LU to LU relation type (getRelatedCluIdsByCluId from the other direction)
     * @param relatedCluId identifier of the CLU
     * @param luLuRelationType the LU to LU relation type
     * @return list of CLU identifiers
     * @throws DoesNotExistException relatedCluId, luLuRelationType not found
     * @throws InvalidParameterException invalid relatedCluId, luLuRelationType
     * @throws MissingParameterException missing relatedCluId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getCluIdsByRelation(@WebParam(name="relatedCluId")String relatedCluId, @WebParam(name="luLuRelationTypeKey") String luLuRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of LUI information for the LUIs related to the specified LUI Id with a certain LU to LU relation type (getRelatedLuisByLuiId from the other direction)
     * @param relatedLuiId identifier of the LUI
     * @param luLuRelationTypeKey the LU to LU relation type
     * @return list of LUI information
     * @throws DoesNotExistException relatedLuiId, luLuRelationType not found
     * @throws InvalidParameterException invalid relatedLuiId, luLuRelationType
     * @throws MissingParameterException missing relatedLuiId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuiInfo> getLuisByRelation(@WebParam(name="relatedLuiId")String relatedLuiId, @WebParam(name="luLuRelationTypeKey") String luLuRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of LUI Ids for the specified related LUI Id and LU to LU relation type (getRelatedLuiIdsByLuiId from the other direction)
     * @param relatedLuiId identifier of the LUI
     * @param luLuRelationType the LU to LU relation type
     * @return list of LUI identifiers
     * @throws DoesNotExistException relatedLuiId, luLuRelationType not found
     * @throws InvalidParameterException invalid relatedLuiId, luLuRelationType
     * @throws MissingParameterException missing relatedLuiId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getLuiIdsByRelation(@WebParam(name="relatedLuiId")String relatedLuiId, @WebParam(name="luLuRelationTypeKey") String luLuRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of related CLU information for the specified CLU Id and LU to LU relation type (getClusByRelation from the other direction)
     * @param cluId identifier of the CLU
     * @param luLuRelationType the LU to LU relation type
     * @return list of CLU information
     * @throws DoesNotExistException cluId, luLuRelationType not found
     * @throws InvalidParameterException invalid cluId, luLuRelationType
     * @throws MissingParameterException missing cluId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluInfo> getRelatedClusByCluId(@WebParam(name="cluId")String cluId, @WebParam(name="luLuRelationTypeKey") String luLuRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of related CLU Ids for the specified CLU Id and LU to LU relation type (getCluIdsByRelation from the other direction)
     * @param cluId identifier of the CLU
     * @param luLuRelationType the LU to LU relation type
     * @return list of CLU identifiers
     * @throws DoesNotExistException cluId, luLuRelationType not found
     * @throws InvalidParameterException invalid cluId, luLuRelationType
     * @throws MissingParameterException missing cluId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getRelatedCluIdsByCluId(@WebParam(name="cluId")String cluId, @WebParam(name="luLuRelationTypeKey") String luLuRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of related LUI information for the specified LUI Id and LU to LU relation type (getLuisByRelation from the other direction)
     * @param luiId identifier of the LUI
     * @param luLuRelationType the LU to LU relation type
     * @return list of LUI information
     * @throws DoesNotExistException luiId, luLuRelationType not found
     * @throws InvalidParameterException invalid luiId, luLuRelationType
     * @throws MissingParameterException missing luiId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuiInfo> getRelatedLuisByLuiId(@WebParam(name="luiId")String luiId, @WebParam(name="luLuRelationTypeKey") String luLuRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of related LUI Ids for the specified LUI Id and LU to LU relation type. (getLuiIdsByRelation from the other direction)
     * @param luiId identifier of the LUI
     * @param luLuRelationType the LU to LU relation type
     * @return list of LUI identifiers
     * @throws DoesNotExistException luiId, luLuRelationType not found
     * @throws InvalidParameterException invalid luiId, luLuRelationType
     * @throws MissingParameterException missing luiId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getRelatedLuiIdsByLuiId(@WebParam(name="luiId")String luiId, @WebParam(name="luLuRelationTypeKey") String luLuRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the relationship information between CLUs for a particular Relation instance
     * @param cluCluRelationId identifier of the CLU to CLU relation
     * @return information on the relation between two CLUs
     * @throws DoesNotExistException cluCluRelationId not found
     * @throws InvalidParameterException invalid cluCluRelationId
     * @throws MissingParameterException missing cluCluRelationId
     * @throws OperationFailedException unable to complete request
	 */
    public CluCluRelationInfo getCluCluRelation(@WebParam(name="cluCluRelationId")String cluCluRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of relationship information for the specified CLU
     * @param cluId identifier of the CLU
     * @return list of CLU to CLU relation information
     * @throws DoesNotExistException cluId not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException missing cluId
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluCluRelationInfo> getCluCluRelationsByClu(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the relationship information between LUIs given a specific relation instance
     * @param luiLuiRelationId identifier of LUI to LUI relation
     * @return information on the relation between two LUIs
     * @throws DoesNotExistException luiLuiRelationId not found
     * @throws InvalidParameterException invalid luiLuiRelationId
     * @throws MissingParameterException missing luiLuiRelationId
     * @throws OperationFailedException unable to complete request
	 */
    public LuiLuiRelationInfo getLuiLuiRelation(@WebParam(name="luiLuiRelationId")String luiLuiRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of relationship information for the specified LUI
     * @param luiId identifier of the LUI
     * @return list of LUI to LUI relation information
     * @throws DoesNotExistException luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuiLuiRelationInfo> getLuiLuiRelations(@WebParam(name="luiId")String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieve information on a CLU set. This information should be about the set itself, and in the case of a dynamic CLU set, should include the criteria used to generate the set.
     * @param cluSetId Identifier of the CLU set
     * @return The retrieved CLU set information
     * @throws DoesNotExistException cluSetId not found
     * @throws InvalidParameterException invalid cluSetId
     * @throws MissingParameterException missing cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CluSetInfo getCluSetInfo(@WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieve information on CLU sets from a list of cluSet Ids.
     * @param cluSetIdList List of identifiers of CLU sets
     * @return The retrieved list of CLU set information
     * @throws DoesNotExistException One or more cluSetIds not found
     * @throws InvalidParameterException One or more cluSetIds invalid
     * @throws MissingParameterException missing cluSetIdList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<CluSetInfo> getCluSetInfoByIdList(@WebParam(name="cluSetIdList")List<String> cluSetIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of CLU Identifiers within a CLU Set. This only retrieves the direct members.
     * @param cluSetId Identifier of the CLU set
     * @return The retrieved list of CLU Ids within the specified CLU set (flattened and de-duped)
     * @throws DoesNotExistException cluSetId not found
     * @throws InvalidParameterException invalid cluSetId
     * @throws MissingParameterException missing cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<String> getCluIdsFromCluSet(@WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of CLUs in a CLU set. This only retrieves the direct members.
     * @param cluSetId Identifier of the CLU set
     * @return The retrieved list of information on the CLUs within the CLU set (flattened and de-duped)
     * @throws DoesNotExistException cluSetId not found
     * @throws InvalidParameterException invalid cluSetId
     * @throws MissingParameterException missing cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<CluInfo> getClusFromCluSet(@WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieve the list of CLU Set Ids within a CLU Set
     * @param cluSetId Identifier of the CLU set
     * @return The retrieved list of CLU Set Ids within the specified CLU set
     * @throws DoesNotExistException cluSetId not found
     * @throws InvalidParameterException invalid cluSetId
     * @throws MissingParameterException missing cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<String> getCluSetIdsFromCluSet(@WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the full list of CLUs in this CLU set or any cluset that is included within that.
     * @param cluSetId Identifier of the CLU set
     * @return The retrieved list of information on the CLUs
     * @throws DoesNotExistException cluSetId not found
     * @throws InvalidParameterException invalid cluSetId
     * @throws MissingParameterException missing cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<CluInfo> getAllClusInCluSet(@WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of CLU Identifiers within a CLU Set or any cluset that is included within that.
     * @param cluSetId Identifier of the CLU set
     * @return The retrieved list of CLU Ids within the specified CLU set
     * @throws DoesNotExistException cluSetId not found
     * @throws InvalidParameterException invalid cluSetId
     * @throws MissingParameterException missing cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<String> getAllCluIdsInCluSet(@WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Checks if a CLU is a member of a CLU set or any contained CLU set
     * @param cluId Identifier of the CLU to check
     * @param cluSetId Identifier of the CLU set
     * @return True if the CLU is a member of the CLU Set
     * @throws DoesNotExistException cluId, cluSetId not found
     * @throws InvalidParameterException invalid cluId, cluSetId
     * @throws MissingParameterException missing cluId, cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public Boolean isCluInCluSet(@WebParam(name="cluId")String cluId, @WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of learning objective identifiers for a given CLU.
     * @param cluId Identifier for the CLU
     * @return List of learning objective identifiers
     * @throws DoesNotExistException cluId not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException missing cluId
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getLoIdsByClu(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of learning objective identifiers for a given LUI.
     * @param luiId Identifier for the LUI
     * @return List of learning objective identifiers
     * @throws DoesNotExistException luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getLoIdsByLui(@WebParam(name="luiId")String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of CLU identifiers associated with a given learning objective identifier.
     * @param loId Identifier for the learning objective
     * @return List of CLU identifiers
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getCluIdsByLoId(@WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of LUI identifiers associated with a given learning objective identifier.
     * @param loId Identifier for the learning objective
     * @return List of LUI identifiers
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getLuiIdsByLoId(@WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of learning result type keys associated with a given CLU.
     * @param cluId Identifier for the CLU
     * @return List of learning result type keys
     * @throws DoesNotExistException cluId not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException missing cluId
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getLrTypesForClu(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of learning result type keys associated with a given LUI.
     * @param luiId Identifier for the LUI
     * @return List of learning result type keys
     * @throws DoesNotExistException luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getLrTypesForLui(@WebParam(name="luiId")String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of learning result scale types for a given learning result type associated with a given CLU.
     * @param cluId Identifier for the CLU
     * @param lrTypeKey Identifier of the learning result type
     * @return List of learning result scale type keys
     * @throws DoesNotExistException cluId, lrTypeKey not found
     * @throws InvalidParameterException invalid cluId, lrTypeKey
     * @throws MissingParameterException missing cluId, lrTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getLrScaleTypesForClu(@WebParam(name="cluId")String cluId, @WebParam(name="lrTypeKey")String lrTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of learning result scale type keys for a given learning result type associated with a given LUI.
     * @param luiId Identifier for the LUI
     * @param lrTypeKey Identifier of the learning result type
     * @return List of learning result scale type keys
     * @throws DoesNotExistException luiId, lrTypeKey not found
     * @throws InvalidParameterException invalid luiId, lrTypeKey
     * @throws MissingParameterException missing luiId, lrTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getLrScaleTypesForLui(@WebParam(name="luiId")String luiId, @WebParam(name="lrTypeKey")String lrTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of Resource requirements for the specified CLU
     * @param cluId Unique identifier for the CLU
     * @return List of resource requirements
     * @throws DoesNotExistException cluId not found
     * @throws InvalidParameterException cluId invalid
     * @throws MissingParameterException cluId missing
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getResourceRequirementsForCluId(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a luDocRelation by its identifier
     * @param luDocRelationId luDocRelation identifier
     * @return luDocRelation information
     * @throws DoesNotExistException luDocRelationId not found
     * @throws InvalidParameterException invalid luDocRelationId
     * @throws MissingParameterException luDocRelationId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public LuDocRelationInfo getLuDocRelation(@WebParam(name="luDocRelationId")String luDocRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of luDocRelations of a particular type
     * @param luDocRelationTypeKey luDocRelationType identifier
     * @return list of luDocRelation of a type
     * @throws DoesNotExistException luDocRelationTypeKey not found
     * @throws InvalidParameterException invalid luDocRelationTypeKey
     * @throws MissingParameterException luDocRelationTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuDocRelationInfo> getLuDocRelationsByType(@WebParam(name="luDocRelationTypeKey")String luDocRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of luDocRelations from a list of ids
     * @param luDocRelationIdList list of luDocRelation identifiers
     * @return list of luDocRelation that matches the id list
     * @throws DoesNotExistException luDocRelationId not found
     * @throws InvalidParameterException invalid luDocRelationIdList
     * @throws MissingParameterException luDocRelationIdList not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuDocRelationInfo> getLuDocRelationsByIdList(@WebParam(name="luDocRelationIdList")List<String> luDocRelationIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of luDocRelations of a particular clu
     * @param cluId cluId identifier
     * @return list of luDocRelation of a clu
     * @throws DoesNotExistException cluId not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException cluId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuDocRelationInfo> getLuDocRelationsByClu(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of luDocRelations by a document identifier.
     * @param documentId document identifier
     * @return list of luDocRelation of a document
     * @throws DoesNotExistException documentId not found
     * @throws InvalidParameterException invalid documentId
     * @throws MissingParameterException documentId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuDocRelationInfo> getLuDocRelationsByDocument(@WebParam(name="documentId")String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a statement by its identifier
     * @param luStatementId statement identifier
     * @return luStatement information
     * @throws DoesNotExistException luStatementId not found
     * @throws InvalidParameterException invalid luStatementId
     * @throws MissingParameterException luStatementId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public LuStatementInfo getLuStatement(@WebParam(name="luStatementId")String luStatementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of statements by a list of statement identifiers
     * @param luStatementIdList List of statement identifiers
     * @return luStatement information
     * @throws DoesNotExistException luStatementId not found
     * @throws InvalidParameterException invalid luStatementId
     * @throws MissingParameterException luStatementId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuStatementInfo> getLuStatements(@WebParam(name="luStatementIdList")List<String> luStatementIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    /** 
     * Retrieves a list of statements of a particular Type
     * @param luStatementTypeKey luStatementType identifier
     * @return list of statements using the specified type
     * @throws DoesNotExistException luStatementTypeKey not found
     * @throws InvalidParameterException invalid luStatementTypeKey
     * @throws MissingParameterException luStatementTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuStatementInfo> getLuStatementsByType(@WebParam(name="luStatementTypeKey")String luStatementTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of statements for a particular clu
     * @param cluId clu identifier
     * @return list of statements using the specified clu
     * @throws DoesNotExistException cluId not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException cluId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuStatementInfo> getLuStatementsForClu(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a component by its identifier
     * @param reqComponentId reqComponent identifier
     * @return reqComponent information
     * @throws DoesNotExistException reqComponentId not found
     * @throws InvalidParameterException invalid reqComponentId
     * @throws MissingParameterException reqComponentId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public ReqComponentInfo getReqComponent(@WebParam(name="reqComponentId")String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of required components by a list of required component identifiers
     * @param reqComponentIdList List of reqComponent identifiers
     * @return reqComponent Required component information
     * @throws DoesNotExistException reqComponentId not found
     * @throws InvalidParameterException invalid reqComponentId
     * @throws MissingParameterException reqComponentId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<ReqComponentInfo> getReqComponents(@WebParam(name="reqComponentIdList")List<String> reqComponentIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    /** 
     * Retrieves a list of Requirement Components of a particular type.
     * @param reqComponentTypeKey reqComponentType identifier
     * @return A list of requirementComponents
     * @throws DoesNotExistException reqComponentTypeKey not found
     * @throws InvalidParameterException invalid reqComponentTypeKey
     * @throws MissingParameterException reqComponentTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<ReqComponentInfo> getReqComponentsByType(@WebParam(name="reqComponentTypeKey")String reqComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of Requirement Components that have some sort of reference to a particular CLU. Note: The reference may not be direct, but through an intermediate object definition.
     * @param cluId clu identifier
     * @return A list of requirementComponents
     * @throws DoesNotExistException cluId not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException cluId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<ReqComponentInfo> getReqComponentsUsingClu(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of statements that use a particular requirement component. Note: The reference may not be direct, but through an intermediate object definition (ex. nested statements).
     * @param reqComponentId requirement component identifier
     * @return list of statements using the specified clu
     * @throws DoesNotExistException reqComponentId not found
     * @throws InvalidParameterException invalid reqComponentId
     * @throws MissingParameterException reqComponentId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuStatementInfo> getStatementsUsingComponent(@WebParam(name="reqComponentId")String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of Clus that use a particular requirement component. Note: The reference may not be direct, but through an intermediate object definition (ex. statements)
     * @param reqComponentId requirement component identifier
     * @return list of cluIds using the specified requirement component
     * @throws DoesNotExistException reqComponentId not found
     * @throws InvalidParameterException invalid reqComponentId
     * @throws MissingParameterException reqComponentId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getClusUsingComponent(@WebParam(name="reqComponentId")String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new CLU
     * @param luTypeKey identifier of the LU Type for the CLU being created
     * @param cluInfo information about the CLU being created
     * @return the created CLU information
     * @throws AlreadyExistsException CLU already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException luTypeKey not found
     * @throws InvalidParameterException invalid luTypeKey, cluInfo
     * @throws MissingParameterException missing luTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CluInfo createClu(@WebParam(name="luTypeKey")String luTypeKey, @WebParam(name="cluInfo")CluInfo cluInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing CLU
     * @param cluId identifier for the CLU to be updated
     * @param cluInfo updated information about the CLU
     * @return the updated CLU information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException cluId not found
     * @throws InvalidParameterException invalid cluId, cluInfo
     * @throws MissingParameterException missing cluId, cluInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public CluInfo updateClu(@WebParam(name="cluId")String cluId, @WebParam(name="cluInfo")CluInfo cluInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing CLU
     * @param cluId identifier for the CLU to be deleted
     * @return status of the operation
     * @throws DoesNotExistException cluId not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException missing cluId
     * @throws DependentObjectsExistException delete would leave orphaned objects or violate integrity constraints
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteClu(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a CLU's state
     * @param cluId identifier for the CLU to be updated
     * @param luState new state for the CLU. Value is expected to be constrained to those in the luState enumeration.
     * @return the updated CLU information
     * @throws DataValidationErrorException new state not valid for existing state of CLU
     * @throws DoesNotExistException cluId, luState not found
     * @throws InvalidParameterException invalid cluId, luState
     * @throws MissingParameterException missing cluId, luState
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CluInfo updateCluState(@WebParam(name="cluId")String cluId, @WebParam(name="luState")String luState) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a new LUI
     * @param cluId identifier of the CLU for the LUI being created
     * @param atpKey identifier of the academic time period for the LUI being created
     * @param luiInfo information about the LUI being created
     * @return the created LUI information
     * @throws AlreadyExistsException LUI already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException atpKey not found
     * @throws InvalidParameterException invalid cluId, atpKey, luiInfo
     * @throws MissingParameterException missing cluId, atpKey, luiInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public LuiInfo createLui(@WebParam(name="cluId")String cluId, @WebParam(name="atpKey")String atpKey, @WebParam(name="luiInfo")LuiInfo luiInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing LUI
     * @param luiId identifier for the LUI to be updated
     * @param luiInfo updated information about the LUI
     * @return the updated LUI information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException luiId not found
     * @throws InvalidParameterException invalid luiId, luiInfo
     * @throws MissingParameterException missing luiId, luiInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public LuiInfo updateLui(@WebParam(name="luiId")String luiId, @WebParam(name="luiInfo")LuiInfo luiInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes a LUI record
     * @param luiId identifier for the LUI to be deleted
     * @return status of the operation
     * @throws DependentObjectsExistException delete would leave orphaned objects or violate integrity constraints
     * @throws DoesNotExistException luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteLui(@WebParam(name="luiId")String luiId) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a LUI's state
     * @param luiId identifier for the LUI to be updated
     * @param luState New state for LUI. Value is expected to be constrained to those in the luState enumeration.
     * @return the updated LUI information
     * @throws DataValidationErrorException New state not valid for existing state of LUI
     * @throws DoesNotExistException luiId, luState not found
     * @throws InvalidParameterException invalid luiId, luState
     * @throws MissingParameterException missing luiId, luState
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public LuiInfo updateLuiState(@WebParam(name="luiId")String luiId, @WebParam(name="luState")String luState) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Create a relationship between two CLUs
     * @param cluId identifier of the first LUI in the relationship
     * @param relatedCluId identifier of the second CLU in the relationship to be related to
     * @param luLuRelationTypeKey the LU to LU relationship type of the relationship
     * @param cluCluRelationInfo information about the relationship between the two CLUs
     * @return the created CLU to CLU relation information
     * @throws AlreadyExistsException relationship already exists
     * @throws CircularReferenceException cluId equals relatedCluId
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException cluId, relatedCluId, luLuRelationType not found
     * @throws InvalidParameterException invalid cluId, relatedCluId, luluRelationType, cluCluRelationInfo
     * @throws MissingParameterException missing cluId, relatedCluId, luluRelationType, cluCluRelationInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CluCluRelationInfo createCluCluRelation(@WebParam(name="cluId")String cluId, @WebParam(name="relatedCluId")String relatedCluId, @WebParam(name="luLuRelationTypeKey")String luLuRelationTypeKey, @WebParam(name="cluCluRelationInfo")CluCluRelationInfo cluCluRelationInfo) throws AlreadyExistsException, CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a relationship between two CLUs
     * @param cluCluRelationId identifier of the CLU to CLU relation to be updated
     * @param cluCluRelationInfo changed information about the CLU to CLU relationship
     * @return the updated CLU to CLU relation information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException cluCluRelationId not found
     * @throws InvalidParameterException invalid cluCluRelationId, cluCluRelationInfo
     * @throws MissingParameterException missing cluCluRelationId, cluCluRelationInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public CluCluRelationInfo updateCluCluRelation(@WebParam(name="cluCluRelationId")String cluCluRelationId, @WebParam(name="cluCluRelationInfo")CluCluRelationInfo cluCluRelationInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes a relationship between two CLUs
     * @param cluCluRelationId identifier of CLU to CLU relationship to delete
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException cluCluRelationId not found
     * @throws InvalidParameterException invalid cluCluRelationId
     * @throws MissingParameterException missing cluCluRelationId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteCluCluRelation(@WebParam(name="cluCluRelationId")String cluCluRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Create a relationship between two LUIs
     * @param luiId identifier of the first LUI in the relationship
     * @param relatedLuiId identifier of the second LUI in the relationship to be related to
     * @param luLuRelationTypeKey the LU to LU relationship type of the relationship
     * @param luiLuiRelationInfo information about the relationship between the two LUIs
     * @return the created LUI to LUI relation information
     * @throws AlreadyExistsException relationship already exists
     * @throws CircularReferenceException luiId equals relatedLuiId
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException luiId, relatedLuiId, luLuRelationType not found
     * @throws InvalidParameterException invalid luiIds, luluRelationType, luiLuiRelationInfo
     * @throws MissingParameterException missing luiIds, luluRelationType, luiLuiRelationInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public LuiLuiRelationInfo createLuiLuiRelation(@WebParam(name="luiId")String luiId, @WebParam(name="relatedLuiId")String relatedLuiId, @WebParam(name="luLuRelationTypeKey")String luLuRelationTypeKey, @WebParam(name="luiLuiRelationInfo")LuiLuiRelationInfo luiLuiRelationInfo) throws AlreadyExistsException, CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a relationship between two LUIs
     * @param luiLuiRelationId identifier of the LUI to LUI relation to update
     * @param luiLuiRelationInfo changed information about the relationship between the two LUIs
     * @return the update LUI to LUI relation information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException luiLuiRelationId not found
     * @throws InvalidParameterException invalid luiLuiRelationId, luiLuiRelationInfo
     * @throws MissingParameterException missing luiLuiRelationId, luiLuiRelationInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public LuiLuiRelationInfo updateLuiLuiRelation(@WebParam(name="luiLuiRelationId")String luiLuiRelationId, @WebParam(name="luiLuiRelationInfo")LuiLuiRelationInfo luiLuiRelationInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes a relationship between two LUIs
     * @param luiLuiRelationId identifier of the LUI to LUI relation to delete
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException luiLuiRelationId not found
     * @throws InvalidParameterException invalid luiLuiRelationId
     * @throws MissingParameterException missing luiLuiRelationId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteLuiLuiRelation(@WebParam(name="luiLuiRelationId")String luiLuiRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a CLU set with manually maintained membership. Sets created in this manner can contain other sets.
     * @param cluSetName name of the CLU set to be created
     * @param cluSetInfo information required to create a CLU set
     * @return the created CLU set information
     * @throws AlreadyExistsException the cluSet already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException invalid cluSetName, cluSetInfo
     * @throws MissingParameterException missing cluSetName, cluSetInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws DoesNotExistException Clu or CluSet not found
	 */
    public CluSetInfo createEnumeratedCluSet(@WebParam(name="cluSetName")String cluSetName, @WebParam(name="cluSetInfo")CluSetInfo cluSetInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

    /** 
     * Update the information for a CLU set
     * @param cluSetId identifier of the CLU set to be updated
     * @param cluSetInfo updated information about the CLU set
     * @return the updated CLU set information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException cluSetId not found
     * @throws InvalidParameterException invalid cluSetId, cluSetInfo
     * @throws MissingParameterException missing cluSetId, cluSetInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     * @throws CircularReferenceException addedCluSetId cannot be added to the cluSetId
     * @throws UnsupportedActionException CLU set is dynamically determined and clu ids or clu set ids are present
	 */
    public CluSetInfo updateCluSet(@WebParam(name="cluSetId")String cluSetId, @WebParam(name="cluSetInfo")CluSetInfo cluSetInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, CircularReferenceException, UnsupportedActionException;

    /** 
     * Delete a CLU set
     * @param cluSetId identifier of the CLU set to be deleted
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException the cluSetId not found
     * @throws InvalidParameterException invalid cluSetId
     * @throws MissingParameterException missing cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteCluSet(@WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Adds one CLU set to another
     * @param cluSetId identifier of the host CLU set
     * @param addedCluSetId identifier of the CLU set to be added
     * @return status of the operation (success or failure)
     * @throws CircularReferenceException addedCluSetId cannot be added to the cluSetId
     * @throws DoesNotExistException cluSetId, addedCluSetId not found
     * @throws InvalidParameterException invalid cluSetId, addedCluSetId
     * @throws MissingParameterException missing cluSetId, addedCluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException CLU set is dynamically determined
	 */
    public StatusInfo addCluSetToCluSet(@WebParam(name="cluSetId")String cluSetId, @WebParam(name="addedCluSetId")String addedCluSetId) throws CircularReferenceException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Removes one CLU set from another
     * @param cluSetId identifier of the host CLU set
     * @param removedCluSetId identifier of the CLU set to be removed
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException cluSetId, removedCluSetId not found
     * @throws InvalidParameterException invalid cluSetId, removedCluSetId
     * @throws MissingParameterException missing cluSetId, removedCluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException CLU set is dynamically determined
	 */
    public StatusInfo removeCluSetFromCluSet(@WebParam(name="cluSetId")String cluSetId, @WebParam(name="removedCluSetId")String removedCluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Add a CLU to a CLU set
     * @param cluId identifier of CLU to add to the CLU set
     * @param cluSetId identifier of the CLU set
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException cluId, cluSetId not found
     * @throws InvalidParameterException invalid cluId, cluSetId
     * @throws MissingParameterException missing cluId, cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException CLU set is dynamically determined
	 */
    public StatusInfo addCluToCluSet(@WebParam(name="cluId")String cluId, @WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Remove a CLU from a CLU set
     * @param cluId identifier of CLU to remove from the CLU set
     * @param cluSetId identifier of the CLU set
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException cluId, cluSetId not found
     * @throws InvalidParameterException invalid cluId, cluSetId
     * @throws MissingParameterException missing cluId, cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException CLU set is dynamically determined
	 */
    public StatusInfo removeCluFromCluSet(@WebParam(name="cluId")String cluId, @WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Add a learning objective as an outcome to a CLU
     * @param loId identifier of learning objective to add to the CLU
     * @param cluId identifier of the CLU
     * @return status of the operation (success or failure)
     * @throws AlreadyExistsException loId is already associated with the cluId
     * @throws DoesNotExistException loId, cluId not found
     * @throws InvalidParameterException invalid loId, cluId
     * @throws MissingParameterException missing loId, cluId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo addOutcomeLoToClu(@WebParam(name="loId")String loId, @WebParam(name="cluId")String cluId) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Remove an outcome learning objective from a CLU
     * @param loId identifier of learning objective to remove from the CLU
     * @param cluId identifier of the CLU
     * @return status of the operation (success or failure)
     * @throws DependentObjectsExistException the learning objective is referenced in one or more LUIs for this CLU
     * @throws DoesNotExistException loId, cluId not found
     * @throws InvalidParameterException invalid loId, cluId
     * @throws MissingParameterException missing loId, cluId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removeOutcomeLoFromClu(@WebParam(name="loId")String loId, @WebParam(name="cluId")String cluId) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Add an learning objective as an outcome to a LUI
     * @param loId identifier of learning objective to add to the LUI
     * @param luiId identifier of the LUI
     * @return status of the operation (success or failure)
     * @throws AlreadyExistsException loId is already associated with the luiId
     * @throws DoesNotExistException loId, luiId not found
     * @throws InvalidParameterException invalid loId, luiId
     * @throws MissingParameterException missing loId, luiId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException loId is not associated with the parent CLU of the LUI
	 */
    public StatusInfo addOutcomeLoToLui(@WebParam(name="loId")String loId, @WebParam(name="luiId")String luiId) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Remove an outcome learning objective from a LUI
     * @param loId identifier of learning objective to remove from the LUI
     * @param luiId identifier of the LUI
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException loId, luiId not found
     * @throws InvalidParameterException invalid loId, luiId
     * @throws MissingParameterException missing loId, luiId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removeOutcomeLoFromLui(@WebParam(name="loId")String loId, @WebParam(name="luiId")String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Add a learning result scale for a given learning result type to a CLU
     * @param lrScaleTypeKey identifier of learning result scale to add to the CLU
     * @param lrTypeKey identifier of the learning result type
     * @param cluId identifier of the CLU
     * @return status of the operation (success or failure)
     * @throws AlreadyExistsException lrScaleTypeKey, lrTypeKey combination is already associated with the cluId
     * @throws DoesNotExistException lrScaleTypeKey, lrTypeKey, cluId not found
     * @throws InvalidParameterException invalid lrScaleTypeKey, lrTypeKey, cluId
     * @throws MissingParameterException missing lrScaleTypeKey, lrTypeKey, cluId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException lrScaleTypeKey, lrTypeKey combination is not associated with the parent LU Type of the CLU
	 */
    public StatusInfo addLrScaleToClu(@WebParam(name="lrScaleTypeKey")String lrScaleTypeKey, @WebParam(name="lrTypeKey")String lrTypeKey, @WebParam(name="cluId")String cluId) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Remove a learning result scale of a given learning result type from a CLU
     * @param lrScaleTypeKey identifier of learning result scale type to remove from the CLU
     * @param lrTypeKey identifier of the learning result type
     * @param cluId identifier of the CLU
     * @return status of the operation (success or failure)
     * @throws DependentObjectsExistException lrcScaleTypeKey, lrTypeKey combination is referenced in one or more LUIs for this CLU
     * @throws DoesNotExistException lrcScaleTypeKey, lrTypeKey, cluId not found
     * @throws InvalidParameterException invalid lrcScaleTypeKey, lrTypeKey, cluId
     * @throws MissingParameterException missing lrcScaleTypeKey, lrTypeKey, cluId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removeLrScaleFromClu(@WebParam(name="lrScaleTypeKey")String lrScaleTypeKey, @WebParam(name="lrTypeKey")String lrTypeKey, @WebParam(name="cluId")String cluId) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Add a learning result scale for a given learning result type to a LUI
     * @param lrScaleTypeKey identifier of learning result scale type to add to the LUI
     * @param lrTypeKey identifier of the learning result type
     * @param luiId identifier of the LUI
     * @return status of the operation (success or failure)
     * @throws AlreadyExistsException lrScaleTypeKey, lrTypeKey combination is already associated with the luiId
     * @throws DoesNotExistException lrScaleTypeKey, lrTypeKey, luiId not found
     * @throws InvalidParameterException invalid lrScaleTypeKey, lrTypeKey, luiId
     * @throws MissingParameterException missing lrScaleTypeKey, lrTypeKey, luiId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException lrScaleTypeKey, lrTypeKey combination is not associated with the parent CLU of the LUI
	 */
    public StatusInfo addLrScaleToLui(@WebParam(name="lrScaleTypeKey")String lrScaleTypeKey, @WebParam(name="lrTypeKey")String lrTypeKey, @WebParam(name="luiId")String luiId) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Remove a learning result scale of a given learning result type from a LUI
     * @param lrScaleTypeKey identifier of learning result scale type to remove from the LUI
     * @param lrTypeKey identifier of the learning result type
     * @param luiId identifier of the LUI
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException lrScaleTypeKey, lrTypeKey, luiId not found
     * @throws InvalidParameterException invalid lrScaleTypeKey, lrTypeKey, luiId
     * @throws MissingParameterException missing lrScaleTypeKey, lrTypeKey, luiId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removeLrScaleFromLui(@WebParam(name="lrScaleTypeKey")String lrScaleTypeKey, @WebParam(name="lrTypeKey")String lrTypeKey, @WebParam(name="luiId")String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Add a Resource requirement to a CLU
     * @param resourceTypeKey identifier of the resource requirement type to be added to the CLU
     * @param cluId identifier of the CLU
     * @return status of the operation (success or failure)
     * @throws AlreadyExistsException resourceTypeKey is already associated with the cluId
     * @throws DoesNotExistException resourceTypeKey or cluId not found
     * @throws InvalidParameterException resourceTypeKey or cluId invalid
     * @throws MissingParameterException resourceTypeKey or cluId missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo addCluResourceRequirement(@WebParam(name="resourceTypeKey")String resourceTypeKey, @WebParam(name="cluId")String cluId) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Remove a Resource requirement from a CLU
     * @param resourceTypeKey identifier of the resource type to be removed from the CLU
     * @param cluId identifier of the CLU
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException resourceTypeKey or cluId or relationship not found
     * @throws InvalidParameterException resourceTypeKey or cluId invalid
     * @throws MissingParameterException resourceTypeKey or cluId missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removeCluResourceRequirement(@WebParam(name="resourceTypeKey")String resourceTypeKey, @WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a luDocRelation for a clu.
     * @param luDocRelationType identifier of the type of luDocRelation
     * @param documentId document identifier
     * @param cluId the identifierof the clu to relate to
     * @param luDocRelationInfo information about the luDocRelation
     * @return information about the newly created luDocRelation
     * @throws AlreadyExistsException luDocRelation already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException cluId or luDocRelationTypeKey or documentId not found
     * @throws InvalidParameterException invalid cluId or luDocRelationTypeKey or documentId or luDocRelationInfo
     * @throws MissingParameterException missing cluId or luDocRelationTypeKey or documentId or luDocRelationInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public LuDocRelationInfo createLuDocRelationForClu(@WebParam(name="luDocRelationType")String luDocRelationType, @WebParam(name="documentId")String documentId, @WebParam(name="cluId")String cluId, @WebParam(name="luDocRelationInfo")LuDocRelationInfo luDocRelationInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a luDocRelation
     * @param luDocRelationId identifier of the luDocRelation to be updated
     * @param luDocRelationInfo information about the luDocRelation to be updated
     * @return the updated luDocRelation information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException luDocRelation not found
     * @throws InvalidParameterException invalid luDocRelationId, luDocRelationInfo
     * @throws MissingParameterException missing luDocRelationId, luDocRelationInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public LuDocRelationInfo updateLuDocRelation(@WebParam(name="luDocRelationId")String luDocRelationId, @WebParam(name="luDocRelationInfo")LuDocRelationInfo luDocRelationInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * removes a luDocRelation from a clu
     * @param luDocRelationId identifier of the luDocRelation to delete
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException luDocRelationId not found
     * @throws InvalidParameterException invalid luDocRelationId
     * @throws MissingParameterException missing luDocRelationId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteLuDocRelation(@WebParam(name="luDocRelationId")String luDocRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a requirement component.
     * @param reqComponentType identifier of the type of requirement component
     * @param reqComponentInfo information about the requirement component
     * @return information about the newly created requirement component
     * @throws AlreadyExistsException Requirement Component already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException reqComponentType not found
     * @throws InvalidParameterException invalid reqComponentType, reqComponentInfo
     * @throws MissingParameterException missing reqComponentType, reqComponentInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public ReqComponentInfo createReqComponent(@WebParam(name="reqComponentType")String reqComponentType, @WebParam(name="reqComponentInfo")ReqComponentInfo reqComponentInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a requirement component
     * @param reqComponentId identifier of the requirement component to be updated
     * @param reqComponentInfo information about the requirement component to be updated
     * @return the updated requirement component information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException Requirement Component not found
     * @throws InvalidParameterException invalid reqComponentId, reqComponentInfo
     * @throws MissingParameterException missing reqComponentId, reqComponentInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public ReqComponentInfo updateReqComponent(@WebParam(name="reqComponentId")String reqComponentId, @WebParam(name="reqComponentInfo")ReqComponentInfo reqComponentInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes a requirement component
     * @param reqComponentId identifier of the requirement component to delete
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException reqComponentId not found
     * @throws InvalidParameterException invalid reqComponentId
     * @throws MissingParameterException missing reqComponentId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteReqComponent(@WebParam(name="reqComponentId")String reqComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Create a LU statement.
     * @param luStatementType identifier of the type of LU statement
     * @param luStatementInfo information about the LU statement
     * @return information about the newly created LU statement
     * @throws AlreadyExistsException LU statement already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException luStatementType not found
     * @throws InvalidParameterException invalid luStatementType, luStatementInfo
     * @throws MissingParameterException missing luStatementType, luStatementInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public LuStatementInfo createLuStatement(@WebParam(name="luStatementType")String luStatementType, @WebParam(name="luStatementInfo")LuStatementInfo luStatementInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a luStatement
     * @param luStatementId identifier of the LU statement to be updated
     * @param luStatementInfo information about the LU statement to be updated
     * @return the updated LU statement information
     * @throws CircularReferenceException included LU statement references the current LU statement
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException luStatement not found
     * @throws InvalidParameterException invalid luStatementId, luStatementInfo
     * @throws MissingParameterException missing luStatementId, luStatementInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public LuStatementInfo updateLuStatement(@WebParam(name="luStatementId")String luStatementId, @WebParam(name="luStatementInfo")LuStatementInfo luStatementInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes a LU statement
     * @param luStatementId identifier of the LU Statement to delete
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException luStatementId not found
     * @throws InvalidParameterException invalid luStatementId
     * @throws MissingParameterException missing luStatementId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteLuStatement(@WebParam(name="luStatementId")String luStatementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Assigns a LU statement to a CLU.
     * @param cluId CLU identifier
     * @param luStatementId statement identifier
     * @return status
     * @throws AlreadyExistsException connection between clu and statement already exists
     * @throws DoesNotExistException cluId, luStatementId not found
     * @throws InvalidParameterException invalid cluId or luStatementId
     * @throws MissingParameterException cluId or statementId not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo addLuStatementToClu(@WebParam(name="cluId")String cluId, @WebParam(name="luStatementId")String luStatementId) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Removes an attachment between a luStatement and a Clu.
     * @param cluId CLU identifier
     * @param luStatementId statement identifier
     * @return Status
     * @throws DoesNotExistException cluId or luStatementId, attachment not found
     * @throws InvalidParameterException invalid cluId or luStatementId
     * @throws MissingParameterException cluId or luStatementId not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removeLuStatementFromClu(@WebParam(name="cluId")String cluId, @WebParam(name="luStatementId")String luStatementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}
