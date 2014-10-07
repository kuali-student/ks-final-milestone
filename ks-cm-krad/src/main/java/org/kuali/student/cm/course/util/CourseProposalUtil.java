/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by delyea on 3/18/14
 */
package org.kuali.student.cm.course.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.student.cm.common.util.CMUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.RetireCourseWrapper;
import org.kuali.student.cm.course.form.wrapper.VersionWrapper;
import org.kuali.student.cm.proposal.util.ProposalUtil;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.constants.AtpSearchServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.dto.SortDirection;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.kuali.student.r2.lum.util.constants.CourseServiceConstants.COURSE_NAMESPACE_URI;

/**
 * This class is a Util class designed to hold common course proposal related code that should be shared across multiple classes
 *
 * @author Kuali Student Team
 */
public class CourseProposalUtil {

    private static final Logger LOG = LoggerFactory.getLogger(CourseProposalUtil.class);

    public enum Position {PREVIOUS, NEXT};

    /**
     * Constructs a text URL for a particular course proposal.
     */
    public static String buildCourseProposalUrl(String methodToCall, String pageId, String workflowDocId, String proposalType) {
        Properties props = ProposalUtil.getProposalUrlProperties(methodToCall, pageId, workflowDocId);
        if (CurriculumManagementConstants.CoursePageIds.REVIEW_RETIRE_COURSE_PROPOSAL_PAGE.equals(pageId)) {
            props.put(UifParameters.DATA_OBJECT_CLASS_NAME, RetireCourseWrapper.class.getCanonicalName());
        } else {
            props.put(UifParameters.DATA_OBJECT_CLASS_NAME, CourseInfoWrapper.class.getCanonicalName());
        }
        String controllerRequestMapping = CurriculumManagementConstants.ControllerRequestMappings.MappingsByProposalType.getControllerMapping(proposalType);
        if (StringUtils.isBlank(controllerRequestMapping)) {
            throw new RuntimeException("Cannot find request mapping for proposal type: " + proposalType);
        }
        String courseBaseUrl = controllerRequestMapping.replaceFirst("/", "");
        return UrlFactory.parameterizeUrl(courseBaseUrl, props);
    }

    /**
     * Constructs the url for view course for the given courseId value
     *
     * NOTE: Will add a return location to the CM Homepage
     */
    public static String getViewCourseUrl(String courseId){
        String courseBaseUrl = CurriculumManagementConstants.ControllerRequestMappings.VIEW_COURSE.replaceFirst("/", "");
        return UrlFactory.parameterizeUrl(courseBaseUrl, buildViewCourseUrlProperties(courseId));
    }

    public static Properties buildViewCourseUrlProperties(String courseId) {
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        props.put(UifConstants.UrlParams.VIEW_ID, CurriculumManagementConstants.CourseViewIds.VIEW_COURSE_VIEW);
        props.put(CurriculumManagementConstants.UrlParams.COURSE_ID, courseId);
        props.put(KRADConstants.RETURN_LOCATION_PARAMETER, CMUtils.getCMHomeUrl());
        return props;
    }

    /**
     * Returns the current course version of the given  version independent id such that the returned course :
     *  - version start date is before 'now'
     *  - version end date is after 'now' or is not set
     * @param versionIndId
     * @param contextInfo
     * @return
     * @throws Exception
     */
    public static CourseInfo getCurrentVersionOfCourse(String versionIndId, ContextInfo contextInfo) throws Exception {

        // Get id of current version of course given the version independent id
        VersionDisplayInfo curVerDisplayInfo = CMUtils.getCourseService().getCurrentVersion(COURSE_NAMESPACE_URI, versionIndId, contextInfo);
        String curVerId = curVerDisplayInfo.getId();

        // Return the current version of the course
        CourseInfo currVerCourse = CMUtils.getCourseService().getCourse(curVerId, contextInfo);

        return currVerCourse;
    }

