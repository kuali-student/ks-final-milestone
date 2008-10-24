package org.kuali.student.enumeration.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "EnumerationService", targetNamespace = "http://student.kuali.org/wsdl/EnumerationService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface EnumerationService {
/*
    @WebMethod
    public List<FactTypeInfoDTO> getEnumerationKeys() throws OperationFailedException;

    @WebMethod
    public EnumeratedValue getEnumeration(EnumerationKey key) throws OperationFailedException;

    @WebMethod
    public List<EnumerationKey> getEnumerationKeys() throws OperationFailedException;

    @WebMethod
    public EnumeratedValue addEnumeratedValue(EnumerationKey key, EnumeratedValue value);

    @WebMethod
    public EnumeratedValue updateEnumeratedValue(EnumerationKey key, EnumeratedValue old, EnumeratedValue old);

    public Status removeEnumeratedValue(EnumerationKey key);
*/
}
