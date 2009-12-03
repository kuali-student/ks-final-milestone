package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.configurable.mvc.impl.DefaultWidgetFactoryImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public abstract class DefaultWidgetFactory {
	private static final DefaultWidgetFactory instance = GWT.create(DefaultWidgetFactoryImpl.class);
	public static DefaultWidgetFactory getInstance() {
		return instance;
	}
	public abstract Widget getWidget(FieldDescriptor field);
}
 