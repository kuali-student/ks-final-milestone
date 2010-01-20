package org.kuali.student.core.statement.service.impl;

import javax.jws.WebService;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.statement.service.StatementService;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.core.statement.service.StatementService", serviceName = "StatementService", portName = "StatementService", targetNamespace = "http://student.kuali.org/wsdl/statement")
@Transactional(rollbackFor={Throwable.class})
public class StatementServiceImpl implements StatementService {

	public String getNaturalLanguageForLuStatement(String luStatementId, String nlUsageTypeKey, String language) 
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return null;
	}

	public String getNaturalLanguageForReqComponent(String reqComponentId, String nlUsageTypeKey, String language) 
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return null;
	}

}