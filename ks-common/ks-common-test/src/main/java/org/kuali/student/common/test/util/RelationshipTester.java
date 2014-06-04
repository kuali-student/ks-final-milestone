/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.common.test.util;

import static org.junit.Assert.assertEquals;

import org.kuali.student.r2.common.dto.RelationshipInfo;

/**
 * Helps create a dynamic 
 * @author nwright
 */
public class RelationshipTester {

    public void check (RelationshipInfo expected, RelationshipInfo actual) {
    	
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
    }

   
}
