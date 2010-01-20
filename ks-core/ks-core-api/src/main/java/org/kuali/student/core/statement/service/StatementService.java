package org.kuali.student.core.statement.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;

@WebService(name = "StatementService", targetNamespace = "http://student.kuali.org/wsdl/statement")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface StatementService {

	public String getNaturalLanguageForLuStatement(String luStatementId, String nlUsageTypeKey, String language)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

	public String getNaturalLanguageForReqComponent(String reqComponentId, String nlUsageTypeKey, String language) 
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
}
