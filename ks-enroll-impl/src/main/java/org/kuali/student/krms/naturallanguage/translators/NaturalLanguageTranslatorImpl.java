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

package org.kuali.student.krms.naturallanguage.translators;

import org.apache.log4j.Logger;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.krms.naturallanguage.NaturalLanguageTranslator;

/**
 * This class translates requirement components and LU (learning unit) 
 * statements into a specific natural language and into a 
 * statement/requirement component tree.
 */
public class NaturalLanguageTranslatorImpl implements NaturalLanguageTranslator {

	private final static Logger logger = Logger.getLogger(NaturalLanguageTranslatorImpl.class);
	
	private PropositionTranslator propositionTranslator;

	/**
	 * Constructs a new natural language translator in the
	 * default language locale.
	 */
	public NaturalLanguageTranslatorImpl() {
	}

	/**
	 * Sets the requirement component translator.
	 *
	 * @param propositionTranslator Requirement component translator
	 */
	public void setPropositionTranslator(final PropositionTranslator propositionTranslator) {
		this.propositionTranslator = propositionTranslator;
	}


	/**
	 * Translates a requirement component in the default language locale for a
	 * specific natural language, usuage type (context) into natural language.
	 *
	 * @param proposition Requirement component to be translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language requirement translation
	 * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException Requirement component id does not exists
	 * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
	 */
	public synchronized String translateProposition(final PropositionDefinitionContract proposition, final String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		String nl = this.propositionTranslator.translate(proposition, nlUsageTypeKey);
		if(logger.isInfoEnabled()) {
			logger.info("Proposition translation="+nl);
		}
		return nl;
	}

	/**
	 * Translates a requirement component for a specific natural language,
	 * usuage type (context) and language locale (e.g. 'en' for English,
	 * 'de' for German) into natural language.
	 *
	 * @param proposition Requirement component to be translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @param language Translation language
	 * @return
	 * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
	 * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
	 */
	public synchronized String translateProposition(final PropositionDefinitionContract proposition, final String nlUsageTypeKey, final String language) throws DoesNotExistException, OperationFailedException {
		String nl = null;
		if(language == null) {
			nl = this.propositionTranslator.translate(proposition, nlUsageTypeKey);
		} else {
			nl = this.propositionTranslator.translate(proposition, nlUsageTypeKey, language);
		}
		if(logger.isInfoEnabled()) {
			logger.info("Proposition translation="+nl);
		}
		return nl;
	}


}
