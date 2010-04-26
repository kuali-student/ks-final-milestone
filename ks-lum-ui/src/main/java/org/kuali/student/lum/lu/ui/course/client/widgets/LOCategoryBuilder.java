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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSThinTitleBar;
import org.kuali.student.common.ui.client.widgets.buttongroups.CreateCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.CreateCancelEnum;
import org.kuali.student.common.ui.client.widgets.focus.FocusGroup;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.SuggestPicker;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoCategoryTypeInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

/**
 * 
 * This class allows a user to select and remove LO categories within the context of 
 * LO creation. New categories can be added 'on the fly' and are persisted in the database
 * independently of LO creation 
 *  
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class LOCategoryBuilder extends Composite implements HasValue<List<LoCategoryInfo>> {

    private String type;
    private String state;
    private String repoKey;
    private String messageGroup;

    private LoRpcServiceAsync loRpcServiceAsync ;
    private LOCategoryPicker picker ;

    LOCategoryList categoryList;
    Map<String, LoCategoryTypeInfo> categoryTypeMap ;

    VerticalPanel root = new VerticalPanel();

    private KSButton addButton = new KSButton("Add");

    private KSLightBox createCategoryWindow;
    Hyperlink browseCategoryLink = new Hyperlink("Browse for categories", "BrowseCategories");

    public LOCategoryBuilder(String messageGroup, String type, String state, String loRepoKey) {
        super();

        this.type = type;
        this.state = state;
        this.repoKey = loRepoKey;
        this.messageGroup = messageGroup;

        loRpcServiceAsync = GWT.create(LoRpcService.class);
        picker = new LOCategoryPicker();
        categoryList = new LOCategoryList();

        initWidget(root);

        final FlowPanel selectedPanel = new FlowPanel();
        selectedPanel.setStyleName("KS-LOSelectedCategories");
        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addEnteredCategory();
            }
        });
        browseCategoryLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final CategoryManagement categoryManagement = new CategoryManagement(true);
                categoryManagement.setDeleteButtonEnabled(false);
                categoryManagement.setInsertButtonEnabled(false);
                categoryManagement.setUpdateButtonEnabled(false);
                
                final KSLightBox pop = new KSLightBox();
                KSButton addButton = new KSButton("Add");
                addButton.addStyleName("KSLOLightBoxButton");
                KSButton cancelButton = new KSButton("Cancel");
                cancelButton.addStyleName("KSLOLightBoxButtonSecondary");
                HorizontalPanel buttonPanel = new HorizontalPanel();
                buttonPanel.addStyleName("KSLOLightBoxButtonPanel");
                buttonPanel.add(addButton);
                buttonPanel.add(cancelButton);
                
                
                VerticalPanel mainPanel = new VerticalPanel();
                mainPanel.addStyleName("KSLOLightBoxMainPanel");
                mainPanel.add(categoryManagement);
                mainPanel.add(buttonPanel);
                
                addButton.addClickHandler(new ClickHandler(){
                    @Override
                    public void onClick(ClickEvent event) {
                        List<LoCategoryInfo> list = categoryManagement.getSelectedCategoryList();        
                        for(LoCategoryInfo info: list){
                            addCategory(info);
                        }
                        pop.hide();
                    }
                });
                
                cancelButton.addClickHandler(new ClickHandler(){
                    @Override
                    public void onClick(ClickEvent event) {
                        pop.hide();
                    }
                });
                
                
                pop.setWidget(mainPanel);
                pop.show();
                
                
                
            }
        }); 

        VerticalPanel main = new VerticalPanel();
        HorizontalPanel suggestPanel = new HorizontalPanel();
        suggestPanel.add(picker);
        suggestPanel.add(addButton);
        
        VerticalPanel suggestAndBrowsePanel = new VerticalPanel();
        suggestAndBrowsePanel.add(suggestPanel);
        suggestAndBrowsePanel.add(browseCategoryLink);

        
        selectedPanel.add(categoryList);
        main.add(getLabel(LUConstants.LO_CATEGORY_KEY));
        main.add(suggestAndBrowsePanel);
        main.add(selectedPanel);
        root.add(main);

        main.addStyleName("KS-LOCategoryPicker");
        addButton.addStyleName("KS-LOCategoryPicker-Button");

    }

    private void addEnteredCategory() {

        if (categoryList == null)
            categoryList = new LOCategoryList();

        if (picker.getSelectedId().trim().equals("")) {
            showNewCategoryWindow();
        }
        else {
            loRpcServiceAsync.getLoCategory(picker.getSelectedId(), new AsyncCallback<LoCategoryInfo>() {

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("getLoCategory failed " + caught.getMessage());
                }

                @Override
                public void onSuccess(LoCategoryInfo result) {
                    addCategory(result);   
                }
            });
        }
    }

    private void showNewCategoryWindow() {

        final VerticalPanel main = new VerticalPanel();
        final KSDropDown typesDropDown = new KSDropDown();
        createCategoryWindow = new KSLightBox();

        loRpcServiceAsync.getLoCategoryTypes(new AsyncCallback<List<LoCategoryTypeInfo>>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("getLoCategoryTypes failed " + caught.getMessage());
            }

            @Override
            public void onSuccess(List<LoCategoryTypeInfo> result) {
                final LOCategoryTypeInfoList list = new LOCategoryTypeInfoList(result);
                typesDropDown.setListItems(list);
                if (categoryTypeMap == null) {
                    loadCategoryTypes(result);                    
                }
                KSThinTitleBar titleBar = new KSThinTitleBar("Create New Category " + picker.getText());//+ enteredWord);
                main.add(titleBar);
                main.add(new KSLabel("Select a Type"));
                main.add(typesDropDown);
                CreateCancelGroup buttonPanel = new CreateCancelGroup(new Callback<CreateCancelEnum>(){

                    @Override
                    public void exec(CreateCancelEnum result) {
                        switch(result){
                            case CREATE:
                                final LoCategoryInfo loCategoryInfo = new LoCategoryInfo();
                                loCategoryInfo.setName(picker.getText());
                                loCategoryInfo.setState("active");
                                loCategoryInfo.setLoRepository(repoKey);
                                loCategoryInfo.setType(typesDropDown.getSelectedItem());

                                loRpcServiceAsync.createLoCategory(repoKey, typesDropDown.getSelectedItem(),
                                        loCategoryInfo, new AsyncCallback<LoCategoryInfo>() {

                                    @Override
                                    public void onFailure(Throwable caught) {
                                        Window.alert("createCategory failed " + caught.getMessage());                                                
                                    }

                                    @Override
                                    public void onSuccess(LoCategoryInfo result) {
                                        addCategory(result);       
                                        createCategoryWindow.hide();
                                    }


                                });


                                break;
                            case CANCEL:
                                createCategoryWindow.hide();
                                break;
                        }
                    }
                });

                main.add(buttonPanel);
                main.addStyleName("KS-LOWindow");
                createCategoryWindow.setWidget(main);
                createCategoryWindow.show();
            }



        });
    }

    private void loadCategoryTypes(List<LoCategoryTypeInfo> categoryTypes) {
        if (categoryTypeMap == null) {
            categoryTypeMap = new HashMap<String, LoCategoryTypeInfo>();
        }
        if (categoryTypes != null){
	        for (LoCategoryTypeInfo i: categoryTypes) {
	            categoryTypeMap.put(i.getId(), i);
	        }
        }
    }

    private void addCategory(final LoCategoryInfo category) {
        if (categoryTypeMap == null) {
            categoryTypeMap = new HashMap<String, LoCategoryTypeInfo>();
        }

        if (categoryTypeMap.containsKey(category.getType())) {
            categoryList.addItem(category);
            picker.reset();
        }
        else {
            loRpcServiceAsync.getLoCategoryType(category.getType(), new AsyncCallback<LoCategoryTypeInfo> () {

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("getLoCategoryType failed " + caught.getMessage());
                }

                @Override
                public void onSuccess(LoCategoryTypeInfo result) {
                    categoryTypeMap.put(result.getId(), result);
                    categoryList.addItem(category);
                    picker.reset();

                }

            });        
        }

    }

    private KSLabel getLabel(String labelKey) {
        return new KSLabel(Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey));
    }

    @Override
    public void setValue(List<LoCategoryInfo> categories) {
        categoryList.setValue(categories);

    }

    @Override
    public void setValue(List<LoCategoryInfo> value, boolean fireEvents) {
        setValue(value);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#getValue()
     */
    @Override
    public List<LoCategoryInfo> getValue() {
        return categoryList.getValue();
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<LoCategoryInfo>> handler) {
        return categoryList.addValueChangeHandler(handler);
    }

    /**
     * 
     * This class provides a suggest box for existing LO categories backed by a search on category name
     *  
     * @author Kuali Rice Team (kuali-rice@googlegroups.com)
     *
     */
    private class LOCategoryPicker extends Composite implements SuggestPicker {

         final SearchSuggestOracle loSearchOracle = new SearchSuggestOracle(
                 "lo.search.loCategories",
                 "lo.queryParam.loOptionalCategoryName",
                 "lo.queryParam.loCategoryId",
                 "lo.resultColumn.categoryId", 
         "lo.resultColumn.categoryNameAndType");

         final KSSuggestBox suggestBox = new KSSuggestBox(loSearchOracle);

         private final FocusGroup focus = new FocusGroup(this);

         private VerticalPanel main = new VerticalPanel();

         protected LOCategoryPicker() {
             super();
             init();
         }

         public String getSelectedId() {
             return suggestBox.getSelectedId();
         }

         private void init () {

             focus.addWidget(suggestBox);

             loSearchOracle.setTextWidget(suggestBox.getTextBox());
//           final ArrayList<QueryParamValue> params = new ArrayList<QueryParamValue>();
//           QueryParamValue luStateParam = new QueryParamValue();
//           luStateParam.setKey("lu.queryParam.cluState");     
//           luStateParam.setValue(STATE_ACTIVATED);
//           params.add(luStateParam);
//           luSearchOracle.setAdditionalQueryParams(params);

             main.add(suggestBox);
             initWidget(main);
         }



         @Override
         public String getValue() {
             return suggestBox.getSelectedId();
         }

         @Override
         public void setValue(String value) {
             setValue(value, true);
         }

         @Override
         public void setValue(String value, boolean fireEvents) {
             suggestBox.reset();
             suggestBox.setValue(value, fireEvents);
         }


         @Override
         public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
             return suggestBox.addValueChangeHandler(handler);
         }

         @Override
         public void fireEvent(GwtEvent<?> event) {
             super.fireEvent(event);
         }

         public void reset(){
             suggestBox.reset();
         }

         @Override
         public HandlerRegistration addFocusHandler(FocusHandler handler) {
             return focus.addFocusHandler(handler);
         }

         @Override
         public HandlerRegistration addBlurHandler(BlurHandler handler) {
             return focus.addBlurHandler(handler);
         }

         public String getText() {
             return suggestBox.getText();
         }

        @Override
        public HandlerRegistration addSelectionChangeHandler(SelectionChangeHandler handler) {
            return suggestBox.addSelectionChangeHandler(handler);
        }
     }

     private class LOCategoryTypeInfoList implements ListItems{
         Map<String, LoCategoryTypeInfo> loTypeMap = new HashMap<String, LoCategoryTypeInfo>();

         public LOCategoryTypeInfoList(List<LoCategoryTypeInfo> loTypes){
             for (LoCategoryTypeInfo type: loTypes){
                 loTypeMap.put(type.getId(), type);
             }
         }

         public List<String> getAttrKeys() {
             return Arrays.asList("Name");
         }

         public String getItemAttribute(String id, String attrkey) {
             LoCategoryTypeInfo lo = loTypeMap.get(id);

             if (attrkey.equals("Name")){
                 return lo.getName(); 
             }

             return null;
         }

         public int getItemCount() {
             return loTypeMap.size();
         }

         public List<String> getItemIds() {
             List<String> keys = new ArrayList<String>();

             for (String s:loTypeMap.keySet()){
                 keys.add(s);
             }

             return keys;
         }

         public String getItemText(String id) {
             return ((LoCategoryTypeInfo)loTypeMap.get(id)).getName();
         }
     }

     /**
      * 
      * This inner class handles adding and removing selected categories to/from 
      * a list in the CategoryPicker.  Uses ModelDTOList
      *  
      * TODO: Still valid in DOL? 
      * 
      * @author Kuali Rice Team (kuali-rice@googlegroups.com)
      *
      */    
     public class LOCategoryList extends Composite {
         
         private static final String CATEGORY_TYPE_SEPARATOR = " - ";
         protected List<LoCategoryInfo> categories = new ArrayList<LoCategoryInfo>();
         VerticalPanel main = new VerticalPanel();

         FlexTable categoryTable = new FlexTable();

         final ClickHandler deleteHandler = new ClickHandler() {
             @Override
             public void onClick(ClickEvent event) {
                 Cell cell = categoryTable.getCellForEvent(event);
                 int r = cell.getRowIndex();
                 KSLabel label = (KSLabel)categoryTable.getWidget(r, 0);
                 categoryList.removeItem(label.getText());
                 categoryList.redraw();
             }                   
         };

         public LOCategoryList(){

             main.add(categoryTable);
             super.initWidget(main);

         }

         public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<LoCategoryInfo>> handler) {
             return null;
         }

         public void updateModelDTOValue() {
             categoryList.updateModelDTOValue();
         }

         public void redraw() {

			if (null == categoryTypeMap || categoryTypeMap.isEmpty()) {
			            	 
				loRpcServiceAsync.getLoCategoryTypes(new AsyncCallback<List<LoCategoryTypeInfo>>() {
				
					@Override
					public void onFailure(Throwable caught) {
					Window.alert("getLoCategoryTypes failed " + caught.getMessage());
					}
					
					@Override
					public void onSuccess(List<LoCategoryTypeInfo> result) {
						if (categoryTypeMap == null) {
							loadCategoryTypes(result);                    
						}
						redrawCategoryTable();
					}
	
				});
			} else {
				redrawCategoryTable();
			}
		}
			
		private void redrawCategoryTable() {
			int row = 0;
			int col = 0;
			             
			categoryTable.clear();
	
			for (int i = 0; i < categories.size(); i++) {
		        String name = categories.get(i).getName();
		        String typeKey = categories.get(i).getType();
		        // TODO - need to somehow ensure that categoryTypeMap is initialized before redraw() 
		        String typeName = "ERROR: uninitialized categoryTypeMap";
		        if (null != categoryTypeMap) {
		            typeName = categoryTypeMap.get(typeKey).getName();
		        } 
		        categoryTable.setWidget(row, col++, new KSLabel(name + CATEGORY_TYPE_SEPARATOR + typeName));
		        KSLabel deleteLabel = new KSLabel("[x]");
		        deleteLabel.addStyleName("KS-LOBuilder-Search-Link");
		        deleteLabel.addClickHandler(deleteHandler);
		        categoryTable.setWidget(row, col++, deleteLabel);
		        row++;
		        col = 0;                                
			}
         }

         public List<LoCategoryInfo> getValue() {
             return categories;
         }

         public void setValue(List<LoCategoryInfo> categories) {
             this.categories = categories;
             redraw();            
         }

         public void removeItem(String text) {

             int a  = text.indexOf(CATEGORY_TYPE_SEPARATOR);
             text = text.substring(0,a);

             int i = 0;
             for (LoCategoryInfo catInfo : categories) {
                 String name = catInfo.getName();

                 if (name.equals(text)) {
                     categories.remove(i);
                     break;
                 }
                 i++;                              
             }
             redraw();
         }

         public void addItem(LoCategoryInfo category) {

             categories.add(category);

             redraw();
         }
     }
}
