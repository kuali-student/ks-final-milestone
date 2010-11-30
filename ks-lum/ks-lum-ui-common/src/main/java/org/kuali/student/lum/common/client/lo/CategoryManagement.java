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

package org.kuali.student.lum.common.client.lo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.common.client.lo.rpc.LoCategoryRpcService;
import org.kuali.student.lum.common.client.lo.rpc.LoCategoryRpcServiceAsync;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoCategoryTypeInfo;

import java.util.ArrayList;
import java.util.List;

public class CategoryManagement extends Composite {
    private KSButton addButton = new KSButton("Create");
    private KSButton deleteButton = new KSButton("Delete");
    private KSButton updateButton = new KSButton("Edit");
   
    KSCheckBox accreditationCheckBox = new KSCheckBox("Accreditation");
    KSCheckBox skillCheckBox = new KSCheckBox("Skill");
    KSCheckBox subjectCheckBox = new KSCheckBox("Subject");
    KSTextBox wordsInCategoryTextBox = new KSTextBox();
	
    static LoCategoryRpcServiceAsync loCatRpcServiceAsync = GWT.create(LoCategoryRpcService.class);

    CategoryManagementTable categoryManagementTable = null;
   
    VerticalPanel mainPanel = new VerticalPanel();
    KSLabel messageLabel = new KSLabel();
    
