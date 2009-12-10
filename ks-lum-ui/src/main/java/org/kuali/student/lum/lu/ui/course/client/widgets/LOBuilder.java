/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.CustomNestedSection;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue;
import org.kuali.student.common.ui.client.configurable.mvc.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.SimpleMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.Section.FieldLabelType;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.Type;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSThinTitleBar;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.GoCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.GoCancelEnum;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.selectors.SearchComponentConfiguration;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluDictionaryClassNameHelper;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class manages the users interactions when building/updating Learning Objectives within the
 * context of managing CLUs.  It allows the user to type in LO text directly or execute a search and
 * select one or more of the returned LOs.
 *
 * Users can then re-organize LOs on the screen including altering the sequence and creating sub LOs
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 *
 */
public class LOBuilder extends Composite  implements HasModelDTOValue {
    
    //TODO: need to move more search stuff to LOSearchWidget
    //

    private static String type;
    private static String state;
    private static String messageGroup;

    private LoRpcServiceAsync loRpcServiceAsync = GWT.create(LoRpcService.class);
    private LuRpcServiceAsync luRpcServiceAsync = GWT.create(LuRpcService.class);

    final CluCodePicker cluPicker = new CluCodePicker();
    LOSearchWidget searchWidget;   

    KSLightBox searchResultsWindow ;

    VerticalPanel main = new VerticalPanel();

    HorizontalPanel searchMainPanel = new HorizontalPanel();
    SimplePanel searchSpacerPanel = new SimplePanel();
    HorizontalPanel searchLinkPanel = new HorizontalPanel();
    final SimplePanel searchParamPanel = new SimplePanel();

    SearchComponentConfiguration loSearchConfig;

    VerticalPanel loPanel = new VerticalPanel();

    KSLabel searchLink;
    LearningObjectiveList loList;
    KSLabel instructions ;
    final KSDropDown loSearches = new KSDropDown();

    private static final int NUM_INITIAL_LOS = 5;

    KSLightBox searchWindow ;
    private static final String SEARCH_BY_COURSE_CODE = "by Course Code";
    private static final String SEARCH_BY_WORD = "for words in Learning Objective";


    protected LOBuilder() {
        //TODO: should this be an error?  Can we set realistic defaults?

    }

