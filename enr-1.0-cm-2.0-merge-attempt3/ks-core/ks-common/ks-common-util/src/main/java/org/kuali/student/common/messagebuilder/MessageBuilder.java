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

package org.kuali.student.common.messagebuilder;

import java.util.Map;

import org.kuali.student.common.messagebuilder.booleanmessage.MessageContainer;
import org.kuali.student.common.messagebuilder.booleanmessage.ast.BooleanFunctionResult;
import org.kuali.student.common.messagebuilder.impl.exceptions.MessageBuilderException;

public interface MessageBuilder {
    /**
     * <p>Builds and evaluates a boolean expression and returns the message and result 
     * of the expression.</p>
     * <p><b>Note:</b> Order of boolean operation: ANDs before ORs and operations 
     * inside parentheses before anything else.</p> 
	 * Example 1: 'A AND B OR C AND D' internally evaluates to '(A AND B) OR (C AND D)'
	 * <pre><code>booleanExpression</code> = "A*B+C*D"</pre>
     * Example 2: '(M1 AND M2) OR M3' 
     * <pre><code>booleanExpression</code> = "(M1*M2)+M3"</pre>
     * 
     * @param booleanRule Boolean expression
     * @param messageContainer Contains a list of messages
     * @return Boolean function result
     * @throws MessageBuilderException Errors building message
     */
    public BooleanFunctionResult build(String booleanRule, MessageContainer messageContainer);

    /**
     * <p>Builds and evaluates a boolean expression and returns the message and result 
     * of the expression.</p>
     * <p><b>Note:</b> Order of boolean operation: ANDs before ORs and operations 
     * inside parentheses before anything else.</p>
     * <p>if <code>messageContainer</code> contains any velocity templates 
     * with keys/tokens then <code>messageContextMap</code> must be set.</p>
	 * Example 1: 'A AND B OR C AND D' internally evaluates to '(A AND B) OR (C AND D)'
	 * <pre><code>booleanExpression</code> = "A*B+C*D"</pre>
     * Example 2: '(M1 AND M2) OR M3' 
     * <pre><code>booleanExpression</code> = "(M1*M2)+M3"</pre>
     * 
     * @param booleanRule Boolean expression
     * @param messageContainer Contains a list of messages
     * @param messageContextMap Message context template map
     * @return Boolean function result
     * @throws MessageBuilderException Errors building message
     */
    public BooleanFunctionResult build(String booleanRule, MessageContainer messageContainer, Map<String, Object> messageContextMap);

    /**
     * Builds a message from a list of message using a boolean expression. 
     * Example: (M1 AND M2) OR M3 -> (M1*M2)+M3
     * 
     * @param booleanRule Boolean expression
     * @param messageContainer Contains a list of messages
     * @return A message
     * @throws MessageBuilderException Errors building message
     */
    public String buildMessage(String booleanRule, MessageContainer messageContainer);
}
