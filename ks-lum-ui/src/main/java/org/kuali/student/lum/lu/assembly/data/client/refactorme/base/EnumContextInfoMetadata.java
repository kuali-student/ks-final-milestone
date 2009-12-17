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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.base;


import java.util.HashMap;
import java.util.Map;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.ConstraintMetadataBank;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.EnumContextInfoHelper.Properties;


public class EnumContextInfoMetadata
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
		int recurseLevel = increment (recursions, "EnumContextInfoMetadata");
		
		Metadata childMeta;
		Metadata listMeta;
		
		// metadata for contextValueDescriptor
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.CONTEXT_VALUE_DESCRIPTOR.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new FieldDescriptorInfoMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for type
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.TYPE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ON_CREATE);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("kuali.type"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("dictionary.enumeration.context.keys"));
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

