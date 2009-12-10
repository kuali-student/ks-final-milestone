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
package org.kuali.student.lum.lu.assembly.data.client.refactorme;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.LookupImplMetadata;
import org.kuali.student.common.assembly.client.LookupMetadata;
import org.kuali.student.common.assembly.client.LookupParamMetadata;
import org.kuali.student.common.assembly.client.LookupResultMetadata;
import org.kuali.student.common.assembly.client.Metadata;


public class LookupMetadataBank
{
	public static final Map <String, LookupMetadata> LOOKUP_BANK = new HashMap ();
	public static final Map <String, LookupMetadata> SEARCH_BANK = new HashMap ();
	
	// static initiliazer
	static
	{
		LookupMetadata lookup = null;
		LookupParamMetadata param = null;
		LookupResultMetadata result = null;
		LookupImplMetadata impl = null;
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lu.lookup.admin.departments");
		lookup.setKey ("org.search.NameAndRelatedPerson");
		lookup.setName ("Search by name and/or related person");
		lookup.setDesc ("Returns a list of organizations with the specified name and with a specified person related to it");
		lookup.setResultReturnKey ("org.resultColumn.orgId");
		
		impl = new LookupImplMetadata ();
		impl.setService ("org");
		impl.setType ("JPQL");
		impl.setInfo ("SELECT org.id, org.type.id, org.state.id, org.shortName, org.longName"
	 + "FROM Org org"
	 + "WHERE (org.shortName like concat ('%', :org_queryParam_NameLookupField, '%')"
	 + "or  org.longName like concat ('%', :org_queryParam_NameLookupField, '%')"
	 + "and (org.type.id in (:org_queryParam_OrganizationTypes) "
	 + "or :org_queryParam_OrganizationTypes is null)"
	 + "and (org.state.id in (:org_queryParam_OrganizationStates) "
	 + "or :(:org_queryParam_OrganizationStates is null)"
	 + "AND TODO LAYER IN LOGIC TO RESTRICT BASED ON ORG-PERSON RELATION IF A PERSON ID IS SUPPLIED");
		lookup.setImpl (impl);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.NameLookupField");
		param.setName ("Name Lookup Field");
		param.setDesc ("Name of organization");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.personIds");
		param.setName ("Ids of a person who must be related to this org");
		param.setDesc ("The internal identifier of the person or peope");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.LIST);
		param.setOptional (true);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.orgPersonRelationTypes");
		param.setName ("Org person relation types to use to match the person");
		param.setDesc ("Type or types defining the relationship of the person to the org");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.LIST);
		param.setOptional (true);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.OrganizationTypes");
		param.setName ("Organization Types");
		param.setDesc ("List of organization types to search");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.LIST);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.OrganizationStates");
		param.setName ("Organization States");
		param.setDesc ("List of organization states to search");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.LIST);
		param.setOptional (true);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("kuali.queryParam.MaximumResults");
		param.setName ("Maximum Results");
		param.setDesc ("Special parameter that limits the number of result rows that this query will return.");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.INTEGER);
		param.setOptional (true);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.orgId");
		result.setName ("Organization Identifier");
		result.setDesc ("Identifier for the organization");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.orgType");
		result.setName ("Organization Type");
		result.setDesc ("Organization Type");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.orgState");
		result.setName ("Organization State");
		result.setDesc ("Organization State");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.orgShortName");
		result.setName ("Organization Short Name");
		result.setDesc ("Short name for the organization, recorded as the default listing");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.orgLongName");
		result.setName ("Organization Long Name");
		result.setDesc ("Long name for the organization, recorded as the default listing");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lu.lookup.proposers");
		lookup.setKey ("person.search.personQuickViewByGivenName");
		lookup.setName ("All org hierarchies");
		lookup.setDesc ("Returns all org hierarchies, name and id");
		lookup.setResultReturnKey ("person.resultColumn.PersonId");
		
		impl = new LookupImplMetadata ();
		impl.setService ("person");
		impl.setType ("SPECIAL");
		impl.setInfo ("I THINK THIS IS HARD CODED VIA KIM");
		lookup.setImpl (impl);
		
		param = new LookupParamMetadata ();
		param.setKey ("person.queryParam.personGivenName");
		param.setName ("Name Lookup Field");
		param.setDesc ("Name of organization");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		lookup.getParams ().add (param);
		
		result = new LookupResultMetadata ();
		result.setKey ("person.resultColumn.PersonId");
		result.setName ("Internal person id");
		result.setDesc ("Internal id");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("person.resultColumn.GivenName");
		result.setName ("Given name of person");
		result.setDesc ("Internal id");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
	}
	
	private static Date asDate (String value)
	{
		try
		{
			return new SimpleDateFormat ("yyyy-MM-dd").parse (value);
		}
		catch (ParseException e)
		{
			assert (false); // this should never happen
		}
		return null;
	}
	
	private static LookupParamMetadata findParam (String lookupKey, String paramKey)
	{
		return findParam (LOOKUP_BANK.get (lookupKey), paramKey);
	}
	
	private static LookupParamMetadata findParam (LookupMetadata lookup,String paramKey)
	{
		for (LookupParamMetadata param : lookup.getParams ())
		{
			if (param.getKey ().equalsIgnoreCase (paramKey))
			{
				return param;
			}
		}
		return null;
	}
}

