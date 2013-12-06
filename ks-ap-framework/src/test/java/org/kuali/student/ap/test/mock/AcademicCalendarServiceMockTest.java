package org.kuali.student.ap.test.mock;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.r2.common.assembler.AssemblyException;
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
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.AcalEventInfo;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.acal.service.assembler.TermAssembler;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class AcademicCalendarServiceMockTest implements AcademicCalendarService{
    private ArrayList<AcademicCalendarInfo> academicCalendars;
    private TermAssembler termAssembler;

    public TermAssembler getTermAssembler() {
        return termAssembler;
    }

    public void setTermAssembler(TermAssembler termAssembler) {
        this.termAssembler = termAssembler;
    }

    private ArrayList<AcademicCalendarInfo> getAcademicCalendars(){
        if(academicCalendars==null){
            academicCalendars = new ArrayList<AcademicCalendarInfo>();
            academicCalendars.add(createAcademicCalendar("76e1d98a-0f82-485b-academic-calendar",null));
        }
        return academicCalendars;
    }
    private AcademicCalendarInfo createAcademicCalendar(String id, List<String> holidayCalendarIds){
        AcademicCalendarInfo newCalendar = new AcademicCalendarInfo();
        newCalendar.setId(id);
        newCalendar.setHolidayCalendarIds(holidayCalendarIds);
        try{
            AtpInfo atp = KsapFrameworkServiceLocator.getAtpService().getAtp(id, KsapFrameworkServiceLocator.getContext().getContextInfo());
            newCalendar.setStartDate(atp.getStartDate());
            newCalendar.setEndDate(atp.getEndDate());
            newCalendar.setAdminOrgId(atp.getAdminOrgId());
            newCalendar.setName(atp.getName());

        }catch (Exception e){

        }
        return  newCalendar;
    }

    @Override
    public TypeInfo getAcademicCalendarType(@WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getAcademicCalendarTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StateInfo getAcademicCalendarState(@WebParam(name = "academicCalendarStateKey") String academicCalendarStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StateInfo> getAcademicCalendarStates(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AcademicCalendarInfo getAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ArrayList<AcademicCalendarInfo> academicCalendars = getAcademicCalendars();
        for(AcademicCalendarInfo acal : academicCalendars){
            if(acal.getId().equals(academicCalendarId)) return acal;
        }
        return null;
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByIds(@WebParam(name = "academicCalendarIds") List<String> academicCalendarIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getAcademicCalendarIdsByType(@WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByStartYear(@WebParam(name = "year") Integer year, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsForTerm(@WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForAcademicCalendarIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AcademicCalendarInfo> searchForAcademicCalendars(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateAcademicCalendar(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "academicCalendarInfo") AcademicCalendarInfo academicCalendarInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AcademicCalendarInfo createAcademicCalendar(@WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "academicCalendarInfo") AcademicCalendarInfo academicCalendarInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AcademicCalendarInfo updateAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "academicCalendarInfo") AcademicCalendarInfo academicCalendarInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo changeAcademicCalendarState(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AcademicCalendarInfo copyAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAcademicCalendarData(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "calendarDataFormatTypeKey") String calendarDataFormatTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TypeInfo getHolidayCalendarType(@WebParam(name = "holidayCalendarTypeKey") String holidayCalendarTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getHolidayCalendarTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StateInfo getHolidayCalendarState(@WebParam(name = "holidayCalendarStateKey") String holidayCalendarStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StateInfo> getHolidayCalendarStates(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HolidayCalendarInfo getHolidayCalendar(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<HolidayCalendarInfo> getHolidayCalendarsByIds(@WebParam(name = "holidayCalendarIds") List<String> holidayCalendarIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getHolidayCalendarIdsByType(@WebParam(name = "holidayCalendarTypeKey") String holidayCalendarTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<HolidayCalendarInfo> getHolidayCalendarsByStartYear(@WebParam(name = "year") Integer year, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForHolidayCalendarIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<HolidayCalendarInfo> searchForHolidayCalendars(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateHolidayCalendar(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "holidayCalendarTypeKey") String holidayCalendarTypeKey, @WebParam(name = "holidayCalendarInfo") HolidayCalendarInfo holidayCalendarInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HolidayCalendarInfo createHolidayCalendar(@WebParam(name = "holidayCalendarTypeKey") String holidayCalendarTypeKey, @WebParam(name = "holidayCalendarInfo") HolidayCalendarInfo holidayCalendarInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HolidayCalendarInfo copyHolidayCalendar(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HolidayCalendarInfo updateHolidayCalendar(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "holidayCalendarInfo") HolidayCalendarInfo holidayCalendarInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo changeHolidayCalendarState(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteHolidayCalendar(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TypeInfo getTermType(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getTermTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getTypesForGroupType(AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY, contextInfo);
    }

    @Override
    public List<TypeInfo> getTermTypesForAcademicCalendarType(@WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getTermTypesForTermType(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StateInfo getTermState(@WebParam(name = "termStateKey") String termStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StateInfo> getTermStates(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TermInfo getTerm(@WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TermInfo> getTermsByIds(@WebParam(name = "termIds") List<String> termIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getTermIdsByType(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TermInfo> getTermsByCode(@WebParam(name = "code") String code, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TermInfo> getTermsForAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TermInfo> getIncludedTermsInTerm(@WebParam(name = "parentTermId") String parentTermId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TermInfo> getContainingTerms(@WebParam(name = "childTermId") String childTermId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForTermIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TermInfo> searchForTerms(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AtpInfo> results = KsapFrameworkServiceLocator.getAtpService().searchForAtps(criteria, contextInfo);
        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());

        for (AtpInfo atp : results) {
            try {
                terms.add(termAssembler.assemble(atp, contextInfo));
            } catch (AssemblyException e) {
                throw new OperationFailedException("AssemblyException : " + e.getMessage());
            }
        }

        return terms;
    }

    @Override
    public List<ValidationResultInfo> validateTerm(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "termInfo") TermInfo termInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TermInfo createTerm(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "termInfo") TermInfo termInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TermInfo updateTerm(@WebParam(name = "termId") String termId, @WebParam(name = "termInfo") TermInfo termInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo changeTermState(@WebParam(name = "termId") String termId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteTerm(@WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
    @Override
    public StatusInfo addTermToAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo removeTermFromAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo addTermToTerm(@WebParam(name = "parentTermId") String parentTermId, @WebParam(name = "childTermId") String childTermId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo removeTermFromTerm(@WebParam(name = "parentTermId") String parentTermId, @WebParam(name = "childTermId") String childTermId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TypeInfo getKeyDateType(@WebParam(name = "keyDateTypeKey") String keyDateTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getKeyDateTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getKeyDateTypesForTermType(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StateInfo getKeyDateState(@WebParam(name = "keyDateStateKey") String keyDateStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StateInfo> getKeyDateStates(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public KeyDateInfo getKeyDate(@WebParam(name = "keyDateId") String keyDateId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<KeyDateInfo> getKeyDatesByIds(@WebParam(name = "keyDateIds") List<String> keyDateIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getKeyDateIdsByType(@WebParam(name = "keyDateTypeKey") String keyDateTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getKeyDateIdsByTypeForTerm(@WebParam(name = "keyDateTypeKey") String keyDateTypeKey, @WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTerm(@WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getKeyDateIdsForTerm(@WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTermByDate(@WebParam(name = "termId") String termId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<KeyDateInfo> getImpactedKeyDates(@WebParam(name = "keyDateId") String keyDateId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForKeyDateIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<KeyDateInfo> searchForKeyDates(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateKeyDate(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "termId") String termId, @WebParam(name = "keyDateTypeKey") String keyDateTypeKey, @WebParam(name = "keyDateInfo") KeyDateInfo keyDateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public KeyDateInfo createKeyDate(@WebParam(name = "termId") String termId, @WebParam(name = "keyDateTypeKey") String keyDateTypeKey, @WebParam(name = "keyDateInfo") KeyDateInfo keyDateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public KeyDateInfo updateKeyDate(@WebParam(name = "keyDateId") String keyDateId, @WebParam(name = "keyDateInfo") KeyDateInfo keyDateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo changeKeyDateState(@WebParam(name = "keyDateId") String keyDateId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteKeyDate(@WebParam(name = "keyDateId") String keyDateId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public KeyDateInfo calculateKeyDate(@WebParam(name = "keyDateId") String keyDateId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TypeInfo getAcalEventType(@WebParam(name = "acalEventTypeKey") String acalEventTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getAcalEventTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getAcalEventTypesForAcademicCalendarType(@WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StateInfo getAcalEventState(@WebParam(name = "acalEventStateKey") String acalEventStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StateInfo> getAcalEventStates(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AcalEventInfo getAcalEvent(@WebParam(name = "acalEventId") String acalEventId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AcalEventInfo> getAcalEventsByIds(@WebParam(name = "acalEventIds") List<String> acalEventIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getAcalEventIdsByType(@WebParam(name = "acalEventTypeKey") String acalEventTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AcalEventInfo> getAcalEventsForAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AcalEventInfo> getAcalEventsForAcademicCalendarByDate(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AcalEventInfo> getImpactedAcalEvents(@WebParam(name = "acalEventId") String acalEventId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForAcalEventIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AcalEventInfo> searchForAcalEvents(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateAcalEvent(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "termId") String termId, @WebParam(name = "acalEventTypeKey") String acalEventTypeKey, @WebParam(name = "acalEventInfo") AcalEventInfo acalEventInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AcalEventInfo createAcalEvent(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "acalEventTypeKey") String acalEventTypeKey, @WebParam(name = "acalEventInfo") AcalEventInfo acalEventInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AcalEventInfo updateAcalEvent(@WebParam(name = "acalEventId") String acalEventId, @WebParam(name = "acalEventInfo") AcalEventInfo acalEventInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo changeAcalEventState(@WebParam(name = "acalEventId") String acalEventId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteAcalEvent(@WebParam(name = "acalEventId") String acalEventId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AcalEventInfo calculateAcalEvent(@WebParam(name = "acalEventId") String acalEventId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TypeInfo getHolidayType(@WebParam(name = "holidayTypeKey") String holidayTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getHolidayTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getHolidayTypesForHolidayCalendarType(@WebParam(name = "holidayCalendarTypeKey") String holidayCalendarTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StateInfo getHolidayState(@WebParam(name = "holidayStateKey") String holidayStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StateInfo> getHolidayStates(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HolidayInfo getHoliday(@WebParam(name = "holidayId") String holidayId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<HolidayInfo> getHolidaysByIds(@WebParam(name = "holidayIds") List<String> holidayIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getHolidayIdsByType(@WebParam(name = "holidayTypeKey") String holidayTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<HolidayInfo> getHolidaysForHolidayCalendar(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<HolidayInfo> getHolidaysForHolidayCalendarByDate(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<HolidayInfo> getHolidaysByDateForAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<HolidayInfo> getImpactedHolidays(@WebParam(name = "holidayId") String holidayId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForHolidayIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<HolidayInfo> searchForHolidays(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateHoliday(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "holidayTypeKey") String holidayTypeKey, @WebParam(name = "holidayInfo") HolidayInfo holidayInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HolidayInfo createHoliday(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "holidayTypeKey") String holidayTypeKey, @WebParam(name = "holidayInfo") HolidayInfo holidayInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HolidayInfo updateHoliday(@WebParam(name = "holidayId") String holidayId, @WebParam(name = "holidayInfo") HolidayInfo holidayInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo changeHolidayState(@WebParam(name = "holidayId") String holidayId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteHoliday(@WebParam(name = "holidayId") String holidayId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HolidayInfo calculateHoliday(@WebParam(name = "holidayId") String holidayId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Integer getInstructionalDaysForTerm(@WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TermInfo> getCurrentTerms(@WebParam(name = "usageKey") String usageKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TypeInfo getExamPeriodType(@WebParam(name = "examPeriodTypeKey") String examPeriodTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getExamPeriodTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getExamPeriodTypesForTermType(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StateInfo getExamPeriodState(@WebParam(name = "examPeriodStateKey") String examPeriodStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StateInfo> getExamPeriodStates(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ExamPeriodInfo getExamPeriod(@WebParam(name = "examPeriodId") String examPeriodId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ExamPeriodInfo> getExamPeriodsByIds(@WebParam(name = "examPeriodIds") List<String> examPeriodIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getExamPeriodIdsByType(@WebParam(name = "examPeriodTypeKey") String examPeriodTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ExamPeriodInfo> getExamPeriodsByCode(@WebParam(name = "code") String code, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForExamPeriodIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ExamPeriodInfo> searchForExamPeriods(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateExamPeriod(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "examPeriodTypeKey") String examPeriodTypeKey, @WebParam(name = "examPeriodInfo") ExamPeriodInfo examPeriodInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ExamPeriodInfo createExamPeriod(@WebParam(name = "examPeriodTypeKey") String examPeriodTypeKey, @WebParam(name = "examPeriodInfo") ExamPeriodInfo examPeriodInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ExamPeriodInfo updateExamPeriod(@WebParam(name = "examPeriodId") String examPeriodId, @WebParam(name = "examPeriodInfo") ExamPeriodInfo examPeriodInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo changeExamPeriodState(@WebParam(name = "examPeriodId") String examPeriodId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteExamPeriod(@WebParam(name = "examPeriodId") String examPeriodId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo addExamPeriodToTerm(@WebParam(name = "termId") String termId, @WebParam(name = "examPeriodId") String examPeriodId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo removeExamPeriodFromTerm(@WebParam(name = "termId") String termId, @WebParam(name = "examPeriodId") String examPeriodId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ExamPeriodInfo> getExamPeriodsForTerm(@WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TermInfo> getTermsForExamPeriod(@WebParam(name = "examPeriodId") String examPeriodId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private List<TypeInfo> getTypesForGroupType(String groupTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException,
            OperationFailedException {
        List<TypeTypeRelationInfo> relations = null;

        try {
            relations = KsapFrameworkServiceLocator.getTypeService().getTypeTypeRelationsByOwnerAndType(groupTypeKey,
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
                    results.add(KsapFrameworkServiceLocator.getTypeService().getType(rel.getRelatedTypeKey(), contextInfo));
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
}
