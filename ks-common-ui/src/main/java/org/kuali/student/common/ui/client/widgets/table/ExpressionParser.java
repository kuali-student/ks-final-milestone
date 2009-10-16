/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.widgets.table;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

/**
 * This is the parser for boolean expression.
 * It checks the error, creates the Reverse Polish notation,
 * merge the binary tree, sort the nodes. 
 * 
 * */
public class ExpressionParser {
    /**
     * Error messages are stored in a list
     * */
    private List<String> errorMessageList = new ArrayList<String>();
    
    public ExpressionParser() {
    }
    /**
     * After parsing, it tells if the expression has error.
     *  
     * @return If the input expression has error
     * */
    public boolean hasError(){
        return errorMessageList.size() > 0;
    }
    /**
     * It returns all error messages
     * 
     * @return List of error messages
     * */
    public List<String> getErrorMessage(){
        return errorMessageList;
    }
    /**
     * Parse the boolean expression
     * 
     * @return the tree of input tokens
     * */
    public Node<Token> parse(String expression) {
        errorMessageList = new ArrayList<String>();
        List<String> tokenValueList = getTokenValue(expression);
        List<Token> tokenList = getTokenList(tokenValueList);
        errorCheck(tokenList);
        if(hasError()){
            return null;
        }
        List<Node<Token>> nodeList = toNodeList(tokenList);
        List<Node<Token>> rpnList = getRPN(nodeList);

        Node<Token> root = binaryTreeFromRPN(rpnList);
        
        //Node<Token> ruleRoot = root;
        Node<Token> ruleRoot = mergeBinaryTree(root);
      
        
        //  ruleRoot = reStructure(root);
        ruleRoot = orderLeafChildren(ruleRoot, tokenList );
        ruleRoot = orderNonLeafChildren(ruleRoot, tokenList );
        return ruleRoot;
    }
    /**
     * Create the boolean expression from tree
     * 
     * @return boolean expression
     * */
    public static String getExpressionString(Node root){
        while(root.getChildCount()> 1){
            List<List<Node>> level = root.toLevel();
            for(Node<Token> n: level.get(level.size()-1)){
                if(n.isLeaf() && n.getParent() != null){
                    Node parent = n.getParent();
                    StringBuilder sb = new StringBuilder();
                    if(parent.getChildAt(0).getUserObject() instanceof ExpressionNode
                            && parent.getChildAt(0).getUserObject() instanceof ExpressionNode
                        && (( ExpressionNode)parent.getChildAt(0).getUserObject()).token.type == Token.Or
                        && (( Token)parent.getUserObject()).type == Token.And){
                        sb.append(" ( "+parent.getChildAt(0).getUserObject().toString()+" ) ");
                    }else{
                        sb.append(" "+parent.getChildAt(0).getUserObject().toString()+" ");    
                    }
                    
                    
                    for(int i=1;i<parent.getChildCount();i++){
                        Node child = parent.getChildAt(i);

                        if(parent.getChildAt(i).getUserObject() instanceof ExpressionNode
                                && parent.getChildAt(i).getUserObject() instanceof ExpressionNode
                            && (( ExpressionNode)parent.getChildAt(i).getUserObject()).token.type == Token.Or
                            && (( Token)parent.getUserObject()).type == Token.And){
                            sb.append(parent.getUserObject().toString()+ " ( "+
                                    child.getUserObject().toString()+" ) ");

                        }else{
                            sb.append(" "+parent.getUserObject().toString()+ "  "+
                                    child.getUserObject().toString()+" ");
                        }
                    }
                    ExpressionNode expressionNode = new ExpressionNode();
                    expressionNode.token = (Token)parent.getUserObject();
                    expressionNode.expression = sb.toString();
                    parent.setUserObject(expressionNode);
                    parent.removeAllChildren();
                    break;
                }
            }
            
        }
        
        
        return root.getUserObject().toString();
    }
    /**
     * Order the nonleaf children
     * */
    private Node<Token> orderNonLeafChildren(Node<Token> binaryTree,List<Token> tokenList ) {
        List<Node> list = binaryTree.getNonLeafChildren();
        if(list.size() > 1){
            sequeceNonLeaves(list, tokenList);
            binaryTree.children().removeAll(list);
            for(Node n: list){
                binaryTree.addNode(n);
            }
        }
        for(Node n: binaryTree.children()){
            if(n.isLeaf() == false){
                orderNonLeafChildren(n,tokenList );
            }
        }
        
        List<Node> nonLeafChildList = binaryTree.getNonLeafChildren();
        List<Node> leafChildList = binaryTree.getLeafChildren();
        binaryTree.children().removeAll(nonLeafChildList);
        binaryTree.children().removeAll(leafChildList);
        for(Node n: nonLeafChildList){
            binaryTree.addNode(n);
        }
        
        for(Node n: leafChildList){
            binaryTree.addNode(n);
        }
        return binaryTree;
   }
   /**
    * Order the non leaves
    * */
   private void sequeceNonLeaves(List<Node> nonLeafChildList, List<Token> list){
       if(nonLeafChildList.size() == 2){
           if (indexInInputTokenList((Token)nonLeafChildList.get(0).getFirstLeafDescendant().getUserObject(), list)> 
           indexInInputTokenList((Token)nonLeafChildList.get(1).getFirstLeafDescendant().getUserObject(), list)) {
               Node buffer = nonLeafChildList.get(0);
               nonLeafChildList.remove(0);
               nonLeafChildList.add(buffer);
           }
       }
       for (int out = nonLeafChildList.size() - 1; out > 1; out--){
         for (int in = 0; in < out; in++){
           // inner loop (forward)
           if (indexInInputTokenList((Token)nonLeafChildList.get(in).getFirstLeafDescendant().getUserObject(), list)> 
           indexInInputTokenList((Token)nonLeafChildList.get(in + 1).getFirstLeafDescendant().getUserObject(), list)) {
             // swap them
               Node node = nonLeafChildList.get(in);
               nonLeafChildList.remove(in);
               nonLeafChildList.add(in+1, node);
           }
         }
       }
   }
    /**
     * Order the leaf children
     * */
    private Node<Token> orderLeafChildren(Node<Token> binaryTree,List<Token> tokenList ) {
         List<Node> list = binaryTree.getLeafChildren();
         if(list.size() > 1){
             sequeceLeaves(list, tokenList);
             binaryTree.children().removeAll(list);
             for(Node n: list){
                 binaryTree.addNode(n);
             }
         }
         for(Node n: binaryTree.children()){
             if(n.isLeaf() == false){
                 orderLeafChildren(n,tokenList );
             }
         }
        return binaryTree;
    }
    /**Reorder the children*/
    private void sequeceLeaves(List<Node> leafChildList, List<Token> list){
        if(leafChildList.size() == 2){
            if (indexInInputTokenList((Token)leafChildList.get(0).getUserObject(), list)> 
            indexInInputTokenList((Token)leafChildList.get(1).getUserObject(), list)) {
              // swap them
                Token buffer = (Token)leafChildList.get(0).getUserObject();
                leafChildList.get(0).setUserObject(leafChildList.get(1).getUserObject());
                leafChildList.get(1).setUserObject(buffer);
            }
            
        }
        
        for (int out = leafChildList.size() - 1; out > 1; out--){
          for (int in = 0; in < out; in++){
            // inner loop (forward)
            if (indexInInputTokenList((Token)leafChildList.get(in).getUserObject(), list)> 
            indexInInputTokenList((Token)leafChildList.get(in + 1).getUserObject(), list)) {
              // swap them
                Token buffer = (Token)leafChildList.get(in).getUserObject();
                leafChildList.get(in).setUserObject(leafChildList.get(in + 1).getUserObject());
                leafChildList.get(in + 1).setUserObject(buffer);
            }
          }
        }
    }
    /**Get the index of a token in the token list*/
    private int indexInInputTokenList(Token token, List<Token> list){
        int i = -1;
        for(Token n: list){
            if(n.value != null && token.value != null && n.value.equals(token.value)){
                return i;
            }
            i++;
        }
        return i;
    }

/*    public static  Node<Token> reStructure(Node<Token> binaryTree) {
        Node root = new Node();
        //Node root = binaryTree;
        root.setUserObject(binaryTree.getUserObject());
        List<Node> childList = binaryTree.children();

        ListIterator<Node> itr = childList.listIterator();
        for (; itr.hasNext();) {
            Node node = getDeeperNode(childList);
            if (node.isLeaf()) {
                root.addNode(node);
            } else {
                root.addNode(reStructure(node));
            }
            childList.remove(node);
        }

        return root.clone();
    }
*/
    private static Node getDeeperNode(List<Node> nodeList) {
        int childCount = 0;
        for (Node node : nodeList) {
            if (childCount < node.getAllChildren().size()) {
                childCount = node.getAllChildren().size();
            }
        }
        for (Node node : nodeList) {
            if (childCount == node.getAllChildren().size()) {
                return node;
            }
        }

        return null;
    }
    /** Merge the binary tree. */
    public static Node<Token> mergeBinaryTree(Node<Token> binaryTree) {
        while (parentEqualsGrandParent(binaryTree)) {
            List<Node<Token>> list = binaryTree.getAllChildren();

            for (Node node : list) {
                if (node.getParent() != null && node.getParent().getParent() != null) {
                    Node parentNode = node.getParent();
                    Node grandParentNode = node.getParent().getParent();
                    Token parentToken = (Token) parentNode.getUserObject();
                    Token grandParentToken = (Token) grandParentNode.getUserObject();

                    if (parentToken.type == grandParentToken.type) {
                        for (int i = 0; i < parentNode.getChildCount(); i++) {
                            Node n = parentNode.getChildAt(i);
                            grandParentNode.addNode(n);
                        }
                        grandParentNode.remove(parentNode);
                    }
                }
            }
        }
        return binaryTree;
    }

