/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.assembly.data;

import java.io.Serializable;
import java.util.ArrayList;

import org.kuali.student.common.assembly.data.LookupMetadata.Usage;
import org.kuali.student.common.assembly.data.Metadata.WriteAccess;


public class LookupParamMetadata implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String key;
    private LookupMetadata childLookup;    
    private Metadata.WriteAccess writeAccess;    
    private Data.DataType dataType;    
    private boolean optional;
    
    private ArrayList<String> defaultValueList;
    private String defaultValueString;
    
    private String name;      
    private String desc;    
    private boolean caseSensitive;
    private Usage usage;

    //TODO none of these are used
    public enum Widget {
        SUGGEST_BOX, DROPDOWN_LIST, RADIO_BUTTONS, CHECK_BOXES, TEXT_BOX, CALENDAR, PICKER
    }

    private Widget widget;

    public LookupMetadata getChildLookup() {
        return childLookup;
    }

    public void setChildLookup(LookupMetadata childLookup) {
        this.childLookup = childLookup;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public Data.DataType getDataType() {
        return dataType;
    }

    public void setDataType(Data.DataType dataType) {
        this.dataType = dataType;
    }

    public WriteAccess getWriteAccess() {
        return writeAccess;
    }

    public void setWriteAccess(WriteAccess writeAccess) {
        this.writeAccess = writeAccess;
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

	@Override
	public String toString() {
		return "LookupParamMetadata[key=" + key + ", name=" + name
				+ ", caseSensitive=" + caseSensitive + ", childLookup="
				+ childLookup + ", dataType=" + dataType + ", defaultValue="
				+ defaultValueList==null?defaultValueString:defaultValueList.toString() + ", optional=" + optional + ", usage=" + usage
				+ ", widget=" + widget + ", writeAccess=" + writeAccess + "]";
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

}
