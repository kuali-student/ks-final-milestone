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

import java.util.List;

import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r1.core.statement.dto.StatementTypeInfo;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabMenuController;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.base.MetaInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.AffiliatedOrgInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseCourseSpecificLOsConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseExpenditureInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseFormatConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseJointsConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseLearningResultsConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseRevenueInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseVersionsConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.FeeInfoFixedRateFeeConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.LearningObjectiveConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.SingleUseLoConstants;
import org.kuali.student.lum.lu.ui.course.client.controllers.ViewCourseController;

import com.google.gwt.core.client.GWT;
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
    
    public static final String CLU_PROPOSAL_MODEL = "courseProposalModel";

    //FIXME: [KSCOR-225] Initialize type and state from selected cluId
    protected String type = "Course";
    protected String state = DtoConstants.STATE_DRAFT;
    protected String groupName = LUUIConstants.COURSE_GROUP_NAME;

    protected DataModelDefinition modelDefinition;
    protected List<StatementTypeInfo> stmtTypes;
    protected CourseSummaryConfigurer summaryConfigurer;

    public static enum ViewCourseSections{BRIEF, DETAILED, CATALOG}

    public void setModelDefinition(DataModelDefinition modelDefinition){
        this.modelDefinition = modelDefinition;
    }

    public void setStatementTypes(List<StatementTypeInfo> stmtTypes) {
        this.stmtTypes = stmtTypes;
    }

    public void generateLayout(ViewCourseController layoutController) {
        groupName = LUUIConstants.COURSE_GROUP_NAME;

        //Summary
        this.generateLayout(layoutController, CLU_PROPOSAL_MODEL);
        layoutController.addContentWidget(layoutController.getStatusLabel());
        Widget dropdown = layoutController.generateActionDropDown();
        layoutController.addContentWidget(dropdown);
        
        //this.modelDefinition.getMetadata(path)
        layoutController.addContentWidget(layoutController.getVersionHistoryWidget());
        layoutController.showPrint(true);
    }
    
    public void generateLayout(TabMenuController layoutController, String modelId) {
        groupName = LUUIConstants.COURSE_GROUP_NAME;

        //Summary
        summaryConfigurer = GWT.create(CourseSummaryConfigurer.class);
        summaryConfigurer.init(type, state, groupName, modelDefinition, stmtTypes, 
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

    public CourseSummaryConfigurer getSummaryConfigurer() {
        return summaryConfigurer;
    }
}