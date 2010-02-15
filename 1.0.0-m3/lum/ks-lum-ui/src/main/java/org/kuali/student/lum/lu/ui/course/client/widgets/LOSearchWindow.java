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
package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDatePickerAbstract;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.KSThinTitleBar;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.SearchCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.SearchCancelGroup;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SearchResultListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.selectors.SearchComponentConfiguration;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.search.dto.QueryParamInfo;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * This a search widget that builds a query form using parameters defined in a 
 * search type defined in a search service. Results are handled in the controller widget
 * 
 * FIXME: This is a temporary solution until the generic search widget is available.
 * 
 * @author Kuali Student Team
 *
 */
public class LOSearchWindow extends Composite {

    private String type;
    private String state;
    private String messageGroup;

    KSLightBox searchWindow ;

    private VerticalPanel searchLayout = new VerticalPanel();
    final SimplePanel searchParamPanel = new SimplePanel();
    private VerticalPanel searchRequestPanel = new VerticalPanel();

    private List<KSTextBox> textBoxes = new ArrayList<KSTextBox>();
    private List<KSDatePickerAbstract> datePickers = new ArrayList<KSDatePickerAbstract>();
    private List<KSSelectItemWidgetAbstract> selectableEnums = new ArrayList<KSSelectItemWidgetAbstract>();
    private Map<KSDatePickerAbstract, String> datePickerCriteriaKeys = new HashMap<KSDatePickerAbstract, String>(); 
    private FlexTable searchParamTable = new FlexTable();

    private KSCheckBoxList loCheckBoxes;
    private ListItems listItems;
    private ConfirmCancelGroup buttons;
    ListItems searchTypesList;

    private SearchComponentConfiguration searchConfig;    

    private boolean ignoreCase = false;
    private boolean partialMatch = false;

    private LoRpcServiceAsync loRpcServiceAsync ;
    private LuRpcServiceAsync luRpcServiceAsync ;

    // FIXME: List should come from search service?
    private static final String SEARCH_BY_COURSE_CODE = "by Course Code";
    private static final String SEARCH_BY_WORD = "for words in Learning Objective";
    private static final String SEARCH_BY_CATEGORY = "by Category";

    private static final String LO_DESCRIPTION_ATTR_KEY = "Description";
    private static final String LO_CLU_CODE_ATTR_KEY = "From";

    final KSDropDown loSearches = new KSDropDown();
    CluCodePicker cluPicker ;

    /**
     * 
     * This constructs an LOSearchWindow which handles all the search functions for LOBuilder
     * 
     * @param messageGroup: 
     * @param type: Clu type, used for message lookup
     * @param state: Clu state, used for message lookup
     * @param buttons: selection buttons for search results
     */
    public LOSearchWindow(String messageGroup, String type, String state, ConfirmCancelGroup buttons )  {
        this.type = type;
        this.state = state;
        this.messageGroup = messageGroup;  
        this.buttons = buttons;

        if (searchWindow == null) {
            initSearchWindow();
        }
        else {
            clear();
        }
        searchWindow.show();

    }

