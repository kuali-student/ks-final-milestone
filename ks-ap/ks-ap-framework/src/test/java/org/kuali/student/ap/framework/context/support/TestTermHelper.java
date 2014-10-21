package org.kuali.student.ap.framework.context.support;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.r2.core.acal.infc.AcademicCalendar;
import org.kuali.student.r2.core.acal.infc.Term;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
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
public class TestTermHelper {

    private TermHelper th = null;

    @Before
    public void setUp() throws Throwable {
        DefaultKsapContext.before("student1");
        th = KsapFrameworkServiceLocator.getTermHelper();
        loadData();
    }

    private void loadData() throws Exception {
        TermAndCalDataLoader loader = new TermAndCalDataLoader(KsapFrameworkServiceLocator.getAtpService(),
                KsapFrameworkServiceLocator.getAcademicCalendarService(), KsapFrameworkServiceLocator.getCourseOfferingSetService(),
                KsapFrameworkServiceLocator.getTypeService());
        loader.loadData();
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

    @Test
    public void testGetCurrentAcademicCalendar() throws Exception {
        AcademicCalendar acal = th.getCurrentAcademicCalendar();
        assertNotNull(acal);
    }

    @Test
    public void testGetCurrentTerm() throws Exception {
        Term t = th.getCurrentTerm();
        assertNotNull(t);
    }

    @Test
    public void testGetCurrentTerms() throws Exception {
        List<Term> terms = th.getCurrentTerms();
        assertNotNull(terms);
        assertTrue(!terms.isEmpty());
    }

    @Test
    public void testGetCurrentTermsBasedOnKeyDate() throws Exception {
        List<Term> terms = th.getCurrentTermsBasedOnKeyDate();
        assertNotNull(terms);
        assertTrue(!terms.isEmpty());
    }

    @Test
    public void testGetCurrentTermsWithPublishedSOC() throws Exception {
        List<Term> terms = th.getCurrentTermsWithPublishedSOC();
        assertNotNull(terms);
        assertTrue(!terms.isEmpty());
    }

    @Test
    public void testGetFutureTermsWithPublishedSOC() throws Exception {
        List<Term> terms = th.getFutureTermsWithPublishedSOC();
        assertNotNull(terms);
        assertTrue(!terms.isEmpty());
    }

    @Test
    public void testGetFirstTermIdOfCurrentAcademicYear() throws Exception {
        String termId = th.getFirstTermIdOfCurrentAcademicYear();
        assertNotNull(termId);
        assertEquals("ksapAtpNow2", termId);
    }

    @Test
    public void testSortBySocPublishedDateDecending() throws Exception {
        List<Term> terms = th.getCurrentTermsWithPublishedSOC();
        terms.addAll(th.getFutureTermsWithPublishedSOC());

        assertEquals("Incorrect list size", 2, terms.size());

        terms = th.sortTermsBySocReleaseDate(terms, false);

        assertEquals("Bad term sort", "ksapAtpFuture", terms.get(0).getId());
        assertEquals("Bad term sort", "ksapAtpNow2", terms.get(1).getId());
    }

    @Test
    public void testSortByStartDate() throws Exception {
        List<Term> terms = th.getCurrentTermsWithPublishedSOC();
        terms.addAll(th.getFutureTermsWithPublishedSOC());

        assertEquals("Incorrect list size", 2, terms.size());

        terms = th.sortTermsByStartDate(terms, true);

        assertEquals("Bad term sort", "ksapAtpNow2", terms.get(0).getId());
        assertEquals("Bad term sort", "ksapAtpFuture", terms.get(1).getId());
    }

}
