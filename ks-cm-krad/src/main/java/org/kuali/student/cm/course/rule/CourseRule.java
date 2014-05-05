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
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADPropertyConstants;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.CourseInfoWrapper;
import org.kuali.student.cm.course.form.OrganizationInfoWrapper;
import org.kuali.student.cm.course.service.util.OrganizationSearchUtil;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.rule.KsMaintenanceDocumentRuleBase;
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

    public static final String DATA_OBJECT_PATH = KRADPropertyConstants.DOCUMENT + "."
            + KRADPropertyConstants.NEW_MAINTAINABLE_OBJECT + ".dataObject";

    @Override
    public boolean processSaveDocument(Document document) {

        boolean success = super.processSaveDocument(document);

        if (!success) {
            return success;
        }

        MaintenanceDocument maintenanceDocument = (MaintenanceDocument) document;
        CourseInfoWrapper dataObject = (CourseInfoWrapper) maintenanceDocument.getNewMaintainableObject().getDataObject();

        if (StringUtils.isBlank(dataObject.getProposalInfo().getName())) {
            GlobalVariables.getMessageMap().putError(DATA_OBJECT_PATH + ".proposalInfo.name",
                    CurriculumManagementConstants.MessageKeys.ERROR_PROPOSAL_TITLE_REQUIRED);
            success = false;
        }

        if (StringUtils.isBlank(dataObject.getCourseInfo().getCourseTitle())) {
            GlobalVariables.getMessageMap().putError(DATA_OBJECT_PATH + ".courseInfo.courseTitle",
                    CurriculumManagementConstants.MessageKeys.ERROR_COURSE_TITLE_REQUIRED);
            success = false;
        }

        if (dataObject.getCourseInfo().getDuration() != null) {
            if (dataObject.getCourseInfo().getDuration().getTimeQuantity() == null
                    || StringUtils.isBlank(dataObject.getCourseInfo().getDuration().getTimeQuantity().toString())) {
                if (StringUtils.isNotBlank(dataObject.getCourseInfo().getDuration().getAtpDurationTypeKey())) {
                    GlobalVariables.getMessageMap().putError(DATA_OBJECT_PATH + ".courseInfo.duration.timeQuantity",
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
                        GlobalVariables.getMessageMap().putError(DATA_OBJECT_PATH + ".courseInfo.CourseVariationInfo",
                                CurriculumManagementConstants.MessageKeys.ERROR_COURSE_VERSION_CODE_AND_TITLE_REQUIRED);
                        success = false;
                    }
                }
            }
        }

        success = success && validateInstructor(dataObject);
        success = success && validateOrganization(dataObject);

        return success;
    }

    protected boolean validateInstructor(CourseInfoWrapper dataObject) {

        List<CluInstructorInfoWrapper> instructorToRemove = new ArrayList<CluInstructorInfoWrapper>();

        for (CluInstructorInfoWrapper instructorDisplay : dataObject.getInstructorWrappers()) {

            if (StringUtils.isBlank(instructorDisplay.getDisplayName())) {

                instructorToRemove.add(instructorDisplay);

            } else {

                String principalName = getInstructorSearchString(instructorDisplay.getDisplayName());
                Map<String, String> searchCriteria = new HashMap<String, String>();
                searchCriteria.put(KIMPropertyConstants.Person.PRINCIPAL_NAME, principalName);
                List<Person> persons = getPersonService().findPeople(searchCriteria);

                if (persons.isEmpty()) {
                    GlobalVariables.getMessageMap().putErrorForSectionId("instructor-section", CurriculumManagementConstants.MessageKeys.ERROR_DATA_NOT_FOUND, "Instructor", principalName);
                    return false;
                } else if (persons.size() > 1) {
                    GlobalVariables.getMessageMap().putErrorForSectionId("instructor-section", CurriculumManagementConstants.MessageKeys.ERROR_DATA_MULTIPLE_MATCH_FOUND, "Instructor", principalName);
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

    /**
     * Validate and populate org id.
     *
     * @param dataObject
     * @return
     */
    protected boolean validateOrganization(CourseInfoWrapper dataObject) {

        dataObject.getCourseInfo().getUnitsDeployment().clear();

        for (OrganizationInfoWrapper organizationInfoWrapper : dataObject.getAdministeringOrganizations()) {

            if (StringUtils.isNotBlank(organizationInfoWrapper.getOrganizationName())) {

                List<OrganizationInfoWrapper> orgs = OrganizationSearchUtil.searchForOrganizations(organizationInfoWrapper.getOrganizationName(), getOrganizationService());

                if (orgs.isEmpty()) {
                    GlobalVariables.getMessageMap().putErrorForSectionId("administering-organization", CurriculumManagementConstants.MessageKeys.ERROR_DATA_NOT_FOUND, "Org", organizationInfoWrapper.getOrganizationName());
                    return false;
                } else if (isMultipleOrganizationInfoFound(orgs)) {
                    GlobalVariables.getMessageMap().putErrorForSectionId("administering-organization", CurriculumManagementConstants.MessageKeys.ERROR_DATA_MULTIPLE_MATCH_FOUND, "Org", organizationInfoWrapper.getOrganizationName());
                    return false;
                } else {
                    dataObject.getCourseInfo().getUnitsDeployment().add(getOrganizationInfoWrapper(orgs, organizationInfoWrapper.getOrganizationName()).getId());
                }
            }
        }
        return true;
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
    protected String getInstructorSearchString(String displayName) {
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
