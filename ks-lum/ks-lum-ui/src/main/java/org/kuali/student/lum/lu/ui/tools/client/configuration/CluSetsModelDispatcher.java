package org.kuali.student.lum.lu.ui.tools.client.configuration;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class CluSetsModelDispatcher extends LayoutController implements ConfigurableLayout{

    private CluSetsManagementController parentController;
    private String modelId;
    
    public CluSetsModelDispatcher() {
        super(CluSetsModelDispatcher.class.getName());
    }
    
    public CluSetsManagementController getParentController() {
        return parentController;
    }

    public void setParentController(CluSetsManagementController parentController) {
        this.parentController = parentController;
    }
    
    private void checkParent() {
        if (parentController == null) {
            throw new UnsupportedOperationException("parentController needs to be set");
        }
    }

    @Override
    public boolean isValid(List<ValidationResultInfo> validationResults, boolean checkCurrentSectionOnly) {
        checkParent();
        return parentController.isValid(validationResults, checkCurrentSectionOnly);
    }

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        return null;
    }

    @Override
    public Enum<?> getViewEnumValue(String enumValue) {
        checkParent();
        return parentController.getViewEnumValue(enumValue);
    }

    @Override
    public Class<? extends Enum<?>> getViewsEnum() {
        checkParent();
        return parentController.getViewsEnum();
    }

    @Override
    protected void hideView(View view) {
    }

    @Override
    protected void renderView(View view) {
        checkParent();
        parentController.renderView(view);
    }

    @Override
    public void showDefaultView(Callback<Boolean> onReadyCallback) {
        checkParent();
        parentController.showDefaultView(onReadyCallback);
    }

    @Override
    public void addSection(String[] hierarchy, SectionView section) {
        checkParent();
        parentController.addSection(hierarchy, section);
    }

    @Override
    public void addStartSection(SectionView section) {
        checkParent();
        parentController.addStartSection(section);
    }

    @Override
    public void addTool(ToolView tool) {
        checkParent();
        parentController.addTool(tool);
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Override
    public void requestModel(ModelRequestCallback callback) {
        if (modelId == null) {
            throw new IllegalStateException(
                    "setModelId must be called before this method can be used");
        } else {
            parentController.requestModel(modelId, callback);
        }
    }

	@Override
	public void updateModel() {
		// TODO Auto-generated method stub
		
	}
    
}
