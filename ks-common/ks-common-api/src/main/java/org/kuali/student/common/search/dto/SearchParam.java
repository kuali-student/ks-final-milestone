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

package org.kuali.student.common.search.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchParam implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String value;
	@XmlElement
	private List<String> listValue;
	@XmlAttribute
	private String key;

	public Object getValue() {
		if (value != null) {
			return value;
		} else {
			return listValue;
		}
	}

	public void setValue(String value) {
		this.value = value;
		listValue = null;
	}

	public void setValue(List<String> listValue) {
		this.listValue = listValue;
		value = null;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "SearchParam[key=" + key + ", value=" + value + ", listValue="
				+ listValue + "]";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchParam that = (SearchParam) o;

        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (listValue != null ? !listValue.equals(that.listValue) : that.listValue != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (listValue != null ? listValue.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }
}
