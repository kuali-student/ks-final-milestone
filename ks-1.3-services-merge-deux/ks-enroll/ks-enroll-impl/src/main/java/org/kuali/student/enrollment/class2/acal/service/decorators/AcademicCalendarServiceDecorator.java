/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.class2.acal.service.decorators;

import java.util.Date;
import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.AcalEventInfo;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import javax.jws.WebParam;

public class AcademicCalendarServiceDecorator implements AcademicCalendarService {

    private AtpService atpService;

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    private AcademicCalendarService nextDecorator;

    public AcademicCalendarService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }

        return nextDecorator;
    }

    public void setNextDecorator(AcademicCalendarService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public TypeInfo getAcademicCalendarType(String academicCalendarTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getAcademicCalendarType(academicCalendarTypeKey, contextInfo));
    }

    @Override
    public List<TypeInfo> getAcademicCalendarTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getAcademicCalendarTypes(contextInfo));
    }

    @Override
    public StateInfo getAcademicCalendarState(String academicCalendarStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getAcademicCalendarState(academicCalendarStateKey, contextInfo));
    }

    @Override
    public List<StateInfo> getAcademicCalendarStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getAcademicCalendarStates(contextInfo));
    }

    @Override
    public AcademicCalendarInfo getAcademicCalendar(String academicCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getAcademicCalendar(academicCalendarId, contextInfo));
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByIds(List<String> academicCalendarIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getAcademicCalendarsByIds(academicCalendarIds, contextInfo));
    }

    @Override
    public List<String> getAcademicCalendarIdsByType(String academicCalendarTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getAcademicCalendarIdsByType(academicCalendarTypeKey, contextInfo));
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByStartYear(Integer year, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getAcademicCalendarsByStartYear(year, contextInfo));
    }

    @Override
    public List<String> searchForAcademicCalendarIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().searchForAcademicCalendarIds(criteria, contextInfo));
    }

    @Override
    public List<AcademicCalendarInfo> searchForAcademicCalendars(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().searchForAcademicCalendars(criteria, contextInfo));
    }

    @Override
    public List<ValidationResultInfo> validateAcademicCalendar(String validationTypeKey, String academicCalendarTypeKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().validateAcademicCalendar(validationTypeKey, academicCalendarTypeKey, academicCalendarInfo, contextInfo));
    }

    @Override
    public AcademicCalendarInfo createAcademicCalendar(String academicCalendarTypeKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return (getNextDecorator().createAcademicCalendar(academicCalendarTypeKey, academicCalendarInfo, contextInfo));
    }

    @Override
    public AcademicCalendarInfo updateAcademicCalendar(String academicCalendarId, AcademicCalendarInfo academicCalendarInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return (getNextDecorator().updateAcademicCalendar(academicCalendarId, academicCalendarInfo, contextInfo));
    }

    @Override
    public StatusInfo deleteAcademicCalendar(String academicCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().deleteAcademicCalendar(academicCalendarId, contextInfo));
    }

    @Override
    public AcademicCalendarInfo copyAcademicCalendar(String academicCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().copyAcademicCalendar(academicCalendarId, startDate, endDate, contextInfo));
    }

    @Override
    public String getAcademicCalendarData(String academicCalendarId, String calendarDataFormatTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getAcademicCalendarData(academicCalendarId, calendarDataFormatTypeKey, contextInfo));
    }

    @Override
    public TypeInfo getHolidayCalendarType(String holidayCalendarTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getHolidayCalendarType(holidayCalendarTypeKey, contextInfo));
    }

    @Override
    public List<TypeInfo> getHolidayCalendarTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getHolidayCalendarTypes(contextInfo));
    }

    @Override
    public StateInfo getHolidayCalendarState(String holidayCalendarStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getHolidayCalendarState(holidayCalendarStateKey, contextInfo));
    }

    @Override
    public List<StateInfo> getHolidayCalendarStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getHolidayCalendarStates(contextInfo));
    }

    @Override
    public HolidayCalendarInfo getHolidayCalendar(String holidayCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getHolidayCalendar(holidayCalendarId, contextInfo));
    }

    @Override
    public List<HolidayCalendarInfo> getHolidayCalendarsByIds(List<String> holidayCalendarIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getHolidayCalendarsByIds(holidayCalendarIds, contextInfo));
    }

    @Override
    public List<String> getHolidayCalendarIdsByType(String holidayCalendarTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getHolidayCalendarIdsByType(holidayCalendarTypeKey, contextInfo));
    }

    @Override
    public List<HolidayCalendarInfo> getHolidayCalendarsByStartYear(Integer year, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getHolidayCalendarsByStartYear(year, contextInfo));
    }

    @Override
    public List<String> searchForHolidayCalendarIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().searchForHolidayCalendarIds(criteria, contextInfo));
    }

    @Override
    public List<HolidayCalendarInfo> searchForHolidayCalendars(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().searchForHolidayCalendars(criteria, contextInfo));
    }

    @Override
    public List<ValidationResultInfo> validateHolidayCalendar(String validationTypeKey, String holidayCalendarTypeKey, HolidayCalendarInfo holidayCalendarInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().validateHolidayCalendar(validationTypeKey, holidayCalendarTypeKey, holidayCalendarInfo, contextInfo));
    }

    @Override
    public HolidayCalendarInfo createHolidayCalendar(String holidayCalendarTypeKey, HolidayCalendarInfo holidayCalendarInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return (getNextDecorator().createHolidayCalendar(holidayCalendarTypeKey, holidayCalendarInfo, contextInfo));
    }

    @Override
    public HolidayCalendarInfo copyHolidayCalendar(String holidayCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().copyHolidayCalendar(holidayCalendarId,startDate,endDate,contextInfo));
    }

    @Override
    public HolidayCalendarInfo updateHolidayCalendar(String holidayCalendarId, HolidayCalendarInfo holidayCalendarInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return (getNextDecorator().updateHolidayCalendar(holidayCalendarId, holidayCalendarInfo, contextInfo));
    }

    @Override
    public StatusInfo deleteHolidayCalendar(String holidayCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().deleteHolidayCalendar(holidayCalendarId, contextInfo));
    }

    @Override
    public TypeInfo getTermType(String termTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getTermType(termTypeKey, contextInfo));
    }

    @Override
    public List<TypeInfo> getTermTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getTermTypes(contextInfo));
    }

    @Override
    public List<TypeInfo> getTermTypesForAcademicCalendarType(String academicCalendarTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getTermTypesForAcademicCalendarType(academicCalendarTypeKey, contextInfo));
    }

    @Override
    public List<TypeInfo> getTermTypesForTermType(String termTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getTermTypesForTermType(termTypeKey, contextInfo));
    }

    @Override
    public StateInfo getTermState(String termStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getTermState(termStateKey, contextInfo));
    }

    @Override
    public List<StateInfo> getTermStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getTermStates(contextInfo));
    }

    @Override
    public TermInfo getTerm(String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getTerm(termId, contextInfo));
    }

    @Override
    public List<TermInfo> getTermsByIds(List<String> termIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getTermsByIds(termIds, contextInfo));
    }

    @Override
    public List<String> getTermIdsByType(String termTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getTermIdsByType(termTypeKey, contextInfo));
    }

    @Override
    public List<TermInfo> getTermsByCode(String code, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getTermsByCode(code, contextInfo));
    }

    @Override
    public List<TermInfo> getTermsForAcademicCalendar(String academicCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getTermsForAcademicCalendar(academicCalendarId, contextInfo));
    }

    @Override
    public List<TermInfo> getIncludedTermsInTerm(String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getIncludedTermsInTerm(termId, contextInfo));
    }

    @Override
    public List<TermInfo> getContainingTerms(String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getContainingTerms(termId, contextInfo));
    }

    @Override
    public List<String> searchForTermIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().searchForTermIds(criteria, contextInfo));
    }

    @Override
    public List<TermInfo> searchForTerms(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().searchForTerms(criteria, contextInfo));
    }

    @Override
    public List<ValidationResultInfo> validateTerm(String validationTypeKey, String termTypeKey, TermInfo termInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().validateTerm(validationTypeKey, termTypeKey, termInfo, contextInfo));
    }

    @Override
    public TermInfo createTerm(String termTypeKey, TermInfo termInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return (getNextDecorator().createTerm(termTypeKey, termInfo, contextInfo));
    }

    @Override
    public TermInfo updateTerm(String termId, TermInfo termInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return (getNextDecorator().updateTerm(termId, termInfo, contextInfo));
    }

    @Override
    public StatusInfo deleteTerm(String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().deleteTerm(termId, contextInfo));
    }

    @Override
    public StatusInfo addTermToAcademicCalendar(String academicCalendarId, String termId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().addTermToAcademicCalendar(academicCalendarId, termId, contextInfo));
    }

    @Override
    public StatusInfo removeTermFromAcademicCalendar(String academicCalendarId, String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().removeTermFromAcademicCalendar(academicCalendarId, termId, contextInfo));
    }

    @Override
    public StatusInfo addTermToTerm(String termId, String includedTermId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().addTermToTerm(termId, includedTermId, contextInfo));
    }

    @Override
    public StatusInfo removeTermFromTerm(String termId, String includedTermId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().removeTermFromTerm(termId, includedTermId, contextInfo));
    }

    @Override
    public TypeInfo getKeyDateType(String keyDateTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getKeyDateType(keyDateTypeKey, contextInfo));
    }

    @Override
    public List<TypeInfo> getKeyDateTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getKeyDateTypes(contextInfo));
    }

    @Override
    public List<TypeInfo> getKeyDateTypesForTermType(String termTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getKeyDateTypesForTermType(termTypeKey, contextInfo));
    }

    @Override
    public StateInfo getKeyDateState(String keyDateStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getKeyDateState(keyDateStateKey, contextInfo));
    }

    @Override
    public List<StateInfo> getKeyDateStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getKeyDateStates(contextInfo));
    }

    @Override
    public KeyDateInfo getKeyDate(String keyDateId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getKeyDate(keyDateId, contextInfo));
    }

    @Override
    public List<KeyDateInfo> getKeyDatesByIds(List<String> keyDateIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getKeyDatesByIds(keyDateIds, contextInfo));
    }

    @Override
    public List<String> getKeyDateIdsByType(String keyDateTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getKeyDateIdsByType(keyDateTypeKey, contextInfo));
    }

    @Override
    public List<String> getKeyDateIdsByTypeForTerm(String keyDateTypeKey, String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getKeyDateIdsByTypeForTerm(keyDateTypeKey, termId, contextInfo));
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTerm(String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getKeyDatesForTerm(termId, contextInfo));
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTermByDate(String termId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getKeyDatesForTermByDate(termId, startDate, endDate, contextInfo));
    }

    @Override
    public List<KeyDateInfo> getImpactedKeyDates(String keyDateId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getImpactedKeyDates(keyDateId, contextInfo));
    }

    @Override
    public List<String> searchForKeyDateIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().searchForKeyDateIds(criteria, contextInfo));
    }

    @Override
    public List<KeyDateInfo> searchForKeyDates(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().searchForKeyDates(criteria, contextInfo));
    }

    @Override
    public List<ValidationResultInfo> validateKeyDate(String validationTypeKey, String termId, String keyDateTypeKey, KeyDateInfo keyDateInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().validateKeyDate(validationTypeKey, termId, keyDateTypeKey, keyDateInfo, contextInfo));
    }

    @Override
    public KeyDateInfo createKeyDate(String termId, String keyDateTypeKey, KeyDateInfo keyDateInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return (getNextDecorator().createKeyDate(termId, keyDateTypeKey, keyDateInfo, contextInfo));
    }

    @Override
    public KeyDateInfo updateKeyDate(String keyDateId, KeyDateInfo keyDateInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return (getNextDecorator().updateKeyDate(keyDateId, keyDateInfo, contextInfo));
    }

    @Override
    public StatusInfo deleteKeyDate(String keyDateId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().deleteKeyDate(keyDateId, contextInfo));
    }

    @Override
    public KeyDateInfo calculateKeyDate(String keyDateId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().calculateKeyDate(keyDateId, contextInfo));
    }

    @Override
    public TypeInfo getAcalEventType(String acalEventTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getAcalEventType(acalEventTypeKey, contextInfo));
    }

    @Override
    public List<TypeInfo> getAcalEventTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getAcalEventTypes(contextInfo));
    }

    @Override
    public List<TypeInfo> getAcalEventTypesForAcademicCalendarType(String academicCalendarTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getAcalEventTypesForAcademicCalendarType(academicCalendarTypeKey, contextInfo));
    }

    @Override
    public StateInfo getAcalEventState(String acalEventStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getAcalEventState(acalEventStateKey, contextInfo));
    }

    @Override
    public List<StateInfo> getAcalEventStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getAcalEventStates(contextInfo));
    }

    @Override
    public AcalEventInfo getAcalEvent(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getAcalEvent(acalEventId, contextInfo));
    }

    @Override
    public List<AcalEventInfo> getAcalEventsByIds(List<String> acalEventIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getAcalEventsByIds(acalEventIds, contextInfo));
    }

    @Override
    public List<String> getAcalEventIdsByType(String acalEventTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getAcalEventIdsByType(acalEventTypeKey, contextInfo));
    }

    @Override
    public List<AcalEventInfo> getAcalEventsForAcademicCalendar(String academicCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getAcalEventsForAcademicCalendar(academicCalendarId, contextInfo));
    }

    @Override
    public List<AcalEventInfo> getAcalEventsForAcademicCalendarByDate(String academicCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getAcalEventsForAcademicCalendarByDate(academicCalendarId, startDate, endDate, contextInfo));
    }

    @Override
    public List<AcalEventInfo> getImpactedAcalEvents(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getImpactedAcalEvents(acalEventId, contextInfo));
    }

    @Override
    public List<String> searchForAcalEventIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().searchForAcalEventIds(criteria, contextInfo));
    }

    @Override
    public List<AcalEventInfo> searchForAcalEvents(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().searchForAcalEvents(criteria, contextInfo));
    }

    @Override
    public List<ValidationResultInfo> validateAcalEvent(String validationTypeKey, String termId, String acalEventTypeKey, AcalEventInfo acalEventInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().validateAcalEvent(validationTypeKey, termId, acalEventTypeKey, acalEventInfo, contextInfo));
    }

    @Override
    public AcalEventInfo createAcalEvent(String termId, String acalEventTypeKey, AcalEventInfo acalEventInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return (getNextDecorator().createAcalEvent(termId, acalEventTypeKey, acalEventInfo, contextInfo));
    }

    @Override
    public AcalEventInfo updateAcalEvent(String acalEventId, AcalEventInfo acalEventInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return (getNextDecorator().updateAcalEvent(acalEventId, acalEventInfo, contextInfo));
    }

    @Override
    public StatusInfo deleteAcalEvent(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().deleteAcalEvent(acalEventId, contextInfo));
    }

    @Override
    public AcalEventInfo calculateAcalEvent(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().calculateAcalEvent(acalEventId, contextInfo));
    }

    @Override
    public TypeInfo getHolidayType(String holidayTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getHolidayType(holidayTypeKey, contextInfo));
    }

    @Override
    public List<TypeInfo> getHolidayTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getHolidayTypes(contextInfo));
    }

    @Override
    public List<TypeInfo> getHolidayTypesForHolidayCalendarType(String holidayCalendarTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getHolidayTypesForHolidayCalendarType(holidayCalendarTypeKey, contextInfo));
    }

    @Override
    public StateInfo getHolidayState(String holidayStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getHolidayState(holidayStateKey, contextInfo));
    }

    @Override
    public List<StateInfo> getHolidayStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getHolidayStates(contextInfo));
    }

    @Override
    public HolidayInfo getHoliday(String holidayId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getHoliday(holidayId, contextInfo));
    }

    @Override
    public List<HolidayInfo> getHolidaysByIds(List<String> holidayIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getHolidaysByIds(holidayIds, contextInfo));
    }

    @Override
    public List<String> getHolidayIdsByType(String holidayTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getHolidayIdsByType(holidayTypeKey, contextInfo));
    }

    @Override
    public List<HolidayInfo> getHolidaysForHolidayCalendar(String holidayCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getHolidaysForHolidayCalendar(holidayCalendarId, contextInfo));
    }

    @Override
    public List<HolidayInfo> getHolidaysForHolidayCalendarByDate(String holidayCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getHolidaysForHolidayCalendarByDate(holidayCalendarId, startDate, endDate, contextInfo));
    }

    @Override
    public List<HolidayInfo> getImpactedHolidays(String holidayId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getImpactedHolidays(holidayId, contextInfo));
    }

    @Override
    public List<String> searchForHolidayIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().searchForHolidayIds(criteria, contextInfo));
    }

    @Override
    public List<HolidayInfo> searchForHolidays(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().searchForHolidays(criteria, contextInfo));
    }

    @Override
    public List<ValidationResultInfo> validateHoliday(String validationTypeKey, String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().validateHoliday(validationTypeKey, holidayCalendarId, holidayTypeKey, holidayInfo, contextInfo));
    }

    @Override
    public HolidayInfo createHoliday(String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return (getNextDecorator().createHoliday(holidayCalendarId, holidayTypeKey, holidayInfo, contextInfo));
    }

    @Override
    public HolidayInfo updateHoliday(String holidayId, HolidayInfo holidayInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return (getNextDecorator().updateHoliday(holidayId, holidayInfo, contextInfo));
    }

    @Override
    public StatusInfo deleteHoliday(String holidayId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().deleteHoliday(holidayId, contextInfo));
    }

    @Override
    public HolidayInfo calculateHoliday(String holidayId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().calculateHoliday(holidayId, contextInfo));
    }

    @Override
    public Integer getInstructionalDaysForTerm(String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getInstructionalDaysForTerm(termId, contextInfo));
    }

    @Override
    public List<TermInfo> getCurrentTerms(String processKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return (getNextDecorator().getCurrentTerms(processKey, contextInfo));
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsForTerm(String termId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException {

        return (getNextDecorator().getAcademicCalendarsForTerm(termId, contextInfo));
    }

    @Override
    public List<HolidayInfo> getHolidaysByDateForAcademicCalendar(String academicCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return (getNextDecorator().getHolidaysByDateForAcademicCalendar(academicCalendarId, startDate, endDate, contextInfo));
    }
}