    /**
     * Checks if the Course is modifiable. A Course is modifiable if:
     * - it is the 'current' version
     * - There is no later version in either 'DRAFT' or 'SUPERSEDED' states
     * @param courseInfo
     * @param contextInfo
     * @return
     * @throws Exception
     */
    public static boolean isModifiableCourse(CourseInfo courseInfo, ContextInfo contextInfo) throws Exception {

        // If this is not 'current' course, return 'false' immediately
        if(!isCurrentVersionOfCourse(courseInfo, contextInfo)) {
            return false;
        }

        String versionIndId = courseInfo.getVersion().getVersionIndId();
        Long versionSequenceNumber = courseInfo.getVersion().getSequenceNumber();

        SearchRequestInfo request = new SearchRequestInfo("lu.search.isVersionable");
        request.addParam("lu.queryParam.versionIndId", versionIndId);
        request.addParam("lu.queryParam.sequenceNumber", versionSequenceNumber.toString());
        List<String> states = new ArrayList<String>();
        states.add(DtoConstants.STATE_DRAFT);
        states.add(DtoConstants.STATE_SUPERSEDED);
        request.addParam("lu.queryParam.luOptionalState", states);
        SearchResultInfo result = CMUtils.getCluService().search(request, contextInfo);

        String resultString = result.getRows().get(0).getCells().get(0).getValue();
        return "0".equals(resultString);
    }

    /**
     * A course cannot be modified to new version if:
     * - There is no later version in either 'DRAFT' or 'SUPERSEDED' states
     * - There is no version in "in progress" 'RETIRE' state.
     * @param courseInfo
     * @param contextInfo
     * @return
     * @throws Exception
     */

    public static boolean isModifyNewVersion(CourseInfo courseInfo, ContextInfo contextInfo) throws Exception {

        if(!hasDraftCourseOrSupersededVersion(courseInfo,contextInfo)) {
            return false;
        }
        else {
            return !hasInProgressProposalForCourse(courseInfo);
        }
    }

