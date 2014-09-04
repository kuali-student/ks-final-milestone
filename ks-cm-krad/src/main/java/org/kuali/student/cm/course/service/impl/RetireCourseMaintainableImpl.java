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
import org.kuali.student.r1.core.proposal.ProposalConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
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

    public void populateCourseData(String courseId, RetireCourseWrapper courseWrapper) throws Exception {

        CourseInfo course = getCourseService().getCourse(courseId, createContextInfo());
        courseWrapper.setCourseInfo(course);
    }

    /**
     * Override the save proposal to add the attributes to proposal
     * @throws Exception
     */
    @Override
    protected void saveProposal() throws Exception {

        RetireCourseWrapper retireCourseWrapper = (RetireCourseWrapper) getDataObject();
        ProposalInfo proposal = retireCourseWrapper.getProposalInfo();
        proposal.setWorkflowId(getDocumentNumber());

        addOrUpdateAttributes(proposal.getAttributes(), CurriculumManagementConstants.PROPOSED_END_TERM, retireCourseWrapper.getRetireEndTerm());
        addOrUpdateAttributes(proposal.getAttributes(), CurriculumManagementConstants.PROPOSED_LAST_TERM_OFFERED, retireCourseWrapper.getLastTerm());
        addOrUpdateAttributes(proposal.getAttributes(), CurriculumManagementConstants.PROPOSED_LAST_COURSE_CATALOG_YEAR, retireCourseWrapper.getPublicationYear());

        // check if the proposal has been previously saved
        if (StringUtils.isBlank(proposal.getId())) {
            // no previous save, so set up proposal as a new proposal
            proposal.setStateKey(ProposalConstants.PROPOSAL_STATE_SAVED);     // remove proposal constant, try to use KualiStudentPostProcessorBase
            String proposalTypeKey = getProposalTypeKey();
            proposal.setTypeKey(proposalTypeKey);
            proposal.setProposalReferenceType(getProposalReferenceType());
            proposal.getProposalReference().add(getProposalReference());
            proposal.getProposerOrg().clear();
            proposal.getProposerPerson().clear();
            proposal = getProposalService().createProposal(proposalTypeKey, proposal, ContextUtils.createDefaultContextInfo());
        } else {
            proposal = getProposalService().updateProposal(proposal.getId(), proposal, ContextUtils.createDefaultContextInfo());
        }

        ((RetireCourseWrapper) getDataObject()).setProposalInfo(proposal);
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

    protected String getProposalReferenceType() {
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
