package org.kuali.student.rules.BRMSCore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

@Transactional
/**
 * An interface to BRMS Meta Data that allows to store and retrieve a business rule
 * @author Zdenek Zraly (zdenek.zraly@ub.ca)
 */
public class BRMSMetaData {	
	
	public static final String FACT_CONTAINER = "AcademicRecord";
	
	@Autowired
    private FunctionalBusinessRuleDAO businessRuleDAO;
	
	long id;
	
	public String getRuleFunctionString(FunctionalBusinessRule rule) {
			
		Collection <RuleElement> ruleElements = rule.getRuleElements();
			
		String functionString = new String("");
		char proposition = 'A';
		for (RuleElement ruleElement : ruleElements) {
			functionString += " ";
			switch (ruleElement.getOperation()) {
				case AND_TYPE:
					functionString += "AND";
					break;
				case LPAREN_TYPE:
					functionString += "(";
					break;
				case NOT_TYPE:
					functionString += "NOT";
					break;
				case OR_TYPE:
					functionString += "OR";
					break;
				case PROPOSITION_TYPE:
					functionString += proposition;
//					propositions.put(proposition, )
					proposition++;
					
					break;
				case RPAREN_TYPE:
					functionString += ")";
					break;
				case XOR_TYPE:
					functionString += "XOR";
					break;	
				default:
					functionString += "Not found";
			}
		}
		return functionString;
	}

	public static void main(String [ ] args) throws Exception {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		
		// add a shutdown hook for the above context... 
        ctx.registerShutdownHook();	
		       
        BRMSMetaData metadata = (BRMSMetaData) ctx.getBean("BRMSMetaData");
        
		//insert our demo MetaData
        try {
            metadata.insertBusinessRuleMetadata();
        } catch (Exception e) {
            System.out.println("Cannot insert duplicate record:" + e.getMessage());
        }
        
        //metadata.getFunctionalBusinessRuleTest("PR 40244");
        
        
        FunctionalBusinessRule rule = metadata.businessRuleDAO.lookupBusinessRuleID("PR 40244"); 
        //FunctionalBusinessRule rule = businessRuleDAO.lookupBusinessRule(id); 
        
        String functionString = metadata.getRuleFunctionString(rule);
        System.out.println("Function String: " + functionString);
        
        
        
	}	

	/**
	 * @return the businessRuleSetDAO
	 */
	public final FunctionalBusinessRuleDAO getBusinessRuleSetDAO() {
		return businessRuleDAO;
	}

	/**
	 * @param businessRuleSetDAO the businessRuleSetDAO to set
	 */
	public final void setBusinessRuleSetDAO(
			FunctionalBusinessRuleDAO businessRuleSetDAO) {
		this.businessRuleDAO = businessRuleSetDAO;
	}	
	
