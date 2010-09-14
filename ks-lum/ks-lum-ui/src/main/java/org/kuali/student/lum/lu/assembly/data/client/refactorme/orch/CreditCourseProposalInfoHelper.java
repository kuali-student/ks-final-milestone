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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch;


import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;
import org.kuali.student.lum.common.client.lo.MetaInfoHelper;


public class CreditCourseProposalInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ID ("id"),
		PROPOSER_PERSON ("proposerPerson"),
		TITLE ("title"),
		RATIONALE ("rationale"),
		REFERENCE_TYPE ("referenceType"),
		REFERENCES ("references"),
		META_INFO ("metaInfo"),
		_RUNTIME_DATA ("_runtimeData");
		
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
	
	private CreditCourseProposalInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static CreditCourseProposalInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CreditCourseProposalInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setId (String value)
	{
		data.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return (String) data.get (Properties.ID.getKey ());
	}
	
	
	public void setProposerPerson (Data value)
	{
		data.set (Properties.PROPOSER_PERSON.getKey (), value);
	}
	
	
	public Data getProposerPerson ()
	{
		return (Data) data.get (Properties.PROPOSER_PERSON.getKey ());
	}
	
	
	public void setTitle (String value)
	{
		data.set (Properties.TITLE.getKey (), value);
	}
	
	
	public String getTitle ()
	{
		return (String) data.get (Properties.TITLE.getKey ());
	}
	
	
	public void setRationale (String value)
	{
		data.set (Properties.RATIONALE.getKey (), value);
	}
	
	
	public String getRationale ()
	{
		return (String) data.get (Properties.RATIONALE.getKey ());
	}
	
	
	public void setReferenceType (String value)
	{
		data.set (Properties.REFERENCE_TYPE.getKey (), value);
	}
	
	
	public String getReferenceType ()
	{
		return (String) data.get (Properties.REFERENCE_TYPE.getKey ());
	}
	
	
	public void setReferences (Data value)
	{
		data.set (Properties.REFERENCES.getKey (), value);
	}
	
	
	public Data getReferences ()
	{
		return (Data) data.get (Properties.REFERENCES.getKey ());
	}
	
	
	public void setMetaInfo (MetaInfoHelper value)
	{
		data.set (Properties.META_INFO.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public MetaInfoHelper getMetaInfo ()
	{
		return MetaInfoHelper.wrap ((Data) data.get (Properties.META_INFO.getKey ()));
	}
	
	
	public void set_runtimeData (RuntimeDataHelper value)
	{
		data.set (Properties._RUNTIME_DATA.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public RuntimeDataHelper get_runtimeData ()
	{
		return RuntimeDataHelper.wrap ((Data) data.get (Properties._RUNTIME_DATA.getKey ()));
	}
	
}

