package org.kuali.student.common.ui.client.mvc;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Base event type for events fired between controllers and views. ApplicationEvent behavior is "checked", in that it does
 * not automatically propagate between controller tiers. To propagate an ApplicationEvent, code must be written in the
 * controller to manually relay it to the parent controller. For events that automatically propagate to the topmost
 * controller, see UncheckedApplicationEvent
 * 
 * @author Kuali Student Team
 * @param <H>
 *            The type of handler associated with the event
 */
public abstract class ApplicationEvent<H extends ApplicationEventHandler> extends GwtEvent<H> {

}
