package org.kuali.student.rules.ruleexecution.runtime.report.ast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.internal.common.statement.propositions.AverageProposition;
import org.kuali.student.rules.internal.common.statement.propositions.IntersectionProposition;
import org.kuali.student.rules.internal.common.statement.propositions.MaxProposition;
import org.kuali.student.rules.internal.common.statement.propositions.MinProposition;
import org.kuali.student.rules.internal.common.statement.propositions.PropositionType;
import org.kuali.student.rules.internal.common.statement.propositions.SimpleComparableProposition;
import org.kuali.student.rules.internal.common.statement.propositions.SubsetProposition;
import org.kuali.student.rules.internal.common.statement.propositions.SumProposition;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.statement.report.RuleReport;
import org.kuali.student.rules.ruleexecution.runtime.drools.DroolsRuleBase;
import org.kuali.student.rules.ruleexecution.runtime.drools.SimpleExecutorDroolsImpl;
import org.kuali.student.rules.ruleexecution.runtime.report.ast.RuleReportBuilder;

public class RuleReportBuilderTest {
	private String functionalRuleString;

	private IntersectionProposition<String> intersectionPropA = new IntersectionProposition<String>("A1", "A", null, null, null, null);
	private IntersectionProposition<String> intersectionPropB = new IntersectionProposition<String>("B1", "B", null, null, null, null);
	private IntersectionProposition<String> intersectionPropC = new IntersectionProposition<String>("C1", "C", null, null, null, null);
	private IntersectionProposition<String> intersectionPropD = new IntersectionProposition<String>("D1", "D", null, null, null, null);

	private PropositionReport propositionReportA = new PropositionReport("A", PropositionType.INTERSECTION);
	private PropositionReport propositionReportB = new PropositionReport("B", PropositionType.INTERSECTION);
	private PropositionReport propositionReportC = new PropositionReport("C", PropositionType.INTERSECTION);
	private PropositionReport propositionReportD = new PropositionReport("D", PropositionType.INTERSECTION);

	private AverageProposition<Double> averageProp;
	private IntersectionProposition<String> intersectionProp;
	private MaxProposition<Double> maxProp;
	private MinProposition<Double> minProp;
	private SimpleComparableProposition<Double> simpleComparableProp;
	private SubsetProposition<String> subsetProp;
	private SumProposition<Double> sumProp;

	private PropositionReport averageReport;
	private PropositionReport intersectionReport;
	private PropositionReport maxReport;
	private PropositionReport minReport;
	private PropositionReport simpleComparableReport;
	private PropositionReport subsetReport;
	private PropositionReport sumReport;

	private RuleReportBuilder ruleReportBuilder;
    private final static DroolsRuleBase ruleBase = new DroolsRuleBase();
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
	    this.ruleReportBuilder = new RuleReportBuilder(executor);

		Set<String> factSet = new HashSet<String>();
	    factSet.add("ENGL100");
	    factSet.add("ENGL200");
	    factSet.add("ENGL300");
	    
		List<Double> factList = new ArrayList<Double>();
	    factList.add(new Double(80));
	    factList.add(new Double(85));
	    factList.add(new Double(90));

		averageProp = new AverageProposition<Double>("Average", "A", null, null, factList);
		intersectionProp = new IntersectionProposition<String>("Intersection", "B", null, null, null, null);
		maxProp = new MaxProposition<Double>("Max", "C", null, null, factList);
		minProp = new MinProposition<Double>("Min", "D", null, null, factList);
		simpleComparableProp = new SimpleComparableProposition<Double>("SimpleComparable", "E", null, null, null);
		subsetProp = new SubsetProposition<String>("Subset", "F", factSet, factSet);
		sumProp = new SumProposition<Double>("Sum", "G", null, null, factList);

		averageReport = new PropositionReport("A", PropositionType.AVERAGE);
		intersectionReport = new PropositionReport("B", PropositionType.INTERSECTION);
		maxReport = new PropositionReport("C", PropositionType.MAX);
		minReport = new PropositionReport("D", PropositionType.MIN);
		simpleComparableReport = new PropositionReport("E", PropositionType.SIMPLECOMPARABLE);
		subsetReport = new PropositionReport("F", PropositionType.SUBSET);
		sumReport = new PropositionReport("G", PropositionType.SUM);
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
	    
	    propositionReportA.setFailureMessage("Need MATH 200");
	    propositionReportB.setFailureMessage("Need MATH 110");
	    propositionReportC.setFailureMessage("Need 15 credits or more of 1st year science");
	    propositionReportD.setFailureMessage("Need English 6000");
	    
