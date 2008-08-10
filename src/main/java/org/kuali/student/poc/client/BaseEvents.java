package org.kuali.student.poc.client;


import org.kuali.student.commons.ui.mvc.client.MVCEvent;

import com.google.gwt.core.client.GWT;

public class BaseEvents {
	public static abstract class ShowView extends MVCEvent {}
	public static final ShowView SHOW_VIEW = GWT.create(ShowView.class);
}
