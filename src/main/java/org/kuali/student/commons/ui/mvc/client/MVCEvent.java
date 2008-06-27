package org.kuali.student.commons.ui.mvc.client;

import com.google.gwt.core.client.GWT;



public abstract class MVCEvent implements HasMetadata {
	public static final MVCEvent MVCEVENT = GWT.create(MVCEvent.class);
}
