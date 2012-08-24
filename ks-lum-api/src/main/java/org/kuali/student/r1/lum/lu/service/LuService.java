/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r1.lum.lu.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r1.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r1.common.search.service.SearchService;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.core.versionmanagement.service.VersionManagementService;
import org.kuali.student.r1.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.r1.lum.lu.dto.CluInfo;
import org.kuali.student.r1.lum.lu.dto.CluLoRelationInfo;
import org.kuali.student.r1.lum.lu.dto.CluLoRelationTypeInfo;
import org.kuali.student.r1.lum.lu.dto.CluPublicationInfo;
import org.kuali.student.r1.lum.lu.dto.CluResultInfo;
import org.kuali.student.r1.lum.lu.dto.CluResultTypeInfo;
import org.kuali.student.r1.lum.lu.dto.CluSetInfo;
import org.kuali.student.r1.lum.lu.dto.CluSetTreeViewInfo;
import org.kuali.student.r1.lum.lu.dto.CluSetTypeInfo;
import org.kuali.student.r1.lum.lu.dto.DeliveryMethodTypeInfo;
import org.kuali.student.r1.lum.lu.dto.InstructionalFormatTypeInfo;
import org.kuali.student.r1.lum.lu.dto.LuCodeTypeInfo;
import org.kuali.student.r1.lum.lu.dto.LuLuRelationTypeInfo;
import org.kuali.student.r1.lum.lu.dto.LuPublicationTypeInfo;
import org.kuali.student.r1.lum.lu.dto.LuTypeInfo;
import org.kuali.student.r1.lum.lu.dto.LuiInfo;
import org.kuali.student.r1.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.r1.lum.lu.dto.ResultUsageTypeInfo;


/**
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Mon Jan 11 15:18:59 PST 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/LU+Service+v1.0-rc4">LUService</>
 *
 */
