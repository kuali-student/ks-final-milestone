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
package org.kuali.student.lum.lu.assembly;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.dictionary.old.MetadataServiceImpl;

/**
 *
 * @author nwright
 */
public class OldMetadataLoaderTest
{

 public OldMetadataLoaderTest ()
 {
 }

 @BeforeClass
 public static void setUpClass ()
     throws Exception
 {
 }

 @AfterClass
 public static void tearDownClass ()
     throws Exception
 {
 }

 @Before
 public void setUp ()
 {
 }

 @After
 public void tearDown ()
 {
 }

 public static final String ORCH_DICTIONARY_CONFIG_LOCATION =
                            "classpath:lum-orchestration-dictionary.xml";

 @Test
 public void testOrchestrationDictionaryMetadata ()
 {
  MetadataServiceImpl metadataService =
                      new MetadataServiceImpl (ORCH_DICTIONARY_CONFIG_LOCATION);

  Metadata metadata =
           metadataService.getMetadata ("CreditCourseProposal", "default", "default");

  Map<String, Metadata> properties = metadata.getProperties ();
  assertTrue (properties.containsKey ("course"));
  metadata = properties.get ("course");

  properties = metadata.getProperties ();
  assertTrue (properties.containsKey ("formats"));
  metadata = properties.get ("formats");

  properties = metadata.getProperties ();
  assertTrue (properties.containsKey ("*"));
  metadata = properties.get ("*");

  properties = metadata.getProperties ();
  assertTrue (properties.containsKey ("activities"));

  metadata = metadataService.getMetadata ("joints", "default", "default");
  properties = metadata.getProperties ();

  metadata = properties.get ("_runtimeData");
  properties = metadata.getProperties ();
  assertTrue (properties.containsKey ("created"));
  
  metadata =
      metadataService.getMetadata ("BrowseCourseCatalog", "", "");
  properties = metadata.getProperties();
  assertTrue (properties.containsKey( ("bySubjectArea")));
  properties = metadata.getProperties();
  assertTrue (properties.containsKey( ("bySchoolOrCollege")));
 }

}
