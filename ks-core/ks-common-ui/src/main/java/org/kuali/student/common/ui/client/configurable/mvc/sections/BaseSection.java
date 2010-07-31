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

package org.kuali.student.common.ui.client.configurable.mvc.sections;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.ValidationEventBinding;
import org.kuali.student.common.ui.client.configurable.mvc.ValidationEventBindingImpl;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityGroup;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityGroupItem;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;
import org.kuali.student.common.ui.client.event.ValidateRequestEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.FieldLayout;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.Key;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public abstract class BaseSection extends SpanPanel implements Section{

	protected FieldLayout layout;

	protected ArrayList<Section> sections = new ArrayList<Section>();
	protected ArrayList<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
	protected LayoutController layoutController = null;
	protected boolean isValidationEnabled = true;
	protected boolean isDirty = false;

	@Override
	public String addField(final FieldDescriptor fieldDescriptor) {

        fields.add(fieldDescriptor);
        String key = layout.addField(fieldDescriptor.getFieldElement());

        ValidationEventBinding binding = new ValidationEventBindingImpl();
        if(fieldDescriptor.getValidationRequestCallback()== null){
            fieldDescriptor.setValidationCallBack(new Callback<Boolean>() {
                @Override
                public void exec(Boolean result) {
            	    final ModelWidgetBinding mwb = fieldDescriptor.getModelWidgetBinding();
            	    if (mwb != null) {
            	        final Widget w = fieldDescriptor.getFieldWidget();
            	        final String modelId = fieldDescriptor.getModelId();
                        final LayoutController parent = LayoutController.findParentLayout(w);
                        if(parent != null){
                        	if (modelId == null) {
                        		parent.requestModel(new ModelRequestCallback<DataModel>() {

                        			@Override
                        			public void onModelReady(DataModel model) {
                        				validateField(fieldDescriptor, model);
                        				
                        			}

                        			@Override
                        			public void onRequestFail(Throwable cause) {
                        				GWT.log("Unable to retrieve model to validate " + fieldDescriptor.getFieldKey(), null);
                        			}

                        		});
                        	} else {
                        		parent.requestModel(modelId, new ModelRequestCallback<DataModel>() {

                        			@Override
                        			public void onModelReady(DataModel model) {
                        				validateField(fieldDescriptor, model);
                        			}

                        			@Override
                        			public void onRequestFail(Throwable cause) {
                        				GWT.log("Unable to retrieve model to validate " + fieldDescriptor.getFieldKey(), null);
                        			}

                        		});                        	}
            	    	}
                    } else {
                        GWT.log(fieldDescriptor.getFieldKey() + " has no widget binding.", null);
                    }
                }
            });
        }
        binding.bind(fieldDescriptor);

        return key;
	}

	private void validateField(
			final FieldDescriptor fieldDescriptor, final DataModel model) {
		Widget w = fieldDescriptor.getFieldWidget();
		ModelWidgetBinding mwb = fieldDescriptor.getModelWidgetBinding();
		if(fieldDescriptor.getFieldKey() != null){
			mwb.setModelValue(w, model, fieldDescriptor.getFieldKey());
			dirtyCheckField(fieldDescriptor, model);
			ValidateRequestEvent e = new ValidateRequestEvent();
			e.setFieldDescriptor(fieldDescriptor);
			LayoutController.findParentLayout(fieldDescriptor.getFieldWidget()).fireApplicationEvent(e);
		}
	}
	
	private void dirtyCheckField(FieldDescriptor fieldDescriptor, DataModel model){
        QueryPath fieldPath = QueryPath.parse(fieldDescriptor.getFieldKey());
		QueryPath qPathDirty = fieldPath.subPath(0, fieldPath.size() - 1);
	    qPathDirty.add(ModelWidgetBindingSupport.RUNTIME_ROOT);
	    qPathDirty.add(ModelWidgetBindingSupport.DIRTY_PATH);
	    qPathDirty.add(fieldPath.get(fieldPath.size()-1));
	    Boolean dirty = false;
	    
	    if(ensureDirtyFlagPath(model.getRoot(), qPathDirty)){
	    	dirty = model.get(qPathDirty);
	    }
	    if(dirty){
	    	isDirty = true;
	    	fieldDescriptor.setDirty(true);
	    }
	}
	
    protected boolean ensureDirtyFlagPath(Data root, QueryPath path) {
        Data current = root;
        boolean containsFlag = false;
        for (int i = 0; i < path.size(); i++) {
            Key key = path.get(i);
            if(i == path.size() - 1){
            	containsFlag = current.containsKey(key);
            	break;
            }
            Data d = current.get(key);
            if (d == null) {
                containsFlag = false;
                break;
            }
            current = d;
        }
        return containsFlag;
    }

	@Override
	public String addSection(Section section) {

        sections.add(section);
        String key = layout.addLayout(section.getLayout());
        return key;
	}

	@Override
	public void removeSection(Section section){
		sections.remove(section);
		layout.removeLayoutElement(section.getLayout());
	}

	protected void clearValidation() {
		layout.clearValidation();

	}

	@Override
	public List<FieldDescriptor> getFields() {
        List<FieldDescriptor> allFields = new ArrayList<FieldDescriptor>();
        allFields.addAll(fields);

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
	public List<Section> getSections() {
		return sections;
	}

	@Override
	public ErrorLevel processValidationResults(List<ValidationResultInfo> results) {
		this.clearValidation();
		ErrorLevel status = ErrorLevel.OK;

		if (isValidationEnabled){

			for(FieldDescriptor f: this.fields){

				if(f.hasHadFocus()){
					System.out.println("Processing field " + f.getFieldKey());
					for(ValidationResultInfo vr: results){
						if(vr.getElement().equals(f.getFieldKey())){
							System.out.println("Checking validation on field " + f.getFieldKey());
							FieldElement element = f.getFieldElement();
							if (element != null){
								ErrorLevel fieldStatus = element.processValidationResult(vr);
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

				if(f.getFieldWidget() instanceof MultiplicityComposite ){
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
				//TODO Can we do this without checking for instanceof  MG??
				if(f.getFieldWidget() instanceof MultiplicityGroup ){
					MultiplicityGroup mg = (MultiplicityGroup) f.getFieldWidget();

					//possibly return error state from processValidationResults to give composite title bar a separate color
	            	for(MultiplicityGroupItem item: mg.getItems()){
	            		if(item.getItemWidget() instanceof Section && !item.isDeleted()){
	            			ErrorLevel fieldStatus = ((Section)item.getItemWidget()).processValidationResults(results);
							if(fieldStatus.getLevel() > status.getLevel()){
								status = fieldStatus;
							}
	            		}
	            	}
				}
			
			}

	        for(Section s: sections){
	            ErrorLevel subsectionStatus = s.processValidationResults(results);
	            if(subsectionStatus.getLevel() > status.getLevel()){
	            	status = subsectionStatus;
	            }
	        }
		}

        return status;
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
	public String addWidget(Widget w) {
		return layout.addWidget(w);
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
    public void enableValidation(boolean enableValidation) {
    	this.isValidationEnabled = enableValidation;
    }

    @Override
    public boolean isValidationEnabled() {
		return isValidationEnabled;
	}

	@Override
    public void updateModel(DataModel model){
        SectionBinding.INSTANCE.setModelValue(this, model, "");
    }

    @Override
    public void updateWidgetData(DataModel model) {
        SectionBinding.INSTANCE.setWidgetValue(this, model, "");
    }

    @Override
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
    public void resetDirtyFlags() {
    	this.isDirty = false;
        for(FieldDescriptor f: fields){
            f.setDirty(false);
        }

        for(Section s: sections){
            s.resetDirtyFlags();
        }
    }


	@Override
	public String addSection(String key, Section section) {
        sections.add(section);
        section.getLayout().setKey(key);
        String rkey = layout.addLayout(section.getLayout());
        return rkey;
	}

	@Override
	public FieldDescriptor getField(String fieldKey) {
		for(FieldDescriptor f: fields){
			if(f.getFieldKey().equals(fieldKey)){
				return f;
			}
		}
		return null;
	}

	@Override
	public FieldLayout getLayout() {
		return layout;
	}

	@Override
	public Section getSection(String sectionKey) {
		for(Section s: sections){
			if(s.getLayout().getKey().equals(sectionKey)){
				return s;
			}
		}
		return null;
	}

	@Override
	public void removeField(String fieldKey) {
		int index = 0;
		boolean found = false;
		for(FieldDescriptor f: fields){
			if(f.getFieldKey().equals(fieldKey)){
				index = fields.indexOf(f);
				found = true;
				break;
			}
		}
		if(found){
			fields.remove(index);
		}
		layout.removeLayoutElement(fieldKey);

	}

	@Override
	public void removeField(FieldDescriptor field) {

		fields.remove(field);
		layout.removeLayoutElement(field.getFieldKey());

	}

	@Override
	public void removeSection(String sectionKey) {
		int index = 0;
		boolean found = false;
		for(Section s: sections){
			if(s.getLayout().getKey().equals(sectionKey)){
				index = sections.indexOf(s);
				found = true;
				break;
			}
		}
		if(found){
			sections.remove(index);
		}
		layout.removeLayoutElement(sectionKey);

	}

	@Override
	public void removeWidget(Widget widget) {
		layout.removeLayoutElement(widget);

	}

	@Override
	public void removeWidget(String key) {
		layout.removeLayoutElement(key);

	}

	public boolean isDirty(){
		if(!this.isDirty){
			//Check child sections for dirtyness
			for(Section s: sections){
				if(s.isDirty()){
					isDirty = true;
					break;
				}
			}
		}
		return isDirty;
	}
	
	/**
	 * Do not use this method for adding sections, fields, or widgets to sections
	 */
	@Override
	public void add(Widget w) {
		super.add(w);
	}
	
	@Override
	public void addStyleName(String style) {
		layout.addStyleName(style);
	}
	
	@Override
	public void setStyleName(String style) {
		layout.setStyleName(style);
	}
}
