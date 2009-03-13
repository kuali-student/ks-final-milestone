package org.kuali.student.rules.ruleexecution.runtime.report.ast;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.exception.VelocityException;
import org.kuali.student.rules.internal.common.runtime.BooleanMessage;
import org.kuali.student.rules.internal.common.runtime.ast.BinaryMessageTree;
import org.kuali.student.rules.internal.common.runtime.ast.BooleanFunction;
import org.kuali.student.rules.internal.common.runtime.ast.BooleanFunctionResult;
import org.kuali.student.rules.internal.common.runtime.ast.BooleanMessageImpl;
import org.kuali.student.rules.internal.common.runtime.ast.BooleanNode;
import org.kuali.student.rules.internal.common.runtime.exceptions.BooleanFunctionException;
import org.kuali.student.rules.internal.common.utils.VelocityTemplateEngine;
import org.kuali.student.rules.ruleexecution.runtime.SimpleExecutor;
import org.kuali.student.rules.ruleexecution.runtime.report.ast.exceptions.MessageBuilderException;

/**
 * This <code>AbstractMessageBuilder</code> class builds a summary message 
 * from plain strings or Velocity template messages. Summary message is built 
 * from analyzing the outcome of a boolean expression.
 */
public abstract class AbstractMessageBuilder {
    private final static String SUCCESS_MESSAGE_LOGGER = "org.kuali.student.rules.runtime.ast.success";
    private final static String FAILURE_MESSAGE_LOGGER = "org.kuali.student.rules.runtime.ast.failure";
    private final static String SUCCESS_MESSAGE_LOGGER_DRL = "/drools/drls/org/kuali/student/rules/ruleexecution/runtime/report/ast/SuccessMessageLogger.drl";
    private final static String FAILURE_MESSAGE_LOGGER_DRL = "/drools/drls/org/kuali/student/rules/ruleexecution/runtime/report/ast/FailureMessageLogger.drl";

    private final VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();

    private String booleanExpression;
    private Map<String, ? extends BooleanMessage> messageMap;
    private Map<String, Object> messageContextMap;
    
    private SimpleExecutor executor;

    public AbstractMessageBuilder(SimpleExecutor executor) {
        this.executor = executor;
    	setup();
    }

	/**
     * <p>Builds and evaluates a boolean expression and returns the message and result 
     * of the expression. Messages in the <code>messageMap</code> can 
     * also contain VTL (Velocity Template Language) but without any VTL keys</p>
     * <p><b>Note:</b> Order of boolean operation: ANDs before ORs and operations 
     * inside parentheses before anything else.</p> 
	 * Example 1: 'A AND B OR C AND D' internally evaluates to '(A AND B) OR (C AND D)'
	 * <pre><code>booleanExpression</code> = "A*B+C*D"</pre>
     * Example 2: '(M1 AND M2) OR M3' 
     * <pre><code>booleanExpression</code> = "(M1*M2)+M3"</pre>
     * 
     * @param booleanExpression Boolean expression
     * @param messageMap Contains a map of messages (or VTL)
     * @return Boolean function result
     */
    public BooleanFunctionResult build(
    		final String booleanExpression, 
    		final Map<String, ? extends BooleanMessage> messageMap) {
    	this.booleanExpression = booleanExpression;
    	this.messageMap = messageMap;

    	return build();
    }

    /**
     * <p>Builds and evaluates a boolean expression and returns the message and result 
     * of the expression. Messages in the <code>messageMap</code> can 
     * also contain VTL (Velocity Template Language). 
     * <code>messageContextMap</code> contains Velocity key/value entries 
     * referenced in the <code>messageMap</code>.</p>
     * <p><b>Note:</b> Order of boolean operation: ANDs before ORs and operations 
     * inside parentheses before anything else.</p> 
	 * Example 1: 'A AND B OR C AND D' internally evaluates to '(A AND B) OR (C AND D)'
	 * <pre><code>booleanExpression</code> = "A*B+C*D"</pre>
     * Example 2: '(M1 AND M2) OR M3' 
     * <pre><code>booleanExpression</code> = "(M1*M2)+M3"</pre>
     * 
     * 
     * @param booleanExpression Boolean expression
     * @param messageMap Contains a map of messages (or VTL)
     * @param messageContextMap Message contact map for Velocity Template Engine
     * @return Boolean function result
     * @throws MessageBuilderException Errors building message
     */
    public BooleanFunctionResult build(
    		final String booleanExpression, 
    		final Map<String, ? extends BooleanMessage> messageMap, 
    		final Map<String, Object> messageContextMap) {
    	this.booleanExpression = booleanExpression;
    	this.messageMap = messageMap;
    	this.messageContextMap = messageContextMap;

    	return build();
    }
    
    public BooleanFunctionResult build() {
    	BinaryMessageTree astTree = null;

        try {
            // set the functionString and Maps from the proposition container
        	Map<String, BooleanMessage> nodeMessageMap = buildMessageMap();

            // go parse function in buildTree
            astTree = new BinaryMessageTree(nodeMessageMap);
            BooleanNode root = astTree.buildTree(this.booleanExpression);
            astTree.traverseTreePostOrder(root, null);

            List<BooleanNode> treeNodes = astTree.getAllNodes();
            this.executor.execute(treeNodes);
        } catch(VelocityException e) {
            throw new MessageBuilderException("Building Velocity message failed: " + e.getMessage(), e);
    	} catch (BooleanFunctionException e) {
            throw new MessageBuilderException("Building message failed: " + e.getMessage(), e);
        }

        // This is the final rule report message summary
        String message = astTree.getRoot().getNodeMessage();
        Boolean result = astTree.getRoot().getValue();
        return new BooleanFunctionResult(this.booleanExpression, result, message);
    }

    /**
     * Builds message map. Also builds message map using velocity templates.
     * 
     * @return Boolean message map
     */
    private Map<String, BooleanMessage> buildMessageMap() {
    	Map<String, BooleanMessage> nodeMessageMap = new HashMap<String, BooleanMessage>();

        if (this.booleanExpression == null || this.booleanExpression.isEmpty()) {
        	throw new MessageBuilderException("Boolean expression is null.");
        }
        
        BooleanFunction func = new BooleanFunction(this.booleanExpression);
        List<String> funcVars = func.getVariables();

        for (String id : funcVars) {
        	BooleanMessage booleanMessage = buildMessage(this.messageMap.get(id));
            nodeMessageMap.put(id, booleanMessage);
        }
        
        return nodeMessageMap;
    }

    /**
     * Builds a failure/success message using the Velocity template engine.
     * 
     * @param message Boolean failure/success message
     * @return
     */
    private BooleanMessage buildMessage(BooleanMessage message) {
		String msg = message.getMessage();

		if(msg != null) {
    		msg = this.templateEngine.evaluate(this.messageContextMap, msg);
		}

    	return new BooleanMessageImpl(message.getMessageId(), message.isSuccesful(), msg);
    }

    /**
     * Setup default rule sets
     */
    private void setup() {
        Reader source1 = new InputStreamReader(MessageBuilderImpl.class.getResourceAsStream(SUCCESS_MESSAGE_LOGGER_DRL));
        this.executor.addRuleSet(SUCCESS_MESSAGE_LOGGER, source1);
        Reader source2 = new InputStreamReader(MessageBuilderImpl.class.getResourceAsStream(FAILURE_MESSAGE_LOGGER_DRL));
        this.executor.addRuleSet(FAILURE_MESSAGE_LOGGER, source2);
    }
}
