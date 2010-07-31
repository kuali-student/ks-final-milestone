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
package org.kuali.student.common.ui.client.widgets.selectors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDatePickerAbstract;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.SearchResultListItems;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.common.ui.client.widgets.searchtable.SearchBackedTable;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.search.dto.QueryParamInfo;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * This a search widget that builds a query form using parameters defined in a 
 * search type defined in a search service and displays results of said query
 * by calling the search service methods. Adding a select click handler to this
 * widget will result in a select button being displayed in the result tab.
 * 
 * @author Kuali Student Team
 *
 */
public class KSAdvancedSearchRpcComponent extends Composite implements HasSelectionHandlers<List<ResultCell>>{
    private VerticalPanel tabPanel = new VerticalPanel();
    private VerticalPanel searchLayout = new VerticalPanel();
    private VerticalPanel resultLayout = new VerticalPanel();
    //private KSSelectableTableList searchResults = new KSSelectableTableList();
    private SearchBackedTable searchResultsTable;
    private KSLabel resultLabel = new KSLabel("No Search Results");
    private KSButton selectButton = new KSButton("Select");
    private boolean hasSelectionHandlers = false;
    //private SearchResultListItems searchResultList = new SearchResultListItems();
    private List<KSTextBox> textBoxes = new ArrayList<KSTextBox>();
    private List<KSDatePickerAbstract> datePickers = new ArrayList<KSDatePickerAbstract>();
    private List<KSSelectItemWidgetAbstract> selectableEnums = new ArrayList<KSSelectItemWidgetAbstract>();
    private Map<KSDatePickerAbstract, String> datePickerCriteriaKeys = new HashMap<KSDatePickerAbstract, String>(); 
    private FlexTable searchParamTable = new FlexTable();
    
    private List<String> searchCriteria;  //which criteria will be shown in the UI search screen
    private SearchComponentConfiguration searchConfig;    
    
    public KSAdvancedSearchRpcComponent(final SearchComponentConfiguration searchConfig, List<String> searchCriteria)       
    {
        this.searchCriteria = searchCriteria;
        this.searchConfig = searchConfig;
        
        searchResultsTable = new SearchBackedTable(searchConfig.getSearchService(), searchConfig.getSearchTypeKey(), searchConfig.getResultIdKey());
        generateSearchLayout();
               
        tabPanel.setSpacing(10);
        tabPanel.add(searchLayout);
        tabPanel.add(resultLayout);
        
        resultLayout.addStyleName(KSStyles.KS_ADVANCED_SEARCH_RESULTS_PANEL);
//        searchLayout.addStyleName(KSStyles.KS_ADVANCED_SEARCH_PANEL);
        searchLayout.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        tabPanel.addStyleName(KSStyles.KS_ADVANCED_SEARCH_TAB_PANEL);
        
        //on selection, return back a list of values that we want to display and also
        //a list of keys (ids) for each record
        selectButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
            	List<ResultCell> selectedValues = new ArrayList<ResultCell>(); 
                for(ResultRow row: searchResultsTable.getSelectedRows()){
                	ResultCell cell = new ResultCell();
                	cell.setKey(row.getId());
                	cell.setValue(row.getValue(searchConfig.getRetrievedColumnKey()));
                	selectedValues.add(cell);
                }                
                
                if (selectedValues.size() > 0){
                    fireSelectEvent(selectedValues);
                }                
            }            
        });
        initWidget(tabPanel);
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
                	if (!searchCriteria.contains(q.getKey())) {
                		continue;
                	}
                	
                   FieldDescriptor fd = q.getFieldDescriptor();
                    //TODO change this to use a message from messages, using the fd.getName() as the token
                    KSLabel paramLabel = new KSLabel(fd.getName());
//                    paramLabel.addStyleName(KSStyles.KS_ADVANCED_SEARCH_PARAM_LABELS);
                    searchParamTable.getColumnFormatter().addStyleName(0, KSStyles.KS_ADVANCED_SEARCH_PARAM_LABEL_COLUMN);