    /**
     * Return false if there is a 'DRAFT' course or given Version is in 'SUPERSEDED' state
     * @param courseInfo
     * @param contextInfo
     * @return
     * @throws Exception
     */
    public static boolean hasDraftCourseOrSupersededVersion(CourseInfo courseInfo, ContextInfo contextInfo) throws Exception {

        String versionIndId = courseInfo.getVersion().getVersionIndId();
        Long versionSequenceNumber = courseInfo.getVersion().getSequenceNumber();

        SearchRequestInfo sr = new SearchRequestInfo();
        List<SearchParamInfo> params = new ArrayList<SearchParamInfo>();
        SearchParamInfo param = new SearchParamInfo();
        param.setKey("lu.queryParam.cluVersionIndId");
        param.getValues().add(versionIndId);
        params.add(param);
        sr.setSortDirection(SortDirection.DESC);
        sr.setParams(params);
        sr.setSearchKey("lu.search.clu.versions");
        sr.setSortColumn("lu.resultColumn.luOptionalVersionSeqNum");

        SearchResultInfo searchResult = CMUtils.getCluService().search(sr, contextInfo);

        List<SearchResultRowInfo> rows = searchResult.getRows();
        for (SearchResultRowInfo row : rows) {
            List<SearchResultCellInfo> cells = row.getCells();
            VersionWrapper courseVersionWrapper = new VersionWrapper();
            for (SearchResultCellInfo cell : cells) {
                if (cell.getKey().equals("lu.resultColumn.cluId")) {
                    courseVersionWrapper.setCluId(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalVersionSeqNum")) {
                    courseVersionWrapper.setSequence(Long.valueOf(cell.getValue()));
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalState")) {
                    courseVersionWrapper.setCourseStatus(cell.getValue());
                }
            }
            if ((courseVersionWrapper.getSequence()).equals(versionSequenceNumber) && (StringUtils.equals(courseVersionWrapper.getCourseStatus(), DtoConstants.STATE_SUPERSEDED))) {
                return false;
            } else  if (StringUtils.equals(courseVersionWrapper.getCourseStatus(),DtoConstants.STATE_DRAFT)){
                    return false;
            }
        }
        return true;
    }

    public static boolean isCurrentVersionOfCourse(CourseInfo course, ContextInfo contextInfo) throws Exception {
        String courseId = course.getId();
        String verIndId = course.getVersion().getVersionIndId();

        // Get id of current version of course given the version independent id
        VersionDisplayInfo currentVersion = CMUtils.getCourseService().getCurrentVersion(COURSE_NAMESPACE_URI, verIndId, contextInfo);

        return currentVersion.getId().equals(courseId) ? true : false;
    }

    /**
     * We can only have one retire proposal in workflow at a time.  This method
     * will call the proposal webservice and run a custom search that will look
     * for any retire proposals that are in the saved or enroute state.  A
     * count is returned and, if the count is > 0, this method will return false.
     */
    public static boolean hasInProgressProposalForCourse(CourseInfo courseInfo) throws Exception {

        if (courseInfo == null) {
            throw new RuntimeException("Cannot verify if retire proposal can be created without valid course");
        }
        // Fill the request with the key to identify the search
        SearchRequestInfo request = new SearchRequestInfo("proposal.search.countForProposals");

        // Add search parms.  In this case, we will use the cluId of the course
        // we are trying to retire
        request.addParam("proposal.queryParam.cluId", courseInfo.getId());

        // Perform search and get the result
        SearchResultInfo result = CMUtils.getProposalService().search(request, ContextUtils.getContextInfo());
        String resultString = result.getRows().get(0).getCells().get(0).getValue();

        // If there are no retire proposals enroute or in saved status/
        // return false, else return true
        return !("0".equals(resultString));
    }

    /**
     * Compares requisites by natural language. Assumes that the rule editor keys used are A-F or G-L, and that they
     * are consistently in the same order alphabetically by key and by type: prereq, coreq, recommended, etc.
     * @return True if the requisites are the same. Otherwise, false.
     */
    public static boolean isRequisitesEqual(final List<AgendaEditor> leftAgendas, final List<AgendaEditor> rightAgendas) {
        /*
         * Put all of the rule editors from each agenda in a single map per side.
         */
        Map<String, RuleEditor> leftRuleEditors = new HashMap<>();
        Map<String, RuleEditor> rightRuleEditors = new HashMap<>();

        for (AgendaEditor ae : leftAgendas) {
            leftRuleEditors.putAll(ae.getRuleEditors());
        }

        for (AgendaEditor ae : rightAgendas) {
            rightRuleEditors.putAll(ae.getRuleEditors());
        }

        if (leftRuleEditors.size() != rightRuleEditors.size()) {
            return false;
        }

        Map<String, String> ruleEditorMap = buildRuleEditorMap(leftRuleEditors, rightRuleEditors);

        for (Map.Entry<String, RuleEditor> re : leftRuleEditors.entrySet()) {

            String leftRuleEditorKey = re.getKey();
            String rightRuleEditorKey = ruleEditorMap.get(leftRuleEditorKey);

            RuleEditor leftRuleEditor = re.getValue();

            //  Get the left proposition and natural language map
            PropositionEditor leftProposition = leftRuleEditor.getPropositionEditor();
            //  If the left proposition is null but the right isn't then the requisites are different
            if (leftProposition == null) {
                if (rightRuleEditors.get(rightRuleEditorKey) != null
                        && rightRuleEditors.get(rightRuleEditorKey).getProposition() != null) {
                    return false;
                } else {
                    continue; //  Both are null so go to the next item.
                }
            }
            Map<String, String> leftNaturalLanguageMap = leftProposition.getNaturalLanguage();

            //  Get the right rule and proposition editors
            RuleEditor rightRuleEditor = rightRuleEditors.get(rightRuleEditorKey);
            //  If no right rule editor then the requisites are different.
            if (rightRuleEditor == null) {
                return false;
            }
            PropositionEditor rightPropositionEditor = rightRuleEditor.getPropositionEditor();
            if (rightPropositionEditor == null) {
                return false;
            }

            /*
             * Compare the natural language for each proposition in both directions
             */
            Map<String, String> rightNaturalLanguageMap = rightPropositionEditor.getNaturalLanguage();
            //  If the natural language maps aren't the same size then no more comparison is necessary
            if (leftNaturalLanguageMap.size() != rightNaturalLanguageMap.size()) {
               return false;
            }

            for (Map.Entry<String, String> leftNl : leftNaturalLanguageMap.entrySet()) {
                String leftNlKey = leftNl.getKey();
                String leftNlValue = leftNl.getValue();

                String rightNlValue = rightNaturalLanguageMap.get(leftNlKey);

                if (rightNlValue == null || ! leftNlValue.equals(rightNlValue)) {
                    return false;
                }
            }

            for (Map.Entry<String, String> rightNl : rightNaturalLanguageMap.entrySet()) {
                String rightNlKey = rightNl.getKey();
                String rightNlValue = rightNl.getValue();

                String leftNlValue = rightNaturalLanguageMap.get(rightNlKey);

                if (leftNlValue == null || ! rightNlValue.equals(leftNlValue)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Assuming that the rule editors are in alphabetical order, creates a map of the corresponding keys from left to right.
     * @param leftRuleEditors The rule editors on the left side of the compare.
     * @param rightRuleEditors The rule editors on the right.
     * @return A key mapping for rule editors.
     */
    private static Map<String, String> buildRuleEditorMap(Map<String, RuleEditor> leftRuleEditors, Map<String, RuleEditor> rightRuleEditors) {
        Map<String, String> ruleEditorMap = new HashMap<>();

        List<String> leftKeys = new ArrayList<>(leftRuleEditors.keySet());
        Collections.sort(leftKeys, RULE_EDITOR_COMPARATOR);

        List<String> rightKeys = new ArrayList<>(rightRuleEditors.keySet());
        Collections.sort(rightKeys, RULE_EDITOR_COMPARATOR);

        int index = 0;
        for (String key : leftKeys) {
            ruleEditorMap.put(key, rightKeys.get(index));
            index++;
        }
        return ruleEditorMap;
    }

    /**
     * Comparator for rule editor. They need special handling because the keys can be sequences
     * like: Y Z AA AB AC etc
     */
    public static final Comparator<String> RULE_EDITOR_COMPARATOR = new Comparator<String>() {
        @Override
        public int compare(String key1, String key2) {

            if (key1.length() < key2.length()) {
                return -1;
            }

            if (key1.length() > key2.length()) {
                return 1;
            }
            return key1.compareTo(key2);
        }
    };

    /**
     *  Returns the previous or next term to the supplied term, subject to ATP types of interest
     *  Note: Ideally we should send ATP Types based on the Duration Type grouping that the given term belongs to.
     *        This should be considered when CM starts using Type Service
     *
     * @param termAtpId
     * @param position  'PREVIOUS' finds the term which is right before 'termAptId'
     *                  and 'NEXT' finds the term right after 'termAtpId'
     * @param contextInfo
     * @return  If no matching results are found, return empty TermResult with null values - KRAD handles 'null' fine
     */

    public static List<TermResult> getTerm(String termAtpId, List<String> termTypeKeys, Position position, ContextInfo contextInfo)  {

        List<TermResult> termResults = new ArrayList<>();

        if (termAtpId == null) {
            return null;
        }

        final SearchRequestInfo atpSearchRequest = new SearchRequestInfo(AtpSearchServiceConstants.ATP_SEARCH_ADVANCED);
        atpSearchRequest.addParam(AtpSearchServiceConstants.ATP_ADVANCED_QUERYPARAM_ATP_TYPE, termTypeKeys);
        if (position.equals(Position.PREVIOUS)) {
            atpSearchRequest.addParam(AtpSearchServiceConstants.ATP_ADVANCED_QUERYPARAM_ATP_END_DATE_BEFORE_START_DATE_CONSTRAINT_EXCLUSIVE, termAtpId);
        }
        else if (position.equals(Position.NEXT)) {
            atpSearchRequest.addParam(AtpSearchServiceConstants.ATP_ADVANCED_QUERYPARAM_ATP_START_DATE_AFTER_END_DATE_CONSTRAINT_EXCLUSIVE, termAtpId);
        }
        try {
            SearchResultInfo searchResult = CMUtils.getAtpService().search(atpSearchRequest, contextInfo);
            if (searchResult.getRows().size() > 0) {
                for (SearchResultRowInfo row : searchResult.getRows()) {
                    List<SearchResultCellInfo> srCells = row.getCells();
                    if (srCells != null && srCells.size() > 0) {
                        TermResult termResult = new TermResult();
                        for (SearchResultCellInfo cell : srCells) {
                            if (AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_ID.equals(cell.getKey())) {
                                termResult.atpId = cell.getValue();
                            }
                            if (AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_START_DATE.equals(cell.getKey())) {
                                termResult.startDate = DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(cell.getValue());
                            }
                            // Note:End date is a required field on ATP
                            if (AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_END_DATE.equals(cell.getKey())) {
                                termResult.endDate = DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(cell.getValue());
                            }
                            if (AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_SHORT_NAME.equals(cell.getKey())){
                                termResult.shortName = cell.getValue();
                            }
                        }
                        termResults.add(termResult);
                    }
                }
            }

        } catch (Exception e) {
            LOG.error("Error obtaining terms", e);
            throw new RuntimeException(e);
        }
        return termResults;
    }

    /**
     *  Returns previous term to the supplied term
     * @param startTermAtpId
     * @param contextInfo
     * @return  If there is no previous term, return empty TermResult (KRAD can handle null, if getShortName is used)
     */

    public static TermResult getPreviousTerm(String startTermAtpId, ContextInfo contextInfo)  {

        List<String> termTypeKeys = new ArrayList<String>();

        // Note: The  correct design  is to consult Duration Type grouping in the Type Service
        // (using the Duration Type of supplied 'termAtpId'). This is currently out of scope for CM work, as Type Service is
        // not used
        termTypeKeys.add("kuali.atp.type.Spring");
        termTypeKeys.add("kuali.atp.type.Fall");
        termTypeKeys.add("kuali.atp.type.Winter");
        termTypeKeys.add("kuali.atp.type.Summer");

        List<TermResult> termResults = CourseProposalUtil.getTerm(startTermAtpId, termTypeKeys, Position.PREVIOUS, contextInfo);

        if (termResults == null || termResults.size() < 1) {
            return new TermResult();
        }
        Collections.sort(termResults, new Comparator<TermResult>() {
            public int compare(TermResult first, TermResult second) {
                // Sort descending order of endDate
                return second.endDate.compareTo(first.endDate);
            }
        });
        return termResults.get(0);
    }

    /**
     * Returns next terms to the supplied term in ascending order of startDate
     * @param termId
     * @param contextInfo
     * @return
     */
    public static List<TermResult> getNextTerms(String termId, ContextInfo contextInfo)  {

        List<String> termTypeKeys = new ArrayList<String>();
        // Changed to just Spring & Fall  types per Functional requirement (kscm-2792)
        termTypeKeys.add("kuali.atp.type.Spring");
        termTypeKeys.add("kuali.atp.type.Fall");

        List<TermResult> termResults = CourseProposalUtil.getTerm(termId, termTypeKeys, Position.NEXT, contextInfo);

        Collections.sort(termResults, new Comparator<TermResult>() {
            public int compare(TermResult first, TermResult second) {
                // Sort ascending order of startDate
                return first.startDate.compareTo(second.startDate);
            }
        });
        return termResults;
    }

    /**
     *  Return appropriate EndTerm information
     *  - If EndTerm is set on current version, return that term(ATP) information
     *  - else obtain previous term of startTerm
     *  Note: If just EndTerm AtpId is desired, don't use this method:
     *        call getCurrentVersionofCourse directly and avoid the ATPService call
     * @param startTerm
     * @param versionIndependentId
     * @param contextInfo
     * @return
     */

    public static TermResult getEndTerm( String startTerm, String versionIndependentId, ContextInfo contextInfo) {
        String currentVersionEndTermId = null;
        TermResult termResult = new TermResult();
        try {
            currentVersionEndTermId = CourseProposalUtil.getCurrentVersionOfCourse(versionIndependentId, contextInfo).getEndTerm();
        } catch (Exception e) {
            LOG.error("Could not get current course for version.", e);
        }
        if (currentVersionEndTermId != null) {
            // Pilot or retired course
            try {
                AtpInfo atpInfo = CMUtils.getAtpService().getAtp(currentVersionEndTermId, contextInfo);
                termResult.startDate = atpInfo.getStartDate();
                termResult.endDate = atpInfo.getEndDate();
                termResult.shortName  = atpInfo.getName();
                termResult.atpId = atpInfo.getId();
            }
            catch (Exception ex) {
                String msg = String.format("Unable to find ATPId [%s].", currentVersionEndTermId);
                LOG.error(msg, ex);
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CurriculumManagementConstants.MessageKeys.ERROR_DATA_NOT_FOUND);
            }
        }
        else {
            // If the endTerm is not set on the currentVersion, then obtain the previous term based on the 'startTerm'
            termResult  = CourseProposalUtil.getPreviousTerm(startTerm, ContextUtils.createDefaultContextInfo());
        }
        return termResult;
    }

    /**
     *  Add or update the attribute list.
     * @param attributeInfoList
     * @param key
     * @param value
     */
    public static void addOrUpdateAttributes(List<AttributeInfo> attributeInfoList, String key, String value) {
        boolean exist = false;
        for (AttributeInfo attrInfo : attributeInfoList) {
            if (attrInfo.getKey().equals(key)) {
                exist = true;
                if ((attrInfo.getValue() != null && !attrInfo.getValue().equalsIgnoreCase(value)) ||
                        (attrInfo.getValue() == null && value != null)) {
                    attrInfo.setValue(value);
                    break;
                }
            }
        }
        if (!exist && StringUtils.isNotBlank(value)) {
            attributeInfoList.add(new AttributeInfo(key, value));
        }
    }

    public static class TermResult {
        private String atpId;
        private String shortName;
        private Date endDate;
        private Date startDate;

        public String getAtpId() {
            return atpId;
        }
        public String getShortName() {
            return shortName;
        }
        public Date getStartDate() {
            return endDate;
        }
        public Date getEndDate() {
            return endDate;
        }
    }
}