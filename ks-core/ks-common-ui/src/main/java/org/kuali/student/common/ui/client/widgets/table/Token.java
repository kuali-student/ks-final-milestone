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

package org.kuali.student.common.ui.client.widgets.table;

public class Token implements Cloneable{
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

    public Token toggleAndOr(){
        Token t = new Token ();
        if(type == And){
            t.type = Or;
            t.value = "Or";

        }else if(type == Or){
            t.type = And;
            t.value = "And";
        }
        return t;
    }
    
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
    public boolean equals(Object obj){
        if(obj instanceof Token == false){
            return false;
        }
        Token t = (Token)obj;
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
    	hash = hash * 31 + new Integer(type).hashCode();
    	hash = hash * 31 + (value == null ? 0 : value.hashCode());
    	return hash;
    }
    public Token clone(){
        Token t = new Token();
        t.type = type;
        t.value = value;
        return t;
    }
}
