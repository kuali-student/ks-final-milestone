package org.kuali.student.rules.statement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.statement.SubsetProposition;


public class PropositionTest{

    Set<String> set1 = new HashSet<String>( Arrays.asList("CHEM101".split(",")) );

    Set<String> set2 = new HashSet<String>( Arrays.asList("MATH101,MATH103,CHEM101".split(",")));

    
    @Test
    public void testSubset1() throws Exception {               
        
        SubsetProposition<String> subsetProp = new SubsetProposition<String>("A", ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, "1", set1, set2 );
        
        Boolean result = subsetProp.apply();
        
        System.out.println("Test1 - Report Success:" + subsetProp.getReport().getSuccessMessage());
        Assert.assertEquals(result, true);
    }       
    

    @Test
    public void testSubset2() throws Exception {               
        
        SubsetProposition<String> subsetProp = new SubsetProposition<String>("A", ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, "2", set1, set2 );
        
        Boolean result = subsetProp.apply();
        
        System.out.println("\nTest2 - Report Failure:" + subsetProp.getReport().getFailureMessage());
        Assert.assertEquals(result, false);
    }       
   
    
    
}
