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
 * Created by venkat on 2/24/14
 */
package org.kuali.student.cm.course.rule;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.wrapper.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.LoDisplayInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.OrganizationInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.ResultValuesGroupInfoWrapper;
import org.kuali.student.cm.course.service.util.OrganizationSearchUtil;
import org.kuali.student.cm.proposal.form.wrapper.SupportingDocumentInfoWrapper;
import org.kuali.student.cm.proposal.service.ProposalMaintainable;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.rule.KsMaintenanceDocumentRuleBase;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.course.dto.CourseVariationInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains all the course creation business rules
 *
 * @author Kuali Student Team
 */
public class CourseRule extends KsMaintenanceDocumentRuleBase {

    /**
     *  This method is overridden to provide custom rules for processing document 'Approve And Activate'
     *
     */
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {

        MaintenanceDocument maintenanceDocument = (MaintenanceDocument) document;
        if (maintenanceDocument.getNewMaintainableObject().getDataObject() instanceof CourseInfoWrapper) {

            CourseInfoWrapper dataObject = (CourseInfoWrapper) maintenanceDocument.getNewMaintainableObject().getDataObject();
            boolean returnVal = true;
            Integer item = 0;
            for (ResultValuesGroupInfoWrapper rvg : dataObject.getCreditOptionWrappers()) {
                if (((rvg.getUiHelper() == null))|| (rvg.getUiHelper().getResultValue() == null) || (rvg.getUiHelper().getResultValue().isEmpty()) ) {
                        String propertyKey = CurriculumManagementConstants.DATA_OBJECT_PATH + ".creditOptionWrappers[" + item.intValue() + "]" + ".uiHelper.resultValue";
                        GlobalVariables.getMessageMap().putError(propertyKey,
                                CurriculumManagementConstants.MessageKeys.ERROR_OUTCOME_CREDIT_VALUE_REQUIRED);
                        returnVal = false;
                }
                item++;
            }
            return returnVal;
        }
        return true;
    }


    /**
     * This method is overridden to provide custom rules for processing document routing
     *
     * @param document
     * @return boolean
     */
    @Override
    protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {

        boolean success = super.processCustomSaveDocumentBusinessRules(document);

        if (!success) {
            return success;
        }

        MaintenanceDocument maintenanceDocument = (MaintenanceDocument) document;

        if (maintenanceDocument.getNewMaintainableObject().getDataObject() instanceof CourseInfoWrapper) {

            CourseInfoWrapper dataObject = (CourseInfoWrapper) maintenanceDocument.getNewMaintainableObject().getDataObject();
            if (StringUtils.isBlank(dataObject.getProposalInfo().getName())) {
                GlobalVariables.getMessageMap().putError(CurriculumManagementConstants.DATA_OBJECT_PATH + ".proposalInfo.name",
                        CurriculumManagementConstants.MessageKeys.ERROR_PROPOSAL_TITLE_REQUIRED);
                success = false;
            }

            if (StringUtils.isBlank(dataObject.getCourseInfo().getCourseTitle())) {
                GlobalVariables.getMessageMap().putError(CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.courseTitle",
                        CurriculumManagementConstants.MessageKeys.ERROR_COURSE_TITLE_REQUIRED);
                success = false;
            }

            if (dataObject.getCourseInfo().getDuration() != null) {
                if (dataObject.getCourseInfo().getDuration().getTimeQuantity() == null
                        || StringUtils.isBlank(dataObject.getCourseInfo().getDuration().getTimeQuantity().toString())) {
                    if (StringUtils.isNotBlank(dataObject.getCourseInfo().getDuration().getAtpDurationTypeKey())) {
                        GlobalVariables.getMessageMap().putError(CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.duration.timeQuantity",
                                CurriculumManagementConstants.MessageKeys.ERROR_COURSE_DURATION_COUNT_REQUIRED);
                        success = false;
                    }
                }
            }

            if (dataObject.getCourseInfo().getVariations() != null) {
                for (CourseVariationInfo courseVariationInfo : dataObject.getCourseInfo().getVariations()) {
                    if (courseVariationInfo.getVariationCode() != null && courseVariationInfo.getVariationTitle() != null) {
                        if ((StringUtils.isBlank(courseVariationInfo.getVariationCode()) && StringUtils.isNotBlank(courseVariationInfo.getVariationTitle())) ||
                                StringUtils.isNotBlank(courseVariationInfo.getVariationCode()) && StringUtils.isBlank(courseVariationInfo.getVariationTitle())) {
                            GlobalVariables.getMessageMap().putError(CurriculumManagementConstants.DATA_OBJECT_PATH + ".courseInfo.CourseVariationInfo",
                                    CurriculumManagementConstants.MessageKeys.ERROR_COURSE_VERSION_CODE_AND_TITLE_REQUIRED);
                            success = false;
                        }
                    }
                }
            }

            success = success && validateOutcomes(dataObject);
            success = success && validateInstructor(dataObject);
            success = success && validateOrganization(dataObject);
            success = success && validateLearningObjectives(dataObject);
            success = success && validateAuthorsAndCollaborators(dataObject);
            success = success && validateSupportingDocuments(maintenanceDocument,dataObject);
        }
        return success;
    }

