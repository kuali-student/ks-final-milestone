package org.kuali.student.common.ui.client.widgets.table;

public class Token {
    public static Token createAndToken(){
        Token t = new Token();
        t.type = And;
        t.value = "and";
        return t;
    }
    public static Token createOrToken(){
        Token t = new Token();
        t.type = Or;
        t.value = "or";
        return t;
    }    
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