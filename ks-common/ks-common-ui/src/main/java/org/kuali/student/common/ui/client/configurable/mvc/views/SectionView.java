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

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.ModelDefinition;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.mvc.*;

import java.util.ArrayList;
import java.util.List;


/**
 * A view implementation of a section.  A section view is used to add sections as views to a controller.
 * 
 * @author Kuali Student
 *
 */
public abstract class SectionView extends BaseSection implements View {

    protected String modelId;
    protected DataModel model;

    private Enum<?> viewEnum;
    private String viewName;

    private List<View> views = new ArrayList<View>();

    /**
     * @param viewEnum Enumeration of this view - id used for navigation, history, and showing a view
     * @param viewName Name of this view - what this view is called in the breadcrumb
     */
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

    public void setViewEnum(Enum<?> viewEnum) {
        this.viewEnum = viewEnum;
    }


    /**
     * Called by controller before the view is displayed to allow lazy initialization or any other preparatory work to be
     * done.
     * In SectionView, the section is cleared of all validation errors, the model is requested from its parent
     * controller, the widgets are updated with the latest data, and beforeShow is called on all of its potential child
     * views.
     */
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {

        super.clearValidationErrors();
        
        if (getController() != null) {
            getController().requestModel(modelId, new ModelRequestCallback<DataModel>() {

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
                ((SectionView) section).beforeShow(new Callback<Boolean>() {
                    @Override
                    public void exec(Boolean result) {
                    }
                });
            }
        }
        for (View view : views) {
            view.beforeShow(Controller.NO_OP_CALLBACK);
        }

    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
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

    public void setName(String name) {
        this.viewName = name;
    }

    public void setController(Controller controller) {
        if (controller instanceof LayoutController) {
            super.setLayoutController((LayoutController) controller);
        } else {
            throw new IllegalArgumentException("Configurable UI sections require a LayoutController, not a base MVC controller");
        }
    }

    /**
     * Update the fields on the screen with the model received back from requestModel on the parent controller
     */
    public void updateView() {
        getController().requestModel(modelId, new ModelRequestCallback<DataModel>() {
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

    /**
     * Force an update of fields on this section with the model passed in
     * @param m
     */
    public void updateView(DataModel m) {
        this.model = m;
        updateWidgetData(m);
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.View#asWidget()
     */
    public Widget asWidget() {
        return this.getLayout();
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.history.HistorySupport#collectHistory(java.lang.String)
     */
    @Override
    public String collectHistory(String historyStack) {
        return null;
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.history.HistorySupport#onHistoryEvent(java.lang.String)
     */
    @Override
    public void onHistoryEvent(String historyStack) {

    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.breadcrumb.BreadcrumbSupport#collectBreadcrumbNames(java.util.List)
     */
    @Override
    public void collectBreadcrumbNames(List<String> names) {
        names.add(this.getName());
    }

    /**
     * Add a view as a widget to this section
     * @param view
     */
    public void addView(View view) {
        views.add(view);
        addWidget(view.asWidget());
    }

    public DataModel getModel() {
        return model;
    }

    public void updateMetadata(ModelDefinition modelDefinition) {
        updateMetadata(modelDefinition, this);
    }

    private void updateMetadata(ModelDefinition modelDefinition, Section topSection) {
        for (Section section : topSection.getSections()) {
            updateMetadata(modelDefinition, section);
        }
        for (FieldDescriptor field : topSection.getFields()) {
            Metadata newMetadata = modelDefinition.getMetadata(field.getFieldKey());
            if (newMetadata != null) {
                field.setMetadata(newMetadata);
            }
        }
    }

    @Override
    public String toString() {
        return viewName;
    }
    
    public boolean isExportButtonActive() {
        return false;
    }

	@Override
	public void showExport(boolean show) {
		// TODO Auto-generated method stub
		
	}
}