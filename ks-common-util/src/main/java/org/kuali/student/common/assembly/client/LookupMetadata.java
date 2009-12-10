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
import java.util.List;

public class LookupMetadata implements Serializable {

	private static final long serialVersionUID = 1L;
	private String key;
	private String name;
	private String desc;
	private List<LookupParamMetadata> params;
	private List<LookupResultMetadata> results;
	private LookupQosMetadata qosMetadata;
	private String resultReturnKey;
 private LookupImplMetadata impl;
 
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

	public List<LookupParamMetadata> getParams() {
		if (params == null) {
			params = new ArrayList<LookupParamMetadata>();
		}
		return params;
	}

	public void setParams(List<LookupParamMetadata> params) {
		this.params = params;
	}

	public List<LookupResultMetadata> getResults() {
		if (results == null) {
			results = new ArrayList<LookupResultMetadata>();
		}
		return results;
	}

	public void setResults(List<LookupResultMetadata> results) {
		this.results = results;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getResultReturnKey() {
		return resultReturnKey;
	}

	public void setResultReturnKey(String resultReturnKey) {
		this.resultReturnKey = resultReturnKey;
	}

	public LookupQosMetadata getQosMetadata() {
		return qosMetadata;
	}

	public void setQosMetadata(LookupQosMetadata qosMetadata) {
		this.qosMetadata = qosMetadata;
	}



 public LookupImplMetadata getImpl ()
 {
  return impl;
 }

 public void setImpl (LookupImplMetadata impl)
 {
  this.impl = impl;
 }

 private String lookupKey;

 public String getLookupKey ()
 {
  return lookupKey;
 }

 public void setLookupKey (String lookupKey)
 {
  this.lookupKey = lookupKey;
 }


}
