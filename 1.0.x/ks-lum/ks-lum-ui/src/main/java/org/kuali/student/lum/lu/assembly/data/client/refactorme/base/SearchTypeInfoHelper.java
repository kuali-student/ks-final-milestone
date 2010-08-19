/*
 * Copyright 2010 The Kuali Foundation
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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.base;


import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;


public class SearchTypeInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		NAME ("name"),
		DESC ("desc"),
		SEARCH_RESULT_TYPE_INFO ("searchResultTypeInfo"),
		SEARCH_CRITERIA_TYPE_INFO ("searchCriteriaTypeInfo"),
		KEY ("key");
		
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
	
	private SearchTypeInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static SearchTypeInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new SearchTypeInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setName (String value)
	{
		data.set (Properties.NAME.getKey (), value);
	}
	
	
	public String getName ()
	{
		return (String) data.get (Properties.NAME.getKey ());
	}
	
	
	public void setDesc (String value)
	{
		data.set (Properties.DESC.getKey (), value);
	}
	
	
	public String getDesc ()
	{
		return (String) data.get (Properties.DESC.getKey ());
	}
	
	
	public void setSearchResultTypeInfo (SearchResultTypeInfoHelper value)
	{
		data.set (Properties.SEARCH_RESULT_TYPE_INFO.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public SearchResultTypeInfoHelper getSearchResultTypeInfo ()
	{
		return SearchResultTypeInfoHelper.wrap ((Data) data.get (Properties.SEARCH_RESULT_TYPE_INFO.getKey ()));
	}
	
	
	public void setSearchCriteriaTypeInfo (SearchCriteriaTypeInfoHelper value)
	{
		data.set (Properties.SEARCH_CRITERIA_TYPE_INFO.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public SearchCriteriaTypeInfoHelper getSearchCriteriaTypeInfo ()
	{
		return SearchCriteriaTypeInfoHelper.wrap ((Data) data.get (Properties.SEARCH_CRITERIA_TYPE_INFO.getKey ()));
	}
	
	
	public void setKey (String value)
	{
		data.set (Properties.KEY.getKey (), value);
	}
	
	
	public String getKey ()
	{
		return (String) data.get (Properties.KEY.getKey ());
	}
	
}

