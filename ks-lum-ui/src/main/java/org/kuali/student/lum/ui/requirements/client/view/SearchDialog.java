/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSListBox;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.*;
import org.kuali.student.common.ui.client.widgets.KSConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.YesNoGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.YesNoEnum;


public class SearchDialog extends Composite {
    private RequirementsRpcServiceAsync requirementsRpcServiceAsync = GWT.create(RequirementsRpcService.class);
    
    private final SimplePanel mainPanel = new SimplePanel();
    //private final KSModalDialogPanel popupPanel = new KSModalDialogPanel();
    KSLightBox popupPanel = new KSLightBox();
    
    private KSLabel linkAddCourses = new KSLabel("Advanced search");
    private KSButton btnCancel = new KSButton("Cancel");
    private KSButton btnAdd = new KSButton("Add");
    KSListBox cluList = new KSListBox();    
    private Model<RuleInfo> model;
    private Controller controller;
    private Map<String, String> listData;
    
    public SearchDialog(Controller controller, Map<String, String> listData) {
        super.initWidget(mainPanel);
        this.listData = listData;
        Panel searchView = new VerticalPanel();       
        HorizontalPanel pnlButtons = new HorizontalPanel();
        this.controller = controller; 
        searchView.add(cluList);        
        pnlButtons.add(btnAdd);
        btnAdd.setStyleName("KS-Rules-Standard-Button");        
        pnlButtons.add(btnCancel);
        btnCancel.setStyleName("KS-Rules-Standard-Button");        
        searchView.add(pnlButtons);
        
        
        VerticalPanel popupPanelContent = new VerticalPanel();
        popupPanelContent.add(new Label("Courses"));
        popupPanelContent.add(searchView);
        popupPanelContent.addStyleName("KS-Rules-SearchPanel");
        popupPanel.setWidget(popupPanelContent);
        
        //popupPanel.setModal(true);
       
        
        // popupPanel.center();
        linkAddCourses.setStyleName("KS-Rules-Search-Link");
        mainPanel.setWidget(linkAddCourses);
        setupHandlers();
        layoutWidgets();
    }
    
    public void show() {
        if (model == null) {
            controller.requestModel(RuleInfo.class, new ModelRequestCallback<RuleInfo>() {
                public void onModelReady(Model<RuleInfo> theModel) {
                    //printModel(theModel);
                    model = theModel;                    
                }

                public void onRequestFail(Throwable cause) {
                    throw new RuntimeException("Unable to connect to model", cause);
                }
            });
        }
        popupPanel.show();
    }
    
    public void hide() {
        popupPanel.hide();
    }


    public void addCourseAddHandler(ClickHandler addCourseHandler) {
        btnAdd.addClickHandler(addCourseHandler);
    }

    public void layoutWidgets() {
        
        requirementsRpcServiceAsync.getAllClus(new AsyncCallback<Map<String, String>>() {
            public void onFailure(Throwable caught) {
                // just re-throw it and let the uncaught exception handler deal with it
                Window.alert(caught.getMessage());
                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(final Map<String, String> clus) {
                ListItems listItemClus = new ListItems() {
                    private Map<String, String> results = listData; //clus; - to be fixed
                    @Override
                    public List<String> getAttrKeys() {
                        List<String> attributes = new ArrayList<String>();
                        attributes.add("Key");
                        return attributes;
                    }

                    @Override
                    public String getItemAttribute(String id, String attrkey) {
                        String value = null;
                        
                        try{
                            if (attrkey.isEmpty()) {
                                value = id; //results.get(id); fix later
                            }
                        } catch (Exception e) {
                        }

                        return value;
                    }

                    @Override
                    public int getItemCount() {    
                        return results.size();
                    }

                    @Override
                    public List<String> getItemIds() {
                        List temp = Arrays.asList(results.keySet().toArray());
                        List<String> ids = new ArrayList<String>(temp);                        
                        return ids;
                    }

                    @Override
                    public String getItemText(String id) {
                        return getItemAttribute(id, ""); //"Key");
                    }
                };                    
                cluList.setListItems(listItemClus);
            }
        });
    }
    
    public void setEnabled(boolean enabled) {
        if (enabled) {
            linkAddCourses.setStyleName("KS-Rules-Search-Link");
        } else {
            linkAddCourses.setStyleName("KS-Rules-Search-Link-Disabled");            
        }
    }
    
    public List<String> getSelections() {
        List<String> selections = new ArrayList<String>();
        ListItems listItems = cluList.getListItems();
        List<String> selectedIds = cluList.getSelectedItems();
        if (listItems != null && selectedIds != null) {
            for (String id : selectedIds) {
                selections.add(listItems.getItemAttribute(id, ""));
            }
        }
        return selections;
    }

    private void setupHandlers() {
        linkAddCourses.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (cluList != null && cluList.getSelectedItems() != null) {
                    for (String id : cluList.getSelectedItems()) {
                        cluList.deSelectItem(id);
                    }
                }
                show();
            }
        });
        btnCancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        btnAdd.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                hide();
            }
        });
    }
    
}
