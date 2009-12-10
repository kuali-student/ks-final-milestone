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
package org.kuali.student.dictionary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.LookupImplMetadata;
import org.kuali.student.common.assembly.client.LookupMetadata;
import org.kuali.student.common.assembly.client.LookupParamMetadata;
import org.kuali.student.common.assembly.client.LookupResultMetadata;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.search.SearchCriteriaParameter;
import org.kuali.student.search.SearchResultColumn;
import org.kuali.student.search.SearchType;

/**
 *
 * @author nwright
 */
public class SearchTypeToLookupMetadataConverter
{

 private SearchType searchType;

 public SearchTypeToLookupMetadataConverter (SearchType searchType)
 {
  this.searchType = searchType;
 }

 public LookupMetadata convert ()
 {
  LookupMetadata lookupMeta = new LookupMetadata ();
  lookupMeta.setKey (asString (searchType.getKey ()));
  lookupMeta.setLookupKey (searchType.getLookupKey ());
  lookupMeta.setName (asString (searchType.getName ()));
  lookupMeta.setDesc (asString (searchType.getDescription ()));
  LookupImplMetadata impl = new LookupImplMetadata ();
  lookupMeta.setImpl (impl);
  impl.setService (asString (searchType.getService ()));
  impl.setType (asString (searchType.getImplementation ().getType ()));
  impl.setInfo (asString (searchType.getImplementation ().getDescription ()));

  for (SearchCriteriaParameter param : searchType.getSearchCriteria ().
   getParameters ())
  {
   LookupParamMetadata paramMeta = new LookupParamMetadata ();
   lookupMeta.getParams ().add (paramMeta);
   paramMeta.setKey (asString (param.getKey ()));
   paramMeta.setName (asString (param.getName ()));
   paramMeta.setDesc (asString (param.getDescription ()));
   paramMeta.setDataType (asDataType (param.getDataType ()));
   if (param.getDefaultValue ().equalsIgnoreCase ("Use LookupContextValue"))
   {
    paramMeta.setDefaultValue (asDataValue (paramMeta.getDataType (), param.
     getDefaultValue (), paramMeta.getKey ()));
    paramMeta.setDefaultValuePath (null);
   }
   else
   {
    paramMeta.setDefaultValue (null);
    paramMeta.setDefaultValuePath (null);
   }
   paramMeta.setOptional (asBoolean (param.getOptional (), "optional"));
   paramMeta.setCaseSensitive ( ! asBoolean (param.getCaseSensitive (), "ignore case"));
   paramMeta.setWriteAccess (calcWriteAccess (asString (param.getWriteAccess ())));
  }

  for (SearchResultColumn col :
       searchType.getSearchResult ().getResultColumns ())
  {
   LookupResultMetadata resultMeta = new LookupResultMetadata ();
   lookupMeta.getResults ().add (resultMeta);
   resultMeta.setKey (asString (col.getKey ()));
   resultMeta.setName (asString (col.getName ()));
   resultMeta.setDesc (asString (col.getDescription ()));
   resultMeta.setDataType (asDataType (col.getDataType ()));
   if (asBoolean (col.getReturnResult (), "return"))
   {
    lookupMeta.setResultReturnKey (col.getKey ());
   }
   resultMeta.setHidden (asBoolean (col.getReturnResult (), "hidden"));
  }
  return lookupMeta;
 }

 private Metadata.WriteAccess calcWriteAccess (String value)
 {
  if (value.equalsIgnoreCase ("Always"))
  {
   return Metadata.WriteAccess.ALWAYS;
  }
  if (value.equalsIgnoreCase ("Never"))
  {
   return Metadata.WriteAccess.NEVER;
  }
  if (value.equalsIgnoreCase ("OnCreate"))
  {
   return Metadata.WriteAccess.ON_CREATE;
  }
  if (value.equalsIgnoreCase ("WhenNull"))
  {
   return Metadata.WriteAccess.WHEN_NULL;
  }
  throw new DictionaryValidationException ("search param " +
   searchType.getKey () +
   " has a parameter with an unknown/unhandled value for WriteAccess [" + value +
   "]");
 }

