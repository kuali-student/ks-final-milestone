package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SimpleConfigurableSection extends LayoutSectionView {
    
    protected final VerticalPanel panel = new VerticalPanel();
	private final Label sectionTitleLabel = new Label();
	private final Label instructionsLabel = new Label();
	private boolean loaded = false;
	
	private Model<ModelDTO> model = null;
		
	public SimpleConfigurableSection(Enum<?> viewEnum, String name) {	    
		super(viewEnum, name);
	    super.initWidget(panel);			
	}
	
	@Override
	public void setInstructions(String instructions) {
	    super.setInstructions(instructions);
		instructionsLabel.setText(instructions);
	}

	@Override
	public void setSectionTitle(String sectionTitle) {
	    super.setSectionTitle(sectionTitle);
		sectionTitleLabel.setText(sectionTitle);
	}
	
    @Override
    public void addField(FieldDescriptor fieldDescriptor) {
        super.addField(fieldDescriptor);
        RowDescriptor row = new RowDescriptor();
        row.addField(fieldDescriptor);
        rows.add(row);        
    }

    @Override
    public void addSection(NestedSection section) {
        super.addSection(section);
        RowDescriptor row = new RowDescriptor();
        row.addSection(section);
        rows.add(row);
    }

	public void beforeShow(){
	    
	    if (!loaded){
	        panel.add(sectionTitleLabel);
	        panel.add(instructionsLabel);
	        for(NestedSection ns: sections){
	            ns.redraw();
	        }
	        for(RowDescriptor r: rows){
	            panel.add(r);
	        }
	        
	        loaded = true;
	    }

        //Request model and redraw view if model changed
	    getController().requestModel(ModelDTO.class, new ModelRequestCallback<ModelDTO>(){
            public void onModelReady(Model<ModelDTO> m) {
                if (model != m){
                    model = m;
                    redraw();
                }                    
            }

            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert("Failed to get model");
            }
            
        });
	}
	
	public void clear(){
	    //TODO: Reset the form...form will require clear/reset method();
	}
		
	public void redraw(){
        ModelDTO modelDTO = model.get();
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = fields.get(i);
            field.getWidgetBinding().setValue(field.getFieldWidget(), field.getPropertyBinding().getValue(modelDTO));
        }
        for(NestedSection s: sections){
            s.updateView(model.get());
        }
	}
			
    @Override
    public void validate(Callback<org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel> callback) {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        
    }

    /**
     * This updates the model
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#updateModel()
     */
    @Override
    @SuppressWarnings("unchecked")
    public void updateModel() {
        ModelDTO modelDTO = model.get();
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            if (field.getFieldWidget() instanceof MultiplicityComposite){
                ((MultiplicityComposite)field.getFieldWidget()).updateModelDTOValue();
            }
            
            field.getPropertyBinding().setValue(modelDTO, field.getWidgetBinding().getValue(field.getFieldWidget()));
        }
        for(NestedSection s: sections){
            s.updateModel(model.get());
        }            
    }
}
