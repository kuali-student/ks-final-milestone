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
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.util.ContextUtils;

import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingEditRule extends MaintenanceDocumentRuleBase {

       private CourseOfferingService courseOfferingService;

    private final static String EXISTING_CO_CODE_FOUND_ERROR = "That Course Offering Code is already in use.  Please enter a different, unique Course Offering Code for ";

    @Override
    protected boolean processGlobalSaveDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = true;

        if (document.getNewMaintainableObject().getDataObject() instanceof CourseOfferingEditWrapper){
            CourseOfferingEditWrapper newCOWrapper = (CourseOfferingEditWrapper)document.getNewMaintainableObject().getDataObject();
            CourseOfferingEditWrapper oldCOWrapper = (CourseOfferingEditWrapper)document.getOldMaintainableObject().getDataObject();

            // Only perform validateDuplicateSuffix check when CO code suffix part is changed, the code itself is readOnly and can't be modified at all.
            // also notice a problem: the suffix of a CO from OldMaintainableObject (the DB reference dataset) could be null
            // while even we didn't modify suffix in edit CO page, the suffix value in NewMaintainableObject became an empty string
            String newSuffix = newCOWrapper.getCourseOfferingInfo().getCourseNumberSuffix();
            String oldSuffix = oldCOWrapper.getCourseOfferingInfo().getCourseNumberSuffix();
            if ((oldSuffix == null || oldSuffix.isEmpty()) && 
                (newSuffix == null || newSuffix.isEmpty())) {
                return valid;
            }else if ((newSuffix != null) && !newSuffix.equals(oldSuffix) ) {
                validateDuplicateSuffix(newCOWrapper);
            }
        }

        return valid;
    }


    protected boolean validateDuplicateSuffix(CourseOfferingEditWrapper coWrapper){
        // Catalog course code is case INSENSITIVE, but the suffix is case SENSITIVE
        String newCoCode = (coWrapper.getCourse().getCode().toUpperCase()) + coWrapper.getCourseOfferingInfo().getCourseNumberSuffix();

        try {
            List<CourseOfferingInfo> wrapperList = getCourseOfferingService().getCourseOfferingsByCourseAndTerm(coWrapper.getCourse().getId(), coWrapper.getCourseOfferingInfo().getTermId(), ContextUtils.createDefaultContextInfo());
            for (CourseOfferingInfo courseOfferingInfo : wrapperList) {

                if (StringUtils.equals(newCoCode, courseOfferingInfo.getCourseOfferingCode())) {
                    StringBuilder sb = new StringBuilder(EXISTING_CO_CODE_FOUND_ERROR);
                    sb.append(coWrapper.getCourse().getCode());
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, sb.toString());
                    return false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }
}
