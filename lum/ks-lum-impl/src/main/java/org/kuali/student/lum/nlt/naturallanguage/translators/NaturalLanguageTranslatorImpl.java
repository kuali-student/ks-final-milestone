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
package org.kuali.student.lum.nlt.naturallanguage.translators;

import java.util.Locale;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo;
import org.kuali.student.lum.nlt.naturallanguage.NaturalLanguageTranslator;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomLuStatementInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomReqComponentInfo;

/**
 * This class translates requirement components and LU (learning unit) 
 * statements into a specific natural language and into a 
 * statement/requirement component tree.
 */
public class NaturalLanguageTranslatorImpl implements NaturalLanguageTranslator {
	private String language;
	private ReqComponentTranslator reqComponentTranslator;
	private StatementTranslator statementTranslator;
	
	/**
	 * Constructs a new natural language translator in the 
	 * default language locale.
	 */
	public NaturalLanguageTranslatorImpl() {
		this.language = Locale.getDefault().getLanguage();
	}

	/**
	 * Sets the requirement component translator.
	 * 
	 * @param reqComponentTranslator Requirement component translator
	 */
	public void setReqComponentTranslator(final ReqComponentTranslator reqComponentTranslator) {
		this.reqComponentTranslator = reqComponentTranslator;
		setLanguage();
	}

	/**
	 * Sets the statement translator.
	 * 
	 * @param statementTranslator Statement translator
	 */
	public void setStatementTranslator(final StatementTranslator statementTranslator) {
		this.statementTranslator = statementTranslator;
		setLanguage();
	}
	
	/**
	 * Gets the translation language.
	 * 
	 * @return Language translation
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the language to translate to.
	 * 
	 * @param language Language translation
	 */
	public void setLanguage(final String language) {
		this.language = language;
		setLanguage();
	}

	/**
	 * Sets the language for the translators.
	 */
	private void setLanguage() {
		if(this.language != null) {
			if(this.reqComponentTranslator != null) {
				this.reqComponentTranslator.setLanguage(this.language);
			}
			if(this.statementTranslator != null) {
				this.statementTranslator.setLanguage(this.language);
			}
		}
	}

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
	public String translateReqComponent(final CustomReqComponentInfo reqComponent, final String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		return this.reqComponentTranslator.translate(reqComponent, nlUsageTypeKey);
	}
	
	/**
	 * Translates a statement for a specific natural language 
	 * usuage type (context) into natural language.
	 * 
	 * @param cluId Anchor CLU id
	 * @param statement Statement to be translated 
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language statement translation
	 * @throws DoesNotExistException CLU does not exists
	 * @throws OperationFailedException Translation fails
	 */
	public String translateStatement(final String cluId, final CustomLuStatementInfo statement, final String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		return this.statementTranslator.translate(cluId, statement, nlUsageTypeKey);
	}

	/**
	 * Translates a statement directly attached to a CLU for a specific natural 
	 * language usuage type (context) into natural language tree structure.
	 * 
	 * @param cluId Clu anchor
	 * @param statement Statement to be translated
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language root tree node
	 * @throws DoesNotExistException CLU or statement does not exist
	 * @throws OperationFailedException Translation fails
	 */
	public NLTranslationNodeInfo translateToTree(final String cluId, final CustomLuStatementInfo statement, final String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		return this.statementTranslator.translateToTree(cluId, statement, nlUsageTypeKey);
	}
}
