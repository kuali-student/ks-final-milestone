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


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.ConstraintMetadataBank;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.LookupMetadataBank;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.SingleUseRepositoryCategoryHelper.Properties;


public class SingleUseRepositoryCategoryMetadata
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
		int recurseLevel = increment (recursions, "SingleUseRepositoryCategoryMetadata");
		
		Metadata childMeta;
		Metadata listMeta;
		
		// metadata for ID
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.I_D.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("hidden"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("read.only"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("kuali.id"));
		}
		
		// metadata for Name
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.NAME.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.setLookupMetadata (LookupMetadataBank.LOOKUP_BANK.get ("kuali.lookup.singleUseRepository.categories".toLowerCase ()));
		childMeta.setLookupContextPath ("");
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single.line.text"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		
		// metadata for LoRepository
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.LO_REPOSITORY.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		childMeta.setDefaultValue (new Data.StringValue ("kuali.loRepository.key.singleUse"));
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("hard.coded.singleuse.lo.repository"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("hard.coded.singleuse.lo.repository"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("lo.hierarchy.types"));
		}
		
		// metadata for TypeKey
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.TYPE_KEY.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("lo.category.types"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		
		// metadata for TypeName
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.TYPE_NAME.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		childMeta.setLookupMetadata (LookupMetadataBank.LOOKUP_BANK.get ("kuali.lookup.lo.category.types".toLowerCase ()));
		childMeta.setLookupContextPath ("loRepository");
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		
		// metadata for State
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.STATE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		childMeta.setDefaultValue (new Data.StringValue ("active"));
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		
		// metadata for EffectiveDate
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.EFFECTIVE_DATE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.TRUNCATED_DATE);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("date.time"));
		}
		
		// metadata for ExpirationDate
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.EXPIRATION_DATE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.TRUNCATED_DATE);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("date.time"));
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

