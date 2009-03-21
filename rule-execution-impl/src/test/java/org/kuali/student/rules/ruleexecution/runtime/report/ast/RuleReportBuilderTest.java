package org.kuali.student.rules.ruleexecution.runtime.report.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.internal.common.statement.propositions.PropositionType;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.statement.report.RuleReport;
import org.kuali.student.rules.ruleexecution.runtime.drools.DroolsKnowledgeBase;
import org.kuali.student.rules.ruleexecution.runtime.drools.SimpleExecutorDroolsImpl;
import org.kuali.student.rules.ruleexecution.runtime.report.ReportBuilder;
import org.kuali.student.rules.ruleexecution.runtime.report.ast.RuleReportBuilderImpl;
import org.kuali.student.rules.ruleexecution.runtime.report.ast.exceptions.MessageBuilderException;

public class RuleReportBuilderTest {
	private String functionalRuleString;

	private PropositionMock intersectionPropA ;
	private PropositionMock intersectionPropB;
	private PropositionMock intersectionPropC;
	private PropositionMock intersectionPropD;

	private PropositionReport propositionReportA = new PropositionReport("A", PropositionType.INTERSECTION);
	private PropositionReport propositionReportB = new PropositionReport("B", PropositionType.INTERSECTION);
	private PropositionReport propositionReportC = new PropositionReport("C", PropositionType.INTERSECTION);
	private PropositionReport propositionReportD = new PropositionReport("D", PropositionType.INTERSECTION);

	private PropositionMock averageProp = new PropositionMock("Average", "A");
	private PropositionMock intersectionProp = new PropositionMock("Intersection", "B");
	private PropositionMock maxProp = new PropositionMock("Max", "C");
	private PropositionMock minProp = new PropositionMock("Min", "D");
	private PropositionMock simpleComparableProp = new PropositionMock("SimpleComparable", "E");
	private PropositionMock subsetProp = new PropositionMock("Subset", "F");
	private PropositionMock sumProp = new PropositionMock("Sum", "G");
	
	private PropositionReport averageReport = new PropositionReport("A", PropositionType.AVERAGE);
	private PropositionReport intersectionReport = new PropositionReport("B", PropositionType.INTERSECTION);
	private PropositionReport maxReport = new PropositionReport("C", PropositionType.MAX);
	private PropositionReport minReport = new PropositionReport("D", PropositionType.MIN);
	private PropositionReport simpleComparableReport = new PropositionReport("E", PropositionType.SIMPLECOMPARABLE);
	private PropositionReport subsetReport = new PropositionReport("F", PropositionType.SUBSET);
	private PropositionReport sumReport = new PropositionReport("G", PropositionType.SUM);

	private ReportBuilder ruleReportBuilder;
    private final static DroolsKnowledgeBase ruleBase = new DroolsKnowledgeBase();
	private static SimpleExecutorDroolsImpl executor = new SimpleExecutorDroolsImpl();

    @BeforeClass
    public static void setUpOnce() throws Exception {
		executor.setEnableStatisticsLogging(true);
    	executor.setRuleBaseCache(ruleBase);
    }
    
    @AfterClass
    public static void tearDownOnce() throws Exception {
    	executor = null;
    }

	@Before
	public void setUp() throws Exception {
	    this.ruleReportBuilder = new RuleReportBuilderImpl(executor);

		Set<String> criteriaSet = new HashSet<String>();
		criteriaSet.add("ENGL100");
	    
		Set<String> factSet = new HashSet<String>();
	    factSet.add("ENGL100");
	    factSet.add("ENGL200");
	    factSet.add("ENGL300");
	    
		List<Double> factList = new ArrayList<Double>();
	    factList.add(new Double(80));
	    factList.add(new Double(85));
	    factList.add(new Double(90));

	    intersectionPropA = new PropositionMock("A1", "A");
	    intersectionPropB = new PropositionMock("B1", "B");
	    intersectionPropC = new PropositionMock("C1", "C");
	    intersectionPropD = new PropositionMock("D1", "D");
	}
	
	@After
	public void tearDown() throws Exception {
	}

	private PropositionReport getProposition(List<PropositionReport> list, String name) {
		for(PropositionReport report : list) {
			if(report.getPropositionName().equals(name)) {
				return report;
			}
		}
		return null;
	}
    
