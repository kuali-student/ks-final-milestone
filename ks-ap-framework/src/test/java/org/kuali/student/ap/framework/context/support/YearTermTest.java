package org.kuali.student.ap.framework.context.support;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:termHelper-test-context.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class YearTermTest {

    YearTerm testYearTerm;

    @Before
    public void setUp() throws Throwable {
        DefaultKsapContext.before("student1");
        testYearTerm = new DefaultYearTerm("kuali.atp.Fall3123", AtpServiceConstants.ATP_FALL_TYPE_KEY,3123);
        loadData();
    }

    @After
    public void tearDown() throws Throwable {
        DefaultKsapContext.after();
    }

    private void loadData() throws Exception {
        TermAndCalDataLoader loader = new TermAndCalDataLoader(KsapFrameworkServiceLocator.getAtpService(),
                KsapFrameworkServiceLocator.getAcademicCalendarService(), KsapFrameworkServiceLocator.getCourseOfferingSetService(), KsapFrameworkServiceLocator.getTypeService());
        loader.loadData();
    }

    @Test
    public void testIsThisThingOn() throws Exception {
        assertNotNull("KSAP Context not found",
                KsapFrameworkServiceLocator.getContext());
        assertNotNull("KSAP TermHelper not found",
                KsapFrameworkServiceLocator.getTermHelper());
    }

    @Test
    public void testRetrieveInformation() throws Exception {
        assertEquals("kuali.atp.Fall3123",testYearTerm.getTermId());
        assertEquals(AtpServiceConstants.ATP_FALL_TYPE_KEY,testYearTerm.getTermType());
        assertEquals(3123 , testYearTerm.getYear());
    }

    @Test
    public void testCompareTo() throws Exception {
        YearTerm spring = new DefaultYearTerm("kuali.atp.Spring3123", AtpServiceConstants.ATP_SPRING_TYPE_KEY,3123);
        YearTerm fall = new DefaultYearTerm("kuali.atp.Fall3123", AtpServiceConstants.ATP_FALL_TYPE_KEY,3123);
        YearTerm winter = new DefaultYearTerm("kuali.atp.Winter3123", AtpServiceConstants.ATP_WINTER_TYPE_KEY,3123);
        assertEquals(0, testYearTerm.compareTo(fall));
        assertEquals(1, testYearTerm.compareTo(spring));
        assertEquals(-1,testYearTerm.compareTo(winter) );
    }

    @Test
    public void testTermNames() throws Exception {
        assertEquals("Fall 3123",testYearTerm.getLongName());
        assertEquals("Fall",testYearTerm.getTermName());
        assertEquals("FA 3123",testYearTerm.getShortName());
        assertEquals("FA 23",testYearTerm.getAbbrivation());

    }

}
