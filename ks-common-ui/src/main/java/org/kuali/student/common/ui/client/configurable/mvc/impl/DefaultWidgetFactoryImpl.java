/*
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
		} else if(!config.canEdit && config.lookupMeta == null) {
		    result = new KSLabel();
		} else {
		    if (config.lookupMeta != null) {
                if (config.metadata != null && MetadataInterrogator.isRepeating(config.metadata) && LookupMetadata.Widget.SUGGEST_BOX.equals(config.lookupMeta.getWidget())) {
                    result =  new KSSelectedList(config);
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
                        } else {
                            KSTextBox text = new KSTextBox();
                            if (config.maxLength != null) {
                                text.setMaxLength(config.maxLength);
                            }
                            result = text;
                        }
                    }
		    }
        }
		return result;
	}


}
