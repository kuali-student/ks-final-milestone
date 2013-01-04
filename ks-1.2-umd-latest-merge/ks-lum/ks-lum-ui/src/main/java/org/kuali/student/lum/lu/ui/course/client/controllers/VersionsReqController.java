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

package org.kuali.student.lum.lu.ui.course.client.controllers;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.DocumentLayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabMenuController;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.WorkQueue;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.lum.common.client.helpers.RecentlyViewedHelper;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.ViewCourseConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer.CourseSections;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsDataModel;
import org.kuali.student.lum.lu.ui.course.client.requirements.HasRequirements;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.widgets.CourseWorkflowActionList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class VersionsReqController extends TabMenuController implements HasRequirements{

    private final CourseRequirementsDataModel reqDataModel;
	            
    public VersionsReqController(VersionsController controller) {
    	super(CourseProposalController.class.getName());
    	reqDataModel = controller.getReqDataModel();
	}

	@Override
    public CourseRequirementsDataModel getReqDataModel() {
        return reqDataModel;
    }    
}