/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.util;

/**
 * @author Kuali Student Team
 */
public class ExpressionToken implements Cloneable {

    public static final int OPERATOR_AND = 1;
    public static final int OPERATOR_OR = 2;
    public static final int PARENTHESIS_START = 3;
    public static final int PARENTHESIS_END = 4;
    public static final int CONDITION = 5;

    private String value;
    private int type;
    private String tokenID = "";

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static boolean isOperator(int type){
        if((type==ExpressionToken.OPERATOR_AND)||(type==ExpressionToken.OPERATOR_OR)){
            return true;
        }
        return false;
    }

    public static boolean isParenthesis(int type){
        if((type==ExpressionToken.PARENTHESIS_START)||(type==PARENTHESIS_END)){
            return true;
        }
        return false;
    }

    public static ExpressionToken createAndToken(){
        ExpressionToken t = new ExpressionToken();
        t.type = OPERATOR_AND;
        t.value = "and";
        return t;
    }

    public static ExpressionToken createOrToken(){
        ExpressionToken t = new ExpressionToken();
        t.type = OPERATOR_OR;
        t.value = "or";
        return t;
    }

    public ExpressionToken toggleAndOr(){
        ExpressionToken t = new ExpressionToken ();
        if(type == OPERATOR_AND){
            t.type = OPERATOR_OR;
            t.value = "Or";

        }else if(type == OPERATOR_OR){
            t.type = OPERATOR_AND;
            t.value = "And";
        }
        return t;
    }
    
    public boolean equals(Object obj){
        if(!(obj instanceof ExpressionToken)){
            return false;
        }
        ExpressionToken t = (ExpressionToken)obj;
        if(t.value == null){
            return false;
        }
        if(t.value.equals(value) && t.type == type){
            return true;
        }
        return false;
    }
    
    public int hashCode(){
    	int hash =1;
    	hash = hash * 31 + Integer.valueOf(type).hashCode();
    	hash = hash * 31 + (value == null ? 0 : value.hashCode());
    	return hash;
    }
    
    public ExpressionToken clone(){
        ExpressionToken t = new ExpressionToken();
        t.type = type;
        t.value = value;
        return t;
    }

    public String toString() {
        if (type == OPERATOR_AND) {
            return "and";
        } else if (type == OPERATOR_OR) {
            return "or";
        } else if (type == PARENTHESIS_START) {
            return "(";
        } else if (type == PARENTHESIS_END) {
            return ")";
        } else if (type == CONDITION) {
            return value;
        }
        return "";
    }
}
