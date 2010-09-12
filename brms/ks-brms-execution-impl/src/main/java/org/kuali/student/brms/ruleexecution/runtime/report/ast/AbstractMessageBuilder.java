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

package org.kuali.student.brms.ruleexecution.runtime.report.ast;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.velocity.exception.VelocityException;
import org.kuali.student.brms.internal.common.runtime.BooleanMessage;
import org.kuali.student.brms.internal.common.runtime.ast.BinaryMessageTree;
import org.kuali.student.brms.internal.common.runtime.ast.BooleanFunction;
import org.kuali.student.brms.internal.common.runtime.ast.BooleanFunctionResult;
import org.kuali.student.brms.internal.common.runtime.ast.BooleanMessageImpl;
import org.kuali.student.brms.internal.common.runtime.ast.BooleanNode;
import org.kuali.student.brms.internal.common.runtime.exceptions.BooleanFunctionException;
import org.kuali.student.common.util.VelocityTemplateEngine;
import org.kuali.student.brms.ruleexecution.runtime.SimpleExecutor;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.exceptions.MessageBuilderException;

/**
 * This <code>AbstractMessageBuilder</code> class builds a summary message 
 * from plain strings or Velocity template messages. Summary message is built 
 * from analyzing the outcome of a boolean expression. 
 * If no language is specified then the default language locale is used. 
 */
public abstract class AbstractMessageBuilder {
    private final static String SUCCESS_MESSAGE_LOGGER = "org.kuali.student.brms.runtime.ast.success";
    private final static String FAILURE_MESSAGE_LOGGER = "org.kuali.student.brms.runtime.ast.failure";
    private final static String SUCCESS_MESSAGE_LOGGER_DRL = "/drools/drls/org/kuali/student/brms/ruleexecution/runtime/report/ast/SuccessMessageLogger.drl";
    private final static String FAILURE_MESSAGE_LOGGER_DRL = "/drools/drls/org/kuali/student/brms/ruleexecution/runtime/report/ast/FailureMessageLogger.drl";

    private final VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();

    private String booleanExpression;
    private Map<String, ? extends BooleanMessage> messageMap;
    private Map<String, Object> messageContextMap;
    private String language;
    
    private final static String BOOLEAN_OPERATOR_ID = ".booleanOperators";
    
    private SimpleExecutor executor;

    /**
     * Constructor. 
     * Default 'and' operator is translated as 'AND' and 
     * default 'or' operator is translated as 'OR'
     * 
     * @param executor Rule engine
     */
    public AbstractMessageBuilder(final SimpleExecutor executor) {
        this.executor = executor;
        this.language = Locale.getDefault().getLanguage(); 
        String id = this.language + BOOLEAN_OPERATOR_ID; 
        BooleanOperators bo = new BooleanOperators();
        this.executor.addGlobalObject(id, bo);
    	setup();
    }

    /**
     * Constructor.
     * 
     * @param executor Rule engine
     * @param andOperator String representation of boolean 'and'
     * @param orOperator String representation of boolean 'or'
     */
    public AbstractMessageBuilder(final SimpleExecutor executor, final String language, final BooleanOperators booleanOperators) {
        this.executor = executor;
        this.language = language; 
        String id = this.language + BOOLEAN_OPERATOR_ID; 
        this.executor.addGlobalObject(id, booleanOperators);
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
            astTree = new BinaryMessageTree(this.language, nodeMessageMap);
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
        
		// Removed starting and ending brackets if they are the only brackets in the message
        if (message.startsWith("(") && message.endsWith(")") && 
				message.replaceAll("[^(]","").length() == 1 &&
				message.replaceAll("[^)]","").length() == 1) {
			message = message.substring(1, message.length()-1);
		}
        
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
        	throw new MessageBuilderException("Boolean expression is null");
        }
        
        BooleanFunction func = new BooleanFunction(this.booleanExpression);
        List<String> funcVars = func.getVariables();

    	if(funcVars == null || funcVars.isEmpty()) {
    		throw new MessageBuilderException("Boolean function variables are null or empty. Boolean expression: " + this.booleanExpression);
    	} 
        
        for (String id : funcVars) {
        	if(id == null) {
        		throw new MessageBuilderException("Boolean variable id is null or empty. Boolean variable ids: " + funcVars);
        	} 

        	BooleanMessage message = this.messageMap.get(id);
        	
        	if (message == null) {
        		throw new MessageBuilderException("Boolean message is null for id='" + id + "'");
        	}
        	
        	BooleanMessage booleanMessage = buildMessage(message);
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