 private Data.Value asDataValue (Data.DataType dataType, String value,
                                 String key)
 {
  switch (dataType)
  {
   case STRING:
    return new Data.StringValue (asString (value));
   case BOOLEAN:
    return new Data.BooleanValue (asBoolean (value));
   case INTEGER:
    return new Data.IntegerValue (asInteger (value));
   case DATE:
    return new Data.DateValue (asDate (value));
   case TRUNCATED_DATE:
    return new Data.DateValue (asDate (value));
   case LIST:
    Data data = new Data (key);
    data.add (value);
    return new Data.DataValue (data);
   default:
    throw new DictionaryValidationException ("Defaults of type " + dataType +
     " used in search type " +
     searchType.getLookupKey () + " are not supported at this time.");

  }
 }

 private Data.DataType asDataType (String value)
 {
  if (value.equalsIgnoreCase ("string"))
  {
   return Data.DataType.STRING;
  }
  if (value.equalsIgnoreCase ("date"))
  {
   return Data.DataType.TRUNCATED_DATE;
  }
  if (value.equalsIgnoreCase ("dateTime"))
  {
   return Data.DataType.DATE;
  }
  if (value.equalsIgnoreCase ("boolean"))
  {
   return Data.DataType.BOOLEAN;
  }
  if (value.equalsIgnoreCase ("integer"))
  {
   return Data.DataType.INTEGER;
  }
  if (value.equalsIgnoreCase ("long"))
  {
   return Data.DataType.LONG;
  }
  if (value.toLowerCase ().startsWith ("list"))
  {
   return Data.DataType.LIST;
  }
  throw new DictionaryValidationException (
   "Unknown/handled field type " +
   value + " " + searchType.getLookupKey ());
 }

 private String asString (String value)
 {
  if (value == null)
  {
   return null;
  }
  if (value.trim ().equals (""))
  {
   return null;
  }
  return value;
 }

 private boolean asBoolean (String value, String trueValue)
 {
  if (value == null)
  {
   return false;
  }
  if (value.equalsIgnoreCase (trueValue))
  {
   return true;
  }
  return false;
 }

 private Boolean asBoolean (String value)
 {
  return asBoolean (value, false);
 }

 private Boolean asBoolean (String value, Boolean defVal)
 {
  if (value == null)
  {
   return defVal;
  }
  if (value.trim ().equals (""))
  {
   return defVal;
  }
  if (value.equalsIgnoreCase ("true"))
  {
   return true;
  }
  if (value.equalsIgnoreCase ("false"))
  {
   return false;
  }
  throw new DictionaryValidationException (value + " in search type " +
   searchType.getLookupKey () + " could not be parsed as a boolean.");
 }

 private Integer asInteger (String value)
 {
  if (value == null)
  {
   return null;
  }
  if (value.trim ().equals (""))
  {
   return null;
  }
  try
  {
   return Integer.parseInt (value);
  }
  catch (NumberFormatException ex)
  {
   throw new DictionaryValidationException (value + " in search type " +
    searchType.getLookupKey () + " could not be parsed as an integer ");
  }
 }

 private Date asDate (String value)
 {
  if (value == null)
  {
   return null;
  }
  if (value.trim ().equals (""))
  {
   return null;
  }
  List<String> formats = new ArrayList ();
  formats.add ("yyyy-MM-dd");
  //TODO: Expand to othe formats?
  for (String format : formats)
  {
   try
   {
    return new SimpleDateFormat (format).parse (value);
   }
   catch (ParseException e)
   {
    // ignore because want to try different formats
   }
  }
  throw new DictionaryValidationException (value + " in search type " +
   searchType.getLookupKey () + " could not be parsed as an date ");
 }

}
