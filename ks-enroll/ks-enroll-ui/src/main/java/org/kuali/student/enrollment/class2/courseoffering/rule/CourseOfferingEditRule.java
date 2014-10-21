/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by vgadiyak on 5/29/12
 */
package org.kuali.student.enrollment.class2.courseoffering.rule;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.common.uif.rule.KsMaintenanceDocumentRuleBase;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OfferingInstructorWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides logic for saving a Course Offering maintenance document in the Edit ui
 *
 * @author Kuali Student Team
 */
public class CourseOfferingEditRule extends KsMaintenanceDocumentRuleBase {

    @Override
    protected boolean isDocumentValidForSave(MaintenanceDocument document) {
        boolean valid = super.isDocumentValidForSave(document);

        if (document.getNewMaintainableObject().getDataObject() instanceof CourseOfferingEditWrapper){
            CourseOfferingEditWrapper newCOWrapper = (CourseOfferingEditWrapper)document.getNewMaintainableObject().getDataObject();
            CourseOfferingEditWrapper oldCOWrapper = (CourseOfferingEditWrapper)document.getOldMaintainableObject().getDataObject();

            // Only perform validateDuplicateSuffix check when CO code suffix part is changed, the code itself is readOnly and can't be modified at all.
            // also notice a problem: the suffix of a CO from OldMaintainableObject (the DB reference dataset) could be null
            // while even we didn't modify suffix in edit CO page, the suffix value in NewMaintainableObject became an empty string
            if(newCOWrapper.getCreateCO()) {  // for Create CO page
                valid = validateRequiredFields(newCOWrapper);

                if (valid) {
                    valid = validateDuplicateSuffixCreate(newCOWrapper);
                }
            } else { // for Edit CO page
                String newSuffix = StringUtils.trimToEmpty(newCOWrapper.getCourseOfferingInfo().getCourseNumberSuffix());
                String oldSuffix = StringUtils.trimToEmpty(oldCOWrapper.getCourseOfferingInfo().getCourseNumberSuffix());

                if (StringUtils.isNotEmpty(oldSuffix) || StringUtils.isNotEmpty(newSuffix)) {
                    if (!StringUtils.equals(newSuffix, oldSuffix)) {
                        valid &= validateDuplicateSuffix(newCOWrapper);
                    }
                }

                // if no duplicate suffix then we validate the personnel ID
                if(valid) {
                    valid = validatePersonnel(newCOWrapper);
                }
            }

            // valid the final exam driver: if final exam type is STANDARD, a final exam driver should be selected
            if (valid) {
                valid = validFinalExamDriver(newCOWrapper);
            }

            if (valid){
                if (!StringUtils.isEmpty(newCOWrapper.getCourseOfferingInfo().getCourseNumberSuffix()) && !StringUtils.isAlphanumeric(newCOWrapper.getCourseOfferingInfo().getCourseNumberSuffix())){
                    valid = false;
                    GlobalVariables.getMessageMap().putError(
                                        "document.newMaintainableObject.dataObject.courseOfferingInfo.courseNumberSuffix",
                                        CourseOfferingConstants.ERROR_INVALID_COURSECODE_SUFFIX);
                }
            }
        }

        return valid;
    }

