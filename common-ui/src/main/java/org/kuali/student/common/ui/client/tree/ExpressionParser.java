package org.kuali.student.common.ui.client.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExpressionParser {
    public static void main(String[] a) {
        new ExpressionParser();
        // String expression = "a and b or (c and d )"; // what if adn
        // String expression = "a and b"; // what if adn
        // String expression = ""; // what if adn
        // String expression = " a"; // what if adn
        // String expression = " a b"; // what if adn
        // String expression = " a and"; // what if adn
        // String expression = " a dsa b"; // what if adn
        // String expression = "(a or (a) and s )"; // what if adn
    }

    public ExpressionParser() {
        // String expression = "a and b or (c and d )"; // what if adn
        // String expression = "a or ( b and s or d ) "; // what if adn
        String expression = "a and (b or c) and d or e and f"; // what if adn

        Node<Token> ruleroot = parse(expression);
    }
public Node<Token> parse(String expression){
    List<String> tokenValueList = getTokenValue(expression);
    System.out.println(tokenValueList);
    List<Token> tokenList = getTokenList(tokenValueList);
    System.out.println(tokenList);
    errorCheck(tokenList);

    List<Node<Token>> nodeList = toNodeList(tokenList);
    List<Node<Token>> rpnList = getRPN(nodeList);

    System.out.println(rpnList);

    Node<Token> root = binaryTreeFromRPN(rpnList);
    System.out.println("binaryTree:" + root);

    Node<Token> ruleRoot = mergeBinaryTree(root);
    System.out.println("  ruleRoot: "+ ruleRoot);
    
    ruleRoot = rebuildTree(root);
    System.out.println("r ruleRoot: "+ ruleRoot);
    
    return ruleRoot;
 }
public Node<Token> rebuildTree(Node<Token> binaryTree){
    Node root = new Node();
    root.setUserObject(binaryTree.getUserObject());
    for(int i=0;i<binaryTree.getChildCount();i++){
        Node node = binaryTree.getChildAt(i);
    
        if(node.isLeaf()){
            root.addNode(node);
        }else{
            root.addNode(rebuildTree(node));
        }
    }

    return root;
}
    public Node<Token> mergeBinaryTree(Node<Token> binaryTree) {
        while (parentEqualsGrandParent(binaryTree)) {
            List<Node> list = binaryTree.getAllChildren();

            for (Node node : list) {
                if (node.getParent() != null && node.getParent().getParent() != null) {
                    Node parentNode = node.getParent();
                    Node grandParentNode = node.getParent().getParent();
                    Token parentToken = (Token) parentNode.getUserObject();
                    Token grandParentToken = (Token) grandParentNode.getUserObject();
                    
                    System.out.println(parentNode+":"+parentToken.type+":" +grandParentToken.type);
                    
                    if (parentToken.type == grandParentToken.type) {
                        //List<Node> removedList = new ArrayList<Node>();
                        for(int i=0;i<parentNode.getChildCount();i++){
                            Node n = parentNode.getChildAt(i);
                            grandParentNode.addNode(n);
                            
                         //  removedList.add(n);
                        }
                       // for(Node n : removedList){
                         //  parentNode.remove(n);
                       // }
                        grandParentNode.remove(parentNode);
                    }
              //     break; 
                }
            }
        }
        return binaryTree;
    }

    private boolean parentEqualsGrandParent(Node<Token> binaryTree) {
        List<Node> list = binaryTree.getAllChildren();

        for (Node node : list) {
            if ( node.getParent() != null && node.getParent().getParent() != null) {
                
                Token parentToken = (Token) node.getParent().getUserObject();
                Token grandParentToken = (Token) node.getParent().getParent().getUserObject();
                if (parentToken.type == grandParentToken.type) {
                    return true;
                }
            }
        }
        
        return false;
    }

    public Node<Token> binaryTreeFromRPN(List<Node<Token>> rpnList) {

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
     * if higher push to stack, else pop till less than or equal, add to list push to stack if ( push to stack if ) pop to
     * list till (
     */
    public List<Node<Token>> getRPN(List<Node<Token>> nodeList) {
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

    public int findNodeIndex(List<Node<Token>> nodeList, int type) {
        int index = -1;
        for (Node<Token> node : nodeList) {
            index++;
            if (node.getUserObject().type == type) {
                return index;
            }
        }

        return index;
    }

    public boolean hasParenthesis(List<Node<Token>> nodeList) {
        boolean has = false;
        for (Node<Token> node : nodeList) {
            if (node.getUserObject().type == Token.StartParenthesis) {
                return true;
            }
        }

        return has;
    }

    List<Node<Token>> toNodeList(List<Token> tokenList) {
        List<Node<Token>> nodeList = new ArrayList<Node<Token>>();
        for (Token token : tokenList) {
            Node<Token> node = new Node<Token>();
            node.setUserObject(token);
            nodeList.add(node);
        }
        return nodeList;
    }

    void errorCheck(List<Token> tokenList) {
        if (tokenList.size() == 0) {
            System.out.println("empty input");
        }
        if (tokenList.size() <= 2) {
            System.out.println("input not complete");
        }
        if ((tokenList.get(0).type == Token.StartParenthesis || tokenList.get(0).type == Token.Condition) == false) {
            System.out.println("must start with ( or condition");
        }
        int lastIndex = tokenList.size() - 1;
        if ((tokenList.get(lastIndex).type == Token.EndParenthesis || tokenList.get(lastIndex).type == Token.Condition) == false) {
            System.out.println("must start with ( or condition");
        }
        if (countToken(tokenList, Token.StartParenthesis) != countToken(tokenList, Token.EndParenthesis)) {
            System.out.println("() not in pair");
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

    int countToken(List<Token> tokenList, int type) {
        int count = 0;
        for (Token t : tokenList) {
            if (t.type == type) {
                count++;
            }
        }
        return count;
    }

    void checkAnd(List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.Condition || tokenList.get(currentIndex - 1).type == Token.EndParenthesis) == false) {
            System.out.println("only ) and condition could sit before and");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Condition || tokenList.get(currentIndex + 1).type == Token.StartParenthesis) == false) {
            System.out.println("only ( and condition could sit after and");
        }

    }

    void checkOr(List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.Condition || tokenList.get(currentIndex - 1).type == Token.EndParenthesis) == false) {

            System.out.println("only ) and condition could sit before or");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Condition || tokenList.get(currentIndex + 1).type == Token.StartParenthesis) == false) {
            System.out.println("only ( and condition could sit after or");
        }
    }

    void checkStartParenthesis(List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.And || tokenList.get(currentIndex - 1).type == Token.Or || tokenList.get(currentIndex - 1).type == Token.StartParenthesis) == false) {

            System.out.println("only and, or, ( could sit before (");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Condition || tokenList.get(currentIndex + 1).type == Token.StartParenthesis) == false) {
            System.out.println("only ( and condition could sit after (");
        }

    }

    void checkEndParenthesis(List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.Condition || tokenList.get(currentIndex - 1).type == Token.EndParenthesis) == false) {
            System.out.println("only condition and ) could sit before )");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Or || tokenList.get(currentIndex + 1).type == Token.And || tokenList.get(currentIndex + 1).type == Token.EndParenthesis) == false) {
            System.out.println("only ), and, or could sit after )");
        }

    }

    void checkCondition(List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.And || tokenList.get(currentIndex - 1).type == Token.Or || tokenList.get(currentIndex - 1).type == Token.StartParenthesis) == false) {
            System.out.println("only and, or could sit before condition");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Or || tokenList.get(currentIndex + 1).type == Token.And || tokenList.get(currentIndex + 1).type == Token.EndParenthesis) == false) {
            System.out.println("only ), and, or could sit after condition");
        }

    }

    List<Token> getTokenList(List<String> tokenValueList) {
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

    List<String> getTokenValue(String expression) {
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

class Token {
    public static int And = 1;
    public static int Or = 2;
    public static int StartParenthesis = 3;
    public static int EndParenthesis = 4;
    public static int Condition = 5;
    public String value;
    public int type;

    public String toString() {
        if (type == And) {
            return "and";
        } else if (type == Or) {
            return "or";
        } else if (type == StartParenthesis) {
            return "(";
        } else if (type == EndParenthesis) {
            return ")";
        } else if (type == Condition) {
            return value;
        }
        return "";
    }

}