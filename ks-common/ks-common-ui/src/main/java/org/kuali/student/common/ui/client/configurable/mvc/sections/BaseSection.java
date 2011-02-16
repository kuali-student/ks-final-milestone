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

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.assembly.data.Data.Key;
import org.kuali.student.common.ui.client.configurable.mvc.CanProcessValidationResults;
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
import org.kuali.student.common.ui.client.event.ContentDirtyEvent;
import org.kuali.student.common.ui.client.event.ValidateRequestEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.FieldLayout;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
 * The base section is the base implementation of the section interface.
 * @author Kuali Student
 *
 */
public abstract class BaseSection extends SpanPanel implements Section{

	protected FieldLayout layout;

	protected ArrayList<Section> sections = new ArrayList<Section>();
	protected ArrayList<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
	protected LayoutController layoutController = null;
	protected boolean isValidationEnabled = true;
	protected boolean isDirty = false;

	/**
	 * Adds a field to this section.  Adds the field to this section's layout.
	 * Attaches an event handler for lost focus events on the field widget
	 * to validate against the metadata defined in its FieldDescriptor as well as firing events for dirty
	 * field handling.
	 * 
	 * Note if a field has been marked as hidden in the dictionary, this method will not add this field to the layout.
	 * If it is not visible the key returned by making call to addField is null.
	 * 
	 * @param field
	 * @return key/path of this field
	 */
	@Override
	public String addField(final FieldDescriptor fieldDescriptor) {
		String key = null;
		
		if (fieldDescriptor.isVisible()){
			fields.add(fieldDescriptor);
	        key = layout.addField(fieldDescriptor.getFieldElement());
	
	        ValidationEventBinding binding = new ValidationEventBindingImpl();
	        if(fieldDescriptor.getValidationRequestCallback()== null){
	            fieldDescriptor.setValidationCallBack(new Callback<Boolean>() {
	                @Override
	                public void exec(Boolean result) {
	            	    final ModelWidgetBinding mwb = fieldDescriptor.getModelWidgetBinding();
	            	    if (mwb != null) {
	            	        final Widget w = fieldDescriptor.getFieldWidget();
	            	        final String modelId = fieldDescriptor.getModelId();
	            	        final Controller parent;
	                        Controller findResult = LayoutController.findParentLayout(w);
	                        if(BaseSection.this instanceof View){
	                        	findResult = ((View)BaseSection.this).getController();
	                        }
	                        parent = findResult;
	                        if(parent != null){
	                        	if (modelId == null) {
	                        		parent.requestModel(new ModelRequestCallback<DataModel>() {
	
	                        			@Override
	                        			public void onModelReady(DataModel model) {
	                        				validateField(fieldDescriptor, model, parent);
	                                                                                           
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
	                        				validateField(fieldDescriptor, model, parent);
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
		}	       
	        
        return key;
	}

	private void validateField(
			final FieldDescriptor fieldDescriptor, final DataModel model, Controller controller) {
		Widget w = fieldDescriptor.getFieldWidget();
		ModelWidgetBinding mwb = fieldDescriptor.getModelWidgetBinding();
		if(fieldDescriptor.getFieldKey() != null){
			mwb.setModelValue(w, model, fieldDescriptor.getFieldKey());
			dirtyCheckField(fieldDescriptor, model);
			ValidateRequestEvent e = new ValidateRequestEvent();
			e.setFieldDescriptor(fieldDescriptor);
			e.setValidateSingleField(true);
			controller.fireApplicationEvent(e);
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
	    	setIsDirty(true);
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

	/** 
	 * Adds a section to this section's layout.
	 * 
	 * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#addSection(org.kuali.student.common.ui.client.configurable.mvc.sections.Section)
	 */
	@Override
	public String addSection(Section section) {

        section.setLayoutController(layoutController);
		sections.add(section);
        String key = layout.addLayout(section.getLayout());
        return key;
	}

	/**
	 * Removes a section from this section's layout.
	 * 
	 * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#removeSection(org.kuali.student.common.ui.client.configurable.mvc.sections.Section)
	 */
	@Override
	public void removeSection(Section section){
		sections.remove(section);
		layout.removeLayoutElement(section.getLayout());
	}

	/**
	 * Clear all validation errors from the layout (removes all red highlight and error text shown on the
	 * screen)
	 */
	protected void clearValidation() {
		layout.clearValidation();

	}

	/**
	 * Gets all the fields in a section and its subsections.
	 * 
	 * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#getFields()
	 */
	@Override
	public List<FieldDescriptor> getFields() {
        List<FieldDescriptor> allFields = new ArrayList<FieldDescriptor>();
        allFields.addAll(fields);

        for(Section ns: sections){
            allFields.addAll(ns.getFields());
        }
        return allFields;
	}

	/**
	 * Gets all the fields in this section, but does not include fields in its nested sections.
	 * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#getUnnestedFields()
	 */
	@Override
	public List<FieldDescriptor> getUnnestedFields() {
        return fields;
	}

	/**
	 * Gets all nested sections contained in this section
	 * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#getSections()
	 */
	@Override
	public List<Section> getSections() {
		return sections;
	}

	/**
	 * Processes the validation results passed in and displays the appropriate message on the screen next
	 * to the corresponding field or section.  If clearAllValidation is true, it will clear all validation before
	 * displaying the errors (otherwise will append these errors to the ones already visible on the section).
	 * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#processValidationResults(java.util.List, boolean)
	 */
	@Override
	public ErrorLevel processValidationResults(List<ValidationResultInfo> results, boolean clearAllValidation){
		if(clearAllValidation){
			this.clearValidation();
		}
		ErrorLevel status = ErrorLevel.OK;

		if (isValidationEnabled){

			for(FieldDescriptor f: this.fields){

				if(f.hasHadFocus()){
					for(ValidationResultInfo vr: results){
                        String vrElement = vr.getElement();
                        if(vrElement.startsWith("/")){
                            vrElement = vrElement.substring(1);
                        }
						if(vrElement.equals(f.getFieldKey())){
							FieldElement element = f.getFieldElement();
							if (element != null){
								ErrorLevel fieldStatus = element.processValidationResult(vr);
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
	            			ErrorLevel fieldStatus = ((Section)item.getItemWidget()).processValidationResults(results, clearAllValidation);
							if(fieldStatus.getLevel() > status.getLevel()){
								status = fieldStatus;
							}
	            		}
	            	}
				}
				// TODO Can we do this without checking for instanceof  MG??
				if(f.getFieldWidget() instanceof MultiplicityGroup ){
					MultiplicityGroup mg = (MultiplicityGroup) f.getFieldWidget();

					//possibly return error state from processValidationResults to give composite title bar a separate color
	            	for(MultiplicityGroupItem item: mg.getItems()){
	            		if(item.getItemWidget() instanceof Section && !item.isDeleted()){
	            			ErrorLevel fieldStatus = ((Section)item.getItemWidget()).processValidationResults(results, clearAllValidation);
							if(fieldStatus.getLevel() > status.getLevel()){
								status = fieldStatus;
							}
	            		}
	            	}
				}
                if (f.getFieldWidget() instanceof CanProcessValidationResults) {
                    ErrorLevel fieldStatus = ((CanProcessValidationResults) f.getFieldWidget()).processValidationResults(f, results, clearAllValidation);
                    if (fieldStatus.getLevel() > status.getLevel()) {
                        status = fieldStatus;
                    }
                }
			}

	        for(Section s: sections){
	            ErrorLevel subsectionStatus = s.processValidationResults(results,clearAllValidation);
	            if(subsectionStatus.getLevel() > status.getLevel()){
	            	status = subsectionStatus;
	            }
	        }
		}

        return status;
	}

	/**
	 * Same as processValidationResults(results, true)
	 * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#processValidationResults(java.util.List)
	 */
	@Override
	public ErrorLevel processValidationResults(List<ValidationResultInfo> results) {
		return processValidationResults(results, true);
	}

	/**
	 * Gets the defined controller for this section if one exists.
	 * 
	 * @see org.kuali.student.common.ui.client.configurable.mvc.HasLayoutController#getLayoutController()
	 */
	@Override
	public LayoutController getLayoutController() {
		return this.layoutController;
	}

	@Override
	public void setLayoutController(LayoutController controller) {
		this.layoutController = controller;
	}


	/**
	 * Use this to add widgets to a sections layout.  DO NOT use the add(Widget widget) call.
	 * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#addWidget(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public String addWidget(Widget w) {
		return layout.addWidget(w);
	}

    /**
     * Sets the fields has had focus flag.  This flag is used for determining if the user has interacted with
     * any fields on the page or if the fields need to be assumed to have been interacted with.  Used for determining
     * when validation is necessary on a particular section.
     * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#setFieldHasHadFocusFlags(boolean)
     */
    public void setFieldHasHadFocusFlags(boolean hadFocus) {
        for(FieldDescriptor f: fields){
            f.setHasHadFocus(hadFocus);
            if(f.getFieldWidget() instanceof MultiplicityComposite){
				MultiplicityComposite mc = (MultiplicityComposite) f.getFieldWidget();

            	for(MultiplicityItem item: mc.getItems()){
            		if(item.getItemWidget() instanceof Section && !item.isDeleted()){
            			((Section) item.getItemWidget()).setFieldHasHadFocusFlags(hadFocus);
            		}
            	}
            }else if(f.getFieldWidget() instanceof MultiplicityGroup){
            	MultiplicityGroup mg = (MultiplicityGroup) f.getFieldWidget();

            	for(MultiplicityGroupItem item: mg.getItems()){
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

    /**
     * A section can turn off all validation by setting this flag
     * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#enableValidation(boolean)
     */
    @Override
    public void enableValidation(boolean enableValidation) {
    	this.isValidationEnabled = enableValidation;
    }

    @Override
    public boolean isValidationEnabled() {
		return isValidationEnabled;
	}

	/**
	 * Update the model passed in with data from the fields on this section.  This method will use the 
	 * modelWidgetBinding defined in each of this sections fields to determine how to add this data to the
	 * model.
	 * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#updateModel(org.kuali.student.common.ui.client.mvc.DataModel)
	 */
	@Override
    public void updateModel(DataModel model){
        SectionBinding.INSTANCE.setModelValue(this, model, "");
    }

    /**
     * Updates the section's fields with data from the model passed in.  This effects all the data input and
     * display widgets on the particular section.  This method will use the 
	 * modelWidgetBinding defined in each of this sections fields to determine how to interpret data from the
	 * model and display it on the fields corresponding widget.
	 * 
     * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#updateWidgetData(org.kuali.student.common.ui.client.mvc.DataModel)
     */
    @Override
    public void updateWidgetData(DataModel model) {
        SectionBinding.INSTANCE.setWidgetValue(this, model, "");
    }

    /**
     * Resets all the dirty and focus flags on fields.
     * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#resetFieldInteractionFlags()
     */
    @Override
    public void resetFieldInteractionFlags() {
    	this.isDirty = false;
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


	/**
	 * Same as addSection except with an option user defined key (for retrieval of the section if necessary).
	 * 
	 * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#addSection(java.lang.String, org.kuali.student.common.ui.client.configurable.mvc.sections.Section)
	 */
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

	/**
	 * Gets this sections fieldLayout.  The fieldLayout is used to determine how sections will layout the
	 * ui and contains things such as the title and validation panels.
	 * 
	 * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#getLayout()
	 */
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

	/**
	 * Returns true if this this section is considered dirty (the user may have entered data into this
	 * section)
	 * 
	 * @see org.kuali.student.common.ui.client.configurable.mvc.sections.Section#isDirty()
	 */
	public boolean isDirty(){
		if(!this.isDirty){
			//Check child sections for dirtyness
			for(Section s: sections){
				if(s.isDirty()){
					setIsDirty(true);
					break;
				}
			}
		}
		return isDirty;
	}

    public void setIsDirty(boolean state) {
		//Should this trust layoutController to be already set?
    	if (layoutController == null){
    		layoutController = LayoutController.findParentLayout(layout);
    	}
    	if (isDirty != state){
        	isDirty = state;
	    	if (layoutController != null && isDirty){
	    		layoutController.fireApplicationEvent(new ContentDirtyEvent());
	    	}
    	}
    }

	/**
	 * DO NOT use this method for adding sections, fields, or widgets to sections
	 */
	@Override
	public void add(Widget w) {
		super.add(w);
	}

	/**
	 * Adds a style to this section's underlying layout.
	 * @see com.google.gwt.user.client.ui.UIObject#addStyleName(java.lang.String)
	 */
	@Override
	public void addStyleName(String style) {
		layout.addStyleName(style);
	}

	@Override
	public void setStyleName(String style) {
		layout.setStyleName(style);
	}

	/**
	 * Sets instructions for this entire section (appears next to section title)
	 * @param html
	 */
	public void setInstructions(String html){
		layout.setInstructions(html);
	}

	/**
	 * Sets help for this entire section (appears next to section title)
	 * @param html
	 */
	public void setHelp(String html){
		layout.setHelp(html);
	}
	
	/**
	 * Sets required for this entire section (appears next to section title)
	 * @param required
	 */
	public void setRequired(AbbrPanel required){
		layout.setRequired(required);
	}
}
