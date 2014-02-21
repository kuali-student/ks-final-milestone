package org.kuali.student.ap.test.mock;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
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
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluLoRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluPublicationInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetTreeViewInfo;
import org.kuali.student.r2.lum.clu.service.CluService;

import javax.jws.WebParam;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 10:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class CluServiceMockTest implements CluService {
    /**
     * Retrieves the list of delivery method types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of delivery method type information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TypeInfo> getDeliveryMethodTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information about a delivery method type
     *
     * @param deliveryMethodTypeKey Key of the Delivery Method Type
     * @param contextInfo           Context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return information about a Delivery Method Type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          deliveryMethodType not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing deliveryMethodType or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public TypeInfo getDeliveryMethodType(@WebParam(name = "deliveryMethodTypeKey") String deliveryMethodTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of instructional format types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of instructional format type information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TypeInfo> getInstructionalFormatTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information about a Instructional Format Type
     *
     * @param instructionalFormatTypeKey Key of the Instructional Format Type
     * @param contextInfo                Context information containing the
     *                                   principalId and locale information
     *                                   about the caller of service operation
     * @return information about a Instructional Format Type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          instructionalFormatTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing instructionalFormatTypeKey or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public TypeInfo getInstructionalFormatType(@WebParam(name = "instructionalFormatTypeKey") String instructionalFormatTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of LU types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of LU type information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TypeInfo> getLuTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information about a LU Type
     *
     * @param luTypeKey   Key of the LU Type
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return information about a LU Type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing luTypeKey or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public TypeInfo getLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of learning unit code types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of lu code type information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TypeInfo> getLuCodeTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information about a learning unit code type
     *
     * @param luCodeTypeKey Key of the learning unit code type
     * @param contextInfo   Context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return information about a learning unit code type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luCodeTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing luCodeTypeKey or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public TypeInfo getLuCodeType(@WebParam(name = "luCodeTypeKey") String luCodeTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the complete list of LU to LU relation types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of LU to LU relation type information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TypeInfo> getCluCluRelationTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the LU to LU relation type
     *
     * @param cluCluRelationTypeKey Key of the LU to LU Relation Type
     * @param contextInfo           Context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return LU to LU relation type information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluCluRelationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluCluRelationTypeKey or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public TypeInfo getLuLuRelationType(@WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luTypeKey, relatedLuTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing luTypeKey, relatedLuTypeKey or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getAllowedLuLuRelationTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "relatedLuTypeKey") String relatedLuTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of Learning Unit publication types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of Learning Unit publication type information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TypeInfo> getLuPublicationTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information about a publication type
     *
     * @param luPublicationTypeKey Key of the Learning Unit Publication Type
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return information about a Learning Unit Publication Type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luPublicationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing luPublicationTypeKey or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public TypeInfo getLuPublicationType(@WebParam(name = "luPublicationTypeKey") String luPublicationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information about a publication type
     *
     * @param luTypeKey   Key of the LU Type
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of LU publication types
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing luTypeKey or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getLuPublicationTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of types for clu result objects.
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of clu result type information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TypeInfo> getCluResultTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information about a publication type
     *
     * @param cluResultTypeKey Key of the Canonical Learning Unit Result Type
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return information about a Learning Unit Publication Type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluResultTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluResultTypeKey or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public TypeInfo getCluResultType(@WebParam(name = "cluResultTypeKey") String cluResultTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of clu result types which are allowed to be used in
     * conjunction with a particular lu type.
     *
     * @param luTypeKey   luTypeKey
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of clu result types
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing luTypeKey or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TypeInfo> getCluResultTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of result usage types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of result usage type information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TypeInfo> getResultUsageTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information about a Result Usage Type
     *
     * @param resultUsageTypeKey Key of the Result Usage Type
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return information about a Result Usage Type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultUsageTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultUsageTypeKey or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public TypeInfo getResultUsageType(@WebParam(name = "resultUsageTypeKey") String resultUsageTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of result usage types which are allowed to be used in
     * conjunction with an lu type.
     *
     * @param luTypeKey   luTypeKey
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of result usage types
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing luTypeKey or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getAllowedResultUsageTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of result component types which are allowed to be used
     * in conjunction with result usage type.
     *
     * @param resultUsageTypeKey resultUsageTypeKey
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return list of result component types
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultUsageTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultUsageTypeKey or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getAllowedResultComponentTypesForResultUsageType(@WebParam(name = "resultUsageTypeKey") String resultUsageTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the complete list of CLU to LO relation types
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of CLU to LO relation type information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TypeInfo> getCluLoRelationTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information for a specified CLU to LO relation type
     *
     * @param cluLoRelationTypeKey Key of the CLU to LO Relation Type
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return CLU to LO relation type information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluLoRelationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluLoRelationTypeKey or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public TypeInfo getCluLoRelationType(@WebParam(name = "cluLoRelationTypeKey") String cluLoRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of CLU LO relation types which are allowed to be used
     * in conjunction with an lu type.
     *
     * @param luTypeKey   luTypeKey
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of clu lo relation types
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing luTypeKey or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getAllowedCluLoRelationTypesForLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of CLU set types known by the service
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of CLU set type information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TypeInfo> getCluSetTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information about a specified CLU set type
     *
     * @param cluSetTypeKey Key of the CLU set type
     * @param contextInfo   Context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return information about a CLU set type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSetTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetTypeKey or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public TypeInfo getCluSetType(@WebParam(name = "cluSetTypeKey") String cluSetTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves core information about a CLU
     *
     * @param cluId       identifier of the CLU
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return information about a CLU
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CluInfo getClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information about CLUs from a list of Ids
     *
     * @param cluIds      List of CLU identifiers
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return information a list of CLUs
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          One or more cluIds not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluIds or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluInfo> getClusByIds(@WebParam(name = "cluIds") List<String> cluIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of CLUs for the specified LU Type and state
     *
     * @param luTypeKey   Type of the CLUs to retrieve
     * @param luState     State of the CLUs to retrieve.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of CLU information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luTypeKey or luState not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing luTypeKey, luState or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluInfo> getClusByLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "luState") String luState, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luType or luState not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing luType, luState or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getCluIdsByLuType(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "luState") String luState, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId, relatedCluId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId, relatedCluId or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getAllowedCluCluRelationTypesByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          relatedCluId, cluCLuRelationTypeKey not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing relatedCluId, cluCLuRelationTypeKey
     *          or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluInfo> getClusByRelatedCluAndRelationType(@WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "cluCLuRelationTypeKey") String cluCLuRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          relatedCluId or cluCluRelationTypeKey
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing relatedCluId, cluCluRelationTypeKey
     *          or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getCluIdsByRelatedCluAndRelationType(@WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId or cluCluRelationTypeKey not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId, cluCluRelationTypeKey or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluInfo> getRelatedClusByCluAndRelationType(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId, cluCluRelationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId, cluCluRelationTypeKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getRelatedCluIdsByCluAndRelationType(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the relationship information between CLUs for a particular
     * Relation instance
     *
     * @param cluCluRelationId identifier of the CLU to CLU relation
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return information on the relation between two CLUs
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluCluRelationId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluCluRelationId or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CluCluRelationInfo getCluCluRelation(@WebParam(name = "cluCluRelationId") String cluCluRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of relationship information for the specified CLU
     *
     * @param cluId       identifier of the parent or From CLU
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of CLU to CLU relation information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluCluRelationInfo> getCluCluRelationsByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of publication objects for a particular clu
     *
     * @param cluId       clu identifier
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of publication objects used by the specified clu
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluPublicationInfo> getCluPublicationsByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of publication objects of a particular Type
     *
     * @param luPublicationTypeKey luPublicationType identifier
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return list of CLU Publication objects using the specified type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luPublicationType not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing luPublicationTypeKey or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluPublicationInfo> getCluPublicationsByType(@WebParam(name = "luPublicationTypeKey") String luPublicationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves an LU publication object by its identifier
     *
     * @param cluPublicationId CLU publication identifier
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return CLU Publication information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluPublicationId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluPublicationId or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CluPublicationInfo getCluPublication(@WebParam(name = "cluPublicationId") String cluPublicationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information about a Clu Result
     *
     * @param cluResultId identifier of the Clu Result
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return information about a Clu Result
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluResultId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluResultId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CluResultInfo getCluResult(@WebParam(name = "cluResultId") String cluResultId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the cluResult for a particular clu
     *
     * @param cluId       clu identifier
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return result information for a clu
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluResultInfo> getCluResultByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of cluResults for the cluIds specified
     *
     * @param cluIds      list of clu identifier's
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of CluResults corresponding to the cluIds specified
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          one or more of the cludIds does not exist.
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluIds or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluResultInfo> getCluResultsByClus(@WebParam(name = "cluIds") List<String> cluIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultUsageType not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultUsageTypeKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultUsageTypeKey
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<String> getCluIdsByResultUsageType(@WebParam(name = "resultUsageTypeKey") String resultUsageTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of clu Ids which use a particular result component
     *
     * @param resultComponentId identifier of the result component
     * @param contextInfo       Context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return list of clu Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resultComponent not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resultComponentId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resultComponentId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<String> getCluIdsByResultComponent(@WebParam(name = "resultComponentId") String resultComponentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve information on a CLU LO Relation.
     *
     * @param cluLoRelationId Identifier of the CLU LO Relation
     * @param contextInfo     Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return The retrieved CLU LO Relation information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluLoRelationId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluLoRelationId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CluLoRelationInfo getCluLoRelation(@WebParam(name = "cluLoRelationId") String cluLoRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluLoRelationInfo> getCluLoRelationsByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of CLU identifiers associated with a given learning
     * objective identifier.
     *
     * @param loId        Identifier for the learning objective
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return List of CLU LO Relations
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          loId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing loId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluLoRelationInfo> getCluLoRelationsByLo(@WebParam(name = "loId") String loId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of Resource requirements for the specified CLU
     *
     * @param cluId       Unique identifier for the CLU
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return List of resource requirements
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getResourceRequirementsForClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CluSetInfo getCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve information on a CLU set and its sub clu set fully expanded.
     *
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved CLU set tree view information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CluSetTreeViewInfo getCluSetTreeView(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve information on CLU sets from a list of cluSet Ids.
     *
     * @param cluSetIds   List of identifiers of CLU sets
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of CLU set information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          One or more cluSets not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetIds or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluSetInfo> getCluSetsByIds(@WebParam(name = "cluSetIds") List<String> cluSetIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve the list of CLU Set Ids within a CLU Set
     *
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of CLU Set Ids within the specified CLU set
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSet not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getCluSetIdsFromCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Check if the given CluSet is dynamic
     *
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of CLU Set Ids within the specified CLU set
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public Boolean isCluSetDynamic(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluInfo> getClusFromCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getCluIdsFromCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the full list of CLUs in this CLU set or any cluset that is
     * included within that.
     *
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of information on the CLUs
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSet not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluInfo> getAllClusInCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of CLU Identifiers within a CLU Set or any cluset that
     * is included within that.
     *
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return The retrieved list of CLU Ids within the specified CLU set
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getAllCluIdsInCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Checks if a CLU is a member of a CLU set or any contained CLU set
     *
     * @param cluId       Identifier of the CLU to check
     * @param cluSetId    Identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return True if the CLU is a member of the CLU Set
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId, cluSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId, cluSetId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public Boolean isCluInCluSet(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, cluInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateClu(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluInfo") CluInfo cluInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new CLU
     *
     * @param luTypeKey   identifier of the LU Type for the CLU being created
     * @param cluInfo     information about the CLU being created
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the created CLU information
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          luTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing luTypeKey, cluInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     */
    @Override
    public CluInfo createClu(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "cluInfo") CluInfo cluInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing CLU
     *
     * @param cluId       identifier for the CLU to be updated
     * @param cluInfo     updated information about the CLU
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the updated CLU information
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId, cluInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          an optimistic locking failure or the
     *          action was attempted on an out of
     *          date version
     */
    @Override
    public CluInfo updateClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluInfo") CluInfo cluInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing CLU
     *
     * @param cluId       identifier for the CLU to be deleted
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return status of the operation
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException
     *          delete would leave orphaned
     *          objects or violate integrity
     *          constraints
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new CLU version based on the current clu
     *
     * @param cluId          identifier for the CLU to be versioned
     * @param versionComment comment for the current version
     * @param contextInfo    Context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return the new versioned CLU information
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId, versionComment or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     */
    @Override
    public CluInfo createNewCluVersion(@WebParam(name = "cluId") String cluId, @WebParam(name = "versionComment") String versionComment, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluVersionId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluVersionId, previousState,
     *          newState
     * @throws org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException
     *          a Clu with higher sequence number
     *          from the one provided is marked
     *          current
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo setCurrentCluVersion(@WebParam(name = "cluVersionId") String cluVersionId, @WebParam(name = "currentVersionStart") Date currentVersionStart, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId, luState or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          an optimistic locking failure or the
     *          action was attempted on an out of
     *          date version
     */
    @Override
    public CluInfo updateCluState(@WebParam(name = "cluId") String cluId, @WebParam(name = "luState") String luState, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey, cluId, relatedCluId
     *          or cluCluRelationTypeKey  not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluCluRelationInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, cluId,
     *          relatedCluId, cluCluRelationTypeKey or
     *          cluCluRelationInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateCluCluRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "cluCluRelationInfo") CluCluRelationInfo cluCluRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.CircularRelationshipException
     *          cluId equals relatedCluId
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId, relatedCluId, cluCluRelationTypeKey
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluCluRelationInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId, relatedCluId,
     *          cluCluRelationTypeKey, cluCluRelationInfo
     *          or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     */
    @Override
    public CluCluRelationInfo createCluCluRelation(@WebParam(name = "cluId") String cluId, @WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "cluCluRelationInfo") CluCluRelationInfo cluCluRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluCluRelation not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluCluRelationInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluCluRelationId,
     *          cluCluRelationInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          an optimistic locking failure or the
     *          action was attempted on an out of
     *          date version
     */
    @Override
    public CluCluRelationInfo updateCluCluRelation(@WebParam(name = "cluCluRelationId") String cluCluRelationId, @WebParam(name = "cluCluRelationInfo") CluCluRelationInfo cluCluRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes a relationship between two CLUs
     *
     * @param cluCluRelationId identifier of CLU to CLU relationship to delete
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluCluRelationId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluCluRelationId or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteCluCluRelation(@WebParam(name = "cluCluRelationId") String cluCluRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey, cluId or
     *          luPublicationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluPublicationInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, cluId,
     *          luPublicationTypeKey, cluPublicationInfo
     *          or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateCluPublication(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "luPublicationTypeKey") String luPublicationTypeKey, @WebParam(name = "cluPublicationInfo") CluPublicationInfo cluPublicationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId or luPublicationTypeKey not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluPublicationInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId, luPublicationTypeKey,
     *          cluPublicationInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     */
    @Override
    public CluPublicationInfo createCluPublication(@WebParam(name = "cluId") String cluId, @WebParam(name = "luPublicationTypeKey") String luPublicationTypeKey, @WebParam(name = "cluPublicationInfo") CluPublicationInfo cluPublicationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluPublicationId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluPublicationInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluPublicationId,
     *          cluPublicationInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out
     *          of date version.
     */
    @Override
    public CluPublicationInfo updateCluPublication(@WebParam(name = "cluPublicationId") String cluPublicationId, @WebParam(name = "cluPublicationInfo") CluPublicationInfo cluPublicationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing clu publication object
     *
     * @param cluPublicationId identifier for the clu publication object to be
     *                         deleted
     * @param contextInfo      Context information containing the principalId
     *                         and locale information about the caller of
     *                         service operation
     * @return status of the operation
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException
     *          delete would leave orphaned
     *          objects or violate integrity
     *          constraints
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluPublicationId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluPublicationId or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteCluPublication(@WebParam(name = "cluPublicationId") String cluPublicationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey, cluId or
     *          cluResultTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluResultInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, cluId,
     *          cluResultTypeKey, cluResultInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateCluResult(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "cluResultTypeKey") String cluResultTypeKey, @WebParam(name = "cluResultInfo") CluResultInfo cluResultInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId or resultUsageTypeKey not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluResultInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId, cluResultTypeKey,
     *          cluResultInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     */
    @Override
    public CluResultInfo createCluResult(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluResultTypeKey") String cluResultTypeKey, @WebParam(name = "cluResultInfo") CluResultInfo cluResultInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing clu result
     *
     * @param cluResultId   identifier for the clu result to be updated
     * @param cluResultInfo updated information about the clu result
     * @param contextInfo   Context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return the updated clu result information
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluResultId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluResultId, cluResultInfo
     *          or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          an optimistic locking failure or the
     *          action was attempted on an out of
     *          date version
     */
    @Override
    public CluResultInfo updateCluResult(@WebParam(name = "cluResultId") String cluResultId, @WebParam(name = "cluResultInfo") CluResultInfo cluResultInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing clu result
     *
     * @param cluResultId identifier for the clu result to be deleted
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return status of the operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluResultId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluResultId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException
     *          delete would leave orphaned
     *          objects or violate integrity
     *          constraints
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteCluResult(@WebParam(name = "cluResultId") String cluResultId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey, cluId, loId or
     *          cluLoRelationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluLoRelationInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, cluId, loId,
     *          cluLoRelationTypeKey, cluLoRelationInfo
     *          or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateCluLoRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "loId") String loId, @WebParam(name = "cluLoRelationTypeKey") String cluLoRelationTypeKey, @WebParam(name = "cluLoRelationInfo") CluLoRelationInfo cluLoRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          data validation error
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId, loId, cluLoRelationTypeKey
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluLoRelationInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId, loId, cluLoRelationTypeKey,
     *          cluLoRelationInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     */
    @Override
    public CluLoRelationInfo createCluLoRelation(@WebParam(name = "cluId") String cluId, @WebParam(name = "loId") String loId, @WebParam(name = "cluLoRelationTypeKey") String cluLoRelationTypeKey, @WebParam(name = "cluLoRelationInfo") CluLoRelationInfo cluLoRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          data validation error
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluLoRelationId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluLoRelationInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluLoRelationId, cluLoRelationInfo
     *          or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out
     *          of date version.
     */
    @Override
    public CluLoRelationInfo updateCluLoRelation(@WebParam(name = "cluLoRelationId") String cluLoRelationId, @WebParam(name = "cluLoRelationInfo") CluLoRelationInfo cluLoRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Removes a relationship between a learning objective and a Clu.
     *
     * @param cluLoRelationId CLU learning objective Relationship identifier
     * @param contextInfo     Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return Status of the delete operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluLoRelationId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluLoRelationId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteCluLoRelation(@WebParam(name = "cluLoRelationId") String cluLoRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          if the resource type has already been added to the clu
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resourceTypeKey or cluId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid resourceTypeKey or cluId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resourceTypeKey, cluId or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo addCluResourceRequirement(@WebParam(name = "resourceTypeKey") String resourceTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          resourceTypeKey or cluId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing resourceTypeKey, cluId or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo removeCluResourceRequirement(@WebParam(name = "resourceTypeKey") String resourceTypeKey, @WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey or cluSetTypeKey not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluSetInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, cluSetTypeKey,
     *          cluSetInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ValidationResultInfo> validateCluSet(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "cluSetTypeKey") String cluSetTypeKey, @WebParam(name = "cluSetInfo") CluSetInfo cluSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a CLU set.
     *
     * @param cluSetTypeKey type of the CLU set to be created
     * @param cluSetInfo    information required to create a CLU set
     * @param contextInfo   Context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return the created CLU set information
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          data validation error
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSetTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluSetInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetTypeKey, cluSetInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.UnsupportedActionException
     *          CLU set need to be static or dynamic
     *          but not both
     */
    @Override
    public CluSetInfo createCluSet(@WebParam(name = "cluSetTypeKey") String cluSetTypeKey, @WebParam(name = "cluSetInfo") CluSetInfo cluSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Update the information for a CLU set
     *
     * @param cluSetId    identifier of the CLU set to be updated
     * @param cluSetInfo  updated information about the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the updated CLU set information
     * @throws org.kuali.student.r2.common.exceptions.CircularRelationshipException
     *          added CluSetId cannot be added to
     *          the cluSetInfo
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          data validation error
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid cluSetInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetId, cluSetInfo or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.UnsupportedActionException
     *          CLU set need to be static or
     *          dynamic but not both
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          an optimistic locking failure or
     *          the action was attempted on an out
     *          of date version
     */
    @Override
    public CluSetInfo updateCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "cluSetInfo") CluSetInfo cluSetInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Delete a CLU set
     *
     * @param cluSetId    identifier of the CLU set to be deleted
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetId or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Adds one CLU set to another
     *
     * @param cluSetId      identifier of the host CLU set
     * @param addedCluSetId identifier of the CLU set to be added
     * @param contextInfo   Context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.CircularRelationshipException
     *          addedCluSetId cannot be added to
     *          the CluSetId
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSetId, addedCluSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetId, addedCluSetId or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.UnsupportedActionException
     *          CLU set is dynamically determined
     */
    @Override
    public StatusInfo addCluSetToCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "addedCluSetId") String addedCluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws CircularRelationshipException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.CircularRelationshipException
     *          addedCluSetIds cannot be added to
     *          the cluSetId
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSetId, one or more of cluSetIds
     *          in addedCluSetIds not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetId, addedCluSetIds or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.UnsupportedActionException
     *          CLU set is dynamically determined
     */
    @Override
    public StatusInfo addCluSetsToCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "addedCluSetIds") List<String> addedCluSetIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws CircularRelationshipException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Removes one CLU set from another
     *
     * @param cluSetId        identifier of the host CLU set
     * @param removedCluSetId identifier of the CLU set to be removed
     * @param contextInfo     Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSetId, removedCluSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetId, removedCluSetId or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.UnsupportedActionException
     *          CLU set is dynamically determined
     */
    @Override
    public StatusInfo removeCluSetFromCluSet(@WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "removedCluSetId") String removedCluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Add a CLU to a CLU set
     *
     * @param cluId       identifier of CLU to add to the CLU set
     * @param cluSetId    identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId, cluSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId, cluSetId or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.UnsupportedActionException
     *          CLU set is dynamically determined
     */
    @Override
    public StatusInfo addCluToCluSet(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluSetIds, cluSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluSetIds, cluSetId or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.UnsupportedActionException
     *          CLU set is dynamically determined
     */
    @Override
    public StatusInfo addClusToCluSet(@WebParam(name = "cluSetIds") List<String> cluSetIds, @WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Remove a CLU from a CLU set
     *
     * @param cluId       identifier of CLU to remove from the CLU set
     * @param cluSetId    identifier of the CLU set
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          cluId, cluSetId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing cluId, cluSetId or
     *          contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.UnsupportedActionException
     *          CLU set is dynamically determined
     */
    @Override
    public StatusInfo removeCluFromCluSet(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluSetId") String cluSetId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Search for Clus using free form search criteria.
     *
     * @param criteria    the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching Clus
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is null
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluInfo> searchForClus(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Search for Clu Ids using free form search criteria
     *
     * @param criteria    the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching Clu Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is null
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> searchForCluIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Search for CluCluRelations using free form search criteria
     *
     * @param criteria    the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluCluRelations
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is null
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluCluRelationInfo> searchForCluCluRelations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Search for CluCluRelation Ids using free form search criteria
     *
     * @param criteria    the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluCluRelation Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is null
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> searchForCluCluRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Search for CluLoRelations using free form search criteria.
     *
     * @param criteria    the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluLoRelations
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is null
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluLoRelationInfo> searchForCluLoRelations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Search for CluLoRelation Ids using free form search criteria
     *
     * @param criteria    the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluLoRelation Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is null
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> searchForCluLoRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Search for CluPublications using free form search criteria
     *
     * @param criteria    the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluPublications
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is null
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluPublicationInfo> searchForCluPublications(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Search for CluPublication Ids using free form search criteria
     *
     * @param criteria    the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluPublication Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is null
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> searchForCluPublicationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Search for CluResults using free form search criteria
     *
     * @param criteria    the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluResults
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is null
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CluResultInfo> searchForCluResults(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Search for CluResult Ids using free form search criteria
     *
     * @param criteria    the search criteria.
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the list of matching CluResult Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          criteria or contextInfo is null
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing criteria or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> searchForCluResultIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of search types known by this service.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of search type information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<TypeInfo> getSearchTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information about a particular search type.
     *
     * @param searchTypeKey identifier of the search type
     * @param contextInfo   information containing the principalId and locale
     *                      information about the caller of service operation
     * @return information on the search type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified searchTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          searchTypeKey or contextInfo is missing
     *          or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public TypeInfo getSearchType(@WebParam(name = "searchTypeKey") String searchTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Performs a search.
     *
     * @param searchRequestInfo the search request
     * @param contextInfo       information containing the principalId and locale
     *                          information about the caller of service operation
     * @return the results of the search
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          searchRequestInfo or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of versions associated with the objectId.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return a list of versions
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          refObjectUri or refObjectId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectUri, refObjectId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<VersionDisplayInfo> getVersions(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves first version associated with the objectId.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return first version
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          refObjectUri or refObjectId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectUri, refObjectId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public VersionDisplayInfo getFirstVersion(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves latest version associated with the objectId. This is not always
     * the current version.  A current version is what is being used by the
     * system right now, but there could be draft version created after the
     * current version.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return latest version
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified refObjectId and refObjectUri
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectUri, refObjectId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public VersionDisplayInfo getLatestVersion(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves current version associated with the objectId.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return current version
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified refObjectUri and refObjectId
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectUri, refObjectId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public VersionDisplayInfo getCurrentVersion(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the version associated with the objectId and the sequence
     * number.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param sequence     sequence number
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return version matching the sequence
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified refObjectUri, refObjectId or
     *          sequence not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          sequence or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectUri, refObjectId, sequence or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public VersionDisplayInfo getVersionBySequenceNumber(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "sequence") Long sequence, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the current version associated with the objectId on a given
     * date.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param date         date
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return current version on given date
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified refObjectId and refObjectUri
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          date or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectUri, refObjectId, date or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public VersionDisplayInfo getCurrentVersionOnDate(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "date") Date date, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the current version associated with the objectId in a given
     * date range
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param from         from date
     * @param to           to date
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return current version in given date range
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified refObjectId and refObjectUri
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          from, to or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectUri, refObjectId, from, to or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<VersionDisplayInfo> getVersionsInDateRange(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "from") Date from, @WebParam(name = "to") Date to, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