    private void initSearchWindow() {
        HorizontalPanel selectSearchPanel = new HorizontalPanel();

        KSThinTitleBar titleBar = new KSThinTitleBar(getLabel(LUConstants.LO_SEARCH_LINK_KEY));

        loRpcServiceAsync = GWT.create(LoRpcService.class);
        luRpcServiceAsync = GWT.create(LuRpcService.class);

//      loSearches.setMultipleSelect(false);
        selectSearchPanel.add(new KSLabel("Search: "));
        selectSearchPanel.add(loSearches);
        searchTypesList = buildSearchListItems();
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
                else if (searchTypesList.getItemText(selectedItems.get(0)).equals(SEARCH_BY_CATEGORY)) {
                    searchParamPanel.clear();
                    searchParamPanel.add(buildCategorySearchPanel());
                }
                else {
                    searchParamPanel.clear();
                    searchParamPanel.add(buildWordSearchPanel());

                }
            }
        })  ;

        SearchCancelGroup buttonPanel = new SearchCancelGroup(new Callback<SearchCancelEnum>(){

            @Override
            public void exec(SearchCancelEnum result) {
                switch(result){
                    case SEARCH:
                        List<String> selectedItems = loSearches.getSelectedItems();
                        for(String s: selectedItems){
                            if (searchTypesList.getItemText(s).equals(SEARCH_BY_COURSE_CODE)) {
                                getLOsForClu();                                                          
                            }
                            else if (searchTypesList.getItemText(s).equals(SEARCH_BY_WORD)) {
                                performWordSearch(searchConfig, buildSearchParams());
                            }
                            else if (searchTypesList.getItemText(s).equals(SEARCH_BY_CATEGORY)) {
                                performCategorySearch(searchConfig, buildSearchParams());
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


        cluPicker = new CluCodePicker(messageGroup, type, state);

        searchRequestPanel.add(titleBar);
        searchRequestPanel.add(selectSearchPanel);
        searchRequestPanel.add(searchParamPanel);
        searchRequestPanel.add(buttonPanel);

        searchWindow = new KSLightBox();
        searchWindow.setWidget(searchRequestPanel);

        searchRequestPanel.addStyleName("KS-LOWindow");
        titleBar.addStyleName("KS-LOSearch-Title");        
        selectSearchPanel.addStyleName("KS-LOSearch-Type-Panel");        
        searchParamPanel.addStyleName("KS-LOSearch-Param-Panel");        
        buttonPanel.addStyleName("KS-LOSearch-Button-Panel");        


    }    


    private void getLOsForClu() {
        luRpcServiceAsync.getLoIdsByClu(cluPicker.getValue(), new AsyncCallback<List<String>>() {

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("getLoIdsByClu failed ", caught);
            }

            @Override
            public void onSuccess(List<String> result) {
                showCourseSearchResultsWindow(cluPicker.getText(), result);

            }
        });
    }

    private Widget buildWordSearchPanel() {

        VerticalPanel main = new VerticalPanel();

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

        searchConfig = new SearchComponentConfiguration(contextCriteria, basicCriteria, null);
        searchConfig.setSearchDialogTitle("Find Learning Objectives");
        searchConfig.setSearchService(loRpcServiceAsync);
        searchConfig.setSearchTypeKey("lo.search.loCluByDesc");
        searchConfig.setResultIdKey("lo.resultColumn.loId");
        searchConfig.setRetrievedColumnKey("lo.resultColumn.loDescPlain");

        setPartialMatch(true);
        setIgnoreCase(true);

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

        generateSearchLayout();
        main.add(searchLayout);
        searchLayout.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        return main;
    }


    private Widget buildCategorySearchPanel() {

        VerticalPanel main = new VerticalPanel();

        List<String> basicCriteria = new ArrayList<String>() {
            {
                add("lo.queryParam.loCategoryName");
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

        searchConfig = new SearchComponentConfiguration(contextCriteria, basicCriteria, null);
        searchConfig.setSearchDialogTitle("Find Learning Objectives");
        searchConfig.setSearchService(loRpcServiceAsync);
        searchConfig.setSearchTypeKey("lo.search.loByCategory");
        searchConfig.setResultIdKey("lo.resultColumn.loId");
        searchConfig.setRetrievedColumnKey("lo.resultColumn.loDescPlain");

        setPartialMatch(true);
        setIgnoreCase(true);

        generateSearchLayout();

        main.add(searchLayout);
        searchLayout.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
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

                    final KSThinTitleBar titleBar = new KSThinTitleBar(results.size() + " results returned for " + selectedCluCode);
                    final VerticalPanel main = new VerticalPanel();

                    KSLabel searchAgainLink = generateSearchAgainLink();
                    listItems = new LoInfoList(results);

                    loCheckBoxes = new KSCheckBoxList();
                    loCheckBoxes.setListItems(listItems);

                    main.add(titleBar);
                    main.add(searchAgainLink);
                    main.add(loCheckBoxes);
                    main.add(buttons);
                    main.addStyleName("KS-LOSearch-Window");

                    searchWindow.setWidget(main);
                }

            });
        }
        else {
            Window.alert("No LOs found");

        }
    }

    private void performWordSearch(SearchComponentConfiguration searchConfig, List<QueryParamValue> queryParamValues){

        searchConfig.getSearchService().searchForResults(searchConfig.getSearchTypeKey(), queryParamValues, new AsyncCallback<List<Result>>(){

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Search failed");
            }

            @Override
            public void onSuccess(List<Result> results) {
                showSearchResultsWindow(results);
            }
        });
    }

    private void performCategorySearch(SearchComponentConfiguration searchConfig, List<QueryParamValue> queryParamValues){

        searchConfig.getSearchService().searchForResults(searchConfig.getSearchTypeKey(), queryParamValues, new AsyncCallback<List<Result>>(){

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Search failed");
            }

            @Override
            public void onSuccess(List<Result> results) {
                showSearchResultsWindow(results);
            }
        });
    }

    private void showSearchResultsWindow(List<Result> results) {   

        final VerticalPanel main = new VerticalPanel();




        if (results != null) {
            listItems = new LoResultList(results);
            loCheckBoxes = new KSCheckBoxList();
            loCheckBoxes.setListItems(listItems);
            final KSThinTitleBar titleBar = new KSThinTitleBar(listItems.getItemCount() + " results returned " );//+ enteredWord);
            
            KSLabel searchAgainLink = generateSearchAgainLink();
            main.add(titleBar);
            main.add(searchAgainLink);
            main.add(loCheckBoxes);
            main.add(buttons);
            main.addStyleName("KS-LOWindow");

            searchWindow.setWidget(main);         
        }
        else {
            Window.alert("No matches found");
        }


    }

    private KSLabel generateSearchAgainLink() {
        KSLabel searchAgainLink = new KSLabel(getLabel(LUConstants.LO_SEARCH_AGAIN_LINK_KEY));
        searchAgainLink.addStyleName("KS-LOBuilder-Search-Link");
        searchAgainLink.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                reset();
            }

        });
        return searchAgainLink;
    }
    private void generateSearchLayout() {
        searchConfig.getSearchService().getSearchType(searchConfig.getSearchTypeKey(), new AsyncCallback<SearchTypeInfo>(){

            public void onFailure(Throwable caught) {
                Window.alert("Error generating search layout: " + caught);
            }

            public void onSuccess(SearchTypeInfo searchTypeInfo) {
                int row = 0;
                int column = 0;

                SearchCriteriaTypeInfo criteriaInfo = searchTypeInfo.getSearchCriteriaTypeInfo();
                List<QueryParamInfo> queryParams = criteriaInfo.getQueryParams();
                for (QueryParamInfo q:queryParams){

                    //show only criteria used in this context to filter the search                  
                    if (!searchConfig.getBasicCriteria().contains(q.getKey())) {
                        continue;
                    }

                    FieldDescriptor fd = q.getFieldDescriptor();
                    //TODO change this to use a message from messages, using the fd.getName() as the token
                    KSLabel paramLabel = new KSLabel(fd.getName());
//                  paramLabel.addStyleName(KSStyles.KS_ADVANCED_SEARCH_PARAM_LABELS);
                    searchParamTable.getColumnFormatter().addStyleName(0, KSStyles.KS_ADVANCED_SEARCH_PARAM_LABEL_COLUMN);
//                  searchParamTable.getCellFormatter().addStyleName(row, column, KSStyles.KS_ADVANCED_SEARCH_PARAM_LABEL_CELLS);
                    searchParamTable.setWidget(row, column, paramLabel);
                    column++;

                    if (fd.getSearch()!=null && fd.getSearch().getKey() != null ){                                                
                        KSSelectItemWidgetAbstract dropDown = new KSDropDown();
                        populateSearchEnumeration(dropDown, fd.getSearch().getKey());

                        dropDown.setName(q.getKey());
                        searchParamTable.setWidget(row, column, dropDown);
                        selectableEnums.add(dropDown);
                    } else if (fd.getDataType() != null && fd.getDataType().equals("date")) {
                        KSDatePickerAbstract dp = new KSDatePicker();
                        searchParamTable.setWidget(row, column, dp);
                        datePickers.add(dp);
                        datePickerCriteriaKeys.put(dp, q.getKey());
                    } else{
                        KSTextBox tb = new KSTextBox();
                        tb.setName(q.getKey());
                        searchParamTable.setWidget(row, column, tb);
                        textBoxes.add(tb);
                    }
                    column = 0;
                    row++;
                }

                column++;
                searchLayout.add(searchParamTable);     
            }            
        });       
    }

    /*    *//** 
     * Use this to allow multiple select of items from search results
     *
     *//*
    public void setMultipleSelect(boolean enable){
       searchResults.setMultipleSelect(enable); 
    }

      *//**
      * Id of selected item.  If multiple items are selected, this will return the
      * first selected item.
      * 
      * @return
      *//*
    public String getSelectedId(){
        return searchResults.getSelectedItem();
    }*/

    /** 
     * Use to get a list of items selected from the results of search displayed to the user.
     * 
     * @return
     */
