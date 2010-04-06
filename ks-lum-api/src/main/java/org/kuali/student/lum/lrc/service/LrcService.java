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
package org.kuali.student.lum.lrc.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.service.SearchService;
import org.kuali.student.lum.lrc.dto.CredentialInfo;
import org.kuali.student.lum.lrc.dto.CredentialTypeInfo;
import org.kuali.student.lum.lrc.dto.CreditInfo;
import org.kuali.student.lum.lrc.dto.CreditTypeInfo;
import org.kuali.student.lum.lrc.dto.GradeInfo;
import org.kuali.student.lum.lrc.dto.GradeTypeInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.lum.lrc.dto.ScaleInfo;

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
public interface LrcService extends SearchService {
    /**
     * Retrieves information on all credential types.
     * @return list of credential type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<CredentialTypeInfo> getCredentialTypes() throws OperationFailedException;

    /**
     * Retrieves information on a specific credential type.
     * @param credentialTypeKey identifier for the credential type
     * @return information about a credential type
     * @throws DoesNotExistException credentialTypeKey not found
     * @throws InvalidParameterException invalid credentialTypeKey
     * @throws MissingParameterException missing credentialTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public CredentialTypeInfo getCredentialType(@WebParam(name="credentialTypeKey")String credentialTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves information on all credit types.
     * @return list of credit type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<CreditTypeInfo> getCreditTypes() throws OperationFailedException;

    /**
     * Retrieves information on a specific credit type.
     * @param creditTypeKey identifier for the credit type
     * @return information about a credit type
     * @throws DoesNotExistException creditTypeKey not found
     * @throws InvalidParameterException invalid creditTypeKey
     * @throws MissingParameterException missing creditTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public CreditTypeInfo getCreditType(@WebParam(name="creditTypeKey")String creditTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves information on all grade types.
     * @return list of grade type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<GradeTypeInfo> getGradeTypes() throws OperationFailedException;

    /**
     * Retrieves information on a specific grade type.
     * @param gradeTypeKey identifier for the grade type
     * @return information about a grade type
     * @throws DoesNotExistException gradeTypeKey not found
     * @throws InvalidParameterException invalid gradeTypeKey
     * @throws MissingParameterException missing gradeTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public GradeTypeInfo getGradeType(@WebParam(name="gradeTypeKey")String gradeTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves information on all result component types.
     * @return list of result component type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<ResultComponentTypeInfo> getResultComponentTypes() throws OperationFailedException;

    /**
     * Retrieves information on a specific result component type.
     * @param resultComponentTypeKey result component type key
     * @return information about a result component type
     * @throws DoesNotExistException resultComponentTypeKey not found
     * @throws InvalidParameterException invalid resultComponentTypeKey
     * @throws MissingParameterException missing resultComponentTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public ResultComponentTypeInfo getResultComponentType(@WebParam(name="resultComponentTypeKey")String resultComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves information on a specific credential by its identifier.
     * @param credentialKey credential identifier
     * @return information about a credential
     * @throws DoesNotExistException credentialKey not found
     * @throws InvalidParameterException invalid credentialKey
     * @throws MissingParameterException missing credentialKey
     * @throws OperationFailedException unable to complete request
	 */
    public CredentialInfo getCredential(@WebParam(name="credentialKey")String credentialKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of existing credentials by a list of identifiers.
     * @param credentialKeyList identifiers for credentials to be retrieved
     * @return details of the credentials for these ids
     * @throws DoesNotExistException credentialKey not found
     * @throws InvalidParameterException invalid credentialKeyList
     * @throws MissingParameterException invalid credentialKeyList
     * @throws OperationFailedException unable to complete request
	 */
    public List<CredentialInfo> getCredentialsByKeyList(@WebParam(name="credentialKeyList")List<String> credentialKeyList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of credential identifiers for a specified credential type.
     * @param credentialTypeKey identifier for the credential type
     * @return list of credential identifiers
     * @throws DoesNotExistException credentialTypeKey not found
     * @throws InvalidParameterException invalid credentialTypeKey
     * @throws MissingParameterException missing credentialTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getCredentialKeysByCredentialType(@WebParam(name="credentialTypeKey")String credentialTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves information on a specific credit value by its identifier.
     * @param creditKey credit identifier
     * @return information about a credit value
     * @throws DoesNotExistException creditKey not found
     * @throws InvalidParameterException invalid creditKey
     * @throws MissingParameterException missing creditKey
     * @throws OperationFailedException unable to complete request
	 */
    public CreditInfo getCredit(@WebParam(name="creditKey")String creditKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of existing credit values by a list of identifiers.
     * @param creditKeyList identifiers for credit values to be retrieved
     * @return details of the credit values for these ids
     * @throws DoesNotExistException creditKey not found
     * @throws InvalidParameterException invalid creditKeyList
     * @throws MissingParameterException invalid creditKeyList
     * @throws OperationFailedException unable to complete request
	 */
    public List<CreditInfo> getCreditsByKeyList(@WebParam(name="creditKeyList")List<String> creditKeyList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of credit identifiers for a specified credit type.
     * @param creditTypeKey identifier for the credit type
     * @return list of credit identifiers
     * @throws DoesNotExistException creditTypeKey not found
     * @throws InvalidParameterException invalid creditTypeKey
     * @throws MissingParameterException missing creditTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getCreditKeysByCreditType(@WebParam(name="creditTypeKey")String creditTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves information on a specific scale by its identifier.
     * @param scaleKey scale identifier
     * @return information about a scale
     * @throws DoesNotExistException scaleKey not found
     * @throws InvalidParameterException invalid scaleKey
     * @throws MissingParameterException missing scaleKey
     * @throws OperationFailedException unable to complete request
	 */
    public ScaleInfo getScale(@WebParam(name="scaleKey")String scaleKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves information on a specific grade value by its identifier.
     * @param gradeKey grade value identifier
     * @return information about a grade value
     * @throws DoesNotExistException gradeKey not found
     * @throws InvalidParameterException invalid gradeKey
     * @throws MissingParameterException missing gradeKey
     * @throws OperationFailedException unable to complete request
	 */
    public GradeInfo getGrade(@WebParam(name="gradeKey")String gradeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of existing grade values by a list of identifiers.
     * @param gradeKeyList identifiers for grade values to be retrieved
     * @return details of the grade values for these ids
     * @throws DoesNotExistException gradeKey not found
     * @throws InvalidParameterException invalid gradeKeyList
     * @throws MissingParameterException invalid gradeKeyList
     * @throws OperationFailedException unable to complete request
	 */
    public List<GradeInfo> getGradesByKeyList(@WebParam(name="gradeKeyList")List<String> gradeKeyList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of grade identifiers for a specified grade type.
     * @param gradeTypeKey identifier for the grade type
     * @return list of grade identifiers
     * @throws DoesNotExistException gradeTypeKey not found
     * @throws InvalidParameterException invalid gradeTypeKey
     * @throws MissingParameterException missing gradeTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getGradeKeysByGradeType(@WebParam(name="gradeTypeKey")String gradeTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of existing grade values for a particular scale.
     * @param scale identifier for the scale
     * @return details of the grade values for the specified scale
     * @throws DoesNotExistException scaleKey not found
     * @throws InvalidParameterException invalid scaleKey
     * @throws MissingParameterException invalid scaleKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<GradeInfo> getGradesByScale(@WebParam(name="scale")String scale) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Translates a grade value in one scale to equivalent value(s) in another scale. This may return a single grade value, multiple grade values, or no grade values (in the case of no equivalent values).
     * @param gradeKey identifier of the first grade
     * @param scaleKey scale of the first grade
     * @param translateScaleKey scale of the second grade
     * @return equivalent grade values for the first grade value in the specified scale
     * @throws InvalidParameterException invalid gradeKey, scaleKey, or translateScaleKey
     * @throws MissingParameterException missing gradeKey, scaleKey, or translateScaleKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<GradeInfo> translateGrade(@WebParam(name="gradeKey")String gradeKey, @WebParam(name="scaleKey")String scaleKey, @WebParam(name="translateScaleKey")String translateScaleKey) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Compares a grade value in one scale to a grade value in another scale.
     * @param gradeKey identifier of the first grade value
     * @param scaleKey scale of the first grade value
     * @param compareGradeKey identifier of the second grade value
     * @param compareScaleKey scale of the second grade value
     * @return status of the comparison (greater than, less than, equals, not applicable, etc.)
     * @throws InvalidParameterException invalid gradeKey, scaleKey, compareGradeKey, or compareScaleKey
     * @throws MissingParameterException missing gradeKey, scaleKey, compareGradeKey, or compareScaleKey
     * @throws OperationFailedException unable to complete request
	 */
    public String compareGrades(@WebParam(name="gradeKey")String gradeKey, @WebParam(name="scaleKey")String scaleKey, @WebParam(name="compareGradeKey")String compareGradeKey, @WebParam(name="compareScaleKey")String compareScaleKey) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves information on a specific result component.
     * @param resultComponentId result component identifier
     * @return information about a result component
     * @throws DoesNotExistException resultComponentId not found
     * @throws InvalidParameterException invalid resultComponentId
     * @throws MissingParameterException missing resultComponentId
     * @throws OperationFailedException unable to complete request
	 */
    public ResultComponentInfo getResultComponent(@WebParam(name="resultComponentId")String resultComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    public ResultComponentInfo createResultComponent(@WebParam(name="resultComponentTypeKey")String resultComponentTypeKey, @WebParam(name="resultComponentInfo")ResultComponentInfo resultComponentInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public ResultComponentInfo updateResultComponent(@WebParam(name="resultComponentId")String resultComponentId, @WebParam(name="resultComponentInfo")ResultComponentInfo resultComponentInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

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
