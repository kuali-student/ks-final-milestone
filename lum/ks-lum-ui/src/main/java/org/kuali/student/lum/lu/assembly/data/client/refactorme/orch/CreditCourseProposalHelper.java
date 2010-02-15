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


public class CreditCourseProposalHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		PROPOSAL ("proposal"),
		COURSE ("course"),
		STATE ("state"),
		TYPE ("type");
		
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
	
	private CreditCourseProposalHelper (Data data)
	{
		this.data = data;
	}
	
	public static CreditCourseProposalHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CreditCourseProposalHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setProposal (CreditCourseProposalInfoHelper value)
	{
		data.set (Properties.PROPOSAL.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public CreditCourseProposalInfoHelper getProposal ()
	{
		return CreditCourseProposalInfoHelper.wrap ((Data) data.get (Properties.PROPOSAL.getKey ()));
	}
	
	
	public void setCourse (CreditCourseHelper value)
	{
		data.set (Properties.COURSE.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public CreditCourseHelper getCourse ()
	{
		return CreditCourseHelper.wrap ((Data) data.get (Properties.COURSE.getKey ()));
	}
	
	
	public void setState (String value)
	{
		data.set (Properties.STATE.getKey (), value);
	}
	
	
	public String getState ()
	{
		return (String) data.get (Properties.STATE.getKey ());
	}
	
	
	public void setType (String value)
	{
		data.set (Properties.TYPE.getKey (), value);
	}
	
	
	public String getType ()
	{
		return (String) data.get (Properties.TYPE.getKey ());
	}
	
}

