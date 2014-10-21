package org.kuali.student.common.ui.client.event;

import org.kuali.student.common.ui.client.mvc.UncheckedApplicationEventHandler;

public interface SectionUpdateHandler extends UncheckedApplicationEventHandler {
	public void onSectionUpdate(SectionUpdateEvent event);
}
