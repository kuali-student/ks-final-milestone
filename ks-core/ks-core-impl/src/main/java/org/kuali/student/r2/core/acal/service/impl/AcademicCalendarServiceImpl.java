package org.kuali.student.r2.core.acal.service.impl;


import org.apache.commons.lang.StringUtils;
import org.joda.time.DateMidnight;
import org.joda.time.DateTimeConstants;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
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
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.AcalEventInfo;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.acal.service.assembler.AcademicCalendarAssembler;
import org.kuali.student.r2.core.acal.service.assembler.AcalEventAssembler;
import org.kuali.student.r2.core.acal.service.assembler.HolidayAssembler;
import org.kuali.student.r2.core.acal.service.assembler.HolidayCalendarAssembler;
import org.kuali.student.r2.core.acal.service.assembler.KeyDateAssembler;
import org.kuali.student.r2.core.acal.service.assembler.TermAssembler;
import org.kuali.student.r2.core.acal.service.transformer.ExamPeriodTransformer;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.search.CoreSearchServiceImpl;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.state.service.StateTransitionsHelper;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.AtpSearchServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class AcademicCalendarServiceImpl implements AcademicCalendarService {
    private AtpService atpService;
    private StateService stateService;
    private TypeService typeService;
    private AcademicCalendarAssembler acalAssembler;
    private TermAssembler termAssembler;
    private HolidayCalendarAssembler holidayCalendarAssembler;
    private HolidayAssembler holidayAssembler;
    private KeyDateAssembler keyDateAssembler;
    private AcalEventAssembler acalEventAssembler;
    private StateTransitionsHelper stateTransitionsHelper;

    private ExamPeriodTransformer examPeriodTransformer = new ExamPeriodTransformer();

    private SearchService searchService = null;

    public AcalEventAssembler getAcalEventAssembler() {
        return acalEventAssembler;
    }

    public void setAcalEventAssembler(AcalEventAssembler acalEventAssembler) {
        this.acalEventAssembler = acalEventAssembler;
    }

    public KeyDateAssembler getKeyDateAssembler() {
        return keyDateAssembler;
    }

    public void setKeyDateAssembler(KeyDateAssembler keyDateAssembler) {
        this.keyDateAssembler = keyDateAssembler;
    }

    public HolidayAssembler getHolidayAssembler() {
        return holidayAssembler;
    }

    public void setHolidayAssembler(HolidayAssembler holidayAssembler) {
        this.holidayAssembler = holidayAssembler;
    }

    public HolidayCalendarAssembler getHolidayCalendarAssembler() {
        return holidayCalendarAssembler;
    }

    public void setHolidayCalendarAssembler(HolidayCalendarAssembler holidayCalendarAssembler) {
        this.holidayCalendarAssembler = holidayCalendarAssembler;
    }

    public ExamPeriodTransformer getExamPeriodTransformer() {
        return examPeriodTransformer;
    }

    public void setExamPeriodTransformer(ExamPeriodTransformer examPeriodTransformer) {
        this.examPeriodTransformer = examPeriodTransformer;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    @Override
    public TypeInfo getAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getAcademicCalendarTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<TypeInfo>();
    }

    @Override
    public StateInfo getAcademicCalendarState(String academicCalendarStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    private List<StateInfo> getAtpStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<StateInfo> results;
        try {
            results = stateService.getStatesByLifecycle(AtpServiceConstants.ATP_LIFECYCLE_KEY, context);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException(AtpServiceConstants.ATP_LIFECYCLE_KEY, ex);
        }

        return results;
    }

    @Override
    public List<StateInfo> getAcademicCalendarStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getAtpStates(context);
    }

    @Override
    public AcademicCalendarInfo getAcademicCalendar(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        AtpInfo atp = atpService.getAtp(academicCalendarKey, context);
        AcademicCalendarInfo acal;

        try {
            acal = acalAssembler.assemble(atp, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        return acal;
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByIds(List<String> academicCalendarKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>();
        List<AtpInfo> atps = atpService.getAtpsByIds(academicCalendarKeyList, context);
        for (AtpInfo atp : atps) {
            try {
                academicCalendars.add(acalAssembler.assemble(atp, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }
        return academicCalendars;
    }

    @Override
    public List<String> getAcademicCalendarIdsByType(String academicCalendarTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return atpService.getAtpIdsByType(academicCalendarTypeKey, context);
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByStartYear(Integer year, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        final Date yearBegin, yearEnd;

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, Calendar.JANUARY, 1);
        yearBegin = cal.getTime(); // XXXX-01-01 00:00:00.000
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.MILLISECOND, -1);
        yearEnd = cal.getTime(); // XXXX-12-31 23:59:59.999

        Set<AtpInfo> atpInfos = new TreeSet<AtpInfo>(new Comparator<AtpInfo>() {

            @Override
            public int compare(AtpInfo atpInfo1, AtpInfo atpInfo2) {
                return atpInfo1.getId().compareTo(atpInfo2.getId());
            }
        });

        atpInfos.addAll(atpService.getAtpsByStartDateRangeAndType(yearBegin, yearEnd, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, context));

        List<AcademicCalendarInfo> acalInfos = new ArrayList<AcademicCalendarInfo>();
        for (AtpInfo atpInfo : atpInfos) {
            try {
                acalInfos.add(acalAssembler.assemble(atpInfo, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }
        return acalInfos;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AcademicCalendarInfo createAcademicCalendar(String academicCalendarTypeKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        // TODO: move this to a validation layer
        try {
            AtpInfo toCreate = acalAssembler.disassemble(academicCalendarInfo, context);
            if(!isInitialState(AtpServiceConstants.ATP_LIFECYCLE_KEY, toCreate.getStateKey(), context)) {
                throw new OperationFailedException("Wrong initial Academic Calendar Info state Key!");
            }

            AtpInfo createdAtp = atpService.createAtp(toCreate.getTypeKey(), toCreate, context);
            try {
                createDeleteAtpAtpRelations(createdAtp.getId(),
                        academicCalendarInfo.getHolidayCalendarIds(),
                        AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY,
                        context);
            } catch (VersionMismatchException e) {
                throw new OperationFailedException();
            }
            return acalAssembler.assemble(createdAtp, context);
        } catch (AlreadyExistsException e) {
            throw new OperationFailedException("Unexpected", e);
        } catch (AssemblyException e) {
            throw new OperationFailedException("Unexpected", e);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Unexpected", e);
        }

    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AcademicCalendarInfo updateAcademicCalendar(String academicCalendarId, AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        AtpInfo existingOne;
        try {
            existingOne = atpService.getAtp(academicCalendarId,context);
            if (!StringUtils.equals(existingOne.getStateKey(),academicCalendarInfo.getStateKey())){
                throw new OperationFailedException("It's not possible to update the state with this call. Please use changeAcademicCalendarState() instead");
            }
        } catch (Exception e) {
            throw new OperationFailedException("Unexpected", e);
        }

        long dtoVersion = Long.parseLong(academicCalendarInfo.getMeta().getVersionInd());
        long currentVersion = Long.parseLong(existingOne.getMeta().getVersionInd());
        if (currentVersion > dtoVersion){
            throw new VersionMismatchException("Data exists in the DB is newer than the DTO.");
        }

        try {
            AtpInfo toUpdate = acalAssembler.disassemble(academicCalendarInfo, context);
            AtpInfo updated = atpService.updateAtp(academicCalendarId, toUpdate, context);
            try {
                createDeleteAtpAtpRelations(academicCalendarId,
                        academicCalendarInfo.getHolidayCalendarIds(),
                        AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY,
                        context);
            } catch (VersionMismatchException e) {
                throw new OperationFailedException();
            }
            return acalAssembler.assemble(updated, context);
        } catch (Exception e) {
            throw new OperationFailedException("Unexpected", e);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo changeAcademicCalendarState(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return processAtpStateChange(academicCalendarId, nextStateKey, contextInfo);

    }


    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteAcademicCalendar(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AtpInfo atp = atpService.getAtp(academicCalendarKey, context);

        if (atp != null) {
            // delete atp/acal
            atpService.deleteAtp(academicCalendarKey, context);
        }

        return status;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AcademicCalendarInfo copyAcademicCalendar(String academicCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method implemented in calculation decorator.");
    }

    @Override
    @Transactional(readOnly = true)
    public HolidayCalendarInfo getHolidayCalendar(String holidayCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        AtpInfo atp = atpService.getAtp(holidayCalendarId, contextInfo);
        HolidayCalendarInfo ccal;
        try {
            ccal = holidayCalendarAssembler.assemble(atp, contextInfo);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }

        return ccal;
    }

    @Override
    public List<HolidayCalendarInfo> getHolidayCalendarsByIds(List<String> holidayCalendarIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<AtpInfo> atps = atpService.getAtpsByIds(holidayCalendarIds, context);
        List<HolidayCalendarInfo> holidayCalendarInfos = new ArrayList<HolidayCalendarInfo>();
        for (AtpInfo atp : atps) {
            try {
                holidayCalendarInfos.add(holidayCalendarAssembler.assemble(atp, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }
        return holidayCalendarInfos;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public HolidayCalendarInfo createHolidayCalendar(String holidayCalendarTypeKey, HolidayCalendarInfo holidayCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        if(!checkTypeForHolidayCalendar(holidayCalendarInfo.getTypeKey())){
            throw new InvalidParameterException("HolidayCalendar type " + holidayCalendarInfo.getTypeKey() + " not right");
        }

        AtpInfo atpInfo;
        try {
            atpInfo = holidayCalendarAssembler.disassemble(holidayCalendarInfo, context);
        } catch (AssemblyException ex) {
            throw new OperationFailedException("Unexpected", ex);
        }

        if(!isInitialState(AtpServiceConstants.ATP_LIFECYCLE_KEY, atpInfo.getStateKey(), context)) {
            throw new OperationFailedException("Wrong initial HolidayCalendar state Key!");
        }

        try {
            System.out.println("AcademicCalendarServiceImp: creating atp for holiday calendar ");
            atpInfo = atpService.createAtp(atpInfo.getTypeKey(), atpInfo, context);
            System.out.println("atp created for holiday calendar " + atpInfo.getId());
        } catch (ReadOnlyException ex) {
            throw new OperationFailedException("Unexpected", ex);
        }
        HolidayCalendarInfo newHolidayCalendar;
        try {
            newHolidayCalendar = holidayCalendarAssembler.assemble(atpInfo, context);
        } catch (AssemblyException ex) {
            throw new OperationFailedException("unexpected", ex);
        }

        return newHolidayCalendar;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public HolidayCalendarInfo copyHolidayCalendar(String holidayCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method implemented in calculation decorator.");
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public HolidayCalendarInfo updateHolidayCalendar(String holidayCalendarId, HolidayCalendarInfo holidayCalendarInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        try {
            AtpInfo existingAtp = atpService.getAtp(holidayCalendarId,contextInfo);
            if (!StringUtils.equals(existingAtp.getStateKey(),holidayCalendarInfo.getStateKey())){
                throw new OperationFailedException("It's not possible to update the state with this call. Please use changeHolidayCalendarState() instead");
            }

            AtpInfo toUpdate = holidayCalendarAssembler.disassemble(holidayCalendarInfo, contextInfo);
            AtpInfo updated = atpService.updateAtp(holidayCalendarId, toUpdate, contextInfo);
            return holidayCalendarAssembler.assemble(updated, contextInfo);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Failed to update ATP - " + e.getMessage(), e);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Failed to update ATP - " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo changeHolidayCalendarState(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return processAtpStateChange(holidayCalendarId, nextStateKey, contextInfo);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteHolidayCalendar(String holidayCalendarId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return atpService.deleteAtp(holidayCalendarId, context);
    }

    @Override
    public TypeInfo getTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        TypeInfo type = typeService.getType(termTypeKey, context);

        if (!checkTypeForTermType(termTypeKey, context)) {
            throw new InvalidParameterException(termTypeKey + " is not a Term type");
        }

        return type;
    }

    @Override
    public List<TypeInfo> getTermTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getTypesForGroupType(AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY, context);
    }

    @Override
    public List<TypeInfo> getTermTypesForAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        TypeInfo acalType = typeService.getType(academicCalendarTypeKey, context);

        return typeService.getAllowedTypesForType(acalType.getKey(), context);
    }

    @Override
    public List<TypeInfo> getTermTypesForTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        TypeInfo termType = getTermType(termTypeKey, context);

        return typeService.getAllowedTypesForType(termType.getKey(), context);
    }

    @Override
    public StateInfo getTermState(String termStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        StateInfo termState = stateService.getState(termStateKey, context);

        return termState;
    }

    @Override
    public List<StateInfo> getTermStates(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<StateInfo> results;
        try {
            results = stateService.getStatesByLifecycle(AtpServiceConstants.ATP_LIFECYCLE_KEY, context);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException(AtpServiceConstants.ATP_LIFECYCLE_KEY, ex);
        }

        return results;
    }

    @Override
    public TermInfo getTerm(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpInfo atp = atpService.getAtp(termId, context);
        TermInfo term;

        if (atp != null && checkTypeForTermType(atp.getTypeKey(), context)) {
            try {
                term = termAssembler.assemble(atp, context);
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        } else {
            throw new DoesNotExistException("This is either not valid Atp or not valid Term. " + termId);
        }

        return term;
    }

    @Override
    public List<TermInfo> getTermsByIds(List<String> termIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<AtpInfo> results = atpService.getAtpsByIds(termIds, context);

        List<TermInfo> terms = new ArrayList<TermInfo>();

        for (AtpInfo atp : results) {
            try {
                terms.add(termAssembler.assemble(atp, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }

        return terms;
    }

    @Override
    public List<TermInfo> getTermsForAcademicCalendar(String academicCalendarId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByTypeAndAtp(academicCalendarId,
                AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY,
                context);
        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());
        for (AtpAtpRelationInfo atpRelation : results) {
            if (atpRelation.getAtpId().equals(academicCalendarId)) {
                AtpInfo possibleTerm = atpService.getAtp(atpRelation.getRelatedAtpId(), context);
                if (checkTypeForTermType(possibleTerm.getTypeKey(), context)) {
                    try {
                        terms.add(termAssembler.assemble(possibleTerm, context));
                    } catch (AssemblyException e) {
                        throw new OperationFailedException("AssemblyException : " + e.getMessage());
                    }
                }
            }
        }
        return terms;
    }

    @Override
    public List<TermInfo> getCurrentTerms(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        //Code Changed for JIRA-9075 - SONAR Critical issues - Use get(0) with caution - 5
        int fistAcademicCalendarInfo = 0;
        List<AcademicCalendarInfo> currentACInfos = getAcademicCalendarsByStartYear(Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR)), context);
        List<TermInfo> terms = getTermsForAcademicCalendar(currentACInfos.get(fistAcademicCalendarInfo).getId(), context);
        if (terms == null || terms.size() == 0) {
            throw new DoesNotExistException("This academic calendar doesn't contain any terms : " + currentACInfos.get(fistAcademicCalendarInfo).getId());
        }
        return terms;
    }

    @Override
    public List<TermInfo> getIncludedTermsInTerm(String parentTermId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        // check for a valid term
        TermInfo parentTerm = getTerm(parentTermId, context);

        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByTypeAndAtp(parentTerm.getId(), AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);

        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());

        for (AtpAtpRelationInfo atpRelation : results) {
            if (atpRelation.getAtpId().equals(parentTermId)) {
                AtpInfo possibleTerm = atpService.getAtp(atpRelation.getRelatedAtpId(), context);

                if (checkTypeForTermType(possibleTerm.getTypeKey(), context)) {
                    try {
                        terms.add(termAssembler.assemble(possibleTerm, context));
                    } catch (AssemblyException e) {
                        throw new OperationFailedException("AssemblyException : " + e.getMessage());
                    }
                }
            }

        }

        return terms;
    }

    @Override
    public List<TermInfo> getContainingTerms(String childTermId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        // check for a valid term
        // we're calling the atp service here because it's much faster than calling the term service.
        AtpInfo childTerm = getAtpService().getAtp(childTermId, context);

        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByAtp(childTerm.getId(), context);

        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());

        // check that the relations we found have the given termId as the
        // "related" atp, and that the owning atp is a term
        for (AtpAtpRelationInfo atpRelation : results) {
            if (atpRelation.getRelatedAtpId().equals(childTermId)) {
                AtpInfo possibleTerm = atpService.getAtp(atpRelation.getAtpId(), context);

                if (checkTypeForTermType(possibleTerm.getTypeKey(), context)) {
                    try {
                        terms.add(termAssembler.assemble(possibleTerm, context));
                    } catch (AssemblyException e) {
                        throw new OperationFailedException("AssemblyException : " + e.getMessage());
                    }
                }
            }

        }

        return terms;
    }

    @Override
    public List<ValidationResultInfo> validateTerm(String validationTypeKey, String termTypeKey, TermInfo termInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public TermInfo createTerm(String termTypeKey, TermInfo termInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DoesNotExistException {
        AtpInfo atp;

        if (checkTypeForTermType(termTypeKey, context)) {
            try {
                atp = termAssembler.disassemble(termInfo, context);
                if(!isInitialState(AtpServiceConstants.ATP_LIFECYCLE_KEY, atp.getStateKey(), context)) {
                    throw new OperationFailedException("Wrong initial TermInfo state Key!");
                }
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }

            try {
                if(!hasTermCode(atp.getTypeKey(), atp.getCode(), context)){
                    AtpInfo newAtp = atpService.createAtp(atp.getTypeKey(), atp, context);
                    termInfo = termAssembler.assemble(newAtp, context);
                }
                else {
                    throw new DataValidationErrorException("The term code " + atp.getCode() + " with type (" + atp.getTypeKey() + ") already exists.");
                }
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling term", e);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException("Error assembling term", e);
            }
        } else {
            throw new InvalidParameterException("Term type not found: '" + termTypeKey + "'");
        }

        return termInfo;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public TermInfo updateTerm(String termId, TermInfo termInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {


        TermInfo updatedTerm;

        try {
            AtpInfo existingAtp = atpService.getAtp(termId, context);
            String termCode = existingAtp.getCode();

            AtpInfo toUpdate = termAssembler.disassemble(termInfo, context);
            if((termCode == null && toUpdate.getCode() == null) || termCode.equals(toUpdate.getCode()) || (!termCode.equals(toUpdate.getCode()) && !hasTermCode(toUpdate.getTypeKey(), toUpdate.getCode(), context))){

                if (!StringUtils.equals(existingAtp.getStateKey(),termInfo.getStateKey())){
                    throw new OperationFailedException("State cant be updated with this call. Please use changeTermState() instead.");
                }

                AtpInfo updated = atpService.updateAtp(termId, toUpdate, context);

                updatedTerm = termAssembler.assemble(updated, context);
            }
            else {
                throw new DataValidationErrorException("The term code " + toUpdate.getCode() + " with type( " + toUpdate.getTypeKey() + ") already exists.");
            }
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Error updating ATP - " + e.getMessage(), e);
        }

        return updatedTerm;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo changeTermState(@WebParam(name = "termId") String termId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return processAtpStateChange(termId,nextStateKey,contextInfo);
    }

    private QueryByCriteria buildQueryByCriteriaForTerm(String type, String code){
        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(PredicateFactory.equal("atpType", type));
        predicates.add(PredicateFactory.equalIgnoreCase("atpCode", code));

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();

        return qbc;
    }

    private boolean hasTermCode(String type, String code, ContextInfo context)throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        if(type == null) return false;
        if(code == null) return false;

        QueryByCriteria qbc = buildQueryByCriteriaForTerm(type, code);

        List<TermInfo> terms = searchForTerms(qbc, context);

        if (terms != null && !terms.isEmpty()) {
            return true;
        }
        return false;

    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteTerm(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        AtpInfo atp = atpService.getAtp(termId, context);

        if (atp == null) {
            throw new DoesNotExistException(termId);
        }

        // if the apt state is not draft, throw exception
        if (!StringUtils.equals(atp.getStateKey(), AtpServiceConstants.ATP_DRAFT_STATE_KEY)) {
            throw new OperationFailedException("KSENROLL-7961 - Official term cannot be deleted. termid: " + termId);
        }

        if (!checkTypeForTermType(atp.getTypeKey(), context)) {
            throw new InvalidParameterException("Invalid termId: " + termId + "  Given key does not map to a Term");
        }

        StatusInfo result = atpService.deleteAtp(termId, context);

        return result;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo addTermToAcademicCalendar(String academicCalendarId,
                                                String termId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            AlreadyExistsException {
        // TODO: move this to a validation layer
        AtpInfo acal = atpService.getAtp(academicCalendarId, context);
        if (!isAcademicCalendar(acal.getTypeKey())) {
            throw new DoesNotExistException("AcademicCalendar with id = " + academicCalendarId + " is not an academic calendar " + acal.getTypeKey());
        }
        // TODO: move this to a validation layer
        AtpInfo term = atpService.getAtp(termId, context);
        if (!checkTypeForTermType(term.getTypeKey(), context)) {
            throw new DoesNotExistException("ter with id = " + termId + " is not an academic calendar " + term.getTypeKey());
        }
        // check if already exists
        if (termAlreadyExists(academicCalendarId, termId, context)) {
            throw new AlreadyExistsException("Term with id = " + termId + " is already associated with academic calendar " + academicCalendarId);
        }
        // actually create it
        try {
            createAtpAtpRelation(academicCalendarId, termId, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private boolean isAcademicCalendar(String acalType) {
        return acalType.equals(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
    }

    private boolean termAlreadyExists(String parentTermOrAcalId, String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<AtpAtpRelationInfo> atpRels = atpService.getAtpAtpRelationsByTypeAndAtp(parentTermOrAcalId, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        for (AtpAtpRelationInfo atpRelInfo : atpRels) {
            if (atpRelInfo.getAtpId().equals(parentTermOrAcalId)) {
                if (atpRelInfo.getRelatedAtpId().equals(termId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo removeTermFromAcademicCalendar(String academicCalendarKey, String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        AtpInfo acal = atpService.getAtp(academicCalendarKey, context);

        if (acal == null) {
            throw new InvalidParameterException("Invalid academicCalendarKey: " + academicCalendarKey);
        }

        AtpInfo term = atpService.getAtp(termId, context);

        if (term == null) {
            throw new InvalidParameterException("Invalid termId: " + termId);
        }

        List<AtpAtpRelationInfo> relations = atpService.getAtpAtpRelationsByTypeAndAtp(academicCalendarKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        if (relations == null || relations.isEmpty()) {
            throw new DoesNotExistException("No relationship exists between academic calendar: " + academicCalendarKey + " and term: " + termId);
        }

        AtpAtpRelationInfo relationToRemove = null;

        for (AtpAtpRelationInfo rel : relations) {
            if (rel.getAtpId().equals(academicCalendarKey)) {
                if (rel.getRelatedAtpId().equals(termId)) {
                    // if the relation represents an "includes" relationship
                    // from the AcademicCalendar to the Term,
                    // then it is the one we need to remove
                    relationToRemove = rel;
                    break;
                }
            }
        }

        if (relationToRemove == null) {
            throw new DoesNotExistException("No relationship exists between academic calendar: " + academicCalendarKey + " and term: " + termId);
        }

        StatusInfo resultStatus = atpService.deleteAtpAtpRelation(relationToRemove.getId(), context);

        return resultStatus;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo addTermToTerm(String parentTermId, String childTermId, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        TermInfo parentTerm = getTerm(parentTermId, context);

        TermInfo childTerm = getTerm(childTermId, context);

        // check if the relationship already exists
        List<TermInfo> terms = getIncludedTermsInTerm(parentTerm.getId(), context);
        for (TermInfo t : terms) {
            if (t.getId().equals(childTerm.getId())) {
                throw new AlreadyExistsException("A relationship already exists exists between parent term: " + parentTermId + " and child term: " + childTermId);
            }
        }

        StatusInfo resultStatus = new StatusInfo();

        try {
            createAtpAtpRelation(parentTermId, childTermId, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        } catch (DataValidationErrorException e) {
            resultStatus.setSuccess(false);
            resultStatus.setMessage("Creation of AtpAtpRelation failed due to DataValidationErrorExecption: " + e.getMessage());
        }

        return resultStatus;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo removeTermFromTerm(String parentTermId, String childTermId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        try {
            atpService.getAtp(parentTermId, context);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException("Invalid termId: " + parentTermId);
        }

        try {
            atpService.getAtp(childTermId, context);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException("Invalid childTermId: " + childTermId);
        }

        List<AtpAtpRelationInfo> relations = atpService.getAtpAtpRelationsByTypeAndAtp(parentTermId, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        if (relations == null || relations.isEmpty()) {
            throw new DoesNotExistException("No relationship exists between term: " + parentTermId + " and included term: " + childTermId);
        }

        AtpAtpRelationInfo relationToRemove = null;

        for (AtpAtpRelationInfo rel : relations) {
            if (rel.getAtpId().equals(parentTermId)) {
                if (rel.getRelatedAtpId().equals(childTermId)) {
                    // if the relation represents an "includes" relationship
                    // from the Term to the included Term,
                    // then it is the one we need to remove
                    relationToRemove = rel;
                    break;
                }
            }
        }

        if (relationToRemove == null) {
            throw new DoesNotExistException("No relationship exists between parent term: " + parentTermId + " and child term: " + childTermId);
        }

        StatusInfo resultStatus = atpService.deleteAtpAtpRelation(relationToRemove.getId(), context);

        return resultStatus;
    }

    @Override
    public TypeInfo getKeyDateType(String keyDateTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getKeyDateTypesForTermType(String termTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        List<TypeInfo> types = null;
        try {
            // TODO: change this when the new contract gets merged in because it does not have the refobject uri as a parameter
            types = this.typeService.getAllowedTypesForType(termTypeKey, context);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("TODO: change the contract to allow this method, getKeyDateTypesForTermType, to throw the PermissionDeniedException", ex);
        }
        // filter by ref object uri
        List<TypeInfo> list = new ArrayList<TypeInfo>(types.size());
        for (TypeInfo type : types) {
            if (type.getRefObjectUri() == null) {
                throw new NullPointerException(type.getKey());
            }
            if (type.getRefObjectUri().equals(AtpServiceConstants.REF_OBJECT_URI_MILESTONE)) {
                list.add(type);
            }
        }
        return list;
    }

    @Override
    public KeyDateInfo getKeyDate(String keyDateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        MilestoneInfo ms = atpService.getMilestone(keyDateId, context);
        return (null != ms) ? keyDateAssembler.assemble(ms,context) : null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesByIds(List<String> keyDateIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return new ArrayList<KeyDateInfo>();
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTerm(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        AtpInfo termAtp = atpService.getAtp(termId, context);
        if (termAtp == null) {
            throw new DoesNotExistException(termId);
        }

        return _getKeyDatesForTerm(termAtp, context);
    }

    @Override
    public List<String> getKeyDateIdsForTerm(String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        AtpInfo termAtp = atpService.getAtp(termId, context);
        if (termAtp == null) {
            throw new DoesNotExistException(termId);
        }
        // Find the registration/instructional milestone types.  These appear to, collectively,
        // constitute the keydate types.
        SearchRequestInfo searchRequest = new SearchRequestInfo(AtpSearchServiceConstants
                .ATP_SEARCH_MILESTONE_IDS_BY_ATP_ID);
        searchRequest.addParam(AtpSearchServiceConstants.ATP_QUERYPARAM_MILESTONE_ATP_ID, termId);
        List<TypeTypeRelationInfo> typeTypeRels =
                typeService.getTypeTypeRelationsByOwnerAndType("kuali.milestone.type.group.instructional",
                        TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, context);
        typeTypeRels.addAll(typeService.getTypeTypeRelationsByOwnerAndType("kuali.milestone.type.group.registration",
                TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, context));
        // Extract out the keydate types
        Set<String> keydateTypesSet = new HashSet<String>();
        for (TypeTypeRelationInfo info: typeTypeRels) {
            keydateTypesSet.add(info.getRelatedTypeKey()); // avoid duplicates
        }
        // Convert set to list
        List<String> keydateTypes = new ArrayList<String>();
        keydateTypes.addAll(keydateTypesSet);
        // Make query
        searchRequest.addParam(AtpSearchServiceConstants.ATP_QUERYPARAM_MILESTONE_TYPES, keydateTypes);
        SearchResultInfo searchResult = atpService.search(searchRequest, context);
        List<String> keyDateIds = new ArrayList<String>();
        //Code Changed for JIRA-9075 - SONAR Critical issues - Use get(0) with caution - 5
        int firstSearchResultCellInfo = 0;
        // Extract out IDs.  Each row should have one cell with one key, so just grab the value (which is an ID)
        for (SearchResultRowInfo row: searchResult.getRows()) {
            List<SearchResultCellInfo> cells = row.getCells();
            String id = cells.get(firstSearchResultCellInfo).getValue(); // keydate ID
            keyDateIds.add(id);
        }
        return keyDateIds;
    }

    protected  List<KeyDateInfo> _getKeyDatesForTerm(AtpInfo termAtp, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,PermissionDeniedException {


        List<MilestoneInfo> milestones = atpService.getMilestonesForAtp(termAtp.getId(), context);

        if (milestones == null || milestones.isEmpty()) {
            return Collections.emptyList();
        }

        List<KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>(milestones.size());

        for (MilestoneInfo milestone : milestones) {
            keyDates.add(keyDateAssembler.assemble(milestone,context));
        }

        return keyDates;
    }

    protected  List<KeyDateInfo> _getKeyDatesForTermByType(String termId, String milestoneType, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,PermissionDeniedException {


        List<MilestoneInfo> milestones = atpService.getMilestonesByTypeForAtp(termId, milestoneType, context);

        if (milestones == null || milestones.isEmpty()) {
            return Collections.emptyList();
        }

        List<KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>(milestones.size());

        for (MilestoneInfo milestone : milestones) {
            keyDates.add(keyDateAssembler.assemble(milestone,context));
        }

        return keyDates;
    }


    @Override
    public List<KeyDateInfo> getKeyDatesForTermByDate(String termId, Date startDate, Date endDate, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesByDatesForAtp(termId, startDate, endDate, context);
        List<KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>();
        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            keyDates.add(keyDateAssembler.assemble(milestoneInfo,context));
        }
        return keyDates;
    }

    @Override
    public List<KeyDateInfo> getImpactedKeyDates(String keyDateId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<KeyDateInfo> impactedKeyDates = new ArrayList<KeyDateInfo>();
        List<MilestoneInfo> impactedMilestones = atpService.getImpactedMilestones(keyDateId, contextInfo);
        for (MilestoneInfo impactedMilestone : impactedMilestones) {
            impactedKeyDates.add(keyDateAssembler.assemble(impactedMilestone,contextInfo));
        }
        return impactedKeyDates;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public KeyDateInfo updateKeyDate(String keyDateId, KeyDateInfo keyDateInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        MilestoneInfo existingMilestone = atpService.getMilestone(keyDateId,context);

        if (!StringUtils.equals(existingMilestone.getStateKey(),keyDateInfo.getStateKey())){
            throw new OperationFailedException("It's not possible to update the state with this call. Please use changeKeyDateState() instead");
        }
        MilestoneInfo toUpdate = keyDateAssembler.disassemble(keyDateInfo,context);
        MilestoneInfo newMilestone;
        try {
            newMilestone = atpService.updateMilestone(keyDateId, toUpdate, context);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Error updating milestone", e);
        }

        return keyDateAssembler.assemble(newMilestone,context);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo changeKeyDateState(@WebParam(name = "keyDateId") String keyDateId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return processMilestoneStateChange(keyDateId,nextStateKey,contextInfo);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteKeyDate(String keyDateId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return atpService.deleteMilestone(keyDateId, context);
    }

    @Override
    @Transactional(readOnly = false)
    public KeyDateInfo calculateKeyDate(String keyDateId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        MilestoneInfo milestone = atpService.calculateMilestone(keyDateId, contextInfo);
        return keyDateAssembler.assemble(milestone,contextInfo);
    }

    @Override
    public TypeInfo getHolidayType(String holidayTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        TypeInfo type = typeService.getType(holidayTypeKey, context);


        if (!checkTypeForHolidayType(holidayTypeKey, context)) {
            throw new InvalidParameterException(holidayTypeKey + " is not a Holiday type");
        }

        return type;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public HolidayInfo updateHoliday(String holidayId, HolidayInfo holidayInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        try {

            MilestoneInfo existingMilestone = atpService.getMilestone(holidayId,context);

            if (!StringUtils.equals(existingMilestone.getStateKey(),holidayInfo.getStateKey())){
                throw new OperationFailedException("It's not possible to update the state with this call. Please use changeHolidayState() instead");
            }

            MilestoneInfo toUpdate = holidayAssembler.disassemble(holidayInfo, context);
            MilestoneInfo updated;
            try {
                updated = atpService.updateMilestone(holidayId, toUpdate, context);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException("Error updating milestone", e);
            }

            return holidayAssembler.assemble(updated, context);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo changeHolidayState(@WebParam(name = "holidayId") String holidayId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return processMilestoneStateChange(holidayId,nextStateKey,contextInfo);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteHoliday(String holidayId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return atpService.deleteMilestone(holidayId, context);
    }

    @Override
    @Transactional(readOnly = false)
    public HolidayInfo calculateHoliday(String holidayId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getHoliday(holidayId, contextInfo);
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public AcademicCalendarAssembler getAcalAssembler() {
        return acalAssembler;
    }

    public void setAcalAssembler(AcademicCalendarAssembler acalAssembler) {
        this.acalAssembler = acalAssembler;
    }

    public TermAssembler getTermAssembler() {
        return termAssembler;
    }

    public void setTermAssembler(TermAssembler termAssembler) {
        this.termAssembler = termAssembler;
    }

    @Override
    public List<AcademicCalendarInfo> searchForAcademicCalendars(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>();
        List<AtpInfo> atps = atpService.searchForAtps(criteria, context);

        for (AtpInfo atp : atps) {
            try {
                academicCalendars.add(acalAssembler.assemble(atp, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }

        return academicCalendars;
    }

    @Override
    public List<TermInfo> searchForTerms(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<AtpInfo> results = atpService.searchForAtps(criteria, context);
        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());

        for (AtpInfo atp : results) {
            try {
                terms.add(termAssembler.assemble(atp, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }

        return terms;
    }

    @Override
    public List<KeyDateInfo> searchForKeyDates(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<KeyDateInfo>();
    }

    @Override
    public List<HolidayInfo> searchForHolidays(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<MilestoneInfo> milestoneInfos = atpService.searchForMilestones(criteria, context);
        List<HolidayInfo> holidayInfos = new ArrayList<HolidayInfo>();

        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                holidayInfos.add(holidayAssembler.assemble(milestoneInfo, context));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling holiday with Id " + milestoneInfo.getId(), e);
            }
        }

        return holidayInfos;
    }

    protected StatusInfo processAtpStateChange(String academicCalendarId, String nextStateKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        AtpInfo atpInfo = getAtpService().getAtp(academicCalendarId,contextInfo);
        String thisStateKey = atpInfo.getStateKey();

        if (StringUtils.isNotBlank(thisStateKey) && !StringUtils.equals(thisStateKey,nextStateKey)){

            StatusInfo statusInfo = getStateTransitionsHelper().processStateConstraints(academicCalendarId,nextStateKey,contextInfo);

            if (statusInfo.getIsSuccess()){

                atpInfo.setStateKey(nextStateKey);
                try{
                    getAtpService().updateAtp(atpInfo.getId(), atpInfo, contextInfo);
                }catch(Exception e){
                    throw new OperationFailedException("Failed to update State", e);
                }

                String propagationKey = thisStateKey + ":" + nextStateKey;
                Map<String,StatusInfo> stringStatusInfoMap = getStateTransitionsHelper().processStatePropagations(academicCalendarId,propagationKey,contextInfo);

                for (StatusInfo statusInfo1 : stringStatusInfoMap.values()) {
                    if (!statusInfo1.getIsSuccess()){
                        throw new OperationFailedException(statusInfo1.getMessage());
                    }
                }

                return new StatusInfo();
            }else{
                return statusInfo;
            }
        }

        return new StatusInfo();
    }

    protected StatusInfo processMilestoneStateChange(String milestoneId,String nextStateKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        MilestoneInfo milestoneInfo = getAtpService().getMilestone(milestoneId,contextInfo);
        String thisStateKey = milestoneInfo.getStateKey();

        if (StringUtils.isNotBlank(thisStateKey) && !StringUtils.equals(thisStateKey,nextStateKey)){

            StatusInfo statusInfo = getStateTransitionsHelper().processStateConstraints(milestoneId,nextStateKey,contextInfo);

            if (statusInfo.getIsSuccess()){

                milestoneInfo.setStateKey(nextStateKey);
                try{
                    getAtpService().updateMilestone(milestoneInfo.getId(), milestoneInfo, contextInfo);
                }catch(Exception e){
                    throw new OperationFailedException("Failed to update State", e);
                }

                String propagationKey = thisStateKey + ":" + nextStateKey;
                Map<String,StatusInfo> stringStatusInfoMap = getStateTransitionsHelper().processStatePropagations(milestoneId,propagationKey,contextInfo);

                for (StatusInfo statusInfo1 : stringStatusInfoMap.values()) {
                    if (!statusInfo1.getIsSuccess()){
                        throw new OperationFailedException(statusInfo1.getMessage());
                    }
                }

                return new StatusInfo();
            }else{
                return statusInfo;
            }
        }

        return new StatusInfo();
    }

    private boolean checkTypeForAcademicCalendar(String typeKey) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (typeKey.equals(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY))
            return true;
        else
            return false;
    }

    private boolean checkTypeForHolidayCalendar(String typeKey) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (typeKey.equals(AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY))
            return true;
        else
            return false;
    }

    private boolean checkTypeForTermType(String typeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeInfo> types = getTermTypes(context);
        return checkTypeInTypes(typeKey, types);
    }

    private boolean checkTypeForHolidayType(String typeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeInfo> types = getHolidayTypes(context);
        return checkTypeInTypes(typeKey, types);
    }

    private boolean checkTypeForAcalEventType(String typeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeInfo> types = getAcalEventTypes(context);
        return checkTypeInTypes(typeKey, types);
    }

    private boolean checkTypeForKeydateType(String kdtypeKey, String termType, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeInfo> kdTypes = new ArrayList<TypeInfo>();
        List<TypeInfo> kdGroupTypes = getKeyDateTypesForTermType(termType, context);

        if (kdGroupTypes != null && !kdGroupTypes.isEmpty()) {
            for (TypeInfo type : kdGroupTypes) {
                List<TypeInfo> kdtype = getTypesForGroupType(type.getKey(), context);
                if (kdtype != null && !kdtype.isEmpty()) {
                    kdTypes.addAll(kdtype);
                }
            }
        }

        return checkTypeInTypes(kdtypeKey, kdTypes);
    }

    private boolean checkTypeForExamPeriodType(String examPeriodTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // Right now, there is only one examPeriod type. So we only check the given examPeriod type against it for now
        if (!StringUtils.equals(examPeriodTypeKey, AtpServiceConstants.ATP_EXAM_PERIOD_TYPE_KEY)){
            return false;
        }

        return true;
    }

    private List<TypeInfo> getTypesForGroupType(String groupTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException,
            OperationFailedException {
        List<TypeTypeRelationInfo> relations = null;

        try {
            relations = typeService.getTypeTypeRelationsByOwnerAndType(groupTypeKey,
                    TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, contextInfo);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(e.getMessage(), e);
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException(e.getMessage(), e);
        }

        if (relations != null) {
            List<TypeInfo> results = new ArrayList<TypeInfo>(relations.size());
            for (TypeTypeRelationInfo rel : relations) {
                try {
                    results.add(typeService.getType(rel.getRelatedTypeKey(), contextInfo));
                } catch (DoesNotExistException e) {
                    throw new OperationFailedException(e.getMessage(), e);
                } catch (PermissionDeniedException e) {
                    throw new OperationFailedException(e.getMessage(), e);
                }
            }

            return results;
        }

        return null;
    }

    private boolean checkTypeInTypes(String typeKey, List<TypeInfo> types) {
        if (types != null && !types.isEmpty()) {
            for (TypeInfo type : types) {
                if (type.getKey().equals(typeKey)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void createDeleteAtpAtpRelations(String fromAtpId,
                                             List<String> newRelatedAtpIds,
                                             String relatedAtpType,
                                             ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        // get the existing but remember the relationship Id too
        List<AtpAtpRelationInfo> atpRels = atpService.getAtpAtpRelationsByAtp(fromAtpId, context);
        Map<String, String> existing = new HashMap<String, String>();
        for (AtpAtpRelationInfo atpRelInfo : atpRels) {
            if (AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY.equals(atpRelInfo.getTypeKey())) {
                AtpInfo atp = null;
                try {
                    atp = atpService.getAtp(atpRelInfo.getRelatedAtpId(), context);
                } catch (DoesNotExistException ex) {
                    throw new OperationFailedException(atpRelInfo.getRelatedAtpId(), ex);
                }
                if (atp.getTypeKey().equals(relatedAtpType)) {
                    existing.put(atpRelInfo.getRelatedAtpId(), atpRelInfo.getId());
                }
            }
        }
        // create any new ones
        for (String relatedAtpId : newRelatedAtpIds) {
            if (!existing.containsKey(relatedAtpId)) {
                createAtpAtpRelation(fromAtpId, relatedAtpId, AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY, context);
            }
        }
        // delete any existing ones that should no longer exist
        //Code Changed for JIRA-8997 - SONAR Critical issues - Performance - Inefficient use of keySet iterator instead of entrySet iterator
        for (Map.Entry<String, String>  entry: existing.entrySet()) {
            String existingAtpId = entry.getKey();
            if (!newRelatedAtpIds.contains(existingAtpId)) {
                String atpAtpRelationId = entry.getValue();
                try {
                    StatusInfo status = atpService.deleteAtpAtpRelation(atpAtpRelationId, context);
                } catch (DoesNotExistException ex) {
                    throw new OperationFailedException("Unexpected", ex);
                }
            }
        }
    }

    private AtpAtpRelationInfo createAtpAtpRelation(String atpId,
                                                    String relatedAtpId,
                                                    String relationType,
                                                    ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        AtpAtpRelationInfo atpRel = new AtpAtpRelationInfo();
        atpRel.setAtpId(atpId);
        atpRel.setRelatedAtpId(relatedAtpId);
        atpRel.setTypeKey(relationType);
        atpRel.setStateKey(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        atpRel.setEffectiveDate(new Date());
        try {
            atpRel = atpService.createAtpAtpRelation(atpRel.getAtpId(), atpRel.getRelatedAtpId(), atpRel.getTypeKey(), atpRel, context);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Error creating atp-atp relation", e);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Error creating atp-atp relation", e);
        }
        return atpRel;
    }

    @Override
    public List<String> searchForAcademicCalendarIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateAcademicCalendar(String validationTypeKey, String academicCalendarTypeKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public TypeInfo getHolidayCalendarType(String holidayCalendarTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        TypeInfo type;
        try {
            type = typeService.getType(holidayCalendarTypeKey, contextInfo);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("Permission Denied");
        }

        return type;

    }

    @Override
    public List<TypeInfo> getHolidayCalendarTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getHolidayCalendarState(String holidayCalendarStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, PermissionDeniedException,
            OperationFailedException {
        StateInfo hcState = stateService.getState(holidayCalendarStateKey, contextInfo);

        return hcState;
    }

    @Override
    public List<StateInfo> getHolidayCalendarStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getAtpStates(contextInfo);
    }

    @Override
    public List<String> getHolidayCalendarIdsByType(String holidayCalendarTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HolidayCalendarInfo> getHolidayCalendarsByStartYear(Integer year, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        //return null;
        final Date yearBegin, yearEnd;

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, 0, 1);
        yearBegin = cal.getTime(); // XXXX-01-01 00:00:00.000
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.MILLISECOND, -1);
        yearEnd = cal.getTime(); // XXXX-12-31 23:59:59.999

        Set<AtpInfo> atpInfos = new TreeSet<AtpInfo>(new Comparator<AtpInfo>() {

            @Override
            public int compare(AtpInfo atpInfo1, AtpInfo atpInfo2) {
                return atpInfo1.getId().compareTo(atpInfo2.getId());
            }
        });

        atpInfos.addAll(atpService.getAtpsByStartDateRangeAndType(yearBegin, yearEnd, AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY, contextInfo));

        List<HolidayCalendarInfo> hcalInfos = new ArrayList<HolidayCalendarInfo>();
        for (AtpInfo atpInfo : atpInfos) {
            try {
                hcalInfos.add(holidayCalendarAssembler.assemble(atpInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }
        return hcalInfos;
    }

    @Override
    public List<String> searchForHolidayCalendarIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HolidayCalendarInfo> searchForHolidayCalendars(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<HolidayCalendarInfo> holidayCalendars = new ArrayList<HolidayCalendarInfo>();
        List<AtpInfo> atps = atpService.searchForAtps(criteria, contextInfo);

        for (AtpInfo atp : atps) {
            try {
                holidayCalendars.add(holidayCalendarAssembler.assemble(atp, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }

        return holidayCalendars;
    }

    @Override
    public List<ValidationResultInfo> validateHolidayCalendar(String validationTypeKey, String holidayCalendarTypeKey, HolidayCalendarInfo holidayCalendarInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public List<String> getTermIdsByType(String termTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO validate type key is a valid term type
        return atpService.getAtpIdsByType(termTypeKey, contextInfo);
    }

    @Override
    public List<TermInfo> getTermsByCode(String code, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        List<AtpInfo> atpList = atpService.getAtpsByCode(code, contextInfo);
        List<TermInfo> termList = new ArrayList<TermInfo>(atpList.size());
        for (AtpInfo atp : atpList) {
            if (atp != null && checkTypeForTermType(atp.getTypeKey(), contextInfo)) {
                try {
                    termList.add(termAssembler.assemble(atp, contextInfo));
                } catch (AssemblyException e) {
                    throw new OperationFailedException("AssemblyException", e);
                }
            }
        }
        return termList;
    }

    @Override
    public List<String> searchForTermIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getKeyDateTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<TypeInfo> kdTypes = new ArrayList<TypeInfo>();
        List<TypeInfo> instructionalKDs = getTypesForGroupType("kuali.milestone.type.group.instructional", contextInfo);
        if (instructionalKDs != null && !instructionalKDs.isEmpty()) {
            kdTypes.addAll(instructionalKDs);
        }

        List<TypeInfo> registrationKDs = getTypesForGroupType("kuali.milestone.type.group.registration", contextInfo);
        if (registrationKDs != null && !registrationKDs.isEmpty()) {
            kdTypes.addAll(registrationKDs);
        }

        return kdTypes;

    }

    @Override
    public StateInfo getKeyDateState(String keyDateStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getKeyDateStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getKeyDateIdsByType(String keyDateTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getKeyDateIdsByTypeForTerm(String keyDateTypeKey, String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO
        List<MilestoneInfo> keyDates = atpService.getMilestonesByTypeForAtp(termId, keyDateTypeKey, contextInfo);
        List<String> ids = new ArrayList<String>(keyDates.size());
        for (MilestoneInfo m : keyDates) {
            ids.add(m.getId());
        }
        return ids;
    }

    @Override
    public List<String> searchForKeyDateIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateKeyDate(String validationTypeKey, String termId, String keyDateTypeKey, KeyDateInfo keyDateInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public KeyDateInfo createKeyDate(String termId, String keyDateTypeKey, KeyDateInfo keyDateInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        KeyDateInfo newKeyDateInfo = null;
        MilestoneInfo milestoneInfo = null;

        AtpInfo atp = atpService.getAtp(termId, contextInfo);

        if(checkTypeForKeydateType(keyDateTypeKey, atp.getTypeKey(), contextInfo)){
            milestoneInfo = keyDateAssembler.disassemble(keyDateInfo, contextInfo);

            if (milestoneInfo != null) {

                if (StringUtils.isBlank(milestoneInfo.getTypeKey())) {
                    milestoneInfo.setTypeKey(keyDateTypeKey);
                }
                MilestoneInfo newMilestone = null;
                try {
                    newMilestone = atpService.createMilestone(keyDateTypeKey, milestoneInfo, contextInfo);
                    newKeyDateInfo = keyDateAssembler.assemble(newMilestone, contextInfo);
                } catch (ReadOnlyException e) {
                    throw new OperationFailedException("Error creating milestone", e);
                }

                try {
                    atpService.addMilestoneToAtp(newMilestone.getId(), termId, contextInfo);
                } catch (AlreadyExistsException e) {
                    throw new OperationFailedException("Error creating ATP-Milestone relation", e);
                }

            }
        } else {
            throw new InvalidParameterException("Keydate type not found: '" + keyDateTypeKey + "'");
        }

        return newKeyDateInfo;
    }

    @Override
    public TypeInfo getAcalEventType(String acalEventTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getAcalEventTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getTypesForGroupType(AtpServiceConstants.MILESTONE_EVENT_GROUPING_TYPE_KEY, contextInfo);
    }

    @Override
    public List<TypeInfo> getAcalEventTypesForAcademicCalendarType(String academicCalendarTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getAcalEventState(String acalEventStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getAcalEventStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public AcalEventInfo getAcalEvent(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        MilestoneInfo milestoneInfo = atpService.getMilestone(acalEventId, contextInfo);

        if (milestoneInfo == null) {
            throw new DoesNotExistException(acalEventId);
        }

        try {
            return acalEventAssembler.assemble(milestoneInfo, contextInfo);
        } catch (AssemblyException e) {
            throw new OperationFailedException("Error assembling AcalEvent", e);
        }
    }

    @Override
    public List<AcalEventInfo> getAcalEventsByIds(List<String> acalEventIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesByIds(acalEventIds, contextInfo);
        List<AcalEventInfo> acalEventInfos = new ArrayList<AcalEventInfo>();

        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                acalEventInfos.add(acalEventAssembler.assemble(milestoneInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling AcalEvent", e);
            }
        }

        return acalEventInfos;
    }

    @Override
    public List<String> getAcalEventIdsByType(String acalEventTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return atpService.getMilestoneIdsByType(acalEventTypeKey, contextInfo);
    }

    @Override
    public List<AcalEventInfo> getAcalEventsForAcademicCalendar(String academicCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesForAtp(academicCalendarId, contextInfo);
        List<AcalEventInfo> acalEventInfos = new ArrayList<AcalEventInfo>();

        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                acalEventInfos.add(acalEventAssembler.assemble(milestoneInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling AcalEvent", e);
            }
        }

        return acalEventInfos;
    }

    @Override
    public List<AcalEventInfo> getAcalEventsForAcademicCalendarByDate(String academicCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesByDatesForAtp(academicCalendarId, startDate, endDate, contextInfo);
        List<AcalEventInfo> acalEventInfos = new ArrayList<AcalEventInfo>();

        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                acalEventInfos.add(acalEventAssembler.assemble(milestoneInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling AcalEvent", e);
            }
        }

        return acalEventInfos;
    }

    @Override
    public List<AcalEventInfo> getImpactedAcalEvents(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<AcalEventInfo>();
    }

    @Override
    public List<String> searchForAcalEventIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<AcalEventInfo> searchForAcalEvents(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<MilestoneInfo> milestoneInfos = atpService.searchForMilestones(criteria, contextInfo);
        List<AcalEventInfo> acalEventInfos = new ArrayList<AcalEventInfo>();

        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                acalEventInfos.add(acalEventAssembler.assemble(milestoneInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling AcalEvent", e);
            }
        }

        return acalEventInfos;
    }

    @Override
    public List<ValidationResultInfo> validateAcalEvent(String validationTypeKey, String termId, String acalEventTypeKey, AcalEventInfo acalEventInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AcalEventInfo createAcalEvent(String academicCalendarId, String acalEventTypeKey, AcalEventInfo acalEventInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if(checkTypeForAcalEventType(acalEventTypeKey, contextInfo)){
            try {
                MilestoneInfo milestoneInfo = acalEventAssembler.disassemble(acalEventInfo, contextInfo);
                if (StringUtils.isBlank(milestoneInfo.getTypeKey())) {
                    milestoneInfo.setTypeKey(acalEventTypeKey);
                }

                MilestoneInfo newMilestone = atpService.createMilestone(acalEventTypeKey, milestoneInfo, contextInfo);

                atpService.addMilestoneToAtp(newMilestone.getId(), academicCalendarId, contextInfo);
                return acalEventAssembler.assemble(newMilestone, contextInfo);

            } catch (AssemblyException e) {
                throw new OperationFailedException("Error disassembling AcalEvent", e);
            } catch (AlreadyExistsException e) {
                throw new OperationFailedException("Error associating AcalEvent with AcademicCalendar", e);
            }
        } else {
            throw new InvalidParameterException("AcalEvent type not found: '" + acalEventTypeKey + "'");
        }

    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AcalEventInfo updateAcalEvent(String acalEventId, AcalEventInfo acalEventInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {


        try {
            MilestoneInfo milestoneInfo = acalEventAssembler.disassemble(acalEventInfo, contextInfo);
            MilestoneInfo newMilestone = atpService.updateMilestone(acalEventId, milestoneInfo, contextInfo);
            return acalEventAssembler.assemble(newMilestone, contextInfo);
        } catch (AssemblyException e) {
            throw new OperationFailedException("Error assembling AcalEvent", e);
        }

    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo changeAcalEventState(@WebParam(name = "acalEventId") String acalEventId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return processMilestoneStateChange(acalEventId,nextStateKey,contextInfo);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteAcalEvent(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        MilestoneInfo existingMilestone = atpService.getMilestone(acalEventId, contextInfo);

        if (existingMilestone == null) {
            throw new DoesNotExistException("AcalEvent doesnt exists " + acalEventId);
        }

        StatusInfo status = atpService.deleteMilestone(acalEventId, contextInfo);
        return status;

    }

    @Override
    @Transactional(readOnly = false)
    public AcalEventInfo calculateAcalEvent(String acalEventId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Sambit - THIS METHOD NEEDS JAVADOCS
        return getAcalEvent(acalEventId, contextInfo);
    }

    @Override
    public List<TypeInfo> getHolidayTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getTypesForGroupType(AtpServiceConstants.MILESTONE_HOLIDAY_GROUPING_TYPE_KEY, contextInfo);
    }

    @Override
    public List<TypeInfo> getHolidayTypesForHolidayCalendarType(String holidayCalendarTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getHolidayState(String holidayStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StateInfo holidayState = stateService.getState(holidayStateKey, contextInfo);

        return holidayState;
    }

    @Override
    public List<StateInfo> getHolidayStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<StateInfo> results;
        try {
            results = stateService.getStatesByLifecycle(AtpServiceConstants.MILESTONE_PROCESS_KEY, contextInfo);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException(AtpServiceConstants.MILESTONE_PROCESS_KEY, ex);
        }

        return results;

    }

    @Override
    public HolidayInfo getHoliday(String holidayId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        MilestoneInfo milestoneInfo = atpService.getMilestone(holidayId, contextInfo);

        if (milestoneInfo == null) {
            throw new DoesNotExistException(holidayId);
        }

        try {
            return holidayAssembler.assemble(milestoneInfo, contextInfo);
        } catch (AssemblyException e) {
            throw new OperationFailedException("Error assembling Holiday", e);
        }
    }

    @Override
    public List<HolidayInfo> getHolidaysByIds(List<String> holidayIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesByIds(holidayIds, contextInfo);

        List<HolidayInfo> holidayInfos = new ArrayList<HolidayInfo>();

        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                holidayInfos.add(holidayAssembler.assemble(milestoneInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling holiday with Id " + milestoneInfo.getId(), e);
            }
        }

        return holidayInfos;
    }

    @Override
    public List<String> getHolidayIdsByType(String holidayTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return atpService.getMilestoneIdsByType(holidayTypeKey, contextInfo);
    }

    @Override
    public List<HolidayInfo> getHolidaysForHolidayCalendar(String holidayCalendarId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesForAtp(holidayCalendarId, contextInfo);
        List<HolidayInfo> holidayInfos = new ArrayList<HolidayInfo>();
        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                holidayInfos.add(holidayAssembler.assemble(milestoneInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling holiday" + milestoneInfo.getId(), e);
            }
        }

        return holidayInfos;
    }

    @Override
    public List<HolidayInfo> getHolidaysForHolidayCalendarByDate(String holidayCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestoneInfos = atpService.getMilestonesByDatesForAtp(holidayCalendarId, startDate, endDate, contextInfo);
        List<HolidayInfo> holidayInfos = new ArrayList<HolidayInfo>();
        for (MilestoneInfo milestoneInfo : milestoneInfos) {
            try {
                holidayInfos.add(holidayAssembler.assemble(milestoneInfo, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("Error assembling holiday" + milestoneInfo.getId(), e);
            }
        }

        return holidayInfos;
    }

    @Override
    public List<HolidayInfo> getImpactedHolidays(String holidayId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<HolidayInfo>();
    }

    @Override
    public List<String> searchForHolidayIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateHoliday(String validationTypeKey, String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public HolidayInfo createHoliday(String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        HolidayInfo newHolidayInfo = null;
        MilestoneInfo milestoneInfo = null;

        if(checkTypeForHolidayType(holidayTypeKey, contextInfo)){
            try {
                milestoneInfo = holidayAssembler.disassemble(holidayInfo, contextInfo);
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException in disassembling: " + e.getMessage());
            }

            if (milestoneInfo != null) {
                if (StringUtils.isBlank(milestoneInfo.getTypeKey())) {
                    milestoneInfo.setTypeKey(holidayTypeKey);
                }
                MilestoneInfo newMilestone = null;
                try {
                    newMilestone = atpService.createMilestone(holidayTypeKey, milestoneInfo, contextInfo);
                    newHolidayInfo = holidayAssembler.assemble(newMilestone, contextInfo);
                } catch (ReadOnlyException e) {
                    throw new OperationFailedException("Error creating milestone", e);
                } catch (AssemblyException e) {
                    throw new OperationFailedException("AssemblyException in assembling: " + e.getMessage());
                }

                try {
                    atpService.addMilestoneToAtp(newMilestone.getId(), holidayCalendarId, contextInfo);
                } catch (AlreadyExistsException e) {
                    throw new OperationFailedException("Error creating ATP-Milestone relation", e);
                }

            }
        } else {
            throw new InvalidParameterException("Holiday type not found: '" + holidayTypeKey + "'");
        }

        return newHolidayInfo;
    }

    @Override
    public String getAcademicCalendarData(String academicCalendarId, String calendarDataFormatTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * Calculates the number of instructional days for a Term. The number of
     * instructional days is the number of class days in a Term minus the
     * non-instructional holidays on the related holiday calendar.
     *
     * Instructional Days for a term are calculated as follows:
     * 1. Find the start and end dates for the term.
     * 2. Count all instructional days for the term. Instructional days of the week is configured via the term's type attribute*
     * 3. Subtract all non-instructional days, like holidays, from the previous count.
     *
     * * By default, KS has it's term types configured so instructional days are only Monday -> Friday (MTWHF). The default value
     * is configured in the application config file with the constant "kuali.ks.core.academiccalendar.instructionalDaysDefault".
     * This default configuration can be overridden by configuring the
     * term's type attribute:  kuali.attribute.type.atp.term.instructional.days
     * Update the Type Attribute table if you want to configure different instructional days.
     *
     * @param termId      an identifier for a Term
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public Integer getInstructionalDaysForTerm(String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        KeyDateInfo instructionalPeriodKeyDate = null;

        AtpInfo termAtp = atpService.getAtp(termId, contextInfo);

        // this section will find all key date for the term. Then do a for loop to find the keydate of type
        // MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY. that KeyDate give the start and end dates for the term.
        List<KeyDateInfo> keyDates = _getKeyDatesForTermByType(termAtp.getId(), AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY, contextInfo);
        for (KeyDateInfo keyDate : keyDates) {       // there should only be one here.
            if (keyDate.getTypeKey().equals(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY)) {
                instructionalPeriodKeyDate = new KeyDateInfo(keyDate);
                break;
            }
        }

        // If there are no Instructional Days configured for a term, it should be 0.
        if(instructionalPeriodKeyDate == null ||
                instructionalPeriodKeyDate.getStartDate() == null ||
                instructionalPeriodKeyDate.getEndDate() == null ){
            return 0;
        }

        int instructionalDaysForTerm = 0;
        DateMidnight currentDate = new DateMidnight(instructionalPeriodKeyDate.getStartDate().getTime());
        DateMidnight endDate = new DateMidnight(instructionalPeriodKeyDate.getEndDate().getTime());

        // go from start to end and count instructional days
        while (currentDate.compareTo(endDate) <= 0) {
            if (_dateIsInstructional(typeService.getType(termAtp.getTypeKey(),contextInfo),currentDate)) {
                ++instructionalDaysForTerm;
            }
            currentDate = currentDate.plusDays(1);
        }

        // subtract non-instructional holidays which fall on instructional days
        instructionalDaysForTerm -=
                _getNumberOfNonInstructionalHolidaysForTerm(termAtp, instructionalPeriodKeyDate, contextInfo);

        return instructionalDaysForTerm;
    }

    /**
     * For this method we need to find the holdiday calendars related to the term. There is an indirect relationship
     * between terms and holiday calendars. Terms are linked to academic calendars, which in term are related to holiday
     * calendars.
     *
     * Once we have the holday calendar we want to find which holidays fall within the instructional period. From there
     * we find out if it's a non-instructional day and add it to the count.
     *
     * @param termAtp
     * @param instructionalPeriodKeyDate
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    private int _getNumberOfNonInstructionalHolidaysForTerm(AtpInfo termAtp, KeyDateInfo instructionalPeriodKeyDate, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        //DateMidnight currentDate, stopDate;
        List<DateMidnight> nonInstructionalHolidayDates = new ArrayList<DateMidnight>();

        // Call the SearchService and get all holidays for this term. This was put in to increase performance
        SearchRequestInfo sr = new SearchRequestInfo(CoreSearchServiceImpl.ACAL_GET_HOLIDAYS_BY_TERM_SEARCH_KEY);
        sr.addParam(CoreSearchServiceImpl.SearchParameters.TERM_ID, termAtp.getId());

        SearchResultInfo searchResult = getSearchService().search(sr, contextInfo);

        for (SearchResultRowInfo row : searchResult.getRows()) {

            Boolean isInstDay = false;
            Boolean isDateRange = false;

            Date holStartDate = null;
            Date holEndDate = null;

            for(SearchResultCellInfo cellInfo : row.getCells()){
                if(CoreSearchServiceImpl.SearchResultColumns.MSTONE_INSTR_DAY.equals(cellInfo.getKey())){
                    isInstDay = Boolean.valueOf(cellInfo.getValue());
                }
                if(CoreSearchServiceImpl.SearchResultColumns.MSTONE_DT_RANGE.equals(cellInfo.getKey())){
                    isDateRange = Boolean.valueOf(cellInfo.getValue());
                }
                if(CoreSearchServiceImpl.SearchResultColumns.MSTONE_START_DT.equals(cellInfo.getKey())){
                    holStartDate = DateFormatters.DEFAULT_TIMESTAMP_FORMATTER.parse(cellInfo.getValue());
                }
                if(CoreSearchServiceImpl.SearchResultColumns.MSTONE_END_DT.equals(cellInfo.getKey())){
                    holEndDate = (cellInfo.getValue() != null && ! cellInfo.getValue().isEmpty()?
                            DateFormatters.DEFAULT_TIMESTAMP_FORMATTER.parse(cellInfo.getValue()): null);
                }
            }

            // If's it's not a range then the start and end dates are the same
            if(!isDateRange){
                holEndDate = holStartDate;
            }

            // if holiday is not an instructional day
            // and if holiday is in instructional period
            if(!isInstDay && doDatesOverlap(instructionalPeriodKeyDate.getStartDate(), instructionalPeriodKeyDate.getEndDate(), holStartDate, holEndDate)){
                DateMidnight currentDate = new DateMidnight(holStartDate.getTime());
                DateMidnight stopDate = new DateMidnight(holEndDate.getTime());

                while (currentDate.compareTo(stopDate) <= 0) {
                    // and holiday falls in a term specific instructional day (Monday->Friday)
                    if ((_dateIsInstructional(typeService.getType(termAtp.getTypeKey(), contextInfo), currentDate))
                            &&  ( ! nonInstructionalHolidayDates.contains(currentDate))) {
                        nonInstructionalHolidayDates.add(currentDate);
                    }
                    currentDate = currentDate.plusDays(1);
                }
            }
        }

        return nonInstructionalHolidayDates.size();
    }

    /**
     * Instructional Days are configurable via the type attribute TypeServiceConstants.ATP_TERM_INSTRUCTIONAL_DAYS_ATTR.
     *
     * By default, every term atp has  this field configured to Monday -> Friday, MTWHF. The default is set in the
     * application config file. But you can easly configure
     * this for your own term types by updating the type attribute in the database.
     *
     * @param atpType
     * @param date
     * @return
     */
    private boolean _dateIsInstructional(TypeInfo atpType, DateMidnight date) {

        boolean bRet = false;

        // This allows us to override the default with an explicit value configured on the term
        String instrDaysConfig = atpType.getAttributeValue(TypeServiceConstants.ATP_TERM_INSTRUCTIONAL_DAYS_ATTR);


        // if we don't have an explicit value set for this term then pull the default from the config file.
        if(instrDaysConfig == null || instrDaysConfig.isEmpty()){
            org.kuali.rice.core.api.config.property.Config cfg = ConfigContext.getCurrentContextConfig();
            instrDaysConfig = cfg.getProperty(AcademicCalendarServiceConstants.CONFIG_PARAM_KEY_INSTRUCTIONAL_DAYS_DEFAULT);
        }

        if(instrDaysConfig != null && !instrDaysConfig.isEmpty()){
            if(date.getDayOfWeek() == DateTimeConstants.MONDAY){
                bRet = instrDaysConfig.contains("M");
            } else if(date.getDayOfWeek() == DateTimeConstants.TUESDAY){
                bRet = instrDaysConfig.contains("T");
            }else if(date.getDayOfWeek() == DateTimeConstants.WEDNESDAY){
                bRet = instrDaysConfig.contains("W");
            }else if(date.getDayOfWeek() == DateTimeConstants.THURSDAY){
                bRet = instrDaysConfig.contains("H");
            }else if(date.getDayOfWeek() == DateTimeConstants.FRIDAY){
                bRet = instrDaysConfig.contains("F");
            }else if(date.getDayOfWeek() == DateTimeConstants.SATURDAY){
                bRet = instrDaysConfig.contains("S");
            }else if(date.getDayOfWeek() == DateTimeConstants.SUNDAY){
                bRet = instrDaysConfig.contains("U");
            }
        }else {
            bRet = true;
        }

        return bRet;
    }


    /**
     * For this method we query the atp relationship table and find the related acals.
     *
     * @param termId  term identifier
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     */
    public List<AcademicCalendarInfo> getAcademicCalendarsForTerm(String termId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException {
        List<AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>();    // will be returned. init empty

        List<Predicate> predicates = new ArrayList<Predicate>();

        // In order to speed up performance we're going to ONLY pull back the ATP-ATP relationships that matter.
        predicates.add(PredicateFactory.equal("atpType", AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY));
        predicates.add(PredicateFactory.equal("atpState", AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY));
        predicates.add(PredicateFactory.equal("atp.atpType", AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY));
        predicates.add(PredicateFactory.equal("relatedAtp.id", termId));

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();


        try{
            List<String> atpIds = new ArrayList<String>();
            // This is a performance problem... we only want the atpId from the atpAtpRelationInfo. The worst part of
            // all this is that the entity used to populate the AtpAtpRelationInfo contains the linked ATP, but
            // durning the transformation only the ID of the ATP is stored in the info object.
            List<AtpAtpRelationInfo> atpAtpRelationsForTerm = this.atpService.searchForAtpAtpRelations(qbc,contextInfo);
            for (AtpAtpRelationInfo atpRelationForTerm : atpAtpRelationsForTerm) {
                atpIds.add(atpRelationForTerm.getAtpId());
            }

            if(!atpIds.isEmpty())    {
                // grab the atps and add them to the list.
                List<AtpInfo> atps = this.atpService.getAtpsByIds(atpIds, contextInfo);
                for(AtpInfo atp : atps){
                    academicCalendars.add(this.acalAssembler.assemble(atp, contextInfo));
                }
            }


        }catch (Exception ex){
            throw new OperationFailedException("Error trying to determine if a term is a subterm", ex);
        }

        return academicCalendars;
    }


    /**
     *
     * Date Compare Logic. We need to do two things here.
     * 1. Make sure a single date is within the time period
     * 2. Make sure SOME PART of a Date Rance is within the time period.
     *
     * For example, you have a week and T -> S is your time period: M | T W H F S | U
     * You have two holidays in this calendar. The first is on H, the second spans M->T
     *
     * So, in order to cover all cases, you need to have some "interesting logic".
     * First set up your holiday start and end dates for each holiday you want to evaluate.
     * holiday start date is easy, it's the holiday start date.
     * Holiday end date depends on if the holiday is a range or a day. If it's a range then
     * holidayEndDate = holidayEndDate. If it's a day then holidayEndDate = holidayStartDate.
     *
     * Now that you have holiday startDate and endDate configured you need use a comparator
     * that returns -1,0,1 (<,=,>).
     *
     * 1. compare holidayStartDate to the periodEndDate; call this compStart
     * 2. compare holidayEndDate to the periodStartDate; call this compEnd
     *
     * The holiday is "in the period" if compStart <= 0 && compEnd >= 0.
     *
     * @param academicCalendarId an identifier for an Academic Calendar
     * @param startDate                  the start of period date range
     * @param endDate                    the end of period date range
     * @param contextInfo                information containing the principalId and
     *                                   locale information about the caller of service
     *                                   operation
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    @Override
    public List<HolidayInfo> getHolidaysByDateForAcademicCalendar(String academicCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AcademicCalendarInfo acInfo = getAcademicCalendar(academicCalendarId, contextInfo);
        List<HolidayInfo> holidaysForAcal = new ArrayList<HolidayInfo>();
        for (String holidayCalendarId : acInfo.getHolidayCalendarIds()) {

            List<HolidayInfo> holidays = getHolidaysForHolidayCalendar(holidayCalendarId, contextInfo);

            for (HolidayInfo holiday : holidays) {
                int compStart = holiday.getStartDate().compareTo(endDate);
                int compEnd = (holiday.getIsDateRange()? holiday.getEndDate().compareTo(startDate) : holiday.getStartDate().compareTo(startDate) );
                if (compStart <= 0 && compEnd >= 0) {
                    holidaysForAcal.add(holiday);
                }
            }
        }

        return holidaysForAcal;
    }

    private boolean doDatesOverlap(Date periodStartDate, Date periodEndDate, Date subStart, Date subEnd){
        boolean bRet = false;

        int compStart = subStart.compareTo(periodEndDate);
        int compEnd = subEnd.compareTo(periodStartDate);
        if (compStart <= 0 && compEnd >= 0) {
            bRet = true;
        }

        return bRet;
    }



    public StateTransitionsHelper getStateTransitionsHelper() {
        return stateTransitionsHelper;
    }

    public void setStateTransitionsHelper(StateTransitionsHelper stateTransitionsHelper) {
        this.stateTransitionsHelper = stateTransitionsHelper;
    }

    public boolean isInitialState(String lifecycleKey, String initState, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        List<String>  initStates = null;

        initStates = stateService.getInitialStatesByLifecycle(lifecycleKey, context);
        if(initStates != null && !initStates.isEmpty()) {
            if(!initStates.contains(initState)) {
                return false;
            }
        }
        return true;
    }

    public SearchService getSearchService() {
        if (searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public List<ExamPeriodInfo> getExamPeriodsForTerm(String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByTypeAndAtp(termId,
                AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TERM2EXAMPERIOD_TYPE_KEY,
                contextInfo);
        List<ExamPeriodInfo> examPeriodInfos = new ArrayList<ExamPeriodInfo>(results.size());
        List<String> examPeriodIds = new ArrayList<String>(results.size());

        for (AtpAtpRelationInfo atpRelation : results) {
            examPeriodIds.add(atpRelation.getRelatedAtpId());
        }

        if (examPeriodIds!=null && !examPeriodIds.isEmpty()) {
            List<AtpInfo> atpInfos = atpService.getAtpsByIds(examPeriodIds, contextInfo);
            for (AtpInfo atpInfo : atpInfos) {
                examPeriodInfos.add(examPeriodTransformer.atp2ExamPeriod(atpInfo));
            }
        }

        return examPeriodInfos;
    }

    @Override
    public TypeInfo getExamPeriodType(String examPeriodTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException ("has not been implemented yet!");
    }

    @Override
    public List<TypeInfo> getExamPeriodTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException ("has not been implemented yet!");
    }

    @Override
    public List<TypeInfo> getExamPeriodTypesForTermType(String termTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeInfo> types;
        types = this.typeService.getAllowedTypesForType(termTypeKey, contextInfo);

        // filter by ref object uri
        List<TypeInfo> list = new ArrayList<TypeInfo>(types.size());
        for (TypeInfo type : types) {
            if (type.getRefObjectUri() == null) {
                throw new NullPointerException(type.getKey());
            }

            if (StringUtils.equals(type.getRefObjectUri(),AtpServiceConstants.REF_OBJECT_URI_ATP)) {
                list.add(type);
            }
        }
        return list;
    }

    @Override
    public StateInfo getExamPeriodState(String examPeriodStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException ("has not been implemented yet!");
    }

    @Override
    public List<StateInfo> getExamPeriodStates(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException ("has not been implemented yet!");
    }

    @Override
    public ExamPeriodInfo getExamPeriod(String examPeriodId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpInfo atp = atpService.getAtp(examPeriodId, contextInfo);

        if (atp == null) {
            throw new DoesNotExistException("ATP does not exist. Exam Period id: " + examPeriodId);
        }
        if (!checkTypeForExamPeriodType(atp.getTypeKey(), contextInfo)) {
            throw new OperationFailedException("ATP type is not valid. Exam Period id: " + examPeriodId + " Type: " + atp.getTypeKey());
        }

        return examPeriodTransformer.atp2ExamPeriod(atp);
    }

    @Override
    public List<ExamPeriodInfo> getExamPeriodsByIds(List<String> examPeriodIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException ("has not been implemented yet!");
    }

    @Override
    public List<String> getExamPeriodIdsByType(String examPeriodTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException ("has not been implemented yet!");
    }

    @Override
    public List<ExamPeriodInfo> getExamPeriodsByCode(String code, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException ("has not been implemented yet!");
    }

    @Override
    public List<String> searchForExamPeriodIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException ("has not been implemented yet!");
    }

    @Override
    public List<ExamPeriodInfo> searchForExamPeriods(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException ("has not been implemented yet!");
    }

    @Override
    public List<ValidationResultInfo> validateExamPeriod(String validationTypeKey, String examPeriodTypeKey, ExamPeriodInfo examPeriodInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ExamPeriodInfo createExamPeriod(String examPeriodTypeKey, ExamPeriodInfo examPeriodInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        AtpInfo atp = examPeriodTransformer.ExamPeriod2Atp(examPeriodInfo);
        AtpInfo newAtp = atpService.createAtp(atp.getTypeKey(), atp, contextInfo);

        return examPeriodTransformer.atp2ExamPeriod(newAtp);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ExamPeriodInfo updateExamPeriod(String examPeriodId, ExamPeriodInfo examPeriodInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        AtpInfo existingAtp = atpService.getAtp(examPeriodId, contextInfo);
        AtpInfo toUpdateAtp = examPeriodTransformer.ExamPeriod2Atp(examPeriodInfo);

        if (!StringUtils.equals(existingAtp.getStateKey(), examPeriodInfo.getStateKey())) {
            throw new OperationFailedException("State cant be updated with this call. Please use changeExamPeriodState() instead.");
        }

        AtpInfo updatedAtp = atpService.updateAtp(examPeriodId, toUpdateAtp, contextInfo);

        return examPeriodTransformer.atp2ExamPeriod(updatedAtp);
    }

    @Override
    public StatusInfo changeExamPeriodState(String examPeriodId, String nextStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return processAtpStateChange(examPeriodId,nextStateKey,contextInfo);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteExamPeriod(String examPeriodId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpInfo atp = atpService.getAtp(examPeriodId, contextInfo);
        if (atp == null) {
            throw new DoesNotExistException(examPeriodId);
        }

        if (!checkTypeForExamPeriodType(atp.getTypeKey(), contextInfo)) {
            throw new InvalidParameterException("Invalid examPeriodId: " + examPeriodId + "  Given key does not map to a Exam Period");
        }

        StatusInfo result = atpService.deleteAtp(examPeriodId, contextInfo);

        return result;
    }

    @Override
    public StatusInfo addExamPeriodToTerm(String termId, String examPeriodId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        TermInfo Term = getTerm(termId, contextInfo);
        ExamPeriodInfo examPeriodInfo = getExamPeriod(examPeriodId, contextInfo);

        // check if the relationship already exists
        List<ExamPeriodInfo> examPeriodInfos = getExamPeriodsForTerm(Term.getId(), contextInfo);

        for (ExamPeriodInfo tempExamPeriod : examPeriodInfos) {
            if (StringUtils.equals(tempExamPeriod.getId(),examPeriodInfo.getId())) {
                throw new AlreadyExistsException("A relationship already exists exists between term: " + termId + " and exam period: " + examPeriodId);
            }
        }

        StatusInfo resultStatus = new StatusInfo();

        try {
            createAtpAtpRelation(termId, examPeriodInfo.getId(), AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TERM2EXAMPERIOD_TYPE_KEY, contextInfo);
        } catch (DataValidationErrorException e) {
            throw new OperationFailedException ("Creation of AtpAtpRelation failed due to DataValidationError Execption: ", e);
        }

        return resultStatus;
    }

    @Override
    public StatusInfo removeExamPeriodFromTerm(String termId, String examPeriodId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException ("has not been implemented yet!");
    }

    @Override
    public List<TermInfo> getTermsForExamPeriod(String examPeriodId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException ("has not been implemented yet!");
    }

}