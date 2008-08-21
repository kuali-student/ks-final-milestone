package org.kuali.student.ui.personidentity.client.view.lu;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelBinding;
import org.kuali.student.commons.ui.widgets.tables.ModelTableSelectionListener;
import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;
import org.kuali.student.ui.personidentity.client.model.lu.LuModelState;
import org.kuali.student.ui.personidentity.client.view.AdminEditPanel;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class CourseSearchResultPanel extends FlowPanel{
    //final VerticalPanel panel = new VerticalPanel();
    final CourseSearchResultTable table = new CourseSearchResultTable();
    ModelBinding<GwtLuiInfo> binding = null;
    Label	lSearchText = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("searchInstructions"));
    boolean loaded = false;
    
    Controller c = null;
    
    public static class CourseSearchResultPanelEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(CourseSearchResultPanelEvent.class, new CourseSearchResultPanelEvent().getHierarchy());
        }

        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(CourseSearchResultPanelEvent.class);
        }
    }

    static {
        new CourseSearchResultPanelEvent();
    }
    
    
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
		
		this.add(lSearchText);
		this.add(table);
		table.setVisible(false);
		
		
	}
	
	public void onLoad() {
        if (!loaded) {
            loaded = true;
            
            c = MVC.findParentController(this);
            
            Model<GwtLuiInfo> model = (Model<GwtLuiInfo>) c.getModel(GwtLuiInfo.class);
            binding = new ModelBinding<GwtLuiInfo>(model, table);
            
            table.addSelectionListener(new ModelTableSelectionListener<GwtLuiInfo>() {
                public void onSelect(GwtLuiInfo modelObject) {
                    LuModelState.getInstance().setCurrLui(modelObject);
                    if (modelObject == null) {
                        LearningUnitController.displayCourseSearchResults();
                    } else {
                        LearningUnitController.displayCourseDetails();
                        
                        
                        c.getEventDispatcher().fireEvent(CourseSearchResultPanelEvent.class, modelObject);
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
