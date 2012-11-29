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
package org.kuali.student.r2.lum.clu.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.core.versionmanagement.service.VersionManagementService;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluLoRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluPublicationInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetTreeViewInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;

/**
 * Learning Unit (LU) Service
 *
 * @Author Kamal
 * @version 2.0
 */
@WebService(name = "CluService", targetNamespace = CluServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CluService extends VersionManagementService ,SearchService {

    /**
     * Retrieves the list of delivery method types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of delivery method type information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getDeliveryMethodTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about a delivery method type
     *
     * @param deliveryMethodTypeKey Key of the Delivery Method Type
     * @param contextInfo           Context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return information about a Delivery Method Type
     * @throws DoesNotExistException     deliveryMethodType not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing deliveryMethodType or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TypeInfo getDeliveryMethodType(@WebParam(name = "deliveryMethodTypeKey") String deliveryMethodTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of instructional format types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of instructional format type information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getInstructionalFormatTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about a Instructional Format Type
     *
     * @param instructionalFormatTypeKey Key of the Instructional Format Type
     * @param contextInfo                Context information containing the
     *                                   principalId and locale information
     *                                   about the caller of service operation
     * @return information about a Instructional Format Type
     * @throws DoesNotExistException     instructionalFormatTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing instructionalFormatTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TypeInfo getInstructionalFormatType(@WebParam(name = "instructionalFormatTypeKey") String instructionalFormatTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of LU types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of LU type information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getLuTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about a LU Type
     *
     * @param luTypeKey   Key of the LU Type
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return information about a LU Type
     * @throws DoesNotExistException     luTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing luTypeKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TypeInfo getLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of learning unit code types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of lu code type information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getLuCodeTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about a learning unit code type
     *
     * @param luCodeTypeKey Key of the learning unit code type
     * @param contextInfo   Context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return information about a learning unit code type
     * @throws DoesNotExistException     luCodeTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing luCodeTypeKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TypeInfo getLuCodeType(@WebParam(name = "luCodeTypeKey") String luCodeTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the complete list of LU to LU relation types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of LU to LU relation type information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getCluCluRelationTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the LU to LU relation type
     *
     * @param cluCluRelationTypeKey Key of the LU to LU Relation Type
     * @param contextInfo           Context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return LU to LU relation type information
     * @throws DoesNotExistException     cluCluRelationTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluCluRelationTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TypeInfo getLuLuRelationType(@WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of allowed relation types between the two specified LU
     * Types
     *
     * @param luTypeKey        Key of the first LU Type
     * @param relatedLuTypeKey Key of the second LU Type
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return list of LU to LU relation types
     * @throws DoesNotExistException     luTypeKey, relatedLuTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing luTypeKey, relatedLuTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getAllowedLuLuRelationTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "relatedLuTypeKey") String relatedLuTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of Learning Unit publication types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of Learning Unit publication type information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getLuPublicationTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about a publication type
     *
     * @param luPublicationTypeKey Key of the Learning Unit Publication Type
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return information about a Learning Unit Publication Type
     * @throws DoesNotExistException     luPublicationTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing luPublicationTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TypeInfo getLuPublicationType(@WebParam(name = "luPublicationTypeKey") String luPublicationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about a publication type
     *
     * @param luTypeKey   Key of the LU Type
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of LU publication types
     * @throws DoesNotExistException     luTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing luTypeKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getLuPublicationTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of types for clu result objects.
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of clu result type information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getCluResultTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about a publication type
     *
     * @param cluResultTypeKey Key of the Canonical Learning Unit Result Type
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return information about a Learning Unit Publication Type
     * @throws DoesNotExistException     cluResultTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluResultTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TypeInfo getCluResultType(@WebParam(name = "cluResultTypeKey") String cluResultTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of clu result types which are allowed to be used in
     * conjunction with a particular lu type.
     *
     * @param luTypeKey   luTypeKey
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of clu result types
     * @throws DoesNotExistException     luTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing luTypeKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getCluResultTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of result usage types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of result usage type information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getResultUsageTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about a Result Usage Type
     *
     * @param resultUsageTypeKey Key of the Result Usage Type
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return information about a Result Usage Type
     * @throws DoesNotExistException     resultUsageTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing resultUsageTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TypeInfo getResultUsageType(@WebParam(name = "resultUsageTypeKey") String resultUsageTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of result usage types which are allowed to be used in
     * conjunction with an lu type.
     *
     * @param luTypeKey   luTypeKey
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of result usage types
     * @throws DoesNotExistException     luTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing luTypeKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getAllowedResultUsageTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of result component types which are allowed to be used
     * in conjunction with result usage type.
     *
     * @param resultUsageTypeKey resultUsageTypeKey
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return list of result component types
     * @throws DoesNotExistException     resultUsageTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing resultUsageTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getAllowedResultComponentTypesForResultUsageType(@WebParam(name = "resultUsageTypeKey") String resultUsageTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the complete list of CLU to LO relation types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of CLU to LO relation type information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getCluLoRelationTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information for a specified CLU to LO relation type
     *
     * @param cluLoRelationTypeKey Key of the CLU to LO Relation Type
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return CLU to LO relation type information
     * @throws DoesNotExistException     cluLoRelationTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluLoRelationTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TypeInfo getCluLoRelationType(@WebParam(name = "cluLoRelationTypeKey") String cluLoRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of CLU LO relation types which are allowed to be used
     * in conjunction with an lu type.
     *
     * @param luTypeKey   luTypeKey
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of clu lo relation types
     * @throws DoesNotExistException     luTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing luTypeKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getAllowedCluLoRelationTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of CLU set types known by the service
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of CLU set type information
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TypeInfo> getCluSetTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about a specified CLU set type
     *
     * @param cluSetTypeKey Key of the CLU set type
     * @param contextInfo   Context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return information about a CLU set type
     * @throws DoesNotExistException     cluSetTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluSetTypeKey or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TypeInfo getCluSetType(@WebParam(name = "cluSetTypeKey") String cluSetTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves core information about a CLU
     *
     * @param cluId       identifier of the CLU
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return information about a CLU
     * @throws DoesNotExistException     cluId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CluInfo getClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about CLUs from a list of Ids
     *
     * @param cluIds      List of CLU identifiers
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return information a list of CLUs
     * @throws DoesNotExistException     One or more cluIds not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluIds or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluInfo> getClusByIds(@WebParam(name = "cluIds") List<String> cluIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of CLUs for the specified LU Type and state
     *
     * @param luTypeKey   Type of the CLUs to retrieve
     * @param luState     State of the CLUs to retrieve.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of CLU information
     * @throws DoesNotExistException     luTypeKey or luState not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing luTypeKey, luState or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluInfo> getClusByLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "luState") String luState, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of CLU Ids for the specified LU Type and state
     *
     * @param luTypeKey   Type of the CLUs whose identifiers should be
     *                    retrieved
     * @param luState     State of the CLUs whose identifiers should be
     *                    retrieved
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of CLU identifiers
     * @throws DoesNotExistException     luType or luState not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing luType, luState or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCluIdsByLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "luState") String luState, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of allowed relation types between the two specified
     * CLUs
     *
     * @param cluId        identifier of the first CLU
     * @param relatedCluId identifier of the second CLU
     * @param contextInfo  Context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return list of LU to LU relation types
     * @throws DoesNotExistException     cluId, relatedCluId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluId, relatedCluId or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getAllowedCluCluRelationTypesByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of CLU information for the CLUs related to a specified
     * CLU Id with a certain LU to LU relation type (getRelatedClusByClu from
     * the other direction)
     *
     * @param relatedCluId          identifier of the child or To CLU
     * @param cluCLuRelationTypeKey the LU to LU relation type
     * @param contextInfo           Context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return list of CLU information
     * @throws DoesNotExistException     relatedCluId, cluCLuRelationTypeKey not
     *                                   found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing relatedCluId, cluCLuRelationTypeKey
     *                                   or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluInfo> getClusByRelatedCluAndRelationType(@WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "cluCLuRelationTypeKey") String cluCLuRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of CLU Ids for the specified related CLU Id and LU to
     * LU relation type (getRelatedCluIdsByCluAndRelationType from the other
     * direction)
     *
     * @param relatedCluId          identifier of the child or To CLU
     * @param cluCluRelationTypeKey the LU to LU relation type
     * @param contextInfo           Context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return list of CLU identifiers
     * @throws DoesNotExistException     relatedCluId or cluCluRelationTypeKey
     *                                   not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing relatedCluId, cluCluRelationTypeKey
     *                                   or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCluIdsByRelatedCluAndRelationType(@WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of related CLU information for the specified CLU Id
     * and LU to LU relation type (getClusByRelation from the other direction)
     *
     * @param cluId                 identifier of the parent or From CLU
     * @param cluCluRelationTypeKey the LU to LU relation type
     * @param contextInfo           Context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return list of CLU information
     * @throws DoesNotExistException     cluId or cluCluRelationTypeKey not
     *                                   found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluId, cluCluRelationTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluInfo> getRelatedClusByCluAndRelationType(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of related CLU Ids for the specified CLU Id and LU to
     * LU relation type (getCluIdsByRelatedCluAndCluCluRelationType from the
     * other direction)
     *
     * @param cluId                 identifier of the parent or From CLU
     * @param cluCluRelationTypeKey the LU to LU relation type
     * @param contextInfo           Context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return list of CLU identifiers
     * @throws DoesNotExistException     cluId, cluCluRelationTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluId, cluCluRelationTypeKey
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getRelatedCluIdsByCluAndRelationType(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the relationship information between CLUs for a particular
     * Relation instance
     *
     * @param cluCluRelationId identifier of the CLU to CLU relation
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return information on the relation between two CLUs
     * @throws DoesNotExistException     cluCluRelationId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluCluRelationId or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CluCluRelationInfo getCluCluRelation(@WebParam(name = "cluCluRelationId") String cluCluRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of relationship information for the specified CLU
     *
     * @param cluId       identifier of the parent or From CLU
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of CLU to CLU relation information
     * @throws DoesNotExistException     cluId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluCluRelationInfo> getCluCluRelationsByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of publication objects for a particular clu
     *
     * @param cluId       clu identifier
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of publication objects used by the specified clu
     * @throws DoesNotExistException     cluId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluPublicationInfo> getCluPublicationsByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of publication objects of a particular Type
     *
     * @param luPublicationTypeKey luPublicationType identifier
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return list of CLU Publication objects using the specified type
     * @throws DoesNotExistException     luPublicationType not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing luPublicationTypeKey or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluPublicationInfo> getCluPublicationsByType(@WebParam(name = "luPublicationTypeKey") String luPublicationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves an LU publication object by its identifier
     *
     * @param cluPublicationId CLU publication identifier
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return CLU Publication information
     * @throws DoesNotExistException     cluPublicationId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluPublicationId or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CluPublicationInfo getCluPublication(@WebParam(name = "cluPublicationId") String cluPublicationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about a Clu Result
     *
     * @param cluResultId identifier of the Clu Result
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return information about a Clu Result
     * @throws DoesNotExistException     cluResultId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluResultId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CluResultInfo getCluResult(@WebParam(name = "cluResultId") String cluResultId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the cluResult for a particular clu
     *
     * @param cluId       clu identifier
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return result information for a clu
     * @throws DoesNotExistException     cluId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluResultInfo> getCluResultByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of cluResults for the cluIds specified
     *
     * @param cluIds       list of clu identifier's
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of CluResults corresponding to the cluIds specified
     * @throws DoesNotExistException     one or more of the cludIds does not exist.
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluIds or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluResultInfo> getCluResultsByClus(@WebParam(name = "cluIds") List<String> cluIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    
    /**
     * Retrieves the list of clu Ids with the results of the specified usage
     * type. This would for example allow requests for all clus which have a
     * final grade.
     *
     * @param resultUsageTypeKey identifier of the result usage type
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return list of clu Ids
     * @throws DoesNotExistException     resultUsageType not found
     * @throws InvalidParameterException invalid resultUsageTypeKey
     * @throws MissingParameterException missing resultUsageTypeKey
     * @throws OperationFailedException  unable to complete request
     */
    public List<String> getCluIdsByResultUsageType(@WebParam(name = "resultUsageTypeKey") String resultUsageTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of clu Ids which use a particular result component
     *
     * @param resultComponentId identifier of the result component
     * @param contextInfo       Context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return list of clu Ids
     * @throws DoesNotExistException     resultComponent not found
     * @throws InvalidParameterException invalid resultComponentId
     * @throws MissingParameterException missing resultComponentId
     * @throws OperationFailedException  unable to complete request
     */
    public List<String> getCluIdsByResultComponent(@WebParam(name = "resultComponentId") String resultComponentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieve information on a CLU LO Relation.
     *
     * @param cluLoRelationId Identifier of the CLU LO Relation
     * @param contextInfo     Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return The retrieved CLU LO Relation information
     * @throws DoesNotExistException     cluLoRelationId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluLoRelationId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CluLoRelationInfo getCluLoRelation(@WebParam(name = "cluLoRelationId") String cluLoRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of canonical learning unit to learning objective
     * relationships for a given CLU.
     *
     * @param cluId       Identifier for the CLU
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return List of canonical learning unit to learning objective
     *         relationships
     * @throws DoesNotExistException     cluId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluLoRelationInfo> getCluLoRelationsByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of CLU identifiers associated with a given learning
     * objective identifier.
     *
     * @param loId        Identifier for the learning objective
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return List of CLU LO Relations
     * @throws DoesNotExistException     loId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing loId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluLoRelationInfo> getCluLoRelationsByLo(@WebParam(name = "loId") String loId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of Resource requirements for the specified CLU
     *
     * @param cluId       Unique identifier for the CLU
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return List of resource requirements
     * @throws DoesNotExistException     cluId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getResourceRequirementsForClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve information on a CLU set. This information should be about the
     * set itself, and in the case of a dynamic CLU set, should include the
     * criteria used to generate the set.
     *
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved CLU set information
     * @throws DoesNotExistException     cluSetId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluSetId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CluSetInfo getCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve information on a CLU set and its sub clu set fully expanded.
     *
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved CLU set tree view information
     * @throws DoesNotExistException     cluSetId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluSetId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CluSetTreeViewInfo getCluSetTreeView(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve information on CLU sets from a list of cluSet Ids.
     *
     * @param cluSetIds   List of identifiers of CLU sets
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of CLU set information
     * @throws DoesNotExistException     One or more cluSets not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluSetIds or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluSetInfo> getCluSetsByIds(@WebParam(name = "cluSetIds") List<String> cluSetIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve the list of CLU Set Ids within a CLU Set
     *
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of CLU Set Ids within the specified CLU set
     * @throws DoesNotExistException     cluSet not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluSetId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCluSetIdsFromCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Check if the given CluSet is dynamic
     *
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of CLU Set Ids within the specified CLU set
     * @throws DoesNotExistException     cluSetId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluSetId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public Boolean isCluSetDynamic(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of CLUs in a CLU set. This only retrieves the direct
     * members.
     *
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of information on the CLUs within the CLU set
     *         (flattened and de-duped)
     * @throws DoesNotExistException     cluSetId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluSetId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluInfo> getClusFromCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of CLU Identifiers within a CLU Set. This only
     * retrieves the direct members.
     *
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of CLU Ids within the specified CLU set
     *         (flattened and de-duped)
     * @throws DoesNotExistException     cluSetId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluSetId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCluIdsFromCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the full list of CLUs in this CLU set or any cluset that is
     * included within that.
     *
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of information on the CLUs
     * @throws DoesNotExistException     cluSet not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluSetId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluInfo> getAllClusInCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of CLU Identifiers within a CLU Set or any cluset that
     * is included within that.
     *
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of CLU Ids within the specified CLU set
     * @throws DoesNotExistException     cluSetId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluSetId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getAllCluIdsInCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Checks if a CLU is a member of a CLU set or any contained CLU set
     *
     * @param cluId       Identifier of the CLU to check
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return True if the CLU is a member of the CLU Set
     * @throws DoesNotExistException     cluId, cluSetId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluId, cluSetId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public Boolean isCluInCluSet(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a CLU. Depending on the value of validationType, this
     * validation could be limited to tests on just the current object and its
     * directly contained sub-objects or expanded to perform all tests related
     * to this object. If an identifier is present for the CLU (and/or one of
     * its contained sub-objects) and a record is found for that identifier, the
     * validation checks if the CLU can be shifted to the new values. If an
     * identifier is not present or a record cannot be found for the identifier,
     * it is assumed that the record does not exist and as such, the checks
     * performed will be much shallower, typically mimicking those performed by
     * setting the validationType to the current object.
     *
     * @param validationTypeKey identifier of the extent of validation
     * @param cluInfo           CLU information to be tested.
     * @param contextInfo       Context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return results from performing the validation
     * @throws DoesNotExistException     validationTypeKey not found
     * @throws InvalidParameterException invalid cluInfo or contextInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateClu(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluInfo") CluInfo cluInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new CLU
     *
     * @param luTypeKey   identifier of the LU Type for the CLU being created
     * @param cluInfo     information about the CLU being created
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the created CLU information
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        luTypeKey not found
     * @throws InvalidParameterException    invalid cluInfo or contextInfo
     * @throws MissingParameterException    missing luTypeKey, cluInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public CluInfo createClu(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "cluInfo") CluInfo cluInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing CLU
     *
     * @param cluId       identifier for the CLU to be updated
     * @param cluInfo     updated information about the CLU
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the updated CLU information
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        cluId not found
     * @throws InvalidParameterException    invalid cluInfo or contextInfo
     * @throws MissingParameterException    missing cluId, cluInfo or
     *                                      contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public CluInfo updateClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluInfo") CluInfo cluInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing CLU
     *
     * @param cluId       identifier for the CLU to be deleted
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return status of the operation
     * @throws DependentObjectsExistException delete would leave orphaned
     *                                        objects or violate integrity
     *                                        constraints
     * @throws DoesNotExistException          cluId not found
     * @throws InvalidParameterException      invalid contextInfo
     * @throws MissingParameterException      missing cluId or contextInfo
     * @throws OperationFailedException       unable to complete request
     * @throws PermissionDeniedException      authorization failure
     */
    public StatusInfo deleteClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new CLU version based on the current clu
     *
     * @param cluId          identifier for the CLU to be versioned
     * @param versionComment comment for the current version
     * @param contextInfo    Context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return the new versioned CLU information
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        cluId not found
     * @throws InvalidParameterException    invalid contextInfo
     * @throws MissingParameterException    missing cluId, versionComment or
     *                                      contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public CluInfo createNewCluVersion(@WebParam(name = "cluId") String cluId,
                                       @WebParam(name = "versionComment") String versionComment,
                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Sets a specific version of the Clu as current. The sequence number must
     * be greater than the existing current Clu version. This will truncate the
     * current version's end date to the currentVersionStart param. If a Clu
     * exists which is set to become current in the future, that clu's
     * currentVersionStart and CurrentVersionEnd will be nullified. The
     * currentVersionStart must be in the future to prevent changing historic
     * data.
     *
     * @param cluVersionId        Version Specific Id of the Clu
     * @param currentVersionStart Date when this clu becomes current. Must be in
     *                            the future and be after the most current clu's
     *                            start date.
     * @param contextInfo         Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return status of the operation
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        cluVersionId not found
     * @throws InvalidParameterException    invalid contextInfo
     * @throws MissingParameterException    missing cluVersionId, previousState,
     *                                      newState
     * @throws IllegalVersionSequencingException a Clu with higher sequence number
     *                                      from the one provided is marked
     *                                      current
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public StatusInfo setCurrentCluVersion(@WebParam(name = "cluVersionId") String cluVersionId, @WebParam(name = "currentVersionStart") Date currentVersionStart, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates the state of the specified CLU
     *
     * @param cluId       identifier for the CLU to be updated
     * @param luState     new state for the CLU. Value is expected to be
     *                    constrained to those in the luState enumeration.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the updated CLU information
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        cluId not found
     * @throws InvalidParameterException    invalid contextInfo
     * @throws MissingParameterException    missing cluId, luState or
     *                                      contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public CluInfo updateCluState(@WebParam(name = "cluId") String cluId, @WebParam(name = "luState") String luState, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Validates a cluCluRelation. Depending on the value of validationType,
     * this validation could be limited to tests on just the current object and
     * its directly contained sub-objects or expanded to perform all tests
     * related to this object. If an identifier is present for the
     * cluCluRelation (and/or one of its contained sub-objects) and a record is
     * found for that identifier, the validation checks if the relationship can
     * be shifted to the new values. If an identifier is not present or a record
     * cannot be found for the identifier, it is assumed that the record does
     * not exist and as such, the checks performed will be much shallower,
     * typically mimicking those performed by setting the validationType to the
     * current object.
     *
     * @param validationTypeKey     identifier of the extent of validation
     * @param cluId                 identifier of the first CLU in the
     *                              relationship - The From or Parent of the
     *                              relation
     * @param relatedCluId          identifier of the second CLU in the
     *                              relationship to be related to - the To or
     *                              Child of the Relation
     * @param cluCluRelationTypeKey the CLU to CLU relationship type of the
     *                              relationship
     * @param cluCluRelationInfo    cluCluRelation information to be tested.
     * @param contextInfo           Context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return results from performing the validation
     * @throws DoesNotExistException     validationTypeKey, cluId, relatedCluId
     *                                   or cluCluRelationTypeKey  not found
     * @throws InvalidParameterException invalid cluCluRelationInfo or
     *                                   contextInfo
     * @throws MissingParameterException missing validationTypeKey, cluId,
     *                                   relatedCluId, cluCluRelationTypeKey or
     *                                   cluCluRelationInfo or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateCluCluRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "cluCluRelationInfo") CluCluRelationInfo cluCluRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create a directional relationship between two CLUs
     *
     * @param cluId                 identifier of the first CLU in the
     *                              relationship - The From or Parent of the
     *                              relation
     * @param relatedCluId          identifier of the second CLU in the
     *                              relationship to be related to - the To or
     *                              Child of the Relation
     * @param cluCluRelationTypeKey the LU to LU relationship type of the
     *                              relationship
     * @param cluCluRelationInfo    information about the relationship between
     *                              the two CLUs
     * @param contextInfo           Context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return the created CLU to CLU relation information
     * @throws CircularRelationshipException cluId equals relatedCluId
     * @throws DataValidationErrorException  One or more values invalid for this
     *                                       operation
     * @throws DoesNotExistException         cluId, relatedCluId, cluCluRelationTypeKey
     *                                       not found
     * @throws InvalidParameterException     invalid cluCluRelationInfo or
     *                                       contextInfo
     * @throws MissingParameterException     missing cluId, relatedCluId,
     *                                       cluCluRelationTypeKey, cluCluRelationInfo
     *                                       or contextInfo
     * @throws OperationFailedException      unable to complete request
     * @throws PermissionDeniedException     authorization failure
     * @throws ReadOnlyException             an attempt at supplying information
     *                                       designated as read only
     */
    public CluCluRelationInfo createCluCluRelation(@WebParam(name = "cluId") String cluId, @WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "cluCluRelationInfo") CluCluRelationInfo cluCluRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates a relationship between two CLUs
     *
     * @param cluCluRelationId   identifier of the CLU to CLU relation to be
     *                           updated
     * @param cluCluRelationInfo changed information about the CLU to CLU
     *                           relationship
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return the updated CLU to CLU relation information
     * @throws DataValidationErrorException One or more values invalid for this
     *                                      operation
     * @throws DoesNotExistException        cluCluRelation not found
     * @throws InvalidParameterException    invalid cluCluRelationInfo or
     *                                      contextInfo
     * @throws MissingParameterException    missing cluCluRelationId,
     *                                      cluCluRelationInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public CluCluRelationInfo updateCluCluRelation(@WebParam(name = "cluCluRelationId") String cluCluRelationId, @WebParam(name = "cluCluRelationInfo") CluCluRelationInfo cluCluRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes a relationship between two CLUs
     *
     * @param cluCluRelationId identifier of CLU to CLU relationship to delete
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException     cluCluRelationId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluCluRelationId or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCluCluRelation(@WebParam(name = "cluCluRelationId") String cluCluRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates information about publication for a clu. Depending on the value
     * of validationTypeKey, this validation could be limited to tests on just
     * the current object and its directly contained sub-objects or expanded to
     * perform all tests related to this object. If an identifier is present for
     * the clu publication object (and/or one of its contained sub-objects) and
     * a record is found for that identifier, the validation checks if the clu
     * publication object can be shifted to the new values. If an identifier is
     * not present or a record cannot be found for the identifier, it is assumed
     * that the record does not exist and as such, the checks performed will be
     * much shallower, typically mimicking those performed by setting the
     * validationTypeKey to the current object.
     *
     * @param validationTypeKey    identifier of the extent of validation
     * @param cluId                identifier of a clu
     * @param luPublicationTypeKey type of lu publication
     * @param cluPublicationInfo   CLU publication information to be tested.
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return results from performing the validation
     * @throws DoesNotExistException     validationTypeKey, cluId or
     *                                   luPublicationTypeKey not found
     * @throws InvalidParameterException invalid cluPublicationInfo or
     *                                   contextInfo
     * @throws MissingParameterException missing validationTypeKey, cluId,
     *                                   luPublicationTypeKey, cluPublicationInfo
     *                                   or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateCluPublication(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                             @WebParam(name = "cluId") String cluId,
                                                             @WebParam(name = "luPublicationTypeKey") String luPublicationTypeKey,
                                                             @WebParam(name = "cluPublicationInfo") CluPublicationInfo cluPublicationInfo,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create a clu publication object, which contains information about
     * publication for a clu.
     *
     * @param cluId                identifier of a clu
     * @param luPublicationTypeKey type of lu publication
     * @param cluPublicationInfo   information about publication for a clu
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return information about the created clu publication object
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        cluId or luPublicationTypeKey not
     *                                      found
     * @throws InvalidParameterException    invalid cluPublicationInfo or
     *                                      contextInfo
     * @throws MissingParameterException    missing cluId, luPublicationTypeKey,
     *                                      cluPublicationInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public CluPublicationInfo createCluPublication(@WebParam(name = "cluId") String cluId, @WebParam(name = "luPublicationTypeKey") String luPublicationTypeKey, @WebParam(name = "cluPublicationInfo") CluPublicationInfo cluPublicationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing clu publication object
     *
     * @param cluPublicationId   identifier for the clu publication object to be
     *                           updated
     * @param cluPublicationInfo updated information about the clu publication
     *                           object
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return the updated clu publication information
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        cluPublicationId not found
     * @throws InvalidParameterException    invalid cluPublicationInfo or
     *                                      contextInfo
     * @throws MissingParameterException    missing cluPublicationId,
     *                                      cluPublicationInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @throws VersionMismatchException     The action was attempted on an out
     *                                      of date version.
     */
    public CluPublicationInfo updateCluPublication(@WebParam(name = "cluPublicationId") String cluPublicationId, @WebParam(name = "cluPublicationInfo") CluPublicationInfo cluPublicationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing clu publication object
     *
     * @param cluPublicationId identifier for the clu publication object to be
     *                         deleted
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return status of the operation
     * @throws DependentObjectsExistException delete would leave orphaned
     *                                        objects or violate integrity
     *                                        constraints
     * @throws DoesNotExistException          cluPublicationId not found
     * @throws InvalidParameterException      invalid contextInfo
     * @throws MissingParameterException      missing cluPublicationId or
     *                                        contextInfo
     * @throws OperationFailedException       unable to complete request
     * @throws PermissionDeniedException      authorization failure
     */
    public StatusInfo deleteCluPublication(@WebParam(name = "cluPublicationId") String cluPublicationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates information about results for a clu. Depending on the value of
     * validationTypeKey, this validation could be limited to tests on just the
     * current object and its directly contained sub-objects or expanded to
     * perform all tests related to this object. If an identifier is present for
     * the clu result object (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if the clu
     * result object can be shifted to the new values. If an identifier is not
     * present or a record cannot be found for the identifier, it is assumed
     * that the record does not exist and as such, the checks performed will be
     * much shallower, typically mimicking those performed by setting the
     * validationTypeKey to the current object.
     *
     * @param validationTypeKey identifier of the extent of validation
     * @param cluId             identifier of a clu
     * @param cluResultTypeKey  type of clu result
     * @param cluResultInfo     CLU result information to be tested.
     * @param contextInfo       Context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return results from performing the validation
     * @throws DoesNotExistException     validationTypeKey, cluId or
     *                                   cluResultTypeKey not found
     * @throws InvalidParameterException invalid cluResultInfo or contextInfo
     * @throws MissingParameterException missing validationTypeKey, cluId,
     *                                   cluResultTypeKey, cluResultInfo or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateCluResult(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                        @WebParam(name = "cluId") String cluId,
                                                        @WebParam(name = "cluResultTypeKey") String cluResultTypeKey,
                                                        @WebParam(name = "cluResultInfo") CluResultInfo cluResultInfo,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create a clu result object, which contains information about potential
     * results for a clu.
     *
     * @param cluId            identifier of a clu
     * @param cluResultTypeKey type of clu result
     * @param cluResultInfo    information about potential results for a clu
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return information about the created clu result
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        cluId or resultUsageTypeKey not
     *                                      found
     * @throws InvalidParameterException    invalid cluResultInfo or contextInfo
     * @throws MissingParameterException    missing cluId, cluResultTypeKey,
     *                                      cluResultInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public CluResultInfo createCluResult(@WebParam(name = "cluId") String cluId,
                                         @WebParam(name = "cluResultTypeKey") String cluResultTypeKey,
                                         @WebParam(name = "cluResultInfo") CluResultInfo cluResultInfo,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing clu result
     *
     * @param cluResultId   identifier for the clu result to be updated
     * @param cluResultInfo updated information about the clu result
     * @param contextInfo   Context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return the updated clu result information
     * @throws DataValidationErrorException One or more values invalid for this
     *                                      operation
     * @throws DoesNotExistException        cluResultId not found
     * @throws InvalidParameterException    invalid contextInfo
     * @throws MissingParameterException    missing cluResultId, cluResultInfo
     *                                      or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public CluResultInfo updateCluResult(@WebParam(name = "cluResultId") String cluResultId, @WebParam(name = "cluResultInfo") CluResultInfo cluResultInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an existing clu result
     *
     * @param cluResultId identifier for the clu result to be deleted
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return status of the operation
     * @throws DoesNotExistException          cluResultId not found
     * @throws InvalidParameterException      invalid contextInfo
     * @throws MissingParameterException      missing cluResultId or contextInfo
     * @throws DependentObjectsExistException delete would leave orphaned
     *                                        objects or violate integrity
     *                                        constraints
     * @throws OperationFailedException       unable to complete request
     * @throws PermissionDeniedException      authorization failure
     */
    public StatusInfo deleteCluResult(@WebParam(name = "cluResultId") String cluResultId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a cluLoRelation. Depending on the value of validationTypeKey,
     * this validation could be limited to tests on just the current object and
     * its directly contained sub-objects or expanded to perform all tests
     * related to this object. If an identifier is present for the cluLoRelation
     * (and/or one of its contained sub-objects) and a record is found for that
     * identifier, the validation checks if the relationship can be shifted to
     * the new values. If an identifier is not present or a record cannot be
     * found for the identifier, it is assumed that the record does not exist
     * and as such, the checks performed will be much shallower, typically
     * mimicking those performed by setting the validationTypeKey to the current
     * object.
     *
     * @param validationTypeKey    identifier of the extent of validation
     * @param cluId                CLU identifier
     * @param loId                 learning objective identifier
     * @param cluLoRelationTypeKey type of clu learning objective relationship
     * @param cluLoRelationInfo    cluLoRelation information to be tested.
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return results from performing the validation
     * @throws DoesNotExistException     validationTypeKey, cluId, loId or
     *                                   cluLoRelationTypeKey not found
     * @throws InvalidParameterException invalid cluLoRelationInfo or
     *                                   contextInfo
     * @throws MissingParameterException missing validationTypeKey, cluId, loId,
     *                                   cluLoRelationTypeKey, cluLoRelationInfo
     *                                   or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateCluLoRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "loId") String loId, @WebParam(name = "cluLoRelationTypeKey") String cluLoRelationTypeKey, @WebParam(name = "cluLoRelationInfo") CluLoRelationInfo cluLoRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a relationship between a learning objective and a CLU.
     *
     * @param cluId                CLU identifier
     * @param loId                 learning objective identifier
     * @param cluLoRelationTypeKey type of clu learning objective relationship
     * @param cluLoRelationInfo    clu learning objective relationship
     *                             information
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return the newly created clu learning objective relationship
     * @throws DataValidationErrorException data validation error
     * @throws DoesNotExistException        cluId, loId, cluLoRelationTypeKey
     *                                      not found
     * @throws InvalidParameterException    invalid cluLoRelationInfo or
     *                                      contextInfo
     * @throws MissingParameterException    missing cluId, loId, cluLoRelationTypeKey,
     *                                      cluLoRelationInfo or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public CluLoRelationInfo createCluLoRelation(@WebParam(name = "cluId") String cluId, @WebParam(name = "loId") String loId, @WebParam(name = "cluLoRelationTypeKey") String cluLoRelationTypeKey, @WebParam(name = "cluLoRelationInfo") CluLoRelationInfo cluLoRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates a relationship between a clu and learning objective
     *
     * @param cluLoRelationId   identifier of the clu learning objective
     *                          relationship to be updated
     * @param cluLoRelationInfo information about the clu learning objective
     *                          relationship to be updated
     * @param contextInfo       Context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return the updated clu learning objective relationship information
     * @throws DataValidationErrorException data validation error
     * @throws DoesNotExistException        cluLoRelationId not found
     * @throws InvalidParameterException    invalid cluLoRelationInfo or
     *                                      contextInfo
     * @throws MissingParameterException    missing cluLoRelationId, cluLoRelationInfo
     *                                      or contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @throws VersionMismatchException     The action was attempted on an out
     *                                      of date version.
     */
    public CluLoRelationInfo updateCluLoRelation(@WebParam(name = "cluLoRelationId") String cluLoRelationId, @WebParam(name = "cluLoRelationInfo") CluLoRelationInfo cluLoRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Removes a relationship between a learning objective and a Clu.
     *
     * @param cluLoRelationId CLU learning objective Relationship identifier
     * @param contextInfo     Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return Status of the delete operation
     * @throws DoesNotExistException     cluLoRelationId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluLoRelationId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCluLoRelation(@WebParam(name = "cluLoRelationId") String cluLoRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Add a Resource requirement to a CLU
     *
     * @param resourceTypeKey identifier of the resource requirement type to be
     *                        added to the CLU
     * @param cluId           identifier of the CLU
     * @param contextInfo     Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return status of the operation (success or failure)
     * @throws AlreadyExistsException if the resource type has already been added to the clu
     * @throws DoesNotExistException     resourceTypeKey or cluId not found
     * @throws InvalidParameterException invalid resourceTypeKey or cluId
     * @throws MissingParameterException missing resourceTypeKey, cluId or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo addCluResourceRequirement(@WebParam(name = "resourceTypeKey") String resourceTypeKey,
                                                @WebParam(name = "cluId") String cluId,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Remove a Resource requirement from a CLU
     *
     * @param resourceTypeKey identifier of the resource type to be removed from
     *                        the CLU
     * @param cluId           identifier of the CLU
     * @param contextInfo     Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException     resourceTypeKey or cluId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing resourceTypeKey, cluId or
     *                                   contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo removeCluResourceRequirement(@WebParam(name = "resourceTypeKey") String resourceTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates information about a clu set. Depending on the value of
     * validationTypeKey, this validation could be limited to tests on just the
     * current object and its directly contained sub-objects or expanded to
     * perform all tests related to this object. If an identifier is present for
     * the clu set (and/or one of its contained sub-objects) and a record is
     * found for that identifier, the validation checks if the clu set can be
     * shifted to the new values. If an identifier is not present or a record
     * cannot be found for the identifier, it is assumed that the record does
     * not exist and as such, the checks performed will be much shallower,
     * typically mimicking those performed by setting the validationType to the
     * current object.
     *
     * @param validationTypeKey identifier of the extent of validation
     * @param cluSetTypeKey     type of the CLU set to be created
     * @param cluSetInfo        CLU set information to be tested.
     * @param contextInfo       Context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return results from performing the validation
     * @throws DoesNotExistException     validationTypeKey or cluSetTypeKey not
     *                                   found
     * @throws InvalidParameterException invalid cluSetInfo or contextInfo
     * @throws MissingParameterException missing validationTypeKey, cluSetTypeKey,
     *                                   cluSetInfo or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateCluSet(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluSetTypeKey") String cluSetTypeKey, @WebParam(name = "cluSetInfo") CluSetInfo cluSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a CLU set.
     *
     * @param cluSetTypeKey type of the CLU set to be created
     * @param cluSetInfo    information required to create a CLU set
     * @param contextInfo   Context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return the created CLU set information
     * @throws DataValidationErrorException data validation error
     * @throws DoesNotExistException        cluSetTypeKey not found
     * @throws InvalidParameterException    invalid cluSetInfo or contextInfo
     * @throws MissingParameterException    missing cluSetTypeKey, cluSetInfo or
     *                                      contextInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @throws UnsupportedActionException   CLU set need to be static or dynamic
     *                                      but not both
     */
    public CluSetInfo createCluSet(@WebParam(name = "cluSetTypeKey") String cluSetTypeKey, @WebParam(name = "cluSetInfo") CluSetInfo cluSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException;

    /**
     * Update the information for a CLU set
     *
     * @param cluSetId    identifier of the CLU set to be updated
     * @param cluSetInfo  updated information about the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the updated CLU set information
     * @throws CircularRelationshipException added CluSetId cannot be added to
     *                                       the cluSetInfo
     * @throws DataValidationErrorException  data validation error
     * @throws DoesNotExistException         cluSetId not found
     * @throws InvalidParameterException     invalid cluSetInfo or contextInfo
     * @throws MissingParameterException     missing cluSetId, cluSetInfo or
     *                                       contextInfo
     * @throws OperationFailedException      unable to complete request
     * @throws PermissionDeniedException     authorization failure
     * @throws ReadOnlyException             an attempt at supplying information
     *                                       designated as read only
     * @throws UnsupportedActionException    CLU set need to be static or
     *                                       dynamic but not both
     * @throws VersionMismatchException      an optimistic locking failure or
     *                                       the action was attempted on an out
     *                                       of date version
     */
    public CluSetInfo updateCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "cluSetInfo") CluSetInfo cluSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException, VersionMismatchException;

    /**
     * Delete a CLU set
     *
     * @param cluSetId    identifier of the CLU set to be deleted
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException     cluSetId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException missing cluSetId or contextInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Adds one CLU set to another
     *
     * @param cluSetId      identifier of the host CLU set
     * @param addedCluSetId identifier of the CLU set to be added
     * @param contextInfo   Context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return status of the operation (success or failure)
     * @throws CircularRelationshipException addedCluSetId cannot be added to
     *                                       the CluSetId
     * @throws DoesNotExistException         cluSetId, addedCluSetId not found
     * @throws InvalidParameterException     invalid contextInfo
     * @throws MissingParameterException     missing cluSetId, addedCluSetId or
     *                                       contextInfo
     * @throws OperationFailedException      unable to complete request
     * @throws PermissionDeniedException     authorization failure
     * @throws UnsupportedActionException    CLU set is dynamically determined
     */
    public StatusInfo addCluSetToCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "addedCluSetId") String addedCluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws CircularRelationshipException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /**
     * Adds a list of CLU sets to another CluSet. If any individual one would
     * fail, then an error is returned and none are added.
     *
     * @param cluSetId       identifier of the host CLU set
     * @param addedCluSetIds list of identifiers of the CLU sets to be added
     * @param contextInfo    Context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return status of the operation (success or failure)
     * @throws CircularRelationshipException addedCluSetIds cannot be added to
     *                                       the cluSetId
     * @throws DoesNotExistException         cluSetId, one or more of cluSetIds
     *                                       in addedCluSetIds not found
     * @throws InvalidParameterException     invalid contextInfo
     * @throws MissingParameterException     missing cluSetId, addedCluSetIds or
     *                                       contextInfo
     * @throws OperationFailedException      unable to complete request
     * @throws PermissionDeniedException     authorization failure
     * @throws UnsupportedActionException    CLU set is dynamically determined
     */
    public StatusInfo addCluSetsToCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "addedCluSetIds") List<String> addedCluSetIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws CircularRelationshipException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /**
     * Removes one CLU set from another
     *
     * @param cluSetId        identifier of the host CLU set
     * @param removedCluSetId identifier of the CLU set to be removed
     * @param contextInfo     Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException      cluSetId, removedCluSetId not found
     * @throws InvalidParameterException  invalid contextInfo
     * @throws MissingParameterException  missing cluSetId, removedCluSetId or
     *                                    contextInfo
     * @throws OperationFailedException   unable to complete request
     * @throws PermissionDeniedException  authorization failure
     * @throws UnsupportedActionException CLU set is dynamically determined
     */
    public StatusInfo removeCluSetFromCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "removedCluSetId") String removedCluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /**
     * Add a CLU to a CLU set
     *
     * @param cluId       identifier of CLU to add to the CLU set
     * @param cluSetId    identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException      cluId, cluSetId not found
     * @throws InvalidParameterException  invalid contextInfo
     * @throws MissingParameterException  missing cluId, cluSetId or
     *                                    contextInfo
     * @throws OperationFailedException   unable to complete request
     * @throws PermissionDeniedException  authorization failure
     * @throws UnsupportedActionException CLU set is dynamically determined
     */
    public StatusInfo addCluToCluSet(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /**
     * Adds a list of CLUs to a CLU set. If any individual one would fail, then
     * an error is returned and none are added.
     *
     * @param cluSetIds   list of identifiers of CLUs to add to the CLU set
     * @param cluSetId    identifier of the CLU set to be added
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException      cluSetIds, cluSetId not found
     * @throws InvalidParameterException  invalid contextInfo
     * @throws MissingParameterException  missing cluSetIds, cluSetId or
     *                                    contextInfo
     * @throws OperationFailedException   unable to complete request
     * @throws PermissionDeniedException  authorization failure
     * @throws UnsupportedActionException CLU set is dynamically determined
     */
    public StatusInfo addClusToCluSet(@WebParam(name = "cluSetIds") List<String> cluSetIds, @WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /**
     * Remove a CLU from a CLU set
     *
     * @param cluId       identifier of CLU to remove from the CLU set
     * @param cluSetId    identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException      cluId, cluSetId not found
     * @throws InvalidParameterException  invalid contextInfo
     * @throws MissingParameterException  missing cluId, cluSetId or
     *                                    contextInfo
     * @throws OperationFailedException   unable to complete request
     * @throws PermissionDeniedException  authorization failure
     * @throws UnsupportedActionException CLU set is dynamically determined
     */
    public StatusInfo removeCluFromCluSet(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;
    
    /**
     * Search for Clus using free form search criteria.
     * 
     * @param criteria the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching Clus
     * @throws InvalidParameterException criteria or contextInfo is null
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluInfo>searchForClus(@WebParam(name = "criteria") QueryByCriteria criteria,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * Search for Clu Ids using free form search criteria
     * 
     * @param criteria  the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching Clu Ids
     * @throws InvalidParameterException criteria or contextInfo is null
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String>searchForCluIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    
    /**
     * Search for CluCluRelations using free form search criteria
     * 
     * @param criteria the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluCluRelations
     * @throws InvalidParameterException criteria or contextInfo is null
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluCluRelationInfo>searchForCluCluRelations(@WebParam(name = "criteria") QueryByCriteria criteria,
    @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * Search for CluCluRelation Ids using free form search criteria
     * 
     * @param criteria the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluCluRelation Ids
     * @throws InvalidParameterException criteria or contextInfo is null
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String>searchForCluCluRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
            
    
    /**
     * Search for CluLoRelations using free form search criteria.
     * 
     * @param criteria the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluLoRelations
     * @throws InvalidParameterException criteria or contextInfo is null
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluLoRelationInfo>searchForCluLoRelations(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * Search for CluLoRelation Ids using free form search criteria
     * 
     * @param criteria the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluLoRelation Ids
     * @throws InvalidParameterException criteria or contextInfo is null
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String>searchForCluLoRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * Search for CluPublications using free form search criteria 
     * 
     * @param criteria the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluPublications
     * @throws InvalidParameterException criteria or contextInfo is null
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluPublicationInfo>searchForCluPublications(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * Search for CluPublication Ids using free form search criteria 
     * 
     * @param criteria the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluPublication Ids
     * @throws InvalidParameterException criteria or contextInfo is null
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String>searchForCluPublicationIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * Search for CluResults using free form search criteria
     * 
     * @param criteria the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluResults
     * @throws InvalidParameterException criteria or contextInfo is null
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CluResultInfo>searchForCluResults(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * Search for CluResult Ids using free form search criteria
     * 
     * @param criteria the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluResult Ids
     * @throws InvalidParameterException criteria or contextInfo is null
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String>searchForCluResultIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}