//                    searchParamTable.getCellFormatter().addStyleName(row, column, KSStyles.KS_ADVANCED_SEARCH_PARAM_LABEL_CELLS);
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
                //TODO: Messages
                KSButton searchButton = new KSButton("Search");
                searchButton.addClickHandler(new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        executeSearch();
                    }            
                });
                searchParamTable.setWidget(row, column, searchButton);
                searchLayout.add(searchParamTable);     
                
                //Initialze Results Layout
/*                SearchResultTypeInfo resultTypeInfo = searchTypeInfo.getSearchResultTypeInfo();
                searchResultList.setResultColumns(resultTypeInfo.getResultColumns());
*/
                FlexTable resultTable = new FlexTable();
                resultLabel.addStyleName(KSStyles.KS_ADVANCED_SEARCH_RESULTS_LABEL);
                resultTable.setWidget(0, 0, resultLabel);
//                searchResultsTable.getElement().getStyle().setProperty("backgroundColor", "red");
                resultTable.setWidget(1, 0, searchResultsTable);
                resultLayout.add(resultTable);

                //Add select button, this will only be visible if a select handler is directly added
                //to this widget.
                resultLayout.add(selectButton);
//                selectButton.setVisible(hasSelectionHandlers);
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
    public List<String> getSelectedIds(){
        return searchResultsTable.getSelectedIds();
    }
           
    private void executeSearch(){
        //Build query parameters
        List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>();
        for (int row=0; row < searchParamTable.getRowCount()-1; row++ ){
            Widget w = searchParamTable.getWidget(row, 1);
            
            QueryParamValue queryParamValue = new QueryParamValue();
            if (w instanceof HasName) {
                queryParamValue.setKey(((HasName)w).getName());
            } else if (w instanceof KSDatePickerAbstract) {
                queryParamValue.setKey(datePickerCriteriaKeys.get(w));
            }
            
            if (w instanceof KSSelectItemWidgetAbstract){
                queryParamValue.setValue(((KSSelectItemWidgetAbstract)w).getSelectedItem());
                System.out.println(((KSSelectItemWidgetAbstract)w).getSelectedItem());
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
                value = value.replace('*','%');  //handle special characters TODO: remove '*' completely
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
        
        //execute the search itself
        searchResultsTable.clearTable();
        searchResultsTable.performSearch(queryParamValues);
        
//        tabPanel.selectTab(1);
       
        //Call the search service
       /* searchService.searchForResults(searchTypeKey, queryParamValues, new AsyncCallback<List<Result>>(){

            @Override
            public void onFailure(Throwable caught) {
                //TODO: Handle this failure
            }

            @Override
            public void onSuccess(List<Result> result) {
                if (result != null){
                    resultLabel.setVisible(false);
                    searchResults.setVisible(true);
                    searchResultList.setResults(result);
                    searchResults.redraw();
                    selectButton.setVisible(hasSelectionHandlers);
                } else{
                    resultLabel.setVisible(true);
                    searchResults.setVisible(false);
                    selectButton.setVisible(false);
                }
                tabPanel.selectTab(1);
            }               
        });*/
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


    /**
     * Use to add a selection handler to this widget. Adding a selection handler will display a select button
     * in results tab.
     * 
     * @see com.google.gwt.event.logical.shared.HasSelectionHandlers#addSelectionHandler(com.google.gwt.event.logical.shared.SelectionHandler)
     */
    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<List<ResultCell>> handler) {
        this.hasSelectionHandlers = true;
        return addHandler(handler,SelectionEvent.getType());
    }
    
    private void fireSelectEvent(List<ResultCell> selectedItems){
        SelectionEvent.fire(this, selectedItems);
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
        searchResultsTable.clearTable();
//        tabPanel.selectTab(0);
    }
}