    private static boolean parentEqualsGrandParent(Node<Token> binaryTree) {
        List<Node<Token>> list = binaryTree.getAllChildren();

        for (Node node : list) {
            if (node.getParent() != null && node.getParent().getParent() != null) {

                Token parentToken = (Token) node.getParent().getUserObject();
                Token grandParentToken = (Token) node.getParent().getParent().getUserObject();
                if (parentToken.type == grandParentToken.type) {
                    return true;
                }
            }
        }

        return false;
    }
    /** Build the binary tree from list of tokens*/
    private Node<Token> binaryTreeFromRPN(List<Node<Token>> rpnList) {
        Stack<Node<Token>> conditionStack = new Stack<Node<Token>>();
        for (Node<Token> node : rpnList) {
            if (node.getUserObject().type == Token.Condition) {
                conditionStack.push(node);
            } else if (node.getUserObject().type == Token.And || node.getUserObject().type == Token.Or) {
                Node<Token> left = conditionStack.pop();
                Node<Token> right = conditionStack.pop();
                node.addNode(left);
                node.addNode(right);
                conditionStack.push(node);
            }
        }

        return conditionStack.pop();
    }

    /**
     * If higher push to stack, else pop till less than or equal, add to list push to stack if ( push to stack if ) pop to
     * list till (.
     * 
     * http://en.wikipedia.org/wiki/Reverse_Polish_notation
     * 
     */
    private List<Node<Token>> getRPN(List<Node<Token>> nodeList) {
        List<Node<Token>> rpnList = new ArrayList<Node<Token>>();
        Stack<Node<Token>> operatorStack = new Stack<Node<Token>>();

        for (Node<Token> node : nodeList) {
            if (node.getUserObject().type == Token.Condition) {
                rpnList.add(node);

            } else if (node.getUserObject().type == Token.And) {
                operatorStack.push(node);
            } else if (node.getUserObject().type == Token.StartParenthesis) {
                operatorStack.push(node);
            } else if (node.getUserObject().type == Token.Or) {

                if (operatorStack.isEmpty() == false && operatorStack.peek().getUserObject().type == Token.And) {
                    do {
                        rpnList.add(operatorStack.pop());
                    } while (operatorStack.isEmpty() == false && operatorStack.peek().getUserObject().type == Token.And);
                }

                operatorStack.push(node);
            } else if (node.getUserObject().type == Token.EndParenthesis) {
                while (operatorStack.peek().getUserObject().type != Token.StartParenthesis) {
                    rpnList.add(operatorStack.pop());
                }
                operatorStack.pop();// pop the (
            }
        }
        if (operatorStack.isEmpty() == false) {
            do {
                rpnList.add(operatorStack.pop());
            } while (operatorStack.isEmpty() == false);
        }
        return rpnList;
    }

