package org.kuali.student.poc.learningunit.luipersonrelation.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;

import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.poc.common.ws.exceptions.ReadOnlyException;
import org.kuali.student.poc.learningunit.luipersonrelation.dao.LuiPersonRelationDAO;
import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelation;
import org.kuali.student.poc.wsdl.learningunit.lu.LuService;
import org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService;
import org.kuali.student.poc.wsdl.personidentity.person.PersonService;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.Status;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationCreateInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationCriteria;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationDisplay;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.ValidationResult;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDisplay;
import org.kuali.student.rules.brms.agenda.AgendaDiscovery;
import org.kuali.student.rules.brms.agenda.AgendaRequest;
import org.kuali.student.rules.brms.agenda.entity.Agenda;
import org.kuali.student.rules.brms.agenda.entity.Anchor;
import org.kuali.student.rules.brms.agenda.entity.AnchorType;
import org.kuali.student.rules.brms.agenda.entity.BusinessRule;
import org.kuali.student.rules.brms.core.dao.FunctionalBusinessRuleDAO;
import org.kuali.student.rules.brms.core.service.FunctionalBusinessRuleManagementService;
import org.kuali.student.rules.brms.repository.RuleEngineRepository;
import org.kuali.student.rules.brms.util.POC2ADroolsLoader;
import org.kuali.student.rules.common.util.CourseEnrollmentRequest;
import org.kuali.student.rules.ruleexecution.RuleSetExecutor;
import org.kuali.student.rules.ruleexecution.drools.RuleSetExecutorDroolsImpl;
import org.kuali.student.rules.runtime.ast.GenerateRuleReport;
import org.kuali.student.rules.statement.PropositionContainer;
import org.kuali.student.rules.util.FactContainer;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService", serviceName = "LuiPersonRelationService", portName = "LuiPersonRelationService", targetNamespace = "http://student.kuali.org/poc/wsdl/learningunit/luipersonrelation")
@Transactional
public class LuiPersonRelationServiceImpl implements LuiPersonRelationService {

    // POC Hack, drools initialization will happen in BRMS in actual implementation
    private static Boolean DROOLS_INIT = false;

	private LuiPersonRelationDAO dao;
	private PersonService personClient;
	private LuService luClient;

    private FunctionalBusinessRuleDAO businessRuleDAO;
    private RuleEngineRepository droolsRepository;

	public LuiPersonRelationDAO getDao() {
		return dao;
	}

	public void setDao(LuiPersonRelationDAO dao) {
		this.dao = dao;
	}

	public PersonService getPersonClient() {
		return personClient;
	}

	public void setPersonClient(PersonService personClient) {
		this.personClient = personClient;
	}

	public LuService getLuClient() {
		return luClient;
	}

	public void setLuClient(LuService luClient) {
		this.luClient = luClient;
	}

    public RuleEngineRepository getDroolsRepository() {
        return droolsRepository;
    }

    public void setDroolsRepository(RuleEngineRepository droolsRepository) {
        this.droolsRepository = droolsRepository;
    }

    public FunctionalBusinessRuleDAO getBusinessRuleDAO() {
        return businessRuleDAO;
    }

    public void setBusinessRuleDAO(FunctionalBusinessRuleDAO businessRuleDAO) {
        this.businessRuleDAO = businessRuleDAO;
    }


