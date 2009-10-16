/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.nlt.naturallanguage;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomLuStatementInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomReqComponentInfo;

public interface NaturalLanguageTranslator {

	/**
	 * Sets the language translation.
	 * 
	 * @param language Language translation
	 */
	public void setLanguage(String language);
	
	/**
	 * Gets the translation language.
	 * 
	 * @return Language translation
	 */
	public String getLanguage();

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
	public String translateReqComponent(CustomReqComponentInfo reqComponent, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;

	/**
	 * Translates a statement for a specific natural language 
	 * usuage type (context) into natural language.
	 * 
	 * @param cluId Anchor CLU id
	 * @param statement Statement 
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language statement translation
	 * @throws DoesNotExistException CLU id does not exists
	 * @throws OperationFailedException
	 */
	public String translateStatement(String cluId, CustomLuStatementInfo statement, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;

	/**
	 * Translates a statement for a specific natural language 
	 * usuage type (context) into natural language tree structure.
	 * 
	 * @param cluId Clu anchor
	 * @param statement Statement to be translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language root tree node
	 * @throws DoesNotExistException CLU or statement does not exist
	 * @throws OperationFailedException Translation fails
	 */
	public NLTranslationNodeInfo translateToTree(String cluId, CustomLuStatementInfo statement, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException;
}
