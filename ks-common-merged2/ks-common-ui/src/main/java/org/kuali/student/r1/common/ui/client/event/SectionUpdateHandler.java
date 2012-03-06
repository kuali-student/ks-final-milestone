package org.kuali.student.r1.common.ui.client.event;

import org.kuali.student.r1.common.ui.client.mvc.UncheckedApplicationEventHandler;

public interface SectionUpdateHandler extends UncheckedApplicationEventHandler {
	public void onSectionUpdate(SectionUpdateEvent event);
}
