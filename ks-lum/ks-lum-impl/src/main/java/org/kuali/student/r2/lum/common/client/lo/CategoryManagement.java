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

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.GroupFieldLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.TableFieldLayout;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.lum.common.client.lo.rpc.LoCategoryRpcService;
import org.kuali.student.lum.common.client.lo.rpc.LoCategoryRpcServiceAsync;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r1.lum.lo.dto.LoCategoryTypeInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class CategoryManagement extends Composite {
    private KSButton addButton = new KSButton("Create", ButtonStyle.SECONDARY);
    private KSButton deleteButton = new KSButton("Delete", ButtonStyle.SECONDARY);
    private KSButton updateButton = new KSButton("Edit", ButtonStyle.SECONDARY);
   
    KSCheckBox accreditationCheckBox = new KSCheckBox("Accreditation");
    KSCheckBox skillCheckBox = new KSCheckBox("Skill");
    KSCheckBox subjectCheckBox = new KSCheckBox("Subject");
    KSTextBox wordsInCategoryTextBox = new KSTextBox();
	
    static LoCategoryRpcServiceAsync loCatRpcServiceAsync = GWT.create(LoCategoryRpcService.class);

    CategoryManagementTable categoryManagementTable = null;
   
    FlowPanel mainPanel = new FlowPanel();
    FlowPanel tablePanel = new FlowPanel();
    HorizontalBlockFlowPanel layout = new HorizontalBlockFlowPanel();
    
    List<LoCategoryTypeInfo> categoryTypeList;
    private void initCategoryManagement() {
        super.initWidget(layout);
        layout.add(mainPanel);
        layout.add(tablePanel);
        //this.addStyleName("standard-content-padding");
        tablePanel.addStyleName("KSLOCategoryManagementTablePanel");
        mainPanel.addStyleName("KSLOCategoryManagementMainPanel");
 
        HorizontalBlockFlowPanel titlePanel = new HorizontalBlockFlowPanel();
        
        KSLabel funnelImg = new KSLabel("");
        funnelImg.addStyleName("KS-LOFunnelImg");
        titlePanel.add(funnelImg);
        
        KSLabel filterLabel = new KSLabel("Filter");
        filterLabel.addStyleName("KSLOCategoryManagementFilterLabel");
        titlePanel.add(filterLabel);
        
        mainPanel.add(titlePanel);
        
        addButton.addStyleName("KS-LOSecondaryButton");
        deleteButton.addStyleName("KS-LOSecondaryButton");
        updateButton.addStyleName("KS-LOSecondaryButton");
     
        accreditationCheckBox.setValue(true);
        skillCheckBox.setValue(true);
        subjectCheckBox.setValue(true);
        
        HorizontalBlockFlowPanel linkButtonPanel = new HorizontalBlockFlowPanel();
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
        
        mainPanel.add(linkButtonPanel);
        
        VerticalFlowPanel filterPanel = new VerticalFlowPanel();
       
        filterPanel.add(accreditationCheckBox);
        filterPanel.add(skillCheckBox);
        filterPanel.add(subjectCheckBox);
        
        FieldElement wordsInCategory = new FieldElement("By words in category", wordsInCategoryTextBox);
        filterPanel.add(wordsInCategory);
        mainPanel.add(filterPanel);
        
        tablePanel.add(addButton);
        tablePanel.add(updateButton);
        tablePanel.add(deleteButton);
        updateButton.setEnabled(false);
		deleteButton.setEnabled(false);
        
        if(this.categoryManagementTable == null) {
            categoryManagementTable = new CategoryManagementTable();
        }
        else{
        	categoryManagementTable.addStyleName("KSLOCategoryManagementTable");
        }
        categoryManagementTable.getTable().addSelectionChangeHandler(new SelectionChangeHandler(){

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				if(categoryManagementTable.getSelectedRows().size() > 0){
					updateButton.setEnabled(true);
					deleteButton.setEnabled(true);
				}
				else{
					updateButton.setEnabled(false);
					deleteButton.setEnabled(false);
				}
			}
		});
        
        tablePanel.add(categoryManagementTable);
         
        loCatRpcServiceAsync.getLoCategoryTypes(new KSAsyncCallback<List<LoCategoryTypeInfo>>() {
            @Override
            public void handleFailure(Throwable caught) {
                GWT.log("getLoCategoryTypes failed", caught);
                Window.alert("Get LoCategory Types failed");
            }
            @Override
            public void onSuccess(List<LoCategoryTypeInfo> results) {
                categoryTypeList = results;
                categoryManagementTable.loadTable(new Callback<Boolean>(){
					@Override
					public void exec(Boolean result) {
						//nothing
					}
				});
            }
        });

        wordsInCategoryTextBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                filterCategoryByWords();
            }

        });
        subjectCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				filterCategoryByType();
			}
        });
        skillCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				filterCategoryByType();
				
			}
        });
        accreditationCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
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
    
    public CategoryManagement(boolean hideInactiveCategories, boolean isMultiSelect) {
        this.categoryManagementTable = new CategoryManagementTable(hideInactiveCategories, isMultiSelect);
        initCategoryManagement();
    }
    /**
     * This constructor is used to filter out items already in the picker
     * <p>
     * We need to pass the list in the constructor because the table is populated
     * using an async call  
     * <p>
     * See KSLAB-1871
     * 
     * @param hideInactiveCategories  ?
     * @param isMultiSelect
     * @param loCategoriesToFilter a list of categories that should be filtered from the popup on open
     */
    public CategoryManagement(boolean hideInactiveCategories, boolean isMultiSelect, List<LoCategoryInfo> loCategoriesToFilter) {
        this.categoryManagementTable = new CategoryManagementTable(hideInactiveCategories, isMultiSelect, loCategoriesToFilter);
        initCategoryManagement();
    }
    public List<LoCategoryInfo> getSelectedCategoryList(){
        return categoryManagementTable.getSelectedLoCategoryInfos();
    }
       	
    private void filterCategoryByType() {

        List<ResultRow> bufferList = new ArrayList<ResultRow>();
        if(subjectCheckBox.getValue() == true){
            bufferList.addAll(categoryManagementTable.getRowsByType("loCategoryType.subject"));
        }
        if(skillCheckBox.getValue() == true){
            bufferList.addAll(categoryManagementTable.getRowsByType("loCategoryType.skillarea"));
        }
        if(accreditationCheckBox.getValue() == true){
            bufferList.addAll(categoryManagementTable.getRowsByType("loCategoryType.accreditation"));
        }
        Collections.sort(bufferList);
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
        KSLabel categoryNameLabel = new KSLabel();
        KSLabel categoryTypeLabel = new KSLabel();
        HorizontalPanel spacer = new HorizontalPanel();
        LoCategoryInfo categoryInfo;
        KSButton deleteButton = new KSButton("Delete");
        KSButton cancelButton = new KSButton("Cancel", ButtonStyle.ANCHOR_LARGE_CENTERED);
        KSTextBox nameTextBox = new KSTextBox(); 
        KSDropDown typeListBox = new KSDropDown();
        
        TableFieldLayout layout = new TableFieldLayout();
        FlowPanel mainPanel = new FlowPanel();
        
        
        public DeleteConfirmationDialog() {
        	this.setSize(540, 200);
        	
        	mainPanel.addStyleName("LOCategoryDialogSpacing");
        	layout.addStyleName("LOCategoryDelete");
        	mainPanel.add(layout);
            
        	layout.setLayoutTitle(SectionTitle.generateH2Title("Delete Category"));
            layout.setInstructions("You are about to delete the following:");
            FieldElement category = new FieldElement("Category", categoryNameLabel);
            FieldElement type = new FieldElement("Type", categoryTypeLabel);
            layout.addField(category);
            layout.addField(type);
            
            this.addButton(deleteButton);
            this.addButton(cancelButton);
            
            deleteButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    // not really deleting; rather 'retiring' LoCategory, but switching state to "inactive"
                	categoryInfo.setStateKey("inactive");
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
                            //categoryManagementTable.remove
                            categoryManagementTable.loadTable(new Callback<Boolean>(){

								@Override
								public void exec(Boolean result) {
									if(result){
										filterCategoryByType();
									}
									
								}
							});
                            }
                        }
                    });

                    DeleteConfirmationDialog.this.hide();
                }
            });

            cancelButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    DeleteConfirmationDialog.this.hide();
                }

            });
            super.setWidget(mainPanel);
        }

        public void setCategory(LoCategoryInfo cate) {
            categoryInfo = cate;
            categoryNameLabel.setText(categoryInfo.getName());
            if (categoryTypeList != null) {
                for (LoCategoryTypeInfo catTypeInfo : categoryTypeList) {
                    if (catTypeInfo.getId() != null && catTypeInfo.getId().equals(categoryInfo.getTypeKey())) {
                        categoryTypeLabel.setText(catTypeInfo.getName());
                        break;
                    }
                }
            }
        }
    }
    class UpdateCategoryDialog extends KSLightBox {
        KSButton okButton = new KSButton("Save");
        KSButton cancelButton = new KSButton("Cancel", ButtonStyle.ANCHOR_LARGE_CENTERED);
        KSTextBox nameTextBox = new KSTextBox(); 
        KSDropDown typeListBox = new KSDropDown();
        
        GroupFieldLayout layout = new GroupFieldLayout();
        FlowPanel mainPanel = new FlowPanel();
        LoCategoryInfo categoryInfo;
        
        public UpdateCategoryDialog() {
        	this.setSize(540, 200);
        	mainPanel.addStyleName("LOCategoryDialogSpacing");
        	layout.setLayoutTitle(SectionTitle.generateH2Title("Edit Category"));
            
            FieldElement category = new FieldElement("Category", nameTextBox);
            FieldElement type = new FieldElement("Type", typeListBox);
            layout.addField(category);
            layout.addField(type);
            
            
            this.addButton(okButton);
            this.addButton(cancelButton);
            
            mainPanel.add(layout);
            super.setWidget(mainPanel);
            okButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    LoCategoryInfo cate = getCategory();
                    boolean error = false;
                    layout.clearValidationErrors();
                    if(nameTextBox.getText().isEmpty()){
                    	layout.addValidationErrorMessage("Category", "Required");
                    	error = true;
                    }
                    if(typeListBox.getSelectedItem() == null || typeListBox.getSelectedItem().isEmpty()){
                    	layout.addValidationErrorMessage("Type", "Required");
                    	error = true;
                    }
                    
                    if(!error){
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
	                            categoryManagementTable.loadTable(new Callback<Boolean>(){

									@Override
									public void exec(Boolean result) {
										if(result){
											filterCategoryByType();
										}
										
									}
								});
	                            }
	                        }
	                    });
	
	                    UpdateCategoryDialog.this.hide();
                    }
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
           	typeListBox.selectItem(categoryInfo.getTypeKey());
        }

        public LoCategoryInfo getCategory() {
            categoryInfo.setName(nameTextBox.getText());
            //categoryInfo.setType(typeListBox.getItemText(typeListBox.getSelectedIndex()));
            categoryInfo.setTypeKey(typeListBox.getSelectedItem());
            return categoryInfo;
        }
    }

    class CreateCategoryDialog extends KSLightBox {
        KSButton okButton = new KSButton("Save");
        KSButton cancelButton = new KSButton("Cancel", ButtonStyle.ANCHOR_LARGE_CENTERED);
        KSTextBox nameTextBox = new KSTextBox(); 
        KSDropDown typeListBox = new KSDropDown();
        
        GroupFieldLayout layout = new GroupFieldLayout();
        FlowPanel mainPanel = new FlowPanel();
        
        public CreateCategoryDialog() {
        	this.setSize(540, 200);
        	mainPanel.addStyleName("LOCategoryDialogSpacing");
        	layout.setLayoutTitle(SectionTitle.generateH2Title("Create New Category"));
            
            FieldElement category = new FieldElement("Category", nameTextBox);
            FieldElement type = new FieldElement("Type", typeListBox);
            layout.addField(category);
            layout.addField(type);
            
            
            this.addButton(okButton);
            this.addButton(cancelButton);
            
            mainPanel.add(layout);
            super.setWidget(mainPanel);
            okButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    LoCategoryInfo cate = getCategory();
                    boolean error = false;
                    layout.clearValidationErrors();
                    if(nameTextBox.getText().isEmpty()){
                    	layout.addValidationErrorMessage("Category", "Required");
                    	error = true;
                    }
                    if(typeListBox.getSelectedItem() == null || typeListBox.getSelectedItem().isEmpty()){
                    	layout.addValidationErrorMessage("Type", "Required");
                    	error = true;
                    }
                    
                    if(!error){
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
	                            categoryManagementTable.loadTable(new Callback<Boolean>(){

									@Override
									public void exec(Boolean result) {
										if(result){
											filterCategoryByType();
										}
										
									}
								});
	                            }
	                        }
	                    });
	
	                    CreateCategoryDialog.this.hide();
                    }
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
            info.setTypeKey(typeListBox.getSelectedItem());
            info.setStateKey("Active");
            info.setLoRepositoryKey("kuali.loRepository.key.singleUse");
            // FIXME [KSCOR-225] user needs to specify what LoRepository they want category to tagged with
            return info;
        }
    }

}


