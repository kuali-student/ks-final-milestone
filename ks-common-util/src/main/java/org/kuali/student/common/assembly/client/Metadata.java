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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Metadata implements Serializable {
	// TODO this class, and referenced classes, need to be moved into a GWT
	// module

	private static final long serialVersionUID = 1L;

	public enum WriteAccess {

		ON_CREATE, /* must also be required */
		ALWAYS, 
  NEVER,
  WHEN_NULL
	}

	private WriteAccess writeAccess;
	private Data.DataType dataType;
	private Data.Value defaultValue;
 private String defaultValuePath;
	private List<ConstraintMetadata> constraints;
	private LookupMetadata lookupMetadata;
 private String lookupContextPath;
 private List<LookupMetadata> additionalLookups;
	private Map<String, Metadata> childProperties;


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		_toString(sb);
		return sb.toString();
	}
	
	protected void _toString(StringBuilder sb) {
		Data.DataType type = (dataType == null) ? Data.DataType.DATA : dataType;
		sb.append("Type: ");
		sb.append(type.toString());
		sb.append(", Default: ");
		sb.append(defaultValue == null ? "null" : defaultValue.toString());
		sb.append(", Properties: {" );
		if (childProperties != null) {
			for (Entry<String, Metadata> e : childProperties.entrySet()) {
				sb.append("(");
				sb.append(e.getKey());
				sb.append(" = ");
				Metadata m = e.getValue();
				if (m == null) {
					sb.append("null");
				} else {
					m._toString(sb);
				}
				sb.append(");");
			}
		}
		sb.append("}");
		// TODO dump lookup/constraint/etc info as well
	}
	
	public List<ConstraintMetadata> getConstraints() {
		if (constraints == null) {
			constraints = new ArrayList<ConstraintMetadata>();
		}
		return constraints;
	}

	public void setConstraints(List<ConstraintMetadata> constraints) {
		this.constraints = constraints;
	}

	public Data.DataType getDataType() {
		return dataType;
	}

	/**
	 * @deprecated
	 * @see #setDataType
	 */
	public void setDataType(String strType) {
		for (Data.DataType dt : Data.DataType.values()) {
			if (dt.toString().equalsIgnoreCase(strType)) {
				setDataType(dt);
				return;
			}
		}
		throw new IllegalArgumentException(strType);
	}

	public void setDataType(Data.DataType dataType) {
		this.dataType = dataType;
	}

	public Data.Value getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Data.Value defaultValue) {
		this.defaultValue = defaultValue;
	}

 public String getDefaultValuePath ()
 {
  return defaultValuePath;
 }

 public void setDefaultValuePath (String defaultValuePath)
 {
  this.defaultValuePath = defaultValuePath;
 }




	public LookupMetadata getLookupMetadata() {
		return lookupMetadata;
	}

 	public void setLookupMetadata(LookupMetadata lookupMetadata) {
		this.lookupMetadata = lookupMetadata;
	}


 public String getLookupContextPath ()
 {
  return lookupContextPath;
 }

 public void setLookupContextPath (String lookupContextPath)
 {
  this.lookupContextPath = lookupContextPath;
 }

 public List<LookupMetadata> getAdditionalLookups ()
 {
  if (additionalLookups == null) {
   additionalLookups = new ArrayList ();
  }
  return additionalLookups;
 }

 public void setAdditionalLookups (List<LookupMetadata> additionalLookups)
 {
  this.additionalLookups = additionalLookups;
 }


 public Map<String, Metadata> getProperties() {
		if (childProperties == null) {
			childProperties = new LinkedHashMap<String, Metadata>();
		}
		return childProperties;
	}

	public void setProperties(Map<String, Metadata> properties) {
		this.childProperties = properties;
	}

	public WriteAccess getWriteAccess() {
		return writeAccess;
	}

	public void setWriteAccess(WriteAccess writeAccess) {
		this.writeAccess = writeAccess;
	}

	private boolean onChangeRefereshMetadata;

	public boolean isOnChangeRefereshMetadata() {
		return onChangeRefereshMetadata;
	}

	public void setOnChangeRefereshMetadata(boolean onChangeRefereshMetadata) {
		this.onChangeRefereshMetadata = onChangeRefereshMetadata;
	}


	
}
