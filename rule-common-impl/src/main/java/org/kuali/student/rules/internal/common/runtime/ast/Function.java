/**
 * 
 */
package org.kuali.student.rules.internal.common.runtime.ast;

import java.util.ArrayList;
import java.util.List;


public class Function {
    private String functionString;
    private BinaryTree ASTtree;
    private BooleanNode root;
    private ArrayList<String> funcVars = new ArrayList<String>();
    private ArrayList<String> symbols = new ArrayList<String>();

    public Function(String functionString) {

        // Since the AST calls the Boolean Function parser,
        // calling the AST here asserts the function is valid
        ASTtree = new BinaryTree();
        root = ASTtree.buildTree(functionString);

        this.functionString = functionString;
    }

    /**
     * Parse function string to get the variables 1. Need this function at rule creation time (i.e when in BRMS) to insert
     * the action part of the rules 2. Also need this at runtime to populate the HashMaps in the proposition which are used
     * as tree leaf nodes in the AST.
     */
    public List<String> getVariables() {

        ASTtree.traverseTreePostOrderDontSetNode(root, null);
        List<BooleanNode> treeNodes = ASTtree.getAllNodes();

        for (BooleanNode bnode : treeNodes) {
            String label = bnode.getLabel();
            if (label.equals("+") || label.equals("*")) {
                continue;
            }
            funcVars.add(label);
        }
        return funcVars;
    }

    /**
     * Parse function string to get all the symbols. Need this only at rule creation time. Can't load the symbols in
     * getVariables() because the AST removes the parenthesis and we need those. Get them from the string which has been
     * validated in the constructor Function() Function example A0*B4+(C*D)
     */
    public List<String> getSymbols() {
        String workString = null;
        // remove spaces
        functionString = (functionString == null ? null : functionString.replaceAll("\\s+", ""));
        
        workString = functionString;

        while (workString != null && workString.length() > 0) {
          String propositionKeyPattern = "^[A-Z][0-9]*";
          String restOfFunctionString = null;
          String theSymbol = null;
          int restOfFunctionStringStartIndex = 0;
          if (workString.matches(propositionKeyPattern + ".*")) {
            restOfFunctionString = workString.replaceFirst(
                propositionKeyPattern,"");
            restOfFunctionStringStartIndex = workString.indexOf(
                restOfFunctionString);
            if (restOfFunctionStringStartIndex <= 0) {
              theSymbol = workString;
              workString = "";
            } else {
              theSymbol = workString.substring(0,
                  restOfFunctionStringStartIndex);
              workString = restOfFunctionString;
            }
          } else {
            theSymbol = workString.substring(0,1);
            workString = workString.substring(1,workString.length());
          }
          symbols.add(theSymbol);
        }
        return symbols;
    }
}
