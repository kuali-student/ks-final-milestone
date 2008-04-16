/**
 * 
 */
package org.kuali.student.rules.brms.parser;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.rules.util.*;
import org.kuali.student.brms.repository.drools.*;
import org.kuali.student.brms.repository.*;
/**
 * @author Rich Diaz
 *
 */
public class GenerateRuleSet {
	String functionString;
	ArrayList<String> ruleAttributes = new ArrayList<String>();
    ArrayList<String> lhs = new ArrayList<String>();
    ArrayList<String> rhs = new ArrayList<String>();
    
    String ruleName;
    String rulesetUuid;
	
	public static void main(String[] args){
		try{
			// Initialize repository, throw exception for now. This code should probably go in another class.
			/*
			DroolsJackrabbitRepository repo;
			repo = new DroolsJackrabbitRepository();
			repo.init();
			
			
			String rulePackage = "org.kuali.student.rules.enrollment";
			// where is brmsRepository instantiated ?
			String rulesetUuid = brmsRepository.createRuleSet(rulePackage, "My package description" );
			*/
			
			GenerateRuleSet grs = new GenerateRuleSet("A0*B4+(C*D)");
			grs.parse();
			
			 //Shutdown (and logout from) the repository:
			 //repo.shutdownRepository();
			 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
    public GenerateRuleSet(String functionString) {
		this.functionString = functionString;
	}
	
	public void parse() throws BRMSRepositoryException {
		Function f = new Function(functionString);
		List<String> funcVars = f.getVariables();
		
		RuleTemplate rt = new RuleTemplate();
		
		// create a rule per function var
		for (String var : funcVars) {
			//System.out.println("The var is " + var );
			rt.setRuleName(ruleName + " " + var);
			rt.setRuleAttributes(ruleAttributes);
			
			// Left Hand Side
			lhs.add("This is coming from the Databse");
			rt.setLHS(lhs);
			
			// Right Hand Side
			rhs.add("Proposition.setProposition(\"" + var + "\", true);");
			rt.setRHS(rhs);
			
			// Merge the template with the cotext set in lhs, rhs, etc ..
			String ruleSourceCode = rt.process("RuleTemplate.vm");
			
			ruleAttributes.clear();
			lhs.clear();
			rhs.clear();
			
			// Add rule to ruleset created in constructor
			//String ruleUuid1 = brmsRepository.createRule(rulesetUuid, ruleName, "", ruleSourceCode, "MyCategory" );
        }
		
		// create the final composite rule for the function
		List<String> symbols = f.getSymbols();
		
		rt.setRuleName(ruleName + " " + "Func");
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
		String ruleSourceCode = rt.process("RuleTemplate.vm");
			
		//ruleAttributes.clear();
		//lhs.clear();
		//rhs.clear();
			
		// Add rule to ruleset created in constructor
		//String ruleUuid1 = brmsRepository.createRule(rulesetUuid, ruleName, "", ruleSourceCode, "MyCategory" );
	}
}
