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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch;


import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoHelper;


public class CreditCourseHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ID ("Id"),
		SUBJECT_AREA ("SubjectArea"),
		COURSE_NUMBER_SUFFIX ("CourseNumberSuffix"),
		TRANSCRIPT_TITLE ("TranscriptTitle"),
		COURSE_TITLE ("CourseTitle"),
		DESCRIPTION ("Description"),
		TERMS_OFFERED ("TermsOffered"),
		DURATION ("Duration"),
		DEPARTMENT ("Department"),
		FORMATS ("Formats"),
		VERSION_IND ("VersionInd");
		
		private final String key;
		
		private Properties (final String key)
		{
			this.key = key;
		}
		
		@Override
		public String getKey ()
		{
			return this.key;
		}
	}
	private Data data;
	
	public CreditCourseHelper (Data data)
	{
		this.data = data;
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setId (String value)
	{
		data.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return (String) data.get (Properties.ID.getKey ());
	}
	
	
	public void setSubjectArea (String value)
	{
		data.set (Properties.SUBJECT_AREA.getKey (), value);
	}
	
	
	public String getSubjectArea ()
	{
		return (String) data.get (Properties.SUBJECT_AREA.getKey ());
	}
	
	
	public void setCourseNumberSuffix (String value)
	{
		data.set (Properties.COURSE_NUMBER_SUFFIX.getKey (), value);
	}
	
	
	public String getCourseNumberSuffix ()
	{
		return (String) data.get (Properties.COURSE_NUMBER_SUFFIX.getKey ());
	}
	
	
	public void setTranscriptTitle (RichTextInfoHelper value)
	{
		data.set (Properties.TRANSCRIPT_TITLE.getKey (), value.getData ());
	}
	
	
	public RichTextInfoHelper getTranscriptTitle ()
	{
		return new RichTextInfoHelper ((Data) data.get (Properties.TRANSCRIPT_TITLE.getKey ()));
	}
	
	
	public void setCourseTitle (RichTextInfoHelper value)
	{
		data.set (Properties.COURSE_TITLE.getKey (), value.getData ());
	}
	
	
	public RichTextInfoHelper getCourseTitle ()
	{
		return new RichTextInfoHelper ((Data) data.get (Properties.COURSE_TITLE.getKey ()));
	}
	
	
	public void setDescription (RichTextInfoHelper value)
	{
		data.set (Properties.DESCRIPTION.getKey (), value.getData ());
	}
	
	
	public RichTextInfoHelper getDescription ()
	{
		return new RichTextInfoHelper ((Data) data.get (Properties.DESCRIPTION.getKey ()));
	}
	
	
	public void setTermsOffered (Data value)
	{
		data.set (Properties.TERMS_OFFERED.getKey (), value);
	}
	
	
	public Data getTermsOffered ()
	{
		return (Data) data.get (Properties.TERMS_OFFERED.getKey ());
	}
	
	
	public void setDuration (CreditCourseDurationHelper value)
	{
		data.set (Properties.DURATION.getKey (), value.getData ());
	}
	
	
	public CreditCourseDurationHelper getDuration ()
	{
		return new CreditCourseDurationHelper ((Data) data.get (Properties.DURATION.getKey ()));
	}
	
	
	public void setDepartment (String value)
	{
		data.set (Properties.DEPARTMENT.getKey (), value);
	}
	
	
	public String getDepartment ()
	{
		return (String) data.get (Properties.DEPARTMENT.getKey ());
	}
	
	
	public void setFormats (Data value)
	{
		data.set (Properties.FORMATS.getKey (), value);
	}
	
	
	public Data getFormats ()
	{
		return (Data) data.get (Properties.FORMATS.getKey ());
	}
	
	
	public void setVersionInd (String value)
	{
		data.set (Properties.VERSION_IND.getKey (), value);
	}
	
	
	public String getVersionInd ()
	{
		return (String) data.get (Properties.VERSION_IND.getKey ());
	}
	
}

