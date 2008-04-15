/**
 * 
 */
package org.kuali.student.rules.brms.parser;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.rules.util.*;

/**
 * @author Rich Diaz
 *
 */
public class GenerateRuleSet {
	String functionString;
	ArrayList<String> ruleAttributes = new ArrayList<String>();
    ArrayList<String> lhs = new ArrayList<String>();
    ArrayList<String> rhs = new ArrayList<String>();
	
	public static void main(String[] args){
		GenerateRuleSet grs = new GenerateRuleSet("A0*B4+(C*D)");
		grs.parse();
	}
	
    public GenerateRuleSet(String functionString){
		this.functionString = functionString;
		//BRMSRepository.createRuleSet() ?
	}
	
	public void parse(){
		Function f = new Function(functionString);
		List<String> funcVars = f.getVariables();
		
		RuleTemplate rt = new RuleTemplate();
		
		// create a rule per function var
		for (String var : funcVars) {
			//System.out.println("The var is " + var );
			rt.setRuleName("Enroll Math300 " + var);
			rt.setRuleAttributes(ruleAttributes);
			
			// Left Hand Side
			lhs.add("This is coming from the Databse");
			rt.setLHS(lhs);
			
			// Right Hand Side
			rhs.add("Proposition.setProposition(\"" + var + "\", true);");
			rt.setRHS(rhs);
			
			// Merge the template with the cotext set in lhs, rhs, etc ..
			rt.process("RuleTemplate.vm");
			
			ruleAttributes.clear();
			lhs.clear();
			rhs.clear();
			
			// Put lens code to add rule to rule set below
			// BRMSRepository.createRule() ?
        }
		
		// create the final composite rule for the function
		List<String> symbols = f.getSymbols();
		
		rt.setRuleName("Enroll Math300 " + "Func");
		rt.setRuleAttributes(ruleAttributes);
		
		for (String symbol : symbols) {
			// Left Hand Side
			if ( symbol.equals("+") ){
				lhs.add("||");
        	}
			else if( symbol.equals("*") ){
				lhs.add("&&");
			}
			else if( symbol.equals("(") ){
				lhs.add("(");
			}
			else if( symbol.equals(")") ){
				lhs.add(")");
			}
			else {
				lhs.add("Proposition.getProposition(\"" + symbol + "\")");
			}
			rt.setLHS(lhs);
		}	
			
		// Right Hand Side
		rhs.add("This is the final outcome");
		rt.setRHS(rhs);
			
		// Merge the template with the cotext set in lhs, rhs, etc ..
		rt.process("RuleTemplate.vm");
			
		//ruleAttributes.clear();
		//lhs.clear();
		//rhs.clear();
			
		// Put lens code to add rule to rule set below
		// BRMSRepository.createRule();
	}
}
