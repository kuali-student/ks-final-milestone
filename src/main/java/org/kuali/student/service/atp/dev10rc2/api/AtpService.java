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
package org.kuali.student.service.atp.dev10rc2.api;


import java.util.Date;
import java.util.List;


public interface AtpService
{
	
	/**
	* Retrieves the list of academic time period types known by this service
	* 
	* @return list of academic time period types
	*/
	public List<AtpTypeInfo> getAtpTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a particular academic time period type
	* 
	* @return academic time period type information
	*/
	public AtpTypeInfo getAtpType(String atpTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of Academic Time Period Seasonal Types known by this service
	* 
	* @return List of academic time period seasonal types
	*/
	public List<AtpSeasonalTypeInfo> getAtpSeasonalTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a particular academic time period seasonal type
	* 
	* @return academic time period seasonal type information
	*/
	public AtpSeasonalTypeInfo getAtpSeasonalType(String atpSeasonalTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of Academic Time Period Duration Types known by this service
	* 
	* @return List of academic time period duration types
	*/
	public List<AtpDurationTypeInfo> getAtpDurationTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a particular academic time period duration type
	* 
	* @return academic time period duration type information
	*/
	public AtpDurationTypeInfo getAtpDurationType(String atpDurationTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of milestone types known by this service
	* 
	* @return List of milestone types
	*/
	public List<MilestoneTypeInfo> getMilestoneTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a particular milestone type
	* 
	* @return milestone type information
	*/
	public MilestoneTypeInfo getMilestoneType(String milestoneTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of milestone types that are defined for a particular Atp Type
	* 
	* @return List of milestone types
	*/
	public List<MilestoneTypeInfo> getMilestoneTypesForAtpType(String atpTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of date range types known by this service
	* 
	* @return List of date range types
	*/
	public List<DateRangeTypeInfo> getDateRangeTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a particular date range type
	* 
	* @return date range type information
	*/
	public DateRangeTypeInfo getDateRangeType(String dateRangeTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of dateRange types that are defined for a particular Atp Type
	* 
	* @return List of milestone types
	*/
	public List<DateRangeTypeInfo> getDateRangeTypesForAtpType(String atpTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Validates an academic time period. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained subobjects or expanded to perform all tests related to this object. If an identifier is present for the academic time period and a record is found for that identifier, the validation checks if the academic time period can be shifted to the new values. If a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object. This is a slightly different pattern from the standard validation as the caller provides the identifier in the create statement instead of the server assigning an identifier.
	* 
	* @return Results from performing the validation
	*/
	public List<ValidationResultInfo> validateAtp(String validationType, AtpInfo atpInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Validates a milestone. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained subobjects or expanded to perform all tests related to this object. If an identifier is present for the milestone and a record is found for that identifier, the validation checks if the milestone can be shifted to the new values. If a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object. This is a slightly different pattern from the standard validation as the caller provides the identifier in the create statement instead of the server assigning an identifier.
	* 
	* @return Results from performing the validation
	*/
	public List<ValidationResultInfo> validateMilestone(String validationType, MilestoneInfo milestoneInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Validates a date range. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained subobjects or expanded to perform all tests related to this object. If an identifier is present for the date range and a record is found for that identifier, the validation checks if the academic time period can be shifted to the new values. If a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object. This is a slightly different pattern from the standard validation as the caller provides the identifier in the create statement instead of the server assigning an identifier.
	* 
	* @return Results from performing the validation
	*/
	public List<ValidationResultInfo> validateDateRange(String validationType, DateRangeInfo dateRangeInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the details of a single Academic Time Period by atpKey
	* 
	* @return Details of the Academic Time Period requested
	*/
	public AtpInfo getAtp(String atpKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of Academic Time Periods that the supplied date falls within
	* 
	* @return List of Academic Time Periods that contain the supplied searchDate
	*/
	public List<AtpInfo> getAtpsByDate(Date searchDate)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of Academic Time Periods that are totally contained within the supplied dates. The entire Atp falls within the supplied dates
	* 
	* @return List of Academic Time Periods that contain the supplied searchDate
	*/
	public List<AtpInfo> getAtpsByDates(Date startDate, Date endDate)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves a list of Academic Time Periods of the specified type
	* 
	* @return List of Academic Time Periods that contain the supplied date
	*/
	public List<AtpInfo> getAtpsByAtpType(String atpTypeKey)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the details of the specified milestone
	* 
	* @return Details of requested milestone
	*/
	public MilestoneInfo getMilestone(String milestoneKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of milestones for a specified Academic Time Period
	* 
	* @return List of milestones for this Academic Time Period
	*/
	public List<MilestoneInfo> getMilestonesByAtp(String atpKey)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of milestones that fall within a specified set of dates
	* 
	* @return List of milestones that fall within this set of dates
	*/
	public List<MilestoneInfo> getMilestonesByDates(Date startDate, Date endDate)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves a list of milestones of a specified type that fall within a specified set of dates
	* 
	* @return List of milestones of this milestone type within this set of dates
	*/
	public List<MilestoneInfo> getMilestonesByDatesAndType(String milestoneTypeKey, Date startDate, Date endDate)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the details of the specified daterange
	* 
	* @return Details of requested daterange
	*/
	public DateRangeInfo getDateRange(String dateRangeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of DateRanges for a specified Academic Time Period
	* 
	* @return List of dateRanges for this Academic Time Period
	*/
	public List<DateRangeInfo> getDateRangesByAtp(String atpKey)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of DateRanges which encompass the specified date
	* 
	* @return List of dateRanges that contain the supplied searchDate
	*/
	public List<DateRangeInfo> getDateRangesByDate(Date searchDate)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Creates a new Academic Time Period
	* 
	* @return Details of ATP just created
	*/
	public AtpInfo createAtp(String atpTypeKey, String atpKey, AtpInfo atpInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates an existing Academic Time Period
	* 
	* @return Details of ATP just updated
	*/
	public AtpInfo updateAtp(String atpKey, AtpInfo atpInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Deletes an existing Academic Time Period
	* 
	* @return status of the operation (success, failed)
	*/
	public StatusInfo deleteAtp(String atpKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Add a new milestone to an existing Academic Time Period
	* 
	* @return Details of the newly created milestone
	*/
	public MilestoneInfo addMilestone(String atpKey, String milestoneKey, MilestoneInfo milestoneInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates an existing milestone.
	* 
	* @return Details of the updated milestone
	*/
	public MilestoneInfo updateMilestone(String milestoneKey, MilestoneInfo milestoneInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Removes an existing milestone.
	* 
	* @return Status of the operation (success, failed)
	*/
	public StatusInfo removeMilestone(String milestoneKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Adds a new dateRange to an existing Academic Time Period
	* 
	* @return details of the newly created dateRange
	*/
	public DateRangeInfo addDateRange(String atpKey, String dateRangeKey, DateRangeInfo dateRangeInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates an existing daterange
	* 
	* @return details of the updated dateRange
	*/
	public DateRangeInfo updateDateRange(String dateRangeKey, DateRangeInfo dateRangeInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Removes an existing daterange.
	* 
	* @return status of the operation (success, failed)
	*/
	public StatusInfo removeDateRange(String dateRangeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
}

