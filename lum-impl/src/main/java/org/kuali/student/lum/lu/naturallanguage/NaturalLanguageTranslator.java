package org.kuali.student.lum.lu.naturallanguage;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;

public interface NaturalLanguageTranslator {
	/**
	 * Translates a requirement component for a specific natural language 
	 * usuage type (context) into natural language.
	 * 
	 * @param reqComponentId Requirement component to be translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language requirement translation
	 * @throws DoesNotExistException Requirement component id does not exists
	 * @throws OperationFailedException Translation fails
	 */
	public String translateReqComponent(String reqComponentId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;

	/**
	 * Translates a statement for a specific natural language 
	 * usuage type (context) into natural language.
	 * 
	 * @param cluId Anchor CLU id
	 * @param statementId Statement id
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language statement translation
	 * @throws DoesNotExistException CLU id does not exists
	 * @throws OperationFailedException Translation fails
	 */
	public String translateStatement(String cluId, String statementId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;

}