	@Test
	public void testExecuteRuleFailureMessage1()
	{
	    functionalRuleString = "A*B*C*D";
	    
	    PropositionContainer pc = new PropositionContainer();
	    pc.setFunctionalRuleString(functionalRuleString);
	    pc.setRuleResult(false);
	    
	    intersectionPropA.setResult(false);
	    intersectionPropB.setResult(true);
	    intersectionPropC.setResult(true);
	    intersectionPropD.setResult(true);
	    
	    propositionReportA.setMessage("Need MATH 200");
	    propositionReportB.setMessage("Need MATH 110");
	    propositionReportC.setMessage("Need 15 credits or more of 1st year science");
	    propositionReportD.setMessage("Need English 6000");
	    
	    intersectionPropA.setReport(propositionReportA);
	    intersectionPropB.setReport(propositionReportB);
	    intersectionPropC.setReport(propositionReportC);
	    intersectionPropD.setReport(propositionReportD);
	    
	    pc.addProposition(intersectionPropA);
	    pc.addProposition(intersectionPropB);
	    pc.addProposition(intersectionPropC);
	    pc.addProposition(intersectionPropD);
	    
	    String expected = "Need MATH 200";
	    
	    RuleReport ruleReport = ruleReportBuilder.buildReport(pc);
	    String actual = ruleReport.getFailureMessage();
	    
	    Assert.assertEquals(expected, actual);
        Assert.assertEquals(4, ruleReport.getPropositionReports().size());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "A").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "B").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "C").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "D").isSuccessful());
	}
	
	@Test
	public void testExecuteRuleFailureMessageTemplate1()
	{
	    functionalRuleString = "A*B*C*D";
	    
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clu1", "MATH 110");
		map.put("clu2", "MATH 200");
		map.put("clu3", "English 6000");
		map.put("str1", "Need 15 credits or more of 1st year science");

		PropositionContainer pc = new PropositionContainer();
	    pc.setFunctionalRuleString(functionalRuleString);
	    pc.setRuleResult(false);
	    
	    intersectionPropA.setResult(false);
	    intersectionPropB.setResult(true);
	    intersectionPropC.setResult(true);
	    intersectionPropD.setResult(true);
	    
	    propositionReportA.setMessage("Need $clu2");
	    propositionReportB.setMessage("Need $clu1");
	    propositionReportC.setMessage("$str1");
	    propositionReportD.setMessage("Need $clu3");
	    
	    intersectionPropA.setReport(propositionReportA);
	    intersectionPropB.setReport(propositionReportB);
	    intersectionPropC.setReport(propositionReportC);
	    intersectionPropD.setReport(propositionReportD);
	    
	    pc.addProposition(intersectionPropA);
	    pc.addProposition(intersectionPropB);
	    pc.addProposition(intersectionPropC);
	    pc.addProposition(intersectionPropD);
	    
	    String expected = "Need MATH 200";
	    
	    RuleReport ruleReport = ruleReportBuilder.buildReport(pc, map);
	    String actual = ruleReport.getFailureMessage();

	    Assert.assertEquals(expected, actual);
        Assert.assertEquals(4, ruleReport.getPropositionReports().size());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "A").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "B").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "C").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "D").isSuccessful());
	}
	
	@Test
	public void testExecuteRuleFailureMessageTemplate1_Exception()
	{
	    functionalRuleString = "A*B*C*D";
	    
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clu1", "MATH 110");
		map.put("clu2", "MATH 200");
		map.put("clu3", "English 6000");
		map.put("str1", "Need 15 credits or more of 1st year science");

		PropositionContainer pc = new PropositionContainer();
	    pc.setFunctionalRuleString(functionalRuleString);
	    pc.setRuleResult(false);
	    
	    intersectionPropA.setResult(false);
	    intersectionPropB.setResult(true);
	    intersectionPropC.setResult(true);
	    intersectionPropD.setResult(true);
	    
	    // VTL syntax error
	    propositionReportA.setMessage("#set($x = $y");
	    propositionReportB.setMessage("Need $clu1");
	    propositionReportC.setMessage("$str1");
	    propositionReportD.setMessage("Need $clu3");
	    
	    intersectionPropA.setReport(propositionReportA);
	    intersectionPropB.setReport(propositionReportB);
	    intersectionPropC.setReport(propositionReportC);
	    intersectionPropD.setReport(propositionReportD);
	    
	    pc.addProposition(intersectionPropA);
	    pc.addProposition(intersectionPropB);
	    pc.addProposition(intersectionPropC);
	    pc.addProposition(intersectionPropD);
	    
	    try {
	    	ruleReportBuilder.buildReport(pc, map);
			Assert.fail("Building message should have failed due to VTL syntax error");
		} catch(MessageBuilderException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testExecuteRuleFailureMessage2()
	{
	    functionalRuleString = "A+(B*C)+D";

        PropositionContainer pc = new PropositionContainer();
        pc.setFunctionalRuleString(functionalRuleString);
        pc.setRuleResult(false);

        // need to set proposition result and failure message for testing.
        intersectionPropA.setResult(false);
        intersectionPropB.setResult(true);
        intersectionPropC.setResult(false);
        intersectionPropD.setResult(false);
        
        propositionReportA.setMessage("Need MATH 200");
        propositionReportB.setMessage("Need MATH 110");
        propositionReportC.setMessage("Need 15 credits or more of 1st year science");
        propositionReportD.setMessage("Need English 6000");
        
        intersectionPropA.setReport(propositionReportA);
        intersectionPropB.setReport(propositionReportB);
        intersectionPropC.setReport(propositionReportC);
        intersectionPropD.setReport(propositionReportD);
        
        pc.addProposition(intersectionPropA);
        pc.addProposition(intersectionPropB);
        pc.addProposition(intersectionPropC);
        pc.addProposition(intersectionPropD);
        
        String expected = "Need MATH 200 OR Need 15 credits or more of 1st year science OR Need English 6000";
        
        RuleReport ruleReport = ruleReportBuilder.buildReport(pc);
        String actual = ruleReport.getFailureMessage();
        
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(4, ruleReport.getPropositionReports().size());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "A").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "B").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "C").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "D").isSuccessful());
	}
	
	@Test
    public void testExecuteRuleSuccessMessage1()
    {
        functionalRuleString = "A*B*C*D";
        
        PropositionContainer pc = new PropositionContainer();
        pc.setFunctionalRuleString(functionalRuleString);
        pc.setRuleResult(true);
        
        intersectionPropA.setResult(true);
        intersectionPropB.setResult(true);
        intersectionPropC.setResult(true);
        intersectionPropD.setResult(true);
        
        propositionReportA.setMessage("Have MATH 200");
        propositionReportB.setMessage("Have MATH 110");
        propositionReportC.setMessage("Have 15 credits or more of 1st year science");
        propositionReportD.setMessage("Have English 6000");
        
        intersectionPropA.setReport(propositionReportA);
        intersectionPropB.setReport(propositionReportB);
        intersectionPropC.setReport(propositionReportC);
        intersectionPropD.setReport(propositionReportD);
        
        pc.addProposition(intersectionPropA);
        pc.addProposition(intersectionPropB);
        pc.addProposition(intersectionPropC);
        pc.addProposition(intersectionPropD);
        
        String expected = "Have MATH 200 AND Have MATH 110 AND Have 15 credits or more of 1st year science AND Have English 6000";
        
        RuleReport ruleReport = ruleReportBuilder.buildReport(pc);
        String actual = ruleReport.getSuccessMessage();
        
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(4, ruleReport.getPropositionReports().size());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "A").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "B").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "C").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "D").isSuccessful());
    }
	
	@Test
    public void testExecuteRuleSuccessMessageTemplate1()
    {
        functionalRuleString = "A*B*C*D";
        
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clu1", "MATH 110");
		map.put("clu2", "MATH 200");
		map.put("clu3", "English 6000");
		map.put("str1", "Have 15 credits or more of 1st year science");

        PropositionContainer pc = new PropositionContainer();
        pc.setFunctionalRuleString(functionalRuleString);
        pc.setRuleResult(true);

        intersectionPropA.setResult(true);
        intersectionPropB.setResult(true);
        intersectionPropC.setResult(true);
        intersectionPropD.setResult(true);

        propositionReportA.setMessage("Have $clu2");
        propositionReportB.setMessage("Have $clu1");
        propositionReportC.setMessage("$str1");
        propositionReportD.setMessage("Have $clu3");

        intersectionPropA.setReport(propositionReportA);
        intersectionPropB.setReport(propositionReportB);
        intersectionPropC.setReport(propositionReportC);
        intersectionPropD.setReport(propositionReportD);

        pc.addProposition(intersectionPropA);
        pc.addProposition(intersectionPropB);
        pc.addProposition(intersectionPropC);
        pc.addProposition(intersectionPropD);

        String expected = "Have MATH 200 AND Have MATH 110 AND Have 15 credits or more of 1st year science AND Have English 6000";

        RuleReport ruleReport = ruleReportBuilder.buildReport(pc, map);
        String actual = ruleReport.getSuccessMessage();

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(4, ruleReport.getPropositionReports().size());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "A").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "B").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "C").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "D").isSuccessful());
    }
	
    @Test
    public void testExecuteRuleSuccessMessage2()
    {
        functionalRuleString = "A+(B*C)+D";
        
        PropositionContainer pc = new PropositionContainer();
        pc.setFunctionalRuleString(functionalRuleString);
        pc.setRuleResult(true);
        
        // need to set proposition result and failure message for testing.
        intersectionPropA.setResult(true);
        intersectionPropB.setResult(true);
        intersectionPropC.setResult(false);
        intersectionPropD.setResult(true);
        
        propositionReportA.setMessage("Have MATH 200");
        propositionReportB.setMessage("Have MATH 110");
        propositionReportC.setMessage("Have 15 credits or more of 1st year science");
        propositionReportD.setMessage("Have English 6000");
        
        intersectionPropA.setReport(propositionReportA);
        intersectionPropB.setReport(propositionReportB);
        intersectionPropC.setReport(propositionReportC);
        intersectionPropD.setReport(propositionReportD);
        
        pc.addProposition(intersectionPropA);
        pc.addProposition(intersectionPropB);
        pc.addProposition(intersectionPropC);
        pc.addProposition(intersectionPropD);
        
        String expected = "Have MATH 200 OR Have English 6000";
        
        RuleReport ruleReport = ruleReportBuilder.buildReport(pc);
        String actual = ruleReport.getSuccessMessage();
        
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(4, ruleReport.getPropositionReports().size());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "A").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "B").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "C").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "D").isSuccessful());
    }

    @Test
    public void testExecuteComplexRuleSuccessMessage()
    {
        functionalRuleString = "(A+(B*C)+D)*(E+F+G)";
        
        PropositionContainer pc = new PropositionContainer();
        pc.setFunctionalRuleString(functionalRuleString);
        pc.setRuleResult(true);
        
        // need to set proposition result and failure message for testing.
        averageProp.setResult(true);
        intersectionProp.setResult(true);
        maxProp.setResult(false);
        minProp.setResult(false);
        simpleComparableProp.setResult(false);
        subsetProp.setResult(true);
        sumProp.setResult(false);
        
        averageReport.setMessage("Have a minimum average of 80");
        intersectionReport.setMessage("Have MATH 110");
        maxReport.setMessage("Have Max 15 credits");
        minReport.setMessage("Have Min 10 credits");
        simpleComparableReport.setMessage("Have English 100");
        subsetReport.setMessage("Have English 200");
        sumReport.setMessage("Have 15 credits or more of 1st year science");
        
        averageProp.setReport(averageReport);
        intersectionProp.setReport(intersectionReport);
        maxProp.setReport(maxReport);
        minProp.setReport(minReport);
        simpleComparableProp.setReport(simpleComparableReport);
        subsetProp.setReport(subsetReport);
        sumProp.setReport(sumReport);
        
        pc.addProposition(averageProp);
        pc.addProposition(intersectionProp);
        pc.addProposition(maxProp);
        pc.addProposition(minProp);
        pc.addProposition(simpleComparableProp);
        pc.addProposition(subsetProp);
        pc.addProposition(sumProp);
        
        String expected = "Have a minimum average of 80 AND Have English 200";
        
        RuleReport ruleReport = ruleReportBuilder.buildReport(pc);
        String actual = ruleReport.getSuccessMessage();

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(7, ruleReport.getPropositionReports().size());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "A").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "B").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "C").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "D").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "E").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "F").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "G").isSuccessful());
    }

    @Test
    public void testExecuteComplexRuleFailureMessage()
    {
        functionalRuleString = "(A+(B*C)+D)*(E+F+G)";
        
        PropositionContainer pc = new PropositionContainer();
        pc.setFunctionalRuleString(functionalRuleString);
        pc.setRuleResult(false);
        
        // need to set proposition result and failure message for testing.
        averageProp.setResult(true);
        intersectionProp.setResult(true);
        maxProp.setResult(false);
        minProp.setResult(false);
        simpleComparableProp.setResult(false);
        subsetProp.setResult(false);
        sumProp.setResult(false);
        
        averageReport.setMessage("Need a minimum average of 80");
        intersectionReport.setMessage("Need MATH 110");
        maxReport.setMessage("Need Max 15 credits");
        minReport.setMessage("Need Min 10 credits");
        simpleComparableReport.setMessage("Need English 100");
        subsetReport.setMessage("Need English 200");
        sumReport.setMessage("Need 15 credits or more of 1st year science");
        
        averageProp.setReport(averageReport);
        intersectionProp.setReport(intersectionReport);
        maxProp.setReport(maxReport);
        minProp.setReport(minReport);
        simpleComparableProp.setReport(simpleComparableReport);
        subsetProp.setReport(subsetReport);
        sumProp.setReport(sumReport);
        
        pc.addProposition(averageProp);
        pc.addProposition(intersectionProp);
        pc.addProposition(maxProp);
        pc.addProposition(minProp);
        pc.addProposition(simpleComparableProp);
        pc.addProposition(subsetProp);
        pc.addProposition(sumProp);
        
        String expected = "Need English 100 OR Need English 200 OR Need 15 credits or more of 1st year science";
        
        RuleReport ruleReport = ruleReportBuilder.buildReport(pc);
        String actual = ruleReport.getFailureMessage();

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(7, ruleReport.getPropositionReports().size());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "A").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "B").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "C").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "D").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "E").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "F").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "G").isSuccessful());
    }
}
