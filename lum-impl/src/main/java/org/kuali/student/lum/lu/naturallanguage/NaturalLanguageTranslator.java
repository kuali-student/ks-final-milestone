package org.kuali.student.lum.lu.naturallanguage;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;

public interface NaturalLanguageTranslator {
	public String translateReqComponent(String reqComponentId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;
	
	public String translateStatement(String statementId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;

}
