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
import org.kuali.student.enrollment.class2.courseoffering.dto.RGStateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.StatePropagationWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.TestStatePropagationForm;
import org.kuali.student.enrollment.class2.courseoffering.service.TestStatePropagationViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.exception.AssertException;
import org.kuali.student.enrollment.class2.courseoffering.service.exception.PseudoUnitTestException;
import org.kuali.student.enrollment.class2.courseoffering.service.util.AFUTTypeEnum;
import org.kuali.student.enrollment.class2.courseoffering.service.util.AoStateTransitionRefSolution;
import org.kuali.student.enrollment.class2.courseoffering.service.util.PseudoUnitTestStateTransitionGrid;
import org.kuali.student.enrollment.class2.courseoffering.service.util.RegGroupStateResult;
import org.kuali.student.enrollment.class2.courseoffering.service.util.TransitionGridYesNoEnum;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseofferingset.service.facade.RolloverAssist;
import org.kuali.student.enrollment.class2.courseofferingset.util.CourseOfferingSetUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.poc.eventproc.KSEventProcessorImpl;
import org.kuali.student.poc.eventproc.event.KSEvent;
import org.kuali.student.poc.eventproc.event.KSEventFactory;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.facade.AcademicCalendarServiceFacade;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * View helper service impl for running AFUTs for state propagation.
 *
 * @author Kuali Student Team
 */
public class TestStatePropagationViewHelperServiceImpl extends ViewHelperServiceImpl implements TestStatePropagationViewHelperService {

    // List of objects used in test
    private SocInfo socInfo;
    private CourseOfferingInfo courseOfferingInfo;
    private List<FormatOfferingInfo> foInfos;
    private RegistrationGroupInfo rgInfo;
    private List<RegistrationGroupInfo> rgInfos;
    private List<ActivityOfferingInfo> aoInfos; // Only for a single RG in first FO
    private String primaryAoId;
    private String secondaryAoId;
    private String secondAoState = LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY;
    private TermInfo targetTerm;
    private TermInfo subtermOne;
    private TermInfo subtermTwo;

    // Constants
    public static final String SAMPLE_TERM = "200008";
    public static final String SAMPLE_ROLLOVER_TERM = "200108";
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

