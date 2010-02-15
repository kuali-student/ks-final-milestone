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

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.core.search.dto.ResultCell;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class KSAdvancedSearchWindowComponent implements HasSelectionHandlers<List<ResultCell>>{
    private enum SearchMode {
        BASIC, ADVANCED, CUSTOM
    }
    
    //KS Advanced Search Window has 3 reusable areas:
    // - title bar e.g. "Find Organization" title on the left with CANCEL button at the far right
    // - links "Basic", "Advanced", "Custom" 
    // - one or more criteria
    // - SEARCH button on the left of a single field or at the bottom of multiple fields
    // - result table with one or more columns
    // - SELECT button
    private KSLightBox dialog = new KSLightBox();
    private VerticalPanel mainPanel = new VerticalPanel();
    private SimplePanel searchPanel = new SimplePanel();
    
    private KSThinTitleBarComponent titleBar = null;
    
    private final HorizontalPanel searchModeLinksPanel = new HorizontalPanel();  //holds search mode links
    private KSLabel basicSearchLabel = new KSLabel("Basic Search");
    private KSLabel advancedSearchLabel = new KSLabel("Advanced Search");
    private KSLabel customSearchLabel = new KSLabel("Custom Search"); 
    private boolean basicSearchEnabled = true;
    private boolean customSearchEnabled = false;
    private SearchMode selectedSearchMode;

    private KSAdvancedSearchRpcComponent basicSearch;
    private KSAdvancedSearchRpcComponent advancedSearch;
    private HandlerManager handlers = new HandlerManager(this);
    
    //Title Bar with Cancel button
    private final HorizontalPanel titlePanel = new HorizontalPanel();
    private KSLabel titleLabel = new KSLabel();
    private KSButton cancelButton = new KSButton("Cancel"); 
    
	                  

	public KSAdvancedSearchWindowComponent(SearchComponentConfiguration searchConfig){
        init(searchConfig);       
    }
    
    private void init(SearchComponentConfiguration searchConfig){
        
        addSearchModeHandlers();
        
        SelectionHandler<List<ResultCell>> selectionHandler = new SelectionHandler<List<ResultCell>>() {
            @Override
            public void onSelection(SelectionEvent<List<ResultCell>> event) {

                final List<ResultCell> selectedCells = event.getSelectedItem();
                if (selectedCells.size() > 0){
                    fireSelectEvent(event.getSelectedItem());                   
                    hide();
                }                  
            }
        };        
        
        basicSearch = new KSAdvancedSearchRpcComponent(searchConfig, searchConfig.getBasicCriteria());       
        basicSearch.addSelectionHandler(selectionHandler);
        advancedSearch = new KSAdvancedSearchRpcComponent(searchConfig, searchConfig.getAdvancedCriteria());       
        advancedSearch.addSelectionHandler(selectionHandler);


        selectedSearchMode = SearchMode.BASIC;
        setSelectedSearchMode(selectedSearchMode);
        
        titleBar = new KSThinTitleBarComponent(searchConfig.getSearchDialogTitle());
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
    
    public void initTitleBar(String text){
        titlePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
        titleLabel = new KSLabel(text, false);
        titlePanel.add(titleLabel);
        titlePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
        titlePanel.add(cancelButton);
        titlePanel.addStyleName(KSStyles.KS_POPUP_HEADER);  //TODO header should be grey
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
    public HandlerRegistration addSelectionHandler(SelectionHandler<List<ResultCell>> handler) {
        return handlers.addHandler(SelectionEvent.getType(), handler);
    }

    /**
     * @see com.google.gwt.event.shared.HasHandlers#fireEvent(com.google.gwt.event.shared.GwtEvent)
     */
    @Override
    public void fireEvent(GwtEvent<?> event) {
        handlers.fireEvent(event);
    }
    
    private void fireSelectEvent(List<ResultCell> selectedItems){
        SelectionEvent.fire(this, selectedItems);
    }
}
