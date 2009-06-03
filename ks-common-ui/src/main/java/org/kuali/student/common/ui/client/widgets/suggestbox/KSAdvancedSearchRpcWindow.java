package org.kuali.student.common.ui.client.widgets.suggestbox;

import java.util.List;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSConfirmButtonPanel;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.VerticalPanel;

/** 
 * This wraps the KSAdvancedSearchWidget into a confirm dialog. 
 * 
 * @author Kuali Student Team
 *
 */
public class KSAdvancedSearchRpcWindow implements HasSelectionHandlers<List<String>>{
    private KSModalDialogPanel dialog = new KSModalDialogPanel();
    private VerticalPanel dialogLayout = new VerticalPanel();
    private KSAdvancedSearchRpc advancedSearch;
    private KSConfirmButtonPanel buttonPanel = new KSConfirmButtonPanel();    
    HandlerManager handlers = new HandlerManager(this);
    
    public KSAdvancedSearchRpcWindow(BaseRpcServiceAsync searchService, String searchTypeKey){
        init(searchService, searchTypeKey);
        dialog.setHeader("Advanced Search");        
    }

    public KSAdvancedSearchRpcWindow(BaseRpcServiceAsync searchService, String searchTypeKey, String title){
        init(searchService, searchTypeKey);
        dialog.setHeader(title);
    }

    private void init(BaseRpcServiceAsync searchService, String searchTypeKey){
        advancedSearch = new KSAdvancedSearchRpc(searchService, searchTypeKey);
        
        dialogLayout.add(advancedSearch);
        dialogLayout.add(buttonPanel);
        buttonPanel.addCancelHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                dialog.hide();
            }
        });
                
        dialogLayout.addStyleName(KSStyles.KS_ADVANCED_SEARCH_WINDOW);
        dialog.setWidget(dialogLayout);
        
        buttonPanel.addConfirmHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                List<String> selectedItems = advancedSearch.getSelectedIds();
                if (selectedItems != null && selectedItems.size() > 0){
                    fireSelectEvent(selectedItems);
                }
            }            
        });
    }       
    
    public void show(){
        dialog.show();
    }
    
    public void hide(){
        dialog.hide();
    }
    
    public void setMultipleSelect(boolean enable){
        advancedSearch.setMultipleSelect(enable);
    }
    

    /**
     * @see com.google.gwt.event.logical.shared.HasSelectionHandlers#addSelectionHandler(com.google.gwt.event.logical.shared.SelectionHandler)
     */
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
