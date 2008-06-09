package org.kuali.student.rules.runtime.ast;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.rules.runtime.ast.GenerateRuleFailureMessage;
import org.kuali.student.rules.statement.*;

import static org.junit.Assert.assertEquals;

public class GenerateRuleFailureMessageTest {

	private String functionalRuleString;
	
	private SubsetProposition<Integer, String> subsetPropA = new SubsetProposition<Integer, String>("A", null, null, null, null );
	private SubsetProposition<Integer, String> subsetPropB = new SubsetProposition<Integer, String>("B", null, null, null, null );
	private SubsetProposition<Integer, String> subsetPropC = new SubsetProposition<Integer, String>("C", null, null, null, null );
	private SubsetProposition<Integer, String> subsetPropD = new SubsetProposition<Integer, String>("D", null, null, null, null );
	
	private PropositionReport propositionReportA = new PropositionReport();
	private PropositionReport propositionReportB = new PropositionReport();
	private PropositionReport propositionReportC = new PropositionReport();
	private PropositionReport propositionReportD = new PropositionReport();
    
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testExecuteRuleFailureMessage1()
	{
	    functionalRuleString = "A*B*C*D";
	    
	    PropositionContainer pc = new PropositionContainer();
	    pc.setFunctionalRuleString(functionalRuleString);
	    
	    subsetPropA.setResult(false);
	    subsetPropB.setResult(true);
	    subsetPropC.setResult(true);
	    subsetPropD.setResult(true);
	    
	    propositionReportA.setFailureMessage("Need MATH 200");
	    propositionReportB.setFailureMessage("Need MATH 110");
	    propositionReportC.setFailureMessage("Need 15 credits or more of 1st year science");
	    propositionReportD.setFailureMessage("Need English 6000");
	    
	    subsetPropA.setReport(propositionReportA);
	    subsetPropB.setReport(propositionReportB);
	    subsetPropC.setReport(propositionReportC);
	    subsetPropD.setReport(propositionReportD);
	    
	    pc.setProposition(subsetPropA.getPropositionName(), subsetPropA);
	    pc.setProposition(subsetPropB.getPropositionName(), subsetPropB);
	    pc.setProposition(subsetPropC.getPropositionName(), subsetPropC);
	    pc.setProposition(subsetPropD.getPropositionName(), subsetPropD);
	    
	    String expected = "Need MATH 200";
        String actual = GenerateRuleFailureMessage.executeRule(pc);
	    assertEquals(expected, actual);
	    
	}
	
	@Test
	public void testExecuteRuleFailureMessage2()
	{
	    // New code for PropositionContainer starts here
	    functionalRuleString = "A+(B*C)+D";
        
        PropositionContainer pc = new PropositionContainer();
        pc.setFunctionalRuleString(functionalRuleString);
        
        // need to set proposition result and failure message for testing.
        subsetPropA.setResult(false);
        subsetPropB.setResult(true);
        subsetPropC.setResult(false);
        subsetPropD.setResult(false);
        
        propositionReportA.setFailureMessage("Need MATH 200");
        propositionReportB.setFailureMessage("Need MATH 110");
        propositionReportC.setFailureMessage("Need 15 credits or more of 1st year science");
        propositionReportD.setFailureMessage("Need English 6000");
        
        subsetPropA.setReport(propositionReportA);
        subsetPropB.setReport(propositionReportB);
        subsetPropC.setReport(propositionReportC);
        subsetPropD.setReport(propositionReportD);
        
        pc.setProposition(subsetPropA.getPropositionName(), subsetPropA);
        pc.setProposition(subsetPropB.getPropositionName(), subsetPropB);
        pc.setProposition(subsetPropC.getPropositionName(), subsetPropC);
        pc.setProposition(subsetPropD.getPropositionName(), subsetPropD);
        
        String expected = "Need MATH 200 OR Need 15 credits or more of 1st year science OR Need English 6000";
        String actual = GenerateRuleFailureMessage.executeRule(pc);
        assertEquals(expected, actual);
        
        
/*	    functionalRuleString = "A+(B*C)+D";
    	
		nodeValueMap.put("A", false);
		nodeValueMap.put("B", true);
		nodeValueMap.put("C", false);
		nodeValueMap.put("D", false);
		
		nodeFailureMessageMap.put("A", "Need MATH 200");
		nodeFailureMessageMap.put("B", "Need MATH 110");
		nodeFailureMessageMap.put("C", "Need 15 credits or more of 1st year science");
		nodeFailureMessageMap.put("D", "Need English 6000");
		
		String expected = "Need MATH 200 OR Need 15 credits or more of 1st year science OR Need English 6000";
		String actual = GenerateRuleFailureMessage.executeRule(functionalRuleString, nodeValueMap, nodeFailureMessageMap);
		
		assertEquals(expected, actual);*/
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
