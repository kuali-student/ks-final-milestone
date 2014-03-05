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

package org.kuali.student.common_test_tester.support;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@Deprecated
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
