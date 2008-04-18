package org.kuali.student.poc.wsdl.learningunit.lupersonrelation;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "LuPersonRelationService", targetNamespace = "http://student.kuali.org/poc/wsdl/learningunit/lupersonrelation")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LuPersonRelationService {

}
