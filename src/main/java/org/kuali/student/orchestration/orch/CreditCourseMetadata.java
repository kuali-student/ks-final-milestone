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
package org.kuali.student.orchestration.orch;


import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.orchestration.base.RichTextInfoMetadata;
import org.kuali.student.orchestration.orch.CreditCourseHelper.Properties;


public class CreditCourseMetadata
{
	public Metadata getMetadata (String type, String state)
	{
		Metadata mainMeta = new Metadata ();
		mainMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		loadChildMetadata (mainMeta, type, state);
		return mainMeta;
	}
	
	public void loadChildMetadata (Metadata mainMeta, String type, String state)
	{
		Metadata childMeta;
		
		// metadata for Id
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.ID.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		
		// metadata for SubjectArea
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.SUBJECT_AREA.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		
		// metadata for CourseNumberSuffix
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.COURSE_NUMBER_SUFFIX.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		
		// metadata for TranscriptTitle
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.TRANSCRIPT_TITLE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		new RichTextInfoMetadata ().loadChildMetadata (childMeta, type, state);
		
		// metadata for CourseTitle
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.COURSE_TITLE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		new RichTextInfoMetadata ().loadChildMetadata (childMeta, type, state);
		
		// metadata for Description
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.DESCRIPTION.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		new RichTextInfoMetadata ().loadChildMetadata (childMeta, type, state);
		
		// metadata for TermsOffered
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.TERMS_OFFERED.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		
		// metadata for Duration
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.DURATION.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		new CreditCourseDurationMetadata ().loadChildMetadata (childMeta, type, state);
		
		// metadata for Department
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.DEPARTMENT.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		
		// metadata for Formats
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.FORMATS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		
		// metadata for VersionInd
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.VERSION_IND.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		
	}
}

