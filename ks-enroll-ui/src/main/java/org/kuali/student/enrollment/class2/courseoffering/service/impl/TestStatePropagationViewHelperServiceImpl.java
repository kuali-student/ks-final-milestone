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
import org.kuali.student.enrollment.class2.courseoffering.service.AssertException;
import org.kuali.student.enrollment.class2.courseoffering.service.TestStatePropagationViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.lum.course.service.CourseService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

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

    private SocInfo socInfo;
    public static final String SAMPLE_TERM = "200001";
    private ContextInfo CONTEXT = ContextUtils.createDefaultContextInfo();
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
    @Override
    public String[] runTests() throws Exception {
        _initServices();
        _reset();
        String[] results = new String[2];
        testSocStateHappy();
        _reset();
        testSocStateUnhappy(CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY);
        return results;
    }

    private void _reset() throws Exception {
        socInfo = _getMainSocForTerm(SAMPLE_TERM);
        if (socInfo != null) {
            socService.deleteSoc(socInfo.getId(), CONTEXT);
        }
        socInfo = _createSocForTerm(SAMPLE_TERM);
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

    public void testSocStateUnhappy(String socState) throws Exception {
        _advanceSocState(socInfo.getId(), socState); // Move us to the desired SOC state
        int i = SOC_STATES_ORDERED.indexOf(socState);
        int count = 1;
        for (int j = 0; j < SOC_STATES_ORDERED.size(); j++) {
            if (j == i || j == i + 1) {
                // Skip over transitioning to yourself or to the next state
                continue;
            }
            _changeSocStateInvalid(socInfo.getId(), SOC_STATES_ORDERED.get(j), socState, "Unhappy Soc B" + count);
            count++;
        }
    }

    private void _advanceSocState(String socId, String socState) throws Exception {
        int index = 1;
        String nextSocState = SOC_STATES_ORDERED.get(index);
        while (!nextSocState.equals(socState)) {
            _changeSocState(socId, nextSocState, "Unhappy Soc " + index);
            index++;
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

    public SocInfo _getMainSocForTerm(String termCode) throws Exception {
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
    }
}
