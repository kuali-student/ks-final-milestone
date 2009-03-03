package org.kuali.student.rules.ruleexecution.runtime.report.ast;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.rules.internal.common.runtime.ast.BinaryMessageTree;
import org.kuali.student.rules.internal.common.runtime.ast.BooleanFunction;
import org.kuali.student.rules.internal.common.runtime.ast.BooleanMessage;
import org.kuali.student.rules.internal.common.runtime.ast.BooleanNode;
import org.kuali.student.rules.internal.common.runtime.ast.Message;
import org.kuali.student.rules.ruleexecution.runtime.SimpleExecutor;

public abstract class AbstractMessageBuilder {
    private final static String SUCCESS_MESSAGE_LOGGER = "org.kuali.student.rules.runtime.ast.success";
    private final static String FAILURE_MESSAGE_LOGGER = "org.kuali.student.rules.runtime.ast.failure";
    private final static String SUCCESS_MESSAGE_LOGGER_DRL = "/drools/drls/org/kuali/student/rules/ruleexecution/runtime/report/ast/SuccessMessageLogger.drl";
    private final static String FAILURE_MESSAGE_LOGGER_DRL = "/drools/drls/org/kuali/student/rules/ruleexecution/runtime/report/ast/FailureMessageLogger.drl";

    private SimpleExecutor executor;

    public AbstractMessageBuilder(SimpleExecutor executor) {
        this.executor = executor;
    	setup();
    }
    
	/**
     * Builds message from a boolean expression. 
     * Example: (M1 AND M2) OR M3 - (M1*M2)+M3
     * 
     * @param booleanRule Boolean expression
     * @param messageContainer Contains a list of messages
     * @return A message
     */
    public String buildMessage(String booleanExpression, Map<String, ? extends Message> messageMap) {
        BinaryMessageTree ASTtree = null;

        try {
            // set the functionString and Maps from the proposition container
        	Map<String, BooleanMessage> nodeMessageMap = buildMessageMap(booleanExpression, messageMap);

            // go parse function in buildTree
            ASTtree = new BinaryMessageTree(nodeMessageMap);
            BooleanNode root = ASTtree.buildTree(booleanExpression);
            ASTtree.traverseTreePostOrder(root, null);

            List<BooleanNode> treeNodes = ASTtree.getAllNodes();
            this.executor.execute(treeNodes);
        } catch (Throwable t) {
            throw new RuntimeException("Building message failed: " + t.getMessage(), t);
        }

        // This is the final rule report message summary
        String message = ASTtree.getRoot().getNodeMessage();
        return message;
    }

    private Map<String, BooleanMessage> buildMessageMap(String booleanExpression, Map<String, ? extends Message> messageMap) {
    	Map<String, BooleanMessage> nodeMessageMap = new HashMap<String, BooleanMessage>();

        if (booleanExpression == null || booleanExpression.isEmpty()) {
        	throw new RuntimeException("Boolean expression is null.");
        }
        
        BooleanFunction func = new BooleanFunction(booleanExpression);
        List<String> funcVars = func.getVariables();

        for (String id : funcVars) {
        	BooleanMessage booleanMessage = messageMap.get(id).getBooleanMessage();
            nodeMessageMap.put(id, booleanMessage);
        }
        
        return nodeMessageMap;
    }

    /**
     * Setup default rule sets
     */
    private void setup() {
        Reader source1 = new InputStreamReader(MessageBuilder.class.getResourceAsStream(SUCCESS_MESSAGE_LOGGER_DRL));
        this.executor.addRuleSet(SUCCESS_MESSAGE_LOGGER, source1);
        Reader source2 = new InputStreamReader(MessageBuilder.class.getResourceAsStream(FAILURE_MESSAGE_LOGGER_DRL));
        this.executor.addRuleSet(FAILURE_MESSAGE_LOGGER, source2);
    }
}
