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

    public void check (RelationshipInfo orig, RelationshipInfo info) {
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
    }

   
}
