package org.kuali.student.brms.ruleexecution.runtime.report.ast;

import java.util.Map;

import org.kuali.student.brms.internal.common.runtime.MessageContainer;
import org.kuali.student.brms.internal.common.runtime.ast.BooleanFunctionResult;
import org.kuali.student.brms.ruleexecution.runtime.SimpleExecutor;
import org.kuali.student.brms.ruleexecution.runtime.report.MessageBuilder;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.exceptions.MessageBuilderException;

/**
 * This <code>MessageBuilder</code> class builds a summary message from 
 * plain strings or Velocity template messages. Summary message is built 
 * from analyzing the outcome of a boolean expression.
 */
public class MessageBuilderImpl extends AbstractMessageBuilder implements MessageBuilder {

	/**
	 * Constructor.
	 * 
	 * @param executor A simple rule engine executor
	 */
	public MessageBuilderImpl(final SimpleExecutor executor) {
		super(executor);
	}

	/**
	 * Constructor.
	 * 
	 * @param executor A simple rule engine executor
     * @param andOperator String representation of boolean 'and'
     * @param orOperator String representation of boolean 'or'
	 */
	public MessageBuilderImpl(final SimpleExecutor executor, final String language, final BooleanOperators booleanOperators) {
		super(executor, language, booleanOperators);
	}
	
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
    public BooleanFunctionResult build(final String booleanRule, final MessageContainer messageContainer) {
        return super.build(booleanRule, messageContainer.getMessageMap());
    }

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
    public BooleanFunctionResult build(
    		final String booleanRule, 
    		final MessageContainer messageContainer, 
    		final Map<String, Object> messageContextMap) {
        return super.build(booleanRule, messageContainer.getMessageMap(), messageContextMap);
    }

    /**
     * Builds a message from a list of message using a boolean expression. 
     * Example: (M1 AND M2) OR M3 -> (M1*M2)+M3
     * 
     * @param booleanRule Boolean expression
     * @param messageContainer Contains a list of messages
     * @return A message
     * @throws MessageBuilderException Errors building message
     */
    public String buildMessage(final String booleanRule, final MessageContainer messageContainer) {
        return super.build(booleanRule, messageContainer.getMessageMap()).getMessage();
    }
}
