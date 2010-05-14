package org.kuali.student.common.ui.client.configurable.mvc.impl;

import org.kuali.student.common.assembly.client.LookupMetadata;
import org.kuali.student.common.assembly.client.LookupParamMetadata;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.MetadataInterrogator;
import org.kuali.student.common.assembly.client.Data.DataType;
import org.kuali.student.common.assembly.client.Metadata.WriteAccess;
import org.kuali.student.common.assembly.client.MetadataInterrogator.ConstraintIds;
import org.kuali.student.common.ui.client.configurable.mvc.DefaultWidgetFactory;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;

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
			config.isRichText = MetadataInterrogator.hasConstraint(meta, ConstraintIds.RICH_TEXT);
			config.maxLength = MetadataInterrogator.getSmallestMaxLength(meta);
			config.type = meta.getDataType();
			config.lookupMeta = meta.getLookupMetadata();
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

	// TODO add any other fancy lookups in here
	private Widget _getWidget(WidgetConfigInfo config) {
		Widget result = null;
			
		if (config.access != null && config.access == WriteAccess.NEVER) {
			result = new KSLabel();
		} else {
			switch (config.type) {
			case BOOLEAN:
				result = new KSCheckBox();
				break;
				
			case DATE:
			case TRUNCATED_DATE:
				result = new KSDatePicker();
				break;
				
			case DATA:
				if (config.isRichText) {
					result = new KSRichEditor();
				}
				break;
				
			}
		}
		
		
		if (result == null) {
			// default to textbox or textarea
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
		
		return result;
	}


	class WidgetConfigInfo {
		public DataType type = null;
		public Integer maxLength = null;
		public WriteAccess access = null;
		public boolean isRichText = false;
		public boolean isMultiLine = false;
		public LookupMetadata lookupMeta = null;
	}
}
