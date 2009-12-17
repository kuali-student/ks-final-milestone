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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch;


import java.util.HashMap;
import java.util.Map;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.ConstraintMetadataBank;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.LookupMetadataBank;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoMetadata;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalInfoHelper.Properties;


public class CreditCourseProposalInfoMetadata
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
		Map <String, Integer> recursions = new HashMap ();
		loadChildMetadata (mainMeta, type, state, recursions);
		return mainMeta;
	}
	
	public void loadChildMetadata (Metadata mainMeta, String type, String state,  Map<String, Integer> recursions)
	{
		int recurseLevel = increment (recursions, "CreditCourseProposalInfoMetadata");
		
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
		}
		
		// metadata for ProposerPerson
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.PROPOSER_PERSON.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.setLookupMetadata (LookupMetadataBank.LOOKUP_BANK.get ("kuali.lu.lookup.proposers".toLowerCase ()));
		childMeta.setLookupContextPath ("");
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("related.personid"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.STRING);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		
		// metadata for Title
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.TITLE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single.line.text"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		
		// metadata for Rationale
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.RATIONALE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("multi.line.text"));
		}
		
		// metadata for ReferenceType
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.REFERENCE_TYPE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ON_CREATE);
		childMeta.setDefaultValue (new Data.StringValue ("kuali.referenceType.CLU"));
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("hard.coded.referencetype.clu"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("reference.types"));
		}
		
		// metadata for References
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.REFERENCES.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ON_CREATE);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("related.cluid"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("related.cluid"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.STRING);
		listMeta.setWriteAccess (Metadata.WriteAccess.ON_CREATE);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		
		// metadata for MetaInfo
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.META_INFO.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("kuali.meta.data"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new MetaInfoMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for _runtimeData
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties._RUNTIME_DATA.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new RuntimeDataMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
	}
	
	private int increment (Map<String, Integer> recursions, String key)
	{
		Integer recurseLevel = recursions.get (key);
		if (recurseLevel == null)
		{
			recursions.put (key, 0);
			return 0;
		}
		recursions.put (key, recurseLevel.intValue () + 1);
		return recurseLevel.intValue ();
	}
}

