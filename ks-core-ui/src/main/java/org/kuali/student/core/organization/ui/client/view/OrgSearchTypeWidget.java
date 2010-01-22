package org.kuali.student.core.organization.ui.client.view;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSThinTitleBar;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchRpc;
import org.kuali.student.core.organization.ui.client.view.searchwidget.OrgAdvancedSearchRpc;
import org.kuali.student.core.organization.ui.client.view.searchwidget.OrgThinTitleBar;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.gen2.table.client.CellRenderer;
import com.google.gwt.gen2.table.client.ColumnDefinition;
import com.google.gwt.gen2.table.client.DefaultCellRenderer;
import com.google.gwt.gen2.table.client.RowRenderer;
import com.google.gwt.gen2.table.client.TableDefinition;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class OrgSearchTypeWidget implements HasSelectionHandlers<List<String>> {
    private enum SearchMode {
        BASIC, ADVANCED, CUSTOM
    }
    
    //KS Advanced Search Window has 3 reusable areas:
    // - title bar e.g. "Find Organization" title on the left with CANCEL button at the far right
    // - links "Basic", "Advanced", "Custom" 
    // - one or more fields
    // - SEARCH button on the left of a single field or at the bottom of multiple fields
    // - result table with one or more columns
    // - paging through search results (if result has too many rows to display on a single page)
    // - SELECT button
    private KSLightBox dialog = new KSLightBox();
    private VerticalPanel mainPanel = new VerticalPanel();
    private SimplePanel searchPanel = new SimplePanel();
    
    private OrgThinTitleBar titleBar = null;
    
    private final HorizontalPanel searchModeLinksPanel = new HorizontalPanel();  //holds search mode links
    private KSLabel basicSearchLabel = new KSLabel("Basic Search");
    private KSLabel advancedSearchLabel = new KSLabel("Advanced Search");
    private KSLabel customSearchLabel = new KSLabel("Custom Search"); 
    private boolean basicSearchEnabled = true;
    private boolean customSearchEnabled = true;
    private SearchMode selectedSearchMode;

    // the class name is better called search rpc
    private OrgAdvancedSearchRpc advancedSearch;
    private OrgAdvancedSearchRpc basicSearch;
    private HandlerManager handlers = new HandlerManager(this);
    
    /*
    private ConfirmCancelGroup buttonPanel = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

        @Override
        public void exec(ConfirmCancelEnum result) {
            switch(result){
                case CONFIRM:
                    List<String> selectedItems = advancedSearch.getSelectedIds();
                    if (selectedItems != null && selectedItems.size() > 0){
                        fireSelectEvent(selectedItems);
                    }
                    dialog.hide();
                    break;
                case CANCEL:
                    dialog.hide();
                    break;
            }
        }
    }); */
    
    public OrgSearchTypeWidget(
            BaseRpcServiceAsync basicSearchService, 
            String basicSearchTypeKey, 
            String basicSearchResultIdKey,
            BaseRpcServiceAsync advancedSearchService,
            String advancedSearchTypeKey,
            String advancedSearchResultIdKey){
        init(basicSearchService, basicSearchTypeKey, basicSearchResultIdKey,
                advancedSearchService, advancedSearchTypeKey, advancedSearchResultIdKey);       
    }    
    
    public OrgSearchTypeWidget(
            BaseRpcServiceAsync basicSearchService, 
            String basicSearchTypeKey, 
            String basicSearchResultIdKey,
            BaseRpcServiceAsync advancedSearchService,
            String advancedSearchTypeKey,
            String advancedSearchResultIdKey,
            boolean basicSearchEnabled, boolean customSearchEnabled){
        this.basicSearchEnabled = basicSearchEnabled;
        this.customSearchEnabled = customSearchEnabled;
        init(basicSearchService, basicSearchTypeKey, basicSearchResultIdKey,
                advancedSearchService, advancedSearchTypeKey, advancedSearchResultIdKey);       
    }

    public OrgSearchTypeWidget(
            BaseRpcServiceAsync basicSearchService, 
            String basicSearchTypeKey, 
            String basicSearchResultIdKey,
            BaseRpcServiceAsync advancedSearchService,
            String advancedSearchTypeKey,
            String advancedSearchResultIdKey,
            String title){
        init(basicSearchService, basicSearchTypeKey, basicSearchResultIdKey,
                advancedSearchService, advancedSearchTypeKey, advancedSearchResultIdKey);       
        titleBar.setTitle(title);
    }
    
    private void init(
            BaseRpcServiceAsync basicSearchService, 
            String basicSearchTypeKey, 
            String basicSearchResultIdKey,
            BaseRpcServiceAsync advancedSearchService,
            String advancedSearchTypeKey,
            String advancedSearchResultIdKey
            ){
        
        addSearchModeHandlers();
        
        RowRenderer<ResultRow> rowRenderer = new RowRenderer<ResultRow>() {
            @Override
            public void renderRowValue(ResultRow rowValue, TableDefinition.AbstractRowView<ResultRow> view) {
                view.setStyleAttribute("background", "white");
                view.setStyleAttribute("border", "none");
            }
        };
        CellRenderer<ResultRow, String> cellRenderer = new SearchCellRenderer<ResultRow, String>();
        SelectionHandler<List<String>> selectionHandler = new SelectionHandler<List<String>>() {
            @Override
            public void onSelection(SelectionEvent<List<String>> event) {
                fireSelectEvent(event.getSelectedItem());
            }
        }; 
        advancedSearch = new OrgAdvancedSearchRpc(
                advancedSearchService, advancedSearchTypeKey, advancedSearchResultIdKey,
                "KS-Button-Tight-Button", rowRenderer, cellRenderer);
        advancedSearch.addSelectionHandler(selectionHandler);
        // "org.search.orgQuickViewByHierarchyShortName", "org.resultColumn.orgId", "Find Organization");
        basicSearch = new OrgAdvancedSearchRpc(basicSearchService, 
                basicSearchTypeKey, basicSearchResultIdKey,
                "KS-Button-Tight-Button", rowRenderer, cellRenderer);
        basicSearch.addSelectionHandler(selectionHandler);

        selectedSearchMode = SearchMode.BASIC;
        setSelectedSearchMode(selectedSearchMode);
        
        titleBar = new OrgThinTitleBar(Application.getApplicationContext().getMessage("advSearch"));
        titleBar.addCancelButtonHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        mainPanel.add(titleBar);
        
        //add links to different mode of search (basic, advanced, custom)
        searchModeLinksPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
        searchModeLinksPanel.setSpacing(10);
        if (basicSearchEnabled) {
            searchModeLinksPanel.add(basicSearchLabel);          
        }
        searchModeLinksPanel.add(advancedSearchLabel);        
        if (customSearchEnabled) {
            searchModeLinksPanel.add(customSearchLabel);         
        }
        mainPanel.add(searchModeLinksPanel);        
        mainPanel.add(searchPanel);
//        mainPanel.add(advancedSearch);
      //  mainPanel.add(buttonPanel);

        dialog.setWidget(mainPanel);
    }
    
    private void addSearchModeHandlers() {
        basicSearchLabel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                selectedSearchMode = SearchMode.BASIC;
                setSelectedSearchMode(selectedSearchMode);
            }
        });
        advancedSearchLabel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                selectedSearchMode = SearchMode.ADVANCED;
                setSelectedSearchMode(selectedSearchMode);
            }
        });
        customSearchLabel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                selectedSearchMode = SearchMode.CUSTOM;
                setSelectedSearchMode(selectedSearchMode);
            }
        });
    }
    
    private void setSelectedSearchMode(SearchMode mode) {
        if (mode == SearchMode.BASIC) {
            basicSearchLabel.setStyleName("action-selected");
            advancedSearchLabel.setStyleName("action");
            customSearchLabel.setStyleName("action");
            searchPanel.setWidget(basicSearch);
        } else if (mode == SearchMode.ADVANCED) {
            basicSearchLabel.setStyleName("action");
            advancedSearchLabel.setStyleName("action-selected");
            customSearchLabel.setStyleName("action");
            searchPanel.setWidget(advancedSearch);
        } else if (mode == SearchMode.CUSTOM) {
            basicSearchLabel.setStyleName("action");
            advancedSearchLabel.setStyleName("action");
            customSearchLabel.setStyleName("action-selected");
            searchPanel.setWidget(advancedSearch);
        }
    }
    
    public void show(){
    //    advancedSearch.reset();
        dialog.show();
    }
    
    public void hide(){
        dialog.hide();
    }
    
    //TODO re-enable this
/*    public void setMultipleSelect(boolean enable){
        advancedSearch.setMultipleSelect(enable);
    }*/
    
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
    
    private void fireSelectEvent(List<String> selectedItems){
        SelectionEvent.fire(this, selectedItems);
    }

}

class SearchCellRenderer<RowType, ColType> extends DefaultCellRenderer<RowType, ColType> {
    @Override
    public void renderRowValue(RowType rowValue,
            ColumnDefinition<RowType, ColType> columnDef,
            TableDefinition.AbstractCellView<RowType> view) {
        Object cellValue = columnDef.getCellValue(rowValue);
        if (cellValue == null) {
            view.setText("");
        } else if (cellValue instanceof Widget) {
            view.setWidget((Widget) cellValue);
        } else {
            view.setText(cellValue.toString());
            view.setStyleAttribute("border", "0px");
            view.setStyleAttribute("borderRight", "5px solid white");
        }
    }
}
