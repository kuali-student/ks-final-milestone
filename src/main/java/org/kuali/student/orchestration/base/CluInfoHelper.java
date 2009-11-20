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
package org.kuali.student.orchestration.base;


import java.util.Date;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
import org.kuali.student.orchestration.base.AdminOrgInfoHelper;
import org.kuali.student.orchestration.base.CluAccountingInfoHelper;
import org.kuali.student.orchestration.base.CluFeeInfoHelper;
import org.kuali.student.orchestration.base.CluIdentifierInfoHelper;
import org.kuali.student.orchestration.base.CluInstructorInfoHelper;
import org.kuali.student.orchestration.base.CluPublishingInfoHelper;
import org.kuali.student.orchestration.base.MetaInfoHelper;
import org.kuali.student.orchestration.base.RichTextInfoHelper;
import org.kuali.student.orchestration.base.TimeAmountInfoHelper;


public class CluInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		OFFICIAL_IDENTIFIER ("officialIdentifier"),
		ALTERNATE_IDENTIFIERS ("alternateIdentifiers"),
		ACADEMIC_SUBJECT_ORGS ("academicSubjectOrgs"),
		STUDY_SUBJECT_AREA ("studySubjectArea"),
		DESC ("desc"),
		MARKETING_DESC ("marketingDesc"),
		CAMPUS_LOCATION_LIST ("campusLocationList"),
		ACCREDITATION ("accreditation"),
		PRIMARY_ADMIN_ORG ("primaryAdminOrg"),
		ALTERNATE_ADMIN_ORGS ("alternateAdminOrgs"),
		PRIMARY_INSTRUCTOR ("primaryInstructor"),
		INSTRUCTORS ("instructors"),
		EXPECTED_FIRST_ATP ("expectedFirstAtp"),
		EFFECTIVE_DATE ("effectiveDate"),
		EXPIRATION_DATE ("expirationDate"),
		INTENSITY ("intensity"),
		STD_DURATION ("stdDuration"),
		CAN_CREATE_LUI ("canCreateLui"),
		REFERENCE_U_R_L ("referenceURL"),
		LU_CODES ("luCodes"),
		PUBLISHING_INFO ("publishingInfo"),
		NEXT_REVIEW_PERIOD ("nextReviewPeriod"),
		IS_ENROLLABLE ("isEnrollable"),
		OFFERED_ATP_TYPES ("offeredAtpTypes"),
		HAS_EARLY_DROP_DEADLINE ("hasEarlyDropDeadline"),
		DEFAULT_ENROLLMENT_ESTIMATE ("defaultEnrollmentEstimate"),
		DEFAULT_MAXIMUM_ENROLLMENT ("defaultMaximumEnrollment"),
		IS_HAZARDOUS_FOR_DISABLED_STUDENTS ("isHazardousForDisabledStudents"),
		FEE_INFO ("feeInfo"),
		ACCOUNTING_INFO ("accountingInfo"),
		ATTRIBUTES ("attributes"),
		META_INFO ("metaInfo"),
		TYPE ("type"),
		STATE ("state"),
		ID ("id");
		
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
	private ModifiableData data;
	
	public CluInfoHelper (ModifiableData data)
	{
		this.data = data;
	}
	
	public ModifiableData getData ()
	{
		return data;
	}
	
	
	public void setOfficialIdentifier (CluIdentifierInfoHelper value)
	{
		data.set (Properties.OFFICIAL_IDENTIFIER.getKey (), value.getData ());
	}
	
	
	public CluIdentifierInfoHelper getOfficialIdentifier ()
	{
		return new CluIdentifierInfoHelper ((Data) data.get (Properties.OFFICIAL_IDENTIFIER.getKey ()));
	}
	
	
	public void setAlternateIdentifiers (Data value)
	{
		data.set (Properties.ALTERNATE_IDENTIFIERS.getKey (), value);
	}
	
	
	public Data getAlternateIdentifiers ()
	{
		return (Data) data.get (Properties.ALTERNATE_IDENTIFIERS.getKey ());
	}
	
	
	public void setAcademicSubjectOrgs (Data value)
	{
		data.set (Properties.ACADEMIC_SUBJECT_ORGS.getKey (), value);
	}
	
	
	public Data getAcademicSubjectOrgs ()
	{
		return (Data) data.get (Properties.ACADEMIC_SUBJECT_ORGS.getKey ());
	}
	
	
	public void setStudySubjectArea (String value)
	{
		data.set (Properties.STUDY_SUBJECT_AREA.getKey (), value);
	}
	
	
	public String getStudySubjectArea ()
	{
		return (String) data.get (Properties.STUDY_SUBJECT_AREA.getKey ());
	}
	
	
	public void setDesc (RichTextInfoHelper value)
	{
		data.set (Properties.DESC.getKey (), value.getData ());
	}
	
	
	public RichTextInfoHelper getDesc ()
	{
		return new RichTextInfoHelper ((Data) data.get (Properties.DESC.getKey ()));
	}
	
	
	public void setMarketingDesc (RichTextInfoHelper value)
	{
		data.set (Properties.MARKETING_DESC.getKey (), value.getData ());
	}
	
	
	public RichTextInfoHelper getMarketingDesc ()
	{
		return new RichTextInfoHelper ((Data) data.get (Properties.MARKETING_DESC.getKey ()));
	}
	
	
	public void setCampusLocationList (Data value)
	{
		data.set (Properties.CAMPUS_LOCATION_LIST.getKey (), value);
	}
	
	
	public Data getCampusLocationList ()
	{
		return (Data) data.get (Properties.CAMPUS_LOCATION_LIST.getKey ());
	}
	
	
	public void setAccreditation (Data value)
	{
		data.set (Properties.ACCREDITATION.getKey (), value);
	}
	
	
	public Data getAccreditation ()
	{
		return (Data) data.get (Properties.ACCREDITATION.getKey ());
	}
	
	
	public void setPrimaryAdminOrg (AdminOrgInfoHelper value)
	{
		data.set (Properties.PRIMARY_ADMIN_ORG.getKey (), value.getData ());
	}
	
	
	public AdminOrgInfoHelper getPrimaryAdminOrg ()
	{
		return new AdminOrgInfoHelper ((Data) data.get (Properties.PRIMARY_ADMIN_ORG.getKey ()));
	}
	
	
	public void setAlternateAdminOrgs (Data value)
	{
		data.set (Properties.ALTERNATE_ADMIN_ORGS.getKey (), value);
	}
	
	
	public Data getAlternateAdminOrgs ()
	{
		return (Data) data.get (Properties.ALTERNATE_ADMIN_ORGS.getKey ());
	}
	
	
	public void setPrimaryInstructor (CluInstructorInfoHelper value)
	{
		data.set (Properties.PRIMARY_INSTRUCTOR.getKey (), value.getData ());
	}
	
	
	public CluInstructorInfoHelper getPrimaryInstructor ()
	{
		return new CluInstructorInfoHelper ((Data) data.get (Properties.PRIMARY_INSTRUCTOR.getKey ()));
	}
	
	
	public void setInstructors (Data value)
	{
		data.set (Properties.INSTRUCTORS.getKey (), value);
	}
	
	
	public Data getInstructors ()
	{
		return (Data) data.get (Properties.INSTRUCTORS.getKey ());
	}
	
	
	public void setExpectedFirstAtp (String value)
	{
		data.set (Properties.EXPECTED_FIRST_ATP.getKey (), value);
	}
	
	
	public String getExpectedFirstAtp ()
	{
		return (String) data.get (Properties.EXPECTED_FIRST_ATP.getKey ());
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
	
	
	public void setIntensity (TimeAmountInfoHelper value)
	{
		data.set (Properties.INTENSITY.getKey (), value.getData ());
	}
	
	
	public TimeAmountInfoHelper getIntensity ()
	{
		return new TimeAmountInfoHelper ((Data) data.get (Properties.INTENSITY.getKey ()));
	}
	
	
	public void setStdDuration (TimeAmountInfoHelper value)
	{
		data.set (Properties.STD_DURATION.getKey (), value.getData ());
	}
	
	
	public TimeAmountInfoHelper getStdDuration ()
	{
		return new TimeAmountInfoHelper ((Data) data.get (Properties.STD_DURATION.getKey ()));
	}
	
	
	public void setCanCreateLui (Boolean value)
	{
		data.set (Properties.CAN_CREATE_LUI.getKey (), value);
	}
	
	
	public Boolean getCanCreateLui ()
	{
		return (Boolean) data.get (Properties.CAN_CREATE_LUI.getKey ());
	}
	
	
	public void setReferenceURL (String value)
	{
		data.set (Properties.REFERENCE_U_R_L.getKey (), value);
	}
	
	
	public String getReferenceURL ()
	{
		return (String) data.get (Properties.REFERENCE_U_R_L.getKey ());
	}
	
	
	public void setLuCodes (Data value)
	{
		data.set (Properties.LU_CODES.getKey (), value);
	}
	
	
	public Data getLuCodes ()
	{
		return (Data) data.get (Properties.LU_CODES.getKey ());
	}
	
	
	public void setPublishingInfo (CluPublishingInfoHelper value)
	{
		data.set (Properties.PUBLISHING_INFO.getKey (), value.getData ());
	}
	
	
	public CluPublishingInfoHelper getPublishingInfo ()
	{
		return new CluPublishingInfoHelper ((Data) data.get (Properties.PUBLISHING_INFO.getKey ()));
	}
	
	
	public void setNextReviewPeriod (String value)
	{
		data.set (Properties.NEXT_REVIEW_PERIOD.getKey (), value);
	}
	
	
	public String getNextReviewPeriod ()
	{
		return (String) data.get (Properties.NEXT_REVIEW_PERIOD.getKey ());
	}
	
	
	public void setIsEnrollable (Boolean value)
	{
		data.set (Properties.IS_ENROLLABLE.getKey (), value);
	}
	
	
	public Boolean getIsEnrollable ()
	{
		return (Boolean) data.get (Properties.IS_ENROLLABLE.getKey ());
	}
	
	
	public void setOfferedAtpTypes (Data value)
	{
		data.set (Properties.OFFERED_ATP_TYPES.getKey (), value);
	}
	
	
	public Data getOfferedAtpTypes ()
	{
		return (Data) data.get (Properties.OFFERED_ATP_TYPES.getKey ());
	}
	
	
	public void setHasEarlyDropDeadline (Boolean value)
	{
		data.set (Properties.HAS_EARLY_DROP_DEADLINE.getKey (), value);
	}
	
	
	public Boolean getHasEarlyDropDeadline ()
	{
		return (Boolean) data.get (Properties.HAS_EARLY_DROP_DEADLINE.getKey ());
	}
	
	
	public void setDefaultEnrollmentEstimate (Integer value)
	{
		data.set (Properties.DEFAULT_ENROLLMENT_ESTIMATE.getKey (), value);
	}
	
	
	public Integer getDefaultEnrollmentEstimate ()
	{
		return (Integer) data.get (Properties.DEFAULT_ENROLLMENT_ESTIMATE.getKey ());
	}
	
	
	public void setDefaultMaximumEnrollment (Integer value)
	{
		data.set (Properties.DEFAULT_MAXIMUM_ENROLLMENT.getKey (), value);
	}
	
	
	public Integer getDefaultMaximumEnrollment ()
	{
		return (Integer) data.get (Properties.DEFAULT_MAXIMUM_ENROLLMENT.getKey ());
	}
	
	
	public void setIsHazardousForDisabledStudents (Boolean value)
	{
		data.set (Properties.IS_HAZARDOUS_FOR_DISABLED_STUDENTS.getKey (), value);
	}
	
	
	public Boolean getIsHazardousForDisabledStudents ()
	{
		return (Boolean) data.get (Properties.IS_HAZARDOUS_FOR_DISABLED_STUDENTS.getKey ());
	}
	
	
	public void setFeeInfo (CluFeeInfoHelper value)
	{
		data.set (Properties.FEE_INFO.getKey (), value.getData ());
	}
	
	
	public CluFeeInfoHelper getFeeInfo ()
	{
		return new CluFeeInfoHelper ((Data) data.get (Properties.FEE_INFO.getKey ()));
	}
	
	
	public void setAccountingInfo (CluAccountingInfoHelper value)
	{
		data.set (Properties.ACCOUNTING_INFO.getKey (), value.getData ());
	}
	
	
	public CluAccountingInfoHelper getAccountingInfo ()
	{
		return new CluAccountingInfoHelper ((Data) data.get (Properties.ACCOUNTING_INFO.getKey ()));
	}
	
	
	public void setAttributes (Data value)
	{
		data.set (Properties.ATTRIBUTES.getKey (), value);
	}
	
	
	public Data getAttributes ()
	{
		return (Data) data.get (Properties.ATTRIBUTES.getKey ());
	}
	
	
	public void setMetaInfo (MetaInfoHelper value)
	{
		data.set (Properties.META_INFO.getKey (), value.getData ());
	}
	
	
	public MetaInfoHelper getMetaInfo ()
	{
		return new MetaInfoHelper ((Data) data.get (Properties.META_INFO.getKey ()));
	}
	
	
	public void setType (String value)
	{
		data.set (Properties.TYPE.getKey (), value);
	}
	
	
	public String getType ()
	{
		return (String) data.get (Properties.TYPE.getKey ());
	}
	
	
	public void setState (String value)
	{
		data.set (Properties.STATE.getKey (), value);
	}
	
	
	public String getState ()
	{
		return (String) data.get (Properties.STATE.getKey ());
	}
	
	
	public void setId (String value)
	{
		data.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return (String) data.get (Properties.ID.getKey ());
	}
	
}

