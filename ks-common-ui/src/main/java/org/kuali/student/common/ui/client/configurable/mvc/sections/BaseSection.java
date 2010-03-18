package org.kuali.student.common.ui.client.configurable.mvc.sections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.PropertyBinding;
import org.kuali.student.common.ui.client.configurable.mvc.RequiredEnum;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.ValidationEventBinding;
import org.kuali.student.common.ui.client.configurable.mvc.ValidationEventBindingImpl;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;
import org.kuali.student.common.ui.client.event.ValidateRequestEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.FieldElement;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public abstract class BaseSection extends Composite implements Section{

	protected ArrayList<Section> sections = new ArrayList<Section>();
	protected ArrayList<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
	protected LayoutController layoutController = null;
	protected SectionTitle sectionTitle = SectionTitle.generateEmptyTitle();
	protected KSLabel instructions = new KSLabel();
	protected InfoMessage message = new InfoMessage();
	protected Map<String, FieldInfo> pathFieldInfoMap = new HashMap<String, FieldInfo>();
	
	
	protected abstract void addSectionToLayout(BaseSection s);
	protected abstract void addFieldToLayout(FieldDescriptor f);
	protected abstract void addWidgetToLayout(Widget w);
	
	private RequiredEnum requiredState = RequiredEnum.NOT_MARKED;
	
	public class FieldInfo{
		private ValidationMessagePanel valPanel;
		private FieldElement fieldDiv;
		private Panel encapsulatingPanel;
		private String fieldName;
		
		public FieldInfo(String fieldName, ValidationMessagePanel validationPanel, FieldElement fieldDiv, Panel encapsulatingPanel){
			this.valPanel = validationPanel;
			this.fieldDiv = fieldDiv;
			this.encapsulatingPanel = encapsulatingPanel;
			this.fieldName = fieldName;
		}

		public ValidationMessagePanel getValidationPanel() {
			return valPanel;
		}
		public FieldElement getFieldDiv() {
			return fieldDiv;
		}
		public Panel getEncapsulatingPanel() {
			return encapsulatingPanel;
		}
		public String getFieldName() {
			return fieldName;
		}
		
		public void setErrorState(boolean error){
			//fieldDiv.setValidationError(error);
			if(error){
				encapsulatingPanel.addStyleName("error");
			}
			else{
				encapsulatingPanel.removeStyleName("error");
			}
			
		}
		public ErrorLevel processValidationResults(List<ValidationResultInfo> validationResults) {
			
			ErrorLevel status = ErrorLevel.OK;
			for(ValidationResultInfo vr: validationResults){
				KSLabel message;
				if(fieldName != null){
					message = new KSLabel(fieldName + " - " + vr.getMessage());
				}
				else{
					message = new KSLabel(vr.getMessage());
				}
				
	    		if(vr.getLevel() == ErrorLevel.ERROR){
	    			message.addStyleName("ks-form-validation-label");
	    			
	    			this.valPanel.addMessage(message);
	    			this.setErrorState(true);
	    			if(status.getLevel() < ErrorLevel.ERROR.getLevel()){
	    				status = vr.getLevel();
	    			}
	    			
	    		}
	    		else if(vr.getLevel() == ErrorLevel.WARN){
	    			if(status.getLevel() < ErrorLevel.WARN.getLevel()){
	    				status = vr.getLevel();
	    			}
	    			//message.addStyleName("KS-Validation-Warning-Message");
	    		}
	    		else{
	    			//message.addStyleName("KS-Validation-Ok-Message");
	    		}
	        }
			
			return status;
		}
	}

	@Override
	public void addField(final FieldDescriptor fieldDescriptor) {
        //Required logic
        if(this.getRequiredState() == RequiredEnum.REQUIRED){
            //if top level is required remove required from sub fields, stop required * from being shown
            if(fieldDescriptor.getRequiredState() == RequiredEnum.REQUIRED){
                fieldDescriptor.setRequiredState(RequiredEnum.NOT_MARKED);
            }
        }
        else if(this.getRequiredState() == RequiredEnum.OPTIONAL){
            if(fieldDescriptor.getRequiredState() == RequiredEnum.OPTIONAL){
                fieldDescriptor.setRequiredState(RequiredEnum.NOT_MARKED);
            }
        }

        fields.add(fieldDescriptor);
        addFieldToLayout(fieldDescriptor);

        ValidationEventBinding binding = new ValidationEventBindingImpl();
        if(fieldDescriptor.getValidationRequestCallback()== null){
            fieldDescriptor.setValidationCallBack(new Callback<Boolean>() {
                @Override
                public void exec(Boolean result) {
                	LayoutController controller = LayoutController.findParentLayout(fieldDescriptor.getFieldWidget());
                	ModelDTO model = null;
                	if(controller != null){
                		model = controller.getModel();
                	}
                	if(model != null){
                		//FIXME this is for backwards compatibility ONLY, tear this out later
                        PropertyBinding pBinding = fieldDescriptor.getPropertyBinding();
                        PropertyBinding wBinding = fieldDescriptor.getWidgetBinding();
                        
                        if (wBinding != null){
                            Widget w = fieldDescriptor.getFieldWidget();
                            pBinding.setValue(model, wBinding.getValue(w));
                            GWT.log(model.toString(), null);
                        } else {
                            GWT.log(fieldDescriptor.getFieldKey() + " has no widget binding.", null);
                        }
                        //TODO uncomment this
                        //LayoutController.findParentLayout(fieldDescriptor.getFieldWidget()).validate(Section.this);
                	}
                	else{
                	    final ModelWidgetBinding mwb = fieldDescriptor.getModelWidgetBinding();
                	    if (mwb != null) {
                	        final Widget w = fieldDescriptor.getFieldWidget();
                            final LayoutController parent = LayoutController.findParentLayout(w);
                            if(parent != null){
	                            parent.requestModel(new ModelRequestCallback<DataModel>() {
	
	                                @Override
	                                public void onModelReady(DataModel model) {
	                                	if(fieldDescriptor.getFieldKey() != null){
		                                    mwb.setModelValue(w, model, fieldDescriptor.getFieldKey());
		                                    ValidateRequestEvent e = new ValidateRequestEvent();
		                                    e.setFieldDescriptor(fieldDescriptor);
		                                    LayoutController.findParentLayout(fieldDescriptor.getFieldWidget()).fireApplicationEvent(e);
	                                	}
	                                }
	
	                                @Override
	                                public void onRequestFail(Throwable cause) {
	                                    GWT.log("Unable to retrieve model to validate " + fieldDescriptor.getFieldKey(), null);
	                                }
	                                
	                            });
                	    	}
                        } else {
                            GWT.log(fieldDescriptor.getFieldKey() + " has no widget binding.", null);
                        }

                	}
                	
                    

                }
            });
        }
        binding.bind(fieldDescriptor);
		
	}
	
	@Override
	public void addSection(BaseSection section) {
        //Required logic
        if(this.getRequiredState() == RequiredEnum.REQUIRED){
            //if top level is required remove required from sub fields, stop required * from being shown
            if(section.getRequiredState() == RequiredEnum.REQUIRED){
                section.setRequiredState(RequiredEnum.NOT_MARKED);
            }
        }
        else if(this.getRequiredState() == RequiredEnum.OPTIONAL){
            if(section.getRequiredState() == RequiredEnum.OPTIONAL){
                section.setRequiredState(RequiredEnum.NOT_MARKED);
            }
        }

        sections.add(section);
        addSectionToLayout(section);
	}
	
	@Override
	public void removeSection(BaseSection section){
		sections.remove(section);
		removeSectionFromLayout(section);
	}
	
	protected abstract void removeSectionFromLayout(BaseSection section);

	@Override
	public void clearHighlight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearValidation() {
		for(FieldInfo info: pathFieldInfoMap.values()){
			info.setErrorState(false);
			info.getValidationPanel().clear();
		}
		
	}

	@Override
	public void clearValidationMessage(String fieldPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FieldDescriptor> getFields() {
        List<FieldDescriptor> allFields = new ArrayList<FieldDescriptor>();
        allFields.addAll(fields);
/*        for(FieldDescriptor f: this.fields){
        	if(f.getFieldWidget() instanceof MultiplicityComposite){
				MultiplicityComposite mc = (MultiplicityComposite) f.getFieldWidget();
            	
				//possibly return error state from processValidationResults to give composite title bar a separate color
            	for(MultiplicityItem item: mc.getItems()){
            		if(item.getItemWidget() instanceof Section && !item.isDeleted()){
            			allFields.addAll(((Section) item.getItemWidget()).getFields());
            		}
            	}
			}
        }*/
        for(Section ns: sections){
            allFields.addAll(ns.getFields());
        }
        return allFields;
	}
	
	@Override
	public List<FieldDescriptor> getUnnestedFields() {
        return fields;
	}

	@Override
	public RequiredEnum getRequiredState() {
		return requiredState;
	}

	@Override
	public List<Section> getSections() {
		return sections;
	}

	@Override
	public ErrorLevel processValidationResults(List<ValidationResultContainer> results) {
		this.clearValidation();
		ErrorLevel status = ErrorLevel.OK;
		
		for(FieldDescriptor f: this.fields){
			
			if(f.hasHadFocus()){
				System.out.println("Processing field " + f.getFieldKey());
				for(ValidationResultContainer vc: results){
					if(vc.getElement().equals(f.getFieldKey())){
						System.out.println("Checking validation on field " + f.getFieldKey());
						FieldInfo info = pathFieldInfoMap.get(f.getFieldKey());
						if (info != null){
							ErrorLevel fieldStatus = info.processValidationResults(vc.getValidationResults());
							if(fieldStatus == ErrorLevel.ERROR){
								System.out.println("Error: " + f.getFieldKey());
							}
							if(fieldStatus.getLevel() > status.getLevel()){
								
								status = fieldStatus;
							}
						} 
					}
				}
			}
			
			if(f.getFieldWidget() instanceof MultiplicityComposite){
				MultiplicityComposite mc = (MultiplicityComposite) f.getFieldWidget();
            	
				//possibly return error state from processValidationResults to give composite title bar a separate color
            	for(MultiplicityItem item: mc.getItems()){
            		if(item.getItemWidget() instanceof Section && !item.isDeleted()){
            			ErrorLevel fieldStatus = ((Section)item.getItemWidget()).processValidationResults(results);
						if(fieldStatus.getLevel() > status.getLevel()){
							status = fieldStatus;
						}
            		}
            	}
			}
			
/*	        for(FieldDescriptor f: fields){
	            f.setHasHadFocus(hadFocus);
	            System.out.println(f.getFieldKey() + " has had focus");
	            if(f.getFieldWidget() instanceof MultiplicityComposite){
					MultiplicityComposite mc = (MultiplicityComposite) f.getFieldWidget();
					
	            	for(MultiplicityItem item: mc.getItems()){
	            		if(item.getItemWidget() instanceof Section && !item.isDeleted()){
	            			((Section) item.getItemWidget()).setFieldHasHadFocusFlags(hadFocus);
	            		}
	            	}
	            }
	        }*/
		}
		
/*        for(RowDescriptor r: rows){
            r.clearValidationMessages();
            for(FieldDescriptor f: r.getFields()){
                if(f.hasHadFocus()){
                    for(ValidationResultContainer vc: results){
                        if(vc.getElement().equals(f.getFieldKey())){
                            r.setValidationMessages(vc.getValidationResults());
                        }
                    }

                }
                else if(f.getFieldWidget() instanceof org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite){
                	org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite mc = (org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite) f.getFieldWidget();
                	
                	for(MultiplicityItem item: mc.getItems()){
                		if(item.getItemWidget() instanceof Section){
                			((Section)item.getItemWidget()).processValidationResults(results);
                		}
                	}
                }
            }
        }*/

        for(Section s: sections){
            ErrorLevel subsectionStatus = s.processValidationResults(results);
            if(subsectionStatus.getLevel() > status.getLevel()){
            	status = subsectionStatus;
            }
        }
		
        return status;
	}

	@Override
	public void setHighlight(SectionState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInstructions(String instructions) {
		this.instructions.addStyleName("ks-section-instuctions");
		this.instructions.setText(instructions);
		this.instructions.setVisible(true);
	}

	@Override
	public void setMessage(String text, boolean show) {
		this.message.setMessage(text, show);
		
	}

	@Override
	public void showMessage(boolean show) {
		this.message.setVisible(show);
		
	}

	@Override
	public void showValidationMessage(String fieldPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LayoutController getLayoutController() {
		return this.layoutController;
	}

	@Override
	public void setLayoutController(LayoutController controller) {
		this.layoutController = controller;
	}

	@Override
	public void setRequiredState(RequiredEnum required) {
		requiredState = required;
	}
	
    @Override
    public SectionTitle getSectionTitle() {
        return sectionTitle;
    }
    
	@Override
	public void setSectionTitle(SectionTitle sectionTitle) {
		this.sectionTitle = sectionTitle;
		
	}
    
	@Override
	public void addWidget(Widget w) {
		this.addWidgetToLayout(w);
	}
	
    public void setFieldHasHadFocusFlags(boolean hadFocus) {
        for(FieldDescriptor f: fields){
            f.setHasHadFocus(hadFocus);
            System.out.println(f.getFieldKey() + " has had focus");
            if(f.getFieldWidget() instanceof MultiplicityComposite){
				MultiplicityComposite mc = (MultiplicityComposite) f.getFieldWidget();
				
            	for(MultiplicityItem item: mc.getItems()){
            		if(item.getItemWidget() instanceof Section && !item.isDeleted()){
            			((Section) item.getItemWidget()).setFieldHasHadFocusFlags(hadFocus);
            		}
            	}
            }
        }

        for(Section s: sections){
            s.setFieldHasHadFocusFlags(hadFocus);
        }

    }
    
    @Override
    public void updateModel(DataModel model){
        SectionBinding.INSTANCE.setModelValue(this, model, "");
    }

    @Override
    public void updateView(DataModel model) {
        SectionBinding.INSTANCE.setWidgetValue(this, model, "");
    }

    public void resetFieldInteractionFlags() {
        for(FieldDescriptor f: fields){
            f.setDirty(false);
            f.setHasHadFocus(false);
        }

        for(Section s: sections){
            s.resetFieldInteractionFlags();
        }

    }
    
	@Override
	public void setSectionValidationPanel(ValidationMessagePanel validationPanel) {
		//DOESNT WORK
/*		pathValidationPanelMap = new HashMap<String, ValidationMessagePanel>();
		for(FieldDescriptor f: fields){
			pathValidationPanelMap.put(f.getFieldKey(), validationPanel);
		}
		
		for(Section s: sections){
			setSectionValidationPanel(validationPanel);
		}*/
	}
    
    

}
