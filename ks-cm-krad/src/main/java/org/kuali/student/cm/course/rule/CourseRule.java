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
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.cm.course.form.CourseInfoWrapper;
import org.kuali.student.common.uif.rule.KsMaintenanceDocumentRuleBase;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseRule extends KsMaintenanceDocumentRuleBase {

    @Override
    public boolean processSaveDocument(Document document) {

        boolean success = super.processSaveDocument(document);

        if (!success){
            return success;
        }

        MaintenanceDocument maintenanceDocument = (MaintenanceDocument) document;
        CourseInfoWrapper dataObject = (CourseInfoWrapper)maintenanceDocument.getNewMaintainableObject().getDataObject();

        if (StringUtils.isBlank(dataObject.getProposalInfo().getName())){
            GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.proposalInfo.name", RiceKeyConstants.ERROR_CUSTOM, "Proposal title required");
        }

        if (StringUtils.isBlank(dataObject.getCourseInfo().getCourseTitle())){
            GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.courseInfo.courseTitle", RiceKeyConstants.ERROR_CUSTOM, "Course title required");
        }

        return success;
    }


    @Override
    public boolean processRouteDocument(Document document) {

        boolean success = super.processRouteDocument(document);


        return success;

    }

}
