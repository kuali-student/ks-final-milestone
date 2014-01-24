package org.kuali.student.ap.framework.context.support;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.r2.core.acal.infc.Term;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 12/16/13
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:termHelper-test-context.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestTermHelperWithNoData {

    private TermHelper th = null;

    @Before
    public void setUp() throws Throwable {
        DefaultKsapContext.before("student1");
        th = KsapFrameworkServiceLocator.getTermHelper();
    }

    @After
    public void tearDown() throws Throwable {
        DefaultKsapContext.after();
    }

    @Test
    public void testIsThisThingOn() throws Exception {
        assertNotNull("KSAP Context not found",
                KsapFrameworkServiceLocator.getContext());
        assertNotNull("KSAP TermHelper not found",
                th);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCurrentAcademicCalendar() throws Exception {
        th.getCurrentAcademicCalendar();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCurrentTerm() throws Exception {
        th.getCurrentTerm();
    }

    @Test
    public void testGetCurrentTerms() throws Exception {
        List<Term> terms = th.getCurrentTerms();
        assertNotNull(terms);
        assertTrue(terms.size() == 0);
    }

    @Test
    public void testGetCurrentTermsBasedOnKeyDate() throws Exception {
        List<Term> terms = th.getCurrentTermsBasedOnKeyDate();
        assertNotNull(terms);
        assertTrue(terms.size() == 0);
    }

    @Test
    public void testGetCurrentTermsWithPublishedSOC() throws Exception {
        List<Term> terms = th.getCurrentTermsWithPublishedSOC();
        assertNotNull(terms);
        assertTrue(terms.size() == 0);
    }

    @Test
    public void testGetFutureTermsWithPublishedSOC() throws Exception {
        List<Term> terms = th.getFutureTermsWithPublishedSOC();
        assertNotNull(terms);
        assertTrue(terms.size() == 0);
    }
}
