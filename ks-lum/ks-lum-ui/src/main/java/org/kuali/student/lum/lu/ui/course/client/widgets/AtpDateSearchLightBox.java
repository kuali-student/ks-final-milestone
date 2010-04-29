package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDatePickerAbstract;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSRadioButtonList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SearchResultListItems;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.organization.ui.client.view.searchwidget.OrgThinTitleBar;
import org.kuali.student.core.search.dto.QueryParamInfo;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.core.search.dto.SearchTypeInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AtpDateSearchLightBox implements HasSelectionHandlers<List<String>> {

    private KSLightBox dialog;
    private HandlerManager handlers;
    private KSRadioButtonList selections;
    private OrgThinTitleBar titleBar = null;
    private BaseRpcServiceAsync searchService;
    private String searchTypeKey;
    private String resultIdKey;
    private String displayKey;
    private FlexTable searchParamTable = new FlexTable();
    private List<KSSelectItemWidgetAbstract> selectableEnums = new ArrayList<KSSelectItemWidgetAbstract>();
    private List<KSDatePickerAbstract> datePickers = new ArrayList<KSDatePickerAbstract>();
    private Map<KSDatePickerAbstract, String> datePickerCriteriaKeys = new HashMap<KSDatePickerAbstract, String>(); 
    private List<KSTextBox> textBoxes = new ArrayList<KSTextBox>();
    private HorizontalPanel searchLayout = new HorizontalPanel();
    private VerticalPanel searchResultsLayout = new VerticalPanel();
    
    public AtpDateSearchLightBox(String title,
            BaseRpcServiceAsync searchService,
            String searchTypeKey,
            String resultIdKey,
            String displayKey) {
        init(title, searchService, searchTypeKey, resultIdKey, displayKey);
    }
    
    private void init(String title,
            BaseRpcServiceAsync searchService,
            String searchTypeKey,
            String resultIdKey,
            String displayKey) {
        final VerticalPanel mainPanel = new VerticalPanel();
        final HorizontalPanel selectButtonPanel = new HorizontalPanel();
        HorizontalPanel horizontalSpacer;
        
        this.searchService = searchService;
        this.searchTypeKey = searchTypeKey;
        this.resultIdKey = resultIdKey;
        this.displayKey = displayKey;
        dialog = new KSLightBox();
        handlers = new HandlerManager(this);
        titleBar = new OrgThinTitleBar(title);
        titleBar.addCancelButtonHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        generateSearchLayout();

        mainPanel.add(titleBar);
        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.setHeight("200px");
        scrollPanel.add(searchLayout);
        mainPanel.add(scrollPanel);
        
        dialog.setWidget(mainPanel);
    }
    
    private void generateSearchLayout() {
        searchService.getSearchType(searchTypeKey, new AsyncCallback<SearchTypeInfo>(){
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error generating search layout: " + caught);
            }
            @Override
            public void onSuccess(SearchTypeInfo searchTypeInfo) {
                int row = 0;
                int column = 0;
                SearchCriteriaTypeInfo criteriaInfo = searchTypeInfo.getSearchCriteriaTypeInfo();
                List<QueryParamInfo> queryParams = criteriaInfo.getQueryParams();
                for (QueryParamInfo q:queryParams){
                    FieldDescriptor fd = q.getFieldDescriptor();
                    KSLabel paramLabel = new KSLabel(fd.getName());
                    searchParamTable.getColumnFormatter().addStyleName(0, KSStyles.KS_ADVANCED_SEARCH_PARAM_LABEL_COLUMN);
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
                KSButton searchButton = new KSButton("Search");
                searchButton.addClickHandler(new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        executeSearch();
                    }            
                });
                searchParamTable.setWidget(row, column, searchButton);
                searchLayout.add(searchParamTable);
                searchLayout.add(searchResultsLayout);
            }
        });
    }

    private void executeSearch(){
        List<SearchParam> queryParamValues = new ArrayList<SearchParam>();
        for (int row=0; row < searchParamTable.getRowCount()-1; row++ ){
            Widget w = searchParamTable.getWidget(row, 1);
            SearchParam queryParamValue = new SearchParam();
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
                value = value.replace('*','%');
                queryParamValue.setValue(value);
            }
            queryParamValues.add(queryParamValue);                
        }
        
        if (selections != null && selections.isAttached()) {
            searchResultsLayout.remove(selections);
        }
        selections = new KSRadioButtonList("Selections");
        
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setParams(queryParamValues);
        searchRequest.setSearchKey(searchTypeKey);
        
        searchService.search(searchRequest, new AsyncCallback<SearchResult>(){
            @Override
            public void onFailure(Throwable caught) {
                System.out.println("AtpDateSearchLightBox Search failed.");
                caught.printStackTrace();
            }
            @Override
            public void onSuccess(SearchResult result) {
                ListItems items = null;
                if (result != null&&result.getRows()!=null) {
                    items = new SelectionItems(result.getRows(), resultIdKey,
                            displayKey);
                    selections.setListItems(items);
                    searchResultsLayout.add(selections);
                } else {
                    searchResultsLayout.add(new KSLabel("Your search returns no results"));
                }
            }        
        });
    }

    private void populateSearchEnumeration(final KSSelectItemWidgetAbstract selectField, String searchType){  
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setParams(null);
        searchRequest.setSearchKey(searchType);
        searchService.search(searchRequest, new AsyncCallback<SearchResult>(){

            @Override
            public void onFailure(Throwable caught) {
                System.out.println("Failed to search for results to enum");
                caught.printStackTrace();                
            }

            @Override
            public void onSuccess(SearchResult result) {
                selectField.setListItems(new SearchResultListItems(result.getRows()));
                selectField.redraw();
            }
            
        });
    } 
    
    private void fireSelectEvent(List<String> selectedItems){
        SelectionEvent.fire(this, selectedItems);
    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<List<String>> handler) {
        return handlers.addHandler(SelectionEvent.getType(), handler);
    }

    /**
     * @see com.google.gwt.event.shared.HasHandlers#fireEvent(com.google.gwt.event.shared.GwtEvent)
     */
    @Override
    public void fireEvent(GwtEvent<?> event) {
        handlers.fireEvent(event);
    }
    
    public void show() {
        dialog.show();
    }
    
    public void hide() {
        dialog.hide();
    }

}

