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


public class NewCreditCourseProposalHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ID ("Id"),
		PROPOSAL ("Proposal"),
		BASED_ON ("BasedOn");
		
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
	
	public NewCreditCourseProposalHelper (Data data)
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
	
	
	public void setProposal (CreditCourseProposalHelper value)
	{
		data.set (Properties.PROPOSAL.getKey (), value.getData ());
	}
	
	
	public CreditCourseProposalHelper getProposal ()
	{
		return new CreditCourseProposalHelper ((Data) data.get (Properties.PROPOSAL.getKey ()));
	}
	
	
	public void setBasedOn (CreditCourseHelper value)
	{
		data.set (Properties.BASED_ON.getKey (), value.getData ());
	}
	
	
	public CreditCourseHelper getBasedOn ()
	{
		return new CreditCourseHelper ((Data) data.get (Properties.BASED_ON.getKey ()));
	}
	
}