    /**
     * What the FO state should be given two of the AO states are initially in draft and the "second" AO is in
     * possibly a different state.
     */
    public static final Map<String, String> SECOND_AO_STATE_TO_FO_STATE;
    static {
        SECOND_AO_STATE_TO_FO_STATE = new HashMap<String, String>();
        SECOND_AO_STATE_TO_FO_STATE.put(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
        SECOND_AO_STATE_TO_FO_STATE.put(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);
        SECOND_AO_STATE_TO_FO_STATE.put(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY, LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY);
    }

    /**
     * What the CO state should be given two of the AO states are initially in draft and the "second" AO is in
     * possibly a different state.
     */
    public static final Map<String, String> SECOND_AO_STATE_TO_CO_STATE;
    static {
        SECOND_AO_STATE_TO_CO_STATE = new HashMap<String, String>();
        SECOND_AO_STATE_TO_CO_STATE.put(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        SECOND_AO_STATE_TO_CO_STATE.put(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY);
        SECOND_AO_STATE_TO_CO_STATE.put(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY, LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
    }
    /**
     * Get a fresh copy from the DB
     */
    private void _refetch() throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        courseOfferingInfo = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(courseOfferingInfo.getId(), CONTEXT);
        for (int i = 0; i < foInfos.size(); i++) {
            FormatOfferingInfo foInfo = foInfos.get(i);
            foInfos.set(i, CourseOfferingManagementUtil.getCourseOfferingService().getFormatOffering(foInfo.getId(), CONTEXT));
        }
        for (int i = 0; i < aoInfos.size(); i++) {
            ActivityOfferingInfo aoInfo = aoInfos.get(i);
            aoInfos.set(i, CourseOfferingManagementUtil.getCourseOfferingService().getActivityOffering(aoInfo.getId(), CONTEXT));
        }
    }

    public TestStatePropagationViewHelperServiceImpl() {
        CONTEXT = ContextUtils.createDefaultContextInfo();
        CONTEXT.setPrincipalId("admin");
        CONTEXT.setCurrentDate(new Date());
    }

    /**
     * fromState The initial state to set

     */
    private void _resetFoCoAndSecondaryAoState(String fromState, String secondaryState) throws Exception {
        // First, reset the secondary AO state
        LuiInfo aoLui = CourseOfferingManagementUtil.getLuiService().getLui(secondaryAoId, CONTEXT);
        aoLui.setStateKey(secondaryState);
        CourseOfferingManagementUtil.getLuiService().updateLui(aoLui.getId(), aoLui, CONTEXT);
        // The AO state that has the biggest index is used to determine what the FO/CO state is.
        int fromStateIndex = AoStateTransitionRefSolution.AO_STATES_ORDERED.indexOf(fromState);
        int secondaryStateIndex = AoStateTransitionRefSolution.AO_STATES_ORDERED.indexOf(secondaryState);
        String aoStateWinner = fromState;
        if (secondaryStateIndex > fromStateIndex) {
            aoStateWinner = secondaryState;
        }
        // Set the FO state
        String foId = foInfos.get(0).getId(); // Grab first FO
        LuiInfo foLui = CourseOfferingManagementUtil.getLuiService().getLui(foId, CONTEXT);
        String foState = SECOND_AO_STATE_TO_FO_STATE.get(aoStateWinner);
        foLui.setStateKey(foState);
        LuiInfo updateFoLui = CourseOfferingManagementUtil.getLuiService().updateLui(foLui.getId(), foLui, CONTEXT);

        // Set the CO state
        String coId = courseOfferingInfo.getId(); // Grab first FO
        LuiInfo coLui = CourseOfferingManagementUtil.getLuiService().getLui(coId, CONTEXT);
        String coState = SECOND_AO_STATE_TO_CO_STATE.get(aoStateWinner);
        coLui.setStateKey(coState);
        LuiInfo updateCoLui = CourseOfferingManagementUtil.getLuiService().updateLui(coLui.getId(), coLui, CONTEXT);
    }

    private void _resetSocOnly() throws Exception {
        socInfo = _getMainSocForTerm(SAMPLE_TERM);
        if (socInfo != null) {
            CourseOfferingManagementUtil.getSocService().deleteSoc(socInfo.getId(), CONTEXT);
        }
        socInfo = _createSocForTerm(SAMPLE_TERM);
    }

    private void _cleanSoc(String termCode) throws Exception {
        SocInfo socInfoLocal = _getMainSocForTerm(termCode);
        if (socInfoLocal != null) {
            List<String> coIds = null;
            try {
                coIds = CourseOfferingManagementUtil.getSocService().getCourseOfferingIdsBySoc(socInfoLocal.getId(), CONTEXT);
                if (coIds != null) {
                    if (coIds.size() > 2) {
                        throw new PseudoUnitTestException("Should only have at most 2 COs in this term");
                    } else if (!coIds.isEmpty()) { // Has one CO
                        CourseOfferingManagementUtil.getCourseOfferingService().deleteCourseOfferingCascaded(coIds.get(0), CONTEXT);
                        if (coIds.size() > 1) {
                            CourseOfferingManagementUtil.getCourseOfferingService().deleteCourseOfferingCascaded(coIds.get(1), CONTEXT);
                        }
                    }
                }
            } catch (DoesNotExistException e) {
                // Do nothing
            }
            List<String> itemIds = CourseOfferingManagementUtil.getSocService().getSocRolloverResultIdsBySourceSoc(socInfoLocal.getId(), CONTEXT);
            // Fetch socRollover
            for (String itemId: itemIds) {
                SocRolloverResultInfo result = CourseOfferingManagementUtil.getSocService().getSocRolloverResult(itemId, CONTEXT);
                List<SocRolloverResultItemInfo> items =
                        CourseOfferingManagementUtil.getSocService().getSocRolloverResultItemsByResultId(result.getId(), CONTEXT);
                if (items != null) {
                    for (SocRolloverResultItemInfo item: items) {
                        CourseOfferingManagementUtil.getSocService().deleteSocRolloverResultItem(item.getId(), CONTEXT);
                    }
                }
                CourseOfferingManagementUtil.getSocService().deleteSocRolloverResult(result.getId(), CONTEXT);
            }
            CourseOfferingManagementUtil.getSocService().deleteSoc(socInfoLocal.getId(), CONTEXT);
        }
    }

    private void _cleanSubterms(String parentTermCode)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        AtpService atpService = (AtpService) GlobalResourceLoader.getService(new QName(AtpServiceConstants.NAMESPACE,
                AtpServiceConstants.SERVICE_NAME_LOCAL_PART));
        TermInfo term = getTermByTermCode(parentTermCode);
        List<TermInfo> childTerms = CourseOfferingManagementUtil.getAcademicCalendarService().getIncludedTermsInTerm(term.getId(), CONTEXT);
        for (TermInfo child: childTerms) {
            // Force into draft state so it can be deleted
            AtpInfo atpInfo = atpService.getAtp(child.getId(), CONTEXT);
            atpInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
            atpService.updateAtp(atpInfo.getId(), atpInfo, CONTEXT);
            // Then delete
            CourseOfferingManagementUtil.getAcademicCalendarService().deleteTerm(child.getId(), CONTEXT);
        }
    }

    private void _reset(boolean createCourseOffering) throws Exception {
        for (int i = 0; i <= 4; i++) {
            _cleanSoc("200" + i + "08");
        }
        // Fortunately, subterms are independent of SOCs
        _cleanSubterms(SAMPLE_TERM);
        _cleanSubterms(SAMPLE_ROLLOVER_TERM);
        socInfo = _createSocForTerm(SAMPLE_TERM);
        _createSocForTerm(SAMPLE_ROLLOVER_TERM);
        if (createCourseOffering) {
            Map<String, Object> keyToValues =
                    rolloverCourseOfferingFromSourceTermToTargetTerm("CHEM237", "201201", SAMPLE_TERM);
            targetTerm = getTermByTermCode(SAMPLE_TERM);
            courseOfferingInfo = (CourseOfferingInfo) keyToValues.get(COURSE_OFFERING_KEY);
            // Get FOs (should only be 1)
            foInfos = CourseOfferingManagementUtil.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingInfo.getId(), CONTEXT);
            // Save all RGs
            rgInfos = CourseOfferingManagementUtil.getCourseOfferingService().getRegistrationGroupsByFormatOffering(foInfos.get(0).getId(), CONTEXT);
            // Pick first RG
            rgInfo = rgInfos.get(0);
            // Get list of AOs but only for this RG
            aoInfos = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByIds(rgInfo.getActivityOfferingIds(), CONTEXT);
            primaryAoId = aoInfos.get(0).getId();
            secondaryAoId = aoInfos.get(1).getId();
        }
    }

    private String _computeFoState(String primaryAoId, String desiredAoState) throws Exception {
        // Use actual AOs to compute CO state
        Set<String> aoStates = new HashSet<String>();
        List<ActivityOfferingInfo> aoInfos = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByFormatOffering(foInfos.get(0).getId(), CONTEXT);
        boolean foundPrimaryAoId = false;
        for (ActivityOfferingInfo ao: aoInfos) {
            if (ao.getId().equals(primaryAoId)) {
                aoStates.add(desiredAoState);
                foundPrimaryAoId = true;
            } else {
                aoStates.add(ao.getStateKey());
            }
        }
        if (!foundPrimaryAoId) {
            throw new PseudoUnitTestException("primary AO ID not found");
        }
        String foStateComputed = LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY;
        if (aoStates.contains(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY)) {
            foStateComputed = LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY;
        } else if (aoStates.contains(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY)) {
            foStateComputed = LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY;
        }
        return foStateComputed;
    }

