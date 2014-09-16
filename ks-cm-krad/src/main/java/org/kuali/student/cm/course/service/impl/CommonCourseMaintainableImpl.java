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
 * Created by delyea on 9/10/14
 */
package org.kuali.student.cm.course.service.impl;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kew.framework.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.IDocumentEvent;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.wrapper.CommonCourseDataWrapper;
import org.kuali.student.cm.course.form.wrapper.CourseCreateUnitsContentOwner;
import org.kuali.student.cm.course.service.CommonCourseMaintainable;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.cm.proposal.service.impl.ProposalMaintainableImpl;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.lum.workflow.CourseStateChangeServiceImpl;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.AttributeHelper;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class can be used to hold common Course elements including Post Processing methods
 *
 * @author Kuali Student Team
 */
public abstract class CommonCourseMaintainableImpl extends ProposalMaintainableImpl implements CommonCourseMaintainable {

    private static final Logger LOG = LoggerFactory.getLogger(CommonCourseMaintainableImpl.class);

    private transient CourseService courseService;

    private CourseStateChangeServiceImpl courseStateChangeService;

    private transient SubjectCodeService subjectCodeService;

    private transient AtpService atpService;

    /**
     * This is used to ignore the post processing for the "Modify This Version" proposal (also known as the "Modify No Version" proposal)
     * because that document type does not have a new version of the course and does no state changing of the course that it's modifying. The
     * save of the data will happen from the Controller rather than here in the post processing.

     * @see ProposalMaintainableImpl#shouldIgnorePostProcessing(String)
     *
     * @return true if the document type of the given document is {@link CurriculumManagementConstants.DocumentTypeNames.CourseProposal#COURSE_MODIFY_ADMIN_NOVERSION}
     */
    protected boolean shouldIgnorePostProcessing(String documentId) {
        String documentTypeName = getWorkflowDocumentService().getDocumentTypeName(documentId);
        // it may be overkill to verify that the document type name is not null here, but we CANNOT allow
        // post processing to run on a "modify this version" proposal (also known as a "modify no version" proposal)
        if (documentTypeName == null) {
            throw new RuntimeException("Cannot run post processing logic if we cannot find a valid documen type name");
        }
        return StringUtils.equals(CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_MODIFY_ADMIN_NOVERSION, documentTypeName);
    }

    protected String getCourseId(ProposalInfo proposalInfo) throws OperationFailedException {
        if (proposalInfo.getProposalReference().size() != 1) {
            String message = String.format("Found %s CLU objects linked to proposal with docId='%s' and proposalId='%s'. Must have exactly 1 linked.",
                    proposalInfo.getProposalReference().size(), proposalInfo.getWorkflowId(), proposalInfo.getId());
            LOG.error(message);
            throw new OperationFailedException(message);
        }
        return proposalInfo.getProposalReference().get(0);
    }

    /**
     * This method takes a clu proposal, determines what the "new state"
     * of the clu should be, then routes the clu I, and the new state
     * to CourseStateChangeServiceImpl.java
     */
    @Override
    protected void processCustomRouteStatusChange(DocumentRouteStatusChange statusChangeEvent, ProposalInfo proposalInfo) throws Exception {

        String courseId = getCourseId(proposalInfo);
        String prevEndTermAtpId = new AttributeHelper(proposalInfo.getAttributes()).get("prevEndTerm");

        // Get the current "existing" courseInfo
        CourseInfo courseInfo = getCourseService().getCourse(courseId, ContextUtils.createDefaultContextInfo());

        // Get the new state the course should now change to
        String newCourseState = getCluStateForRouteStatus(courseInfo.getStateKey(), statusChangeEvent.getNewRouteStatus(), proposalInfo.getType());

        //Use the state change service to update to active and update preceding versions
        if (newCourseState != null) {
            switch (newCourseState) {
                case DtoConstants.STATE_ACTIVE:
                    // Change the state using the effective date as the version start date
                    // update course and save it for retire if state = retire
                    getCourseStateChangeService().changeState(courseId, newCourseState, prevEndTermAtpId, ContextUtils.createDefaultContextInfo());
                    break;
                case DtoConstants.STATE_RETIRED:
                    // Retire By Proposal will come through here, extra data will need
                    // to be copied from the proposalInfo to the courseInfo fields before
                    // the save happens.
                    retireCourseByProposalCopyAndSave(newCourseState, courseInfo, proposalInfo);
                    getCourseStateChangeService().changeState(courseId, newCourseState, prevEndTermAtpId, ContextUtils.createDefaultContextInfo());
                    break;
                default:
                    updateCourseIfNecessary(statusChangeEvent, newCourseState, courseInfo, proposalInfo);
            }
        }
    }