@WebService(name = "LuService", targetNamespace = LuServiceConstants.LU_NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LuService extends DictionaryService, SearchService, VersionManagementService { 

	/** 
     * Retrieves the list of delivery method types
     * @return list of delivery method type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<DeliveryMethodTypeInfo> getDeliveryMethodTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a delivery method type
     * @param deliveryMethodTypeKey Key of the Delivery Method Type
     * @return information about a Delivery Method Type
     * @throws DoesNotExistException deliveryMethodType not found
     * @throws InvalidParameterException invalid deliveryMethodType
     * @throws MissingParameterException missing deliveryMethodType
     * @throws OperationFailedException unable to complete request
	 */
    public DeliveryMethodTypeInfo getDeliveryMethodType(@WebParam(name="deliveryMethodTypeKey")String deliveryMethodTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of instructional format types
     * @return list of instructional format type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<InstructionalFormatTypeInfo> getInstructionalFormatTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a Instructional Format Type
     * @param instructionalFormatTypeKey Key of the Instructional Format Type
     * @return information about a Instructional Format Type
     * @throws DoesNotExistException instructionalFormatType not found
     * @throws InvalidParameterException invalid instructionalFormatType
     * @throws MissingParameterException missing instructionalFormatType
     * @throws OperationFailedException unable to complete request
	 */
    public InstructionalFormatTypeInfo getInstructionalFormatType(@WebParam(name="instructionalFormatTypeKey")String instructionalFormatTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
     * Retrieves the list of learning unit code types
     * @return list of lu code type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuCodeTypeInfo> getLuCodeTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a learning unit code type
     * @param luCodeTypeKey Key of the learning unit code type
     * @return information about a learning unit code type
     * @throws DoesNotExistException luCodeTypeKey not found
     * @throws InvalidParameterException invalid luCodeTypeKey
     * @throws MissingParameterException missing luCodeTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public LuCodeTypeInfo getLuCodeType(@WebParam(name="luCodeTypeKey")String luCodeTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the complete list of LU to LU relation types
     * @return list of LU to LU relation type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuLuRelationTypeInfo> getLuLuRelationTypes() throws OperationFailedException;

    /** 
     * Retrieves the LU to LU relation type
     * @param luLuRelationTypeKey Key of the LU to LU Relation Type
     * @return LU to LU relation type information
     * @throws OperationFailedException unable to complete request
     * @throws MissingParameterException missing luLuRelationTypeKey
     * @throws DoesNotExistException luLuRelationTypeKey not found
	 */
    public LuLuRelationTypeInfo getLuLuRelationType(@WebParam(name="luLuRelationTypeKey")String luLuRelationTypeKey) throws OperationFailedException, MissingParameterException, DoesNotExistException;

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
    public List<String> getAllowedLuLuRelationTypesForLuType(@WebParam(name="luTypeKey")String luTypeKey, @WebParam(name="relatedLuTypeKey")String relatedLuTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of Learning Unit publication types
     * @return list of Learning Unit publication type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuPublicationTypeInfo> getLuPublicationTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a publication type
     * @param luPublicationTypeKey Key of the Learning Unit Publication Type
     * @return information about a Learning Unit Publication Type
     * @throws DoesNotExistException luPublicationType not found
     * @throws InvalidParameterException invalid luPublicationType
     * @throws MissingParameterException missing luPublicationType
     * @throws OperationFailedException unable to complete request
	 */
    public LuPublicationTypeInfo getLuPublicationType(@WebParam(name="luPublicationTypeKey")String luPublicationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information about a publication type
     * @param luTypeKey Key of the LU Type
     * @return list of LU publication types
     * @throws DoesNotExistException luPublicationType not found
     * @throws InvalidParameterException invalid luPublicationType
     * @throws MissingParameterException missing luPublicationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getLuPublicationTypesForLuType(@WebParam(name="luTypeKey")String luTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
        
    /** 
     * Retrieves a list of types for clu result objects.
     * @return list of clu result type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluResultTypeInfo> getCluResultTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a publication type
     * @param cluResultTypeKey Key of the Canonical Learning Unit Result Type
     * @return information about a Learning Unit Publication Type
     * @throws DoesNotExistException luPublicationType not found
     * @throws InvalidParameterException invalid luPublicationType
     * @throws MissingParameterException missing luPublicationType
     * @throws OperationFailedException unable to complete request
	 */
    public CluResultTypeInfo getCluResultType(@WebParam(name="cluResultTypeKey")String cluResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
        
    /** 
     * Retrieves the list of clu result types which are allowed to be used in conjunction with a particular lu type.
     * @param luTypeKey luTypeKey
     * @return list of clu result types
     * @throws DoesNotExistException luTypeKey not found
     * @throws InvalidParameterException invalid luTypeKey
     * @throws MissingParameterException missing luTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluResultTypeInfo> getCluResultTypesForLuType(@WebParam(name="luTypeKey")String luTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of result usage types
     * @return list of result usage type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<ResultUsageTypeInfo> getResultUsageTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a Result Usage Type
     * @param resultUsageTypeKey Key of the Result Usage Type
     * @return information about a Result Usage Type
     * @throws DoesNotExistException resultUsageTypeKey not found
     * @throws InvalidParameterException invalid resultUsageTypeKey
     * @throws MissingParameterException missing resultUsageTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public ResultUsageTypeInfo getResultUsageType(@WebParam(name="resultUsageTypeKey")String resultUsageTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of result usage types which are allowed to be used in conjunction with an lu type.
     * @param luTypeKey luTypeKey
     * @return list of result usage types
     * @throws DoesNotExistException luTypeKey not found
     * @throws InvalidParameterException invalid luTypeKey
     * @throws MissingParameterException missing luTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getAllowedResultUsageTypesForLuType(@WebParam(name="luTypeKey")String luTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of result component types which are allowed to be used in conjunction with result usage type.
     * @param resultUsageTypeKey resultUsageTypeKey
     * @return list of result component types
     * @throws DoesNotExistException resultUsageTypeKey not found
     * @throws InvalidParameterException invalid resultUsageTypeKey
     * @throws MissingParameterException missing resultUsageTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getAllowedResultComponentTypesForResultUsageType(@WebParam(name="resultUsageTypeKey")String resultUsageTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the complete list of CLU to LO relation types
     * @return list of CLU to LO relation type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluLoRelationTypeInfo> getCluLoRelationTypes() throws OperationFailedException;

    /** 
     * Retrieves information for a specified CLU to LO relation type
     * @param cluLoRelationTypeKey Key of the CLU to LO Relation Type
     * @return CLU to LO relation type information
     * @throws DoesNotExistException cluLoRelationTypeKey not found
     * @throws InvalidParameterException invalid cluLoRelationTypeKey
     * @throws MissingParameterException missing cluLoRelationTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public CluLoRelationTypeInfo getCluLoRelationType(@WebParam(name="cluLoRelationTypeKey")String cluLoRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of CLU LO relation types which are allowed to be used in conjunction with an lu type.
     * @param luTypeKey luTypeKey
     * @return list of clu lo relation types
     * @throws DoesNotExistException luTypeKey not found
     * @throws InvalidParameterException invalid luTypeKey
     * @throws MissingParameterException missing luTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getAllowedCluLoRelationTypesForLuType(@WebParam(name="luTypeKey")String luTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of CLU set types known by the service
     * @return list of CLU set type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluSetTypeInfo> getCluSetTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a specified CLU set type
     * @param cluSetTypeKey Key of the CLU set type
     * @return information about a CLU set type
     * @throws DoesNotExistException cluSetTypeKey not found
     * @throws InvalidParameterException invalid cluSetTypeKey
     * @throws MissingParameterException missing cluSetTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public CluSetTypeInfo getCluSetType(@WebParam(name="cluSetTypeKey")String cluSetTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves core information about a CLU
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
     * @param luTypeKey Type of the CLUs to retrieve
     * @param luState State of the CLUs to retrieve.
     * @return list of CLU information
     * @throws DoesNotExistException luType or luState not found
     * @throws InvalidParameterException invalid luType or luState
     * @throws MissingParameterException missing luType or luState
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluInfo> getClusByLuType(@WebParam(name="luTypeKey")String luTypeKey, @WebParam(name="luState")String luState) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of CLU ids for the specified LU Type and state
     * @param luTypeKey Type of the CLUs whose identifiers should be retrieved
     * @param luState State of the CLUs whose identifiers should be retrieved
     * @return list of CLU identifiers
     * @throws DoesNotExistException luType or luState not found
     * @throws InvalidParameterException invalid luType or luState
     * @throws MissingParameterException missing luType or luState
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getCluIdsByLuType(@WebParam(name="luTypeKey")String luTypeKey, @WebParam(name="luState")String luState) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of allowed relation types between the two specified CLUs
     * @param cluId identifier of the first CLU
     * @param relatedCluId identifier of the second CLU
     * @return list of LU to LU relation types
     * @throws DoesNotExistException clu, relatedClu not found
     * @throws InvalidParameterException invalid cluId, relatedCluId
     * @throws MissingParameterException missing cluId, relatedCluId
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getAllowedLuLuRelationTypesByCluId(@WebParam(name="cluId")String cluId, @WebParam(name="relatedCluId")String relatedCluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of CLU information for the CLUs related to a specified CLU Id with a certain LU to LU relation type (getRelatedClusByCluId from the other direction)
     * @param relatedCluId identifier of the child or To CLU
     * @param luLuRelationType the LU to LU relation type
     * @return list of CLU information
     * @throws DoesNotExistException relatedCluId, luLuRelationType not found
     * @throws InvalidParameterException invalid relatedCluId, luLuRelationType
     * @throws MissingParameterException missing relatedCluId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluInfo> getClusByRelation(@WebParam(name="relatedCluId")String relatedCluId, @WebParam(name="luLuRelationType")String luLuRelationType) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of CLU Ids for the specified related CLU Id and LU to LU relation type (getRelatedCluIdsByCluId from the other direction)
     * @param relatedCluId identifier of the child or To CLU
     * @param luLuRelationType the LU to LU relation type
     * @return list of CLU identifiers
     * @throws DoesNotExistException relatedClu, luLuRelationType not found
     * @throws InvalidParameterException invalid relatedCluId, luLuRelationType
     * @throws MissingParameterException missing relatedCluId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getCluIdsByRelation(@WebParam(name="relatedCluId")String relatedCluId, @WebParam(name="luLuRelationType")String luLuRelationType) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of related CLU information for the specified CLU Id and LU to LU relation type (getClusByRelation from the other direction)
     * @param cluId identifier of the parent or From CLU
     * @param luLuRelationType the LU to LU relation type
     * @return list of CLU information
     * @throws DoesNotExistException clu, luLuRelationType not found
     * @throws InvalidParameterException invalid cluId, luLuRelationType
     * @throws MissingParameterException missing cluId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluInfo> getRelatedClusByCluId(@WebParam(name="cluId")String cluId, @WebParam(name="luLuRelationType")String luLuRelationType) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of related CLU Ids for the specified CLU Id and LU to LU relation type (getCluIdsByRelation from the other direction)
     * @param cluId identifier of the parent or From CLU
     * @param luLuRelationType the LU to LU relation type
     * @return list of CLU identifiers
     * @throws DoesNotExistException clu, luLuRelationType not found
     * @throws InvalidParameterException invalid cluId, luLuRelationType
     * @throws MissingParameterException missing cluId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getRelatedCluIdsByCluId(@WebParam(name="cluId")String cluId, @WebParam(name="luLuRelationType")String luLuRelationType) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the relationship information between CLUs for a particular Relation instance
     * @param cluCluRelationId identifier of the CLU to CLU relation
     * @return information on the relation between two CLUs
     * @throws DoesNotExistException cluCluRelation not found
     * @throws InvalidParameterException invalid cluCluRelationId
     * @throws MissingParameterException missing cluCluRelationId
     * @throws OperationFailedException unable to complete request
	 */
    public CluCluRelationInfo getCluCluRelation(@WebParam(name="cluCluRelationId")String cluCluRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of relationship information for the specified CLU
     * @param cluId identifier of the parent or From CLU
     * @return list of CLU to CLU relation information
     * @throws DoesNotExistException clu not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException missing cluId
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluCluRelationInfo> getCluCluRelationsByClu(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of publication objects for a particular clu
     * @param cluId clu identifier
     * @return list of publication objects used by the specified clu
     * @throws DoesNotExistException clu not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException cluId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluPublicationInfo> getCluPublicationsByCluId(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of publication objects of a particular Type
     * @param luPublicationTypeKey luPublicationType identifier
     * @return list of CLU Publication objects using the specified type
     * @throws DoesNotExistException luPublicationType not found
     * @throws InvalidParameterException invalid luPublicationTypeKey
     * @throws MissingParameterException luPublicationTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluPublicationInfo> getCluPublicationsByType(@WebParam(name="luPublicationTypeKey")String luPublicationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves an LU publication object by its identifier
     * @param cluPublicationId CLU publication identifier
     * @return CLU Publication information
     * @throws DoesNotExistException CLU Publication not found
     * @throws InvalidParameterException invalid cluPublicationId
     * @throws MissingParameterException cluPublicationId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public CluPublicationInfo getCluPublication(@WebParam(name="cluPublicationId")String cluPublicationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information about a Clu Result
     * @param cluResultId identifier of the Clu Result
     * @return information about a Clu Result
     * @throws DoesNotExistException cluResult not found
     * @throws InvalidParameterException invalid cluResultId
     * @throws MissingParameterException missing cluResultId
     * @throws OperationFailedException unable to complete request
	 */
    public CluResultInfo getCluResult(@WebParam(name="cluResultId")String cluResultId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the cluResult for a particular clu
     * @param cluId clu identifier
     * @return result information for a clu
     * @throws DoesNotExistException clu not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException cluId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluResultInfo> getCluResultByClu(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of clu ids with the results of the specified usage type. This would for example allow requests for all clus which have a final grade.
     * @param resultUsageTypeKey identifier of the result usage type
     * @return list of clu ids
     * @throws DoesNotExistException resultUsageType not found
     * @throws InvalidParameterException invalid resultUsageTypeKey
     * @throws MissingParameterException missing resultUsageTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getCluIdsByResultUsageType(@WebParam(name="resultUsageTypeKey")String resultUsageTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of clu ids which use a particular result component
     * @param resultComponentId identifier of the result component
     * @return list of clu ids
     * @throws DoesNotExistException resultComponent not found
     * @throws InvalidParameterException invalid resultComponentId
     * @throws MissingParameterException missing resultComponentId
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getCluIdsByResultComponent(@WebParam(name="resultComponentId")String resultComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieve information on a CLU LO Relation.
     * @param cluLoRelationId Identifier of the CLU LO Relation
     * @return The retrieved CLU LO Relation information
     * @throws DoesNotExistException cluLoRelation not found
     * @throws InvalidParameterException invalid cluLoRelationId
     * @throws MissingParameterException missing cluLoRelationId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CluLoRelationInfo getCluLoRelation(@WebParam(name="cluLoRelationId")String cluLoRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of canonical learning unit to learning objective relationships for a given CLU.
     * @param cluId Identifier for the CLU
     * @return List of canonical learning unit to learning objective relationships
     * @throws DoesNotExistException clu not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException missing cluId
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluLoRelationInfo> getCluLoRelationsByClu(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of CLU identifiers associated with a given learning objective identifier.
     * @param loId Identifier for the learning objective
     * @return List of CLU LO Relations
     * @throws DoesNotExistException lo not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public List<CluLoRelationInfo> getCluLoRelationsByLo(@WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of Resource requirements for the specified CLU
     * @param cluId Unique identifier for the CLU
     * @return List of resource requirements
     * @throws DoesNotExistException clu not found
     * @throws InvalidParameterException cluId invalid
     * @throws MissingParameterException cluId missing
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getResourceRequirementsForCluId(@WebParam(name="cluId")String cluId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieve information on a CLU set. This information should be about the set itself, and in the case of a dynamic CLU set, should include the criteria used to generate the set.
     * @param cluSetId Identifier of the CLU set
     * @return The retrieved CLU set information
     * @throws DoesNotExistException cluSet not found
     * @throws InvalidParameterException invalid cluSetId
     * @throws MissingParameterException missing cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CluSetInfo getCluSetInfo(@WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /** 
     * Retrieve information on a CLU set and its sub clu set fully expanded.
     * @param cluSetId Identifier of the CLU set
     * @return The retrieved CLU set tree view information
     * @throws DoesNotExistException cluSet not found
     * @throws InvalidParameterException invalid cluSetId
     * @throws MissingParameterException missing cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CluSetTreeViewInfo getCluSetTreeView(@WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    
    /** 
     * Retrieve information on CLU sets from a list of cluSet Ids.
     * @param cluSetIdList List of identifiers of CLU sets
     * @return The retrieved list of CLU set information
     * @throws DoesNotExistException One or more cluSets not found
     * @throws InvalidParameterException One or more cluSetIds invalid
     * @throws MissingParameterException missing cluSetIdList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<CluSetInfo> getCluSetInfoByIdList(@WebParam(name="cluSetIdList")List<String> cluSetIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieve the list of CLU Set Ids within a CLU Set
     * @param cluSetId Identifier of the CLU set
     * @return The retrieved list of CLU Set Ids within the specified CLU set
     * @throws DoesNotExistException cluSet not found
     * @throws InvalidParameterException invalid cluSetId
     * @throws MissingParameterException missing cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<String> getCluSetIdsFromCluSet(@WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Check if the given CluSet is dynamic
     * @param cluSetId Identifier of the CLU set
     * @return The retrieved list of CLU Set Ids within the specified CLU set
     * @throws DoesNotExistException cluSet not found
     * @throws InvalidParameterException invalid cluSetId
     * @throws MissingParameterException missing cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public Boolean isCluSetDynamic(@WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    
    /** 
     * Retrieves the list of CLUs in a CLU set. This only retrieves the direct members.
     * @param cluSetId Identifier of the CLU set
     * @return The retrieved list of information on the CLUs within the CLU set (flattened and de-duped)
     * @throws DoesNotExistException cluSet not found
     * @throws InvalidParameterException invalid cluSetId
     * @throws MissingParameterException missing cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<CluInfo> getClusFromCluSet(@WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of CLU Identifiers within a CLU Set. This only retrieves the direct members.
     * @param cluSetId Identifier of the CLU set
     * @return The retrieved list of CLU Ids within the specified CLU set (flattened and de-duped)
     * @throws DoesNotExistException cluSet not found
     * @throws InvalidParameterException invalid cluSetId
     * @throws MissingParameterException missing cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<String> getCluIdsFromCluSet(@WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the full list of CLUs in this CLU set or any cluset that is included within that.
     * @param cluSetId Identifier of the CLU set
     * @return The retrieved list of information on the CLUs
     * @throws DoesNotExistException cluSet not found
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
     * @throws DoesNotExistException cluSet not found
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
     * @throws DoesNotExistException clu, cluSet not found
     * @throws InvalidParameterException invalid cluId, cluSetId
     * @throws MissingParameterException missing cluId, cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public Boolean isCluInCluSet(@WebParam(name="cluId")String cluId, @WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves information about a LUI
     * @param luiId identifier of the LUI
     * @return information about a LUI
     * @throws DoesNotExistException lui not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException unable to complete request
	 */
    public LuiInfo getLui(@WebParam(name="luiId")String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information about LUIs from a list of Ids
     * @param luiIdList List of LUI identifiers
     * @return information about a list of LUIs
     * @throws DoesNotExistException One or more luis not found
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
     * @throws DoesNotExistException clu, atp not found
     * @throws InvalidParameterException invalid cluId, atpKey
     * @throws MissingParameterException missing cluId, atpKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuiInfo> getLuisInAtpByCluId(@WebParam(name="cluId")String cluId, @WebParam(name="atpKey")String atpKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of LUI ids for the specified CLU
     * @param cluId identifier of the CLU
     * @return list of LUI identifiers
     * @throws DoesNotExistException clu not found
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
     * @throws DoesNotExistException clu, atp not found
     * @throws InvalidParameterException invalid cluId, atpKey
     * @throws MissingParameterException missing cluId, atpKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getLuiIdsInAtpByCluId(@WebParam(name="cluId")String cluId, @WebParam(name="atpKey")String atpKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of allowed relation types between the two specified LUIs
     * @param luiId identifier of the first LUI
     * @param relatedLuiId identifier of the second LUI
     * @return list of LU to LU relation types
     * @throws DoesNotExistException lui, relatedLui not found
     * @throws InvalidParameterException invalid luiId, relatedLuiId
     * @throws MissingParameterException missing luiId, relatedLuiId
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getAllowedLuLuRelationTypesByLuiId(@WebParam(name="luiId")String luiId, @WebParam(name="relatedLuiId")String relatedLuiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of LUI information for the LUIs related to the specified LUI Id with a certain LU to LU relation type (getRelatedLuisByLuiId from the other direction)
     * @param relatedLuiId identifier of the LUI
     * @param luLuRelationType the LU to LU relation type
     * @return list of LUI information
     * @throws DoesNotExistException relatedLui, luLuRelationType not found
     * @throws InvalidParameterException invalid relatedLuiId, luLuRelationType
     * @throws MissingParameterException missing relatedLuiId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuiInfo> getLuisByRelation(@WebParam(name="relatedLuiId")String relatedLuiId, @WebParam(name="luLuRelationType")String luLuRelationType) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of LUI Ids for the specified related LUI Id and LU to LU relation type (getRelatedLuiIdsByLuiId from the other direction)
     * @param relatedLuiId identifier of the LUI
     * @param luLuRelationType the LU to LU relation type
     * @return list of LUI identifiers
     * @throws DoesNotExistException relatedLui, luLuRelationType not found
     * @throws InvalidParameterException invalid relatedLuiId, luLuRelationType
     * @throws MissingParameterException missing relatedLuiId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getLuiIdsByRelation(@WebParam(name="relatedLuiId")String relatedLuiId, @WebParam(name="luLuRelationType")String luLuRelationType) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of related LUI information for the specified LUI Id and LU to LU relation type (getLuisByRelation from the other direction)
     * @param luiId identifier of the LUI
     * @param luLuRelationType the LU to LU relation type
     * @return list of LUI information
     * @throws DoesNotExistException lui, luLuRelationType not found
     * @throws InvalidParameterException invalid luiId, luLuRelationType
     * @throws MissingParameterException missing luiId, luLuRelationType
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuiInfo> getRelatedLuisByLuiId(@WebParam(name="luiId")String luiId, @WebParam(name="luLuRelationType")String luLuRelationType) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    public List<String> getRelatedLuiIdsByLuiId(@WebParam(name="luiId")String luiId, @WebParam(name="luLuRelationType")String luLuRelationType) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the relationship information between LUIs given a specific relation instance
     * @param luiLuiRelationId identifier of LUI to LUI relation
     * @return information on the relation between two LUIs
     * @throws DoesNotExistException luiLuiRelation not found
     * @throws InvalidParameterException invalid luiLuiRelationId
     * @throws MissingParameterException missing luiLuiRelationId
     * @throws OperationFailedException unable to complete request
	 */
    public LuiLuiRelationInfo getLuiLuiRelation(@WebParam(name="luiLuiRelationId")String luiLuiRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of relationship information for the specified LUI
     * @param luiId identifier of the LUI
     * @return list of LUI to LUI relation information
     * @throws DoesNotExistException lui not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(@WebParam(name="luiId")String luiId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Validates a CLU. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the CLU (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the CLU can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param cluInfo CLU information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateClu(@WebParam(name="validationType")String validationType, @WebParam(name="cluInfo")CluInfo cluInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
     * @throws VersionMismatchException The action was attempted on an out of date version
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
     * Creates a new CLU version based on the current clu
     * @param cluId identifier for the CLU to be versioned
     * @param versionComment comment for the current version
     * @return the new versioned CLU information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException cluId not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException missing cluId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version
     */
    public CluInfo createNewCluVersion(@WebParam(name="cluId")String cluId, @WebParam(name="versionComment")String versionComment) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    
    /** 
     * Sets a specific version of the Clu as current. The sequence number must be greater than the existing current Clu version.
     * This will truncate the current version's end date to the currentVersionStart param.
     * If a Clu exists which is set to become current in the future, that clu's currentVersionStart and CurrentVersionEnd will be nullified.
     * The currentVersionStart must be in the future to prevent changing historic data. 
     * @param cluVersionId Version Specific Id of the Clu
     * @param currentVersionStart Date when this clu becomes current. Must be in the future and be after the most current clu's start date. 
     * @return status of the operation
     * @throws DoesNotExistException cluVersionId not found
     * @throws InvalidParameterException invalid cluVersionId, previousState, newState
     * @throws MissingParameterException missing cluVersionId, previousState, newState
     * @throws IllegalVersionSequencingException a Clu with higher sequence number from the one provided is marked current
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo setCurrentCluVersion(@WebParam(name="cluVersionId")String cluVersionId, @WebParam(name="currentVersionStart")Date currentVersionStart) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates the state of the specified CLU
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
     * Validates a cluCluRelation. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the cluCluRelation (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the relationship can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param cluCluRelationInfo cluCluRelation information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, cluCluRelationInfo
     * @throws MissingParameterException missing validationTypeKey, cluCluRelationInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateCluCluRelation(@WebParam(name="validationType")String validationType, @WebParam(name="cluCluRelationInfo")CluCluRelationInfo cluCluRelationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Create a directional relationship between two CLUs
     * @param cluId identifier of the first CLU in the relationship - The From or Parent of the relation
     * @param relatedCluId identifier of the second CLU in the relationship to be related to - the To or Child of the Relation
     * @param luLuRelationTypeKey the LU to LU relationship type of the relationship
     * @param cluCluRelationInfo information about the relationship between the two CLUs
     * @return the created CLU to CLU relation information
     * @throws AlreadyExistsException relationship already exists
     * @throws CircularRelationshipException cluId equals relatedCluId
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException cluId, relatedCluId, luLuRelationType not found
     * @throws InvalidParameterException invalid cluId, relatedCluId, luluRelationType, cluCluRelationInfo
     * @throws MissingParameterException missing cluId, relatedCluId, luluRelationType, cluCluRelationInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CluCluRelationInfo createCluCluRelation(@WebParam(name="cluId")String cluId, @WebParam(name="relatedCluId")String relatedCluId, @WebParam(name="luLuRelationTypeKey")String luLuRelationTypeKey, @WebParam(name="cluCluRelationInfo")CluCluRelationInfo cluCluRelationInfo) throws AlreadyExistsException, CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a relationship between two CLUs
     * @param cluCluRelationId identifier of the CLU to CLU relation to be updated
     * @param cluCluRelationInfo changed information about the CLU to CLU relationship
     * @return the updated CLU to CLU relation information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException cluCluRelation not found
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
     * @throws DoesNotExistException cluCluRelation not found
     * @throws InvalidParameterException invalid cluCluRelationId
     * @throws MissingParameterException missing cluCluRelationId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteCluCluRelation(@WebParam(name="cluCluRelationId")String cluCluRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates information about publication for a clu. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the clu publication object (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the clu publication object can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param cluPublicationInfo CLU publication information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, cluPublicationInfo
     * @throws MissingParameterException missing validationTypeKey, cluPublicationInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateCluPublication(@WebParam(name="validationType")String validationType, @WebParam(name="cluPublicationInfo")CluPublicationInfo cluPublicationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Create a clu publication object, which contains information about publication for a clu.
     * @param cluId identifier of a clu
     * @param luPublicationType type of lu publication
     * @param cluPublicationInfo information about publication for a clu
     * @return information about the created clu publication object
     * @throws AlreadyExistsException clu publication object already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException invalid cluId, luPublicationType, cluPublicationInfo
     * @throws MissingParameterException missing cluId, luPublicationType, cluPublicationInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CluPublicationInfo createCluPublication(@WebParam(name="cluId")String cluId, @WebParam(name="luPublicationType")String luPublicationType, @WebParam(name="cluPublicationInfo")CluPublicationInfo cluPublicationInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing clu publication object
     * @param cluPublicationId identifier for the clu publication object to be updated
     * @param cluPublicationInfo updated information about the clu publication object
     * @return the updated clu publication information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException cluPublication not found
     * @throws InvalidParameterException invalid cluPublicationId, cluPublicationInfo
     * @throws MissingParameterException missing cluPublicationId, cluPublicationInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public CluPublicationInfo updateCluPublication(@WebParam(name="cluPublicationId")String cluPublicationId, @WebParam(name="cluPublicationInfo")CluPublicationInfo cluPublicationInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing clu publication object
     * @param cluPublicationId identifier for the clu publication object to be deleted
     * @return status of the operation
     * @throws DoesNotExistException cluPublication not found
     * @throws InvalidParameterException invalid cluPublicationId
     * @throws MissingParameterException missing cluPublicationId
     * @throws DependentObjectsExistException delete would leave orphaned objects or violate integrity constraints
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteCluPublication(@WebParam(name="cluPublicationId")String cluPublicationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates information about results for a clu. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the clu result object (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the clu result object can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param cluResultInfo CLU result information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, cluResultInfo
     * @throws MissingParameterException missing validationTypeKey, cluResultInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateCluResult(@WebParam(name="validationType")String validationType, @WebParam(name="cluResultInfo")CluResultInfo cluResultInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Create a clu result object, which contains information about potential results for a clu.
     * @param cluId identifier of a clu
     * @param cluResultType type of clu result
     * @param cluResultInfo information about potential results for a clu
     * @return information about the created clu result
     * @throws AlreadyExistsException clu result already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException invalid cluId, cluResultType, cluResultInfo
     * @throws MissingParameterException missing cluId, cluResultType, cluResultInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws DoesNotExistException resultUsageTypeKey not found
	 */
    public CluResultInfo createCluResult(@WebParam(name="cluId")String cluId, @WebParam(name="cluResultType")String cluResultType, @WebParam(name="cluResultInfo")CluResultInfo cluResultInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

    /** 
     * Updates an existing clu result
     * @param cluResultId identifier for the clu result to be updated
     * @param cluResultInfo updated information about the clu result
     * @return the updated clu result information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException cluResult not found
     * @throws InvalidParameterException invalid cluResultId, cluResultInfo
     * @throws MissingParameterException missing cluResultId, cluResultInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public CluResultInfo updateCluResult(@WebParam(name="cluResultId")String cluResultId, @WebParam(name="cluResultInfo")CluResultInfo cluResultInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing clu result
     * @param cluResultId identifier for the clu result to be deleted
     * @return status of the operation
     * @throws DoesNotExistException cluResult not found
     * @throws InvalidParameterException invalid cluResultId
     * @throws MissingParameterException missing cluResultId
     * @throws DependentObjectsExistException delete would leave orphaned objects or violate integrity constraints
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteCluResult(@WebParam(name="cluResultId")String cluResultId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a cluLoRelation. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the cluLoRelation (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the relationship can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param cluLoRelationInfo cluLoRelation information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, cluLoRelationInfo
     * @throws MissingParameterException missing validationTypeKey, cluLoRelationInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateCluLoRelation(@WebParam(name="validationType")String validationType, @WebParam(name="cluLoRelationInfo")CluLoRelationInfo cluLoRelationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a relationship between a learning objective and a CLU.
     * @param cluId CLU identifier
     * @param loId learning objective identifier
     * @param cluLoRelationType type of clu learning objective relationship
     * @param cluLoRelationInfo clu learning objective relationship information
     * @return the newly created clu learning objective relationship
     * @throws AlreadyExistsException connection between clu and learning objective already exists
     * @throws DoesNotExistException cluId, loId, cluLoRelationType not found
     * @throws InvalidParameterException one or more parameters invalid
     * @throws MissingParameterException one or more parameters not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws DataValidationErrorException data validation error
	 */
    public CluLoRelationInfo createCluLoRelation(@WebParam(name="cluId")String cluId, @WebParam(name="loId")String loId, @WebParam(name="cluLoRelationType")String cluLoRelationType, @WebParam(name="cluLoRelationInfo")CluLoRelationInfo cluLoRelationInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException;

    /** 
     * Updates a relationship between a clu and learning objective
     * @param cluLoRelationId identifier of the clu learning objective relationship to be updated
     * @param cluLoRelationInfo information about the clu learning objective relationship to be updated
     * @return the updated clu learning objective relationship information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException cluLoRelation not found
     * @throws InvalidParameterException one or more parameters invalid
     * @throws MissingParameterException one or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
	 */
    public CluLoRelationInfo updateCluLoRelation(@WebParam(name="cluLoRelationId")String cluLoRelationId, @WebParam(name="cluLoRelationInfo")CluLoRelationInfo cluLoRelationInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Removes a relationship between a learning objective and a Clu.
     * @param cluLoRelationId CLU learning objective Relationship identifier
     * @return Status
     * @throws DoesNotExistException cluLoRelation not found
     * @throws InvalidParameterException invalid cluLoRelationId
     * @throws MissingParameterException cluLoRelationId not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteCluLoRelation(@WebParam(name="cluLoRelationId")String cluLoRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * Validates information about a clu set. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the clu set (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the clu set can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param cluSetInfo CLU set information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, cluSetInfo
     * @throws MissingParameterException missing validationTypeKey, cluSetInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateCluSet(@WebParam(name="validationType")String validationType, @WebParam(name="cluSetInfo")CluSetInfo cluSetInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a CLU set.
     * @param cluSetType type of the CLU set to be created
     * @param cluSetInfo information required to create a CLU set
     * @return the created CLU set information
     * @throws AlreadyExistsException the cluSet already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException invalid cluSetName, cluSetInfo
     * @throws MissingParameterException missing cluSetName, cluSetInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException CLU set need to be static or dynamic but not both
	 */
    public CluSetInfo createCluSet(@WebParam(name="cluSetType")String cluSetType, @WebParam(name="cluSetInfo")CluSetInfo cluSetInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Update the information for a CLU set
     * @param cluSetId identifier of the CLU set to be updated
     * @param cluSetInfo updated information about the CLU set
     * @return the updated CLU set information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException cluSet not found
     * @throws InvalidParameterException invalid cluSetId, cluSetInfo
     * @throws MissingParameterException missing cluSetId, cluSetInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     * @throws UnsupportedActionException CLU set need to be static or dynamic but not both
     * @throws CircularRelationshipException addedCluSetId cannot be added to the cluSetId
	 */
    public CluSetInfo updateCluSet(@WebParam(name="cluSetId")String cluSetId, @WebParam(name="cluSetInfo")CluSetInfo cluSetInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, UnsupportedActionException, CircularRelationshipException;

    /** 
     * Delete a CLU set
     * @param cluSetId identifier of the CLU set to be deleted
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException cluSet not found
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
     * @throws CircularRelationshipException addedCluSetId cannot be added to the cluSetId
     * @throws DoesNotExistException cluSet, addedCluSet not found
     * @throws InvalidParameterException invalid cluSetId, addedCluSetId
     * @throws MissingParameterException missing cluSetId, addedCluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException CLU set is dynamically determined
	 */
    public StatusInfo addCluSetToCluSet(@WebParam(name="cluSetId")String cluSetId, @WebParam(name="addedCluSetId")String addedCluSetId) throws CircularRelationshipException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * 	Adds a list of CLU sets to another CluSet. If any individual one would fail, then an error is returned and none are added.
     * @param cluSetId identifier of the host CLU set
     * @param addedCluSetIdList list of identifiers of the CLU sets to be added
     * @return status of the operation (success or failure)
     * @throws CircularRelationshipException addedCluSetId cannot be added to the cluSetId
     * @throws DoesNotExistException cluSet, addedCluSet not found
     * @throws InvalidParameterException invalid cluSetId, addedCluSetId
     * @throws MissingParameterException missing cluSetId, addedCluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException CLU set is dynamically determined
	 */
    public StatusInfo addCluSetsToCluSet(@WebParam(name="cluSetId")String cluSetId, @WebParam(name="addedCluSetIdList")List<String> addedCluSetIdList) throws CircularRelationshipException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Removes one CLU set from another
     * @param cluSetId identifier of the host CLU set
     * @param removedCluSetId identifier of the CLU set to be removed
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException cluSet, removedCluSet not found
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
     * @throws DoesNotExistException clu, cluSet not found
     * @throws InvalidParameterException invalid cluId, cluSetId
     * @throws MissingParameterException missing cluId, cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException CLU set is dynamically determined
	 */
    public StatusInfo addCluToCluSet(@WebParam(name="cluId")String cluId, @WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Adds a list of CLUs to a CLU set. If any individual one would fail, then an error is returned and none are added.
     * @param cluSetIds list of identifiers of CLUs to add to the CLU set
     * @param cluSetId identifier of the CLU set to be added
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException clu, cluSet not found
     * @throws InvalidParameterException invalid cluId, cluSetId
     * @throws MissingParameterException missing cluId, cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException CLU set is dynamically determined
	 */
    public StatusInfo addClusToCluSet(@WebParam(name="cluIdList")List<String> cluIdList, @WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Remove a CLU from a CLU set
     * @param cluId identifier of CLU to remove from the CLU set
     * @param cluSetId identifier of the CLU set
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException clu, cluSet not found
     * @throws InvalidParameterException invalid cluId, cluSetId
     * @throws MissingParameterException missing cluId, cluSetId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException CLU set is dynamically determined
	 */
    public StatusInfo removeCluFromCluSet(@WebParam(name="cluId")String cluId, @WebParam(name="cluSetId")String cluSetId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Validates a LUI. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the LUI (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the LUI can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param luiInfo LUI information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, luiInfo
     * @throws MissingParameterException missing validationTypeKey, luiInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateLui(@WebParam(name="validationType")String validationType, @WebParam(name="luiInfo")LuiInfo luiInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new LUI
     * @param cluId identifier of the CLU for the LUI being created
     * @param atpKey identifier of the academic time period for the LUI being created
     * @param luiInfo information about the LUI being created
     * @return the created LUI information
     * @throws AlreadyExistsException LUI already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException clu, atp not found
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
     * @throws DoesNotExistException lui not found
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
     * @throws DoesNotExistException lui not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteLui(@WebParam(name="luiId")String luiId) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates the state of the LUI
     * @param luiId identifier for the LUI to be updated
     * @param luState New state for LUI. Value is expected to be constrained to those in the luState enumeration.
     * @return the updated LUI information
     * @throws DataValidationErrorException New state not valid for existing state of LUI
     * @throws DoesNotExistException lui, luState not found
     * @throws InvalidParameterException invalid luiId, luState
     * @throws MissingParameterException missing luiId, luState
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public LuiInfo updateLuiState(@WebParam(name="luiId")String luiId, @WebParam(name="luState")String luState) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a relationship between LUIs. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the relationship (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the relationship can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param luiLuiRelationInfo LUI to LUI relationship information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, luiLuiRelationInfo
     * @throws MissingParameterException missing validationTypeKey, luiLuiRelationInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateLuiLuiRelation(@WebParam(name="validationType")String validationType, @WebParam(name="luiLuiRelationInfo")LuiLuiRelationInfo luiLuiRelationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Create a relationship between two LUIs
     * @param luiId identifier of the first LUI in the relationship
     * @param relatedLuiId identifier of the second LUI in the relationship to be related to
     * @param luLuRelationType the LU to LU relationship type of the relationship
     * @param luiLuiRelationInfo information about the relationship between the two LUIs
     * @return the created LUI to LUI relation information
     * @throws AlreadyExistsException relationship already exists
     * @throws CircularRelationshipException luiId equals relatedLuiId
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException luiId, relatedLuiId, luLuRelationType not found
     * @throws InvalidParameterException invalid luiIds, luluRelationType, luiLuiRelationInfo
     * @throws MissingParameterException missing luiIds, luluRelationType, luiLuiRelationInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public LuiLuiRelationInfo createLuiLuiRelation(@WebParam(name="luiId")String luiId, @WebParam(name="relatedLuiId")String relatedLuiId, @WebParam(name="luLuRelationType")String luLuRelationType, @WebParam(name="luiLuiRelationInfo")LuiLuiRelationInfo luiLuiRelationInfo) throws AlreadyExistsException, CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a relationship between two LUIs
     * @param luiLuiRelationId identifier of the LUI to LUI relation to update
     * @param luiLuiRelationInfo changed information about the relationship between the two LUIs
     * @return the update LUI to LUI relation information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException luiLuiRelation not found
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
     * @throws DoesNotExistException luiLuiRelation not found
     * @throws InvalidParameterException invalid luiLuiRelationId
     * @throws MissingParameterException missing luiLuiRelationId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteLuiLuiRelation(@WebParam(name="luiLuiRelationId")String luiLuiRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;



}