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
 * Created by chongzhu on 2/6/14
 */
package org.kuali.student.cm.course.form;

import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CreateCourseForm extends MaintenanceDocumentForm {
    private boolean renderNavigationPanel = false;
    private String createCourseInitialAction;
    private boolean useCMreviewProcess = false;

    private int selectedTabIndex = 0;

    public boolean isRenderNavigationPanel() {
        return renderNavigationPanel;
    }

    public void setRenderNavigationPanel(boolean renderNavigationPanel) {
        this.renderNavigationPanel = renderNavigationPanel;
    }

    public String getCreateCourseInitialAction() {
        return createCourseInitialAction;
    }

    public void setCreateCourseInitialAction(String createCourseInitialAction) {
        this.createCourseInitialAction = createCourseInitialAction;
    }

    public boolean isUseCMreviewProcess() {
        return useCMreviewProcess;
    }

    public void setUseCMreviewProcess(boolean useCMreviewProcess) {
        this.useCMreviewProcess = useCMreviewProcess;
    }

    public int getSelectedTabIndex() {
        return selectedTabIndex;
    }

    public void setSelectedTabIndex(int selectedTabIndex) {
        this.selectedTabIndex = selectedTabIndex;
    }
}
