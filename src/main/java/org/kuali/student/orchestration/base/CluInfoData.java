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
import org.kuali.student.lum.lu.dto.CluInfo;


public class CluInfoData
	extends ModifiableData
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		OFFICIAL_IDENTIFIER ("officialIdentifier"),
		ALTERNATE_IDENTIFIERS ("alternateIdentifiers"),
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
	
	public CluInfoData ()
	{
		// TODO: ask Wil if we want to really use the class name as the key
		super (CluInfo.class.getName ());
	}
	
	public void setOfficialIdentifier (CluIdentifierInfoData value)
	{
		super.set (Properties.OFFICIAL_IDENTIFIER.getKey (), value);
	}
	
	
	public CluIdentifierInfoData getOfficialIdentifier ()
	{
		return super.get (Properties.OFFICIAL_IDENTIFIER.getKey ());
	}
	
	
	public void setAlternateIdentifiers (Data value)
	{
		super.set (Properties.ALTERNATE_IDENTIFIERS.getKey (), value);
	}
	
	
	public Data getAlternateIdentifiers ()
	{
		return super.get (Properties.ALTERNATE_IDENTIFIERS.getKey ());
	}
	
	
	public void setStudySubjectArea (String value)
	{
		super.set (Properties.STUDY_SUBJECT_AREA.getKey (), value);
	}
	
	
	public String getStudySubjectArea ()
	{
		return super.get (Properties.STUDY_SUBJECT_AREA.getKey ());
	}
	
	
	public void setDesc (RichTextInfoData value)
	{
		super.set (Properties.DESC.getKey (), value);
	}
	
	
	public RichTextInfoData getDesc ()
	{
		return super.get (Properties.DESC.getKey ());
	}
	
	
	public void setMarketingDesc (RichTextInfoData value)
	{
		super.set (Properties.MARKETING_DESC.getKey (), value);
	}
	
	
	public RichTextInfoData getMarketingDesc ()
	{
		return super.get (Properties.MARKETING_DESC.getKey ());
	}
	
	
	public void setCampusLocationList (Data value)
	{
		super.set (Properties.CAMPUS_LOCATION_LIST.getKey (), value);
	}
	
	
	public Data getCampusLocationList ()
	{
		return super.get (Properties.CAMPUS_LOCATION_LIST.getKey ());
	}
	
	
	public void setAccreditation (Data value)
	{
		super.set (Properties.ACCREDITATION.getKey (), value);
	}
	
	
	public Data getAccreditation ()
	{
		return super.get (Properties.ACCREDITATION.getKey ());
	}
	
	
	public void setPrimaryAdminOrg (AdminOrgInfoData value)
	{
		super.set (Properties.PRIMARY_ADMIN_ORG.getKey (), value);
	}
	
	
	public AdminOrgInfoData getPrimaryAdminOrg ()
	{
		return super.get (Properties.PRIMARY_ADMIN_ORG.getKey ());
	}
	
	
	public void setAlternateAdminOrgs (Data value)
	{
		super.set (Properties.ALTERNATE_ADMIN_ORGS.getKey (), value);
	}
	
	
	public Data getAlternateAdminOrgs ()
	{
		return super.get (Properties.ALTERNATE_ADMIN_ORGS.getKey ());
	}
	
	
	public void setPrimaryInstructor (CluInstructorInfoData value)
	{
		super.set (Properties.PRIMARY_INSTRUCTOR.getKey (), value);
	}
	
	
	public CluInstructorInfoData getPrimaryInstructor ()
	{
		return super.get (Properties.PRIMARY_INSTRUCTOR.getKey ());
	}
	
	
	public void setInstructors (Data value)
	{
		super.set (Properties.INSTRUCTORS.getKey (), value);
	}
	
	
	public Data getInstructors ()
	{
		return super.get (Properties.INSTRUCTORS.getKey ());
	}
	
	
	public void setExpectedFirstAtp (String value)
	{
		super.set (Properties.EXPECTED_FIRST_ATP.getKey (), value);
	}
	
	
	public String getExpectedFirstAtp ()
	{
		return super.get (Properties.EXPECTED_FIRST_ATP.getKey ());
	}
	
	
	public void setEffectiveDate (Date value)
	{
		super.set (Properties.EFFECTIVE_DATE.getKey (), value);
	}
	
	
	public Date getEffectiveDate ()
	{
		return super.get (Properties.EFFECTIVE_DATE.getKey ());
	}
	
	
	public void setExpirationDate (Date value)
	{
		super.set (Properties.EXPIRATION_DATE.getKey (), value);
	}
	
	
	public Date getExpirationDate ()
	{
		return super.get (Properties.EXPIRATION_DATE.getKey ());
	}
	
	
	public void setIntensity (TimeAmountInfoData value)
	{
		super.set (Properties.INTENSITY.getKey (), value);
	}
	
	
	public TimeAmountInfoData getIntensity ()
	{
		return super.get (Properties.INTENSITY.getKey ());
	}
	
	
	public void setStdDuration (TimeAmountInfoData value)
	{
		super.set (Properties.STD_DURATION.getKey (), value);
	}
	
	
	public TimeAmountInfoData getStdDuration ()
	{
		return super.get (Properties.STD_DURATION.getKey ());
	}
	
	
	public void setCanCreateLui (Boolean value)
	{
		super.set (Properties.CAN_CREATE_LUI.getKey (), value);
	}
	
	
	public Boolean getCanCreateLui ()
	{
		return super.get (Properties.CAN_CREATE_LUI.getKey ());
	}
	
	
	public void setReferenceURL (String value)
	{
		super.set (Properties.REFERENCE_U_R_L.getKey (), value);
	}
	
	
	public String getReferenceURL ()
	{
		return super.get (Properties.REFERENCE_U_R_L.getKey ());
	}
	
	
	public void setLuCodes (Data value)
	{
		super.set (Properties.LU_CODES.getKey (), value);
	}
	
	
	public Data getLuCodes ()
	{
		return super.get (Properties.LU_CODES.getKey ());
	}
	
	
	public void setPublishingInfo (CluPublishingInfoData value)
	{
		super.set (Properties.PUBLISHING_INFO.getKey (), value);
	}
	
	
	public CluPublishingInfoData getPublishingInfo ()
	{
		return super.get (Properties.PUBLISHING_INFO.getKey ());
	}
	
	
	public void setNextReviewPeriod (String value)
	{
		super.set (Properties.NEXT_REVIEW_PERIOD.getKey (), value);
	}
	
	
	public String getNextReviewPeriod ()
	{
		return super.get (Properties.NEXT_REVIEW_PERIOD.getKey ());
	}
	
	
	public void setIsEnrollable (Boolean value)
	{
		super.set (Properties.IS_ENROLLABLE.getKey (), value);
	}
	
	
	public Boolean getIsEnrollable ()
	{
		return super.get (Properties.IS_ENROLLABLE.getKey ());
	}
	
	
	public void setOfferedAtpTypes (Data value)
	{
		super.set (Properties.OFFERED_ATP_TYPES.getKey (), value);
	}
	
	
	public Data getOfferedAtpTypes ()
	{
		return super.get (Properties.OFFERED_ATP_TYPES.getKey ());
	}
	
	
	public void setHasEarlyDropDeadline (Boolean value)
	{
		super.set (Properties.HAS_EARLY_DROP_DEADLINE.getKey (), value);
	}
	
	
	public Boolean getHasEarlyDropDeadline ()
	{
		return super.get (Properties.HAS_EARLY_DROP_DEADLINE.getKey ());
	}
	
	
	public void setDefaultEnrollmentEstimate (Integer value)
	{
		super.set (Properties.DEFAULT_ENROLLMENT_ESTIMATE.getKey (), value);
	}
	
	
	public Integer getDefaultEnrollmentEstimate ()
	{
		return super.get (Properties.DEFAULT_ENROLLMENT_ESTIMATE.getKey ());
	}
	
	
	public void setDefaultMaximumEnrollment (Integer value)
	{
		super.set (Properties.DEFAULT_MAXIMUM_ENROLLMENT.getKey (), value);
	}
	
	
	public Integer getDefaultMaximumEnrollment ()
	{
		return super.get (Properties.DEFAULT_MAXIMUM_ENROLLMENT.getKey ());
	}
	
	
	public void setIsHazardousForDisabledStudents (Boolean value)
	{
		super.set (Properties.IS_HAZARDOUS_FOR_DISABLED_STUDENTS.getKey (), value);
	}
	
	
	public Boolean getIsHazardousForDisabledStudents ()
	{
		return super.get (Properties.IS_HAZARDOUS_FOR_DISABLED_STUDENTS.getKey ());
	}
	
	
	public void setFeeInfo (CluFeeInfoData value)
	{
		super.set (Properties.FEE_INFO.getKey (), value);
	}
	
	
	public CluFeeInfoData getFeeInfo ()
	{
		return super.get (Properties.FEE_INFO.getKey ());
	}
	
	
	public void setAccountingInfo (CluAccountingInfoData value)
	{
		super.set (Properties.ACCOUNTING_INFO.getKey (), value);
	}
	
	
	public CluAccountingInfoData getAccountingInfo ()
	{
		return super.get (Properties.ACCOUNTING_INFO.getKey ());
	}
	
	
	public void setAttributes (Data value)
	{
		super.set (Properties.ATTRIBUTES.getKey (), value);
	}
	
	
	public Data getAttributes ()
	{
		return super.get (Properties.ATTRIBUTES.getKey ());
	}
	
	
	public void setMetaInfo (MetaInfoData value)
	{
		super.set (Properties.META_INFO.getKey (), value);
	}
	
	
	public MetaInfoData getMetaInfo ()
	{
		return super.get (Properties.META_INFO.getKey ());
	}
	
	
	public void setType (String value)
	{
		super.set (Properties.TYPE.getKey (), value);
	}
	
	
	public String getType ()
	{
		return super.get (Properties.TYPE.getKey ());
	}
	
	
	public void setState (String value)
	{
		super.set (Properties.STATE.getKey (), value);
	}
	
	
	public String getState ()
	{
		return super.get (Properties.STATE.getKey ());
	}
	
	
	public void setId (String value)
	{
		super.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return super.get (Properties.ID.getKey ());
	}
	
}

