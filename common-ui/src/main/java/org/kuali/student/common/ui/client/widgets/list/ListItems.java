package org.kuali.student.common.ui.client.widgets.list;

import java.util.List;

import org.kuali.student.common.util.Callback;
import org.kuali.student.core.dto.Idable;

public interface ListItems<T extends Idable> {
	public int getItemCount();
	public String getItemText(String id);
	public String getItemAttribute(String id, String attrkey);
	public List<String> getAttrKeys();
	public List<String> getItemIds();
    void addOnAddCallback(Callback<T> callback);
    void addOnRemoveCallback(Callback<T> callback);
    void addOnUpdateCallback(Callback<T> callback);
}