    private int findNodeIndex(List<Node<Token>> nodeList, int type) {
        int index = -1;
        for (Node<Token> node : nodeList) {
            index++;
            if (node.getUserObject().type == type) {
                return index;
            }
        }
        return index;
    }

    private boolean hasParenthesis(List<Node<Token>> nodeList) {
        boolean has = false;
        for (Node<Token> node : nodeList) {
            if (node.getUserObject().type == Token.StartParenthesis) {
                return true;
            }
        }
        return has;
    }

    private List<Node<Token>> toNodeList(List<Token> tokenList) {
        List<Node<Token>> nodeList = new ArrayList<Node<Token>>();
        for (Token token : tokenList) {
            Node<Token> node = new Node<Token>();
            node.setUserObject(token);
            nodeList.add(node);
        }
        return nodeList;
    }

    private void errorCheck(List<Token> tokenList) {
        if (tokenList.size() == 0) {
            errorMessageList.add("empty input");
            return;
        }
        if (tokenList.size() <= 2) {
            errorMessageList.add("input not complete");
            return;
        }
        if ((tokenList.get(0).type == Token.StartParenthesis 
                || tokenList.get(0).type == Token.Condition) == false) {
            errorMessageList.add("must start with ( or condition");
            return;
        }
        int lastIndex = tokenList.size() - 1;
        if ((tokenList.get(lastIndex).type == Token.EndParenthesis || tokenList.get(lastIndex).type == Token.Condition) == false) {
            errorMessageList.add("must end with ) or condition");
            return;
        }
        if (countToken(tokenList, Token.StartParenthesis) != countToken(tokenList, Token.EndParenthesis)) {
            errorMessageList.add("() not in pair");
            return;
        }
        // condition cannot duplicate
        for (int i = 1; i < tokenList.size(); i++) {
            Token token = tokenList.get(i);
            if (token.type == Token.And) {
                checkAnd(tokenList, i);
            } else if (token.type == Token.Or) {
                checkOr(tokenList, i);
            } else if (token.type == Token.StartParenthesis) {
                checkStartParenthesis(tokenList, i);
            } else if (token.type == Token.EndParenthesis) {
                checkEndParenthesis(tokenList, i);
            } else if (token.type == Token.Condition) {
                checkCondition(tokenList, i);
            }
        }
    }

