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

package org.kuali.student.r1.core.statement.naturallanguage;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r1.core.statement.entity.ReqComponent;
import org.kuali.student.r1.core.statement.entity.Statement;

/**
 * This class translates requirement components and statements into 
 * natural language.
 */
@Deprecated
public interface NaturalLanguageTranslator {
	/**
	 * Translates a requirement component for a specific natural language 
	 * usuage type (context) into natural language.
	 * 
	 * @param reqComponent Requirement component to be translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language requirement translation
	 * @throws DoesNotExistException Requirement component id does not exists
	 * @throws OperationFailedException
	 */
	public String translateReqComponent(ReqComponent reqComponent, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;

	/**
	 * Translates a requirement component for a specific natural language 
	 * usuage type (context) and language locale (e.g. 'en' for English, 
	 * 'de' for German) into natural language.
	 * 
	 * @param reqComponent Requirement component to be translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @param language Translation language
	 * @return
	 * @throws DoesNotExistException
	 * @throws OperationFailedException
	 */
	public String translateReqComponent(ReqComponent reqComponent, String nlUsageTypeKey, String language) throws DoesNotExistException, OperationFailedException;

	/**
	 * Translates a statement in the default language locale for a specific 
	 * natural language usuage type (context) into natural language.
	 * 
	 * @param statement Statement to be translated 
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language statement translation
	 * @throws DoesNotExistException CLU id does not exists
	 * @throws OperationFailedException Translation fails
	 */
	public String translateStatement(Statement statement, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;

	/**
	 * Translates a statement for a specific natural language 
	 * usuage type (context) and language locale into natural language.
	 * 
	 * @param statement Statement to be translated 
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @param language Translation language
	 * @return Natural language statement translation
	 * @throws DoesNotExistException CLU id does not exists
	 * @throws OperationFailedException Translation fails
	 */
	public String translateStatement(Statement statement, String nlUsageTypeKey, String language) throws DoesNotExistException, OperationFailedException;
}