    public void insertBusinessRuleMetadata() throws ConstraintViolationException {

		int ordinalPosition = 1;
		RuleElement ruleElement = null;
		RuleProposition ruleProp = null;
		LeftHandSide leftSide = null;
		RightHandSide rightSide = null;
		Operator operator = null;
		ArrayList<String> criteria = null;
		ArrayList<String> facts = null;
		
		//setup business rule meta data and main rule entity
		RuleMetaData metaData = new RuleMetaData("Tom Smith", new Date(), "", null, new Date(), new Date(), "1.1", "active");
		
		//we keep this entity empty for now
		BusinessRuleEvaluation businessRuleEvaluation = new BusinessRuleEvaluation();
		
		//create basic rule structure
		FunctionalBusinessRule busRule = new FunctionalBusinessRule("PR CHEM 200",
				"enrollment prerequisites for Chemistry 200", "PR 40244", metaData, businessRuleEvaluation);

		
		// =======================================
		// ( Completed any 2 of (Math 101, 102, 103) OR Completed any 3 of (Chem 101, 102, 103) )
		// =======================================
		ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		// Completed any 2 of (Math 101, 102, 103)
		facts = new ArrayList<String>();		
		facts.add("MATH101, MATH102, MATH103");
		leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", criteria);
		operator = new Operator(ComparisonOperatorType.GREATER_THAN_OR_EQUAL_TO_TYPE);
		criteria = new ArrayList<String>();		
		criteria.add("2");		
		rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);				
		ruleProp = new RuleProposition("Math Prerequisite", "enumeration of required Math courses", leftSide, operator, rightSide);
		ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		// OR
		ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		// Completed any 3 of (Chem 101, 102, 103)
		facts = new ArrayList<String>();		
		facts.add("CHEM101, CHEM102, CHEM103");
		leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", criteria);
		operator = new Operator(ComparisonOperatorType.GREATER_THAN_OR_EQUAL_TO_TYPE);
		criteria = new ArrayList<String>();		
		criteria.add("3");		
		rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);						
		ruleProp = new RuleProposition("Chemistry Prerequisite", "enumeration of required Chem courses", leftSide, operator, rightSide);
		ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);

		
		// =======================================
		// AND Accumulated 10 creds in Basic Core
		// =======================================
		ruleElement = new RuleElement(RuleElementType.AND_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		facts = new ArrayList<String>();		
		facts.add("ENG100, PSYC102, MATH103");
		leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCoreUnits", criteria);
		operator = new Operator(ComparisonOperatorType.GREATER_THAN_OR_EQUAL_TO_TYPE);
		criteria = new ArrayList<String>();		
		criteria.add("2");		
		rightSide = new RightHandSide("requiredCoreUnits", "java.lang.Integer", criteria);				
		ruleProp = new RuleProposition("Required Core Units", "Number of core units", leftSide, operator, rightSide);
		ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		
		// =======================================		
		// AND ( obtained instructor approval OR has Senior class standing )
		// =======================================		
		ruleElement = new RuleElement(RuleElementType.AND_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);		
		
		ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		// obtained instructor approval
		facts = new ArrayList<String>();		
		facts.add("");
		leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "isApprovedByInstructor", criteria);
		operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
		criteria = new ArrayList<String>();		
		criteria.add("true");		
		rightSide = new RightHandSide("instructorApproval", "java.lang.Boolean", criteria);				
		ruleProp = new RuleProposition("Instructor Approval", "approval granted", leftSide, operator, rightSide);		
		ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);		
	
		// OR		
		ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		// has Senior class standing
		facts = new ArrayList<String>();		
		facts.add("");
		leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "hasSeniorStanding", criteria);
		operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
		criteria = new ArrayList<String>();		
		criteria.add("true");		
		rightSide = new RightHandSide("seniorStanding", "java.lang.Boolean", criteria);				
		ruleProp = new RuleProposition("Senior Class Standing", "Senior standing achieved", leftSide, operator, rightSide);	
		ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);		
		
		ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		//ruleElementDAO.createRuleElement(ruleElement);
		busRule.addRuleElement(ruleElement);				
		
		businessRuleDAO.createBusinessRule(busRule);	
		
		id = busRule.getId();
		System.out.println("Rule ID:" + id);
		System.out.println("Rule IDname:" + busRule.getRuleSetIdentifier());	
	}
    
    protected String[] getConfigLocations() {
    	return new String[] {"classpath:/application-context.xml"};
    }
    
    private String mapMetaRuleToDroolRule(String metaRule) {
        
        if (metaRule.indexOf("Completed any") != -1) { 
            return "Person( $academicRecord : academicRecord )" + System.getProperty("line.separator") +       
                   "//Count using the Drools count accumulate function" + System.getProperty("line.separator") +
                   "countCLUMatches1 : Long( longValue >= 1 )" + System.getProperty("line.separator") +
                   "from accumulate( LUIPersonAssociation( canonicalLUName in ( \"CPSC219\", \"CPSC124\" ), $clu : canonicalLU )" +
                   System.getProperty("line.separator") + "from $academicRecord.records, count( $clu ) )";         
        }
        return "";        
    }
    
    public FunctionalBusinessRule getFunctionalBusinessRuleTest(String ruleID) throws Exception {
        
        FunctionalBusinessRule rule = businessRuleDAO.lookupBusinessRuleID(ruleID);         
        //FunctionalBusinessRule ruleSet = businessRuleSetDAO.lookupBusinessRuleSet(ruleSetId);
        
        if (rule != null) {
            System.out.println("Found rule: " + rule.getName());        
        } else {
            System.out.println("Rule " + ruleID + " not found");
        }
        
        return rule;
    }    
    
}
