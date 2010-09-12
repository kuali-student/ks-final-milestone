package org.kuali.student.common.ui.client.widgets.search;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSThinTitleBar;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

public class AdvancedSearchWindow {
	private KSLightBox dialog = new KSLightBox();
	private KSThinTitleBar titleBar = null;
	private List<Callback<List<String>>> callbacks = new ArrayList<Callback<List<String>>>();
	private SearchPanel searchPanel;
	private VerticalFlowPanel layout = new VerticalFlowPanel();
	
    private ConfirmCancelGroup buttonPanel = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

        @Override
        public void exec(ConfirmCancelEnum result) {
            switch(result){
                case CONFIRM:
                    List<String> selectedItems = searchPanel.getSelectedIds();
                    for(Callback<List<String>> callback: callbacks){
                    	callback.exec(selectedItems);
                    }
                    dialog.hide();
                    break;
                case CANCEL:
                    dialog.hide();
                    break;
            }
        }
    });
	
	public AdvancedSearchWindow(String fieldMessageKey, SearchPanel panel){
		searchPanel = panel;
		//TODO use messages here
		titleBar = new KSThinTitleBar("Find " + fieldMessageKey);
		layout.add(titleBar);
		layout.add(panel);
		layout.add(buttonPanel);
		dialog.setWidget(layout);
	}
	
	public void addSelectionCompleteCallback(Callback<List<String>> callback){
		callbacks.add(callback);
	}
	
	public void show(){
		//TODO Reset panel here??? Also how much reseting
		
		dialog.show();
	}
}
