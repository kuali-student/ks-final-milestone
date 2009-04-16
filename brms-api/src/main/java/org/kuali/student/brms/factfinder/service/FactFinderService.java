package org.kuali.student.brms.factfinder.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.factfinder.dto.FactTypeInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;

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
