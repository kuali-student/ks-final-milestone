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
package org.kuali.student.loader;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.dictionary.model.spreadsheet.SpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.WorksheetNotFoundException;
import org.kuali.student.dictionary.model.spreadsheet.WorksheetReader;


/**
 *
 * @author nwright
 */
public class EnumeratedValueLoaderModelExcelImpl implements EnumeratedValueLoaderModel
{

 private SpreadsheetReader reader;

 public EnumeratedValueLoaderModelExcelImpl (SpreadsheetReader reader)
 {
  this.reader = reader;
 }



 @Override
 public List<EnumeratedValue> getEnumeratedValues ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("EnumeratedValues");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new IllegalArgumentException (ex);
  }
  ExcelLoaderHelper helper = new ExcelLoaderHelper (worksheetReader);
  helper.setMaxStringSize (500);
  List<EnumeratedValue> list = new ArrayList (worksheetReader.getEstimatedRows ());
  int row = 0;
  while (worksheetReader.next ())
  {
   EnumeratedValue ev = new EnumeratedValue ();
   ev.setCode (helper.getFixup ("code"));
   if (ev.getCode () == null)
   {
    continue;
   }
   list.add (ev);
   row++;
   ev.setEnumerationKey (helper.getFixup ("enumerationKey"));
   ev.setCode (helper.getFixup ("code"));
   ev.setAbbrevValue (helper.getFixup ("abbrevValue"));
   ev.setValue (helper.getFixup ("value"));
   ev.setEffectiveDate (helper.getFixupDate ("effectiveDate"));
   ev.setExpirationDate (helper.getFixupDate ("expirationDate"));
   ev.setSortKey (helper.getFixupInteger ("sortKey"));
   ev.setContexts (helper.getFixup ("contexts"));
  }
  return list;
 }

}
