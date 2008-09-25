package org.kuali.student.rules.statement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.propositions.AverageProposition;
import org.kuali.student.rules.internal.common.statement.propositions.IntersectionProposition;
import org.kuali.student.rules.internal.common.statement.propositions.SubsetProposition;
import org.kuali.student.rules.internal.common.statement.propositions.SumProposition;

public class PropositionTest {

    private Set<String> set1 = new HashSet<String>(Arrays.asList("CHEM101".split(",")));

    private Set<String> set2 = new HashSet<String>(Arrays.asList("MATH101,MATH103,CHEM101".split(",")));

    private Set<String> set3 = new HashSet<String>(Arrays.asList("BIOL101".split(",")));
    
    private List<BigDecimal> creditList = createList("3.0,6.0,3.0");
    private List<BigDecimal> gradeList = createList("85.0,75.0,80.0");

    private List<BigDecimal> createList(String list) {
    	List<BigDecimal> set = new ArrayList<BigDecimal>();
        for( String s : list.split(",") ) {
        	set.add(new BigDecimal(s.trim()));
        }
        return set;
    }
    
    @Test
    public void testIntersectionTrue() throws Exception {

        IntersectionProposition<String> subsetProp = new IntersectionProposition<String>("A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, "1", set1, set2);

        Boolean result = subsetProp.apply();

        Assert.assertTrue(result);
    }

    @Test
    public void testIntersectionFalse() throws Exception {

        IntersectionProposition<String> subsetProp = new IntersectionProposition<String>("A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, "2", set1, set2);

        Boolean result = subsetProp.apply();

        Assert.assertFalse(result);
    }

    @Test
    public void testSubsetTrue() throws Exception {

        SubsetProposition<String> subsetProp = new SubsetProposition<String>("A", set1, set2);

        Boolean result = subsetProp.apply();

        Assert.assertTrue(result);
    }

    @Test
    public void testSubsetFalse() throws Exception {

        SubsetProposition<String> subsetProp = new SubsetProposition<String>("A", set3, set2);

        Boolean result = subsetProp.apply();

        Assert.assertFalse(result);
    }

    @Test
    public void testSumTrue() throws Exception {

        SumProposition<BigDecimal> subsetProp = new SumProposition<BigDecimal>("A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, "12.0", creditList);

        Boolean result = subsetProp.apply();

        Assert.assertTrue(result);
    }

    @Test
    public void testSumFalse() throws Exception {

        SumProposition<BigDecimal> subsetProp = new SumProposition<BigDecimal>("A",
                ComparisonOperator.LESS_THAN, "12.0", creditList);

        Boolean result = subsetProp.apply();

        Assert.assertFalse(result);
    }

    @Test
    public void testAverageTrue() throws Exception {

        AverageProposition<BigDecimal> subsetProp = new AverageProposition<BigDecimal>("A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, "80.0", gradeList);

        Boolean result = subsetProp.apply();

        Assert.assertTrue(result);
    }

    @Test
    public void testAverageFalse() throws Exception {

    	AverageProposition<BigDecimal> subsetProp = new AverageProposition<BigDecimal>("A",
                ComparisonOperator.EQUAL_TO, "70.0", gradeList);

        Boolean result = subsetProp.apply();

        Assert.assertFalse(result);
    }

}
