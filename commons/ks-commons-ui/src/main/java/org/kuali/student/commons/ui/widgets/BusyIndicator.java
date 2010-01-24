package org.kuali.student.commons.ui.widgets;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class BusyIndicator extends Composite {
	public static class TaskEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(TaskEvent.class, new TaskEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(TaskEvent.class);
        }
    }
	public static class BeginTask extends TaskEvent {
        static {
            EventTypeRegistry.register(BeginTask.class, new BeginTask().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(BeginTask.class);
        }
    }
	public static class EndTask extends TaskEvent {
        static {
            EventTypeRegistry.register(EndTask.class, new EndTask().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(EndTask.class);
        }
    }
	
    // force initialization of the event types we're aware of
    static {
        new TaskEvent();
        new BeginTask();
        new EndTask();
    }
	
	
	final SimplePanel panel = new SimplePanel();
	final Image indicator = new Image("images/busy.gif");
	int taskCount = 0;
	
	public BusyIndicator() {
		indicator.setVisible(false);
		panel.add(indicator);
		super.initWidget(panel);
		ApplicationContext.getGlobalEventDispatcher().addListener(BeginTask.class, new MVCEventListener() {
			public void onEvent(Class<? extends MVCEvent> event, Object data) {
				taskCount++;
				indicator.setVisible(true);
			}
		});
		ApplicationContext.getGlobalEventDispatcher().addListener(EndTask.class, new MVCEventListener() {
			public void onEvent(Class<? extends MVCEvent> event, Object data) {
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
