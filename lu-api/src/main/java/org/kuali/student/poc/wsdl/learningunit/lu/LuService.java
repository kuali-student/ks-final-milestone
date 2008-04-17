package org.kuali.student.poc.wsdl.learningunit.lu;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.poc.xsd.learningunit.lu.dto.LuTypeInfo;

@WebService(name = "LuService", targetNamespace = "http://student.kuali.org/poc/wsdl/learningunit/lu")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface LuService {
	@WebMethod
	public List<LuTypeInfo> findLuTypes();
}
