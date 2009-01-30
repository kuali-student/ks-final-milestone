package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;

public class IntersectionPropositionTest {
    private Set<String> set1 = new HashSet<String>(Arrays.asList("CHEM101".split(",")));
    private Set<String> set2 = new HashSet<String>(Arrays.asList("MATH101,MATH103,CHEM101".split(",")));
    private Set<String> set3 = new HashSet<String>(Arrays.asList("ENGL101".split(",")));

    @Test
    public void testIntersectionTrue() throws Exception {
        IntersectionProposition<String> prop = new IntersectionProposition<String>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new Integer(1), set1, set2);

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertTrue(prop.getResultValues().contains("CHEM101"));
    }

    @Test
    public void testIntersectionFalse() throws Exception {
        IntersectionProposition<String> prop = new IntersectionProposition<String>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new Integer(2), set1, set2);

        Boolean result = prop.apply();

        Assert.assertFalse(result);
		Assert.assertTrue(prop.getResultValues().contains("CHEM101"));
    }

    @Test
    public void testIntersectionFalse2() throws Exception {
        IntersectionProposition<String> prop = new IntersectionProposition<String>(
        		"A-1", "A",
                ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new Integer(1), set3, set2);

        Boolean result = prop.apply();

        Assert.assertFalse(result);
		Assert.assertTrue(prop.getResultValues().isEmpty());
    }
}