class SelectionItems implements ListItems {
    
    private List<SearchResultRow> results;
    private String resultIdKey;
    private String displayKey;
    
    public SelectionItems(List<SearchResultRow> results,
            String resultIdKey, 
            String displayKey) {
        this.results = results;
        this.resultIdKey = resultIdKey;
        this.displayKey = displayKey;
        setupAttrKeys();
    }
    
    private void setupAttrKeys() {
    }
    
    @Override
    public List<String> getAttrKeys() {
        List<String> attrKeys = null;        
        for (SearchResultRow result : results) {
            for (SearchResultCell resultCell : result.getCells()) {
                attrKeys = (attrKeys == null)? new ArrayList<String>() : attrKeys;
                attrKeys.add(resultCell.getKey());
            }
        }
        return attrKeys;
    }

    @Override
    public String getItemAttribute(String id, String attrkey) {
    	SearchResultRow item = null;
        String itemAttribute = null;
        // go through the results to find the item with the same id
        // as the "id" method argument
        for (SearchResultRow result : results) {
            for (SearchResultCell resultCell : result.getCells()) {
                if (resultCell.getValue().equals(id)) {
                    item = result;
                }
            }
        }
        // retrieve the value of the item found above.
        if (item != null) {
            for (SearchResultCell resultCell : item.getCells()) {
                if (resultCell.getKey().equals(attrkey)) {
                    itemAttribute = resultCell.getValue();
                }
            }
        }
        return itemAttribute;
    }

    @Override
    public int getItemCount() {
        int itemCount = (results == null)? 0 : results.size();
        return itemCount;
    }

    @Override
    public List<String> getItemIds() {
        List<String> itemIds = null;
        if (results != null) {
            for (SearchResultRow result : results) {
                for (SearchResultCell resultCell : result.getCells()) {
                    if (resultCell.getKey().equals(resultIdKey)) {
                        itemIds = (itemIds == null)? new ArrayList<String>() : itemIds;
                        itemIds.add(resultCell.getValue());
                    }
                }
            }
        }
        return itemIds;
    }

    @Override
    public String getItemText(String id) {
        String displayValue = getItemAttribute(id, displayKey);
        return displayValue;
    }
    
}
