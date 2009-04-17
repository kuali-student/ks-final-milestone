package org.kuali.student.common.ui.client.widgets.list;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.dto.Idable;

public interface ListItems{
	public int getItemCount();
	public String getItemText(String id);
	public String getItemAttribute(String id, String attrkey);
	public List<String> getAttrKeys();
	public List<String> getItemIds();
}
