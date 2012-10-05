package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.server.gwt.DataService;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.r1.lum.statement.typekey.ReqComponentFieldTypes;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r1.core.proposal.dto.ProposalInfo;
import org.kuali.student.r1.core.proposal.service.ProposalService;
import org.kuali.student.r2.lum.clu.CLUConstants;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseFeeInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
import org.kuali.student.r2.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.r2.lum.course.dto.CourseVariationInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.transaction.annotation.Transactional;

@Transactional(noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class CopyCourseServiceImpl {
    final static Logger LOG = Logger.getLogger(CopyCourseServiceImpl.class);

    private DataService courseDataService;
    private DataService courseProposalDataService;
    private CourseService courseService;
    private CluService cluService;
    private StatementService statementService;
    private ProposalService proposalService;

    private String defaultState = DtoConstants.STATE_DRAFT;

    private List<String> ignoreProperties;

    public DataSaveResult createCopyCourse(String originalCluId, ContextInfo contextInfo) throws Exception {
        //Copy the course and use the data service to return
        CourseInfo copiedCourse = copyCourse(originalCluId, contextInfo);

        DataSaveResult result = new DataSaveResult();
        result.setValue(courseDataService.getData(copiedCourse.getId(), contextInfo));
        return result;
    }

    public DataSaveResult createCopyCourseProposal(String originalProposalId, String documentType, ContextInfo contextInfo) throws Exception {
        //Copy the proposal and use the data service to return
        ProposalInfo copiedProposal = copyProposal(originalProposalId, documentType, contextInfo);

        //Grab the data object so it is transformed
        Data data = courseProposalDataService.getData(copiedProposal.getId(), contextInfo);

        //Save it so that it goes through the filters and creates workflow
        return courseProposalDataService.saveData(data, contextInfo);
    }

    private CourseInfo copyCourse(String originalCluId, ContextInfo contextInfo) throws Exception {
        //Copy the course
        return copyCourse(originalCluId, null, defaultState, ignoreProperties, statementService, cluService,
                courseService, contextInfo);
    }

    private ProposalInfo copyProposal(String originalProposalId, String documentType, ContextInfo contextInfo) throws Exception {
        try {
            //Get the original Proposal
            ProposalInfo originalProposal = proposalService.getProposal(originalProposalId);

            //Copy the course from the original Proposal
            String originalCluId = originalProposal.getProposalReference().get(0);
            CourseInfo copiedCourse = copyCourse(originalCluId, contextInfo);

            //Clear ids and set the reference to the copied course
            originalProposal.setId(null);
            originalProposal.setWorkflowId(null);
            originalProposal.setState(defaultState);
            originalProposal.setType(documentType);
            originalProposal.getProposalReference().set(0, copiedCourse.getId());
            originalProposal.getProposerOrg().clear();
            originalProposal.getProposerPerson().clear();
            originalProposal.setName(null);

            //Create the proposal
            ProposalInfo copiedProposal = proposalService.createProposal(documentType, originalProposal);

            return copiedProposal;

        } catch (Exception e) {
            LOG.error("Error copying proposal id:" + originalProposalId, e);
            throw e;
        }
    }

    private void resetIds(CourseInfo course) {
        //Clear/Reset Joint info ids
        for (CourseJointInfo joint : course.getJoints()) {
            joint.setRelationId(null);
        }
        //Clear Los
        for (LoDisplayInfo lo : course.getCourseSpecificLOs()) {
            resetLoRecursively(lo);
        }
        //Clear format/activity ids
        for (FormatInfo format : course.getFormats()) {
            format.setId(null);
            for (ActivityInfo activity : format.getActivities()) {
                activity.setId(null);
            }
        }
        //Clear credit options
        course.getCreditOptions().clear();
        //Clear cross listing ids
        for (CourseCrossListingInfo crossListing : course.getCrossListings()) {
            crossListing.setId(null);
        }
        //Clear Expenditures
        for (AffiliatedOrgInfo orgInfo : course.getExpenditure().getAffiliatedOrgs()) {
            orgInfo.setId(null);
        }
        //Clear Fees
        for (CourseFeeInfo fee : course.getFees()) {
            fee.setId(null);
            for (CurrencyAmountInfo feeAmount : fee.getFeeAmounts()) {
                feeAmount.setId(null);
            }
        }
        //Clear revenue
        for (CourseRevenueInfo revenue : course.getRevenues()) {
            revenue.setId(null);
            for (AffiliatedOrgInfo orgInfo : revenue.getAffiliatedOrgs()) {
                orgInfo.setId(null);
            }
        }
        //Clear variation ids
        for (CourseVariationInfo variation : course.getVariations()) {
            variation.setId(null);
        }
    }

    private void resetLoRecursively(LoDisplayInfo lo) {
        //Clear out all the Lo ids recursively
        lo.getLoInfo().setId(null);
        for (LoDisplayInfo nestedLo : lo.getLoDisplayInfoList()) {
            resetLoRecursively(nestedLo);
        }
    }

    private void clearStatementTreeViewIds(
            List<StatementTreeViewInfo> statementTreeViews, String newState, CluService cluService,
            ContextInfo contextInfo) throws OperationFailedException {
        //Clear out all statement ids recursively
        for (StatementTreeViewInfo statementTreeView : statementTreeViews) {
            clearStatementTreeViewIdsRecursively(statementTreeView, newState, cluService, contextInfo);
        }
    }

    /**
     * Clears out ids recursively and also copies adhock clusets
     * @param statementTreeView
     * @param cluService
     * @throws OperationFailedException
     */
    private void clearStatementTreeViewIdsRecursively(StatementTreeViewInfo statementTreeView, String newState,
            CluService cluService, ContextInfo contextInfo) throws OperationFailedException {
        statementTreeView.setId(null);
        statementTreeView.setState(newState);

        //clear out all the nested requirement components
        for (ReqComponentInfo reqComp : statementTreeView.getReqComponents()) {
            reqComp.setId(null);
            reqComp.setState(newState);
            for (ReqCompFieldInfo field : reqComp.getReqCompFields()) {
                field.setId(null);
                //copy any clusets that are adhoc'd and set the field value to the new cluset
                if (ReqComponentFieldTypes.COURSE_CLUSET_KEY.getId().equals(field.getType()) ||
                        ReqComponentFieldTypes.PROGRAM_CLUSET_KEY.getId().equals(field.getType()) ||
                        ReqComponentFieldTypes.CLUSET_KEY.getId().equals(field.getType())) {
                    try {
                        CluSetInfo cluSet = cluService.getCluSet(field.getValue(), contextInfo);
                        cluSet.setId(null);
                        cluSet.setStateKey(newState);
                        //Clear clu ids if membership info exists, they will be re-added based on membership info 
                        if (cluSet.getMembershipQuery() != null) {
                            cluSet.getCluIds().clear();
                            cluSet.getCluSetIds().clear();
                        }
                        cluSet = cluService.createCluSet(cluSet.getTypeKey(), cluSet, contextInfo);
                        field.setValue(cluSet.getId());
                    } catch (Exception e) {
                        throw new OperationFailedException("Error copying clusets.", e);
                    }
                }

            }
        }
        //recurse through nested statements
        for (StatementTreeViewInfo child : statementTreeView.getStatements()) {
            clearStatementTreeViewIdsRecursively(child, newState, cluService, contextInfo);
        }
    }

    private void copyStatements(String originalCluId, String newCluId, String newState,
            StatementService statementService, CluService cluService, CourseService courseService,
            ContextInfo contextInfo) throws OperationFailedException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, PermissionDeniedException, DataValidationErrorException {
        //Get the course statements
        List<StatementTreeViewInfo> statementTreeViews = courseService.getCourseStatements(originalCluId, null, null,
                contextInfo);

        //Clear out the ids and create causing a copy to be made
        if (statementTreeViews != null) {
            clearStatementTreeViewIds(statementTreeViews, newState, cluService, contextInfo);

            for (StatementTreeViewInfo statementTreeView : statementTreeViews) {
                courseService.createCourseStatement(newCluId, statementTreeView, contextInfo);
            }
        }
    }

    private CourseInfo copyCourse(String originalCluId, String newCluId, String newState,
            List<String> ignoreProperties, StatementService statementService, CluService cluService,
            CourseService courseService, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            AlreadyExistsException, DataValidationErrorException, VersionMismatchException,
            CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException {
        CourseInfo originalCourse = courseService.getCourse(originalCluId, contextInfo);
        resetIds(originalCourse);
        originalCourse.setCourseTitle("Copy of " + originalCourse.getCourseTitle());
        //Default the newState to the existing course state if no state was set.
        //State should never be null
        if (newState == null) {
            newState = originalCourse.getStateKey();
        }

        originalCourse.setId(newCluId);
        originalCourse.setStateKey(newState);
        originalCourse.setPilotCourse(false);

        //Loop through the ignore properties and null out the values
        if (ignoreProperties != null) {
            for (String property : ignoreProperties) {
                try {
                    PropertyUtils.setProperty(originalCourse, property, null);
                } catch (Exception e) {
                    throw new InvalidParameterException("Ignore property is invalid and is causing an exception.");
                }
            }
        }

        CourseInfo newCourse = courseService.createCourse(originalCourse, contextInfo);
        copyStatements(originalCluId, newCourse.getId(), newState, statementService, cluService, courseService,
                contextInfo);
        return newCourse;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }

    public void setProposalService(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    public void setCourseDataService(DataService courseDataService) {
        this.courseDataService = courseDataService;
    }

    public void setCourseProposalDataService(DataService courseProposalDataService) {
        this.courseProposalDataService = courseProposalDataService;
    }

    public void setDefaultState(String defaultState) {
        this.defaultState = defaultState;
    }

    public void setIgnoreProperties(List<String> ignoreProperties) {
        this.ignoreProperties = ignoreProperties;
    }

}
