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
package org.kuali.student.dictionary.model.impl;

import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import org.kuali.student.core.assembly.data.Data;
//import org.kuali.student.core.assembly.data.LookupMetadata;
//import org.kuali.student.core.assembly.data.LookupParamMetadata;
//import org.kuali.student.core.assembly.data.LookupResultMetadata;
//import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.dictionary.model.SearchCriteriaParameter;
import org.kuali.student.dictionary.model.SearchResultColumn;
import org.kuali.student.dictionary.model.SearchType;

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

// public LookupMetadata convert ()
// {
//  LookupMetadata lookupMeta = new LookupMetadata ();
//  lookupMeta.setSearchTypeId (asString (searchType.getKey ()));
//  lookupMeta.setId (searchType.getLookupKey ());
//  lookupMeta.setUsage (calcLookupUsage (searchType.getUsage ()));
//  lookupMeta.setName (asString (searchType.getName ()));
//  lookupMeta.setDesc (asString (searchType.getDescription ()));
//  for (SearchCriteriaParameter param : searchType.getSearchCriteria ().
//   getParameters ())
//  {
//   LookupParamMetadata paramMeta = new LookupParamMetadata ();
//   lookupMeta.getParams ().add (paramMeta);
//   paramMeta.setKey (asString (param.getKey ()));
//   paramMeta.setName (asString (param.getName ()));
//   paramMeta.setDesc (asString (param.getDescription ()));
//   paramMeta.setDataType (asDataType (param.getDataType ()));
//   paramMeta.setOptional (asBoolean (param.getOptional (), "optional"));
//   paramMeta.setCaseSensitive ( ! asBoolean (param.getCaseSensitive (), "ignore case"));
//   paramMeta.setWriteAccess (calcWriteAccess (asString (param.getWriteAccess ())));
//   paramMeta.setUsage (calcParamUsage (param.getUsage ()));
//   paramMeta.setWidget (calcParamWidget (param.getWidget ()));
//   // TODO: support default values as lists)
//   paramMeta.setDefaultValueString (calcParamDefaultValueString (param.getDefaultValue ()));
//  }
//
//  for (SearchResultColumn col :
//       searchType.getSearchResult ().getResultColumns ())
//  {
//   LookupResultMetadata resultMeta = new LookupResultMetadata ();
//   lookupMeta.getResults ().add (resultMeta);
//   resultMeta.setKey (asString (col.getKey ()));
//   resultMeta.setName (asString (col.getName ()));
//   resultMeta.setDesc (asString (col.getDescription ()));
//   resultMeta.setDataType (asDataType (col.getDataType ()));
//   if (asBoolean (col.getUsage (), "value"))
//   {
//    lookupMeta.setResultReturnKey (col.getKey ());
//   }
//   if (asBoolean (col.getUsage (), "display"))
//   {
//    lookupMeta.setResultDisplayKey (col.getKey ());
//   }
//   if (asBoolean (col.getUsage (), "sort"))
//   {
//    lookupMeta.setResultSortKey (col.getKey ());
//   }
//   resultMeta.setHidden (asBoolean (col.getUsage (), "hidden"));
//  }
//  if (lookupMeta.getResultDisplayKey () == null)
//  {
//   lookupMeta.setResultDisplayKey (lookupMeta.getResultReturnKey ());
//  }
//  if (lookupMeta.getResultSortKey () == null)
//  {
//   lookupMeta.setResultSortKey (lookupMeta.getResultDisplayKey ());
//  }
//  return lookupMeta;
// }
//
// private String calcParamDefaultValueString (String value)
// {
//  if (value == null)
//  {
//   return null;
//  }
//  if (value.equals (""))
//  {
//   return null;
//  }
//  return value;
// }
//
//
// private Metadata.WriteAccess calcWriteAccess (String value)
// {
//  if (value.equalsIgnoreCase ("Always"))
//  {
//   return Metadata.WriteAccess.ALWAYS;
//  }
//  if (value.equalsIgnoreCase ("Never"))
//  {
//   return Metadata.WriteAccess.NEVER;
//  }
//  if (value.equalsIgnoreCase ("On Create"))
//  {
//   return Metadata.WriteAccess.ON_CREATE;
//  }
//  if (value.equalsIgnoreCase ("When Null"))
//  {
//   return Metadata.WriteAccess.WHEN_NULL;
//  }
//  throw new DictionaryValidationException ("search param " +
//   searchType.getKey () +
//   " has a parameter with an unknown/unhandled value for WriteAccess [" + value +
//   "]");
// }
//
// private LookupMetadata.Usage calcLookupUsage (String value)
// {
//  if (value.equalsIgnoreCase ("Default"))
//  {
//   return LookupMetadata.Usage.DEFAULT;
//  }
//  if (value.equalsIgnoreCase ("Custom"))
//  {
//   return LookupMetadata.Usage.CUSTOM;
//  }
//  if (value.equalsIgnoreCase ("Advanced"))
//  {
//   return LookupMetadata.Usage.ADVANCED;
//  }
//
//  throw new DictionaryValidationException ("search " +
//   searchType.getKey () +
//   " has a usage with an unknown/unhandled value [" + value +
//   "]");
// }
//
// private LookupMetadata.Usage calcParamUsage (String value)
// {
//  if (value == null)
//  {
//   return null;
//  }
//  if (value.equals (""))
//  {
//   return null;
//  }
//  if (value.equalsIgnoreCase ("Default"))
//  {
//   return LookupMetadata.Usage.DEFAULT;
//  }
//  if (value.equalsIgnoreCase ("Custom"))
//  {
//   return LookupMetadata.Usage.CUSTOM;
//  }
//  if (value.equalsIgnoreCase ("Advanced"))
//  {
//   return LookupMetadata.Usage.ADVANCED;
//  }
//    if (value.equalsIgnoreCase ("Advanced/Custom"))
//  {
//   return LookupMetadata.Usage.ADVANCED_CUSTOM;
//  }
//  throw new DictionaryValidationException ("search " +
//   searchType.getKey () +
//   " has a usage with an unknown/unhandled value [" + value +
//   "]");
// }
//
// private LookupParamMetadata.Widget calcParamWidget (String value)
// {
//  if (value == null)
//  {
//   return null;
//  }
//  if (value.equals (""))
//  {
//   return null;
//  }
//  if (value.equalsIgnoreCase ("Suggest Box"))
//  {
//   return LookupParamMetadata.Widget.SUGGEST_BOX;
//  }
//  if (value.equalsIgnoreCase ("Check Boxes"))
//  {
//   return LookupParamMetadata.Widget.CHECK_BOXES;
//  }
//  if (value.equalsIgnoreCase ("Dropdown List"))
//  {
//   return LookupParamMetadata.Widget.DROPDOWN_LIST;
//  }
//  if (value.equalsIgnoreCase ("Picker"))
//  {
//   return LookupParamMetadata.Widget.PICKER;
//  }
//  if (value.equalsIgnoreCase ("Radio Buttons"))
//  {
//   return LookupParamMetadata.Widget.RADIO_BUTTONS;
//  }
//  if (value.equalsIgnoreCase ("Calendar"))
//  {
//   return LookupParamMetadata.Widget.CALENDAR;
//  }
//  if (value.equalsIgnoreCase ("Text Box"))
//  {
//   return LookupParamMetadata.Widget.TEXT_BOX;
//  }
//
//  throw new DictionaryValidationException ("search " +
//   searchType.getKey () +
//   " has a parameter with an unknown/unhandled value for widget [" + value +
//   "]");
// }
//
// private Data.Value asDataValue (Data.DataType dataType, String value,
//                                 String key)
// {
//  if (value == null || value.equals (""))
//  {
//   return null;
//  }
//  switch (dataType)
//  {
//   case STRING:
//    return new Data.StringValue (asString (value));
//   case BOOLEAN:
//    return new Data.BooleanValue (asBoolean (value));
//   case INTEGER:
//    return new Data.IntegerValue (asInteger (value));
//   case DATE:
//    return new Data.DateValue (asDate (value));
//   case TRUNCATED_DATE:
//    return new Data.DateValue (asDate (value));
//    // TODO: parse lists on commas and insert each one
//   case LIST:
//    Data data = new Data (key);
//    data.add (value);
//    return new Data.DataValue (data);
//   default:
//    throw new DictionaryValidationException ("Defaults of type " + dataType +
//     " used in search type " +
//     searchType.getLookupKey () + " are not supported at this time.");
//
//  }
// }
//
// private Data.DataType asDataType (String value)
// {
//  if (value.equalsIgnoreCase ("string"))
//  {
//   return Data.DataType.STRING;
//  }
//  if (value.equalsIgnoreCase ("date"))
//  {
//   return Data.DataType.TRUNCATED_DATE;
//  }
//  if (value.equalsIgnoreCase ("dateTime"))
//  {
//   return Data.DataType.DATE;
//  }
//  if (value.equalsIgnoreCase ("boolean"))
//  {
//   return Data.DataType.BOOLEAN;
//  }
//  if (value.equalsIgnoreCase ("integer"))
//  {
//   return Data.DataType.INTEGER;
//  }
//  if (value.equalsIgnoreCase ("long"))
//  {
//   return Data.DataType.LONG;
//  }
//  if (value.toLowerCase ().startsWith ("list"))
//  {
//   return Data.DataType.LIST;
//  }
//  throw new DictionaryValidationException (
//   "Unknown/handled field type " +
//   value + " " + searchType.getLookupKey ());
// }

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
