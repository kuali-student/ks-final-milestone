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

package org.kuali.student.core.dictionary.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.kuali.student.core.dictionary.dto.ObjectStructure;

@WebService(name = "DictionaryService", targetNamespace = "http://student.kuali.org/wsdl/dictionary")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface DictionaryService {

    @WebMethod
    @RequestWrapper(className="org.kuali.student.core.dictionary.service.jaxws.GetObjectTypes", targetNamespace="http://student.kuali.org/wsdl/dictionary")    
    @ResponseWrapper(className="org.kuali.student.core.dictionary.service.jaxws.GetObjectTypesResponse", targetNamespace="http://student.kuali.org/wsdl/dictionary")
    public List<String> getObjectTypes();

    @WebMethod
    @RequestWrapper(className="org.kuali.student.core.dictionary.service.jaxws.GetObjectStructure", targetNamespace="http://student.kuali.org/wsdl/dictionary")    
    @ResponseWrapper(className="org.kuali.student.core.dictionary.service.jaxws.GetObjectStructureResponse", targetNamespace="http://student.kuali.org/wsdl/dictionary")
    public ObjectStructure getObjectStructure(@WebParam(name = "objectTypeKey") String objectTypeKey);

    @WebMethod
    @RequestWrapper(className="org.kuali.student.core.dictionary.service.jaxws.ValidateObject", targetNamespace="http://student.kuali.org/wsdl/dictionary")    
    @ResponseWrapper(className="org.kuali.student.core.dictionary.service.jaxws.ValidateObjectResponse", targetNamespace="http://student.kuali.org/wsdl/dictionary")
    public boolean validateObject(@WebParam(name = "objectTypeKey")String objectTypeKey, 
            @WebParam(name = "stateKey")String stateKey, 
            @WebParam(name = "info")String info);

    @WebMethod
    @RequestWrapper(className="org.kuali.student.core.dictionary.service.jaxws.ValidateStructureData", targetNamespace="http://student.kuali.org/wsdl/dictionary")    
    @ResponseWrapper(className="org.kuali.student.core.dictionary.service.jaxws.ValidateStructureDataResponse", targetNamespace="http://student.kuali.org/wsdl/dictionary")
    public boolean validateStructureData(@WebParam(name = "objectTypeKey")String objectTypeKey, 
            @WebParam(name = "stateKey")String stateKey, 
            @WebParam(name = "info")String info);
}
