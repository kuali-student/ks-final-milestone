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
public class OrganizationLoaderModelExcelImpl implements OrganizationLoaderModel
{

 private SpreadsheetReader reader;

 public OrganizationLoaderModelExcelImpl (SpreadsheetReader reader)
 {
  this.reader = reader;
 }

 @Override
 public List<Organization> getOrganizations ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("Organizations");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new IllegalArgumentException (ex);
  }
  ExcelLoaderHelper helper = new ExcelLoaderHelper (worksheetReader);
  helper.setMaxStringSize (500);
  List<Organization> list = new ArrayList (worksheetReader.getEstimatedRows ());
  int row = 0;
  while (worksheetReader.next ())
  {
   Organization org = new Organization ();
   org.setShortName (helper.getFixup ("shortName"));
   if (org.getShortName () == null)
   {
    continue;
   }
   list.add (org);
   row ++;
   org.setGrouping (helper.getFixup ("grouping"));
   org.setShortName (helper.getFixup ("shortName"));
   org.setLongName (helper.getFixup ("longName"));
   org.setSortName (helper.getFixup ("sortName"));
   org.setSubjectArea1 (helper.getFixup ("subjectArea1"));
   org.setSubjectArea2 (helper.getFixup ("subjectArea2"));
   org.setShortDesc (helper.getFixup ("shortDesc"));
   org.setTypeName (helper.getFixup ("typeName"));
   org.setType (helper.getFixup ("type"));
   org.setState (helper.getFixup ("state"));
   org.setSource (helper.getFixup ("source"));
   org.setNotes (helper.getFixup ("notes"));
   org.setLongDesc (helper.getFixup ("longDesc"));
   org.setEffectiveDate (helper.getFixupDate ("effectiveDate"));
   org.setExpirationDate (helper.getFixupDate ("expirationDate"));
  }
  return list;
 }
}
