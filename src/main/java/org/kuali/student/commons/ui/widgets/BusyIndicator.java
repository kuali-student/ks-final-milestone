package org.kuali.student.commons.ui.widgets;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class BusyIndicator extends Composite {
	public static abstract class TaskEvent extends MVCEvent {}
	public static abstract class BeginTask extends TaskEvent {}
	public static abstract class EndTask extends TaskEvent {}
	
	public static final TaskEvent TASK_EVENT = GWT.create(TaskEvent.class);
	public static final TaskEvent BEGIN_TASK = GWT.create(BeginTask.class);
	public static final TaskEvent END_TASK = GWT.create(EndTask.class);
	
	final SimplePanel panel = new SimplePanel();
	final Image indicator = new Image("images/busy.gif");
	int taskCount = 0;
	
	public BusyIndicator() {
		indicator.setVisible(false);
		panel.add(indicator);
		super.initWidget(panel);
		ApplicationContext.getGlobalEventDispatcher().addListener(BEGIN_TASK, new MVCEventListener() {
			public void onEvent(MVCEvent event, Object data) {
				taskCount++;
				indicator.setVisible(true);
			}
		});
		ApplicationContext.getGlobalEventDispatcher().addListener(END_TASK, new MVCEventListener() {
			public void onEvent(MVCEvent event, Object data) {
				taskCount--;
				if (taskCount < 0) {
					// should never happen, but selfcorrect if it does
					taskCount = 0;
				}
				indicator.setVisible(false);
			}
		});
	}
	
}
