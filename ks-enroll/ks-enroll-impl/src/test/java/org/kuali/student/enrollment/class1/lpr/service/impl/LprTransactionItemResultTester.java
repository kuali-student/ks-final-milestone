/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl;

import static org.junit.Assert.*;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemResultInfo;

/**
 * Helps create a dynamic 
 * @author nwright
 */
public class LprTransactionItemResultTester {

   

    public void check(LprTransactionItemResultInfo expected, LprTransactionItemResultInfo actual) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected == null) {
            fail ("expected was null but actual was not null");
        }
        if (actual == null) {
            fail ("expected was not null but expected was null");
        }
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getResultingLprId(), actual.getResultingLprId());
        assertEquals(expected.getMessage(), actual.getMessage());        
    }

}
