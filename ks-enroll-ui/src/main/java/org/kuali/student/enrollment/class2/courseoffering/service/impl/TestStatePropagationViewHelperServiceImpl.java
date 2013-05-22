/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Charles on 5/6/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.exception.AssertException;
import org.kuali.student.enrollment.class2.courseoffering.service.TestStatePropagationViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.exception.PseudoUnitTestException;
import org.kuali.student.enrollment.class2.courseoffering.service.util.AoStateTransitionRefSolution;
import org.kuali.student.enrollment.class2.courseoffering.service.util.PseudoUnitTestStateTransitionGrid;
import org.kuali.student.enrollment.class2.courseoffering.service.util.TransitionGridEnum;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.lum.course.service.CourseService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class TestStatePropagationViewHelperServiceImpl extends ViewHelperServiceImpl implements TestStatePropagationViewHelperService {
    private AcademicCalendarService acalService = null;
    private CourseOfferingService coService = null;
    private CourseOfferingSetService socService = null;
    private CourseService courseService = null;
    private LuiService luiService = null;

    // List of objects used in test
    private SocInfo socInfo;
    private CourseOfferingInfo courseOfferingInfo;
    private FormatOfferingInfo formatOfferingInfo;
    private RegistrationGroupInfo rgInfo;
    private List<ActivityOfferingInfo> aoInfos;
    // Constants
    public static final String SAMPLE_TERM = "200001";
    public static final String COURSE_OFFERING_KEY = "courseOfferingKey";
    private ContextInfo CONTEXT;

    // Soc states
    public static final List<String> SOC_STATES_ORDERED;
    static {
        SOC_STATES_ORDERED = new ArrayList<String>();
        SOC_STATES_ORDERED.add(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY);
        SOC_STATES_ORDERED.add(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY);
        SOC_STATES_ORDERED.add(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY);
        SOC_STATES_ORDERED.add(CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY);
        SOC_STATES_ORDERED.add(CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY);
        SOC_STATES_ORDERED.add(CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY);
    }

    public static final List<String> FO_STATES_ORDERED;
    static {
        FO_STATES_ORDERED = new ArrayList<String>();
        FO_STATES_ORDERED.add(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
        FO_STATES_ORDERED.add(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);
        // can only get to this state if SOC state is publishing/published
        FO_STATES_ORDERED.add(LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY);
    }

    public static final List<String> CO_STATES_ORDERED;
    static {
        CO_STATES_ORDERED = new ArrayList<String>();
        CO_STATES_ORDERED.add(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        CO_STATES_ORDERED.add(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY);
        // can only get to this state if SOC state is publishing/published
        CO_STATES_ORDERED.add(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
    }

    public TestStatePropagationViewHelperServiceImpl() {
        CONTEXT = ContextUtils.createDefaultContextInfo();
        CONTEXT.setPrincipalId("carol");
        CONTEXT.setCurrentDate(new Date());
    }

    private void _resetFoCoDraft() throws Exception {
        LuiInfo lui = luiService.getLui(formatOfferingInfo.getId(), CONTEXT);
        lui.setStateKey(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
        luiService.updateLui(lui.getId(), lui, CONTEXT);
        // Reset CO to draft
        lui = luiService.getLui(courseOfferingInfo.getId(), CONTEXT);
        lui.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        luiService.updateLui(lui.getId(), lui, CONTEXT);
    }

    private void _resetFoCoPlanned() throws Exception {
        LuiInfo lui = luiService.getLui(formatOfferingInfo.getId(), CONTEXT);
        lui.setStateKey(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
        luiService.updateLui(lui.getId(), lui, CONTEXT);
        // Reset CO to draft
        lui = luiService.getLui(courseOfferingInfo.getId(), CONTEXT);
        lui.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        luiService.updateLui(lui.getId(), lui, CONTEXT);
    }

    private void _resetFoCoOffered() throws Exception {
        LuiInfo lui = luiService.getLui(formatOfferingInfo.getId(), CONTEXT);
        lui.setStateKey(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
        luiService.updateLui(lui.getId(), lui, CONTEXT);
        // Reset CO to draft
        lui = luiService.getLui(courseOfferingInfo.getId(), CONTEXT);
        lui.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        luiService.updateLui(lui.getId(), lui, CONTEXT);
    }

    private void _resetSocOnly() throws Exception {
        socInfo = _getMainSocForTerm(SAMPLE_TERM);
        if (socInfo != null) {
            socService.deleteSoc(socInfo.getId(), CONTEXT);
        }
        socInfo = _createSocForTerm(SAMPLE_TERM);
    }

    private void _reset(boolean createCourseOffering) throws Exception {
        socInfo = _getMainSocForTerm(SAMPLE_TERM);
        if (socInfo != null) {
            List<String> coIds = null;
            try {
                coIds = socService.getCourseOfferingIdsBySoc(socInfo.getId(), CONTEXT);
                if (coIds != null) {
                    if (coIds.size() > 1) {
                       throw new PseudoUnitTestException("Should only have 1 CO in this term");
                    } else if (!coIds.isEmpty()) { // Has one CO
                        coService.deleteCourseOfferingCascaded(coIds.get(0), CONTEXT);
                    }
                }
            } catch (DoesNotExistException e) {
                // Do nothing
            }
            socService.deleteSoc(socInfo.getId(), CONTEXT);
        }
        socInfo = _createSocForTerm(SAMPLE_TERM);
        if (createCourseOffering) {
            Map<String, Object> keyToValues =
                    rolloverCourseOfferingFromSourceTermToTargetTerm("CHEM237", "201201", "200001");
            courseOfferingInfo = (CourseOfferingInfo) keyToValues.get(COURSE_OFFERING_KEY);
            // Get FOs (should only be 1)
            List<FormatOfferingInfo> foInfos = coService.getFormatOfferingsByCourseOffering(courseOfferingInfo.getId(), CONTEXT);
            // Use first FO to get RGs
            List<RegistrationGroupInfo> rgInfos = coService.getRegistrationGroupsByFormatOffering(foInfos.get(0).getId(), CONTEXT);
            // Pick first RG
            rgInfo = rgInfos.get(0);
            // Get list of AOs
            aoInfos = coService.getActivityOfferingsByIds(rgInfo.getActivityOfferingIds(), CONTEXT);
        }
    }

    private String _computeFoState(String foId) throws Exception {
        List<ActivityOfferingInfo> aoInfos = coService.getActivityOfferingsByFormatOffering(foId, CONTEXT);
        List<String> aoStates = new ArrayList<>();
        for (ActivityOfferingInfo ao: aoInfos) {
            aoStates.add(ao.getStateKey());
        }
        String foStateComputed = LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY;
        if (aoStates.contains(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY)) {
            foStateComputed = LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY;
        } else if (aoStates.contains(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY)) {
            foStateComputed = LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY;
        }
        return foStateComputed;
    }

    private String _computeCoState() throws Exception {
        // Use actual AOs to compute CO state, not FOs (which may not be accurate)
        List<FormatOfferingInfo> foInfos = coService.getFormatOfferingsByCourseOffering(courseOfferingInfo.getId(), CONTEXT);
        List<String> foStates = new ArrayList<>();
        for (FormatOfferingInfo fo: foInfos) {
            foStates.add(_computeFoState(fo.getId()));
        }
        String coStateComputed = LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY;
        if (foStates.contains(LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY)) {
            coStateComputed = LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY;
        } else if (foStates.contains(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY)) {
            coStateComputed = LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY;
        }
        return coStateComputed;
    }

    @Override
    public String[] runTests() throws Exception {
        _initServices();
//        _reset(false);
//        String[] results = new String[2];
//        testSocStateHappy();
//        // Now iterate over all the unhappy paths
//        for (int i = 0; i < SOC_STATES_ORDERED.size(); i++) {
//            _reset(false);
//            testSocStateUnhappy(CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY, i);
//        }
        // Now begin to test AO state transitions
        System.err.println("---------------------- Starting tests");
        _reset(true);
        String aoId = aoInfos.get(0).getId();
        for (int i = 0; i < SOC_STATES_ORDERED.size(); i++) {
            String socState = SOC_STATES_ORDERED.get(i);
            System.err.println("---------------------- socState = " + socState);
            PseudoUnitTestStateTransitionGrid result =
                    testAoStateTransitionsInSocState(aoId, socInfo.getId(), socState);
            _compareGrids(result);
            _resetSocOnly(); // Make sure to reset the SOC
        }
        return null;
    }

    private void _compareGrids(PseudoUnitTestStateTransitionGrid grid) {
        List<Map<String, String>> results = grid.compare();
        for (int i = 0; i < results.size(); i++) {
            // Put socState in front
            Map<String, String> resultMap = results.get(i);
            String socState = resultMap.get(PseudoUnitTestStateTransitionGrid.SOC_STATE);
            String aoFromState = resultMap.get(PseudoUnitTestStateTransitionGrid.AO_STATE_FROM);
            String aoToState = resultMap.get(PseudoUnitTestStateTransitionGrid.AO_STATE_TO);
            String expectedVal = resultMap.get(PseudoUnitTestStateTransitionGrid.EXPECTED);
            String actualVal = resultMap.get(PseudoUnitTestStateTransitionGrid.ACTUAL);
            String passFail = resultMap.get(PseudoUnitTestStateTransitionGrid.PASS_FAIL);
            String color = passFail.equals(PseudoUnitTestStateTransitionGrid.PASS_VAL) ? "((( GREEN )))" :
                    (passFail.equals(PseudoUnitTestStateTransitionGrid.FAIL_VAL) ?
                            "*** red ***" : "... White ...");
            String message = "(" + socState + ") " + "[" + aoFromState + " => " + aoToState + "]" +
                    " expected/actual = " + expectedVal + "/" + actualVal + " " + color;
            System.err.println(message);
        }
        System.err.println("---------------------- end");
    }

    public void testSocStateHappy() throws Exception {
        _initServices();
        assertEquals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY, "SocHappy 1");
        // Draft to Open
        _changeSocState(socInfo.getId(), CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY, "SocHappy 2");
        // Open to Locked
        _changeSocState(socInfo.getId(), CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY, "SocHappy 3");
        // Locked to Final Edits
        _changeSocState(socInfo.getId(), CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY, "SocHappy 4");
        // Final Edits to Publishing
        _changeSocState(socInfo.getId(), CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY, "SocHappy 5");
        // Publishing to Published
        _changeSocState(socInfo.getId(), CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY, "SocHappy 6");
    }

    public void testSocStateUnhappy(String socState, int index) throws Exception {
        _advanceSocState(socInfo.getId(), socState); // Move us to the desired SOC state
        int i = SOC_STATES_ORDERED.indexOf(socState);
        for (int j = 0; j < SOC_STATES_ORDERED.size(); j++) {
            if (j == i || j == i + 1) {
                // Skip over transitioning to yourself or to the next state
                continue;
            }
            _changeSocStateInvalid(socInfo.getId(), SOC_STATES_ORDERED.get(j), socState, "Unhappy Soc " + index + "-" + j);
        }
    }

    public PseudoUnitTestStateTransitionGrid testAoStateTransitionsInSocState(String aoId, String socId, String socState) throws Exception {
        SocInfo fetched = socService.getSoc(socId, CONTEXT);
        if (!fetched.getStateKey().equals(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY)) {
            throw new PseudoUnitTestException("Initial soc state not in DRAFT state");
        }
        if (!socState.equals(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY)) {
            _advanceSocState(socId, socState);
        }
        PseudoUnitTestStateTransitionGrid foExpected = new PseudoUnitTestStateTransitionGrid(FO_STATES_ORDERED, "fo");
        PseudoUnitTestStateTransitionGrid foActual = new PseudoUnitTestStateTransitionGrid(FO_STATES_ORDERED, "fo");
        PseudoUnitTestStateTransitionGrid coExpected = new PseudoUnitTestStateTransitionGrid(CO_STATES_ORDERED, "co");
        PseudoUnitTestStateTransitionGrid coActual = new PseudoUnitTestStateTransitionGrid(CO_STATES_ORDERED, "co");

        PseudoUnitTestStateTransitionGrid aoGrid = AoStateTransitionRefSolution.getReferenceGridForState(socState);
        for (int i = 0; i < aoGrid.size(); i++) {
            String fromState = aoGrid.getStateKeyAt(i);
            for (int j = 0; j < aoGrid.size(); j++) {
                String toState = aoGrid.getStateKeyAt(j);
                boolean invalidTransition = aoGrid.getTransition(TransitionGridEnum.EXPECTED, fromState, toState) == -1;
                if (j == i || invalidTransition) {
                    if (invalidTransition) {
                        aoGrid.setTransition(TransitionGridEnum.ACTUAL, fromState, toState, -1);
                    } else {
                        // Illegal transition
                        aoGrid.setTransition(TransitionGridEnum.ACTUAL, fromState, toState, 1);
                    }
                    continue;
                }
                // Force the original AO to be in fromState
                _forceChangeAoState(aoId, fromState);
                // Attempt to change toState using normal services call
                boolean change = _tryChangingAoState(aoId, toState);
                aoGrid.setTransition(TransitionGridEnum.ACTUAL, fromState, toState, change ? 1 : 0);
            }
        }
        return aoGrid;
    }
    public void testDraftAoStateToNewAoState(String aoId, String toState, PseudoUnitTestStateTransitionGrid grid) throws AssertException, PseudoUnitTestException {
        ActivityOfferingInfo aoInfo = null;
        try {
            aoInfo = coService.getActivityOffering(aoId, CONTEXT);
        } catch (Exception e) {
            throw new PseudoUnitTestException("Unable to retrieve AO: " + aoId);
        }
        assertEquals(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, aoInfo.getStateKey(), "");
        try {
            boolean change = _tryChangingAoState(aoInfo.getId(), toState);
            grid.setTransition(TransitionGridEnum.ACTUAL, aoInfo.getStateKey(), toState, change ? 1 : 0);
        } catch (PseudoUnitTestException e) {
            // do nothing
        }
    }

    public void testAoStateBackToDraft(String aoId, String fromState, PseudoUnitTestStateTransitionGrid grid) throws AssertException, PseudoUnitTestException {
        _forceChangeAoState(aoId, fromState);
        try {
            boolean change = _tryChangingAoState(aoId, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
            grid.setTransition(TransitionGridEnum.ACTUAL, fromState, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, change ? 1 : 0);
        } catch (PseudoUnitTestException e) {
            // do nothing
        } catch (Exception e) {
            throw new PseudoUnitTestException("Something weird in testAoStateBackToDraft");
        }
    }

    private void _advanceSocState(String socId, String socState) throws Exception {
        if (socState.equals(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY)) {
            return;
        }
        int index = 1;
        String nextSocState = SOC_STATES_ORDERED.get(index);
        while (!nextSocState.equals(socState)) {
            _changeSocState(socId, nextSocState, "Unhappy Soc " + index);
            index++;
            if (index == 6) {
                System.out.println();
            }
            nextSocState = SOC_STATES_ORDERED.get(index);
        }
        if (!nextSocState.equals(SOC_STATES_ORDERED.get(0))) {
            _changeSocState(socId, socState, "Unhappy Soc " + index);
        }
    }

    private void _changeSocState(String socId, String nextState, String message) throws Exception {
        socService.changeSocState(socId, nextState, ContextUtils.createDefaultContextInfo());
        SocInfo fetched = _getMainSocForTerm(SAMPLE_TERM);
        assertEquals(fetched.getStateKey(), nextState, message);
    }

    private void _changeSocStateInvalid(String socId, String nextState, String origState, String message) throws Exception {
        boolean exceptionThrown = false;
        try {
            socService.changeSocState(socId, nextState, ContextUtils.createDefaultContextInfo());
        } catch (OperationFailedException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "Exception not thrown for invalid Soc state change");
        SocInfo fetched = _getMainSocForTerm(SAMPLE_TERM);
        assertEquals(fetched.getStateKey(), origState, message);
    }

    private boolean _forceChangeAoState(String aoId, String nextState) throws PseudoUnitTestException {
        // DanEp suggested this sneaky way to circumvent state changes
        try {
            LuiInfo lui = luiService.getLui(aoId, CONTEXT);
            lui.setStateKey(nextState);
            luiService.updateLui(lui.getId(), lui, CONTEXT);
            return true;
        } catch (Exception e) {
            throw new PseudoUnitTestException("Unexpected exception in _forceChangeAoState: " + e.getMessage());
        }
    }

    private boolean _tryChangingAoState(String aoId, String nextState) throws PseudoUnitTestException {
        try {
            if (nextState.equals(LuiServiceConstants.LUI_AO_STATE_SUSPENDED_KEY)) {
                System.out.println("Hi");
            }
            coService.changeActivityOfferingState(aoId, nextState, CONTEXT);
            return true;
        } catch (OperationFailedException e) {
            return false;
        } catch (Exception e) {
            throw new PseudoUnitTestException("Unexpected exception: " + e.getMessage());
        }
    }

    private void assertTrue(boolean actual, String message) throws AssertException {
        if (!actual) {
            throw new AssertException("assertTrue failed", message);
        }
    }

    private void assertEquals(String expected, String actual, String testType) throws AssertException {
        if (!expected.equals(actual)) {
            throw new AssertException(expected + " != " + actual, testType);
        }
    }

    private SocInfo _createSocForTerm(String termCode) throws Exception {
        _initServices();
        SocInfo soc = null;
        if (_getMainSocForTerm(termCode) != null) {
            return null; // Already exists, so return null
        } else {
            TermInfo mainTerm = getTermByTermCode(termCode);
            SocInfo socInfo = new SocInfo();
            socInfo.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
            socInfo.setStateKey(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY);
            socInfo.setTermId(mainTerm.getId());
            socInfo.setSchedulingStateKey(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_NOT_STARTED);
            soc = socService.createSoc(mainTerm.getId(), socInfo.getTypeKey(), socInfo, new ContextInfo());
        }
        return soc;
    }

    private SocInfo _getMainSocForTerm(String termCode) throws Exception {
        _initServices();

        TermInfo mainTerm = getTermByTermCode(termCode);
        ContextInfo contextInfo = new ContextInfo();
        List<String> socIds = socService.getSocIdsByTerm(mainTerm.getId(), contextInfo);
        List<SocInfo> socInfos = socService.getSocsByIds(socIds, contextInfo);
        for (SocInfo socInfo: socInfos) {
            if (socInfo.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                return socInfo;
            }
        }
        return null;
    }

    public TermInfo getTermByTermCode(String termCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        _initServices();

        // TODO: Find sensible way to rewrap exception that acal service may throw
        // Find the term (alas, I think it does approximate search)
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));
        QueryByCriteria criteria = qbcBuilder.build();

        // Do search.  In ideal case, terms returns one element, which is the desired term.
        List<TermInfo> terms = null;
        terms = acalService.searchForTerms(criteria, new ContextInfo());
        TermInfo mainTerm = null;
        if (terms == null || terms.isEmpty()) {
            throw new InvalidParameterException("Unable to find term for: " + termCode);
        } else {
            mainTerm = terms.get(0);
        }
        return mainTerm;
    }

    @Override
    public Map<String, Object> rolloverCourseOfferingFromSourceTermToTargetTerm(String courseOfferingCode, String sourceTermCode, String targetTermCode) throws Exception {
        _initServices();
        TermInfo sourceTerm = getTermByTermCode(sourceTermCode);
        TermInfo targetTerm = getTermByTermCode(targetTermCode);
        List<CourseOfferingInfo> coInfos = _searchCourseOfferingByCOCodeAndTerm(courseOfferingCode, sourceTerm.getId());
        if (coInfos == null || coInfos.isEmpty()) {
            return null;
        }
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        CourseOfferingInfo coInfo = coInfos.get(0); // Just get the first one
        Date start = new Date();
        SocRolloverResultItemInfo rolloverResultInfo =
                coService.rolloverCourseOffering(coInfo.getId(), targetTerm.getId(), new ArrayList<String>(), contextInfo);
        Date end = new Date();
        String targetId = rolloverResultInfo.getTargetCourseOfferingId();
        CourseOfferingInfo targetCo = coService.getCourseOffering(targetId, contextInfo);

        Map<String, Object> keyToValues = new HashMap<String, Object>();
        keyToValues.put(COURSE_OFFERING_KEY, targetCo);
        return keyToValues;
    }

    private List<CourseOfferingInfo> _searchCourseOfferingByCOCodeAndTerm(String courseOfferingCode, String termId) throws Exception {
        _initServices();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.and(
                        PredicateFactory.equal(CourseOfferingConstants.COURSEOFFERING_COURSE_OFFERING_CODE, courseOfferingCode),
                        PredicateFactory.equal(CourseOfferingConstants.ATP_ID, termId)
                ));
        QueryByCriteria criteria = qbcBuilder.build();
        List<CourseOfferingInfo> coList = coService.searchForCourseOfferings(criteria, new ContextInfo());
        return coList;
    }

    private void _initServices() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        if (courseService == null) {
            Object o = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "course",
                    "CourseService"));
            courseService = (CourseService) o;
        }

        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        if (luiService == null) {
            luiService = (LuiService) GlobalResourceLoader.getService(new QName(LuiServiceConstants.NAMESPACE,
                    LuiServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
    }
}
