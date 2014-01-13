package org.kuali.student.ap.framework.context.support;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
        try {
            loadData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void loadData() throws Exception {
        TermAndCalDataLoader loader = new TermAndCalDataLoader(KsapFrameworkServiceLocator.getAtpService(),
                KsapFrameworkServiceLocator.getAcademicCalendarService());
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
    @Ignore
    public void testFrontLoadForPlanner() throws Exception {
        //TODO What is this doing?  Do we really need it?
        //th.frontLoadForPlanner();
        fail("Not implemented yet");
    }

    @Test
    @Ignore
    public void testGetCalendarTerms() throws Exception {
        //TODO Need data
        //List<Term> terms = th.getCalendarTerms(term);
        fail("Not implemented yet");
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
        assertTrue(terms.size() > 0);
    }

    @Test
    @Ignore
    public void testGetFirstTermOfAcademicYear() throws Exception {
        fail("Not implemented yet");
    }

    @Test
    @Ignore
    public void testGetNumberOfTermsInAcademicYear() throws Exception {
        fail("Not implemented yet");
    }
    @Test
    @Ignore
    public void testGetTermsInAcademicYear() throws Exception {
        fail("Not implemented yet");
    }
    @Test
    @Ignore
    public void testGetTermNameInAcadmicYear() throws Exception {
        fail("Not implemented yet");
    }
    @Test
    @Ignore
    public void testIsPlanning() throws Exception {
        fail("Not implemented yet");
    }
    @Test
    @Ignore
    public void testIsPublished() throws Exception {
        fail("Not implemented yet");
    }
    @Test
    @Ignore
    public void testIsCompleted() throws Exception {
        fail("Not implemented yet");
    }
    @Test
    @Ignore
    public void testIsCourseOffered() throws Exception {
        fail("Not implemented yet");
    }
    @Test
    @Ignore
    public void testGetPublishedTerms() throws Exception {
        fail("Not implemented yet");
    }
    @Test
    @Ignore
    public void testGetPlanningTerms() throws Exception {
        fail("Not implemented yet");
    }
    @Test
    @Ignore
    public void testGetTermsByDateRange() throws Exception {
        fail("Not implemented yet");
    }
    @Test
    @Ignore
    public void testGetTerm() throws Exception {
        fail("Not implemented yet");
    }
    @Test
    @Ignore
    public void testGetYearTerm() throws Exception {
        fail("Not implemented yet");
    }
    @Test
    @Ignore
    public void testGetStartTermId() throws Exception {
        fail("Not implemented yet");
    }

}
