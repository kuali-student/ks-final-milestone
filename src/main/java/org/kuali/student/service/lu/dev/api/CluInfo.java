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


import java.util.Date;
import java.util.List;
import java.util.Map;


public interface CluInfo
{
	
	/**
	* Set Official Idenfifier
	*
	* Information related to the official identification of the clu, typically in 
	* human readable form. Used to officially reference or publish.
	*/
	public void setOfficialIdentifier(CluIdentifierInfo officialIdentifier);
	
	/**
	* Get Official Idenfifier
	*
	* Information related to the official identification of the clu, typically in 
	* human readable form. Used to officially reference or publish.
	*/
	public CluIdentifierInfo getOfficialIdentifier();
	
	
	
	/**
	* Set Alternate Idenfifiers
	*
	* Information related to alternate identifications of the clu, typically in human 
	* readable form. Used to reference or publish.
	*/
	public void setAlternateIdentifiers(List<CluIdentifierInfo> alternateIdentifiers);
	
	/**
	* Get Alternate Idenfifiers
	*
	* Information related to alternate identifications of the clu, typically in human 
	* readable form. Used to reference or publish.
	*/
	public List<CluIdentifierInfo> getAlternateIdentifiers();
	
	
	
	/**
	* Set Academic Subject Orgs
	*
	* The organizations that represents the Subject area of the Clu, if different from 
	* the PrimaryAdminOrg?
	*/
	public void setAcademicSubjectOrgs(List<AcademicSubjectOrgInfo> academicSubjectOrgs);
	
	/**
	* Get Academic Subject Orgs
	*
	* The organizations that represents the Subject area of the Clu, if different from 
	* the PrimaryAdminOrg?
	*/
	public List<AcademicSubjectOrgInfo> getAcademicSubjectOrgs();
	
	
	
	/**
	* Set Study Subject Area
	*
	* The Study Subject Area is used to identify the area of study associated with the 
	* clu. It may be a general study area (e.g. Chemistry) or very specific (e.g. 
	* Naval Architecture) depending on the level of specificity of the clu.
	*/
	public void setStudySubjectArea(String studySubjectArea);
	
	/**
	* Get Study Subject Area
	*
	* The Study Subject Area is used to identify the area of study associated with the 
	* clu. It may be a general study area (e.g. Chemistry) or very specific (e.g. 
	* Naval Architecture) depending on the level of specificity of the clu.
	*/
	public String getStudySubjectArea();
	
	
	
	/**
	* Set Description
	*
	* Narrative description of the CLU, used for the catalog.
	*/
	public void setDesc(RichTextInfo desc);
	
	/**
	* Get Description
	*
	* Narrative description of the CLU, used for the catalog.
	*/
	public RichTextInfo getDesc();
	
	
	
	/**
	* Set List of Campus Locations
	*
	* Places where this clu might be offered
	*/
	public void setCampusLocations(List<String> campusLocations);
	
	/**
	* Get List of Campus Locations
	*
	* Places where this clu might be offered
	*/
	public List<String> getCampusLocations();
	
	
	
	/**
	* Set Accreditations
	*
	* Information around the accreditation of the clu.
	*/
	public void setAccreditations(List<AccreditationInfo> accreditations);
	
	/**
	* Get Accreditations
	*
	* Information around the accreditation of the clu.
	*/
	public List<AccreditationInfo> getAccreditations();
	
	
	
	/**
	* Set Admin Org
	*
	* The primary organization (typically, an academic department) with administrative 
	* oversight over the CLU. This will be used for Authorization and Workflow.
	*/
	public void setPrimaryAdminOrg(AdminOrgInfo primaryAdminOrg);
	
	/**
	* Get Admin Org
	*
	* The primary organization (typically, an academic department) with administrative 
	* oversight over the CLU. This will be used for Authorization and Workflow.
	*/
	public AdminOrgInfo getPrimaryAdminOrg();
	
	
	
