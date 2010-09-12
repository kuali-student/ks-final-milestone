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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.kuali.student.common.messagebuilder.MessageBuilder;
import org.kuali.student.common.messagebuilder.booleanmessage.MessageContainer;
import org.kuali.student.common.messagebuilder.impl.BooleanOperators;
import org.kuali.student.common.messagebuilder.impl.MessageBuilderImpl;

/**
 * This class builds a message from a boolean expression in a specific language.
 */
public class NaturalLanguageMessageBuilder {
    private MessageBuilder messageBuilder;
    private String language;
    private BooleanOperators booleanOperators;
    private Map<String, BooleanOperators> booleanOperatorsLanguageMap;
    private final Map<String, MessageBuilder> messageBuilderMap = new HashMap<String, MessageBuilder>();

    /**
     * Constructs a new natural language message builder in the default
     * language locale with default 'and' and 'or' boolean operators.
     * 
     * @param executor Rule engine executor
     */
	public NaturalLanguageMessageBuilder() {
		this.language = Locale.getDefault().getLanguage();
		this.booleanOperators = new BooleanOperators("and", "or");
		setup();
	}

	/**
     * Constructs a new natural language message builder in a specific language.
	 * 
	 * @param executor Rule engine executor
	 * @param language Translation language
	 * @param booleanOperators Boolean operators
	 */
	public NaturalLanguageMessageBuilder(final String language, final BooleanOperators booleanOperators) {
		this.language = language;
		this.booleanOperators = booleanOperators;
		setup();
	}

	/**
     * Constructs a new natural language message builder in a specific language
     * with a boolean operator language map.
	 * 
	 * @param executor Rule engine executor
	 * @param language Translation language
	 * @param booleanOperatorsLanguageMap boolean operator language map
	 */
	public NaturalLanguageMessageBuilder(final String language, final Map<String, BooleanOperators> booleanOperatorsLanguageMap) {
		this.language = language;
		this.booleanOperatorsLanguageMap = booleanOperatorsLanguageMap;
		setup();
	}

	/**
	 * Gets the translation language.
	 * 
	 * @return Boolean operator translation language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the translation language.
	 * 
	 * @param language Boolean operator translation language
	 */
	public void setLanguage(final String language) {
		this.language = language;
		setup();
	}

	/**
	 * Builds the message from the <code>booleanExpression</code>.
	 * 
	 * @param booleanExpression Boolean expression
	 * @param messageContainer List of messages
	 * @return Composed message from the boolean expression
	 */
	public String buildMessage(final String booleanExpression, final MessageContainer messageContainer) {
		return this.messageBuilder.buildMessage(booleanExpression, messageContainer);
	}

	/**
	 * Setup message builder.
	 */
	private void setup() {
		if(this.messageBuilderMap.containsKey(this.language)) {
			this.messageBuilder = this.messageBuilderMap.get(this.language);
		} else {
			this.messageBuilder = createMessageBuilder();
			this.messageBuilderMap.put(this.language, this.messageBuilder);
		}
	}

	/**
	 * Creates message builder.
	 */
	private MessageBuilder createMessageBuilder() {
		if(this.booleanOperators != null) {
			return new MessageBuilderImpl(this.language, this.booleanOperators);
		} else {
			if(this.booleanOperatorsLanguageMap == null) {
				throw new RuntimeException("Boolean operators language map not found. Language key: " + this.language);
			}
			BooleanOperators booleanOperators = this.booleanOperatorsLanguageMap.get(this.language);
			if(booleanOperators == null) {
				throw new RuntimeException("Boolean operators not found for language key: " + this.language);
			}
			return new MessageBuilderImpl(this.language, booleanOperators);
		}
	}
}
