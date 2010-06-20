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

package org.kuali.student.lum.lu.ui.home.client.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.application.ViewContext.IdType;
import org.kuali.student.common.ui.client.event.ChangeViewActionEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.rice.authorization.PermissionType;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ModifyFindPanel extends ViewComposite{
    public static final String SEARCH_TYPE_PROPOSALS = "Proposals";
    public static final String SEARCH_TYPE_COURSES = "Courses";

    CreditCourseProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CreditCourseProposalRpcService.class);
    MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);

    private VerticalPanel mainPanel = new VerticalPanel();

    private boolean loaded = false;

    public ModifyFindPanel(Controller controller) {
        super(controller, "Find Course");
        mainPanel.addStyleName("KS-Picker-Border");
        this.initWidget(mainPanel);
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
        if (!loaded){
            addIfPermitted(PermissionType.SEARCH, "Courses");
            //addIfPermitted(PermissionType.SEARCH, "Proposals");
            loaded = true;
        }
        onReadyCallback.exec(true);
    }
    private void addIfPermitted(PermissionType permType, String searchType) {
    	addIfPermitted(permType, searchType, new HashMap<String,String>());
    }

    private void addIfPermitted(PermissionType permType, final String searchType, Map<String,String> permissionAttributes) {
        cluProposalRpcServiceAsync.isAuthorized(permType, permissionAttributes, new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {
                throw new RuntimeException("Could not verify authorization: " + caught.getMessage(), caught);
            }
            @Override
            public void onSuccess(Boolean result) {
                //NOTE: quick hack; does not matter because this all goes away with new Curriculum Management home screen
                if(result) {
                    if (searchType.equals("Courses")) {
                        addCourseSearchWindow();
                    } else {
                        //addProposalSearchWindow();
                    }
                }
            }
        });
    }

    private void addCourseSearchWindow(){
        metadataServiceAsync.getMetadata("search", "", "", new AsyncCallback<Metadata>() {
            @Override
            public void onFailure(Throwable caught) {
                throw new RuntimeException("Could not verify authorization: " + caught.getMessage(), caught);
            }
            @Override
            public void onSuccess(Metadata metadata) {
                metadata = metadata.getProperties().get("findCourseTmp");  //TEMP until we have new home page screen where we have suggest box instead of a link
                KSPicker courseSearchWindow = new KSPicker(metadata.getInitialLookup(), metadata.getAdditionalLookups());
                courseSearchWindow.addValuesChangeHandler(new ValueChangeHandler<List<String>>(){
                    public void onValueChange(ValueChangeEvent<List<String>> event) {
                        List<String> selection = (List<String>)event.getValue();
                        ViewContext viewContext = new ViewContext();
                        viewContext.setId(selection.get(0));
                        viewContext.setIdType(IdType.OBJECT_ID);
                        ModifyFindPanel.this.getController().fireApplicationEvent(new ChangeViewActionEvent<LUMViews>(LUMViews.VIEW_COURSE, viewContext));
                    }
                });
                mainPanel.add(courseSearchWindow);
            }
        });
    }
}
