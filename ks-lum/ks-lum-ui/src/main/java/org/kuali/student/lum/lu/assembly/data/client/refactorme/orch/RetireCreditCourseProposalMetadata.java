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
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.ConstraintMetadataBank;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.RecursionCounter;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.RetireCreditCourseProposalHelper.Properties;


public class RetireCreditCourseProposalMetadata
{
	
	public boolean matches (String inputType, String inputState, String dictType, String dictState)
	{
		// TODO: code more complex matches
		return true;
	}
	
	public Metadata getMetadata (String type, String state)
	{
		Metadata mainMeta = new Metadata ();
		mainMeta.setDataType (Data.DataType.DATA);
		mainMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		loadChildMetadata (mainMeta, type, state, new RecursionCounter ());
		return mainMeta;
	}
	
	public void loadChildMetadata (Metadata mainMeta, String type, String state,  RecursionCounter recursions)
	{
		if (recursions.decrement (this.getClass ().getName ()) < 0)
		{
			recursions.increment (this.getClass ().getName ());
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
			return;
		}
		
		Metadata childMeta;
		Metadata listMeta;
		
		// metadata for Id
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.ID.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("hidden"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("kuali.id"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("read.only"));
		}
		
		// metadata for Proposal
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.PROPOSAL.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ON_CREATE);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		new CreditCourseProposalMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		
		// metadata for Original
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.ORIGINAL.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ON_CREATE);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		new CreditCourseMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		
		recursions.increment (this.getClass ().getName ());
	}
}