    @Override
    protected void processCustomActionTaken(ActionTakenEvent actionTakenEvent, ActionTaken actionTaken, ProposalInfo proposalInfo) throws Exception {
        String cluId = getCourseId(proposalInfo);
        CourseInfo courseInfo = getCourseService().getCourse(cluId, ContextUtils.createDefaultContextInfo());
        // submit, blanket approve action taken comes through here.
        updateCourseIfNecessary(actionTakenEvent, null, courseInfo, proposalInfo);
    }

    /**
     * This method exists for implementations to override to add custom logic at the point a save action is taken
     *
     * @param actionTakenEvent
     * @param actionTaken
     * @throws Exception
     */
    @Override
    protected void processCustomSaveActionTaken(ActionTakenEvent actionTakenEvent, ActionTaken actionTaken) throws Exception {
        // do nothing
    }

    /**
     * This method changes the state of the course when a Withdraw action is processed on a proposal.
     * For create and modify proposals, a new clu was created which needs to be cancelled via
     * setting it to "not approved."
     * <p/>
     * For retirement proposals, a clu is never actually created, therefore we don't update the clu at
     * all if it is withdrawn.
     *
     * @param actionTakenEvent - contains the docId, the action taken (code "d"), the principalId which submitted it, etc
     * @param proposalInfo     - The proposal object being withdrawn
     */
    @Override
    protected void processWithdrawActionTaken(ActionTakenEvent actionTakenEvent, ProposalInfo proposalInfo) throws Exception {

        if (proposalInfo != null) {
            String proposalDocType = proposalInfo.getType();
            // The current two proposal docTypes which being withdrawn will cause a course to be
            // disapproved are Create and Modify (because a new DRAFT version is created when these
            // proposals are submitted.)
            if (ArrayUtils.contains(CurriculumManagementConstants.DocumentTypeNames.COURSE_CREATE_DOC_TYPE_NAMES, proposalDocType)
                    || ArrayUtils.contains(CurriculumManagementConstants.DocumentTypeNames.COURSE_MODIFY_DOC_TYPE_NAMES, proposalDocType)) {
                LOG.info("Will set CLU state to '{}'", DtoConstants.STATE_NOT_APPROVED);
                // Get Clu
                CourseInfo courseInfo = getCourseService().getCourse(
                        getCourseId(proposalInfo), ContextUtils.createDefaultContextInfo());
                // Update Clu
                updateCourseIfNecessary(actionTakenEvent, DtoConstants.STATE_NOT_APPROVED,
                        courseInfo, proposalInfo);
            }
            // Retire proposal is the only proposal type at this time which will not require a
            // change to the clu if withdrawn.
            else if (ArrayUtils.contains(CurriculumManagementConstants.DocumentTypeNames.COURSE_RETIRE_DOC_TYPE_NAMES, proposalDocType)) {
                LOG.info("Withdrawing a retire proposal with ID'{}, will not change any CLU state as there is no new CLU object to set.",
                        proposalInfo.getId());
            }
        } else {
            LOG.info("Proposal Info is null when a withdraw proposal action was taken, doing nothing.");
        }
    }

    protected boolean preProcessCourseSave(IDocumentEvent iDocumentEvent, CourseInfo courseInfo) {
        // do nothing
        return false;
    }

