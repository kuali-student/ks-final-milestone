/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.core.assembly.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;

import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.assembly.data.LookupMetadata.Usage;
import org.kuali.student.core.assembly.data.Metadata.WriteAccess;

@XmlAccessorType(XmlAccessType.FIELD)
public class LookupParamMetadata implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String key;
    private LookupMetadata childLookup;    
    private Metadata.WriteAccess writeAccess;    
    private Data.DataType dataType;    
    private boolean optional;
    
    @XmlAnyElement
    @XmlElementRefs({
        @XmlElementRef(type=Data.BooleanValue.class),
        @XmlElementRef(type=Data.StringValue.class),
        @XmlElementRef(type=Data.IntegerValue.class),
        @XmlElementRef(type=Data.DoubleValue.class),
        @XmlElementRef(type=Data.FloatValue.class),
        @XmlElementRef(type=Data.LongValue.class),
        @XmlElementRef(type=Data.ShortValue.class),
        @XmlElementRef(type=Data.DataValue.class),
        @XmlElementRef(type=Data.TimestampValue.class),
        @XmlElementRef(type=Data.TimeValue.class)
    })
    private Value defaultValue;
    
    private String name;      
    private String desc;    
    private boolean caseSensitive;
    private Usage usage;

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

    public Value getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Value defaultValue) {
        this.defaultValue = defaultValue;
    }

	@Override
	public String toString() {
		return "LookupParamMetadata[key=" + key + ", name=" + name
				+ ", caseSensitive=" + caseSensitive + ", childLookup="
				+ childLookup + ", dataType=" + dataType + ", defaultValue="
				+ defaultValue + ", optional=" + optional + ", usage=" + usage
				+ ", widget=" + widget + ", writeAccess=" + writeAccess + "]";
	}

}
