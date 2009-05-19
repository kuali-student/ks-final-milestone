package org.kuali.student.common.ui.client.widgets.suggestbox;

import java.util.List;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSConfirmButtonPanel;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.VerticalPanel;

/** 
 * This wraps the KSAdvancedSearchWidget into a confirm dialog. 
 * 
 * @author Kuali Student Team
 *
 */
public class KSAdvancedSearchRpcWindow {
    private KSModalDialogPanel dialog = new KSModalDialogPanel();
    private VerticalPanel dialogLayout = new VerticalPanel();
    private KSAdvancedSearchRpc advancedSearch;
    private KSConfirmButtonPanel buttonPanel = new KSConfirmButtonPanel();    
    
    public KSAdvancedSearchRpcWindow(BaseRpcServiceAsync searchService, String searchTypeKey){
        init(searchService, searchTypeKey);        
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
        dialog.setHeader("Advanced Search");
        dialog.setWidget(dialogLayout);        
    }       

    public void addConfirmHandler(ClickHandler handler){
        buttonPanel.addConfirmHandler(handler);
    }
    
    public void show(){
        dialog.show();
    }
    
    public void hide(){
        dialog.hide();
    }
    
    public List<String> getSelectedIds(){
        return advancedSearch.getSelectedIds();
    }           
}
