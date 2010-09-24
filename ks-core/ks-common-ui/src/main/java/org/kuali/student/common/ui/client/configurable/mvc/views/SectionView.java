/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.ui.client.configurable.mvc.views;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;


public abstract class SectionView extends BaseSection implements View{

    protected String modelId;
    protected DataModel model;

    private Enum<?> viewEnum;
    private String viewName;

    public SectionView(Enum<?> viewEnum, String viewName) {
        this.viewEnum = viewEnum;
        this.viewName = viewName;
    }

    /**
     * This method gets view name enumeration
     *
     * @return
     */
    @Override
    public Enum<?> getViewEnum() {
        return viewEnum;
    }

    /**
     * Called by controller before the view is displayed to allow lazy initialization or any other preparatory work to be
     * done.
     */
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
    	
    	super.clearValidation();
        if (getController() != null) {
            getController().requestModel(modelId, new ModelRequestCallback<DataModel>(){

                @Override
                public void onRequestFail(Throwable cause) {
                    Window.alert("Failed to get model: " + getName());
                    onReadyCallback.exec(false);
                }

                @Override
                public void onModelReady(DataModel m) {
                    model = m;
                    updateWidgetData(m);
                    resetFieldInteractionFlags();
                    onReadyCallback.exec(true);
                }

            });
        }

        for (Section section : sections) {
            if (section instanceof SectionView) {
                ((SectionView)section).beforeShow(new Callback<Boolean>() {
                    @Override
                    public void exec(Boolean result) {
                    }
                });
            }
        }

    }

	/**
     * Called by the controller before the view is hidden to allow the view to perform cleanup or request confirmation from
     * the user, etc. Can cancel the action by returning false.
     *
     * @return true if the view can be hidden, or false to cancel the action.
     */
    @Override
    public boolean beforeHide() {
        return true;
    }

    /**
     * Returns the controller associated with the view
     *
     * @see org.kuali.student.common.ui.client.mvc.View#getController()
     */
    @Override
    public Controller getController() {
        return super.getLayoutController();
    }

    /**
     * Returns the view's name
     *
     * @see org.kuali.student.common.ui.client.mvc.View#getName()
     */
    @Override
    public String getName() {
        return viewName;
    }

    public void setController(Controller controller) {
    	if (controller instanceof LayoutController) {
    		super.setLayoutController((LayoutController) controller);
    	} else {
    		throw new IllegalArgumentException("Configurable UI sections require a LayoutController, not a base MVC controller");
    	}
    }

	public void updateView() {
        getController().requestModel(modelId, new ModelRequestCallback<DataModel>(){
            @Override
            public void onModelReady(DataModel m) {
            	// TODO review this, shouldn't it assign this.model = m?
            	SectionView.this.model = m;
                updateWidgetData(m);
            }


            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert("Failed to get model");
            }
        });

	}

	public void updateView(DataModel m) {
		this.model = m;
         updateWidgetData(m);
	}
	
    public Widget asWidget(){
    	return this.getLayout();
    }

	@Override
	public String collectHistory(String historyStack) {
		return null;
	}

	@Override
	public void onHistoryEvent(String historyStack) {
		
	}
	
	@Override
	public void collectBreadcrumbNames(List<String> names) {
		names.add(this.getName());
	}
}
