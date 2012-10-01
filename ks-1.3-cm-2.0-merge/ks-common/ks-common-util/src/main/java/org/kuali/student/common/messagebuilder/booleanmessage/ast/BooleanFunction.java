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

/**
 * 
 */
package org.kuali.student.common.messagebuilder.booleanmessage.ast;

import java.util.ArrayList;
import java.util.List;

public class BooleanFunction {
    private String booleanExpression;
    private BinaryMessageTree ASTtree;
    private BooleanNode root;
    private ArrayList<String> funcVars = new ArrayList<String>();
    private ArrayList<String> symbols = new ArrayList<String>();

    public BooleanFunction(String booleanExpression) {
        // Since the AST calls the Boolean Function parser,
        // calling the AST here asserts the function is valid
        ASTtree = new BinaryMessageTree();
        root = ASTtree.buildTree(booleanExpression);
        this.booleanExpression = booleanExpression;
    }

    /**
     * Parse function string to get the variables 
     * 1. Need this function at rule creation time (i.e when in BRMS) to insert
     * the action part of the rules 
     * 2. Also need this at runtime to populate the HashMaps in the 
     * proposition which are used as tree leaf nodes in the AST.
     * 
     * @return List of variables
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
     * Parse function string to get all the symbols. 
     * Need this only at rule creation time. Can't load the symbols in
     * getVariables() because the AST removes the parenthesis and 
     * we need those. Get them from the string which has been
     * validated in the constructor example A0*B4+(C*D)
     * 
     * @return List of symbols
     */
    public List<String> getSymbols() {
		String workString = null;
		// remove spaces
		this.booleanExpression = (booleanExpression == null ? null : booleanExpression.replaceAll("\\s+", ""));

		workString = this.booleanExpression;

		while (workString != null && workString.length() > 0) {
			String propositionKeyPattern = "^[A-Z][0-9]*";
			String restOfFunctionString = null;
			String theSymbol = null;
			int restOfFunctionStringStartIndex = 0;
			if (workString.matches(propositionKeyPattern + ".*")) {
				restOfFunctionString = workString.replaceFirst(propositionKeyPattern, "");
				restOfFunctionStringStartIndex = workString.indexOf(restOfFunctionString);
				if (restOfFunctionStringStartIndex <= 0) {
					theSymbol = workString;
					workString = "";
				} else {
					theSymbol = workString.substring(0, restOfFunctionStringStartIndex);
					workString = restOfFunctionString;
				}
			} else {
				theSymbol = workString.substring(0, 1);
				workString = workString.substring(1, workString.length());
			}
			symbols.add(theSymbol);
		}
		return symbols;
	}
}
