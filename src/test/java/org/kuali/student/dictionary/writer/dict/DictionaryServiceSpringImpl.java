/*
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
package org.kuali.student.dictionary.writer.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * This is a copy of theone
 * @author nwright
 */
public class DictionaryServiceSpringImpl implements DictionaryService
{

 private String dictionaryContext;
 private Map<String, ObjectStructure> objectStructures;

 public DictionaryServiceSpringImpl ()
 {
  super ();
 }

 public DictionaryServiceSpringImpl (String dictionaryContext)
 {
  super ();
  this.dictionaryContext = dictionaryContext;
  init ();
 }

 @SuppressWarnings("unchecked")
 public void init ()
 {
  //ClassPathXmlApplicationContext
  ApplicationContext ac =
   new FileSystemXmlApplicationContext (dictionaryContext);

  Map<String, ObjectStructure> beansOfType
   = (Map<String, ObjectStructure>) ac.getBeansOfType (ObjectStructure.class);
  objectStructures = new HashMap<String, ObjectStructure> ();
  for (ObjectStructure objStr : beansOfType.values ())
  {
   objectStructures.put (objStr.getKey (), objStr);
  }
 }

 @Override
 public ObjectStructure getObjectStructure (String objectTypeKey)
 {
  return objectStructures.get (objectTypeKey);
 }

 @Override
 public List<String> getObjectTypes ()
 {
  return new ArrayList<String> (objectStructures.keySet ());
 }

 @Override
 public boolean validateObject (String objectTypeKey, String stateKey,
                                String info)
 {
  // TODO ddean - THIS METHOD NEEDS JAVADOCS
  return false;
 }

 @Override
 public boolean validateStructureData (String objectTypeKey, String stateKey,
                                       String info)
 {
  // TODO ddean - THIS METHOD NEEDS JAVADOCS
  return false;
 }

 public String getDictionaryContext ()
 {
  return dictionaryContext;
 }

 public void setDictionaryContext (String dictionaryContext)
 {
  this.dictionaryContext = dictionaryContext;
 }

}
