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

package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.List;
//import java.util.ListIterator;
//import java.util.Set;

//import org.kuali.student.common.ui.client.service.ServerPropertiesRpcService;
//import org.kuali.student.common.ui.client.service.ServerPropertiesRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
//import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
//import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoCategoryTypeInfo;
//import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcServiceAsync;
//import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.ColumnDefinition;
//import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.ColumnRenderer;
//import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable;
//import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.Row;
//import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.Selection;
//import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableCell;
//import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableDefinition;
//import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableModel;
//import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableDefinition.SelectionMode;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
//import com.google.gwt.event.logical.shared.SelectionEvent;
//import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
//import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
//import com.google.gwt.user.client.ui.Widget;

public class CategoryManagement extends Composite {
    private KSButton addButton = new KSButton("Create");
    private KSButton deleteButton = new KSButton("Delete");
    private KSButton updateButton = new KSButton("Update");

    KSCheckBox accreditationCheckBox = new KSCheckBox("Accreditation");
    KSCheckBox skillCheckBox = new KSCheckBox("Skill");
    KSCheckBox subjectCheckBox = new KSCheckBox("Subject");
    KSTextBox wordsInCategoryTextBox = new KSTextBox();

//	private Boolean displayOnlyActiveCategories;
//	private Boolean canDeleteLoCatAssociatedWithActiveLo;
	
    static LoRpcServiceAsync loRpcServiceAsync = GWT.create(LoRpcService.class);
//    static ServerPropertiesRpcServiceAsync serverProperties = GWT.create(ServerPropertiesRpcService.class);
    
    CategoryManagementTable categoryManagementTable = null;

    VerticalPanel mainPanel = new VerticalPanel();
    KSLabel messageLabel = new KSLabel();
    
    List<LoCategoryInfo> categoryList;
    List<LoCategoryTypeInfo> categoryTypeList;
    private void initCategoryManagement() {
        super.initWidget(mainPanel);
        mainPanel.addStyleName("KSLOCategoryManagementMainPanel");
        accreditationCheckBox.setValue(true);
        skillCheckBox.setValue(true);
        subjectCheckBox.setValue(true);
        VerticalPanel filterPanel = new VerticalPanel();
        filterPanel.addStyleName("KSLOCategoryManagementFilterPanel");
        KSLabel filterLabel = new KSLabel("Filter");
        filterLabel.addStyleName("KSLOCategoryManagementFilterLabel");
        filterPanel.add(filterLabel);

        Hyperlink selectAllLink = new Hyperlink("Select All","Select All");
        selectAllLink.addStyleName("Home-Small-Hyperlink");
        filterPanel.add(selectAllLink);
        selectAllLink.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                accreditationCheckBox.setValue(true);
                skillCheckBox.setValue(true);
                subjectCheckBox.setValue(true);
                filterCategoryByType();
            }
        });

        Hyperlink clearLink = new Hyperlink("Clear","Clear");
        clearLink.addStyleName("Home-Small-Hyperlink");
        filterPanel.add(clearLink);
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
        filterPanel.add(accreditationCheckBox);
        filterPanel.add(skillCheckBox);
        filterPanel.add(subjectCheckBox);
        filterPanel.add(new KSLabel("By words in category"));
        filterPanel.add(wordsInCategoryTextBox);

        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.addStyleName("KSLOCategoryManagementButtonPanel");
        buttonPanel.add(addButton);
        // buttonPanel.add(filterButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        mainPanel.add(buttonPanel);
        HorizontalPanel filterTablePanel = new HorizontalPanel();
        filterTablePanel.add(filterPanel);
        if(this.categoryManagementTable == null) {
            categoryManagementTable = new CategoryManagementTable();
        }
        filterTablePanel.add(categoryManagementTable);
        mainPanel.add(filterTablePanel);
        
        mainPanel.add(messageLabel);

        loRpcServiceAsync.getLoCategoryTypes(new AsyncCallback<List<LoCategoryTypeInfo>>() {
            @Override
            public void onFailure(Throwable caught) {
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
                loRpcServiceAsync.getLoCategory(id, new AsyncCallback<LoCategoryInfo>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        GWT.log("getSelectedLoCategoryInfo failed", caught);
                        Window.alert("Get Selected Lo Category failed");
                    }

                    @Override
                    public void onSuccess(LoCategoryInfo result) {
                        DeleteConfirmationDialog dialog = new DeleteConfirmationDialog();
                        dialog.setCategory(result);
                        dialog.show();
                    }
                });

            }
        });
        updateButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String id = categoryManagementTable.getSelectedLoCategoryInfoId();
                loRpcServiceAsync.getLoCategory(id, new AsyncCallback<LoCategoryInfo>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        GWT.log("getSelectedLoCategoryInfo failed", caught);
                        Window.alert("Get Selected Lo Category failed");
                    }

                    @Override
                    public void onSuccess(LoCategoryInfo result) {
                        UpdateCategoryDialog dialog = new UpdateCategoryDialog();
                        dialog.setCategory(result);
                        dialog.setCategoryType(categoryTypeList);
                        dialog.show();
                    }
                });
            }
        });
    }
    public CategoryManagement() {
        initCategoryManagement();
    }
    
    public CategoryManagement(boolean hideInactiveCategories) {
        this.categoryManagementTable = new CategoryManagementTable(hideInactiveCategories);
        initCategoryManagement();
    }
    public List<LoCategoryInfo> getSelectedCategoryList(){
        return categoryManagementTable.getSelectedLoCategoryInfos();
    }
    
