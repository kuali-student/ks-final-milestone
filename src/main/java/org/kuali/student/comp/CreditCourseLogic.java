/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.kuali.student.core.atp.dto.AtpDurationTypeInfo;
import org.kuali.student.core.atp.dto.AtpTypeInfo;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;

/**
 *
 * @author nwright
 */
public class CreditCourseLogic
{

 public List<AtpTypeInfo> getPossibleTermsOffered ()
  throws OperationFailedException
 {
  List<AtpTypeInfo> list = new ArrayList ();
  for (AtpTypeInfo info : atpService.getAtpTypes ())
  {
   if (possibleTermsOfferred.contains (info.getId ()))
   {
    list.add (info);
   }
  }
  return list;
 }

 public List<AtpDurationTypeInfo> getPossibleDuration ()
  throws OperationFailedException
 {
  List<AtpDurationTypeInfo> list = new ArrayList ();
  for (AtpDurationTypeInfo info : atpService.getAtpDurationTypes ())
  {
   if (possibleDuration.contains (info.getId ()))
   {
    list.add (info);
   }
  }
  return list;
 }

 private AtpService atpService;

 public void setAtpService (AtpService atpService)
 {
  this.atpService = atpService;
 }

 private LuService luService;

 public void setLuService (LuService luService)
 {
  this.luService = luService;
 }

 private Set<String> possibleTermsOfferred;

 public void setPossibleTermsOffered (Set<String> atpTypeKeys)
 {
  this.possibleTermsOfferred = atpTypeKeys;
 }

 private Set<String> possibleDuration;

 public void setPossibleDuration (Set<String> durationTypeKeys)
 {
  this.possibleDuration = durationTypeKeys;
 }

 public enum SubjectArea
 {

  AACH ("AACH", "Art & Architecture"),
  BENG ("BENG", "Biomedical Engineering"),
  CHEE ("CHEE", "Chemical Engineering"),
  CSCI ("CSCI", "Computer Science"),
  ROBT ("ROBT", "Robotics"),
  EDUC ("EDUC", "Education"),
  ENGL ("ENGL", "English"),
  ARTS ("ARTS", "Fine Arts"),
  FREN ("FREN", "French"),
  GEOG ("GEOG", "Geography"),
  HIST ("HIST", "History"),
  LING ("LING", "Linguistics"),
  MENG ("MENG", "Mechanical Engineering"),
  MUSC ("MUSC", "Music"),
  PHIL ("PHIL", "Philosophy"),
  POLI ("POLI", "Political Science"),
  PSYC ("PSYC", "Psychology"),
  PUAD ("PUAD", "Public Administration"),
  SOWK ("SOWK", "Social Work"),
  SOCI ("SOCI", "Sociology");
  private final String code;
  private final String description;

  SubjectArea (String code, String description)
  {
   this.code = code;
   this.description = description;
  }

  public String getCode ()
  {
   return code;
  }

  public String getDescription ()
  {
   return description;
  }

 }

 public List<SubjectArea> getAllowedSubjectAreasForDeptName (String deptName)
 {
  // Note: This is actually defined by
  // https://test.kuali.org/confluence/display/KULSTU/LuConfig.Dictionary.Enumeration.SubjectArea
  // which says this should be stored in an orgCode field and retrieved by a search but in my environment I don't have 
  // the org service defined so this will have to do
  // Also...
  // the parameter should be deptId not name... but I don't know how do hard code this otherwise
  // because I don't know the departments id's
  List<SubjectArea> list = new ArrayList ();
  for (SubjectArea area : SubjectArea.values ())
  {
   if (area.getDescription ().equals (deptName))
   {
    list.add (area);
   }
  }
  if (deptName.equals ("Computer Science"))
  {
   list.add (SubjectArea.ROBT);
  }
  if (deptName.equals ("Mechanical Engineering"))
  {
   list.add (SubjectArea.ROBT);
  }
  return list;
 }

 public class AvailableNumber
 {

  private final String number;
  private boolean available;

  public AvailableNumber (String number, boolean available)
  {
   this.number = number;
   this.available = available;
  }

