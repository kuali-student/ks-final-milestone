package org.kuali.student.ui.personidentity.client.view.lu;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelBinding;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeEvent;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListener;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListenerProxy;
import org.kuali.student.commons.ui.widgets.tables.ModelTableSelectionListener;
import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;
import org.kuali.student.ui.personidentity.client.model.lu.LuModelState;
import org.kuali.student.ui.personidentity.client.view.AdminEditPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CourseSearchResultPanel extends Composite{
    final VerticalPanel panel = new VerticalPanel();
    final CourseSearchResultTable table = new CourseSearchResultTable();
    ModelBinding<GwtLuiInfo> binding = null;
    Label	lSearchText = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("searchInstructions"));
    boolean loaded = false;
    /*
	private PropertyChangeListener listener  
	= new PropertyChangeListenerProxy(
            "searchResult",
            new PropertyChangeListener() {
                public void propertyChange(
                    PropertyChangeEvent propertyChangeEvent) {
                    //updateView( (List<GwtPersonInfo>)propertyChangeEvent.getNewValue());
                	updateView( LuModelState.getInstance().getSearchResults());
                }
            });
*/
	public CourseSearchResultPanel(){
		lSearchText.addStyleName("KS-Label");
		lSearchText.addStyleName("KS-Search-Message");
		
		panel.add(lSearchText);
		panel.add(table);
		table.setVisible(false);
		
		super.initWidget(panel);
	}
	
	public void onLoad() {
        if (!loaded) {
            loaded = true;
            Model<GwtLuiInfo> model = (Model<GwtLuiInfo>) MVC.findParentController(this).getModel(GwtLuiInfo.class);
            binding = new ModelBinding<GwtLuiInfo>(model, table);
            
            table.addSelectionListener(new ModelTableSelectionListener<GwtLuiInfo>() {
                public void onSelect(GwtLuiInfo modelObject) {
                    LuModelState.getInstance().setCurrLui(modelObject);
                    if (modelObject == null) {
                        LearningUnitController.displayCourseSearchResults();
                    } else {
                        LearningUnitController.displayCourseDetails();
                    }
                }
            });
        }
    }
	
	public void setSearchLabel(String text)
	{
		lSearchText.setText(text);
	}
	
	public void setTableVisibility(boolean visible)
	{
		table.setVisible(visible);
	}
	
	/*
	public CourseSearchResultPanel(List<GwtLuiInfo> list){
		init();
		updateView(list);
	}
	
	protected void init(){
		LuModelState.getInstance().addPropertyChangeListener(listener);
	}
	*/
	
	/*
	public void updateView(List<GwtLuiInfo> list){
		this.clear();				
		for(GwtLuiInfo pInfo: list){			
			this.add(new LuSearchDetailsWidget(pInfo));
		}
	}
	*/
}