    protected  boolean validateOutcomes(CourseInfoWrapper dataObject) {
        boolean returnVal = true;
        Integer item = 0;
        for (ResultValuesGroupInfoWrapper rvg : dataObject.getCreditOptionWrappers()) {
            if (StringUtils.isNotBlank(rvg.getTypeKey()) && rvg.getTypeKey().length() > 1) {
                if(!rvg.isUserEntered()) {
                    String propertyKey = CurriculumManagementConstants.DATA_OBJECT_PATH + ".creditOptionWrappers[" + item.intValue() + "]" + ".uiHelper.resultValue";
                    GlobalVariables.getMessageMap().putError(propertyKey,
                            CurriculumManagementConstants.MessageKeys.ERROR_OUTCOME_CREDIT_VALUE_REQUIRED);
                    returnVal = false;
                }
            }
            item++;
        }

        return returnVal;
    }

    protected boolean validateInstructor(CourseInfoWrapper dataObject) {

        List<CluInstructorInfoWrapper> instructorToRemove = new ArrayList<CluInstructorInfoWrapper>();

        for (CluInstructorInfoWrapper instructorDisplay : dataObject.getInstructorWrappers()) {

            if (!instructorDisplay.isUserEntered()) {

                instructorToRemove.add(instructorDisplay);

            } else {

                String principalName = parseDisplayNameForPrincipalName(instructorDisplay.getDisplayName());
                Map<String, String> searchCriteria = new HashMap<String, String>();
                searchCriteria.put(KIMPropertyConstants.Person.PRINCIPAL_NAME, principalName);
                List<Person> persons = getPersonService().findPeople(searchCriteria);

                if (persons.isEmpty()) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, CurriculumManagementConstants.MessageKeys.ERROR_DATA_NOT_FOUND, "Instructor", principalName);
                    return false;
                } else if (persons.size() > 1) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, CurriculumManagementConstants.MessageKeys.ERROR_DATA_MULTIPLE_MATCH_FOUND, "Instructor", principalName);
                    return false;
                } else {
                    try {
                        instructorDisplay.setPersonId(KSCollectionUtils.getOptionalZeroElement(persons).getPrincipalId());
                    } catch (OperationFailedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        dataObject.getInstructorWrappers().removeAll(instructorToRemove);

        return true;
    }

    protected boolean validateAuthorsAndCollaborators(CourseInfoWrapper dataObject) {

        List<CollaboratorWrapper> collaboratorsToRemove = new ArrayList<CollaboratorWrapper>();

        for (CollaboratorWrapper collaboratorWrapper : dataObject.getCollaboratorWrappers()) {

            if (StringUtils.isBlank(collaboratorWrapper.getDisplayName())) {

                collaboratorsToRemove.add(collaboratorWrapper);

            } else {

                // only need to validate new author collab records indicated by a blank actionRequestId value
                if (StringUtils.isNotBlank(collaboratorWrapper.getActionRequestId())) {
                    continue;
                }

                String principalName = parseDisplayNameForPrincipalName(collaboratorWrapper.getDisplayName());
                Map<String, String> searchCriteria = new HashMap<String, String>();
                searchCriteria.put(KIMPropertyConstants.Person.PRINCIPAL_NAME, principalName);
                List<Person> persons = getPersonService().findPeople(searchCriteria);

                if (persons.isEmpty()) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, CurriculumManagementConstants.MessageKeys.ERROR_DATA_NOT_FOUND, "Collaborator", principalName);
                    return false;
                } else if (persons.size() > 1) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, CurriculumManagementConstants.MessageKeys.ERROR_DATA_MULTIPLE_MATCH_FOUND, "Collaborator", principalName);
                    return false;
                } else {
                    try {
                        Person collabUser = KSCollectionUtils.getOptionalZeroElement(persons);
                        if (StringUtils.equals(GlobalVariables.getUserSession().getPrincipalId(), collabUser.getPrincipalId())) {
                            GlobalVariables.getMessageMap().putErrorForSectionId(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, CurriculumManagementConstants.MessageKeys.ERROR_PROPOSAL_COLLABORATORS_CANNOT_ADD_SELF, principalName);
                            return false;
                        }
                        collaboratorWrapper.setPrincipalId(collabUser.getPrincipalId());
                    } catch (OperationFailedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        dataObject.getCollaboratorWrappers().removeAll(collaboratorsToRemove);

        return true;
    }

    /**
     * Validate and populate org id.
     *
     * @param dataObject
     * @return
     */
    protected boolean validateOrganization(CourseInfoWrapper dataObject) {

        dataObject.getCourseInfo().getUnitsDeployment().clear();

        for (OrganizationInfoWrapper organizationInfoWrapper : dataObject.getAdministeringOrganizations()) {

            if (organizationInfoWrapper.isUserEntered()) {

                List<OrganizationInfoWrapper> orgs = OrganizationSearchUtil.searchForOrganizations(organizationInfoWrapper.getOrganizationName(), getOrganizationService());

                if (orgs.isEmpty()) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, CurriculumManagementConstants.MessageKeys.ERROR_DATA_NOT_FOUND, "Org", organizationInfoWrapper.getOrganizationName());
                    return false;
                } else if (isMultipleOrganizationInfoFound(orgs)) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(CurriculumManagementConstants.CoursePageIds.CREATE_COURSE_PAGE, CurriculumManagementConstants.MessageKeys.ERROR_DATA_MULTIPLE_MATCH_FOUND, "Org", organizationInfoWrapper.getOrganizationName());
                    return false;
                } else {
                    dataObject.getCourseInfo().getUnitsDeployment().add(getOrganizationInfoWrapper(orgs, organizationInfoWrapper.getOrganizationName()).getId());
                }
            }
        }
        return true;
    }

    protected boolean validateLearningObjectives(CourseInfoWrapper dataObject){

        boolean result = true;
        int index = 0;
        for (LoDisplayInfoWrapper loDisplayInfoWrapper : dataObject.getLoDisplayWrapperModel().getLoWrappers()) {
            // description is always required for an LoDisplayInfo object
            if (!loDisplayInfoWrapper.isUserEntered()) {
                String propertyKey = CurriculumManagementConstants.DATA_OBJECT_PATH + ".loDisplayWrapperModel.loWrappers[" + index + "]" + ".loInfo.descr.plain";
                GlobalVariables.getMessageMap().putError(propertyKey,CurriculumManagementConstants.MessageKeys.ERROR_COURSE_LO_DESC_REQUIRED);
                result = false;
            }
            index++;
        }

        return result;
    }

    protected boolean validateSupportingDocuments(MaintenanceDocument maintenanceDocument,CourseInfoWrapper courseInfoWrapper) {

        int index = 0;
        List<SupportingDocumentInfoWrapper> emptyDocsToDelete = new ArrayList<>();
        boolean result = true;

        ProposalMaintainable maintainable = (ProposalMaintainable)maintenanceDocument.getNewMaintainableObject();

        for (SupportingDocumentInfoWrapper supportingDoc : courseInfoWrapper.getSupportingDocs()){

            maintainable.populateSupportingDocBytes(supportingDoc);

            if (supportingDoc.isNewDto() && supportingDoc.getUploadedDoc() != null) {
                long size = Long.valueOf(CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString(
                        CurriculumManagementConstants.MessageKeys.SUPPORTING_DOC_MAX_SIZE_LIMIT));
                if (supportingDoc.getUploadedDoc().length > size) {
                    GlobalVariables.getMessageMap().putError(CurriculumManagementConstants.DATA_OBJECT_PATH + ".supportingDocs[" +
                            index + "].documentUpload",
                            CurriculumManagementConstants.MessageKeys.ERROR_SUPPORTING_DOCUMENTS_FILE_TOO_LARGE);
                    LOG.warn(CurriculumManagementConstants.MessageKeys.ERROR_SUPPORTING_DOCUMENTS_FILE_TOO_LARGE);
                    supportingDoc.setDocumentName(null);
                    supportingDoc.setUploadedDoc(null);
                    result = false;
                }
            } else if (supportingDoc.isNewDto()) {
                emptyDocsToDelete.add(supportingDoc);
            }
            index++;
        }

        courseInfoWrapper.getSupportingDocs().removeAll(emptyDocsToDelete);
        return result;
    }

    protected boolean isMultipleOrganizationInfoFound(List<OrganizationInfoWrapper> orgs) {
        boolean isEquals = true;
        try {
            isEquals = KSCollectionUtils.hasDuplicates(orgs, new Comparator<OrganizationInfoWrapper>() {
                @Override
                public int compare(OrganizationInfoWrapper organizationInfoWrapper1, OrganizationInfoWrapper organizationInfoWrapper2) {
                    if (organizationInfoWrapper1 == null && organizationInfoWrapper2 == null) {
                        return 0;
                    } else if (organizationInfoWrapper1 == null || organizationInfoWrapper2 == null) { // any one object is not null
                        return 1;
                    } else {
                        if (organizationInfoWrapper1.getOrganizationName().equals(organizationInfoWrapper2.getOrganizationName()) &&
                                (organizationInfoWrapper1.getId().equals(organizationInfoWrapper2.getId())))
                            return 0;
                        else
                            return 1;
                    }
                }
            });
        } catch (OperationFailedException e) {
            throw new RuntimeException(e);
        }
        return isEquals;
    }

    protected OrganizationInfoWrapper getOrganizationInfoWrapper(List<OrganizationInfoWrapper> orgs, String organizationName) {
        for (OrganizationInfoWrapper organizationInfoWrapper : orgs) {
            if (organizationInfoWrapper.getOrganizationName().equals(organizationName))
                return organizationInfoWrapper;
        }
        return new OrganizationInfoWrapper(); // always orgs will contain organizationName, therefore this part of the code will never be called.
    }

    protected OrganizationService getOrganizationService() {
        return (OrganizationService) GlobalResourceLoader.getService(new QName(OrganizationServiceConstants.NAMESPACE, OrganizationServiceConstants.SERVICE_NAME_LOCAL_PART));
    }

    /**
     * Converts the display name of the instructor into the plain user name (for use in a search query)
     *
     * @param displayName The display name of the instructor.
     * @return The user name of the instructor.
     */
    protected String parseDisplayNameForPrincipalName(String displayName) {
        String searchString = "";
        if (displayName.contains("(") && displayName.contains(")")) {
            searchString = displayName.substring(displayName.lastIndexOf('(') + 1, displayName.lastIndexOf(')'));
        }
        return searchString;
    }

    @Override
    public boolean processRouteDocument(Document document) {
        boolean success = super.processRouteDocument(document);
        return success;
    }


}
