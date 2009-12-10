package org.kuali.student.common.ui.client.configurable.mvc.impl;

import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.MetadataInterrogator;
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

	// TODO add any other fancy lookups in here
	@Override
	public Widget getWidget(FieldDescriptor field) {
		Widget result = null;
		Metadata meta = field.getMetadata();
		if (meta != null) {
			
			if (meta.getWriteAccess() == WriteAccess.NEVER) {
				result = new KSLabel();
			} else {
				switch (meta.getDataType()) {
				case BOOLEAN:
					result = new KSCheckBox();
					break;
					
				case DATE:
				case TRUNCATED_DATE:
					result = new KSDatePicker();
					break;
					
				case DATA:
					if (MetadataInterrogator.hasConstraint(meta, ConstraintIds.RICH_TEXT)) {
						result = new KSRichEditor();
					}
					break;
					
				}
			}
		}
		
		
		if (result == null) {
			// default to textbox or textarea
			if (MetadataInterrogator.isMultilined(meta)) {
				result = new KSTextArea();
			} else {
				KSTextBox text = new KSTextBox();
				Integer maxLength = MetadataInterrogator.getSmallestMaxLength(meta);
				if (maxLength != null) {
					text.setMaxLength(maxLength);
				}
				result = text;
			}
		}
		
		return result;
	}

}
