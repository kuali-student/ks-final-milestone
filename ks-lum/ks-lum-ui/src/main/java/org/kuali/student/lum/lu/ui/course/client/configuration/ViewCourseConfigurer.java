/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabMenuController;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.lum.common.client.lo.LUConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.AffiliatedOrgInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseCourseSpecificLOsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseExpenditureInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseFormatConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseJointsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseLearningResultsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseRevenueInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseVersionsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoFixedRateFeeConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.LearningObjectiveConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.SingleUseLoConstants;
import org.kuali.student.lum.lu.ui.course.client.controllers.VersionsController;
import org.kuali.student.lum.lu.ui.course.client.controllers.ViewCourseController;
import org.kuali.student.lum.lu.ui.course.client.views.ShowVersionView;

import com.google.gwt.user.client.ui.Widget;


/**
 * This is the configuration factory class for viewing a courss.
 *
 * @author Kuali Student Team
 *
 */
public class ViewCourseConfigurer
implements
CreditCourseConstants,
CreditCourseFormatConstants,
CreditCourseActivityConstants,
MetaInfoConstants,
CreditCourseDurationConstants,
FeeInfoConstants,
LearningObjectiveConstants,
CreditCourseCourseSpecificLOsConstants,
SingleUseLoConstants,
CreditCourseRevenueInfoConstants,
CreditCourseExpenditureInfoConstants,
AffiliatedOrgInfoConstants,
CreditCourseVersionsConstants,
CreditCourseJointsConstants,
RichTextInfoConstants,
FeeInfoFixedRateFeeConstants,
CreditCourseLearningResultsConstants
{

    public static final String COURSE_MODEL = "courseModel";

    //FIXME: [KSCOR-225] Temp paths waiting for DOL changes
    private static final String STATEMENTS_PATH = "statements";
    private static final String ID_TRANSLATION = "id-translation";
    
    public static final String CLU_PROPOSAL_MODEL = "cluProposalModel";

    //FIXME: [KSCOR-225] Initialize type and state from selected cluId
    private String type = "course";
    private String state = "draft";
    private String groupName = LUConstants.COURSE_GROUP_NAME;

    private DataModelDefinition modelDefinition;

    public static enum ViewCourseSections{BRIEF, DETAILED, CATALOG}

    public void setModelDefinition(DataModelDefinition modelDefinition){
        this.modelDefinition = modelDefinition;
    }

    public void generateLayout(ViewCourseController layoutController) {
        groupName = LUConstants.COURSE_GROUP_NAME;

        //Summary
        this.generateLayout(layoutController, CLU_PROPOSAL_MODEL);
        layoutController.addContentWidget(layoutController.getStatusLabel());
        Widget dropdown = layoutController.generateActionDropDown();
        dropdown.addStyleName("KS-Workflow-DropDown");
        layoutController.addContentWidget(dropdown);
        layoutController.addContentWidget(layoutController.getVersionHistoryWidget());

    }
    
    public void generateLayout(TabMenuController layoutController, String modelId) {
        groupName = LUConstants.COURSE_GROUP_NAME;

        //Summary
        CourseSummaryConfigurer summaryConfigurer = new CourseSummaryConfigurer(type, state, groupName, modelDefinition, 
        		(Controller)layoutController, modelId);
        layoutController.addTab(summaryConfigurer.generateCourseBriefSection(), "At a Glance");
        layoutController.addTab(summaryConfigurer.generateCourseSummarySection(), "Detailed View");
        layoutController.addTab(summaryConfigurer.generateCourseCatalogSection(), "Catalog View");
        layoutController.setDefaultView(ViewCourseSections.BRIEF);
        //layoutController.addContentWidget(layoutController.getStatusLabel());
        //Widget dropdown = layoutController.generateActionDropDown();
        //dropdown.addStyleName("KS-Workflow-DropDown");
        //layoutController.addContentWidget(dropdown);

    }
}