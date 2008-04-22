/**
 * 
 */
package org.kuali.student.rules.brms.parser;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.rules.util.*;
import org.kuali.student.brms.repository.drools.*;
import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.brms.repository.rule.BuilderResultList;
import org.kuali.student.brms.repository.rule.RuleSet;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.*;
/**
 * @author Rich Diaz
 *
 */
public class GenerateRuleSet {
	String functionString;
	ArrayList<String> ruleAttributes = new ArrayList<String>();
    ArrayList<String> lhsDB = new ArrayList<String>();
    ArrayList<String> rhsDB = new ArrayList<String>();
    ArrayList<String> lhsConst = new ArrayList<String>();
    ArrayList<String> rhsConst = new ArrayList<String>();
    
    RuleEngineRepository brmsRepository;
    String rulesetUuid;
    String ruleName;
    String description;
    String category;
    
	
	public static void main(String[] args){
		try{
			// Initialize repository
			DroolsJackrabbitRepository repo;
			URL url = DroolsJackrabbitRepository.class.getResource("/repository");
			System.out.println("the url " + url.toString());
			repo = new DroolsJackrabbitRepository(url);
			repo.initialize();
			
			repo.clearData();
			RuleEngineRepository brmsRepository = new RuleEngineRepositoryDroolsImpl( repo.getRepository() );
			String rulePackage = "org.kuali.student.rules.enrollment";
			String rulesetUuid = brmsRepository.createRuleSet(rulePackage, "My package description" );
			
			
			GenerateRuleSet grs = new GenerateRuleSet("A0*B4+(C*D)");
			grs.setRuleEngineRepository(brmsRepository);
			grs.setRuleSetUuid(rulesetUuid);
			grs.setRuleName("Enrollment Physics 5000");
			grs.setRuleDescription("");
			grs.setRuleCategory(null);
			
			grs.setRuleAttributes(null);
			//grs.setLhsDB(null);
			//grs.setRhsDB(null);
			
			grs.parse();
			
			// compile rule and save in repository
			BuilderResultList results = brmsRepository.compileRuleSet(rulesetUuid);
			
			// load rule from repo and print
			RuleSet ruleset = brmsRepository.loadRuleSet(rulesetUuid);
			List<Rule> rules = ruleset.getRules();
			for (Rule myrule : rules) {
			    System.out.println("the rule " + myrule.getContent() );
			}
			//Shutdown (and logout from) the repository:
			repo.shutdownRepository();
			 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
    public GenerateRuleSet(String functionString) {
		this.functionString = functionString;
	}
	
	public void parse() throws RuleEngineRepositoryException {
		Function f = new Function(functionString);
		List<String> funcVars = f.getVariables();
		
		RuleTemplate rt = new RuleTemplate();
		String extRuleName;
		
		// create a rule per function var
		for (String var : funcVars) {
			//System.out.println("The var is " + var );
			extRuleName = ruleName + " (" + var + ")";
			rt.setRuleName(extRuleName);
			rt.setRuleAttributes(ruleAttributes);
			
			// Left Hand Side
			
			lhsDB.add("This is coming from the Databse");
			rt.setLHS(lhsDB);
			
			// Right Hand Side
			rhsConst.add("Propositions.setProposition(\"" + var + "\", true);");
			rt.setRHS(rhsConst);
			
			// Merge the template with the context set in lhs, rhs, etc ..
			String ruleSourceCode = rt.process("RuleTemplate.vm");
			
			// depending on how we set the ruleAttributes and lhsDB we may not need to clear.
			// for example if lhsDB is set as an array (before parse() is called)
			// then we just iterate over it and do not clear.
			ruleAttributes.clear();
			lhsDB.clear();
			// always clear
			rhsConst.clear();
			
			// Add rule to ruleset created in constructor
			String ruleUuid1 = brmsRepository.createRule(rulesetUuid, extRuleName, description, ruleSourceCode, category );
        }
		
		// create the final composite rule for the function
		List<String> symbols = f.getSymbols();
		extRuleName = ruleName + " " + "Func";
		rt.setRuleName(extRuleName);
		rt.setRuleAttributes(ruleAttributes);
		
		for (String symbol : symbols) {
			// Left Hand Side
			if ( symbol.equals("+") ){
				lhsConst.add("||");
        	}
			else if( symbol.equals("*") ){
			    lhsConst.add("&&");
			}
			else if( symbol.equals("(") ){
			    lhsConst.add("(");
			}
			else if( symbol.equals(")") ){
			    lhsConst.add(")");
			}
			else {
			    lhsConst.add("Propositions.getProposition(\"" + symbol + "\")");
			}
			rt.setLHS(lhsConst);
		}	
			
		// Right Hand Side
		rhsDB.add("This is the final outcome");
		rt.setRHS(rhsDB);
			
		// Merge the template with the cotext set in lhs, rhs, etc ..
		String ruleSourceCode = rt.process("RuleTemplate.vm");
			
		//ruleAttributes.clear();
		//lhs.clear();
		//rhs.clear();
			
		// Add rule to ruleset created in constructor
		String ruleUuid1 = brmsRepository.createRule(rulesetUuid, extRuleName, description, ruleSourceCode, category );
	}
	
	public void setRuleEngineRepository(RuleEngineRepository brmsRepository) {
	    this.brmsRepository = brmsRepository;
	}
	
	public void setRuleSetUuid(String rulesetUuid) {
        this.rulesetUuid = rulesetUuid;
    }
	
	public void setRuleName(String ruleName){
        this.ruleName = ruleName;
    }
	
	public void setRuleDescription(String description) {
	    this.description = description;
	}
	
	public void setRuleCategory(String category){
	    this.category = category;
	}
	
	public void setRuleAttributes(ArrayList<String> ruleAttributes) {
	    if (ruleAttributes != null){
	        this.ruleAttributes = ruleAttributes;
	    }
	}
	
	public void setLhsDB(ArrayList<String> lhsDB) {
	    this.lhsDB = lhsDB;
	}
	
	public void setRhsDB(ArrayList<String> rhsDB) {
        this.rhsDB = rhsDB;
    }
}