    protected void updateCourseIfNecessary(IDocumentEvent iDocumentEvent, String courseState, CourseInfo courseInfo, ProposalInfo proposalInfo) throws Exception {
        // only change the state if the course is not currently set to that state
        boolean requiresSave = false;
        if (courseState != null) {
            LOG.info("Setting state '{}' on CLU with cluId='{}'", courseState, courseInfo.getId());
            courseInfo.setStateKey(courseState);
            requiresSave = true;
        }
        LOG.info("Running preProcessCluSave with cluId='{}'", courseInfo.getId());
        requiresSave |= preProcessCourseSave(iDocumentEvent, courseInfo);

        if (requiresSave) {
            getCourseService().updateCourse(courseInfo.getId(), courseInfo, ContextUtils.createDefaultContextInfo());

            //For a newly approved course (w/no prior active versions), make the new course the current version.
            if (DtoConstants.STATE_ACTIVE.equals(courseState) && courseInfo.getVersion().getCurrentVersionStart() == null) {
                // How are other courses set to Superseded state?

                // if current version's state is not active then we can set this course as the active course
                //if (!DtoConstants.STATE_ACTIVE.equals(getCourseService().getCourse(getCourseService().getCurrentVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, courseInfo.getVersion().getVersionIndId()).getId()).getState())) {
                getCourseService().setCurrentCourseVersion(courseInfo.getId(), null, ContextUtils.createDefaultContextInfo());
                //}
            }

            List<StatementTreeViewInfo> statementTreeViewInfos = getCourseService().getCourseStatements(courseInfo.getId(), null, null, ContextUtils.createDefaultContextInfo());
            if (statementTreeViewInfos != null) {
                statementTreeViewInfoStateSetter(courseInfo.getStateKey(), statementTreeViewInfos.iterator());

                for (Iterator<StatementTreeViewInfo> it = statementTreeViewInfos.iterator(); it.hasNext(); )

                    getCourseService().updateCourseStatement(courseInfo.getId(), courseState, it.next(), ContextUtils.createDefaultContextInfo());
            }
        }

    }

    /**
     * Recursively set state for StatementTreeViewInfo
     *
     * We are not able to reuse the code in CourseStateUtil for dependency reason.
     */
    public void statementTreeViewInfoStateSetter(String courseState, Iterator<StatementTreeViewInfo> itr) {
        while (itr.hasNext()) {
            StatementTreeViewInfo statementTreeViewInfo = (StatementTreeViewInfo) itr.next();
            statementTreeViewInfo.setState(courseState);
            List<ReqComponentInfo> reqComponents = statementTreeViewInfo.getReqComponents();
            for (Iterator<ReqComponentInfo> it = reqComponents.iterator(); it.hasNext(); )
                it.next().setState(courseState);

            statementTreeViewInfoStateSetter(courseState, statementTreeViewInfo.getStatements().iterator());
        }
    }

    /**
     * Default behavior is to return the <code>newCluState</code> variable only if it differs from the
     * <code>currentCluState</code> value. Otherwise <code>null</code> will be returned.
     */
    protected String getCourseStateFromNewState(String currentCourseState, String newCourseState) {
        LOG.info("current CLU state is '{}' and new CLU state will be '{}'", currentCourseState, newCourseState);
        return getStateFromNewState(currentCourseState, newCourseState);
    }

