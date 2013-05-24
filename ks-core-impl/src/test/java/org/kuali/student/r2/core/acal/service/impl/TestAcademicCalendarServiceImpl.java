package org.kuali.student.r2.core.acal.service.impl;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.AcalEventInfo;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.infc.AcademicCalendar;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.atp.service.impl.AtpTestDataLoader;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:acal-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestAcademicCalendarServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(TestAcademicCalendarServiceImpl.class);

    @Autowired
    @Qualifier("acalServiceAuthDecorator")
    private AcademicCalendarService acalService;

    @Autowired
    @Qualifier("atpServiceAuthorization")
    private AtpService atpService;

    @Autowired
    @Qualifier("stateService")
    private StateService stateService;

    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            loadData();
            createStateTestData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @After
    public void teardown() throws Exception {
        destroyStateTestData();
    }

    private void createStateTestData() throws Exception {

        // ATP state
        LifecycleInfo atpLifecycle = addLifecycle( AtpServiceConstants.ATP_LIFECYCLE_KEY );
        addState( atpLifecycle, AtpServiceConstants.ATP_DRAFT_STATE_KEY, true );
        addState( atpLifecycle, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, false );
    }

    private void destroyStateTestData() throws Exception {

        // ATP state
        cleanupStateTestData( AtpServiceConstants.ATP_DRAFT_STATE_KEY );
        cleanupStateTestData( AtpServiceConstants.ATP_OFFICIAL_STATE_KEY );
        cleanupLifecycleTestData( AtpServiceConstants.ATP_LIFECYCLE_KEY );
    }

    private LifecycleInfo addLifecycle( String name ) throws Exception {

        LifecycleInfo origLife = new LifecycleInfo();
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted</b> lifecycle for testing purposes");
        rti.setPlain("Plain lifecycle for testing purposes");
        origLife.setDescr(rti);
        origLife.setKey( name );
        origLife.setName( "TEST_NAME" );
        origLife.setRefObjectUri( "TEST_URI" );
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        origLife.getAttributes().add(attr);

        return stateService.createLifecycle(origLife.getKey(), origLife, callContext);
    }

    private StateInfo addState( LifecycleInfo lifecycleInfo, String state, boolean isInitialState ) throws Exception {

        StateInfo orig = new StateInfo();
        orig.setKey(state);
        orig.setLifecycleKey(lifecycleInfo.getKey());
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted again</b> state for testing purposes");
        rti.setPlain("Plain state again for testing purposes");
        orig.setDescr(rti);
        orig.setName("Testing state");
        Date effDate = new Date();
        orig.setEffectiveDate(effDate);
        Calendar cal = Calendar.getInstance();
        cal.set(2022, 8, 23);
        orig.setExpirationDate(cal.getTime());
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        orig.getAttributes().add(attr);
        orig.setIsInitialState(isInitialState);

        return stateService.createState(orig.getLifecycleKey(), orig.getKey(), orig, callContext);
    }

    private void cleanupStateTestData( String state ) {
        try {
            stateService.deleteState( state, callContext );
        } catch( Exception e ) { }
    }

    private void cleanupLifecycleTestData( String name ) {
        try {
            stateService.deleteLifecycle(name, callContext);
        } catch( Exception e ) { }
    }

    private void loadData() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, VersionMismatchException, AlreadyExistsException {
        AtpTestDataLoader loader = new AtpTestDataLoader(this.atpService);
        loader.loadData();
    }

    @Test
    public void testAcademicCalendarServiceSetup() {
        assertNotNull(acalService);
    }

    @Test
    public void testGetAcademicCalendarFromDerby() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AcademicCalendarInfo acal = acalService.getAcademicCalendar("testAtpId1", callContext);
        assertNotNull(acal);
        assertEquals("testAtpId1", acal.getId());
        assertEquals("testAtp1", acal.getName());
        assertEquals("Desc 101", acal.getDescr().getPlain());
        assertEquals(AtpServiceConstants.ATP_DRAFT_STATE_KEY, acal.getStateKey());
        assertEquals(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, acal.getTypeKey());
    }

    @Test
    public void testGetAcademicCalendar() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException {
        AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setName("testAcal");
        acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        populateRequiredFields(acal);
        AcademicCalendarInfo created = acalService.createAcademicCalendar(null, acal, callContext);
        assertNotNull(created);
        assertNotNull(created.getId());

        AcademicCalendarInfo existed = acalService.getAcademicCalendar(created.getId(), callContext);

        assertNotNull(existed);
        assertNotNull(existed.getId());
        assertEquals("testAcal", existed.getName());
    }

    // Ignored until KSENROLL-4206 is resolved, since the validator will not work until then
    @Test
    @Ignore
    public void testValidateAcademicCalendar() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setId("testAcalId1");
        acal.setName("testAcal1");
        List<String> ccKeys = new ArrayList<String>();
        ccKeys.add("testAtpId2");

        acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);

        try {
            List<ValidationResultInfo> vris = acalService.validateAcademicCalendar("FULL_VALIDATION", null, acal, callContext);
            if (!vris.isEmpty()) {
                StringBuilder buf = new StringBuilder();
                buf.append(vris.size()).append(" validaton errors found did not expect any");
                for (ValidationResultInfo vri : vris) {
                    buf.append("\n");
                    buf.append(vri.getElement()).append(": ").append(vri.getMessage());
                }
                fail(buf.toString());
            }
            assertTrue(vris.isEmpty());
        } catch (OperationFailedException ex) {
            // dictionary not ready, this is expected
        } catch (Exception ex) {
            // fail("exception from service call :" + ex.getMessage());
            // TODO: test exception aspect & get dictionary ready, this is
            // expected
        }
    }

    @Test
    public void testCreateAcademicCalendar() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, ReadOnlyException {
        AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setName("testAcal1");
        List<String> ccKeys = new ArrayList<String>();
        // Assume holidayCalendarIds picking up from dropdown and valid(already
        // in db)
        ccKeys.add("testAtpId2");
        acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        populateRequiredFields(acal);
        try {
            AcademicCalendarInfo created = acalService.createAcademicCalendar(null, acal, callContext);
            assertNotNull(created);
            assertNotNull(created.getId());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testUpdateAcademicCalendar() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {

        AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setName("testNewAcal");
        acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        populateRequiredFields(acal);
        AcademicCalendarInfo created = acalService.createAcademicCalendar(null, acal, callContext);
        assertNotNull(created);
        assertNotNull(created.getId());

        AcademicCalendarInfo existed = acalService.getAcademicCalendar(created.getId(), callContext);

        assertNotNull(existed);

        existed.setName("testUpdatedAcal");
        // TODO - need actual ProgramTypeKey attribute in test database sql
        AcademicCalendarInfo updated = acalService.updateAcademicCalendar(created.getId(), existed, callContext);
        assertEquals("testUpdatedAcal", updated.getName());
    }

    @Test
    public void testDeleteAcademicCalendar() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setName("testDeletedAcal");
        acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        populateRequiredFields(acal);
        try {
            AcademicCalendarInfo created = acalService.createAcademicCalendar(null, acal, callContext);
            assertNotNull(created);
            assertNotNull(created.getId());

            StatusInfo ret = acalService.deleteAcademicCalendar(created.getId(), callContext);
            assertTrue(ret.getIsSuccess());

            AcademicCalendarInfo existed = acalService.getAcademicCalendar("testDeletedAcalId", callContext);
            assertNull(existed);
        } catch (DoesNotExistException dnee) {
            // this is expected
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testGetAcademicCalendarsByStartYear() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            AlreadyExistsException {
        List<AcademicCalendarInfo> acalInfos = acalService.getAcademicCalendarsByStartYear(1980, callContext);
        assertNotNull("No Calendars returned.", acalInfos);

        Set<String> acalNames = new HashSet<String>();
        for (AcademicCalendarInfo acalInfo : acalInfos) {
            acalNames.add(acalInfo.getName());
        }

        Set<String> expected = new HashSet<String>();
        expected.add("testEdgeAtpId1");
        expected.add("testEdgeAtpId4");
        expected.add("testEdgeAtpId6");
        expected.add("testEdgeAtpId7");
        expected.add("testEdgeAtpId8");

        Set<String> unexpected = new HashSet<String>();
        unexpected.add("testEdgeAtpId2");
        unexpected.add("testEdgeAtpId3");
        unexpected.add("testEdgeAtpId5");
        unexpected.add("testEdgeAtpId9");
        unexpected.add("testEdgeAtpId10");

        for (String acalName : expected) {
            assertTrue("Expected calendar returned: " + acalName, acalNames.contains(acalName));
        }
        for (String acalName : unexpected) {
            assertFalse("Unexpected calendar returned: " + acalName, acalNames.contains(acalName));
        }
    }

    @Test
    public void testValidateTerm() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        TermInfo term = new TermInfo();
        term.setId("testTermId");
        term.setName("testTerm");
        term.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        term.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);

        try {
            List<ValidationResultInfo> vri = acalService.validateTerm("SKIP_REQUREDNESS_VALIDATIONS", null, term, callContext);
            assertTrue(vri.isEmpty());
        } catch (OperationFailedException ex) {
            // dictionary not ready, this is expected
        } catch (Exception ex) {
            // fail("exception from service call :" + ex.getMessage());
            // TODO: test exception aspect & get dictionary ready, this is
            // expected
        }
    }

    @Test
    public void testCreateAndGetTerm() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException {
        TermInfo term = new TermInfo();
        term.setName("testNewTerm");
        term.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        term.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        populateRequiredFields(term);
        term.setStartDate(new Date(term.getStartDate().getTime()));

        TermInfo created;

        created = acalService.createTerm(AtpServiceConstants.ATP_FALL_TYPE_KEY, term, callContext);
        TermInfo temp = acalService.getTerm(created.getId(), callContext);
        assertNotNull(temp);
        assertNotNull(temp.getId());

        // check the generated code, based on formula in https://jira.kuali.org/browse/KSENROLL-1146
        String expectedCode = DateUtil.formatDate(term.getStartDate(), "yyyy") + "08";
        assertEquals(expectedCode, created.getCode());

        term.setId(null);
        term.setStartDate(new Date(term.getStartDate().getTime()));
        term.setTypeKey(AtpServiceConstants.ATP_WINTER_TYPE_KEY);
        created = acalService.createTerm(AtpServiceConstants.ATP_WINTER_TYPE_KEY, term, callContext);
        assertNotNull(created);
        assertNotNull(created.getId());

        TermInfo existed = acalService.getTerm(created.getId(), callContext);

        assertNotNull(existed);
        assertNotNull(existed.getId());
        assertEquals("testNewTerm", existed.getName());
    }

    @Test
    public void testAddTermToAcademicCalendar() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            AlreadyExistsException {
        try {
            try {
                acalService.addTermToAcademicCalendar("testAtpId1", "testTermId1", callContext);
            } catch (AlreadyExistsException ex) {
                // expected);
            }

            StatusInfo status = acalService.addTermToAcademicCalendar("testAtpId1", "testTermId2", callContext);
            assertTrue(status.getIsSuccess());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }

        List<TermInfo> terms = acalService.getTermsForAcademicCalendar("testAtpId1", callContext);
        assertEquals(2, terms.size());
    }

    @Test
    public void testGetTermToAcademicCalendar() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            AlreadyExistsException {
        try {
            try {
                acalService.getTermsForAcademicCalendar("testTermId1", callContext);
            } catch (OperationFailedException ex) {
                // expected because it's not an acal
            }

            List<TermInfo> terms = acalService.getTermsForAcademicCalendar("testAtpId1", callContext);
            assertNotNull(terms);

            // make sure an expected term is in the list of returned terms
            boolean found = false;
            for (TermInfo term : terms) {
                if (term.getId().equals("testTermId1")) {
                    found = true;
                    break;
                }
            }

            assertTrue(found);

        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testGetContainingTerms() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TermInfo> results = acalService.getContainingTerms("termRelationTestingTerm2", callContext);

        assertNotNull(results);
        assertEquals(1, results.size());

        List<String> expectedIds = new ArrayList<String>();
        expectedIds.add("termRelationTestingTerm1");

        // check that all the expected Ids came back
        for (TermInfo info : results) {
            expectedIds.remove(info.getId());
        }

        assertTrue(expectedIds.isEmpty());

        List<TermInfo> fakeResults = null;

        try {
            fakeResults = acalService.getContainingTerms("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(fakeResults);
        }
    }

    @Test
    public void testGetTermIdsByType() throws Exception {
        String expectedTermType = "kuali.atp.type.HalfFall1";

        List<String> termIds = acalService.getTermIdsByType(expectedTermType, callContext);

        assertTrue(termIds.contains("termRelationTestingTerm4"));

        String expectedEmptyTermType = "kuali.atp.type.SessionG2";

        termIds = acalService.getTermIdsByType(expectedEmptyTermType, callContext);

        assertTrue(termIds.isEmpty());

        termIds = acalService.getTermIdsByType("fakeTypeKey", callContext);
        // fake key returns an empty list as well
        assertTrue("Term IDs should be empty", termIds.isEmpty());
    }

    @Test
    public void testGetTermsByKeyList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> termIds = new ArrayList<String>();
        termIds.addAll(Arrays.asList("termRelationTestingTerm1", "termRelationTestingTerm2"));

        List<TermInfo> terms = acalService.getTermsByIds(termIds, callContext);

        assertNotNull(terms);
        assertEquals(termIds.size(), terms.size());

        // check that all the expected Ids came back
        for (TermInfo info : terms) {
            termIds.remove(info.getId());
        }

        assertTrue(termIds.isEmpty());

        // now make sure an exception is thrown for any not found keys

        List<String> fakeKeys = Arrays.asList("termRelationTestingTerm1", "fakeKey1", "fakeKey2");

        List<TermInfo> shouldBeNull = null;
        try {
            shouldBeNull = acalService.getTermsByIds(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }

    @Test
    public void testGetTermsForAcademicCalendar() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TermInfo> results = acalService.getTermsForAcademicCalendar("termRelationTestingAcal1", callContext);

        assertNotNull(results);
        assertEquals(1, results.size());

        List<String> expectedIds = new ArrayList<String>();
        expectedIds.add("termRelationTestingTerm1");

        // check that all the expected Ids came back
        for (TermInfo info : results) {
            expectedIds.remove(info.getId());
        }

        assertTrue(expectedIds.isEmpty());

        List<TermInfo> fakeResults = null;

        try {
            fakeResults = acalService.getTermsForAcademicCalendar("fakeKey", callContext);
            assertEquals(0, fakeResults.size());
        } catch (DoesNotExistException e) {
            assertNull(fakeResults);
        }
    }

    @Test
    public void testGetIncludedTermsInTerm() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TermInfo> results = acalService.getIncludedTermsInTerm("termRelationTestingTerm1", callContext);

        assertNotNull(results);
        assertEquals(1, results.size());

        List<String> expectedIds = new ArrayList<String>();
        expectedIds.add("termRelationTestingTerm2");

        // check that all the expected Ids came back
        for (TermInfo info : results) {
            expectedIds.remove(info.getId());
        }

        assertTrue(expectedIds.isEmpty());

        List<TermInfo> fakeResults = null;

        try {
            fakeResults = acalService.getIncludedTermsInTerm("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(fakeResults);
        }
    }

    @Test
    public void testSearchForTerms() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", "testAtpId1"));
        QueryByCriteria qbc = qbcBuilder.build();
        try {
            List<TermInfo> terms = acalService.searchForTerms(qbc, callContext);
            assertNotNull(terms);
            assertEquals(1, terms.size());
            TermInfo term = terms.get(0);
            assertEquals("testAtpId1", term.getId());
            assertEquals("testAtp1", term.getName());
            assertEquals("Desc 101", term.getDescr().getPlain());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetTermState() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StateInfo result = acalService.getTermState(AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);

        assertNotNull(result);
        assertEquals(result.getName(), "Testing state");

        StateInfo fakeState = null;
        try {
            fakeState = acalService.getTermState("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(fakeState);
        }
    }

    @Test
    public void testGetTermStates() throws InvalidParameterException, MissingParameterException, OperationFailedException, DoesNotExistException, PermissionDeniedException {
        List<StateInfo> result = acalService.getTermStates(callContext);

        assertNotNull(result);
        assertTrue(!result.isEmpty());

        List<String> expectedIds = new ArrayList<String>();
        expectedIds.addAll(Arrays.asList(AtpServiceConstants.ATP_DRAFT_STATE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY));

        // ensure we have all the expected Ids in our list
        assertEquals(expectedIds.size(), result.size());

        for (StateInfo state : result) {
            expectedIds.remove(state.getKey());
        }

        assertTrue(expectedIds.isEmpty());
    }

    @Test
    public void testGetTermType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        TypeInfo result = acalService.getTermType(AtpServiceConstants.ATP_HALF_FALL_1_TYPE_KEY, callContext);

        assertNotNull(result);
        assertEquals(result.getName(), "Fall Half-Semester 1");

        TypeInfo fakeType = null;
        try {
            fakeType = acalService.getTermType("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(fakeType);
        }
    }

    @Test
    public void testGetTermTypes() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeInfo> result = acalService.getTermTypes(callContext);

        assertNotNull(result);
        assertTrue(!result.isEmpty());
    }

    @Test
    public void testGetTermTypesForAcademicCalendarType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeInfo> results = acalService.getTermTypesForAcademicCalendarType(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, callContext);

        assertNotNull(results);
        List<String> expectedIds = new ArrayList<String>(2);
        expectedIds.addAll(Arrays.asList("kuali.atp.type.Fall", "kuali.atp.type.Spring"));

        // check that all the expected Ids came back
        for (TypeInfo info : results) {
            expectedIds.remove(info.getKey());
        }

        assertTrue(expectedIds.isEmpty());

        List<TypeInfo> fakeTypes = null;
        try {
            fakeTypes = acalService.getTermTypesForAcademicCalendarType("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(fakeTypes);
        }

    }

    @Test
    public void testGetTermTypesForTermType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO
        List<TypeInfo> results = acalService.getTermTypesForTermType(AtpServiceConstants.ATP_SPRING_TYPE_KEY, callContext);

        assertNotNull(results);
        List<String> expectedIds = new ArrayList<String>(2);
        expectedIds.addAll(Arrays.asList(AtpServiceConstants.ATP_HALF_SPRING_1_TYPE_KEY, AtpServiceConstants.ATP_HALF_SPRING_2_TYPE_KEY, AtpServiceConstants.ATP_SPRING_BREAK_TYPE_KEY));

        // check that all the expected Ids came back
        for (TypeInfo info : results) {
            expectedIds.remove(info.getKey());
        }

        assertTrue(expectedIds.isEmpty());

        List<TypeInfo> fakeTypes = null;
        try {
            fakeTypes = acalService.getTermTypesForTermType("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(fakeTypes);
        }

        List<TypeInfo> expectedEmpty = acalService.getTermTypesForTermType(AtpServiceConstants.ATP_SESSION_G2_TYPE_KEY, callContext);

        assertTrue(expectedEmpty == null || expectedEmpty.isEmpty());
    }

    @Test
    public void testRemoveTermFromAcademicCalendar() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = acalService.removeTermFromAcademicCalendar("termRelationTestingAcal2", "termRelationTestingTerm2", callContext);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());

        // retrieve the terms for the acal and make sure it does not include the
        // removed term
        List<TermInfo> results = acalService.getTermsForAcademicCalendar("termRelationTestingAcal2", callContext);

        if (results != null) {
            for (TermInfo term : results) {
                assertTrue(!term.getId().equals("termRelationTestingTerm2"));
            }
        }

        StatusInfo noStatus = null;
        try {
            noStatus = acalService.removeTermFromAcademicCalendar("termRelationTestingAcal2", "fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(noStatus);
        }
    }

    @Test
    public void testRemoveTermFromTerm() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = acalService.removeTermFromTerm("termRelationTestingTerm3", "termRelationTestingTerm4", callContext);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());

        // retrieve the terms for the parent term and make sure it does not
        // include the removed term
        List<TermInfo> results = acalService.getIncludedTermsInTerm("termRelationTestingTerm3", callContext);

        assertTrue(results == null || results.isEmpty());

        // try to remove the same term again, should get a DoesNotExistException
        StatusInfo noRepeatStatus = null;
        try {
            noRepeatStatus = acalService.removeTermFromTerm("termRelationTestingTerm3", "termRelationTestingTerm4", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(noRepeatStatus);
        }

        StatusInfo noStatus = null;
        try {
            noStatus = acalService.removeTermFromTerm("termRelationTestingTerm3", "fakeKey", callContext);
            fail("Did not get a InvalidParameterException when expected");
        } catch (InvalidParameterException e) {
            assertNull(noStatus);
        }
    }

    @Test
    public void testUpdateTerm() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException,
            VersionMismatchException, ReadOnlyException {

        // test updating an invalid term
        TermInfo blankTerm = new TermInfo();
        blankTerm.setId("fakeKey");
        blankTerm.setTypeKey("fakeType");
        blankTerm.setStateKey("fakeState");
        blankTerm.setStartDate(new Date());
        blankTerm.setEndDate(new Date());

        TermInfo fakeTerm = null;
        try {
            fakeTerm = acalService.updateTerm("fakeKey", blankTerm, callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(fakeTerm);
        }

        TermInfo existing = acalService.getTerm("termRelationTestingTerm3", callContext);
        String updatedName = "updated " + existing.getName();
        String originalCode = existing.getCode();

        existing.setName(updatedName);

        TermInfo revised = acalService.updateTerm("termRelationTestingTerm3", existing, callContext);

        assertNotNull(revised);
        assertEquals(revised.getId(), existing.getId());

        TermInfo retrieved = acalService.getTerm("termRelationTestingTerm3", callContext);

        assertNotNull(retrieved);
        assertEquals(retrieved.getName(), updatedName);

        // ensure new calculated code is the same as the pre-update code
        assertEquals(originalCode, retrieved.getCode());

    }

    @Test
    public void testDeleteTerm() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = acalService.deleteTerm("termRelationTestingTermDelete", callContext);

        assertTrue(status.getIsSuccess());

        StatusInfo noStatus = null;
        try {
            noStatus = acalService.deleteTerm("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(noStatus);
        }

        // ensure the delete prevents future gets
        TermInfo shouldBeNull = null;
        try {
            shouldBeNull = acalService.getTerm("termRelationTestingTermDelete", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }

    @Test
    public void testAddTermToTerm() throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StatusInfo status = acalService.addTermToTerm("termRelationTestingTerm5", "termRelationTestingTerm6", callContext);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());

        // retrieve the terms for the parent term and make sure it does include
        // the added term
        List<TermInfo> results = acalService.getIncludedTermsInTerm("termRelationTestingTerm5", callContext);

        assertNotNull(results);
        assertEquals(1, results.size());

        TermInfo added = results.iterator().next();
        assertEquals("termRelationTestingTerm6", added.getId());

        // assert that we can't add the term to the same term twice
        StatusInfo nullStatus = null;

        try {
            nullStatus = acalService.addTermToTerm("termRelationTestingTerm5", "termRelationTestingTerm6", callContext);
            fail("Did not get an AlreadyExistsException when expected");
        } catch (AlreadyExistsException e) {
            assertNull(nullStatus);
        }

        // assert that adding an invalid term fails
        try {
            nullStatus = acalService.addTermToTerm("termRelationTestingTerm5", "fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(nullStatus);
        }
    }

    @Test
    public void testCreateKeyDate() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, ReadOnlyException {
        String keyDateId = null;
        KeyDateInfo keyDate = new KeyDateInfo();
        keyDate.setId(keyDateId);
        keyDate.setName("testCreate");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2005);

        keyDate.setStartDate(cal.getTime());
        cal.set(Calendar.YEAR, 2006);
        keyDate.setEndDate(cal.getTime());
        keyDate.setIsAllDay(false);
        keyDate.setIsDateRange(true);
        keyDate.setIsRelativeToKeyDate(false);
        keyDate.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        keyDate.setTypeKey(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Test");
        keyDate.setDescr(descr);

        KeyDateInfo created = acalService.createKeyDate("termRelationTestingTerm1", keyDate.getTypeKey(), keyDate, callContext);
        assertNotNull(created);
        keyDateId = created.getId();
        assertNotNull(keyDateId);
        assertEquals("testCreate", created.getName());

        KeyDateInfo retrieved = acalService.getKeyDate(keyDateId, callContext);
        assertNotNull(retrieved);
        assertEquals(keyDateId, retrieved.getId());
        assertEquals("testCreate", retrieved.getName());

        List<KeyDateInfo> kds = acalService.getKeyDatesForTerm("termRelationTestingTerm1", callContext);
        assertNotNull(kds);
        assertTrue(!kds.isEmpty());
        List<String> kdIds = new ArrayList<String>();
        for (KeyDateInfo kd : kds) {
            kdIds.add(kd.getId());
        }
        assertTrue(kdIds.contains(keyDateId));
    }

    @Test
    public void testGetAcademicCalendarsByKeyList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> calendarKeys = new ArrayList<String>();
        calendarKeys.add("testAtpId1");
        calendarKeys.add("testTermId1");
        calendarKeys.add("testTermId2");
        calendarKeys.add("testTermId2");
        List<AcademicCalendarInfo> calendars = acalService.getAcademicCalendarsByIds(calendarKeys, callContext);
        assertNotNull(calendars);
        assertEquals("Number of calendars returned not as expected.", 3, calendars.size());

        // DoesNotExistException
        calendarKeys.clear();
        calendarKeys.add("3B6605D9-9370-441D-89D8-8B747B9AB496"); // Bogus UID
        try {
            calendars = acalService.getAcademicCalendarsByIds(calendarKeys, callContext);
            fail("Expected DoesNotExistException.");
        } catch (DoesNotExistException e) {
        }
    }

    @Test
    public void testSearchForAcademicCalendars() throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ParseException {

        List<String> atpTypes = new ArrayList<String>();
        List<String> atpStates = new ArrayList<String>();

        atpTypes.add("kuali.atp.type.AcademicCalendar");
        atpStates.add("kuali.atp.state.Official");
        atpStates.add("kuali.atp.state.Draft");
        String acalId = "testAtpId1";
        String acalName = "";
        String acalYear = "";

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (!StringUtils.isEmpty(acalId)) {
            predicates.add(PredicateFactory.equalIgnoreCase("id", acalId));
        }
        if (!atpTypes.isEmpty()) {
            predicates.add(PredicateFactory.in("atpType", atpTypes.toArray(new String[atpTypes.size()])));
        }
        if (!atpStates.isEmpty()) {
            predicates.add(PredicateFactory.in("atpState", atpStates.toArray(new String[atpStates.size()])));
        }

        if (acalName != null && acalName.length() > 0) {
            predicates.add(PredicateFactory.like("name", "%" + acalName + "%"));
        }
        if (acalYear != null && acalYear.length() > 0) {
            int year = Integer.parseInt(acalYear);
            Date yearStart = new GregorianCalendar(year, Calendar.JANUARY, 1).getTime();
            Date yearEnd = new GregorianCalendar(year + 1, Calendar.JANUARY, 1).getTime();
            predicates.add(PredicateFactory.greaterThanOrEqual("startDate", yearStart));
            predicates.add(PredicateFactory.lessThan("startDate", yearEnd));
        }

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();

        try {
            List<AcademicCalendarInfo> calendars = acalService.searchForAcademicCalendars(qbc, callContext);
            assertNotNull(calendars);
            assertEquals(1, calendars.size());
            AcademicCalendarInfo calendar = calendars.get(0);
            assertEquals("testAtpId1", calendar.getId());
            assertEquals("testAtp1", calendar.getName());
            assertEquals("Desc 101", calendar.getDescr().getPlain());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCopyAcademicCalendar() throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ParseException {
        final String originalCalendarKey = "ACADEMICCALENDAR1990";

        AcademicCalendar originalCalendar = acalService.getAcademicCalendar(originalCalendarKey, callContext);
        Date startDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse("2008-09-01");
        Date endDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse("2009-08-31");

        AcademicCalendar copiedCalendar = null;
        try {
            copiedCalendar = acalService.copyAcademicCalendar(originalCalendarKey, startDate, endDate, callContext);
        } catch (OperationFailedException e) {
            log.error("copy Academic Calendar failed ", e);
            throw e;
        }

        assertNotNull(originalCalendar.getId());
        assertNotNull(copiedCalendar.getId());
        assertFalse(originalCalendar.getId().equals(copiedCalendar.getId()));
        assertNotNull(originalCalendar.getName());
        assertEquals(originalCalendar.getName(), copiedCalendar.getName());
        assertNotNull(originalCalendar.getTypeKey());
        assertEquals(originalCalendar.getTypeKey(), copiedCalendar.getTypeKey());
        assertNotNull(originalCalendar.getStateKey());
        assertEquals("kuali.atp.state.Draft", copiedCalendar.getStateKey());
        assertNotNull(originalCalendar.getStartDate());
        assertEquals(startDate, copiedCalendar.getStartDate());
        assertNotNull(originalCalendar.getEndDate());
        assertEquals(endDate, copiedCalendar.getEndDate());
        assertNotNull(originalCalendar.getDescr());
        assertEquals(originalCalendar.getDescr().getFormatted(), copiedCalendar.getDescr().getFormatted());
        assertEquals(originalCalendar.getDescr().getPlain(), copiedCalendar.getDescr().getPlain());

        List<String> originalHolidayCalendarsKeys = originalCalendar.getHolidayCalendarIds();
        List<String> copiedHolidayCalendarsKeys = copiedCalendar.getHolidayCalendarIds();
        assertNotNull(originalHolidayCalendarsKeys);
        assertNotNull(copiedHolidayCalendarsKeys);
        assertFalse(originalHolidayCalendarsKeys.isEmpty());
        assertEquals(originalHolidayCalendarsKeys.size(), copiedHolidayCalendarsKeys.size());
        for (String holidayCalendarId : originalHolidayCalendarsKeys) {
            assertFalse("Holiday Calendar with same ID found: " + holidayCalendarId, copiedHolidayCalendarsKeys.contains(holidayCalendarId));
        }

        List<TermInfo> originalCalendarTerms = acalService.getTermsForAcademicCalendar(originalCalendarKey, callContext);
        List<TermInfo> copiedCalendarTerms = acalService.getTermsForAcademicCalendar(copiedCalendar.getId(), callContext);
        assertNotNull(originalCalendarTerms);
        assertNotNull(copiedCalendarTerms);
        assertFalse(originalCalendarTerms.isEmpty());
        assertEquals(originalCalendarTerms.size(), copiedCalendarTerms.size());
        List<String> originalCalendarTermIds = new ArrayList<String>();
        for (TermInfo term : originalCalendarTerms) {
            originalCalendarTermIds.add(term.getId());
        }
        for (TermInfo term : copiedCalendarTerms) {
            assertFalse(originalCalendarTermIds.contains(term.getId()));
        }
        for (TermInfo term : originalCalendarTerms) {
            assertFalse(copiedCalendarTerms.contains(term));
            // TODO check terms were copied properly
            assertNotNull(acalService.getContainingTerms(term.getId(), callContext));
        }
    }

    @Test
    public void testGetImpactedKeyDates() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        String milestoneId = "FALLTERM1990INSTRUCTIONPERIOD";
        List<KeyDateInfo> impactedKeyDates = acalService.getImpactedKeyDates(milestoneId, callContext);
        assertNotNull(impactedKeyDates);
        List<String> impactedKeyDateIds = new ArrayList<String>();
        for (KeyDateInfo impactedKeyDate : impactedKeyDates) {
            assertTrue(impactedKeyDate.getIsRelativeToKeyDate());
            assertEquals(milestoneId, impactedKeyDate.getRelativeAnchorKeyDateId());
            impactedKeyDateIds.add(impactedKeyDate.getId());
        }
        assertTrue(impactedKeyDateIds.contains("FALLTERM1990CENSUS"));
    }

    @Test
    public void testCalculateKeyDate() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        // Census start needs to be recalculated to 14-sept-1990
        final Date censusExpectedStartDate = (new GregorianCalendar(1990, Calendar.SEPTEMBER, 14)).getTime();
        final Date instructionStartDate = (new GregorianCalendar(1990, Calendar.SEPTEMBER, 3)).getTime();

        final String censusId = "FALLTERM1990CENSUS";
        final String instructionPeriodId = "FALLTERM1990INSTRUCTIONPERIOD";

        KeyDateInfo census = acalService.getKeyDate(censusId, callContext);
        assertFalse(censusExpectedStartDate.equals(census.getStartDate()));
        KeyDateInfo instructionPeriod = acalService.getKeyDate(instructionPeriodId, callContext);
        assertEquals(instructionStartDate, instructionPeriod.getStartDate());

        census = acalService.calculateKeyDate(censusId, callContext);
        assertTrue("KeyDate start date not calculated as expected.", censusExpectedStartDate.equals(census.getStartDate()));
        census = acalService.getKeyDate(censusId, callContext);
        // TODO should the milestone be saved in the calculation method or is that a seperate call?
        assertFalse("KeyDate was saved after calculation.", censusExpectedStartDate.equals(census.getStartDate()));
    }

    @Test
    public void testSearchForAcalEvents() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", "testId2"));
        QueryByCriteria qbc = qbcBuilder.build();
        try {
            List<AcalEventInfo> acalEventInfos = acalService.searchForAcalEvents(qbc, callContext);
            assertNotNull(acalEventInfos);
            assertEquals(1, acalEventInfos.size());
            AcalEventInfo acalEventInfo = acalEventInfos.get(0);
            assertEquals("testId2", acalEventInfo.getId());
            assertEquals("testId2", acalEventInfo.getName());
        } catch (Exception e) {
            log.error("testSearchForAcalEvents failed", e);
            fail(e.getMessage());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(2011, Calendar.JUNE, 1);
        Predicate startPredicate = PredicateFactory.greaterThanOrEqual("startDate", new Timestamp(calendar.getTime().getTime()));
        calendar.set(2011, Calendar.DECEMBER, 30);
        Predicate endPredicate = PredicateFactory.lessThanOrEqual("endDate", new Timestamp(calendar.getTime().getTime()));
        qbcBuilder.setPredicates(startPredicate, endPredicate);
        qbc = qbcBuilder.build();

        try {
            List<AcalEventInfo> acalEventInfos = acalService.searchForAcalEvents(qbc, callContext);
            assertNotNull(acalEventInfos);
            assertEquals(2, acalEventInfos.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSearchForHolidays() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        //qbcBuilder.setPredicates(PredicateFactory.equal("isAllDay", "0"));
        qbcBuilder.setPredicates(PredicateFactory.equal("name", "testId2"));
        QueryByCriteria qbc = qbcBuilder.build();

        try {
            List<HolidayInfo> holidayInfos = acalService.searchForHolidays(qbc, callContext);
            assertNotNull(holidayInfos);
            assertEquals(1, holidayInfos.size());
            HolidayInfo holidayInfo = holidayInfos.get(0);
            assertEquals("testId2", holidayInfo.getId());
            assertEquals("testId2", holidayInfo.getName());

        } catch (Exception e) {
            log.error("testSearchForHolidays failed", e);
            fail(e.getMessage());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(2011, Calendar.JUNE, 1);
        Predicate startPredicate = PredicateFactory.greaterThanOrEqual("startDate", new Timestamp(calendar.getTime().getTime()));
        calendar.set(2011, Calendar.DECEMBER, 30);
        Predicate endPredicate = PredicateFactory.lessThanOrEqual("endDate", new Timestamp(calendar.getTime().getTime()));
        qbcBuilder.setPredicates(startPredicate, endPredicate);
        qbc = qbcBuilder.build();

        try {
            List<HolidayInfo> holidayInfos = acalService.searchForHolidays(qbc, callContext);
            assertNotNull(holidayInfos);
            assertEquals(2, holidayInfos.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetHolidayType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        TypeInfo result = acalService.getHolidayType(AtpServiceConstants.MILESTONE_LABOR_DAY_TYPE_KEY, callContext);

        assertNotNull(result);
        assertEquals(result.getName(), "Labor Day");

        TypeInfo fakeType = null;
        try {
            fakeType = acalService.getHolidayType("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(fakeType);
        }
    }

    @Test
    public void testGetHolidayTypes() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeInfo> result = acalService.getHolidayTypes(callContext);

        assertNotNull(result);
        assertTrue(!result.isEmpty());
    }

    @Test   // Jira - KSENROLL-530
    public void testGetKeyDateTypesForTermType()
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, ReadOnlyException, VersionMismatchException {

        String termType = AtpServiceConstants.ATP_FALL_TYPE_KEY;
        List<TypeInfo> types = this.acalService.getKeyDateTypesForTermType(termType, callContext);
        assertNotNull(types);
        if (types.size() < 2) {
            fail("too few key date types");
        }
        System.out.println("Found " + types.size() + " keydate types for " + termType);
        for (TypeInfo type : types) {
            System.out.println(type.getKey() + " " + type.getName());
        }
    }

    @Test //Jira - KSENROLL-679
    public void testCreateAcalWithHcal()
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, ReadOnlyException, VersionMismatchException {
        AcademicCalendarInfo orig = new AcademicCalendarInfo();
        orig.setName("testAcal1 name");
        orig.setEndDate(new Date());
        orig.setStartDate(new Date());
        orig.setDescr(new RichTextHelper().fromPlain("test descrtion 1"));
        // Assume holidayCalendarIds picking up from dropdown and valid(already
        // in db)
        List<String> holidayCalendarIds = new ArrayList<String>();
        holidayCalendarIds.add("testAtpId2");
        orig.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        orig.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        orig.setHolidayCalendarIds(holidayCalendarIds);
        AcademicCalendarInfo info = acalService.createAcademicCalendar(orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getHolidayCalendarIds().size(), info.getHolidayCalendarIds().size());
        assertEquals(orig.getHolidayCalendarIds().get(0), info.getHolidayCalendarIds().get(0));


        orig = info;
        info = acalService.getAcademicCalendar(info.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getHolidayCalendarIds().size(), info.getHolidayCalendarIds().size());
        assertEquals(orig.getHolidayCalendarIds().get(0), info.getHolidayCalendarIds().get(0));

        orig = info;
        orig.setName("testNewAcal name");
        info = acalService.updateAcademicCalendar(info.getId(), info, callContext);
        assertNotNull(info);
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getHolidayCalendarIds().size(), info.getHolidayCalendarIds().size());
        assertEquals(orig.getHolidayCalendarIds().get(0), info.getHolidayCalendarIds().get(0));
    }

    private void populateRequiredFields(AcademicCalendarInfo acal) {
        acal.setEndDate(new Date());
        acal.setStartDate(new Date());
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("");
        acal.setDescr(richTextInfo);
    }

    private void populateRequiredFields(TermInfo term) {
        term.setEndDate(new Date());
        term.setStartDate(new Date());
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("");
        term.setDescr(richTextInfo);
    }

    /**
     * This method tests that instructional days are calculated properly.
     * It tests standard, overlapping, and edge cases.
     * @throws Exception
     */
    @Test
    public void testGetInstructionalDaysForTerm() throws Exception{
        // whole year
        String hCalStart = "2013-01-01";
        String hCalEnd = "2013-12-31";

        // month of march
        String aCalStart = "2013-02-01";
        String aCalEnd = "2013-03-31";

        // term is 2 wks
        String termStart = "2013-02-25";
        String termEnd = "2013-03-08";

        Date  aCalStartDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse(aCalStart);  // Start date
        Date  aCalEndDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse(aCalEnd);  // end date

        Date  termStartDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse(termStart);  // Start date
        Date  termEndDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse(termEnd);  // end date

        // create hCal
        HolidayCalendarInfo hCal = _createHcal(hCalStart,hCalEnd);


        AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setName("KSENROLL-923testGetInstructionalDaysForTerm name");
        acal.setDescr(new RichTextHelper().toRichTextInfo("description plain KSENROLL-923testGetInstructionalDaysForTerm", "description formatted KSENROLL-923testGetInstructionalDaysForTerm"));
        acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        acal.setStartDate(aCalStartDate);
        acal.setEndDate(aCalEndDate);
        acal.setAdminOrgId("testOrgId1");
        acal.getHolidayCalendarIds().add(hCal.getId()); // add the holiday calendar to the aCal

        AcademicCalendarInfo acalInfo = acalService.createAcademicCalendar(acal.getTypeKey(), acal, callContext);
        assertNotNull(acalInfo);
        assertNotNull(acalInfo.getId());
        assertEquals(acal.getName(), acalInfo.getName());

        //Term
        TermInfo term = new TermInfo();
        term.setName("KSENROLL-923testGetInstructionalDaysForTerm term");
        term.setDescr(new RichTextHelper().toRichTextInfo("description plain KSENROLL-923testGetInstructionalDaysForTerm term", "description formatted KSENROLL-923testGetInstructionalDaysForTerm term"));
        term.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        term.setStartDate(termStartDate);
        term.setEndDate(termEndDate);
        term.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        TermInfo termInfo = acalService.createTerm(term.getTypeKey(), term, callContext);
        assertNotNull(termInfo);
        assertNotNull(termInfo.getId());
        assertEquals(term.getName(), termInfo.getName());

        // add term to acal

        acalService.addTermToAcademicCalendar(acalInfo.getId(), termInfo.getId(), callContext);

        //Keydate
        KeyDateInfo kd = new KeyDateInfo();
        kd.setName("Jira3470TestKeydate name");
        kd.setDescr(new RichTextHelper().toRichTextInfo("description plain Jira3470TestKeydate", "description formatted Jira3470TestKeydate"));
        kd.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        kd.setStartDate(termStartDate);
        kd.setEndDate(termEndDate);
        kd.setIsAllDay(Boolean.TRUE);
        kd.setIsDateRange(Boolean.TRUE);
        kd.setTypeKey(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY);
        KeyDateInfo kdInfo = acalService.createKeyDate(termInfo.getId(), kd.getTypeKey(), kd, callContext);
        assertNotNull(kdInfo);
        assertNotNull(kdInfo.getId());
        assertEquals(kd.getName(), kdInfo.getName());

        // No Holidays
        Integer days =  acalService.getInstructionalDaysForTerm(termInfo.getId(), callContext);
        assertEquals(days.intValue(), 10);

        // Place Date Range Holiday inside period
        HolidayInfo holidayInfo = _createHoliday(hCal.getId(), "2013-03-04","2013-03-05");
        days =  acalService.getInstructionalDaysForTerm(termInfo.getId(), callContext);
        assertEquals(days.intValue(), 8);

        // Overlap period start dates
        holidayInfo = _updateHcalDatesDates(holidayInfo, "2013-02-24","2013-02-25");
        days =  acalService.getInstructionalDaysForTerm(termInfo.getId(), callContext);
        assertEquals(days.intValue(), 9);

        // Overlap period end dates
        holidayInfo = _updateHcalDatesDates(holidayInfo, "2013-03-08","2013-03-09");
        days =  acalService.getInstructionalDaysForTerm(termInfo.getId(), callContext);
        assertEquals(days.intValue(), 9);

        // Overlaping holidays. Make sure we don't count the same day twice
        HolidayInfo holidayInfoSingle = _createHoliday(hCal.getId(), "2013-03-08");
        days =  acalService.getInstructionalDaysForTerm(termInfo.getId(), callContext);
        assertEquals(days.intValue(), 9);




    }
    public HolidayInfo _updateHcalDatesDates(HolidayInfo hDate, String start, String end) throws Exception{
        Date  startDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse(start);  // Start date
        Date  endDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse(end);  // end date

        hDate.setStartDate(startDate);
        hDate.setEndDate(endDate);

       return acalService.updateHoliday(hDate.getId(), hDate, callContext);

    }

    public HolidayInfo _createHoliday(String holidayCalendarId, String start, String end) throws Exception{
        Date  startDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse(start);  // Start date
        Date  endDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse(end);  // end date

        //Holiday
        HolidayInfo holiday = new HolidayInfo();
        holiday.setName("Jira3470TestHoliday name");
        holiday.setDescr(new RichTextHelper().toRichTextInfo("description plain Jira3470TestHoliday", "description formatted Jira3470TestHoliday"));
        holiday.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        holiday.setStartDate(startDate);

        holiday.setEndDate(endDate);
        holiday.setIsAllDay(Boolean.TRUE);
        holiday.setIsDateRange(Boolean.TRUE);
        holiday.setIsInstructionalDay(Boolean.FALSE);

        holiday.setTypeKey(AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_OBSERVED_TYPE_KEY);
        HolidayInfo holidayInfo = acalService.createHoliday(holidayCalendarId, holiday.getTypeKey(), holiday, callContext);

        return holidayInfo;

    }

    public HolidayInfo _createHoliday(String holidayCalendarId, String start) throws Exception{
        Date  startDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse(start);  // Start date


        //Holiday
        HolidayInfo holiday = new HolidayInfo();
        holiday.setName("Jira3470TestHoliday name");
        holiday.setDescr(new RichTextHelper().toRichTextInfo("description plain Jira3470TestHoliday", "description formatted Jira3470TestHoliday"));
        holiday.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        holiday.setStartDate(startDate);


        holiday.setIsAllDay(Boolean.TRUE);
        holiday.setIsDateRange(Boolean.FALSE);
        holiday.setIsInstructionalDay(Boolean.FALSE);

        holiday.setTypeKey(AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_OBSERVED_TYPE_KEY);
        HolidayInfo holidayInfo = acalService.createHoliday(holidayCalendarId, holiday.getTypeKey(), holiday, callContext);

        return holidayInfo;

    }

    public HolidayCalendarInfo _createHcal(String start, String end) throws Exception{
        Date  startDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse(start);  // Start date
        Date  endDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse(end);  // end date

        //Hcal
        HolidayCalendarInfo hcal = new HolidayCalendarInfo();
        hcal.setName("Jira3470TestHcal name");
        hcal.setDescr(new RichTextHelper().toRichTextInfo("description plain Jira3470TestHcal", "description formatted Jira3470TestHcal"));
        hcal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        hcal.setStartDate(startDate);
        hcal.setEndDate(endDate);
        hcal.setAdminOrgId("testOrgId1");

        hcal.setTypeKey(AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY);
        HolidayCalendarInfo info = acalService.createHolidayCalendar(hcal.getTypeKey(), hcal, callContext);
        assertNotNull(info);
        assertNotNull(info.getId());
        assertEquals(hcal.getName(), info.getName());

        return info;
    }


}
