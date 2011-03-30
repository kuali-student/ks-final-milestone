package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel.TabPanelStyle;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A layout controller which provides tabs for the views added through the addTab method.  If the user
 * clicks on a tab the view is swapped.
 * 
 * @author Kuali Student Team
 *
 */
public class TabMenuController extends LayoutController{

	protected KSTabPanel tabPanel = new KSTabPanel(TabPanelStyle.SMALL);
	protected FlowPanel layout = new FlowPanel();
	protected KSDocumentHeader header = new KSDocumentHeader(false);
	
	public TabMenuController(String controllerId) {
		super();
		header.addStyleName("tabLayout-ContentHeader");
		layout.add(header);
		header.setVisible(false);
		layout.add(tabPanel);
		this.initWidget(layout);
	}
	
	/**
	 * Do not use in combination with setContentTitle
	 */
	public void setBasicTitle(String titleText){
		layout.insert(SectionTitle.generateH1Title(titleText), 0);
	}
	
    public void setContentTitle(String title){
        header.setTitle(title);
        header.setVisible(true);
    }

    public void addContentWidget(Widget w){
        header.addWidget(w);
        header.setVisible(true);
    }

    public void setContentInfo(String info){
        header.getInfoLabel().setHTML(info);
        header.getInfoLabel().removeStyleName("content-warning");
        header.getInfoLabel().addStyleName("content-info");
    }

    public void setContentWarning(String info){
        header.getInfoLabel().setHTML(info);
        header.getInfoLabel().removeStyleName("content-info");
        header.getInfoLabel().addStyleName("content-warning");

    }
    
    public void showPrint(boolean show){
    	header.showPrint(show);
    }

    public void showExport(boolean show) {
        header.showJasper(show);
    }
	/** 
	 * This version of updateModel only updates from the currentView (since only one view is shown/accessed at a time).  
	 * Call updateModelFromView to update from a specific view under this controller's scope.
	 */
	@Override
	public void updateModel() {
		this.updateModelFromCurrentView();
	}

	@Override
	protected void hideView(View view) {
		//Dont need to do anything special here
	}

	@Override
	protected void renderView(View view) {
		//tabPanel.selectTab(view.getViewEnum().toString());
	}
	
	@Override
	public void showDefaultView(Callback<Boolean> onReadyCallback) {
		super.showDefaultView(onReadyCallback);
		if(this.defaultView != null){
			tabPanel.selectTab(this.defaultView.toString());
		}
	}
	
	public void addTab(final View view, String tabName){
		super.addView(view);
		tabPanel.addTab(view.getViewEnum().toString(), tabName, view.asWidget());
		tabPanel.addTabCustomCallback(view.getViewEnum().toString(), new Callback<String>(){
			@Override
			public void exec(String result) {
				HistoryManager.setLogNavigationHistory(false);
				showView(view.getViewEnum());
			}
		});
	}

    @Override
    public ArrayList<ExportElement> getExportElementsFromView() {
        return super.getExportElementsFromView();
        
    }
}