	    intersectionPropA.setReport(propositionReportA);
	    intersectionPropB.setReport(propositionReportB);
	    intersectionPropC.setReport(propositionReportC);
	    intersectionPropD.setReport(propositionReportD);
	    
	    pc.addProposition(intersectionPropA);
	    pc.addProposition(intersectionPropB);
	    pc.addProposition(intersectionPropC);
	    pc.addProposition(intersectionPropD);
	    
	    String expected = "Need MATH 200";
	    
	    RuleReport ruleReport = ruleReportBuilder.execute(pc);
	    String actual = ruleReport.getFailureMessage();
	    
	    Assert.assertEquals(expected, actual);
        Assert.assertEquals(4, ruleReport.getPropositionReports().size());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "A").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "B").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "C").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "D").isSuccessful());
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
        
        propositionReportA.setFailureMessage("Need MATH 200");
        propositionReportB.setFailureMessage("Need MATH 110");
        propositionReportC.setFailureMessage("Need 15 credits or more of 1st year science");
        propositionReportD.setFailureMessage("Need English 6000");
        
        intersectionPropA.setReport(propositionReportA);
        intersectionPropB.setReport(propositionReportB);
        intersectionPropC.setReport(propositionReportC);
        intersectionPropD.setReport(propositionReportD);
        
        pc.addProposition(intersectionPropA);
        pc.addProposition(intersectionPropB);
        pc.addProposition(intersectionPropC);
        pc.addProposition(intersectionPropD);
        
        String expected = "Need MATH 200 OR Need 15 credits or more of 1st year science OR Need English 6000";
        
        RuleReport ruleReport = ruleReportBuilder.execute(pc);
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
        
        propositionReportA.setSuccessMessage("Have MATH 200");
        propositionReportB.setSuccessMessage("Have MATH 110");
        propositionReportC.setSuccessMessage("Have 15 credits or more of 1st year science");
        propositionReportD.setSuccessMessage("Have English 6000");
        
        intersectionPropA.setReport(propositionReportA);
        intersectionPropB.setReport(propositionReportB);
        intersectionPropC.setReport(propositionReportC);
        intersectionPropD.setReport(propositionReportD);
        
        pc.addProposition(intersectionPropA);
        pc.addProposition(intersectionPropB);
        pc.addProposition(intersectionPropC);
        pc.addProposition(intersectionPropD);
        
        String expected = "Have MATH 200 AND Have MATH 110 AND Have 15 credits or more of 1st year science AND Have English 6000";
        
        RuleReport ruleReport = ruleReportBuilder.execute(pc);
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
        
        propositionReportA.setSuccessMessage("Have MATH 200");
        propositionReportB.setSuccessMessage("Have MATH 110");
        propositionReportC.setSuccessMessage("Have 15 credits or more of 1st year science");
        propositionReportD.setSuccessMessage("Have English 6000");
        
        intersectionPropA.setReport(propositionReportA);
        intersectionPropB.setReport(propositionReportB);
        intersectionPropC.setReport(propositionReportC);
        intersectionPropD.setReport(propositionReportD);
        
        pc.addProposition(intersectionPropA);
        pc.addProposition(intersectionPropB);
        pc.addProposition(intersectionPropC);
        pc.addProposition(intersectionPropD);
        
        String expected = "Have MATH 200 OR Have English 6000";
        
        RuleReport ruleReport = ruleReportBuilder.execute(pc);
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
        
        averageReport.setSuccessMessage("Have a minimum average of 80");
        intersectionReport.setSuccessMessage("Have MATH 110");
        maxReport.setSuccessMessage("Have Max 15 credits");
        minReport.setSuccessMessage("Have Min 10 credits");
        simpleComparableReport.setSuccessMessage("Have English 100");
        subsetReport.setSuccessMessage("Have English 200");
        sumReport.setSuccessMessage("Have 15 credits or more of 1st year science");
        
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
        
        RuleReport ruleReport = ruleReportBuilder.execute(pc);
        String actual = ruleReport.getSuccessMessage();

        System.out.println("Rule Report: "+actual);
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
        
        averageReport.setFailureMessage("Need a minimum average of 80");
        intersectionReport.setFailureMessage("Need MATH 110");
        maxReport.setFailureMessage("Need Max 15 credits");
        minReport.setFailureMessage("Need Min 10 credits");
        simpleComparableReport.setFailureMessage("Need English 100");
        subsetReport.setFailureMessage("Need English 200");
        sumReport.setFailureMessage("Need 15 credits or more of 1st year science");
        
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
        
        RuleReport ruleReport = ruleReportBuilder.execute(pc);
        String actual = ruleReport.getFailureMessage();

        System.out.println("Rule Report: "+actual);
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
