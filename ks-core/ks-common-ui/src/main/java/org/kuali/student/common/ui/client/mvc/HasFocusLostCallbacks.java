package org.kuali.student.common.ui.client.mvc;

import com.google.gwt.user.client.ui.Widget;

public interface HasFocusLostCallbacks {
	public void addFocusLostCallback(Callback<Boolean> callback);
}
