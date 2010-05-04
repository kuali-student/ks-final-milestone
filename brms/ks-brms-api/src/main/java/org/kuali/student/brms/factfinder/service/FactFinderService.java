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

package org.kuali.student.brms.factfinder.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.factfinder.dto.FactTypeInfo;
import org.kuali.student.brms.factfinder.runtime.FactFinder;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
/**
 * <b>IMPORTANT:</b> This service contract is currently under development. If you are planning to implement the Kuali Student System or parts thereof, <b>please do not consider this service to be final!</b> Consult this page for status before making any plans that rely on specific implementations of these services.</p>
 * 
 * <h3><a name="KSDOC-ServiceDescriptions-Description"></a>Description</h3>
 * 
 * <p>This service supports the retrieval of Facts used in the Rule Management Service.</p>
 * 
 * <h3><a name="KSDOC-ServiceDescriptions-Assumptions"></a>Assumptions</h3>
 * 
 * <ul>
 * 	<li>The creation and maintenance of Fact types/definitions is handled through configuration.</li>
 * </ul>
 * 
 * @author Kuali Student Team
 *
 */
@WebService(name = "FactFinderService", targetNamespace = "http://student.kuali.org/wsdl/brms/FactFinder") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface FactFinderService extends FactFinder {

    /* Setup */
    /**
     * Retrieves the list of Facts Types known by this service.
     * 
     * @return list of Agenda types
     * @throws OperationFailedException
     */
    @WebMethod
    public List<FactTypeInfo> getFactTypes() throws OperationFailedException;

    /**
     * Retrieves information about a type of Fact, listing all the keys required for its computation.
     * 
     * @return list of business rule types
     * @throws OperationFailedException
     */
    @WebMethod
    public FactTypeInfo getFactType(@WebParam(name="factTypeKey")String factTypeKey) throws OperationFailedException, DoesNotExistException;

    
    /**
     * 
     * Retrieves the result of computation of the Fact by the service.
     * 
     * @return
     * @throws OperationFailedException
     */
    @WebMethod
    public FactResultInfo getFact(@WebParam(name="factTypeKey")String factTypeKey, @WebParam(name="factStructure")FactStructureInfo factStructure) throws OperationFailedException, DoesNotExistException;    
}
