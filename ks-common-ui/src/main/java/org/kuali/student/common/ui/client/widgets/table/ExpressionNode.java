package org.kuali.student.common.ui.client.widgets.table;

public  class ExpressionNode {
    Token token;
    String expression;
    
    public String toString(){
        return expression;
    }
    
    public ExpressionNode clone(){
        ExpressionNode e = new ExpressionNode();
        e.expression = expression;
        e.token = token;
        return e;
    } 
}