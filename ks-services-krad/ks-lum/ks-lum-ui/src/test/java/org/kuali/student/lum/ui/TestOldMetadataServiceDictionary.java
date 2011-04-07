/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.MetadataFormatter;
import org.kuali.student.core.assembly.dictionary.old.MetadataServiceImpl;
import org.kuali.student.lum.lu.assembly.OldMetadataLoaderTest;

public class TestOldMetadataServiceDictionary
{

 @Test
 public void testMetadataService ()
 {
  Set<String> startingClasses = new LinkedHashSet ();
  Map<String, Set<String>> types = new LinkedHashMap ();
  startingClasses.add ("course");
  startingClasses.add ("proposal");
  startingClasses.add ("BrowseCourseCatalog");
  startingClasses.add ("search");
  startingClasses.add ("cluset");

  //  startingClasses.add (StatementTreeViewInfo.class);

  Set<String> typesForClass = new LinkedHashSet ();

  MetadataServiceImpl metadataService =
                      new MetadataServiceImpl (OldMetadataLoaderTest.ORCH_DICTIONARY_CONFIG_LOCATION);

  Metadata metadata =
           metadataService.getMetadata ("CreditCourseProposal", "default", "default");
  Map<String, Metadata> properties = metadata.getProperties ();
  assertTrue (properties.containsKey ("course"));
  metadata = properties.get ("course");

  String outFile = "target/oldmetadata.txt";
  File file = new File (outFile);
  OutputStream outputStream = null;
  try
  {
   outputStream = new FileOutputStream (file, false);
  }
  catch (FileNotFoundException ex)
  {
   throw new RuntimeException (ex);
  }
  PrintStream out = new PrintStream (outputStream);
  out.println ("(!) This page was automatically generated on "
               + new Date ());
  out.println ("DO NOT UPDATE MANUALLY!");
  out.println ("");
  out.print (
      "This page represents a formatted view of the lum ui dictionary");
  for (String className: startingClasses)
  {
   out.println ("# " + className);
  }
  out.println ("");
  out.println ("----");
  out.println ("{toc}");
  out.println ("----");

  for (String className: startingClasses)
  {
//   out.println ("getting meta data for " + className);
   metadata =
   metadataService.getMetadata (className, "default", "default");
   assertNotNull (metadata);
   MetadataFormatter formatter = new MetadataFormatter (className,
                                                        metadata, null,
                                                        null, new HashSet (),
                                                        1);
   out.println (formatter.formatForWiki ());
   if (types.get (className) == null)
   {
    continue;
   }
   for (String type: types.get (className))
   {
    System.out.println ("*** Generating formatted version for " + type);
    metadata =
    metadataService.getMetadata (className, type, (String) "default");
    assertNotNull (metadata);
    formatter = new MetadataFormatter (className,
                                       metadata, type,
                                       null, new HashSet (),
                                       1);
    out.println (formatter.formatForWiki ());
   }
  }
 }

}
