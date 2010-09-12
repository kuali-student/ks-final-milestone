/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.assembly.client;

import java.io.Serializable;

import org.kuali.student.common.assembly.client.Data.Value;
import org.kuali.student.common.assembly.client.Metadata.WriteAccess;

public class LookupParamMetadata implements Serializable {

	private static final long serialVersionUID = 1L;
	private String key;
	private LookupMetadata childLookup;
 private Metadata.WriteAccess writeAccess;
	private Data.DataType dataType;
	private boolean optional;
	private Value defaultValue;
	private String defaultValuePath;
 private String name;
	private String desc;
 private boolean caseSensitive;

	public LookupMetadata getChildLookup() {
		return childLookup;
	}

	public void setChildLookup(LookupMetadata childLookup) {
		this.childLookup = childLookup;
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

	public Value getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Value defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValuePath() {
		return defaultValuePath;
	}

	public void setDefaultValuePath(String defaultValuePath) {
		this.defaultValuePath = defaultValuePath;
	}

 public boolean isCaseSensitive ()
 {
  return caseSensitive;
 }

 public void setCaseSensitive (boolean caseSensitive)
 {
  this.caseSensitive = caseSensitive;
 }

}
