package org.kuali.student.common.ui.client.widgets.suggestbox;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSThinTitleBar;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.VerticalPanel;

public class KSAdvancedSearchWindow implements HasSelectionHandlers<List<String>>{
    final static ApplicationContext context = Application.getApplicationContext();
    private KSLightBox dialog = new KSLightBox();
    private KSThinTitleBar titleBar = new KSThinTitleBar(context.getMessage("advSearch"));
    private VerticalPanel mainPanel = new VerticalPanel();
    private KSAdvancedSearchRpc advancedSearch;
    private HandlerManager handlers = new HandlerManager(this);
    private boolean resetOnClose = true;
    private ConfirmCancelGroup buttonPanel = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

        @Override
        public void exec(ConfirmCancelEnum result) {
            switch(result){
                case CONFIRM:
                    List<String> selectedItems = advancedSearch.getSelectedIds();
                    if (selectedItems != null && selectedItems.size() > 0){
                        fireSelectEvent(selectedItems);
                    }
                    if(resetOnClose){
                        //advancedSearch.reset();
                    }
                    dialog.hide();
                    break;
                case CANCEL:
                    dialog.hide();
                    break;
            }
        }
    });
    
    public KSAdvancedSearchWindow(BaseRpcServiceAsync searchService, String searchTypeKey){
        init(searchService, searchTypeKey);       
    }

    public KSAdvancedSearchWindow(BaseRpcServiceAsync searchService, String searchTypeKey, String title){
        init(searchService, searchTypeKey);
        titleBar.setTitle(title);
    }
    
    private void init(BaseRpcServiceAsync searchService, String searchTypeKey){
        advancedSearch = new KSAdvancedSearchRpc(searchService, searchTypeKey);
        mainPanel.add(titleBar);
        mainPanel.add(advancedSearch);
        mainPanel.add(buttonPanel);
        dialog.setWidget(mainPanel);
    }
    
    public void show(){
        advancedSearch.reset();
        dialog.show();
    }
    
    public void hide(){
        dialog.hide();
    }
    
    public void setMultipleSelect(boolean enable){
        advancedSearch.setMultipleSelect(enable);
    }
    
    public void setResetOnClose(boolean enable){
        resetOnClose = enable;
    }
    
    public boolean isResetOnClose(){
        return resetOnClose;
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
    
    private void fireSelectEvent(List<String> selectedItems){
        SelectionEvent.fire(this, selectedItems);
    }
}