    List<LoCategoryTypeInfo> categoryTypeList;
    private void initCategoryManagement() {
        super.initWidget(mainPanel);
        mainPanel.addStyleName("KSLOCategoryManagementMainPanel");
 
        HorizontalPanel titlePanel = new HorizontalPanel();
        
        KSLabel funnelImg = new KSLabel("");
        funnelImg.addStyleName("KS-LOFunnelImg");
        titlePanel.add(funnelImg);
        
        KSLabel filterLabel = new KSLabel("Filter");
        filterLabel.addStyleName("KSLOCategoryManagementFilterLabel");
        titlePanel.add(filterLabel);
        
        mainPanel.add(titlePanel);
        
        addButton.setStyleName("KS-LOSecondaryButton");
        deleteButton.setStyleName("KS-LOSecondaryButton");
        updateButton.setStyleName("KS-LOSecondaryButton");
     
        accreditationCheckBox.setValue(true);
        skillCheckBox.setValue(true);
        subjectCheckBox.setValue(true);
        
        HorizontalPanel linkButtonPanel = new HorizontalPanel();
        linkButtonPanel.addStyleName("KSLOCategoryManagementFilterPanel");
        
        KSLabel spacer = new KSLabel("");
        spacer.setWidth("60px");
        KSLabel divider = new KSLabel("|");
        
        Anchor selectAllLink = new Anchor("Select All");
        selectAllLink.addStyleName("KS-LOSelectAllHyperlink");
        linkButtonPanel.add(selectAllLink);
        selectAllLink.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                accreditationCheckBox.setValue(true);
                skillCheckBox.setValue(true);
                subjectCheckBox.setValue(true);
                filterCategoryByType();
            }
        });
         
        linkButtonPanel.add(divider);
        
        Anchor clearLink = new Anchor("Clear");
        clearLink.addStyleName("KS-LOClearHyperlink");
        linkButtonPanel.add(clearLink);
        clearLink.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                accreditationCheckBox.setValue(false);
                skillCheckBox.setValue(false);
                subjectCheckBox.setValue(false);
                wordsInCategoryTextBox.setText(null);
                filterCategoryByType();
            }
        });
        linkButtonPanel.add(spacer);
        linkButtonPanel.add(addButton);
        linkButtonPanel.add(updateButton);
        linkButtonPanel.add(deleteButton);
        mainPanel.add(linkButtonPanel);
        
        HorizontalPanel filterTablePanel = new HorizontalPanel();
        VerticalPanel filterPanel = new VerticalPanel();
        filterPanel.addStyleName("KSLOCategoryManagementFilterPanel");
       
        filterPanel.add(accreditationCheckBox);
        filterPanel.add(skillCheckBox);
        filterPanel.add(subjectCheckBox);
        HorizontalPanel spacer1 = new HorizontalPanel();
        spacer1.setStyleName("KS-LOSpacer");
        filterPanel.add(spacer1);
        KSLabel wordsInCategory = new KSLabel("By words in category");
        wordsInCategory.setStyleName("KS-LODialogFieldLabel");
        filterPanel.add(wordsInCategory);
        filterPanel.add(wordsInCategoryTextBox);
        
        filterTablePanel.add(filterPanel);
        if(this.categoryManagementTable == null) {
            categoryManagementTable = new CategoryManagementTable();
        }
        filterTablePanel.add(categoryManagementTable);
        mainPanel.add(filterTablePanel);
        
        mainPanel.add(messageLabel);

        loCatRpcServiceAsync.getLoCategoryTypes(new KSAsyncCallback<List<LoCategoryTypeInfo>>() {
            @Override
            public void handleFailure(Throwable caught) {
                GWT.log("getLoCategoryTypes failed", caught);
                Window.alert("Get LoCategory Types failed");
            }
            @Override
            public void onSuccess(List<LoCategoryTypeInfo> results) {
                categoryTypeList = results;
                categoryManagementTable.loadTable();
            }
        });

        wordsInCategoryTextBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                filterCategoryByWords();
            }

        });
        subjectCheckBox.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                filterCategoryByType();
            }
        });
        skillCheckBox.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                filterCategoryByType();
            }
        });
        accreditationCheckBox.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                filterCategoryByType();
            }
        });

        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                CreateCategoryDialog dialog = new CreateCategoryDialog();
                dialog.setCategoryType(categoryTypeList);
                dialog.show();
            }
        });
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                
                String id = categoryManagementTable.getSelectedLoCategoryInfoId();
                if(id != null){
	                loCatRpcServiceAsync.getData(id, new KSAsyncCallback<Data>() {
	                    @Override
	                    public void handleFailure(Throwable caught) {
	                        GWT.log("getSelectedLoCategoryInfo failed", caught);
	                        Window.alert("Get Selected Lo Category failed");
	                    }

                        @Override
                        public void onSuccess(Data result) {
                            DeleteConfirmationDialog dialog = new DeleteConfirmationDialog();
                            dialog.setCategory(CategoryDataUtil.toLoCategoryInfo(result));
                            dialog.show();
                        }
	                });
                }

            }
        });
        updateButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String id = categoryManagementTable.getSelectedLoCategoryInfoId();
                if(id != null){
	                loCatRpcServiceAsync.getData(id, new KSAsyncCallback<Data>() {
	                    @Override
	                    public void handleFailure(Throwable caught) {
	                        GWT.log("getSelectedLoCategoryInfo failed", caught);
	                        Window.alert("Get Selected Lo Category failed");
	                    }
                        @Override
                        public void onSuccess(Data result) {
	                        UpdateCategoryDialog dialog = new UpdateCategoryDialog();
	                        dialog.setCategory(CategoryDataUtil.toLoCategoryInfo(result));
	                        dialog.setCategoryType(categoryTypeList);
	                        dialog.show();
	                    }
	                });
                }
            }
        });
    }

    public CategoryManagement() {
        initCategoryManagement();
    }
    
    public CategoryManagement(boolean hideInactiveCategories,SelectionPolicy selectionPolicy) {
        this.categoryManagementTable = new CategoryManagementTable(hideInactiveCategories, selectionPolicy);
        initCategoryManagement();
    }
    public List<LoCategoryInfo> getSelectedCategoryList(){
        return categoryManagementTable.getSelectedLoCategoryInfos();
    }
       	
    private void filterCategoryByType() {

        List<ResultRow> bufferList = new ArrayList<ResultRow>();
        if(subjectCheckBox.getValue() == true){
            bufferList.addAll(categoryManagementTable.getRowsByType("subject"));
        }
        if(skillCheckBox.getValue() == true){
            bufferList.addAll(categoryManagementTable.getRowsByType("skill"));
        }
        if(accreditationCheckBox.getValue() == true){
            bufferList.addAll(categoryManagementTable.getRowsByType("accreditation"));
        }
        categoryManagementTable.redraw(bufferList);

    }
    
    private void filterCategoryByWords() {

        List<ResultRow> bufferList = new ArrayList<ResultRow>();
        String input = wordsInCategoryTextBox.getText();
        if(input != null && (!input.isEmpty())){
            List<ResultRow> tmpList = categoryManagementTable.getRowsLikeName(input);
            bufferList.addAll(tmpList);
            categoryManagementTable.redraw(bufferList);
        } else {
            filterCategoryByType();
        }       
    }

    public void setDeleteButtonEnabled(boolean b) {
        deleteButton.setVisible(b);
    }

    public void setUpdateButtonEnabled(boolean b) {
        updateButton.setVisible(b);
    }

    public void setInsertButtonEnabled(boolean b) {
        addButton.setVisible(b);
    }
    class DeleteConfirmationDialog extends KSLightBox {
    	FlexTable layoutTable = new FlexTable();
        KSLabel categoryNameLabel = new KSLabel();
        KSLabel categoryTypeLabel = new KSLabel();
        HorizontalPanel spacer = new HorizontalPanel();
        LoCategoryInfo categoryInfo;
        
        public DeleteConfirmationDialog() {
        	this.setSize(540, 200);
        	spacer.setStyleName("KS-LOSpacer");
            VerticalPanel mainPanel = new VerticalPanel();
            
            KSLabel titleLabel = new KSLabel("Delete Category");
            titleLabel.setStyleName("KS-LODialogTitle");
            mainPanel.add(spacer);
            mainPanel.add(titleLabel);
            HorizontalPanel spacer1 = new HorizontalPanel();
            spacer1.setStyleName("KS-LOSpacer");
            mainPanel.add(spacer1);
            mainPanel.add(new KSLabel("You are about to delete the following:"));
            HorizontalPanel spacer2 = new HorizontalPanel();
            spacer2.setStyleName("KS-LOSpacer"); 
            mainPanel.add(spacer2);
            KSLabel catLabel = new KSLabel("Category");
            mainPanel.add(layoutTable);
            layoutTable.setWidget(0, 0, catLabel);
            layoutTable.setWidget(0, 1, categoryNameLabel);
            KSLabel typeLabel = new KSLabel("Type");
            layoutTable.setWidget(1, 0, typeLabel);
            layoutTable.setWidget(1, 1, categoryTypeLabel);
            layoutTable.getFlexCellFormatter().setStyleName(0, 0, "KS-LODialogLabel");
            layoutTable.getFlexCellFormatter().setStyleName(1, 0, "KS-LODialogLabel");
            
        	HorizontalPanel spacer3 = new HorizontalPanel();
            spacer3.setStyleName("KS-LOSpacer"); 
            mainPanel.add(spacer3);
            KSButton deleteButton = new KSButton("Delete");
            Anchor cancelButton = new Anchor();
            cancelButton.setStyleName("KS-LODialogCancel");
            cancelButton.setText("Cancel");
            HorizontalPanel buttonPanel = new HorizontalPanel();
            buttonPanel.add(deleteButton);

            deleteButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    // not really deleting; rather 'retiring' LoCategory, but switching state to "inactive"
                	categoryInfo.setState("inactive");
                    loCatRpcServiceAsync.saveData(CategoryDataUtil.toData(categoryInfo), new KSAsyncCallback<DataSaveResult>(){
                        @Override
                        public void handleFailure(Throwable caught) {
                            GWT.log("updateLoCategory failed ", caught);
                            Window.alert("Switch LoCategory state to inactive failed ");
                        }
                        @Override
                        public void onSuccess(DataSaveResult result) {
//                            KSBlockingProgressIndicator.removeTask(saving);

                            if(result.getValidationResults()!=null && !result.getValidationResults().isEmpty()){
                                Window.alert("Update LO Category failed: " + result.getValidationResults().get(0).getMessage());
                           }else{
                            KSNotifier.add(new KSNotification("Update LO Category Successful", false, 3000));
                            categoryManagementTable.loadTable();
                            filterCategoryByType();
                            }
                        }
                    });

                    DeleteConfirmationDialog.this.hide();
                }
            });

            buttonPanel.add(cancelButton);
            cancelButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    DeleteConfirmationDialog.this.hide();
                }

            });
            mainPanel.add(buttonPanel);
            super.setWidget(mainPanel);
        }

        public void setCategory(LoCategoryInfo cate) {
            categoryInfo = cate;
            categoryNameLabel.setText(categoryInfo.getName());
            categoryTypeLabel.setText(categoryInfo.getType());
        }
    }
    class UpdateCategoryDialog extends KSLightBox {
        FlexTable layoutTable = new FlexTable();
        KSTextBox nameTextBox = new KSTextBox();
        KSDropDown typeListBox = new KSDropDown();
        KSButton okButton = new KSButton("Save");
        Anchor cancelButton = new Anchor("Cancel");
        LoCategoryInfo categoryInfo;
        HorizontalPanel spacer = new HorizontalPanel();

        public UpdateCategoryDialog() {
        	this.setSize(540, 200);
        	spacer.setStyleName("KS-LOSpacer");
        	cancelButton.setStyleName("KS-LODialogCancel");
        	typeListBox.setHeight("21px");
        	KSLabel catLabel = new KSLabel("Category");
        	catLabel.setStyleName("KS-LODialogFieldLabel");
        	KSLabel typeLabel = new KSLabel("Type");
        	typeLabel.setStyleName("KS-LODialogFieldLabel");
            layoutTable.setWidget(0, 0, catLabel);
            layoutTable.setWidget(0, 1, typeLabel);
            layoutTable.setWidget(1, 0, nameTextBox);
            layoutTable.setWidget(1, 1, typeListBox);

            HorizontalPanel buttonPanel = new HorizontalPanel();
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);

            VerticalPanel mainPanel = new VerticalPanel();
            mainPanel.add(spacer);
            KSLabel titleLabel = new KSLabel("Edit Category");
            titleLabel.setStyleName("KS-LODialogTitle");
            mainPanel.add(titleLabel);
            HorizontalPanel spacer1 = new HorizontalPanel();
            spacer1.setStyleName("KS-LOSpacer");
            mainPanel.add(spacer1);
            mainPanel.add(layoutTable);
            HorizontalPanel spacer2 = new HorizontalPanel();
            spacer2.setStyleName("KS-LOSpacer");
            mainPanel.add(spacer2);
            mainPanel.add(buttonPanel);
            super.setWidget(mainPanel);
            okButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    LoCategoryInfo cate = getCategory();

                    loCatRpcServiceAsync.saveData(CategoryDataUtil.toData(cate), new KSAsyncCallback<DataSaveResult>(){
                        @Override
                        public void handleFailure(Throwable caught) {
                            GWT.log("updateLoCategory failed ", caught);
                            Window.alert("Update LoCategory failed ");
                        }
                        @Override
                        public void onSuccess(DataSaveResult result) {
                            if(result.getValidationResults()!=null && !result.getValidationResults().isEmpty()){
                                Window.alert("Update LO Category failed: " + result.getValidationResults().get(0).getMessage());
                           }else{
                            KSNotifier.add(new KSNotification("Update LO Category Successful", false, 3000));
                            categoryManagementTable.loadTable();
                            filterCategoryByType();
                            }
                        }
                    });

                    UpdateCategoryDialog.this.hide();
                }

            });

            cancelButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    UpdateCategoryDialog.this.hide();
                }

            });
            super.setWidget(mainPanel);
        }

        public void setCategoryType(List<LoCategoryTypeInfo> categoryTypeList) {
            typeListBox.clear();
            SimpleListItems categoryTypes = new SimpleListItems();
            for (LoCategoryTypeInfo type : categoryTypeList) {
                categoryTypes.addItem(type.getId(), type.getDesc());
            }
            typeListBox.setListItems(categoryTypes);
        }

        public void setCategory(LoCategoryInfo cate) {
            categoryInfo = cate;
            nameTextBox.setText(categoryInfo.getName());
            typeListBox.selectItem(categoryInfo.getType());
        }

        public LoCategoryInfo getCategory() {
            categoryInfo.setName(nameTextBox.getText());
            //categoryInfo.setType(typeListBox.getItemText(typeListBox.getSelectedIndex()));
            categoryInfo.setType(typeListBox.getSelectedItem());
            return categoryInfo;
        }
    }

    class CreateCategoryDialog extends KSLightBox {
        FlexTable layoutTable = new FlexTable();
        KSButton okButton = new KSButton("Save");
        Anchor cancelButton = new Anchor("Cancel");
        KSTextBox nameTextBox = new KSTextBox(); 
        KSDropDown typeListBox = new KSDropDown();
        HorizontalPanel spacer = new HorizontalPanel();
        
        public CreateCategoryDialog() {
        	this.setSize(540, 200);
        	spacer.setStyleName("KS-LOSpacer");
        	cancelButton.setStyleName("KS-LODialogCancel");
        	typeListBox.setHeight("21px");
        	KSLabel catLabel = new KSLabel("Category");
        	catLabel.setStyleName("KS-LODialogFieldLabel");
        	KSLabel typeLabel = new KSLabel("Type");
        	typeLabel.setStyleName("KS-LODialogFieldLabel");
            layoutTable.setWidget(0, 0, catLabel);
            layoutTable.setWidget(0, 1, typeLabel);
            layoutTable.setWidget(1, 0, nameTextBox);
            layoutTable.setWidget(1, 1, typeListBox);
            
            HorizontalPanel buttonPanel = new HorizontalPanel();
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            
            VerticalPanel mainPanel = new VerticalPanel();
            mainPanel.add(spacer);
            KSLabel titleLabel = new KSLabel("Create New Category");
            titleLabel.setStyleName("KS-LODialogTitle");
            HorizontalPanel spacer1 = new HorizontalPanel();
            spacer1.setStyleName("KS-LOSpacer");
            
            mainPanel.add(titleLabel);
            mainPanel.add(spacer1);
            mainPanel.add(layoutTable);
            HorizontalPanel spacer2 = new HorizontalPanel();
            spacer2.setStyleName("KS-LOSpacer");
            mainPanel.add(spacer2);
            mainPanel.add(buttonPanel);
            super.setWidget(mainPanel);
            okButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    LoCategoryInfo cate = getCategory();
                    loCatRpcServiceAsync.saveData(CategoryDataUtil.toData(cate), new KSAsyncCallback<DataSaveResult>(){
                        @Override
                        public void handleFailure(Throwable caught) {
                            Window.alert("Create LO Category failed: " + caught.getMessage());
                        }
                        @Override
                        public void onSuccess(DataSaveResult result) {
                            if(result.getValidationResults()!=null && !result.getValidationResults().isEmpty()){
                                Window.alert("Create LO Category failed: " + result.getValidationResults().get(0).getMessage());
                           }else{
                            KSNotifier.add(new KSNotification("Create LO Category Successful", false, 3000));
                            categoryManagementTable.loadTable();
                            filterCategoryByType();
                            }
                        }
                    });

                    CreateCategoryDialog.this.hide();
                }
            });
            cancelButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    CreateCategoryDialog.this.hide();
                }

            });
        }

        public void setCategoryType(List<LoCategoryTypeInfo> categoryTypeList) {
            typeListBox.clear();
            SimpleListItems categoryTypes = new SimpleListItems();
            for (LoCategoryTypeInfo type : categoryTypeList) {
                categoryTypes.addItem(type.getId(), type.getDesc());
            }
            typeListBox.setListItems(categoryTypes);
        }

        public LoCategoryInfo getCategory() {
            LoCategoryInfo info = new LoCategoryInfo();
            info.setName(nameTextBox.getText());
            info.setType(typeListBox.getSelectedItem());
            info.setState("active");
            info.setLoRepository("kuali.loRepository.key.singleUse");
            // FIXME [KSCOR-225] user needs to specify what LoRepository they want category to tagged with
            return info;
        }
    }

}


