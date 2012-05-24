/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.test.util;

import org.kuali.student.r2.common.dto.RelationshipInfo;
import static org.junit.Assert.*;

/**
 * Helps create a dynamic 
 * @author nwright
 */
public class RelationshipTester {

    public void check (RelationshipInfo expected, RelationshipInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals(expected.getExpirationDate(), actual.getExpirationDate());
    }

   
}
