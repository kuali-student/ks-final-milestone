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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validates the entire spreadsheet model
 * @author nwright
 */
public class OrchestrationModelValidator implements ModelValidator
{

 private DictionarySpreadsheet sheet;
 private SpreadsheetFinder finder;

 public OrchestrationModelValidator (DictionarySpreadsheet sheet)
 {
  this.sheet = sheet;
  this.finder = new SpreadsheetFinder (sheet);
 }

 List<String> errors;

 @Override
 public Collection<String> validate ()
 {
  errors = new ArrayList ();
  validateOrchObjs ();
  return errors;
 }

 private void validateOrchObjs ()
 {
  if (sheet.getOrchObjs ().size () == 0)
  {
   addError ("No orchestration objects found");
  }
  for (OrchObj orch : sheet.getOrchObjs ())
  {
   OrchObjValidator cv = new OrchObjValidator (orch);
   errors.addAll (cv.validate ());
  }
 }

 private void addError (String msg)
 {
  String error = "Error in orchestration dictionary spreadsheet: " + msg;
  if ( ! errors.contains (error))
  {
   errors.add (error);
  }
 }

}
