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


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.kuali.student.common.assembly.client.ConstraintMetadata;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.ConstraintMetadataBank;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.LookupMetadataBank;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoMetadata;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseHelper.Properties;


public class CreditCourseMetadata
{
	
	public boolean matches (String inputType, String inputState, String dictType, String dictState)
	{
		// TODO: code more complex matches
		return true;
	}
	
	public Metadata getMetadata (String type, String state)
	{
		Metadata mainMeta = new Metadata ();
		mainMeta.setDataType (Data.DataType.DATA);
		mainMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		Map <String, Integer> recursions = new HashMap ();
		loadChildMetadata (mainMeta, type, state, recursions);
		return mainMeta;
	}
	
	public void loadChildMetadata (Metadata mainMeta, String type, String state,  Map<String, Integer> recursions)
	{
		int recurseLevel = increment (recursions, "CreditCourseMetadata");
		
		Metadata childMeta;
		Metadata listMeta;
		
		// metadata for Id
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.ID.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("hidden"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("read.only"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("kuali.id"));
		}
		
		// metadata for Formats
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.FORMATS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
			ConstraintMetadata consMeta = new ConstraintMetadata ();
			consMeta.setMinOccurs (1);
			childMeta.getConstraints ().add (consMeta);
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.DATA);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new CreditCourseFormatMetadata ().loadChildMetadata (listMeta, type, state, recursions);
		}
		
		// metadata for TermsOffered
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.TERMS_OFFERED.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.setLookupMetadata (LookupMetadataBank.LOOKUP_BANK.get ("kuali.lu.lookup.creditCourse.termsOfferred".toLowerCase ()));
		childMeta.setLookupContextPath ("");
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("atp.types"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.STRING);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		
		// metadata for Duration
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.DURATION.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new CreditCourseDurationMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for TranscriptTitle
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.TRANSCRIPT_TITLE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			ConstraintMetadata consMeta = new ConstraintMetadata ();
			consMeta.setMaxLength (20);
			childMeta.getConstraints ().add (consMeta);
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single.line.text"));
		}
		
		// metadata for CourseTitle
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.COURSE_TITLE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			ConstraintMetadata consMeta = new ConstraintMetadata ();
			consMeta.setMaxLength (60);
			childMeta.getConstraints ().add (consMeta);
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single.line.text"));
		}
		
		// metadata for Description
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.DESCRIPTION.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new RichTextInfoMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for Department
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.DEPARTMENT.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.setLookupMetadata (LookupMetadataBank.LOOKUP_BANK.get ("kuali.lu.lookup.admin.departments".toLowerCase ()));
		childMeta.setLookupContextPath ("proposal.proposerPerson");
		childMeta.getAdditionalLookups ().add ((LookupMetadataBank.LOOKUP_BANK.get ("kuali.lu.lookup.admin.departments.additional.1".toLowerCase ())));
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("related.orgid"));
		}
		
		// metadata for SubjectArea
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.SUBJECT_AREA.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.setLookupMetadata (LookupMetadataBank.LOOKUP_BANK.get ("kuali.lu.lookup.subjectAreas".toLowerCase ()));
		childMeta.setLookupContextPath ("department");
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("subject.areas"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("code"));
		}
		
		// metadata for CourseNumberSuffix
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.COURSE_NUMBER_SUFFIX.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.setLookupMetadata (LookupMetadataBank.LOOKUP_BANK.get ("kuali.lu.lookup.available.numbers".toLowerCase ()));
		childMeta.setLookupContextPath ("subjectArea");
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("available.course.numbers"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("code"));
		}
		
		// metadata for CrossListings
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.CROSS_LISTINGS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.DATA);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new CreditCourseCrossListingsMetadata ().loadChildMetadata (listMeta, type, state, recursions);
		}
		
		// metadata for Versions
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.VERSIONS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.DATA);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new CreditCourseVersionsMetadata ().loadChildMetadata (listMeta, type, state, recursions);
		}
		
		// metadata for Joints
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.JOINTS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.DATA);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new CreditCourseJointsMetadata ().loadChildMetadata (listMeta, type, state, recursions);
		}
		
		// metadata for FinalResults
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.FINAL_RESULTS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		
		// metadata for Fees
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.FEES.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.DATA);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new FeeInfoMetadata ().loadChildMetadata (listMeta, type, state, recursions);
		}
		
		// metadata for State
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.STATE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.setDefaultValue (new Data.StringValue ("draft.public"));
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("kuali.state"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("lu.states"));
		}
		
		// metadata for Type
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.TYPE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ON_CREATE);
		childMeta.setDefaultValue (new Data.StringValue ("kuali.lu.type.CreditCourse"));
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("kuali.type"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("lu.types"));
		}
		
		// metadata for EffectiveDate
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.EFFECTIVE_DATE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.TRUNCATED_DATE);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("default.today"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("date.time"));
		}
		
		// metadata for ExpirationDate
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.EXPIRATION_DATE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.TRUNCATED_DATE);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("date.time"));
		}
		
		// metadata for AcademicSubjectOrgs
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.ACADEMIC_SUBJECT_ORGS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.setLookupMetadata (LookupMetadataBank.LOOKUP_BANK.get ("kuali.lu.lookup.oversight.orgs".toLowerCase ()));
		childMeta.setLookupContextPath ("");
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("not.used"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.STRING);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		
		// metadata for CampusLocations
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.CAMPUS_LOCATIONS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.setLookupMetadata (LookupMetadataBank.LOOKUP_BANK.get ("kuali.lu.lookup.campusLocations".toLowerCase ()));
		childMeta.setLookupContextPath ("");
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("code"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.STRING);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		
		// metadata for PrimaryInstructor
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.PRIMARY_INSTRUCTOR.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.setLookupMetadata (LookupMetadataBank.LOOKUP_BANK.get ("kuali.lu.lookup.instructors".toLowerCase ()));
		childMeta.setLookupContextPath ("department");
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("not.used"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		
		// metadata for CourseSpecificLOs
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.COURSE_SPECIFIC_L_OS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.DATA);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new CreditCourseCourseSpecificLOsMetadata ().loadChildMetadata (listMeta, type, state, recursions);
		}
		
		// metadata for _runtimeData
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties._RUNTIME_DATA.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new RuntimeDataMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
	}
	
	private int increment (Map<String, Integer> recursions, String key)
	{
		Integer recurseLevel = recursions.get (key);
		if (recurseLevel == null)
		{
			recursions.put (key, 0);
			return 0;
		}
		recursions.put (key, recurseLevel.intValue () + 1);
		return recurseLevel.intValue ();
	}
}

