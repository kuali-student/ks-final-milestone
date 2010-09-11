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

/**
 *
 * @author nwright
 */
public class CreditCourse
{

private String code;
private String subjectArea;
private String courseNumberSuffixCode;
private String variation;
private String administeringOrgName;
private String administeringOrgId;
private String transcriptTitle;
private String courseTitle;
private String descr;
private String credits;
private String restrictions;
private String prereq;
private String prereqNL;
private String coreq;
private String coreqNL;
private String equivalencies;
private String gradingOptions;
private String termsOffered;
private String requirementsMet;
private String learningObjectives;

 public String getCode ()
 {
  return code;
 }

 public void setCode (String code)
 {
  this.code = code;
 }

 public String getCoreq ()
 {
  return coreq;
 }

 public String getVariation ()
 {
  return variation;
 }

 public void setVariation (String variation)
 {
  this.variation = variation;
 }

 
 public void setCoreq (String coreq)
 {
  this.coreq = coreq;
 }

 public String getCoreqNL ()
 {
  return coreqNL;
 }

 public void setCoreqNL (String coreqNL)
 {
  this.coreqNL = coreqNL;
 }

 public String getCredits ()
 {
  return credits;
 }

 public void setCredits (String credits)
 {
  this.credits = credits;
 }

 public String getDescr ()
 {
  return descr;
 }

 public void setDescr (String descr)
 {
  this.descr = descr;
 }

 public String getSubjectArea ()
 {
  return subjectArea;
 }

 public void setSubjectArea (String subjectArea)
 {
  this.subjectArea = subjectArea;
 }

 public String getEquivalencies ()
 {
  return equivalencies;
 }

 public void setEquivalencies (String equivalencies)
 {
  this.equivalencies = equivalencies;
 }

 public String getGradingOptions ()
 {
  return gradingOptions;
 }

 public void setGradingOptions (String gradingOptions)
 {
  this.gradingOptions = gradingOptions;
 }

 public String getLearningObjectives ()
 {
  return learningObjectives;
 }

 public void setLearningObjectives (String learningObjectives)
 {
  this.learningObjectives = learningObjectives;
 }

 public String getTermsOffered ()
 {
  return termsOffered;
 }

 public void setTermsOffered (String termsOffered)
 {
  this.termsOffered = termsOffered;
 }

 public String getPrereqNL ()
 {
  return prereqNL;
 }

 public void setPrereqNL (String prereqNL)
 {
  this.prereqNL = prereqNL;
 }

 public String getPrereq ()
 {
  return prereq;
 }

 public void setPrereq (String prereq)
 {
  this.prereq = prereq;
 }

 public String getAdministeringOrgName ()
 {
  return administeringOrgName;
 }

 public void setAdministeringOrgName (String name)
 {
  this.administeringOrgName = name;
 }

 public String getAdministeringOrg ()
 {
  return administeringOrgId;
 }

 public void setAdministeringOrg (String orgId)
 {
  this.administeringOrgId = orgId;
 }

 public String getRequirementsMet ()
 {
  return requirementsMet;
 }

 public void setRequirementsMet (String requirementsMet)
 {
  this.requirementsMet = requirementsMet;
 }

 public String getRestrictions ()
 {
  return restrictions;
 }

 public void setRestrictions (String restrictions)
 {
  this.restrictions = restrictions;
 }

 public String getTranscriptTitle ()
 {
  return transcriptTitle;
 }

 public void setTranscriptTitle (String title)
 {
  this.transcriptTitle = title;
 }

 public String getCourseTitle ()
 {
  return courseTitle;
 }

 public void setCourseTitle (String title)
 {
  this.courseTitle = title;
 }

 public String getCourseNumberSuffix ()
 {
  return courseNumberSuffixCode;
 }

 public void setCourseNumberSuffix (String suffixCode)
 {
  this.courseNumberSuffixCode = suffixCode;
 }


 
}