/*    
    private void loadDataAndRefresh() {
        loRpcServiceAsync.getLoCategories("kuali.loRepository.key.singleUse", new AsyncCallback<List<LoCategoryInfo>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("getLoCategory failed " + caught.getMessage());
            }

            @Override
            public void onSuccess(List<LoCategoryInfo> result) {
                categoryList = result;
                
                if (null == displayOnlyActiveCategories) {
			        serverProperties.get("ks.lum.ui.displayOnlyActiveLoCategories", new AsyncCallback<String>() {
						@Override
						public void onFailure(Throwable caught) {
							displayOnlyActiveCategories = new Boolean("false");
                            Window.alert("Unable to retrieve displayOnlyActiveLoCategories setting: " + caught.getMessage());
			                setCatTableData(categoryList);
						}
			
						@Override
						public void onSuccess(String result) {
							displayOnlyActiveCategories = Boolean.parseBoolean(result);
			                setCatTableData(categoryList);
						}
			        });
                }
                else {
	                setCatTableData(categoryList);
                }
            }
        });
    }
    
	private void setCatTableData(List<LoCategoryInfo> cats) {
        // FIXME - need a custom search for LoCateogies of the proper repo and state == active
        // then we can get rid of this removal loop
        if (null != displayOnlyActiveCategories && displayOnlyActiveCategories.equals(Boolean.TRUE)) {
        	ListIterator<LoCategoryInfo> iter = categoryList.listIterator();
        	while (iter.hasNext()) {
        		LoCategoryInfo catInfo = iter.next();
        		if ( ! catInfo.getState().equals("active") ) {
        			iter.remove();
        		}
        	}
        }
        categoryTable.setData(cats);
	}
*/    	
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
        KSLabel categoryNameLabel = new KSLabel();
        KSLabel categoryTypeLabel = new KSLabel();
        LoCategoryInfo categoryInfo;
        public DeleteConfirmationDialog() {
            VerticalPanel mainPanel = new VerticalPanel();
            FlexTable layoutTable = new FlexTable();
            mainPanel.add(new KSLabel("You are about to delete the following:"));
            mainPanel.add(layoutTable);
            layoutTable.setWidget(0, 0, new KSLabel("Category"));
            layoutTable.setWidget(0, 1, categoryNameLabel);
            layoutTable.setWidget(1, 0, new KSLabel("Type"));
            layoutTable.setWidget(1, 1, categoryTypeLabel);

            KSButton deleteButton = new KSButton("Delete");
            Hyperlink cancelButton = new Hyperlink();
            cancelButton.setText("Cancel");
            HorizontalPanel buttonPanel = new HorizontalPanel();
            buttonPanel.add(deleteButton);

            deleteButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    // not really deleting; rather 'retiring' LoCategory, but switching state to "inactive"
                	categoryInfo.setState("inactive");
                    CategoryManagement.loRpcServiceAsync.updateLoCategory(categoryInfo.getId(), categoryInfo, new AsyncCallback<LoCategoryInfo>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            GWT.log("updateLoCategory failed ", caught);
                            Window.alert("Switch LoCategory state to inactive failed ");
                        }

                        @Override
                        public void onSuccess(LoCategoryInfo updatedLo) {
                            categoryManagementTable.loadTable();
                            filterCategoryByType();
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
        //ListBox typeListBox = new ListBox();
        KSDropDown typeListBox = new KSDropDown();
        KSButton okButton = new KSButton("OK");
        KSButton cancelButton = new KSButton("Cancel");
        LoCategoryInfo categoryInfo;

        public UpdateCategoryDialog() {
            layoutTable.setWidget(0, 0, new KSLabel("Category"));
            layoutTable.setWidget(0, 1, new KSLabel("Type"));
            layoutTable.setWidget(1, 0, nameTextBox);
            layoutTable.setWidget(1, 1, typeListBox);

            HorizontalPanel buttonPanel = new HorizontalPanel();
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);

            VerticalPanel mainPanel = new VerticalPanel();
            mainPanel.add(new KSLabel("Update Category"));
            mainPanel.add(layoutTable);
            mainPanel.add(buttonPanel);

            super.setWidget(mainPanel);
            okButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    LoCategoryInfo cate = getCategory();
                  //  Window.alert(cate.getType());
                    CategoryManagement.loRpcServiceAsync.updateLoCategory(cate.getId(), cate, new AsyncCallback<LoCategoryInfo>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            GWT.log("updateLoCategory failed ", caught);
                            Window.alert("Update LoCategory failed ");
                        }
                        @Override
                        public void onSuccess(LoCategoryInfo result) {
                            categoryManagementTable.loadTable();
                            filterCategoryByType();
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
                //typeListBox.addItem(type.getId());
                categoryTypes.addItem(type.getId(), type.getDesc());
            }
            typeListBox.setListItems(categoryTypes);
        }

        public void setCategory(LoCategoryInfo cate) {
            categoryInfo = cate;
            nameTextBox.setText(categoryInfo.getName());
            typeListBox.selectItem(categoryInfo.getType());
            //typeListBox.setSelectedIndex(getTypeIndex(categoryInfo.getType()));
        }
/*        private int getTypeIndex(String type){
            for(int i=0;i<typeListBox.getListItems().getItemCount();i++){
                if(typeListBox.getSelectedItems().get(i).equals(type)){
                    return i;
                }
            }
            return 0;
        }
*/
        public LoCategoryInfo getCategory() {
            categoryInfo.setName(nameTextBox.getText());
            //categoryInfo.setType(typeListBox.getItemText(typeListBox.getSelectedIndex()));
            categoryInfo.setType(typeListBox.getSelectedItem());
            return categoryInfo;
        }
    }

    class CreateCategoryDialog extends KSLightBox {
        FlexTable layoutTable = new FlexTable();

        KSButton okButton = new KSButton("OK");
        KSButton cancelButton = new KSButton("Cancel");

        KSTextBox nameTextBox = new KSTextBox();
       // ListBox typeListBox = new ListBox();

         KSDropDown typeListBox = new KSDropDown();
        public CreateCategoryDialog() {

            layoutTable.setWidget(0, 0, new KSLabel("Category"));
            layoutTable.setWidget(0, 1, new KSLabel("Type"));
            layoutTable.setWidget(1, 0, nameTextBox);
            layoutTable.setWidget(1, 1, typeListBox);

            HorizontalPanel buttonPanel = new HorizontalPanel();
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);

            VerticalPanel mainPanel = new VerticalPanel();
            mainPanel.add(new KSLabel("Create New Category"));
            mainPanel.add(layoutTable);
            mainPanel.add(buttonPanel);
            mainPanel.setPixelSize(300, 300);
            super.setWidget(mainPanel);
            okButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    LoCategoryInfo cate = getCategory();
                    CategoryManagement.loRpcServiceAsync.createLoCategory(cate.getLoRepository(), cate.getType(), cate, new AsyncCallback<LoCategoryInfo>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            GWT.log("createLoCategory failed ", caught);
                            Window.alert("Create LoCategory failed ");
                        }
                        @Override
                        public void onSuccess(LoCategoryInfo result) {
                            categoryManagementTable.loadTable();
                            filterCategoryByType();
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
                //typeListBox.addItem();
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
            // FIXME - user needs to specify what LoRepository they want category to tagged with
            return info;
        }
    }
