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
package org.kuali.student.dictionary.model.impl;

import org.kuali.student.dictionary.model.spreadsheet.WorksheetNotFoundException;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.model.Service;
import org.kuali.student.dictionary.model.ServiceMethod;
import org.kuali.student.dictionary.model.ServiceMethodError;
import org.kuali.student.dictionary.model.ServiceContractModel;
import org.kuali.student.dictionary.model.ServiceMethodParameter;
import org.kuali.student.dictionary.model.ServiceMethodReturnValue;
import org.kuali.student.dictionary.model.ServiceMethodRow;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.spreadsheet.SpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.WorksheetReader;

/**
 * Loads a spreadsheet using either a google or excel reader
 * @author nwright
 */
public class ServiceContractModelLoader implements ServiceContractModel
{

 private SpreadsheetReader reader;

 public ServiceContractModelLoader (SpreadsheetReader reader)
 {
  this.reader = reader;
 }

 public List<ServiceMethod> getServiceMethods ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("Methods");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  List<ServiceMethod> list = new ArrayList ();
  ServiceMethod serviceMethod = null;
  int rowNumber = 1;
  String lastKey = "";
  while (worksheetReader.next ())
  {
   rowNumber ++;
   ServiceMethodRow row = new ServiceMethodRow ();
   loadRow (worksheetReader, row, rowNumber);
   if (isBlankRow (row))
   {
    continue;
   }
   String key = row.getKey ();
   if (key.equals (""))
   {
    key = lastKey;
   }
   lastKey = key;
   if (key.equalsIgnoreCase ("Method"))
   {
    serviceMethod = new ServiceMethod ();
    serviceMethod.setService (row.getService ());
    serviceMethod.setName (row.getShortName ());
    list.add (serviceMethod);
   }
   else if (key.equalsIgnoreCase ("Description"))
   {
    serviceMethod.setDescription (row.getShortName ());
   }
   else if (key.equalsIgnoreCase ("Parameters"))
   {
    if ( ! row.getShortName ().equalsIgnoreCase ("None"))
    {
     ServiceMethodParameter param = new ServiceMethodParameter ();
     param.setName (row.getLongName ());
     param.setType (row.getShortName ());
     param.setDescription (row.getDescription ());
     serviceMethod.getParameters ().add (param);
    }
   }
   else if (key.equalsIgnoreCase ("Return"))
   {
    ServiceMethodReturnValue returnValue = new ServiceMethodReturnValue ();
    returnValue.setType (row.getShortName ());
    returnValue.setDescription (row.getLongName ());
    serviceMethod.setReturnValue (returnValue);
   }
   else if (key.equalsIgnoreCase ("Errors"))
   {
    ServiceMethodError error = new ServiceMethodError ();
    error.setType (row.getShortName ());
    error.setDescription (row.getLongName ());
    serviceMethod.getErrors ().add (error);
   }
   else if (key.equalsIgnoreCase ("Capabilities"))
   {
    // all atp methods have these as empty so do nothing for now
   }
   else if (key.equalsIgnoreCase ("Use Cases"))
   {
    // all atp methods have these as empty so do nothing for now
   }
   else if (key.equalsIgnoreCase ("Comments/Feedback"))
   {
    // all atp methods have these as empty so do nothing for now
   }
   else
   {
    throw new DictionaryValidationException ("Spreadsheet row #" + rowNumber
     + " has an unknown key,[" + key + "]");
   }
  }
  return list;
 }

 private boolean isBlankRow (ServiceMethodRow row)
 {
  if ( ! row.getService ().equals (""))
  {
   return false;
  }
  if ( ! row.getKey ().equals (""))
  {
   return false;
  }
  if ( ! row.getShortName ().equals (""))
  {
   return false;
  }
  if ( ! row.getLongName ().equals (""))
  {
   return false;
  }
  if ( ! row.getDescription ().equals (""))
  {
   return false;
  }
  return true;
 }

 private void loadRow (WorksheetReader worksheetReader, ServiceMethodRow row,
                       int rowNumber)
 {
  row.setRowNumber (rowNumber);
  row.setService (getFixup (worksheetReader, "Service"));
  row.setKey (getFixup (worksheetReader, "Key"));
  row.setShortName (getFixup (worksheetReader, "ShortName"));
  row.setLongName (getFixup (worksheetReader, "LongName"));
  row.setDescription (getFixup (worksheetReader, "Description"));
 }

 private String get (WorksheetReader worksheetReader, String colName)
 {
  return get (worksheetReader, colName, null);
 }

 private String get (WorksheetReader worksheetReader,
                     String colName,
                     String colName2)
 {
  while (true)
  {
   if (worksheetReader.getIndex (colName) == -1)
   {
    if (colName2 != null)
    {
     colName = colName2;
    }
   }
   String value = worksheetReader.getValue (colName);
   if (value == null)
   {
    return "";
   }
   value = value.trim ();
   return value;
  }
 }

 private boolean getFixupBoolean (WorksheetReader reader, String name)
 {
  String str = getFixup (reader, name);
  if (str == null)
  {
   return false;
  }
  if (str.equals (""))
  {
   return false;
  }
  if (str.equalsIgnoreCase ("true"))
  {
   return true;
  }
  if (str.equalsIgnoreCase ("false"))
  {
   return false;
  }
  throw new DictionaryExecutionException ("Could not parse " + name
   + " as a boolean");
 }

 private String getFixup (WorksheetReader worksheetReader, String colName)
 {
  return fixup (get (worksheetReader, colName));
 }

 private String fixup (String str)
 {
  if (str == null)
  {
   return "";
  }
  // TODO: figure out why I am getting an unprintable character that gets translated to a 63 which is a ?
  //       when there is a calculation that returns null
  if (str.length () == 1)
  {
   byte[] bytes = str.getBytes ();
   if (bytes[0] == 63)
   {
    return "";
   }
  }
  if (str.equalsIgnoreCase ("FALSE"))
  {
   return "false";
  }
  if (str.equalsIgnoreCase ("TRUE"))
  {
   return "true";
  }
  return str;
 }

 @Override
 public List<String> getSourceNames ()
 {
  return Arrays.asList (reader.getSourceName ());
 }

 @Override
 public List<Service> getServices ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("Services");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  List<Service> list =
   new ArrayList (worksheetReader.getEstimatedRows ());
  while (worksheetReader.next ())
  {
   Service serv = new Service ();
   serv.setKey (getFixup (worksheetReader, "key"));
   if (serv.getKey ().equals (""))
   {
    continue;
   }
   serv.setVersion (getFixup (worksheetReader, "url"));
   serv.setVersion (getFixup (worksheetReader, "version"));
   serv.setStatus (getFixup (worksheetReader, "status"));
   if (serv.getStatus ().equals ("ignore"))
   {
    continue;
   }
   serv.setName (getFixup (worksheetReader, "name"));
   serv.setImplProject (getFixup (worksheetReader, "implProject"));
   serv.setComments (getFixup (worksheetReader, "comments"));
   list.add (serv);
  }
  return list;
 }

 @Override
 public List<XmlType> getXmlTypes ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("XmlTypes");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  List<XmlType> list = new ArrayList (worksheetReader.getEstimatedRows ());
  while (worksheetReader.next ())
  {
   XmlType info = new XmlType ();
   info.setName (getFixup (worksheetReader, "name"));
   if (info.getName ().equals (""))
   {
    continue;
   }
   info.setDesc (getFixup (worksheetReader, "desc"));
   info.setPrimitive (getFixup (worksheetReader, "primitive"));
   info.setHasOwnType (getFixupBoolean (worksheetReader, "hasOwnType"));
   info.setHasOwnState (getFixupBoolean (worksheetReader, "hasOwnState"));
   info.setHasOwnCreateUpdate (getFixupBoolean (worksheetReader, "hasOwnCreateUpdate"));
   info.setService (getFixup (worksheetReader, "service"));

   info.setJavaPackage (getFixup (worksheetReader, "javaPackage"));
   info.setExamples (getFixup (worksheetReader, "examples"));
   if ( ! info.getDesc ().equals ("ignore this row"))
   {
    list.add (info);
   }
   info.setComments (getFixup (worksheetReader, "comments"));
  }
  return list;
 }

 @Override
 public List<MessageStructure> getMessageStructures ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("MessageStructure");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  List<MessageStructure> list =
   new ArrayList (worksheetReader.getEstimatedRows ());
  while (worksheetReader.next ())
  {
   MessageStructure struct = new MessageStructure ();
   struct.setId (getFixup (worksheetReader, "id"));
   if (struct.getId ().equals (""))
   {
    continue;
   }
   struct.setDescription (getFixup (worksheetReader, "description"));
   if (struct.getDescription ().equals ("ignore this row"))
   {
    continue;
   }

   list.add (struct);
   struct.setXmlObject (getFixup (worksheetReader, "XmlObject"));
   struct.setShortName (getFixup (worksheetReader, "ShortName"));
   struct.setName (getFixup (worksheetReader, "Name"));
   struct.setType (getFixup (worksheetReader, "Type"));
   struct.setRequired (getFixup (worksheetReader, "Required"));
   struct.setCardinality (getFixup (worksheetReader, "Cardinality"));
   struct.setXmlAttribute (getFixup (worksheetReader, "XMLAttribute"));
   struct.setStatus (getFixup (worksheetReader, "status"));
   struct.setFeedback (getFixup (worksheetReader, "comments"));
  }
  return list;
 }

}
