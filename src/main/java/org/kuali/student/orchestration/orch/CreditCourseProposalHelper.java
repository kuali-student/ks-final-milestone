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
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
import org.kuali.student.orchestration.base.RichTextInfoHelper;


public class CreditCourseProposalHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ID ("Id"),
		TYPE ("Type"),
		STATE ("State"),
		RATIONALE ("Rationale"),
		TITLE ("Title"),
		PROPOSER ("Proposer"),
		CREDIT_COURSE ("CreditCourse"),
		VERSION_IND ("VersionInd");
		
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
	
	public CreditCourseProposalHelper (Data data)
	{
		this.data = data;
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
	
	
	public void setType (String value)
	{
		data.set (Properties.TYPE.getKey (), value);
	}
	
	
	public String getType ()
	{
		return (String) data.get (Properties.TYPE.getKey ());
	}
	
	
	public void setState (String value)
	{
		data.set (Properties.STATE.getKey (), value);
	}
	
	
	public String getState ()
	{
		return (String) data.get (Properties.STATE.getKey ());
	}
	
	
	public void setRationale (RichTextInfoHelper value)
	{
		data.set (Properties.RATIONALE.getKey (), value.getData ());
	}
	
	
	public RichTextInfoHelper getRationale ()
	{
		return new RichTextInfoHelper ((Data) data.get (Properties.RATIONALE.getKey ()));
	}
	
	
	public void setTitle (String value)
	{
		data.set (Properties.TITLE.getKey (), value);
	}
	
	
	public String getTitle ()
	{
		return (String) data.get (Properties.TITLE.getKey ());
	}
	
	
	public void setProposer (String value)
	{
		data.set (Properties.PROPOSER.getKey (), value);
	}
	
	
	public String getProposer ()
	{
		return (String) data.get (Properties.PROPOSER.getKey ());
	}
	
	
	public void setCreditCourse (CreditCourseHelper value)
	{
		data.set (Properties.CREDIT_COURSE.getKey (), value.getData ());
	}
	
	
	public CreditCourseHelper getCreditCourse ()
	{
		return new CreditCourseHelper ((Data) data.get (Properties.CREDIT_COURSE.getKey ()));
	}
	
	
	public void setVersionInd (String value)
	{
		data.set (Properties.VERSION_IND.getKey (), value);
	}
	
	
	public String getVersionInd ()
	{
		return (String) data.get (Properties.VERSION_IND.getKey ());
	}
	
}

