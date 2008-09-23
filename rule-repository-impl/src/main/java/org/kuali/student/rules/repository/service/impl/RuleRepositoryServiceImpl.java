/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.repository.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.drools.repository.RulesRepository;
import org.kuali.student.rules.repository.RuleEngineRepository;
import org.kuali.student.rules.repository.drools.DefaultDroolsRepository;
import org.kuali.student.rules.repository.drools.RuleEngineRepositoryDroolsImpl;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.exceptions.CategoryExistsException;
import org.kuali.student.rules.repository.exceptions.RuleSetTranslatorException;
import org.kuali.student.rules.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.rules.repository.exceptions.RuleExistsException;
import org.kuali.student.rules.repository.exceptions.RuleSetExistsException;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.repository.service.RuleAdapter;
import org.kuali.student.rules.repository.service.RuleRepositoryService;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;
import org.kuali.student.rules.translators.RuleSetTranslator;
import org.springframework.transaction.annotation.Transactional;
/**
 * This is a convenience interface for the rules repository interface.
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 */
/**
 * @author lcarlsen
 *
 */
@WebService(endpointInterface = "org.kuali.student.rules.repository.service.RuleRepositoryService", 
			serviceName = "RuleRepositoryService", 
			portName = "RuleRepositoryService", 
			targetNamespace = "http://student.kuali.org/wsdl/brms/RuleRepository")
//@Transactional
//@ContextConfiguration(locations = {"classpath:rule-repository-context.xml"})
public class RuleRepositoryServiceImpl implements RuleRepositoryService {
    
	private final static RuleAdapter ruleAdapter = RuleAdapter.getInstance();
	
	/** Drools rule repository */
    //@Resource
    private RuleEngineRepository ruleEngineRepository;
    
    private RuleSetTranslator ruleSetTranslator;

    public RuleRepositoryServiceImpl() {
    	RulesRepository repository = new DefaultDroolsRepository("/drools-repository").getRepository();
        this.ruleEngineRepository = new RuleEngineRepositoryDroolsImpl(repository);
    }

    /**
     * Constructor
     * 
     * @param repository Drools rules repository
     */
    public RuleRepositoryServiceImpl(final RuleEngineRepository repository) {
        this.ruleEngineRepository = repository;
    }

    /**
     * 
     * @return
     */
	public RuleSetTranslator getRuleSetTranslator() {
		return ruleSetTranslator;
	}

	/**
	 * 
	 * @param ruleSetTranslator
	 */
	public void setRuleSetTranslator(RuleSetTranslator ruleSetTranslator) {
		this.ruleSetTranslator = ruleSetTranslator;
	}

	//TODO - Remove this method
    /*public void clearRepository() {
    		//this.defaultRepo.logout();
    		//this.defaultRepo.login(defaultRepo.getCredentials());
			this.defaultRepo.clearData();
    }*/

	/**
     * Creates a new category in the repository.
     * 
     * @param path Category path
     * @param name Category name
     * @param description Category description
     * @return True if category successfully created, otherwise false
     * @throws CategoryExistsException Thrown if rule set already exists
     * @throws RuleEngineRepositoryException
     */
    public Boolean createCategory(final String path, final String name, final String description) 
        throws CategoryExistsException {
        return this.ruleEngineRepository.createCategory(path, name, description);
    }

    /**
     * Removes a category.
     * 
     * @param categoryPath Category path
     */
    public void removeCategory(final String path) {
    	this.ruleEngineRepository.removeCategory(path);
    }

    /**
     * Loads child categories.
     * 
     * @param categoryPath Category path
     * @return List of child category names
     */
    public List<String> fetchChildCategories(final String path) 
	    throws CategoryExistsException {
	    return this.ruleEngineRepository.loadChildCategories(path);
	}
	
    /**
     * Creates, compiles and checks in a rule set into the repository.
     * 
     * @param ruleSet Rule set to create
     * @return Rule set uuid
     * @throws RuleExistsException Thrown if a rule within the rule set already exists
     * @throws RuleSetExistsException Thrown if rule set already exists
     * @throws RuleEngineRepositoryException
     */
    public RuleSetDTO createRuleSet(final RuleSetDTO ruleSetDTO) 
        throws RuleSetExistsException, RuleExistsException {
    	RuleSet ruleSet = ruleAdapter.getRuleSet(ruleSetDTO);
        RuleSet newRuleSet = this.ruleEngineRepository.createRuleSet(ruleSet);
        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(newRuleSet);
        return dto;
    }

    /**
     * Deletes a rule set by uuid.
     * 
     * @param uuid Rule set uuid
     * @throws RuleEngineRepositoryException Thrown if removing rule set fails
     */
    public void removeRuleSet(final String uuid) {
    	this.ruleEngineRepository.removeRuleSet(uuid);
    }

    /**
     * Deletes a rule set snapshot. 
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @throws RuleEngineRepositoryException Thrown if snapshot fails to be deleted or any other errors occur
     */
    public void removeRuleSetSnapshot(final String ruleSetName, final String snapshotName) {
    	this.ruleEngineRepository.removeRuleSetSnapshot(ruleSetName, snapshotName);
    }
    
