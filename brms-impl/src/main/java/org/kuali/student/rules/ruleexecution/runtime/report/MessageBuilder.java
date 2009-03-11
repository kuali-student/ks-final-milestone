package org.kuali.student.rules.ruleexecution.runtime.report;

import java.util.Map;

import org.kuali.student.rules.internal.common.runtime.MessageContainer;
import org.kuali.student.rules.internal.common.runtime.ast.BooleanFunctionResult;
import org.kuali.student.rules.ruleexecution.runtime.report.ast.exceptions.MessageBuilderException;

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
