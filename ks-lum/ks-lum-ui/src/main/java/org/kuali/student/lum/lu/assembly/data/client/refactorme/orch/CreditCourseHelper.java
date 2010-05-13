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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch;

import java.util.Date;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoHelper;

public class CreditCourseHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ID ("id"),
		COPY_OF_COURSE_ID ("copyOfCourseId"),
		FORMATS ("formats"),
		TERMS_OFFERED ("termsOffered"),
		FIRST_EXPECTED_OFFERING ("firstExpectedOffering"),
		DURATION ("duration"),
		TRANSCRIPT_TITLE ("transcriptTitle"),
		COURSE_TITLE ("courseTitle"),
		DESCRIPTION ("description"),
		DEPARTMENT ("department"),
		SUBJECT_AREA ("subjectArea"),
		COURSE_NUMBER_SUFFIX ("courseNumberSuffix"),
		CROSS_LISTINGS ("crossListings"),
		VERSIONS ("versions"),
		JOINTS ("joints"),
		FINAL_RESULTS ("finalResults"),
		FEES ("fees"),
		EXPENDITURE_INFO ("expenditureInfo"),
		REVENUE_INFO ("revenueInfo"),
		STATE ("state"),
		TYPE ("type"),
		EFFECTIVE_DATE ("effectiveDate"),
		EXPIRATION_DATE ("expirationDate"),
		ACADEMIC_SUBJECT_ORGS ("academicSubjectOrgs"),
		CAMPUS_LOCATIONS ("campusLocations"),
		PRIMARY_INSTRUCTOR ("primaryInstructor"),
		COURSE_SPECIFIC_LOS ("courseSpecificLOs"),
		GRADING_OPTIONS("gradingOptions"),
		OUTCOME_OPTIONS("outcomeOptions"),
		META_INFO ("metaInfo"),
		_RUNTIME_DATA ("_runtimeData");
		
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
	
	private CreditCourseHelper (Data data)
	{
		this.data = data;
	}
	
	public static CreditCourseHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CreditCourseHelper (data);
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
	
	
	public void setCopyOfCourseId (String value)
	{
		data.set (Properties.COPY_OF_COURSE_ID.getKey (), value);
	}
	
	
	public String getCopyOfCourseId ()
	{
		return (String) data.get (Properties.COPY_OF_COURSE_ID.getKey ());
	}
	
	
	public void setFormats (Data value)
	{
		data.set (Properties.FORMATS.getKey (), value);
	}
	
	
	public Data getFormats ()
	{
		return (Data) data.get (Properties.FORMATS.getKey ());
	}
	
	
	public void setTermsOffered (Data value)
	{
		data.set (Properties.TERMS_OFFERED.getKey (), value);
	}
	
	
	public Data getTermsOffered ()
	{
		return (Data) data.get (Properties.TERMS_OFFERED.getKey ());
	}
	
	
	public void setFirstExpectedOffering (String value)
	{
		data.set (Properties.FIRST_EXPECTED_OFFERING.getKey (), value);
	}
	
	
	public String getFirstExpectedOffering ()
	{
		return (String) data.get (Properties.FIRST_EXPECTED_OFFERING.getKey ());
	}
	
	
	public void setDuration (CreditCourseDurationHelper value)
	{
		data.set (Properties.DURATION.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public CreditCourseDurationHelper getDuration ()
	{
		return CreditCourseDurationHelper.wrap ((Data) data.get (Properties.DURATION.getKey ()));
	}
	
	
	public void setTranscriptTitle (String value)
	{
		data.set (Properties.TRANSCRIPT_TITLE.getKey (), value);
	}
	
	
	public String getTranscriptTitle ()
	{
		return (String) data.get (Properties.TRANSCRIPT_TITLE.getKey ());
	}
	
	
	public void setCourseTitle (String value)
	{
		data.set (Properties.COURSE_TITLE.getKey (), value);
	}
	
	
	public String getCourseTitle ()
	{
		return (String) data.get (Properties.COURSE_TITLE.getKey ());
	}
	
	
	public void setDescription (RichTextInfoHelper value)
	{
		data.set (Properties.DESCRIPTION.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public RichTextInfoHelper getDescription ()
	{
		return RichTextInfoHelper.wrap ((Data) data.get (Properties.DESCRIPTION.getKey ()));
	}
	
	
	public void setDepartment (String value)
	{
		data.set (Properties.DEPARTMENT.getKey (), value);
	}
	
	
	public String getDepartment ()
	{
		return (String) data.get (Properties.DEPARTMENT.getKey ());
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
	
	
	public void setCrossListings (Data value)
	{
		data.set (Properties.CROSS_LISTINGS.getKey (), value);
	}
	
	
	public Data getCrossListings ()
	{
		return (Data) data.get (Properties.CROSS_LISTINGS.getKey ());
	}
	
	
	public void setVersions (Data value)
	{
		data.set (Properties.VERSIONS.getKey (), value);
	}
	
	
	public Data getVersions ()
	{
		return (Data) data.get (Properties.VERSIONS.getKey ());
	}
	
	
	public void setJoints (Data value)
	{
		data.set (Properties.JOINTS.getKey (), value);
	}
	
	
	public Data getJoints ()
	{
		return (Data) data.get (Properties.JOINTS.getKey ());
	}
	
	
	public void setFinalResults (String value)
	{
		data.set (Properties.FINAL_RESULTS.getKey (), value);
	}
	
	
	public String getFinalResults ()
	{
		return (String) data.get (Properties.FINAL_RESULTS.getKey ());
	}
	
	
	public void setFees (Data value)
	{
		data.set (Properties.FEES.getKey (), value);
	}
	
	
	public Data getFees ()
	{
		return (Data) data.get (Properties.FEES.getKey ());
	}
	
	
	public void setExpenditureInfo (CreditCourseExpenditureInfoHelper value)
	{
		data.set (Properties.EXPENDITURE_INFO.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public CreditCourseExpenditureInfoHelper getExpenditureInfo ()
	{
		return CreditCourseExpenditureInfoHelper.wrap ((Data) data.get (Properties.EXPENDITURE_INFO.getKey ()));
	}
	
	
	public void setRevenueInfo (CreditCourseRevenueInfoHelper value)
	{
		data.set (Properties.REVENUE_INFO.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public CreditCourseRevenueInfoHelper getRevenueInfo ()
	{
		return CreditCourseRevenueInfoHelper.wrap ((Data) data.get (Properties.REVENUE_INFO.getKey ()));
	}
	
	
	public void setState (String value)
	{
		data.set (Properties.STATE.getKey (), value);
	}
	
	
	public String getState ()
	{
		return (String) data.get (Properties.STATE.getKey ());
	}
	
	
	public void setType (String value)
	{
		data.set (Properties.TYPE.getKey (), value);
	}
	
	
	public String getType ()
	{
		return (String) data.get (Properties.TYPE.getKey ());
	}
	
	
	public void setEffectiveDate (Date value)
	{
		data.set (Properties.EFFECTIVE_DATE.getKey (), value);
	}
	
	
	public Date getEffectiveDate ()
	{
		return (Date) data.get (Properties.EFFECTIVE_DATE.getKey ());
	}
	
	
	public void setExpirationDate (Date value)
	{
		data.set (Properties.EXPIRATION_DATE.getKey (), value);
	}
	
	
	public Date getExpirationDate ()
	{
		return (Date) data.get (Properties.EXPIRATION_DATE.getKey ());
	}
	
	
	public void setAcademicSubjectOrgs (Data value)
	{
		data.set (Properties.ACADEMIC_SUBJECT_ORGS.getKey (), value);
	}
	
	
	public Data getAcademicSubjectOrgs ()
	{
		return (Data) data.get (Properties.ACADEMIC_SUBJECT_ORGS.getKey ());
	}
	
	
	public void setCampusLocations (Data value)
	{
		data.set (Properties.CAMPUS_LOCATIONS.getKey (), value);
	}
	
	
	public Data getCampusLocations ()
	{
		return (Data) data.get (Properties.CAMPUS_LOCATIONS.getKey ());
	}
	
	
	public void setPrimaryInstructor (String value)
	{
		data.set (Properties.PRIMARY_INSTRUCTOR.getKey (), value);
	}
	
	
	public String getPrimaryInstructor ()
	{
		return (String) data.get (Properties.PRIMARY_INSTRUCTOR.getKey ());
	}
	
	
	public void setCourseSpecificLOs (Data value)
	{
		data.set (Properties.COURSE_SPECIFIC_LOS.getKey (), value);
	}
	
	
	public Data getCourseSpecificLOs ()
	{
		return (Data) data.get (Properties.COURSE_SPECIFIC_LOS.getKey ());
	}
	
	
	public void set_runtimeData (RuntimeDataHelper value)
	{
		data.set (Properties._RUNTIME_DATA.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public RuntimeDataHelper get_runtimeData ()
	{
		return RuntimeDataHelper.wrap ((Data) data.get (Properties._RUNTIME_DATA.getKey ()));
	}

	public void setGradingOptions(Data value) {
		data.set (Properties.GRADING_OPTIONS.getKey (), value);
	}

	public Data getGradingOptions () {
		return (Data) data.get (Properties.GRADING_OPTIONS.getKey ());
	}

	public void setOutcomeOptions(Data value) {
		data.set (Properties.OUTCOME_OPTIONS.getKey (), value);
	}

	public Data getOutcomeOptions () {
		return (Data) data.get (Properties.OUTCOME_OPTIONS.getKey ());
	}

    public void setMetaInfo (MetaInfoHelper value) {
        data.set (Properties.META_INFO.getKey (), (value == null) ? null : value.getData ());
    }
    public MetaInfoHelper getMetaInfo () {
        return MetaInfoHelper.wrap ((Data) data.get (Properties.META_INFO.getKey ()));
    }
}

