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

package org.kuali.student.r1.core.statement.naturallanguage.translators;

import java.util.Map;

import org.kuali.student.common.messagebuilder.MessageBuilder;
import org.kuali.student.common.messagebuilder.booleanmessage.MessageContainer;
import org.kuali.student.common.messagebuilder.impl.exceptions.MessageBuilderException;

/**
 * This class builds a message from a boolean expression in a specific language.
 */
@Deprecated
public class NaturalLanguageMessageBuilder {
    private Map<String, MessageBuilder> messageBuilderMap;

	/**
     * Constructs a new natural language message builder for a map of 
     * message builders. Each map entry is a message builder for 
     * a specific language.
	 * 
	 * @param messageBuilderMap Map of message builders
	 */
	public NaturalLanguageMessageBuilder(final Map<String, MessageBuilder> messageBuilderMap) {
		this.messageBuilderMap = messageBuilderMap;
	}
	
	/**
	 * Builds the message from the <code>booleanExpression</code>.
	 * 
	 * @param booleanExpression Boolean expression
	 * @param messageContainer List of messages
	 * @return Composed message from the boolean expression
	 */
	public String buildMessage(final String language, final String booleanExpression, final MessageContainer messageContainer) {
		MessageBuilder mb = this.messageBuilderMap.get(language);
		if(mb == null) {
			throw new MessageBuilderException("Message builder not found for language key '" + language +"'");
		}
		return mb.buildMessage(booleanExpression, messageContainer);
	}
}
