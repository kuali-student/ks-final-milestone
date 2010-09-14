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


import java.util.Date;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;
import org.kuali.student.lum.common.client.lo.MetaInfoHelper;


public class ProposalInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		NAME ("name"),
		PROPOSER_PERSON ("proposerPerson"),
		PROPOSER_ORG ("proposerOrg"),
		PROPOSAL_REFERENCE_TYPE ("proposalReferenceType"),
		PROPOSAL_REFERENCE ("proposalReference"),
		RATIONALE ("rationale"),
		DETAIL_DESC ("detailDesc"),
		EFFECTIVE_DATE ("effectiveDate"),
		EXPIRATION_DATE ("expirationDate"),
		ATTRIBUTES ("attributes"),
		META_INFO ("metaInfo"),
		TYPE ("type"),
		STATE ("state"),
		ID ("id");
		
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
	
	private ProposalInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static ProposalInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new ProposalInfoHelper (data);
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
	
	
	public void setProposerPerson (Data value)
	{
		data.set (Properties.PROPOSER_PERSON.getKey (), value);
	}
	
	
	public Data getProposerPerson ()
	{
		return (Data) data.get (Properties.PROPOSER_PERSON.getKey ());
	}
	
	
	public void setProposerOrg (Data value)
	{
		data.set (Properties.PROPOSER_ORG.getKey (), value);
	}
	
	
	public Data getProposerOrg ()
	{
		return (Data) data.get (Properties.PROPOSER_ORG.getKey ());
	}
	
	
	public void setProposalReferenceType (String value)
	{
		data.set (Properties.PROPOSAL_REFERENCE_TYPE.getKey (), value);
	}
	
	
	public String getProposalReferenceType ()
	{
		return (String) data.get (Properties.PROPOSAL_REFERENCE_TYPE.getKey ());
	}
	
	
	public void setProposalReference (Data value)
	{
		data.set (Properties.PROPOSAL_REFERENCE.getKey (), value);
	}
	
	
	public Data getProposalReference ()
	{
		return (Data) data.get (Properties.PROPOSAL_REFERENCE.getKey ());
	}
	
	
	public void setRationale (String value)
	{
		data.set (Properties.RATIONALE.getKey (), value);
	}
	
	
	public String getRationale ()
	{
		return (String) data.get (Properties.RATIONALE.getKey ());
	}
	
	
	public void setDetailDesc (String value)
	{
		data.set (Properties.DETAIL_DESC.getKey (), value);
	}
	
	
	public String getDetailDesc ()
	{
		return (String) data.get (Properties.DETAIL_DESC.getKey ());
	}
	
	
	public void setEffectiveDate (Date value)
	{
		data.set (Properties.EFFECTIVE_DATE.getKey (), value);
	}
	
	
	public Date getEffectiveDate ()
	{
		return (Date) data.get (Properties.EFFECTIVE_DATE.getKey ());
	}
	
	
	public void setExpirationDate (Date value)
	{
		data.set (Properties.EXPIRATION_DATE.getKey (), value);
	}
	
	
	public Date getExpirationDate ()
	{
		return (Date) data.get (Properties.EXPIRATION_DATE.getKey ());
	}
	
	
	public void setAttributes (Data value)
	{
		data.set (Properties.ATTRIBUTES.getKey (), value);
	}
	
	
	public Data getAttributes ()
	{
		return (Data) data.get (Properties.ATTRIBUTES.getKey ());
	}
	
	
	public void setMetaInfo (MetaInfoHelper value)
	{
		data.set (Properties.META_INFO.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public MetaInfoHelper getMetaInfo ()
	{
		return MetaInfoHelper.wrap ((Data) data.get (Properties.META_INFO.getKey ()));
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
	
	
	public void setId (String value)
	{
		data.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return (String) data.get (Properties.ID.getKey ());
	}
	
}

