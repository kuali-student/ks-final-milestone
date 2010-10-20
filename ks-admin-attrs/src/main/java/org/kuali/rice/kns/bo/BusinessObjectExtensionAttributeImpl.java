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

package org.kuali.rice.kns.bo;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Id;

public class BusinessObjectExtensionAttributeImpl extends PersistableBusinessObjectBase implements BusinessObjectExtensionAttribute {
	private static final long serialVersionUID = 4389123837785324444L;

	@Id
	private String id;

	@Column(name="ATTR_NAME")
	private String name;
	
	@Column(name="ATTR_VALUE")
	private String value;
	
	private String ownerObjectId;

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getOwnerObjectId() {
		return ownerObjectId;
	}

	public void setOwnerObjectId(String ownerObjectId) {
		this.ownerObjectId = ownerObjectId;
	}

	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap m = new LinkedHashMap();

		m.put("id", this.id);

		return m;
	}
	
}
