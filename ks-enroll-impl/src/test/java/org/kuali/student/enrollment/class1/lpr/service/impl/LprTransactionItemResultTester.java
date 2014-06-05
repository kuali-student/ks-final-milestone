/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl;

import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Helps create a dynamic 
 * @author nwright
 */
public class LprTransactionItemResultTester {

   

    public void check(LprTransactionItemInfo expected, LprTransactionItemInfo actual) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected == null) {
            fail ("expected was null but actual was not null");
        }
        if (actual == null) {
            fail ("expected was not null but expected was null");
        }
        assertEquals(expected.getResultingLprId(), actual.getResultingLprId());
    }

}
