package org.kuali.student.r1.common.dictionary.dto;

import java.io.Serializable;
import java.util.ArrayList;




@Deprecated
public class CommonLookupParam  implements Serializable {


	private static final long serialVersionUID = 1L;
    private String key;
    private CommonLookup childLookup;    
    private WriteAccess writeAccess; 
    private DataType dataType;    
    private boolean optional;
    
    private ArrayList<String> defaultValueList;
    private String defaultValueString;
    
    private String name;      
    private String desc;    
    private boolean caseSensitive;
    private Usage usage;
	protected String fieldPath;
	protected String fieldPathMessageId;

    public enum Widget {
        SUGGEST_BOX, DROPDOWN_LIST, RADIO_BUTTONS, CHECK_BOXES, TEXT_BOX, CALENDAR, PICKER
    }

    public enum WriteAccess {
        ON_CREATE, /* must also be required */
        ALWAYS, NEVER, WHEN_NULL, REQUIRED
    }
    
    public enum Usage {
        DEFAULT, ADVANCED, CUSTOM, ADVANCED_CUSTOM
    }
    private Widget widget;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public CommonLookup getChildLookup() {
		return childLookup;
	}

	public void setChildLookup(CommonLookup childLookup) {
		this.childLookup = childLookup;
	}

	public WriteAccess getWriteAccess() {
		return writeAccess;
	}

	public void setWriteAccess(WriteAccess writeAccess) {
		this.writeAccess = writeAccess;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public ArrayList<String> getDefaultValueList() {
		return defaultValueList;
	}

	public void setDefaultValueList(ArrayList<String> defaultValueList) {
		this.defaultValueList = defaultValueList;
	}

	public String getDefaultValueString() {
		return defaultValueString;
	}

	public void setDefaultValueString(String defaultValueString) {
		this.defaultValueString = defaultValueString;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public Usage getUsage() {
		return usage;
	}

	public void setUsage(Usage usage) {
		this.usage = usage;
	}

	public String getFieldPath() {
		return fieldPath;
	}

	public void setFieldPath(String fieldPath) {
		this.fieldPath = fieldPath;
	}

	public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
	}

	public String getFieldPathMessageId() {
		return fieldPathMessageId;
	}

	public void setFieldPathMessageId(String fieldPathMessageId) {
		this.fieldPathMessageId = fieldPathMessageId;
	}
}
