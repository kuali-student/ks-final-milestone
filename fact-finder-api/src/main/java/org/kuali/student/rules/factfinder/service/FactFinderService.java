package org.kuali.student.rules.factfinder.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.factfinder.dto.FactDataDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDisplayDTO;

@WebService(name = "FactFinderService", targetNamespace = "http://student.kuali.org/poc/wsdl/brms/factfinder")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface FactFinderService {

    /* Setup */
    /**
     * Retrieves the list of Agenda types
     * 
     * @return list of Agenda types
     * @throws OperationFailedException
     */
    @WebMethod
    public FactDataDTO findFact(FactStructureDTO factStructureDTO) throws OperationFailedException;

    /**
     * Retrieves the fact structure information associated with a fact
     * 
     * @return list of business rule types
     * @throws OperationFailedException
     */
    @WebMethod
    public FactStructureDTO fetchFactStructureInfo(String factStructureId) throws OperationFailedException;

    
    /**
     * 
     * This method returns the list of all the known facts in the system
     * 
     * @return
     * @throws OperationFailedException
     */
    @WebMethod
    public List<FactStructureDisplayDTO> fetchAllFactsDisplayInfo() throws OperationFailedException;    
}
