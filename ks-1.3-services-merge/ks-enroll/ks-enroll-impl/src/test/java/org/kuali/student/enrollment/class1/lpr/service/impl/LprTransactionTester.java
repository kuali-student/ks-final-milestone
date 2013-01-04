/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.test.util.IdEntityTester;
import org.kuali.student.enrollment.test.util.ListOfStringTester;

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
