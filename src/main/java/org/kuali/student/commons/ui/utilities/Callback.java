package org.kuali.student.commons.ui.utilities;

public interface Callback<T extends Object> {
	public void onResult(T result);
}
