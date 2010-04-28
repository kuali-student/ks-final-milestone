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

private String id;
private String code;
private String division;
private String suffixCode;
private String variation;
private String primaryAdminOrg;
private String primaryAdminOrgId;
private String shortName;
private String longName;
private String desc;
private String credits;
private String restrictions;
private String prereq;
private String prereqNL;
private String coreq;
private String coreqNL;
private String equivalencies;
private String gradingOptions;
private String offeredAtpTypes;
private String requirementsMet;
private String learningObjectives;

 public String getId ()
 {
  return id;
 }

 public void setId (String id)
 {
  this.id = id;
 }

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

 public String getDesc ()
 {
  return desc;
 }

 public void setDesc (String desc)
 {
  this.desc = desc;
 }

 public String getDivision ()
 {
  return division;
 }

 public void setDivision (String division)
 {
  this.division = division;
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

 public String getOfferedAtpTypes ()
 {
  return offeredAtpTypes;
 }

 public void setOfferedAtpTypes (String offeredAtpTypes)
 {
  this.offeredAtpTypes = offeredAtpTypes;
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

 public String getPrimaryAdminOrg ()
 {
  return primaryAdminOrg;
 }

 public void setPrimaryAdminOrg (String primaryAdminOrg)
 {
  this.primaryAdminOrg = primaryAdminOrg;
 }

 public String getPrimaryAdminOrgId ()
 {
  return primaryAdminOrgId;
 }

 public void setPrimaryAdminOrgId (String primaryAdminOrgId)
 {
  this.primaryAdminOrgId = primaryAdminOrgId;
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

 public String getShortName ()
 {
  return shortName;
 }

 public void setShortName (String shortName)
 {
  this.shortName = shortName;
 }

 public String getLongName ()
 {
  return longName;
 }

 public void setLongName (String longName)
 {
  this.longName = longName;
 }

 public String getSuffixCode ()
 {
  return suffixCode;
 }

 public void setSuffixCode (String suffixCode)
 {
  this.suffixCode = suffixCode;
 }


 
}
