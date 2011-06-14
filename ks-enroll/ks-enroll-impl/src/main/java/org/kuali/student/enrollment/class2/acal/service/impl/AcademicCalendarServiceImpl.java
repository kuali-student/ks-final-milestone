package org.kuali.student.enrollment.class2.acal.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.CampusCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.RegistrationDateGroupInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.service.assembler.AcademicCalendarAssembler;
import org.kuali.student.enrollment.class2.acal.service.assembler.TermAssembler;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.CriteriaInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class AcademicCalendarServiceImpl implements AcademicCalendarService{
    private AtpService atpService;
    private AcademicCalendarAssembler acalAssembler;
    private TermAssembler termAssembler;
    
    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException,
            MissingParameterException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }
 
    @Override
    public TypeInfo getAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getAcademicCalendarTypes(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getAcademicCalendarState(String academicCalendarStateKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getAcademicCalendarStates(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, DoesNotExistException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public AcademicCalendarInfo getAcademicCalendar(String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        AtpInfo atp = atpService.getAtp(academicCalendarKey, context);

        return acalAssembler.assemble(atp, context);
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByKeyList(List<String> academicCalendarKeyList,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getAcademicCalendarKeysByType(String academicCalendarTypeKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByYear(Integer year, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramType(String credentialProgramTypeKey,
            ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramTypeForYear(
            String credentialProgramTypeKey, Integer year, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateAcademicCalendar(String validationType,
            AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional(readOnly=false)
    public AcademicCalendarInfo createAcademicCalendar(String academicCalendarKey,
            AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        AtpInfo atp = acalAssembler.disassemble(academicCalendarInfo, context);
        try {
            AtpInfo existing = atpService.getAtp(academicCalendarKey, context);
            if(existing == null) {
                atpService.createAtp(academicCalendarKey, atp, context);
            } else { 
                throw new AlreadyExistsException("Academic calendar with id = " + academicCalendarKey + " already exists");
            }
        } catch (DoesNotExistException e1) {
            atpService.createAtp(academicCalendarKey, atp, context);
        }
       
        return academicCalendarInfo;
    }

    @Override
    @Transactional(readOnly=false)
    public AcademicCalendarInfo updateAcademicCalendar(String academicCalendarKey,
            AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        
        try{
        	AtpInfo existing = atpService.getAtp(academicCalendarKey, context);
        	if(existing != null){
	            AtpInfo atp = acalAssembler.disassemble(academicCalendarInfo, context);
	
	            if(atp != null)
	                atpService.updateAtp(academicCalendarKey, atp, context);
        	}
        	else
        		throw new DoesNotExistException("The AcademicCalendar does not exist: " + academicCalendarKey);
        } catch (DoesNotExistException e1) {
            throw new DoesNotExistException("The AcademicCalendar does not exist: " + academicCalendarKey);
        }
        
        return academicCalendarInfo;
    }

    @Override
    @Transactional(readOnly=false)
    public StatusInfo deleteAcademicCalendar(String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        
         AtpInfo atp =  atpService.getAtp(academicCalendarKey, context);
            
           if(atp != null){
               //delete atp/acal
               atpService.deleteAtp(academicCalendarKey, context);         
           }
        
        return status;
    }

    @Override
    public AcademicCalendarInfo copyAcademicCalendar(String academicCalendarKey, String newAcademicCalendarKey,
            ContextInfo context) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getAcademicCalendarData(String academicCalendarKey, String calendarDataFormatTypeKey,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getCampusCalendarType(String campusCalendarTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getCampusCalendarTypes(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getCampusCalendarState(String campusCalendarStateKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getCampusCalendarStates(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, DoesNotExistException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public CampusCalendarInfo getCampusCalendar(String campusCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CampusCalendarInfo> getCampusCalendarsByKeyList(List<String> campusCalendarKeyList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getCampusCalendarKeysByType(String campusCalendarTypeKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CampusCalendarInfo> getCampusCalendarsByYear(Integer year, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateCampusCalendar(String validationType,
            CampusCalendarInfo campusCalendarInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public CampusCalendarInfo createCampusCalendar(String campusCalendarKey, CampusCalendarInfo campusCalendarInfo,
            ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public CampusCalendarInfo updateCampusCalendar(String campusCalendarKey, CampusCalendarInfo campusCalendarInfo,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteCampusCalendar(String campusCalendarKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getTermType(String termTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        TypeInfo type = atpService.getType(termTypeKey, context);
        
        if(!checkTypeForTermType(termTypeKey, context)) {
            throw new InvalidParameterException(termTypeKey + " is not a Term type");
        }
        
        return type;
    }

    @Override
    public List<TypeInfo> getTermTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        
        List<TypeTypeRelationInfo> relations = null;
        
        try {
            relations = atpService.getTypeRelationsByOwnerType(AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY, TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, context);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(e.getMessage(), e);
        }
        
        if(relations != null) {
            List<TypeInfo> results = new ArrayList<TypeInfo>(relations.size());
            for(TypeTypeRelationInfo rel : relations) {
                try {
                    results.add(atpService.getType(rel.getRelatedTypeKey(), context));
                } catch (DoesNotExistException e) {
                    throw new OperationFailedException(e.getMessage(), e);
                }
            }
            
            return results;
        }
        
        return null;
    }

    @Override
    public List<TypeInfo> getTermTypesForAcademicCalendarType(String academicCalendarTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        
        TypeInfo acalType = atpService.getType(academicCalendarTypeKey, context);
        
    	return atpService.getAllowedTypesForType(acalType.getKey(), AtpServiceConstants.REF_OBJECT_URI_ATP, context);
    }

    @Override
    public List<TypeInfo> getTermTypesForTermType(String termTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        TypeInfo termType = getTermType(termTypeKey, context);
        
        return atpService.getAllowedTypesForType(termType.getKey(), AtpServiceConstants.REF_OBJECT_URI_ATP, context);
    }

    @Override
    public StateInfo getTermState(String termStateKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        
        StateInfo termState = atpService.getState(AtpServiceConstants.ATP_PROCESS_KEY, termStateKey, context);
        
        return termState;
    }

    @Override
    public List<StateInfo> getTermStates(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, DoesNotExistException {
        
        List<StateInfo> results = atpService.getStatesByProcess(AtpServiceConstants.ATP_PROCESS_KEY, context);
        
        return results;
    }

    @Override
    public TermInfo getTerm(String termKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpInfo atp = atpService.getAtp(termKey, context);
        TermInfo term = null;
        
        if(atp != null && checkTypeForTermType(atp.getTypeKey(), context))
        	term = termAssembler.assemble(atp, context);
        else
        	throw new DoesNotExistException("This is either not valid Atp or not valid Term. " + termKey);
        
        return term;
    }

    @Override
    public List<TermInfo> getTermsByKeyList(List<String> termKeyList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        List<AtpInfo> results = atpService.getAtpsByKeyList(termKeyList, context);
        
        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());
        
        for(AtpInfo atp : results) {
            terms.add(termAssembler.assemble(atp, context));
        }
        
        return terms;
    }

    @Override
    public List<String> getTermKeysByType(String termTypeKey, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        return atpService.getAtpKeysByType(termTypeKey, context);
    }

    @Override
    public List<TermInfo> getTermsForAcademicCalendar(String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByAtpAndRelationType(academicCalendarKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        
        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());
        
        for(AtpAtpRelationInfo atpRelation : results) {
            if(atpRelation.getAtpKey().equals(academicCalendarKey)) {
                AtpInfo possibleTerm = atpService.getAtp(atpRelation.getRelatedAtpKey(), context);
                
                if(checkTypeForTermType(possibleTerm.getTypeKey(), context)) {
                    terms.add(termAssembler.assemble(possibleTerm, context));
                }
            }
            
        }
        
        return terms;
    }
    
    @Override
    public List<TermInfo> getIncludedTermsInTerm(String termKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        // check for a valid term
        TermInfo parentTerm = getTerm(termKey, context);
        
        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByAtpAndRelationType(parentTerm.getKey(), AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        
        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());
        
        for(AtpAtpRelationInfo atpRelation : results) {
            if(atpRelation.getAtpKey().equals(termKey)) {
                AtpInfo possibleTerm = atpService.getAtp(atpRelation.getRelatedAtpKey(), context);
                
                if(checkTypeForTermType(possibleTerm.getTypeKey(), context)) {
                    terms.add(termAssembler.assemble(possibleTerm, context));
                }
            }
            
        }
        
        return terms;
    }

    @Override
    public List<TermInfo> getContainingTerms(String termKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        // check for a valid term
        TermInfo term = getTerm(termKey, context);
        
        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByAtp(term.getKey(), context);
        
        List<TermInfo> terms = new ArrayList<TermInfo>(results.size());
        
        // check that the relations we found have the given termKey as the "related" atp, and that the owning atp is a term
        for(AtpAtpRelationInfo atpRelation : results) {
            if(atpRelation.getRelatedAtpKey().equals(termKey)) {
                AtpInfo possibleTerm = atpService.getAtp(atpRelation.getAtpKey(), context);
                
                if(checkTypeForTermType(possibleTerm.getTypeKey(), context)) {
                    terms.add(termAssembler.assemble(possibleTerm, context));
                }
            }
            
        }
        
        return terms;
    }

    @Override
    public List<ValidationResultInfo> validateTerm(String validationType, TermInfo termInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional(readOnly=false)
    public TermInfo createTerm(String termKey, TermInfo termInfo, ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        AtpInfo atp;
        
        if(checkTypeForTermType(termInfo.getTypeKey(), context)){
	        atp = termAssembler.disassemble(termInfo, context);
	        try {
	                AtpInfo existing = atpService.getAtp(termKey, context);
	                if(existing == null)
	                    atpService.createAtp(termKey, atp, context);
					else
						throw new AlreadyExistsException("Term with id = " + termKey + " already exists");
	        } catch (DoesNotExistException e1) {
	            atpService.createAtp(termKey, atp, context);
	        }
        }
        else
        	throw new InvalidParameterException("Term with id = " + termKey + " has invalid term type: " + termInfo.getTypeKey());
        
        return termInfo;
    }

    @Override
    @Transactional(readOnly=false)
    public TermInfo updateTerm(String termKey, TermInfo termInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        
        AtpInfo atp = atpService.getAtp(termKey, context);
        
        if(atp == null) {
            throw new DoesNotExistException(termKey);
        }
        
        if(!checkTypeForTermType(atp.getTypeKey(), context)) {
            throw new InvalidParameterException("Invalid termKey: " + termKey + "  Given key does not map to a Term");
        }
        
        AtpInfo toUpdate = termAssembler.disassemble(termInfo, context);
        
        AtpInfo updated = atpService.updateAtp(termKey, toUpdate, context);
        
        TermInfo updatedTerm = termAssembler.assemble(updated, context);
        
        return updatedTerm;
    }

    @Override
    @Transactional(readOnly=false)
    public StatusInfo deleteTerm(String termKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        AtpInfo atp = atpService.getAtp(termKey, context);
        
        if(atp == null) {
            throw new DoesNotExistException(termKey);
        }
        
        if(!checkTypeForTermType(atp.getTypeKey(), context)) {
            throw new InvalidParameterException("Invalid termKey: " + termKey + "  Given key does not map to a Term");
        }
        
        StatusInfo result = atpService.deleteAtp(termKey, context);
        
        return result;
    }

    @Override
    public StatusInfo addTermToAcademicCalendar(String academicCalendarKey, String termKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, AlreadyExistsException {
    	StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
       
        AtpInfo acal = atpService.getAtp(academicCalendarKey, context);
        if(acal != null){
	        if(isAcademicCalendar(acal.getTypeKey())){
	        	AtpInfo term = atpService.getAtp(termKey, context);
	        	if(term != null){
	        		if(checkTypeForTermType(term.getTypeKey(), context)){
	        			if(!termAlreadyExists(academicCalendarKey, termKey, context)){
			        		try {
			        			acalAssembler.getRelAssembler().createAtpAtpRelations(academicCalendarKey, termKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
					          } catch (DataValidationErrorException e) {
					              status.setSuccess(Boolean.FALSE);     
					          }
	        			}
	        			else
	        				throw new AlreadyExistsException("Term with id = " + termKey + " already exists.");
	        		}
	        		else
	        			throw new InvalidParameterException("Term with id = " + termKey + " has invalid type: " + term.getTypeKey());
	        	}
	        	else
	        		throw new DoesNotExistException("Term with id = " + termKey + " does not exist.");
	        }
	        else 
	        	throw new InvalidParameterException("AcademicCalendar with id = " + academicCalendarKey + " has invalid type: " + acal.getTypeKey());
    	}
        else
        	throw new DoesNotExistException("AcademicCalendar with id = " + academicCalendarKey + " does not exist.");
        
        return status;
    }

    private boolean isAcademicCalendar(String acalType){
    	return null != acalType ? acalType.equals(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY) : false;
    }
    
    private boolean termAlreadyExists(String atpKey, String termKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
    	boolean found = false;
    	List<AtpAtpRelationInfo > atpRels = atpService.getAtpAtpRelationsByAtpAndRelationType(atpKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);

		if(atpRels != null && !atpRels.isEmpty()){
			for(AtpAtpRelationInfo atpRelInfo : atpRels){
				if(atpRelInfo.getAtpKey().equals(atpKey)){
					if(atpRelInfo.getRelatedAtpKey().equals(termKey)){
					 found = true;
					 break;
					}
				}
			}
		}
		
		return found;
}
    
    @Override
    @Transactional
    public StatusInfo removeTermFromAcademicCalendar(String academicCalendarKey, String termKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        AtpInfo acal = atpService.getAtp(academicCalendarKey, context);
        
        if(acal == null) {
            throw new InvalidParameterException("Invalid academicCalendarKey: " + academicCalendarKey);
        }
        
        AtpInfo term = atpService.getAtp(termKey, context);
        
        if(term == null) {
            throw new InvalidParameterException("Invalid termKey: " + termKey);
        }
        
        List<AtpAtpRelationInfo> relations = atpService.getAtpAtpRelationsByAtpAndRelationType(academicCalendarKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        if(relations == null || relations.isEmpty()) {
            throw new DoesNotExistException("No relationship exists between academic calendar: " + academicCalendarKey + " and term: " + termKey);
        }
        
        AtpAtpRelationInfo relationToRemove = null;
        
        for(AtpAtpRelationInfo rel : relations) {
            if(rel.getAtpKey().equals(academicCalendarKey)) {
                if(rel.getRelatedAtpKey().equals(termKey)) {
                    // if the relation represents an "includes" relationship from the AcademicCalendar to the Term,
                    // then it is the one we need to remove
                    relationToRemove = rel;
                    break;
                }
            }
        }
        
        if(relationToRemove == null) {
            throw new DoesNotExistException("No relationship exists between academic calendar: " + academicCalendarKey + " and term: " + termKey);
        }
        
        StatusInfo resultStatus = atpService.deleteAtpAtpRelation(relationToRemove.getId(), context);
        
        return resultStatus;
    }

    @Override
    public StatusInfo addTermToTerm(String termKey, String includedTermKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional
    public StatusInfo removeTermFromTerm(String termKey, String includedTermKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        try {
            atpService.getAtp(termKey, context);
        }
        catch(DoesNotExistException e) {
            throw new InvalidParameterException("Invalid termKey: " + termKey);
        }
        
        try {
            atpService.getAtp(includedTermKey, context);
        }
        catch(DoesNotExistException e) {
            throw new InvalidParameterException("Invalid includedTermKey: " + includedTermKey);
        }
        
        List<AtpAtpRelationInfo> relations = atpService.getAtpAtpRelationsByAtpAndRelationType(termKey, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        if(relations == null || relations.isEmpty()) {
            throw new DoesNotExistException("No relationship exists between term: " + termKey + " and included term: " + includedTermKey);
        }
        
        AtpAtpRelationInfo relationToRemove = null;
        
        for(AtpAtpRelationInfo rel : relations) {
            if(rel.getAtpKey().equals(termKey)) {
                if(rel.getRelatedAtpKey().equals(includedTermKey)) {
                    // if the relation represents an "includes" relationship from the Term to the included Term,
                    // then it is the one we need to remove
                    relationToRemove = rel;
                    break;
                }
            }
        }
        
        if(relationToRemove == null) {
            throw new DoesNotExistException("No relationship exists between term: " + termKey + " and included term: " + includedTermKey);
        }
        
        StatusInfo resultStatus = atpService.deleteAtpAtpRelation(relationToRemove.getId(), context);
        
        return resultStatus;
    }

    @Override
    public TypeInfo getKeyDateType(String keyDateTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getKeyDateTypesForTermType(String termTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public KeyDateInfo getKeyDate(String keyDateKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesByKeyList(List<String> keyDateKeyList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getKeyDateKeysByType(String keyDateTypeKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAcademicCalendar(String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAcademicCalendarByDate(String academicCalendarKey, Date startDate,
            Date endDate, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTerm(String termKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        AtpInfo termAtp = atpService.getAtp(termKey, context);
        if(termAtp == null) {
            throw new DoesNotExistException(termKey);
        }
        
        List<MilestoneInfo> milestones = atpService.getMilestonesByAtp(termKey, context);
        
        if(milestones == null || milestones.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>(milestones.size());
        
        for(MilestoneInfo milestone : milestones) {
            keyDates.add(new KeyDateInfo(milestone));
        }
        
        return keyDates;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForTermByDate(String termKey, Date startDate, Date endDate, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<KeyDateInfo> getAllKeyDatesForTerm(String termKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<KeyDateInfo> getKeyDatesForAllTermsByDate(String termKey, Date startDate, Date endDate,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateKeyDate(String validationType, KeyDateInfo keyDateInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public KeyDateInfo createKeyDateForTerm(String termKey, String keyDateKey, KeyDateInfo keyDateInfo,
            ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public KeyDateInfo updateKeyDate(String keyDateKey, KeyDateInfo keyDateInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteKeyDate(String keyDateKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getHolidayType(String holidayTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getHolidayTypesForCampusCalendarType(String campusCalendarTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HolidayInfo> getHolidaysForAcademicCalendar(String academicCalendarKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateHoliday(String validationType, HolidayInfo holidayInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public HolidayInfo createHolidayForCampusCalendar(String campusCalendarKey, String holidayKey,
            HolidayInfo holidayInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public HolidayInfo updateHoliday(String holidayKey, HolidayInfo holidayInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteHoliday(String holidayKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RegistrationDateGroupInfo getRegistrationDateGroup(String termKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationDateGroup(String validationType,
            RegistrationDateGroupInfo registrationDateGroupInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RegistrationDateGroupInfo updateRegistrationDateGroup(String termKey,
            RegistrationDateGroupInfo registrationDateGroupInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Integer getInstructionalDaysForTerm(String termKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Li Pan - THIS METHOD NEEDS JAVADOCS
        return null;
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
    public List<AcademicCalendarInfo> searchForAcademicCalendars(CriteriaInfo criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForAcademicCalendarIds(CriteriaInfo criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CampusCalendarInfo> searchForCampusCalendars(CriteriaInfo criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForCampusCalendarIds(CriteriaInfo criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForTermIds(CriteriaInfo criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TermInfo> searchForTerms(CriteriaInfo criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<KeyDateInfo> searchForKeyDates(CriteriaInfo criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForKeyDateIds(CriteriaInfo criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    private boolean checkTypeForTermType(String typeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<TypeInfo> types = getTermTypes(context);
        
        for(TypeInfo type : types) {
            if(type.getKey().equals(typeKey)) {
                return true;
            }
        }
        
        return false;
    }
    
}
