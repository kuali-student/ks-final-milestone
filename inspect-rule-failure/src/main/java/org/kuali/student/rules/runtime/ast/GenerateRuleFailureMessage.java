package org.kuali.student.rules.runtime.ast;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

import org.kuali.student.rules.statement.*;
import org.kuali.student.rules.util.Function;
import org.kuali.student.rules.common.runtime.ast.*;

/**
 * This is a sample file to launch a rule package from a rule source file.
 */
public class GenerateRuleFailureMessage {
	
	static HashMap<String, Boolean> nodeValueMap;
	static HashMap<String, String> nodeFailureMessageMap;
	static String functionString;
	
    //public static String executeRule(String functionString, HashMap<String, Boolean> nodeValueMap, HashMap<String, String> nodeFailureMessageMap) {
    public static String executeRule(PropositionContainer propContainer) {
    	BinaryTree ASTtree = null;
    	try {
        	//load up the rulebase
            RuleBase ruleBase = readRule();
            WorkingMemory workingMemory = ruleBase.newStatefulSession();
            
            // set the functionString and Maps from the proposition container
            fillStringAndMap(propContainer);
            
            // go parse function in buildTree
            ASTtree = new BinaryTree(nodeValueMap, nodeFailureMessageMap);
            BooleanNode root = ASTtree.buildTree( functionString );
            ASTtree.traverseTreePostOrder(root, null);
            
            List<BooleanNode> treeNodes = ASTtree.getAllNodes();
            // Iterate over List<Node> and insert in memory
            for (BooleanNode bnode : treeNodes) {
            	//System.out.println(bnode.getLabel() + "   " + bnode.getValue() + "   " + bnode.getRuleFailureMessage());
            	workingMemory.insert( bnode );
            }
            //System.out.println("ABOUT TO FIRE ALL RULES");
            workingMemory.fireAllRules();
            //System.out.println( ASTtree.getRoot().getRuleFailureMessage() + "   IM ROOT");
            
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return ASTtree.getRoot().getRuleFailureMessage();
    }
    
    private static void fillStringAndMap(PropositionContainer propContainer){
        nodeValueMap = new HashMap<String, Boolean>();
        nodeFailureMessageMap = new HashMap<String, String>();
        functionString = propContainer.getFunctionalRuleString();
        
        Function func = new Function(functionString);
        List<String> funcVars = func.getVariables();
        
        for (String var : funcVars) {
            Boolean value = propContainer.getProposition(var).getResult();
            nodeValueMap.put(var, value);
            
            PropositionReport report = propContainer.getProposition(var).getReport();
            String message = report.getFailureMessage();
            nodeFailureMessageMap.put(var, message);
        }
    }
    

    /**
     * Please note that this is the "low level" rule assembly API.
     */
	private static RuleBase readRule() throws Exception {
		//read in the source
		Reader source = new InputStreamReader( GenerateRuleFailureMessage.class.getResourceAsStream( "/BoolBinaryTreeLogger.drl" ) );
		
		PackageBuilder builder = new PackageBuilder();

		builder.addPackageFromDrl( source );

		Package pkg = builder.getPackage();
		
		//add the package to a rulebase (deploy the rule package).
		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		ruleBase.addPackage( pkg );
		return ruleBase;
	}
    
}