//  public List<String> getSelectedIds(){
//  return searchResultsTable.getSelectedIds();
//  }

    public List<QueryParamValue> buildSearchParams(){
        //Build query parameters
        List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>();
        int a = searchParamTable.getRowCount();
        for (int row=0; row < searchParamTable.getRowCount(); row++ ){
            Widget w = searchParamTable.getWidget(row, 1);

            QueryParamValue queryParamValue = new QueryParamValue();
            if (w instanceof HasName) {
                queryParamValue.setKey(((HasName)w).getName());
            } else if (w instanceof KSDatePickerAbstract) {
                queryParamValue.setKey(datePickerCriteriaKeys.get(w));
            }

            if (w instanceof KSSelectItemWidgetAbstract){
                queryParamValue.setValue(((KSSelectItemWidgetAbstract)w).getSelectedItem());
            } else if (w instanceof KSDatePickerAbstract) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                java.util.Date date = ((KSDatePickerAbstract)w).getValue();
                String paramValue = null;
                if (date != null) {
                    paramValue = (sdf.format(date));
                }
                queryParamValue.setValue(paramValue);
            } else {
                String value = ((HasText)w).getText();
                if (value.contains("*")) {//handle special characters TODO: remove '*' completely
                    value = value.replace('*','%');                    
                }
                else if (partialMatch) {
                    value = "%" + value + "%";
                }
                if (ignoreCase) {
                    value = value.toLowerCase();
                }
                queryParamValue.setValue(value);
            }

            //do not add criteria that are empty; criteria are empty if user did not use them or picker context does not require them
            if ((queryParamValue.getValue() != null) && (!((String) queryParamValue.getValue()).trim().isEmpty())) {
                queryParamValues.add(queryParamValue);                
            }
        }

        //add context-specific criteria
        for (QueryParamValue contextCriterion : searchConfig.getContextCriteria()){
            queryParamValues.add(contextCriterion);
        }             

        return queryParamValues;
    }

    private void populateSearchEnumeration(final KSSelectItemWidgetAbstract selectField, String searchType){               
        searchConfig.getSearchService().searchForResults(searchType, null, new AsyncCallback<List<Result>>(){

            @Override
            public void onFailure(Throwable caught) {
                // TODO: Handle this exception                
            }

            @Override
            public void onSuccess(List<Result> results) {
                selectField.setListItems(new SearchResultListItems(results));
                selectField.redraw();
            }

        });
    } 

    private void populateSearchEnumeration(final KSSelectItemWidgetAbstract selectField, Enum enumInfo){
        //TODO: Call the enumeration managment service to get enum
    }

    public void reset(){
        for(KSTextBox t: textBoxes){
            t.setText("");
        }
        for(KSSelectItemWidgetAbstract w: selectableEnums){
            w.redraw();
        }
        for (KSDatePickerAbstract dp: datePickers) {
            dp.setValue(null);
        }
        clear();
        searchWindow.setWidget(searchRequestPanel);
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public void setPartialMatch(boolean partialMatch) {
        this.partialMatch = partialMatch;
    }

    private String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey);
    }

    private ListItems buildSearchListItems() {

        return new ListItems(){
            List<String> names = Arrays.asList(SEARCH_BY_WORD, SEARCH_BY_COURSE_CODE, SEARCH_BY_CATEGORY);

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

    private class LoInfoList implements ListItems{
        Map<String, LoInfo> loInfoMap = new HashMap<String, LoInfo>();

        public LoInfoList(List<LoInfo> loInfos){
            for (LoInfo lo: loInfos){
                loInfoMap.put(lo.getId(), lo);
            }
        }

        public List<String> getAttrKeys() {
            return Arrays.asList(LO_DESCRIPTION_ATTR_KEY);
        }

        public String getItemAttribute(String id, String attrkey) {
            LoInfo lo = loInfoMap.get(id);

            if (attrkey.equals(LO_DESCRIPTION_ATTR_KEY)){
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
            return Arrays.asList(LO_DESCRIPTION_ATTR_KEY, LO_CLU_CODE_ATTR_KEY);
        }

        public String getItemAttribute(String id, String attrkey) {
            Result r = loResultMap.get(id);

            if (attrkey.equals(LO_DESCRIPTION_ATTR_KEY)){
                return r.getResultCells().get(3).getValue(); 
            }
            else if (attrkey.equals(LO_CLU_CODE_ATTR_KEY)){
                return r.getResultCells().get(2).getValue(); 
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
            return ((Result)loResultMap.get(id)).getResultCells().get(3).getValue();
        }
    }

    public void clear() {

        loSearches.setListItems(searchTypesList);
        searchParamPanel.clear();
        cluPicker.clear();        
    }

    public void show() {
        searchWindow.show();
    }

    public void hide(){
        searchWindow.hide();
    }

    public List<String> getLoSelections() {
        List<String> selected = new ArrayList<String>();                                
        for (String s: loCheckBoxes.getSelectedItems()) {
            selected.add(listItems.getItemText(s));

        }
        return selected;        
    }
}
