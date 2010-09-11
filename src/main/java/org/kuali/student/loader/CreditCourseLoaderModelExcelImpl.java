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
public class CreditCourseLoaderModelExcelImpl implements CreditCourseLoaderModel
{

 private SpreadsheetReader reader;

 public CreditCourseLoaderModelExcelImpl (SpreadsheetReader reader)
 {
  this.reader = reader;
 }



 @Override
 public List<CreditCourse> getCreditCourses ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("CreditCourses");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new IllegalArgumentException (ex);
  }
  ExcelLoaderHelper helper = new ExcelLoaderHelper (worksheetReader);
  helper.setMaxStringSize (500);
  List<CreditCourse> list = new ArrayList (worksheetReader.getEstimatedRows ());
  int row = 0;
  while (worksheetReader.next ())
  {
   CreditCourse cc = new CreditCourse ();
   cc.setCode (helper.getFixup ("code"));
   if (cc.getCode () == null)
   {
    continue;
   }
   list.add (cc);
   row++;
   cc.setSubjectArea (helper.getFixup ("subjectArea"));
   cc.setCourseNumberSuffix (helper.getFixup ("courseNumberSuffix"));
   cc.setVariation (helper.getFixup ("variation"));
   cc.setAdministeringOrgName (helper.getFixup ("AdministeringOrgName"));
   cc.setAdministeringOrg (helper.getFixup ("AdministeringOrgName"));
   cc.setTranscriptTitle (helper.getFixup ("TranscriptTitle"));
   cc.setCourseTitle (helper.getFixup ("CourseTitle"));
   cc.setDescr (helper.getFixup ("Descr"));
   cc.setCredits (helper.getFixup ("credits"));
   cc.setRestrictions (helper.getFixup ("restrictions"));
   cc.setPrereq (helper.getFixup ("prereq"));
   cc.setPrereqNL (helper.getFixup ("prereqNL"));
   cc.setCoreq (helper.getFixup ("coreq"));
   cc.setCoreqNL (helper.getFixup ("coreqNL"));
   cc.setEquivalencies (helper.getFixup ("equivalencies"));
   cc.setGradingOptions (helper.getFixup ("gradingOptions"));
   cc.setTermsOffered (helper.getFixup ("TermsOffered"));
   cc.setRequirementsMet (helper.getFixup ("requirementsMet"));
   cc.setLearningObjectives (helper.getFixup ("learningObjectives"));
  }
  return list;
 }

}
