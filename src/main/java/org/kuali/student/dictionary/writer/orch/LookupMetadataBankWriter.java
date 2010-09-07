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
package org.kuali.student.dictionary.writer.orch;

import org.kuali.student.dictionary.model.DictionaryModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import org.kuali.student.core.assembly.data.LookupMetadata;
//import org.kuali.student.core.assembly.data.LookupParamMetadata;
//import org.kuali.student.core.assembly.data.LookupResultMetadata;
import org.kuali.student.dictionary.model.OrchestrationModel;
import org.kuali.student.dictionary.writer.JavaClassWriter;
import org.kuali.student.dictionary.writer.StringQuoter;

/**
 *
 * @author nwright
 */
public class LookupMetadataBankWriter extends JavaClassWriter
{

 private DictionaryModel model;
 OrchestrationModel orchModel;
 private String directory;
 private String rootPackage;
 public static final String JAVA_CLASS_NAME = "LookupMetadataBank";

 public LookupMetadataBankWriter (DictionaryModel model,
                                  OrchestrationModel orchModel,
                                  String directory,
                                  String rootPackage)
 {
  super (directory, rootPackage, JAVA_CLASS_NAME);
  this.model = model;
  this.orchModel = orchModel;
  this.directory = directory;
  this.rootPackage = rootPackage;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  indentPrintln ("public class " + JAVA_CLASS_NAME);
  openBrace ();

  importsAdd (Map.class.getName ());
  importsAdd (HashMap.class.getName ());
//  importsAdd (LookupMetadata.class.getName ());
  indentPrintln ("public static final Map <String, LookupMetadata> LOOKUP_BANK = new HashMap ();");
  indentPrintln ("public static final Map <String, LookupMetadata> SEARCH_BANK = new HashMap ();");
  indentPrintln ("");
  indentPrintln ("// static initiliazer");
  indentPrintln ("static");
  openBrace ();
  indentPrintln ("LookupMetadata lookup = null;");
  indentPrintln ("LookupParamMetadata param = null;");
  indentPrintln ("LookupResultMetadata result = null;");
//  importsAdd (LookupMetadata.class.getName ());
//  importsAdd (LookupParamMetadata.class.getName ());
//  importsAdd (LookupResultMetadata.class.getName ());

  // basic conversion
//  List<LookupMetadata> lookupMetas = orchModel.getLookups ();
//
//  // now write them out
//  for (LookupMetadata lookupMeta : lookupMetas)
//  {
//   indentPrintln ("");
//   indentPrintln ("//");
//   indentPrintln ("// new lookup metadata");
//   indentPrintln ("lookup = new LookupMetadata ();");
//   new LookupMetadataWriter (this, "lookup", lookupMeta).write ();
//   indentPrintln ("SEARCH_BANK.put (lookup.getSearchTypeId ().toLowerCase (), lookup);");
//   indentPrintln ("LOOKUP_BANK.put (lookup.getId ().toLowerCase (), lookup);");
//  }
//
//  for (LookupMetadata lookupMeta : lookupMetas)
//  {
//   for (LookupParamMetadata param : lookupMeta.getParams ())
//   {
//    if (param.getChildLookup () != null)
//    {
//     String childLookupKey = param.getChildLookup ().getSearchTypeId ();
//     String lookupKey = lookupMeta.getId ();
//     String paramKey = param.getKey ();
//     indentPrintln ("");
//     indentPrintln ("// set childLookup " + childLookupKey);
//     indentPrintln ("// on " + lookupKey + "." + paramKey);
//     indentPrintln ("param = findParam (" + quote (lookupKey) + ", " +
//      quote (paramKey) + ");");
//     indentPrintln ("lookup = LOOKUP_BANK.get (" + quote (childLookupKey) +
//      ".toLowerCase ());");
//     indentPrintln ("param.setChildLookup (lookup);");
//    }
//   }
//  }
//  closeBrace (); // end static initializer
//
//  indentPrintln ("");
//  indentPrintln ("private static Date asDate (String value)");
//  openBrace ();
//  importsAdd (Date.class.getName ());
//  importsAdd (SimpleDateFormat.class.getName ());
//  //importsAdd (ParseException.class.getName ());
//  indentPrintln ("try");
//  openBrace ();
//  indentPrintln ("return new SimpleDateFormat (\"yyyy-MM-dd\").parse (value);");
//  closeBrace ();
//  indentPrintln ("catch (Exception e)");
//  openBrace ();
//  indentPrintln ("assert (false); // this should never happen");
//  closeBrace ();
//  indentPrintln ("return null;");
//  closeBrace ();
//
//  indentPrintln ("");
//  indentPrintln ("private static LookupParamMetadata findParam (String lookupKey, String paramKey)");
//  openBrace ();
//  indentPrintln ("return findParam (LOOKUP_BANK.get (lookupKey.toLowerCase()), paramKey);");
//  closeBrace ();
//
//  indentPrintln ("");
//  indentPrintln ("private static LookupParamMetadata findParam (LookupMetadata lookup, String paramKey)");
//  openBrace ();
//  indentPrintln ("for (LookupParamMetadata param : lookup.getParams ())");
//  openBrace ();
//  indentPrintln ("if (param.getKey ().equalsIgnoreCase (paramKey))");
//  openBrace ();
//  indentPrintln ("return param;");
//  closeBrace ();
//  closeBrace ();
//  indentPrintln ("return null;");
//  closeBrace ();
//
//  importsAdd (List.class.getName ());
//  importsAdd (ArrayList.class.getName ());
//
//  indentPrintln ("");
//  indentPrintln ("public static void setLookups (Metadata meta, String lookupKey)");
//  openBrace ();
//  indentPrintln ("List<LookupMetadata> list = findAdditional (lookupKey);");
//  indentPrintln ("LookupMetadata lookup = LOOKUP_BANK.get (lookupKey.toLowerCase ());");
//  indentPrintln ("if (lookup != null)");
//  openBrace ();
//  indentPrintln ("list.add (0, lookup);");
//  closeBrace ();
//  indentPrintln ("if (list.size () == 0)");
//  openBrace ();
//  indentPrintln ("meta.setInitialLookup (null);");
//  indentPrintln ("meta.setAdditionalLookups (list);");
//  indentPrintln ("return;");
//  closeBrace ();
//  indentPrintln ("if (list.size () == 1)");
//  openBrace ();
//  indentPrintln ("meta.setInitialLookup (list.get (0));");
//  indentPrintln ("meta.setAdditionalLookups (new ArrayList ());");
//  indentPrintln ("return;");
//  closeBrace ();
//  indentPrintln ("meta.setInitialLookup (list.get (0));");
//  indentPrintln ("List<LookupMetadata> additional = new ArrayList ();");
//  indentPrintln ("for(int i = 1; i < list.size(); i++)");
//  openBrace ();
//  indentPrintln ("additional.add (list.get (i));");
//  closeBrace ();
//  indentPrintln ("meta.setAdditionalLookups (additional);");
//  closeBrace ();
//
//  importsAdd (List.class.getName ());
//  importsAdd (ArrayList.class.getName ());
//  indentPrintln ("");
//  indentPrintln ("public static List<LookupMetadata> findAdditional (String lookupKey)");
//  openBrace ();
//  indentPrintln ("List<LookupMetadata> list = new ArrayList ();");
//  indentPrintln ("int sequence = 0;");
//  indentPrintln ("while (true)");
//  openBrace ();
//  indentPrintln ("sequence++;");
//  indentPrintln ("LookupMetadata meta = LOOKUP_BANK.get (lookupKey.toLowerCase () + \".additional.\" + sequence);");
//  indentPrintln ("if (meta == null)");
//  openBrace ();
//  indentPrintln ("return list;");
//  closeBrace ();
//  indentPrintln ("list.add (meta);");
//  closeBrace ();
//  closeBrace ();
//
//  closeBrace (); // end class
//
//  this.writeJavaClassAndImportsOutToFile ();
//  this.getOut ().close ();
// }
//
// private String quote (String str)
// {
//  return StringQuoter.quote (str);
 }

}

