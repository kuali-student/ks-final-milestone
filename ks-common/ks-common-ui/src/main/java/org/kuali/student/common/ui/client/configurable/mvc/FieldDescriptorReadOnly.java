package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.r1.common.assembly.data.Metadata;

import com.google.gwt.user.client.ui.Widget;

/**
 * Read only (ui) equivalent of FieldDescriptor.
 * @see FieldDescriptor
 * @author Kuali Student Team
 * 
 */
public class FieldDescriptorReadOnly extends FieldDescriptor{

	public FieldDescriptorReadOnly(String fieldKey, MessageKeyInfo messageKey,
			Metadata metadata) {
		super(fieldKey, messageKey, metadata);
	}
	
	public FieldDescriptorReadOnly(String fieldKey, MessageKeyInfo messageKey,
			Metadata metadata, Widget widget) {
		super(fieldKey, messageKey, metadata, widget);
	}

	
	@Override
	protected Widget createFieldWidget() {
    	if (metadata == null) {
	    	Widget result = new KSLabel();
	    	addStyleToWidget(result);
	    	return result;
    	} else {
    		Widget result = DefaultWidgetFactory.getInstance().getReadOnlyWidget(metadata);
    		addStyleToWidget(result);
    		return result;
    	}
	}
	
	@Override
	protected void addStyleToWidget(Widget w) {
    	if(fieldKey != null && !fieldKey.isEmpty()){
    		String style = this.fieldKey.replaceAll("/", "-");
    		style = style + "readOnly";
    		w.addStyleName(style);
    	}
	}

	@Override
    protected void setupField() {
	    // do nothing for read-only fields
    }
}
