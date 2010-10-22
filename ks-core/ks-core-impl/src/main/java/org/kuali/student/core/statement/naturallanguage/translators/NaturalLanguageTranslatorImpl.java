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

package org.kuali.student.core.statement.naturallanguage.translators;

import org.apache.log4j.Logger;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.Statement;
import org.kuali.student.core.statement.naturallanguage.NaturalLanguageTranslator;

/**
 * This class translates requirement components and LU (learning unit) 
 * statements into a specific natural language and into a 
 * statement/requirement component tree.
 */
public class NaturalLanguageTranslatorImpl implements NaturalLanguageTranslator {

	private final static Logger logger = Logger.getLogger(NaturalLanguageTranslatorImpl.class);
	
	private ReqComponentTranslator reqComponentTranslator;
	private StatementTranslator statementTranslator;
	
	/**
	 * Constructs a new natural language translator in the 
	 * default language locale.
	 */
	public NaturalLanguageTranslatorImpl() {
	}

	/**
	 * Sets the requirement component translator.
	 * 
	 * @param reqComponentTranslator Requirement component translator
	 */
	public void setReqComponentTranslator(final ReqComponentTranslator reqComponentTranslator) {
		this.reqComponentTranslator = reqComponentTranslator;
	}

	/**
	 * Sets the statement translator.
	 * 
	 * @param statementTranslator Statement translator
	 */
	public void setStatementTranslator(final StatementTranslator statementTranslator) {
		this.statementTranslator = statementTranslator;
	}
	
	/**
	 * Translates a requirement component in the default language locale for a 
	 * specific natural language, usuage type (context) into natural language.
	 * 
	 * @param reqComponent Requirement component to be translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language requirement translation
	 * @throws DoesNotExistException Requirement component id does not exists
	 * @throws OperationFailedException
	 */
	public synchronized String translateReqComponent(final ReqComponent reqComponent, final String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		String nl = this.reqComponentTranslator.translate(reqComponent, nlUsageTypeKey);
		if(logger.isInfoEnabled()) {
			logger.info("ReqComponent translation="+nl);
		}
		return nl;
	}

	/**
	 * Translates a requirement component for a specific natural language, 
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
	public synchronized String translateReqComponent(final ReqComponent reqComponent, final String nlUsageTypeKey, final String language) throws DoesNotExistException, OperationFailedException {
		String nl = null;
		if(language == null) {
			nl = this.reqComponentTranslator.translate(reqComponent, nlUsageTypeKey);
		} else {
			nl = this.reqComponentTranslator.translate(reqComponent, nlUsageTypeKey, language);
		}
		if(logger.isInfoEnabled()) {
			logger.info("ReqComponent translation="+nl);
		}
		return nl;
	}
	
	/**
	 * Translates a statement for a specific natural language, 
	 * usuage type (context) into natural language.
	 * 
	 * @param statement Statement to be translated 
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language statement translation
	 * @throws DoesNotExistException CLU does not exists
	 * @throws OperationFailedException Translation fails
	 */
	public synchronized String translateStatement(final Statement statement, final String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		String nl = this.statementTranslator.translate(statement, nlUsageTypeKey);
		if(logger.isInfoEnabled()) {
			logger.info("Statement translation="+nl);
		}
		return nl;
	}

	/**
	 * Translates a statement for a specific natural language, 
	 * usuage type (context) and language locale into natural language.
	 * 
	 * @param statement Statement to be translated 
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @param language Translation language
	 * @return Natural language statement translation
	 * @throws DoesNotExistException CLU id does not exists
	 * @throws OperationFailedException Translation fails
	 */
	public synchronized String translateStatement(Statement statement, String nlUsageTypeKey, String language) throws DoesNotExistException, OperationFailedException {
		String nl = null;
		if(language == null) {
			nl = this.statementTranslator.translate(statement, nlUsageTypeKey);
		} else {
			nl = this.statementTranslator.translate(statement, nlUsageTypeKey, language);
		}
		if(logger.isInfoEnabled()) {
			logger.info("Statement translation="+nl);
		}
		return nl;
	}
}
