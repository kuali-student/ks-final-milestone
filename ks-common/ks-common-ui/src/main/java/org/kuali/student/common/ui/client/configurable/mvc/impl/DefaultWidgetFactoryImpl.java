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

package org.kuali.student.common.ui.client.configurable.mvc.impl;

import org.kuali.student.common.ui.client.configurable.mvc.DefaultWidgetFactory;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.WidgetConfigInfo;
import org.kuali.student.common.ui.client.widgets.BooleanDisplayLabel;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSPlaceholder;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.MetadataInterrogator;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.assembly.data.MetadataInterrogator.ConstraintIds;

import com.google.gwt.user.client.ui.Widget;

public class DefaultWidgetFactoryImpl extends DefaultWidgetFactory {	

	@Override
	public Widget getWidget(FieldDescriptor field) {
		return getWidget(field.getMetadata());
	}

	@Override
	public Widget getWidget(Metadata meta) {
		WidgetConfigInfo config = new WidgetConfigInfo();
		if (meta != null) {
			config.access = meta.getWriteAccess();
			config.isMultiLine = MetadataInterrogator.isMultilined(meta);
			config.isRepeating = MetadataInterrogator.isRepeating(meta);
			config.isRichText = MetadataInterrogator.hasConstraint(meta, ConstraintIds.RICH_TEXT);
			config.maxLength = MetadataInterrogator.getSmallestMaxLength(meta);
			config.type = meta.getDataType();
			config.metadata = meta;
			config.lookupMeta = meta.getInitialLookup();
			config.additionalLookups = meta.getAdditionalLookups();
			config.canEdit = meta.isCanEdit();
			config.canUnmask = meta.isCanUnmask();
			config.canView = meta.isCanView();
		}
		return _getWidget(config);
	}
	
	@Override
	public Widget getReadOnlyWidget(Metadata meta){
		WidgetConfigInfo config = new WidgetConfigInfo();
		if (meta != null) {
			config.access = meta.getWriteAccess();
			config.isMultiLine = MetadataInterrogator.isMultilined(meta);
			config.isRepeating = MetadataInterrogator.isRepeating(meta);
			config.isRichText = MetadataInterrogator.hasConstraint(meta, ConstraintIds.RICH_TEXT);
			config.maxLength = MetadataInterrogator.getSmallestMaxLength(meta);
			config.type = meta.getDataType();
			config.metadata = meta;
			config.lookupMeta = meta.getInitialLookup();
			config.additionalLookups = meta.getAdditionalLookups();
			config.canEdit = false;
			config.canUnmask = meta.isCanUnmask();
			config.canView = meta.isCanView();
		}
		return _getWidget(config);
	}

	@Override
	public Widget getWidget(LookupParamMetadata meta) {
		WidgetConfigInfo config = new WidgetConfigInfo();
		if (meta != null) {
			config.access = meta.getWriteAccess();
			config.type = meta.getDataType();
			config.lookupMeta = meta.getChildLookup();
		}
		return _getWidget(config);
	}

	private Widget _getWidget(WidgetConfigInfo config) {
		Widget result = null;
		if(!config.canView) {
		    result =  new KSPlaceholder();
		    result.setVisible(config.canView);
		} else if(!config.canEdit && (config.lookupMeta == null || config.lookupMeta.getWidget() == null)) {
			if(config.type == DataType.BOOLEAN){
				result = new BooleanDisplayLabel();
			}
			else{
				result = new KSLabel();
			}
		} else {
		    if (config.lookupMeta != null && config.lookupMeta.getWidget() != null) {
		    	//All repeating fields should use the KSSelectedList for multiplicities (Except checkboxes)
                if (config.metadata != null && MetadataInterrogator.isRepeating(config.metadata) && !LookupMetadata.Widget.CHECKBOX_LIST.equals(config.lookupMeta.getWidget())) {
                    result = new KSSelectedList(config);
                } else {
                    result = new KSPicker(config);
                }
            } else {
                switch (config.type) {
                    case BOOLEAN:
                        result = new KSCheckBox();
                        break;
    
                    case DATE:
                        // fall through
    
                    case TRUNCATED_DATE:
                        result = new KSDatePicker();
                        break;
    
                    case DATA:
                        if (config.isRichText) {
                            result = new KSRichEditor();
                            break;
                        }
                    default:
                        if (config.isMultiLine) {
                            result = new KSTextArea();
                            result.addStyleName("ks-textarea-width");
                            if(config.maxLength != null){
                            	if(config.maxLength < 250){
                            		result.addStyleName("ks-textarea-small-height");
                            	}
                            	else if(config.maxLength < 500){
                            		result.addStyleName("ks-textarea-medium-height");
                            	}
                            	else{
                            		result.addStyleName("ks-textarea-large-height");
                            	}
                            }
                            else{
                            	result.addStyleName("ks-textarea-medium-height");
                            }
                        } else {
                            KSTextBox text = new KSTextBox();
                            //text.removeStyleName("KS-Textbox");
                            if (config.maxLength != null) {
                                text.setMaxLength(config.maxLength);
                                if(config.maxLength < 5 ){
	                                switch(config.maxLength){
	                                	case 1:
	                                		text.addStyleName("ks-one-width");
	                                		break;
	                                	case 2:
	                                		text.addStyleName("ks-two-width");
	                                		break;
	                                	case 3:
	                                		text.addStyleName("ks-three-width");
	                                		break;
	                                	case 4:
	                                		text.addStyleName("ks-four-width");
	                                		break;
	                                }
                                }
                                else if(config.maxLength < 23){
                                	text.addStyleName("ks-small-width");
                                }
                                else if(config.maxLength < 35){
                                	text.addStyleName("ks-medium-width");
                                }
                                else if(config.maxLength < 60){
                                	text.addStyleName("ks-large-width");
                                }
                                else{
                                	text.addStyleName("ks-extra-large-width");
                                }
                            }
                            else{
                            	text.addStyleName("ks-medium-width");
                            }
                            
                            result = text;
                        }
                    }
		    }
        }
		return result;
	}
}
