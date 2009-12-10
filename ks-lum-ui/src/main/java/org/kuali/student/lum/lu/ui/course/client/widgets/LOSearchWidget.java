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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDatePickerAbstract;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.SearchResultListItems;
import org.kuali.student.common.ui.client.widgets.selectors.SearchComponentConfiguration;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.search.dto.QueryParamInfo;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;

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
 * search type defined in a search service. Results are handled in the controller widget
 * 
 * FIXME: This is a temporary solution until the generic search widget is available.
 * 
 * @author Kuali Student Team
 *
 */
public class LOSearchWidget extends Composite implements HasSelectionHandlers<List<ResultCell>>{
    private VerticalPanel main = new VerticalPanel();
    private VerticalPanel searchLayout = new VerticalPanel();
    private KSLabel resultLabel = new KSLabel("No Search Results");
    private boolean hasSelectionHandlers = false;
    private List<KSTextBox> textBoxes = new ArrayList<KSTextBox>();
    private List<KSDatePickerAbstract> datePickers = new ArrayList<KSDatePickerAbstract>();
    private List<KSSelectItemWidgetAbstract> selectableEnums = new ArrayList<KSSelectItemWidgetAbstract>();
    private Map<KSDatePickerAbstract, String> datePickerCriteriaKeys = new HashMap<KSDatePickerAbstract, String>(); 
    private FlexTable searchParamTable = new FlexTable();
    
    private List<String> searchCriteria;  //which criteria will be shown in the UI search screen
    private SearchComponentConfiguration searchConfig;    
    
    private boolean ignoreCase = false;
    private boolean partialMatch = false;
 
    
    public LOSearchWidget(final SearchComponentConfiguration searchConfig, List<String> searchCriteria)       
    {
        this.searchCriteria = searchCriteria;
        this.searchConfig = searchConfig;
        
        generateSearchLayout();
               
//        main.setSpacing(10);
        main.add(searchLayout);
        searchLayout.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
//        main.addStyleName(KSStyles.KS_ADVANCED_SEARCH_TAB_PANEL);
        
        initWidget(main);
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
                searchLayout.add(searchParamTable);     
                
                //Initialze Results Layout
/*                SearchResultTypeInfo resultTypeInfo = searchTypeInfo.getSearchResultTypeInfo();
                searchResultList.setResultColumns(resultTypeInfo.getResultColumns());
*/
//                FlexTable resultTable = new FlexTable();
//                resultLabel.addStyleName(KSStyles.KS_ADVANCED_SEARCH_RESULTS_LABEL);
//                resultTable.setWidget(0, 0, resultLabel);
////                searchResultsTable.getElement().getStyle().setProperty("backgroundColor", "red");
//                resultTable.setWidget(1, 0, searchResultsTable);
//                resultLayout.add(resultTable);
//
//                //Add select button, this will only be visible if a select handler is directly added
//                //to this widget.
//                resultLayout.add(selectButton);
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
//    public List<String> getSelectedIds(){
//        return searchResultsTable.getSelectedIds();
//    }
           
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
    }
    
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public void setPartialMatch(boolean partialMatch) {
        this.partialMatch = partialMatch;
    }
}
