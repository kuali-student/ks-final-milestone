package org.kuali.student.commons.ui.viewmetadata.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.commons.ui.messages.client.Messages;

public class ViewMetaData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4220576995454965057L;

	private String viewName;
	private Map<String, FieldMetaData> fields = new HashMap<String, FieldMetaData>();
	private Messages messages = null;
	
	public Messages getMessages() {
		return messages;
	}
	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	public ViewMetaData() {
		super();
	}
	public ViewMetaData(String viewName) {
		this.viewName = viewName;
	}
	
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public void addField(FieldMetaData fmd) {
		fmd.setParentView(this);
		fields.put(fmd.getFieldName(), fmd);
	}
	
	public Map<String, FieldMetaData> getFields() {
		return fields;
	}
	
	// TODO remove
	public String toString() {
		String result = viewName;
		for (String key : fields.keySet()) {
			result += "\n FIELD DEF: " + key + "\n" + fields.get(key).toString();
		}
		return result;
	}
	
}