    private String _computeCoState(String primaryAoId, String desiredAoState) throws Exception {
        // Use actual AOs to compute CO state
        Set<String> aoStates = new HashSet<String>();
        List<ActivityOfferingInfo> aoInfos = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingInfo.getId(), CONTEXT);
        boolean foundPrimaryAoId = false;
        for (ActivityOfferingInfo ao: aoInfos) {
            if (ao.getId().equals(primaryAoId)) {
                aoStates.add(desiredAoState);
                foundPrimaryAoId = true;
            } else {
                aoStates.add(ao.getStateKey());
            }
        }
        if (!foundPrimaryAoId) {
            throw new PseudoUnitTestException("primary AO ID not found");
        }

        String coStateComputed = LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY;
        if (aoStates.contains(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY)) {
            coStateComputed = LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY;
        } else if (aoStates.contains(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY)) {
            coStateComputed = LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY;
        }
        return coStateComputed;
    }

    private void _testRollover() throws Exception {
        _reset(true);
        TermInfo halfFallOne = new TermInfo();
        halfFallOne.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        halfFallOne.setTypeKey(AtpServiceConstants.ATP_HALF_FALL_1_TYPE_KEY);
        halfFallOne.setStartDate(targetTerm.getStartDate());
        halfFallOne.setEndDate(targetTerm.getEndDate());
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("foo");
        richTextInfo.setFormatted("bar");
        halfFallOne.setDescr(richTextInfo);
        subtermOne = CourseOfferingManagementUtil.getAcademicCalendarService().createTerm(halfFallOne.getTypeKey(), halfFallOne, CONTEXT);
        // Attach subterm to term
        CourseOfferingManagementUtil.getAcademicCalendarService().addTermToTerm(targetTerm.getId(), subtermOne.getId(), CONTEXT);
        // Change primary AO to refer to this term
        ActivityOfferingInfo aoInfo = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOffering(primaryAoId, CONTEXT);
        aoInfo.setTermId(subtermOne.getId());
        aoInfo.setTermCode(null);
        CourseOfferingManagementUtil.getCourseOfferingService().updateActivityOffering(aoInfo.getId(), aoInfo, CONTEXT);
        // Create SOC in another term to rollover
        SocInfo socInfo2 = _createSocForTerm(SAMPLE_ROLLOVER_TERM);
        TermInfo newTargetTerm = getTermByTermCode(SAMPLE_ROLLOVER_TERM);
        // Create fall subterm in new target term
        TermInfo halfFallTwo = new TermInfo();
        halfFallTwo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        halfFallTwo.setTypeKey(AtpServiceConstants.ATP_HALF_FALL_1_TYPE_KEY);
        halfFallTwo.setStartDate(newTargetTerm.getStartDate());
        halfFallTwo.setEndDate(newTargetTerm.getEndDate());
        richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("foo");
        richTextInfo.setFormatted("bar");
        halfFallTwo.setDescr(richTextInfo);
        //
        subtermTwo = CourseOfferingManagementUtil.getAcademicCalendarService().createTerm(halfFallTwo.getTypeKey(), halfFallTwo, CONTEXT);
        // Attach subterm to term
        CourseOfferingManagementUtil.getAcademicCalendarService().addTermToTerm(newTargetTerm.getId(), subtermTwo.getId(), CONTEXT);
        AcademicCalendarServiceFacade acalServiceFacade
            = (AcademicCalendarServiceFacade) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acalServiceFacade", "AcademicCalendarServiceFacade"));
        acalServiceFacade.makeTermOfficialCascaded(subtermTwo.getId(), CONTEXT);
        // Rollover (should cause exception)
        try {
            Map<String, Object> keyToValues =
                    rolloverCourseOfferingFromSourceTermToTargetTerm("CHEM237", SAMPLE_TERM, SAMPLE_ROLLOVER_TERM);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        List<String> coIds = CourseOfferingManagementUtil.getSocService().getCourseOfferingIdsBySoc(socInfo2.getId(), CONTEXT);
        List<ActivityOfferingInfo> aoInfos = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByCourseOffering(coIds.get(0), CONTEXT);
        System.err.println("Hi");
    }

    private void _testColo(ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {

        RolloverAssist rolloverAssist =
                (RolloverAssist) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/rolloverAssist", "RolloverAssist"));
        List<ScheduleRequestSetInfo> srsList1 =
                CourseOfferingManagementUtil.getSchedulingService().getScheduleRequestSetsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, primaryAoId, context);
        List<ScheduleRequestSetInfo> srsList2 =
                CourseOfferingManagementUtil.getSchedulingService().getScheduleRequestSetsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, secondaryAoId, context);
        ScheduleRequestSetInfo srs1 = srsList1.get(0);
        ScheduleRequestSetInfo srs2 = srsList2.get(0);
        CourseOfferingManagementUtil.getSchedulingService().deleteScheduleRequestSet(srs2.getId(), context);
        srs1.getRefObjectIds().add(secondaryAoId);
        ScheduleRequestSetInfo srs1Fetched = null;
        try {
            srs1Fetched = CourseOfferingManagementUtil.getSchedulingService().updateScheduleRequestSet(srs1.getId(), srs1, context);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException(e.getMessage());
        }
        try {
            rolloverCourseOfferingFromSourceTermToTargetTerm("CHEM237", SAMPLE_TERM, SAMPLE_TERM);
        } catch (Exception e) {
            System.out.println("Woops");
        }
        System.out.println("Hi");
    }

    private void _testEventProcessor() throws Exception {
        _reset(true);
        KSEventProcessorImpl ksEventProcessor
                = (KSEventProcessorImpl) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/ksEventProcessor", "KSEventProcessor"));
        // Get all the AOs for the initial RG
        List<ActivityOfferingInfo> rgAos = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByIds(rgInfo.getActivityOfferingIds(), CONTEXT);
        ActivityOfferingInfo sampleAO = null;
        for (ActivityOfferingInfo ao: rgAos) {
            // Find the one that's a lecture
            if (ao.getTypeKey().equals(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY)) {
                sampleAO = ao;
                break;
            }
        }
        // Find another RG that has the same AO as sampleAO and store it in secondRG
        RegistrationGroupInfo secondRG = null;
        for (int i = 1; i < rgInfos.size(); i++) {
            // Skip the zeroth one
            RegistrationGroupInfo rg = rgInfos.get(i);
            rgAos = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByIds(rgInfo.getActivityOfferingIds(), CONTEXT);
            for (ActivityOfferingInfo ao: rgAos) {
                // Find the one that's a lecture
                if (ao.getId().equals(sampleAO.getId())) {
                    secondRG = rg;
                    break;
                }
            }
            if (secondRG != null) {
                break;
            }
        }
        ksEventProcessor.getCoService().scheduleActivityOffering(sampleAO.getId(), CONTEXT);
        sampleAO = ksEventProcessor.getCoService().getActivityOffering(sampleAO.getId(), CONTEXT);
        KSEvent changeAOState = KSEventFactory.createChangeActivityOfferingStateEvent(sampleAO.getId(), LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY);
        ksEventProcessor.fireEvent(changeAOState, CONTEXT);
    }

    private void _runTimings() throws Exception {
        _reset(true);
        List<LuiLuiRelationInfo> infos = null;

//        Stopwatch watch = new Stopwatch();
//        watch.reset();
//        int iterations = 2000;
//        for (int i = 0; i < iterations; i++) {
//            infos = CourseOfferingManagementUtil.getLuiService().getLuiLuiRelationsByLui(rgInfo.getId(), CONTEXT);
//        }
//        String overall = watch.computeAndAccumulate();
//        LOGGER.info("Total time: " + overall);
//
//        Stopwatch watch2 = new Stopwatch();
//        watch2.reset();
//        for (int i = 0; i < iterations; i++) {
//            List<String> aoIds = CourseOfferingManagementUtil.getLuiService().getLuiIdsByLuiAndRelationType(rgInfo.getId(),
//                            LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY, CONTEXT);
//            List<String> foIds = CourseOfferingManagementUtil.getLuiService().getLuiIdsByRelatedLuiAndRelationType(rgInfo.getId(),
//                            LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY, CONTEXT);
//        }
//        overall = watch2.computeAndAccumulate();
//        LOGGER.info("Total time 2: " + overall);
    }


    @Override
    public void runTests(TestStatePropagationForm form) throws Exception {
        // Now begin to test AO state transitions
        System.err.println("<<<<<<<<<<<<<< Starting tests >>>>>>>>>>>>");
        _reset(true);
        _testAoStateTransition(form);
        System.err.println("<<<<<<<<<<<<<< Ending tests >>>>>>>>>>>>");
        System.err.println("<<<<<<<<<<<<<< Starting RG tests >>>>>>>>>>>>");
        _reset(true);
        System.err.println("------------------------- Testing draft");
        RegGroupStateResult rgStateResult = testRegistrationGroupStatePropagation(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
        _printRegGroupResults(rgStateResult, form);
        System.err.println("------------------------- Testing approved");
        rgStateResult = testRegistrationGroupStatePropagation(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY);
        _printRegGroupResults(rgStateResult, form);
        System.err.println("<<<<<<<<<<<<<< Ending RG tests >>>>>>>>>>>>");

        System.err.println("<<<<<<<<<<<<<< Starting RG Invalid tests >>>>>>>>>>>>");
        _reset(true);
        PseudoUnitTestStateTransitionGrid grid = testRegistrationGroupInvalid(true);
        _printRegGroupInvalid(grid, true, form);
        grid = testRegistrationGroupInvalid(false);
        _printRegGroupInvalid(grid, false, form);
        System.err.println("<<<<<<<<<<<<<< END RG Invalid tests >>>>>>>>>>>>");
    }

     private void _printRegGroupInvalid(PseudoUnitTestStateTransitionGrid grid, boolean isRgOffered, TestStatePropagationForm form) {
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.size(); j++) {
                String expected = grid.getTransition(AFUTTypeEnum.EXPECTED, i, j);
                if (!expected.equals(TransitionGridYesNoEnum.INVALID.getName())) {
                    String actual = grid.getTransition(AFUTTypeEnum.ACTUAL, i, j);
                    String offered = "[RG offered] ";
                    if (!isRgOffered) {
                        offered = "[RG pending] ";
                    }
                    String message = offered + " expected/actual = " + _getAfterDot(expected)
                            + "/" + _getAfterDot(actual);
                    System.err.println(message);
                    RGStateWrapper rgStateWrapper = new RGStateWrapper(grid.getStateKeyAt(i), grid.getStateKeyAt(j), _getAfterDot(expected) ,_getAfterDot(actual));
                    if(rgStateWrapper.getStatus().equals("pass")){
                        form.setPassCount(form.getPassCount()+1);
                    } else if(rgStateWrapper.getStatus().equals("fail")){
                        form.setFailCount(form.getFailCount()+1);
                    }
                    form.addRGFromTransitionStatePropagationWrapper(rgStateWrapper);
               }
           }
       }
    }

    private void _setAosInRgToAoState(String aoState) throws Exception {
        for (ActivityOfferingInfo aoInfo: aoInfos) {
            _forceChangeLuiState(aoInfo.getId(), aoState);
        }
    }
    public static final List<String> RG_STATES;
    static {
        RG_STATES = new ArrayList<String>();
        RG_STATES.add(LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY);
        RG_STATES.add(LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY);
        RG_STATES.add(LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY);
    }

    // Only testing three transitions
    private PseudoUnitTestStateTransitionGrid _createRgStateGrid(boolean aosAllOffered) {
        PseudoUnitTestStateTransitionGrid rgStateGrid =
                new PseudoUnitTestStateTransitionGrid(RG_STATES, TransitionGridYesNoEnum.ALLOWED_VALUES, "rg");
        if (aosAllOffered) {
            rgStateGrid.setTransition(AFUTTypeEnum.EXPECTED,
                    LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY,
                    LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY,
                    TransitionGridYesNoEnum.YES.getName());
            rgStateGrid.setTransition(AFUTTypeEnum.EXPECTED,
                    LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY,
                    LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY,
                    TransitionGridYesNoEnum.YES.getName());
            rgStateGrid.setTransition(AFUTTypeEnum.EXPECTED,
                    LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY,
                    LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY,
                    TransitionGridYesNoEnum.NO.getName());
        } else {
            rgStateGrid.setTransition(AFUTTypeEnum.EXPECTED,
                    LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY,
                    LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY,
                    TransitionGridYesNoEnum.YES.getName());
            rgStateGrid.setTransition(AFUTTypeEnum.EXPECTED,
                    LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY,
                    LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY,
                    TransitionGridYesNoEnum.NO.getName());
            rgStateGrid.setTransition(AFUTTypeEnum.EXPECTED,
                    LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY,
                    LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY,
                    TransitionGridYesNoEnum.YES.getName());
        }
        return rgStateGrid;
    }

    // Just test in SOC state of publishing
    public PseudoUnitTestStateTransitionGrid testRegistrationGroupInvalid(boolean aosAllOffered) throws Exception {
        String initRgState = LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY;
        String otherRgState = LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY;
        if (aosAllOffered) {
            _setAosInRgToAoState(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);
            _forceChangeLuiState(rgInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY);
        } else {
            initRgState = LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY;
            otherRgState = LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY;
            _setAosInRgToAoState(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
            _forceChangeLuiState(rgInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY);
        }

        StatusInfo status = CourseOfferingManagementUtil.getCourseOfferingService().changeRegistrationGroupState(rgInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY, CONTEXT);
        PseudoUnitTestStateTransitionGrid rgStateGrid = _createRgStateGrid(aosAllOffered);
        String result = TransitionGridYesNoEnum.YES.getName();
        if (!status.getIsSuccess()) {
            result = TransitionGridYesNoEnum.NO.getName();
        }
        rgStateGrid.setTransition(AFUTTypeEnum.ACTUAL,
                initRgState, LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY, result);
        // RG should be in invalid state now
        rgInfo = CourseOfferingManagementUtil.getCourseOfferingService().getRegistrationGroup(rgInfo.getId(), CONTEXT);
        if (!rgInfo.getStateKey().equals(LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY)) {
            throw new PseudoUnitTestException("RG should be in invalid state");
        }
        // Attempt to change it to state it shouldn't go to
        status = CourseOfferingManagementUtil.getCourseOfferingService().changeRegistrationGroupState(rgInfo.getId(), otherRgState, CONTEXT);
        result = TransitionGridYesNoEnum.YES.getName();
        if (!status.getIsSuccess()) {
            result = TransitionGridYesNoEnum.NO.getName();
        }
        rgStateGrid.setTransition(AFUTTypeEnum.ACTUAL,
                LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY, otherRgState, result);
        // Change back to invalid for now
        _forceChangeLuiState(rgInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY);
        // Attempt to change it to state it SHOULD go to
        status = CourseOfferingManagementUtil.getCourseOfferingService().changeRegistrationGroupState(rgInfo.getId(), initRgState, CONTEXT);
        result = TransitionGridYesNoEnum.YES.getName();
        if (!status.getIsSuccess()) {
            result = TransitionGridYesNoEnum.NO.getName();
        }
        rgStateGrid.setTransition(AFUTTypeEnum.ACTUAL,
                LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY, initRgState, result);
        return rgStateGrid;
    }

    private void _testAoStateTransition(TestStatePropagationForm form) throws Exception {
                String aoId = aoInfos.get(0).getId();
        for (String secondaryAoState: AoStateTransitionRefSolution.AO_STATES_ORDERED) {
            secondAoState = secondaryAoState; // Save to instance variable

            for (int i = 0; i < SOC_STATES_ORDERED.size(); i++) {
                String socState = SOC_STATES_ORDERED.get(i);
                if (secondAoState.equals(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY)) {
                    if (! (socState.equals(CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY) ||
                          socState.equals(CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY))) {
                        // Can only have AO offered in SOC published/publishing, so skip if need be
                        continue;
                    }
                }
                System.err.println("======================= SOC state = " + socState + " (Second AO state: " + secondAoState + ")");
                Map<String, PseudoUnitTestStateTransitionGrid> gridTypeToGrid =
                        testAoStateTransitionsInSocState(aoId, socInfo.getId(), socState, secondaryAoState);
                _compareGrids(gridTypeToGrid, socState, secondaryAoState, form);
                _resetSocOnly(); // Make sure to reset the SOC
            }
        }
    }

    private String _computeAoStateString(int index, int size) {
        // "O" stands for offered state and "X" stands for not offered
        String binString = Integer.toString(index, 2);
        binString = binString.replaceAll("1", "O");
        binString = binString.replaceAll("0", "X");
        while (binString.length() < size) {
            binString = "X" + binString;
        }
        return binString;
    }

    private String _getAfterDot(String s) {
        int index = s.lastIndexOf('.');
        if (index == -1) {
            return s;
        }
        s = s.substring(index + 1);
        return s;
    }

    private void _printRegGroupResults(RegGroupStateResult rgStateResult, TestStatePropagationForm form) {
        for (int i = 0; i < rgStateResult.size(); i++) {
            String aoStatesString = _computeAoStateString(i, rgStateResult.numAos());
            String expected = _getAfterDot(rgStateResult.getExpected(i));
            String actual = _getAfterDot(rgStateResult.getActual(i));
            System.err.println(aoStatesString + " expected/actual = " + expected + "/" + actual);
            RGStateWrapper rgStateWrapper = new RGStateWrapper(aoStatesString, expected, actual);
            if(rgStateWrapper.getStatus().equals("pass")){
                form.setPassCount(form.getPassCount()+1);
            } else if(rgStateWrapper.getStatus().equals("fail")){
                form.setFailCount(form.getFailCount()+1);
            }
            form.addRGFromAOStatePropagationWrapper(rgStateWrapper);

        }
    }

    private int _intPower(int base, int exponent) {
        return (int) Math.pow(base, exponent);
    }

    public RegGroupStateResult testRegistrationGroupStatePropagation(String otherAoState) throws Exception {
        RegGroupStateResult result = new RegGroupStateResult(rgInfo.getActivityOfferingIds().size());
        _resetSocOnly();
        _advanceSocState(socInfo.getId(), CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY);
        List<Integer> ints = new ArrayList<Integer>();
        int numPermutations = result.size();
        for (int i = 0; i < numPermutations; i++) {
            ints.add(i);
        }
        Random rn = new Random();
        // Randomly select values from 0 to power - 1 to test RG states
        for (int i = 0; i < numPermutations; i++) {
            int index = rn.nextInt(ints.size());
            int val = ints.get(index); // Pick a random int from the list of ints
            String resultRgState = testRegGroupPermutation(val, otherAoState);
            result.setActual(val, resultRgState);
            ints.remove(index); // Remove it so it doesn't get picked again
        }

        return result;
    }

    private String testRegGroupPermutation(int index, String otherAoState) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        List<String> aoIds = rgInfo.getActivityOfferingIds();
        int pow = _intPower(2, aoIds.size());
        if (index >= pow) {
            throw new IndexOutOfBoundsException(index + " too big for: " + pow);
        }

        String aoStateString = _computeAoStateString(index, aoIds.size());
        for (int i = 0; i < aoStateString.length(); i++) {
            char ch = aoStateString.charAt(i);
            if (ch == 'X') {
                CourseOfferingManagementUtil.getCourseOfferingService().changeActivityOfferingState(aoIds.get(i), otherAoState, CONTEXT);
            } else {
                CourseOfferingManagementUtil.getCourseOfferingService().changeActivityOfferingState(aoIds.get(i), LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY, CONTEXT);
            }
        }
        LuiInfo lui = CourseOfferingManagementUtil.getLuiService().getLui(rgInfo.getId(), CONTEXT);
        return lui.getStateKey();
    }

    private String _computeDisplayString(String aoFromState, String aoToState, int size) {
        StringBuilder transition = new StringBuilder("[AO ");
        transition.append(aoFromState).append(" => AO ").append(aoToState).append("]");
        while (transition.length() < size) {
            transition.append(" ");
        }
        return transition.toString();
    }

    private String _computeExpectedActual(String expected, String actual, int size) {
        StringBuilder result = new StringBuilder(expected);
        result.append("/").append(actual);
        while (result.length() < size) {
            result.append(" ");
        }
        return result.toString();
    }

    private void _compareGrids(Map<String, PseudoUnitTestStateTransitionGrid> gridTypeToGrid,
                               String socState, String secondAoState, TestStatePropagationForm form) {
        List<Map<String, String>> aoResults = gridTypeToGrid.get("ao").compare();
        System.err.println("---------------------- AO state results (SOC: " + socState + ")");
        for (int i = 0; i < aoResults.size(); i++) {
            // Put socState in front
            Map<String, String> resultMap = aoResults.get(i);
            String aoFromState = resultMap.get(PseudoUnitTestStateTransitionGrid.AO_STATE_FROM);
            int indexOfLastDot = aoFromState.lastIndexOf(".");
            aoFromState = aoFromState.substring(indexOfLastDot + 1);
            String aoToState = resultMap.get(PseudoUnitTestStateTransitionGrid.AO_STATE_TO);
            indexOfLastDot = aoToState.lastIndexOf(".");
            aoToState = aoToState.substring(indexOfLastDot + 1);
            String expectedVal = resultMap.get(PseudoUnitTestStateTransitionGrid.EXPECTED);
            String actualVal = resultMap.get(PseudoUnitTestStateTransitionGrid.ACTUAL);
            String passFail = resultMap.get(PseudoUnitTestStateTransitionGrid.PASS_FAIL);
            String color = passFail.equals(PseudoUnitTestStateTransitionGrid.PASS_VAL) ? "((( GREEN )))" :
                    (passFail.equals(PseudoUnitTestStateTransitionGrid.FAIL_VAL) ?
                            "*** red ***" : "... White ...");
            String transition = _computeDisplayString(aoFromState, aoToState, 30);
            String message = "(AO) " + transition +
                    " expected/actual = " + _computeExpectedActual(expectedVal, actualVal, 15) + " " + color;
            System.err.println(message);
            StatePropagationWrapper statePropagationWrapper = new StatePropagationWrapper(socState, aoFromState, aoToState, secondAoState, expectedVal, actualVal, passFail);
            if(statePropagationWrapper.getStatus().equals("pass")){
                form.setPassCount(form.getPassCount()+1);
            } else if(statePropagationWrapper.getStatus().equals("fail")){
                form.setFailCount(form.getFailCount()+1);
            }
            form.addAoStatePropagationWrapper(statePropagationWrapper);

        }
        System.err.println("---------------------- FO state results (SOC: " + socState + ")");
        List<Map<String, String>> foResults = gridTypeToGrid.get("fo").compare();
        for (int i = 0; i < foResults.size(); i++) {
            // Put socState in front
            Map<String, String> resultMap = foResults.get(i);
            String aoFromState = resultMap.get(PseudoUnitTestStateTransitionGrid.AO_STATE_FROM);
            int indexOfLastDot = aoFromState.lastIndexOf(".");
            aoFromState = aoFromState.substring(indexOfLastDot + 1);

            String aoToState = resultMap.get(PseudoUnitTestStateTransitionGrid.AO_STATE_TO);
            indexOfLastDot = aoToState.lastIndexOf(".");
            aoToState = aoToState.substring(indexOfLastDot + 1);

            String expectedVal = resultMap.get(PseudoUnitTestStateTransitionGrid.EXPECTED);
            indexOfLastDot = expectedVal.lastIndexOf(".");
            expectedVal = expectedVal.substring(indexOfLastDot + 1);

            String actualVal = resultMap.get(PseudoUnitTestStateTransitionGrid.ACTUAL);
            indexOfLastDot = actualVal.lastIndexOf(".");
            actualVal = actualVal.substring(indexOfLastDot + 1);
            String passFail = resultMap.get(PseudoUnitTestStateTransitionGrid.PASS_FAIL);
            String color = passFail.equals(PseudoUnitTestStateTransitionGrid.PASS_VAL) ? "((( GREEN )))" :
                    (passFail.equals(PseudoUnitTestStateTransitionGrid.FAIL_VAL) ?
                            "*** red ***" : "... White ...");
            String transition = _computeDisplayString(aoFromState, aoToState, 30);
            String message = "(FO) " + transition +
                    " expected/actual = " + _computeExpectedActual(expectedVal, actualVal, 15) + " " + color;
            StatePropagationWrapper statePropagationWrapper = new StatePropagationWrapper(socState, aoFromState, aoToState, secondAoState, expectedVal, actualVal, passFail);
            if(statePropagationWrapper.getStatus().equals("pass")){
                form.setPassCount(form.getPassCount()+1);
            } else if(statePropagationWrapper.getStatus().equals("fail")){
                form.setFailCount(form.getFailCount()+1);
            }
            form.addFoStatePropagationWrapper(statePropagationWrapper);
            System.err.println(message);
        }
        System.err.println("---------------------- CO state results (SOC: " + socState + ")");
        List<Map<String, String>> coResults = gridTypeToGrid.get("co").compare();
        for (int i = 0; i < foResults.size(); i++) {
            // Put socState in front
            Map<String, String> resultMap = coResults.get(i);
            String aoFromState = resultMap.get(PseudoUnitTestStateTransitionGrid.AO_STATE_FROM);
            int indexOfLastDot = aoFromState.lastIndexOf(".");
            aoFromState = aoFromState.substring(indexOfLastDot + 1);

            String aoToState = resultMap.get(PseudoUnitTestStateTransitionGrid.AO_STATE_TO);
            indexOfLastDot = aoToState.lastIndexOf(".");
            aoToState = aoToState.substring(indexOfLastDot + 1);

            String expectedVal = resultMap.get(PseudoUnitTestStateTransitionGrid.EXPECTED);
            indexOfLastDot = expectedVal.lastIndexOf(".");
            expectedVal = expectedVal.substring(indexOfLastDot + 1);
            String actualVal = resultMap.get(PseudoUnitTestStateTransitionGrid.ACTUAL);
            indexOfLastDot = actualVal.lastIndexOf(".");
            actualVal = actualVal.substring(indexOfLastDot + 1);
            String passFail = resultMap.get(PseudoUnitTestStateTransitionGrid.PASS_FAIL);
            String color = passFail.equals(PseudoUnitTestStateTransitionGrid.PASS_VAL) ? "((( GREEN )))" :
                    (passFail.equals(PseudoUnitTestStateTransitionGrid.FAIL_VAL) ?
                            "*** red ***" : "... White ...");
            String transition = _computeDisplayString(aoFromState, aoToState, 30);
            String message = "(CO) " + transition +
                    " expected/actual = " + _computeExpectedActual(expectedVal, actualVal, 15) + " " + color;
            System.err.println(message);
            StatePropagationWrapper statePropagationWrapper = new StatePropagationWrapper(socState, aoFromState, aoToState, secondAoState, expectedVal, actualVal, passFail);
            if(statePropagationWrapper.getStatus().equals("pass")){
                form.setPassCount(form.getPassCount()+1);
            } else if(statePropagationWrapper.getStatus().equals("fail")){
                form.setFailCount(form.getFailCount()+1);
            }
            form.addCoStatePropagationWrapper(statePropagationWrapper);
        }
        System.err.println("---------------------- end");
    }

    public void testSocStateHappy() throws Exception {
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


    public Map<String, PseudoUnitTestStateTransitionGrid>
    testAoStateTransitionsInSocState(String aoId, String socId, String socState, String secondaryAoState) throws Exception {
        SocInfo fetched = CourseOfferingManagementUtil.getSocService().getSoc(socId, CONTEXT);
        if (!fetched.getStateKey().equals(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY)) {
            throw new PseudoUnitTestException("Initial soc state not in DRAFT state");
        }
        if (!socState.equals(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY)) {
            _advanceSocState(socId, socState);
        }
        List<String> allowedAoValues = new ArrayList<String>();
        allowedAoValues.add(TransitionGridYesNoEnum.YES.getName());
        allowedAoValues.add(TransitionGridYesNoEnum.NO.getName());
        allowedAoValues.add(TransitionGridYesNoEnum.YES.getName());
        PseudoUnitTestStateTransitionGrid foGrid =
                new PseudoUnitTestStateTransitionGrid(AoStateTransitionRefSolution.AO_STATES_ORDERED, FO_STATES_ORDERED, "fo");
        PseudoUnitTestStateTransitionGrid coGrid =
                new PseudoUnitTestStateTransitionGrid(AoStateTransitionRefSolution.AO_STATES_ORDERED, CO_STATES_ORDERED, "co");
        foGrid.setSocStateKey(socState);
        coGrid.setSocStateKey(socState);
        PseudoUnitTestStateTransitionGrid aoGrid = AoStateTransitionRefSolution.getReferenceGridForState(socState);
        for (int i = 0; i < aoGrid.size(); i++) {
            String fromState = aoGrid.getStateKeyAt(i);
            for (int j = 0; j < aoGrid.size(); j++) {
                String toState = aoGrid.getStateKeyAt(j);
                boolean invalidTransition = aoGrid.getTransition(AFUTTypeEnum.EXPECTED, fromState, toState).equals(TransitionGridYesNoEnum.INVALID.getName());
                if (invalidTransition) {
                    aoGrid.setTransition(AFUTTypeEnum.ACTUAL, fromState, toState, TransitionGridYesNoEnum.INVALID.getName());
                    continue;
                }

                // Force the original AO to be in fromState
                _forceChangeLuiState(aoId, fromState);
                // Adjust FO/CO states
                _resetFoCoAndSecondaryAoState(fromState, secondaryAoState);
                _refetch(); // Need to get latest CO/FO/AOs

                // Attempt to change toState using normal services call
                boolean change = _tryChangingAoState(aoId, toState);
                aoGrid.setTransition(AFUTTypeEnum.ACTUAL, fromState, toState,
                        change ? TransitionGridYesNoEnum.YES.getName() : TransitionGridYesNoEnum.NO.getName());
                // Now to test FO/CO states
                _refetch();
                String localCoState = courseOfferingInfo.getStateKey();
                String localFoState = foInfos.get(0).getStateKey();
                // In order to test CO/FO state propagation, we need to pretend valid AO state transitions occurred.
                String desiredAoState = fromState; // Initialize to fromState
                if (aoGrid.getTransition(AFUTTypeEnum.EXPECTED, fromState, toState).equals(TransitionGridYesNoEnum.YES.getName())) {
                    desiredAoState = toState; // Transition valid so this is the desired AO state
                }
                if (fromState.equals(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY) &&
                        toState.equals(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY)  &&
                        socState.equals(CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY)) {
                    System.err.print("");
                }
                String foStateComputed = _computeFoState(primaryAoId, desiredAoState);
                String coStateComputed = _computeCoState(primaryAoId, desiredAoState);
                foGrid.setTransition(AFUTTypeEnum.EXPECTED, fromState, toState, foStateComputed);
                foGrid.setTransition(AFUTTypeEnum.ACTUAL, fromState, toState, localFoState);
                coGrid.setTransition(AFUTTypeEnum.EXPECTED, fromState, toState, coStateComputed);
                coGrid.setTransition(AFUTTypeEnum.ACTUAL, fromState, toState, localCoState);
            }
        }
        Map<String, PseudoUnitTestStateTransitionGrid> gridTypeToGrid = new HashMap<String, PseudoUnitTestStateTransitionGrid>();
        gridTypeToGrid.put("ao", aoGrid);
        gridTypeToGrid.put("fo", foGrid);
        gridTypeToGrid.put("co", coGrid);
        return gridTypeToGrid;
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
        CourseOfferingManagementUtil.getSocService().changeSocState(socId, nextState, ContextUtils.createDefaultContextInfo());
        SocInfo fetched = _getMainSocForTerm(SAMPLE_TERM);
        assertEquals(fetched.getStateKey(), nextState, message);
    }

    private void _changeSocStateInvalid(String socId, String nextState, String origState, String message) throws Exception {
        boolean exceptionThrown = false;
        try {
            CourseOfferingManagementUtil.getSocService().changeSocState(socId, nextState, ContextUtils.createDefaultContextInfo());
        } catch (OperationFailedException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "Exception not thrown for invalid Soc state change");
        SocInfo fetched = _getMainSocForTerm(SAMPLE_TERM);
        assertEquals(fetched.getStateKey(), origState, message);
    }

    private boolean _forceChangeLuiState(String aoId, String nextState) throws PseudoUnitTestException {
        // DanEp suggested this sneaky way to circumvent state changes
        try {
            LuiInfo lui = CourseOfferingManagementUtil.getLuiService().getLui(aoId, CONTEXT);
            lui.setStateKey(nextState);
            CourseOfferingManagementUtil.getLuiService().updateLui(lui.getId(), lui, CONTEXT);
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
            CourseOfferingManagementUtil.getCourseOfferingService().changeActivityOfferingState(aoId, nextState, CONTEXT);
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
            soc = CourseOfferingManagementUtil.getSocService().createSoc(mainTerm.getId(), socInfo.getTypeKey(), socInfo, new ContextInfo());
        }
        return soc;
    }

    private SocInfo _getMainSocForTerm(String termCode) throws Exception {
        TermInfo mainTerm = getTermByTermCode(termCode);
        ContextInfo contextInfo = new ContextInfo();

        SocInfo socInfo = CourseOfferingSetUtil.getMainSocForTermId(mainTerm.getId(), contextInfo);
        if (socInfo != null) {
            return socInfo;
        }
        return null;
    }

    public TermInfo getTermByTermCode(String termCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        // TODO: Find sensible way to rewrap exception that acal service may throw
        // Find the term (alas, I think it does approximate search)
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));
        QueryByCriteria criteria = qbcBuilder.build();

        // Do search.  In ideal case, terms returns one element, which is the desired term.
        List<TermInfo> terms = null;
        terms = CourseOfferingManagementUtil.getAcademicCalendarService().searchForTerms(criteria, new ContextInfo());
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
        TermInfo sourceTerm = getTermByTermCode(sourceTermCode);
        TermInfo targetTerm = getTermByTermCode(targetTermCode);
        List<CourseOfferingInfo> coInfos = _searchCourseOfferingByCOCodeAndTerm(courseOfferingCode, sourceTerm.getId());
        if (coInfos == null || coInfos.isEmpty()) {
            return null;
        }
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        CourseOfferingInfo coInfo = coInfos.get(0); // Just get the first one
        Date start = new Date();
        List<String> optionKeys = CourseOfferingManagementUtil.getDefaultOptionKeysService().getDefaultOptionKeysForCopySingleCourseOffering();
        SocRolloverResultItemInfo rolloverResultInfo =
                CourseOfferingManagementUtil.getCourseOfferingService().rolloverCourseOffering(coInfo.getId(), targetTerm.getId(), optionKeys, contextInfo);
        Date end = new Date();
        String targetId = rolloverResultInfo.getTargetCourseOfferingId();
        CourseOfferingInfo targetCo = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(targetId, contextInfo);

        Map<String, Object> keyToValues = new HashMap<String, Object>();
        keyToValues.put(COURSE_OFFERING_KEY, targetCo);
        return keyToValues;
    }

    private List<CourseOfferingInfo> _searchCourseOfferingByCOCodeAndTerm(String courseOfferingCode, String termId) throws Exception {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.and(
                        PredicateFactory.equal(CourseOfferingConstants.COURSEOFFERING_COURSE_OFFERING_CODE, courseOfferingCode),
                        PredicateFactory.equal(CourseOfferingConstants.ATP_ID, termId)
                ));
        QueryByCriteria criteria = qbcBuilder.build();
        List<CourseOfferingInfo> coList = CourseOfferingManagementUtil.getCourseOfferingService().searchForCourseOfferings(criteria, new ContextInfo());
        return coList;
    }
}
