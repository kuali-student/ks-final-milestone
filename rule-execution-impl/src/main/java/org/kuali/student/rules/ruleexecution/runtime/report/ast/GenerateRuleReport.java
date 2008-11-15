/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.ruleexecution.runtime.report.ast;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

import org.kuali.student.rules.internal.common.runtime.ast.BinaryTree;
import org.kuali.student.rules.internal.common.runtime.ast.BooleanNode;
import org.kuali.student.rules.internal.common.runtime.ast.Function;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.rules.ruleexecution.runtime.SimpleExecutor;

/**
 * This is a sample file to launch a rule package from a rule source file.
 */
public class GenerateRuleReport {
    private HashMap<String, Boolean> nodeValueMap;
    private HashMap<String, String> nodeMessageMap;
    private boolean ruleResult;
    private String functionString;
    
    private final static String SUCCESS_MESSAGE_LOGGER = "org.kuali.student.rules.runtime.ast.success";
    private final static String FAILURE_MESSAGE_LOGGER = "org.kuali.student.rules.runtime.ast.failure";
    private final static String SUCCESS_MESSAGE_LOGGER_DRL = "/drools/drls/org/kuali/student/rules/rulesetexecution/runtime/ast/SuccessMessageLogger.drl";
    private final static String FAILURE_MESSAGE_LOGGER_DRL = "/drools/drls/org/kuali/student/rules/rulesetexecution/runtime/ast/FailureMessageLogger.drl";

    private SimpleExecutor executor;
    
    public GenerateRuleReport(SimpleExecutor executor) {
        this.executor = executor;
    	setup();
    }

    /**
     * Creates proposition result report.
     * 
     * @param propContainer Contains a list of propositions
     * @return The proposition container <code>propContainer</code> with a report
     */
    public PropositionReport execute(PropositionContainer propContainer) {
        BinaryTree ASTtree = null;
        ruleResult = propContainer.getRuleResult();

        try {
            // set the functionString and Maps from the proposition container
            fillStringAndMap(propContainer, ruleResult);

            // go parse function in buildTree
            ASTtree = new BinaryTree(nodeValueMap, nodeMessageMap);
            BooleanNode root = ASTtree.buildTree(functionString);
            ASTtree.traverseTreePostOrder(root, null);

            List<BooleanNode> treeNodes = ASTtree.getAllNodes();
            this.executor.execute(treeNodes);

        } catch (Throwable t) {
            throw new RuleSetExecutionException( "Generating rule report failed: " + t.getMessage(), t );
        }

        // This is the final rule report message
        String message = ASTtree.getRoot().getNodeMessage();
        PropositionReport ruleReport = propContainer.getRuleReport();
        ruleReport.setSuccessful(ruleResult);

        if (ruleResult == true) {
            ruleReport.setSuccessMessage(message);
        } else {
            ruleReport.setFailureMessage(message);
        }

        propContainer.setRuleReport(ruleReport);
        return ruleReport;
    }

    private void fillStringAndMap(PropositionContainer propContainer, boolean ruleResult) {
        nodeValueMap = new HashMap<String, Boolean>();
        nodeMessageMap = new HashMap<String, String>();
        functionString = propContainer.getFunctionalRuleString();

        if (functionString == null || functionString.isEmpty()) {
        	throw new RuleSetExecutionException("Boolean function is null. Rule may not have been executed.");
        }
        
        Function func = new Function(functionString);
        List<String> funcVars = func.getVariables();

        for (String var : funcVars) {
            Boolean value = propContainer.getProposition(var).getResult();
            nodeValueMap.put(var, value);

            PropositionReport report = propContainer.getProposition(var).getReport();
            String message = "Rule execution result undefined";

            if (ruleResult == true) {
                message = report.getSuccessMessage();
            } else {
                message = report.getFailureMessage();
            }
            nodeMessageMap.put(var, message);
        }
    }

    /**
     * Setup default rule sets
     */
    private void setup() {
        Reader source1 = new InputStreamReader(GenerateRuleReport.class.getResourceAsStream(SUCCESS_MESSAGE_LOGGER_DRL));
        this.executor.addRuleSet(SUCCESS_MESSAGE_LOGGER, source1);
        Reader source2 = new InputStreamReader(GenerateRuleReport.class.getResourceAsStream(FAILURE_MESSAGE_LOGGER_DRL));
        this.executor.addRuleSet(FAILURE_MESSAGE_LOGGER, source2);
    }
}
