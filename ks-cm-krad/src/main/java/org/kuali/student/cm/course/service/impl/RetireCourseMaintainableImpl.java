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
 * Created by prasannag on 1/9/14
 */
package org.kuali.student.cm.course.service.impl;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.wrapper.RetireCourseWrapper;
import org.kuali.student.cm.course.service.RetireCourseMaintainable;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.cm.proposal.service.impl.ProposalMaintainableImpl;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.constants.ProposalServiceConstants;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import javax.xml.namespace.QName;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class handles the operation related to course retire
 *
 * @author Kuali Student Team
 */
public class RetireCourseMaintainableImpl extends ProposalMaintainableImpl implements RetireCourseMaintainable {

    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {

        RetireCourseWrapper retireCourseWrapper = (RetireCourseWrapper) getDataObject();

        // We can actually get this from the workflow document initiator id. It doesn't need to be stored in the form.
        retireCourseWrapper.setUserId(ContextUtils.createDefaultContextInfo().getPrincipalId());

        // Initialize Course Requisites
        retireCourseWrapper.setNamespace(KSKRMSServiceConstants.NAMESPACE_CODE);
        retireCourseWrapper.setRefDiscriminatorType(CourseServiceConstants.REF_OBJECT_URI_COURSE);
        retireCourseWrapper.setRefObjectId(retireCourseWrapper.getCourseInfo().getId());

        retireCourseWrapper.setLastUpdated(CurriculumManagementConstants.CM_DATE_FORMATTER.format(new DateTime()));

    }

    public void retrieveDataObject() {
        super.retrieveDataObject();
        try {
            RetireCourseWrapper dataObject = (RetireCourseWrapper) getDataObject();
            populateCourseAndReviewData(dataObject);
        } catch (Exception e) {
            throw new RuntimeException("Caught Exception while populating Course data", e);
        }
    }

    /**
     * This method loads course information and populate to <class>CourseInfoWrapper</class> and also to
     * <class>ReviewProposalDisplay</class> for display purpose at 'review proposal' and 'view course'.
     *
     * @throws Exception
     */
    public void populateCourseAndReviewData(RetireCourseWrapper courseWrapper) throws Exception {
        populateWrapperData(courseWrapper);
        updateReview(courseWrapper, false);
    }

    /**
     * Populates the wrapper objects used on documents that utilize proposals.
     *
     * @param courseWrapper The wrapper to populate.
     */
    public void populateWrapperData(RetireCourseWrapper courseWrapper) throws Exception {
        super.populateWrapperData(courseWrapper);
        CourseInfo course = getCourseService().getCourse(getProposalInfo().getProposalReference().get(0), createContextInfo());
        courseWrapper.setCourseInfo(course);

        // copy data from proposal to wrapper object
        courseWrapper.setRetireEndTerm(courseWrapper.getProposalInfo().getAttributeValue(CurriculumManagementConstants.PROPOSED_END_TERM));
        courseWrapper.setLastTerm(courseWrapper.getProposalInfo().getAttributeValue(CurriculumManagementConstants.PROPOSED_LAST_TERM_OFFERED));
        courseWrapper.setPublicationYear(courseWrapper.getProposalInfo().getAttributeValue(CurriculumManagementConstants.PROPOSED_LAST_COURSE_CATALOG_YEAR));
        RichTextInfo otherComment = new RichTextInfo();
        otherComment.setPlain(courseWrapper.getProposalInfo().getAttributeValue(CurriculumManagementConstants.PROPOSED_OTHER_COMMENTS));
        otherComment.setFormatted(otherComment.getPlain());
        courseWrapper.setOtherComment(otherComment);
    }

    protected void updateReview(RetireCourseWrapper retireCourseWrapper, boolean shouldRepopulateRemoteData) {
        super.updateReview(retireCourseWrapper, shouldRepopulateRemoteData);
    }

    /**
     * Override the save proposal to add the attributes to proposal
     * @throws Exception
     */
    @Override
    protected void saveProposal() throws Exception {
        RetireCourseWrapper retireCourseWrapper = (RetireCourseWrapper) getDataObject();
        ProposalInfo proposal = retireCourseWrapper.getProposalInfo();

        addOrUpdateAttributes(proposal.getAttributes(), CurriculumManagementConstants.PROPOSED_END_TERM, retireCourseWrapper.getRetireEndTerm());
        addOrUpdateAttributes(proposal.getAttributes(), CurriculumManagementConstants.PROPOSED_LAST_TERM_OFFERED, retireCourseWrapper.getLastTerm());
        addOrUpdateAttributes(proposal.getAttributes(), CurriculumManagementConstants.PROPOSED_LAST_COURSE_CATALOG_YEAR, retireCourseWrapper.getPublicationYear());
        if (retireCourseWrapper.getOtherComment() != null) {
            addOrUpdateAttributes(proposal.getAttributes(), CurriculumManagementConstants.PROPOSED_OTHER_COMMENTS, retireCourseWrapper.getOtherComment().getPlain());
        }

        super.saveProposal();
    }

    /**
     *  Add or update the attribute list.
     * @param attributeInfoList
     * @param key
     * @param value
     */
    protected void addOrUpdateAttributes(List<AttributeInfo> attributeInfoList, String key, String value) {
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

    @Override
    public String getViewTypeNameForProposal() {
        throw new RuntimeException("View type name is not allowed for retire course");
    }

    @Override
    public ProposalElementsWrapper copyWrapperObjectsToProposal(ProposalInfo sourceProposal) throws Exception {
        throw new RuntimeException("Copying wrapper object to proposal is not allowed for retire course");
    }

    /**
     * Return the clu id from the canonical course that is linked to the given course offering id.
     *
     * @param refObjectId - the course offering id.
     * @return
     * @throws Exception
     */
    @Override
    public List<ReferenceObjectBinding> getParentRefObjectsForProposal(String refObjectId) {
        if (StringUtils.isBlank(refObjectId)) {
            return Collections.EMPTY_LIST;
        }
        return this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(CourseServiceConstants.REF_OBJECT_URI_COURSE, refObjectId);
    }

    protected String getProposalTypeKey() {
        return ProposalServiceConstants.PROPOSAL_TYPE_COURSE_RETIRE_KEY;
    }

    public String getProposalReferenceType() {
        return ProposalServiceConstants.PROPOSAL_DOC_RELATION_TYPE_CLU_KEY;
    }

    protected String getProposalReference() {
        RetireCourseWrapper retireCourseWrapper = (RetireCourseWrapper)getDataObject();
        return retireCourseWrapper.getCourseInfo().getId();
    }

    private transient CourseService courseService;

    protected CourseService getCourseService() {
        if (courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, CourseServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseService;
    }
}
