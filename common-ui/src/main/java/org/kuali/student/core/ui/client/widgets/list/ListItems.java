package org.kuali.student.core.ui.client.widgets.list;

import java.util.List;

public interface ListItems {
	public int getItemCount();
	public String getItemText(String id);
	public String getItemAttribute(String id, String attrkey);
	public List<String> getItemIds();
}
