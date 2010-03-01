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
package org.kuali.student.lum.lu.assembly.data.client.refactorme;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.LookupImplMetadata;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.assembly.data.LookupResultMetadata;
import org.kuali.student.core.assembly.data.Metadata;


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
		lookup.setKey ("org.search.generic");
		lookup.setName ("Basic and Advanced Search");
		lookup.setDesc ("Query with multiple optional elements to satisfy most advanced pickers");
		lookup.setResultReturnKey ("org.resultColumn.orgId");
		lookup.setResultDisplayKey ("org.resultColumn.orgShortName");
		lookup.setResultSortKey ("org.resultColumn.orgShortName");
		lookup.setUsage (LookupMetadata.Usage.DEFAULT);
		
		impl = new LookupImplMetadata ();
		impl.setService ("organization");
		impl.setType ("JPQL");
		impl.setInfo ("SELECT org.id, org.shortName, org.longName, org.type.id FROM Org org");
		lookup.setImpl (impl);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.orgOptionalLongName");
		param.setName ("Organization Name");
		param.setDesc ("Long organization name.");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.DEFAULT);
		param.setWidget (LookupParamMetadata.Widget.SUGGEST_BOX);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.orgOptionalLocation");
		param.setName ("Location");
		param.setDesc ("Organization location.");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.ADVANCED);
		param.setWidget (LookupParamMetadata.Widget.TEXT_BOX);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.orgOptionalId");
		param.setName ("Id");
		param.setDesc ("Unique identifier for an organization.");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.CUSTOM);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.orgOptionalShortName");
		param.setName ("Organization Name");
		param.setDesc ("Short organization name.");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.ADVANCED);
		param.setWidget (LookupParamMetadata.Widget.SUGGEST_BOX);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.orgOptionalType");
		param.setName ("Org Type");
		param.setDesc ("Type of an organization.");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setDefaultValue (new Data.StringValue ("kuali.org.Department"));
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.orgId");
		result.setName ("Organization Identifier");
		result.setDesc ("Identifier for the organization");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.orgShortName");
		result.setName ("Organization Short Name");
		result.setDesc ("Short name for the organization, recorded as the default listing");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.orgOptionalLongName");
		result.setName ("Name");
		result.setDesc ("Long name for the organization, recorded as the default listing.");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.orgType");
		result.setName ("Organization Type");
		result.setDesc ("Organization Type");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.active.org.types");
		lookup.setKey ("org.search.all.active.org.types");
		lookup.setName ("All Organization Types");
		lookup.setDesc ("Returns all active organization types");
		lookup.setResultReturnKey ("org.resultColumn.key");
		lookup.setResultDisplayKey ("org.resultColumn.name");
		lookup.setResultSortKey ("org.resultColumn.name");
		lookup.setUsage (LookupMetadata.Usage.DEFAULT);
		
		impl = new LookupImplMetadata ();
		impl.setService ("organization");
		impl.setType ("JPQL");
		impl.setInfo ("select key, name, desc, effective_date, expiration_date"
	 + "from OrgType"
	 + "where expiration_date is null");
		lookup.setImpl (impl);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.key");
		result.setName ("Key of the type");
		result.setDesc ("The internal key to the org type");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.name");
		result.setName ("Name of type");
		result.setDesc ("The name of the type");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.desc");
		result.setName ("Description of type");
		result.setDesc ("The description of the type");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.effective_date");
		result.setName ("Effective date");
		result.setDesc ("The date the ltype became effective");
		result.setDataType (Data.DataType.TRUNCATED_DATE);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.expiration_date");
		result.setName ("Expiration date");
		result.setDesc ("The date the type became non-effective");
		result.setDataType (Data.DataType.TRUNCATED_DATE);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lookup.people");
		lookup.setKey ("person.search.personQuickViewByGivenName");
		lookup.setName ("All org hierarchies");
		lookup.setDesc ("Returns all org hierarchies, name and id");
		lookup.setResultReturnKey ("person.resultColumn.PersonId");
		lookup.setResultDisplayKey ("person.resultColumn.GivenName");
		lookup.setResultSortKey ("person.resultColumn.GivenName");
		lookup.setUsage (LookupMetadata.Usage.DEFAULT);
		
		impl = new LookupImplMetadata ();
		impl.setService ("person");
		impl.setType ("SPECIAL");
		impl.setInfo ("I THINK THIS IS HARD CODED VIA KIM");
		lookup.setImpl (impl);
		
		param = new LookupParamMetadata ();
		param.setKey ("person.queryParam.personGivenName");
		param.setName ("Name Lookup Field");
		param.setDesc ("Name of person");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setWidget (LookupParamMetadata.Widget.SUGGEST_BOX);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("person.queryParam.personAffiliation");
		param.setName ("Affiliatilon Type");
		param.setDesc ("How the person is affiliated with the school");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setCaseSensitive (true);
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
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lu.lookup.oversight.orgs");
		lookup.setKey ("org.search.generic");
		lookup.setName ("Basic and Advanced Search");
		lookup.setDesc ("Query with multiple optional elements to satisfy most advanced pickers");
		lookup.setResultReturnKey ("org.resultColumn.orgId");
		lookup.setResultDisplayKey ("org.resultColumn.orgShortName");
		lookup.setResultSortKey ("org.resultColumn.orgShortName");
		lookup.setUsage (LookupMetadata.Usage.DEFAULT);
		
		impl = new LookupImplMetadata ();
		impl.setService ("organization");
		impl.setType ("JPQL");
		impl.setInfo ("SELECT org.id, org.shortName, org.longName, org.type.id FROM Org org");
		lookup.setImpl (impl);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.orgOptionalLongName");
		param.setName ("Organization Name");
		param.setDesc ("Long organization name.");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.DEFAULT);
		param.setWidget (LookupParamMetadata.Widget.CHECK_BOXES);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.orgOptionalLocation");
		param.setName ("Location");
		param.setDesc ("Organization location.");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.ADVANCED);
		param.setWidget (LookupParamMetadata.Widget.TEXT_BOX);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.orgOptionalId");
		param.setName ("Id");
		param.setDesc ("Unique identifier for an organization.");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.CUSTOM);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.orgOptionalShortName");
		param.setName ("Organization Name");
		param.setDesc ("Short organization name.");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.ADVANCED);
		param.setWidget (LookupParamMetadata.Widget.SUGGEST_BOX);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("org.queryParam.orgOptionalType");
		param.setName ("Org Type");
		param.setDesc ("Type of an organization.");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setDefaultValue (new Data.StringValue ("kuali.org.Program"));
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.orgId");
		result.setName ("Organization Identifier");
		result.setDesc ("Identifier for the organization");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.orgShortName");
		result.setName ("Organization Short Name");
		result.setDesc ("Short name for the organization, recorded as the default listing");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.orgOptionalLongName");
		result.setName ("Name");
		result.setDesc ("Long name for the organization, recorded as the default listing.");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("org.resultColumn.orgType");
		result.setName ("Organization Type");
		result.setDesc ("Organization Type");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lookup.termsOfferred");
		lookup.setKey ("atp.search.atpTypes");
		lookup.setName ("All ATP types");
		lookup.setDesc ("Returns the list of all ATP Types");
		lookup.setResultReturnKey ("atp.resultColumn.atpTypeId");
		lookup.setResultDisplayKey ("atp.resultColumn.atpTypeName");
		lookup.setResultSortKey ("atp.resultColumn.atpTypeName");
		lookup.setUsage (LookupMetadata.Usage.DEFAULT);
		
		impl = new LookupImplMetadata ();
		impl.setService ("atp");
		impl.setType ("JPQL");
		impl.setInfo ("SELECT atptype.id, atptype.name FROM AtpType atptype");
		lookup.setImpl (impl);
		
		result = new LookupResultMetadata ();
		result.setKey ("atp.resultColumn.atpTypeId");
		result.setName ("ATP Type Identifier");
		result.setDesc ("Identifier for an ATP Type");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("atp.resultColumn.atpTypeName");
		result.setName ("Type Name");
		result.setDesc ("Type of the ATP.");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lookup.durations");
		lookup.setKey ("atp.search.atpDurationTypes");
		lookup.setName ("All ATP Duration Types");
		lookup.setDesc ("Returns the list of all ATP Duration Types");
		lookup.setResultReturnKey ("atp.resultColumn.atpDurationTypeKey");
		lookup.setResultDisplayKey ("atp.resultColumn.atpDurationTypeName");
		lookup.setResultSortKey ("atp.resultColumn.atpDurationTypeName");
		lookup.setUsage (LookupMetadata.Usage.DEFAULT);
		
		impl = new LookupImplMetadata ();
		impl.setService ("atp");
		impl.setType ("JPQL");
		impl.setInfo ("TBD");
		lookup.setImpl (impl);
		
		result = new LookupResultMetadata ();
		result.setKey ("atp.resultColumn.atpDurationTypeKey");
		result.setName ("ATP Duration Type Key");
		result.setDesc ("ATP Duration Type Key");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("atp.resultColumn.atpDurationTypeName");
		result.setName ("Duration Type Name");
		result.setDesc ("Duration Type Name");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lu.lookup.creditCoursesAndProposals");
		lookup.setKey ("lu.search.generic");
		lookup.setName ("Basic and Advanced Search");
		lookup.setDesc ("Query with multiple optional elements to satisfy most advanced pickers");
		lookup.setResultReturnKey ("lu.resultColumn.cluId");
		lookup.setResultDisplayKey ("lu.resultColumn.luOptionalLongName");
		lookup.setResultSortKey ("lu.resultColumn.luOptionalLongName");
		lookup.setUsage (LookupMetadata.Usage.DEFAULT);
		
		impl = new LookupImplMetadata ();
		impl.setService ("lu");
		impl.setType ("JPQL");
		impl.setInfo ("TBD already in xml");
		lookup.setImpl (impl);
		
		param = new LookupParamMetadata ();
		param.setKey ("lu.queryParam.luOptionalId");
		param.setName ("Id");
		param.setDesc ("Unique identifier for a lu.");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.CUSTOM);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("lu.queryParam.luOptionalLongName");
		param.setName ("Name");
		param.setDesc ("Long name");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.DEFAULT);
		param.setWidget (LookupParamMetadata.Widget.SUGGEST_BOX);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("lu.queryParam.luOptionalType");
		param.setName ("Type");
		param.setDesc ("Lu type (course, program etc.)");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setDefaultValue (new Data.StringValue ("kuali.lu.type.CreditCourse"));
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("lu.queryParam.luOptionalCode");
		param.setName ("Code");
		param.setDesc ("Lu code");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.ADVANCED);
		param.setWidget (LookupParamMetadata.Widget.TEXT_BOX);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("lu.queryParam.luOptionalLevel");
		param.setName ("Level");
		param.setDesc ("Lu level grad/undergrad");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.ADVANCED);
		param.setWidget (LookupParamMetadata.Widget.TEXT_BOX);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		result = new LookupResultMetadata ();
		result.setKey ("lu.resultColumn.cluId");
		result.setName ("Clu Id");
		result.setDesc ("Identifier of a Clu");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lu.resultColumn.luOptionalLongName");
		result.setName ("Name");
		result.setDesc ("Long name for the lu, recorded as the default listing.");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lu.resultColumn.luOptionalCode");
		result.setName ("Code");
		result.setDesc ("Lu code");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lu.resultColumn.luOptionalLevel");
		result.setName ("Level");
		result.setDesc ("Level of Lu");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lu.lookup.activity.types");
		lookup.setKey ("lu.search.all.lu.types");
		lookup.setName ("All activity tlypes");
		lookup.setDesc ("All activity types");
		lookup.setResultReturnKey ("lu.resultColumn.key");
		lookup.setResultDisplayKey ("lu.resultColumn.name");
		lookup.setResultSortKey ("lu.resultColumn.name");
		lookup.setUsage (LookupMetadata.Usage.DEFAULT);
		
		impl = new LookupImplMetadata ();
		impl.setService ("lu");
		impl.setType ("JPQL");
		impl.setInfo ("select key, name, desc, effective_date, expiration_date"
	 + "from LuType"
	 + "where expiration_date is null");
		lookup.setImpl (impl);
		
		param = new LookupParamMetadata ();
		param.setKey ("lu.queryParam.luOptionalType");
		param.setName ("Type");
		param.setDesc ("Lu type (course, program etc.)");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setDefaultValue (new Data.StringValue ("kuali.lu.type.CreditCourse"));
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		result = new LookupResultMetadata ();
		result.setKey ("lu.resultColumn.key");
		result.setName ("Key of the type");
		result.setDesc ("The internal key to the org type");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lu.resultColumn.name");
		result.setName ("Name of type");
		result.setDesc ("The name of the type");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lu.resultColumn.desc");
		result.setName ("Description of type");
		result.setDesc ("The description of the type");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lu.resultColumn.effective_date");
		result.setName ("Effective date");
		result.setDesc ("The date the ltype became effective");
		result.setDataType (Data.DataType.TRUNCATED_DATE);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lu.resultColumn.expiration_date");
		result.setName ("Expiration date");
		result.setDesc ("The date the type became non-effective");
		result.setDataType (Data.DataType.TRUNCATED_DATE);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lookup.terms");
		lookup.setKey ("atp.search.advancedAtpSearch");
		lookup.setName ("Advanced ATP match");
		lookup.setDesc ("Search by Name, start date, end date, and type");
		lookup.setResultReturnKey ("atp.resultColumn.atpId");
		lookup.setResultDisplayKey ("atp.resultColumn.atpSeasonalType");
		lookup.setResultSortKey ("atp.resultColumn.atpSeasonalType");
		lookup.setUsage (LookupMetadata.Usage.DEFAULT);
		
		impl = new LookupImplMetadata ();
		impl.setService ("organization");
		impl.setType ("JPQL");
		impl.setInfo ("TBD");
		lookup.setImpl (impl);
		
		param = new LookupParamMetadata ();
		param.setKey ("atp.advancedAtpSearchParam.atpShortName");
		param.setName ("ATP Short Name");
		param.setDesc ("Short name for the ATP, recorded as the default listing");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.DEFAULT);
		param.setWidget (LookupParamMetadata.Widget.SUGGEST_BOX);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("atp.advancedAtpSearchParam.atpStartDate");
		param.setName ("Start Date");
		param.setDesc ("Start date of the ATP");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.TRUNCATED_DATE);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.ADVANCED);
		param.setWidget (LookupParamMetadata.Widget.CALENDAR);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("atp.advancedAtpSearchParam.atpEndDate");
		param.setName ("End Date");
		param.setDesc ("End date of the ATP.");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.TRUNCATED_DATE);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.CUSTOM);
		param.setWidget (LookupParamMetadata.Widget.CALENDAR);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("atp.advancedAtpSearchParam.atpType");
		param.setName ("Type");
		param.setDesc ("Type of the ATP");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setUsage (LookupMetadata.Usage.ADVANCED);
		param.setWidget (LookupParamMetadata.Widget.DROPDOWN_LIST);
		lookup.getParams ().add (param);
		
		result = new LookupResultMetadata ();
		result.setKey ("atp.resultColumn.atpId");
		result.setName ("ATP Identifier");
		result.setDesc ("Identifier for the ATP");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("atp.resultColumn.atpSeasonalType");
		result.setName ("Season Type");
		result.setDesc ("Will return Season Type.");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("atp.resultColumn.atpDurType");
		result.setName ("Duration Type");
		result.setDesc ("Will return Duration Type");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("atp.resultColumn.atpStartDate");
		result.setName ("Start Date");
		result.setDesc ("Start Date of the ATP");
		result.setDataType (Data.DataType.TRUNCATED_DATE);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("atp.resultColumn.atpTypeName");
		result.setName ("Type Name");
		result.setDesc ("Type of the ATP.");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lu.lookup.campusLocations");
		lookup.setKey ("enumeration.management.search");
		lookup.setName ("Get enumerations");
		lookup.setDesc ("Get enumerated lists of values given context");
		lookup.setResultReturnKey ("enumeration.resultColumn.code");
		lookup.setResultDisplayKey ("enumeration.resultColumn.value");
		lookup.setResultSortKey ("enumeration.resultColumn.sortKey");
		lookup.setUsage (LookupMetadata.Usage.DEFAULT);
		
		impl = new LookupImplMetadata ();
		impl.setService ("enumerationmanagement");
		impl.setType ("JPQL");
		impl.setInfo ("TBD");
		lookup.setImpl (impl);
		
		param = new LookupParamMetadata ();
		param.setKey ("enumeration.queryParam.enumerationType");
		param.setName ("Enumeration Type");
		param.setDesc ("The type of the enumeration to search");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setDefaultValue (new Data.StringValue ("kuali.lu.campusLocation"));
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("enumeration.queryParam.contextType");
		param.setName ("Context Type");
		param.setDesc ("The type of context being used");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("enumeration.queryParam.contextValue");
		param.setName ("Context Value");
		param.setDesc ("The value of the supplied context");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("enumeration.queryParam.contextDate");
		param.setName ("Context Date");
		param.setDesc ("A date supplied as context");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.DATE);
		param.setOptional (true);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		result = new LookupResultMetadata ();
		result.setKey ("enumeration.resultColumn.code");
		result.setName ("Code");
		result.setDesc ("The internal code of the enumeration value");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("enumeration.resultColumn.abbrevValue");
		result.setName ("Abbreviation of the code");
		result.setDesc ("An abbreviation of the enumeration value");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("enumeration.resultColumn.value");
		result.setName ("Description of the code");
		result.setDesc ("The description of the enumeration value");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("enumeration.resultColumn.effectiveDate");
		result.setName ("Effective date");
		result.setDesc ("The date the enumeration became effective");
		result.setDataType (Data.DataType.TRUNCATED_DATE);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("enumeration.resultColumn.expirationDate");
		result.setName ("Expiration date");
		result.setDesc ("The date the enumeration became non-effective");
		result.setDataType (Data.DataType.TRUNCATED_DATE);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("enumeration.resultColumn.sortKey");
		result.setName ("Key used to sort the results");
		result.setDesc ("The description of the type");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lu.lookup.subjectAreas");
		lookup.setKey ("enumeration.management.search");
		lookup.setName ("Get enumerations");
		lookup.setDesc ("Get enumerated lists of values given context");
		lookup.setResultReturnKey ("enumeration.resultColumn.code");
		lookup.setResultDisplayKey ("enumeration.resultColumn.abbrevValue");
		lookup.setResultSortKey ("enumeration.resultColumn.sortKey");
		lookup.setUsage (LookupMetadata.Usage.DEFAULT);
		
		impl = new LookupImplMetadata ();
		impl.setService ("enumerationmanagement");
		impl.setType ("JPQL");
		impl.setInfo ("TBD");
		lookup.setImpl (impl);
		
		param = new LookupParamMetadata ();
		param.setKey ("enumeration.queryParam.enumerationType");
		param.setName ("Enumeration Type");
		param.setDesc ("The type of the enumeration to search");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setDefaultValue (new Data.StringValue ("kuali.lu.subjectArea"));
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("enumeration.queryParam.contextType");
		param.setName ("Context Type");
		param.setDesc ("The type of context being used");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("enumeration.queryParam.contextValue");
		param.setName ("Context Value");
		param.setDesc ("The value of the supplied context");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.STRING);
		param.setOptional (true);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		param = new LookupParamMetadata ();
		param.setKey ("enumeration.queryParam.contextDate");
		param.setName ("Context Date");
		param.setDesc ("A date supplied as context");
		param.setWriteAccess (Metadata.WriteAccess.NEVER);
		param.setDataType (Data.DataType.DATE);
		param.setOptional (true);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		result = new LookupResultMetadata ();
		result.setKey ("enumeration.resultColumn.code");
		result.setName ("Code");
		result.setDesc ("The internal code of the enumeration value");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("enumeration.resultColumn.abbrevValue");
		result.setName ("Abbreviation of the code");
		result.setDesc ("An abbreviation of the enumeration value");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("enumeration.resultColumn.value");
		result.setName ("Description of the code");
		result.setDesc ("The description of the enumeration value");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("enumeration.resultColumn.effectiveDate");
		result.setName ("Effective date");
		result.setDesc ("The date the enumeration became effective");
		result.setDataType (Data.DataType.TRUNCATED_DATE);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("enumeration.resultColumn.expirationDate");
		result.setName ("Expiration date");
		result.setDesc ("The date the enumeration became non-effective");
		result.setDataType (Data.DataType.TRUNCATED_DATE);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("enumeration.resultColumn.sortKey");
		result.setName ("Key used to sort the results");
		result.setDesc ("The description of the type");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lookup.singleUseLos");
		lookup.setKey ("lo.search.loByClu");
		lookup.setName ("LO Search by Clu");
		lookup.setDesc ("Returns all Los connected with the selected clu");
		lookup.setResultReturnKey ("lo.resultColumn.loDescPlain");
		lookup.setResultDisplayKey ("lo.resultColumn.loDescPlain");
		lookup.setResultSortKey ("lo.resultColumn.loDescPlain");
		lookup.setUsage (LookupMetadata.Usage.ADVANCED);
		
		impl = new LookupImplMetadata ();
		impl.setService ("lo");
		impl.setType ("SPECIAL");
		impl.setInfo ("The \"get LO's by Course Number\" is implemented as follows:"
	 + "(1) Use a search to get the cluId"
	 + "(2) Call the getLoIdsByClu to get the Lo ID's "
	 + "(3) Calls getLoByIdList to get the info"
	 + "(4) Hard wires the mappings to to the list.");
		lookup.setImpl (impl);
		
		param = new LookupParamMetadata ();
		param.setKey ("lo.queryParam.cluId");
		param.setName ("Clu Id");
		param.setDesc ("Internal ID of a Clu");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setWidget (LookupParamMetadata.Widget.SUGGEST_BOX);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		result = new LookupResultMetadata ();
		result.setKey ("lo.resultColumn.loId");
		result.setName ("Lo Identifier");
		result.setDesc ("Identifier of an Lo");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lo.resultColumn.loDescPlain");
		result.setName ("Lo Desc");
		result.setDesc ("Desc of an Lo");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lookup.singleUseLos.additional.1");
		lookup.setKey ("lo.search.loCluByDesc");
		lookup.setName ("LO Search for all LOs and related Clus matching supplied word");
		lookup.setDesc ("Returns all matching LOs ids and related Clu ids and codes");
		lookup.setResultReturnKey ("lo.resultColumn.loDescPlain");
		lookup.setResultDisplayKey ("lo.resultColumn.loDescPlain");
		lookup.setResultSortKey ("lo.resultColumn.loDescPlain");
		lookup.setUsage (LookupMetadata.Usage.ADVANCED);
		
		impl = new LookupImplMetadata ();
		impl.setService ("lo");
		impl.setType ("JPQL");
		impl.setInfo ("NATIVE:SELECT "
	 + "    lo.id lo_id, "
	 + "    clu.id clu_id, "
	 + "    officialIdentifier.cd code, "
	 + "    lodesc.plain plain "
	 + "FROM "
	 + "    KSLU_CLU clu "
	 + "JOIN "
	 + "    KSLU_LO_JN_CLU jn "
	 + "    ON "
	 + "    jn.CLU_ID = clu.ID "
	 + "JOIN "
	 + "    KSLU_LO lo "
	 + "    ON "
	 + "    jn.LO_ID = lo.ID "
	 + "JOIN "
	 + "    KSLU_CLU_IDENT officialIdentifier "
	 + "    ON "
	 + "    clu.OFFIC_CLU_ID = officialIdentifier.ID "
	 + "JOIN "
	 + "    KS_LO_RICH_TEXT_T lodesc "
	 + "    ON "
	 + "    lodesc.ID = lo.RT_DESCR_ID "
	 + "WHERE "
	 + "    lower(lodesc.PLAIN) like :lo_queryParam_loDescPlain");
		lookup.setImpl (impl);
		
		param = new LookupParamMetadata ();
		param.setKey ("lo.queryParam.loDescPlain");
		param.setName ("Description");
		param.setDesc ("no description supplied");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		result = new LookupResultMetadata ();
		result.setKey ("lo.resultColumn.loId");
		result.setName ("Lo Identifier");
		result.setDesc ("Identifier of an Lo");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lo.resultColumn.loCluId");
		result.setName ("Clu Identifier");
		result.setDesc ("Identifier of a Clu");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lo.resultColumn.loCluCode");
		result.setName ("Clu Code");
		result.setDesc ("Clu Code");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lo.resultColumn.loDescPlain");
		result.setName ("Lo Desc");
		result.setDesc ("Desc of an Lo");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lookup.singleUseLos.additional.2");
		lookup.setKey ("lo.search.loByCategory");
		lookup.setName ("LO Search for all LOs by matching category");
		lookup.setDesc ("Returns all matching LOs ids");
		lookup.setResultReturnKey ("lo.resultColumn.loDescPlain");
		lookup.setResultDisplayKey ("lo.resultColumn.loDescPlain");
		lookup.setResultSortKey ("lo.resultColumn.loDescPlain");
		lookup.setUsage (LookupMetadata.Usage.ADVANCED);
		
		impl = new LookupImplMetadata ();
		impl.setService ("lo");
		impl.setType ("JPQL");
		impl.setInfo ("NATIVE:SELECT "
	 + "    lo.id lo_id, "
	 + "    clu.id clu_id, "
	 + "    officialIdentifier.cd code, "
	 + "    lodesc.plain plain "
	 + "FROM "
	 + "    KSLU_CLU clu "
	 + "JOIN "
	 + "    KSLU_LO_JN_CLU jn "
	 + "    ON "
	 + "    jn.CLU_ID = clu.ID "
	 + "JOIN "
	 + "    KSLU_LO lo "
	 + "    ON "
	 + "    jn.LO_ID = lo.ID "
	 + "JOIN "
	 + "    KSLU_CLU_IDENT officialIdentifier "
	 + "    ON "
	 + "    clu.OFFIC_CLU_ID = officialIdentifier.ID "
	 + "JOIN "
	 + "    KS_LO_RICH_TEXT_T lodesc "
	 + "    ON "
	 + "    lodesc.ID = lo.RT_DESCR_ID "
	 + "JOIN "
	 + "    KSLU_LO_JN_LOCATEGORY jncat"
	 + "    ON"
	 + "    lo.id = jncat.lo_id"
	 + "JOIN"
	 + "    KSLU_LO_CATEGORY cat"
	 + "    ON"
	 + "    cat.id = jncat.locategory_id"
	 + ""
	 + "WHERE "
	 + "    lower(cat.name) like :lo_queryParam_loCategoryName");
		lookup.setImpl (impl);
		
		param = new LookupParamMetadata ();
		param.setKey ("lo.queryParam.loCategoryName");
		param.setName ("Category");
		param.setDesc ("no description supplied");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setWidget (LookupParamMetadata.Widget.SUGGEST_BOX);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		result = new LookupResultMetadata ();
		result.setKey ("lo.resultColumn.loId");
		result.setName ("Lo Identifier");
		result.setDesc ("Identifier of an Lo");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lo.resultColumn.loCluId");
		result.setName ("Clu Identifier");
		result.setDesc ("Identifier of a Clu");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lo.resultColumn.loCluCode");
		result.setName ("Clu Code");
		result.setDesc ("Clu Code");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lo.resultColumn.loDescPlain");
		result.setName ("Description");
		result.setDesc ("no description supplied");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		//
		// new lookup metadata
		lookup = new LookupMetadata ();
		lookup.setLookupKey ("kuali.lookup.lo.category");
		lookup.setKey ("lo.search.categories");
		lookup.setName ("LO Search for all categories");
		lookup.setDesc ("Returns all matching category names");
		lookup.setResultReturnKey ("lo.resultColumn.categoryId");
		lookup.setResultDisplayKey ("lo.resultColumn.categoryNameAndType");
		lookup.setResultSortKey ("lo.resultColumn.categoryNameAndType");
		lookup.setUsage (LookupMetadata.Usage.DEFAULT);
		
		impl = new LookupImplMetadata ();
		impl.setService ("lo");
		impl.setType ("JPQL");
		impl.setInfo ("SELECT cat.id, cat.name, cat.loCategoryType.name, cat.name||' - '||cat.loCategoryType.name FROM LoCategory cat WHERE cat.name like :lo_queryParam_loCategoryName or cat.id = :lo_queryParam_loCategoryId");
		lookup.setImpl (impl);
		
		param = new LookupParamMetadata ();
		param.setKey ("lo.queryParam.loCategoryName");
		param.setName ("Category");
		param.setDesc ("no description supplied");
		param.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		param.setDataType (Data.DataType.STRING);
		param.setCaseSensitive (true);
		lookup.getParams ().add (param);
		
		result = new LookupResultMetadata ();
		result.setKey ("lo.resultColumn.categoryId");
		result.setName ("Category ID");
		result.setDesc ("Category to be used for an LO");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lo.resultColumn.categoryName");
		result.setName ("Category");
		result.setDesc ("Category to be used for an LO");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lo.resultColumn.categoryType");
		result.setName ("Category");
		result.setDesc ("Category type for a category");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		
		result = new LookupResultMetadata ();
		result.setKey ("lo.resultColumn.categoryNameAndType");
		result.setName ("Category");
		result.setDesc ("Category name concatenated with category type");
		result.setDataType (Data.DataType.STRING);
		lookup.getResults ().add (result);
		SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);
		LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);
		
		// set childLookup atp.search.atpTypes
		// on kuali.lookup.terms.atp.advancedAtpSearchParam.atpType
		param = findParam ("kuali.lookup.terms", "atp.advancedAtpSearchParam.atpType");
		lookup = LOOKUP_BANK.get ("atp.search.atpTypes".toLowerCase ());
		param.setChildLookup (lookup);
		
		// set childLookup lu.search.generic
		// on kuali.lookup.singleUseLos.lo.queryParam.cluId
		param = findParam ("kuali.lookup.singleUseLos", "lo.queryParam.cluId");
		lookup = LOOKUP_BANK.get ("lu.search.generic".toLowerCase ());
		param.setChildLookup (lookup);
		
		// set childLookup lo.search.categories
		// on kuali.lookup.singleUseLos.additional.2.lo.queryParam.loCategoryName
		param = findParam ("kuali.lookup.singleUseLos.additional.2", "lo.queryParam.loCategoryName");
		lookup = LOOKUP_BANK.get ("lo.search.categories".toLowerCase ());
		param.setChildLookup (lookup);
	}
	
	private static Date asDate (String value)
	{
		try
		{
			return new SimpleDateFormat ("yyyy-MM-dd").parse (value);
		}
		catch (Exception e)
		{
			assert (false); // this should never happen
		}
		return null;
	}
	
	private static LookupParamMetadata findParam (String lookupKey, String paramKey)
	{
		return findParam (LOOKUP_BANK.get (lookupKey.toLowerCase()), paramKey);
	}
	
	private static LookupParamMetadata findParam (LookupMetadata lookup, String paramKey)
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
	
	public static void setLookups (Metadata meta, String lookupKey)
	{
		List<LookupMetadata> list = findAdditional (lookupKey);
		LookupMetadata lookup = LOOKUP_BANK.get (lookupKey.toLowerCase ());
		if (lookup != null)
		{
			list.add (0, lookup);
		}
		if (list.size () == 0)
		{
			meta.setInitialLookup (null);
			meta.setAdditionalLookups (list);
			return;
		}
		if (list.size () == 1)
		{
			meta.setInitialLookup (list.get (0));
			meta.setAdditionalLookups (new ArrayList ());
			return;
		}
		meta.setInitialLookup (list.get (0));
		List<LookupMetadata> additional = new ArrayList<LookupMetadata>();
		for(int i = 1; i < list.size(); i++) {
		    additional.add(list.get(i));
		}
		meta.setAdditionalLookups (additional);
	}
	
	public static List<LookupMetadata> findAdditional (String lookupKey)
	{
		List<LookupMetadata> list = new ArrayList ();
		int sequence = 0;
		while (true)
		{
			sequence++;
			LookupMetadata meta = LOOKUP_BANK.get (lookupKey.toLowerCase () + ".additional." + sequence);
			if (meta == null)
			{
				return list;
			}
			list.add (meta);
		}
	}
}

