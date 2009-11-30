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
package org.kuali.student.orchestration.orch;


import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Data.Property;
import org.kuali.student.core.organization.assembly.data.client.VersionData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.RuntimeDataHelper.VersionProperties;


public class RuntimeDataHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		IS_CREATED ("isCreated"),
		IS_DELETED ("isDeleted"),
		IS_UPDATED ("isUpdated"),
		VERSIONS ("versions");
		
		private final String key;
		
		private Properties (final String key)
		{
			this.key = key;
		}
		
		@Override
		public String getKey ()
		{
			return this.key;
		}
	}
	private Data data;
	
	public RuntimeDataHelper (Data data)
	{
		this.data = data;
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setCreated (String value)
	{
		data.set (Properties.IS_CREATED.getKey (), value);
	}
	
	
	public String isCreated ()
	{
		return (String) data.get (Properties.IS_CREATED.getKey ());
	}
	
	
	public void setDeleted (String value)
	{
		data.set (Properties.IS_DELETED.getKey (), value);
	}
	
	
	public String isDeleted ()
	{
		return (String) data.get (Properties.IS_DELETED.getKey ());
	}
	
	
	public void setUpdated (String value)
	{
		data.set (Properties.IS_UPDATED.getKey (), value);
	}
	
	
	public String isUpdated ()
	{
		return (String) data.get (Properties.IS_UPDATED.getKey ());
	}
	
	
	public void setVersions (Data value)
	{
		data.set (Properties.VERSIONS.getKey (), value);
	}
	
	
	public Data getVersions ()
	{
		return (Data) data.get (Properties.VERSIONS.getKey ());
	}

	public enum VersionProperties implements PropertyEnum {
		TYPENAME("typeName"), ID("id"), VERSION_INDICATOR("versionIndicator");

		private final String key;

		private VersionProperties(final String key) {
			this.key = key;
		}

		@Override
		public String getKey() {
			return this.key;
		}		
	}
	
	public String getVersionIndicator() {
		String result = null;
		Data versions = getVersions();
		if (versions == null) {
			return null;
		}
		if (versions.size() > 0) {
			Data v = versions.get(0);
			if (v != null) {
				result = v.get(VersionProperties.VERSION_INDICATOR.getKey());
			}
		}
		return result;
	}

	public String getVersionIndicator(String typeName) {
		return getVersionIndicator(typeName, null);
	}
	
	public String getVersionIndicator(String typeName, String id) {
		Data version = getVersionData(typeName, id);
		if (version == null) {
			return null;
		} else {
			return version.get(VersionProperties.VERSION_INDICATOR.getKey());
		}
	}
	
	public Data getVersionData(String typeName, String id) {
		Data result = null;
		Data versions = getVersions();
		if (versions == null) {
			return null;
		}
		for (Property p : versions) {
			Data v = p.getValue();
			if (typeName.equals((String) v.get(VersionProperties.TYPENAME.getKey())) && (id == null || id.equals((String) v.get(VersionProperties.ID.getKey())))) {
				result = v;
				break;
			}
		}
		return result;
		
	}
	
	public void addVersionIndicator(String typeName, String id, String version) {
		Data existing = getVersionData(typeName, id);
		if (existing == null) {
			Data d = new Data();
			d.set(VersionProperties.TYPENAME.getKey(), typeName);
			d.set(VersionProperties.ID.getKey(), id);
			d.set(VersionProperties.VERSION_INDICATOR.getKey(), version);
			Data versions = getVersions();
			if (versions == null) {
				versions = new Data();
				setVersions(versions);
			}
			versions.add(d);
		} else {
			existing.set(VersionProperties.VERSION_INDICATOR.getKey(), version);
		}
	}
}

