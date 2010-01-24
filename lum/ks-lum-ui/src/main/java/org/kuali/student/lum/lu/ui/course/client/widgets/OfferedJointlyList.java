package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.List;

import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.UpdatableMultiplicityComposite;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class OfferedJointlyList extends UpdatableMultiplicityComposite {
	
	{
        setAddItemLabel(Application.getApplicationContext().getUILabel(LUConstants.COURSE_GROUP_NAME, "course", "draft", LUConstants.ADD_EXISTING_LABEL_KEY));
        setMinEmptyItems(1);
    }

    KSAdvancedSearchWindow courseSearchWindow;
    LuRpcServiceAsync luServiceAsync = GWT.create(LuRpcService.class);
    boolean loaded = false;
    

	@Override
	public MultiplicityItem getItemDecorator() {
		return null;
	}

	    	
	@Override
	public void onLoad() {
		super.onLoad();
		if (!loaded){
			courseSearchWindow = new KSAdvancedSearchWindow(luServiceAsync, "lu.search.clus","lu.resultColumn.cluId", "Find Course");
			loaded = true;
		}
	}



	@Override
    public Widget createItem() {
        return new OfferedJointlyItem();
    }
	
	
    public Widget generateAddWidget() {
    	Widget addWidget = super.generateAddWidget();
    	addWidget.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        return addWidget;
    }
    
	public class OfferedJointlyItem extends MultiplicityItem implements ModelWidgetBinding<OfferedJointlyItem> {
		VerticalFlowPanel panel;
		KSTextBox course;
		KSLabel search;
		private boolean loaded = false;
					
		String courseId;
		String courseNumber;
		HandlerRegistration handlerRegistration;

		SelectionHandler<List<String>> courseSelectionHandler = new SelectionHandler<List<String>>(){
            public void onSelection(SelectionEvent<List<String>> event) {
                List<String> selected = event.getSelectedItem();
                courseId = selected.get(0);
                luServiceAsync.getClu(courseId, new AsyncCallback<CluInfo>(){

					public void onFailure(Throwable caught) {
						Window.alert("Error retreiving course details for joint clu");
					}

					@Override
					public void onSuccess(CluInfo result) {
						if (result != null){
							String crsDisplay = result.getOfficialIdentifier().getShortName(); 
							course.setText(crsDisplay);
						}
					}
                	
                });
                handlerRegistration.removeHandler();
            }
		};
		
		public OfferedJointlyItem(){
		}
		
		@Override
		public void setItemWidget(Widget itemWidget) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Widget getItemWidget() {
			return this;
		}

		@Override
		protected void onLoad() {
			redraw();
		}


		@Override
		public void clear() {
			if (loaded){
				course.setText("");
			}
		}

		@Override
		public void redraw() {
			if (!loaded){					
				course = new KSTextBox();
				course.setWidth("30em");
				course.setEnabled(false);
				search = new KSLabel("Search");	
				search.addStyleName("KS-Multiplicity-Link-Label");
				panel = new VerticalFlowPanel();
				initWidget(panel);
				panel.add(course);
				panel.add(search);				
				search.addClickHandler(new ClickHandler(){
					public void onClick(ClickEvent event) {
						handlerRegistration = courseSearchWindow.addSelectionHandler(courseSelectionHandler);
						courseSearchWindow.show(); 
					}						
				});
				loaded=true;
			}
		}

		@Override
		public void setModelValue(OfferedJointlyItem widget,
				DataModel model, String path) {
			if (loaded){
				String fieldPath = path + QueryPath.getPathSeparator() + getItemKey()+ QueryPath.getPathSeparator()+"courseId";
				model.set(QueryPath.parse(fieldPath), courseId);					
			}				
		}

		@Override
		public void setWidgetValue(OfferedJointlyItem widget,
				DataModel model, String path) {
			if (loaded){
				String fieldPath = path + QueryPath.getPathSeparator();
				courseId = (String)model.get(fieldPath + getItemKey()+ QueryPath.getPathSeparator()+"courseId");
				course.setText((String)model.get(fieldPath + getItemKey()+ QueryPath.getPathSeparator() + "courseTitle"));
			}								
		}  			
	}
}
