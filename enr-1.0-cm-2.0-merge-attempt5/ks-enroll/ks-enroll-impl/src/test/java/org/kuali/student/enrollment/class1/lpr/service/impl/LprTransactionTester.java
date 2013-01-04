/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl;

import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;

import static org.junit.Assert.assertEquals;

/**
 * Helps create a dynamic 
 * @author nwright
 */
public class LprTransactionTester {

    public void check(LprTransactionInfo expected, LprTransactionInfo actual) {
        assertEquals(expected.getRequestingPersonId(), actual.getRequestingPersonId());
        assertEquals(expected.getAtpId(), actual.getAtpId());
        new LprTransactionItemTester ().check(expected.getLprTransactionItems(), actual.getLprTransactionItems());
    }
}
