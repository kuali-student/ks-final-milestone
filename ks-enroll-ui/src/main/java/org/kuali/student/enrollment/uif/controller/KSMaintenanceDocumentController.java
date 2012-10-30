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
 */
package org.kuali.student.enrollment.uif.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for <code>MaintenanceView</code> screens which operate on
 * <code>MaintenanceDocument</code> instances
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/maintenance")
public class KSMaintenanceDocumentController extends MaintenanceDocumentController {

    protected ViewHelperService getViewHelperService(UifFormBase form){
        if (form.getView() != null){
            return form.getView().getViewHelperService();
        }else{
            return form.getPostedView().getViewHelperService();
        }
    }

    protected int getSelectedCollectionLineIndex(UifFormBase form){

        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);

        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Unable to determine the selected collection path");
        }

        int selectedLineIndex = -1;
        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Unable to determine the selected line index");
        }

        return selectedLineIndex;
    }

}
