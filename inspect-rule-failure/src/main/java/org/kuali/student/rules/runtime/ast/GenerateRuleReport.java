package org.kuali.student.rules.runtime.ast;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.kuali.student.rules.common.runtime.ast.BinaryTree;
import org.kuali.student.rules.common.runtime.ast.BooleanNode;
import org.kuali.student.rules.common.util.Function;
import org.kuali.student.rules.statement.PropositionContainer;
import org.kuali.student.rules.statement.PropositionReport;

/**
 * This is a sample file to launch a rule package from a rule source file.
 */
public class GenerateRuleReport {

    private HashMap<String, Boolean> nodeValueMap;
    private HashMap<String, String> nodeMessageMap;
    private boolean ruleResult;
    private String functionString;

    // public static String executeRule(String functionString, HashMap<String, Boolean> nodeValueMap, HashMap<String, String>
    // nodeFailureMessageMap) {
    public PropositionContainer executeRule(PropositionContainer propContainer) {
        BinaryTree ASTtree = null;
        ruleResult = propContainer.getRuleResult();

        try {
            // load up the rulebase
            RuleBase ruleBase = readRule(ruleResult);
            WorkingMemory workingMemory = ruleBase.newStatefulSession();

            // set the functionString and Maps from the proposition container
            fillStringAndMap(propContainer, ruleResult);

            // go parse function in buildTree
            ASTtree = new BinaryTree(nodeValueMap, nodeMessageMap);
            BooleanNode root = ASTtree.buildTree(functionString);
            ASTtree.traverseTreePostOrder(root, null);

            List<BooleanNode> treeNodes = ASTtree.getAllNodes();
            // Iterate over List<Node> and insert in memory
            for (BooleanNode bnode : treeNodes) {
                // System.out.println(bnode.getLabel() + " " + bnode.getValue() + " " + bnode.getNodeMessage());
                workingMemory.insert(bnode);
            }
            // System.out.println("ABOUT TO FIRE ALL RULES");
            workingMemory.fireAllRules();
            // System.out.println( ASTtree.getRoot().getNodeMessage() + " IM ROOT");

        } catch (Throwable t) {
            t.printStackTrace();
        }

        // This is the final rule report message
        String message = ASTtree.getRoot().getNodeMessage();
        PropositionReport ruleReport = propContainer.getRuleReport();

        if (ruleResult == true) {
            ruleReport.setSuccessMessage(message);
        } else {
            ruleReport.setFailureMessage(message);
        }

        propContainer.setRuleReport(ruleReport);
        return propContainer;
    }

    private void fillStringAndMap(PropositionContainer propContainer, boolean ruleResult) {
        nodeValueMap = new HashMap<String, Boolean>();
        nodeMessageMap = new HashMap<String, String>();
        functionString = propContainer.getFunctionalRuleString();

        Function func = new Function(functionString);
        List<String> funcVars = func.getVariables();

        for (String var : funcVars) {

            Boolean value = propContainer.getProposition(var).getResult();
            nodeValueMap.put(var, value);

            PropositionReport report = propContainer.getProposition(var).getReport();
            String message = "rule result undefined";

            if (ruleResult == true) {
                message = report.getSuccessMessage();
            } else {
                message = report.getFailureMessage();
            }
            nodeMessageMap.put(var, message);
        }
    }

    /**
     * Please note that this is the "low level" rule assembly API.
     */
    private RuleBase readRule(boolean ruleResult) throws Exception {
        // read in the source
        Reader source = null;

        if (ruleResult == true) {
            source = new InputStreamReader(GenerateRuleReport.class.getResourceAsStream("/SuccessMessageLogger.drl"));
        } else {
            source = new InputStreamReader(GenerateRuleReport.class.getResourceAsStream("/FailureMessageLogger.drl"));
        }

        PackageBuilder builder = new PackageBuilder();

        builder.addPackageFromDrl(source);

        Package pkg = builder.getPackage();

        // add the package to a rulebase (deploy the rule package).
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage(pkg);
        return ruleBase;
    }

}