    /**
     * This method returns the state a clu should go to, based on
     * the Proposal's docType and the newWorkflow StatusCode
     * which are passed in.
     *
     * @param currentCluState       - the current state set on the CLU
     * @param newWorkflowStatusCode - the new route status code that is getting set on the workflow document
     * @param docType               - The doctype of the proposal which kicked off this workflow.
     * @return the CLU state to set or null if the CLU does not need it's state changed
     */
    protected String getCluStateForRouteStatus(String currentCluState, String newWorkflowStatusCode, String docType) {
        if (ArrayUtils.contains(CurriculumManagementConstants.DocumentTypeNames.COURSE_RETIRE_DOC_TYPE_NAMES, docType)) {
            // This is for Retire Proposal, Course State should remain active for
            // all other route statuses.
            if (KewApiConstants.ROUTE_HEADER_PROCESSED_CD.equals(newWorkflowStatusCode)) {
                return DtoConstants.STATE_RETIRED;
            }
            return null;  // returning null indicates no change in course state required
        } else {
            //  The following is for Create, Modify, and Admin Modify proposals.
            if (StringUtils.equals(KewApiConstants.ROUTE_HEADER_SAVED_CD, newWorkflowStatusCode)) {
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_DRAFT);
            } else if (KewApiConstants.ROUTE_HEADER_CANCEL_CD.equals(newWorkflowStatusCode)) {
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_NOT_APPROVED);
            } else if (KewApiConstants.ROUTE_HEADER_ENROUTE_CD.equals(newWorkflowStatusCode)) {
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_DRAFT);
            } else if (KewApiConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(newWorkflowStatusCode)) {
                /* current requirements state that on a Withdraw (which is a KEW Disapproval) the
                 * CLU state should be submitted so no special handling required here
                 */
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_NOT_APPROVED);
            } else if (KewApiConstants.ROUTE_HEADER_PROCESSED_CD.equals(newWorkflowStatusCode)) {
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_ACTIVE);
            } else if (KewApiConstants.ROUTE_HEADER_EXCEPTION_CD.equals(newWorkflowStatusCode)) {
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_DRAFT);
            } else {
                // no status to set
                return null;
            }
        }
    }

    /**
     * In this method, the proposal object fields are copied to the cluInfo object
     * fields to pass validation. This method copies data from the custom Retire
     * By Proposal proposalInfo Object Fields into the courseInfo object so that upon save it will
     * pass validation.
     * <p/>
     * Admin Retire and Retire by Proposal both end up here.
     * <p/>
     * This Route will get you here, Route Statuses:
     * 'S' Saved
     * 'R' Enroute
     * 'A' Approved - After final approve, status is set to 'A'
     * 'P' Processed - During this run through coursepostprocessorbase, assuming
     * doctype is Retire, we end up here.
     *
     * @param courseState  - used to confirm state is retired
     * @param courseInfo   - course object we are updating
     * @param proposalInfo - proposal object which has the on-screen fields we are copying from
     */
    protected void retireCourseByProposalCopyAndSave(String courseState, CourseInfo courseInfo, ProposalInfo proposalInfo) throws Exception {
        if (!StringUtils.equals(DtoConstants.STATE_RETIRED, courseState)) {
            throw new RuntimeException("Attempted to call a retire operation using course state '" + courseState + "'");
        }

        setRetirementAttributesOnCourse(courseInfo, proposalInfo);

        // Save the Data to the DB
        getCourseService().updateCourse(courseInfo.getId(), courseInfo, ContextUtils.createDefaultContextInfo());
    }

    public void setRetirementAttributesOnCourse(CourseInfo courseInfo, ProposalInfo proposalInfo) {
        // Copy the data to the object -
        // These Proposal Attribs need to go back to courseInfo Object
        // to pass validation.
        if ((proposalInfo != null) && (proposalInfo.getAttributes() != null)) {
            String rationale = null;
            if (proposalInfo.getRationale() != null) {
                rationale = proposalInfo.getRationale().getPlain();
            }
            String proposedEndTerm = new AttributeHelper(proposalInfo.getAttributes()).get(CurriculumManagementConstants.PROPOSED_END_TERM);
            String proposedLastTermOffered = new AttributeHelper(proposalInfo.getAttributes()).get(CurriculumManagementConstants.PROPOSED_LAST_TERM_OFFERED);
            String proposedLastCourseCatalogYear = new AttributeHelper(proposalInfo.getAttributes()).get(CurriculumManagementConstants.PROPOSED_LAST_COURSE_CATALOG_YEAR);

            courseInfo.setEndTerm(proposedEndTerm);
            courseInfo.getAttributes().add(new AttributeInfo(CurriculumManagementConstants.COURSE_ATTRIBUTE_RETIREMENT_RATIONALE, rationale));
            courseInfo.getAttributes().add(new AttributeInfo(CurriculumManagementConstants.COURSE_ATTRIBUTE_LAST_TERM_OFFERED, proposedLastTermOffered));
            courseInfo.getAttributes().add(new AttributeInfo(CurriculumManagementConstants.COURSE_ATTRIBUTE_LAST_PUBLICATION_YEAR, proposedLastCourseCatalogYear));

            // lastTermOffered is a special case field, as it is required upon retire state
            // but not required for submit.  Therefore it is possible for a user to submit a retire proposal
            // without this field filled out, then when the course gets approved, and the state changes to RETIRED
            // validation would fail and the proposal will then go into exception routing.
            // We can't simply make lastTermOffered a required field as it is not a desired field
            // on the course proposal screen.
            //
            // So in the case of lastTermOffered being null when a course is retired,
            // Just copy the "proposalInfo.proposedEndTerm" value (required for saves, so it will be filled out)
            // into "courseInfo.lastTermOffered" to pass validation.
            if ((proposalInfo != null) && (courseInfo != null)
                    && (courseInfo.getAttributeValue(CurriculumManagementConstants.COURSE_ATTRIBUTE_LAST_TERM_OFFERED) == null)) {
                courseInfo.getAttributes().add(new AttributeInfo(CurriculumManagementConstants.COURSE_ATTRIBUTE_LAST_TERM_OFFERED, proposedEndTerm));
            }
        }
    }

    /**
     * Creates a List of curriculum oversight strings.
     */
    protected List<String> buildCurriculumOversightList(String subjectArea, List<String> unitContentOwner) {
        List<String> oversights = new ArrayList<String>();

        for (String existing : unitContentOwner) {
            CourseCreateUnitsContentOwner courseCreateUnitsContentOwner = new CourseCreateUnitsContentOwner();
            courseCreateUnitsContentOwner.setOrgId(existing);
            populateOrgName(subjectArea, courseCreateUnitsContentOwner);
            oversights.add(courseCreateUnitsContentOwner.getRenderHelper().getOrgLongName());
        }
        return oversights;
    }

    /**
     *  Populates the Org Name using the Subject Area and unitContentOwner
     * @param subjectArea
     * @param unitsContentOwner
     * @return
     */
    protected String populateOrgName(String subjectArea, CourseCreateUnitsContentOwner unitsContentOwner) {

        if (StringUtils.isBlank(unitsContentOwner.getOrgId())) {
            return StringUtils.EMPTY;
        }

        final SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("subjectCode.search.orgsForSubjectCode");

        searchRequest.addParam("subjectCode.queryParam.code", subjectArea);
        searchRequest.addParam("subjectCode.queryParam.optionalOrgId", unitsContentOwner.getOrgId());

        List<KeyValue> departments = new ArrayList<KeyValue>();

        try {

            SearchResultInfo result = getSubjectCodeService().search(searchRequest, ContextUtils.createDefaultContextInfo());

            if (result.getRows().isEmpty()) {
                throw new RuntimeException("Invalid Org Id");
            }

            SearchResultRowInfo row = null;
            if (subjectArea == null) {
                // This for loop is kind of get(0) this is to avid sonar violation.
                // without giving subjectArea Organization cannot be added. this is tricky scenario where subjectArea ia added and Orgs is chosen, but before "save" subjectArea is removed.
                // search result returns multiple values without subjectArea.
                // for all return result "subjectCode.resultColumn.orgLongName" will be the same.
                for (SearchResultRowInfo resultCell : result.getRows()) {
                    row = resultCell;
                    break;
                }
            } else {
                row = KSCollectionUtils.getOptionalZeroElement(result.getRows(), true);
            }

            for (final SearchResultCellInfo resultCell : row.getCells()) {
                if ("subjectCode.resultColumn.orgLongName".equals(resultCell.getKey())) {
                    unitsContentOwner.getRenderHelper().setOrgLongName(resultCell.getValue());
                    break;
                }
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Returning {}", departments);
            }

            return StringUtils.EMPTY;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  This method returns the Term description by passing the term id.
     * @param term
     * @return term description
     */
    protected String getTermDesc(String term) {

        String result = "";

        if (StringUtils.isNotEmpty(term)) {

            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.in("id", term));

            QueryByCriteria qbc = qbcBuilder.build();
            try {

                List<AtpInfo> searchResult = getAtpService().searchForAtps(qbc, ContextUtils.createDefaultContextInfo());

                AtpInfo atpInfo = KSCollectionUtils.getOptionalZeroElement(searchResult);

                if (atpInfo != null) {
                    result = atpInfo.getName();
                }

            } catch (Exception ex) {
                throw new RuntimeException("Could not retrieve description of Term \"" + term + "\" : " + ex);
            }
        }

        return result;
    }

    /**
     * Populates the wrapper objects used on the create course proposal and course view pages.
     *
     * @param proposalElementsWrapper The wrapper to populate.
     */
    @Override
    public void populateWrapperData(ProposalElementsWrapper proposalElementsWrapper) throws Exception {
        CommonCourseDataWrapper courseWrapper = (CommonCourseDataWrapper) proposalElementsWrapper;

        courseWrapper.setLastTerm(courseWrapper.getProposalInfo().getAttributeValue(CurriculumManagementConstants.PROPOSED_LAST_TERM_OFFERED));
        courseWrapper.setPublicationYear(courseWrapper.getProposalInfo().getAttributeValue(CurriculumManagementConstants.PROPOSED_LAST_COURSE_CATALOG_YEAR));
        RichTextInfo retirementComment = new RichTextInfo();
        retirementComment.setPlain(courseWrapper.getProposalInfo().getAttributeValue(CurriculumManagementConstants.PROPOSED_OTHER_COMMENTS));
        retirementComment.setFormatted(retirementComment.getPlain());
        courseWrapper.setRetirementComment(retirementComment);

        /*
         * Curriculum Oversight
         */
        courseWrapper.getUnitsContentOwner().clear();
        for (String orgId : courseWrapper.getCourseInfo().getUnitsContentOwner()) {
            CourseCreateUnitsContentOwner orgWrapper = new CourseCreateUnitsContentOwner();
            orgWrapper.setOrgId(orgId);
            populateOrgName(courseWrapper.getCourseInfo().getSubjectArea(), orgWrapper);
            courseWrapper.getUnitsContentOwner().add(orgWrapper);
        }

        if (courseWrapper.getCourseInfo().getUnitsContentOwner() == null) {
            courseWrapper.getCourseInfo().setUnitsContentOwner(new ArrayList<String>());
        }

        //  Only add an add-line if the collection is empty.
        if (courseWrapper.getUnitsContentOwner().isEmpty()) {
            CourseCreateUnitsContentOwner newCourseCreateUnitsContentOwner = new CourseCreateUnitsContentOwner();
            newCourseCreateUnitsContentOwner.getRenderHelper().setNewRow(true);
            courseWrapper.getUnitsContentOwner().add(newCourseCreateUnitsContentOwner);
        }

        //  Omit authors and collaborators for course view
        if (courseWrapper.isProposalDataRequired()) {
            super.populateWrapperData(courseWrapper);
        }

    }

    protected AtpService getAtpService() {
        if (atpService == null) {
            QName qname = new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART);
            atpService = (AtpService) GlobalResourceLoader.getService(qname);
        }
        return atpService;
    }

    protected SubjectCodeService getSubjectCodeService() {
        if (subjectCodeService == null) {
            subjectCodeService = GlobalResourceLoader.getService(new QName(CourseServiceConstants.NAMESPACE_SUBJECTCODE, SubjectCodeService.class.getSimpleName()));
        }
        return subjectCodeService;
    }

    protected CourseStateChangeServiceImpl getCourseStateChangeService() {
        if (this.courseStateChangeService == null) {
            this.courseStateChangeService = new CourseStateChangeServiceImpl();
            this.courseStateChangeService.setCourseService(getCourseService());
        }
        return this.courseStateChangeService;
    }

    protected CourseService getCourseService() {
        if (courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, CourseServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseService;
    }

}