	/**
	* Set Alternate Admin Orgs
	*
	* For situations where more than one Organization shares in the Administration of 
	* this clu. This will be used in Authorization and Workflow
	*/
	public void setAlternateAdminOrgs(List<AdminOrgInfo> alternateAdminOrgs);
	
	/**
	* Get Alternate Admin Orgs
	*
	* For situations where more than one Organization shares in the Administration of 
	* this clu. This will be used in Authorization and Workflow
	*/
	public List<AdminOrgInfo> getAlternateAdminOrgs();
	
	
	
	/**
	* Set Primary Instructor
	*
	* Primary potential instructor for the clu. This is primarily for use in 
	* advertising the clu and may not be the actual instructor.
	*/
	public void setPrimaryInstructor(CluInstructorInfo primaryInstructor);
	
	/**
	* Get Primary Instructor
	*
	* Primary potential instructor for the clu. This is primarily for use in 
	* advertising the clu and may not be the actual instructor.
	*/
	public CluInstructorInfo getPrimaryInstructor();
	
	
	
	/**
	* Set Instructors
	*
	* Instructors associated with this clu. This may not be an exhaustive list, and 
	* instead may only be used to indicate potential instructors in publication.
	*/
	public void setInstructors(List<CluInstructorInfo> instructors);
	
	/**
	* Get Instructors
	*
	* Instructors associated with this clu. This may not be an exhaustive list, and 
	* instead may only be used to indicate potential instructors in publication.
	*/
	public List<CluInstructorInfo> getInstructors();
	
	
	
	/**
	* Set Expected First ATP
	*
	* The expected first academic time period that this clu would be effective. This 
	* may not reflect the first "real" academic time period for this clu.
	*/
	public void setExpectedFirstAtp(String expectedFirstAtp);
	
	/**
	* Get Expected First ATP
	*
	* The expected first academic time period that this clu would be effective. This 
	* may not reflect the first "real" academic time period for this clu.
	*/
	public String getExpectedFirstAtp();
	
	
	
	/**
	* Set Effective Date
	*
	* Date and time the CLU became effective. This is a similar concept to the 
	* effective date on enumerated values. When an expiration date has been specified, 
	* this field must be less than or equal to the expiration date.
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get Effective Date
	*
	* Date and time the CLU became effective. This is a similar concept to the 
	* effective date on enumerated values. When an expiration date has been specified, 
	* this field must be less than or equal to the expiration date.
	*/
	public Date getEffectiveDate();
	
	
	
	/**
	* Set Expiration Date
	*
	* Date and time that this CLU expires. This is a similar concept to the expiration 
	* date on enumerated values. If specified, this should be greater than or equal to 
	* the effective date. If this field is not specified, then no expiration date has 
	* been currently defined and should automatically be considered greater than the 
	* effective date.
	*/
	public void setExpirationDate(Date expirationDate);
	
	/**
	* Get Expiration Date
	*
	* Date and time that this CLU expires. This is a similar concept to the expiration 
	* date on enumerated values. If specified, this should be greater than or equal to 
	* the effective date. If this field is not specified, then no expiration date has 
	* been currently defined and should automatically be considered greater than the 
	* effective date.
	*/
	public Date getExpirationDate();
	
	
	
	/**
	* Set Intensity
	*
	* The expected level of time commitment between the student and the CLU meetings.
	*/
	public void setIntensity(AmountInfo intensity);
	
	/**
	* Get Intensity
	*
	* The expected level of time commitment between the student and the CLU meetings.
	*/
	public AmountInfo getIntensity();
	
	
	
	/**
	* Set Standard Duration
	*
	* The standard duration of the learning unit.
	*/
	public void setStdDuration(TimeAmountInfo stdDuration);
	
	/**
	* Get Standard Duration
	*
	* The standard duration of the learning unit.
	*/
	public TimeAmountInfo getStdDuration();
	
	
	
