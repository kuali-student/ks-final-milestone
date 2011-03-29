/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.common.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.TypeInfo;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.datadictionary.dto.DictionaryEntryInfo;

/**
 * Provides a read-only view of types and type type relations. This service
 * needs to be implemented by any KS service that is going to handle types
 * 
 * @author kamal
 */
@WebService(name = "TypeService", targetNamespace = "http://student.kuali.org/wsdl/types")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface TypeService {

 
    public TypeInfo getType(@WebParam(name="typeKey")String typeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    public List<TypeInfo> getTypesByRefObjectURI(@WebParam(name="refObjectURI") String refObjectURI) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    public List<TypeInfo> getAllowedTypesForType(@WebParam(name="ownerTypeKey") String ownerTypeKey, @WebParam(name="relatedRefObjectURI") String relatedRefObjectURI) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
}
