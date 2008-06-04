package org.kuali.student.rules.BRMSCore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.BRMS.mapper.SubsetProposition;
import org.kuali.student.rules.BRMSCore.entity.ComparisonOperator;

public class PropositionTest{

    Set<String> set1 = new HashSet<String>( Arrays.asList("MATH101, MATH102".split(",")) );

    Set<String> set2 = new HashSet<String>( Arrays.asList("MATH101,MATH103,CHEM101".split(",")));

    
    @Test
    public void testSubset1() throws Exception {               
        
        SubsetProposition<Integer, String> subsetProp = new SubsetProposition<Integer, String>("A", set1, set2 );
        
        Boolean result = subsetProp.apply(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new Integer(1));
        
        System.out.println("Test1 - Report Success:" + subsetProp.getReport().getSuccessMessage());
        Assert.assertEquals(result, true);
    }       
    

    @Test
    public void testSubset2() throws Exception {               
        
        SubsetProposition<Integer, String> subsetProp = new SubsetProposition<Integer, String>("A", set1, set2 );
        
        Boolean result = subsetProp.apply(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, new Integer(2));
        
        System.out.println("\nTest2 - Report Failure:" + subsetProp.getReport().getFailureMessage());
        Assert.assertEquals(result, false);
    }       
   
    
    
}