    protected boolean validFinalExamDriver (CourseOfferingEditWrapper coWrapper) {
        if (StringUtils.equals(coWrapper.getCourseOfferingInfo().getFinalExamType(), CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_STANDARD)
                && (!StringUtils.equals(coWrapper.getFinalExamDriver(), LuServiceConstants.LU_EXAM_DRIVER_AO_KEY) && !StringUtils.equals(coWrapper.getFinalExamDriver(), LuServiceConstants.LU_EXAM_DRIVER_CO_KEY))) {
            GlobalVariables.getMessageMap().putError(
                    "document.newMaintainableObject.dataObject.finalExamDriver",
                    CourseOfferingConstants.COURSEOFFERING_CREATE_ERROR_PARAMETER_IS_REQUIRED, "Final Exam Driver");
            return false;
        }
        return true;
    }
    protected boolean validateDuplicateSuffix(CourseOfferingEditWrapper coWrapper){
        // Catalog course code is case INSENSITIVE, but the suffix is case SENSITIVE
        String courseCode = coWrapper.getCourse().getCode().toUpperCase();
        String newCoCode = courseCode + StringUtils.trimToEmpty(coWrapper.getCourseOfferingInfo().getCourseNumberSuffix()).toUpperCase();

        try {
            List<CourseOfferingInfo> wrapperList = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOfferingsByCourseAndTerm(coWrapper.getCourse().getId(), coWrapper.getCourseOfferingInfo().getTermId(), ContextUtils.createDefaultContextInfo());
            for (CourseOfferingInfo courseOfferingInfo : wrapperList) {

                if (StringUtils.equals(newCoCode, courseOfferingInfo.getCourseOfferingCode())) {
                    GlobalVariables.getMessageMap().putError(
                            "document.newMaintainableObject.dataObject.courseOfferingInfo.courseNumberSuffix",
                            CourseOfferingConstants.COURSEOFFERING_ERROR_CREATE_DUPLICATECODE, newCoCode, courseCode);
                    return false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }


    protected boolean validatePersonnel(CourseOfferingEditWrapper coWrapper) {
        List<OfferingInstructorWrapper> instructors = coWrapper.getInstructors();
        boolean noError = true;
        if (instructors != null && !instructors.isEmpty()) {
            int index = 0;
            for (OfferingInstructorWrapper instructorWrapper : instructors)   {
                if (instructorWrapper != null) {
                    OfferingInstructorInfo info = instructorWrapper.getOfferingInstructorInfo();
                    if ((info != null) && (info.getPersonId() != null) && !info.getPersonId().isEmpty()) {
                        // verify this is a legal personId
                        List<Person> personList = CourseOfferingViewHelperUtil.getInstructorByPersonId(info.getPersonId());
                        if (personList.isEmpty()) {
                            GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.instructors[" + index + "].offeringInstructorInfo.personId",
                                    CourseOfferingConstants.COURSEOFFERING_ERROR_INVALID_PERSONNEL_ID, info.getPersonId());
                            noError &= false;
                        } else {
                            int firstPerson = 0;
                            String instructorName = personList.get(firstPerson).getName().trim();
                            if(instructorName != null && !instructorName.isEmpty()) {
                                if(!instructorName.equals(info.getPersonName())) {
                                    GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.instructors[" + index + "].offeringInstructorInfo.personName",
                                            CourseOfferingConstants.COURSEOFFERING_ERROR_UNMATCHING_PERSONNEL_NAME, info.getPersonName(), instructorName);
                                    noError &=  false;
                                }
                            }
                            if(info.getTypeKey() == null || info.getTypeKey().isEmpty()) {
                                GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.instructors[" + index + "].offeringInstructorInfo.typeKey",
                                        CourseOfferingConstants.COURSEOFFERING_ERROR_PERSONNEL_AFFILIATION);
                                noError &= false;
                            }
                        }
                    }
                }
                index++;
            }
        }
        return noError;
    }

    protected boolean validateRequiredFields(CourseOfferingEditWrapper coWrapper){
        boolean valid = false;
        if (!coWrapper.getFormatOfferingList().isEmpty()) {
            for (FormatOfferingWrapper offeringWrapper : coWrapper.getFormatOfferingList()) {
                if (offeringWrapper.getFormatId() != null) {
                    // At least 1 format offering must have an ID (can select an 'empty' option that still comes through in the list).
                    valid = true;
                    break;
                }
            }
        }

        if (!valid) {
            GlobalVariables.getMessageMap().putErrorForSectionId("KS-CourseOfferingEdit-DeliveryFormats", CourseOfferingConstants.DELIVERY_FORMAT_REQUIRED_ERROR);
            return false;
        }

        return true;
    }

    protected boolean validateDuplicateSuffixCreate(CourseOfferingEditWrapper coWrapper){
        String courseCode = coWrapper.getCourse().getCode().toUpperCase();
        String newCoCode = courseCode + StringUtils.trimToEmpty(coWrapper.getCourseOfferingInfo().getCourseNumberSuffix()).toUpperCase();
        try {
            List<CourseOfferingInfo> wrapperList =
                    _findCourseOfferingsByTermAndCourseCode(coWrapper.getTerm().getId(), newCoCode);
            for (CourseOfferingInfo courseOfferingInfo : wrapperList) {
                if (StringUtils.equals(newCoCode, courseOfferingInfo.getCourseOfferingCode())) {
                    GlobalVariables.getMessageMap().putError(
                            "document.newMaintainableObject.dataObject.courseOfferingInfo.courseNumberSuffix",
                            CourseOfferingConstants.COURSEOFFERING_ERROR_CREATE_DUPLICATECODE, newCoCode, courseCode);
                    return false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private List<CourseOfferingInfo> _findCourseOfferingsByTermAndCourseCode(String termId, String courseCode)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        List<CourseOfferingInfo> courseOfferings = new ArrayList<CourseOfferingInfo>();
        if (StringUtils.isNotBlank(courseCode) && StringUtils.isNotBlank(termId)) {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.equal("courseOfferingCode", courseCode),
                    PredicateFactory.equalIgnoreCase("atpId", termId)));
            QueryByCriteria criteria = qbcBuilder.build();

            courseOfferings = CourseOfferingManagementUtil.getCourseOfferingService().searchForCourseOfferings(criteria, ContextUtils.createDefaultContextInfo());
        }
        return courseOfferings;
    }
}
