/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.service.dto;

/**
 *
 * @author nwright
 */
public enum CluField
{

 officialIdentifier ("officialIdentifier"),
 alternateIdentifiers ("alternateIdentifiers"),
 academicSubjectOrgs ("academicSubjectOrgs"),
 studySubjectArea ("studySubjectArea"),
 desc ("desc"),
 marketingDesc ("marketingDesc"),
 campusLocationList ("campusLocationList"),
 accreditation ("accreditation"),
 primaryAdminOrg ("primaryAdminOrg"),
 alternateAdminOrgs ("alternateAdminOrgs"),
 primaryInstructor ("primaryInstructor"),
 instructors ("instructors"),
 expectedFirstAtp ("expectedFirstAtp"),
 effectiveDate (""),
 expirationDate ("expirationDate"),
 intensity ("intensity"),
 stdDuration ("stdDuration"),
 canCreateLui ("canCreateLui"),
 referenceURL ("referenceURL"),
 luCodes ("luCodes"),
 publishingInfo ("publishingInfo"),
 nextReviewPeriod ("nextReviewPeriod"),
 isEnrollable ("isEnrollable"),
 offeredAtpTypes ("offeredAtpTypes"),
 hasEarlyDropDeadline ("hasEarlyDropDeadline"),
 defaultEnrollmentEstimate ("defaultEnrollmentEstimate"),
 defaultMaximumEnrollment ("defaultMaximumEnrollment"),
 isHazardousForDisabledStudents ("isHazardousForDisabledStudents"),
 feeInfo ("feeInfo"),
 accountingInfo ("accountingInfo"),
 attributes ("attributes"),
 metaInfo ("metaInfo"),
 type ("type"),
 state ("state"),
 id ("id");
 private final String fieldName;

 CluField (String fieldName)
 {
  this.fieldName = fieldName;
 }

 public String getFieldName ()
 {
  return fieldName;
 }

}

