package org.kuali.student.core.ui.client.widgets.list;

public interface ListItems {
	public int getItemCount();
	public String getItemText(int index);
	public String getItemAttribute(int index, String key);
}