	public List<String> createBulkRelationshipsForLui(String luiId,
			List<String> personIdList, RelationStateInfo relationStateInfo,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			LuiPersonRelationCreateInfo luiPersonRelationCreateInfo)
			throws AlreadyExistsException, DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<String> ids = new ArrayList<String>();
		for (String personId : personIdList) {
			ids.add(createLuiPersonRelation(personId, luiId, relationStateInfo,
					luiPersonRelationTypeInfo, luiPersonRelationCreateInfo));
		}
		return ids;
	}


	public List<String> createBulkRelationshipsForPerson(String personId,
			List<String> luiIdList, RelationStateInfo relationStateInfo,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			LuiPersonRelationCreateInfo luiPersonRelationCreateInfo)
			throws AlreadyExistsException, DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// I wonder if error handling should be somewhat different somehow, but
		// I doubt it
		List<String> ids = new ArrayList<String>();
		for (String luiId : luiIdList) {
			ids.add(createLuiPersonRelation(personId, luiId, relationStateInfo,
					luiPersonRelationTypeInfo, luiPersonRelationCreateInfo));
		}
		return ids;
	}


	public String createLuiPersonRelation(String personId, String luiId,
			RelationStateInfo relationStateInfo,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			LuiPersonRelationCreateInfo luiPersonRelationCreateInfo)
			throws AlreadyExistsException, DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		String id = null;

		if (validateLuiPersonRelation(personId, luiId,
				luiPersonRelationTypeInfo, relationStateInfo).isSuccess()
				&& isValidLuiPersonRelation(personId, luiId,
						luiPersonRelationTypeInfo, relationStateInfo)) {
			LuiPersonRelation luiPersonRelation = new LuiPersonRelation();
			luiPersonRelation.setPersonId(personId);
			luiPersonRelation.setLuiId(luiId);
			luiPersonRelation.setRelationState(relationStateInfo.getState());
			luiPersonRelation
					.setLuiPersonRelationType(luiPersonRelationTypeInfo
							.getName());
			luiPersonRelation.setEffectiveEndDate(luiPersonRelationCreateInfo
					.getEffectiveEndDate());
			luiPersonRelation.setEffectiveStartDate(luiPersonRelationCreateInfo
					.getEffectiveStartDate());
			id = dao.createLuiPersonRelation(luiPersonRelation).getId();
		}

		return id;
	}


	public Status deleteLuiPersonRelation(String luiPersonRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		boolean success = dao.deleteLuiPersonRelation(luiPersonRelationId);

		// this is of course stupid since if there is no success, an error gets
		// thrown
		Status status = new Status();
		status.setSuccess(success);
		return status;
	}


	public LuiPersonRelationInfo fetchLUIPersonRelation(
			String luiPersonRelationId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		LuiPersonRelation luiPersonRelation = dao
				.lookupLuiPersonRelation(luiPersonRelationId);
		
		if (luiPersonRelation == null){
		    throw new DoesNotExistException();
		}
		
		return toLuiPersonRelationInfo(luiPersonRelation);
	}


	public List<String> findAllValidLuiIdsForPerson(String personId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo, String atpId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Do we lookup lui to filter on atpId
		return null;
	}


	public List<LuiDisplay> findAllValidLuisForPerson(String personId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo, String atpId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Do we lookup lui to filter on atpId
		return null;
	}


	public List<PersonDisplay> findAllValidPeopleForLui(String luiId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		
	    List<String> personIds = findAllValidPersonIdsForLui(luiId,
				luiPersonRelationTypeInfo, relationStateInfo);

		return personClient.findPeopleDisplayByPersonIds(personIds);
	}


	public List<String> findAllValidPersonIdsForLui(String luiId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		List<LuiPersonRelation> luiPersonRelations = dao
				.findLuiPersonRelationsByLui(luiId, luiPersonRelationTypeInfo
						.getName(), relationStateInfo.getState());

		List<String> personIdList = new ArrayList<String>();
		for (LuiPersonRelation lpr : luiPersonRelations) {
			personIdList.add(lpr.getPersonId());
		}

		return personIdList;
	}


	public List<RelationStateInfo> findAllowedRelationStates(
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo)
			throws OperationFailedException, DoesNotExistException,
			InvalidParameterException, MissingParameterException {

		throw new UnsupportedOperationException();
	}


	public List<String> findLuiIdsRelatedToPerson(String personId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		List<LuiPersonRelation> luiPersonRelations = dao
				.findLuiPersonRelationsByPerson(personId,
						luiPersonRelationTypeInfo.getName(), relationStateInfo
								.getState());

		List<String> luiIdList = new ArrayList<String>();

		for (LuiPersonRelation lpr : luiPersonRelations) {
			luiIdList.add(lpr.getLuiId());
		}

		return luiIdList;
	}


	public List<String> findLuiPersonRelationIds(String personId, String luiId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<LuiPersonRelation> luiPersonRelations = dao
				.findLuiPersonRelations(personId, luiId, "%", "%");

		List<String> lprIdList = new ArrayList<String>();

		for (LuiPersonRelation lpr : luiPersonRelations) {
			lprIdList.add(lpr.getId());
		}

		return lprIdList;
	}


	public List<String> findLuiPersonRelationIdsForLui(String luiId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<LuiPersonRelation> luiPersonRelations = dao
				.findLuiPersonRelationsByLui(luiId, "%", "%");

		List<String> luiPersonRelationIdList = new ArrayList<String>();
		for (LuiPersonRelation lpr : luiPersonRelations) {
			luiPersonRelationIdList.add(lpr.getId());
		}

		return luiPersonRelationIdList;
	}


	public List<String> findLuiPersonRelationIdsForPerson(String personId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<LuiPersonRelation> luiPersonRelations = dao
				.findLuiPersonRelationsByPerson(personId, "%", "%");

		List<String> luiPersonRelationIdList = new ArrayList<String>();

		for (LuiPersonRelation lpr : luiPersonRelations) {
			luiPersonRelationIdList.add(lpr.getId());
		}

		return luiPersonRelationIdList;
	}


	public List<LuiPersonRelationTypeInfo> findLuiPersonRelationTypes()
			throws OperationFailedException {
		// TODO Make up relation types to send back
		return null;
	}


	public List<LuiPersonRelationTypeInfo> findLuiPersonRelationTypesForLuiPersonRelation(
			String personId, String luiId, RelationStateInfo relationStateInfo)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<LuiPersonRelation> luiPersonRelations = dao
				.findLuiPersonRelations(personId, luiId, "%", relationStateInfo
						.getState());

		List<LuiPersonRelationTypeInfo> lprTypeInfoList = new ArrayList<LuiPersonRelationTypeInfo>();
		for (LuiPersonRelation lpr : luiPersonRelations) {
			LuiPersonRelationTypeInfo lprTypeInfo = new LuiPersonRelationTypeInfo();
			lprTypeInfo.setName(lpr.getLuiPersonRelationType());
			lprTypeInfoList.add(lprTypeInfo);
		}

		return lprTypeInfoList;
	}


	public List<LuiPersonRelationDisplay> findLuiPersonRelations(
			String personId, String luiId) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<LuiPersonRelation> luiPersonRelations = dao
				.findLuiPersonRelations(personId, luiId, "%", "%");

		return toLuiPersonRelationDisplayList(luiPersonRelations);
	}


	public List<LuiPersonRelationDisplay> findLuiPersonRelationsForLui(
			String luiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<LuiPersonRelation> luiPersonRelations = dao
				.findLuiPersonRelationsByLui(luiId, "%", "%");

		return toLuiPersonRelationDisplayList(luiPersonRelations);
	}


	public List<LuiPersonRelationDisplay> findLuiPersonRelationsForPerson(
			String personId) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<LuiPersonRelation> luiPersonRelations = dao
				.findLuiPersonRelationsByPerson(personId, "%", "%");

		return toLuiPersonRelationDisplayList(luiPersonRelations);
	}


	public List<LuiPersonRelationDisplay> findOrderedRelationStatesForLuiPersonRelation(
			String luiPersonRelationId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<String> findPersonIdsRelatedToLui(String luiId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<LuiPersonRelation> luiPersonRelations = dao
				.findLuiPersonRelationsByLui(luiId, luiPersonRelationTypeInfo
						.getName(), relationStateInfo.getState());

		List<String> personIdList = new ArrayList<String>();
		for (LuiPersonRelation lpr : luiPersonRelations) {
			personIdList.add(lpr.getPersonId());
		}

		return personIdList;
	}


	public List<RelationStateInfo> findRelationStates()
			throws OperationFailedException {
		// TODO What are all the relation states?
	    List <RelationStateInfo> rsList = new ArrayList<RelationStateInfo>();
	    
	    RelationStateInfo rs = new RelationStateInfo();
	    rs.setState("add");
	    rsList.add(rs);
	    
	    rs= new RelationStateInfo();
	    rs.setState("drop");
	    rsList.add(rs);
	    
		return rsList;
	}


	public List<RelationStateInfo> findValidRelationStatesForLuiPersonRelation(
			String personId, String luiId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO What are all the valid relation states?
		throw new UnsupportedOperationException();
	}


	public boolean isRelated(String personId, String luiId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<LuiPersonRelation> luiPersonRelations = dao
				.findLuiPersonRelations(personId, luiId,
						luiPersonRelationTypeInfo.getName(), relationStateInfo
								.getState());

		return (luiPersonRelations != null && luiPersonRelations.size() > 0);
	}


	public boolean isValidLuiPersonRelation(String personId, String luiId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

	    //TODO: So far this only checks if seats full.
	    
	    //Get max seat count from lui service
	    LuiInfo lui = luClient.fetchLui(luiId);
	    
	    //Get total registered for course (#of "student" relation types having relation state "add") 
	    List<LuiPersonRelation> lprList = 
	        dao.findLuiPersonRelationsByLui(luiId, luiPersonRelationTypeInfo.getName(), relationStateInfo.getState());

		return (lprList.size() < lui.getMaxSeats());
	}


	public List<String> searchForLuiPersonRelationIds(
			LuiPersonRelationCriteria luiPersonRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
	    return dao.searchForLuiPersonRelationIds(luiPersonRelationCriteria.getLuiId(), luiPersonRelationCriteria.getPersonId(), luiPersonRelationCriteria.getLuiPersonRelationType().getName(), luiPersonRelationCriteria.getRelationState().getState(), luiPersonRelationCriteria.getEffectiveStartDate(), luiPersonRelationCriteria.getEffectiveEndDate());
	}


	public List<LuiPersonRelationDisplay> searchForLuiPersonRelations(
			LuiPersonRelationCriteria luiPersonRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
	    List<LuiPersonRelation> luiPersonRelations = dao.searchForLuiPersonRelations(luiPersonRelationCriteria.getLuiId(), luiPersonRelationCriteria.getPersonId(), luiPersonRelationCriteria.getLuiPersonRelationType().getName(), luiPersonRelationCriteria.getRelationState().getState(), luiPersonRelationCriteria.getEffectiveStartDate(), luiPersonRelationCriteria.getEffectiveEndDate());
	    return toLuiPersonRelationDisplayList(luiPersonRelations);
	}


	public Status updateLuiPersonRelation(String luiPersonRelationId,
			LuiPersonRelationUpdateInfo luiPersonRelationUpdateInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, ReadOnlyException,
			OperationFailedException, PermissionDeniedException {
		LuiPersonRelation luiPersonRelation = dao
				.lookupLuiPersonRelation(luiPersonRelationId);
		if (luiPersonRelation == null)
			throw new DoesNotExistException("No LUI Person Relation with id "
					+ luiPersonRelationId);
		luiPersonRelation.setEffectiveEndDate(luiPersonRelationUpdateInfo
				.getEffectiveEndDate());
		luiPersonRelation.setEffectiveStartDate(luiPersonRelationUpdateInfo
				.getEffectiveStartDate());
		luiPersonRelation.setLuiPersonRelationType(luiPersonRelationUpdateInfo
				.getLuiPersonRelationType().getName());
		luiPersonRelation.setRelationState(luiPersonRelationUpdateInfo
				.getRelationState().getState());
		dao.updateLuiPersonRelation(luiPersonRelation);

		Status status = new Status();
		status.setSuccess(true); // if no exceptions, then it must've worked
		return status;
	}


	public Status updateRelationState(String luiPersonRelationId,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		LuiPersonRelation luiPersonRelation = dao
				.lookupLuiPersonRelation(luiPersonRelationId);
		if (luiPersonRelation == null)
			throw new DoesNotExistException("No LUI Person Relation with id "
					+ luiPersonRelationId);
		luiPersonRelation.setRelationState(relationStateInfo.getState());
		dao.updateLuiPersonRelation(luiPersonRelation);

		Status status = new Status();
		status.setSuccess(true); // if no exceptions, then it must've worked
		return status;
	}


	public ValidationResult validateLuiPersonRelation(String personId,
			String luiId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Use "rules" for validation (eg. prereq checks).

        FunctionalBusinessRuleManagementService brmsService = new FunctionalBusinessRuleManagementService();
        brmsService.setBusinessRuleDAO(businessRuleDAO);

        AgendaDiscovery agendaDiscovery = new AgendaDiscovery();
        agendaDiscovery.setRuleMgmtService(brmsService);

        RuleSetExecutor rulesExecutor = new RuleSetExecutorDroolsImpl(droolsRepository);

        synchronized (DROOLS_INIT) {
            if (!DROOLS_INIT) {
                POC2ADroolsLoader droolsLoader = new POC2ADroolsLoader();
                droolsLoader.setBrmsService(brmsService);
                droolsLoader.setDroolsRepository(droolsRepository);
                try {
                    droolsLoader.init();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new OperationFailedException("Could not initialize drools repository!");
                }

                DROOLS_INIT = true;
            }
        }

        /* Current Hack is to return true unless the rule explicitly says no */
        ValidationResult validationResult = new ValidationResult();
        validationResult.setSuccess(true);
        
        GenerateRuleReport ruleReportBuilder = new GenerateRuleReport();

        // 1. Discover Agenda

        AgendaRequest agendaRequest = new AgendaRequest(luiPersonRelationTypeInfo.getName(), "course", "offered", relationStateInfo.getState());
        AnchorType type = new AnchorType("course", "clu.type.course");
        Anchor anchor = new Anchor(luiId.replaceAll("\\s", "_"), luiId, type);

        Agenda agenda = agendaDiscovery.getAgenda(agendaRequest, anchor);

        Iterator<BusinessRule> itr = agenda.getBusinessRules().iterator();

        while (itr.hasNext()) {
            BusinessRule rule = itr.next();

            PropositionContainer props = new PropositionContainer();
            props.setFunctionalRuleString(rule.getFunctionalRuleString());
            CourseEnrollmentRequest request = new CourseEnrollmentRequest();
            FactContainer factContainer = new FactContainer(rule.getBusinessRuleName(), request, props);
            
            request.setLuiIds( new HashSet<String>( findLuiIdsRelatedToPerson(personId, luiPersonRelationTypeInfo,
             relationStateInfo) ) );

            List<Object> factList = new ArrayList<Object>();
            factList.add(props);
            factList.add(request);
            factList.add(factContainer);

            rulesExecutor.execute(agenda, factList);

            // 5. Process rule outcome
            ruleReportBuilder.executeRule(props);

            if (props.getRuleResult()) {
                validationResult.setSuccess(true);
                validationResult.setRuleMessage(props.getRuleReport().getSuccessMessage());
            } else {
                validationResult.setRuleMessage(props.getRuleReport().getFailureMessage());
            }

            // Dirty hack. In POC we only process one rule
            break;
        }

        return validationResult;
	}

	private LuiPersonRelationInfo toLuiPersonRelationInfo(
			LuiPersonRelation luiPersonRelation) throws OperationFailedException{
		LuiPersonRelationInfo luiPersonRelationInfo = new LuiPersonRelationInfo();

		luiPersonRelationInfo.setEffectiveEndDate(luiPersonRelation
				.getEffectiveEndDate());
		luiPersonRelationInfo.setEffectiveStartDate(luiPersonRelation
				.getEffectiveStartDate());

		luiPersonRelationInfo.setLuiPersonRelationId(luiPersonRelation.getId());
		
		LuiPersonRelationTypeInfo lprTypeInfo = new LuiPersonRelationTypeInfo();
		lprTypeInfo.setName(luiPersonRelation.getLuiPersonRelationType());
		luiPersonRelationInfo.setLuiPersonRelationType(lprTypeInfo);
		
		RelationStateInfo rsInfo = new RelationStateInfo();
		rsInfo.setState(luiPersonRelation.getRelationState());
		luiPersonRelationInfo.setRelationState(rsInfo);
		
        try{ 
            PersonDisplay personDisplay = personClient.fetchPersonDisplay(luiPersonRelation.getPersonId());
            luiPersonRelationInfo.setPersonDisplay(personDisplay);
        } catch (Exception e){
            throw new OperationFailedException("Could not fetch person display");
        }

        try {
            LuiDisplay luiDisplay = luClient.fetchLuiDisplay(luiPersonRelation.getLuiId());
            luiPersonRelationInfo.setLuiDisplay(luiDisplay);
        } catch (Exception e){
            throw new OperationFailedException("Could not fetch lui display");
        }

		return luiPersonRelationInfo;
	}

	private LuiPersonRelationDisplay toLuiPersonRelationDisplay(
			LuiPersonRelation lpr) 
	    throws OperationFailedException {
		LuiPersonRelationDisplay lprDisplay = new LuiPersonRelationDisplay();

		try{ 
    		PersonDisplay personDisplay = personClient.fetchPersonDisplay(lpr.getPersonId());
    		lprDisplay.setPersonDisplay(personDisplay);
		} catch (Exception e){
		    throw new OperationFailedException("Could not fetch person display");
		}

		try {
    		LuiDisplay luiDisplay = luClient.fetchLuiDisplay(lpr.getLuiId());
    		lprDisplay.setLuiDisplay(luiDisplay);
        } catch (Exception e){
            throw new OperationFailedException("Could not fetch lui display");
        }

		
		LuiPersonRelationTypeInfo lprTypeInfo = new LuiPersonRelationTypeInfo();
		lprTypeInfo.setName(lpr.getLuiPersonRelationType());
		lprDisplay.setLuiPersonRelationType(lprTypeInfo);

		lprDisplay.setLuiPersonRelationId(lpr.getId());

		RelationStateInfo relationStateInfo = new RelationStateInfo();
		relationStateInfo.setState(lpr.getRelationState());
		lprDisplay.setRelationState(relationStateInfo);

		return lprDisplay;
	}

	private List<LuiPersonRelationDisplay> toLuiPersonRelationDisplayList(
			List<LuiPersonRelation> lprs) throws OperationFailedException {
		List<LuiPersonRelationDisplay> lprDisplayList = new ArrayList<LuiPersonRelationDisplay>();
		for (LuiPersonRelation lpr : lprs) {
			lprDisplayList.add(toLuiPersonRelationDisplay(lpr));
		}

		return lprDisplayList;
	}


}
