package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import org.kuali.student.common.ui.client.mvc.View;

public interface ViewLayoutController {
	public void addView(View view);
	public <V extends Enum<?>> void showView(final V viewType);
	public <V extends Enum<?>> void setDefaultView(V viewType);
	public void addStartViewPopup(final View view);
}
