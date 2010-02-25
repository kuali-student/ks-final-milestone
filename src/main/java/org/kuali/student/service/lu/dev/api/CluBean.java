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
	package org.kuali.student.service.lu.dev.api;
	
	
	import java.io.Serializable;
	import java.util.Date;
	import java.util.List;
	import java.util.Map;
	
	
	public class CluBean
	 implements CluInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private CluIdentifierInfo officialIdentifier;
		
		/**
		* Set Official Idenfifier
		*
		* Information related to the official identification of the clu, typically in 
		* human readable form. Used to officially reference or publish.
		*/
		@Override
		public void setOfficialIdentifier(CluIdentifierInfo officialIdentifier)
		{
			this.officialIdentifier = officialIdentifier;
		}
		
		/**
		* Get Official Idenfifier
		*
		* Information related to the official identification of the clu, typically in 
		* human readable form. Used to officially reference or publish.
		*/
		@Override
		public CluIdentifierInfo getOfficialIdentifier()
		{
			return this.officialIdentifier;
		}
						
		private List<CluIdentifierInfo> alternateIdentifiers;
		
		/**
		* Set Alternate Idenfifiers
		*
		* Information related to alternate identifications of the clu, typically in human 
		* readable form. Used to reference or publish.
		*/
		@Override
		public void setAlternateIdentifiers(List<CluIdentifierInfo> alternateIdentifiers)
		{
			this.alternateIdentifiers = alternateIdentifiers;
		}
		
		/**
		* Get Alternate Idenfifiers
		*
		* Information related to alternate identifications of the clu, typically in human 
		* readable form. Used to reference or publish.
		*/
		@Override
		public List<CluIdentifierInfo> getAlternateIdentifiers()
		{
			return this.alternateIdentifiers;
		}
						
		private List<AcademicSubjectOrgInfo> academicSubjectOrgs;
		
		/**
		* Set Academic Subject Orgs
		*
		* The organizations that represents the Subject area of the Clu, if different from 
		* the PrimaryAdminOrg?
		*/
		@Override
		public void setAcademicSubjectOrgs(List<AcademicSubjectOrgInfo> academicSubjectOrgs)
		{
			this.academicSubjectOrgs = academicSubjectOrgs;
		}
		
		/**
		* Get Academic Subject Orgs
		*
		* The organizations that represents the Subject area of the Clu, if different from 
		* the PrimaryAdminOrg?
		*/
		@Override
		public List<AcademicSubjectOrgInfo> getAcademicSubjectOrgs()
		{
			return this.academicSubjectOrgs;
		}
						
		private String studySubjectArea;
		
		/**
		* Set Study Subject Area
		*
		* The Study Subject Area is used to identify the area of study associated with the 
		* clu. It may be a general study area (e.g. Chemistry) or very specific (e.g. 
		* Naval Architecture) depending on the level of specificity of the clu.
		*/
		@Override
		public void setStudySubjectArea(String studySubjectArea)
		{
			this.studySubjectArea = studySubjectArea;
		}
		
		/**
		* Get Study Subject Area
		*
		* The Study Subject Area is used to identify the area of study associated with the 
		* clu. It may be a general study area (e.g. Chemistry) or very specific (e.g. 
		* Naval Architecture) depending on the level of specificity of the clu.
		*/
		@Override
		public String getStudySubjectArea()
		{
			return this.studySubjectArea;
		}
						
		private RichTextInfo desc;
		
		/**
		* Set Description
		*
		* Narrative description of the CLU, used for the catalog.
		*/
		@Override
		public void setDesc(RichTextInfo desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get Description
		*
		* Narrative description of the CLU, used for the catalog.
		*/
		@Override
		public RichTextInfo getDesc()
		{
			return this.desc;
		}
						
		private List<String> campusLocations;
		
		/**
		* Set List of Campus Locations
		*
		* Places where this clu might be offered
		*/
		@Override
		public void setCampusLocations(List<String> campusLocations)
		{
			this.campusLocations = campusLocations;
		}
		
		/**
		* Get List of Campus Locations
		*
		* Places where this clu might be offered
		*/
		@Override
		public List<String> getCampusLocations()
		{
			return this.campusLocations;
		}
						
		private List<AccreditationInfo> accreditations;
		
		/**
		* Set Accreditations
		*
		* Information around the accreditation of the clu.
		*/
		@Override
		public void setAccreditations(List<AccreditationInfo> accreditations)
		{
			this.accreditations = accreditations;
		}
		
		/**
		* Get Accreditations
		*
		* Information around the accreditation of the clu.
		*/
		@Override
		public List<AccreditationInfo> getAccreditations()
		{
			return this.accreditations;
		}
						
		private AdminOrgInfo primaryAdminOrg;
		
		/**
		* Set Admin Org
		*
		* The primary organization (typically, an academic department) with administrative 
		* oversight over the CLU. This will be used for Authorization and Workflow.
		*/
		@Override
		public void setPrimaryAdminOrg(AdminOrgInfo primaryAdminOrg)
		{
			this.primaryAdminOrg = primaryAdminOrg;
		}
		
		/**
		* Get Admin Org
		*
		* The primary organization (typically, an academic department) with administrative 
		* oversight over the CLU. This will be used for Authorization and Workflow.
		*/
		@Override
		public AdminOrgInfo getPrimaryAdminOrg()
		{
			return this.primaryAdminOrg;
		}
						
		private List<AdminOrgInfo> alternateAdminOrgs;
		
		/**
		* Set Alternate Admin Orgs
		*
		* For situations where more than one Organization shares in the Administration of 
		* this clu. This will be used in Authorization and Workflow
		*/
		@Override
		public void setAlternateAdminOrgs(List<AdminOrgInfo> alternateAdminOrgs)
		{
			this.alternateAdminOrgs = alternateAdminOrgs;
		}
		
		/**
		* Get Alternate Admin Orgs
		*
		* For situations where more than one Organization shares in the Administration of 
		* this clu. This will be used in Authorization and Workflow
		*/
		@Override
		public List<AdminOrgInfo> getAlternateAdminOrgs()
		{
			return this.alternateAdminOrgs;
		}
						
		private CluInstructorInfo primaryInstructor;
		
		/**
		* Set Primary Instructor
		*
		* Primary potential instructor for the clu. This is primarily for use in 
		* advertising the clu and may not be the actual instructor.
		*/
		@Override
		public void setPrimaryInstructor(CluInstructorInfo primaryInstructor)
		{
			this.primaryInstructor = primaryInstructor;
		}
		
		/**
		* Get Primary Instructor
		*
		* Primary potential instructor for the clu. This is primarily for use in 
		* advertising the clu and may not be the actual instructor.
		*/
		@Override
		public CluInstructorInfo getPrimaryInstructor()
		{
			return this.primaryInstructor;
		}
						
		private List<CluInstructorInfo> instructors;
		
		/**
		* Set Instructors
		*
		* Instructors associated with this clu. This may not be an exhaustive list, and 
		* instead may only be used to indicate potential instructors in publication.
		*/
		@Override
		public void setInstructors(List<CluInstructorInfo> instructors)
		{
			this.instructors = instructors;
		}
		
		/**
		* Get Instructors
		*
		* Instructors associated with this clu. This may not be an exhaustive list, and 
		* instead may only be used to indicate potential instructors in publication.
		*/
		@Override
		public List<CluInstructorInfo> getInstructors()
		{
			return this.instructors;
		}
						
		private String expectedFirstAtp;
		
		/**
		* Set Expected First ATP
		*
		* The expected first academic time period that this clu would be effective. This 
		* may not reflect the first "real" academic time period for this clu.
		*/
		@Override
		public void setExpectedFirstAtp(String expectedFirstAtp)
		{
			this.expectedFirstAtp = expectedFirstAtp;
		}
		
		/**
		* Get Expected First ATP
		*
		* The expected first academic time period that this clu would be effective. This 
		* may not reflect the first "real" academic time period for this clu.
		*/
		@Override
		public String getExpectedFirstAtp()
		{
			return this.expectedFirstAtp;
		}
						
		private Date effectiveDate;
		
		/**
		* Set Effective Date
		*
		* Date and time the CLU became effective. This is a similar concept to the 
		* effective date on enumerated values. When an expiration date has been specified, 
		* this field must be less than or equal to the expiration date.
		*/
		@Override
		public void setEffectiveDate(Date effectiveDate)
		{
			this.effectiveDate = effectiveDate;
		}
		
		/**
		* Get Effective Date
		*
		* Date and time the CLU became effective. This is a similar concept to the 
		* effective date on enumerated values. When an expiration date has been specified, 
		* this field must be less than or equal to the expiration date.
		*/
		@Override
		public Date getEffectiveDate()
		{
			return this.effectiveDate;
		}
						
		private Date expirationDate;
		
		/**
		* Set Expiration Date
		*
		* Date and time that this CLU expires. This is a similar concept to the expiration 
		* date on enumerated values. If specified, this should be greater than or equal to 
		* the effective date. If this field is not specified, then no expiration date has 
		* been currently defined and should automatically be considered greater than the 
		* effective date.
		*/
		@Override
		public void setExpirationDate(Date expirationDate)
		{
			this.expirationDate = expirationDate;
		}
		
		/**
		* Get Expiration Date
		*
		* Date and time that this CLU expires. This is a similar concept to the expiration 
		* date on enumerated values. If specified, this should be greater than or equal to 
		* the effective date. If this field is not specified, then no expiration date has 
		* been currently defined and should automatically be considered greater than the 
		* effective date.
		*/
		@Override
		public Date getExpirationDate()
		{
			return this.expirationDate;
		}
						
		private AmountInfo intensity;
		
		/**
		* Set Intensity
		*
		* The expected level of time commitment between the student and the CLU meetings.
		*/
		@Override
		public void setIntensity(AmountInfo intensity)
		{
			this.intensity = intensity;
		}
		
		/**
		* Get Intensity
		*
		* The expected level of time commitment between the student and the CLU meetings.
		*/
		@Override
		public AmountInfo getIntensity()
		{
			return this.intensity;
		}
						
		private TimeAmountInfo stdDuration;
		
		/**
		* Set Standard Duration
		*
		* The standard duration of the learning unit.
		*/
		@Override
		public void setStdDuration(TimeAmountInfo stdDuration)
		{
			this.stdDuration = stdDuration;
		}
		
		/**
		* Get Standard Duration
		*
		* The standard duration of the learning unit.
		*/
		@Override
		public TimeAmountInfo getStdDuration()
		{
			return this.stdDuration;
		}
						
		private Boolean canCreateLui;
		
		/**
		* Set Can Create LUI
		*
		* Indicates if the CLU can be used to instantiate LUIs (offerings).
		*/
		@Override
		public void setCanCreateLui(Boolean canCreateLui)
		{
			this.canCreateLui = canCreateLui;
		}
		
		/**
		* Get Can Create LUI
		*
		* Indicates if the CLU can be used to instantiate LUIs (offerings).
		*/
		@Override
		public Boolean isCanCreateLui()
		{
			return this.canCreateLui;
		}
						
		private String referenceURL;
		
		/**
		* Set Reference URL
		*
		* An URL for additional information about the CLU. This could be a reference to a 
		* document which might in turn have references to other documents (e.g. course 
		* syllabus provided by the faculty or department, standard schedule of classes, 
		* etc.).
		*/
		@Override
		public void setReferenceURL(String referenceURL)
		{
			this.referenceURL = referenceURL;
		}
		
		/**
		* Get Reference URL
		*
		* An URL for additional information about the CLU. This could be a reference to a 
		* document which might in turn have references to other documents (e.g. course 
		* syllabus provided by the faculty or department, standard schedule of classes, 
		* etc.).
		*/
		@Override
		public String getReferenceURL()
		{
			return this.referenceURL;
		}
						
		private List<LuCodeInfo> luCodes;
		
		/**
		* Set LU Code Info
		*
		* List of LU code info structures. These are structures so that many different 
		* types of codes can be associated with the clu. This allows them to be put into 
		* categories.
		*/
		@Override
		public void setLuCodes(List<LuCodeInfo> luCodes)
		{
			this.luCodes = luCodes;
		}
		
		/**
		* Get LU Code Info
		*
		* List of LU code info structures. These are structures so that many different 
		* types of codes can be associated with the clu. This allows them to be put into 
		* categories.
		*/
		@Override
		public List<LuCodeInfo> getLuCodes()
		{
			return this.luCodes;
		}
						
		private String nextReviewPeriod;
		
		/**
		* Set Next Review Period
		*
		* When the next review should be
		*/
		@Override
		public void setNextReviewPeriod(String nextReviewPeriod)
		{
			this.nextReviewPeriod = nextReviewPeriod;
		}
		
		/**
		* Get Next Review Period
		*
		* When the next review should be
		*/
		@Override
		public String getNextReviewPeriod()
		{
			return this.nextReviewPeriod;
		}
						
		private Boolean isEnrollable;
		
		/**
		* Set Is Enrollable
		*
		* Indicates if Luis generated from this Clu are intended to be enrolled in by 
		* Students directly
		*/
		@Override
		public void setIsEnrollable(Boolean isEnrollable)
		{
			this.isEnrollable = isEnrollable;
		}
		
		/**
		* Get Is Enrollable
		*
		* Indicates if Luis generated from this Clu are intended to be enrolled in by 
		* Students directly
		*/
		@Override
		public Boolean isIsEnrollable()
		{
			return this.isEnrollable;
		}
						
		private List<String> offeredAtpTypes;
		
		/**
		* Set Offered Academic Time Period Types
		*
		* The academic time period types in which this CLU is typically offered. Standard 
		* usage would equate to terms. It can define a timeframe that a clu with of a 
		* certain stdDuration would fall in.
		*/
		@Override
		public void setOfferedAtpTypes(List<String> offeredAtpTypes)
		{
			this.offeredAtpTypes = offeredAtpTypes;
		}
		
		/**
		* Get Offered Academic Time Period Types
		*
		* The academic time period types in which this CLU is typically offered. Standard 
		* usage would equate to terms. It can define a timeframe that a clu with of a 
		* certain stdDuration would fall in.
		*/
		@Override
		public List<String> getOfferedAtpTypes()
		{
			return this.offeredAtpTypes;
		}
						
		private Boolean hasEarlyDropDeadline;
		
		/**
		* Set Has Early Drop Deadline
		*
		* Indicates if the CLU has an Early Drop Deadline (EDD). Certain courses are 
		* designated as such to maximize access to courses that have historically 
		* experienced high demand and high attrition. Default is "false".
		*/
		@Override
		public void setHasEarlyDropDeadline(Boolean hasEarlyDropDeadline)
		{
			this.hasEarlyDropDeadline = hasEarlyDropDeadline;
		}
		
		/**
		* Get Has Early Drop Deadline
		*
		* Indicates if the CLU has an Early Drop Deadline (EDD). Certain courses are 
		* designated as such to maximize access to courses that have historically 
		* experienced high demand and high attrition. Default is "false".
		*/
		@Override
		public Boolean isHasEarlyDropDeadline()
		{
			return this.hasEarlyDropDeadline;
		}
						
		private Integer defaultEnrollmentEstimate;
		
		/**
		* Set Default Enrollment Estimate
		*
		* Default enrollment estimate for this CLU.
		*/
		@Override
		public void setDefaultEnrollmentEstimate(Integer defaultEnrollmentEstimate)
		{
			this.defaultEnrollmentEstimate = defaultEnrollmentEstimate;
		}
		
		/**
		* Get Default Enrollment Estimate
		*
		* Default enrollment estimate for this CLU.
		*/
		@Override
		public Integer getDefaultEnrollmentEstimate()
		{
			return this.defaultEnrollmentEstimate;
		}
						
		private Integer defaultMaximumEnrollment;
		
		/**
		* Set Default Maximum Enrollment
		*
		* Default maximum enrollment for this CLU.
		*/
		@Override
		public void setDefaultMaximumEnrollment(Integer defaultMaximumEnrollment)
		{
			this.defaultMaximumEnrollment = defaultMaximumEnrollment;
		}
		
		/**
		* Get Default Maximum Enrollment
		*
		* Default maximum enrollment for this CLU.
		*/
		@Override
		public Integer getDefaultMaximumEnrollment()
		{
			return this.defaultMaximumEnrollment;
		}
						
		private Boolean isHazardousForDisabledStudents;
		
		/**
		* Set Is Hazardous For Disabled Students
		*
		* Indicates if the CLU may be hazardous for students with disabilities. Would 
		* default to "false".
		*/
		@Override
		public void setIsHazardousForDisabledStudents(Boolean isHazardousForDisabledStudents)
		{
			this.isHazardousForDisabledStudents = isHazardousForDisabledStudents;
		}
		
		/**
		* Get Is Hazardous For Disabled Students
		*
		* Indicates if the CLU may be hazardous for students with disabilities. Would 
		* default to "false".
		*/
		@Override
		public Boolean isIsHazardousForDisabledStudents()
		{
			return this.isHazardousForDisabledStudents;
		}
						
		private CluFeeInfo feeInfo;
		
		/**
		* Set feeInfo
		*
		* Fee information associated with this CLU.
		*/
		@Override
		public void setFeeInfo(CluFeeInfo feeInfo)
		{
			this.feeInfo = feeInfo;
		}
		
		/**
		* Get feeInfo
		*
		* Fee information associated with this CLU.
		*/
		@Override
		public CluFeeInfo getFeeInfo()
		{
			return this.feeInfo;
		}
						
		private CluAccountingInfo accountingInfo;
		
		/**
		* Set accountingInfo
		*
		* Accounting information associated with this CLU.
		*/
		@Override
		public void setAccountingInfo(CluAccountingInfo accountingInfo)
		{
			this.accountingInfo = accountingInfo;
		}
		
		/**
		* Get accountingInfo
		*
		* Accounting information associated with this CLU.
		*/
		@Override
		public CluAccountingInfo getAccountingInfo()
		{
			return this.accountingInfo;
		}
						
		private Map<String,String> attributes;
		
		/**
		* Set Generic/dynamic attributes
		*
		* List of key/value pairs, typically used for dynamic attributes.
		*/
		@Override
		public void setAttributes(Map<String,String> attributes)
		{
			this.attributes = attributes;
		}
		
		/**
		* Get Generic/dynamic attributes
		*
		* List of key/value pairs, typically used for dynamic attributes.
		*/
		@Override
		public Map<String,String> getAttributes()
		{
			return this.attributes;
		}
						
		private MetaInfo metaInfo;
		
		/**
		* Set Create/Update meta info
		*
		* Create and last update info for the structure. This is optional and treated as 
		* read only since the data is set by the internals of the service during 
		* maintenance operations.
		*/
		@Override
		public void setMetaInfo(MetaInfo metaInfo)
		{
			this.metaInfo = metaInfo;
		}
		
		/**
		* Get Create/Update meta info
		*
		* Create and last update info for the structure. This is optional and treated as 
		* read only since the data is set by the internals of the service during 
		* maintenance operations.
		*/
		@Override
		public MetaInfo getMetaInfo()
		{
			return this.metaInfo;
		}
						
		private String type;
		
		/**
		* Set Learning Unit Type
		*
		* Unique identifier for a learning unit type. Once set at create time, this field 
		* may not be updated.
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get Learning Unit Type
		*
		* Unique identifier for a learning unit type. Once set at create time, this field 
		* may not be updated.
		*/
		@Override
		public String getType()
		{
			return this.type;
		}
						
		private String state;
		
		/**
		* Set Learning Unit State
		*
		* The current status of the clu. The values for this field are constrained to 
		* those in the luState enumeration. A separate setup operation does not exist for 
		* retrieval of the meta data around this value. This field may not be updated 
		* through updating this structure and must instead be updated through a dedicated 
		* operation.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get Learning Unit State
		*
		* The current status of the clu. The values for this field are constrained to 
		* those in the luState enumeration. A separate setup operation does not exist for 
		* retrieval of the meta data around this value. This field may not be updated 
		* through updating this structure and must instead be updated through a dedicated 
		* operation.
		*/
		@Override
		public String getState()
		{
			return this.state;
		}
						
		private String id;
		
		/**
		* Set CLU Identifier
		*
		* Unique identifier for a Canonical Learning Unit (CLU). This is optional, due to 
		* the identifier being set at the time of creation. Once the CLU has been created, 
		* this should be seen as required.
		*/
		@Override
		public void setId(String id)
		{
			this.id = id;
		}
		
		/**
		* Get CLU Identifier
		*
		* Unique identifier for a Canonical Learning Unit (CLU). This is optional, due to 
		* the identifier being set at the time of creation. Once the CLU has been created, 
		* this should be seen as required.
		*/
		@Override
		public String getId()
		{
			return this.id;
		}
						
	}

