package org.kuali.student.rules.ast;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

/**
 * This is a sample file to launch a rule package from a rule source file.
 */
public class GenerateRuleFailureMessage {
	
	static HashMap<String, Boolean> nodeValueMap;
	static HashMap<String, String> nodeFailureMessageMap;
	static String functionString;
	
    public static final void main(String[] args) {
    	setRuleParams();
    	// if there are Sets, pre-process rule messages
    	//preProcessRuleFailureMessages();
    	executeRule(functionString, nodeValueMap, nodeFailureMessageMap);
    }
    public static String executeRule(String functionString, HashMap<String, Boolean> nodeValueMap, HashMap<String, String> nodeFailureMessageMap) {
    	BinaryTree ASTtree = null;
    	try {
        	//load up the rulebase
            RuleBase ruleBase = readRule();
            WorkingMemory workingMemory = ruleBase.newStatefulSession();
            
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
            System.out.println("ABOUT TO FIRE ALL RULES");
            workingMemory.fireAllRules();
            System.out.println( ASTtree.getRoot().getRuleFailureMessage() + "   IM ROOT");
            
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return ASTtree.getRoot().getRuleFailureMessage();
    }
    
    private static void setRuleParams (){
		
    	functionString = "A*B*C*D";
    	
    	nodeValueMap = new HashMap<String, Boolean>();
		nodeValueMap.put("A", false);
		nodeValueMap.put("B", true);
		nodeValueMap.put("C", true);
		nodeValueMap.put("D", true);
		
		nodeFailureMessageMap = new HashMap<String, String>();
		nodeFailureMessageMap.put("A", "Need MATH 200");
		nodeFailureMessageMap.put("B", "Need MATH 110");
		nodeFailureMessageMap.put("C", "Need 15 credits or more of 1st year science");
		nodeFailureMessageMap.put("D", "Need English 6000");
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