  public String getNumber ()
  {
   return number;
  }

  public boolean isAvailable ()
  {
   return available;
  }

  public void setAvailable (boolean available)
  {
   this.available = available;
  }

 }

 public List<AvailableNumber> getAvailableNumbers (String subjectArea)
 {
  List<AvailableNumber> list = new ArrayList (1000);
  for (int i = 0; i < 1000; i ++)
  {
   String number = "" + i;
   while (number.length () < 3)
   {
    number = "0" + number;
   }

   list.add (new AvailableNumber (number, true));
  }
  // TODO: calculate which are not really available based on the subject area chosen
  return list;
 }

 public CreditCourse create (CreditCourse course)
  throws AlreadyExistsException,
         DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  CluInfo cluInfo = ServiceContext.get ().getLuService ().createClu (course.
   getCluInfo ().
   getType (), course.getCluInfo ());

  // TODO: add the formats and activities if they exist
  CreditCourse newCC =
   new CreditCourse (cluInfo);
  return newCC;
 }

 private Defaulter defaulter = new Defaulter ();

 public class Defaulter
 {

  public static final String CREDIT_COURSE_TYPE = "kuali.lu.type.CreditCourse";
  public static final String DRAFT_STATE = "draft";

  public void apply (CreditCourse course)
  {
   course.getCluInfo ().setType (CREDIT_COURSE_TYPE);
   // Don't set it on cluInfo directly because creditCourse has logic to
   // replicate the state down to sub-clu if needed
   course.setState (DRAFT_STATE);
  }

 }

 public void setDefaulter (Defaulter defaulter)
 {
  this.defaulter = defaulter;
 }

 public Defaulter getDefaulter ()
 {
  return defaulter;
 }

 public class StdDurationDefaulter extends Defaulter
 {

  @Override
  public void apply (CreditCourse creditCourse)
  {
   creditCourse.getCluInfo ().getStdDuration ().setTimeQuantity (1);
  }

 }
 Defaulter stdDurationDefaulter = new StdDurationDefaulter ();

 public Defaulter getStdDurationDefaulter ()
 {
  return stdDurationDefaulter;
 }

 public void setStdDurationDefaulter (Defaulter defaulter)
 {
  this.stdDurationDefaulter = defaulter;
 }

 public static final String OFFICIAL_IDENTIFIER_TYPE =
  "kuali.lu.type.CreditCourse.identifier.official";
 public static final String ACTIVE_STATE = "active";

 public class OfficialIdentifierDefaulter extends Defaulter
 {

  @Override
  public void apply (CreditCourse creditCourse)
  {
   creditCourse.getCluInfo ().getOfficialIdentifier ().setType (OFFICIAL_IDENTIFIER_TYPE);
   creditCourse.getCluInfo ().getOfficialIdentifier ().setState (ACTIVE_STATE);
  }

 }
 private OfficialIdentifierDefaulter officialIdentifierDefaulter =
  new OfficialIdentifierDefaulter ();

 public Defaulter getOfficialIdentifierDefaulter ()
 {
  return officialIdentifierDefaulter;
 }

 public void setOfficialIdentifierDefaulter (
  OfficialIdentifierDefaulter defaulter)
 {
  this.officialIdentifierDefaulter = defaulter;
 }

 public class PrimaryAdminOrgDefaulter extends Defaulter
 {

  @Override
  public void apply (CreditCourse creditCourse)
  {
   // this is where you might create a default value for the dynamic attributes
  }

 }
 private Defaulter primaryAdminOrgDefaulter =
  new PrimaryAdminOrgDefaulter ();

 public Defaulter getPrimaryAdminOrgDefaulter ()
 {
  return primaryAdminOrgDefaulter;
 }

 public void setPrimaryAdminOrgDefaulter (
  PrimaryAdminOrgDefaulter defaulter)
 {
  this.primaryAdminOrgDefaulter = defaulter;
 }

 public class OfficialIdentifierCalculator extends Defaulter
 {

  @Override
  public void apply (CreditCourse creditCourse)
  {
   CluIdentifierInfo info = creditCourse.getCluInfo ().getOfficialIdentifier ();
   info.setCode (calculate (info));
  }

  public String calculate (CluIdentifierInfo info)
  {
   return calculate (info.getDivision (), info.getSuffixCode (), info.
    getVariation ());
  }

  public String calculate (String subjectArea, String suffixCode, String version)
  {
   if (subjectArea == null)
   {
    return null;
   }
   if (suffixCode == null)
   {
    return null;
   }
   String code = subjectArea + suffixCode;
   if (version != null)
   {
    code = code + version;
   }
   return code;
  }

 }
 private Defaulter officialIdentifierCalculator =
  new OfficialIdentifierCalculator ();

 public Defaulter getOfficialIdentifierCalculator ()
 {
  return officialIdentifierCalculator;
 }

 public void setOfficialIdentifierCalculator (
  OfficialIdentifierCalculator calculator)
 {
  this.officialIdentifierCalculator = calculator;
 }

 private Copier copier = new Copier ();

 public Copier getCopier ()
 {
  return copier;
 }

 public void setCopier (Copier copier)
 {
  this.copier = copier;
 }

 public class Copier
 {

  private CourseFormatLogic.Copier formatCopier;

  public void setFormatCopier (CourseFormatLogic.Copier copier)
  {
   this.formatCopier = copier;
  }

  public CourseFormatLogic.Copier getFormatCopier ()
  {
   return formatCopier;
  }

  public void copy (CreditCourse source, CreditCourse destination)
  {
   destination.setTranscriptTitle (source.getTranscriptTitle ());
   destination.setCourseTitle (source.getCourseTitle ());
   destination.setDescription (new RichTextInfoCopier (source.getDescription ()).
    copy ());
   destination.setDepartment (source.getDepartment ());
   destination.setDuration (source.getDuration ());
   destination.setTermsOffered (new StringListCopier (source.getTermssOffered ()).
    copy ());
   for (CourseFormat srcFormat : source.getCourseFormats ())
   {
    CourseFormat destFormat = destination.addCourseFormat ();
    formatCopier.copy (srcFormat, destFormat);
   }
  }

 }

  private Copier toNewFromTemplateCopier = new Copier ();

 public Copier getToNewFromTemplateCopier ()
 {
  toNewFromTemplateCopier.setFormatCopier (LogicContext.get ().
   getCourseFormatLogic ().getToNewFromTemplateCopier ());
  return toNewFromTemplateCopier;
 }

 public void setToNewFromTemplateCopier (Copier copier)
 {
  this.toNewFromTemplateCopier = copier;
 }

 private Copier toNewFromExistingCourseCopier = new Copier ();

 public Copier getToNewFromExistingCourseCopier ()
 {
  toNewFromExistingCourseCopier.setFormatCopier (LogicContext.get ().
   getCourseFormatLogic ().getToNewFromExistingCourseCopier ());
  return toNewFromExistingCourseCopier;
 }

 public void setToNewFromExistingCourseCopier (Copier copier)
 {
  this.toNewFromExistingCourseCopier = copier;
 }

 private Copier toNewFromExistingProposalCopier = new Copier ();

 public Copier getToNewFromExistingProposalCopier ()
 {
  toNewFromExistingProposalCopier.setFormatCopier (LogicContext.get ().
   getCourseFormatLogic ().getToNewFromExistingProposalCopier ());
  return toNewFromExistingProposalCopier;
 }

 public void setToNewFromExistingProposalCopier (
  Copier newFromExistingProposalCopier)
 {
  this.toNewFromExistingProposalCopier = newFromExistingProposalCopier;
 }

 private Copier modifyExistingCourseCopier = new Copier ();

 public Copier getModifyExistingCourseCopier ()
 {
  modifyExistingCourseCopier.setFormatCopier (LogicContext.get ().
   getCourseFormatLogic ().getModifyExistingCourseCopier ());
  return modifyExistingCourseCopier;
 }

 public void setModifyExistingCourseCopier (Copier copier)
 {
  this.modifyExistingCourseCopier = copier;
 }



}
