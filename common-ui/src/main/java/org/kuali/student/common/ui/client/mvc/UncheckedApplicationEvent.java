package org.kuali.student.common.ui.client.mvc;

/**
 * Type for events fired between controllers and views. UncheckedApplicationEvents are automatically propagated to the
 * topmost controller for handler registration and event dispatch. For events that do not automatically propagate to the
 * topmost controller, see ApplicationEvent
 * 
 * @author Kuali Student Team
 * @param <H>
 *            Handler type associated with the event
 */
public abstract class UncheckedApplicationEvent<H extends UncheckedApplicationEventHandler> extends ApplicationEvent<H> {

}
