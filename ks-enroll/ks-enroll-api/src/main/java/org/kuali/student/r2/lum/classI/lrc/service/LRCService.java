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

package org.kuali.student.r2.lum.classI.lrc.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.VersionMismatchException;


import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.service.TypeService;
import org.kuali.student.r2.lum.classI.lrc.dto.ResultValueGroupInfo;
import org.kuali.student.r2.lum.classI.lrc.dto.ResultValueGroupTypeInfo;
import org.kuali.student.r2.lum.classI.lrc.dto.ResultValueInfo;

/**
 *
 * @Author KSContractMojo
 * @Author lindholm
 * @Since Tue Apr 21 14:09:46 PDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/Learning+Result+Catalog+Service">LrcService</>
 *
 */
@WebService(name = "LrcService", targetNamespace = "http://student.kuali.org/wsdl/lrc")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LRCService extends DataDictionaryService, TypeService, StateService {
    /**
     * Retrieves information on all result types.
     * @return list of result type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<ResultValueGroupTypeInfo> getResultTypes() throws OperationFailedException;

    /**
     * Retrieves information on a specific result type.
     * @param credentialTypeKey identifier for the credential type
     * @return information about a credential type
     * @throws DoesNotExistException resultTypeKey not found
     * @throws InvalidParameterException invalid resultTypeKey
     * @throws MissingParameterException missing resultTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public ResultValueGroupTypeInfo getResultType(@WebParam(name="resultTypeKey")String resultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of existing credentials by a list of identifiers.
     * @param credentialKeyList identifiers for credentials to be retrieved
     * @return details of the credentials for these ids
     * @throws DoesNotExistException credentialKey not found
     * @throws InvalidParameterException invalid credentialKeyList
     * @throws MissingParameterException invalid credentialKeyList
     * @throws OperationFailedException unable to complete request
	 */
    public ResultValueInfo getResult(@WebParam(name="resultKey")String resultKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of existing credentials by a list of identifiers.
     * @param credentialKeyList identifiers for credentials to be retrieved
     * @return details of the credentials for these ids
     * @throws DoesNotExistException credentialKey not found
     * @throws InvalidParameterException invalid credentialKeyList
     * @throws MissingParameterException invalid credentialKeyList
     * @throws OperationFailedException unable to complete request
	 */
    public List<ResultValueInfo> getResultsByKeyList(@WebParam(name="resultKeyList")List<String> resultKeyList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of credential identifiers for a specified credential type.
     * @param credentialTypeKey identifier for the credential type
     * @return list of credential identifiers
     * @throws DoesNotExistException credentialTypeKey not found
     * @throws InvalidParameterException invalid credentialTypeKey
     * @throws MissingParameterException missing credentialTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getResultKeysByResultType(@WebParam(name="resultTypeKey")String resultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

   
   
    /**
     * Retrieves information on a specific result component.
     * @param resultComponentId result component identifier
     * @return information about a result component
     * @throws DoesNotExistException resultComponentId not found
     * @throws InvalidParameterException invalid resultComponentId
     * @throws MissingParameterException missing resultComponentId
     * @throws OperationFailedException unable to complete request
	 */
    public ResultValueGroupInfo getResultComponent(@WebParam(name="resultComponentId")String resultComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of result component identifiers for a specified result component type.
     * @param resultComponentTypeKey identifier for the result component type
     * @return list of result component identifiers
     * @throws DoesNotExistException resultComponentTypeKey not found
     * @throws InvalidParameterException invalid resultComponentTypeKey
     * @throws MissingParameterException missing resultComponentTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getResultComponentIdsByResultComponentType(@WebParam(name="resultComponentTypeKey")String resultComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of result component identifiers for a specified result. The result component type is specified as well to provide an indication of the id space.
     * @param resultValueId identifier for the result value
     * @param resultComponentTypeKey identifier for the result component type
     * @return list of result component identifiers
     * @throws DoesNotExistException resultValueId, resultComponentTypeKey not found
     * @throws InvalidParameterException invalid resultValueId, resultComponentTypeKey
     * @throws MissingParameterException missing resultValueId, resultComponentTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getResultComponentIdsByResult(@WebParam(name="resultValueId")String resultValueId, @WebParam(name="resultComponentTypeKey")String resultComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Creates a new result component.
     * @param resultComponentTypeKey identifier of the result component type
     * @param resultComponentInfo information about the result component being created
     * @return create result component information
     * @throws AlreadyExistsException result component already exists
     * @throws DataValidationErrorException one or more values invalid for this operation
     * @throws DoesNotExistException resultComponentTypeKey not found
     * @throws InvalidParameterException invalid resultComponentTypeKey, resultComponentInfo
     * @throws MissingParameterException missing resultComponentTypeKey, resultComponentInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public ResultValueGroupInfo createResultComponent(@WebParam(name="resultComponentTypeKey")String resultComponentTypeKey, @WebParam(name="resultComponentInfo")ResultValueGroupInfo resultComponentInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing result component.
     * @param resultComponentId identifier of the result component to update
     * @param resultComponentInfo updated information about the result component
     * @return updated result component information
     * @throws DataValidationErrorException one or more values invalid for this operation
     * @throws DoesNotExistException resultComponentId not found
     * @throws InvalidParameterException invalid resultComponentId, resultComponentInfo
     * @throws MissingParameterException missing resultComponentId, resultComponentInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException action was attempted on an out of date version.
	 */
    public ResultValueGroupInfo updateResultComponent(@WebParam(name="resultComponentId")String resultComponentId, @WebParam(name="resultComponentInfo")ResultValueGroupInfo resultComponentInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes an existing result component.
     * @param resultComponentId identifier of the result component to update
     * @return status of the operation
     * @throws DoesNotExistException resultComponentId not found
     * @throws InvalidParameterException invalid resultComponentId
     * @throws MissingParameterException missing resultComponentId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteResultComponent(@WebParam(name="resultComponentId")String resultComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}
