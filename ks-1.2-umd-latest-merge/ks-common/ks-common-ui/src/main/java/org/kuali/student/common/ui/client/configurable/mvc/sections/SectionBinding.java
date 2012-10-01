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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.mvc.DataModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
 * Model widget binding for a section - calls the bindings on its fields and sub sections.  Special handling
 * for sections which can have their sections deleted by the user.
 * 
 * @author Kuali Student Team
 *
 */
public class SectionBinding extends ModelWidgetBindingSupport<Section> {
	public static SectionBinding INSTANCE = new SectionBinding();

    private SectionBinding() {};

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport#setModelValue(java.lang.Object,
     *      org.kuali.student.common.ui.client.mvc.DataModel, java.lang.String)
     */
    @Override
    public void setModelValue(Section section, DataModel model, String path) {
        
        List<FieldDescriptor> fields = section.getUnnestedFields();
        Map<String, Object> selectedData = null;
        for (int i = 0; i < fields.size(); i++) {
        	if(!(fields.get(i) instanceof FieldDescriptorReadOnly)){
        		FieldDescriptor field = (FieldDescriptor) fields.get(i);
            
	            String fieldPath = path + QueryPath.getPathSeparator() + field.getFieldKey();
	            fieldPath = fieldPath.trim();
	            if (fieldPath.startsWith(QueryPath.getPathSeparator())) {
	                fieldPath = fieldPath.substring(QueryPath.getPathSeparator().length());
	            }
	            ModelWidgetBinding binding = field.getModelWidgetBinding();
	            if (binding != null) {
	                Widget w = field.getFieldWidget();
	                binding.setModelValue(w, model, fieldPath);
	            } else {
	                GWT.log(field.getFieldKey() + " has no widget binding.", null);
	            }
            }
        }
        
        if(section instanceof HasSectionDeletion){
        	List<Section> deleted =((HasSectionDeletion) section).getDeletedSections();
        	for(int i =0; i < deleted.size(); i++){
        		Section deletedSection = deleted.get(i);
        		//get all fields and delete them>
        		List<FieldDescriptor> deletedFields = deletedSection.getFields();
        		for(int j = 0; j < deletedFields.size(); j++){
        			FieldDescriptor field = deletedFields.get(j);
        			model.remove(QueryPath.parse(field.getFieldKey()));
        		}
        	}
        }
        
        for (Section s : section.getSections()) {
            // data of the HasSectionDeletion section that should be in the model 
        	if(section instanceof HasSectionDeletion){
        		List<Section> deleted =((HasSectionDeletion) section).getDeletedSections();
        		List<String> deletionParentKeys = ((HasSectionDeletion) section).getDeletionParentKeys();
        		if (deletionParentKeys != null) {
        		    for (String deletionParentKey : deletionParentKeys) {
        		        model.remove(QueryPath.parse(deletionParentKey));
        		    }
        		}
        		// section is not deleted update model with the data from widget.  Keep
        		// a record of the list of data so that they can be added back into the model
        		if(!deleted.contains(s)){
        			s.updateModel(model);
                    if (deletionParentKeys != null) {
                        for (String deletionParentKey : deletionParentKeys) {
                            selectedData = (selectedData == null)? 
                                    new HashMap<String, Object>() : selectedData;
                            selectedData.put(deletionParentKey, model.get(deletionParentKey));
                        }
                    }
        		} else if (selectedData != null && !selectedData.isEmpty()) {
        		    for (String key : selectedData.keySet()) {
        		        setValue(model, key, selectedData.get(key));
        		    }
        		}
        	}
        	else{
        		s.updateModel(model);
        	}      
        }
    }
    
    private void setValue(DataModel model, String path, Object value) {
        QueryPath qPath = QueryPath.parse(path);
        if (value instanceof String) {
            model.set(qPath, (String) value);
        } else if (value instanceof Integer) {
            model.set(qPath, (Integer) value);
        } else if (value instanceof Long) {
            model.set(qPath, (Long) value);
        } else if (value instanceof Float) {
            model.set(qPath, (Float) value);
        } else if (value instanceof Double) {
            model.set(qPath, (Double) value);
        } else if (value instanceof Boolean) {
            model.set(qPath, (Boolean) value);
        } else if (value instanceof Date) {
            model.set(qPath, (Date) value);
        } else if (value instanceof Data) {
            model.set(qPath, (Data) value);
        }
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport#setWidgetValue(java.lang.Object,
     *      org.kuali.student.common.ui.client.mvc.DataModel, java.lang.String)
     */
	@Override
	public void setWidgetValue(Section section, DataModel model, String path) {
        List<FieldDescriptor> fields = section.getUnnestedFields();

        for (int i = 0; i < fields.size(); i++) {
            FieldDescriptor field = fields.get(i);

            ModelWidgetBinding binding = field.getModelWidgetBinding();
            String fieldPath = path + QueryPath.getPathSeparator() + field.getFieldKey();
            if (binding != null) {
                Widget w = field.getFieldWidget();
                binding.setWidgetValue(w, model, fieldPath);
            } else {
                GWT.log(field.getFieldKey() + " has no widget binding.", null);
            }
        }
        for (Section s : section.getSections()) {
            s.updateWidgetData(model);
        }
		
	}
}
