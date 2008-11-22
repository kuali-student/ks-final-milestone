package org.kuali.student.rules.factfinder.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;

@WebService(name = "FactFinderService", targetNamespace = "http://student.kuali.org/wsdl/brms/FactFinder") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface FactFinderService {

    /* Setup */
    /**
     * Retrieves the list of Facts Types known by this service.
     * 
     * @return list of Agenda types
     * @throws OperationFailedException
     */
    @WebMethod
    public List<FactTypeInfoDTO> findFactTypes() throws OperationFailedException;

    /**
     * Retrieves information about a type of Fact, listing all the keys required for its computation.
     * 
     * @return list of business rule types
     * @throws OperationFailedException
     */
    @WebMethod
    public FactTypeInfoDTO fetchFactType(@WebParam(name="factTypeKey")String factTypeKey) throws OperationFailedException, DoesNotExistException;

    
    /**
     * 
     * Retrieves the result of computation of the Fact by the service.
     * 
     * @return
     * @throws OperationFailedException
     */
    @WebMethod
    public FactResultDTO fetchFact(@WebParam(name="factTypeKey")String factTypeKey, @WebParam(name="factStructure")FactStructureDTO factStructure) throws OperationFailedException, DoesNotExistException;    
}