/*
    class CategoryTable extends Composite {
        List<LoCategoryInfo> categoryList = null;

        private final boolean showHeader;
        private final boolean showFooter;
        final FlowPanel panel = new FlowPanel();
        TableModel<LoCategoryInfo> model = new MyTableModel();
      

        public LoCategoryInfo getSelectedItem() {
            Set<String> ids = model.getSelection().getIds();
            for (String id : ids) {
                for (LoCategoryInfo cate : categoryList) {
                    if (cate.getId().equals(id)) {
                        return cate;
                    }
                }
            }
            return null;
        }
        public List<LoCategoryInfo> getSelectedItems() {
            Set<String> ids = model.getSelection().getIds();
            List<LoCategoryInfo> list = new ArrayList<LoCategoryInfo>();
            for (String id : ids) {
                for (LoCategoryInfo cate : categoryList) {
                    if (cate.getId().equals(id)) {
                        list.add(cate);
                    }
                }
            }
            return list;
        }

        public void setData(List<LoCategoryInfo> l) {
            categoryList = l;
            model.refresh();
        }
        
        public CategoryTable() {
            this.showHeader = false;
            this.showFooter = false;
            super.initWidget(panel);
            TableDefinition<LoCategoryInfo> definition = new MyTableDefinition(SelectionMode.MULTI_ITEM);
            definition.setShowHeader(showHeader);
            definition.setShowFooter(showFooter);
            DynamicTable<LoCategoryInfo> table = new DynamicTable<LoCategoryInfo>(10, definition, model);
            
            if ( null == canDeleteLoCatAssociatedWithActiveLo ) { 
		        serverProperties.get("ks.lum.ui.canDeleteLoCatAssociatedWithActiveLo", new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						canDeleteLoCatAssociatedWithActiveLo = new Boolean("false");
                        Window.alert("Unable to retrieve canDeleteLoCatAssociatedWithActiveLo setting: " + caught.getMessage());
                        // Confirm that this can be done async, probably happening after table's added to panel
                        model.addSelectionHandler(getDeleteButtonToggleHandler());
					}
		
					@Override
					public void onSuccess(String result) {
						canDeleteLoCatAssociatedWithActiveLo = Boolean.parseBoolean(result);
						if ( ! canDeleteLoCatAssociatedWithActiveLo ) {
	                        // Confirm that this can be done async, probably happening after table's added to panel
	                        model.addSelectionHandler(getDeleteButtonToggleHandler());
						}
					}
		        });
            }

            panel.add(table);
        }
        
		private SelectionHandler<Selection<LoCategoryInfo>> getDeleteButtonToggleHandler() {
            SelectionHandler<Selection<LoCategoryInfo>> returnHandler = new SelectionHandler<Selection<LoCategoryInfo>>() {
                @Override
                public void onSelection(SelectionEvent<Selection<LoCategoryInfo>> event) {
                	// Selection<LoCategoryInfo> sel = event.getSelectedItem();
                    LoCategoryInfo cate = getSelectedItem();
                    if (null == cate) { // nothing selected
                        deleteButton.setEnabled( true );
                    }
                    // FIXME - either need to make list single-select, or we need to somehow get
                    // see if there any LOs associated with any of the selected LoCategories
                    // List<LoCategoryInfo> los = getSelectedItems();
                    loRpcServiceAsync.getLosByLoCategory(cate.getId(), new AsyncCallback<List<LoInfo>>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        @Override
                        public void onSuccess(List<LoInfo> result) {
                            if(result == null || result.size() == 0){
                                deleteButton.setEnabled( true );
                            }else{
                                deleteButton.setEnabled( false );
                            }
                        }
                    });
                    
                }
            };
            return returnHandler;
		}

        class MyTableModel extends TableModel<LoCategoryInfo> {
            @Override
            public String getUniqueIdentifier(final LoCategoryInfo value) {
                return value.getId();
            }

            @Override
            public void refresh() {
                requestRowCount();
            }

            private void requestRowCount() {
                if (categoryList == null) {
                    return;
                }
                fireModelChangeEvent(categoryList.size());
                // fireBusyStateChange(BusyState.BUSY);
            }

            @Override
            public void requestRows(final int start, final int count, final AsyncCallback<List<Row<LoCategoryInfo>>> callback) {
                // fireBusyStateChange(BusyState.BUSY);
                final List<Row<LoCategoryInfo>> result = new ArrayList<Row<LoCategoryInfo>>();
                callback.onSuccess(result); // clear the table
                if (start >= categoryList.size()) {
                    return;
                }
                if (start + count > categoryList.size()) {
                    return;
                }
                final List<LoCategoryInfo> rows = new ArrayList<LoCategoryInfo>();
                for (int i = start; i < count; i++) {
                    rows.add(categoryList.get(i));
                }
                for (int i = 0; i < rows.size(); i++) {
                    final Row<LoCategoryInfo> r = new Row<LoCategoryInfo>(start + i, rows.get(i));

                    result.add(r);
                }
                callback.onSuccess(result);
            }

        }
    }

    class MyTableDefinition extends TableDefinition<LoCategoryInfo> {

        public MyTableDefinition(final SelectionMode selectionMode) {
            // super(SelectionMode.MULTI_ITEM, "myTable", DefaultTableImages.INSTANCE);
            super(selectionMode, "myTable");
            super.addColumn(new ColumnDefinition<LoCategoryInfo>("Name", true, true, new ColumnRenderer<LoCategoryInfo>() {

                @Override
                public String getDisplayName() {
                    return "Name";
                }

                @Override
                public void onRedraw(final DynamicTable<LoCategoryInfo> table, final Widget headerFooterWidget) {
                // do nothing
                }

                @Override
                public void renderCell(final DynamicTable<LoCategoryInfo> table, final TableCell cell, final LoCategoryInfo value) {
                    cell.setText(value.getName());
                }

                @Override
                public void renderHeader(final DynamicTable<LoCategoryInfo> table, final TableCell cell) {
                    cell.setText(getDisplayName());
                }

            }));
            super.addColumn(new ColumnDefinition<LoCategoryInfo>("Type", true, true, new ColumnRenderer<LoCategoryInfo>() {

                @Override
                public String getDisplayName() {
                    return "Type";
                }
                @Override
                public void onRedraw(final DynamicTable<LoCategoryInfo> table, final Widget headerFooterWidget) {
                }
                @Override
                public void renderCell(final DynamicTable<LoCategoryInfo> table, final TableCell cell, final LoCategoryInfo value) {
                    for(LoCategoryTypeInfo typeInfo : categoryTypeList){
                        if(typeInfo.getId().equals(value.getType())){
                            cell.setText(typeInfo.getDesc());
                        }
                    }
                }
                @Override
                public void renderHeader(final DynamicTable<LoCategoryInfo> table, final TableCell cell) {
                    cell.setText(getDisplayName());
                }
            }));
            super.addColumn(buildLoCatStateColumnDefinition());
            super.getColumns().get(2).setVisible(false);
            if (null == displayOnlyActiveCategories) {
		        serverProperties.get("ks.lum.ui.displayOnlyActiveLoCategories", new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						displayOnlyActiveCategories = new Boolean("false");
                        Window.alert("Unable to retrieve displayOnlyActiveLoCategories setting: " + caught.getMessage());
		            //    MyTableDefinition.super.addColumn(buildLoCatStateColumnDefinition());
                        MyTableDefinition.super.getColumns().get(2).setVisible(true);
					}
		
					@Override
					public void onSuccess(String result) {
						displayOnlyActiveCategories = Boolean.parseBoolean(result);
						if ( ! displayOnlyActiveCategories ) {
			          //      MyTableDefinition.super.addColumn(buildLoCatStateColumnDefinition());
						    MyTableDefinition.super.getColumns().get(2).setVisible(true);
						}
					}
		        });
            }
            else {
				if ( ! displayOnlyActiveCategories ) {
	                //MyTableDefinition.super.addColumn(buildLoCatStateColumnDefinition());
				    MyTableDefinition.super.getColumns().get(2).setVisible(true);
				}
            }
        }
        
		private ColumnDefinition<LoCategoryInfo> buildLoCatStateColumnDefinition() {
	        return new ColumnDefinition<LoCategoryInfo>("State", true, true, new ColumnRenderer<LoCategoryInfo>() {
	
	            @Override
	            public String getDisplayName() {
	                return "State";
	            }
	            @Override
	            public void onRedraw(final DynamicTable<LoCategoryInfo> table, final Widget headerFooterWidget) {
	            }
	            @Override
	            public void renderCell(final DynamicTable<LoCategoryInfo> table, final TableCell cell, final LoCategoryInfo value) {
	                cell.setText(value.getState());
	            }
	            @Override
	            public void renderHeader(final DynamicTable<LoCategoryInfo> table, final TableCell cell) {
	                cell.setText(getDisplayName());
	            }
	        });
		}
    }
    */
}


