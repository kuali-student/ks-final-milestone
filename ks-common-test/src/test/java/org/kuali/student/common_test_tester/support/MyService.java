package org.kuali.student.common_test_tester.support;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "MyService", targetNamespace = "http://student.kuali.org/poc/wsdl/test/my")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface MyService {
	@WebMethod
	public String saveString(String value);
	
	@WebMethod
	public String findStringId(String id);
	
	@WebMethod
	public boolean updateValue(String id, String value);

    @WebMethod
    public String findValueFromValue(String value);

    @WebMethod
    public String echo(String string);
    
}