    /**
     * Updates a rule set in the repository and returns a new rule set.
     * 
     * @param ruleSet A rule set to update
     * @return An updated rule set
     * @throws RuleEngineRepositoryException
     */
    /*public RuleSetDTO updateRuleSet(RuleSetDTO ruleSetDTO) {
    	RuleSet ruleSet = ruleAdapter.getRuleSet(ruleSetDTO);
        RuleSet updatedRuleSet = this.ruleEngineRepository.updateRuleSet(ruleSet);
        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(updatedRuleSet);
        return dto;
    }*/

    /**
     * Checks in a rule set into the repository.
     * Checkin rule set will create a new version of the rule set.
     * Rule set version is incremented by 1.
     * 
     * @param uuid Rule set uuid
     * @param comment Checkin comments
     * @throws RuleEngineRepositoryException
     */
    public long checkinRuleSet(final String uuid, final String comment) {
        return this.ruleEngineRepository.checkinRuleSet(uuid, comment);
    }

    /**
     * Loads a rule by uuid.
     * 
     * @param uuid Rule uuid
     * @return A rule
     * @throws RuleEngineRepositoryException Thrown if loading rule fails
     */
    /*public RuleDTO fetchRule(final String uuid) {
    	Rule rule = this.ruleEngineRepository.loadRule(uuid);
    	RuleDTO dto = ruleAdapter.getRuleDTO(rule);
    	return dto;
    }*/
    
    /**
     * Loads a rule set (including all rules) by UUID from the repository.
     * 
     * @param uuid Rule set uuid
     * @return A rule set
     * @throws RuleEngineRepositoryException
     */
    public RuleSetDTO fetchRuleSet(final String ruleSetUUID) {
        RuleSet ruleSet = this.ruleEngineRepository.loadRuleSet(ruleSetUUID);
        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(ruleSet);
        return dto;
    }

    /**
     * Loads a compiled rule set from the repository.
     * 
     * @param ruleSetUUID Rule set UUID
     * @return A compiled rule set as a byte array (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public byte[] fetchCompiledRuleSet(final String ruleSetUUID) {
        return this.ruleEngineRepository.loadCompiledRuleSetAsBytes(ruleSetUUID);
    }

    /**
     * Creates a new rule set snapshot for deployment and stores it in the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @param comment Comments for creating the snapshot
     * @throws RuleEngineRepositoryException
     */
    public void createRuleSetSnapshot(final String ruleSetName, final String snapshotName, final String comment) {
        this.ruleEngineRepository.createRuleSetSnapshot(ruleSetName, snapshotName, comment);
    }

    /**
     * Rebuilds (recompiles) an existing rule set snapshot and stores it in the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @param comment Comments for creating the snapshot
     * @throws RuleEngineRepositoryException
     */
    public void rebuildRuleSetSnapshot(final String ruleSetName, final String snapshotName, final String comment) {
        this.ruleEngineRepository.rebuildRuleSetSnapshot(ruleSetName, snapshotName, comment);
    }

    /**
     * Loads a compiled rule set snapshot from the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @return Compiled rule set as a byte array (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public byte[] fetchCompiledRuleSetSnapshot(final String ruleSetName, final String snapshotName) {
        return this.ruleEngineRepository.loadCompiledRuleSetSnapshotAsBytes(ruleSetName, snapshotName);
    }

    /**
     * Loads a rule set snapshot.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Rule set's snapshot name
     * @return A rule set snapshot
     */
    public RuleSetDTO fetchRuleSetSnapshot(final String ruleSetName, final String snapshotName) {
        RuleSet ruleSet = this.ruleEngineRepository.loadRuleSetSnapshot(ruleSetName, snapshotName);
        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(ruleSet);
        return dto;
    }

    /**
     * Creates a new status if it doesn't already exists.
     * 
     * @param name Status name
     * @return New status uuid
     */
    public String createState(final String name) {
    	return this.ruleEngineRepository.createStatus(name);
    }
    
    /**
     * Loads all states.
     * 
     * @return Array of all states (statuses)
     */
    public String[] fetchStates() {
    	return this.ruleEngineRepository.loadStates();
    }

    /**
     * Removes a status from the repository.
     * 
     * @param uuid Status name
     */
    public void removeState(String name) {
    	this.ruleEngineRepository.removeStatus(name);
    }

    /**
     * Changes a rule set status by uuid.
     * 
     * @param uuid Rule set uuid
     * @param newState New rule set status
     */
    public void changeRuleSetState(final String ruleSetUUID, final String newState) {
    	this.ruleEngineRepository.changeRuleSetStatus(ruleSetUUID, newState);
    }

    /**
     * TODO: Needs fixing and testing...
     * 
     * @param businessRuleContainerDTO
     * @return
     * @throws RuleSetTranslatorException
     */
    public RuleSetDTO generateRuleSet(BusinessRuleContainerDTO businessRuleContainer) throws RuleSetTranslatorException {
    	RuleSet ruleSet = ruleSetTranslator.translate(businessRuleContainer);
    	ruleSet = this.ruleEngineRepository.createRuleSet(ruleSet);
    	RuleSetDTO dto = ruleAdapter.getRuleSetDTO(ruleSet);
    	return dto;
    }

}
