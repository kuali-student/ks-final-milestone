/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.datadictionary.service;

import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.DataDictionaryServiceConstants;

/**
 * Data Dictionary Service
 *
 * Provides a read-only view of meta data about the objects and fields on those objects
 * that are known to the service for which the data dictionary is included.
 *
 * The dictionary service is aligned with Rice's KRAD dictionary, as such the dictionary
 * structures should match up field for field.
 *
 * The Data Dictionary Service is an "included" service in that it is not expected to be a web service on it's
 * own but instead it's methods simply appear (are "included") on the service that includes it.
 *
 * Version: 1.0 (Dev)
 *
 * @author nwright
 */
@WebService(name = "DataDictionaryService", targetNamespace = DataDictionaryServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface DataDictionaryService {

    /**
     * Get the list of entry keys in this dictionary
     *
     * The list of keys is stored in the ref object URI strcture
     * E.g http://student.kuali.org/wsdl/luService/CluInfo will be the objectTypeURI for the CluInfo structure
     * The refObjectURI has three parts:<ol>
     * <li>http://student.kuali.org/wsdl -- which is fixed
     * <li>luService -- which should match the namespace of the service in which the object is defined
     * <li>CluInfo -- which should match the java class's simple name
     * </ol>
     *
     * @param context information about the user and locale
     * @return a list of all the known data dictionary entry keys in the ref object URI structure.
     * @throws OperationFailedException if could not complete the operation
     * @throws MissingParameterException if entryKey is null
     * @throws PermissionDeniedException if user does not have permission to call this method
     */
    public List<String> getDataDictionaryEntryKeys(@WebParam(name = "context") ContextInfo context)
            throws OperationFailedException,
            MissingParameterException,
            PermissionDeniedException;

    /**
     * Get the data dictionary entry for the specified entry key
     * 
     * @param entryKey that identifies the dictionary entry, this is done by specifying
     *        a refObjectURI
     * @param context information about the user and locale
     * @return the data dictionary entry key
     * @throws OperationFailedException if could not complete the operation
     * @throws MissingParameterException if entryKey is null
     * @throws DoesNotExistException if entryKey does not exist in the dictionary
     * @throws PermissionDeniedException if user does not have permission to call this method
     */
    public DictionaryEntryInfo getDataDictionaryEntry(@WebParam(name = "entryKey") String entryKey,
            @WebParam(name = "context") ContextInfo context)
            throws OperationFailedException,
            MissingParameterException,
            PermissionDeniedException,
            DoesNotExistException;
}