	/**
	* Set Can Create LUI
	*
	* Indicates if the CLU can be used to instantiate LUIs (offerings).
	*/
	public void setCanCreateLui(Boolean canCreateLui);
	
	/**
	* Get Can Create LUI
	*
	* Indicates if the CLU can be used to instantiate LUIs (offerings).
	*/
	public Boolean isCanCreateLui();
	
	
	
	/**
	* Set Reference URL
	*
	* An URL for additional information about the CLU. This could be a reference to a 
	* document which might in turn have references to other documents (e.g. course 
	* syllabus provided by the faculty or department, standard schedule of classes, 
	* etc.).
	*/
	public void setReferenceURL(String referenceURL);
	
	/**
	* Get Reference URL
	*
	* An URL for additional information about the CLU. This could be a reference to a 
	* document which might in turn have references to other documents (e.g. course 
	* syllabus provided by the faculty or department, standard schedule of classes, 
	* etc.).
	*/
	public String getReferenceURL();
	
	
	
	/**
	* Set LU Code Info
	*
	* List of LU code info structures. These are structures so that many different 
	* types of codes can be associated with the clu. This allows them to be put into 
	* categories.
	*/
	public void setLuCodes(List<LuCodeInfo> luCodes);
	
	/**
	* Get LU Code Info
	*
	* List of LU code info structures. These are structures so that many different 
	* types of codes can be associated with the clu. This allows them to be put into 
	* categories.
	*/
	public List<LuCodeInfo> getLuCodes();
	
	
	
	/**
	* Set Next Review Period
	*
	* When the next review should be
	*/
	public void setNextReviewPeriod(String nextReviewPeriod);
	
	/**
	* Get Next Review Period
	*
	* When the next review should be
	*/
	public String getNextReviewPeriod();
	
	
	
	/**
	* Set Is Enrollable
	*
	* Indicates if Luis generated from this Clu are intended to be enrolled in by 
	* Students directly
	*/
	public void setIsEnrollable(Boolean isEnrollable);
	
	/**
	* Get Is Enrollable
	*
	* Indicates if Luis generated from this Clu are intended to be enrolled in by 
	* Students directly
	*/
	public Boolean isIsEnrollable();
	
	
	
	/**
	* Set Offered Academic Time Period Types
	*
	* The academic time period types in which this CLU is typically offered. Standard 
	* usage would equate to terms. It can define a timeframe that a clu with of a 
	* certain stdDuration would fall in.
	*/
	public void setOfferedAtpTypes(List<String> offeredAtpTypes);
	
	/**
	* Get Offered Academic Time Period Types
	*
	* The academic time period types in which this CLU is typically offered. Standard 
	* usage would equate to terms. It can define a timeframe that a clu with of a 
	* certain stdDuration would fall in.
	*/
	public List<String> getOfferedAtpTypes();
	
	
	
	/**
	* Set Has Early Drop Deadline
	*
	* Indicates if the CLU has an Early Drop Deadline (EDD). Certain courses are 
	* designated as such to maximize access to courses that have historically 
	* experienced high demand and high attrition. Default is "false".
	*/
	public void setHasEarlyDropDeadline(Boolean hasEarlyDropDeadline);
	
	/**
	* Get Has Early Drop Deadline
	*
	* Indicates if the CLU has an Early Drop Deadline (EDD). Certain courses are 
	* designated as such to maximize access to courses that have historically 
	* experienced high demand and high attrition. Default is "false".
	*/
	public Boolean isHasEarlyDropDeadline();
	
	
	
	/**
	* Set Default Enrollment Estimate
	*
	* Default enrollment estimate for this CLU.
	*/
	public void setDefaultEnrollmentEstimate(Integer defaultEnrollmentEstimate);
	
	/**
	* Get Default Enrollment Estimate
	*
	* Default enrollment estimate for this CLU.
	*/
	public Integer getDefaultEnrollmentEstimate();
	
	
	