    public LOBuilder(String luType, String luState, String luGroup) {
        super();
        initWidget(main);

        type = luType;
        state = luState;
        messageGroup = luGroup;

        ClickHandler searchClickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (searchWindow == null) {
                    initSearchWindow();
                }
                else {
//                  loSearches.redraw();  TODO doesn't seem to work
                    searchParamPanel.clear();
                    cluPicker.clear();

                }
                searchWindow.show();
            }
        };

        searchLink = new KSLabel(getLabel(LUConstants.LO_SEARCH_LINK));
        searchLink.addClickHandler(searchClickHandler);

        instructions = new KSLabel(getLabel(LUConstants.LO_INSTRUCTIONS));
        searchLink = new KSLabel(getLabel(LUConstants.LO_SEARCH_LINK));
        searchLink.addClickHandler(searchClickHandler);
        KSImage searchImage = Theme.INSTANCE.getCommonImages().getSearchIcon();
        searchImage.addClickHandler(searchClickHandler);

        searchLinkPanel.add(searchImage);
        searchLinkPanel.add(searchLink);
        searchSpacerPanel.add(new KSLabel(""));

        searchMainPanel.add(searchSpacerPanel);
        searchMainPanel.add(searchLinkPanel);

        loList = new LearningObjectiveList();
        loPanel.add(loList);

        searchImage.addStyleName("KS-LOBuilder-Search-Image");
        searchLink.addStyleName("KS-LOBuilder-Search-Link");
        searchSpacerPanel.addStyleName("KS-LOBuilder-Spacer-Panel");
        searchMainPanel.addStyleName("KS-LOBuilder-Search-Panel");
        loPanel.addStyleName("KS-LOBuilder-LO-Panel");
        instructions.addStyleName("KS-LOBuilder-Instructions");

        main.add(searchMainPanel);
        main.add(instructions);
        main.add(loPanel);

    }

    private void initSearchWindow() {
        VerticalPanel main = new VerticalPanel();
        HorizontalPanel selectSearchPanel = new HorizontalPanel();

        KSThinTitleBar titleBar = new KSThinTitleBar(getLabel(LUConstants.LO_SEARCH_LINK));

//      loSearches.setMultipleSelect(false);
        selectSearchPanel.add(new KSLabel("Search: "));
        selectSearchPanel.add(loSearches);
        final ListItems searchTypesList = buildSearchListItems();
        loSearches.setListItems(searchTypesList);
        loSearches.addSelectionChangeHandler(new SelectionChangeHandler() {

            @Override
            public void onSelectionChange(KSSelectItemWidgetAbstract w) {
                List<String> selectedItems = loSearches.getSelectedItems();
                if (searchTypesList.getItemText(selectedItems.get(0)).equals(SEARCH_BY_COURSE_CODE)) {
                    cluPicker.clear();                    
                    searchParamPanel.clear();
                    searchParamPanel.add(cluPicker);
                }
                else {
                    searchParamPanel.clear();
                    searchParamPanel.add(buildWordSearchPanel());

                }
            }
        })  ;

        GoCancelGroup buttonPanel = new GoCancelGroup(new Callback<GoCancelEnum>(){

            @Override
            public void exec(GoCancelEnum result) {
                switch(result){
                    case GO:
                        List<String> selectedItems = loSearches.getSelectedItems();
                        for(String s: selectedItems){
                            if (searchTypesList.getItemText(s).equals(SEARCH_BY_COURSE_CODE)) {
                                getLOsForClu();                                                          
                            }
                            else if (searchTypesList.getItemText(s).equals(SEARCH_BY_WORD)) {
                                performSearch(loSearchConfig, searchWidget.buildSearchParams());
//                              Window.alert(searchTypesList.getItemText(s) + " selected");

                            }
                            else {
                                Window.alert("Invalid search type selected");

                            }
                        }
                        break;
                    case CANCEL:
                        searchWindow.hide();
                        break;
                }
            }
        });

        main.add(titleBar);
        main.add(selectSearchPanel);
        main.add(searchParamPanel);
        main.add(buttonPanel);

        searchWindow = new KSLightBox();
        searchWindow.setWidget(main);
    }    

    private void getLOsForClu() {
        luRpcServiceAsync.getLoIdsByClu(cluPicker.getValue(), new AsyncCallback<List<String>>() {

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("getLoIdsByClu failed ", caught);
            }

            @Override
            public void onSuccess(List<String> result) {
                searchWindow.hide();
                showCourseSearchResultsWindow(cluPicker.getText(), result);

            }
        });
    }

    private Widget buildWordSearchPanel() {

        VerticalPanel main = new VerticalPanel();

//      KSAdvancedSearchRpc search = new KSAdvancedSearchRpc(loRpcServiceAsync, "lo.search.loByDesc","lo.resultColumn.loDescId");

//      search.setIgnoreCase(true);
//      search.setPartialMatch(true);


        List<String> basicCriteria = new ArrayList<String>() {
            {
                add("lo.queryParam.loDescPlain");
            }
        };

//      List<String> advancedCriteria = new ArrayList<String>() {
//      {
//      add("org.queryParam.orgOptionalLongName");
//      add("org.queryParam.orgOptionalLocation");
//      add("org.queryParam.orgOptionalId");
//      }
//      };    

        //set context criteria
        List<QueryParamValue> contextCriteria = new ArrayList<QueryParamValue>();           
//      QueryParamValue orgOptionalTypeParam = new QueryParamValue();
//      orgOptionalTypeParam.setKey("org.queryParam.orgOptionalType");
//      orgOptionalTypeParam.setValue("kuali.org.Department");   
//      contextCriteria.add(orgOptionalTypeParam);              

        loSearchConfig = new SearchComponentConfiguration(contextCriteria, basicCriteria, null);
        loSearchConfig.setSearchDialogTitle("Find Learning Objectives");
        loSearchConfig.setSearchService(loRpcServiceAsync);
        loSearchConfig.setSearchTypeKey("lo.search.loByDesc");
        loSearchConfig.setResultIdKey("lo.resultColumn.loDescId");
        loSearchConfig.setRetrievedColumnKey("lo.resultColumn.loDescPlain");


        searchWidget = new LOSearchWidget(loSearchConfig, loSearchConfig.getBasicCriteria());   
        searchWidget.setPartialMatch(true);
        searchWidget.setIgnoreCase(true);

//      searchWidget.addSelectionHandler(new SelectionHandler<List<ResultCell>>(){
////    public void onSelection(SelectionEvent<List<String>> event) {
////    }

//      public void onSelection(SelectionEvent<List<ResultCell>> event) {

//      final List<ResultCell> selectedCells = event.getSelectedItem();
//      if (selectedCells.size() > 0){      
//      List<String> selected = new ArrayList<String>();                                
//      for (ResultCell c: selectedCells) {
//      selected.add(c.getValue());

//      }
//      loList.addSelectedLOs(selected);
//      searchWindow.hide();
//      }                  
//      }

//      });

        main.add(searchWidget);
        return main;
    }

    private void showCourseSearchResultsWindow(final String selectedCluCode, final List<String> loIds) {
        if (loIds != null && !loIds.isEmpty()) {
            loRpcServiceAsync.getLoByIdList(loIds, new AsyncCallback<List<LoInfo>>() {

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("getLoByIdList failed " + caught.getMessage());
                }

                @Override
                public void onSuccess(List<LoInfo> results) {

                    searchResultsWindow = new KSLightBox();
                    final KSThinTitleBar titleBar = new KSThinTitleBar(results.size() + " results returned for " + selectedCluCode);
                    final VerticalPanel main = new VerticalPanel();
                    final KSCheckBoxList loCheckBoxes = new KSCheckBoxList();
                    final ListItems loListItems = new LoInfoList(results);
                    loCheckBoxes.setListItems(loListItems);

                    main.add(titleBar);
                    main.add(loCheckBoxes);
                    main.add(new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

                        @Override
                        public void exec(ConfirmCancelEnum result) {
                            switch(result){
                                case CONFIRM:
                                    List<String> selected = new ArrayList<String>();                                
                                    for (String s: loCheckBoxes.getSelectedItems()) {
                                        selected.add(loListItems.getItemText(s));

                                    }
                                    loList.addSelectedLOs(selected);
                                    searchResultsWindow.hide();
                                    break;
                                case CANCEL:
                                    searchWindow.show();
                                    searchResultsWindow.hide();
                                    break;
                            }
                        }
                    }));
                    searchWindow.hide();
                    searchResultsWindow.setWidget(main);
                    searchResultsWindow.show();
                }

            });
        }
        else {
            Window.alert("No LOs found");

        }
    }

    private void performSearch(SearchComponentConfiguration searchConfig, List<QueryParamValue> queryParamValues){

        searchConfig.getSearchService().searchForResults(searchConfig.getSearchTypeKey(), queryParamValues, new AsyncCallback<List<Result>>(){

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Search failed");
            }

            @Override
            public void onSuccess(List<Result> results) {
                showWordSearchResultsWindow(results);
            }
        });
    }

    private void showWordSearchResultsWindow(List<Result> results) {   
                
        searchResultsWindow = new KSLightBox();
        final VerticalPanel main = new VerticalPanel();
        final KSCheckBoxList loCheckBoxes = new KSCheckBoxList();
        final LoResultList loListItems = new LoResultList(results);
        loCheckBoxes.setListItems(loListItems);
        final KSThinTitleBar titleBar = new KSThinTitleBar(loListItems.getItemCount() + " results returned " );//+ enteredWord);

        main.add(titleBar);
        main.add(loCheckBoxes);
        main.add(new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

            @Override
            public void exec(ConfirmCancelEnum result) {
                switch(result){
                    case CONFIRM:
                        List<String> selected = new ArrayList<String>();                                
                        for (String s: loCheckBoxes.getSelectedItems()) {
                            selected.add(loListItems.getItemText(s));

                        }
                        loList.addSelectedLOs(selected);
                        searchResultsWindow.hide();
                        break;
                    case CANCEL:
                        searchWindow.show();
                        searchResultsWindow.hide();
                        break;
                }
            }
        }));
        searchWindow.hide();
        searchResultsWindow.setWidget(main);
        searchResultsWindow.show();

    }
    
    @Override
    public void setValue(ModelDTOValue modelDTOValue) {
        loList.setValue(modelDTOValue);

    }

    @Override
    public void setValue(ModelDTOValue value, boolean fireEvents) {
        setValue(value, fireEvents);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#getValue()
     */
    @Override
    public ModelDTOValue getValue() {
        return loList.getValue();
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#updateModelDTO(org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue)
     */
    @Override
    public void updateModelDTOValue() {
        loList.updateModelDTOValue();
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ModelDTOValue> handler) {
        return loList.addValueChangeHandler(handler);
    }


    private static String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey);
    }

    public static class LearningObjectiveList extends SimpleMultiplicityComposite {
        private static final String STYLE_HIGHLIGHTED_ITEM = "KS-LOBuilder-Highlighted-Item";
        private static final String DESC_KEY = "desc";

        {
            setAddItemLabel(getLabel(LUConstants.LEARNING_OBJECTIVE_ADD_LABEL_KEY));
//            setShowDelete(false);
        }

        @Override
        public void redraw() {
            super.redraw();
            // populate with at least NUM_INITIAL_LOS items,
            // even if there aren't that many defined yet
            int startIdx = null == modelDTOList ? 0 : modelDTOList.get().size();
            for (int i = startIdx; i < NUM_INITIAL_LOS; i++) {
                addItem();
            }
        }

        @Override
        public Widget createItem() {
            final MultiplicitySection multi = new MultiplicitySection(CluDictionaryClassNameHelper.LO_INFO_CLASS,
                    "kuali.lo.type.singleUse", "draft");
            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);

            final LOPicker picker = new LOPicker();
            picker.addValueChangeHandler(new ValueChangeHandler<String>() {
                @Override
                public void onValueChange(ValueChangeEvent<String> event) {


                }
            });
            picker.addMoveUpAction(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                    moveUp(multi);
                }
            });
            picker.addMoveDownAction(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                    moveDown(multi);
                }
            });
            picker.addDeleteAction(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                    delete(multi);
                }
            });
            FieldDescriptor fd = new FieldDescriptor("desc", null/* getLabel(LUConstants.LEARNING_OBJECTIVE_LO_NAME_KEY)*/, Type.STRING, picker);
            ns.addField(fd);
            ns.addStyleName("KS-LOBuilder-Section");
            multi.addSection(ns);

            return multi;
        }

        private void removeOldHighlights() {
            List<HasModelDTOValue> widgets = modelDTOValueWidgets;
            for (HasModelDTOValue w: widgets) {
                ((Widget)w).removeStyleName(STYLE_HIGHLIGHTED_ITEM);
            }
        }

        private void addHighlights(List<Integer> selectedWidgets) {
            for (int i: selectedWidgets) {
                Widget w = (Widget)modelDTOValueWidgets.get(i);
                w.addStyleName(STYLE_HIGHLIGHTED_ITEM);

            }
        }

        private void addSelectedLOs(List<String> selected) {
            this.removeOldHighlights();
            List<Integer> addedLos = new ArrayList<Integer>();
            if (selected.size() > 0){
                for (String loDesc : selected ) {
                    int a = this.addSelectedLO(loDesc);
                    addedLos.add(a);
                }
            }
            this.redraw(); 
            this.addHighlights(addedLos);                   
        }

        private int addSelectedLO(String loDescription) {

            int widgetKey = -1;
            boolean foundEmptyWidget = false;

            for (ModelDTOValue v: modelDTOList.get()) {
                ModelDTO model = ((ModelDTOType) v).get();
                widgetKey++;
                if (model.get(DESC_KEY) == null) {
                    model.put(DESC_KEY, loDescription);
                    foundEmptyWidget = true;
                    break;
                }
            }
            if (!foundEmptyWidget) {
                this.addItem();
                widgetKey = modelDTOList.get().size()-1;
                //FIXME: is the new item always going to be the last one?
                ModelDTOValue v = modelDTOList.get().get(widgetKey);
                ModelDTO model = ((ModelDTOType) v).get();
                model.put(DESC_KEY, loDescription);
            }

            return widgetKey;
        }

        private void moveUp(Widget item){
            Widget decrator = item.getParent().getParent();
            int index = LearningObjectiveList.this.itemsPanel.getWidgetIndex(decrator);
            if(index ==0){
                return;
            }
            LearningObjectiveList.this.itemsPanel.remove(decrator);
            LearningObjectiveList.this.itemsPanel.insert(decrator,index -1 );

        }
        private void moveDown(Widget item){
            Widget decrator = item.getParent().getParent();
            int index = LearningObjectiveList.this.itemsPanel.getWidgetIndex(decrator);
            if(index == LearningObjectiveList.this.itemsPanel.getWidgetCount()-1){
                return;
            }
            LearningObjectiveList.this.itemsPanel.remove(decrator);
            LearningObjectiveList.this.itemsPanel.insert(decrator,index+1 );

        }
        private void delete(Widget item){
            Widget decrator = item.getParent().getParent();
            int index = LearningObjectiveList.this.itemsPanel.getWidgetIndex(decrator);
            if(index == -1){
                return;
            }
            super.decorateItemWidget(item);
            LearningObjectiveList.this.itemsPanel.remove(decrator);
        }
    }
    
    private class LoInfoList implements ListItems{
        Map<String, LoInfo> loInfoMap = new HashMap<String, LoInfo>();

        public LoInfoList(List<LoInfo> loInfos){
            for (LoInfo lo: loInfos){
                loInfoMap.put(lo.getId(), lo);
            }
        }
        
        public List<String> getAttrKeys() {
            return Arrays.asList("Description");
        }
        
        public String getItemAttribute(String id, String attrkey) {
            LoInfo lo = loInfoMap.get(id);
            
            if (attrkey.equals("Description")){
                return lo.getDesc().getPlain(); 
            }
            
            return null;
        }
        
        public int getItemCount() {
            return loInfoMap.size();
        }
        
        public List<String> getItemIds() {
            List<String> keys = new ArrayList<String>();

            for (String s:loInfoMap.keySet()){
                keys.add(s);
            }
            
            return keys;
        }
        
        public String getItemText(String id) {
            return ((LoInfo)loInfoMap.get(id)).getDesc().getPlain();
        }
    }



    class LoResultList implements ListItems{
        Map<String, Result> loResultMap = new HashMap<String, Result>();
                
        public LoResultList(List<Result> results){
            for (Result r: results){
                loResultMap.put(r.getResultCells().get(0).getValue(), r);
            }
        }
        
        public List<String> getAttrKeys() {
            return Arrays.asList("Description");
        }

        public String getItemAttribute(String id, String attrkey) {
            Result r = loResultMap.get(id);
            
            if (attrkey.equals("Description")){
                return r.getResultCells().get(1).getValue(); 
            }
            
            return null;
        }

        public int getItemCount() {
            return loResultMap.size();
        }

        public List<String> getItemIds() {
            List<String> keys = new ArrayList<String>();

            for (String s:loResultMap.keySet()){
                keys.add(s);
            }
            
            return keys;
        }

        public String getItemText(String id) {
            return ((Result)loResultMap.get(id)).getResultCells().get(1).getValue();
        }
                
    }
   
    private ListItems buildSearchListItems() {

        return new ListItems(){
            List<String> names = Arrays.asList(SEARCH_BY_WORD, SEARCH_BY_COURSE_CODE);

            @Override
            public List<String> getAttrKeys() {
                List<String> attributes = new ArrayList<String>();
                attributes.add("Name");
                return attributes;
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                String value = null;
                Integer index;
                try{
                    index = Integer.valueOf(id);
                    value = names.get(index);
                } catch (Exception e) {
                }

                return value;
            }

            @Override
            public int getItemCount() {
                return names.size();
            }

            @Override
            public List<String> getItemIds() {
                List<String> ids = new ArrayList<String>();
                for(int i=0; i < names.size(); i++){
                    ids.add(String.valueOf(i));
                }
                return ids;
            }

            @Override
            public String getItemText(String id) {
                return getItemAttribute(id, "Name");
            }
        };

    }
}
