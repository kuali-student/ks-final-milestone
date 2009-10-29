/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import java.util.Date;
import java.util.List;
import org.kuali.student.core.atp.dto.AtpDurationTypeInfo;
import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.atp.dto.AtpSeasonalTypeInfo;
import org.kuali.student.core.atp.dto.AtpTypeInfo;
import org.kuali.student.core.atp.dto.DateRangeInfo;
import org.kuali.student.core.atp.dto.DateRangeTypeInfo;
import org.kuali.student.core.atp.dto.MilestoneInfo;
import org.kuali.student.core.atp.dto.MilestoneTypeInfo;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.validation.dto.ValidationResultContainer;

/**
 *
 * @author nwright
 */
public class MockAtpService implements AtpService
{

 public DateRangeInfo addDateRange (String atpKey, String dateRangeKey,
                                    DateRangeInfo dateRangeInfo)
  throws AlreadyExistsException,
         DataValidationErrorException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public MilestoneInfo addMilestone (String atpKey, String milestoneKey,
                                    MilestoneInfo milestoneInfo)
  throws AlreadyExistsException,
         DataValidationErrorException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public AtpInfo createAtp (String atpTypeKey, String atpKey, AtpInfo atpInfo)
  throws AlreadyExistsException,
         DataValidationErrorException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public StatusInfo deleteAtp (String atpKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public AtpInfo getAtp (String atpKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public AtpDurationTypeInfo getAtpDurationType (String atpDurationTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<AtpDurationTypeInfo> getAtpDurationTypes ()
  throws OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public AtpSeasonalTypeInfo getAtpSeasonalType (String atpSeasonalTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<AtpSeasonalTypeInfo> getAtpSeasonalTypes ()
  throws OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public AtpTypeInfo getAtpType (String atpTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<AtpTypeInfo> getAtpTypes ()
  throws OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<AtpInfo> getAtpsByAtpType (String atpTypeKey)
  throws InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<AtpInfo> getAtpsByDate (Date searchDate)
  throws InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<AtpInfo> getAtpsByDates (Date startDate, Date endDate)
  throws InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public DateRangeInfo getDateRange (String dateRangeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public DateRangeTypeInfo getDateRangeType (String dateRangeTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<DateRangeTypeInfo> getDateRangeTypes ()
  throws OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<DateRangeTypeInfo> getDateRangeTypesForAtpType (String atpTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<DateRangeInfo> getDateRangesByAtp (String atpKey)
  throws InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<DateRangeInfo> getDateRangesByDate (Date searchDate)
  throws InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public MilestoneInfo getMilestone (String milestoneKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public MilestoneTypeInfo getMilestoneType (String milestoneTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<MilestoneTypeInfo> getMilestoneTypes ()
  throws OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<MilestoneTypeInfo> getMilestoneTypesForAtpType (String atpTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<MilestoneInfo> getMilestonesByAtp (String atpKey)
  throws InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<MilestoneInfo> getMilestonesByDates (Date startDate, Date endDate)
  throws InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<MilestoneInfo> getMilestonesByDatesAndType (String milestoneTypeKey,
                                                         Date startDate,
                                                         Date endDate)
  throws InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public StatusInfo removeDateRange (String dateRangeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public StatusInfo removeMilestone (String milestoneKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public AtpInfo updateAtp (String atpKey, AtpInfo atpInfo)
  throws DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         VersionMismatchException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public DateRangeInfo updateDateRange (String dateRangeKey,
                                       DateRangeInfo dateRangeInfo)
  throws DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         VersionMismatchException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public MilestoneInfo updateMilestone (String milestoneKey,
                                       MilestoneInfo milestoneInfo)
  throws DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         VersionMismatchException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<ValidationResultContainer> validateAtp (String validationType,
                                                     AtpInfo atpInfo)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<ValidationResultContainer> validateDateRange (String validationType,
                                                           DateRangeInfo dateRangeInfo)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<ValidationResultContainer> validateMilestone (String validationType,
                                                           MilestoneInfo milestoneInfo)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

}