	/**
	* Set Default Maximum Enrollment
	*
	* Default maximum enrollment for this CLU.
	*/
	public void setDefaultMaximumEnrollment(Integer defaultMaximumEnrollment);
	
	/**
	* Get Default Maximum Enrollment
	*
	* Default maximum enrollment for this CLU.
	*/
	public Integer getDefaultMaximumEnrollment();
	
	
	
	/**
	* Set Is Hazardous For Disabled Students
	*
	* Indicates if the CLU may be hazardous for students with disabilities. Would 
	* default to "false".
	*/
	public void setIsHazardousForDisabledStudents(Boolean isHazardousForDisabledStudents);
	
	/**
	* Get Is Hazardous For Disabled Students
	*
	* Indicates if the CLU may be hazardous for students with disabilities. Would 
	* default to "false".
	*/
	public Boolean isIsHazardousForDisabledStudents();
	
	
	
	/**
	* Set feeInfo
	*
	* Fee information associated with this CLU.
	*/
	public void setFeeInfo(CluFeeInfo feeInfo);
	
	/**
	* Get feeInfo
	*
	* Fee information associated with this CLU.
	*/
	public CluFeeInfo getFeeInfo();
	
	
	
	/**
	* Set accountingInfo
	*
	* Accounting information associated with this CLU.
	*/
	public void setAccountingInfo(CluAccountingInfo accountingInfo);
	
	/**
	* Get accountingInfo
	*
	* Accounting information associated with this CLU.
	*/
	public CluAccountingInfo getAccountingInfo();
	
	
	
	/**
	* Set Generic/dynamic attributes
	*
	* List of key/value pairs, typically used for dynamic attributes.
	*/
	public void setAttributes(Map<String,String> attributes);
	
	/**
	* Get Generic/dynamic attributes
	*
	* List of key/value pairs, typically used for dynamic attributes.
	*/
	public Map<String,String> getAttributes();
	
	
	
	/**
	* Set Create/Update meta info
	*
	* Create and last update info for the structure. This is optional and treated as 
	* read only since the data is set by the internals of the service during 
	* maintenance operations.
	*/
	public void setMetaInfo(MetaInfo metaInfo);
	
	/**
	* Get Create/Update meta info
	*
	* Create and last update info for the structure. This is optional and treated as 
	* read only since the data is set by the internals of the service during 
	* maintenance operations.
	*/
	public MetaInfo getMetaInfo();
	
	
	
	/**
	* Set Learning Unit Type
	*
	* Unique identifier for a learning unit type. Once set at create time, this field 
	* may not be updated.
	*/
	public void setType(String type);
	
	/**
	* Get Learning Unit Type
	*
	* Unique identifier for a learning unit type. Once set at create time, this field 
	* may not be updated.
	*/
	public String getType();
	
	
	
	/**
	* Set Learning Unit State
	*
	* The current status of the clu. The values for this field are constrained to 
	* those in the luState enumeration. A separate setup operation does not exist for 
	* retrieval of the meta data around this value. This field may not be updated 
	* through updating this structure and must instead be updated through a dedicated 
	* operation.
	*/
	public void setState(String state);
	
	/**
	* Get Learning Unit State
	*
	* The current status of the clu. The values for this field are constrained to 
	* those in the luState enumeration. A separate setup operation does not exist for 
	* retrieval of the meta data around this value. This field may not be updated 
	* through updating this structure and must instead be updated through a dedicated 
	* operation.
	*/
	public String getState();
	
	
	
	/**
	* Set CLU Identifier
	*
	* Unique identifier for a Canonical Learning Unit (CLU). This is optional, due to 
	* the identifier being set at the time of creation. Once the CLU has been created, 
	* this should be seen as required.
	*/
	public void setId(String id);
	
	/**
	* Get CLU Identifier
	*
	* Unique identifier for a Canonical Learning Unit (CLU). This is optional, due to 
	* the identifier being set at the time of creation. Once the CLU has been created, 
	* this should be seen as required.
	*/
	public String getId();
	
	
	
}