    private int countToken(List<Token> tokenList, int type) {
        int count = 0;
        for (Token t : tokenList) {
            if (t.type == type) {
                count++;
            }
        }
        return count;
    }

    private void checkAnd(List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.Condition || tokenList.get(currentIndex - 1).type == Token.EndParenthesis) == false) {
            errorMessageList.add("only ) and condition could sit before and");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Condition || tokenList.get(currentIndex + 1).type == Token.StartParenthesis) == false) {
            errorMessageList.add("only ( and condition could sit after and");
        }

    }

    private void checkOr(List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.Condition || tokenList.get(currentIndex - 1).type == Token.EndParenthesis) == false) {
            errorMessageList.add("only ) and condition could sit before or");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Condition || tokenList.get(currentIndex + 1).type == Token.StartParenthesis) == false) {
            errorMessageList.add("only ( and condition could sit after or");
        }
    }

    private void checkStartParenthesis(List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.And || tokenList.get(currentIndex - 1).type == Token.Or || tokenList.get(currentIndex - 1).type == Token.StartParenthesis) == false) {
            errorMessageList.add("only and, or, ( could sit before (");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Condition || tokenList.get(currentIndex + 1).type == Token.StartParenthesis) == false) {
            errorMessageList.add("only ( and condition could sit after (");
        }

    }

    private void checkEndParenthesis(List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.Condition || tokenList.get(currentIndex - 1).type == Token.EndParenthesis) == false) {
            errorMessageList.add("only condition and ) could sit before )");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Or || tokenList.get(currentIndex + 1).type == Token.And || tokenList.get(currentIndex + 1).type == Token.EndParenthesis) == false) {
            errorMessageList.add("only ), and, or could sit after )");
        }

    }

    private void checkCondition(List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.And || tokenList.get(currentIndex - 1).type == Token.Or || tokenList.get(currentIndex - 1).type == Token.StartParenthesis) == false) {
            errorMessageList.add("only and, or could sit before condition");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Or || tokenList.get(currentIndex + 1).type == Token.And || tokenList.get(currentIndex + 1).type == Token.EndParenthesis) == false) {
            errorMessageList.add("only ), and, or could sit after condition");
        }

    }

    private List<Token> getTokenList(List<String> tokenValueList) {
        List<Token> tokenList = new ArrayList<Token>();
        for (String value : tokenValueList) {
            if (value.isEmpty()) {
                continue;
            }
            if ("(".equals(value)) {
                Token t = new Token();
                t.type = Token.StartParenthesis;
                tokenList.add(t);
            } else if (")".equals(value)) {
                Token t = new Token();
                t.type = Token.EndParenthesis;
                tokenList.add(t);

            } else if ("and".equals(value)) {
                Token t = new Token();
                t.type = Token.And;
                tokenList.add(t);

            } else if ("or".equals(value)) {
                Token t = new Token();
                t.type = Token.Or;
                tokenList.add(t);

            } else {
                Token t = new Token();
                t.type = Token.Condition;
                t.value = value;
                tokenList.add(t);

            }
        }
        return tokenList;
    }

    private List<String> getTokenValue(String expression) {
        expression = expression.toLowerCase();
        List<String> tokenValueList = new ArrayList<String>();
        StringBuffer tokenValue = new StringBuffer();
        for (int i = 0; i < expression.length(); i++) {

            char ch = expression.charAt(i);
            if (ch == ' ') {
                tokenValueList.add(tokenValue.toString());
                tokenValue = new StringBuffer();
            } else if (ch == '(' || ch == ')') {
                tokenValueList.add(tokenValue.toString());
                tokenValue = new StringBuffer();
                tokenValueList.add(String.valueOf(ch));
            } else {
                tokenValue.append(ch);
            }
        }
        tokenValueList.add(tokenValue.toString());
        return tokenValueList;
    }
}

