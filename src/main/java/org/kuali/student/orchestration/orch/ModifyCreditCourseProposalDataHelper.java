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


import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class ModifyCreditCourseProposalDataHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ID ("Id"),
		PROPOSAL ("Proposal"),
		ORIGINAL ("Original");
		
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
	private ModifiableData data;
	
	public ModifyCreditCourseProposalDataHelper (ModifiableData data)
	{
		this.data = data;
	}
	
	public ModifiableData getData ()
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
	
	
	public void setProposal (CreditCourseProposalDataHelper value)
	{
		data.set (Properties.PROPOSAL.getKey (), value.getData ());
	}
	
	
	public CreditCourseProposalDataHelper getProposal ()
	{
		return new CreditCourseProposalDataHelper ((ModifiableData) data.get (Properties.PROPOSAL.getKey ()));
	}
	
	
	public void setOriginal (CreditCourseDataHelper value)
	{
		data.set (Properties.ORIGINAL.getKey (), value.getData ());
	}
	
	
	public CreditCourseDataHelper getOriginal ()
	{
		return new CreditCourseDataHelper ((ModifiableData) data.get (Properties.ORIGINAL.getKey ()));
	}
	
}

